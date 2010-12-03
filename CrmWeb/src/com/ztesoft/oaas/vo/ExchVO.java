package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ExchVO extends ValueObject implements VO, XMLItem {

	private String exchId = "";
	private String regionId = "";
	private String exchCode = "";
	private String exchName = "";
	private String accNbrBegin = "";
	private String accNbrEnd = "";
	private String state = "";
	private String effDate = "";
	private String expDate = "";
	private String areaId = "";
	private String prodFamilyId = "";
	
	private String prodFamilyName = "";

	public ExchVO() {}

	public ExchVO( String pexchId, String pregionId, String pexchCode, String pexchName, String paccNbrBegin, String paccNbrEnd, String pstate, String peffDate, String pexpDate, String pareaId, String pprodFamilyId,String pprodFamilyName ) {
		exchId = pexchId;
		regionId = pregionId;
		exchCode = pexchCode;
		exchName = pexchName;
		accNbrBegin = paccNbrBegin;
		accNbrEnd = paccNbrEnd;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		areaId = pareaId;
		prodFamilyId = pprodFamilyId;
		prodFamilyName = pprodFamilyName;
	}

	public String getExchId() {
		return exchId;
	}

	public String getRegionId() {
		return regionId;
	}

	public String getExchCode() {
		return exchCode;
	}

	public String getExchName() {
		return exchName;
	}

	public String getAccNbrBegin() {
		return accNbrBegin;
	}

	public String getAccNbrEnd() {
		return accNbrEnd;
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

	public String getAreaId() {
		return areaId;
	}

	public String getProdFamilyId() {
		return prodFamilyId;
	}

	public void setExchId(String pExchId) {
		exchId = pExchId;
	}

	public void setRegionId(String pRegionId) {
		regionId = pRegionId;
	}

	public void setExchCode(String pExchCode) {
		exchCode = pExchCode;
	}

	public void setExchName(String pExchName) {
		exchName = pExchName;
	}

	public void setAccNbrBegin(String pAccNbrBegin) {
		accNbrBegin = pAccNbrBegin;
	}

	public void setAccNbrEnd(String pAccNbrEnd) {
		accNbrEnd = pAccNbrEnd;
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

	public void setAreaId(String pAreaId) {
		areaId = pAreaId;
	}

	public void setProdFamilyId(String pProdFamilyId) {
		prodFamilyId = pProdFamilyId;
	}
	public void setProdFamilyName( String pProdFamilyName){
		prodFamilyName = pProdFamilyName;
	}
	public String getProdFamilyName(){
		return prodFamilyName;
	}
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("EXCHANGE_ID",this.exchId);
		hashMap.put("REGION_ID",this.regionId);
		hashMap.put("EXCHANGE_CODE",this.exchCode);
		hashMap.put("EXCHANGE_NAME",this.exchName);
		hashMap.put("ACC_NBR_BEGIN",this.accNbrBegin);
		hashMap.put("ACC_NBR_END",this.accNbrEnd);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("AREA_ID",this.areaId);
		hashMap.put("PRODUCT_FAMILY_ID",this.prodFamilyId);
		hashMap.put("PRODUCT_FAMILY_NAME", this.prodFamilyName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.exchId = (String) hashMap.get("EXCHANGE_ID");
			this.regionId = (String) hashMap.get("REGION_ID");
			this.exchCode = (String) hashMap.get("EXCHANGE_CODE");
			this.exchName = (String) hashMap.get("EXCHANGE_NAME");
			this.accNbrBegin = (String) hashMap.get("ACC_NBR_BEGIN");
			this.accNbrEnd = (String) hashMap.get("ACC_NBR_END");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.areaId = (String) hashMap.get("AREA_ID");
			this.prodFamilyId = (String) hashMap.get("PRODUCT_FAMILY_ID");
			this.prodFamilyName = (String)hashMap.get("PRODUCT_FAMILY_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("EXCHANGE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "EXCHANGE";
	}

    /**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("exchId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.exchId));
        sbXml.append("' ");
        sbXml.append("regionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.regionId));
        sbXml.append("' ");
        sbXml.append("exchCode='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.exchCode));
        sbXml.append("' ");
        sbXml.append("exchName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.exchName));
        sbXml.append("' ");
        sbXml.append("accNbrBegin='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.accNbrBegin));
        sbXml.append("' ");
        sbXml.append("accNbrEnd='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.accNbrEnd));
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
        sbXml.append("areaId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.areaId));
        sbXml.append("' ");
        sbXml.append("prodFamilyId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.prodFamilyId));
        sbXml.append("' ");
        sbXml.append("prodFamilyName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.prodFamilyName));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }
    
    public String pathInTree() {
        return null;
    }

}
