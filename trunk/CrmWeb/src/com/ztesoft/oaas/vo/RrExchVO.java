package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RrExchVO extends ValueObject implements VO {

	private String lanId = "";
	private String ppdomId = "";
	private String exchId = "";
	private String exchCode = "";
	private String exchType = "";
	private String exchName = "";
	private String superId = "";
	private String comments = "";

	public RrExchVO() {}

	public RrExchVO( String planId, String pppdomId, String pexchId, String pexchCode, String pexchType, String pexchName, String psuperId, String pcomments ) {
		lanId = planId;
		ppdomId = pppdomId;
		exchId = pexchId;
		exchCode = pexchCode;
		exchType = pexchType;
		exchName = pexchName;
		superId = psuperId;
		comments = pcomments;
	}

	public String getLanId() {
		return lanId;
	}

	public String getPpdomId() {
		return ppdomId;
	}

	public String getExchId() {
		return exchId;
	}

	public String getExchCode() {
		return exchCode;
	}

	public String getExchType() {
		return exchType;
	}

	public String getExchName() {
		return exchName;
	}

	public String getSuperId() {
		return superId;
	}

	public String getComments() {
		return comments;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setPpdomId(String pPpdomId) {
		ppdomId = pPpdomId;
	}

	public void setExchId(String pExchId) {
		exchId = pExchId;
	}

	public void setExchCode(String pExchCode) {
		exchCode = pExchCode;
	}

	public void setExchType(String pExchType) {
		exchType = pExchType;
	}

	public void setExchName(String pExchName) {
		exchName = pExchName;
	}

	public void setSuperId(String pSuperId) {
		superId = pSuperId;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("PPDOM_ID",this.ppdomId);
		hashMap.put("EXCH_ID",this.exchId);
		hashMap.put("EXCH_CODE",this.exchCode);
		hashMap.put("EXCH_TYPE",this.exchType);
		hashMap.put("EXCH_NAME",this.exchName);
		hashMap.put("SUPER_ID",this.superId);
		hashMap.put("COMMENTS",this.comments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.ppdomId = (String) hashMap.get("PPDOM_ID");
			this.exchId = (String) hashMap.get("EXCH_ID");
			this.exchCode = (String) hashMap.get("EXCH_CODE");
			this.exchType = (String) hashMap.get("EXCH_TYPE");
			this.exchName = (String) hashMap.get("EXCH_NAME");
			this.superId = (String) hashMap.get("SUPER_ID");
			this.comments = (String) hashMap.get("COMMENTS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("EXCH_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RR_EXCH";
	}

}
