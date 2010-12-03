/**
 * 
 */
package com.ztesoft.oaas.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RegionOrgRelaVO extends ValueObject implements VO {

	private String regionId = "";
	private String partyId = "";

	public RegionOrgRelaVO() {}

	public RegionOrgRelaVO( String pregionId, String ppartyId ) {
		regionId = pregionId;
		partyId = ppartyId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("PARTY_ID",this.partyId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.regionId = (String) hashMap.get("REGION_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("REGION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "REGION_ORG_RELA";
	}

}

