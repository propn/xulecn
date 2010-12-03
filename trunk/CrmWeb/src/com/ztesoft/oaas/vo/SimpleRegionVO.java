package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class SimpleRegionVO implements Serializable {
	private String regionId = "";
	private String regionName = "";
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
