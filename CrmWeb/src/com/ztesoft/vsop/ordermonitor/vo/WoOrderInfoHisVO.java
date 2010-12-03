package com.ztesoft.vsop.ordermonitor.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class WoOrderInfoHisVO extends ValueObject implements VO {
	

	private String order_id;
	

	private String prod_id;
	

	private String nbr;
	

	private String rela_order_id;
	

	private String state_code;
	

	private String dispatch_grade_id;
	

	private String area_id;
	

	private String receive_date;
	

	private String execute_date;
	

	private String finish_limit;
	

	private String finish_date;
	

	private String process_instance_id;
	

	private String pre_nbr;
	

	private String order_type_id;
	

	private String type_grade;
	

	private String control_str;
	

	private String executed_str;
	

	private String act_type;
	

	private String int_sys_id;
	

	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProd_id() {
		return prod_id;
	}
	
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getNbr() {
		return nbr;
	}
	
	public void setNbr(String nbr) {
		this.nbr = nbr;
	}
	public String getRela_order_id() {
		return rela_order_id;
	}
	
	public void setRela_order_id(String rela_order_id) {
		this.rela_order_id = rela_order_id;
	}
	public String getState_code() {
		return state_code;
	}
	
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
	public String getDispatch_grade_id() {
		return dispatch_grade_id;
	}
	
	public void setDispatch_grade_id(String dispatch_grade_id) {
		this.dispatch_grade_id = dispatch_grade_id;
	}
	public String getArea_id() {
		return area_id;
	}
	
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getReceive_date() {
		return receive_date;
	}
	
	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}
	public String getExecute_date() {
		return execute_date;
	}
	
	public void setExecute_date(String execute_date) {
		this.execute_date = execute_date;
	}
	public String getFinish_limit() {
		return finish_limit;
	}
	
	public void setFinish_limit(String finish_limit) {
		this.finish_limit = finish_limit;
	}
	public String getFinish_date() {
		return finish_date;
	}
	
	public void setFinish_date(String finish_date) {
		this.finish_date = finish_date;
	}
	public String getProcess_instance_id() {
		return process_instance_id;
	}
	
	public void setProcess_instance_id(String process_instance_id) {
		this.process_instance_id = process_instance_id;
	}
	public String getPre_nbr() {
		return pre_nbr;
	}
	
	public void setPre_nbr(String pre_nbr) {
		this.pre_nbr = pre_nbr;
	}
	public String getOrder_type_id() {
		return order_type_id;
	}
	
	public void setOrder_type_id(String order_type_id) {
		this.order_type_id = order_type_id;
	}
	public String getType_grade() {
		return type_grade;
	}
	
	public void setType_grade(String type_grade) {
		this.type_grade = type_grade;
	}
	public String getControl_str() {
		return control_str;
	}
	
	public void setControl_str(String control_str) {
		this.control_str = control_str;
	}
	public String getExecuted_str() {
		return executed_str;
	}
	
	public void setExecuted_str(String executed_str) {
		this.executed_str = executed_str;
	}
	public String getAct_type() {
		return act_type;
	}
	
	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}
	public String getInt_sys_id() {
		return int_sys_id;
	}
	
	public void setInt_sys_id(String int_sys_id) {
		this.int_sys_id = int_sys_id;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("order_id",this.order_id);	
				
		hashMap.put("prod_id",this.prod_id);	
				
		hashMap.put("nbr",this.nbr);	
				
		hashMap.put("rela_order_id",this.rela_order_id);	
				
		hashMap.put("state_code",this.state_code);	
				
		hashMap.put("dispatch_grade_id",this.dispatch_grade_id);	
				
		hashMap.put("area_id",this.area_id);	
				
		hashMap.put("receive_date",this.receive_date);	
				
		hashMap.put("execute_date",this.execute_date);	
				
		hashMap.put("finish_limit",this.finish_limit);	
				
		hashMap.put("finish_date",this.finish_date);	
				
		hashMap.put("process_instance_id",this.process_instance_id);	
				
		hashMap.put("pre_nbr",this.pre_nbr);	
				
		hashMap.put("order_type_id",this.order_type_id);	
				
		hashMap.put("type_grade",this.type_grade);	
				
		hashMap.put("control_str",this.control_str);	
				
		hashMap.put("executed_str",this.executed_str);	
				
		hashMap.put("act_type",this.act_type);	
				
		hashMap.put("int_sys_id",this.int_sys_id);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.order_id = Const.getStrValue(hashMap, "order_id");
						
			this.prod_id = Const.getStrValue(hashMap, "prod_id");
						
			this.nbr = Const.getStrValue(hashMap, "nbr");
						
			this.rela_order_id = Const.getStrValue(hashMap, "rela_order_id");
						
			this.state_code = Const.getStrValue(hashMap, "state_code");
						
			this.dispatch_grade_id = Const.getStrValue(hashMap, "dispatch_grade_id");
						
			this.area_id = Const.getStrValue(hashMap, "area_id");
						
			this.receive_date = Const.getStrValue(hashMap, "receive_date");
						
			this.execute_date = Const.getStrValue(hashMap, "execute_date");
						
			this.finish_limit = Const.getStrValue(hashMap, "finish_limit");
						
			this.finish_date = Const.getStrValue(hashMap, "finish_date");
						
			this.process_instance_id = Const.getStrValue(hashMap, "process_instance_id");
						
			this.pre_nbr = Const.getStrValue(hashMap, "pre_nbr");
						
			this.order_type_id = Const.getStrValue(hashMap, "order_type_id");
						
			this.type_grade = Const.getStrValue(hashMap, "type_grade");
						
			this.control_str = Const.getStrValue(hashMap, "control_str");
						
			this.executed_str = Const.getStrValue(hashMap, "executed_str");
						
			this.act_type = Const.getStrValue(hashMap, "act_type");
						
			this.int_sys_id = Const.getStrValue(hashMap, "int_sys_id");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "WO_ORDER_INFO_HIS";
	}
	
}
