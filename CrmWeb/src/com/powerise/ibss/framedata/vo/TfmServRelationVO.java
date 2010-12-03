package com.powerise.ibss.framedata.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;
import com.powerise.ibss.framework.Const;

public class TfmServRelationVO extends ValueObject implements VO {
	

	private String service_name;
	

	private String seq;
	

	private String co_service_name;
	

	private String cond_service_name;
	

	private String condition_flag;
	

	private String cond_arg_name;
	
	private TfmServicesVO coService = null ;
	private TfmServicesVO condService = null ;
	

	public TfmServicesVO getCondService() {
		return condService;
	}

	public void setCondService(TfmServicesVO condService) {
		this.condService = condService;
	}

	public TfmServicesVO getCoService() {
		return coService;
	}

	public void setCoService(TfmServicesVO coService) {
		this.coService = coService;
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
	public String getCo_service_name() {
		return co_service_name;
	}
	
	public void setCo_service_name(String co_service_name) {
		this.co_service_name = co_service_name;
	}
	public String getCond_service_name() {
		return cond_service_name;
	}
	
	public void setCond_service_name(String cond_service_name) {
		this.cond_service_name = cond_service_name;
	}
	public String getCondition_flag() {
		return condition_flag;
	}
	
	public void setCondition_flag(String condition_flag) {
		this.condition_flag = condition_flag;
	}
	public String getCond_arg_name() {
		return cond_arg_name;
	}
	
	public void setCond_arg_name(String cond_arg_name) {
		this.cond_arg_name = cond_arg_name;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
				
		hashMap.put("service_name",this.service_name);	
				
		hashMap.put("seq",this.seq);	
				
		hashMap.put("co_service_name",this.co_service_name);	
				
		hashMap.put("cond_service_name",this.cond_service_name);	
				
		hashMap.put("condition_flag",this.condition_flag);	
				
		hashMap.put("cond_arg_name",this.cond_arg_name);	
				
		return hashMap;
	}
	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
						
			this.service_name = Const.getStrValue(hashMap, "service_name");
						
			this.seq = Const.getStrValue(hashMap, "seq");
						
			this.co_service_name = Const.getStrValue(hashMap, "co_service_name");
						
			this.cond_service_name = Const.getStrValue(hashMap, "cond_service_name");
						
			this.condition_flag = Const.getStrValue(hashMap, "condition_flag");
						
			this.cond_arg_name = Const.getStrValue(hashMap, "cond_arg_name");
						
		}
	}
	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "TFM_SERV_RELATION";
	}
	
	
	public static TfmServRelationVO getVOFromHashMap(Map hashMap) {
		TfmServRelationVO vo = new TfmServRelationVO() ;
		
		if (hashMap != null) {
						
			vo.service_name = Const.getStrValue(hashMap, "service_name");
						
			vo.seq = Const.getStrValue(hashMap, "seq");
						
			vo.co_service_name = Const.getStrValue(hashMap, "co_service_name");
						
			vo.cond_service_name = Const.getStrValue(hashMap, "cond_service_name");
						
			vo.condition_flag = Const.getStrValue(hashMap, "condition_flag");
						
			vo.cond_arg_name = Const.getStrValue(hashMap, "cond_arg_name");
			if("1".equals(vo.condition_flag) && vo.cond_service_name != null && !"".equals(vo.cond_service_name)){
				vo.condService = TfmServicesVO.getService(vo.cond_service_name) ;
			}
			
			vo.coService = TfmServicesVO.getService(vo.co_service_name) ;
		}
		
		return vo ;
	}
	
}
