package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class MpDepartTermVO extends ValueObject implements VO {

	private String termCode = "";
	private String departId = "";
	private String partyId = "";
	private String startTime = "";
	private String endTime = "";

	public MpDepartTermVO() {}

	public MpDepartTermVO( String ptermCode, String pdepartId, String ppartyId, String pstartTime, String pendTime ) {
		termCode = ptermCode;
		departId = pdepartId;
		partyId = ppartyId;
		startTime = pstartTime;
		endTime = pendTime;
	}

	public String getTermCode() {
		return termCode;
	}

	public String getDepartId() {
		return departId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setTermCode(String pTermCode) {
		termCode = pTermCode;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setStartTime(String pStartTime) {
		startTime = pStartTime;
	}

	public void setEndTime(String pEndTime) {
		endTime = pEndTime;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("TERM_CODE",this.termCode);
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("START_TIME",this.startTime);
		hashMap.put("END_TIME",this.endTime);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.termCode = (String) hashMap.get("TERM_CODE");
			this.departId = (String) hashMap.get("DEPART_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.startTime = (String) hashMap.get("START_TIME");
			this.endTime = (String) hashMap.get("END_TIME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DEPART_ID");
		arrayList.add("TERM_CODE");
		return arrayList;
	}

	public String getTableName() {
		return "MP_DEPART_TERM";
	}

}
