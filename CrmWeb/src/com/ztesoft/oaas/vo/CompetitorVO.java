package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class CompetitorVO extends ValueObject implements VO {

	private String partyRoleId = "";
	private String competitorCode = "";
	private String competitorDesc = "";

	public CompetitorVO() {}

	public CompetitorVO( String ppartyRoleId, String pcompetitorCode, String pcompetitorDesc ) {
		partyRoleId = ppartyRoleId;
		competitorCode = pcompetitorCode;
		competitorDesc = pcompetitorDesc;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getCompetitorCode() {
		return competitorCode;
	}

	public String getCompetitorDesc() {
		return competitorDesc;
	}

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setCompetitorCode(String pCompetitorCode) {
		competitorCode = pCompetitorCode;
	}

	public void setCompetitorDesc(String pCompetitorDesc) {
		competitorDesc = pCompetitorDesc;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("COMPETITOR_CODE",this.competitorCode);
		hashMap.put("COMPETITOR_DESC",this.competitorDesc);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.competitorCode = (String) hashMap.get("COMPETITOR_CODE");
			this.competitorDesc = (String) hashMap.get("COMPETITOR_DESC");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "COMPETITOR";
	}

}
