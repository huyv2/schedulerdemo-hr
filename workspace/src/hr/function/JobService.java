package hr.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.cache.object.ExpireObject;
import hr.constant.CacheName;
import hr.dao.ScheduleDao;
import hr.model.JobModel;

public class JobService {
	private static final Logger log = Logger.getLogger(JobService.class);
	
	public static List<JobModel> getJobList() {
		List<JobModel> jobList = new ArrayList<>();
		
		try {
			Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
			Map<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) cache.getAll();
			for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
				String key = cacheMap.getKey();
				JobModel jobModel = new JobModel();
				jobModel.setName(key);
				jobList.add(jobModel);
			}
		} catch(Exception e) {
			log.error(e);
		}
		
		return jobList;
	}
	
	public static List<JobModel> getScheduleJobList(){
		List<JobModel> jobList = new ArrayList<>();
		
		try {
			Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
			Map<String, Object> cacheMaps = (ConcurrentHashMap<String, Object>) cache.getAll();
			for(Map.Entry<String, Object> cacheMap : cacheMaps.entrySet()) {
				ExpireObject expireObject = (ExpireObject) cacheMap.getValue();
				ScheduleDao schedule = (ScheduleDao) expireObject.getValueObject();
				JobModel jobModel = new JobModel();
				jobModel.setName(schedule.getJobName());
				jobModel.setId(Long.toString(schedule.getId()));
				jobList.add(jobModel);
			}
		} catch(Exception e) {
			log.error(e);
		}
		
		return jobList;
	}
}
