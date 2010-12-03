package com.ztesoft.crm.business.accept.entry.bo;

import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.consts.Services;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.model.AcceptRequest;
import com.ztesoft.crm.business.common.model.InitRequest;
import com.ztesoft.crm.business.common.sem.engine.BusiEngine;
import com.ztesoft.crm.business.common.sem.engine.BusiResult;

/**
 * 订单后台服务的统一入口
 * */
public class OrderEntryAction extends DictAction {

	/**
	 * 后台保存服务公共入口方法，通过不同的服务签名执行不同的方法
	 * @dto里面封装的是buffalo请求过来的参数封装
	 * */
	public Object execute(DynamicDict dto) throws Exception {
		//在buffalo中组装的hashmap
		Map parameterMap= Const.getParam(dto);	
		//获取业务服务
		String methodName = Const.getStrValue(parameterMap, Actions.EXECUTE_METHOD);
		//根据不同的服务执行相应的业务动作
		if(Actions.ACCEPT.equals(methodName)){
			return this.accept(parameterMap);
		}else if(Actions.CREATE_CUST_ORDER.equals(methodName)){
			return this.create(parameterMap);
		}/*else if(Actions.INIT.equals(methodName)) {
			this.init(parameterMap);
		}*/
		
		return null;
		
	}
	/**
	 * 销售或产品保存提交入口
	 * @param serviceName后台服务的名字
	 * @param parameterMap界面组装的参数
	 * @throws Exception 
	 * */
	public Object accept(Map parameterMap) throws Exception{
		//获取参数		
		Object parameter =parameterMap.get(Actions.PARAMETER);
		//执行业务引擎		
		AcceptRequest request=(AcceptRequest)parameter;
		String serviceName=null;
		
		if (Services.OFFER_SERV_ACCEPT.equals(request.getBusi_type())) {
			serviceName=Services.OFFER_SERV_ACCEPT;
		}else if (Services.OFFER_ACCEPT.equals(request.getBusi_type())) {
			serviceName=Services.OFFER_ACCEPT;
		}if(Services.SERV_ACCEPT.equals(request.getBusi_type())){			
			serviceName=Services.SERV_ACCEPT;
		}	
		
		//封装共享数据
		CommonData commonData=new CommonData();
		commonData.setServs(request.getServs());
		commonData.setCompInsts(request.getCompInsts());
		commonData.setServIds(request.getServIds());
		//commonData.set
		commonData.set(Keys.CUST_ORD_ID, request.getCust_ord_id());
		commonData.set(Keys.CUST_ID, request.getCust_id());
		commonData.set(Keys.STAFF_NO, request.getStaff_no());
		commonData.set(Keys.SITE_NO, request.getSite_no());
		commonData.set(Keys.LAN_ID, request.getLan_id());
		commonData.set(Keys.BUSINESS_ID, request.getBusiness_id());
		commonData.set(Keys.ASK_ID, request.getAsk_id());
		
		//执行业务引擎
		BusiEngine.set(Actions.COMMON_DATA, commonData);
		//获取服务定义
		
		BusiResult busiResult = BusiEngine.call(serviceName, parameter);
		
		if(!busiResult.empty())
			return (String)busiResult.first();
		
		return null;
	}
	
	/**
	 * 创建客户订单操作
	 * @param serviceName后台服务的名字
	 * @param parameterMap界面组装的参数
	 * @throws Exception 
	 * */
	public String create(Map parameterMap) throws Exception{
		//执行业务引擎
		BusiEngine busiEngine = new BusiEngine();
		busiEngine.set("CUST_ORDER", parameterMap);
		BusiResult busiResult=busiEngine.call(Services.CUST_ORDER_CREATE, null);
		if(!busiResult.empty())
			return (String)busiResult.first();
		return null;

	}
	
	/**
	 * 受理初始化
	 * @param serviceName后台服务的名字
	 * @param parameterMap界面组装的参数
	 * @throws Exception 
	 * */
	public void init(Map parameterMap) throws Exception {
		//获取参数		
		InitRequest parameter = (InitRequest)parameterMap.get(Actions.PARAMETER);
		//执行业务引擎
		
		String serviceName=null;
		
		//判断到底是哪类初始化
		if (((String)parameter.getService_offer_id()).equals("受理初始化")) {
			serviceName = "initServ";
		}		
		
		//封装共享数据
		CommonData commondata = new CommonData();
		
		commondata.setAttributes(parameter.unloadtoMap());
		
		//执行业务引擎
		BusiEngine.set(Actions.COMMON_DATA, commondata);
		
		//获取服务定义		
		BusiEngine.call(serviceName, parameter);
	}
	
	
	
	

}
