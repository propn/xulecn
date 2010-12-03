package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class AcctItemGrpVO extends ValueObject implements VO {

	private String acctItemGrpId = "";
	private String acctItemGrpName = "";
	private String priority = "";
	private String state = "";
	private String stateDate = "";

	public AcctItemGrpVO() {}

	public AcctItemGrpVO( String pacctItemGrpId, String pacctItemGrpName, String ppriority, String pstate, String pstateDate ) {
		acctItemGrpId = pacctItemGrpId;
		acctItemGrpName = pacctItemGrpName;
		priority = ppriority;
		state = pstate;
		stateDate = pstateDate;
	}

	public String getAcctItemGrpId() {
		return acctItemGrpId;
	}

	public String getAcctItemGrpName() {
		return acctItemGrpName;
	}

	public String getPriority() {
		return priority;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setAcctItemGrpId(String pAcctItemGrpId) {
		acctItemGrpId = pAcctItemGrpId;
	}

	public void setAcctItemGrpName(String pAcctItemGrpName) {
		acctItemGrpName = pAcctItemGrpName;
	}

	public void setPriority(String pPriority) {
		priority = pPriority;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ACCT_ITEM_GROUP_ID",this.acctItemGrpId);
		hashMap.put("ACCT_ITEM_GROUP_NAME",this.acctItemGrpName);
		hashMap.put("PRIORITY",this.priority);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.acctItemGrpId = (String) hashMap.get("ACCT_ITEM_GROUP_ID");
			this.acctItemGrpName = (String) hashMap.get("ACCT_ITEM_GROUP_NAME");
			this.priority = (String) hashMap.get("PRIORITY");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ACCT_ITEM_GROUP_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ACCT_ITEM_GROUP";
	}

}
