package hr.dto.response.define;

import com.google.gson.annotations.SerializedName;

import hr.dto.response.ResponseBaseDto;

public class ScheduleSettingDto extends ResponseBaseDto {
	@SerializedName("id")
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
