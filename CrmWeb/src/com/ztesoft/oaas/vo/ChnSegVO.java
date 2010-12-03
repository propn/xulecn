package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ChnSegVO extends ValueObject implements VO {

	private String chnSegId = "";
	private String cooperantPartId = "";
	private String chnSegType = "";
	private String chnSegName = "";
	private String chnSegDeta = "";

	public ChnSegVO() {}

	public ChnSegVO( String pchnSegId, String pcooperantPartId, String pchnSegType, String pchnSegName, String pchnSegDeta ) {
		chnSegId = pchnSegId;
		cooperantPartId = pcooperantPartId;
		chnSegType = pchnSegType;
		chnSegName = pchnSegName;
		chnSegDeta = pchnSegDeta;
	}

	public String getChnSegId() {
		return chnSegId;
	}

	public String getCooperantPartId() {
		return cooperantPartId;
	}

	public String getChnSegType() {
		return chnSegType;
	}

	public String getChnSegName() {
		return chnSegName;
	}

	public String getChnSegDeta() {
		return chnSegDeta;
	}

	public void setChnSegId(String pChnSegId) {
		chnSegId = pChnSegId;
	}

	public void setCooperantPartId(String pCooperantPartId) {
		cooperantPartId = pCooperantPartId;
	}

	public void setChnSegType(String pChnSegType) {
		chnSegType = pChnSegType;
	}

	public void setChnSegName(String pChnSegName) {
		chnSegName = pChnSegName;
	}

	public void setChnSegDeta(String pChnSegDeta) {
		chnSegDeta = pChnSegDeta;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CHANNEL_SEGMENT_ID",this.chnSegId);
		hashMap.put("COOPERANT_PART_ID",this.cooperantPartId);
		hashMap.put("CHANNEL_SEGMENT_TYPE",this.chnSegType);
		hashMap.put("CHANNEL_SEGMENT_NAME",this.chnSegName);
		hashMap.put("CHANNEL_SEGMENT_DETAIL",this.chnSegDeta);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.chnSegId = (String) hashMap.get("CHANNEL_SEGMENT_ID");
			this.cooperantPartId = (String) hashMap.get("COOPERANT_PART_ID");
			this.chnSegType = (String) hashMap.get("CHANNEL_SEGMENT_TYPE");
			this.chnSegName = (String) hashMap.get("CHANNEL_SEGMENT_NAME");
			this.chnSegDeta = (String) hashMap.get("CHANNEL_SEGMENT_DETAIL");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CHANNEL_SEGMENT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CHANNEL_SEGMENT";
	}

}
