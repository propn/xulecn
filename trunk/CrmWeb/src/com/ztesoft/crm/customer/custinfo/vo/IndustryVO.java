package com.ztesoft.crm.customer.custinfo.vo;

import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.*;
import java.util.*;

public class IndustryVO extends ValueObject implements VO {

	private String attr_id = "";
	private String attr_value_id = "";
	private String industryName = "";
	private String industryCode = "";
	private String parentIndustryId = "";
	private String pathCode = null;
	
	public IndustryVO() {}

	public IndustryVO( String pindustryId, String pindustryName, String pindustryType,String pindustryCode,String pparentIndustryId,String ppathCode  ) {
		attr_id = pindustryId;
		industryName = pindustryName;
		attr_value_id = pindustryType;
		industryCode = pindustryCode;
		parentIndustryId = pparentIndustryId;
		pathCode = ppathCode;
	}

	public String getIndustryId() {
		return attr_id;
	}

	public String getIndustryName() {
		return industryName;
	}

	public String getIndustryType() {
		return attr_value_id;
	}

	public String getIndustryCode() {
		return industryCode;
	}	
	
	public String getParentIndustryId() {
		return parentIndustryId;
	}

	public String getPathCode() {
		return pathCode;
	}	
	
	public void setIndustryId(String pIndustryId) {
		attr_id = pIndustryId;
	}

	public void setIndustryName(String pIndustryName) {
		industryName = pIndustryName;
	}

	public void setIndustryType(String pIndustryType) {
		attr_value_id = pIndustryType;
	}

	public void setIndustryCode(String pindustryCode) {
		industryCode = pindustryCode;
	}
	
	public void setParentIndustryId(String pparentIndustryId) {
		parentIndustryId = pparentIndustryId;
	}

	public void setPathCode(String ppathCode) {
		pathCode = ppathCode;
	}	

	
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ATTR_ID",this.attr_id);
		hashMap.put("INDUSTRY_NAME",this.industryName);
		hashMap.put("ATTR_VALUE_ID",this.attr_value_id);
		hashMap.put("INDUSTRY_CODE",this.industryCode);
		hashMap.put("PARENT_INDUSTRY_ID",this.parentIndustryId);
		hashMap.put("PATH_CODE",this.pathCode);
		return hashMap;
	}
	
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.attr_id = (String) hashMap.get("ATTR_ID");
			this.industryName = (String) hashMap.get("INDUSTRY_NAME");
			this.attr_value_id = (String) hashMap.get("ATTR_VALUE_ID");
			this.industryCode = (String) hashMap.get("INDUSTRY_CODE");
			this.parentIndustryId = (String) hashMap.get("PARENT_INDUSTRY_ID");
			this.pathCode = (String) hashMap.get("PATH_CODE");					
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("INDUSTRY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "INDUSTRY";
	}
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("attr_id='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.attr_id));
        sbXml.append("' ");
        sbXml.append("industryName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.industryName));
        sbXml.append("' ");
        sbXml.append("attr_value_id='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.attr_value_id));
        sbXml.append("' ");
        sbXml.append("industryCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.industryCode));
        sbXml.append("' ");
        sbXml.append("parentIndustryId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parentIndustryId));
        sbXml.append("' ");
        sbXml.append("pathCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
        sbXml.append("' ");      
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return null;
    }	

}
