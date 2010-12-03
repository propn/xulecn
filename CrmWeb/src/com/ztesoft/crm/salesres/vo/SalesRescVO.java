package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class SalesRescVO extends ValueObject implements VO {

	private String salesRescId = "";

	private String familyId = "";

	private String familyName = "";

	private String rescAreaId = "";

	private String manageMode = "";

	private String salesRescCode = "";

	private String salesRescName = "";

	private String salesRescWorth = "";

	private String state = "";

	private String stateDate = "";

	private String createDate = "";

	private String effDate = "";

	private String expDate = "";

	// 物资系统编码
	private String ncSalesRescId = "";

	// 终端设备编码
	private String dcDeviceScode = "";

	private String lowPrice = "";

	private String fee_item_id = "";

	private String feeitem_name = "";

	private String isCoop = "";

	private String doDetail = "";
	
	private String uKind = "";

	private String mostPrice = "";
	
	/** 营销资源管理模式:实例管理 * */
	public final static String ManageMode_Entity = "0";

	/** 营销资源管理模式:存量管理 * */
	public final static String ManageMode_Stock = "1";

	/** 营销资源管理模式:不管理 * */
	public final static String ManageMode_NoMag = "2";

	public SalesRescVO() {
	}

	public SalesRescVO(String psalesRescId, String pfamilyId,
			String prescAreaId, String pmanageMode, String psalesRescCode,
			String psalesRescName, String psalesRescWorth, String pstate,
			String pstateDate, String pcreateDate, String peffDate,
			String pexpDate) {
		salesRescId = psalesRescId;
		familyId = pfamilyId;
		rescAreaId = prescAreaId;
		manageMode = pmanageMode;
		salesRescCode = psalesRescCode;
		salesRescName = psalesRescName;
		salesRescWorth = psalesRescWorth;
		state = pstate;
		stateDate = pstateDate;
		createDate = pcreateDate;
		effDate = peffDate;
		expDate = pexpDate;
	}

	public String getSalesRescId() {
		return salesRescId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getRescAreaId() {
		return rescAreaId;
	}

	public String getManageMode() {
		return manageMode;
	}

	public String getSalesRescCode() {
		return salesRescCode;
	}

	public String getSalesRescName() {
		return salesRescName;
	}

	public String getSalesRescWorth() {
		return salesRescWorth;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setSalesRescId(String pSalesRescId) {
		salesRescId = pSalesRescId;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setRescAreaId(String pRescAreaId) {
		rescAreaId = pRescAreaId;
	}

	public void setManageMode(String pManageMode) {
		manageMode = pManageMode;
	}

	public void setSalesRescCode(String pSalesRescCode) {
		salesRescCode = pSalesRescCode;
	}

	public void setSalesRescName(String pSalesRescName) {
		salesRescName = pSalesRescName;
	}

	public void setSalesRescWorth(String pSalesRescWorth) {
		salesRescWorth = pSalesRescWorth;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SALES_RESOURCE_ID", this.salesRescId);
		hashMap.put("FAMILY_ID", this.familyId);
		hashMap.put("RESOURCE_AREA_ID", this.rescAreaId);
		hashMap.put("MANAGE_MODE", this.manageMode);
		hashMap.put("SALES_RESOURCE_CODE", this.salesRescCode);
		hashMap.put("SALES_RESOURCE_NAME", this.salesRescName);
		hashMap.put("SALES_RESOURCE_WORTH", this.salesRescWorth);
		hashMap.put("STATE", this.state);
		hashMap.put("STATE_DATE", this.stateDate);
		hashMap.put("CREATE_DATE", this.createDate);
		hashMap.put("EFF_DATE", this.effDate);
		hashMap.put("EXP_DATE", this.expDate);
		hashMap.put("FAMILY_NAME", this.familyName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.salesRescId = (String) hashMap.get("SALES_RESOURCE_ID");
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.rescAreaId = (String) hashMap.get("RESOURCE_AREA_ID");
			this.manageMode = (String) hashMap.get("MANAGE_MODE");
			this.salesRescCode = (String) hashMap.get("SALES_RESOURCE_CODE");
			this.salesRescName = (String) hashMap.get("SALES_RESOURCE_NAME");
			this.salesRescWorth = (String) hashMap.get("SALES_RESOURCE_WORTH");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.familyName = (String) hashMap.get("FAMILY_NAME");
		}
	}

	public String getTableName() {
		return "SALES_RESOURCE";
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public void setDcDeviceScode(String dcDeviceScode) {
		this.dcDeviceScode = dcDeviceScode;
	}

	public String getDcDeviceScode() {
		return dcDeviceScode;
	}

	public String getNcSalesRescId() {
		return ncSalesRescId;
	}

	public String getDoDetail() {
		return doDetail;
	}

	public void setNcSalesRescId(String ncSalesRescId) {
		this.ncSalesRescId = ncSalesRescId;
	}

	public void setDoDetail(String doDetail) {
		this.doDetail = doDetail;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getFee_item_id() {
		return fee_item_id;
	}

	public void setFee_item_id(String fee_item_id) {
		this.fee_item_id = fee_item_id;
	}

	public String getFeeitem_name() {
		return feeitem_name;
	}

	public void setFeeitem_name(String feeitem_name) {
		this.feeitem_name = feeitem_name;
	}

	public String getIsCoop() {
		return isCoop;
	}

	public void setIsCoop(String isCoop) {
		this.isCoop = isCoop;
	}

	public String getUKind() {
		return uKind;
	}

	public void setUKind(String kind) {
		uKind = kind;
	}

	public String getMostPrice() {
		return mostPrice;
	}

	public void setMostPrice(String mostPrice) {
		this.mostPrice = mostPrice;
	}

}
