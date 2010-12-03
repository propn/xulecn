package com.ztesoft.crm.business.common.history.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class UhBookDateVO extends ValueObject implements VO {
	

	private String book_seq;
	

	private String cust_ord_id;
	

	private String product_offer_instance_id;
	

	private String book_type;
	

	private String time_name_id;
	

	private String book_date;
	

	private String book_date2;
	

	private String book_dow;
	

	private String book_dow2;
	

	private String book_time;
	

	private String book_time2;
	

	public String getBook_seq() {
		return book_seq;
	}
	
	public void setBook_seq(String book_seq) {
		this.book_seq = book_seq;
	}
	public String getCust_ord_id() {
		return cust_ord_id;
	}
	
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getProduct_offer_instance_id() {
		return product_offer_instance_id;
	}
	
	public void setProduct_offer_instance_id(String product_offer_instance_id) {
		this.product_offer_instance_id = product_offer_instance_id;
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
				
		hashMap.put("book_seq",this.book_seq);	
				
		hashMap.put("cust_ord_id",this.cust_ord_id);	
				
		hashMap.put("product_offer_instance_id",this.product_offer_instance_id);	
				
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
						
			this.book_seq = Const.getStrValue(hashMap, "book_seq");
						
			this.cust_ord_id = Const.getStrValue(hashMap, "cust_ord_id");
						
			this.product_offer_instance_id = Const.getStrValue(hashMap, "product_offer_instance_id");
						
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
		return "UH_BOOK_DATE";
	}
	
}
