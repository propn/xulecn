package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RaAddrVO extends ValueObject implements VO {

	private String addrId = "";
	private String roadId = "";
	private String deta = "";
	private String raRoadId = "";

	public RaAddrVO() {}

	public RaAddrVO( String paddrId, String proadId, String pdeta, String praRoadId ) {
		addrId = paddrId;
		roadId = proadId;
		deta = pdeta;
		raRoadId = praRoadId;
	}

	public String getAddrId() {
		return addrId;
	}

	public String getRoadId() {
		return roadId;
	}

	public String getDeta() {
		return deta;
	}

	public String getRaRoadId() {
		return raRoadId;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public void setRoadId(String pRoadId) {
		roadId = pRoadId;
	}

	public void setDeta(String pDeta) {
		deta = pDeta;
	}

	public void setRaRoadId(String pRaRoadId) {
		raRoadId = pRaRoadId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("ROAD_ID",this.roadId);
		hashMap.put("DETAIL",this.deta);
		hashMap.put("RA_ROAD_ID",this.raRoadId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.roadId = (String) hashMap.get("ROAD_ID");
			this.deta = (String) hashMap.get("DETAIL");
			this.raRoadId = (String) hashMap.get("RA_ROAD_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ADDRESS_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RA_ADDRESS";
	}

}
