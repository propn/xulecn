package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class MpDepartVO extends ValueObject implements VO {

	private String partyId = "";
	private String orgName = "";
	private String departType = "";
	private String termFlag = "";
	private String paySeatType = "";
	private String regionId = "";
	private String regionName = "";
	private String superPartyId = "";
	private String superPartyName = "";

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getSuperPartyName() {
		return superPartyName;
	}

	public void setSuperPartyName(String superPartyName) {
		this.superPartyName = superPartyName;
	}

	public MpDepartVO() {}

	public MpDepartVO( String ppartyId, String pdepartType, String ptermFlag, String ppaySeatType, String pregionId, String psuperPartyId ) {
		partyId = ppartyId;
		departType = pdepartType;
		termFlag = ptermFlag;
		paySeatType = ppaySeatType;
		regionId = pregionId;
		superPartyId = psuperPartyId ;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getDepartType() {
		return departType;
	}

	public String getTermFlag() {
		return termFlag;
	}

	public String getPaySeatType() {
		return paySeatType;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setDepartType(String pDepartType) {
		departType = pDepartType;
	}

	public void setTermFlag(String pTermFlag) {
		termFlag = pTermFlag;
	}

	public void setPaySeatType(String pPaySeatType) {
		paySeatType = pPaySeatType;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("DEPART_TYPE",this.departType);
		hashMap.put("TERM_FLAG",this.termFlag);
		hashMap.put("PAY_SEAT_TYPE",this.paySeatType);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("SUPER_PARTY_ID",this.superPartyId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.departType = (String) hashMap.get("DEPART_TYPE");
			this.termFlag = (String) hashMap.get("TERM_FLAG");
			this.paySeatType = (String) hashMap.get("PAY_SEAT_TYPE");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.superPartyId = (String)hashMap.get("SUPER_PARTY_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "MP_DEPARTMENT";
	}

	public String getSuperPartyId() {
		return superPartyId;
	}

	public void setSuperPartyId(String superPartyId) {
		this.superPartyId = superPartyId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
