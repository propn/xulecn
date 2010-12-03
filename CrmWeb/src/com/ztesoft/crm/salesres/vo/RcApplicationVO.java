package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcApplicationVO extends ValueObject implements VO {

	private String appId = "";
	private String appType = "";
	private String operId = "";
	private String departId = "";
	private String appTime = "";
	
	private String appTypeName = "";
	private String operName = "";
	private String departName = "";
	private String oldAppType = "";
	
	private String status = "";
	

	public RcApplicationVO() {}

	public RcApplicationVO( String pappId, String pappType, String poperId, String pdepartId, String pappTime ) {
		appId = pappId;
		appType = pappType;
		operId = poperId;
		departId = pdepartId;
		appTime = pappTime;
	}
	
	

	public String getOldAppType() {
		return oldAppType;
	}

	public void setOldAppType(String oldAppType) {
		this.oldAppType = oldAppType;
	}

	public String getAppTypeName() {
		return appTypeName;
	}

	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppType() {
		return appType;
	}

	public String getOperId() {
		return operId;
	}

	public String getDepartId() {
		return departId;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppId(String pAppId) {
		appId = pAppId;
	}

	public void setAppType(String pAppType) {
		appType = pAppType;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setAppTime(String pAppTime) {
		appTime = pAppTime;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("APP_ID",this.appId);
		hashMap.put("APP_TYPE",this.appType);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("APP_TIME",this.appTime);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.appId = (String) hashMap.get("APP_ID");
			this.appType = (String) hashMap.get("APP_TYPE");
			this.operId = (String) hashMap.get("OPER_ID");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.appTime = (String) hashMap.get("APP_TIME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_APPLICATION";
	}

}
