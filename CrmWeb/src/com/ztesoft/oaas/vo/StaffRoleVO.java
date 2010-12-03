package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffRoleVO extends ValueObject implements VO {

	private String partyRoleId = "";
	private String roleId = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";

	private String regionId = "";
	private String regionName = "";
	private String regionType = "" ;
	
    private String roleName = "";
    private String roleDesc = "";
    
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public StaffRoleVO() {}

	public StaffRoleVO( String ppartyRoleId, String proleId, String pstate, String peffDate, String pexpDate,String pregionId,String pregionType ) {
		partyRoleId = ppartyRoleId;
		roleId = proleId;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		regionId = pregionId ;
		regionType = pregionType ;
	}

	public String getPartyRoleId() {
		return partyRoleId;
	}

	public String getRoleId() {
		return roleId;
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

	public void setRoleId(String pRoleId) {
		roleId = pRoleId;
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

    public String getRoleName()
    {
        return roleName;
    }
    
    public void setRoleName(String pRoleName)
    {
        roleName = pRoleName;
    }
    
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ROLE_ID",this.partyRoleId);
		hashMap.put("ROLE_ID",this.roleId);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("REGION_ID", this.regionId);
		hashMap.put("REGION_TYPE", this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID");
			this.roleId = (String) hashMap.get("ROLE_ID");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.regionId = (String)hashMap.get("REGION_ID");
			this.regionType = (String)hashMap.get("REGION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("STAFF_ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF_ROLE";
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String pathInTree() {
        return null;
    }
    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("partyRoleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.partyRoleId));
        sbXml.append("' ");
        sbXml.append("roleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleId));
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
        sbXml.append("roleDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleDesc));
        sbXml.append("' ");
    	sbXml.append("roleName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleName));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }
}
