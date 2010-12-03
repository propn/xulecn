package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderStateVO extends ValueObject implements VO {

	private String stateId = "";
	private String stateName = "";
	private String stateType = "";

	public RcOrderStateVO() {}

	public RcOrderStateVO( String pstateId, String pstateName, String pstateType ) {
		stateId = pstateId;
		stateName = pstateName;
		stateType = pstateType;
	}

	public String getStateId() {
		return stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public String getStateType() {
		return stateType;
	}

	public void setStateId(String pStateId) {
		stateId = pStateId;
	}

	public void setStateName(String pStateName) {
		stateName = pStateName;
	}

	public void setStateType(String pStateType) {
		stateType = pStateType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STATE_ID",this.stateId);
		hashMap.put("STATE_NAME",this.stateName);
		hashMap.put("STATE_TYPE",this.stateType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.stateId = (String) hashMap.get("STATE_ID");
			this.stateName = (String) hashMap.get("STATE_NAME");
			this.stateType = (String) hashMap.get("STATE_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_ORDER_STATE";
	}

}
