package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdBookDateVO extends ValueObject implements VO {
	

	private String ord_item_id;
	

	private String cust_ord_id;
	

	private String ord_id;
	

	private String action_type;
	

	private String state_date;
	

	private String end_time;
	

	private String beg_time;
	

	private String book_seq;
	

	private String comp_inst_id;
	

	private String book_type;
	

	private String time_name_id;
	

	private String book_date;
	

	private String book_date2;
	

	private String book_dow;
	

	private String book_dow2;
	

	private String book_time;
	

	private String book_time2;
	

	public String getOrd_item_id() {
		return ord_item_id;
	}
	
	public void setOrd_item_id(String ord_item_id) {
		this.ord_item_id = ord_item_id;
	}
	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getOrd_id() {
		return ord_id;
	}
	
	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}
	public String getAction_type() {
		return action_type;
	}
	
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getState_date() {
		return state_date;
	}
	
	public void setState_date(String state_date) {
		this.state_date = state_date;
	}
	public String getEnd_time() {
		return end_time;
	}
	
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getBeg_time() {
		return beg_time;
	}
	
	public void setBeg_time(String beg_time) {
		this.beg_time = beg_time;
	}
	public String getBook_seq() {
		return book_seq;
	}
	
	public void setBook_seq(String book_seq) {
		this.book_seq = book_seq;
	}
	public String getComp_inst_id() {
		return comp_inst_id;
	}
	
	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}
	public String getBook_type() {
		return book_type;
	}
	
	public void setBook_type(String book_type) {
		this.book_type = book_type;
	}
	public String getTime_name_id() {
		return time_name_id;
	}
	
	public void setTime_name_id(String time_name_id) {
		this.time_name_id = time_name_id;
	}
	public String getBook_date() {
		return book_date;
	}
	
	public void setBook_date(String book_date) {
		this.book_date = book_date;
	}
	public String getBook_date2() {
		return book_date2;
	}
	
	public void setBook_date2(String book_date2) {
		this.book_date2 = book_date2;
	}
	public String getBook_dow() {
		return book_dow;
	}
	
	public void setBook_dow(String book_dow) {
		this.book_dow = book_dow;
	}
	public String getBook_dow2() {
		return book_dow2;
	}
	
	public void setBook_dow2(String book_dow2) {
		this.book_dow2 = book_dow2;
	}
	public String getBook_time() {
		return book_time;
	}
	
	public void setBook_time(String book_time) {
		this.book_time = book_time;
	}
	public String getBook_time2() {
		return book_time2;
	}
	
	public void setBook_time2(String book_time2) {
		this.book_time2 = book_time2;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("ord_item_id",this.ord_item_id);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("ord_id",this.ord_id);	
				
		hashMap.put("action_type",this.action_type);	
				
		hashMap.put("state_date",this.state_date);	
				
		hashMap.put("end_time",this.end_time);	
				
		hashMap.put("beg_time",this.beg_time);	
				
		hashMap.put("book_seq",this.book_seq);	
				
		hashMap.put("comp_inst_id",this.comp_inst_id);	
				
		hashMap.put("book_type",this.book_type);	
				
		hashMap.put("time_name_id",this.time_name_id);	
				
		hashMap.put("book_date",this.book_date);	
				
		hashMap.put("book_date2",this.book_date2);	
				
		hashMap.put("book_dow",this.book_dow);	
				
		hashMap.put("book_dow2",this.book_dow2);	
				
		hashMap.put("book_time",this.book_time);	
				
		hashMap.put("book_time2",this.book_time2);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.ord_item_id = Const.getStrValue(hashMap, "ord_item_id");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.ord_id = Const.getStrValue(hashMap, "ord_id");
						
			this.action_type = Const.getStrValue(hashMap, "action_type");
						
			this.state_date = Const.getStrValue(hashMap, "state_date");
						
			this.end_time = Const.getStrValue(hashMap, "end_time");
						
			this.beg_time = Const.getStrValue(hashMap, "beg_time");
						
			this.book_seq = Const.getStrValue(hashMap, "book_seq");
						
			this.comp_inst_id = Const.getStrValue(hashMap, "comp_inst_id");
						
			this.book_type = Const.getStrValue(hashMap, "book_type");
						
			this.time_name_id = Const.getStrValue(hashMap, "time_name_id");
						
			this.book_date = Const.getStrValue(hashMap, "book_date");
						
			this.book_date2 = Const.getStrValue(hashMap, "book_date2");
						
			this.book_dow = Const.getStrValue(hashMap, "book_dow");
						
			this.book_dow2 = Const.getStrValue(hashMap, "book_dow2");
						
			this.book_time = Const.getStrValue(hashMap, "book_time");
						
			this.book_time2 = Const.getStrValue(hashMap, "book_time2");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_BOOK_DATE";
	}
	
}
