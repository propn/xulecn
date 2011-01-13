package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class OrganizationVO extends ValueObject implements VO, XMLItem {

	private String partyId = "";
	private String parentPartyId = "";
	private String orgCode = "";
	private String orgName = "";
	private String orgLevel = "";
	private String orgTypeId = "";//组织类型(部门,班组......) 
	private String orgType = "";//计费组织类型(92B:社会代办点,'92A':电信内部组织)
	private String orgContent = "";
	private String addrId = "";
	private String state = "";
	private String stateDate = "";
	private String pathCode = "";
	private String pathName = "";
	private String orgClass = "";
    private String effDate = ""; 
    private String expDate = "";
    private String addDescription = "";
    private String orgManager = "";
    private String partnerType = "";
    
    private String orgTypeName = "";

    private String privilegeFlag = "";
	public String getPrivilegeFlag() {
		return privilegeFlag;
	}

	public void setPrivilegeFlag(String privilegeFlag) {
		this.privilegeFlag = privilegeFlag;
	}

	public OrganizationVO() {}

	public OrganizationVO( String ppartyId, String pparentPartyId, String porgCode, String porgName, String porgLevel, String porgTypeId, String porgContent, String paddrId, String pstate, String pstateDate, String ppathCode, String ppathName, String porgClass ) {
		partyId = ppartyId;
		parentPartyId = pparentPartyId;
		orgCode = porgCode;
		orgName = porgName;
		orgLevel = porgLevel;
		orgTypeId = porgTypeId;
		orgContent = porgContent;
		addrId = paddrId;
		state = pstate;
		stateDate = pstateDate;
		pathCode = ppathCode;
		pathName = ppathName;
		orgClass = porgClass;
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

	public String getOrgTypeId() {
		return orgTypeId;
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

    public String getPathCode() {
        return pathCode;
    }

	public String getPathName() {
		return pathName;
	}

	public String getOrgClass() {
		return orgClass;
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

	public void setOrgTypeId(String pOrgTypeId) {
		orgTypeId = pOrgTypeId;
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

	public void setPathCode(String pPathCode) {
		pathCode = pPathCode;
	}

	public void setPathName(String pPathName) {
		pathName = pPathName;
	}

	public void setOrgClass(String pOrgClass) {
		orgClass = pOrgClass;
	}

    public String getEffDate()
    {
        return effDate;
    }
    
    public void setEffDate(String pEffDate)
    {
        effDate = pEffDate;
    }
    
    public String getExpDate()
    {
        return expDate;
    }
    
    public void setExpDate(String pExpDate)
    {
        expDate = pExpDate;
    }
    
    public String getAddDescription()
    {
        return addDescription;
    }
    
    public void setAddDescription(String pAddDescription)
    {
        addDescription = pAddDescription;
    }
    
    public String getOrgManager()
    {
        return orgManager;
    }
    
    public void setOrgManager(String pOrgManager)
    {
        orgManager = pOrgManager;
    }
    
    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String pPartnerType) {
        partnerType = pPartnerType;
    }
    
    public String getOrgTypeName()
    {
        return orgTypeName;
    }
    
    public void setOrgTypeName(String pOrgTypeName)
    {
        orgTypeName = pOrgTypeName;
    }

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTY_ID",this.partyId);
		hashMap.put("PARENT_PARTY_ID",this.parentPartyId);
		hashMap.put("ORG_CODE",this.orgCode);
		hashMap.put("ORG_NAME",this.orgName);
		hashMap.put("ORG_LEVEL",this.orgLevel);
		hashMap.put("ORG_TYPE_ID",this.orgTypeId);
		hashMap.put("ORG_CONTENT",this.orgContent);
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("PATH_CODE",this.pathCode);
		hashMap.put("PATH_NAME",this.pathName);
		hashMap.put("ORG_CLASS",this.orgClass);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partyId = (String) hashMap.get("PARTY_ID");
			this.parentPartyId = (String) hashMap.get("PARENT_PARTY_ID");
			this.orgCode = (String) hashMap.get("ORG_CODE");
			this.orgName = (String) hashMap.get("ORG_NAME");
			this.orgLevel = (String) hashMap.get("ORG_LEVEL");
			this.orgTypeId = (String) hashMap.get("ORG_TYPE_ID");
			this.orgContent = (String) hashMap.get("ORG_CONTENT");
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.pathCode = (String) hashMap.get("PATH_CODE");
			this.pathName = (String) hashMap.get("PATH_NAME");
			this.orgClass = (String) hashMap.get("ORG_CLASS");
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

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("partyId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.partyId));
        sbXml.append("' ");
        sbXml.append("parentPartyId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parentPartyId));
        sbXml.append("' ");
        sbXml.append("orgCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgCode));
        sbXml.append("' ");
        sbXml.append("orgName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgName));
        sbXml.append("' ");
        sbXml.append("orgLevel='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgLevel));
        sbXml.append("' ");
        sbXml.append("orgTypeId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgTypeId));
        sbXml.append("' ");
        sbXml.append("orgContent='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgContent));
        sbXml.append("' ");
        sbXml.append("addrId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.addrId));
        sbXml.append("' ");
        sbXml.append("state='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.state));
        sbXml.append("' ");
        sbXml.append("stateDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.stateDate));
        sbXml.append("' ");
        sbXml.append("pathCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
        sbXml.append("' ");
        sbXml.append("pathName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathName));
        sbXml.append("' ");
        sbXml.append("orgClass='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgClass));
        sbXml.append("' ");
        sbXml.append("effDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.effDate));
        sbXml.append("' ");
        sbXml.append("expDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.expDate));
        sbXml.append("' ");
        sbXml.append("addDescription='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.addDescription));
        sbXml.append("' ");
        sbXml.append("orgManager='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgManager));
        sbXml.append("' ");
        sbXml.append("partnerType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.partnerType));
        sbXml.append("' ");
        
        if( !"".equals(privilegeFlag)){
	        sbXml.append("privilegeFlag='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privilegeFlag));
	        sbXml.append("' ");
        }
        
        if( !"".equals(orgTypeName)){
        	sbXml.append("orgTypeName='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orgTypeName));
	        sbXml.append("' ");
        }
        
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return pathCode==null?"":pathCode;
    }

	/**
	 * @return Returns the orgType.
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType The orgType to set.
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

}
