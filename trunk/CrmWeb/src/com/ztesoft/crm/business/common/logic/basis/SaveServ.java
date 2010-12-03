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

	// 不将公共信息如：SITE_NO放入serv,不利于新老数据比较
	public static void perform(Serv serv, Map common) throws Exception {
		// 1. 如果操作类型为K，那么退出；
		if (serv == null
				|| KeyValues.ACTION_TYPE_K.equals(serv.get(BusiTables.SERV.action_type))) {
			return;
		}
		/**最新数据***/
		Map nServMap = new HashMap();
		List nServMaps = new ArrayList();
		nServMaps.add(nServMap);
		List nServAttrMaps = new ArrayList();
		/**过程数据***/
		List oServMaps=new ArrayList();
		List oServAttrMaps=new ArrayList();
		/**订单项数据***/
		List ordServs=new ArrayList();
		List ordServAttrs=new ArrayList();
		// 2.根据规格配置数据把serv转换为nServMap, nServAttrMaps, 操作类型和serv一样;
		performConvert(serv,nServMap,nServAttrMaps);
		
		// 3.查询老数据：根据servID查询serv和serv_attr表，得到oServMap和oServAttrMaps;
		performLoad(serv,oServMaps,oServAttrMaps);
		
		// 4.比对nServMap和oServMap得到变更信息,同时oServMap为最新的,比对nServAttrMaps和oServAttrMaps得到变更信息：
		performCompare(serv,common,nServMaps,nServAttrMaps,oServMaps,oServAttrMaps,ordServs,ordServAttrs);
		
		// 5.刷新最新数据到serv对象中
		performRefresh(serv,oServMaps,oServAttrMaps,ordServs,ordServAttrs);
		
		// 6.保存数据到库表中
		performSave(serv,ordServs,ordServAttrs);
		

	}
	/**
	 * 根据规格配置数据把serv转换为nServMap, nServAttrMaps, 操作类型和serv一样
	 * @param serv
	 * @param nServMap
	 * @param nServAttrMaps
	 * */
	private static void performConvert(Serv serv,Map nServMap,List nServAttrMaps){
		
		splitServAndServAttr(serv.getAttributes(), nServMap, nServAttrMaps);
	}
	/**
	 *  查询老数据：根据servID查询serv和serv_attr表，得到oServMap和oServAttrMaps
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 * */
	private static void performLoad(Serv serv,List oServMaps,List oServAttrMaps) throws Exception{
		
		 oServMaps.addAll(loadServInfo(serv));
		 oServAttrMaps.addAll(loadServAttrInfo(serv));
	}
	/**
	 *  比对nServMap和oServMap得到变更信息,同时oServMap为最新的,比对nServAttrMaps和oServAttrMaps得到变更信息
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
        
		// 4.比对nServMap和oServMap得到变更信息,同时oServMap为最新的
		 ordServs.addAll(UtilTools.compare(servKey,nServMaps, oServMaps, common, Keys.IGNORE_FIELD_SERV));
		// 5.比对nServAttrMaps和oServAttrMaps得到变更信息：
		 ordServAttrs.addAll(UtilTools.compareAttr(servAttrKey, nServAttrMaps, oServAttrMaps, ordServs,
                 servKey, common, Keys.IGNORE_FIELD_SERV_ATTR));
	}
	/**
	 *  刷新最新数据到serv对象中
	 * @param serv
	 * @param oServMaps
	 * @param oServAttrMaps
	 * @param ordServs
	 * @param ordServAttrs
	 * */
	
	private static void performRefresh(Serv serv,List oServMaps,List oServAttrMaps,List ordServs,List ordServAttrs){
		//使用oServMap和oServAttrMaps数据跟新serv数据,返回过程表数据
		
		if(oServMaps!=null&&!oServMaps.isEmpty()){
			serv.loadFromMap((Map) oServMaps.get(0));
			serv.loadFromList(oServAttrMaps);
			serv.setActionType(KeyValues.ACTION_TYPE_K);
		}
		//返回过程表数据
		serv.getOrdItemInfo().setOrdServs(ordServs); // 只有一个
		serv.getOrdItemInfo().setOrdServAttrs(ordServAttrs);
		
	}
	/**
	 *  保存数据到库表中
	 * @param serv
	 * @param ordServs
	 * @param ordServAttrs
	 * */
	private static void performSave(Serv serv,List ordServs,List ordServAttrs) throws Exception{
		
		//保存最新数据到数据库：根据ordServ和ordServAttrs数据更新最新数据表serv和serv_attr
		servdao.saveNewinfo(ordServs);
		servattrdao.saveNewinfo(ordServAttrs);
		
		//保存过程表OrdServ表
		saveOrdServItems(serv.getOrdItemInfo());
	}
	

	// 加载产品信息
	private static List loadServInfo(Serv serv) throws FrameException {
		String whereCond = " and serv_id = ? ";
		List params = new ArrayList();
		params.add(serv.get(BusiTables.SERV.serv_id));

		return servdao.findByCond(whereCond, params);
	}

	// 加载产品属性信息
	private static List loadServAttrInfo(Serv serv) throws FrameException {
		String whereCond = "and serv_id = ? ";
		List whereCondParams = new ArrayList();

		whereCondParams.add(serv.get(BusiTables.SERV.serv_id));

		return servattrdao.findByCond(whereCond, whereCondParams);
	}

	// 保存主产品订单项信息和产品订单项附加属性
	private static void saveOrdServItems(OrdItemInfo nOrdItemInfo)
			throws FrameException {
		// 1.对于ordItemInfo中的ordServs,
		// ordServAttr….数据，以表为单位，这里以ordServAttrs为例子依次处理如下：
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

	// 保存主产品订单项信息和产品订单项附加属性
	private static void saveOrdServ(OrderItem ordServItem) throws FrameException {
        //将map中的订单项ID和客户标识写入List中
        ordServItem.initOrderItemDetailsInfo();
        
        //如果其operFlag为"A"，插入一条记录
         if (KeyValues.OPER_FLAG_A.equals(ordServItem.getOperFlag())) {
             ordservdao.insert(ordServItem.getAttributes());
             
             //新增时，没有ord_ask_log信息               
             for (int j = 0; j < ordServItem.getOrderItemDetails().size(); j++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)ordServItem.getOrderItemDetails().get(j);
                 
                 ordasklogdao.insert(ordasklogitem.getAttributes());
             }
             
         } else { //删除记录
             ordservdao.deleteByKey(ordServItem.getAttributes());
             
             String whereCond = " and ord_item_id = ? ";
             List params = new ArrayList();
             params.add(ordServItem.get("ord_item_id"));
             ordasklogdao.delete(whereCond, params);
             
         }

         if (KeyValues.OPER_FLAG_M.equals(ordServItem.getOperFlag())) { //再插入
             ordservdao.insert(ordServItem.getAttributes());
             
             for (int i = 0; i < ordServItem.getOrderItemDetails().size(); i++) {
                 OrderItemDetail ordasklogitem = (OrderItemDetail)ordServItem.getOrderItemDetails().get(i);
                 
                 ordasklogdao.insert(ordasklogitem.getAttributes());
             }
         }        
	}

	// 保存主产品订单项信息和产品订单项附加属性
	private static void saveOrdServAttr(OrderItem ordservattritem) throws FrameException {
        //将map中的订单项ID和客户标识写入List中
        ordservattritem.initOrderItemDetailsInfo();
        
        //如果其operFlag为"A"，插入一条记录
        if (KeyValues.OPER_FLAG_A.equals(ordservattritem.getOperFlag())) {
            ordservattrdao.insert(ordservattritem.getAttributes());
            
            //新增时，没有ord_ask_log信息               
            for (int j = 0; j < ordservattritem.getOrderItemDetails().size(); j++) {
                OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(j);
                
                ordasklogdao.insert(ordasklogitem.getAttributes());
            }
            
        } else { //删除记录
            ordservattrdao.deleteByKey(ordservattritem.getAttributes());
            
            String whereCond = " and ord_item_id = ? ";
            List params = new ArrayList();
            params.add(ordservattritem.get("ord_item_id"));
            ordasklogdao.delete(whereCond, params);
            
        }

        if (KeyValues.OPER_FLAG_M.equals(ordservattritem.getOperFlag())) { //再插入
            ordservattrdao.insert(ordservattritem.getAttributes());;
            
            for (int i = 0; i < ordservattritem.getOrderItemDetails().size(); i++) {
                OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(i);
                
                ordasklogdao.insert(ordasklogitem.getAttributes());
            }
        }  
        
	}

	// 按照规格数据分解主产品和产品属性
	private static void splitServAndServAttr(Map servmap, Map nservmap,
			List servattrs) {
		// 1.根据产品ID取规格属性
		Product pro = SpecsData.getProduct((String) servmap
				.get(BusiTables.SERV.product_id));

		// 产品属性表可能有的Key,List of attrRestrict对象
		List attrlist = pro.getAttrByTableName(Keys.TABLE_SERV_ATTR);

		// 产品属性表没有Key，则全部是主产品信息
		if (null != attrlist && !attrlist.isEmpty()) {
			// 2.遍历map，找到主产品属性字段，组成纵表
			for (int i = 0; i < attrlist.size(); i++) {
				AttrRestrict attrRestrict = (AttrRestrict) attrlist.get(i);

				String key = attrRestrict.getField_name();
				String value = (String) servmap.get(key);

				// map有key，生成属性信息
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

		// 2.根据产品DAO，找到主产品字段，
		String[] servKeys = BusiTables.SERV.TABLE_FIELDS;

		// 删除属性表key，剩下的就是主产品表key
		for (int j = 0; j < servKeys.length; j++) {
			String skey = servKeys[j];
			String svalue = (String) servmap.get(skey);
			if (svalue != null)
				nservmap.put(skey, svalue);
		}
	}
    
	// 加快初始化速度
	private static ServAttrDAO servattrdao = new ServAttrDAO();

	private static ServDAO servdao = new ServDAO();

	private static OrdServDAO ordservdao = new OrdServDAO();

	private static OrdServAttrDAO ordservattrdao = new OrdServAttrDAO();

	private static OrdAskLogDAO ordasklogdao = new OrdAskLogDAO();

}
