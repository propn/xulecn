package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcFamilyVO extends ValueObject implements VO {

	private String familyId = "";
	private String familyName = "";
	private String comments = "";
	private String codeMinLen = "";
	private String codeMaxLen = "";
	private String rescType = "";
	private String state = "";
	private String stateDate = "";
	private String createDate = "";
	private String effDate = "";
	private String expDate = "";
	
	/** ÖÕ¶Ë¼Ò×åµÄid **/
	public static final String TerminalFamily = "104";

	public RcFamilyVO() {}

	public RcFamilyVO( String pfamilyId, String pfamilyName, String pcomments, String pcodeMinLen, String pcodeMaxLen, String prescType, String pstate, String pstateDate, String pcreateDate, String peffDate, String pexpDate ) {
		familyId = pfamilyId;
		familyName = pfamilyName;
		comments = pcomments;
		codeMinLen = pcodeMinLen;
		codeMaxLen = pcodeMaxLen;
		rescType = prescType;
		state = pstate;
		stateDate = pstateDate;
		createDate = pcreateDate;
		effDate = peffDate;
		expDate = pexpDate;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getComments() {
		return comments;
	}

	public String getCodeMinLen() {
		return codeMinLen;
	}

	public String getCodeMaxLen() {
		return codeMaxLen;
	}

	public String getRescType() {
		return rescType;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setFamilyName(String pFamilyName) {
		familyName = pFamilyName;
	}

	public void setComments(String pComments) {
		comments = pComments;
	}

	public void setCodeMinLen(String pCodeMinLen) {
		codeMinLen = pCodeMinLen;
	}

	public void setCodeMaxLen(String pCodeMaxLen) {
		codeMaxLen = pCodeMaxLen;
	}

	public void setRescType(String pRescType) {
		rescType = pRescType;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("FAMILY_NAME",this.familyName);
		hashMap.put("COMMENTS",this.comments);
		hashMap.put("CODE_MIN_LEN",this.codeMinLen);
		hashMap.put("CODE_MAX_LEN",this.codeMaxLen);
		hashMap.put("RESOURCE_TYPE",this.rescType);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.familyName = (String) hashMap.get("FAMILY_NAME");
			this.comments = (String) hashMap.get("COMMENTS");
			this.codeMinLen = (String) hashMap.get("CODE_MIN_LEN");
			this.codeMaxLen = (String) hashMap.get("CODE_MAX_LEN");
			this.rescType = (String) hashMap.get("RESOURCE_TYPE");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_FAMILY";
	}

}
