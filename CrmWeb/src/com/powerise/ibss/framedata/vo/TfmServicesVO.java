package com.powerise.ibss.framedata.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class TfmServicesVO extends ValueObject implements VO {
	
	
	public static Map servicesMap = new HashMap() ;
	
	private String service_name;
	
	private String env_id;
	
	private String version;

	private String service_desc;
	
	private String service_type;
	
	private String className;
	
	private String state;
	
	private String cache_flag;
	
	private String module_name;
	
	private String if_log;
	
	//组合服务
	private List tfmServRelationVOList = null ;
	//查询服务
	private SQLArgs sqlArgs = null ;
	
	public SQLArgs getSqlArgs() {
		return sqlArgs;
	}

	public void setSqlArgs(SQLArgs sqlArgs) {
		this.sqlArgs = sqlArgs;
	}

	public List getTfmServRelationVOList() {
		return tfmServRelationVOList;
	}

	public void setTfmServRelationVOList(List tfmServRelationVOList) {
		this.tfmServRelationVOList = tfmServRelationVOList;
	}

	public String getService_name() {
		return service_name;
	}
	
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getEnv_id() {
		return env_id;
	}
	
	public void setEnv_id(String env_id) {
		this.env_id = env_id;
	}
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	public String getService_desc() {
		return service_desc;
	}
	
	public void setService_desc(String service_desc) {
		this.service_desc = service_desc;
	}
	public String getService_type() {
		return service_type;
	}
	
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	public String getCache_flag() {
		return cache_flag;
	}
	
	public void setCache_flag(String cache_flag) {
		this.cache_flag = cache_flag;
	}
	public String getModule_name() {
		return module_name;
	}
	
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public String getIf_log() {
		return if_log;
	}
	
	public void setIf_log(String if_log) {
		this.if_log = if_log;
	}


	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("service_name",this.service_name);	
				
		hashMap.put("env_id",this.env_id);	
				
		hashMap.put("version",this.version);	
				
		hashMap.put("service_desc",this.service_desc);	
				
		hashMap.put("service_type",this.service_type);	
				
		hashMap.put("definition",this.className);	
				
		hashMap.put("state",this.state);	
				
		hashMap.put("cache_flag",this.cache_flag);	
				
		hashMap.put("module_name",this.module_name);	
				
		hashMap.put("if_log",this.if_log);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.service_name = Const.getStrValue(hashMap, "service_name");
						
			this.env_id = Const.getStrValue(hashMap, "env_id");
						
			this.version = Const.getStrValue(hashMap, "version");
						
			this.service_desc = Const.getStrValue(hashMap, "service_desc");
						
			this.service_type = Const.getStrValue(hashMap, "service_type");
						
			this.className = Const.getStrValue(hashMap, "definition");
						
			this.state = Const.getStrValue(hashMap, "state");
						
			this.cache_flag = Const.getStrValue(hashMap, "cache_flag");
						
			this.module_name = Const.getStrValue(hashMap, "module_name");
						
			this.if_log = Const.getStrValue(hashMap, "if_log");
						
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "TFM_SERVICES";
	}
	
	public static TfmServicesVO getVOFromHashMap(Map hashMap) {
		TfmServicesVO vo = new TfmServicesVO() ;	
		if (hashMap != null) {
			vo.service_name = Const.getStrValue(hashMap, "service_name").toUpperCase();
						
			vo.env_id = Const.getStrValue(hashMap, "env_id").toUpperCase();
						
//			vo.version = Const.getStrValue(hashMap, "version");
						
//			vo.service_desc = Const.getStrValue(hashMap, "service_desc");
						
			vo.service_type = Const.getStrValue(hashMap, "service_type");
						
			vo.className = Const.getStrValue(hashMap, "class_name");
						
//			vo.state = Const.getStrValue(hashMap, "state");
						
//			vo.cache_flag = Const.getStrValue(hashMap, "cache_flag");
						
//			vo.module_name = Const.getStrValue(hashMap, "module_name");
						
			vo.if_log = Const.getStrValue(hashMap, "if_log");
			
		}
		if(vo.getService_name() != null && !"".equals(vo.getService_name().trim())){
			servicesMap.put(vo.getService_name(), vo) ;
		}
		return vo ;
	}
	
	public static TfmServicesVO getService(String serviceName ){
		Object o = servicesMap.get(serviceName) ;
		if( o == null )
			return new TfmServicesVO() ;
		return (TfmServicesVO) o ;
	}
}
