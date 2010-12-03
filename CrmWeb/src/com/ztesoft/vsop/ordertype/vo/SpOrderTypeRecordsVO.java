package com.ztesoft.vsop.ordertype.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpOrderTypeRecordsVO extends ValueObject implements VO {
	

	private String out_order_type_id;
	

	private String record_id;
	

	public String getOut_order_type_id() {
		return out_order_type_id;
	}
	
	public void setOut_order_type_id(String out_order_type_id) {
		this.out_order_type_id = out_order_type_id;
	}
	public String getRecord_id() {
		return record_id;
	}
	
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("out_order_type_id",this.out_order_type_id);	
				
		hashMap.put("record_id",this.record_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.out_order_type_id = Const.getStrValue(hashMap, "out_order_type_id");
						
			this.record_id = Const.getStrValue(hashMap, "record_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_ORDER_TYPE_RECORDS";
	}
	
}
