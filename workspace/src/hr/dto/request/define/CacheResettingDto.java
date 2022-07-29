package hr.dto.request.define;

import com.google.gson.annotations.SerializedName;

import hr.dto.request.RequestBaseDto;

public class CacheResettingDto extends RequestBaseDto {
	@SerializedName("cacheName")
	private String cacheName;

	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
}
