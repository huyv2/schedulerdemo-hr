package hr.dao;

public class ScheduleDao extends BaseDao {
	private String jobName;
	private int beginHourAt;
	private int beginMinuteAt;
	private int endHourAt;
	private int endMinuteAt;
	private int interval;
	private int dayOfWeek;
	private int dayOfMonth;
	private String type;
	private boolean isRunning;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getBeginHourAt() {
		return beginHourAt;
	}
	public void setBeginHourAt(int beginHourAt) {
		this.beginHourAt = beginHourAt;
	}
	public int getBeginMinuteAt() {
		return beginMinuteAt;
	}
	public void setBeginMinuteAt(int beginMinuteAt) {
		this.beginMinuteAt = beginMinuteAt;
	}
	public int getEndHourAt() {
		return endHourAt;
	}
	public void setEndHourAt(int endHourAt) {
		this.endHourAt = endHourAt;
	}
	public int getEndMinuteAt() {
		return endMinuteAt;
	}
	public void setEndMinuteAt(int endMinuteAt) {
		this.endMinuteAt = endMinuteAt;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
