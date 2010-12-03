package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class OrganizationPostVO extends ValueObject implements VO {

	private String orgPostId = "";
	private String partyId = "";
	private String positionId = "";
	private String state = "";
	private String stateDate = "";
    
    private String positionName = "";

	public OrganizationPostVO() {}

	public OrganizationPostVO( String porgPostId, String ppartyId, String ppositionId, String pstate, String pstateDate ) {
		orgPostId = porgPostId;
		partyId = ppartyId;
		positionId = ppositionId;
		state = pstate;
		stateDate = pstateDate;
	}

	public String getOrgPostId() {
		return orgPostId;
	}

	public String getPartyId() {
		return partyId;
	}

	public String getPositionId() {
		return positionId;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setOrgPostId(String pOrgPostId) {
		orgPostId = pOrgPostId;
	}

	public void setPartyId(String pPartyId) {
		partyId = pPartyId;
	}

	public void setPositionId(String pPositionId) {
		positionId = pPositionId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

    public String getPositionName()
    {
        return positionName;
    }
    
    public void setPositionName(String pPositionName)
    {
        positionName = pPositionName;
    }
    
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORG_POST_ID",this.orgPostId);
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("POSITION_ID",this.positionId);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orgPostId = (String) hashMap.get("ORG_POST_ID");
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.positionId = (String) hashMap.get("POSITION_ID");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORGANIZATION_POST";
	}

}
