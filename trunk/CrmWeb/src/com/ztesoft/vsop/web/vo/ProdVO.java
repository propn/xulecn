package com.ztesoft.vsop.web.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.VproductInfo;

public class ProdVO extends ValueObject implements VO {

	private String prodId = "";
	private String prodNbr = "";
	private String prodName = "";
	private String prodDesc = "";
	private String manageGrade = "";
	private String prodBundleType = "";
	private String prodProviderId = "";
	private String prodStateCd = "";
	private String stateDate = "";
	private String effDate = "";
	private String expDate = "";
	private String createDate = "";
	private String prodFuncType = "";
	private String orderHost = "";
	private String chargingPolicyCn = "";
	private String chargingPolicyId = "";
	private String subOption = "";
	private String present = "";
	private String corpOnly = "";
	private String packageOnly = "";
	private String settlementCycle = "";
	private String settlementPaytype = "";
	private String settlementPercent = "";
	private String scope = "";
	private String systemCode = "";
	private String prodOffId = "";
	private String prodType = "";
	private String prodCode = "";
	private String prodClass = "";

	private List prodPlatforms;// 产品接入平台列表
	private List prodServiceAbilitys;// 产品关联业务能力列表
	private PartnerVO partnerVO; // 产品对应提供商（SPCP)

	// private List prodRelations;//产品关系 A端＋Z端

	public ProdVO() {
	}

	public ProdVO(String pprodId, String pprodNbr, String pprodName,
			String pprodDesc, String pmanageGrade, String pprodBundleType,
			String pprodProviderId, String pprodStateCd, String pstateDate,
			String peffDate, String pexpDate, String pcreateDate,
			String pprodFuncType, String porderHost, String pchargingPolicyCn,
			String pchargingPolicyId, String psubOption, String ppresent,
			String pcorpOnly, String ppackageOnly, String psettlementCycle,
			String psettlementPaytype, String psettlementPercent,
			String pscope, String psystemCode, String pprodOffId,
			String pprodType, String pprodCode, String pprodClass) {
		prodId = pprodId;
		prodNbr = pprodNbr;
		prodName = pprodName;
		prodDesc = pprodDesc;
		manageGrade = pmanageGrade;
		prodBundleType = pprodBundleType;
		prodProviderId = pprodProviderId;
		prodStateCd = pprodStateCd;
		stateDate = pstateDate;
		effDate = peffDate;
		expDate = pexpDate;
		createDate = pcreateDate;
		prodFuncType = pprodFuncType;
		orderHost = porderHost;
		chargingPolicyCn = pchargingPolicyCn;
		chargingPolicyId = pchargingPolicyId;
		subOption = psubOption;
		present = ppresent;
		corpOnly = pcorpOnly;
		packageOnly = ppackageOnly;
		settlementCycle = psettlementCycle;
		settlementPaytype = psettlementPaytype;
		settlementPercent = psettlementPercent;
		scope = pscope;
		systemCode = psystemCode;
		prodOffId = pprodOffId;
		prodType = pprodType;
		prodCode = pprodCode;
		prodClass = pprodClass;
	}

	public String getProdId() {
		return prodId;
	}

	public String getProdNbr() {
		return prodNbr;
	}

	public String getProdName() {
		return prodName;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public String getManageGrade() {
		return manageGrade;
	}

	public String getProdBundleType() {
		return prodBundleType;
	}

	public String getProdProviderId() {
		return prodProviderId;
	}

	public String getProdStateCd() {
		return prodStateCd;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getProdFuncType() {
		return prodFuncType;
	}

	public String getOrderHost() {
		return orderHost;
	}

	public String getChargingPolicyCn() {
		return chargingPolicyCn;
	}

	public String getChargingPolicyId() {
		return chargingPolicyId;
	}

	public String getSubOption() {
		return subOption;
	}

	public String getPresent() {
		return present;
	}

	public String getCorpOnly() {
		return corpOnly;
	}

	public String getPackageOnly() {
		return packageOnly;
	}

	public String getSettlementCycle() {
		return settlementCycle;
	}

	public String getSettlementPaytype() {
		return settlementPaytype;
	}

	public String getSettlementPercent() {
		return settlementPercent;
	}

	public String getScope() {
		return scope;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public String getProdOffId() {
		return prodOffId;
	}

	public String getProdType() {
		return prodType;
	}

	public String getProdCode() {
		return prodCode;
	}

	public String getProdClass() {
		return prodClass;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setProdNbr(String pProdNbr) {
		prodNbr = pProdNbr;
	}

	public void setProdName(String pProdName) {
		prodName = pProdName;
	}

	public void setProdDesc(String pProdDesc) {
		prodDesc = pProdDesc;
	}

	public void setManageGrade(String pManageGrade) {
		manageGrade = pManageGrade;
	}

	public void setProdBundleType(String pProdBundleType) {
		prodBundleType = pProdBundleType;
	}

	public void setProdProviderId(String pProdProviderId) {
		prodProviderId = pProdProviderId;
	}

	public void setProdStateCd(String pProdStateCd) {
		prodStateCd = pProdStateCd;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setProdFuncType(String pProdFuncType) {
		prodFuncType = pProdFuncType;
	}

	public void setOrderHost(String pOrderHost) {
		orderHost = pOrderHost;
	}

	public void setChargingPolicyCn(String pChargingPolicyCn) {
		chargingPolicyCn = pChargingPolicyCn;
	}

	public void setChargingPolicyId(String pChargingPolicyId) {
		chargingPolicyId = pChargingPolicyId;
	}

	public void setSubOption(String pSubOption) {
		subOption = pSubOption;
	}

	public void setPresent(String pPresent) {
		present = pPresent;
	}

	public void setCorpOnly(String pCorpOnly) {
		corpOnly = pCorpOnly;
	}

	public void setPackageOnly(String pPackageOnly) {
		packageOnly = pPackageOnly;
	}

	public void setSettlementCycle(String pSettlementCycle) {
		settlementCycle = pSettlementCycle;
	}

	public void setSettlementPaytype(String pSettlementPaytype) {
		settlementPaytype = pSettlementPaytype;
	}

	public void setSettlementPercent(String pSettlementPercent) {
		settlementPercent = pSettlementPercent;
	}

	public void setScope(String pScope) {
		scope = pScope;
	}

	public void setSystemCode(String pSystemCode) {
		systemCode = pSystemCode;
	}

	public void setProdOffId(String pProdOffId) {
		prodOffId = pProdOffId;
	}

	public void setProdType(String pProdType) {
		prodType = pProdType;
	}

	public void setProdCode(String pProdCode) {
		prodCode = pProdCode;
	}

	public void setProdClass(String pProdClass) {
		prodClass = pProdClass;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRODUCT_ID", this.prodId);
		hashMap.put("PRODUCT_NBR", this.prodNbr);
		hashMap.put("PRODUCT_NAME", this.prodName);
		hashMap.put("PRODUCT_DESC", this.prodDesc);
		hashMap.put("MANAGE_GRADE", this.manageGrade);
		hashMap.put("PROD_BUNDLE_TYPE", this.prodBundleType);
		hashMap.put("PRODUCT_PROVIDER_ID", this.prodProviderId);
		hashMap.put("PRODUCT_STATE_CD", this.prodStateCd);
		hashMap.put("STATE_DATE", this.stateDate);
		hashMap.put("EFF_DATE", this.effDate);
		hashMap.put("EXP_DATE", this.expDate);
		hashMap.put("CREATE_DATE", this.createDate);
		hashMap.put("PROD_FUNC_TYPE", this.prodFuncType);
		hashMap.put("ORDER_HOST", this.orderHost);
		hashMap.put("CHARGING_POLICY_CN", this.chargingPolicyCn);
		hashMap.put("CHARGING_POLICY_ID", this.chargingPolicyId);
		hashMap.put("SUB_OPTION", this.subOption);
		hashMap.put("PRESENT", this.present);
		hashMap.put("CORP_ONLY", this.corpOnly);
		hashMap.put("PACKAGE_ONLY", this.packageOnly);
		hashMap.put("SETTLEMENT_CYCLE", this.settlementCycle);
		hashMap.put("SETTLEMENT_PAYTYPE", this.settlementPaytype);
		hashMap.put("SETTLEMENT_PERCENT", this.settlementPercent);
		hashMap.put("SCOPE", this.scope);
		hashMap.put("SYSTEM_CODE", this.systemCode);
		hashMap.put("PROD_OFFER_ID", this.prodOffId);
		hashMap.put("PROD_TYPE", this.prodType);
		hashMap.put("PRODUCT_CODE", this.prodCode);
		hashMap.put("PROD_CLASS", this.prodClass);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.prodNbr = (String) hashMap.get("PRODUCT_NBR");
			this.prodName = (String) hashMap.get("PRODUCT_NAME");
			this.prodDesc = (String) hashMap.get("PRODUCT_DESC");
			this.manageGrade = (String) hashMap.get("MANAGE_GRADE");
			this.prodBundleType = (String) hashMap.get("PROD_BUNDLE_TYPE");
			this.prodProviderId = (String) hashMap.get("PRODUCT_PROVIDER_ID");
			this.prodStateCd = (String) hashMap.get("PRODUCT_STATE_CD");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.prodFuncType = (String) hashMap.get("PROD_FUNC_TYPE");
			this.orderHost = (String) hashMap.get("ORDER_HOST");
			this.chargingPolicyCn = (String) hashMap.get("CHARGING_POLICY_CN");
			this.chargingPolicyId = (String) hashMap.get("CHARGING_POLICY_ID");
			this.subOption = (String) hashMap.get("SUB_OPTION");
			this.present = (String) hashMap.get("PRESENT");
			this.corpOnly = (String) hashMap.get("CORP_ONLY");
			this.packageOnly = (String) hashMap.get("PACKAGE_ONLY");
			this.settlementCycle = (String) hashMap.get("SETTLEMENT_CYCLE");
			this.settlementPaytype = (String) hashMap.get("SETTLEMENT_PAYTYPE");
			this.settlementPercent = (String) hashMap.get("SETTLEMENT_PERCENT");
			this.scope = (String) hashMap.get("SCOPE");
			this.systemCode = (String) hashMap.get("SYSTEM_CODE");
			this.prodOffId = (String) hashMap.get("PROD_OFFER_ID");
			this.prodType = (String) hashMap.get("PROD_TYPE");
			this.prodCode = (String) hashMap.get("PRODUCT_CODE");
			this.prodClass = (String) hashMap.get("PROD_CLASS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRODUCT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT";
	}

	public List getProdPlatforms() {
		return prodPlatforms;
	}

	public void setProdPlatforms(List prodPlatforms) {
		this.prodPlatforms = prodPlatforms;
	}

	public List getProdServiceAbilitys() {
		return prodServiceAbilitys;
	}

	public void setProdServiceAbilitys(List prodServiceAbilitys) {
		this.prodServiceAbilitys = prodServiceAbilitys;
	}

	public PartnerVO getPartnerVO() {
		return partnerVO;
	}

	public void setPartnerVO(PartnerVO partnerVO) {
		this.partnerVO = partnerVO;
	}

	/**
	 * 把销售品下的增值产品转换成订单增值产品
	 * @param prodOffVO 销售品
	 * @param orderActionType 订单动作类型
	 * @param orderState 订单状态
	 * @param productInstID 订单增值产品里的订购关系实例标识
	 * @return
	 */
	public VproductInfo toVproductInfo(ProdOffVO prodOffVO,String orderActionType, String orderState, String productInstID) {
		VproductInfo vpi = new VproductInfo();
		vpi.setOfferId(prodOffVO.getProdOffId());
		vpi.setOfferNbr(prodOffVO.getOffNbr());
		vpi.setOfferType(prodOffVO.getProdOffSubType());
		vpi.setActionType(orderActionType);
		vpi.setState(orderState);
		vpi.setEffDate(this.getEffDate());
		vpi.setExpDate(this.getExpDate());
		vpi.setVProductNbr(this.getProdNbr());
		vpi.setVProductId(this.getProdId());
		vpi.setVProductInstID(productInstID);
		return vpi;
	}

	/*
	 * public List getProdRelations() { return prodRelations; }
	 * 
	 * public void setProdRelations(List prodRelations) { this.prodRelations =
	 * prodRelations; }
	 */

}
