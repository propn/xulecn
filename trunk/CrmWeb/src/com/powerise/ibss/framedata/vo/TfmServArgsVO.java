package com.powerise.ibss.framedata.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class TfmServArgsVO extends ValueObject implements VO {
	

	private String service_name;
	

	private String name;
	

	private String data_type;
	

	private String data_length;
	

	private String io_flag;
	

	private String map_name;
	

	private String map_flag;
	

	private String default_value;
	

	public String getService_name() {
		return service_name;
	}
	
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getData_type() {
		return data_type;
	}
	
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_length() {
		return data_length;
	}
	
	public void setData_length(String data_length) {
		this.data_length = data_length;
	}
	public String getIo_flag() {
		return io_flag;
	}
	
	public void setIo_flag(String io_flag) {
		this.io_flag = io_flag;
	}
	public String getMap_name() {
		return map_name;
	}
	
	public void setMap_name(String map_name) {
		this.map_name = map_name;
	}
	public String getMap_flag() {
		return map_flag;
	}
	
	public void setMap_flag(String map_flag) {
		this.map_flag = map_flag;
	}
	public String getDefault_value() {
		return default_value;
	}
	
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("service_name",this.service_name);	
				
		hashMap.put("name",this.name);	
				
		hashMap.put("data_type",this.data_type);	
				
		hashMap.put("data_length",this.data_length);	
				
		hashMap.put("io_flag",this.io_flag);	
				
		hashMap.put("map_name",this.map_name);	
				
		hashMap.put("map_flag",this.map_flag);	
				
		hashMap.put("default_value",this.default_value);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.service_name = Const.getStrValue(hashMap, "service_name");
						
			this.name = Const.getStrValue(hashMap, "name");
						
			this.data_type = Const.getStrValue(hashMap, "data_type");
						
			this.data_length = Const.getStrValue(hashMap, "data_length");
						
			this.io_flag = Const.getStrValue(hashMap, "io_flag");
						
			this.map_name = Const.getStrValue(hashMap, "map_name");
						
			this.map_flag = Const.getStrValue(hashMap, "map_flag");
						
			this.default_value = Const.getStrValue(hashMap, "default_value");
						
		}
	}
	
	public static TfmServArgsVO getVOFromHashMap(Map hashMap) {
		TfmServArgsVO vo = new TfmServArgsVO() ;
		if (hashMap != null) {
						
			vo.service_name = Const.getStrValue(hashMap, "service_name");
						
			vo.name = Const.getStrValue(hashMap, "name");
						
			vo.data_type = Const.getStrValue(hashMap, "data_type");
						
			vo.data_length = Const.getStrValue(hashMap, "data_length");
						
			vo.io_flag = Const.getStrValue(hashMap, "io_flag");
						
			vo.map_name = Const.getStrValue(hashMap, "map_name");
						
			vo.map_flag = Const.getStrValue(hashMap, "map_flag");
						
			vo.default_value = Const.getStrValue(hashMap, "default_value");
						
		}
		return vo ;
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "TFM_SERV_ARGS";
	}
	
}
