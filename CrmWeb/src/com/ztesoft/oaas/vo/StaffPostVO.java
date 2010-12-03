package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffPostVO extends ValueObject implements VO {

	private String partyRoleId = "";
	private String orgPostId = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
    
    private String orgPostName = "";
    private String positionName="";

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public StaffPostVO() {}

	public StaffPostVO( String ppartyRoleId, String porgPostId, String pstate, String peffDate, String pexpDate ) {
		partyRoleId = ppartyRoleId;
		orgPostId = porgPostId;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getOrgPostId() {
		return orgPostId;
	}

	public String getState() {
		return state;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setOrgPostId(String pOrgPostId) {
		orgPostId = pOrgPostId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

    public String getOrgPostName()
    {
        return orgPostName;
    }
    
    public void setOrgPostName(String pOrgPostName)
    {
        orgPostName = pOrgPostName;
    }
    
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("ORG_POST_ID",this.orgPostId);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.orgPostId = (String) hashMap.get("ORG_POST_ID");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORG_POST_ID");
		arrayList.add("PARTY_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF_POST";
	}

}
