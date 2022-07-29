package hr.repository.impl;

import java.util.List;

import hr.dao.TrackingJobDao;
import hr.mapper.impl.TrackingJobMapper;
import hr.repository.ITrackingJobRepository;

public class TrackingJobRepository extends BaseRepository<TrackingJobDao> implements ITrackingJobRepository {

	@Override
	public List<TrackingJobDao> findByDate(String fromDate, String toDate) {
		String sql = "Select JOB_NAME, TYPE, DATE_OF_TYPE, STATUS, MESSAGE, CREATED_AT, UPDATED_AT, REFERENCE_ID, ID from TRACKING_JOB"
				+ " where trunc(CREATED_AT) between to_date(?, 'DD-MM-YYYY') and to_date(?, 'DD-MM-YYYY') order by ID desc";
		return query(sql, new TrackingJobMapper(), fromDate, toDate);
	}
	
	@Override
	public List<TrackingJobDao> findByDateAndTaskName(String fromDate, String toDate, String taskName) {
		String sql = "Select JOB_NAME, TYPE, DATE_OF_TYPE, STATUS, MESSAGE, CREATED_AT, UPDATED_AT, REFERENCE_ID, ID from TRACKING_JOB"
				+ " where trunc(CREATED_AT) between to_date(?, 'DD-MM-YYYY') and to_date(?, 'DD-MM-YYYY') and JOB_NAME=? order by ID desc";
		return query(sql, new TrackingJobMapper(), fromDate, toDate, taskName);
	}

	@Override
	public Long insert(TrackingJobDao trackingJobDao) {
		String sql = "Insert into TRACKING_JOB(JOB_NAME, TYPE, DATE_OF_TYPE, STATUS, REFERENCE_ID, ID)"
				+ "Values(?, ?, ?, ?, ?, ?)";
		String sqlIdentifier = "Select TRACKING_JOB_SEQ.nextval from dual";
		return insert(sql, sqlIdentifier, trackingJobDao.getJobName(), trackingJobDao.getType(),
				trackingJobDao.getDateOfType(), trackingJobDao.getStatus(),
				trackingJobDao.getReferenceId());
	}

	@Override
	public boolean update(TrackingJobDao trackingJobDao) {
		String sql = "Update TRACKING_JOB set STATUS = ?, MESSAGE = ?, UPDATED_AT = sysdate where ID = ?";
		return update(sql, trackingJobDao.getStatus(), trackingJobDao.getMessage(), trackingJobDao.getId());
	}
}
