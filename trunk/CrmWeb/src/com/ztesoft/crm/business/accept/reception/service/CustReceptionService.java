package com.ztesoft.crm.business.accept.reception.service;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.business.common.consts.ReceptionActions;
import com.ztesoft.crm.business.common.consts.Services;

/**
 * 客户接待调用服务入口
 * @author lindy
 *
 */
public class CustReceptionService {
	
	public CustReceptionService(){
		
	}
	
	public PageModel getCustList(String lanId,String searchType,String searchValue,
				int pageIndex, int pageSize)throws Exception{
		//界面传入参数
		Map param = new HashMap();
		param.put("lanId", lanId);
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		//param.put("pageIndex", pageIndex);
		//param.put("pageSize", pageSize);
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_CUST_LIST, param);
		return (PageModel) result ;
	}
	
	/**
	 * 获取需要推荐给客户的销售品。
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCustRecommend(String custId)throws Exception{
		//界面传入参数
		Map param = new HashMap();
		param.put("custId", custId);
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_OFFER_RECOMMEND, param);
		return (ArrayList) result ;
	}
	
	/**
	 * 查询推销销售品的描述信息。
	 * @param offerId
	 * @return
	 * @throws Exception
	 */
	public String showOfferRecommend(String offerId)throws Exception{
		HashMap param = new HashMap();
		//界面传入参数
		param.put("offerId", offerId);
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.SHOW_OFFER_RECOMMEND, param);
		
		return (String) result;
	}
	
	/**
	 * 查询客户的销售品实例信息。
	 * @param custId
	 * @param pageCount
	 * @param currentPages
	 * @param accNbr    //精确定位的号码
	 * @param queryNbr  //用于优先显示的号码
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstance(String custId, int pageCount, int currentPages, String accNbr,String queryNbr)throws Exception{
		//获取工号
		HashMap loginMap = (HashMap)RequestContext.getContext().getHttpSession().getAttribute("LoginRtnParamsHashMap");
		String operId = (String) loginMap.get("vg_oper_id");
		//设置参数。
		HashMap paramMap = new HashMap();
		paramMap.put("custId", custId);
		paramMap.put("operId", operId);
		paramMap.put("accNbr", accNbr);
		paramMap.put("queryNbr", queryNbr);
		//paramMap.put("pageCount", pageCount);
		//paramMap.put("currentPages", currentPages);
		
		//后台订单服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.SHOW_OFFER_INSTANCE, paramMap);
		//返回对象
		return (HashMap) result;
	}
	
	/**
	 * 查询销售品服务
	 * @param source  调用的入口
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffService(String source,HashMap paramIn) throws Exception{
		//设置参数。
		HashMap paramMap = new HashMap();
		paramMap.put("source", source);
		paramMap.put("param", paramIn);
		
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.SHOW_OFFER_SERVICE, paramMap);
		
		return (HashMap) result;
	}
	
	/**
	 * 查询销售品实例的详细信息。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstanceDetail(HashMap paramIn) throws Exception{

		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_INSTANCE_DETAIL, paramIn);
		
		return (HashMap) result;
	}
	
	/**
	 * 在客户接待页面展示客户的过程单。
	 * @param custId
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public HashMap showCustOrdInfo(String custId,String source,int currentPages, int pageCount) throws Exception{
		//设置参数。
		HashMap paramMap = new HashMap();
		paramMap.put("custId", custId);
		paramMap.put("source", source);
		//paramMap.put("currentPages", currentPages);
		//paramMap.put("pageCount", pageCount);
		
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_CUST_ORD_INFO, paramMap);
		
		return (HashMap) result;
	}
	
	/**
	 * 根据客户标识查询客户的帐户。
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public PageModel getAcctList(String custId,int pageIndex, int pageSize) throws Exception{
		//界面传入参数
		Map param = new HashMap();
		param.put("cust_id", custId);
		//param.put("pageIndex", pageIndex);
		//param.put("pageSize", pageSize);
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_ACCT_LIST, param);
		return (PageModel) result ;
	}
	
	/**
	 * 根据表名、字段名获取序列号。
	 * @param tableCode
	 * @param fieldCode
	 * @return
	 * @throws Exception
	 */
	public String getSeqId(String tableCode,String fieldCode) throws Exception{
		//界面传入参数
		Map param = new HashMap();
		param.put("table_code", tableCode);
		param.put("field_code", fieldCode);
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_SEQ_ID,param);
		return (String) result ;
	}
	
	/**
	 * 查询客户后，需要获取的客户相关信息。
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public HashMap getInitInfoByCustId(String custId) throws Exception{
		//界面传入参数
		Map param = new HashMap();
		param.put("cust_id", custId);
		//后台服务
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.QUERY_CUST_INIT_INFO,param);
		return (HashMap) result ;
	}
}
