package com.ztesoft.vsop.ordermonitor.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoOrderErrorLogVO extends ValueObject implements VO {
	

	private String error_serial_id;
	

	private String order_id;
	

	private String create_date;
	

	private String error_content;
	

	private String error_code;
	

	public String getError_serial_id() {
		return error_serial_id;
	}
	
	public void setError_serial_id(String error_serial_id) {
		this.error_serial_id = error_serial_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getError_content() {
		return error_content;
	}
	
	public void setError_content(String error_content) {
		this.error_content = error_content;
	}
	public String getError_code() {
		return error_code;
	}
	
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("error_serial_id",this.error_serial_id);	
				
		hashMap.put("order_id",this.order_id);	
				
		hashMap.put("create_date",this.create_date);	
				
		hashMap.put("error_content",this.error_content);	
				
		hashMap.put("error_code",this.error_code);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.error_serial_id = Const.getStrValue(hashMap, "error_serial_id");
						
			this.order_id = Const.getStrValue(hashMap, "order_id");
						
			this.create_date = Const.getStrValue(hashMap, "create_date");
						
			this.error_content = Const.getStrValue(hashMap, "error_content");
						
			this.error_code = Const.getStrValue(hashMap, "error_code");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_ORDER_ERROR_LOG";
	}
	
}
