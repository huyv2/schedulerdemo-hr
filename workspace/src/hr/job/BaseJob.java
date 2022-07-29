package hr.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import org.apache.log4j.Logger;

import hr.constant.ParamConstant;
import hr.dao.ScheduleDao;
import hr.dao.TrackingJobDao;
import hr.repository.ITrackingJobRepository;
import hr.repository.RepositoryInstanceManager;

public abstract class BaseJob implements Runnable {
	private Class<? extends BaseJob> classObject = this.getClass();
	public final Logger log = Logger.getLogger(classObject);
	private ITrackingJobRepository trackingJobRepository = RepositoryInstanceManager.getTrackingJobRepositoryInstance();
	
	private Long id = null;
	private TrackingJobDao trackingJobDao = new TrackingJobDao();
	private ScheduleDao schedule;
	private String className = classObject.getSimpleName();
	public void setSchedule(ScheduleDao schedule) {
		this.schedule = schedule;
	}
	public String getClassName() {
		return className;
	}

	@Override
	public void run() {
		synchronized(this) {
			try {
				log.info(String.format("%s starts running - reference schedule id %s", className, schedule.getId()));
				
				trackingJobDao.setJobName(schedule.getJobName());
				trackingJobDao.setType(schedule.getType());
				trackingJobDao.setReferenceId(schedule.getId());
				trackingJobDao.setStatus(ParamConstant.JOB_RUNNING);
				if (schedule.getType().equals(ParamConstant.DB_DAILY_JOB_TYPE)) {
					Calendar cNow = Calendar.getInstance();
					int date = cNow.get(Calendar.DATE);
					trackingJobDao.setDateOfType(date);
				} else if (schedule.getType().equals(ParamConstant.DB_WEEKLY_JOB_TYPE)) {
					trackingJobDao.setDateOfType(schedule.getDayOfWeek());
				} else if (schedule.getType().equals(ParamConstant.DB_MONTHLY_JOB_TYPE)) {
					trackingJobDao.setDateOfType(schedule.getDayOfMonth());
				}
				id = trackingJobRepository.insert(trackingJobDao);
				if (id != null) {
					trackingJobDao.setId(id);
					// Put to cache if we use caching to save tracking data
				}
				
				log.info(String.format("%s is running - reference schedule id %s - generating id %s", className, trackingJobDao.getReferenceId(), id));
				
				execute();
				
				if (id != null) {
					trackingJobDao.setStatus(ParamConstant.JOB_FINISH);
					trackingJobDao.setMessage("");
					if (trackingJobRepository.update(trackingJobDao)) {
						// Put to cache if we use caching to save tracking data
					}
				}
			} catch(Exception e) {
				log.error(e);
				setErrorLogView(e);
			} finally {
				try {
					if (id != null) {
						log.info(String.format("%s finishes - reference schedule id %s - generating id %s", className, trackingJobDao.getReferenceId(), trackingJobDao.getId()));
					} else {
						log.info(String.format("%s finishes in an error - reference schedule id %s - generating id %s", className, trackingJobDao.getReferenceId(), trackingJobDao.getId()));
					}
				} catch(Exception e) {
					log.error(e);
					setErrorLogView(e);
				}
			}
		}
	}
	
	protected abstract void execute() throws Exception;
	
	public void setErrorLogView(Exception e) {
		try {
			if (id != null) {
				trackingJobDao.setStatus(ParamConstant.JOB_ERROR);
				StringBuilder sbMessage = new StringBuilder();
				if (e != null) {
					//sbMessage.append(e.toString());
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					sbMessage.append(errors);
					if (e.getLocalizedMessage() != null) {
						sbMessage.append("-").append(e.getLocalizedMessage());
					}
					if (e.getMessage() != null) {
						sbMessage.append(e.getMessage());
					}
					if (e.getCause() != null) {
						sbMessage.append(e.getCause());
					}
					if (e.getStackTrace() != null) {
						sbMessage.append(e.getStackTrace());
					}
				}
				trackingJobDao.setMessage(sbMessage.toString());
				if (trackingJobRepository.update(trackingJobDao)) {
					// Put to cache if we use caching to save tracking data
				}
			} else {
				log.info("Cannot log error due to no data in tracking");
			}
		} catch(Exception e1) {
			log.error(e1);
		} finally {
			id = null;
		}
	}
	
	protected void disableSchedule() {
		schedule.setIsRunning(false);
		RepositoryInstanceManager.getScheduleRepositoryInstance().updateDisablingSchedule(schedule);
		log.debug(String.format("Disabled this job day - Schedule id: %s", schedule != null ? schedule.getId() : "null"));
	}
}
