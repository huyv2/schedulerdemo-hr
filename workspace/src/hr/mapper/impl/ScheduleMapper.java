package hr.mapper.impl;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import hr.dao.ScheduleDao;
import hr.mapper.DaoMapper;

public class ScheduleMapper implements DaoMapper<ScheduleDao> {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public ScheduleDao mapRow(ResultSet rs) {
		ScheduleDao scheduleDao = null;
		try {
			scheduleDao = new ScheduleDao();
			scheduleDao.setJobName(rs.getString("JOB_NAME"));
			scheduleDao.setBeginHourAt(rs.getInt("BEGIN_HOUR_AT"));
			scheduleDao.setBeginMinuteAt(rs.getInt("BEGIN_MINUTE_AT"));
			scheduleDao.setEndHourAt(rs.getInt("END_HOUR_AT"));
			scheduleDao.setEndMinuteAt(rs.getInt("END_MINUTE_AT"));
			scheduleDao.setInterval(rs.getInt("INTERVAL"));
			scheduleDao.setDayOfWeek(rs.getInt("DAY_OF_WEEK"));
			scheduleDao.setDayOfMonth(rs.getInt("DAY_OF_MONTH"));
			scheduleDao.setType(rs.getString("TYPE"));
			scheduleDao.setIsRunning(Boolean.parseBoolean(rs.getString("IS_RUNNING")));
			scheduleDao.setCreatedAt(rs.getTimestamp("CREATED_AT"));
			scheduleDao.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
			scheduleDao.setId(rs.getLong("ID"));
		} catch(Exception e) {
			log.error(e);
		}
		return scheduleDao;
	}
}
