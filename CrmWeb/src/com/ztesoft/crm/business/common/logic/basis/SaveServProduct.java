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
import com.ztesoft.crm.business.common.inst.dao.ServProductAttrDAO;
import com.ztesoft.crm.business.common.inst.dao.ServProductDAO;
import com.ztesoft.crm.business.common.logic.model.OrdItemInfo;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServProduct;
import com.ztesoft.crm.business.common.logic.tools.UtilTools;
import com.ztesoft.crm.business.common.order.dao.OrdAskLogDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServProductAttrDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServProductDAO;

public class SaveServProduct {

	public static void perform(Serv serv, Map common) throws Exception {

		// 1.���δ��������Ʒ����ֱ�ӷ���
		if (null == serv || null == serv.getServProducts()
				|| serv.getServProducts().isEmpty()) {
			return;
		}
        
		/** ���½�������* */
		List nServProductMaps = new ArrayList();
		List nServProductAttrMaps = new ArrayList();

		/** ������������* */
		List oServProductMaps = new ArrayList();
		List oServProductAttrMaps = new ArrayList();

		/** ������������* */
		List ordServProducts = new ArrayList();
		List ordServProductAttrs = new ArrayList();
		
		//2.���ݹ���������ݰ�servת��ΪnServMap, nServAttrMaps, �������ͺ�servһ��;
		performConvert(serv,nServProductMaps,nServProductAttrMaps);
		
		// 3.����servID��ѯserv_product��serv_product_attr���õ�oServproductMaps;
		performLoad(serv,oServProductMaps,oServProductAttrMaps);
		
		// 4.�ȶ�nServproductMaps��oServproductMaps�õ������Ϣ
		performCompare(serv,common,nServProductMaps,nServProductAttrMaps,oServProductMaps,
                oServProductAttrMaps,ordServProducts,ordServProductAttrs);
		
		// 5.ˢ���������ݵ�serv������
		performRefresh(serv,oServProductMaps,oServProductAttrMaps);
        
		// 6.�������ݵ�����
		performSave(serv,ordServProducts,ordServProductAttrs);

	}

	/**
	 * ���ݹ���������ݰ�serv����Ķ���ת��ΪnServMap, nServAttrMaps, �������ͺ�servһ��
	 * 
	 * @param serv
	 * @param nServMap
	 * @param nServAttrMaps
	 * @throws Exception
	 */
	private static void performConvert(Serv serv, List nServProductMaps,
			            List nServProductAttrMaps) throws Exception {
		List servProducts = serv.getServProducts();

		for (int k = 0; k < servProducts.size(); k++) {
			ServProduct servProduct = (ServProduct) servProducts.get(k);
            
            if (null == servProduct) {
                continue;
            }
                        
			Map servProductMap = new HashMap();

			servProductMap.putAll(servProduct.getAttributes());
			
			splitServProductAndAttr(servProductMap, nServProductMaps,
					nServProductAttrMaps, servProduct.attributes);

		}
	}

	/**
	 * ��ѯ�����ݣ�����servID��ѯserv��serv_attr���õ�oServMap��oServAttrMaps
	 * 
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 */
	private static void performLoad(Serv serv, List oServProductMaps,
			List oServProductAttrMaps) throws Exception {

		oServProductMaps.addAll(loadServProductInfo(serv));
		oServProductAttrMaps.addAll(loadServProductAttrInfo(serv));
	}

	/**
	 * �ȶ�nServMap��oServMap�õ������Ϣ,ͬʱoServMapΪ���µ�,�ȶ�nServAttrMaps��oServAttrMaps�õ������Ϣ
	 * 
	 * @param serv
	 * @param common
	 * @param nServMaps
	 * @param nServAttrMaps
	 * @param oServMaps
	 * @param oServAttrMaps
	 * @param ordServs
	 * @param ordServAttrs
	 */
	private static void performCompare(Serv serv, Map common,
			List nServProductMaps, List nServProductAttrMaps,
			List oServProductMaps, List oServProductAttrMaps,
			List ordServProducts, List ordServProductAttrs) throws Exception {
        String[] servProductKey = new String[]{BusiTables.SERV_PRODUCT.serv_product_id};
        String[] servProductAttrKey = new String[]{BusiTables.SERV_PRODUCT.serv_product_id, BusiTables.SERV_PRODUCT_ATTR.field_name};
       
		// 4.�ȶ�nServproductMaps��oServproductMaps�õ������Ϣ��
		ordServProducts.addAll(UtilTools.compare(
                servProductKey, nServProductMaps,
				oServProductMaps, common, Keys.IGNORE_FIELD_SERV_PRODUCT));

		// 5.�ȶ�nServproductAttrMaps��oServproductAttrMaps�õ������Ϣ��
		ordServProductAttrs.addAll(UtilTools.compareAttr(servProductAttrKey,
                nServProductAttrMaps,oServProductAttrMaps, ordServProducts,
                servProductKey, common, Keys.IGNORE_FIELD_SERV_PRODUCT_ATTR));
	}

	/**
	 * ˢ���������ݵ�serv������
	 * 
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 * @param ordServs
	 * @param ordServAttrs
	 */

	private static void performRefresh(Serv serv, List ordServProducts,
			List ordServProductAttrs) {
		// ʹ��oServMap��oServAttrMaps���ݸ���serv����,���ع��̱�����
		refreshServProducts(serv, ordServProducts, ordServProductAttrs);
	}

	/**
	 * �������ݵ������
	 * 
	 * @param serv
	 * @param ordServs
	 * @param ordServAttrs
	 */
	private static void performSave(Serv serv, List ordServProducts,
			List ordServProductAttrs) throws Exception {

		// �����������ݵ����ݿ⣺����ordServ��ordServAttrs���ݸ����������ݱ�serv��serv_attr
		servProductdao.saveNewinfo(ordServProducts);
		servProductAttrdao.saveNewinfo(ordServProductAttrs);

        //���ع��̱�����
        serv.getOrdItemInfo().setOrdServproducts(ordServProducts);
        serv.getOrdItemInfo().setOrdServproductAttrs(ordServProductAttrs);
        
		// ������̱�OrdServ��
		saveOrdServItems(serv.getOrdItemInfo());
	}

	private static List loadServProductInfo(Serv serv) throws FrameException {
		String whereCond = " and serv_id = ? ";
		List params = new ArrayList();
		params.add(serv.get(BusiTables.SERV_PRODUCT.serv_id));

		return servProductdao.findByCond(whereCond, params);
	}

	private static List loadServProductAttrInfo(Serv serv)
			throws FrameException {
		String whereCond = " and serv_product_id in (select serv_product_id from serv_product where serv_id = ?) ";
		List whereCondParams = new ArrayList();

		whereCondParams.add(serv.get(BusiTables.SERV_PRODUCT.serv_id));

		return servProductAttrdao.findByCond(whereCond, whereCondParams);
	}

	private static void refreshServProducts(Serv serv, List oServproducts, List oServproductAttrMaps) {
		List servProducts = new ArrayList();

		for (int i = 0; i < oServproducts.size(); i++) {
			 
			HashMap map = (HashMap) oServproducts.get(i);
			ServProduct servProduct = new ServProduct();

			servProduct.loadFromMap(map);

			String[] keys = new String[] {BusiTables.SERV_PRODUCT.serv_product_id };

			List myServProductAttrs = UtilTools.findAndRemove(servProduct
					.getAttributes(), keys, oServproductAttrMaps);

			servProduct.loadFromList(myServProductAttrs);
			servProduct.set(BusiTables.SERV_PRODUCT.action_type, KeyValues.ACTION_TYPE_K);

			servProducts.add(servProduct);
		}

		serv.setServProducts(servProducts);

	}

	// ���渽����Ʒ��������Ϣ�͸�����Ʒ�����������
	private static void saveOrdServItems(OrdItemInfo nOrdItemInfo)
			throws FrameException {
		List ordServProductItems = nOrdItemInfo.getOrdServproducts();
		List ordServProductAttrItems = nOrdItemInfo.getOrdServproductAttrs();

		int i = 0;

		for (i = 0; i < ordServProductItems.size(); i++) {
			OrderItem ordServProductItem = (OrderItem) ordServProductItems.get(i);
            
            if (null == ordServProductItem) {
                continue;
            }
            
            UtilTools.addData(ordServProductItem);            

			saveOrdServProduct(ordServProductItem);
		}

		for (i = 0; i < ordServProductAttrItems.size(); i++) {
			OrderItem ordServProductAttrItem = (OrderItem) ordServProductAttrItems.get(i);
            
            if (null == ordServProductAttrItem) {
                continue;
            }
            
            UtilTools.addData(ordServProductAttrItem);

			saveOrdServProductAttr(ordServProductAttrItem);
		}

	}

	// ��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	private static void saveOrdServProduct(OrderItem servorderitem) throws FrameException {

        //��map�еĶ�����ID�Ϳͻ���ʶд��List��
        servorderitem.initOrderItemDetailsInfo();
        
        //�����operFlagΪ"A"������һ����¼
         if (KeyValues.OPER_FLAG_A.equals(servorderitem.getOperFlag())) {
             ordServProductdao.insert(servorderitem.getAttributes());
             
             //����ʱ��û��ord_ask_log��Ϣ               
             for (int j = 0; j < servorderitem.getOrderItemDetails().size(); j++) {
                 OrderItemDetail ordAskLogItem = (OrderItemDetail)servorderitem.getOrderItemDetails().get(j);
                                   
                ordAskLogdao.insert(ordAskLogItem.getAttributes());                 
             }
             
         } else { //ɾ����¼
             ordServProductdao.deleteByKey(servorderitem.getAttributes());
             
             String whereCond = " and ord_item_id = ? ";
             List params = new ArrayList();
             params.add(servorderitem.get(BusiTables.ORD_SERV_PRODUCT.ord_item_id));
             ordAskLogdao.delete(whereCond, params);
             
         }

         if (KeyValues.OPER_FLAG_M.equals(servorderitem.getOperFlag())) { //�ٲ���
             ordServProductdao.insert(servorderitem.getAttributes());;
             
             for (int i = 0; i < servorderitem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)servorderitem.getOrderItemDetails().get(i);
                 
                 ordAskLogdao.insert(ordasklogitem.getAttributes());
             }
         }
		
	}

	// ��������Ʒ��������Ϣ�Ͳ�Ʒ�����������
	private static void saveOrdServProductAttr(OrderItem ordServAttrItem)
			throws FrameException {
        //��map�еĶ�����ID�Ϳͻ���ʶд��List��
        ordServAttrItem.initOrderItemDetailsInfo();
        
        //�����operFlagΪ"A"������һ����¼
        if (KeyValues.OPER_FLAG_A.equals(ordServAttrItem.getOperFlag())) {
            ordServProductAttrdao.insert(ordServAttrItem.getAttributes());
            
            //����ʱ��û��ord_ask_log��Ϣ               
            for (int j = 0; j < ordServAttrItem.getOrderItemDetails().size(); j++) {
                OrderItemDetail ordasklogitem1 = (OrderItemDetail)ordServAttrItem.getOrderItemDetails().get(j);
                
                ordAskLogdao.insert(ordasklogitem1.getAttributes());
            }
            
        } else { //ɾ����¼
            ordServProductAttrdao.deleteByKey(ordServAttrItem.getAttributes());
            
            String whereCond = " and ord_item_id = ? ";
            List params = new ArrayList();
            params.add(ordServAttrItem.get(BusiTables.ORD_SERV_PRODUCT_ATTR.ord_item_id));
            ordAskLogdao.delete(whereCond, params);
            
        }

        if (KeyValues.OPER_FLAG_M.equals(ordServAttrItem.getOperFlag())) { //�ٲ���
            ordServProductAttrdao.insert(ordServAttrItem.getAttributes());;
            
            for (int i = 0; i < ordServAttrItem.getOrderItemDetails().size(); i++) {
                OrderItemDetail ordasklogitem = (OrderItemDetail)ordServAttrItem.getOrderItemDetails().get(i);
                
                ordAskLogdao.insert(ordasklogitem.getAttributes());
            }
        }
        
	}

	// ���չ�����ݷֽ⸽����Ʒ�͸�����Ʒ����
	private static void splitServProductAndAttr(Map servproductmap,
			List nservproductlist, List servproductattrs, Map common) {
		// 1.���ݲ�ƷIDȡ�������
		Product pro = SpecsData.getProduct((String) servproductmap
				.get(BusiTables.SERV.product_id));

		// ��Ʒ���Ա�����е�Key
		List attrkeylist = pro.getAttrByTableName(Keys.TABLE_SERV_PRODUCT_ATTR);

		// ������Ʒ���Ա��м�¼
		if (null != attrkeylist && !attrkeylist.isEmpty()) {
			// 2.����map���ҵ�����Ʒ�����ֶΣ�����ݱ�
			for (int i = 0; i < attrkeylist.size(); i++) {
				AttrRestrict attrrestrict = (AttrRestrict) attrkeylist.get(i);

				String key = attrrestrict.getField_name();
				String value = (String) servproductmap.get(key);

				// map��key������������Ϣ
				if (servproductmap.containsKey(key)) {
					Map attrmap = new HashMap();

					attrmap.put(BusiTables.SERV_PRODUCT.serv_product_id,
                                servproductmap.get(BusiTables.SERV_PRODUCT.serv_product_id));
					attrmap.put(BusiTables.SERV_PRODUCT_ATTR.attr_id, attrrestrict.getAttr_id());
					attrmap.put(BusiTables.SERV_PRODUCT_ATTR.attr_val, value);
					attrmap.put(BusiTables.SERV_PRODUCT_ATTR.field_name, key);
					attrmap.put(BusiTables.SERV_PRODUCT_ATTR.product_id, pro.getProduct_id());
					attrmap.put(BusiTables.SERV_PRODUCT_ATTR.action_type, 
                                servproductmap.get(BusiTables.SERV_PRODUCT_ATTR.action_type));

					servproductattrs.add(attrmap);
				}
			}
		}
		// ����DAO,�ҵ�������Ʒ�����ֶ�
		String[] servproductKeys = BusiTables.SERV_PRODUCT.TABLE_FIELDS;
		// ���渽����Ʒ������Ϣ
		Map map = new HashMap();

		// ɾ�����Ա�key��ʣ�µľ�������Ʒ��key
		for (int j = 0; j < servproductKeys.length; j++) {
			String skey = servproductKeys[j];
			String svalue = (String) servproductmap.get(skey);
			if (svalue != null)
				map.put(skey, svalue);
		}

		nservproductlist.add(map);

	}
    

	private static ServProductDAO servProductdao = new ServProductDAO();

	private static ServProductAttrDAO servProductAttrdao = new ServProductAttrDAO();

	private static OrdServProductDAO ordServProductdao = new OrdServProductDAO();

	private static OrdServProductAttrDAO ordServProductAttrdao = new OrdServProductAttrDAO();

	private static OrdAskLogDAO ordAskLogdao = new OrdAskLogDAO();

}
