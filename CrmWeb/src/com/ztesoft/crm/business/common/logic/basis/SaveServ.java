package com.ztesoft.crm.business.common.logic.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.crm.business.common.cache.AttrRestrict;
import com.ztesoft.crm.business.common.cache.Product;
import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.inst.dao.ServAttrDAO;
import com.ztesoft.crm.business.common.inst.dao.ServDAO;
import com.ztesoft.crm.business.common.logic.model.OrdItemInfo;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.tools.UtilTools;
import com.ztesoft.crm.business.common.order.dao.OrdAskLogDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServAttrDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServDAO;

public class SaveServ {

	// ����������Ϣ�磺SITE_NO����serv,�������������ݱȽ�
	public static void perform(Serv serv, Map common) throws Exception {
		// 1. �����������ΪK����ô�˳���
		if (serv == null
				|| KeyValues.ACTION_TYPE_K.equals(serv.get(BusiTables.SERV.action_type))) {
			return;
		}
		/**��������***/
		Map nServMap = new HashMap();
		List nServMaps = new ArrayList();
		nServMaps.add(nServMap);
		List nServAttrMaps = new ArrayList();
		/**��������***/
		List oServMaps=new ArrayList();
		List oServAttrMaps=new ArrayList();
		/**����������***/
		List ordServs=new ArrayList();
		List ordServAttrs=new ArrayList();
		// 2.���ݹ���������ݰ�servת��ΪnServMap, nServAttrMaps, �������ͺ�servһ��;
		performConvert(serv,nServMap,nServAttrMaps);
		
		// 3.��ѯ�����ݣ�����servID��ѯserv��serv_attr���õ�oServMap��oServAttrMaps;
		performLoad(serv,oServMaps,oServAttrMaps);
		
		// 4.�ȶ�nServMap��oServMap�õ������Ϣ,ͬʱoServMapΪ���µ�,�ȶ�nServAttrMaps��oServAttrMaps�õ������Ϣ��
		performCompare(serv,common,nServMaps,nServAttrMaps,oServMaps,oServAttrMaps,ordServs,ordServAttrs);
		
		// 5.ˢ���������ݵ�serv������
		performRefresh(serv,oServMaps,oServAttrMaps,ordServs,ordServAttrs);
		
		// 6.�������ݵ������
		performSave(serv,ordServs,ordServAttrs);
		

	}
	/**
	 * ���ݹ���������ݰ�servת��ΪnServMap, nServAttrMaps, �������ͺ�servһ��
	 * @param serv
	 * @param nServMap
	 * @param nServAttrMaps
	 * */
	private static void performConvert(Serv serv,Map nServMap,List nServAttrMaps){
		
		splitServAndServAttr(serv.getAttributes(), nServMap, nServAttrMaps);
	}
	/**
	 *  ��ѯ�����ݣ�����servID��ѯserv��serv_attr���õ�oServMap��oServAttrMaps
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 * */
	private static void performLoad(Serv serv,List oServMaps,List oServAttrMaps) throws Exception{
		
		 oServMaps.addAll(loadServInfo(serv));
		 oServAttrMaps.addAll(loadServAttrInfo(serv));
	}
	/**
	 *  �ȶ�nServMap��oServMap�õ������Ϣ,ͬʱoServMapΪ���µ�,�ȶ�nServAttrMaps��oServAttrMaps�õ������Ϣ
	 * @param serv
	 * @param common
	 * @param nServMaps
	 * @param nServAttrMaps
	 * @param oServMaps
	 * @param oServAttrMaps
	 * @param ordServs
	 * @param ordServAttrs
	 * */
	private static void performCompare(Serv serv,Map common,List nServMaps,List nServAttrMaps,List oServMaps,List oServAttrMaps,List ordServs,List ordServAttrs) throws Exception{
		String[] servKey = new String[]{BusiTables.SERV.serv_id}; 
        String[] servAttrKey = new String[]{BusiTables.SERV.serv_id, BusiTables.SERV_ATTR.field_name}; 
        
		// 4.�ȶ�nServMap��oServMap�õ������Ϣ,ͬʱoServMapΪ���µ�
		 ordServs.addAll(UtilTools.compare(servKey,nServMaps, oServMaps, common, Keys.IGNORE_FIELD_SERV));
		// 5.�ȶ�nServAttrMaps��oServAttrMaps�õ������Ϣ��
		 ordServAttrs.addAll(UtilTools.compareAttr(servAttrKey, nServAttrMaps, oServAttrMaps, ordServs,
                 servKey, common, Keys.IGNORE_FIELD_SERV_ATTR));
	}
	/**
	 *  ˢ���������ݵ�serv������
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 * @param ordServs
	 * @param ordServAttrs
	 * */
	
	private static void performRefresh(Serv serv,List oServMaps,List oServAttrMaps,List ordServs,List ordServAttrs){
		//ʹ��oServMap��oServAttrMaps���ݸ���serv����,���ع��̱�����
		
		if(oServMaps!=null&&!oServMaps.isEmpty()){
			serv.loadFromMap((Map) oServMaps.get(0));
			serv.loadFromList(oServAttrMaps);
			serv.setActionType(KeyValues.ACTION_TYPE_K);
		}
		//���ع��̱�����
		serv.getOrdItemInfo().setOrdServs(ordServs); // ֻ��һ��
		serv.getOrdItemInfo().setOrdServAttrs(ordServAttrs);
		
	}
	/**
	 *  �������ݵ������
	 * @param serv
	 * @param ordServs
	 * @param ordServAttrs
	 * */
	private static void performSave(Serv serv,List ordServs,List ordServAttrs) throws Exception{
		
		//�����������ݵ����ݿ⣺����ordServ��ordServAttrs���ݸ����������ݱ�serv��serv_attr
		servdao.saveNewinfo(ordServs);
		servattrdao.saveNewinfo(ordServAttrs);
		
		//������̱�OrdServ��
		saveOrdServItems(serv.getOrdItemInfo());
	}
	

	// ���ز�Ʒ��Ϣ
	private static List loadServInfo(Serv serv) throws FrameException {
		String whereCond = " and serv_id = ? ";
		List params = new ArrayList();
		params.add(serv.get(BusiTables.SERV.serv_id));

		return servdao.findByCond(whereCond, params);
	}

	// ���ز�Ʒ������Ϣ
	private static List loadServAttrInfo(Serv serv) throws FrameException {
		String whereCond = "and serv_id = ? ";
		List whereCondParams = new ArrayList();

		whereCondParams.add(serv.get(BusiTables.SERV.serv_id));

		return servattrdao.findByCond(whereCond, whereCondParams);
	}

	// ��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	private static void saveOrdServItems(OrdItemInfo nOrdItemInfo)
			throws FrameException {
		// 1.����ordItemInfo�е�ordServs,
		// ordServAttr��.���ݣ��Ա�Ϊ��λ��������ordServAttrsΪ�������δ������£�
		List ordServItems = nOrdItemInfo.getOrdServs();

		List ordServAttrItems = nOrdItemInfo.getOrdServAttrs();
		for (int i = 0; i < ordServItems.size(); i++) {
			OrderItem ordServItem = (OrderItem) ordServItems.get(i);
            
            if (null == ordServItem) {
                continue;
            }
            
            UtilTools.addData(ordServItem);
            
			saveOrdServ(ordServItem);
		}
        
		for (int i = 0; i < ordServAttrItems.size(); i++) {
			OrderItem ordServAttrItem = (OrderItem) ordServAttrItems.get(i);

            if (null == ordServAttrItem) {
                continue;
            }
            
            UtilTools.addData(ordServAttrItem);
            
			saveOrdServAttr(ordServAttrItem);
		}

	}

	// ��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	private static void saveOrdServ(OrderItem ordServItem) throws FrameException {
        //��map�еĶ�����ID�Ϳͻ���ʶд��List��
        ordServItem.initOrderItemDetailsInfo();
        
        //�����operFlagΪ"A"������һ����¼
         if (KeyValues.OPER_FLAG_A.equals(ordServItem.getOperFlag())) {
             ordservdao.insert(ordServItem.getAttributes());
             
             //����ʱ��û��ord_ask_log��Ϣ               
             for (int j = 0; j < ordServItem.getOrderItemDetails().size(); j++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)ordServItem.getOrderItemDetails().get(j);
                 
                 ordasklogdao.insert(ordasklogitem.getAttributes());
             }
             
         } else { //ɾ����¼
             ordservdao.deleteByKey(ordServItem.getAttributes());
             
             String whereCond = " and ord_item_id = ? ";
             List params = new ArrayList();
             params.add(ordServItem.get("ord_item_id"));
             ordasklogdao.delete(whereCond, params);
             
         }

         if (KeyValues.OPER_FLAG_M.equals(ordServItem.getOperFlag())) { //�ٲ���
             ordservdao.insert(ordServItem.getAttributes());
             
             for (int i = 0; i < ordServItem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)ordServItem.getOrderItemDetails().get(i);
                 
                 ordasklogdao.insert(ordasklogitem.getAttributes());
             }
         }        
	}

	// ��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	private static void saveOrdServAttr(OrderItem ordservattritem) throws FrameException {
        //��map�еĶ�����ID�Ϳͻ���ʶд��List��
        ordservattritem.initOrderItemDetailsInfo();
        
        //�����operFlagΪ"A"������һ����¼
        if (KeyValues.OPER_FLAG_A.equals(ordservattritem.getOperFlag())) {
            ordservattrdao.insert(ordservattritem.getAttributes());
            
            //����ʱ��û��ord_ask_log��Ϣ               
            for (int j = 0; j < ordservattritem.getOrderItemDetails().size(); j++) {
                OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(j);
                
                ordasklogdao.insert(ordasklogitem.getAttributes());
            }
            
        } else { //ɾ����¼
            ordservattrdao.deleteByKey(ordservattritem.getAttributes());
            
            String whereCond = " and ord_item_id = ? ";
            List params = new ArrayList();
            params.add(ordservattritem.get("ord_item_id"));
            ordasklogdao.delete(whereCond, params);
            
        }

        if (KeyValues.OPER_FLAG_M.equals(ordservattritem.getOperFlag())) { //�ٲ���
            ordservattrdao.insert(ordservattritem.getAttributes());;
            
            for (int i = 0; i < ordservattritem.getOrderItemDetails().size(); i++) {
                OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(i);
                
                ordasklogdao.insert(ordasklogitem.getAttributes());
            }
        }  
        
	}

	// ���չ�����ݷֽ�����Ʒ�Ͳ�Ʒ����
	private static void splitServAndServAttr(Map servmap, Map nservmap,
			List servattrs) {
		// 1.���ݲ�ƷIDȡ�������
		Product pro = SpecsData.getProduct((String) servmap
				.get(BusiTables.SERV.product_id));

		// ��Ʒ���Ա�����е�Key,List of attrRestrict����
		List attrlist = pro.getAttrByTableName(Keys.TABLE_SERV_ATTR);

		// ��Ʒ���Ա�û��Key����ȫ��������Ʒ��Ϣ
		if (null != attrlist && !attrlist.isEmpty()) {
			// 2.����map���ҵ�����Ʒ�����ֶΣ�����ݱ�
			for (int i = 0; i < attrlist.size(); i++) {
				AttrRestrict attrRestrict = (AttrRestrict) attrlist.get(i);

				String key = attrRestrict.getField_name();
				String value = (String) servmap.get(key);

				// map��key������������Ϣ
				if (null != value) {
					Map attrmap = new HashMap();

					attrmap.put(BusiTables.SERV.serv_id, servmap.get(BusiTables.SERV.serv_id));
					attrmap.put(BusiTables.SERV_ATTR.attr_id, pro.getAttrIdByFieldName(key));
					attrmap.put(BusiTables.SERV_ATTR.attr_val, value);
					attrmap.put(BusiTables.SERV_ATTR.field_name, key);
					attrmap.put(BusiTables.SERV_ATTR.product_id, pro.getProduct_id());
					attrmap.put(BusiTables.SERV_ATTR.action_type, 
                                servmap.get(BusiTables.SERV_ATTR.action_type));

					servattrs.add(attrmap);
				}
			}
		}

		// 2.���ݲ�ƷDAO���ҵ�����Ʒ�ֶΣ�
		String[] servKeys = BusiTables.SERV.TABLE_FIELDS;

		// ɾ�����Ա�key��ʣ�µľ�������Ʒ��key
		for (int j = 0; j < servKeys.length; j++) {
			String skey = servKeys[j];
			String svalue = (String) servmap.get(skey);
			if (svalue != null)
				nservmap.put(skey, svalue);
		}
	}
    
	// �ӿ��ʼ���ٶ�
	private static ServAttrDAO servattrdao = new ServAttrDAO();

	private static ServDAO servdao = new ServDAO();

	private static OrdServDAO ordservdao = new OrdServDAO();

	private static OrdServAttrDAO ordservattrdao = new OrdServAttrDAO();

	private static OrdAskLogDAO ordasklogdao = new OrdAskLogDAO();

}
