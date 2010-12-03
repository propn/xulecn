package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderExcVO extends ValueObject implements VO {

	private String logId = "";
	private String orderId = "";
	private String tacheId = "";
	private String excTime = "";
	private String departId = "";
	private String operId = "";
	private String stateId = "";
	private String excComments = "";
	
	private String tacheName = "";
	private String departName = "";
	private String operName = "";
	private String stateName = "";

	public RcOrderExcVO() {}

	public RcOrderExcVO( String plogId, String porderId, String ptacheId, String pexcTime, String pdepartId, String poperId, String pstateId, String pexcComments ) {
		logId = plogId;
		orderId = porderId;
		tacheId = ptacheId;
		excTime = pexcTime;
		departId = pdepartId;
		operId = poperId;
		stateId = pstateId;
		excComments = pexcComments;
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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getTacheName() {
		return tacheName;
	}

	public void setTacheName(String tacheName) {
		this.tacheName = tacheName;
	}

	public String getLogId() {
		return logId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getTacheId() {
		return tacheId;
	}

	public String getExcTime() {
		return excTime;
	}

	public String getDepartId() {
		return departId;
	}

	public String getOperId() {
		return operId;
	}

	public String getStateId() {
		return stateId;
	}

	public String getExcComments() {
		return excComments;
	}

	public void setLogId(String pLogId) {
		logId = pLogId;
	}

	public void setOrderId(String pOrderId) {
		orderId = pOrderId;
	}

	public void setTacheId(String pTacheId) {
		tacheId = pTacheId;
	}

	public void setExcTime(String pExcTime) {
		excTime = pExcTime;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setStateId(String pStateId) {
		stateId = pStateId;
	}

	public void setExcComments(String pExcComments) {
		excComments = pExcComments;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_ID",this.logId);
		hashMap.put("ORDER_ID",this.orderId);
		hashMap.put("TACHE_ID",this.tacheId);
		hashMap.put("EXC_TIME",this.excTime);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("STATE_ID",this.stateId);
		hashMap.put("EXC_COMMENTS",this.excComments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logId = (String) hashMap.get("LOG_ID");
			this.orderId = (String) hashMap.get("ORDER_ID");
			this.tacheId = (String) hashMap.get("TACHE_ID");
			this.excTime = (String) hashMap.get("EXC_TIME");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.stateId = (String) hashMap.get("STATE_ID");
			this.excComments = (String) hashMap.get("EXC_COMMENTS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_ORDER_EXC";
	}

}
