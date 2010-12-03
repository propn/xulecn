package com.ztesoft.crm.business.common.logic.auto;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInst;
/**
 * @author liupeidawn
 * 
 */
public class DelMutexOfferInst implements AutoProcessor {

	/* �������µ�����Ʒ��ϵͳ�Զ���ɾ�����������Ʒ�Ļ�������Ʒ����Ч����Ʒ��
	 * ���� List CompInsts  Map common 
	 */
	public void execute(ProcessParameter parameter) throws Exception{
		/******************����Ʒ���⴦��*********************************/
			List compInsts = parameter.compInsts;
			//List servs = parameter.serv;
			List toAddOfferInstDetails = new ArrayList();//����Ϊ���ӵ�����Ʒ��ϸ
			List todelOfferInsts = new ArrayList();//��Ҫɾ��������Ʒ
			List addOfferInsts = new ArrayList();//����Ϊ���ӵ�����Ʒ
			List useOfferInsts = new ArrayList();//����Ϊ���û򱣳ֵ�����Ʒ
			List allInsts = new ArrayList();//��������Ʒʵ��
			List allInstsDetails = new ArrayList();//��������Ʒʵ����ϸ
			
			
			if(compInsts!=null&&compInsts.size()!=0){
				for(int i=0;i<compInsts.size();i++){
					CompInst compInst = (CompInst)compInsts.get(i);
/*					List offerInstDetails = compInst.getOfferInstDetails();
					//ȷ������Ҫ���ӵ�����Ʒʵ����ϸ��Ϣ 
					if(offerInstDetails!=null&&offerInstDetails.size()!=0){
						for(int j = 0;j<offerInstDetails.size();j++){
							OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(j);
							if(OfferConsts.ACTION_TYPE_A.equals(offerInstDetail.get(CompInst.ACTION_TYPE))){
								toAddOfferInstDetails.add(offerInstDetail);
							}
							allInstsDetails.add(offerInstDetail);
						}
					}*/
					//��ȡ����Ϊ���Ӻ����õ�����Ʒʵ��
					List offerInsts = compInst.getOfferInsts();
					if(offerInsts!=null&&offerInsts.size()!=0){
						for(int j = 0;j<offerInsts.size();j++){
							OfferInst offerInst = (OfferInst)offerInsts.get(j);
							if(OfferConsts.ACTION_TYPE_A.equals(offerInst.get(CompInst.ACTION_TYPE))){
								addOfferInsts.add(offerInst);
							}else if(!OfferConsts.ACTION_TYPE_D.equals(offerInst.get(CompInst.ACTION_TYPE))){
								useOfferInsts.add(offerInst);
							} 
							allInsts.add(offerInst);
						}
					}
				}
				List mutexOfferList = SpecsData.getMutexOffers(addOfferInsts);//��ȡ����������Ʒ���������Ʒ�б�
				/**************��������Ʒ������װ����Ʒ���бȽ�**************/
				if(mutexOfferList!=null&&mutexOfferList.size()!=0 && useOfferInsts!=null&&useOfferInsts.size()!=0){
					for(int i =0;i<mutexOfferList.size();i++){
						OfferInst offerInst = (OfferInst)mutexOfferList.get(i);
						String mutexOfferId = offerInst.get(OFFER_INST.product_offer_id);
						for(int j = 0;j<useOfferInsts.size();j++){
							OfferInst useOfferInst = (OfferInst)useOfferInsts.get(j);
							//�������õ�����װ�Ļ��� ��ôɾ�������õģ�Ŀǰû�п������ñ�ѡ�������
							if(useOfferInst.get(OFFER_INST.product_offer_id).equals(mutexOfferId)){
								SpecsData.removeMutexOffers(mutexOfferId, compInsts);
							}
						}
					}
				}
				/**************��װ������Ʒ������װ����Ʒ���бȽ� ������� ɾ������һ��**************/
				if(mutexOfferList!=null&&mutexOfferList.size()!=0 && addOfferInsts!=null&&addOfferInsts.size()!=0){
					for(int i =0;i<mutexOfferList.size();i++){
						OfferInst offerInst = (OfferInst)mutexOfferList.get(i);
						String mutexOfferId = offerInst.get(OFFER_INST.product_offer_id);
						for(int j = 0;j<addOfferInsts.size();j++){
							OfferInst addOfferInst = (OfferInst)addOfferInsts.get(j);
							//������װ������װ�Ļ��� ��ôɾ��������һ���ģ�Ŀǰû�п������ñ�ѡ�������
							if(addOfferInst.get(OFFER_INST.product_offer_id).equals(mutexOfferId)){
								SpecsData.removeMutexOffers(mutexOfferId, compInsts);
							}
						}
					}
				}
				
			}
			/******************��Ч����Ʒ����*************************************/
			
			
			
	}

}
