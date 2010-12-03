package com.ztesoft.vsop.engine.vo;

import java.util.*;
/**
 * ¶©µ¥Ïî
 * @author cooltan
 *
 */
public class OrderItemVO  {

	private String orderItemId ;
	private String custOrderId ;
	private String orderItemCd ;
	private String orderItemObjId ;
	private String custWorksheetId ;
	private String status ;
	private String statusDate ;
	private String stateChangeReason ;
	private String priority ;
	private String preHandleFlag ;
	private String handleTime ;
	private String archiveDate ;
	private String finishTime ;
	private String prodId ;
	private String accNbr ;
	private String packageId ;
	private String prodOffId ;
	private String effTime ;
	private String expTime ;
	private String resultType ;
	private String resultInfo ;
	private String lanId ;
	private String pprodOffId ;
	private String prodInstId ;
	private String servOffId ;
	private String partitionId ;
	private String dbActionType;
	public OrderItemVO() {}

	public OrderItemVO( String porderItemId, String pcustOrderId, String porderItemCd, String porderItemObjId, String pcustWorksheetId, String pstatus, String pstatusDate, String pstateChangeReason, String ppriority, String ppreHandleFlag, String phandleTime, String parchiveDate, String pfinishTime, String pprodId, String paccNbr, String ppackageId, String pprodOffId, String peffTime, String pexpTime, String presultType, String presultInfo, String planId, String ppprodOffId, String pprodInstId, String pservOffId, String ppartitionId ) {
		orderItemId = porderItemId;
		custOrderId = pcustOrderId;
		orderItemCd = porderItemCd;
		orderItemObjId = porderItemObjId;
		custWorksheetId = pcustWorksheetId;
		status = pstatus;
		statusDate = pstatusDate;
		stateChangeReason = pstateChangeReason;
		priority = ppriority;
		preHandleFlag = ppreHandleFlag;
		handleTime = phandleTime;
		archiveDate = parchiveDate;
		finishTime = pfinishTime;
		prodId = pprodId;
		accNbr = paccNbr;
		packageId = ppackageId;
		prodOffId = pprodOffId;
		effTime = peffTime;
		expTime = pexpTime;
		resultType = presultType;
		resultInfo = presultInfo;
		lanId = planId;
		pprodOffId = ppprodOffId;
		prodInstId = pprodInstId;
		servOffId = pservOffId;
		partitionId = ppartitionId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public String getCustOrderId() {
		return custOrderId;
	}

	public String getOrderItemCd() {
		return orderItemCd;
	}

	public String getOrderItemObjId() {
		return orderItemObjId;
	}

	public String getCustWorksheetId() {
		return custWorksheetId;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public String getStateChangeReason() {
		return stateChangeReason;
	}

	public String getPriority() {
		return priority;
	}

	public String getPreHandleFlag() {
		return preHandleFlag;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public String getArchiveDate() {
		return archiveDate;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public String getProdId() {
		return prodId;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public String getPackageId() {
		return packageId;
	}

	public String getProdOffId() {
		return prodOffId;
	}

	public String getEffTime() {
		return effTime;
	}

	public String getExpTime() {
		return expTime;
	}

	public String getResultType() {
		return resultType;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public String getLanId() {
		return lanId;
	}

	public String getPprodOffId() {
		return pprodOffId;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public String getServOffId() {
		return servOffId;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setOrderItemId(String pOrderItemId) {
		orderItemId = pOrderItemId;
	}

	public void setCustOrderId(String pCustOrderId) {
		custOrderId = pCustOrderId;
	}

	public void setOrderItemCd(String pOrderItemCd) {
		orderItemCd = pOrderItemCd;
	}

	public void setOrderItemObjId(String pOrderItemObjId) {
		orderItemObjId = pOrderItemObjId;
	}

	public void setCustWorksheetId(String pCustWorksheetId) {
		custWorksheetId = pCustWorksheetId;
	}

	public void setStatus(String pStatus) {
		status = pStatus;
	}

	public void setStatusDate(String pStatusDate) {
		statusDate = pStatusDate;
	}

	public void setStateChangeReason(String pStateChangeReason) {
		stateChangeReason = pStateChangeReason;
	}

	public void setPriority(String pPriority) {
		priority = pPriority;
	}

	public void setPreHandleFlag(String pPreHandleFlag) {
		preHandleFlag = pPreHandleFlag;
	}

	public void setHandleTime(String pHandleTime) {
		handleTime = pHandleTime;
	}

	public void setArchiveDate(String pArchiveDate) {
		archiveDate = pArchiveDate;
	}

	public void setFinishTime(String pFinishTime) {
		finishTime = pFinishTime;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setAccNbr(String pAccNbr) {
		accNbr = pAccNbr;
	}

	public void setPackageId(String pPackageId) {
		packageId = pPackageId;
	}

	public void setProdOffId(String pProdOffId) {
		prodOffId = pProdOffId;
	}

	public void setEffTime(String pEffTime) {
		effTime = pEffTime;
	}

	public void setExpTime(String pExpTime) {
		expTime = pExpTime;
	}

	public void setResultType(String pResultType) {
		resultType = pResultType;
	}

	public void setResultInfo(String pResultInfo) {
		resultInfo = pResultInfo;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setPprodOffId(String pPprodOffId) {
		pprodOffId = pPprodOffId;
	}

	public void setProdInstId(String pProdInstId) {
		prodInstId = pProdInstId;
	}

	public void setServOffId(String pServOffId) {
		servOffId = pServOffId;
	}

	public void setPartitionId(String pPartitionId) {
		partitionId = pPartitionId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORDER_ITEM_ID",this.orderItemId);
		hashMap.put("CUST_ORDER_ID",this.custOrderId);
		hashMap.put("ORDER_ITEM_CD",this.orderItemCd);
		hashMap.put("ORDER_ITEM_OBJ_ID",this.orderItemObjId);
		hashMap.put("CUST_WORKSHEET_ID",this.custWorksheetId);
		hashMap.put("STATUS",this.status);
		hashMap.put("STATUS_DATE",this.statusDate);
		hashMap.put("STATE_CHANGE_REASON",this.stateChangeReason);
		hashMap.put("PRIORITY",this.priority);
		hashMap.put("PRE_HANDLE_FLAG",this.preHandleFlag);
		hashMap.put("HANDLE_TIME",this.handleTime);
		hashMap.put("ARCHIVE_DATE",this.archiveDate);
		hashMap.put("FINISH_TIME",this.finishTime);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("ACC_NBR",this.accNbr);
		hashMap.put("PACKAGE_ID",this.packageId);
		hashMap.put("PRODUCT_OFFER_ID",this.prodOffId);
		hashMap.put("EFF_TIME",this.effTime);
		hashMap.put("EXP_TIME",this.expTime);
		hashMap.put("RESULT_TYPE",this.resultType);
		hashMap.put("RESULT_INFO",this.resultInfo);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PPROD_OFFER_ID",this.pprodOffId);
		hashMap.put("PROD_INST_ID",this.prodInstId);
		hashMap.put("SERVICE_OFFER_ID",this.servOffId);
		hashMap.put("PARTITION_ID",this.partitionId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orderItemId = (String) hashMap.get("ORDER_ITEM_ID");
			this.custOrderId = (String) hashMap.get("CUST_ORDER_ID");
			this.orderItemCd = (String) hashMap.get("ORDER_ITEM_CD");
			this.orderItemObjId = (String) hashMap.get("ORDER_ITEM_OBJ_ID");
			this.custWorksheetId = (String) hashMap.get("CUST_WORKSHEET_ID");
			this.status = (String) hashMap.get("STATUS");
			this.statusDate = (String) hashMap.get("STATUS_DATE");
			this.stateChangeReason = (String) hashMap.get("STATE_CHANGE_REASON");
			this.priority = (String) hashMap.get("PRIORITY");
			this.preHandleFlag = (String) hashMap.get("PRE_HANDLE_FLAG");
			this.handleTime = (String) hashMap.get("HANDLE_TIME");
			this.archiveDate = (String) hashMap.get("ARCHIVE_DATE");
			this.finishTime = (String) hashMap.get("FINISH_TIME");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.accNbr = (String) hashMap.get("ACC_NBR");
			this.packageId = (String) hashMap.get("PACKAGE_ID");
			this.prodOffId = (String) hashMap.get("PRODUCT_OFFER_ID");
			this.effTime = (String) hashMap.get("EFF_TIME");
			this.expTime = (String) hashMap.get("EXP_TIME");
			this.resultType = (String) hashMap.get("RESULT_TYPE");
			this.resultInfo = (String) hashMap.get("RESULT_INFO");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.pprodOffId = (String) hashMap.get("PPROD_OFFER_ID");
			this.prodInstId = (String) hashMap.get("PROD_INST_ID");
			this.servOffId = (String) hashMap.get("SERVICE_OFFER_ID");
			this.partitionId = (String) hashMap.get("PARTITION_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORDER_ITEM_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ORDER_ITEM";
	}

	public String getDbActionType() {
		return dbActionType;
	}

	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}

}
