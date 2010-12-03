package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderOperStateVO extends ValueObject implements VO {
       /** 流转环节，申请环节 **/
       public static final String Tache_apply = "1";
       /** 流转环节，审批环节 **/
       public static final String Tache_approve = "2";
       /** 流转环节，出库环节 **/
       public static final String Tache_outStorage = "3";
       /** 流转环节，入库环节 **/
       public static final String Tache_inStorage = "4";
       /** 流转环节，结束环节 **/
       public static final String Tache_end = "5";

	private String appType = "";
	private String fTacheId = "";
	private String stateId = "";
	private String tTacheId = "";
	private String rcDesc = "";

	public RcOrderOperStateVO() {}

	public RcOrderOperStateVO( String pappType, String pfTacheId, String pstateId, String ptTacheId, String prcDesc ) {
		appType = pappType;
		fTacheId = pfTacheId;
		stateId = pstateId;
		tTacheId = ptTacheId;
		rcDesc = prcDesc;
	}

	public String getAppType() {
		return appType;
	}

	public String getFTacheId() {
		return fTacheId;
	}

	public String getStateId() {
		return stateId;
	}

	public String getTTacheId() {
		return tTacheId;
	}

	public String getRcDesc() {
		return rcDesc;
	}

	public void setAppType(String pAppType) {
		appType = pAppType;
	}

	public void setFTacheId(String pFTacheId) {
		fTacheId = pFTacheId;
	}

	public void setStateId(String pStateId) {
		stateId = pStateId;
	}

	public void setTTacheId(String pTTacheId) {
		tTacheId = pTTacheId;
	}

	public void setRcDesc(String pRcDesc) {
		rcDesc = pRcDesc;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("APP_TYPE",this.appType);
		hashMap.put("F_TACHE_ID",this.fTacheId);
		hashMap.put("STATE_ID",this.stateId);
		hashMap.put("T_TACHE_ID",this.tTacheId);
		hashMap.put("RC_DESC",this.rcDesc);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.appType = (String) hashMap.get("APP_TYPE");
			this.fTacheId = (String) hashMap.get("F_TACHE_ID");
			this.stateId = (String) hashMap.get("STATE_ID");
			this.tTacheId = (String) hashMap.get("T_TACHE_ID");
			this.rcDesc = (String) hashMap.get("RC_DESC");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("APP_TYPE");
		arrayList.add("F_TACHE_ID");
		arrayList.add("STATE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_ORDER_OPER_STATE";
	}

}
