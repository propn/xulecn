package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class OrgPostRoleScopeVO extends ValueObject implements VO, XMLItem {

	private String orgPostRoleScopeId = "";
	private String orgPostRoleId = "";
	private String regionId = "";
	private String regionType = "";

	public OrgPostRoleScopeVO() {}

	public OrgPostRoleScopeVO( String porgPostRoleScopeId, String porgPostRoleId, String pregionId, String pregionType ) {
		orgPostRoleScopeId = porgPostRoleScopeId;
		orgPostRoleId = porgPostRoleId;
		regionId = pregionId;
		regionType = pregionType;
	}

	public String getOrgPostRoleScopeId() {
		return orgPostRoleScopeId;
	}

	public String getOrgPostRoleId() {
		return orgPostRoleId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getRegionType() {
		return regionType;
	}

	public void setOrgPostRoleScopeId(String pOrgPostRoleScopeId) {
		orgPostRoleScopeId = pOrgPostRoleScopeId;
	}

	public void setOrgPostRoleId(String pOrgPostRoleId) {
		orgPostRoleId = pOrgPostRoleId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setRegionType(String pRegionType) {
		regionType = pRegionType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORG_POST_ROLE_SCOPE_ID",this.orgPostRoleScopeId);
		hashMap.put("ORG_POST_ROLE_ID",this.orgPostRoleId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("REGION_TYPE",this.regionType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orgPostRoleScopeId = (String) hashMap.get("ORG_POST_ROLE_SCOPE_ID");
			this.orgPostRoleId = (String) hashMap.get("ORG_POST_ROLE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.regionType = (String) hashMap.get("REGION_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORG_POST_ROLE_SCOPE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ORG_POST_ROLE_SCOPE";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("orgPostRoleScopeId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgPostRoleScopeId));
        sbXml.append("' ");
        sbXml.append("orgPostRoleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgPostRoleId));
        sbXml.append("' ");
        sbXml.append("regionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionId));
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

}
