package com.ztesoft.vsop.engine.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * ������Ϣ
 * @author cooltan
 *
 */
public class CustomerOrder {
	private static Logger logger = Logger.getLogger(CustomerOrder.class);
	private String custOrderId ; //������ʶ
	private String otherSysOrderId ;//�ⲿ������ʶ
	private String custSoNumber ;//��ˮ��
	private String custOrderType ;//�������� ��Ӧ�ӿڵ�ActionType
	private String timeNameId ;
	private String custId ;//�ͻ���ʶ
	private String staffId ; //����Ա����ʶ
	private String chnId ;
	private String status ;//����״̬
	private String statusDate ;
	private String preHandleFlag ;
	private String handlePeopleName ;
	private String contactPhone ;
	private String contactPeople ;
	private String priority ;
	private String reason ;
	private String orderChn ;//�������� �붩��ϵͳһֱ
	private String orderSystem ;//����ϵͳ 
	private String receiveDate ;//��������ʱ��
	private String disposalResult ;//����������
	private String disposalResultDesc ;//��������������
	private String accNbr ; //��Ʒ����
	private String prodId ;//��Ʒ����(��Ʒ���ͣ�
	private String lanId ;//����������
	private String prodInstId ;//��Ʒʵ����ʶ
	private String partitionId ;
	
	private String userName;//�û�����
	private String uimNO;//UIM������
	private String oldUimNO;//�û���UIM������
	
	private String oldAccNbr;//ԭ��Ʒ���룬�ĺ���
	private ProdInstVO prodInst;//��Ʒʵ��VO
	//List< ProductOfferInfo>;��������Ʒ�б�
	private List productOfferInfoList;
	//List< AproductInfo>; ����ҵ������������Ʒ�б�
	private List aproductInfoList;
	private String dbActionType;
	private boolean successAll=true;//��ʾ�˶����Ƿ�����Ƿ�ȫ���ɹ� GX�� yuanyang
	private List successAllReason;//<resultInfo>��ʾ�˶����Ƿ�����Ƿ�ȫ���ɹ�ԭ�� GX�� yuanyang
	private boolean breakScream=false;//��ʾ��Ҫ�����˶����ķ���Ťת���� GX�� yuanyang
	private boolean isExistProdInst=true;//��־�û���Ϣ�Ƿ�����VSOP, ������ϵͬ������ʱ����IOMδͬ���û���Ϣ��VSOPʱ���Ȱ�ͬ�������Ķ�����ϵд���м�� GX�� yuanyang
	private boolean sendActiveFlag=true;//�Ƿ�Ҫ��ƽ̨�����־��Ĭ���ͣ�ismp����ͬ��ʱ���͡�--add 2010-9-21 by wendm

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
		//�������� UID oldUIM
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
			//�������� UID oldUIM
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
