package com.ztesoft.component.common.log.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PbExceptionLogVO extends ValueObject implements VO {

	private String logId = "";
	private String exceptionCode = "";
	private String exceptionReason = "";
	private String exceptionProject = "";
	private String stack = "";
	private String className = "";
	private String methName = "";

	public PbExceptionLogVO() {}

	public PbExceptionLogVO( String plogId, String pexceptionCode, String pexceptionReason, String pexceptionProject, String pstack, String pclassName, String pmethName ) {
		logId = plogId;
		exceptionCode = pexceptionCode;
		exceptionReason = pexceptionReason;
		exceptionProject = pexceptionProject;
		stack = pstack;
		className = pclassName;
		methName = pmethName;
	}

	public String getLogId() {
		return logId;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public String getExceptionProject() {
		return exceptionProject;
	}

	public String getStack() {
		return stack;
	}

	public String getClassName() {
		return className;
	}

	public String getMethName() {
		return methName;
	}

	public void setLogId(String pLogId) {
		logId = pLogId;
	}

	public void setExceptionCode(String pExceptionCode) {
		exceptionCode = pExceptionCode;
	}

	public void setExceptionReason(String pExceptionReason) {
		exceptionReason = pExceptionReason;
	}

	public void setExceptionProject(String pExceptionProject) {
		exceptionProject = pExceptionProject;
	}

	public void setStack(String pStack) {
		stack = pStack;
	}

	public void setClassName(String pClassName) {
		className = pClassName;
	}

	public void setMethName(String pMethName) {
		methName = pMethName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_ID",this.logId);
		hashMap.put("EXCEPTION_CODE",this.exceptionCode);
		hashMap.put("EXCEPTION_REASON",this.exceptionReason);
		hashMap.put("EXCEPTION_PROJECT",this.exceptionProject);
		hashMap.put("STACK",this.stack);
		hashMap.put("CLASS_NAME",this.className);
		hashMap.put("METH_NAME",this.methName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logId = (String) hashMap.get("LOG_ID");
			this.exceptionCode = (String) hashMap.get("EXCEPTION_CODE");
			this.exceptionReason = (String) hashMap.get("EXCEPTION_REASON");
			this.exceptionProject = (String) hashMap.get("EXCEPTION_PROJECT");
			this.stack = (String) hashMap.get("STACK");
			this.className = (String) hashMap.get("CLASS_NAME");
			this.methName = (String) hashMap.get("METH_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("LOG_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PB_EXCEPTION_LOG";
	}

}

