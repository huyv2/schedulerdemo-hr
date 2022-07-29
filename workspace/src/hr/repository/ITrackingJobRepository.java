package hr.repository;

import java.util.List;

import hr.dao.TrackingJobDao;

public interface ITrackingJobRepository extends IGenericRepository<TrackingJobDao> {
	List<TrackingJobDao> findByDate(String fromDate, String toDate);
	List<TrackingJobDao> findByDateAndTaskName(String fromDate, String toDate, String taskName);
	Long insert(TrackingJobDao trackingJobDao);
	boolean update(TrackingJobDao trackingJobDao);
}
