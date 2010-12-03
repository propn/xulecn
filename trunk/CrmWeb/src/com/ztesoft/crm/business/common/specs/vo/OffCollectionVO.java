package com.ztesoft.crm.business.common.specs.vo;

import com.ztesoft.common.valueobject.*;
import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import java.util.*;

public class OffCollectionVO extends ValueObject implements VO,XMLItem{

	private String productOfferId = "";
	private String collectionType = "";
	private String collectionId = "";
	private String state = "";
	private String seq = "";
	private String partyId = "";
	private String partyRoleId = "";
	private String operRegionId = "";
	private String operDate = "";
	
	private String offerName  = "";
	
	private String offerKind  = "";
	private String offerComments  = "";
	public OffCollectionVO() {}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRODUCT_OFFER_ID".toLowerCase(),this.productOfferId);
		hashMap.put("COLLECTION_TYPE".toLowerCase(),this.collectionType);
		hashMap.put("COLLECTION_ID".toLowerCase(),this.collectionId);
		hashMap.put("STATE".toLowerCase(),this.state);
		hashMap.put("SEQ".toLowerCase(),this.seq);
		hashMap.put("PARTY_ID".toLowerCase(),this.partyId);
		hashMap.put("PARTY_ROLE_ID".toLowerCase(),this.partyRoleId);
		hashMap.put("OPER_REGION_ID".toLowerCase(),this.operRegionId);
		hashMap.put("OPER_DATE".toLowerCase(),this.operDate);
		hashMap.put("OFFER_KIND".toLowerCase(),this.offerKind);
		hashMap.put("OFFER_COMMENTS".toLowerCase(),this.offerComments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap){
		if (hashMap != null){
			this.productOfferId = (String) hashMap.get("PRODUCT_OFFER_ID".toLowerCase());
			this.collectionType = (String) hashMap.get("COLLECTION_TYPE".toLowerCase());
			this.collectionId = (String) hashMap.get("COLLECTION_ID".toLowerCase());
			this.state = (String) hashMap.get("STATE".toLowerCase());
			this.seq = (String) hashMap.get("SEQ".toLowerCase());
			this.partyId = (String) hashMap.get("PARTY_ID".toLowerCase());
			this.partyRoleId = (String) hashMap.get("PARTY_ROLE_ID".toLowerCase());
			this.operRegionId = (String) hashMap.get("OPER_REGION_ID".toLowerCase());
			this.operDate = (String) hashMap.get("OPER_DATE".toLowerCase());
			this.offerKind = (String) hashMap.get("OFFER_KIND ".toLowerCase());
			this.offerComments = (String) hashMap.get("OFFER_COMMENTS ".toLowerCase());
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRODUCT_OFFER_ID");
		arrayList.add("COLLECTION_TYPE");
		arrayList.add("COLLECTION_ID");
		arrayList.add("SEQ");
		return arrayList;
	}

	public String getTableName() {
		return "OFFER_COLLECTION";
	}
	
	
	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("productOfferId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.productOfferId));
        sbXml.append("' ");
        sbXml.append("collectionType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.collectionType));
        sbXml.append("' ");
        sbXml.append("collectionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.collectionId));
        sbXml.append("' ");
        sbXml.append("state='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.collectionId));
        sbXml.append("' ");
        sbXml.append("seq='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.seq));
        sbXml.append("' ");
        sbXml.append("partyId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.partyId));
        sbXml.append("' ");
        sbXml.append("partyRoleId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.partyRoleId));
        sbXml.append("' ");
        sbXml.append("operRegionId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.operRegionId));
        sbXml.append("' ");
        sbXml.append("operDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.operDate));
        sbXml.append("' ");
        sbXml.append("offerKind='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.offerKind));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }





	public String getProductOfferId() {
		return productOfferId;
	}





	public void setProductOfferId(String productOfferId) {
		this.productOfferId = productOfferId;
	}





	public String getCollectionType() {
		return collectionType;
	}





	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}





	public String getCollectionId() {
		return collectionId;
	}





	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}





	public String getState() {
		return state;
	}





	public void setState(String state) {
		this.state = state;
	}





	public String getSeq() {
		return seq;
	}





	public void setSeq(String seq) {
		this.seq = seq;
	}





	public String getPartyId() {
		return partyId;
	}





	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}





	public String getPartyRoleId() {
		return partyRoleId;
	}





	public void setPartyRoleId(String partyRoleId) {
		this.partyRoleId = partyRoleId;
	}





	public String getOperRegionId() {
		return operRegionId;
	}





	public void setOperRegionId(String operRegionId) {
		this.operRegionId = operRegionId;
	}





	public String getOperDate() {
		return operDate;
	}





	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferKind() {
		return offerKind;
	}

	public void setOfferKind(String offerKind) {
		this.offerKind = offerKind;
	}

	public String getOfferComments() {
		return offerComments;
	}

	public void setOfferComments(String offerComments) {
		this.offerComments = offerComments;
	}

	

}
