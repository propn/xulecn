package com.ztesoft.vsop.paras.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class NeTranRuleParaVO extends ValueObject implements VO {
	

	private String tran_rule_id;
	

	private String para_id;
	

	private String seq;
	

	private String code;
	

	public String getTran_rule_id() {
		return tran_rule_id;
	}
	
	public void setTran_rule_id(String tran_rule_id) {
		this.tran_rule_id = tran_rule_id;
	}
	public String getPara_id() {
		return para_id;
	}
	
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	public String getSeq() {
		return seq;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("tran_rule_id",this.tran_rule_id);	
				
		hashMap.put("para_id",this.para_id);	
				
		hashMap.put("seq",this.seq);	
				
		hashMap.put("code",this.code);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.tran_rule_id = Const.getStrValue(hashMap, "tran_rule_id");
						
			this.para_id = Const.getStrValue(hashMap, "para_id");
						
			this.seq = Const.getStrValue(hashMap, "seq");
						
			this.code = Const.getStrValue(hashMap, "code");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "NE_TRAN_RULE_PARA";
	}
	
}
