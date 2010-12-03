package com.ztesoft.crm.business.common.logic.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.crm.business.common.cache.AttrRestrict;
import com.ztesoft.crm.business.common.cache.ProductOffer;
import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.exception.BusiException;
import com.ztesoft.crm.business.common.inst.dao.OffInstAttrDAO;
import com.ztesoft.crm.business.common.inst.dao.OffInstDAO;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInst;
import com.ztesoft.crm.business.common.logic.model.OrdItemInfo;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;
import com.ztesoft.crm.business.common.logic.tools.UtilTools;
import com.ztesoft.crm.business.common.order.dao.OrdAskLogDAO;
import com.ztesoft.crm.business.common.order.dao.OrdOffInstAttrDAO;
import com.ztesoft.crm.business.common.order.dao.OrdOffInstDAO;

public class SaveOfferInst {

	public static void perform(CompInst inst, Map common) {
		List offerInsts = inst.getOfferInsts();
		List nOfferMaps = new ArrayList(); //��ֺ������Ʒ�б�
		List nOfferAttrMaps = new ArrayList(); //��ֺ������Ʒ�����б�

		try {			
			// 1.���ݹ���������ݰ�����Ʒ��Ϣ�ֽ�
			performConvert(offerInsts, nOfferMaps,nOfferAttrMaps);

			//2.ȡ��������Ʒ��Ϣ������Ʒ������Ϣ
			List oOfferMaps = getOfferData(inst.get(BusiTables.OFFER_INST.comp_inst_id));
			List oOfferAttrMaps = getOfferAttrData(inst.get(BusiTables.OFFER_INST.comp_inst_id));
			
			//3.�������ݱȽ�
			List offInstChangeItems = UtilTools.compare(new String[] { BusiTables.OFFER_INST.comp_inst_id },
                                           nOfferMaps, oOfferMaps, common, Keys.IGNORE_FIELD_OFFER_INST);
			List offInstAttrChangeItems = UtilTools.compareAttr(new String[] {BusiTables.OFFER_INST.product_offer_instance_id, BusiTables.OFFER_INST_ATTR.field_name },
					nOfferAttrMaps, oOfferAttrMaps, offInstChangeItems, new String[] { BusiTables.OFFER_INST.comp_inst_id }, common, Keys.IGNORE_FIELD_OFFER_INST_ATTR);
			

			//4.ʹ��oOfferMaps��oOfferAttrMaps���ݸ���offer_inst����
			performRefresh(offerInsts, oOfferMaps, oOfferAttrMaps);
		
			//5���ؽ��
			inst.getOrdItemInfo().setOrdOfferInsts(offInstChangeItems);
			inst.getOrdItemInfo().setOrdOfferInstAttrs(offInstAttrChangeItems);
			
            //6.�������ݵ������
            performSave(inst,offInstChangeItems,offInstAttrChangeItems);

		} catch (Exception e) {
			throw new BusiException(e);
		}
		
	}
	
	// ˢ���������ݵ�CompInst������
	private static void performRefresh(List offerInsts, List oOfferMaps, List oOfferAttrMaps) {
		String[] keys = new String[]{BusiTables.OFFER_INST.product_offer_instance_id};
		for (int i = 0; i < offerInsts.size(); i++) {			 
			OfferInst offerInst = (OfferInst) offerInsts.get(i);
			
			if (offerInst == null) {
				continue;
			}				
			
			//ȡ��������Ʒ��Ϣ
			Map offMap = UtilTools.locate(offerInst.getAttributes(), keys, oOfferMaps); 
			
			//��������Ʒ��Ϣ
			if (null != offMap) {
				offerInst.loadFromMap(offMap);
			}
			
			//ȡ��������Ʒ����
			List offAttrs = UtilTools.find(offerInst.getAttributes(), keys, oOfferAttrMaps);
			
			//��������Ʒ����
			if (null != offAttrs || offAttrs.isEmpty()) {
				offerInst.loadFromList(offAttrs);
			}
			
			offerInst.set(BusiTables.OFFER_INST.action_type, KeyValues.ACTION_TYPE_K);
		}
	}

	public static List getOfferData(String scomp_inst_id) throws FrameException {

		// ��������Ʒ������Ʒʵ��ID = ������Ʒʵ��ID
		String whereCond = " and comp_inst_id = ?";

		List params = new ArrayList();
		params.add(scomp_inst_id);

		return offerInstDao.findByCond(whereCond, params);
	}

	public static List getOfferAttrData(String scomp_inst_id)
			throws FrameException {

		String whereCond = " and product_offer_instance_id in (select product_offer_instance_id from offer_inst where comp_inst_id = ?) ";
		List whereCondParams = new ArrayList();
		whereCondParams.add(scomp_inst_id);

		return attrDao.findByCond(whereCond, whereCondParams);
	}

	
	 //���չ�����ݷֽ�����Ʒ�Ͳ�Ʒ����
	 private static void splitOfferInstAndAttr(Map oOfferInstMap, Map nOfferInstMap, List oOfferInstAttrs) {
		 //1.���ݲ�ƷIDȡ�������
		 ProductOffer pdof = SpecsData.getProductOffer((String)oOfferInstMap.get(BusiTables.OFFER_INST.product_offer_id));
		 
		 //��Ʒ���Ա�����е�Key,List of attrRestrict����
		 List attrlist = pdof.getAttrByTableName(Keys.TABLE_OFFER_INST_ATTR);		 
		 
		 //��Ʒ���Ա�û��Key����ȫ��������Ʒ��Ϣ
		 if (null != attrlist && !attrlist.isEmpty()) {
			 //2.����map���ҵ�����Ʒ�����ֶΣ�����ݱ�		
			 for (int i = 0; i < attrlist.size(); i++) {
				 AttrRestrict attrRestrict = (AttrRestrict)attrlist.get(i);
				 
				 String key = attrRestrict.getField_name(); //��Ӧfield_name
				 String value = (String) oOfferInstMap.get(key);
				 
				 //map��key������������Ϣ
				 if (null != value) {
					 Map attrmap = new HashMap();
					 
					 attrmap.put(BusiTables.OFFER_INST_ATTR.product_offer_instance_id, oOfferInstMap.get(BusiTables.OFFER_INST.product_offer_instance_id));
					 attrmap.put(BusiTables.OFFER_INST_ATTR.attr_id, pdof.getAttrIdByFieldName(key));					 
					 attrmap.put(BusiTables.OFFER_INST_ATTR.attr_val, value);				 
					 attrmap.put(BusiTables.OFFER_INST_ATTR.field_name, key);					 
					 attrmap.put(BusiTables.OFFER_INST_ATTR.product_offer_id, pdof.getOffer_id());
					 attrmap.put(BusiTables.OFFER_INST_ATTR.action_type, oOfferInstMap.get(BusiTables.OFFER_INST.action_type));
                     attrmap.put(BusiTables.OFFER_INST_ATTR.comp_inst_id, oOfferInstMap.get(BusiTables.OFFER_INST.comp_inst_id));
                     
					 oOfferInstAttrs.add(attrmap);
				 }
			 }
		}

        //2.���ݲ�ƷDAO���ҵ�����Ʒ�ֶΣ�		
		 String[] servKeys = BusiTables.OFFER_INST.TABLE_FIELDS;
		 
		 //ɾ�����Ա�key��ʣ�µľ�������Ʒ��key
		 for (int j = 0; j < servKeys.length; j++) {
			 String skey = servKeys[j];
			 String svalue =(String) oOfferInstMap.get(skey);
			 if (svalue != null)
				 nOfferInstMap.put(skey, svalue);
		 }
	 }
	
	
     //���渽����Ʒ��������Ϣ�͸�����Ʒ�����������
	 private static void saveOrdOfferinstItems(OrdItemInfo nordIteminfo) throws FrameException {	
		 List ordOffers = nordIteminfo.getOrdOfferInsts();
		 List ordOfferAttrs = nordIteminfo.getOrdOfferInstAttrs();
		 
		 int i = 0;
		 
		 for (i = 0; i < ordOffers.size(); i++) {
			 OrderItem ordOffer = (OrderItem)ordOffers.get(i);
			 
             if (null == ordOffer) {
                 continue;
             }
             UtilTools.addData(ordOffer);
			 
			 saveOrdOfferInst(ordOffer);
		 }
		 
		 for (i = 0; i < ordOfferAttrs.size(); i++) {
			 OrderItem ordOfferAttr = (OrderItem)ordOfferAttrs.get(i);
			 
             if (null == ordOfferAttr) {
                 continue;
             }
             UtilTools.addData(ordOfferAttr);
			 
			 saveOrdOfferInstAttr(ordOfferAttr);
		 }
		 
	 }
		 
	 //��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	 private static void saveOrdOfferInst(OrderItem ordOffer) throws FrameException {
         //��map�еĶ�����ID�Ϳͻ���ʶд��List��
         ordOffer.initOrderItemDetailsInfo();
         
		 //�����operFlagΪ"A"������һ����¼
		 if (KeyValues.OPER_FLAG_A.equals(ordOffer.getOperFlag())) {
			 ordOfferInstdao.insert(ordOffer.getAttributes());
			 
			 //����ʱ��û��ord_ask_log��Ϣ				 
			 for (int j = 0; j < ordOffer.getOrderItemDetails().size(); j++) {
				 OrderItemDetail ordasklogitem = (OrderItemDetail)ordOffer.getOrderItemDetails().get(j);
                 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
			 
		 } else { //ɾ����¼
			 ordOfferInstdao.deleteByKey(ordOffer.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(ordOffer.get("ord_item_id"));
			 ordAskLogdao.delete(whereCond, params);
			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(ordOffer.getOperFlag())) { //�ٲ���
			 ordOfferInstdao.insert(ordOffer.getAttributes());;
			 
			 for (int i = 0; i < ordOffer.getOrderItemDetails().size(); i++) {
				 OrderItemDetail ordasklogitem = (OrderItemDetail)ordOffer.getOrderItemDetails().get(i);
				 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
		 }
	 }

	 //��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	 private static void saveOrdOfferInstAttr(OrderItem ordservattritem) throws FrameException {
         //��map�еĶ�����ID�Ϳͻ���ʶд��List��
         ordservattritem.initOrderItemDetailsInfo();
         
		 //�����operFlagΪ"A"������һ����¼
		 if (KeyValues.OPER_FLAG_A.equals(ordservattritem.getOperFlag())) {
			 ordOfferInstAttrdao.insert(ordservattritem.getAttributes());
			 
			 //����ʱ��û��ord_ask_log��Ϣ				 
			 for (int j = 0; j < ordservattritem.getOrderItemDetails().size(); j++) {
				 OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(j);
				 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
			 
		 } else { //ɾ����¼
			 ordOfferInstAttrdao.deleteByKey(ordservattritem.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(ordservattritem.get("ord_item_id"));
			 ordAskLogdao.delete(whereCond, params);
			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(ordservattritem.getOperFlag())) { //�ٲ���
			 ordOfferInstAttrdao.insert(ordservattritem.getAttributes());;
			 
			 for (int i = 0; i < ordservattritem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(i);
				 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
		 }
	 }
	 
	 private static void performConvert(List offerInsts, List nOfferMaps, List nOfferAttrMaps) {
         for (int i = 0; i < offerInsts.size(); i++) {
			OfferInst offerInst = (OfferInst) offerInsts.get(i);
            
			if (offerInst == null) {
				continue;
			}
            			
			Map map = new HashMap();
			List list = new ArrayList();					

			//���ݹ���������ݰ�servת��ΪnServMap, nServAttrMaps, �������ͺ�servһ��;
			splitOfferInstAndAttr(offerInst.getAttributes(), map, list);
			
			if (null != map) {
				nOfferMaps.add(map);
				nOfferAttrMaps.addAll(list);
			}			
		}
	 }
     
        /** 
        * @Title: performSave 
        * @Description: �������ݵ������
        * @param inst  ����Ʒ����
        * @param offInstChangeItems  ����Ʒ�������ݶԱȽ��
        * @param offInstAttrChangeItems  ����Ʒ�����������ݶԱȽ��
        * @param Exception     
        * @return void    �������� 
        * @throws 
        */
        private static void performSave(CompInst inst,List offInstChangeItems,List offInstAttrChangeItems) throws Exception{            
            //����ordOffers��ordOfferAttrs���ݸ����������ݱ�offer_inst��offer_inst_attr            
            offerInstDao.saveNewinfo(offInstChangeItems);
            attrDao.saveNewinfo(offInstAttrChangeItems);
            
            //������̱�OrdServ��
            saveOrdOfferinstItems(inst.getOrdItemInfo());
        }
     
    private static OffInstDAO offerInstDao = new OffInstDAO();
    private static OffInstAttrDAO attrDao = new OffInstAttrDAO();
    private static OrdOffInstDAO ordOfferInstdao = new OrdOffInstDAO();
    private static OrdOffInstAttrDAO ordOfferInstAttrdao = new OrdOffInstAttrDAO(); //Ҫ���dao
    private static OrdAskLogDAO ordAskLogdao = new OrdAskLogDAO();     
     
}
