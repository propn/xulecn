package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class ClRcEntityVO extends ValueObject implements VO {

	private String serialNo = "";
	private String rescInstanceId = "";
	private String custId = "";
	private String salesRescId = "";
	private String prodId = "";
	private String occupyDate = "";
	private String lanId = "";
	private String operId = "";
	private String operDate = "";
	private String storageId = "";
	private String actCode = "";

	public ClRcEntityVO() {}

	public ClRcEntityVO( String pserialNo, String prescInstanceId, String pcustId, String psalesRescId, String pprodId, String poccupyDate, String planId, String poperId, String poperDate, String pstorageId, String pactCode ) {
		serialNo = pserialNo;
		rescInstanceId = prescInstanceId;
		custId = pcustId;
		salesRescId = psalesRescId;
		prodId = pprodId;
		occupyDate = poccupyDate;
		lanId = planId;
		operId = poperId;
		operDate = poperDate;
		storageId = pstorageId;
		actCode = pactCode;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public String getRescInstanceId() {
		return rescInstanceId;
	}

	public String getCustId() {
		return custId;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getOccupyDate() {
		return occupyDate;
	}

	public String getLanId() {
		return lanId;
	}

	public String getOperId() {
		return operId;
	}

	public String getOperDate() {
		return operDate;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getActCode() {
		return actCode;
	}

	public void setSerialNo(String pSerialNo) {
		serialNo = pSerialNo;
	}

	public void setRescInstanceId(String pRescInstanceId) {
		rescInstanceId = pRescInstanceId;
	}

	public void setCustId(String pCustId) {
		custId = pCustId;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setOccupyDate(String pOccupyDate) {
		occupyDate = pOccupyDate;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setOperDate(String pOperDate) {
		operDate = pOperDate;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setActCode(String pActCode) {
		actCode = pActCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SERIAL_NO",this.serialNo);
		hashMap.put("RESOURCE_INSTANCE_ID",this.rescInstanceId);
		hashMap.put("CUST_ID",this.custId);
		hashMap.put("SALES_RESOURCE_ID",this.salesRescId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("OCCUPY_DATE",this.occupyDate);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("OPER_DATE",this.operDate);
		hashMap.put("STORAGE_ID",this.storageId);
		hashMap.put("ACT_CODE",this.actCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.serialNo = (String) hashMap.get("SERIAL_NO");
			this.rescInstanceId = (String) hashMap.get("RESOURCE_INSTANCE_ID");
			this.custId = (String) hashMap.get("CUST_ID");
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.occupyDate = (String) hashMap.get("OCCUPY_DATE");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.operDate = (String) hashMap.get("OPER_DATE");
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.actCode = (String) hashMap.get("ACT_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("SERIAL_NO");
		return arrayList;
	}

	public String getTableName() {
		return "CL_RC_ENTITY";
	}

}
