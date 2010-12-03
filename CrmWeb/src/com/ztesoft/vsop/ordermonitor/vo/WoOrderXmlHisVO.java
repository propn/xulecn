package com.ztesoft.vsop.ordermonitor.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoOrderXmlHisVO extends ValueObject implements VO {
	

	private String order_id;
	

	private String xml_seq;
	

	private String xml_info;
	

	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getXml_seq() {
		return xml_seq;
	}
	
	public void setXml_seq(String xml_seq) {
		this.xml_seq = xml_seq;
	}
	public String getXml_info() {
		return xml_info;
	}
	
	public void setXml_info(String xml_info) {
		this.xml_info = xml_info;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("order_id",this.order_id);	
				
		hashMap.put("xml_seq",this.xml_seq);	
				
		hashMap.put("xml_info",this.xml_info);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.order_id = Const.getStrValue(hashMap, "order_id");
						
			this.xml_seq = Const.getStrValue(hashMap, "xml_seq");
						
			this.xml_info = Const.getStrValue(hashMap, "xml_info");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_ORDER_XML_HIS";
	}
	
}
