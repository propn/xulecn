package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartnerVO extends ValueObject implements VO {

	private String partyRoleId = "";
	private String partnerCode = "";
	private String partnerType = "";
	private String partnerDesc = "";

	public PartnerVO() {}

	public PartnerVO( String ppartyRoleId, String ppartnerCode, String ppartnerType, String ppartnerDesc ) {
		partyRoleId = ppartyRoleId;
		partnerCode = ppartnerCode;
		partnerType = ppartnerType;
		partnerDesc = ppartnerDesc;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public String getPartnerType() {
		return partnerType;
	}

	public String getPartnerDesc() {
		return partnerDesc;
	}

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setPartnerCode(String pPartnerCode) {
		partnerCode = pPartnerCode;
	}

	public void setPartnerType(String pPartnerType) {
		partnerType = pPartnerType;
	}

	public void setPartnerDesc(String pPartnerDesc) {
		partnerDesc = pPartnerDesc;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("PARTNER_CODE",this.partnerCode);
		hashMap.put("PARTNER_TYPE",this.partnerType);
		hashMap.put("PARTNER_DESC",this.partnerDesc);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.partnerCode = (String) hashMap.get("PARTNER_CODE");
			this.partnerType = (String) hashMap.get("PARTNER_TYPE");
			this.partnerDesc = (String) hashMap.get("PARTNER_DESC");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER";
	}

}
