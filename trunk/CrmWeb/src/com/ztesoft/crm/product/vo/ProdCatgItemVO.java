package com.ztesoft.crm.product.vo;

import com.ztesoft.common.valueobject.*;
import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import java.util.*;

public class ProdCatgItemVO extends ValueObject implements VO,XMLItem{

	private String catgItemId = "";
	private String parentCatgItemId = "";
	private String catgId = "";
	private String catgItemName = "";
	private String catgItemDesc = "";
	private String catgItemType = "";
	private String orderId = "";
	private String seq = "";
	private String state = "";
	private String siteNo = "";
	private String staffNo = "";
	private String operBureauNo = "";
	private String operDate = "";
	private String ordActType = "";
	private String ordNo = "";
	private String cancelOrdNo = "";
	private String eventSeq = "";
	private String cancelEventSeq = "";
	private String nodeType="";
	private String elementId="";

	public ProdCatgItemVO() {}

	public ProdCatgItemVO( String pcatgItemId, String pparentCatgItemId, String pcatgId, String pcatgItemName, String pcatgItemDesc, String pcatgItemType, String porderId, String pseq, String pstate, String psiteNo, String pstaffNo, String poperBureauNo, String poperDate, String pordActType, String pordNo, String pcancelOrdNo, String peventSeq, String pcancelEventSeq ) {
		catgItemId = pcatgItemId;
		parentCatgItemId = pparentCatgItemId;
		catgId = pcatgId;
		catgItemName = pcatgItemName;
		catgItemDesc = pcatgItemDesc;
		catgItemType = pcatgItemType;
		orderId = porderId;
		seq = pseq;
		state = pstate;
		siteNo = psiteNo;
		staffNo = pstaffNo;
		operBureauNo = poperBureauNo;
		operDate = poperDate;
		ordActType = pordActType;
		ordNo = pordNo;
		cancelOrdNo = pcancelOrdNo;
		eventSeq = peventSeq;
		cancelEventSeq = pcancelEventSeq;
	}

	public String getCatgItemId() {
		return catgItemId;
	}

	public String getParentCatgItemId() {
		return parentCatgItemId;
	}

	public String getCatgId() {
		return catgId;
	}

	public String getCatgItemName() {
		return catgItemName;
	}

	public String getCatgItemDesc() {
		return catgItemDesc;
	}

	public String getCatgItemType() {
		return catgItemType;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getSeq() {
		return seq;
	}

	public String getState() {
		return state;
	}

	public String getSiteNo() {
		return siteNo;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public String getOperBureauNo() {
		return operBureauNo;
	}

	public String getOperDate() {
		return operDate;
	}

	public String getOrdActType() {
		return ordActType;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public String getCancelOrdNo() {
		return cancelOrdNo;
	}

	public String getEventSeq() {
		return eventSeq;
	}

	public String getCancelEventSeq() {
		return cancelEventSeq;
	}

	public void setCatgItemId(String pCatgItemId) {
		catgItemId = pCatgItemId;
	}

	public void setParentCatgItemId(String pParentCatgItemId) {
		parentCatgItemId = pParentCatgItemId;
	}

	public void setCatgId(String pCatgId) {
		catgId = pCatgId;
	}

	public void setCatgItemName(String pCatgItemName) {
		catgItemName = pCatgItemName;
	}

	public void setCatgItemDesc(String pCatgItemDesc) {
		catgItemDesc = pCatgItemDesc;
	}

	public void setCatgItemType(String pCatgItemType) {
		catgItemType = pCatgItemType;
	}

	public void setOrderId(String pOrderId) {
		orderId = pOrderId;
	}

	public void setSeq(String pSeq) {
		seq = pSeq;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setSiteNo(String pSiteNo) {
		siteNo = pSiteNo;
	}

	public void setStaffNo(String pStaffNo) {
		staffNo = pStaffNo;
	}

	public void setOperBureauNo(String pOperBureauNo) {
		operBureauNo = pOperBureauNo;
	}

	public void setOperDate(String pOperDate) {
		operDate = pOperDate;
	}

	public void setOrdActType(String pOrdActType) {
		ordActType = pOrdActType;
	}

	public void setOrdNo(String pOrdNo) {
		ordNo = pOrdNo;
	}

	public void setCancelOrdNo(String pCancelOrdNo) {
		cancelOrdNo = pCancelOrdNo;
	}

	public void setEventSeq(String pEventSeq) {
		eventSeq = pEventSeq;
	}

	public void setCancelEventSeq(String pCancelEventSeq) {
		cancelEventSeq = pCancelEventSeq;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CATALOG_ITEM_ID",this.catgItemId);
		hashMap.put("PARENT_CATALOG_ITEM_ID",this.parentCatgItemId);
		hashMap.put("CATALOG_ID",this.catgId);
		hashMap.put("CATALOG_ITEM_NAME",this.catgItemName);
		hashMap.put("CATALOG_ITEM_DESC",this.catgItemDesc);
		hashMap.put("CATALOG_ITEM_TYPE",this.catgItemType);
		hashMap.put("ORDER_ID",this.orderId);
		hashMap.put("SEQ",this.seq);
		hashMap.put("STATE",this.state);
		hashMap.put("SITE_NO",this.siteNo);
		hashMap.put("STAFF_NO",this.staffNo);
		hashMap.put("OPER_BUREAU_NO",this.operBureauNo);
		hashMap.put("OPER_DATE",this.operDate);
		hashMap.put("ORD_ACTION_TYPE",this.ordActType);
		hashMap.put("ORD_NO",this.ordNo);
		hashMap.put("CANCEL_ORD_NO",this.cancelOrdNo);
		hashMap.put("EVENT_SEQ",this.eventSeq);
		hashMap.put("CANCEL_EVENT_SEQ",this.cancelEventSeq);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap){
		if (hashMap != null){
			this.catgItemId = (String) hashMap.get("CATALOG_ITEM_ID".toLowerCase());
			this.parentCatgItemId = (String) hashMap.get("PARENT_CATALOG_ITEM_ID".toLowerCase());
			this.catgId = (String) hashMap.get("CATALOG_ID".toLowerCase());
			this.catgItemName = (String) hashMap.get("CATALOG_ITEM_NAME".toLowerCase());
			this.catgItemDesc = (String) hashMap.get("CATALOG_ITEM_DESC".toLowerCase());
			this.catgItemType = (String) hashMap.get("CATALOG_ITEM_TYPE".toLowerCase());
			this.orderId = (String) hashMap.get("ORDER_ID".toLowerCase());
			this.seq = (String) hashMap.get("SEQ".toLowerCase());
			this.state = (String) hashMap.get("STATE".toLowerCase());
			this.siteNo = (String) hashMap.get("SITE_NO".toLowerCase());
			this.staffNo = (String) hashMap.get("STAFF_NO".toLowerCase());
			this.operBureauNo = (String) hashMap.get("OPER_BUREAU_NO".toLowerCase());
			this.operDate = (String) hashMap.get("OPER_DATE".toLowerCase());
			this.ordActType = (String) hashMap.get("ORD_ACTION_TYPE".toLowerCase());
			this.ordNo = (String) hashMap.get("ORD_NO".toLowerCase());
			this.cancelOrdNo = (String) hashMap.get("CANCEL_ORD_NO".toLowerCase());
			this.eventSeq = (String) hashMap.get("EVENT_SEQ".toLowerCase());
			this.cancelEventSeq = (String) hashMap.get("CANCEL_EVENT_SEQ".toLowerCase());
			this.elementId = (String) hashMap.get("element_id".toLowerCase());
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CATALOG_ITEM_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_CATALOG_ITEM";
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
        sbXml.append("catgItemDesc='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catgItemDesc));
        sbXml.append("' ");
        sbXml.append("catgItemName='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catgItemName));
        sbXml.append("' ");
        sbXml.append("catgItemType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catgItemType));
        sbXml.append("' ");
        sbXml.append("orderId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.orderId));
        sbXml.append("' ");
        sbXml.append("operDate='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.operDate));
        sbXml.append("' ");
        sbXml.append("catgId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.catgId));
        sbXml.append("' ");
        sbXml.append("nodeType='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.nodeType));
        sbXml.append("' ");
        sbXml.append("elementId='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.elementId));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}


}
