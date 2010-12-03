package com.ztesoft.vsop.web.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class ProdOffVO extends ValueObject implements VO {

	private String prodOffId = "";
	private String feeSetFlag = "";
	private String prodOffSubType = "";
	private String prodOffName = "";
	private String stateDate = "";
	private String prodOffPublisher = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	private String manageGrade = "";
	private String offNbr = "";
	private String offProviderId = "";
	private String brandId = "";
	private String servBrandId = "";
	private String templetId = "";
	private String defaultTimePeriod = "";
	private String offDesc = "";
	private String pricingDesc = "";
	private String pnameCn = "";
	private String pnameEn = "";
	private String pdesCn = "";
	private String pdesEn = "";
	private String chargingpolicyCn = "";
	private String chargingpolicyId = "";
	private String subOption = "";
	private String presentOption = "";
	private String corpOnly = "";
	private String scope = "";
	private String packageHost = "";
	private String offCode = "";

	public ProdOffVO() {}

	public ProdOffVO( String pprodOffId, String pfeeSetFlag, String pprodOffSubType, String pprodOffName, String pstateDate, String pprodOffPublisher, String pstate, String peffDate, String pexpDate, String pmanageGrade, String poffNbr, String poffProviderId, String pbrandId, String pservBrandId, String ptempletId, String pdefaultTimePeriod, String poffDesc, String ppricingDesc, String ppnameCn, String ppnameEn, String ppdesCn, String ppdesEn, String pchargingpolicyCn, String pchargingpolicyId, String psubOption, String ppresentOption, String pcorpOnly, String pscope, String ppackageHost, String poffCode ) {
		prodOffId = pprodOffId;
		feeSetFlag = pfeeSetFlag;
		prodOffSubType = pprodOffSubType;
		prodOffName = pprodOffName;
		stateDate = pstateDate;
		prodOffPublisher = pprodOffPublisher;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		manageGrade = pmanageGrade;
		offNbr = poffNbr;
		offProviderId = poffProviderId;
		brandId = pbrandId;
		servBrandId = pservBrandId;
		templetId = ptempletId;
		defaultTimePeriod = pdefaultTimePeriod;
		offDesc = poffDesc;
		pricingDesc = ppricingDesc;
		pnameCn = ppnameCn;
		pnameEn = ppnameEn;
		pdesCn = ppdesCn;
		pdesEn = ppdesEn;
		chargingpolicyCn = pchargingpolicyCn;
		chargingpolicyId = pchargingpolicyId;
		subOption = psubOption;
		presentOption = ppresentOption;
		corpOnly = pcorpOnly;
		scope = pscope;
		packageHost = ppackageHost;
		offCode = poffCode;
	}

	public String getProdOffId() {
		return prodOffId;
	}

	public String getFeeSetFlag() {
		return feeSetFlag;
	}

	public String getProdOffSubType() {
		return prodOffSubType;
	}

	public String getProdOffName() {
		return prodOffName;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getProdOffPublisher() {
		return prodOffPublisher;
	}

	public String getState() {
		return state;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getManageGrade() {
		return manageGrade;
	}

	public String getOffNbr() {
		return offNbr;
	}

	public String getOffProviderId() {
		return offProviderId;
	}

	public String getBrandId() {
		return brandId;
	}

	public String getServBrandId() {
		return servBrandId;
	}

	public String getTempletId() {
		return templetId;
	}

	public String getDefaultTimePeriod() {
		return defaultTimePeriod;
	}

	public String getOffDesc() {
		return offDesc;
	}

	public String getPricingDesc() {
		return pricingDesc;
	}

	public String getPnameCn() {
		return pnameCn;
	}

	public String getPnameEn() {
		return pnameEn;
	}

	public String getPdesCn() {
		return pdesCn;
	}

	public String getPdesEn() {
		return pdesEn;
	}

	public String getChargingpolicyCn() {
		return chargingpolicyCn;
	}

	public String getChargingpolicyId() {
		return chargingpolicyId;
	}

	public String getSubOption() {
		return subOption;
	}

	public String getPresentOption() {
		return presentOption;
	}

	public String getCorpOnly() {
		return corpOnly;
	}

	public String getScope() {
		return scope;
	}

	public String getPackageHost() {
		return packageHost;
	}

	public String getOffCode() {
		return offCode;
	}

	public void setProdOffId(String pProdOffId) {
		prodOffId = pProdOffId;
	}

	public void setFeeSetFlag(String pFeeSetFlag) {
		feeSetFlag = pFeeSetFlag;
	}

	public void setProdOffSubType(String pProdOffSubType) {
		prodOffSubType = pProdOffSubType;
	}

	public void setProdOffName(String pProdOffName) {
		prodOffName = pProdOffName;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setProdOffPublisher(String pProdOffPublisher) {
		prodOffPublisher = pProdOffPublisher;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setManageGrade(String pManageGrade) {
		manageGrade = pManageGrade;
	}

	public void setOffNbr(String pOffNbr) {
		offNbr = pOffNbr;
	}

	public void setOffProviderId(String pOffProviderId) {
		offProviderId = pOffProviderId;
	}

	public void setBrandId(String pBrandId) {
		brandId = pBrandId;
	}

	public void setServBrandId(String pServBrandId) {
		servBrandId = pServBrandId;
	}

	public void setTempletId(String pTempletId) {
		templetId = pTempletId;
	}

	public void setDefaultTimePeriod(String pDefaultTimePeriod) {
		defaultTimePeriod = pDefaultTimePeriod;
	}

	public void setOffDesc(String pOffDesc) {
		offDesc = pOffDesc;
	}

	public void setPricingDesc(String pPricingDesc) {
		pricingDesc = pPricingDesc;
	}

	public void setPnameCn(String pPnameCn) {
		pnameCn = pPnameCn;
	}

	public void setPnameEn(String pPnameEn) {
		pnameEn = pPnameEn;
	}

	public void setPdesCn(String pPdesCn) {
		pdesCn = pPdesCn;
	}

	public void setPdesEn(String pPdesEn) {
		pdesEn = pPdesEn;
	}

	public void setChargingpolicyCn(String pChargingpolicyCn) {
		chargingpolicyCn = pChargingpolicyCn;
	}

	public void setChargingpolicyId(String pChargingpolicyId) {
		chargingpolicyId = pChargingpolicyId;
	}

	public void setSubOption(String pSubOption) {
		subOption = pSubOption;
	}

	public void setPresentOption(String pPresentOption) {
		presentOption = pPresentOption;
	}

	public void setCorpOnly(String pCorpOnly) {
		corpOnly = pCorpOnly;
	}

	public void setScope(String pScope) {
		scope = pScope;
	}

	public void setPackageHost(String pPackageHost) {
		packageHost = pPackageHost;
	}

	public void setOffCode(String pOffCode) {
		offCode = pOffCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PROD_OFFER_ID",this.prodOffId);
		hashMap.put("FEE_SET_FLAG",this.feeSetFlag);
		hashMap.put("PROD_OFFER_SUB_TYPE",this.prodOffSubType);
		hashMap.put("PROD_OFFER_NAME",this.prodOffName);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("PROD_OFFER_PUBLISHER",this.prodOffPublisher);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("MANAGE_GRADE",this.manageGrade);
		hashMap.put("OFFER_NBR",this.offNbr);
		hashMap.put("OFFER_PROVIDER_ID",this.offProviderId);
		hashMap.put("BRAND_ID",this.brandId);
		hashMap.put("SERV_BRAND_ID",this.servBrandId);
		hashMap.put("TEMPLET_ID",this.templetId);
		hashMap.put("DEFAULT_TIME_PERIOD",this.defaultTimePeriod);
		hashMap.put("OFFER_DESC",this.offDesc);
		hashMap.put("PRICING_DESC",this.pricingDesc);
		hashMap.put("PNAME_CN",this.pnameCn);
		hashMap.put("PNAME_EN",this.pnameEn);
		hashMap.put("PDES_CN",this.pdesCn);
		hashMap.put("PDES_EN",this.pdesEn);
		hashMap.put("CHARGINGPOLICY_CN",this.chargingpolicyCn);
		hashMap.put("CHARGINGPOLICY_ID",this.chargingpolicyId);
		hashMap.put("SUB_OPTION",this.subOption);
		hashMap.put("PRESENT_OPTION",this.presentOption);
		hashMap.put("CORP_ONLY",this.corpOnly);
		hashMap.put("SCOPE",this.scope);
		hashMap.put("PACKAGE_HOST",this.packageHost);
		hashMap.put("OFFER_CODE",this.offCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.prodOffId = (String) hashMap.get("PROD_OFFER_ID");
			this.feeSetFlag = (String) hashMap.get("FEE_SET_FLAG");
			this.prodOffSubType = (String) hashMap.get("PROD_OFFER_SUB_TYPE");
			this.prodOffName = (String) hashMap.get("PROD_OFFER_NAME");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.prodOffPublisher = (String) hashMap.get("PROD_OFFER_PUBLISHER");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.manageGrade = (String) hashMap.get("MANAGE_GRADE");
			this.offNbr = (String) hashMap.get("OFFER_NBR");
			this.offProviderId = (String) hashMap.get("OFFER_PROVIDER_ID");
			this.brandId = (String) hashMap.get("BRAND_ID");
			this.servBrandId = (String) hashMap.get("SERV_BRAND_ID");
			this.templetId = (String) hashMap.get("TEMPLET_ID");
			this.defaultTimePeriod = (String) hashMap.get("DEFAULT_TIME_PERIOD");
			this.offDesc = (String) hashMap.get("OFFER_DESC");
			this.pricingDesc = (String) hashMap.get("PRICING_DESC");
			this.pnameCn = (String) hashMap.get("PNAME_CN");
			this.pnameEn = (String) hashMap.get("PNAME_EN");
			this.pdesCn = (String) hashMap.get("PDES_CN");
			this.pdesEn = (String) hashMap.get("PDES_EN");
			this.chargingpolicyCn = (String) hashMap.get("CHARGINGPOLICY_CN");
			this.chargingpolicyId = (String) hashMap.get("CHARGINGPOLICY_ID");
			this.subOption = (String) hashMap.get("SUB_OPTION");
			this.presentOption = (String) hashMap.get("PRESENT_OPTION");
			this.corpOnly = (String) hashMap.get("CORP_ONLY");
			this.scope = (String) hashMap.get("SCOPE");
			this.packageHost = (String) hashMap.get("PACKAGE_HOST");
			this.offCode = (String) hashMap.get("OFFER_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PROD_OFFER_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PROD_OFFER";
	}

}
