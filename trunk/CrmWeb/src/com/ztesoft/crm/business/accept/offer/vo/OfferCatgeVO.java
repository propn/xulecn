package com.ztesoft.crm.business.accept.offer.vo;

import com.ztesoft.common.valueobject.*;
import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import java.util.*;

public class OfferCatgeVO extends ValueObject implements VO,XMLItem{

	private String catgItemId = "";
	private String parentCatgItemId = "";
	private String offerId = "";
	private String catgItemName = "";
	private String offerName = "";
	private String offerKind = "";
	private String offerComments = "";

	public OfferCatgeVO() {}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CATALOG_ITEM_ID",this.catgItemId);
		hashMap.put("PARENT_CATALOG_ITEM_ID",this.parentCatgItemId);
		hashMap.put("OFFER_ID",this.offerId);
		hashMap.put("CATALOG_ITEM_NAME",this.catgItemName);
		hashMap.put("OFFER_NAME",this.offerName);
		hashMap.put("OFFER_KIND",this.offerKind);
		hashMap.put("OFFER_COMMENTS",this.offerComments);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap){
		if (hashMap != null){
			this.catgItemId = (String) hashMap.get("CATALOG_ITEM_ID".toLowerCase());
			this.parentCatgItemId = (String) hashMap.get("PARENT_CATALOG_ITEM_ID".toLowerCase());
			this.offerId = (String) hashMap.get("OFFER_ID".toLowerCase());
			this.catgItemName = (String) hashMap.get("CATALOG_ITEM_NAME".toLowerCase());
			this.offerName = (String) hashMap.get("OFFER_NAME".toLowerCase());
			this.offerKind = (String) hashMap.get("OFFER_KIND".toLowerCase());
			this.offerComments = (String) hashMap.get("OFFER_COMMENTS".toLowerCase());
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CATALOG_ITEM_ID");
		return arrayList;
	}

	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("catgItemId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catgItemId));
        sbXml.append("' ");
        sbXml.append("parentCatgItemId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.parentCatgItemId));
        sbXml.append("' ");
        sbXml.append("offerId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.offerId));
        sbXml.append("' ");
        sbXml.append("catgItemName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catgItemName));
        sbXml.append("' ");
        sbXml.append("offerKind='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.offerKind));
        sbXml.append("' ");
        sbXml.append("offerComments='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.offerComments));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }
    
    
	public String getTableName() {
		return "";
	}

	public String getCatgItemId() {
		return catgItemId;
	}

	public void setCatgItemId(String catgItemId) {
		this.catgItemId = catgItemId;
	}

	public String getParentCatgItemId() {
		return parentCatgItemId;
	}

	public void setParentCatgItemId(String parentCatgItemId) {
		this.parentCatgItemId = parentCatgItemId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getCatgItemName() {
		return catgItemName;
	}

	public void setCatgItemName(String catgItemName) {
		this.catgItemName = catgItemName;
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
