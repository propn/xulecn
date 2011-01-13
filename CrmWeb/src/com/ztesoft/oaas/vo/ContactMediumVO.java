package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ContactMediumVO extends ValueObject implements VO {

	private String contactMediumId = "";
	private String addrId = "";
	private String partyId = "";

	public ContactMediumVO() {}

	public ContactMediumVO( String pcontactMediumId, String paddrId, String ppartyId ) {
		contactMediumId = pcontactMediumId;
		addrId = paddrId;
		partyId = ppartyId;
	}

	public String getContactMediumId() {
		return contactMediumId;
	}

	public String getAddrId() {
		return addrId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setContactMediumId(String pContactMediumId) {
		contactMediumId = pContactMediumId;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CONTACT_MEDIUM_ID",this.contactMediumId);
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("PARTY_ID",this.partyId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.contactMediumId = (String) hashMap.get("CONTACT_MEDIUM_ID");
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CONTACT_MEDIUM_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CONTACT_MEDIUM";
	}

}
