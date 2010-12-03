package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcStateVO extends ValueObject implements VO {

	private String state = "";
	private String stateName = "";

	public RcStateVO() {}

	public RcStateVO( String pstate, String pstateName ) {
		state = pstate;
		stateName = pstateName;
	}

	public String getState() {
		return state;
	}

	public String getStateName() {
		return stateName;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateName(String pStateName) {
		stateName = pStateName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_NAME",this.stateName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.state = (String) hashMap.get("STATE");
			this.stateName = (String) hashMap.get("STATE_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_STATE";
	}

}
