package com.ztesoft.oaas.vo;

public class StaffInfo {
	private String staffCode = "" ;//员工工号
	private String staffName = "";//员工姓名
	private String businessId ;//营业区ID
	private String businessCode ="";//营业区编码
	private String lanId ;//本地网ID
	private String partyId ;//所属部门ID
	private String partyCode;//所属部门编码
	private String partyName ;//所属部门名称
	private String departType ;//部门类型
	private String channelSegmentId ;//渠道ID
	private String termCode ;
	private String companyId ;//分公司ID
	private String groupId ;//班组ID
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getChannelSegmentId() {
		return channelSegmentId;
	}
	public void setChannelSegmentId(String channelSegmentId) {
		this.channelSegmentId = channelSegmentId;
	}
	public String getDepartType() {
		return departType;
	}
	public void setDepartType(String departType) {
		this.departType = departType;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getLanId() {
		return lanId;
	}
	public void setLanId(String lanId) {
		this.lanId = lanId;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getPartyCode() {
		return partyCode;
	}
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
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
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}
