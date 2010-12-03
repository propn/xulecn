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
	 * ���ǰ̨����Ʒ���������������װ��ͳһ��� �ύ����̨����
	 * */
	public String offerSave(OfferData offerData) throws Exception {
		//���õ�½��Ϣ
		HashMap respond = (HashMap)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRtnParamsHashMap");
		offerData.setLoginInfo(respond);
		
		Map param = new HashMap();
		
		param.put(Actions.PARAMETER, offerData);//��ǰ̨��������ݷ���param
		//��������
		param.put(Actions.EXECUTE_METHOD, Actions.CREATE_OFFER_BACK_DATA);
		//������װ
		Object result = ServiceManager.callJavaBeanService(Actions.DATA_ACTION, Actions.EXECUTE, param);
		
		VAcceptRequest request = new VAcceptRequest();
		
		request = (VAcceptRequest)result;//��ȡ��װ�õ�ҵ�����
		
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
		//ҳ�洫�������
		param.put(Actions.PARAMETER, servData);
		//���÷���
		param.put(Actions.EXECUTE_METHOD, Actions.CREATE_SERV_DATA);
		
		VAcceptRequest vrequest = new VAcceptRequest();
		vrequest = (VAcceptRequest) ServiceManager.callJavaBeanService(Actions.DATA_ACTION, Actions.EXECUTE, param);
		
		AcceptService accepServ = new AcceptService();
		//���ú�̨���档
		accepServ.accept(vrequest);
	}
}
