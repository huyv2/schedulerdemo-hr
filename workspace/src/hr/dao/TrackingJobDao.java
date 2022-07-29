package hr.dao;

public class TrackingJobDao extends BaseDao {
	private String jobName;
	private String type;
	private int dateOfType;
	private String status;
	private Long referenceId;
	private String message;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDateOfType() {
		return dateOfType;
	}
	public void setDateOfType(int dateOfType) {
		this.dateOfType = dateOfType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
