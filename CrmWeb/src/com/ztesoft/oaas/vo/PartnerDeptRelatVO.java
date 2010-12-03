package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartnerDeptRelatVO extends ValueObject implements VO {

	private String partyId = "";
	private String partnerId = "";
	private String partyName = "";
	private String partyCode = "";
	
	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public PartnerDeptRelatVO() {}

	public PartnerDeptRelatVO( String ppartyId, String ppartnerId ) {
		partyId = ppartyId;
		partnerId = ppartnerId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setPartnerId(String pPartnerId) {
		partnerId = pPartnerId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("PARTNER_ID",this.partnerId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.partnerId = (String) hashMap.get("PARTNER_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER_DEPT_RELAT";
	}

}

