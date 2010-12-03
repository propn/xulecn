package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RrDepartTermVO extends ValueObject implements VO {

	private String ipaddr = "";
	private String endIpaddr = "";
	private String partyId = "";
	private String ipName = "";
	private String validDate = "";
	private String lanId = "";
	private String staticState = "";
	private String validFlag = "";
	private String comments = "";
	private String regionId = "";

	public RrDepartTermVO() {}

	public RrDepartTermVO( String pipaddr, String pendIpaddr, String ppartyId, String pipName, String pvalidDate, String planId, String pstaticState, String pvalidFlag, String pcomments, String pregionId ) {
		ipaddr = pipaddr;
		endIpaddr = pendIpaddr;
		partyId = ppartyId;
		ipName = pipName;
		validDate = pvalidDate;
		lanId = planId;
		staticState = pstaticState;
		validFlag = pvalidFlag;
		comments = pcomments;
		regionId = pregionId;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public String getEndIpaddr() {
		return endIpaddr;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getIpName() {
		return ipName;
	}

	public String getValidDate() {
		return validDate;
	}

	public String getLanId() {
		return lanId;
	}

	public String getStaticState() {
		return staticState;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public String getComments() {
		return comments;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setIpaddr(String pIpaddr) {
		ipaddr = pIpaddr;
	}

	public void setEndIpaddr(String pEndIpaddr) {
		endIpaddr = pEndIpaddr;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setIpName(String pIpName) {
		ipName = pIpName;
	}

	public void setValidDate(String pValidDate) {
		validDate = pValidDate;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setStaticState(String pStaticState) {
		staticState = pStaticState;
	}

	public void setValidFlag(String pValidFlag) {
		validFlag = pValidFlag;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("IPADDR",this.ipaddr);
		hashMap.put("END_IPADDR",this.endIpaddr);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("IP_NAME",this.ipName);
		hashMap.put("VALID_DATE",this.validDate);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("STATIC_STATE",this.staticState);
		hashMap.put("VALID_FLAG",this.validFlag);
		hashMap.put("COMMENTS",this.comments);
		hashMap.put("REGION_ID",this.regionId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.ipaddr = (String) hashMap.get("IPADDR");
			this.endIpaddr = (String) hashMap.get("END_IPADDR");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.ipName = (String) hashMap.get("IP_NAME");
			this.validDate = (String) hashMap.get("VALID_DATE");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.staticState = (String) hashMap.get("STATIC_STATE");
			this.validFlag = (String) hashMap.get("VALID_FLAG");
			this.comments = (String) hashMap.get("COMMENTS");
			this.regionId = (String) hashMap.get("REGION_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("END_IPADDR");
		arrayList.add("IPADDR");
		return arrayList;
	}

	public String getTableName() {
		return "RR_DEPARTMENT_TERMINAL";
	}

}
