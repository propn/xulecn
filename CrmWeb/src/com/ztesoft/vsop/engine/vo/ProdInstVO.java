package com.ztesoft.vsop.engine.vo;


import java.util.*;
/**
 * 产品实例
 * @author cooltan
 *
 */
public class ProdInstVO {
	public static final String StateOfEffective = "00A"; //有效
	public static final String StateOfDowntime = "00B"; //停机
	public static final String StateOfArrearsDown = "00C"; //欠费停机
	public static final String StateOfDisassemble = "00X"; //拆机
	private String prodInstId ;
	private String prodId ;
	private String commonRegionId ;
	private String prodTypeCd ;
	private String paymentModeCd ;
	private String createDate ;
	private String beginRentTime ;
	private String stopRentTime ;
	private String finishTime ;
	private String prodPassword ;
	private String stateCd ;
	private String lanId ;
	private String accNbr ;
	private String stateDate ;
	private String dbActionType;
	
	private String userName;//用户名称
	private String uimNO;//UIM卡号码
	private String oldUimNO;//用户旧UIM卡号码
	
	public ProdInstVO() {}

	public ProdInstVO( String pprodInstId, String pprodId, String pcommonRegionId, String pprodTypeCd, String ppaymentModeCd, String pcreateDate, String pbeginRentTime, String pstopRentTime, String pfinishTime, String pprodPassword, String pstateCd, String planId, String paccNbr, String pstateDate ) {
		prodInstId = pprodInstId;
		prodId = pprodId;
		commonRegionId = pcommonRegionId;
		prodTypeCd = pprodTypeCd;
		paymentModeCd = ppaymentModeCd;
		createDate = pcreateDate;
		beginRentTime = pbeginRentTime;
		stopRentTime = pstopRentTime;
		finishTime = pfinishTime;
		prodPassword = pprodPassword;
		stateCd = pstateCd;
		lanId = planId;
		accNbr = paccNbr;
		stateDate = pstateDate;
	}
	//增加用户名称 UIM oldUim xulei 20100930
	public ProdInstVO( String pprodInstId, String pprodId, String pcommonRegionId, String pprodTypeCd, String ppaymentModeCd, String pcreateDate, String pbeginRentTime, String pstopRentTime, String pfinishTime, String pprodPassword, String pstateCd, String planId, String paccNbr, String pstateDate, String puserName, String puimNO, String poldUimNO ) {
		prodInstId = pprodInstId;
		prodId = pprodId;
		commonRegionId = pcommonRegionId;
		prodTypeCd = pprodTypeCd;
		paymentModeCd = ppaymentModeCd;
		createDate = pcreateDate;
		beginRentTime = pbeginRentTime;
		stopRentTime = pstopRentTime;
		finishTime = pfinishTime;
		prodPassword = pprodPassword;
		stateCd = pstateCd;
		lanId = planId;
		accNbr = paccNbr;
		stateDate = pstateDate;
		userName = puserName;
		uimNO = puimNO;
		oldUimNO = poldUimNO;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getCommonRegionId() {
		return commonRegionId;
	}

	public String getProdTypeCd() {
		return prodTypeCd;
	}

	public String getPaymentModeCd() {
		return paymentModeCd;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getBeginRentTime() {
		return beginRentTime;
	}

	public String getStopRentTime() {
		return stopRentTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public String getProdPassword() {
		return prodPassword;
	}

	public String getStateCd() {
		return stateCd;
	}

	public String getLanId() {
		return lanId;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setProdInstId(String pProdInstId) {
		prodInstId = pProdInstId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setCommonRegionId(String pCommonRegionId) {
		commonRegionId = pCommonRegionId;
	}

	public void setProdTypeCd(String pProdTypeCd) {
		prodTypeCd = pProdTypeCd;
	}

	public void setPaymentModeCd(String pPaymentModeCd) {
		paymentModeCd = pPaymentModeCd;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setBeginRentTime(String pBeginRentTime) {
		beginRentTime = pBeginRentTime;
	}

	public void setStopRentTime(String pStopRentTime) {
		stopRentTime = pStopRentTime;
	}

	public void setFinishTime(String pFinishTime) {
		finishTime = pFinishTime;
	}

	public void setProdPassword(String pProdPassword) {
		prodPassword = pProdPassword;
	}

	public void setStateCd(String pStateCd) {
		stateCd = pStateCd;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setAccNbr(String pAccNbr) {
		accNbr = pAccNbr;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PROD_INST_ID",this.prodInstId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("COMMON_REGION_ID",this.commonRegionId);
		hashMap.put("PROD_TYPE_CD",this.prodTypeCd);
		hashMap.put("PAYMENT_MODE_CD",this.paymentModeCd);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("BEGIN_RENT_TIME",this.beginRentTime);
		hashMap.put("STOP_RENT_TIME",this.stopRentTime);
		hashMap.put("FINISH_TIME",this.finishTime);
		hashMap.put("PRODUCT_PASSWORD",this.prodPassword);
		hashMap.put("STATE_CD",this.stateCd);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("ACC_NBR",this.accNbr);
		hashMap.put("STATE_DATE",this.stateDate);
		
		hashMap.put("USER_NAME",this.userName);
		hashMap.put("UIM_NO",this.uimNO);
		hashMap.put("OLD_UIM_NO",this.oldUimNO);
		
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.prodInstId = (String) hashMap.get("PROD_INST_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.commonRegionId = (String) hashMap.get("COMMON_REGION_ID");
			this.prodTypeCd = (String) hashMap.get("PROD_TYPE_CD");
			this.paymentModeCd = (String) hashMap.get("PAYMENT_MODE_CD");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.beginRentTime = (String) hashMap.get("BEGIN_RENT_TIME");
			this.stopRentTime = (String) hashMap.get("STOP_RENT_TIME");
			this.finishTime = (String) hashMap.get("FINISH_TIME");
			this.prodPassword = (String) hashMap.get("PRODUCT_PASSWORD");
			this.stateCd = (String) hashMap.get("STATE_CD");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.accNbr = (String) hashMap.get("ACC_NBR");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			
			this.userName = (String) hashMap.get("USER_NAME");
			this.uimNO = (String) hashMap.get("UIM_NO");
			this.oldUimNO = (String) hashMap.get("OLD_UIM_NO");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PROD_INST_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PROD_INST";
	}

	public String getDbActionType() {
		return dbActionType;
	}

	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUimNO() {
		return uimNO;
	}

	public void setUimNO(String uimNO) {
		this.uimNO = uimNO;
	}

	public String getOldUimNO() {
		return oldUimNO;
	}

	public void setOldUimNO(String oldUimNO) {
		this.oldUimNO = oldUimNO;
	}

}
