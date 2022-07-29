package hr.mapper.impl;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import hr.dao.TrackingJobDao;
import hr.mapper.DaoMapper;

public class TrackingJobMapper implements DaoMapper<TrackingJobDao> {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public TrackingJobDao mapRow(ResultSet rs) {
		TrackingJobDao trackingJobDao = null;
		try {
			trackingJobDao = new TrackingJobDao();
			trackingJobDao.setJobName(rs.getString("JOB_NAME"));
			trackingJobDao.setType(rs.getString("TYPE"));
			trackingJobDao.setDateOfType(Integer.parseInt(rs.getString("DATE_OF_TYPE")));
			trackingJobDao.setStatus(rs.getString("STATUS"));
			trackingJobDao.setMessage(rs.getString("MESSAGE"));
			trackingJobDao.setCreatedAt(rs.getTimestamp("CREATED_AT"));
			trackingJobDao.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
			trackingJobDao.setReferenceId(rs.getLong("REFERENCE_ID"));
			trackingJobDao.setId(rs.getLong("ID"));
		} catch(Exception e) {
			log.error(e);
		}
		return trackingJobDao;
	}
}
