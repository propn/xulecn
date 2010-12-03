package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class OrganizationVO extends ValueObject implements VO {

	private String partyId = "";
	private String parentPartyId = "";
	private String orgCode = "";
	private String orgName = "";
	private String orgLevel = "";
	private String orgContent = "";
	private String addrId = "";
	private String state = "";
	private String stateDate = "";
	private String orgClass = "";
	private String orgTypeId = "";
	private String pathName = "";
	private String pathCode = "";

	public OrganizationVO() {}

	public OrganizationVO( String ppartyId, String pparentPartyId, String porgCode, String porgName, String porgLevel, String porgContent, String paddrId, String pstate, String pstateDate, String porgClass, String porgTypeId, String ppathName, String ppathCode ) {
		partyId = ppartyId;
		parentPartyId = pparentPartyId;
		orgCode = porgCode;
		orgName = porgName;
		orgLevel = porgLevel;
		orgContent = porgContent;
		addrId = paddrId;
		state = pstate;
		stateDate = pstateDate;
		orgClass = porgClass;
		orgTypeId = porgTypeId;
		pathName = ppathName;
		pathCode = ppathCode;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getParentPartyId() {
		return parentPartyId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public String getOrgContent() {
		return orgContent;
	}

	public String getAddrId() {
		return addrId;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getOrgClass() {
		return orgClass;
	}

	public String getOrgTypeId() {
		return orgTypeId;
	}

	public String getPathName() {
		return pathName;
	}

	public String getPathCode() {
		return pathCode;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setParentPartyId(String pParentPartyId) {
		parentPartyId = pParentPartyId;
	}

	public void setOrgCode(String pOrgCode) {
		orgCode = pOrgCode;
	}

	public void setOrgName(String pOrgName) {
		orgName = pOrgName;
	}

	public void setOrgLevel(String pOrgLevel) {
		orgLevel = pOrgLevel;
	}

	public void setOrgContent(String pOrgContent) {
		orgContent = pOrgContent;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setOrgClass(String pOrgClass) {
		orgClass = pOrgClass;
	}

	public void setOrgTypeId(String pOrgTypeId) {
		orgTypeId = pOrgTypeId;
	}

	public void setPathName(String pPathName) {
		pathName = pPathName;
	}

	public void setPathCode(String pPathCode) {
		pathCode = pPathCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("PARENT_PARTY_ID",this.parentPartyId);
		hashMap.put("ORG_CODE",this.orgCode);
		hashMap.put("ORG_NAME",this.orgName);
		hashMap.put("ORG_LEVEL",this.orgLevel);
		hashMap.put("ORG_CONTENT",this.orgContent);
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("ORG_CLASS",this.orgClass);
		hashMap.put("ORG_TYPE_ID",this.orgTypeId);
		hashMap.put("PATH_NAME",this.pathName);
		hashMap.put("PATH_CODE",this.pathCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.parentPartyId = (String) hashMap.get("PARENT_PARTY_ID");
			this.orgCode = (String) hashMap.get("ORG_CODE");
			this.orgName = (String) hashMap.get("ORG_NAME");
			this.orgLevel = (String) hashMap.get("ORG_LEVEL");
			this.orgContent = (String) hashMap.get("ORG_CONTENT");
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.orgClass = (String) hashMap.get("ORG_CLASS");
			this.orgTypeId = (String) hashMap.get("ORG_TYPE_ID");
			this.pathName = (String) hashMap.get("PATH_NAME");
			this.pathCode = (String) hashMap.get("PATH_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ORGANIZATION";
	}

}
