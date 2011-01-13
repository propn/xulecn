/**
 * 
 */
package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

/**
 * @author –Ì»Ò∫¿
 *
 */
public class AgentPropertyVO extends ValueObject implements VO, XMLItem {
	private String partyId = "";
	private String depositAmount = "";
	private String ceilFlag = "";
	private String upperAmount ;
	private String scopeFlag = "";
	private String servGroupId = "";
	private String settledCharge = "";
	private String charge = "";
	private String lastSettleDate = "" ;
	private String state = "";
	private String stateDate = "";
	private String alarmAmount = "";
	/**
	 * @return Returns the alarmAmount.
	 */
	public String getAlarmAmount() {
		return alarmAmount;
	}
	/**
	 * @param alarmAmount The alarmAmount to set.
	 */
	public void setAlarmAmount(String alarmAmount) {
		this.alarmAmount = alarmAmount;
	}
	/**
	 * @return Returns the cellFlag.
	 */
	public String getCeilFlag() {
		return ceilFlag;
	}
	/**
	 * @param cellFlag The cellFlag to set.
	 */
	public void setCeilFlag(String cellFlag) {
		this.ceilFlag = cellFlag;
	}
	/**
	 * @return Returns the charge.
	 */
	public String getCharge() {
		return charge;
	}
	/**
	 * @param charge The charge to set.
	 */
	public void setCharge(String charge) {
		this.charge = charge;
	}
	/**
	 * @return Returns the depositAmount.
	 */
	public String getDepositAmount() {
		return depositAmount;
	}
	/**
	 * @param depositAmount The depositAmount to set.
	 */
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	/**
	 * @return Returns the lastSettleDate.
	 */
	public String getLastSettleDate() {
		return lastSettleDate;
	}
	/**
	 * @param lastSettleDate The lastSettleDate to set.
	 */
	public void setLastSettleDate(String lastSettleDate) {
		this.lastSettleDate = lastSettleDate;
	}
	/**
	 * @return Returns the partyId.
	 */
	public String getPartyId() {
		return partyId;
	}
	/**
	 * @param partyId The partyId to set.
	 */
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	/**
	 * @return Returns the scopyFlag.
	 */
	public String getScopeFlag() {
		return scopeFlag;
	}
	/**
	 * @param scopyFlag The scopyFlag to set.
	 */
	public void setScopeFlag(String scopyFlag) {
		this.scopeFlag = scopyFlag;
	}
	/**
	 * @return Returns the servGroupId.
	 */
	public String getServGroupId() {
		return servGroupId;
	}
	/**
	 * @param servGroupId The servGroupId to set.
	 */
	public void setServGroupId(String servGroupId) {
		this.servGroupId = servGroupId;
	}
	/**
	 * @return Returns the settledCharge.
	 */
	public String getSettledCharge() {
		return settledCharge;
	}
	/**
	 * @param settledCharge The settledCharge to set.
	 */
	public void setSettledCharge(String settledCharge) {
		this.settledCharge = settledCharge;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return Returns the stateDate.
	 */
	public String getStateDate() {
		return stateDate;
	}
	/**
	 * @param stateDate The stateDate to set.
	 */
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}
	/**
	 * @return Returns the upperAmount.
	 */
	public String getUpperAmount() {
		return upperAmount;
	}
	/**
	 * @param upperAmountDecimal The upperAmountDecimal to set.
	 */
	public void setUpperAmount(String upperAmount) {
		this.upperAmount = upperAmount;
	}
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("DEPOSIT_AMOUNT",this.depositAmount);
		hashMap.put("CEIL_FLAG",this.ceilFlag);
		hashMap.put("UPPER_AMOUNT",this.upperAmount);
		hashMap.put("SCOPE_FLAG",this.scopeFlag);
		hashMap.put("SERV_GROUP_ID",this.servGroupId);
		hashMap.put("SETTLED_CHARGE",this.settledCharge);
		hashMap.put("CHARGE",this.charge);
		hashMap.put("LAST_SETTLE_DATE",this.lastSettleDate);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("ALARM_AMOUNT",this.alarmAmount);
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.depositAmount = (String) hashMap.get("DEPOSIT_AMOUNT");
			this.ceilFlag = (String) hashMap.get("CEIL_FLAG");
			this.upperAmount = (String) hashMap.get("UPPER_AMOUNT");
			this.scopeFlag = (String) hashMap.get("SCOPE_FLAG");
			this.servGroupId = (String) hashMap.get("SERV_GROUP_ID");
			this.settledCharge = (String) hashMap.get("SETTLED_CHARGE");
			this.charge = (String) hashMap.get("CHARGE");
			this.lastSettleDate = (String) hashMap.get("LAST_SETTLE_DATE");
			this.state = (String) hashMap.get("STATE");
			this.stateDate= (String) hashMap.get("STATE_DATE");
			this.alarmAmount = (String) hashMap.get("ALARM_AMOUNT");
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ID");
		return arrayList;
	}
	public String getTableName() {
		return "AGENT_PROPERTY";
	}
}
