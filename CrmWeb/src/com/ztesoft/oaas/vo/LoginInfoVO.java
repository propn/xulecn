package com.ztesoft.oaas.vo;

import java.io.Serializable;
public class LoginInfoVO implements Serializable{
	private String staffCode ;
	private String partyName ;
	private String partyId ;
	private String businessId ;
	private String businessName ;
	private String lanId ;
	private String lanName ;
	private String channelSegmentId ;
	private String ip;
	private String operOrgName ;//所属组织名称
	private String operOrgId ;//所属组织ID
	private String operOrgLevel ;//所属组织级别
	   
	/**
	 * @return Returns the operOrgLevel.
	 */
	public String getOperOrgLevel() {
		return operOrgLevel;
	}
	/**
	 * @param operOrgLevel The operOrgLevel to set.
	 */
	public void setOperOrgLevel(String operOrgLevel) {
		this.operOrgLevel = operOrgLevel;
	}
	public String getOperOrgId() {
		return operOrgId;
	}
	public void setOperOrgId(String operOrgId) {
		this.operOrgId = operOrgId;
	}
	public String getOperOrgName() {
		return operOrgName;
	}
	public void setOperOrgName(String operOrgName) {
		this.operOrgName = operOrgName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getChannelSegmentId() {
		return channelSegmentId;
	}
	public void setChannelSegmentId(String channelSegmentId) {
		this.channelSegmentId = channelSegmentId;
	}
	public String getLanId() {
		return lanId;
	}
	public void setLanId(String lanId) {
		this.lanId = lanId;
	}
	public String getLanName() {
		return lanName;
	}
	public void setLanName(String lanName) {
		this.lanName = lanName;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
}
