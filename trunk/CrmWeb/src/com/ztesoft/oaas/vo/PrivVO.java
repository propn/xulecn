package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class PrivVO extends ValueObject implements VO, XMLItem {

	private String privId = "";
	private String parentPrgId = "";
	private String privName = "";
	private String privType = "";
	private String privTypeName = "";
	private String privDesc = "";
	private String privCode = "";
	private String pathCode = "";
	
	private String ifRegionRela = "";
	private String ifRegionRelaName = "";

	public String getIfRegionRelaName() {
		return ifRegionRelaName;
	}

	public void setIfRegionRelaName(String ifRegionRelaName) {
		this.ifRegionRelaName = ifRegionRelaName;
	}

	public String getIfRegionRela() {
		return ifRegionRela;
	}

	public void setIfRegionRela(String ifRegionRela) {
		this.ifRegionRela = ifRegionRela;
	}

	public PrivVO() {}

	public PrivVO( String pprivId, String pparentPrgId, String pprivName, String pprivType, String pprivDesc, String pprivCode, String ppathCode ) {
		privId = pprivId;
		parentPrgId = pparentPrgId;
		privName = pprivName;
		privType = pprivType;
		privDesc = pprivDesc;
		privCode = pprivCode;
		pathCode = ppathCode;
	}

	public String getPrivId() {
		return privId;
	}

	public String getParentPrgId() {
		return parentPrgId;
	}

	public String getPrivName() {
		return privName;
	}

	public String getPrivType() {
		return privType;
	}

	public String getPrivDesc() {
		return privDesc;
	}

	public String getPrivCode() {
		return privCode;
	}

    public String getPathCode() {
        return pathCode;
    }

	public void setPrivId(String pPrivId) {
		privId = pPrivId;
	}

	public void setParentPrgId(String pParentPrgId) {
		parentPrgId = pParentPrgId;
	}

	public void setPrivName(String pPrivName) {
		privName = pPrivName;
	}

	public void setPrivType(String pPrivType) {
		privType = pPrivType;
	}

	public void setPrivDesc(String pPrivDesc) {
		privDesc = pPrivDesc;
	}

	public void setPrivCode(String pPrivCode) {
		privCode = pPrivCode;
	}

	public void setPathCode(String pPathCode) {
		pathCode = pPathCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRIVILEGE_ID",this.privId);
		hashMap.put("PARENT_PRG_ID",this.parentPrgId);
		hashMap.put("PRIVILEGE_NAME",this.privName);
		hashMap.put("PRIVILEGE_TYPE",this.privType);
		hashMap.put("PRIVILEGE_DESC",this.privDesc);
		hashMap.put("PRIVILEGE_CODE",this.privCode);
		hashMap.put("PATH_CODE",this.pathCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.privId = (String) hashMap.get("PRIVILEGE_ID");
			this.parentPrgId = (String) hashMap.get("PARENT_PRG_ID");
			this.privName = (String) hashMap.get("PRIVILEGE_NAME");
			this.privType = (String) hashMap.get("PRIVILEGE_TYPE");
			this.privDesc = (String) hashMap.get("PRIVILEGE_DESC");
			this.privCode = (String) hashMap.get("PRIVILEGE_CODE");
			this.pathCode = (String) hashMap.get("PATH_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRIVILEGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRIVILEGE";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("privId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privId));
        sbXml.append("' ");
        sbXml.append("parentPrgId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parentPrgId));
        sbXml.append("' ");
        sbXml.append("privName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privName));
        sbXml.append("' ");
        sbXml.append("privType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privType));
        sbXml.append("' ");
        sbXml.append("privTypeName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privTypeName));
        sbXml.append("' ");
        sbXml.append("privDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privDesc));
        sbXml.append("' ");
        sbXml.append("privCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.privCode));
        sbXml.append("' ");
        sbXml.append("pathCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.pathCode));
        sbXml.append("' ");
        
        if( !"".equals(this.ifRegionRela)){
	        sbXml.append("ifRegionRela='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.ifRegionRela));
	        sbXml.append("' ");
        }
        if( !"".equals(this.ifRegionRelaName)){
	        sbXml.append("ifRegionRelaName='");
	        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.ifRegionRelaName));
	        sbXml.append("' ");
        }        
        
        sbXml.append(">");
        return sbXml;
    }

    public String pathInTree() {
        return pathCode==null?"":pathCode;
    }

	public String getPrivTypeName() {
		return privTypeName;
	}

	public void setPrivTypeName(String privTypeName) {
		this.privTypeName = privTypeName;
	}

}
