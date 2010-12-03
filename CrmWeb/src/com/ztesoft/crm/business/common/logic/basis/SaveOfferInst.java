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
		List nOfferMaps = new ArrayList(); //拆分后的销售品列表
		List nOfferAttrMaps = new ArrayList(); //拆分后的销售品属性列表

		try {			
			// 1.根据规格配置数据把销售品信息分解
			performConvert(offerInsts, nOfferMaps,nOfferAttrMaps);

			//2.取本地销售品信息和销售品属性信息
			List oOfferMaps = getOfferData(inst.get(BusiTables.OFFER_INST.comp_inst_id));
			List oOfferAttrMaps = getOfferAttrData(inst.get(BusiTables.OFFER_INST.comp_inst_id));
			
			//3.新老数据比较
			List offInstChangeItems = UtilTools.compare(new String[] { BusiTables.OFFER_INST.comp_inst_id },
                                           nOfferMaps, oOfferMaps, common, Keys.IGNORE_FIELD_OFFER_INST);
			List offInstAttrChangeItems = UtilTools.compareAttr(new String[] {BusiTables.OFFER_INST.product_offer_instance_id, BusiTables.OFFER_INST_ATTR.field_name },
					nOfferAttrMaps, oOfferAttrMaps, offInstChangeItems, new String[] { BusiTables.OFFER_INST.comp_inst_id }, common, Keys.IGNORE_FIELD_OFFER_INST_ATTR);
			

			//4.使用oOfferMaps和oOfferAttrMaps数据更新offer_inst数据
			performRefresh(offerInsts, oOfferMaps, oOfferAttrMaps);
		
			//5返回结果
			inst.getOrdItemInfo().setOrdOfferInsts(offInstChangeItems);
			inst.getOrdItemInfo().setOrdOfferInstAttrs(offInstAttrChangeItems);
			
            //6.保存数据到库表中
            performSave(inst,offInstChangeItems,offInstAttrChangeItems);

		} catch (Exception e) {
			throw new BusiException(e);
		}
		
	}
	
	// 刷新最新数据到CompInst对象中
	private static void performRefresh(List offerInsts, List oOfferMaps, List oOfferAttrMaps) {
		String[] keys = new String[]{BusiTables.OFFER_INST.product_offer_instance_id};
		for (int i = 0; i < offerInsts.size(); i++) {			 
			OfferInst offerInst = (OfferInst) offerInsts.get(i);
			
			if (offerInst == null) {
				continue;
			}				
			
			//取最新销售品信息
			Map offMap = UtilTools.locate(offerInst.getAttributes(), keys, oOfferMaps); 
			
			//更新销售品信息
			if (null != offMap) {
				offerInst.loadFromMap(offMap);
			}
			
			//取最新销售品属性
			List offAttrs = UtilTools.find(offerInst.getAttributes(), keys, oOfferAttrMaps);
			
			//更新销售品属性
			if (null != offAttrs || offAttrs.isEmpty()) {
				offerInst.loadFromList(offAttrs);
			}
			
			offerInst.set(BusiTables.OFFER_INST.action_type, KeyValues.ACTION_TYPE_K);
		}
	}

	public static List getOfferData(String scomp_inst_id) throws FrameException {

		// 基础销售品的销售品实例ID = 父销售品实例ID
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

	
	 //按照规格数据分解主产品和产品属性
	 private static void splitOfferInstAndAttr(Map oOfferInstMap, Map nOfferInstMap, List oOfferInstAttrs) {
		 //1.根据产品ID取规格属性
		 ProductOffer pdof = SpecsData.getProductOffer((String)oOfferInstMap.get(BusiTables.OFFER_INST.product_offer_id));
		 
		 //产品属性表可能有的Key,List of attrRestrict对象
		 List attrlist = pdof.getAttrByTableName(Keys.TABLE_OFFER_INST_ATTR);		 
		 
		 //产品属性表没有Key，则全部是主产品信息
		 if (null != attrlist && !attrlist.isEmpty()) {
			 //2.遍历map，找到主产品属性字段，组成纵表		
			 for (int i = 0; i < attrlist.size(); i++) {
				 AttrRestrict attrRestrict = (AttrRestrict)attrlist.get(i);
				 
				 String key = attrRestrict.getField_name(); //对应field_name
				 String value = (String) oOfferInstMap.get(key);
				 
				 //map有key，生成属性信息
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

        //2.根据产品DAO，找到主产品字段，		
		 String[] servKeys = BusiTables.OFFER_INST.TABLE_FIELDS;
		 
		 //删除属性表key，剩下的就是主产品表key
		 for (int j = 0; j < servKeys.length; j++) {
			 String skey = servKeys[j];
			 String svalue =(String) oOfferInstMap.get(skey);
			 if (svalue != null)
				 nOfferInstMap.put(skey, svalue);
		 }
	 }
	
	
     //保存附属产品订单项信息和附属产品订单项附加属性
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
		 
	 //保存主产品订单项信息和产品订单项附加属性
	 private static void saveOrdOfferInst(OrderItem ordOffer) throws FrameException {
         //将map中的订单项ID和客户标识写入List中
         ordOffer.initOrderItemDetailsInfo();
         
		 //如果其operFlag为"A"，插入一条记录
		 if (KeyValues.OPER_FLAG_A.equals(ordOffer.getOperFlag())) {
			 ordOfferInstdao.insert(ordOffer.getAttributes());
			 
			 //新增时，没有ord_ask_log信息				 
			 for (int j = 0; j < ordOffer.getOrderItemDetails().size(); j++) {
				 OrderItemDetail ordasklogitem = (OrderItemDetail)ordOffer.getOrderItemDetails().get(j);
                 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
			 
		 } else { //删除记录
			 ordOfferInstdao.deleteByKey(ordOffer.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(ordOffer.get("ord_item_id"));
			 ordAskLogdao.delete(whereCond, params);
			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(ordOffer.getOperFlag())) { //再插入
			 ordOfferInstdao.insert(ordOffer.getAttributes());;
			 
			 for (int i = 0; i < ordOffer.getOrderItemDetails().size(); i++) {
				 OrderItemDetail ordasklogitem = (OrderItemDetail)ordOffer.getOrderItemDetails().get(i);
				 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
		 }
	 }

	 //保存主产品订单项信息和产品订单项附加属性
	 private static void saveOrdOfferInstAttr(OrderItem ordservattritem) throws FrameException {
         //将map中的订单项ID和客户标识写入List中
         ordservattritem.initOrderItemDetailsInfo();
         
		 //如果其operFlag为"A"，插入一条记录
		 if (KeyValues.OPER_FLAG_A.equals(ordservattritem.getOperFlag())) {
			 ordOfferInstAttrdao.insert(ordservattritem.getAttributes());
			 
			 //新增时，没有ord_ask_log信息				 
			 for (int j = 0; j < ordservattritem.getOrderItemDetails().size(); j++) {
				 OrderItemDetail ordasklogitem = (OrderItemDetail)ordservattritem.getOrderItemDetails().get(j);
				 
				 ordAskLogdao.insert(ordasklogitem.getAttributes());
			 }
			 
		 } else { //删除记录
			 ordOfferInstAttrdao.deleteByKey(ordservattritem.getAttributes());
			 
			 String whereCond = " and ord_item_id = ? ";
			 List params = new ArrayList();
			 params.add(ordservattritem.get("ord_item_id"));
			 ordAskLogdao.delete(whereCond, params);
			 
		 }

		 if (KeyValues.OPER_FLAG_M.equals(ordservattritem.getOperFlag())) { //再插入
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

			//根据规格配置数据把serv转换为nServMap, nServAttrMaps, 操作类型和serv一样;
			splitOfferInstAndAttr(offerInst.getAttributes(), map, list);
			
			if (null != map) {
				nOfferMaps.add(map);
				nOfferAttrMaps.addAll(list);
			}			
		}
	 }
     
        /** 
        * @Title: performSave 
        * @Description: 保存数据到库表中
        * @param inst  销售品数据
        * @param offInstChangeItems  销售品新老数据对比结果
        * @param offInstAttrChangeItems  销售品属性新老数据对比结果
        * @param Exception     
        * @return void    返回类型 
        * @throws 
        */
        private static void performSave(CompInst inst,List offInstChangeItems,List offInstAttrChangeItems) throws Exception{            
            //根据ordOffers和ordOfferAttrs数据更新最新数据表offer_inst和offer_inst_attr            
            offerInstDao.saveNewinfo(offInstChangeItems);
            attrDao.saveNewinfo(offInstAttrChangeItems);
            
            //保存过程表OrdServ表
            saveOrdOfferinstItems(inst.getOrdItemInfo());
        }
     
    private static OffInstDAO offerInstDao = new OffInstDAO();
    private static OffInstAttrDAO attrDao = new OffInstAttrDAO();
    private static OrdOffInstDAO ordOfferInstdao = new OrdOffInstDAO();
    private static OrdOffInstAttrDAO ordOfferInstAttrdao = new OrdOffInstAttrDAO(); //要添加dao
    private static OrdAskLogDAO ordAskLogdao = new OrdAskLogDAO();     
     
}
