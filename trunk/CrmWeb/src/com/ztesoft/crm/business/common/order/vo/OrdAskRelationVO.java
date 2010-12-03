package com.ztesoft.crm.business.common.order.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class OrdAskRelationVO extends ValueObject implements VO {
	

	private String a_ord_id;
	

	private String z_ord_no;
	

	private String rela_type;
	

	private String state;
	

	public String getA_ord_id() {
		return a_ord_id;
	}
	
	public void setA_ord_id(String a_ord_id) {
		this.a_ord_id = a_ord_id;
	}
	public String getZ_ord_no() {
		return z_ord_no;
	}
	
	public void setZ_ord_no(String z_ord_no) {
		this.z_ord_no = z_ord_no;
	}
	public String getRela_type() {
		return rela_type;
	}
	
	public void setRela_type(String rela_type) {
		this.rela_type = rela_type;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("a_ord_id",this.a_ord_id);	
				
		hashMap.put("z_ord_no",this.z_ord_no);	
				
		hashMap.put("rela_type",this.rela_type);	
				
		hashMap.put("state",this.state);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.a_ord_id = Const.getStrValue(hashMap, "a_ord_id");
						
			this.z_ord_no = Const.getStrValue(hashMap, "z_ord_no");
						
			this.rela_type = Const.getStrValue(hashMap, "rela_type");
						
			this.state = Const.getStrValue(hashMap, "state");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "ORD_ASK_RELATION";
	}
	
}
