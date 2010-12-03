package com.powerise.ibss.framedata.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class TfmActArgsVO extends ValueObject implements VO {
	

	private String action_id;
	

	private String arg_seq;
	

	private String arg_data_type;
	

	private String arg_length;
	

	private String arg_name;
	

	private String in_out_flag;
	

	public String getAction_id() {
		return action_id;
	}
	
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}
	public String getArg_seq() {
		return arg_seq;
	}
	
	public void setArg_seq(String arg_seq) {
		this.arg_seq = arg_seq;
	}
	public String getArg_data_type() {
		return arg_data_type;
	}
	
	public void setArg_data_type(String arg_data_type) {
		this.arg_data_type = arg_data_type;
	}
	public String getArg_length() {
		return arg_length;
	}
	
	public void setArg_length(String arg_length) {
		this.arg_length = arg_length;
	}
	public String getArg_name() {
		return arg_name;
	}
	
	public void setArg_name(String arg_name) {
		this.arg_name = arg_name;
	}
	public String getIn_out_flag() {
		return in_out_flag;
	}
	
	public void setIn_out_flag(String in_out_flag) {
		this.in_out_flag = in_out_flag;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("action_id",this.action_id);	
				
		hashMap.put("arg_seq",this.arg_seq);	
				
		hashMap.put("arg_data_type",this.arg_data_type);	
				
		hashMap.put("arg_length",this.arg_length);	
				
		hashMap.put("arg_name",this.arg_name);	
				
		hashMap.put("in_out_flag",this.in_out_flag);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.action_id = Const.getStrValue(hashMap, "action_id");
						
			this.arg_seq = Const.getStrValue(hashMap, "arg_seq");
						
			this.arg_data_type = Const.getStrValue(hashMap, "arg_data_type");
						
			this.arg_length = Const.getStrValue(hashMap, "arg_length");
						
			this.arg_name = Const.getStrValue(hashMap, "arg_name");
						
			this.in_out_flag = Const.getStrValue(hashMap, "in_out_flag");
						
		}
	}
	
	public static TfmActArgsVO getVOFromHashMap(Map hashMap) {
		TfmActArgsVO vo = new TfmActArgsVO() ;
		if (hashMap != null) {
//			vo.action_id = Const.getStrValue(hashMap, "action_id");
			vo.arg_seq = Const.getStrValue(hashMap, "arg_seq");
			vo.arg_data_type = Const.getStrValue(hashMap, "arg_data_type");
//			vo.arg_length = Const.getStrValue(hashMap, "arg_length");
			vo.arg_name = Const.getStrValue(hashMap, "arg_name");
			vo.in_out_flag = Const.getStrValue(hashMap, "in_out_flag");
						
		}
		return vo ;
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "TFM_ACTION_ARGS";
	}
	
}
