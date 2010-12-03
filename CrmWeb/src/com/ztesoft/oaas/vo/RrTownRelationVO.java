package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RrTownRelationVO extends ValueObject implements VO {

	private String exchId = "";
	private String townId = "";

	public RrTownRelationVO() {}

	public RrTownRelationVO( String pexchId, String ptownId ) {
		exchId = pexchId;
		townId = ptownId;
	}

	public String getExchId() {
		return exchId;
	}

	public String getTownId() {
		return townId;
	}

	public void setExchId(String pExchId) {
		exchId = pExchId;
	}

	public void setTownId(String pTownId) {
		townId = pTownId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("EXCH_ID",this.exchId);
		hashMap.put("TOWN_ID",this.townId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.exchId = (String) hashMap.get("EXCH_ID");
			this.townId = (String) hashMap.get("TOWN_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("EXCH_ID");
		arrayList.add("TOWN_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RR_TOWN_RELATION";
	}

}
