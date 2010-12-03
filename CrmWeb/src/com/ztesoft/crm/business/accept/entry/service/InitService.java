package com.ztesoft.crm.business.accept.entry.service;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.model.InitRequest;
import com.ztesoft.crm.business.common.model.VInitRequest;

public class InitService {

	
	/**
	 * 获取下一个可用的流水，例如主产品实例标识。
	 * */
	public void getNextSequence(){
		Map param = new HashMap();
		SequenceManageDAOImpl getSequence = new SequenceManageDAOImpl();
		String serv_id = getSequence.getNextSequence(
				"SERV", "SERV_ID");
		param.put("serv_id", serv_id);
	}
	
	/**
	 * 订单受理初始化，并进行相应的自动处理，
	 * 并返回需要录入的最小信息(在核心逻辑层需要分为两个服务)。
	 * */
	public void initlize(VInitRequest vinitrequest) throws Exception {
		InitRequest initrequest = new InitRequest();
		
		//是否有更好的方法？
		initrequest.setComp_inst_id(vinitrequest.getComp_inst_id());
		initrequest.setCust_id(vinitrequest.getCust_id());		
		initrequest.setCust_ord_id(vinitrequest.getCust_ord_id());
		initrequest.setServ_id(vinitrequest.getComp_inst_id());		
		initrequest.setService_offer_id(vinitrequest.getService_offer_id());
		initrequest.setSite_no(vinitrequest.getSite_no());
		initrequest.setStaff_no(vinitrequest.getStaff_no());
		
		Map param = new HashMap();
		//转换
		param.put(Actions.PARAMETER, initrequest);
		//方法定义
		param.put(Actions.EXECUTE_METHOD, Actions.INIT);
		//后台订单服务
		Object result = ServiceManager.callJavaBeanService(Actions.ORDER_ENTRY_ACTION, Actions.EXECUTE, param);
		
	}
}
