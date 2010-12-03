package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ChSaleCommVO extends ValueObject implements VO {

	private String commId = "";
	private String commName = "";
	private String areaId = "";
	private String manager = "";
	private String validFlag = "";
	private String remark = "";

	public ChSaleCommVO() {}

	public ChSaleCommVO( String pcommId, String pcommName, String pareaId, String pmanager, String pvalidFlag, String premark ) {
		commId = pcommId;
		commName = pcommName;
		areaId = pareaId;
		manager = pmanager;
		validFlag = pvalidFlag;
		remark = premark;
	}

	public String getCommId() {
		return commId;
	}

	public String getCommName() {
		return commName;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getManager() {
		return manager;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setCommId(String pCommId) {
		commId = pCommId;
	}

	public void setCommName(String pCommName) {
		commName = pCommName;
	}

	public void setAreaId(String pAreaId) {
		areaId = pAreaId;
	}

	public void setManager(String pManager) {
		manager = pManager;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public void setRemark(String pRemark) {
		remark = pRemark;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("COMM_ID",this.commId);
		hashMap.put("COMM_NAME",this.commName);
		hashMap.put("AREA_ID",this.areaId);
		hashMap.put("MANAGER",this.manager);
		hashMap.put("VALID_FLAG",this.validFlag);
		hashMap.put("REMARK",this.remark);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.commId = (String) hashMap.get("COMM_ID");
			this.commName = (String) hashMap.get("COMM_NAME");
			this.areaId = (String) hashMap.get("AREA_ID");
			this.manager = (String) hashMap.get("MANAGER");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
			this.remark = (String) hashMap.get("REMARK");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("COMM_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CH_SALE_COMM";
	}

}
