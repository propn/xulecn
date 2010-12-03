package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RcMeasureVO extends ValueObject implements VO {

	private String lanId = "";
	private String exchId = "";
	private String measureId = "";
	private String measureCode = "";
	private String measureName = "";
	private String ctTache = "";
	private String smeasureId = "";
	private String comments = "";

	public RcMeasureVO() {}

	public RcMeasureVO( String planId, String pexchId, String pmeasureId, String pmeasureCode, String pmeasureName, String pctTache, String psmeasureId, String pcomments ) {
		lanId = planId;
		exchId = pexchId;
		measureId = pmeasureId;
		measureCode = pmeasureCode;
		measureName = pmeasureName;
		ctTache = pctTache;
		smeasureId = psmeasureId;
		comments = pcomments;
	}

	public String getLanId() {
		return lanId;
	}

	public String getExchId() {
		return exchId;
	}

	public String getMeasureId() {
		return measureId;
	}

	public String getMeasureCode() {
		return measureCode;
	}

	public String getMeasureName() {
		return measureName;
	}

	public String getCtTache() {
		return ctTache;
	}

	public String getSmeasureId() {
		return smeasureId;
	}

	public String getComments() {
		return comments;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setExchId(String pExchId) {
		exchId = pExchId;
	}

	public void setMeasureId(String pMeasureId) {
		measureId = pMeasureId;
	}

	public void setMeasureCode(String pMeasureCode) {
		measureCode = pMeasureCode;
	}

	public void setMeasureName(String pMeasureName) {
		measureName = pMeasureName;
	}

	public void setCtTache(String pCtTache) {
		ctTache = pCtTache;
	}

	public void setSmeasureId(String pSmeasureId) {
		smeasureId = pSmeasureId;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("EXCH_ID",this.exchId);
		hashMap.put("MEASURE_ID",this.measureId);
		hashMap.put("MEASURE_CODE",this.measureCode);
		hashMap.put("MEASURE_NAME",this.measureName);
		hashMap.put("CT_TACHE",this.ctTache);
		hashMap.put("SMEASURE_ID",this.smeasureId);
		hashMap.put("COMMENTS",this.comments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.exchId = (String) hashMap.get("EXCH_ID");
			this.measureId = (String) hashMap.get("MEASURE_ID");
			this.measureCode = (String) hashMap.get("MEASURE_CODE");
			this.measureName = (String) hashMap.get("MEASURE_NAME");
			this.ctTache = (String) hashMap.get("CT_TACHE");
			this.smeasureId = (String) hashMap.get("SMEASURE_ID");
			this.comments = (String) hashMap.get("COMMENTS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("MEASURE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_MEASURE";
	}

}
