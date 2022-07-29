package hr.dto.response;

import com.google.gson.annotations.SerializedName;

import hr.dto.BaseDto;

public abstract class ResponseBaseDto extends BaseDto {
	@SerializedName("responseCode")
	private String responseCode;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
}
