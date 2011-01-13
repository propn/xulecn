package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RolesVO extends ValueObject implements VO, XMLItem {

	private String roleId = "";
	private String roleName = "";
	private String roleDesc = "";
	private String state = "";
	private String stateDate = "";
	private String abilityGradeNum = "";
	private String regionId = "";
	private String regionName = "";
	private String regionType = "";
	
	private String orgPostRoleId = "";//组织岗位角色标识
	
	//ROLE_NAME_SHORT
	private String roleNameShort = "";

	public String getRoleNameShort() {
		return roleNameShort;
	}

	public void setRoleNameShort(String roleNameShort) {
		this.roleNameShort = roleNameShort;
	}

	public RolesVO() {}

	public RolesVO( String proleId, String proleName, String proleDesc, String pstate, String pstateDate, String pabilityGradeNum,String pregionId,String pregionName,String pregionType ) {
		roleId = proleId;
		roleName = proleName;
		roleDesc = proleDesc;
		state = pstate;
		stateDate = pstateDate;
		abilityGradeNum = pabilityGradeNum;
		regionId = pregionId;
		regionName = pregionName;
		regionType = pregionType;
	}

	public String getOrgPostRoleId(){
		return orgPostRoleId ;
	}
	public void setOrgPostRoleId(String orgPostRoleId){
		this.orgPostRoleId = orgPostRoleId ;
	}
	public String getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getAbilityGradeNum() {
		return abilityGradeNum;
	}

	public void setRoleId(String pRoleId) {
		roleId = pRoleId;
	}

	public void setRoleName(String pRoleName) {
		roleName = pRoleName;
	}

	public void setRoleDesc(String pRoleDesc) {
		roleDesc = pRoleDesc;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setAbilityGradeNum(String pAbilityGradeNum) {
		abilityGradeNum = pAbilityGradeNum;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ROLE_ID",this.roleId);
		hashMap.put("ROLE_NAME",this.roleName);
		hashMap.put("ROLE_DESC",this.roleDesc);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("ABILITY_GRADE_NUM",this.abilityGradeNum);
		hashMap.put("ORG_POST_ROLE_ID",this.orgPostRoleId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("REGION_NAME", this.regionName);
		hashMap.put("REGION_TYPE",this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.roleId = (String) hashMap.get("ROLE_ID");
			this.roleName = (String) hashMap.get("ROLE_NAME");
			this.roleDesc = (String) hashMap.get("ROLE_DESC");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.abilityGradeNum = (String) hashMap.get("ABILITY_GRADE_NUM");
			this.orgPostRoleId = (String)hashMap.get("ORG_POST_ROLE_ID");
			this.regionId = (String)hashMap.get("REGION_ID");
			this.regionName = (String)hashMap.get("REGION_NAME");
			this.regionType = (String)hashMap.get("REGION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ROLE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ROLES";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("roleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleId));
        sbXml.append("' ");
        sbXml.append("roleName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleName));
        sbXml.append("' ");
        sbXml.append("roleDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleDesc));
        sbXml.append("' ");
        sbXml.append("state='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.state));
        sbXml.append("' ");
        sbXml.append("stateDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.stateDate));
        sbXml.append("' ");
        sbXml.append("abilityGradeNum='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.abilityGradeNum));
        sbXml.append("' ");
        
        sbXml.append("orgPostRoleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgPostRoleId));
        sbXml.append("' ");
        sbXml.append("roleNameShort='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.roleNameShort));
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
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return null;
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

}
