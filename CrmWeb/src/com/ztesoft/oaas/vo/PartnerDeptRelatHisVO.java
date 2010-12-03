package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartnerDeptRelatHisVO extends ValueObject implements VO {

	private String operHistId = "";
	private String partnerId = "";
	private String partyId = "";
	private String operId = "";
	private String operTime = "";
	private String operType = "";

	public PartnerDeptRelatHisVO() {}

	public PartnerDeptRelatHisVO( String poperHistId, String ppartnerId, String ppartyId, String poperId, String poperTime, String poperType ) {
		operHistId = poperHistId;
		partnerId = ppartnerId;
		partyId = ppartyId;
		operId = poperId;
		operTime = poperTime;
		operType = poperType;
	}

	public String getOperHistId() {
		return operHistId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getOperId() {
		return operId;
	}

	public String getOperTime() {
		return operTime;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperHistId(String pOperHistId) {
		operHistId = pOperHistId;
	}

	public void setPartnerId(String pPartnerId) {
		partnerId = pPartnerId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setOperTime(String pOperTime) {
		operTime = pOperTime;
	}

	public void setOperType(String pOperType) {
		operType = pOperType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("OPER_HIST_ID",this.operHistId);
		hashMap.put("PARTNER_ID",this.partnerId);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("OPER_TIME",this.operTime);
		hashMap.put("OPER_TYPE",this.operType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.operHistId = (String) hashMap.get("OPER_HIST_ID");
			this.partnerId = (String) hashMap.get("PARTNER_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.operTime = (String) hashMap.get("OPER_TIME");
			this.operType = (String) hashMap.get("OPER_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTNER_ID");
		arrayList.add("PARTY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER_DEPT_RELAT_HIS";
	}

}
