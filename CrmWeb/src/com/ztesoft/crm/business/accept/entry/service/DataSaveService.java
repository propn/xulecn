package com.ztesoft.crm.business.accept.entry.service;

import java.util.HashMap;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.model.OfferData;
import com.ztesoft.crm.business.common.model.ServData;
import com.ztesoft.crm.business.common.model.VAcceptRequest;


public class DataSaveService {

	/**
	 * 完成前台销售品数据受理的数据组装的统一入口 提交给后台处理
	 * */
	public String offerSave(OfferData offerData) throws Exception {
		//设置登陆信息
		HashMap respond = (HashMap)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRtnParamsHashMap");
		offerData.setLoginInfo(respond);
		
		Map param = new HashMap();
		
		param.put(Actions.PARAMETER, offerData);//将前台传入的数据放入param
		//方法定义
		param.put(Actions.EXECUTE_METHOD, Actions.CREATE_OFFER_BACK_DATA);
		//数据组装
		Object result = ServiceManager.callJavaBeanService(Actions.DATA_ACTION, Actions.EXECUTE, param);
		
		VAcceptRequest request = new VAcceptRequest();
		
		request = (VAcceptRequest)result;//获取组装好的业务对象
		
		AcceptService accepServ = new AcceptService();
		
		return (String)accepServ.accept(request);
		
	}

	/**
	 * 
	 * @param servData
	 * @throws Exception
	 */
	public void servSave(ServData servData) throws Exception{
		Map param = new HashMap();
		//页面传入的数据
		param.put(Actions.PARAMETER, servData);
		//调用方法
		param.put(Actions.EXECUTE_METHOD, Actions.CREATE_SERV_DATA);
		
		VAcceptRequest vrequest = new VAcceptRequest();
		vrequest = (VAcceptRequest) ServiceManager.callJavaBeanService(Actions.DATA_ACTION, Actions.EXECUTE, param);
		
		AcceptService accepServ = new AcceptService();
		//调用后台保存。
		accepServ.accept(vrequest);
	}
}
