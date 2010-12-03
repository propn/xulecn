package com.ztesoft.crm.business.accept.entry.bo.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV;
import com.ztesoft.crm.business.common.logic.auto.AddServProduct;
import com.ztesoft.crm.business.common.logic.auto.DefServAttr;
import com.ztesoft.crm.business.common.logic.auto.DelMutexOfferInst;
import com.ztesoft.crm.business.common.logic.auto.DelServAcct;
import com.ztesoft.crm.business.common.logic.auto.DelServProduct;
import com.ztesoft.crm.business.common.logic.auto.ProcessParameter;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.utils.DataUtil;


/**
 * ����/VsopWeb/src/com/ztesoft/crm/business/common/logic
 * ����Ʒ����/����Զ�������
 * */
public class AcceptHandler implements BusiComponent{

	public Object execute() throws Exception{

		CommonData data = CommonData.getData();
		HashMap common = data.getAttributes();
		List compInsts = data.getCompInsts();//��ȡ����Ʒʵ������
		List servs = data.getServs();//��ȡ����Ʒʵ��ID
		ProcessParameter parameter = new ProcessParameter();
		parameter.setCompInsts(compInsts);//��������Ʒʵ������
		
		DelMutexOfferInst delMutexOfferInst = new DelMutexOfferInst();//ɾ����Ч/��������Ʒ������
		//ɾ����Ч/�������۵�ʵ�ַ���
		delMutexOfferInst.execute(parameter);
		
		if(servs!=null && servs.size()!=0){
			//ǰ̨����Ķ�����׼ȷ ��Ҫ�˷������и���
			DataUtil.getInst().refreshActionType(servs, "Serv");
			for(int i =0;i<servs.size();i++){
				List offerDetailIds = new ArrayList();//�����Ч����Ʒ��ϸ��������
				Serv serv = (Serv)servs.get(i);
				String serv_id = serv.get(Serv.SERV_ID);
				if(ServConsts.ACTION_TYPE_A.equals(serv.get(Serv.ACTION_TYPE))){
					serv.set(SERV.acc_nbr, "-100000");//���Ϊ���� ��Ϊ��Ĭ��һ������
				}
				//��ȡ��Ч����Ʒ��ϸ����
				offerDetailIds = DataUtil.getInst().getOfferDetailIds(compInsts, serv_id);
				/****************Ĭ������Ʒ������Ϣ********************/
				//ʵ���� Ĭ������Ʒ������Ϣ�Զ�������
				DefServAttr defServAttr = new DefServAttr();
				parameter = new ProcessParameter();//����ʵ������������
				parameter.setServ(serv);
				parameter.setOfferDetailIds(offerDetailIds);
				parameter.setCommon(data.getAttributes());
				// Ĭ������Ʒ������Ϣ�Զ������� ִ�з��� 
				defServAttr.execute(parameter);
				/****************���ӱ��븽����Ʒ********************/
				AddServProduct addServProduct = new AddServProduct();
				addServProduct.execute(parameter);
				/***************ɾ�����⸽����Ʒ������ڷ����ӵĲ�Ʒ��************************/
				if(!ServConsts.ACTION_TYPE_A.equals(serv.get(SERV.action_type))){
					DelServProduct delServProduct = new DelServProduct();
					delServProduct.execute(parameter);
				}
				
			}
		} 
		/***************Ĭ�������ƣ�������������Ʒ��ҵ�� ��ô����������Ʒ��һ��������Ĭ���ʣ�
		 * ��������ǻ�������Ʒ��ҵ����ô������Ӧ�ģ����ӻ����޸ģ��Ļ������۽���������Ĭ��
		(˵�����κ�һ��ҵ�������������һ��������Ʒ������������Ʒ�Ǹ�������Ʒ�������Զ�����)��*****/
		if(compInsts!=null && compInsts.size()!=0){
			for(int i = 0;i<compInsts.size();i++){
				CompInst compInst = (CompInst)compInsts.get(i);
				parameter = new ProcessParameter();//����ʵ������������
				parameter.setServs(servs);
				parameter.setCompInst(compInst);
				DelServAcct delServAcct = new DelServAcct();
				delServAcct.execute(parameter);
			}
		}
		return null;
	}
	
	

}
