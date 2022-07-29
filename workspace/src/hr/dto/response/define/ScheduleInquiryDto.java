package hr.dto.response.define;

import com.google.gson.annotations.SerializedName;

import hr.dto.response.ResponseBaseDto;

public class ScheduleInquiryDto extends ResponseBaseDto {
	@SerializedName("type")
	private String type;
	@SerializedName("beginHourAt")
	private int beginHourAt;
	@SerializedName("beginMinuteAt")
	private int beginMinuteAt;
	@SerializedName("endHourAt")
	private int endHourAt;
	@SerializedName("endMinuteAt")
	private int endMinuteAt;
	@SerializedName("interval")
	private int interval;
	@SerializedName("dayOfWeek")
	private int dayOfWeek;
	@SerializedName("dayOfMonth")
	private int dayOfMonth;
	@SerializedName("isRunning")
	private String isRunning;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getIsRunning() {
		return isRunning;
	}
	public void setIsRunning(String isRunning) {
		this.isRunning = isRunning;
	}
}
