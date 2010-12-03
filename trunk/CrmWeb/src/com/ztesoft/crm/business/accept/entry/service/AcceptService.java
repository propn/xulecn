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
	 * ��ɻ�������Ʒ���������Ʒ�������˶���������ĵ���������ҵ���������
	 * */
	public Object accept(VAcceptRequest request) throws Exception {
		DataChange dataChange=new DataChange();
		
		Map param = new HashMap();
		
		//���洫�����
		List servs = dataChange.getServsFromReq(request);
		List compInsts = dataChange.getCompInstsFromReq(request);
		HashMap servIds = request.getServIds();//����Ʒʵ��ID
		AcceptRequest acceptRequest= new AcceptRequest();	
		acceptRequest.setServIds(servIds);
		acceptRequest.setServs(servs);	
		acceptRequest.setCompInsts(compInsts);
		
		//ȡǰ̨���������Ϣ
		acceptRequest.getBaseInfofromVOrderRequest(request);
		
		//ת��
		param.put(Actions.PARAMETER, acceptRequest);
		//��������
		param.put(Actions.EXECUTE_METHOD, Actions.ACCEPT);
		//��̨��������
		Object result = ServiceManager.callJavaBeanService(Actions.ORDER_ENTRY_ACTION, Actions.EXECUTE, param);
		
		return result;
		
	}
	/**
	 * ���������Ŀͻ�������Ϣ
	 * @throws Exception 
	 * */
	public String create(String custId) throws Exception{
		Map param = new HashMap();
		//��������
		param.put(Actions.EXECUTE_METHOD, Actions.CREATE_CUST_ORDER);
	
		//��ȡӪҵԱ�ı�������Ӫҵ������Ϣ��
		GlobalVariableHelper globalHelper = new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
		String lanId = globalHelper.getVariable(globalHelper.LAN_ID);
		String businessId = globalHelper.getVariable(globalHelper.BUSINESS_ID);
		//����Ա��Ϣ
		String staff_no = globalHelper.getVariable(globalHelper.OPER_ID);
		String site_no = globalHelper.getVariable(globalHelper.OPER_ORG_CODE);  //����Ա���ڵĲ��š�
		String ask_source = globalHelper.getVariable(globalHelper.ACCPET_SOURCE);
		
		param.put(ORD_CUSTOMER_ORDER.cust_id, custId);
		param.put(ORD_CUSTOMER_ORDER.lan_id, lanId);
		param.put(ORD_CUSTOMER_ORDER.business_id, businessId);
		param.put(ORD_CUSTOMER_ORDER.staff_no, staff_no);
		param.put(ORD_CUSTOMER_ORDER.site_no, site_no);
		param.put(ORD_CUSTOMER_ORDER.ask_source, ask_source);
		
	
		//��̨��������
		String result = DataTranslate._String(ServiceManager.callJavaBeanService(Actions.ORDER_ENTRY_ACTION, Actions.EXECUTE, param));
		
		return result;

	}
	/**
	 * ��������ͻ�������ȡ��
	 * */
	public void cancel(){
		
	}
	
	/**
	 * ���һ����������Ʒ�����������Ʒ������ȡ��
	 * */
	public void cancelOrder(){
		
	}
	/**
	 * ���һ����������Ʒ�����������Ʒ�����ĳ���
	 * */
	public void withdraw(){
		
	}
	

	

}
