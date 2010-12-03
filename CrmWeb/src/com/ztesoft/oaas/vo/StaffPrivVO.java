package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffPrivVO extends ValueObject implements VO,XMLItem {

	private String staffPrivId = "";
	private String partyRoleId = "";
	private String privId = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	
	private String regionId = "" ;
	private String regionName = "";
	private String regionType = "";
    
    private String privName = "";
    private String privType = "";
    
    private String pathCode = "";

	public StaffPrivVO() {}

	public StaffPrivVO( String pstaffPrivId, String ppartyRoleId, String pprivId, String pstate, String peffDate, String pexpDate,String pregionId,String pregionName,String pregionType ) {
		staffPrivId = pstaffPrivId;
		partyRoleId = ppartyRoleId;
		privId = pprivId;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		regionId = pregionId ;
		regionName = pregionName;
		regionType = pregionType ;
	}

	public String getStaffPrivId() {
		return staffPrivId;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getPrivId() {
		return privId;
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

	public void setStaffPrivId(String pStaffPrivId) {
		staffPrivId = pStaffPrivId;
	}

	public void setPartyRoleId(String pPartyRoleId) {
		partyRoleId = pPartyRoleId;
	}

	public void setPrivId(String pPrivId) {
		privId = pPrivId;
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

    public String getPrivName()
    {
        return privName;
    }
    
    public void setPrivName(String pPrivName)
    {
        privName = pPrivName;
    }
    
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STAFF_PRIVILEGE_ID",this.staffPrivId);
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("PRIVILEGE_ID",this.privId);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("REGION_ID", this.regionId );
		hashMap.put("REGION_NAME", this.regionName);
		hashMap.put("REGION_TYPE",this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.staffPrivId = (String) hashMap.get("STAFF_PRIVILEGE_ID");
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.privId = (String) hashMap.get("PRIVILEGE_ID");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.regionId = (String)hashMap.get("REGION_ID");
			this.regionName = (String)hashMap.get("REGION_NAME");
			this.regionType = (String)hashMap.get("REGION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("STAFF_PRIVILEGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF_PRIVILEGE";
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getPrivType() {
		return privType;
	}

	public void setPrivType(String privType) {
		this.privType = privType;
	}
	
	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("staffPrivId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.staffPrivId));
        sbXml.append("' ");
        sbXml.append("partyRoleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.partyRoleId));
        sbXml.append("' ");
        sbXml.append("privId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privId));
        sbXml.append("' ");
        sbXml.append("state='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.state));
        sbXml.append("' ");
        sbXml.append("effDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.effDate));
        sbXml.append("' ");
        sbXml.append("expDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.expDate));
        sbXml.append("' ");
        sbXml.append("regionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionId));
        sbXml.append("' ");
        sbXml.append("regionName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionName));
        sbXml.append("' ");
        sbXml.append("regionType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionType));
        sbXml.append("' ");
        sbXml.append("privName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privName));
        sbXml.append("' ");
        sbXml.append("privType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privType));
        sbXml.append("' ");
        sbXml.append("pathCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }

	public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}
    public String pathInTree() {
        return pathCode==null?"":pathCode;
    }
}
