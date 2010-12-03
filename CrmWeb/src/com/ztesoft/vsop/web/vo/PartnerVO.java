package com.ztesoft.vsop.web.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class PartnerVO extends ValueObject implements VO {

	private String partnerId = "";
	private String partnerCode = "";
	private String partnerType = "";
	private String partnerDesc = "";
	private String state = "";
	private String stateDate = "";
	private String partnerLevel = "";
	private String partnerName = "";
	private String partnerPassword = "";
	private String partnerAreaCode = "";
	private String partnerUrl = "";
	private String partnerIp = "";
	private String createDate = "";
	private String partnerEngName = "";
	private String partnerEngDesc = "";
	private String settleCycle = "";
	private String settlePayMethod = "";
	private String settleRate = "";
	private String custServPhone = "";
	private String ifRoam = "";
	private String companyAddr = "";
	private String artificialPerson = "";
	private String primaryPersonName = "";
	private String primaryPersonPhone = "";
	private String primaryPersonEmail = "";
	private String businessLicense = "";
	private String contractExpDate = "";
	private String companyCode = "";
	private String partnerNumber = "";
	private String custServUrl = "";
	private String systemCode = "";

	public PartnerVO() {}

	public PartnerVO( String ppartnerId, String ppartnerCode, String ppartnerType, String ppartnerDesc, String pstate, String pstateDate, String ppartnerLevel, String ppartnerName, String ppartnerPassword, String ppartnerAreaCode, String ppartnerUrl, String ppartnerIp, String pcreateDate, String ppartnerEngName, String ppartnerEngDesc, String psettleCycle, String psettlePayMethod, String psettleRate, String pcustServPhone, String pifRoam, String pcompanyAddr, String partificialPerson, String pprimaryPersonName, String pprimaryPersonPhone, String pprimaryPersonEmail, String pbusinessLicense, String pcontractExpDate, String pcompanyCode, String ppartnerNumber, String pcustServUrl, String psystemCode ) {
		partnerId = ppartnerId;
		partnerCode = ppartnerCode;
		partnerType = ppartnerType;
		partnerDesc = ppartnerDesc;
		state = pstate;
		stateDate = pstateDate;
		partnerLevel = ppartnerLevel;
		partnerName = ppartnerName;
		partnerPassword = ppartnerPassword;
		partnerAreaCode = ppartnerAreaCode;
		partnerUrl = ppartnerUrl;
		partnerIp = ppartnerIp;
		createDate = pcreateDate;
		partnerEngName = ppartnerEngName;
		partnerEngDesc = ppartnerEngDesc;
		settleCycle = psettleCycle;
		settlePayMethod = psettlePayMethod;
		settleRate = psettleRate;
		custServPhone = pcustServPhone;
		ifRoam = pifRoam;
		companyAddr = pcompanyAddr;
		artificialPerson = partificialPerson;
		primaryPersonName = pprimaryPersonName;
		primaryPersonPhone = pprimaryPersonPhone;
		primaryPersonEmail = pprimaryPersonEmail;
		businessLicense = pbusinessLicense;
		contractExpDate = pcontractExpDate;
		companyCode = pcompanyCode;
		partnerNumber = ppartnerNumber;
		custServUrl = pcustServUrl;
		systemCode = psystemCode;
	}

	public String getPartnerId() {
		return partnerId;
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

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getPartnerLevel() {
		return partnerLevel;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public String getPartnerPassword() {
		return partnerPassword;
	}

	public String getPartnerAreaCode() {
		return partnerAreaCode;
	}

	public String getPartnerUrl() {
		return partnerUrl;
	}

	public String getPartnerIp() {
		return partnerIp;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getPartnerEngName() {
		return partnerEngName;
	}

	public String getPartnerEngDesc() {
		return partnerEngDesc;
	}

	public String getSettleCycle() {
		return settleCycle;
	}

	public String getSettlePayMethod() {
		return settlePayMethod;
	}

	public String getSettleRate() {
		return settleRate;
	}

	public String getCustServPhone() {
		return custServPhone;
	}

	public String getIfRoam() {
		return ifRoam;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public String getArtificialPerson() {
		return artificialPerson;
	}

	public String getPrimaryPersonName() {
		return primaryPersonName;
	}

	public String getPrimaryPersonPhone() {
		return primaryPersonPhone;
	}

	public String getPrimaryPersonEmail() {
		return primaryPersonEmail;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public String getContractExpDate() {
		return contractExpDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getPartnerNumber() {
		return partnerNumber;
	}

	public String getCustServUrl() {
		return custServUrl;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setPartnerId(String pPartnerId) {
		partnerId = pPartnerId;
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

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setPartnerLevel(String pPartnerLevel) {
		partnerLevel = pPartnerLevel;
	}

	public void setPartnerName(String pPartnerName) {
		partnerName = pPartnerName;
	}

	public void setPartnerPassword(String pPartnerPassword) {
		partnerPassword = pPartnerPassword;
	}

	public void setPartnerAreaCode(String pPartnerAreaCode) {
		partnerAreaCode = pPartnerAreaCode;
	}

	public void setPartnerUrl(String pPartnerUrl) {
		partnerUrl = pPartnerUrl;
	}

	public void setPartnerIp(String pPartnerIp) {
		partnerIp = pPartnerIp;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setPartnerEngName(String pPartnerEngName) {
		partnerEngName = pPartnerEngName;
	}

	public void setPartnerEngDesc(String pPartnerEngDesc) {
		partnerEngDesc = pPartnerEngDesc;
	}

	public void setSettleCycle(String pSettleCycle) {
		settleCycle = pSettleCycle;
	}

	public void setSettlePayMethod(String pSettlePayMethod) {
		settlePayMethod = pSettlePayMethod;
	}

	public void setSettleRate(String pSettleRate) {
		settleRate = pSettleRate;
	}

	public void setCustServPhone(String pCustServPhone) {
		custServPhone = pCustServPhone;
	}

	public void setIfRoam(String pIfRoam) {
		ifRoam = pIfRoam;
	}

	public void setCompanyAddr(String pCompanyAddr) {
		companyAddr = pCompanyAddr;
	}

	public void setArtificialPerson(String pArtificialPerson) {
		artificialPerson = pArtificialPerson;
	}

	public void setPrimaryPersonName(String pPrimaryPersonName) {
		primaryPersonName = pPrimaryPersonName;
	}

	public void setPrimaryPersonPhone(String pPrimaryPersonPhone) {
		primaryPersonPhone = pPrimaryPersonPhone;
	}

	public void setPrimaryPersonEmail(String pPrimaryPersonEmail) {
		primaryPersonEmail = pPrimaryPersonEmail;
	}

	public void setBusinessLicense(String pBusinessLicense) {
		businessLicense = pBusinessLicense;
	}

	public void setContractExpDate(String pContractExpDate) {
		contractExpDate = pContractExpDate;
	}

	public void setCompanyCode(String pCompanyCode) {
		companyCode = pCompanyCode;
	}

	public void setPartnerNumber(String pPartnerNumber) {
		partnerNumber = pPartnerNumber;
	}

	public void setCustServUrl(String pCustServUrl) {
		custServUrl = pCustServUrl;
	}

	public void setSystemCode(String pSystemCode) {
		systemCode = pSystemCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTNER_ID",this.partnerId);
		hashMap.put("PARTNER_CODE",this.partnerCode);
		hashMap.put("PARTNER_TYPE",this.partnerType);
		hashMap.put("PARTNER_DESC",this.partnerDesc);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("PARTNER_LEVEL",this.partnerLevel);
		hashMap.put("PARTNER_NAME",this.partnerName);
		hashMap.put("PARTNER_PASSWORD",this.partnerPassword);
		hashMap.put("PARTNER_AREA_CODE",this.partnerAreaCode);
		hashMap.put("PARTNER_URL",this.partnerUrl);
		hashMap.put("PARTNER_IP",this.partnerIp);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("PARTNER_ENG_NAME",this.partnerEngName);
		hashMap.put("PARTNER_ENG_DESC",this.partnerEngDesc);
		hashMap.put("SETTLE_CYCLE",this.settleCycle);
		hashMap.put("SETTLE_PAY_METHOD",this.settlePayMethod);
		hashMap.put("SETTLE_RATE",this.settleRate);
		hashMap.put("CUST_SERVICE_PHONE",this.custServPhone);
		hashMap.put("IF_ROAM",this.ifRoam);
		hashMap.put("COMPANY_ADDRESS",this.companyAddr);
		hashMap.put("ARTIFICIAL_PERSON",this.artificialPerson);
		hashMap.put("PRIMARY_PERSON_NAME",this.primaryPersonName);
		hashMap.put("PRIMARY_PERSON_PHONE",this.primaryPersonPhone);
		hashMap.put("PRIMARY_PERSON_EMAIL",this.primaryPersonEmail);
		hashMap.put("BUSINESS_LICENSE",this.businessLicense);
		hashMap.put("CONTRACT_EXP_DATE",this.contractExpDate);
		hashMap.put("COMPANY_CODE",this.companyCode);
		hashMap.put("PARTNER_NUMBER",this.partnerNumber);
		hashMap.put("CUST_SERVICE_URL",this.custServUrl);
		hashMap.put("SYSTEM_CODE",this.systemCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partnerId = (String) hashMap.get("PARTNER_ID");
			this.partnerCode = (String) hashMap.get("PARTNER_CODE");
			this.partnerType = (String) hashMap.get("PARTNER_TYPE");
			this.partnerDesc = (String) hashMap.get("PARTNER_DESC");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.partnerLevel = (String) hashMap.get("PARTNER_LEVEL");
			this.partnerName = (String) hashMap.get("PARTNER_NAME");
			this.partnerPassword = (String) hashMap.get("PARTNER_PASSWORD");
			this.partnerAreaCode = (String) hashMap.get("PARTNER_AREA_CODE");
			this.partnerUrl = (String) hashMap.get("PARTNER_URL");
			this.partnerIp = (String) hashMap.get("PARTNER_IP");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.partnerEngName = (String) hashMap.get("PARTNER_ENG_NAME");
			this.partnerEngDesc = (String) hashMap.get("PARTNER_ENG_DESC");
			this.settleCycle = (String) hashMap.get("SETTLE_CYCLE");
			this.settlePayMethod = (String) hashMap.get("SETTLE_PAY_METHOD");
			this.settleRate = (String) hashMap.get("SETTLE_RATE");
			this.custServPhone = (String) hashMap.get("CUST_SERVICE_PHONE");
			this.ifRoam = (String) hashMap.get("IF_ROAM");
			this.companyAddr = (String) hashMap.get("COMPANY_ADDRESS");
			this.artificialPerson = (String) hashMap.get("ARTIFICIAL_PERSON");
			this.primaryPersonName = (String) hashMap.get("PRIMARY_PERSON_NAME");
			this.primaryPersonPhone = (String) hashMap.get("PRIMARY_PERSON_PHONE");
			this.primaryPersonEmail = (String) hashMap.get("PRIMARY_PERSON_EMAIL");
			this.businessLicense = (String) hashMap.get("BUSINESS_LICENSE");
			this.contractExpDate = (String) hashMap.get("CONTRACT_EXP_DATE");
			this.companyCode = (String) hashMap.get("COMPANY_CODE");
			this.partnerNumber = (String) hashMap.get("PARTNER_NUMBER");
			this.custServUrl = (String) hashMap.get("CUST_SERVICE_URL");
			this.systemCode = (String) hashMap.get("SYSTEM_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTNER_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER";
	}

}
