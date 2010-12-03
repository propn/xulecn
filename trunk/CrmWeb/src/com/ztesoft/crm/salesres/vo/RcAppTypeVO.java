package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcAppTypeVO extends ValueObject implements VO {

	private String appType = "";
	private String appTypeName = "";
	private String flowStr = "";
	private String inStyle = "";
	private String outStyle = "";
        private String flowStype = "";
       private String upAppType = "";

	public RcAppTypeVO() {}

	public RcAppTypeVO( String pappType, String pappTypeName, String pflowStr, String pinStyle, String poutStyle ) {
		appType = pappType;
		appTypeName = pappTypeName;
		flowStr = pflowStr;
		inStyle = pinStyle;
		outStyle = poutStyle;
	}

	public String getAppType() {
		return appType;
	}

	public String getAppTypeName() {
		return appTypeName;
	}

	public String getFlowStr() {
		return flowStr;
	}

	public String getInStyle() {
		return inStyle;
	}

	public String getOutStyle() {
		return outStyle;
	}

  public String getFlowStype() {
    return flowStype;
  }

  public String getUpAppType() {
    return upAppType;
  }

  public void setAppType(String pAppType) {
		appType = pAppType;
	}

	public void setAppTypeName(String pAppTypeName) {
		appTypeName = pAppTypeName;
	}

	public void setFlowStr(String pFlowStr) {
		flowStr = pFlowStr;
	}

	public void setInStyle(String pInStyle) {
		inStyle = pInStyle;
	}

	public void setOutStyle(String pOutStyle) {
		outStyle = pOutStyle;
	}

  public void setFlowStype(String flowStype) {
    this.flowStype = flowStype;
  }

  public void setUpAppType(String upAppType) {
    this.upAppType = upAppType;
  }

  public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("APP_TYPE",this.appType);
		hashMap.put("APP_TYPE_NAME",this.appTypeName);
		hashMap.put("FLOW_STR",this.flowStr);
		hashMap.put("IN_STYLE",this.inStyle);
		hashMap.put("OUT_STYLE",this.outStyle);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.appType = (String) hashMap.get("APP_TYPE");
			this.appTypeName = (String) hashMap.get("APP_TYPE_NAME");
			this.flowStr = (String) hashMap.get("FLOW_STR");
			this.inStyle = (String) hashMap.get("IN_STYLE");
			this.outStyle = (String) hashMap.get("OUT_STYLE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_APP_TYPE";
	}

}
