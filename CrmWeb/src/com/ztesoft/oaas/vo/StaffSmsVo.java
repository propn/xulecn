package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class StaffSmsVo implements Serializable {
	private String staffCode;
	private String mobile;
	private String stateDate;
	public void setStaffCode(String staffCode){
		this.staffCode=staffCode;
	}
	public String getStaffCode(){
		return  staffCode;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return  mobile;
	}
	public void setStateDate(String stateDate){
		this.stateDate=stateDate;
	}
	public String getStateDate(){
		return stateDate;
	}

}
