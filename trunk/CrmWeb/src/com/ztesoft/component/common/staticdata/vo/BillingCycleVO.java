package com.ztesoft.component.common.staticdata.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class BillingCycleVO extends ValueObject implements VO {

	private String billingCycleId = "";
	private String lastBillingCycleId = "";
	private String cycleBeginDate = "";
	private String cycleEndDate = "";
	private String dueDate = "";
	private String blockDate = "";
	private String state = "";
	private String stateDate = "";

	public BillingCycleVO() {}

	public BillingCycleVO( String pbillingCycleId, String plastBillingCycleId, String pcycleBeginDate, String pcycleEndDate, String pdueDate, String pblockDate, String pstate, String pstateDate ) {
		billingCycleId = pbillingCycleId;
		lastBillingCycleId = plastBillingCycleId;
		cycleBeginDate = pcycleBeginDate;
		cycleEndDate = pcycleEndDate;
		dueDate = pdueDate;
		blockDate = pblockDate;
		state = pstate;
		stateDate = pstateDate;
	}

	public String getBillingCycleId() {
		return billingCycleId;
	}

	public String getLastBillingCycleId() {
		return lastBillingCycleId;
	}

	public String getCycleBeginDate() {
		return cycleBeginDate;
	}

	public String getCycleEndDate() {
		return cycleEndDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public String getBlockDate() {
		return blockDate;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setBillingCycleId(String pBillingCycleId) {
		billingCycleId = pBillingCycleId;
	}

	public void setLastBillingCycleId(String pLastBillingCycleId) {
		lastBillingCycleId = pLastBillingCycleId;
	}

	public void setCycleBeginDate(String pCycleBeginDate) {
		cycleBeginDate = pCycleBeginDate;
	}

	public void setCycleEndDate(String pCycleEndDate) {
		cycleEndDate = pCycleEndDate;
	}

	public void setDueDate(String pDueDate) {
		dueDate = pDueDate;
	}

	public void setBlockDate(String pBlockDate) {
		blockDate = pBlockDate;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("BILLING_CYCLE_ID",this.billingCycleId);
		hashMap.put("LAST_BILLING_CYCLE_ID",this.lastBillingCycleId);
		hashMap.put("CYCLE_BEGIN_DATE",this.cycleBeginDate);
		hashMap.put("CYCLE_END_DATE",this.cycleEndDate);
		hashMap.put("DUE_DATE",this.dueDate);
		hashMap.put("BLOCK_DATE",this.blockDate);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.billingCycleId = (String) hashMap.get("BILLING_CYCLE_ID");
			this.lastBillingCycleId = (String) hashMap.get("LAST_BILLING_CYCLE_ID");
			this.cycleBeginDate = (String) hashMap.get("CYCLE_BEGIN_DATE");
			this.cycleEndDate = (String) hashMap.get("CYCLE_END_DATE");
			this.dueDate = (String) hashMap.get("DUE_DATE");
			this.blockDate = (String) hashMap.get("BLOCK_DATE");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("BILLING_CYCLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "BILLING_CYCLE";
	}

}
