package hr.dto.request.define;

import com.google.gson.annotations.SerializedName;

import hr.dto.request.RequestBaseDto;

public class ScheduleSettingDto extends RequestBaseDto {
	@SerializedName("jobName")
	private String jobName;
	@SerializedName("beginHourAt")
	private String beginHourAt;
	@SerializedName("beginMinuteAt")
	private String beginMinuteAt;
	@SerializedName("endHourAt")
	private String endHourAt;
	@SerializedName("endMinuteAt")
	private String endMinuteAt;
	@SerializedName("interval")
	private String interval;
	@SerializedName("dayOfWeek")
	private String dayOfWeek;
	@SerializedName("dayOfMonth")
	private String dayOfMonth;
	@SerializedName("type")
	private String type;
	@SerializedName("id")
	private String id;
	@SerializedName("isRunning")
	private String isRunning;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getBeginHourAt() {
		return beginHourAt;
	}
	public void setBeginHourAt(String beginHourAt) {
		this.beginHourAt = beginHourAt;
	}
	public String getBeginMinuteAt() {
		return beginMinuteAt;
	}
	public void setBeginMinuteAt(String beginMinuteAt) {
		this.beginMinuteAt = beginMinuteAt;
	}
	public String getEndHourAt() {
		return endHourAt;
	}
	public void setEndHourAt(String endHourAt) {
		this.endHourAt = endHourAt;
	}
	public String getEndMinuteAt() {
		return endMinuteAt;
	}
	public void setEndMinuteAt(String endMinuteAt) {
		this.endMinuteAt = endMinuteAt;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(String isRunning) {
		this.isRunning = isRunning;
	}
}
