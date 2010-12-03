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
 *  * ����/VsopWeb/src/com/ztesoft/crm/business/common/logic
 *    Ŀ¼����Ļ����࣬�������ݿ�Ĳ���
 * */
public class SaveInstHandler implements BusiComponent{

	//���˾��ù������ݲ�����serv�����Щ,��ȻӰ�����ıȽ�
	//������Ϣ�� cust_ord_id, cust_id, staff_no, site_no, service_offer_id, ord_id
	public Object execute() throws Exception {
		
		CommonData data = CommonData.getData();
		
		/***************����Ʒ��Ϣ�ı���***********************/
		HashMap ordIdMap = new HashMap();
		List compInsts = data.getCompInsts();
		Iterator it = compInsts.iterator();
		String ask_id = data.get(Keys.ASK_ID);
		while(it.hasNext()){
			CompInst compInst=(CompInst)it.next();
			String ordExsit = (String)ordIdMap.get(compInst.get(BusiTables.OFFER_INST.comp_inst_id));
			if(ordExsit==null||"".equals(ordExsit)){
				String ord_id = "";
				//���������Ʒ��������Ʒ �� ����ORD_IDҪ��ASK_IDһ��
				if(compInsts.size()==1 || OfferConsts.OFFER_KIND_1.equals(compInst.get(CompInst.OFFER_KIND))){
					if(ask_id==null||"".equals(ask_id)){//���ASK_IDΪ�� �Ͱ�������������ORD_ID
						ord_id = SeqUtil.getInst().getNext("ORD_ASK", "ORD_ID");
					}else{//���� ��ASK_ID��Ϊ����ID
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
            
            //ÿ�ν�ord_id����data�У��������ĵ���
            data.set(BusiTables.ORD_ASK.ord_id, compInst.get(Keys.ORD_ID));
			//����Ʒ����
			SaveCompInst.execute(compInst, data.getAttributes());
		}
		
		/****************��Ʒ��Ϣ�ı���**********************/
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
            //ÿ�ν�ord_id����data�У��������ĵ���
            data.set(BusiTables.ORD_ASK.ord_id, serv.get(BusiTables.ORD_ASK.ord_id));
			//��Ʒ����
			SaveProdInst.execute(serv, data.getAttributes());
		}
		
		return null;
		
	}
	
}
