package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class SimpleDataPrivilegeVO implements Serializable{
	private String dataId = "";
	private String dataName = "";
	private String isChecked = "";
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDataName() {
		return dataName;
	}
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
}
