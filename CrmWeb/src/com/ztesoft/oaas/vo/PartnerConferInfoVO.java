package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PartnerConferInfoVO extends ValueObject implements VO {

	private String partnerConfId = "";
	private String partnerId = "";
	private String conferCode = "";
	private String partnerConferType = "";
	private String signDate = "";
	private String expDate = "";
	private String cooperateType = "";
	private String conferContence = "";
	private String balnsRule = "";
	private String partnerDroitDuty = "";
	private String droitDuty = "";
	private String abortCondiction = "";
	private String breakDuty = "";
	private String state = "";
	private String operId = "";
	private String operTime = "";

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public PartnerConferInfoVO() {}

	public PartnerConferInfoVO( String ppartnerConfId, String ppartnerId, String pconferCode, String ppartnerConferType, String psignDate, String pexpDate, String pcooperateType, String pconferContence, String pbalnsRule, String ppartnerDroitDuty, String pdroitDuty, String pabortCondiction, String pbreakDuty, String pstate ) {
		partnerConfId = ppartnerConfId;
		partnerId = ppartnerId;
		conferCode = pconferCode;
		partnerConferType = ppartnerConferType;
		signDate = psignDate;
		expDate = pexpDate;
		cooperateType = pcooperateType;
		conferContence = pconferContence;
		balnsRule = pbalnsRule;
		partnerDroitDuty = ppartnerDroitDuty;
		droitDuty = pdroitDuty;
		abortCondiction = pabortCondiction;
		breakDuty = pbreakDuty;
		state = pstate;
	}

	public String getPartnerConfId() {
		return partnerConfId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getConferCode() {
		return conferCode;
	}

	public String getPartnerConferType() {
		return partnerConferType;
	}

	public String getSignDate() {
		return signDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getCooperateType() {
		return cooperateType;
	}

	public String getConferContence() {
		return conferContence;
	}

	public String getBalnsRule() {
		return balnsRule;
	}

	public String getPartnerDroitDuty() {
		return partnerDroitDuty;
	}

	public String getDroitDuty() {
		return droitDuty;
	}

	public String getAbortCondiction() {
		return abortCondiction;
	}

	public String getBreakDuty() {
		return breakDuty;
	}

	public String getState() {
		return state;
	}

	public void setPartnerConfId(String pPartnerConfId) {
		partnerConfId = pPartnerConfId;
	}

	public void setPartnerId(String pPartnerId) {
		partnerId = pPartnerId;
	}

	public void setConferCode(String pConferCode) {
		conferCode = pConferCode;
	}

	public void setPartnerConferType(String pPartnerConferType) {
		partnerConferType = pPartnerConferType;
	}

	public void setSignDate(String pSignDate) {
		signDate = pSignDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setCooperateType(String pCooperateType) {
		cooperateType = pCooperateType;
	}

	public void setConferContence(String pConferContence) {
		conferContence = pConferContence;
	}

	public void setBalnsRule(String pBalnsRule) {
		balnsRule = pBalnsRule;
	}

	public void setPartnerDroitDuty(String pPartnerDroitDuty) {
		partnerDroitDuty = pPartnerDroitDuty;
	}

	public void setDroitDuty(String pDroitDuty) {
		droitDuty = pDroitDuty;
	}

	public void setAbortCondiction(String pAbortCondiction) {
		abortCondiction = pAbortCondiction;
	}

	public void setBreakDuty(String pBreakDuty) {
		breakDuty = pBreakDuty;
	}

	public void setState(String pState) {
		state = pState;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTNER_CONF_ID",this.partnerConfId);
		hashMap.put("PARTNER_ID",this.partnerId);
		hashMap.put("CONFER_CODE",this.conferCode);
		hashMap.put("PARTNER_CONFER_TYPE",this.partnerConferType);
		hashMap.put("SIGN_DATE",this.signDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("COOPERATE_TYPE",this.cooperateType);
		hashMap.put("CONFER_CONTENCE",this.conferContence);
		hashMap.put("BALANCE_RULE",this.balnsRule);
		hashMap.put("PARTNER_DROIT_DUTY",this.partnerDroitDuty);
		hashMap.put("DROIT_DUTY",this.droitDuty);
		hashMap.put("ABORT_CONDICTION",this.abortCondiction);
		hashMap.put("BREAK_DUTY",this.breakDuty);
		hashMap.put("STATE",this.state);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partnerConfId = (String) hashMap.get("PARTNER_CONF_ID");
			this.partnerId = (String) hashMap.get("PARTNER_ID");
			this.conferCode = (String) hashMap.get("CONFER_CODE");
			this.partnerConferType = (String) hashMap.get("PARTNER_CONFER_TYPE");
			this.signDate = (String) hashMap.get("SIGN_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.cooperateType = (String) hashMap.get("COOPERATE_TYPE");
			this.conferContence = (String) hashMap.get("CONFER_CONTENCE");
			this.balnsRule = (String) hashMap.get("BALANCE_RULE");
			this.partnerDroitDuty = (String) hashMap.get("PARTNER_DROIT_DUTY");
			this.droitDuty = (String) hashMap.get("DROIT_DUTY");
			this.abortCondiction = (String) hashMap.get("ABORT_CONDICTION");
			this.breakDuty = (String) hashMap.get("BREAK_DUTY");
			this.state = (String) hashMap.get("STATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTNER_CONF_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PARTNER_CONFER_INFO";
	}

}

