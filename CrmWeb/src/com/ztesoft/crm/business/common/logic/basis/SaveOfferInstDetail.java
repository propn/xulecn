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
import com.ztesoft.crm.business.common.inst.dao.OffInstDetaDAO;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInstDetail;
import com.ztesoft.crm.business.common.logic.model.OrdItemInfo;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;
import com.ztesoft.crm.business.common.logic.tools.UtilTools;
import com.ztesoft.crm.business.common.order.dao.OrdAskLogDAO;
import com.ztesoft.crm.business.common.order.dao.OrdOffInstDetaDAO;

public class SaveOfferInstDetail {
	
	public static void perform(CompInst inst, Map common) {
		List nOfferInstDetails = inst.getOfferInstDetails();
		List nOffInstDetailMaps = new ArrayList();
		String[] keys = new String[]{BusiTables.OFFER_INST_DETAIL.comp_inst_id};
		
		if (null == nOfferInstDetails || nOfferInstDetails.isEmpty()) {
			return;
		}
		
		try {
			//1.将List of OfferInstDetail 转为 List of Map
			performConvert(nOfferInstDetails, nOffInstDetailMaps);

			//2.加载老数据
			List oOfferInstDetailMaps = getOfferData(inst.get(BusiTables.OFFER_INST_DETAIL.comp_inst_id));

			//3.新老数据比较
			List ordOffers = UtilTools.compare(keys, nOffInstDetailMaps, oOfferInstDetailMaps, common, Keys.IGNORE_FIELD_OFFER_INST_DETAIL);
			
			//4.使用oofferinstdetailMaps数据更新nOfferInstDetails数据,并设置其操作类为“K”
			performRefresh(nOfferInstDetails, oOfferInstDetailMaps);
		
			//5.返回到出参中
			inst.getOrdItemInfo().setOrdOfferInstDetail(ordOffers);
			
            //6.保存数据到库表中
            performSave(inst,ordOffers);
			
		} catch (Exception e) {
			throw new BusiException(e);
		}
	}
	
	//将List of OfferInstDetail 转为 List of Map
	private static void performConvert(List nOfferInstDetails, List nOffInstDetailMaps) {
		for (int i = 0; i < nOfferInstDetails.size(); i++) {
			OfferInstDetail offerInstDetail = (OfferInstDetail)nOfferInstDetails.get(i);
			
			if (null == offerInstDetail) {
				continue;
			}
            
			HashMap map = new HashMap();
			map.putAll(offerInstDetail.getAttributes());
			
			nOffInstDetailMaps.add(map);
		}
	}
	
	private static void performRefresh(List nOfferInstDetails, List oofferinstdetailMaps) {
		String[] detailKeys = new String[]{BusiTables.OFFER_INST.product_offer_instance_id, 
                                     BusiTables.OFFER_INST_DETAIL.offer_detail_id, 
                                     BusiTables.OFFER_INST_DETAIL.instance_id}; //这个为key不知是否正确
		for (int i = 0; i < nOfferInstDetails.size(); i++) {			 
			OfferInstDetail offerInstDetail = (OfferInstDetail) nOfferInstDetails.get(i);
			
			if (offerInstDetail == null) {
				continue;
			}				
			
			//取最新销售品信息
			Map offMap = UtilTools.locate(offerInstDetail.getAttributes(), detailKeys, oofferinstdetailMaps); 
			
			//更新销售品信息
			if (null != offMap) {
				offerInstDetail.loadFromMap(offMap);
			}
						
			offerInstDetail.set(BusiTables.OFFER_INST_DETAIL.action_type, KeyValues.ACTION_TYPE_K);
		}
	}

	public static List getOfferData(String scomp_inst_id) throws FrameException {
		List oOfferMaps = null;
		
		// 基础销售品的销售品实例ID = 父销售品实例ID
		String whereCond = " and product_offer_instance_id in (select product_offer_instance_id from offer_inst where comp_inst_id = ?) ";
		List params = new ArrayList();
		params.add(scomp_inst_id);

		return offInstDetaildao.findByCond(whereCond, params);
	}
	
	 //保存产品订单项帐务定制关系
	private static void saveOrdServaccts(OrdItemInfo nordIteminfo) throws FrameException {	
		 List ordservdetailitems = nordIteminfo.getOrdOfferInstDetail();
		 		 
		 for (int i = 0; i < ordservdetailitems.size(); i++) {
			 OrderItem orddetailitem = (OrderItem)ordservdetailitems.get(i);
                          
             UtilTools.addData(orddetailitem);
             
			 saveOrdOffInstDetail(orddetailitem);
		 }
		 
	 }
		 
	 //保存主产品订单项信息和产品订单项附加属性
	 private static void saveOrdOffInstDetail(OrderItem orddetailitem) throws FrameException {		 
         //将map中的订单项ID和客户标识写入List中
         orddetailitem.initOrderItemDetailsInfo();
         //如果其operFlag为"A"，插入一条记录
		 if (KeyValues.OPER_FLAG_A.equals(orddetailitem.getOperFlag())) {
			 ordOffInstDetaildao.insert(orddetailitem.getAttributes());
			 
			 //新增时，没有ord_ask_log信息				 
			 for (int j = 0; j < orddetailitem.getOrderItemDetails().size(); j++) {
                 OrderItemDetail ordasklogitem1 = (OrderItemDetail)orddetailitem.getOrderItemDetails().get(j);
				 
				 ordasklogdao.insert(ordasklogitem1.getAttributes());
			 }
			 
		 } else { //删除记录
			 ordOffInstDetaildao.deleteByKey(orddetailitem.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(orddetailitem.get(BusiTables.ORD_OFFER_INST_DETAIL.ord_item_id));
			 ordasklogdao.delete(whereCond, params);
			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(orddetailitem.getOperFlag())) { //再插入
			 ordOffInstDetaildao.insert(orddetailitem.getAttributes());;
			 
			 for (int i = 0; i < orddetailitem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)orddetailitem.getOrderItemDetails().get(i);
				 
				 ordasklogdao.insert(ordasklogitem.getAttributes());
			 }
		 }
	 }
	 
     //保存数据到库表中
     private static void performSave(CompInst inst, List ordOffers) throws Exception {
         //记录到Offer_inst_detail表       
         offInstDetaildao.saveNewinfo(ordOffers);
         
         //过程表入库
         saveOrdServaccts(inst.getOrdItemInfo());
     }
	 
	private static OffInstDetaDAO offInstDetaildao = new OffInstDetaDAO();
	private static OrdOffInstDetaDAO ordOffInstDetaildao = new OrdOffInstDetaDAO();
	private static OrdAskLogDAO ordasklogdao = new OrdAskLogDAO();

}
