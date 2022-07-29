package hr.dto.response.define;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import hr.dto.response.ResponseBaseDto;

public class LoadingInstanceJobDto extends ResponseBaseDto {
	@SerializedName("jobNameList")
	private List<String> jobNameList = null;

	public List<String> getJobNameList() {
		return jobNameList;
	}
	public void setJobNameList(List<String> jobNameList) {
		this.jobNameList = jobNameList;
	}
	
	public void add(String name) {
		if (jobNameList == null) {
			jobNameList = new ArrayList<String>();
		}
		jobNameList.add(name);
	}
}
