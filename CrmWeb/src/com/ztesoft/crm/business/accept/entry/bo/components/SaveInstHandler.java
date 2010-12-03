package com.ztesoft.crm.business.accept.entry.bo.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.logic.basis.SaveCompInst;
import com.ztesoft.crm.business.common.logic.basis.SaveProdInst;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.utils.SeqUtil;



/**
 *  * 调用/VsopWeb/src/com/ztesoft/crm/business/common/logic
 *    目录下面的基础类，进行数据库的操作
 * */
public class SaveInstHandler implements BusiComponent{

	//个人觉得公共数据不放在serv里面好些,不然影响后面的比较
	//公共信息有 cust_ord_id, cust_id, staff_no, site_no, service_offer_id, ord_id
	public Object execute() throws Exception {
		
		CommonData data = CommonData.getData();
		
		/***************销售品信息的保存***********************/
		HashMap ordIdMap = new HashMap();
		List compInsts = data.getCompInsts();
		Iterator it = compInsts.iterator();
		String ask_id = data.get(Keys.ASK_ID);
		while(it.hasNext()){
			CompInst compInst=(CompInst)it.next();
			String ordExsit = (String)ordIdMap.get(compInst.get(BusiTables.OFFER_INST.comp_inst_id));
			if(ordExsit==null||"".equals(ordExsit)){
				String ord_id = "";
				//如果该销售品是主销售品 则 他的ORD_ID要和ASK_ID一致
				if(compInsts.size()==1 || OfferConsts.OFFER_KIND_1.equals(compInst.get(CompInst.OFFER_KIND))){
					if(ask_id==null||"".equals(ask_id)){//如果ASK_ID为空 就按序列生成主单ORD_ID
						ord_id = SeqUtil.getInst().getNext("ORD_ASK", "ORD_ID");
					}else{//否则 把ASK_ID作为主单ID
						//String old_ord_id = compInst.get(ORD_ASK.ord_id);
						//compInst.set(ORD_ASK.ord_id, ask_id);
						ord_id = ask_id;
					}
				}else{
					ord_id = SeqUtil.getInst().getNext("ORD_ASK", "ORD_ID");
				}
			    //ord_id = SeqUtil.getInst().getNext("ORD_ASK", "ORD_ID");
				compInst.set(BusiTables.ORD_ASK.ord_id, ord_id);
				ordIdMap.put(compInst.get(BusiTables.OFFER_INST.comp_inst_id), ord_id);
			}
            
            //每次将ord_id放在data中，方便后面的调用
            data.set(BusiTables.ORD_ASK.ord_id, compInst.get(Keys.ORD_ID));
			//销售品保存
			SaveCompInst.execute(compInst, data.getAttributes());
		}
		
		/****************产品信息的保存**********************/
		List servs=data.getServs();
		 it = servs.iterator();
		 
		while(it.hasNext()){
			Serv serv=(Serv)it.next();
			String ordExsit = (String)ordIdMap.get(serv.get(BusiTables.SERV.comp_inst_id));
			if(ordExsit==null||"".equals(ordExsit)){
				String ord_id = SeqUtil.getInst().getNext("ORD_ASK", "ORD_ID");
				serv.set(BusiTables.ORD_ASK.ord_id, ord_id);
				ordIdMap.put(serv.get(BusiTables.SERV.comp_inst_id), ord_id);
			}else{
				serv.set(BusiTables.ORD_ASK.ord_id, ordExsit);
			}
            //每次将ord_id放在data中，方便后面的调用
            data.set(BusiTables.ORD_ASK.ord_id, serv.get(BusiTables.ORD_ASK.ord_id));
			//产品保存
			SaveProdInst.execute(serv, data.getAttributes());
		}
		
		return null;
		
	}
	
}
