package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class MpOperDepartVO extends ValueObject implements VO {

	private String operId = "";
	private String lanId = "";
	private String regionId = "";
	private String departId = "";

	public MpOperDepartVO() {}

	public MpOperDepartVO( String poperId, String planId, String pregionId, String pdepartId ) {
		operId = poperId;
		lanId = planId;
		regionId = pregionId;
		departId = pdepartId;
	}

	public String getOperId() {
		return operId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("OPER_ID",this.operId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("DEPART_ID",this.departId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.operId = (String) hashMap.get("OPER_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.departId = (String) hashMap.get("DEPART_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "MP_OPERATOR_DEPART";
	}

}
