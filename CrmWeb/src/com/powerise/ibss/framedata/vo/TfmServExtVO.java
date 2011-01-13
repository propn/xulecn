package com.powerise.ibss.framedata.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class TfmServExtVO extends ValueObject implements VO {
	

	private String service_name;
	

	private String seq;
	

	private String section;
	

	private String action_type ;
	
	
	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getService_name() {
		return service_name;
	}
	
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getSeq() {
		return seq;
	}
	
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSection() {
		return section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("service_name",this.service_name);	
				
		hashMap.put("seq",this.seq);	
				
		hashMap.put("section",this.section);	
		
		hashMap.put("action_type",this.action_type);	
		
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.service_name = Const.getStrValue(hashMap, "service_name");
						
			this.seq = Const.getStrValue(hashMap, "seq");
						
			this.section = Const.getStrValue(hashMap, "section");
			
			this.action_type = Const.getStrValue(hashMap, "action_type");
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "TFM_SERV_EXT";
	}
	
}
