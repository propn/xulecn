package com.ztesoft.vsop.ordertype.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class SpOrderTypeCommandsVO extends ValueObject implements VO {
	

	private String out_order_type_id;
	

	private String command_id;
	

	public String getOut_order_type_id() {
		return out_order_type_id;
	}
	
	public void setOut_order_type_id(String out_order_type_id) {
		this.out_order_type_id = out_order_type_id;
	}
	public String getCommand_id() {
		return command_id;
	}
	
	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("out_order_type_id",this.out_order_type_id);	
				
		hashMap.put("command_id",this.command_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.out_order_type_id = Const.getStrValue(hashMap, "out_order_type_id");
						
			this.command_id = Const.getStrValue(hashMap, "command_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "SP_ORDER_TYPE_COMMANDS";
	}
	
}
