package com.powerise.ibss.framedata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;


/**
 * 后台框架数据维护
 * 
 * @author governlee 2010-02-23
 *
 */
public class TfmServicesService  {
	
	
	public boolean insertTfmServices(HashMap TfmServices ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmServices", TfmServices) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"insertTfmServices" ,param)) ;
		return result ;
	}

	
	public boolean updateTfmServices(HashMap TfmServices ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmServices", TfmServices) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"updateTfmServices" ,param)) ;
		return result ;
	}
	
	public boolean updateTfmServicesByKey(HashMap TfmServices ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmServices", TfmServices) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"updateTfmServicesByKey" ,param)) ;
		return result ;
	}
	
	
	public PageModel searchTfmServicesData(String service_name , String service_type ,String service_desc , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("service_name", service_name) ;
		param.put("service_type", service_type) ;
		param.put("service_desc", service_desc) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"searchTfmServicesData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getTfmServicesById(String service_name) throws Exception {
		Map param = getTfmServicesKeyMap(service_name) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"getTfmServicesById" ,param)) ;
		
		return result ;
	}
	

	public List findTfmServicesByCond(String service_name) throws Exception {
		Map param = getTfmServicesKeyMap(service_name) ;
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"findTfmServicesByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteTfmServicesById(String service_name,String service_type) throws Exception {
		Map param = new HashMap() ;
        param.put("service_name", service_name);
        param.put("service_type", service_type);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"deleteTfmServicesById" ,param)) ;
		
		return result ;
	}
	
	private Map getTfmServicesKeyMap(String service_name){
		Map param = new HashMap() ;
				
		param.put("service_name", service_name) ;
				
		return param ;
	}
	
	
	
	
	public boolean checkExistServiceName(String service_name) throws Exception{
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"checkExistServiceName" ,getTfmServicesKeyMap(service_name)));
		return result;
		
	}
	

	public PageModel searchTfmServRelationData(String service_name, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("service_name", service_name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"searchTfmServRelationData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getTfmServRelationById(String service_name,String seq) throws Exception {
		Map param = getTfmServRelationKeyMap(service_name,seq) ;
		
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"getTfmServRelationById" ,param)) ;
		
		return result ;
	}
	

	public List findTfmServRelationOrderBySeq(String service_name) throws Exception {
		Map param = new HashMap() ;
		param.put("service_name", service_name);
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"findTfmServRelationOrderBySeq" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteTfmServRelationById(String service_name,Integer seq) throws Exception {
		
		Map param = new HashMap();
		param.put("service_name", service_name);
		param.put("seq", seq);

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"deleteTfmServRelationById" ,param)) ;	
		return result ;
		
	
	}
	
	private Map getTfmServRelationKeyMap(String service_name,String seq){
		Map param = new HashMap() ;
		param.put("service_name", service_name);	
		param.put("seq", seq);
		return param ;
	}
	
	
	public boolean checkExistServiceSeq(String service_name,Integer seq) throws Exception{
		Map param = new HashMap();
		param.put("service_name", service_name);
		param.put("seq", seq);		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"checkExistServiceSeq" ,param));
		return result;		
	}
	
	public boolean insertTfmServRelation(HashMap TfmServRelation ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmServRelation", TfmServRelation) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"insertTfmServRelation" ,param)) ;
		return result ;
	}
	
	
	public boolean updateTfmServRelationByKey(HashMap TfmServRelation) throws Exception{
		Map param = new HashMap() ;
		param.put("TfmServRelation", TfmServRelation) ;	
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"updateTfmServRelation" ,param));
		return result;
	}
	
	public boolean insertTfmServExt(HashMap TfmServExt ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmServExt", TfmServExt) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"insertTfmServExt" ,param)) ;
		return result ;
	}

	
	public boolean updateTfmServExt(HashMap TfmServExt ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmServExt", TfmServExt) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"updateTfmServExt" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchTfmServExtData(String service_name , int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("service_name", service_name) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"searchTfmServExtData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getTfmServExtById(String service_name,String seq) throws Exception {
		Map param = getTfmServExtKeyMap(service_name,seq) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"getTfmServExtById" ,param)) ;
		
		return result ;
	}
	


	
	public boolean deleteTfmServExtById(String service_name,Integer seq) throws Exception {
		Map param = new HashMap() ;
		param.put("service_name", service_name);
		param.put("seq", seq);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"deleteTfmServExtById" ,param)) ;
		
		return result ;
	}
	
	private Map getTfmServExtKeyMap(String service_name,String seq){
		Map param = new HashMap() ;
		param.put("service_name", service_name);
		param.put("seq", seq);
		return param ;
	}
	
	public boolean insertTfmActArgs(HashMap TfmActArgs ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmActArgs", TfmActArgs) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"insertTfmActArgs" ,param)) ;
		return result ;
	}

	
	public boolean updateTfmActArgs(HashMap TfmActArgs ) throws Exception {
		Map param = new HashMap() ;
		param.put("TfmActArgs", TfmActArgs) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"updateTfmActArgs" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchTfmActArgsData(String action_id ) throws Exception {
		
		Map param = new HashMap() ;
		param.put("action_id", action_id) ;
//		param.put("pageIndex", new Integer(pageIndex)) ;
//		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"searchTfmActArgsData" ,param)) ;
		
		return result ;
	}
	
	
	public Map getTfmActArgsById(String action_id,String arg_seq) throws Exception {
		Map param = getTfmActArgsKeyMap(action_id,arg_seq) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"getTfmActArgsById" ,param)) ;
		
		return result ;
	}
	

	public List findTfmActArgsOrderBySeq(String action_id) throws Exception {
		Map param = new HashMap();
		param.put("action_id", action_id);
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"findTfmActArgsOrderBySeq" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteTfmActArgsById(String action_id,Integer arg_seq) throws Exception {
		Map param = new HashMap();
		param.put("action_id", action_id);
		param.put("arg_seq", arg_seq);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"deleteTfmActArgsById" ,param)) ;
		
		return result ;
	}
	
	private Map getTfmActArgsKeyMap(String action_id,String arg_seq){
		Map param = new HashMap() ;
				
		param.put("action_id", action_id) ;
				
		param.put("arg_seq", arg_seq) ;
				
		return param ;
	}
	
	public List findTfmServExtOrderBySeq(String service_name) throws Exception {
		Map param = new HashMap();
		param.put("service_name", service_name);
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"findTfmServExtOrderBySeq" ,param)) ;
		
		return result ;
	}
	
	public boolean checkTfmServExtSeq(String service_name,Integer seq) throws Exception{
		Map param = new HashMap();
		param.put("service_name", service_name);
		param.put("seq", seq);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"checkTfmServExtSeq" ,param)) ;		
		return result ;	
	}
	
	public boolean checkTfmActArgsSeq(String action_id,Integer arg_seq) throws Exception{
		Map param = new HashMap();
		param.put("action_id", action_id);
		param.put("arg_seq", arg_seq);
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TfmServicesBO",
						"checkTfmActArgsSeq" ,param)) ;		
		return result ;	
	}
}


