package com.ztesoft.crm.business.common.logic.basis;

import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.tools.UtilTools;
import com.ztesoft.crm.business.common.utils.SeqUtil;

public final class OrdItemUtil {

	private static OrdItemUtil instance = null;

	private OrdItemUtil() {

	}

	public static OrdItemUtil getInst() {

		if (instance == null) {
			synchronized (OrdItemUtil.class) {

				if (instance == null) {
					instance = new OrdItemUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 生成一个增加的订单项
	 * 
	 * @param oMap  老的记录
	 * @param nMap  最新记录
	 * @param common 公共信息
	 */
	public static OrderItem create(Map oMap, Map nMap, Map common) {

		OrderItem ordItem = new OrderItem();
		ordItem.loadFromMap(nMap);
		
		//取公共数据到OrderItem中
		setCommonDataToOrdItem(ordItem, common);
        
        //WANJFTODO 新增时，将state置为"00N",对有些表如SERV_ATTR等会产生冗余字段，
        //暂时没有影响,因为入库时会根据表字段去取值。但不利于后期扩展
        ordItem.set(BusiTables.SERV.state, KeyValues.STATE_ONWAY);
		
		// 设置操作类型为A
		ordItem.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_A);
		
		return ordItem;
	}   
    

	/**
	 * 生成一个修改的订单项
	 * 
	 * @param oMap 老的记录
	 * @param nMap 最新记录
	 * @param common 公共信息
	 */
	public static OrderItem update(Map oMap, Map nMap, Map common) {
		return update(oMap, nMap, common, null);
	}
    
    /**
     * 生成一个修改的订单项
     * 
     * @param oMap 老的记录
     * @param nMap 最新记录
     * @param common 公共信息
     * @param noCompareStr 不要比较的Keys，用逗号分隔, 如果为空，则不比较默认的字段
     */
    public static OrderItem update(Map oMap, Map nMap, Map common, String noCompareStr) {
        OrderItem ordItem = new OrderItem();
        ordItem.loadFromMap(oMap); //取合并前的数据
        
        List orditemdetails = null;
        if (null == noCompareStr || "".equals(noCompareStr)) {
            orditemdetails = UtilTools.diff(oMap, nMap);
        } else {
            orditemdetails = UtilTools.diff(oMap, nMap, noCompareStr);
        }
        
        //取公共数据到OrderItem中
        setCommonDataToOrdItem(ordItem, common);
        // 设置操作类型为M
        ordItem.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_M);
        
        ordItem.setOrderItemDetails(orditemdetails);
        
        if (orditemdetails == null || orditemdetails.size() <= 0)
            return null;
        else
            return ordItem;
    }

	/**
	 * 生成一个删除的订单项
	 * 
	 * @param oMap 老的记录
	 * @param nMap 最新记录
	 * @param common 公共信息
	 */
	public static OrderItem delete(Map oMap, Map nMap, Map common) {
		OrderItem ordItem = new OrderItem();
		ordItem.loadFromMap(oMap);
		
		//取公共数据到OrderItem中
		setCommonDataToOrdItem(ordItem, common);
		
		// 设置操作类型为D
		ordItem.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_D);

		return ordItem;
	}   

	/**
	 * 新老订单项进行合并
	 */
	public static List merge(List ords, List oldOrds, String[] keys) {
		// 如果老的订单项为空，则代表不需要合并直接返回最新的订单项数据
		if (oldOrds == null) {
			for (int i = 0; i < ords.size(); i++) {
				OrderItem ord = (OrderItem) ords.get(i);
				// 为每个ord设置ord_item_id (OrdItemIdManager.getNextVal())； 返回ords;
				ord.set(BusiTables.SERV.ord_item_id, SeqUtil.getInst()
						.getNext("SEQ_ORD_ITEM_ID"));
			}
			return ords;
		}
		for (int i = 0; i < ords.size(); i++) {
			// 1. 确认是否存在受理中的相同订单项信息
			OrderItem ord = (OrderItem) ords.get(i);
			OrderItem oldOrd = (OrderItem) UtilTools.locate(ord, keys, oldOrds);
			if (oldOrd == null)
				ord.set(BusiTables.SERV.ord_item_id, SeqUtil.getInst()
						.getNext("SEQ_ORD_ITEM_ID"));

			// ord.put(BusiTables.SERV.ord_item_id, oldOrd.get(BusiTables.SERV.ord_item_id));

			String oldOrdOperFlag = oldOrd.getOperFlag();
			String ordOperFlag = ord.getOperFlag();

			if (KeyValues.OPER_FLAG_A.equals(oldOrdOperFlag)
					&& (KeyValues.OPER_FLAG_A.equals(ordOperFlag) || KeyValues.OPER_FLAG_M
							.equals(ordOperFlag))) {
				// 如果oldOrd操作类型为A, ord操作类型为A或者M，操作类为A，用新ORD数据更老新ORD数据
				ord.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_A);
				ord.set(BusiTables.SERV.ord_item_id, oldOrd.get(BusiTables.SERV.ord_item_id));
				ord.set(BusiTables.SERV.staff_no, oldOrd.get(BusiTables.SERV.staff_no));
				ord.set(BusiTables.SERV.site_no, oldOrd.get(BusiTables.SERV.site_no));
				ord.set(BusiTables.ORD_ASK.service_offer_id, oldOrd.get(BusiTables.ORD_ASK.service_offer_id));
				ord.set(BusiTables.ORD_ASK.ord_id, oldOrd.get(BusiTables.ORD_ASK.ord_id));
				ord.set(BusiTables.ORD_ASK.cust_ord_id, oldOrd.get(BusiTables.ORD_ASK.cust_ord_id));
				ord.setOperFlag(KeyValues.OPER_FLAG_M);
			} else if (KeyValues.OPER_FLAG_A.equals(oldOrdOperFlag)
					&& KeyValues.OPER_FLAG_D.equals(ordOperFlag)) {
				// 如果原ORD操作类型为A,当前操作类型为D,那么删除原ORD数据, 该操作被抵消
				ord.setOperFlag(KeyValues.OPER_FLAG_D);

			} else if (KeyValues.OPER_FLAG_A.equals(oldOrdOperFlag)
					&& (KeyValues.OPER_FLAG_A.equals(ordOperFlag) || KeyValues.OPER_FLAG_M
							.equals(ordOperFlag))) {
				/*
				 * 如果原ORD操作类型为M，当前ORD操作类型为A或者M，那么操作类型为M，ORD数据保存不变，用最新数据和老ORD数据比较，重新计算受理日志信息
				 */
				ord.setOrderItemDetails(UtilTools.diff(oldOrd.getAttributes(),
						ord.getAttributes()));
				ord.setOperFlag(KeyValues.OPER_FLAG_M);
			} else if (KeyValues.OPER_FLAG_M.equals(oldOrdOperFlag)
					&& KeyValues.OPER_FLAG_D.equals(ordOperFlag)) {
				/*
				 * 如果原ORD操作类型为M，当前ORD操作类型为A或者M，那么操作类型为M，ORD数据保存不变，用最新数据和老ORD数据比较，重新计算受理日志信息
				 */
				ord.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_D);
				ord.setOperFlag(KeyValues.OPER_FLAG_M);
			} else if (KeyValues.OPER_FLAG_D.equals(oldOrdOperFlag)
					&& (KeyValues.OPER_FLAG_A.equals(ordOperFlag) || KeyValues.OPER_FLAG_M
							.equals(ordOperFlag))) {
				// 如果原ORD操作为D，当前ORD操作为A，M，那么操作类型为M,
				// ORD数据不变，用最新数据和老ORD数据比较，重新计算受理日志信息。
				ord.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_M);
				ord.setOrderItemDetails(UtilTools.diff(oldOrd.getAttributes(),
						ord.getAttributes()));

				if (ord.getOrderItemDetails().size() == 0)
					ord.setOperFlag(KeyValues.OPER_FLAG_D);
				else
					ord.setOperFlag(KeyValues.OPER_FLAG_M);
			} else if (KeyValues.OPER_FLAG_D.equals(oldOrdOperFlag)
					&& KeyValues.OPER_FLAG_D.equals(ordOperFlag)) {
				/*
				 * 如果原ORD操作为D，当前ORD操作为D，那么不改变
				 */
				ord.setOperFlag(KeyValues.OPER_FLAG_K);
			}

		}
		return ords;

	}
	
	//取公共数据到OrderItem中
	private static OrderItem setCommonDataToOrdItem (OrderItem ordItem, Map common) {
		//设置订单项目的客户订单标识

		ordItem.set(BusiTables.ORD_ASK.cust_ord_id, Const.getStrValue(common, BusiTables.ORD_ASK.cust_ord_id));
		ordItem.set(BusiTables.ORD_ASK.ask_id, Const.getStrValue(common, BusiTables.ORD_ASK.ask_id));
		ordItem.set(BusiTables.ORD_ASK.ord_id, Const.getStrValue(common, BusiTables.ORD_ASK.ord_id));
		ordItem.set(BusiTables.SERV.staff_no, Const.getStrValue(common, BusiTables.SERV.staff_no));
		ordItem.set(BusiTables.SERV.site_no, Const.getStrValue(common, BusiTables.SERV.site_no));
		ordItem.set(BusiTables.SERV.business_id, Const.getStrValue(common, BusiTables.SERV.business_id));
		ordItem.set(BusiTables.SERV.lan_id, Const.getStrValue(common, BusiTables.SERV.lan_id));

		return ordItem;
	}
	
	

}
