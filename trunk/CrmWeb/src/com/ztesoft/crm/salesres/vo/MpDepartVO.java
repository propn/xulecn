package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class MpDepartVO extends ValueObject implements VO {

	private String departId = "";
	private String departCode = "";
	private String departType = "";
	private String departName = "";
	private String superId = "";
	private String townId = "";
	private String regionId = "";
	private String termFlag = "";
	private String unitType = "";
	private String unitCode = "";
	private String validFlag = "";
	private String comments = "";
	private String departBelong = "";


	public MpDepartVO() {}

	public MpDepartVO( String pdepartId, String pdepartCode, String pdepartType, String pdepartName, String psuperId, String ptownId, String pregionId, String ptermFlag, String punitType, String punitCode, String pvalidFlag, String pcomments, String pdepartBelong ) {
		departId = pdepartId;
		departCode = pdepartCode;
		departType = pdepartType;
		departName = pdepartName;
		superId = psuperId;
		townId = ptownId;
		regionId = pregionId;
		termFlag = ptermFlag;
		unitType = punitType;
		unitCode = punitCode;
		validFlag = pvalidFlag;
		comments = pcomments;
		departBelong = pdepartBelong;
	}

	public String getDepartId() {
		return departId;
	}

	public String getDepartCode() {
		return departCode;
	}

	public String getDepartType() {
		return departType;
	}

	public String getDepartName() {
		return departName;
	}

	public String getSuperId() {
		return superId;
	}

	public String getTownId() {
		return townId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getTermFlag() {
		return termFlag;
	}

	public String getUnitType() {
		return unitType;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public String getComments() {
		return comments;
	}

	public String getDepartBelong() {
		return departBelong;
	}

    public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setDepartCode(String pDepartCode) {
		departCode = pDepartCode;
	}

	public void setDepartType(String pDepartType) {
		departType = pDepartType;
	}

	public void setDepartName(String pDepartName) {
		departName = pDepartName;
	}

	public void setSuperId(String pSuperId) {
		superId = pSuperId;
	}

	public void setTownId(String pTownId) {
		townId = pTownId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setTermFlag(String pTermFlag) {
		termFlag = pTermFlag;
	}

	public void setUnitType(String pUnitType) {
		unitType = pUnitType;
	}

	public void setUnitCode(String pUnitCode) {
		unitCode = pUnitCode;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public void setDepartBelong(String pDepartBelong) {
		departBelong = pDepartBelong;
	}


    public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("DEPART_CODE",this.departCode);
		hashMap.put("DEPART_TYPE",this.departType);
		hashMap.put("DEPART_NAME",this.departName);
		hashMap.put("SUPER_ID",this.superId);
		hashMap.put("TOWN_ID",this.townId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("TERM_FLAG",this.termFlag);
		hashMap.put("UNIT_TYPE",this.unitType);
		hashMap.put("UNIT_CODE",this.unitCode);
		hashMap.put("VALID_FLAG",this.validFlag);
		hashMap.put("COMMENTS",this.comments);
		hashMap.put("DEPART_BELONG",this.departBelong);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.departId = (String) hashMap.get("DEPART_ID");
			this.departCode = (String) hashMap.get("DEPART_CODE");
			this.departType = (String) hashMap.get("DEPART_TYPE");
			this.departName = (String) hashMap.get("DEPART_NAME");
			this.superId = (String) hashMap.get("SUPER_ID");
			this.townId = (String) hashMap.get("TOWN_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.termFlag = (String) hashMap.get("TERM_FLAG");
			this.unitType = (String) hashMap.get("UNIT_TYPE");
			this.unitCode = (String) hashMap.get("UNIT_CODE");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
			this.comments = (String) hashMap.get("COMMENTS");
			this.departBelong = (String) hashMap.get("DEPART_BELONG");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DEPART_ID");
		return arrayList;
	}

	public String getTableName() {
		return "MP_DEPARTMENT";
	}

}
