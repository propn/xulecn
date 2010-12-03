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
			
			//1.如果servAccts为空, 那么退出。
			if (null == servAccts || servAccts.isEmpty()) {
				return;
			}
            
            //2.将List of ServAcct 转成List of Map
            performConvert(servAccts, servAcctMaps);
			
			//3.根据servID查询serv_acct表，得到oServacctMaps
			List oServacctMaps = loadServAcctInfo(serv);			
			
			//4.比对oServacctMaps和servAcctMaps得到变更信息：
			List ordServaccts = UtilTools.compare(new String[]{BusiTables.SERV.serv_id, 
                                                               BusiTables.SERV_ACCT.acct_item_group_id},
                                                  servAcctMaps, oServacctMaps, common, Keys.IGNORE_FIELD_SERV_ACCT);
			
			//5根据oServAcctMaps, oServproductAttrMaps创建servProducts，放入serv
			getNewServAcctInfo(serv, ordServaccts);
					
			//6.返回合并结果
			serv.getOrdItemInfo().setOrdServAccts(ordServaccts);
			
            //7.保存数据到库中
            performSave(serv, ordServaccts);
			
		} catch (FrameException e) {
            throw new BusiException(e);
		}
	}
    
    //将List of ServAcct 转成List of Map
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
	 

     //根据比较结果，创建新的账务信息, 放到主产品信息中
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
	 
    //保存产品订单项帐务定制关系
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
		 
	 //保存主产品订单项信息和产品订单项附加属性
	 private static void saveOrdServProduct(OrderItem ordServAcctItem) throws FrameException {		 
         //将map中的订单项ID和客户标识写入List中
         ordServAcctItem.initOrderItemDetailsInfo();
         
         //如果其operFlag为"A"，插入一条记录
		 if (KeyValues.OPER_FLAG_A.equals(ordServAcctItem.getOperFlag())) {
			 ordservacctdao.insert(ordServAcctItem.getAttributes());
			 
			 //新增时，没有ord_ask_log信息				 
			 for (int j = 0; j < ordServAcctItem.getOrderItemDetails().size(); j++) {
				 OrderItemDetail ordAskLogItem = (OrderItemDetail)ordServAcctItem.getOrderItemDetails().get(j);
				 
				 ordasklogdao.insert(ordAskLogItem.getAttributes());
			 }
			 
		 } else { //删除记录
			 ordservacctdao.deleteByKey(ordServAcctItem.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(ordServAcctItem.get("ord_item_id"));
			 ordasklogdao.delete(whereCond, params);			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(ordServAcctItem.getOperFlag())) { //再插入
			 ordservacctdao.insert(ordServAcctItem.getAttributes());;
			 
			 for (int i = 0; i < ordServAcctItem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordAskLogItem = (OrderItemDetail)ordServAcctItem.getOrderItemDetails().get(i);
				 
				 ordasklogdao.insert(ordAskLogItem.getAttributes());
			 }
		 }
	 }
     
    //保存数据到库中
    private static void performSave(Serv serv, List ordServaccts) throws FrameException {            
        //1保存到账务表
        servacctdao.saveNewinfo(ordServaccts); 
        
        //2保存过程表信息
        saveOrdServaccts(serv.getOrdItemInfo());
    }
     
	 private static ServAcctDAO servacctdao = new ServAcctDAO();
	 private static OrdServAcctDAO ordservacctdao = new OrdServAcctDAO();
	 private static OrdAskLogDAO ordasklogdao = new OrdAskLogDAO();
}
