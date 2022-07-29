package hr.repository.impl;

import java.util.List;

import hr.dao.ScheduleDao;
import hr.mapper.impl.ScheduleMapper;
import hr.repository.IScheduleRepository;

public class ScheduleRepository extends BaseRepository<ScheduleDao> implements IScheduleRepository {

	@Override
	public List<ScheduleDao> findAll() {
		String sql = "Select JOB_NAME, BEGIN_HOUR_AT, BEGIN_MINUTE_AT, END_HOUR_AT, END_MINUTE_AT, INTERVAL, DAY_OF_WEEK, DAY_OF_MONTH, TYPE, case IS_RUNNING when 'Y' then 'true' else 'false' end as IS_RUNNING, CREATED_AT, UPDATED_AT, ID from SCHEDULE_JOB where IS_DELETED = ?";
		return query(sql, new ScheduleMapper(), "N");
	}

	@Override
	public Long insert(ScheduleDao scheduleDao) {
		String sql = "Insert into SCHEDULE_JOB(JOB_NAME, BEGIN_HOUR_AT, BEGIN_MINUTE_AT, END_HOUR_AT, END_MINUTE_AT, INTERVAL, DAY_OF_WEEK, DAY_OF_MONTH, TYPE, IS_DELETED, ID)"
				+ "Values(?, ?, ?, ?, ?, ?, ?, ?, ?, 'N', ?)";
		String sqlIdentifier = "Select SCHEDULE_JOB_SEQ.nextval from dual";
		return insert(sql, sqlIdentifier, scheduleDao.getJobName(), scheduleDao.getBeginHourAt(), scheduleDao.getBeginMinuteAt()
				, scheduleDao.getEndHourAt(), scheduleDao.getEndMinuteAt(), scheduleDao.getInterval(), scheduleDao.getDayOfWeek()
				, scheduleDao.getDayOfMonth(), scheduleDao.getType());
	}

	@Override
	public boolean update(ScheduleDao scheduleDao) {
		String sql = "Update SCHEDULE_JOB set JOB_NAME = ?, BEGIN_HOUR_AT = ?, BEGIN_MINUTE_AT = ?, END_HOUR_AT = ?, END_MINUTE_AT = ?,"
				+ "INTERVAL = ?, DAY_OF_WEEK = ?, DAY_OF_MONTH = ?, TYPE = ?, UPDATED_AT = sysdate where ID = ? and JOB_NAME = ? and IS_DELETED = 'N'";
		return update(sql, scheduleDao.getJobName(), scheduleDao.getBeginHourAt(), scheduleDao.getBeginMinuteAt(), scheduleDao.getEndHourAt(),
				scheduleDao.getEndMinuteAt(), scheduleDao.getInterval(), scheduleDao.getDayOfWeek(), scheduleDao.getDayOfMonth(),
				scheduleDao.getType(), scheduleDao.getId(), scheduleDao.getJobName());
	}

	@Override
	public boolean delete(Long id, Object... args) {
		String sql = "Update SCHEDULE_JOB set IS_DELETED = 'Y' where ID = ? and JOB_NAME = ? and IS_DELETED = 'N'";
		String jobName = (String) args[0];
		return update(sql, id, jobName);
	}

	@Override
	public boolean updateDisablingSchedule(ScheduleDao scheduleDao) {
		String sql = "Update SCHEDULE_JOB set IS_RUNNING = 'N', UPDATED_AT = sysdate where ID = ? and JOB_NAME = ? and IS_DELETED = 'N'";
		return update(sql, scheduleDao.getId(), scheduleDao.getJobName());
	}

	@Override
	public boolean updateEnablingSchedule(ScheduleDao scheduleDao) {
		String sql = "Update SCHEDULE_JOB set IS_RUNNING = 'Y', UPDATED_AT = sysdate where ID = ? and JOB_NAME = ? and IS_DELETED = 'N'";
		return update(sql, scheduleDao.getId(), scheduleDao.getJobName());
	}

	@Override
	public boolean updateResettingSchedule() {
		String sql = "Update SCHEDULE_JOB set IS_RUNNING = 'Y', UPDATED_AT = sysdate where IS_DELETED = 'N'";
		return update(sql);
	}
}
