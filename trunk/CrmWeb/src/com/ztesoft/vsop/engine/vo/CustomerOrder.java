package com.ztesoft.vsop.engine.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 订单信息
 * @author cooltan
 *
 */
public class CustomerOrder {
	private static Logger logger = Logger.getLogger(CustomerOrder.class);
	private String custOrderId ; //订单标识
	private String otherSysOrderId ;//外部订单标识
	private String custSoNumber ;//流水号
	private String custOrderType ;//订单类型 对应接口的ActionType
	private String timeNameId ;
	private String custId ;//客户标识
	private String staffId ; //受理员工标识
	private String chnId ;
	private String status ;//订单状态
	private String statusDate ;
	private String preHandleFlag ;
	private String handlePeopleName ;
	private String contactPhone ;
	private String contactPeople ;
	private String priority ;
	private String reason ;
	private String orderChn ;//订购渠道 与订购系统一直
	private String orderSystem ;//订购系统 
	private String receiveDate ;//订单接收时间
	private String disposalResult ;//订单处理结果
	private String disposalResultDesc ;//订单处理结果描述
	private String accNbr ; //产品号码
	private String prodId ;//产品编码(产品类型）
	private String lanId ;//本地网区号
	private String prodInstId ;//产品实例标识
	private String partitionId ;
	
	private String userName;//用户名称
	private String uimNO;//UIM卡号码
	private String oldUimNO;//用户旧UIM卡号码
	
	private String oldAccNbr;//原产品号码，改号用
	private ProdInstVO prodInst;//产品实例VO
	//List< ProductOfferInfo>;订单销售品列表
	private List productOfferInfoList;
	//List< AproductInfo>; 订单业务能力附属产品列表
	private List aproductInfoList;
	private String dbActionType;
	private boolean successAll=true;//表示此订单是否操作是否全部成功 GX用 yuanyang
	private List successAllReason;//<resultInfo>表示此订单是否操作是否全部成功原因 GX用 yuanyang
	private boolean breakScream=false;//表示需要跳过此订单的服务扭转操作 GX用 yuanyang
	private boolean isExistProdInst=true;//标志用户信息是否已在VSOP, 订购关系同步过来时，当IOM未同步用户信息到VSOP时，先把同步过来的订购关系写到中间表 GX用 yuanyang
	private boolean sendActiveFlag=true;//是否要送平台激活标志，默认送，ismp反向同步时不送。--add 2010-9-21 by wendm

	public CustomerOrder() {}

	public CustomerOrder( String pcustOrderId, String potherSysOrderId, String pcustSoNumber, String pcustOrderType, String ptimeNameId, String pcustId, String pstaffId, String pchnId, String pstatus, String pstatusDate, String ppreHandleFlag, String phandlePeopleName, String pcontactPhone, String pcontactPeople, String ppriority, String preason, String porderChn, String porderSystem, String preceiveDate, String pdisposalResult, String pdisposalResultDesc, String paccNbr, String pprodId, String planId, String pprodInstId, String ppartitionId ) {
		custOrderId = pcustOrderId;
		otherSysOrderId = potherSysOrderId;
		custSoNumber = pcustSoNumber;
		custOrderType = pcustOrderType;
		timeNameId = ptimeNameId;
		custId = pcustId;
		staffId = pstaffId;
		chnId = pchnId;
		status = pstatus;
		statusDate = pstatusDate;
		preHandleFlag = ppreHandleFlag;
		handlePeopleName = phandlePeopleName;
		contactPhone = pcontactPhone;
		contactPeople = pcontactPeople;
		priority = ppriority;
		reason = preason;
		orderChn = porderChn;
		orderSystem = porderSystem;
		receiveDate = preceiveDate;
		disposalResult = pdisposalResult;
		disposalResultDesc = pdisposalResultDesc;
		accNbr = paccNbr;
		prodId = pprodId;
		lanId = planId;
		prodInstId = pprodInstId;
		partitionId = ppartitionId;
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
	
	public String getCustOrderId() {
		return custOrderId;
	}

	public String getOtherSysOrderId() {
		return otherSysOrderId;
	}

	public String getCustSoNumber() {
		return custSoNumber;
	}

	public String getCustOrderType() {
		return custOrderType;
	}

	public String getTimeNameId() {
		return timeNameId;
	}

	public String getCustId() {
		return custId;
	}

	public String getStaffId() {
		return staffId;
	}

	public String getChnId() {
		return chnId;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public String getPreHandleFlag() {
		return preHandleFlag;
	}

	public String getHandlePeopleName() {
		return handlePeopleName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getContactPeople() {
		return contactPeople;
	}

	public String getPriority() {
		return priority;
	}

	public String getReason() {
		return reason;
	}

	public String getOrderChn() {
		return orderChn;
	}

	public String getOrderSystem() {
		return orderSystem;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public String getDisposalResult() {
		return disposalResult;
	}

	public String getDisposalResultDesc() {
		return disposalResultDesc;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public String getProdId() {
		return prodId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public String getPartitionId() {
		return partitionId;
	}

	public void setCustOrderId(String pCustOrderId) {
		custOrderId = pCustOrderId;
	}

	public void setOtherSysOrderId(String pOtherSysOrderId) {
		otherSysOrderId = pOtherSysOrderId;
	}

	public void setCustSoNumber(String pCustSoNumber) {
		custSoNumber = pCustSoNumber;
	}

	public void setCustOrderType(String pCustOrderType) {
		custOrderType = pCustOrderType;
	}

	public void setTimeNameId(String pTimeNameId) {
		timeNameId = pTimeNameId;
	}

	public void setCustId(String pCustId) {
		custId = pCustId;
	}

	public void setStaffId(String pStaffId) {
		staffId = pStaffId;
	}

	public void setChnId(String pChnId) {
		chnId = pChnId;
	}

	public void setStatus(String pStatus) {
		status = pStatus;
	}

	public void setStatusDate(String pStatusDate) {
		statusDate = pStatusDate;
	}

	public void setPreHandleFlag(String pPreHandleFlag) {
		preHandleFlag = pPreHandleFlag;
	}

	public void setHandlePeopleName(String pHandlePeopleName) {
		handlePeopleName = pHandlePeopleName;
	}

	public void setContactPhone(String pContactPhone) {
		contactPhone = pContactPhone;
	}

	public void setContactPeople(String pContactPeople) {
		contactPeople = pContactPeople;
	}

	public void setPriority(String pPriority) {
		priority = pPriority;
	}

	public void setReason(String pReason) {
		reason = pReason;
	}

	public void setOrderChn(String pOrderChn) {
		orderChn = pOrderChn;
	}

	public void setOrderSystem(String pOrderSystem) {
		orderSystem = pOrderSystem;
	}

	public void setReceiveDate(String pReceiveDate) {
		receiveDate = pReceiveDate;
	}

	public void setDisposalResult(String pDisposalResult) {
		disposalResult = pDisposalResult;
	}

	public void setDisposalResultDesc(String pDisposalResultDesc) {
		disposalResultDesc = pDisposalResultDesc;
	}

	public void setAccNbr(String pAccNbr) {
		accNbr = pAccNbr;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setProdInstId(String pProdInstId) {
		prodInstId = pProdInstId;
	}

	public void setPartitionId(String pPartitionId) {
		partitionId = pPartitionId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CUST_ORDER_ID",this.custOrderId);
		hashMap.put("OTHER_SYS_ORDER_ID",this.otherSysOrderId);
		hashMap.put("CUST_SO_NUMBER",this.custSoNumber);
		hashMap.put("CUST_ORDER_TYPE",this.custOrderType);
		hashMap.put("TIME_NAME_ID",this.timeNameId);
		hashMap.put("CUST_ID",this.custId);
		hashMap.put("STAFF_ID",this.staffId);
		hashMap.put("CHANNEL_ID",this.chnId);
		hashMap.put("STATUS",this.status);
		hashMap.put("STATUS_DATE",this.statusDate);
		hashMap.put("PRE_HANDLE_FLAG",this.preHandleFlag);
		hashMap.put("HANDLE_PEOPLE_NAME",this.handlePeopleName);
		hashMap.put("CONTACT_PHONE",this.contactPhone);
		hashMap.put("CONTACT_PEOPLE",this.contactPeople);
		hashMap.put("PRIORITY",this.priority);
		hashMap.put("REASON",this.reason);
		hashMap.put("ORDER_CHANNEL",this.orderChn);
		hashMap.put("ORDER_SYSTEM",this.orderSystem);
		hashMap.put("RECEIVE_DATE",this.receiveDate);
		hashMap.put("DISPOSAL_RESULT",this.disposalResult);
		hashMap.put("DISPOSAL_RESULT_DESC",this.disposalResultDesc);
		hashMap.put("ACC_NBR",this.accNbr);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PROD_INST_ID",this.prodInstId);
		hashMap.put("PARTITION_ID",this.partitionId);
		//增加姓名 UID oldUIM
		hashMap.put("USER_NAME",this.userName);
		hashMap.put("UIM_NO",this.uimNO);
		hashMap.put("OLD_UIM_NO",this.oldUimNO);
		
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.custOrderId = (String) hashMap.get("CUST_ORDER_ID");
			this.otherSysOrderId = (String) hashMap.get("OTHER_SYS_ORDER_ID");
			this.custSoNumber = (String) hashMap.get("CUST_SO_NUMBER");
			this.custOrderType = (String) hashMap.get("CUST_ORDER_TYPE");
			this.timeNameId = (String) hashMap.get("TIME_NAME_ID");
			this.custId = (String) hashMap.get("CUST_ID");
			this.staffId = (String) hashMap.get("STAFF_ID");
			this.chnId = (String) hashMap.get("CHANNEL_ID");
			this.status = (String) hashMap.get("STATUS");
			this.statusDate = (String) hashMap.get("STATUS_DATE");
			this.preHandleFlag = (String) hashMap.get("PRE_HANDLE_FLAG");
			this.handlePeopleName = (String) hashMap.get("HANDLE_PEOPLE_NAME");
			this.contactPhone = (String) hashMap.get("CONTACT_PHONE");
			this.contactPeople = (String) hashMap.get("CONTACT_PEOPLE");
			this.priority = (String) hashMap.get("PRIORITY");
			this.reason = (String) hashMap.get("REASON");
			this.orderChn = (String) hashMap.get("ORDER_CHANNEL");
			this.orderSystem = (String) hashMap.get("ORDER_SYSTEM");
			this.receiveDate = (String) hashMap.get("RECEIVE_DATE");
			this.disposalResult = (String) hashMap.get("DISPOSAL_RESULT");
			this.disposalResultDesc = (String) hashMap.get("DISPOSAL_RESULT_DESC");
			this.accNbr = (String) hashMap.get("ACC_NBR");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.prodInstId = (String) hashMap.get("PROD_INST_ID");
			this.partitionId = (String) hashMap.get("PARTITION_ID");
			//增加姓名 UID oldUIM
			this.userName = (String) hashMap.get("USER_NAME");
			this.uimNO = (String) hashMap.get("UIM_NO");
			this.oldUimNO = (String) hashMap.get("OLD_UIM_NO");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CUST_ORDER_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CUSTOMER_ORDER";
	}

	public List getProductOfferInfoList() {
		return productOfferInfoList;
	}

	public void setProductOfferInfoList(List productOfferInfoList) {
		this.productOfferInfoList = productOfferInfoList;
	}

	public List getAproductInfoList() {
		return aproductInfoList;
	}

	public void setAproductInfoList(List aproductInfoList) {
		this.aproductInfoList = aproductInfoList;
	}

	public String getDbActionType() {
		return dbActionType;
	}

	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}

	public String getOldAccNbr() {
		return oldAccNbr;
	}

	public void setOldAccNbr(String oldAccNbr) {
		this.oldAccNbr = oldAccNbr;
	}

	public ProdInstVO getProdInst() {
		return prodInst;
	}

	public void setProdInst(ProdInstVO prodInst) {
		this.prodInst = prodInst;
	}

	public boolean isSuccessAll() {
		return successAll;
	}

	public void setSuccessAll(boolean successAll) {
		this.successAll = successAll;
	}

	public List getSuccessAllReason() {
		return successAllReason;
	}

	public void setSuccessAllReason(List successAllReason) {
		this.successAllReason = successAllReason;
	}

	public boolean isBreakScream() {
		return breakScream;
	}

	public void setBreakScream(boolean breakScream) {
		this.breakScream = breakScream;
	}

	public boolean isExistProdInst() {
		return isExistProdInst;
	}

	public void setExistProdInst(boolean isExistProdInst) {
		this.isExistProdInst = isExistProdInst;
	}

	public boolean getSendActiveFlag() {
		return sendActiveFlag;
	}

	public void setSendActiveFlag(boolean sendActiveFlag) {
		this.sendActiveFlag = sendActiveFlag;
	}
	

}
