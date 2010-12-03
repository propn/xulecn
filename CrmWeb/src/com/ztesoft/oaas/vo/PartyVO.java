package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartyVO extends ValueObject implements VO {

	private String partyId = "";
	private String partyName = "";
	private String effDate = "";
	private String state = "";
	private String stateDate = "";
	private String addrId = "";
	private String addDescription = "";

	public PartyVO() {}

	public PartyVO( String ppartyId, String ppartyName, String peffDate, String pstate, String pstateDate, String paddrId, String paddDescription ) {
		partyId = ppartyId;
		partyName = ppartyName;
		effDate = peffDate;
		state = pstate;
		stateDate = pstateDate;
		addrId = paddrId;
		addDescription = paddDescription;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getAddrId() {
		return addrId;
	}

	public String getAddDescription() {
		return addDescription;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setPartyName(String pPartyName) {
		partyName = pPartyName;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public void setAddDescription(String pAddDescription) {
		addDescription = pAddDescription;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("PARTY_NAME",this.partyName);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("ADD_DESCRIPTION",this.addDescription);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.partyName = (String) hashMap.get("PARTY_NAME");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.addDescription = (String) hashMap.get("ADD_DESCRIPTION");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTY";
	}

}
