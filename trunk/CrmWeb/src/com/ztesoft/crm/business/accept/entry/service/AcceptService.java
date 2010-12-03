package com.ztesoft.crm.business.accept.entry.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.consts.BusiTables.ORD_CUSTOMER_ORDER;
import com.ztesoft.crm.business.common.logic.tools.DataChange;
import com.ztesoft.crm.business.common.model.AcceptRequest;
import com.ztesoft.crm.business.common.model.VAcceptRequest;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class AcceptService {

	/**
	 * 完成基础销售品、组合销售品订购、退订、变更、改单、修正等业务的受理保存
	 * */
	public Object accept(VAcceptRequest request) throws Exception {
		DataChange dataChange=new DataChange();
		
		Map param = new HashMap();
		
		//界面传入参数
		List servs = dataChange.getServsFromReq(request);
		List compInsts = dataChange.getCompInstsFromReq(request);
		HashMap servIds = request.getServIds();//主产品实例ID
		AcceptRequest acceptRequest= new AcceptRequest();	
		acceptRequest.setServIds(servIds);
		acceptRequest.setServs(servs);	
		acceptRequest.setCompInsts(compInsts);
		
		//取前台传入基本信息
		acceptRequest.getBaseInfofromVOrderRequest(request);
		
		//转换
		param.put(Actions.PARAMETER, acceptRequest);
		//方法定义
		param.put(Actions.EXECUTE_METHOD, Actions.ACCEPT);
		//后台订单服务
		Object result = ServiceManager.callJavaBeanService(Actions.ORDER_ENTRY_ACTION, Actions.EXECUTE, param);
		
		return result;
		
	}
	/**
	 * 创建基本的客户订单信息
	 * @throws Exception 
	 * */
	public String create(String custId) throws Exception{
		Map param = new HashMap();
		//方法定义
		param.put(Actions.EXECUTE_METHOD, Actions.CREATE_CUST_ORDER);
	
		//获取营业员的本地网、营业区等信息，
		GlobalVariableHelper globalHelper = new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
		String lanId = globalHelper.getVariable(globalHelper.LAN_ID);
		String businessId = globalHelper.getVariable(globalHelper.BUSINESS_ID);
		//操作员信息
		String staff_no = globalHelper.getVariable(globalHelper.OPER_ID);
		String site_no = globalHelper.getVariable(globalHelper.OPER_ORG_CODE);  //操作员所在的部门。
		String ask_source = globalHelper.getVariable(globalHelper.ACCPET_SOURCE);
		
		param.put(ORD_CUSTOMER_ORDER.cust_id, custId);
		param.put(ORD_CUSTOMER_ORDER.lan_id, lanId);
		param.put(ORD_CUSTOMER_ORDER.business_id, businessId);
		param.put(ORD_CUSTOMER_ORDER.staff_no, staff_no);
		param.put(ORD_CUSTOMER_ORDER.site_no, site_no);
		param.put(ORD_CUSTOMER_ORDER.ask_source, ask_source);
		
	
		//后台订单服务
		String result = DataTranslate._String(ServiceManager.callJavaBeanService(Actions.ORDER_ENTRY_ACTION, Actions.EXECUTE, param));
		
		return result;

	}
	/**
	 * 完成整个客户订单的取消
	 * */
	public void cancel(){
		
	}
	
	/**
	 * 完成一个基础销售品或者组合销售品定单的取消
	 * */
	public void cancelOrder(){
		
	}
	/**
	 * 完成一个基础销售品或者组合销售品定单的撤单
	 * */
	public void withdraw(){
		
	}
	

	

}
