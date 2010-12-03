package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class DcDataInfoVO extends ValueObject implements VO {

	private String infoId = "";
	private String infoName = "";
	private String runSql = "";
	private String retCols = "";
	private String infoType = "";
	private String memo = "";
	private String objType = "";
	private String lanId = "";
	private String lanIdCN = "";

	public String getLanIdCN() {
		return lanIdCN;
	}

	public void setLanIdCN(String lanIdCN) {
		this.lanIdCN = lanIdCN;
	}

	public DcDataInfoVO() {}

	public DcDataInfoVO( String pinfoId, String pinfoName, String prunSql, String pretCols, String pinfoType, String pmemo, String pobjType, String planId ) {
		infoId = pinfoId;
		infoName = pinfoName;
		runSql = prunSql;
		retCols = pretCols;
		infoType = pinfoType;
		memo = pmemo;
		objType = pobjType;
		lanId = planId;
	}

	public String getInfoId() {
		return infoId;
	}

	public String getInfoName() {
		return infoName;
	}

	public String getRunSql() {
		return runSql;
	}

	public String getRetCols() {
		return retCols;
	}

	public String getInfoType() {
		return infoType;
	}

	public String getMemo() {
		return memo;
	}

	public String getObjType() {
		return objType;
	}

	public String getLanId() {
		return lanId;
	}

	public void setInfoId(String pInfoId) {
		infoId = pInfoId;
	}

	public void setInfoName(String pInfoName) {
		infoName = pInfoName;
	}

	public void setRunSql(String pRunSql) {
		runSql = pRunSql;
	}

	public void setRetCols(String pRetCols) {
		retCols = pRetCols;
	}

	public void setInfoType(String pInfoType) {
		infoType = pInfoType;
	}

	public void setMemo(String pMemo) {
		memo = pMemo;
	}

	public void setObjType(String pObjType) {
		objType = pObjType;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("INFO_ID",this.infoId);
		hashMap.put("INFO_NAME",this.infoName);
		hashMap.put("RUN_SQL",this.runSql);
		hashMap.put("RET_COLS",this.retCols);
		hashMap.put("INFO_TYPE",this.infoType);
		hashMap.put("MEMO",this.memo);
		hashMap.put("OBJ_TYPE",this.objType);
		hashMap.put("LAN_ID",this.lanId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.infoId = (String) hashMap.get("INFO_ID");
			this.infoName = (String) hashMap.get("INFO_NAME");
			this.runSql = (String) hashMap.get("RUN_SQL");
			this.retCols = (String) hashMap.get("RET_COLS");
			this.infoType = (String) hashMap.get("INFO_TYPE");
			this.memo = (String) hashMap.get("MEMO");
			this.objType = (String) hashMap.get("OBJ_TYPE");
			this.lanId = (String) hashMap.get("LAN_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("INFO_ID");
		return arrayList;
	}

	public String getTableName() {
		return "DC_DATA_INFO";
	}

}
