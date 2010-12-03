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
			//1.��List of OfferInstDetail תΪ List of Map
			performConvert(nOfferInstDetails, nOffInstDetailMaps);

			//2.����������
			List oOfferInstDetailMaps = getOfferData(inst.get(BusiTables.OFFER_INST_DETAIL.comp_inst_id));

			//3.�������ݱȽ�
			List ordOffers = UtilTools.compare(keys, nOffInstDetailMaps, oOfferInstDetailMaps, common, Keys.IGNORE_FIELD_OFFER_INST_DETAIL);
			
			//4.ʹ��oofferinstdetailMaps���ݸ���nOfferInstDetails����,�������������Ϊ��K��
			performRefresh(nOfferInstDetails, oOfferInstDetailMaps);
		
			//5.���ص�������
			inst.getOrdItemInfo().setOrdOfferInstDetail(ordOffers);
			
            //6.�������ݵ������
            performSave(inst,ordOffers);
			
		} catch (Exception e) {
			throw new BusiException(e);
		}
	}
	
	//��List of OfferInstDetail תΪ List of Map
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
                                     BusiTables.OFFER_INST_DETAIL.instance_id}; //���Ϊkey��֪�Ƿ���ȷ
		for (int i = 0; i < nOfferInstDetails.size(); i++) {			 
			OfferInstDetail offerInstDetail = (OfferInstDetail) nOfferInstDetails.get(i);
			
			if (offerInstDetail == null) {
				continue;
			}				
			
			//ȡ��������Ʒ��Ϣ
			Map offMap = UtilTools.locate(offerInstDetail.getAttributes(), detailKeys, oofferinstdetailMaps); 
			
			//��������Ʒ��Ϣ
			if (null != offMap) {
				offerInstDetail.loadFromMap(offMap);
			}
						
			offerInstDetail.set(BusiTables.OFFER_INST_DETAIL.action_type, KeyValues.ACTION_TYPE_K);
		}
	}

	public static List getOfferData(String scomp_inst_id) throws FrameException {
		List oOfferMaps = null;
		
		// ��������Ʒ������Ʒʵ��ID = ������Ʒʵ��ID
		String whereCond = " and product_offer_instance_id in (select product_offer_instance_id from offer_inst where comp_inst_id = ?) ";
		List params = new ArrayList();
		params.add(scomp_inst_id);

		return offInstDetaildao.findByCond(whereCond, params);
	}
	
	 //�����Ʒ�����������ƹ�ϵ
	private static void saveOrdServaccts(OrdItemInfo nordIteminfo) throws FrameException {	
		 List ordservdetailitems = nordIteminfo.getOrdOfferInstDetail();
		 		 
		 for (int i = 0; i < ordservdetailitems.size(); i++) {
			 OrderItem orddetailitem = (OrderItem)ordservdetailitems.get(i);
                          
             UtilTools.addData(orddetailitem);
             
			 saveOrdOffInstDetail(orddetailitem);
		 }
		 
	 }
		 
	 //��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	 private static void saveOrdOffInstDetail(OrderItem orddetailitem) throws FrameException {		 
         //��map�еĶ�����ID�Ϳͻ���ʶд��List��
         orddetailitem.initOrderItemDetailsInfo();
         //�����operFlagΪ"A"������һ����¼
		 if (KeyValues.OPER_FLAG_A.equals(orddetailitem.getOperFlag())) {
			 ordOffInstDetaildao.insert(orddetailitem.getAttributes());
			 
			 //����ʱ��û��ord_ask_log��Ϣ				 
			 for (int j = 0; j < orddetailitem.getOrderItemDetails().size(); j++) {
                 OrderItemDetail ordasklogitem1 = (OrderItemDetail)orddetailitem.getOrderItemDetails().get(j);
				 
				 ordasklogdao.insert(ordasklogitem1.getAttributes());
			 }
			 
		 } else { //ɾ����¼
			 ordOffInstDetaildao.deleteByKey(orddetailitem.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(orddetailitem.get(BusiTables.ORD_OFFER_INST_DETAIL.ord_item_id));
			 ordasklogdao.delete(whereCond, params);
			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(orddetailitem.getOperFlag())) { //�ٲ���
			 ordOffInstDetaildao.insert(orddetailitem.getAttributes());;
			 
			 for (int i = 0; i < orddetailitem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)orddetailitem.getOrderItemDetails().get(i);
				 
				 ordasklogdao.insert(ordasklogitem.getAttributes());
			 }
		 }
	 }
	 
     //�������ݵ������
     private static void performSave(CompInst inst, List ordOffers) throws Exception {
         //��¼��Offer_inst_detail��       
         offInstDetaildao.saveNewinfo(ordOffers);
         
         //���̱����
         saveOrdServaccts(inst.getOrdItemInfo());
     }
	 
	private static OffInstDetaDAO offInstDetaildao = new OffInstDetaDAO();
	private static OrdOffInstDetaDAO ordOffInstDetaildao = new OrdOffInstDetaDAO();
	private static OrdAskLogDAO ordasklogdao = new OrdAskLogDAO();

}
