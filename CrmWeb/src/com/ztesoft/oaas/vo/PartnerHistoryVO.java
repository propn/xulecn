package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartnerHistoryVO extends ValueObject implements VO {

	private String partnerHisId = "";
	private String partnerId = "";
	private String partyId = "";
	private String partnerCode = "";
	private String partnerType = "";
	private String partnerDesc = "";
	private String corpAgent = "";
	private String addr = "";
	private String applyDate = "";
	private String linkman = "";
	private String corporateLicenceNo = "";
	private String spcpLicenceNo = "";
	private String bankPermit = "";
	private String companySize = "";
	private String companyBalns = "";
	private String cooperateType = "";
	private String devPlan = "";
	private String partnerGrade = "";
	private String state = "";
	private String orgId = "";
	private String staffId = "";
	private String operId = "";
	private String operTime = "";
	private String superPartnerId = "";
	private String pathCode = "";

	public PartnerHistoryVO() {}

	public PartnerHistoryVO( String ppartnerHisId, String ppartnerId, String ppartyId, String ppartnerCode, String ppartnerType, String ppartnerDesc, String pcorpAgent, String paddr, String papplyDate, String plinkman, String pcorporateLicenceNo, String pspcpLicenceNo, String pbankPermit, String pcompanySize, String pcompanyBalns, String pcooperateType, String pdevPlan, String ppartnerGrade, String pstate, String porgId, String pstaffId, String poperId, String poperTime, String psuperPartnerId, String ppathCode ) {
		partnerHisId = ppartnerHisId;
		partnerId = ppartnerId;
		partyId = ppartyId;
		partnerCode = ppartnerCode;
		partnerType = ppartnerType;
		partnerDesc = ppartnerDesc;
		corpAgent = pcorpAgent;
		addr = paddr;
		applyDate = papplyDate;
		linkman = plinkman;
		corporateLicenceNo = pcorporateLicenceNo;
		spcpLicenceNo = pspcpLicenceNo;
		bankPermit = pbankPermit;
		companySize = pcompanySize;
		companyBalns = pcompanyBalns;
		cooperateType = pcooperateType;
		devPlan = pdevPlan;
		partnerGrade = ppartnerGrade;
		state = pstate;
		orgId = porgId;
		staffId = pstaffId;
		operId = poperId;
		operTime = poperTime;
		superPartnerId = psuperPartnerId;
		pathCode = ppathCode;
	}

	public String getPartnerHisId() {
		return partnerHisId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public String getPartnerDesc() {
		return partnerDesc;
	}

	public String getCorpAgent() {
		return corpAgent;
	}

	public String getAddr() {
		return addr;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public String getLinkman() {
		return linkman;
	}

	public String getCorporateLicenceNo() {
		return corporateLicenceNo;
	}

	public String getSpcpLicenceNo() {
		return spcpLicenceNo;
	}

	public String getBankPermit() {
		return bankPermit;
	}

	public String getCompanySize() {
		return companySize;
	}

	public String getCompanyBalns() {
		return companyBalns;
	}

	public String getCooperateType() {
		return cooperateType;
	}

	public String getDevPlan() {
		return devPlan;
	}

	public String getPartnerGrade() {
		return partnerGrade;
	}

	public String getState() {
		return state;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getStaffId() {
		return staffId;
	}

	public String getOperId() {
		return operId;
	}

	public String getOperTime() {
		return operTime;
	}

	public String getSuperPartnerId() {
		return superPartnerId;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPartnerHisId(String pPartnerHisId) {
		partnerHisId = pPartnerHisId;
	}

	public void setPartnerId(String pPartnerId) {
		partnerId = pPartnerId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setPartnerCode(String pPartnerCode) {
		partnerCode = pPartnerCode;
	}

	public void setPartnerType(String pPartnerType) {
		partnerType = pPartnerType;
	}

	public void setPartnerDesc(String pPartnerDesc) {
		partnerDesc = pPartnerDesc;
	}

	public void setCorpAgent(String pCorpAgent) {
		corpAgent = pCorpAgent;
	}

	public void setAddr(String pAddr) {
		addr = pAddr;
	}

	public void setApplyDate(String pApplyDate) {
		applyDate = pApplyDate;
	}

	public void setLinkman(String pLinkman) {
		linkman = pLinkman;
	}

	public void setCorporateLicenceNo(String pCorporateLicenceNo) {
		corporateLicenceNo = pCorporateLicenceNo;
	}

	public void setSpcpLicenceNo(String pSpcpLicenceNo) {
		spcpLicenceNo = pSpcpLicenceNo;
	}

	public void setBankPermit(String pBankPermit) {
		bankPermit = pBankPermit;
	}

	public void setCompanySize(String pCompanySize) {
		companySize = pCompanySize;
	}

	public void setCompanyBalns(String pCompanyBalns) {
		companyBalns = pCompanyBalns;
	}

	public void setCooperateType(String pCooperateType) {
		cooperateType = pCooperateType;
	}

	public void setDevPlan(String pDevPlan) {
		devPlan = pDevPlan;
	}

	public void setPartnerGrade(String pPartnerGrade) {
		partnerGrade = pPartnerGrade;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setOrgId(String pOrgId) {
		orgId = pOrgId;
	}

	public void setStaffId(String pStaffId) {
		staffId = pStaffId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setOperTime(String pOperTime) {
		operTime = pOperTime;
	}

	public void setSuperPartnerId(String pSuperPartnerId) {
		superPartnerId = pSuperPartnerId;
	}

	public void setPathCode(String pPathCode) {
		pathCode = pPathCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTNER_HIS_ID",this.partnerHisId);
		hashMap.put("PARTNER_ID",this.partnerId);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("PARTNER_CODE",this.partnerCode);
		hashMap.put("PARTNER_TYPE",this.partnerType);
		hashMap.put("PARTNER_DESC",this.partnerDesc);
		hashMap.put("CORPORATION_AGENT",this.corpAgent);
		hashMap.put("ADDRESS",this.addr);
		hashMap.put("APPLY_DATE",this.applyDate);
		hashMap.put("LINKMAN",this.linkman);
		hashMap.put("CORPORATE_LICENCE_NO",this.corporateLicenceNo);
		hashMap.put("SPCP_LICENCE_NO",this.spcpLicenceNo);
		hashMap.put("BANK_PERMIT",this.bankPermit);
		hashMap.put("COMPANY_SIZE",this.companySize);
		hashMap.put("COMPANY_BALANCE",this.companyBalns);
		hashMap.put("COOPERATE_TYPE",this.cooperateType);
		hashMap.put("DEV_PLAN",this.devPlan);
		hashMap.put("PARTNER_GRADE",this.partnerGrade);
		hashMap.put("STATE",this.state);
		hashMap.put("ORG_ID",this.orgId);
		hashMap.put("STAFF_ID",this.staffId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("OPER_TIME",this.operTime);
		hashMap.put("SUPER_PARTNER_ID",this.superPartnerId);
		hashMap.put("PATH_CODE",this.pathCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partnerHisId = (String) hashMap.get("PARTNER_HIS_ID");
			this.partnerId = (String) hashMap.get("PARTNER_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.partnerCode = (String) hashMap.get("PARTNER_CODE");
			this.partnerType = (String) hashMap.get("PARTNER_TYPE");
			this.partnerDesc = (String) hashMap.get("PARTNER_DESC");
			this.corpAgent = (String) hashMap.get("CORPORATION_AGENT");
			this.addr = (String) hashMap.get("ADDRESS");
			this.applyDate = (String) hashMap.get("APPLY_DATE");
			this.linkman = (String) hashMap.get("LINKMAN");
			this.corporateLicenceNo = (String) hashMap.get("CORPORATE_LICENCE_NO");
			this.spcpLicenceNo = (String) hashMap.get("SPCP_LICENCE_NO");
			this.bankPermit = (String) hashMap.get("BANK_PERMIT");
			this.companySize = (String) hashMap.get("COMPANY_SIZE");
			this.companyBalns = (String) hashMap.get("COMPANY_BALANCE");
			this.cooperateType = (String) hashMap.get("COOPERATE_TYPE");
			this.devPlan = (String) hashMap.get("DEV_PLAN");
			this.partnerGrade = (String) hashMap.get("PARTNER_GRADE");
			this.state = (String) hashMap.get("STATE");
			this.orgId = (String) hashMap.get("ORG_ID");
			this.staffId = (String) hashMap.get("STAFF_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.operTime = (String) hashMap.get("OPER_TIME");
			this.superPartnerId = (String) hashMap.get("SUPER_PARTNER_ID");
			this.pathCode = (String) hashMap.get("PATH_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTNER_HIS_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER_HISTORY";
	}

}
