package com.ztesoft.crm.business.accept.entry.bo.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST;
import com.ztesoft.crm.business.common.consts.BusiTables.ORD_ASK;
import com.ztesoft.crm.business.common.logic.basis.SaveOrdAsk;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.Serv;


/**
 * 调用/VsopWeb/src/com/ztesoft/crm/business/common/logic
 * 服务订单保存处理
 * */
public class SaveOrdAskHandler implements BusiComponent{

	/* 服务订单保存处理
	 * @see com.ztesoft.crm.business.accept.entry.bo.BusiComponent#execute()
	 */
	public Object execute() throws Exception{
	CommonData data = CommonData.getData();
	String ask_id = data.get(Keys.ASK_ID);
		/***************销售品信息的保存***********************/
		List compInsts = data.getCompInsts();
		Iterator it = compInsts.iterator();
		
		HashMap ordAskMap = new HashMap();
		List offerAskOrdList = new ArrayList();
			//需要从前台获取 如果前台不为空 才需要继续生成
			if(compInsts!=null&&compInsts.size()!=0){
				for(int i =0;i<compInsts.size();i++){
					CompInst compInst=(CompInst)compInsts.get(i);
					if(compInsts.size()==1){
						if(ask_id==null||"".equals(ask_id)){
							ask_id = compInst.get(ORD_ASK.ord_id);
							break;
						}else{
							String old_ord_id = compInst.get(ORD_ASK.ord_id);
							compInst.set(ORD_ASK.ord_id, ask_id);
							//SaveOrdAsk.updateOrdAsk(compInst.get(ORD_ASK.ord_id), ask_id);
							break;
						}
						
						
					}
					if(OfferConsts.OFFER_KIND_1.equals(compInst.get(CompInst.OFFER_KIND))){
						if(ask_id==null||"".equals(ask_id)){
							ask_id = compInst.get(ORD_ASK.ord_id);
							break;
						}else{
							String old_ord_id = compInst.get(ORD_ASK.ord_id);
							compInst.set(ORD_ASK.ord_id, ask_id);
							//SaveOrdAsk.updateOrdAsk(compInst.get(ORD_ASK.ord_id), ask_id);
							break;
						}
						
					}
				}
			}
	//	}
		/*String ask_id = SeqUtil.getInst().getNext("ORD_ASK", "ASK_ID");//测试代码  先从序列获取ASK_ID 真实环境需要从前台获取
*/		String askData = "";
		askData = DAOUtils.getFormatedDbDate();
		//循环生成服务订单 并保存
		while(it.hasNext()){
			CompInst compInst=(CompInst)it.next();
			//如果该销售品实例之前已经生成过 那么就不在生成
			HashMap ordAsk = (HashMap)ordAskMap.get(compInst.get(CompInst.COMP_INST_ID));
			if (ordAsk == null){
				ordAsk = new HashMap();
				String service_offer_id  = "";
				if(OfferConsts.ACTION_TYPE_A.equals(compInst.get(CompInst.ACTION_TYPE))){
					 service_offer_id = "4";//订购
				}else if (OfferConsts.ACTION_TYPE_D.equals(compInst.get(CompInst.ACTION_TYPE))){
					service_offer_id = "5";//退订
				} 
				else {
					service_offer_id = "6";//综合改类
				}
				ordAsk.put(ORD_ASK.ord_id, compInst.get(CompInst.ORD_ID));
				ordAsk.put(ORD_ASK.cust_ord_id, data.get(Keys.CUST_ORD_ID));
				ordAsk.put(ORD_ASK.ask_id,ask_id);
				ordAsk.put(ORD_ASK.service_offer_id, service_offer_id);
				String offer_kind = compInst.get(OFFER_INST.offer_kind);
				if("0".equals(offer_kind)){
					ordAsk.put(ORD_ASK.ord_type, "11");//11为基础销售品 
				}
				else{
					ordAsk.put(ORD_ASK.ord_type, "14");//14为组合销售品
				}
				ordAsk.put(ORD_ASK.ord_prop, "0");//0表示受理
				ordAsk.put(ORD_ASK.instance_type, "10C");
				ordAsk.put(ORD_ASK.instance_type_id, compInst.get("product_offer_id"));
				ordAsk.put(ORD_ASK.instance_id, compInst.get(CompInst.COMP_INST_ID));
				ordAsk.put(ORD_ASK.solution_id, "");
				ordAsk.put(ORD_ASK.notes, "");
				ordAsk.put(ORD_ASK.state, "100");
				ordAsk.put(ORD_ASK.state_reason_id, "");
				ordAsk.put(ORD_ASK.ask_date,askData);
				ordAsk.put(ORD_ASK.confirm_date,"");
				ordAsk.put(ORD_ASK.fin_date,"");
				ordAsk.put(ORD_ASK.beg_time,"");
				ordAsk.put(ORD_ASK.staff_no, compInst.get(Keys.STAFF_NO));
				ordAsk.put(ORD_ASK.site_no, compInst.get(Keys.SITE_NO));
				ordAsk.put(ORD_ASK.agreement_id, "");
				ordAsk.put(ORD_ASK.last_ord_id, "");
				ordAskMap.put(CompInst.COMP_INST_ID, ordAsk);//标记为已经生成
				offerAskOrdList.add(ordAsk);
			}
			
			//组合销售品服务订单保存
			
		}
		if(offerAskOrdList!=null&&offerAskOrdList.size()!=0){
			SaveOrdAsk.perform(offerAskOrdList, data.getAttributes(),ask_id);
		}
		
		//****************基础销售品信息的保存**********************//*
		List servs=data.getServs();
		 it = servs.iterator();
		 List prodAskOrdList = new ArrayList();
		while(it.hasNext()){
			Serv serv=(Serv)it.next();
			//如果该销售品实例之前已经生成过 那么就不在生成
			HashMap ordAsk = (HashMap)ordAskMap.get(serv.get(CompInst.COMP_INST_ID));
			if (ordAsk == null){
				ordAsk = new HashMap();
				String service_offer_id ;
				if (ServConsts.ACTION_TYPE_A.equals(serv.get(CompInst.ACTION_TYPE))){
					 service_offer_id = "4";
				}else if (ServConsts.ACTION_TYPE_D.equals(serv.get(CompInst.ACTION_TYPE))){
					service_offer_id = "5";
				}else{
					 service_offer_id = "6";
				}
				
				ordAsk.put(ORD_ASK.ord_id, serv.get(serv.ORD_ID));
				ordAsk.put(ORD_ASK.cust_ord_id, data.get(Keys.CUST_ORD_ID));
				ordAsk.put(ORD_ASK.ask_id,ask_id);
				ordAsk.put(ORD_ASK.service_offer_id, service_offer_id);
				ordAsk.put(ORD_ASK.ord_type, "11");//11为基础销售品 
				ordAsk.put(ORD_ASK.ord_prop, "0");//0表示受理
				ordAsk.put(ORD_ASK.instance_type, "10A");
				ordAsk.put(ORD_ASK.instance_type_id, serv.get(Serv.PRODUCT_ID));
				ordAsk.put(ORD_ASK.instance_id, serv.get(serv.COMP_INST_ID));
				ordAsk.put(ORD_ASK.solution_id, "");
				ordAsk.put(ORD_ASK.notes, "");
				ordAsk.put(ORD_ASK.state, "100");
				ordAsk.put(ORD_ASK.state_reason_id, "");
				ordAsk.put(ORD_ASK.ask_date,askData);
				ordAsk.put(ORD_ASK.confirm_date,"");
				ordAsk.put(ORD_ASK.fin_date,"");
				ordAsk.put(ORD_ASK.beg_time,"");
				ordAsk.put(ORD_ASK.staff_no, serv.get(Keys.STAFF_NO));
				ordAsk.put(ORD_ASK.site_no, serv.get(Keys.SITE_NO));
				ordAsk.put(ORD_ASK.agreement_id, "");
				ordAsk.put(ORD_ASK.last_ord_id, "");
				ordAskMap.put(CompInst.COMP_INST_ID, ordAsk);//标记为已经生成
				prodAskOrdList.add(ordAsk);
			}
		}
		//基础销售品服务订单保存
		if(prodAskOrdList!=null&&prodAskOrdList.size()!=0){
			SaveOrdAsk.perform(prodAskOrdList, data.getAttributes(),ask_id);
		}
		return ask_id;
		
	}
}
