package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcLevelDefVO extends ValueObject implements VO {

	private String familyId = "";
	private String rcLevelId = "";
	private String rcLevelName = "";
	private String levelComments = "";
	private String priId = "";
	private String ruleString = "";
	private String preFee = "";
	private String limitFee = "";
	private String lanId = "";
	private String isLucky="";
	
	public String getLimitFee() {
		return limitFee;
	}

	public void setLimitFee(String limitFee) {
		this.limitFee = limitFee;
	}

	public String getPreFee() {
		return preFee;
	}

	public void setPreFee(String preFee) {
		this.preFee = preFee;
	}

	public RcLevelDefVO() {}

	public RcLevelDefVO( String pfamilyId, String prcLevelId, String prcLevelName, String plevelComments, String ppriId, String pruleString ) {
		familyId = pfamilyId;
		rcLevelId = prcLevelId;
		rcLevelName = prcLevelName;
		levelComments = plevelComments;
		priId = ppriId;
		ruleString = pruleString;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getRcLevelId() {
		return rcLevelId;
	}

	public String getRcLevelName() {
		return rcLevelName;
	}

	public String getLevelComments() {
		return levelComments;
	}

	public String getPriId() {
		return priId;
	}

	public String getRuleString() {
		return ruleString;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setRcLevelId(String pRcLevelId) {
		rcLevelId = pRcLevelId;
	}

	public void setRcLevelName(String pRcLevelName) {
		rcLevelName = pRcLevelName;
	}

	public void setLevelComments(String pLevelComments) {
		levelComments = pLevelComments;
	}

	public void setPriId(String pPriId) {
		priId = pPriId;
	}

	public void setRuleString(String pRuleString) {
		ruleString = pRuleString;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("RC_LEVEL_ID",this.rcLevelId);
		hashMap.put("RC_LEVEL_NAME",this.rcLevelName);
		hashMap.put("LEVEL_COMMENTS",this.levelComments);
		hashMap.put("PRI_ID",this.priId);
		hashMap.put("RULE_STRING",this.ruleString);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.rcLevelId = (String) hashMap.get("RC_LEVEL_ID");
			this.rcLevelName = (String) hashMap.get("RC_LEVEL_NAME");
			this.levelComments = (String) hashMap.get("LEVEL_COMMENTS");
			this.priId = (String) hashMap.get("PRI_ID");
			this.ruleString = (String) hashMap.get("RULE_STRING");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("FAMILY_ID");
		arrayList.add("RC_LEVEL_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_LEVEL_DEF";
	}

	public String getLanId() {
		return lanId;
	}

	public void setLanId(String lanId) {
		this.lanId = lanId;
	}

	public String getIsLucky() {
		return isLucky;
	}

	public void setIsLucky(String isLucky) {
		this.isLucky = isLucky;
	}

}
