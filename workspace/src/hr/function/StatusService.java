package hr.function;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import hr.dao.TrackingJobDao;
import hr.model.StatusModel;
import hr.repository.ITrackingJobRepository;
import hr.repository.RepositoryInstanceManager;

public class StatusService {
	private static final Logger log = Logger.getLogger(StatusService.class);
	
	public static List<StatusModel> statusListInquiry(String fromDate, String toDate, String taskName) {
		//List<TrackingJobDao> trackingJobList = trackingJobRepository.findByDate("06-05-2020", "06-05-2020");
		ITrackingJobRepository trackingJobRepository = RepositoryInstanceManager.getTrackingJobRepositoryInstance();
		List<StatusModel> statusModelList = new ArrayList<>();
		try {
			List<TrackingJobDao> trackingJobList;
			if (taskName != null && taskName.endsWith("Job")) {
				trackingJobList = trackingJobRepository.findByDateAndTaskName(fromDate, toDate, taskName);
			} else {
				trackingJobList = trackingJobRepository.findByDate(fromDate, toDate);
			}
			for(TrackingJobDao trackingJob : trackingJobList) {
				StatusModel statusModel = new StatusModel();
				statusModel.setJobName(trackingJob.getJobName());
				statusModel.setId(Long.toString(trackingJob.getId()));
				statusModel.setReferenceId(Long.toString(trackingJob.getReferenceId()));
				statusModel.setType(trackingJob.getType());
				statusModel.setDateOfType(Integer.toString(trackingJob.getDateOfType()));
				statusModel.setCreatedAt(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(trackingJob.getCreatedAt()));
				statusModel.setUpdatedAt(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(trackingJob.getUpdatedAt()));
				statusModel.setStatus(trackingJob.getStatus());
				statusModel.setMessage(trackingJob.getMessage());
				statusModelList.add(statusModel);
			}
		} catch(Exception e) {
			log.error(e);
		}
		return statusModelList;
	}
}
