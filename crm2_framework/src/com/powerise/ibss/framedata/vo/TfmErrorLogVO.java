package com.powerise.ibss.framedata.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class TfmErrorLogVO extends ValueObject implements VO {
	

	private String error_seq;
	

	private String error_code;
	

	private String error_log;
	

	private String occur_time;
	

	private String terminal;
	

	private String error_pos;
	

	private String service_name;
	

	public String getError_seq() {
		return error_seq;
	}
	
	public void setError_seq(String error_seq) {
		this.error_seq = error_seq;
	}
	public String getError_code() {
		return error_code;
	}
	
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_log() {
		return error_log;
	}
	
	public void setError_log(String error_log) {
		this.error_log = error_log;
	}
	public String getOccur_time() {
		return occur_time;
	}
	
	public void setOccur_time(String occur_time) {
		this.occur_time = occur_time;
	}
	public String getTerminal() {
		return terminal;
	}
	
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getError_pos() {
		return error_pos;
	}
	
	public void setError_pos(String error_pos) {
		this.error_pos = error_pos;
	}
	public String getService_name() {
		return service_name;
	}
	
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("error_seq",this.error_seq);	
				
		hashMap.put("error_code",this.error_code);	
				
		hashMap.put("error_log",this.error_log);	
				
		hashMap.put("occur_time",this.occur_time);	
				
		hashMap.put("terminal",this.terminal);	
				
		hashMap.put("error_pos",this.error_pos);	
				
		hashMap.put("service_name",this.service_name);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.error_seq = Const.getStrValue(hashMap, "error_seq");
						
			this.error_code = Const.getStrValue(hashMap, "error_code");
						
			this.error_log = Const.getStrValue(hashMap, "error_log");
						
			this.occur_time = Const.getStrValue(hashMap, "occur_time");
						
			this.terminal = Const.getStrValue(hashMap, "terminal");
						
			this.error_pos = Const.getStrValue(hashMap, "error_pos");
						
			this.service_name = Const.getStrValue(hashMap, "service_name");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "TFM_ERROR_LOG";
	}
	
}
