package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class IndividualVO extends ValueObject implements VO {

	private String partyId = "";
	private String gender = "";
	private String birthPlace = "";
	private String birthDate = "";
	private String maritalStatus = "";
	private String skill = "";

	public IndividualVO() {}

	public IndividualVO( String ppartyId, String pgender, String pbirthPlace, String pbirthDate, String pmaritalStatus, String pskill ) {
		partyId = ppartyId;
		gender = pgender;
		birthPlace = pbirthPlace;
		birthDate = pbirthDate;
		maritalStatus = pmaritalStatus;
		skill = pskill;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getGender() {
		return gender;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getSkill() {
		return skill;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setGender(String pGender) {
		gender = pGender;
	}

	public void setBirthPlace(String pBirthPlace) {
		birthPlace = pBirthPlace;
	}

	public void setBirthDate(String pBirthDate) {
		birthDate = pBirthDate;
	}

	public void setMaritalStatus(String pMaritalStatus) {
		maritalStatus = pMaritalStatus;
	}

	public void setSkill(String pSkill) {
		skill = pSkill;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("GENDER",this.gender);
		hashMap.put("BIRTH_PLACE",this.birthPlace);
		hashMap.put("BIRTH_DATE",this.birthDate);
		hashMap.put("MARITAL_STATUS",this.maritalStatus);
		hashMap.put("SKILL",this.skill);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.gender = (String) hashMap.get("GENDER");
			this.birthPlace = (String) hashMap.get("BIRTH_PLACE");
			this.birthDate = (String) hashMap.get("BIRTH_DATE");
			this.maritalStatus = (String) hashMap.get("MARITAL_STATUS");
			this.skill = (String) hashMap.get("SKILL");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "INDIVIDUAL";
	}

}
