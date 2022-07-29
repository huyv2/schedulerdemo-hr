package hr.dto.request.define;

import com.google.gson.annotations.SerializedName;

import hr.dto.request.RequestBaseDto;

public class LoadingInstanceJobDto extends RequestBaseDto {
	@SerializedName("instanceJobName")
	private String instanceJobName;

	public String getInstanceJobName() {
		return instanceJobName;
	}
	public void setInstanceJobName(String instanceJobName) {
		this.instanceJobName = instanceJobName;
	}
}
