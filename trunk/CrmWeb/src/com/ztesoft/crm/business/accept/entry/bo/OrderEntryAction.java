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
 * ������̨�����ͳһ���
 * */
public class OrderEntryAction extends DictAction {

	/**
	 * ��̨������񹫹���ڷ�����ͨ����ͬ�ķ���ǩ��ִ�в�ͬ�ķ���
	 * @dto�����װ����buffalo��������Ĳ�����װ
	 * */
	public Object execute(DynamicDict dto) throws Exception {
		//��buffalo����װ��hashmap
		Map parameterMap= Const.getParam(dto);	
		//��ȡҵ�����
		String methodName = Const.getStrValue(parameterMap, Actions.EXECUTE_METHOD);
		//���ݲ�ͬ�ķ���ִ����Ӧ��ҵ����
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
	 * ���ۻ��Ʒ�����ύ���
	 * @param serviceName��̨���������
	 * @param parameterMap������װ�Ĳ���
	 * @throws Exception 
	 * */
	public Object accept(Map parameterMap) throws Exception{
		//��ȡ����		
		Object parameter =parameterMap.get(Actions.PARAMETER);
		//ִ��ҵ������		
		AcceptRequest request=(AcceptRequest)parameter;
		String serviceName=null;
		
		if (Services.OFFER_SERV_ACCEPT.equals(request.getBusi_type())) {
			serviceName=Services.OFFER_SERV_ACCEPT;
		}else if (Services.OFFER_ACCEPT.equals(request.getBusi_type())) {
			serviceName=Services.OFFER_ACCEPT;
		}if(Services.SERV_ACCEPT.equals(request.getBusi_type())){			
			serviceName=Services.SERV_ACCEPT;
		}	
		
		//��װ��������
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
		
		//ִ��ҵ������
		BusiEngine.set(Actions.COMMON_DATA, commonData);
		//��ȡ������
		
		BusiResult busiResult = BusiEngine.call(serviceName, parameter);
		
		if(!busiResult.empty())
			return (String)busiResult.first();
		
		return null;
	}
	
	/**
	 * �����ͻ���������
	 * @param serviceName��̨���������
	 * @param parameterMap������װ�Ĳ���
	 * @throws Exception 
	 * */
	public String create(Map parameterMap) throws Exception{
		//ִ��ҵ������
		BusiEngine busiEngine = new BusiEngine();
		busiEngine.set("CUST_ORDER", parameterMap);
		BusiResult busiResult=busiEngine.call(Services.CUST_ORDER_CREATE, null);
		if(!busiResult.empty())
			return (String)busiResult.first();
		return null;

	}
	
	/**
	 * �����ʼ��
	 * @param serviceName��̨���������
	 * @param parameterMap������װ�Ĳ���
	 * @throws Exception 
	 * */
	public void init(Map parameterMap) throws Exception {
		//��ȡ����		
		InitRequest parameter = (InitRequest)parameterMap.get(Actions.PARAMETER);
		//ִ��ҵ������
		
		String serviceName=null;
		
		//�жϵ����������ʼ��
		if (((String)parameter.getService_offer_id()).equals("�����ʼ��")) {
			serviceName = "initServ";
		}		
		
		//��װ��������
		CommonData commondata = new CommonData();
		
		commondata.setAttributes(parameter.unloadtoMap());
		
		//ִ��ҵ������
		BusiEngine.set(Actions.COMMON_DATA, commondata);
		
		//��ȡ������		
		BusiEngine.call(serviceName, parameter);
	}
	
	
	
	

}
