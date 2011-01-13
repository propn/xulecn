package com.ztesoft.component.common.log.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PbLogVO extends ValueObject implements VO {

	private String id = "";
	private String moduleId = "";
	private String actId = "";
	private String logType = "";
	private String staffId = "";
	private String staffName = "";
	private String logDeta = "";
	private String state = "";
	private String stateDate = "";
	private String createDate = "";
	private String logDate = "";

	public PbLogVO() {}

	public PbLogVO( String pid, String pmoduleId, String pactId, String plogType, String pstaffId, String pstaffName, String plogDeta, String pstate, String pstateDate, String pcreateDate, String plogDate ) {
		id = pid;
		moduleId = pmoduleId;
		actId = pactId;
		logType = plogType;
		staffId = pstaffId;
		staffName = pstaffName ;
		logDeta = plogDeta;
		state = pstate;
		stateDate = pstateDate;
		createDate = pcreateDate;
		logDate = plogDate;
	}

	public String getId() {
		return id;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getActId() {
		return actId;
	}

	public String getLogType() {
		return logType;
	}

	public String getStaffId() {
		return staffId;
	}

	public String getLogDeta() {
		return logDeta;
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

	public String getLogDate() {
		return logDate;
	}

	public void setId(String pId) {
		id = pId;
	}

	public void setModuleId(String pModuleId) {
		moduleId = pModuleId;
	}

	public void setActId(String pActId) {
		actId = pActId;
	}

	public void setLogType(String pLogType) {
		logType = pLogType;
	}

	public void setStaffId(String pStaffId) {
		staffId = pStaffId;
	}

	public void setLogDeta(String pLogDeta) {
		logDeta = pLogDeta;
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

	public void setLogDate(String pLogDate) {
		logDate = pLogDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ID",this.id);
		hashMap.put("MODULE_ID",this.moduleId);
		hashMap.put("ACTION_ID",this.actId);
		hashMap.put("LOG_TYPE",this.logType);
		hashMap.put("STAFF_ID",this.staffId);
		hashMap.put("STAFF_NAME",this.staffName);
		hashMap.put("LOG_DETAIL",this.logDeta);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("LOG_DATE",this.logDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.id = (String) hashMap.get("ID");
			this.moduleId = (String) hashMap.get("MODULE_ID");
			this.actId = (String) hashMap.get("ACTION_ID");
			this.logType = (String) hashMap.get("LOG_TYPE");
			this.staffId = (String) hashMap.get("STAFF_ID");
			this.staffName = (String) hashMap.get("STAFF_NAME");
			this.logDeta = (String) hashMap.get("LOG_DETAIL");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.logDate = (String) hashMap.get("LOG_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ID");
		return arrayList;
	}

	public String getTableName() {
		return "PB_LOG";
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}

