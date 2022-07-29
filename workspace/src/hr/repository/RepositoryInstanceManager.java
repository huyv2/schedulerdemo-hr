package hr.repository;

import hr.repository.impl.EAbcTransactionRepository;
import hr.repository.impl.ScheduleRepository;
import hr.repository.impl.TrackingJobRepository;

public class RepositoryInstanceManager {
	private static IScheduleRepository scheduleRepositoryInstance = null;
	private static ITrackingJobRepository trackingJobRepositoryInstance = null;
	private static IEAbcTransactionRepository eAbcTransactionRepositoryInstance = null;
	
	public static IScheduleRepository getScheduleRepositoryInstance() {
		if (scheduleRepositoryInstance == null) {
			scheduleRepositoryInstance = new ScheduleRepository();
		}
		return scheduleRepositoryInstance;
	}
	public static ITrackingJobRepository getTrackingJobRepositoryInstance() {
		if (trackingJobRepositoryInstance == null) {
			trackingJobRepositoryInstance = new TrackingJobRepository();
		}
		return trackingJobRepositoryInstance;
	}
	public static IEAbcTransactionRepository getEAbcTransactionInstance() {
		if (eAbcTransactionRepositoryInstance == null) {
			eAbcTransactionRepositoryInstance = new EAbcTransactionRepository();
		}
		return eAbcTransactionRepositoryInstance;
	}
}
