package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class StDepartAreaVO extends ValueObject implements VO {

	private String departId = "";
	private String areaId = "";
    private String departName = "";
    private String departCode = "";

	public StDepartAreaVO() {}

	public StDepartAreaVO( String pdepartId, String pareaId ) {
		departId = pdepartId;
		areaId = pareaId;
	}

	public String getDepartId() {
		return departId;
	}

	public String getAreaId() {
		return areaId;
	}

    public String getDepartName() {
        return departName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartId(String pDepartId) {
		departId = pDepartId;
	}

	public void setAreaId(String pAreaId) {
		areaId = pAreaId;
	}

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("DEPART_ID",this.departId);
		hashMap.put("AREA_ID",this.areaId);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.departId = (String) hashMap.get("DEPART_ID");
			this.areaId = (String) hashMap.get("AREA_ID");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DEPART_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ST_DEPART_AREA";
	}

}
