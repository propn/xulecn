package com.ztesoft.vsop.paras.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;


public class SpParaInfoService  {
	/**
	     需要替换位置 说明 ：
	  1. ServiceList.MyService  替换为ServiceList注册的服务 
	  2. searchSpParaInfoData 改方法的参数名称
	  3. findSpParaInfoByCond(String para_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSpParaInfo(HashMap SpParaInfo ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpParaInfo", SpParaInfo) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaInfoBo,
						"insertSpParaInfo" ,param)) ;
		return result ;
	}

	
	public boolean updateSpParaInfo(HashMap SpParaInfo ) throws Exception {
		Map param = new HashMap() ;
		param.put("SpParaInfo", SpParaInfo) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaInfoBo,
						"updateSpParaInfo" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchSpParaInfoData(String paraCatgId,int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("para_dir_id", paraCatgId) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SpParaInfoBo,
						"searchSpParaInfoData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getSpParaInfoById(String para_id) throws Exception {
		Map param = getSpParaInfoKeyMap(para_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SpParaInfoBo,
						"getSpParaInfoById" ,param)) ;
		
		return result ;
	}
	

	public List findSpParaInfoByCond(String para_id) throws Exception {
		Map param = getSpParaInfoKeyMap(para_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SpParaInfoBo,
						"findSpParaInfoByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaInfoById(String para_id) throws Exception {
		Map param = getSpParaInfoKeyMap(para_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SpParaInfoBo,
						"deleteSpParaInfoById" ,param)) ;
		
		return result ;
	}
	
	private Map getSpParaInfoKeyMap(String para_id){
		Map param = new HashMap() ;
				
		param.put("para_id", para_id) ;
				
		return param ;
	}
}
