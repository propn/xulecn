package com.ztesoft.crm.business.accept.offer.vo;

import com.ztesoft.common.valueobject.*;
import com.ztesoft.common.util.XMLItem;
import com.ztesoft.common.util.XMLSegBuilder;
import java.util.*;

public class PackagesInfoVO extends ValueObject implements VO,XMLItem{

	private String package_name = "";
	private String package_id = "";
	private String offer_id = "";
	private String packet_type = "";
	private String offer_kind = "";
	private String product_offer_instance_id = "";
	private String role_min_num = "";
	private String role_max_num = "";
	public PackagesInfoVO() {}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PACKAGE_NAME",this.package_name);
		hashMap.put("PACKAGE_ID",this.package_id);
		hashMap.put("OFFER_ID",this.offer_id);
		hashMap.put("OFFER_ID",this.offer_id);
		hashMap.put("PACKET_TYPE",this.packet_type);
		hashMap.put("OFFER_KIND",this.offer_kind);
		hashMap.put("PRODUCT_OFFER_INSTANCE_ID",this.product_offer_instance_id);
		hashMap.put("ROLE_MIN_NUM",this.role_min_num);
		hashMap.put("ROLE_MAX_NUM",this.role_max_num);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap){
		if (hashMap != null){
			this.package_name = (String) hashMap.get("PACKAGE_NAME".toLowerCase());
			this.package_id = (String) hashMap.get("PACKAGE_ID".toLowerCase());
			this.offer_id = (String) hashMap.get("OFFER_ID".toLowerCase());
			this.packet_type = (String) hashMap.get("PACKET_TYPE".toLowerCase());
			this.offer_kind = (String) hashMap.get("OFFER_KIND".toLowerCase());
			this.product_offer_instance_id = (String) hashMap.get("PRODUCT_OFFER_INSTANCE_ID".toLowerCase());
			this.role_min_num = (String) hashMap.get("ROLE_MIN_NUM".toLowerCase());
			this.role_max_num = (String) hashMap.get("ROLE_MAX_NUM".toLowerCase());
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
	
		return arrayList;
	}

	/**
     * 生成item节点格式的XML片断
     * @return item节点格式的XML片断
     */
    public StringBuffer toXmlItemUnclosed(StringBuffer sbXml)
    {
        sbXml.append("<item ");
        sbXml.append("package_name='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.package_name));
        sbXml.append("' ");
        sbXml.append("package_id='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.package_id));
        sbXml.append("' ");
        sbXml.append("offer_id='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.offer_id));
        sbXml.append("' ");
        sbXml.append("packet_type='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.packet_type));
        sbXml.append("' ");
        sbXml.append("offer_kind='");
        sbXml.append(XMLSegBuilder.escapeXMLStringValue(this.offer_kind));
        sbXml.append("' ");
        sbXml.append(">");
        return sbXml;
    }
    
    
	public String getTableName() {
		return "";
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getOffer_id() {
		return offer_id;
	}

	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}

	public String getPacket_type() {
		return packet_type;
	}

	public void setPacket_type(String packet_type) {
		this.packet_type = packet_type;
	}

	public String getOffer_kind() {
		return offer_kind;
	}

	public void setOffer_kind(String offer_kind) {
		this.offer_kind = offer_kind;
	}

	public String getProduct_offer_instance_id() {
		return product_offer_instance_id;
	}

	public void setProduct_offer_instance_id(String product_offer_instance_id) {
		this.product_offer_instance_id = product_offer_instance_id;
	}

	public String getRole_min_num() {
		return role_min_num;
	}

	public void setRole_min_num(String role_min_num) {
		this.role_min_num = role_min_num;
	}

	public String getRole_max_num() {
		return role_max_num;
	}

	public void setRole_max_num(String role_max_num) {
		this.role_max_num = role_max_num;
	}


	
}
