package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class RegionVO extends ValueObject implements VO,XMLItem{

	private String regionId = "";
	private String parentRegionId = "";
	private String regionLevel = "";
	private String regionLevelName = "";
	private String regionName = "";
	private String regionDesc = "";
	private String regionCode = "";
	private String pathCode = "";
	private String pathName = "";
	private String ngnFlag = "" ;
	private String privilegeFlag = "";
	private String virtualDealFlag = "";
	private String partyId = "";
	private String isActualRegion = "";
	private String provinceCode = "";	
	
	private String countryType = "";
	
	
	public String getCountryType() {
		return countryType;
	}

	public void setCountryType(String countryType) {
		this.countryType = countryType;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getVirtualDealFlag() {
		return virtualDealFlag;
	}

	public void setVirtualDealFlag(String virtualDealFlag) {
		this.virtualDealFlag = virtualDealFlag;
	}

	public String getPrivilegeFlag() {
		return privilegeFlag;
	}

	public void setPrivilegeFlag(String privilegeFlag) {
		this.privilegeFlag = privilegeFlag;
	}

	public String getNgnFlag() {
		return ngnFlag;
	}

	public void setNgnFlag(String ngnFlag) {
		this.ngnFlag = ngnFlag;
	}

	public String getIsActualRegion() {
		return isActualRegion;
	}
	
	public void setIsActualRegion(String isActualRegion) {
		this.isActualRegion = isActualRegion;
	}
	
	public String getProvinceCode() {
		return provinceCode;
	}
	
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}		
	
	public RegionVO() {}

	public RegionVO( String pregionId, String pparentRegionId, String pregionLevel, String pregionName, String pregionDesc, String pregionCode, String ppathCode, String ppathName, String pngnFlag ) {
		regionId = pregionId;
		parentRegionId = pparentRegionId;
		regionLevel = pregionLevel;
		regionName = pregionName;
		regionDesc = pregionDesc;
		regionCode = pregionCode;
		pathCode = ppathCode;
		pathName = ppathName;
		ngnFlag = pngnFlag ;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getParentRegionId() {
		return parentRegionId;
	}

	public String getRegionLevel() {
		return regionLevel;
	}

	public String getRegionName() {
		return regionName;
	}

	public String getRegionDesc() {
		return regionDesc;
	}

	public String getRegionCode() {
		return regionCode;
	}

    public String getPathCode() {
        return pathCode;
    }

	public String getPathName() {
		return pathName;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setParentRegionId(String pParentRegionId) {
		parentRegionId = pParentRegionId;
	}

	public void setRegionLevel(String pRegionLevel) {
		regionLevel = pRegionLevel;
	}

	public void setRegionName(String pRegionName) {
		regionName = pRegionName;
	}

	public void setRegionDesc(String pRegionDesc) {
		regionDesc = pRegionDesc;
	}

	public void setRegionCode(String pRegionCode) {
		regionCode = pRegionCode;
	}

	public void setPathCode(String pPathCode) {
		pathCode = pPathCode;
	}

	public void setPathName(String pPathName) {
		pathName = pPathName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("PARENT_REGION_ID",this.parentRegionId);
		hashMap.put("REGION_LEVEL",this.regionLevel);
		hashMap.put("REGION_NAME",this.regionName);
		hashMap.put("REGION_DESC",this.regionDesc);
		hashMap.put("REGION_CODE",this.regionCode);
		hashMap.put("PATH_CODE",this.pathCode);
		hashMap.put("PATH_NAME",this.pathName);
		hashMap.put("NGN_FLAG",this.ngnFlag);
		hashMap.put("IS_ACTUAL_REGION",this.isActualRegion);
		hashMap.put("PROVINCE_CODE",this.provinceCode);		
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.regionId = (String) hashMap.get("REGION_ID");
			this.parentRegionId = (String) hashMap.get("PARENT_REGION_ID");
			this.regionLevel = (String) hashMap.get("REGION_LEVEL");
			this.regionName = (String) hashMap.get("REGION_NAME");
			this.regionDesc = (String) hashMap.get("REGION_DESC");
			this.regionCode = (String) hashMap.get("REGION_CODE");
			this.pathCode = (String) hashMap.get("PATH_CODE");
			this.pathName = (String) hashMap.get("PATH_NAME");
			this.ngnFlag = (String)hashMap.get("NGN_FLAG");
			this.isActualRegion = (String) hashMap.get("IS_ACTUAL_REGION");
			this.provinceCode = (String) hashMap.get("PROVINCE_CODE");
		
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("REGION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "REGION";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("regionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionId));
        sbXml.append("' ");
        sbXml.append("parentRegionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parentRegionId));
        sbXml.append("' ");
        sbXml.append("regionLevel='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionLevel));
        sbXml.append("' ");
        sbXml.append("regionName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionName));
        sbXml.append("' ");
        sbXml.append("regionDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionDesc));
        sbXml.append("' ");
        sbXml.append("regionCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionCode));
        sbXml.append("' ");
        sbXml.append("ngnFlag='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.ngnFlag));
        sbXml.append("' ");
        sbXml.append("virtualDealFlag='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.virtualDealFlag));
        sbXml.append("' ");
        sbXml.append("pathCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
        sbXml.append("' ");
        sbXml.append("pathName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathName));
        sbXml.append("' ");
        sbXml.append("isActualRegion='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.isActualRegion));
        sbXml.append("' ");
        sbXml.append("provinceCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.provinceCode));
        sbXml.append("' ");       
        sbXml.append("countryType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.countryType));
        sbXml.append("' ");   
        
        if( !"".equals(this.privilegeFlag)){
        	sbXml.append("privilegeFlag='");
            sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privilegeFlag));
            sbXml.append("' ");
        }
        if( !"".equals(this.regionLevelName)){
        	sbXml.append("regionLevelName='");
            sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionLevelName));
            sbXml.append("' ");
        }
        
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return pathCode==null?"":pathCode;
    }

	public String getRegionLevelName() {
		return regionLevelName;
	}

	public void setRegionLevelName(String regionLevelName) {
		this.regionLevelName = regionLevelName;
	}

}
