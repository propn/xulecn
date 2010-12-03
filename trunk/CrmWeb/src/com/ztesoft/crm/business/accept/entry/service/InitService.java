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
	 * ��ȡ��һ�����õ���ˮ����������Ʒʵ����ʶ��
	 * */
	public void getNextSequence(){
		Map param = new HashMap();
		SequenceManageDAOImpl getSequence = new SequenceManageDAOImpl();
		String serv_id = getSequence.getNextSequence(
				"SERV", "SERV_ID");
		param.put("serv_id", serv_id);
	}
	
	/**
	 * ���������ʼ������������Ӧ���Զ�����
	 * ��������Ҫ¼�����С��Ϣ(�ں����߼�����Ҫ��Ϊ��������)��
	 * */
	public void initlize(VInitRequest vinitrequest) throws Exception {
		InitRequest initrequest = new InitRequest();
		
		//�Ƿ��и��õķ�����
		initrequest.setComp_inst_id(vinitrequest.getComp_inst_id());
		initrequest.setCust_id(vinitrequest.getCust_id());		
		initrequest.setCust_ord_id(vinitrequest.getCust_ord_id());
		initrequest.setServ_id(vinitrequest.getComp_inst_id());		
		initrequest.setService_offer_id(vinitrequest.getService_offer_id());
		initrequest.setSite_no(vinitrequest.getSite_no());
		initrequest.setStaff_no(vinitrequest.getStaff_no());
		
		Map param = new HashMap();
		//ת��
		param.put(Actions.PARAMETER, initrequest);
		//��������
		param.put(Actions.EXECUTE_METHOD, Actions.INIT);
		//��̨��������
		Object result = ServiceManager.callJavaBeanService(Actions.ORDER_ENTRY_ACTION, Actions.EXECUTE, param);
		
	}
}
