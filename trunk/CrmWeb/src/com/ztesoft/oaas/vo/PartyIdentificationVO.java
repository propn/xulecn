package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartyIdentificationVO extends ValueObject implements VO {

	private String identId = "";
	private String partyId = "";
	private String socialIdType = "";
	private String socialId = "";
	private String createdDate = "";
	private String effDate = "";
	private String expDate = "";
	private String partyRoleId = "" ;
	
	public String getPartyRoleId() {
		return partyRoleId;
	}

	public void setPartyRoleId(String partyRoleId) {
		this.partyRoleId = partyRoleId;
	}

	public PartyIdentificationVO() {}

	public PartyIdentificationVO( String pidentId, String ppartyId, String psocialIdType, String psocialId, String pcreatedDate, String peffDate, String pexpDate ) {
		identId = pidentId;
		partyId = ppartyId;
		socialIdType = psocialIdType;
		socialId = psocialId;
		createdDate = pcreatedDate;
		effDate = peffDate;
		expDate = pexpDate;
	}

	public String getIdentId() {
		return identId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getSocialIdType() {
		return socialIdType;
	}

	public String getSocialId() {
		return socialId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setIdentId(String pIdentId) {
		identId = pIdentId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setSocialIdType(String pSocialIdType) {
		socialIdType = pSocialIdType;
	}

	public void setSocialId(String pSocialId) {
		socialId = pSocialId;
	}

	public void setCreatedDate(String pCreatedDate) {
		createdDate = pCreatedDate;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("IDENT_ID",this.identId);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("SOCIAL_ID_TYPE",this.socialIdType);
		hashMap.put("SOCIAL_ID",this.socialId);
		hashMap.put("CREATED_DATE",this.createdDate);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.identId = (String) hashMap.get("IDENT_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.socialIdType = (String) hashMap.get("SOCIAL_ID_TYPE");
			this.socialId = (String) hashMap.get("SOCIAL_ID");
			this.createdDate = (String) hashMap.get("CREATED_DATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("IDENT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTY_IDENTIFICATION";
	}

}
