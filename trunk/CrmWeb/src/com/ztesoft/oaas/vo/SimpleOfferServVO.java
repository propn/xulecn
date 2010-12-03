package com.ztesoft.oaas.vo;

import java.io.Serializable;
public class SimpleOfferServVO implements Serializable {
	private String servOffId = "";
	private String servOffName = "";
	private String isChecked = "";
	
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getServOffId() {
		return servOffId;
	}
	public void setServOffId(String servOffId) {
		this.servOffId = servOffId;
	}
	public String getServOffName() {
		return servOffName;
	}
	public void setServOffName(String servOffName) {
		this.servOffName = servOffName;
	}
}
