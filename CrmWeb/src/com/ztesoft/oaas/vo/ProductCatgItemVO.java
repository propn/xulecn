package com.ztesoft.oaas.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class ProductCatgItemVO extends ValueObject implements VO {
	

	private String catalog_item_id;
	

	private String parent_catalog_item_id;
	

	private String catalog_id;
	

	private String catalog_item_name;
	

	private String catalog_item_desc;
	

	private String catalog_item_cd;
	

	private String catalog_item_type;
	

	private String order_id;
	

	private String seq;
	

	private String state;
	

	private String oper_date;
	

	private String ord_action_type;
	

	private String ord_no;
	

	private String cancel_ord_no;
	

	private String event_seq;
	

	private String cancel_event_seq;
	

	private String party_id;
	

	private String party_role_id;
	

	private String oper_region_id;
	

	public String getCatalog_item_id() {
		return catalog_item_id;
	}
	
	public void setCatalog_item_id(String catalog_item_id) {
		this.catalog_item_id = catalog_item_id;
	}
	public String getParent_catalog_item_id() {
		return parent_catalog_item_id;
	}
	
	public void setParent_catalog_item_id(String parent_catalog_item_id) {
		this.parent_catalog_item_id = parent_catalog_item_id;
	}
	public String getCatalog_id() {
		return catalog_id;
	}
	
	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}
	public String getCatalog_item_name() {
		return catalog_item_name;
	}
	
	public void setCatalog_item_name(String catalog_item_name) {
		this.catalog_item_name = catalog_item_name;
	}
	public String getCatalog_item_desc() {
		return catalog_item_desc;
	}
	
	public void setCatalog_item_desc(String catalog_item_desc) {
		this.catalog_item_desc = catalog_item_desc;
	}
	public String getCatalog_item_cd() {
		return catalog_item_cd;
	}
	
	public void setCatalog_item_cd(String catalog_item_cd) {
		this.catalog_item_cd = catalog_item_cd;
	}
	public String getCatalog_item_type() {
		return catalog_item_type;
	}
	
	public void setCatalog_item_type(String catalog_item_type) {
		this.catalog_item_type = catalog_item_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSeq() {
		return seq;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getOper_date() {
		return oper_date;
	}
	
	public void setOper_date(String oper_date) {
		this.oper_date = oper_date;
	}
	public String getOrd_action_type() {
		return ord_action_type;
	}
	
	public void setOrd_action_type(String ord_action_type) {
		this.ord_action_type = ord_action_type;
	}
	public String getOrd_no() {
		return ord_no;
	}
	
	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}
	public String getCancel_ord_no() {
		return cancel_ord_no;
	}
	
	public void setCancel_ord_no(String cancel_ord_no) {
		this.cancel_ord_no = cancel_ord_no;
	}
	public String getEvent_seq() {
		return event_seq;
	}
	
	public void setEvent_seq(String event_seq) {
		this.event_seq = event_seq;
	}
	public String getCancel_event_seq() {
		return cancel_event_seq;
	}
	
	public void setCancel_event_seq(String cancel_event_seq) {
		this.cancel_event_seq = cancel_event_seq;
	}
	public String getParty_id() {
		return party_id;
	}
	
	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}
	public String getParty_role_id() {
		return party_role_id;
	}
	
	public void setParty_role_id(String party_role_id) {
		this.party_role_id = party_role_id;
	}
	public String getOper_region_id() {
		return oper_region_id;
	}
	
	public void setOper_region_id(String oper_region_id) {
		this.oper_region_id = oper_region_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("catalog_item_id",this.catalog_item_id);	
				
		hashMap.put("parent_catalog_item_id",this.parent_catalog_item_id);	
				
		hashMap.put("catalog_id",this.catalog_id);	
				
		hashMap.put("catalog_item_name",this.catalog_item_name);	
				
		hashMap.put("catalog_item_desc",this.catalog_item_desc);	
				
		hashMap.put("catalog_item_cd",this.catalog_item_cd);	
				
		hashMap.put("catalog_item_type",this.catalog_item_type);	
				
		hashMap.put("order_id",this.order_id);	
				
		hashMap.put("seq",this.seq);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("oper_date",this.oper_date);	
				
		hashMap.put("ord_action_type",this.ord_action_type);	
				
		hashMap.put("ord_no",this.ord_no);	
				
		hashMap.put("cancel_ord_no",this.cancel_ord_no);	
				
		hashMap.put("event_seq",this.event_seq);	
				
		hashMap.put("cancel_event_seq",this.cancel_event_seq);	
				
		hashMap.put("party_id",this.party_id);	
				
		hashMap.put("party_role_id",this.party_role_id);	
				
		hashMap.put("oper_region_id",this.oper_region_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.catalog_item_id = Const.getStrValue(hashMap, "catalog_item_id");
						
			this.parent_catalog_item_id = Const.getStrValue(hashMap, "parent_catalog_item_id");
						
			this.catalog_id = Const.getStrValue(hashMap, "catalog_id");
						
			this.catalog_item_name = Const.getStrValue(hashMap, "catalog_item_name");
						
			this.catalog_item_desc = Const.getStrValue(hashMap, "catalog_item_desc");
						
			this.catalog_item_cd = Const.getStrValue(hashMap, "catalog_item_cd");
						
			this.catalog_item_type = Const.getStrValue(hashMap, "catalog_item_type");
						
			this.order_id = Const.getStrValue(hashMap, "order_id");
						
			this.seq = Const.getStrValue(hashMap, "seq");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.oper_date = Const.getStrValue(hashMap, "oper_date");
						
			this.ord_action_type = Const.getStrValue(hashMap, "ord_action_type");
						
			this.ord_no = Const.getStrValue(hashMap, "ord_no");
						
			this.cancel_ord_no = Const.getStrValue(hashMap, "cancel_ord_no");
						
			this.event_seq = Const.getStrValue(hashMap, "event_seq");
						
			this.cancel_event_seq = Const.getStrValue(hashMap, "cancel_event_seq");
						
			this.party_id = Const.getStrValue(hashMap, "party_id");
						
			this.party_role_id = Const.getStrValue(hashMap, "party_role_id");
						
			this.oper_region_id = Const.getStrValue(hashMap, "oper_region_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_CATALOG_ITEM";
	}
	
}
