package com.ztesoft.vsop.order.vo;

public class SPSignInfoVO {
	String SPSignID="";
	String SPID="";
	String serviceCapabilityID="";
	String effectiveTime="";
	String expiryTime="";
	String chargingPolicyID="";
	String state = "";
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getChargingPolicyID() {
		return chargingPolicyID;
	}
	public void setChargingPolicyID(String chargingPolicyID) {
		this.chargingPolicyID = chargingPolicyID;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getServiceCapabilityID() {
		return serviceCapabilityID;
	}
	public void setServiceCapabilityID(String serviceCapabilityID) {
		this.serviceCapabilityID = serviceCapabilityID;
	}
	public String getSPID() {
		return SPID;
	}
	public void setSPID(String spid) {
		SPID = spid;
	}
	public String getSPSignID() {
		return SPSignID;
	}
	public void setSPSignID(String signID) {
		SPSignID = signID;
	}
}
