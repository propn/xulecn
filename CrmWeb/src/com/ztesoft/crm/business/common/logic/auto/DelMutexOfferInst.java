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

	/* 增加了新的销售品后，系统自动的删除这个新销售品的互斥销售品和无效销售品。
	 * 输入 List CompInsts  Map common 
	 */
	public void execute(ProcessParameter parameter) throws Exception{
		/******************销售品互斥处理*********************************/
			List compInsts = parameter.compInsts;
			//List servs = parameter.serv;
			List toAddOfferInstDetails = new ArrayList();//动作为增加的销售品明细
			List todelOfferInsts = new ArrayList();//需要删除的销售品
			List addOfferInsts = new ArrayList();//动作为增加的销售品
			List useOfferInsts = new ArrayList();//动作为在用或保持的销售品
			List allInsts = new ArrayList();//所有销售品实例
			List allInstsDetails = new ArrayList();//所有销售品实例明细
			
			
			if(compInsts!=null&&compInsts.size()!=0){
				for(int i=0;i<compInsts.size();i++){
					CompInst compInst = (CompInst)compInsts.get(i);
/*					List offerInstDetails = compInst.getOfferInstDetails();
					//确定所有要增加的销售品实例明细信息 
					if(offerInstDetails!=null&&offerInstDetails.size()!=0){
						for(int j = 0;j<offerInstDetails.size();j++){
							OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(j);
							if(OfferConsts.ACTION_TYPE_A.equals(offerInstDetail.get(CompInst.ACTION_TYPE))){
								toAddOfferInstDetails.add(offerInstDetail);
							}
							allInstsDetails.add(offerInstDetail);
						}
					}*/
					//获取动作为增加和在用的销售品实例
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
				List mutexOfferList = SpecsData.getMutexOffers(addOfferInsts);//获取与新增销售品互斥的销售品列表
				/**************在用销售品包与新装销售品进行比较**************/
				if(mutexOfferList!=null&&mutexOfferList.size()!=0 && useOfferInsts!=null&&useOfferInsts.size()!=0){
					for(int i =0;i<mutexOfferList.size();i++){
						OfferInst offerInst = (OfferInst)mutexOfferList.get(i);
						String mutexOfferId = offerInst.get(OFFER_INST.product_offer_id);
						for(int j = 0;j<useOfferInsts.size();j++){
							OfferInst useOfferInst = (OfferInst)useOfferInsts.get(j);
							//发现在用的与新装的互斥 那么删除掉在用的（目前没有考虑在用必选的情况）
							if(useOfferInst.get(OFFER_INST.product_offer_id).equals(mutexOfferId)){
								SpecsData.removeMutexOffers(mutexOfferId, compInsts);
							}
						}
					}
				}
				/**************新装的销售品包与新装销售品进行比较 如果互斥 删除其中一个**************/
				if(mutexOfferList!=null&&mutexOfferList.size()!=0 && addOfferInsts!=null&&addOfferInsts.size()!=0){
					for(int i =0;i<mutexOfferList.size();i++){
						OfferInst offerInst = (OfferInst)mutexOfferList.get(i);
						String mutexOfferId = offerInst.get(OFFER_INST.product_offer_id);
						for(int j = 0;j<addOfferInsts.size();j++){
							OfferInst addOfferInst = (OfferInst)addOfferInsts.get(j);
							//发现新装的与新装的互斥 那么删除掉其中一个的（目前没有考虑在用必选的情况）
							if(addOfferInst.get(OFFER_INST.product_offer_id).equals(mutexOfferId)){
								SpecsData.removeMutexOffers(mutexOfferId, compInsts);
							}
						}
					}
				}
				
			}
			/******************无效销售品处理*************************************/
			
			
			
	}

}
