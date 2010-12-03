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

		// 1.如果未处理附属产品，则直接返回
		if (null == serv || null == serv.getServProducts()
				|| serv.getServProducts().isEmpty()) {
			return;
		}
        
		/** 最新界面数据* */
		List nServProductMaps = new ArrayList();
		List nServProductAttrMaps = new ArrayList();

		/** 过程最新数据* */
		List oServProductMaps = new ArrayList();
		List oServProductAttrMaps = new ArrayList();

		/** 订单项表的数据* */
		List ordServProducts = new ArrayList();
		List ordServProductAttrs = new ArrayList();
		
		//2.根据规格配置数据把serv转换为nServMap, nServAttrMaps, 操作类型和serv一样;
		performConvert(serv,nServProductMaps,nServProductAttrMaps);
		
		// 3.根据servID查询serv_product和serv_product_attr表，得到oServproductMaps;
		performLoad(serv,oServProductMaps,oServProductAttrMaps);
		
		// 4.比对nServproductMaps和oServproductMaps得到变更信息
		performCompare(serv,common,nServProductMaps,nServProductAttrMaps,oServProductMaps,
                oServProductAttrMaps,ordServProducts,ordServProductAttrs);
		
		// 5.刷新最新数据到serv对象中
		performRefresh(serv,oServProductMaps,oServProductAttrMaps);
        
		// 6.保存数据到库中
		performSave(serv,ordServProducts,ordServProductAttrs);

	}

	/**
	 * 根据规格配置数据把serv里面的对象转换为nServMap, nServAttrMaps, 操作类型和serv一样
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
	 * 查询老数据：根据servID查询serv和serv_attr表，得到oServMap和oServAttrMaps
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
	 * 比对nServMap和oServMap得到变更信息,同时oServMap为最新的,比对nServAttrMaps和oServAttrMaps得到变更信息
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
       
		// 4.比对nServproductMaps和oServproductMaps得到变更信息：
		ordServProducts.addAll(UtilTools.compare(
                servProductKey, nServProductMaps,
				oServProductMaps, common, Keys.IGNORE_FIELD_SERV_PRODUCT));

		// 5.比对nServproductAttrMaps和oServproductAttrMaps得到变更信息：
		ordServProductAttrs.addAll(UtilTools.compareAttr(servProductAttrKey,
                nServProductAttrMaps,oServProductAttrMaps, ordServProducts,
                servProductKey, common, Keys.IGNORE_FIELD_SERV_PRODUCT_ATTR));
	}

	/**
	 * 刷新最新数据到serv对象中
	 * 
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 * @param ordServs
	 * @param ordServAttrs
	 */

	private static void performRefresh(Serv serv, List ordServProducts,
			List ordServProductAttrs) {
		// 使用oServMap和oServAttrMaps数据跟新serv数据,返回过程表数据
		refreshServProducts(serv, ordServProducts, ordServProductAttrs);
	}

	/**
	 * 保存数据到库表中
	 * 
	 * @param serv
	 * @param ordServs
	 * @param ordServAttrs
	 */
	private static void performSave(Serv serv, List ordServProducts,
			List ordServProductAttrs) throws Exception {

		// 保存最新数据到数据库：根据ordServ和ordServAttrs数据更新最新数据表serv和serv_attr
		servProductdao.saveNewinfo(ordServProducts);
		servProductAttrdao.saveNewinfo(ordServProductAttrs);

        //返回过程表数据
        serv.getOrdItemInfo().setOrdServproducts(ordServProducts);
        serv.getOrdItemInfo().setOrdServproductAttrs(ordServProductAttrs);
        
		// 保存过程表OrdServ表
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

	// 保存附属产品订单项信息和附属产品订单项附加属性
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

	// 保存主产品订单项信息和产品订单项附加属性
	private static void saveOrdServProduct(OrderItem servorderitem) throws FrameException {

        //将map中的订单项ID和客户标识写入List中
        servorderitem.initOrderItemDetailsInfo();
        
        //如果其operFlag为"A"，插入一条记录
         if (KeyValues.OPER_FLAG_A.equals(servorderitem.getOperFlag())) {
             ordServProductdao.insert(servorderitem.getAttributes());
             
             //新增时，没有ord_ask_log信息               
             for (int j = 0; j < servorderitem.getOrderItemDetails().size(); j++) {
                 OrderItemDetail ordAskLogItem = (OrderItemDetail)servorderitem.getOrderItemDetails().get(j);
                                   
                ordAskLogdao.insert(ordAskLogItem.getAttributes());                 
             }
             
         } else { //删除记录
             ordServProductdao.deleteByKey(servorderitem.getAttributes());
             
             String whereCond = " and ord_item_id = ? ";
             List params = new ArrayList();
             params.add(servorderitem.get(BusiTables.ORD_SERV_PRODUCT.ord_item_id));
             ordAskLogdao.delete(whereCond, params);
             
         }

         if (KeyValues.OPER_FLAG_M.equals(servorderitem.getOperFlag())) { //再插入
             ordServProductdao.insert(servorderitem.getAttributes());;
             
             for (int i = 0; i < servorderitem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)servorderitem.getOrderItemDetails().get(i);
                 
                 ordAskLogdao.insert(ordasklogitem.getAttributes());
             }
         }
		
	}

	// 保存主产品订单项信息和产品订单项附加属性
	private static void saveOrdServProductAttr(OrderItem ordServAttrItem)
			throws FrameException {
        //将map中的订单项ID和客户标识写入List中
        ordServAttrItem.initOrderItemDetailsInfo();
        
        //如果其operFlag为"A"，插入一条记录
        if (KeyValues.OPER_FLAG_A.equals(ordServAttrItem.getOperFlag())) {
            ordServProductAttrdao.insert(ordServAttrItem.getAttributes());
            
            //新增时，没有ord_ask_log信息               
            for (int j = 0; j < ordServAttrItem.getOrderItemDetails().size(); j++) {
                OrderItemDetail ordasklogitem1 = (OrderItemDetail)ordServAttrItem.getOrderItemDetails().get(j);
                
                ordAskLogdao.insert(ordasklogitem1.getAttributes());
            }
            
        } else { //删除记录
            ordServProductAttrdao.deleteByKey(ordServAttrItem.getAttributes());
            
            String whereCond = " and ord_item_id = ? ";
            List params = new ArrayList();
            params.add(ordServAttrItem.get(BusiTables.ORD_SERV_PRODUCT_ATTR.ord_item_id));
            ordAskLogdao.delete(whereCond, params);
            
        }

        if (KeyValues.OPER_FLAG_M.equals(ordServAttrItem.getOperFlag())) { //再插入
            ordServProductAttrdao.insert(ordServAttrItem.getAttributes());;
            
            for (int i = 0; i < ordServAttrItem.getOrderItemDetails().size(); i++) {
                OrderItemDetail ordasklogitem = (OrderItemDetail)ordServAttrItem.getOrderItemDetails().get(i);
                
                ordAskLogdao.insert(ordasklogitem.getAttributes());
            }
        }
        
	}

	// 按照规格数据分解附属产品和附属产品属性
	private static void splitServProductAndAttr(Map servproductmap,
			List nservproductlist, List servproductattrs, Map common) {
		// 1.根据产品ID取规格属性
		Product pro = SpecsData.getProduct((String) servproductmap
				.get(BusiTables.SERV.product_id));

		// 产品属性表可能有的Key
		List attrkeylist = pro.getAttrByTableName(Keys.TABLE_SERV_PRODUCT_ATTR);

		// 附属产品属性表有记录
		if (null != attrkeylist && !attrkeylist.isEmpty()) {
			// 2.遍历map，找到主产品属性字段，组成纵表
			for (int i = 0; i < attrkeylist.size(); i++) {
				AttrRestrict attrrestrict = (AttrRestrict) attrkeylist.get(i);

				String key = attrrestrict.getField_name();
				String value = (String) servproductmap.get(key);

				// map有key，生成属性信息
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
		// 根据DAO,找到附属产品主表字段
		String[] servproductKeys = BusiTables.SERV_PRODUCT.TABLE_FIELDS;
		// 保存附属产品主表信息
		Map map = new HashMap();

		// 删除属性表key，剩下的就是主产品表key
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
