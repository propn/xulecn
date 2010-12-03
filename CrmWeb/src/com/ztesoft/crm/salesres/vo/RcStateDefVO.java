package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcStateDefVO extends ValueObject implements VO {

	private String stateName = "";
	private String familyId = "";
	private String state = "";

	public RcStateDefVO() {}

	public RcStateDefVO( String pfamilyId, String pstate ) {
		familyId = pfamilyId;
		state = pstate;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getState() {
		return state;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("STATE",this.state);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.state = (String) hashMap.get("STATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_STATE_DEF";
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
