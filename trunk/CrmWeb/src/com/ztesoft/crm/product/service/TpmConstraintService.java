package com.ztesoft.crm.product.service;


import java.util.ArrayList;
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


public class TpmConstraintService  {
	/**
	     需要替换位置 说明 ：
	  1. "TpmConstraintBO"  替换为ServiceList注册的服务 
	  2. searchTpmConstraintData 改方法的参数名称
	  3. findTpmConstraintByCond(String constraint_id) 参数需要根据实际情况修改
	  4. 不需要的方法，可以根据实际情况进行裁剪
	  5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public List getStrategy_Id() throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_STRATEGY_ID_0001", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraint_Desc(String object_type,String object_id) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OBJECT_ID", object_id);
		   param.put("OBJECT_TYPE", object_type);
		   param.put("OBJECT_ID", object_id);
		   param.put("OBJECT_ID", object_id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0001", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraint_Type(String constraint_adsc,String object_type) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("constraint_adsc", constraint_adsc);
		   param.put("object_type", object_type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0014", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	public List getConstraint_Para(String constraint_type) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("constraint_type", constraint_type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0015", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getOperation_Flag() throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_1001", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getTime_Limit_Type() throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_1002", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getNav_Type(String Object_Type) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("FROM_OBJECT_TYPE", Object_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0002", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getRela_type() throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0003", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getOffer_Attr(String OFFER_ID) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OFFER_ID", OFFER_ID);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0013", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getGroup_Type_ID(String vObject_Id,String vObject_Type,String vRela_Type) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OBJECT_ID", vObject_Id);
		   param.put("OBJECT_TYPE", vObject_Type);
		   param.put("RELA_TYPE", vRela_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0040", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getSub_Product(String vOffer_Id,String vRela_Type,String vGroup_No) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OFFER_ID", vOffer_Id);
		   param.put("RELA_TYPE", vRela_Type);
		   param.put("RELA_TYPE", vRela_Type);
		   param.put("GROUP_NO", vGroup_No);
		   param.put("GROUP_NO", vGroup_No);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0010", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getSub_Product2(String vObject_Id) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PRODUCT_ID", vObject_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0007", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getProduct_Attr(String vObject_Id) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PRODUCT_ID", vObject_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0012", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getEnt_Object(String vObject_Id) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PRODUCT_ID", vObject_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0009", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getGroup_No(String vObject_Id,String vvTo_Object_Type,String vRole_Rela_Type) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OBJECT_ID", vObject_Id);
		   param.put("OBJECT_TYPE", vvTo_Object_Type);
		   param.put("ROLE_RELA_TYPE", vRole_Rela_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0006", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getObject(String vObject_Id,String vvTo_Object_Type,String vRole_Rela_Type,String vGroup_No,String vTo_Object_Type,String strServiceName) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   if(strServiceName.equals("QPM_PROD_OFFER_CONSTRAINT_0005")){
			   param.put("OFFER_ID", vObject_Id);
			   param.put("ROLE_RELA_TYPE", vRole_Rela_Type);
		   }else if(strServiceName.equals("QPM_PROD_OFFER_CONSTRAINT_0004")){
			   param.put("OFFER_ID", vObject_Id);
			   param.put("OBJECT_TYPE", vvTo_Object_Type);
			   param.put("ROLE_RELA_TYPE", vRole_Rela_Type);
			   param.put("Group_No", vGroup_No);
			   param.put("PRODUCT_CLASSIFICATION", vTo_Object_Type);
		   }
		  
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strServiceName, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getCust_Nav_Object(String Object_Type) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("FROM_OBJECT_TYPE", Object_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0002", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getCust_Attr() throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0017", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getProduct_Card() throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0008", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getObject_Service(String vStrObject_Id,String strServiceName) throws Exception{
		 Map param = new HashMap() ;
		 if(strServiceName.equals("QPM_PROD_OFFER_PRICE_0004")){
			   param.put("OBJECT_ID", vStrObject_Id);
		   }else if(strServiceName.equals("QPM_PROD_OFFER_CONSTRAINT_SERV")){
			   param.put("OBJECT_ID", vStrObject_Id);
			   param.put("PRODUCT_ID", vStrObject_Id);			   
		   }
		 
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strServiceName, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getActionArgs(String Vservice) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("ACTION_ID", Vservice);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0050", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getPropertyStaticParamCard(String vParam_Id,String tParam_Type) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PARAM_ID", vParam_Id);
		   param.put("PARAM_TYPE", tParam_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0022", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getPropertyStaticParamCard(String vParam_Id,String tParam_Type,String serv) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PARAM_ID", vParam_Id);
		   param.put("PARAM_TYPE", tParam_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(serv, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara0(String vCityNo,String vtxtValue,String strService) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("CITY_NO", vCityNo);
		   param.put("SEARCH_DATA", vtxtValue);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara10(String vParam_Id,String vSearchData,String strService) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OBJECT_ID", vParam_Id);
		   param.put("SEARCH_DATA", vSearchData);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara11(String vParam_Id,String strService) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OBJECT_ID", vParam_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara12(String strService) throws Exception{
		 Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara13(String vParam_Id,String vSearchData,String vParam_Type,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("OBJECT_ID", vParam_Id);
		 param.put("SEARCH_DATA", vSearchData);
		 param.put("OBJECT_TYPE", vParam_Type);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara14(String vParam_Id,String vParam_Type,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("OBJECT_ID", vParam_Id);
		 param.put("OBJECT_TYPE", vParam_Type);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara50(String vvObject_Id,String vSearchData,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("PRODUCT_ID", vvObject_Id);
		 param.put("SEARCH_DATA", vSearchData);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara51(String vObject_Id,String strService) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PRODUCT_ID", vObject_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara60(String vParam_Id,String vSearchData,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("PRODUCT_ID", vParam_Id);
		 param.put("SEARCH_DATA", vSearchData);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara61(String vParam_Id,String strService) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("PRODUCT_ID", vParam_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara7(String vGroup_Type_Id,String strService) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("GROUP_TYPE_ID", vGroup_Type_Id);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara8(String vObject_Id,String vvTo_Object_Type,String vRela_Type,String vGroup_No,String strService) throws Exception{
		   Map param = new HashMap() ;
		   List rtlist = new ArrayList();
		   param.put("OFFER_ID", vObject_Id);
		   param.put("OBJECT_TYPE", vvTo_Object_Type);
		   param.put("ROLE_RELA_TYPE", vRela_Type);
		   param.put("GROUP_NO", vGroup_No);
		   param.put("PRODUCT_CLASSIFICATION", vvTo_Object_Type);
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara40(String group_no,String RELA_TYPE,String OFFER_ID,String vSearchData,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("OFFER_ID", OFFER_ID);
		 param.put("RELA_TYPE", RELA_TYPE);
		 param.put("GROUP_NO", group_no);
		 param.put("SEARCH_DATA", vSearchData);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	
	public List getConstraintPara41(String group_no,String RELA_TYPE,String OFFER_ID,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("OFFER_ID", OFFER_ID);
		 param.put("RELA_TYPE", RELA_TYPE);
		 param.put("GROUP_NO", group_no);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara90(String vObject_Id,String vParam_Id,String vSearchData,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("OBJECT_ID", vObject_Id);
		 param.put("PARAM_ID", vParam_Id);
		 param.put("SEARCH_DATA", vSearchData);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getConstraintPara91(String vObject_Id,String vParam_Id,String strService) throws Exception{
		 Map param = new HashMap() ;
		 param.put("OBJECT_ID", vObject_Id);
		 param.put("PARAM_ID", vParam_Id);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService(strService, param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getExpandCardFieldName(String vFieldName) throws Exception{
		 Map param = new HashMap() ;
		 param.put("FIELD_NAME", vFieldName);
		   List rtlist = new ArrayList();
		   rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0045", param)) ;
		   //System.out.println(rtlist.toString());
		   return rtlist;
	}
	
	public List getTpmConstraintById(String vConstraint_Id) throws Exception {
		 Map param = new HashMap() ;
		 param.put("CONSTRAINT_ID", vConstraint_Id);
		 List rtlist = new ArrayList();
		 rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0030", param)) ;
		   //System.out.println(rtlist.toString());
		 return rtlist;
	}
	
	public List getTpmConstraintPathById(String vPath_Id) throws Exception {
		 Map param = new HashMap() ;
		 param.put("PATH_ID", vPath_Id);
		 List rtlist = new ArrayList();
		 rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0031", param)) ;
		   //System.out.println(rtlist.toString());
		 return rtlist;
	}
	
	public List getPara_Values(String PARA_VALUES_ID) throws Exception {
		 Map param = new HashMap() ;
		 param.put("PARA_VALUES_ID", PARA_VALUES_ID);
		 List rtlist = new ArrayList();
		 rtlist = DataTranslate._List(ServiceManager.callSQLService("QPM_PROD_OFFER_CONSTRAINT_0032", param)) ;
		   //System.out.println(rtlist.toString());
		 return rtlist;
	}
	
	public boolean saveTpmConstraint(ArrayList TpmConstraint ) throws Exception {
		Map param = new HashMap() ;
		param.put("TpmConstraint", TpmConstraint) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TpmConstraintBO",
						"saveTpmConstraint" ,param)) ;
		return result ;
	}
	
	
	
	public boolean insertTpmConstraint(HashMap TpmConstraint ) throws Exception {
		Map param = new HashMap() ;
		param.put("TpmConstraint", TpmConstraint) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TpmConstraintBO",
						"insertTpmConstraint" ,param)) ;
		return result ;
	}

	
	public boolean updateTpmConstraint(HashMap TpmConstraint ) throws Exception {
		Map param = new HashMap() ;
		param.put("TpmConstraint", TpmConstraint) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TpmConstraintBO",
						"updateTpmConstraint" ,param)) ;
		
		return result ;
	}
	
	
	public PageModel searchTpmConstraintData(String vObject_id , String vObject_type , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("object_id", vObject_id) ;
		param.put("object_type", vObject_type) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("TpmConstraintBO",
						"searchTpmConstraintData" ,param)) ;
		
		return result ;
	}
	
	
	
	

	public List findTpmConstraintByCond(String constraint_id) throws Exception {
		Map param = getTpmConstraintKeyMap(constraint_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("TpmConstraintBO",
						"findTpmConstraintByCond" ,param)) ;
		
		return result ;
	}
	
	
	public boolean deleteTpmConstraintById(String constraint_id) throws Exception {
		Map param = getTpmConstraintKeyMap(constraint_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("TpmConstraintBO",
						"deleteTpmConstraintById" ,param)) ;
		
		return result ;
	}
	
	private Map getTpmConstraintKeyMap(String constraint_id){
		Map param = new HashMap() ;
				
		param.put("constraint_id", constraint_id) ;
				
		return param ;
	}
}
