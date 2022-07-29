package hr.execution;

import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.cache.object.ExpireObject;
import hr.config.Config;
import hr.constant.CacheName;
import hr.constant.ParamConstant;
import hr.dao.ScheduleDao;
import hr.factory.ExecutorFactory;
import hr.job.BaseJob;

public class Scheduler {
	private final Logger log = Logger.getLogger(Scheduler.class);
	private static Scheduler scheduler = null;
	private Timer timer = null;
	private long intervalUnit;
	private int intervalUnitInSecond;
	
	public static Scheduler getInstance() {
		if (scheduler == null) {
			scheduler = new Scheduler();
		}
		return scheduler;
	}
	
	private Scheduler() {
		try {
			intervalUnit = Long.parseLong(Config.getValue("intervalUnit"));
			if (intervalUnit > 60000) {
				intervalUnit = 60000; 
			}
		} catch(Exception e) {
			intervalUnit = 60000;
		}
		intervalUnitInSecond = (int) intervalUnit/1000;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
					Map<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) cache.getAll();
					for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
						ExpireObject expireObject = (ExpireObject) cacheMap.getValue();
						ScheduleDao schedule = (ScheduleDao) expireObject.getValueObject();
						if (schedule.getIsRunning()) {
							Calendar cNow = Calendar.getInstance();
							if (
									(schedule.getType().equals(ParamConstant.DB_DAILY_JOB_TYPE)) ||
									(schedule.getType().equals(ParamConstant.DB_WEEKLY_JOB_TYPE) && cNow.get(Calendar.DAY_OF_WEEK) == schedule.getDayOfWeek()) ||
									(schedule.getType().equals(ParamConstant.DB_MONTHLY_JOB_TYPE) && cNow.get(Calendar.DAY_OF_MONTH) == schedule.getDayOfMonth())
								) {
								doSchedule(schedule, cNow);
							}
						}
					}
				} catch(Exception e) {
					log.error(e);
				}
			}
			
		}, 0, intervalUnit);
	}
	
	public void init() {}
	
	private void doSchedule(ScheduleDao schedule, Calendar cNow) throws InstantiationException, IllegalAccessException {
		if ((schedule.getBeginHourAt() == schedule.getEndHourAt() && schedule.getBeginMinuteAt() == schedule.getEndMinuteAt())) {
			if (cNow.get(Calendar.HOUR_OF_DAY) == schedule.getBeginHourAt() &&
					cNow.get(Calendar.MINUTE) == schedule.getBeginMinuteAt() &&
					cNow.get(Calendar.SECOND) < intervalUnitInSecond) {
				Cache jobCache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
				BaseJob job = (BaseJob) jobCache.get(schedule.getJobName());
				if (job != null) {
					job.setSchedule(schedule);
					ExecutorFactory.execute(job, null);
				}
				job = null;
			}
		} else {
			Calendar cBeginSchedule = Calendar.getInstance();
			Calendar cEndSchedule = Calendar.getInstance();
			cBeginSchedule.set(Calendar.HOUR_OF_DAY, schedule.getBeginHourAt());
			cBeginSchedule.set(Calendar.MINUTE, schedule.getBeginMinuteAt());
			cBeginSchedule.set(Calendar.SECOND, 0);
			cEndSchedule.set(Calendar.HOUR_OF_DAY, schedule.getEndHourAt());
			cEndSchedule.set(Calendar.MINUTE, schedule.getEndMinuteAt());
			cEndSchedule.set(Calendar.SECOND, 0);
			
			// Example IntervalUnit as 10s(10000), 15s(15000), 20s(20000), 30s(30000), ... until 60s
			long nowInIntervalUnit = (cNow.getTimeInMillis()/intervalUnit);
			long beginInIntervalUnit = (cBeginSchedule.getTimeInMillis()/intervalUnit);
			long endInIntervalUnit = (cEndSchedule.getTimeInMillis()/intervalUnit);
			
			if (nowInIntervalUnit >= beginInIntervalUnit && nowInIntervalUnit <= endInIntervalUnit) {
				int differenceInIntervalUnit = (int) (nowInIntervalUnit - beginInIntervalUnit);
				int intervalInIntervalUnit = schedule.getInterval();
				if ((differenceInIntervalUnit % intervalInIntervalUnit) == 0) {
					Cache jobCache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
					BaseJob job = (BaseJob) jobCache.get(schedule.getJobName());
					if (job != null) {
						job.setSchedule(schedule);
						ExecutorFactory.execute(job, null);
					}
					job = null;
				}
			}
		}
	}
	
	public void stop() {
		try {
			if (timer != null) {
				timer.cancel();
				timer.purge();
			}
			log.info("Stoped checking Scheduler successfully");
		} catch(Exception e) {
			log.error(e);
		}
	}
}
