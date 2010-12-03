package com.ztesoft.crm.business.common.logic.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.exception.BusiException;
import com.ztesoft.crm.business.common.inst.dao.ServAcctDAO;
import com.ztesoft.crm.business.common.logic.model.OrdItemInfo;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServAcct;
import com.ztesoft.crm.business.common.logic.model.ServProduct;
import com.ztesoft.crm.business.common.logic.tools.UtilTools;
import com.ztesoft.crm.business.common.order.dao.OrdAskLogDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServAcctDAO;

public class SaveServAcct {

	public static void perform(Serv serv, Map common){ 
		if (null == serv) {
			return;
		}
		
	    try {
			List servAccts = serv.getServAccts();
            List servAcctMaps = new ArrayList();
			
			//1.���servAcctsΪ��, ��ô�˳���
			if (null == servAccts || servAccts.isEmpty()) {
				return;
			}
            
            //2.��List of ServAcct ת��List of Map
            performConvert(servAccts, servAcctMaps);
			
			//3.����servID��ѯserv_acct���õ�oServacctMaps
			List oServacctMaps = loadServAcctInfo(serv);			
			
			//4.�ȶ�oServacctMaps��servAcctMaps�õ������Ϣ��
			List ordServaccts = UtilTools.compare(new String[]{BusiTables.SERV.serv_id, 
                                                               BusiTables.SERV_ACCT.acct_item_group_id},
                                                  servAcctMaps, oServacctMaps, common, Keys.IGNORE_FIELD_SERV_ACCT);
			
			//5����oServAcctMaps, oServproductAttrMaps����servProducts������serv
			getNewServAcctInfo(serv, ordServaccts);
					
			//6.���غϲ����
			serv.getOrdItemInfo().setOrdServAccts(ordServaccts);
			
            //7.�������ݵ�����
            performSave(serv, ordServaccts);
			
		} catch (FrameException e) {
            throw new BusiException(e);
		}
	}
    
    //��List of ServAcct ת��List of Map
    private static void performConvert(List servAccts, List servAcctMaps) {
        for (int i = 0; i < servAccts.size(); i++) {
            ServAcct servAcct = (ServAcct)servAccts.get(i);
            
            if (null == servAcct) {
                continue;
            }
                        
            HashMap map = new HashMap();
            servAcctMaps.add(map);
            
            map.putAll(servAcct.getAttributes());            
        }
    }
	
	 private static List loadServAcctInfo(Serv serv) throws FrameException {			
		String whereCond = " and serv_id = ? ";
		List params = new ArrayList();
		params.add(serv.get(BusiTables.SERV.serv_id));

		return servacctdao.findByCond(whereCond, params);
	 }
	 

     //���ݱȽϽ���������µ�������Ϣ, �ŵ�����Ʒ��Ϣ��
	 private static void getNewServAcctInfo(Serv serv, List ordServAccts) {
         List servProducts = new ArrayList();
			
		 for (int i = 0; i < ordServAccts.size(); i++) {
			OrderItem  orderitem = (OrderItem)ordServAccts.get(i);
			ServProduct servProduct = new ServProduct();
			
			servProduct.loadFromMap(orderitem.getAttributes());			
			
			servProduct.set(BusiTables.SERV_PRODUCT.action_type, KeyValues.ACTION_TYPE_K); 
			
			servProducts.add(servProduct);			
         }
         
         serv.setServProducts(servProducts);
     }
	 
    //�����Ʒ�����������ƹ�ϵ
	private static void saveOrdServaccts(OrdItemInfo nOrdIteminfo) throws FrameException {	
		 List ordSerAcctItems = nOrdIteminfo.getOrdServAccts();
		 		 
		 for (int i = 0; i < ordSerAcctItems.size(); i++) {
			 OrderItem ordSerAcctItem = (OrderItem)ordSerAcctItems.get(i);
            
             if (null == ordSerAcctItem) {
                continue;
            }
            
            UtilTools.addData(ordSerAcctItem);     
            
			saveOrdServProduct(ordSerAcctItem);
		 }
		 
	 }
		 
	 //��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	 private static void saveOrdServProduct(OrderItem ordServAcctItem) throws FrameException {		 
         //��map�еĶ�����ID�Ϳͻ���ʶд��List��
         ordServAcctItem.initOrderItemDetailsInfo();
         
         //�����operFlagΪ"A"������һ����¼
		 if (KeyValues.OPER_FLAG_A.equals(ordServAcctItem.getOperFlag())) {
			 ordservacctdao.insert(ordServAcctItem.getAttributes());
			 
			 //����ʱ��û��ord_ask_log��Ϣ				 
			 for (int j = 0; j < ordServAcctItem.getOrderItemDetails().size(); j++) {
				 OrderItemDetail ordAskLogItem = (OrderItemDetail)ordServAcctItem.getOrderItemDetails().get(j);
				 
				 ordasklogdao.insert(ordAskLogItem.getAttributes());
			 }
			 
		 } else { //ɾ����¼
			 ordservacctdao.deleteByKey(ordServAcctItem.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(ordServAcctItem.get("ord_item_id"));
			 ordasklogdao.delete(whereCond, params);			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(ordServAcctItem.getOperFlag())) { //�ٲ���
			 ordservacctdao.insert(ordServAcctItem.getAttributes());;
			 
			 for (int i = 0; i < ordServAcctItem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordAskLogItem = (OrderItemDetail)ordServAcctItem.getOrderItemDetails().get(i);
				 
				 ordasklogdao.insert(ordAskLogItem.getAttributes());
			 }
		 }
	 }
     
    //�������ݵ�����
    private static void performSave(Serv serv, List ordServaccts) throws FrameException {            
        //1���浽�����
        servacctdao.saveNewinfo(ordServaccts); 
        
        //2������̱���Ϣ
        saveOrdServaccts(serv.getOrdItemInfo());
    }
     
	 private static ServAcctDAO servacctdao = new ServAcctDAO();
	 private static OrdServAcctDAO ordservacctdao = new OrdServAcctDAO();
	 private static OrdAskLogDAO ordasklogdao = new OrdAskLogDAO();
}
