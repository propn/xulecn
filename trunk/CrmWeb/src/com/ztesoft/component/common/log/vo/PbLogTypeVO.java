package com.ztesoft.component.common.log.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PbLogTypeVO extends ValueObject implements VO {

	private String logType = "";
	private String logTypeName = "";
	private String comments = "";

	public PbLogTypeVO() {}

	public PbLogTypeVO( String plogType, String plogTypeName, String pcomments ) {
		logType = plogType;
		logTypeName = plogTypeName;
		comments = pcomments;
	}

	public String getLogType() {
		return logType;
	}

	public String getLogTypeName() {
		return logTypeName;
	}

	public String getComments() {
		return comments;
	}

	public void setLogType(String pLogType) {
		logType = pLogType;
	}

	public void setLogTypeName(String pLogTypeName) {
		logTypeName = pLogTypeName;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_TYPE",this.logType);
		hashMap.put("LOG_TYPE_NAME",this.logTypeName);
		hashMap.put("COMMENTS",this.comments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logType = (String) hashMap.get("LOG_TYPE");
			this.logTypeName = (String) hashMap.get("LOG_TYPE_NAME");
			this.comments = (String) hashMap.get("COMMENTS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("LOG_TYPE");
		return arrayList;
	}

	public String getTableName() {
		return "PB_LOG_TYPE";
	}

}

