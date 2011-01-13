package com.ztesoft.oaas.vo;

import java.io.Serializable;
public class PartnerQueryVO implements Serializable{
	private String partnerCode = "" ;
	private String partnerType  = "" ;
	private String cooperateType = "" ;
	private String partnerGrade = "" ;
	private String orgId = "" ;
	private String staffId = "" ;
	private String superPartnerId = "";
	public String getSuperPartnerId() {
		return superPartnerId;
	}
	public void setSuperPartnerId(String superPartnerId) {
		this.superPartnerId = superPartnerId;
	}
	public String getCooperateType() {
		return cooperateType;
	}
	public void setCooperateType(String cooperateType) {
		this.cooperateType = cooperateType;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getPartnerGrade() {
		return partnerGrade;
	}
	public void setPartnerGrade(String partnerGrade) {
		this.partnerGrade = partnerGrade;
	}
	public String getPartnerType() {
		return partnerType;
	}
	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
}
