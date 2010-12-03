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
	 * ����һ�����ӵĶ�����
	 * 
	 * @param oMap  �ϵļ�¼
	 * @param nMap  ���¼�¼
	 * @param common ������Ϣ
	 */
	public static OrderItem create(Map oMap, Map nMap, Map common) {

		OrderItem ordItem = new OrderItem();
		ordItem.loadFromMap(nMap);
		
		//ȡ�������ݵ�OrderItem��
		setCommonDataToOrdItem(ordItem, common);
        
        //WANJFTODO ����ʱ����state��Ϊ"00N",����Щ����SERV_ATTR�Ȼ���������ֶΣ�
        //��ʱû��Ӱ��,��Ϊ���ʱ����ݱ��ֶ�ȥȡֵ���������ں�����չ
        ordItem.set(BusiTables.SERV.state, KeyValues.STATE_ONWAY);
		
		// ���ò�������ΪA
		ordItem.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_A);
		
		return ordItem;
	}   
    

	/**
	 * ����һ���޸ĵĶ�����
	 * 
	 * @param oMap �ϵļ�¼
	 * @param nMap ���¼�¼
	 * @param common ������Ϣ
	 */
	public static OrderItem update(Map oMap, Map nMap, Map common) {
		return update(oMap, nMap, common, null);
	}
    
    /**
     * ����һ���޸ĵĶ�����
     * 
     * @param oMap �ϵļ�¼
     * @param nMap ���¼�¼
     * @param common ������Ϣ
     * @param noCompareStr ��Ҫ�Ƚϵ�Keys���ö��ŷָ�, ���Ϊ�գ��򲻱Ƚ�Ĭ�ϵ��ֶ�
     */
    public static OrderItem update(Map oMap, Map nMap, Map common, String noCompareStr) {
        OrderItem ordItem = new OrderItem();
        ordItem.loadFromMap(oMap); //ȡ�ϲ�ǰ������
        
        List orditemdetails = null;
        if (null == noCompareStr || "".equals(noCompareStr)) {
            orditemdetails = UtilTools.diff(oMap, nMap);
        } else {
            orditemdetails = UtilTools.diff(oMap, nMap, noCompareStr);
        }
        
        //ȡ�������ݵ�OrderItem��
        setCommonDataToOrdItem(ordItem, common);
        // ���ò�������ΪM
        ordItem.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_M);
        
        ordItem.setOrderItemDetails(orditemdetails);
        
        if (orditemdetails == null || orditemdetails.size() <= 0)
            return null;
        else
            return ordItem;
    }

	/**
	 * ����һ��ɾ���Ķ�����
	 * 
	 * @param oMap �ϵļ�¼
	 * @param nMap ���¼�¼
	 * @param common ������Ϣ
	 */
	public static OrderItem delete(Map oMap, Map nMap, Map common) {
		OrderItem ordItem = new OrderItem();
		ordItem.loadFromMap(oMap);
		
		//ȡ�������ݵ�OrderItem��
		setCommonDataToOrdItem(ordItem, common);
		
		// ���ò�������ΪD
		ordItem.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_D);

		return ordItem;
	}   

	/**
	 * ���϶�������кϲ�
	 */
	public static List merge(List ords, List oldOrds, String[] keys) {
		// ����ϵĶ�����Ϊ�գ��������Ҫ�ϲ�ֱ�ӷ������µĶ���������
		if (oldOrds == null) {
			for (int i = 0; i < ords.size(); i++) {
				OrderItem ord = (OrderItem) ords.get(i);
				// Ϊÿ��ord����ord_item_id (OrdItemIdManager.getNextVal())�� ����ords;
				ord.set(BusiTables.SERV.ord_item_id, SeqUtil.getInst()
						.getNext("SEQ_ORD_ITEM_ID"));
			}
			return ords;
		}
		for (int i = 0; i < ords.size(); i++) {
			// 1. ȷ���Ƿ���������е���ͬ��������Ϣ
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
				// ���oldOrd��������ΪA, ord��������ΪA����M��������ΪA������ORD���ݸ�����ORD����
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
				// ���ԭORD��������ΪA,��ǰ��������ΪD,��ôɾ��ԭORD����, �ò���������
				ord.setOperFlag(KeyValues.OPER_FLAG_D);

			} else if (KeyValues.OPER_FLAG_A.equals(oldOrdOperFlag)
					&& (KeyValues.OPER_FLAG_A.equals(ordOperFlag) || KeyValues.OPER_FLAG_M
							.equals(ordOperFlag))) {
				/*
				 * ���ԭORD��������ΪM����ǰORD��������ΪA����M����ô��������ΪM��ORD���ݱ��治�䣬���������ݺ���ORD���ݱȽϣ����¼���������־��Ϣ
				 */
				ord.setOrderItemDetails(UtilTools.diff(oldOrd.getAttributes(),
						ord.getAttributes()));
				ord.setOperFlag(KeyValues.OPER_FLAG_M);
			} else if (KeyValues.OPER_FLAG_M.equals(oldOrdOperFlag)
					&& KeyValues.OPER_FLAG_D.equals(ordOperFlag)) {
				/*
				 * ���ԭORD��������ΪM����ǰORD��������ΪA����M����ô��������ΪM��ORD���ݱ��治�䣬���������ݺ���ORD���ݱȽϣ����¼���������־��Ϣ
				 */
				ord.set(BusiTables.SERV.action_type, KeyValues.ACTION_TYPE_D);
				ord.setOperFlag(KeyValues.OPER_FLAG_M);
			} else if (KeyValues.OPER_FLAG_D.equals(oldOrdOperFlag)
					&& (KeyValues.OPER_FLAG_A.equals(ordOperFlag) || KeyValues.OPER_FLAG_M
							.equals(ordOperFlag))) {
				// ���ԭORD����ΪD����ǰORD����ΪA��M����ô��������ΪM,
				// ORD���ݲ��䣬���������ݺ���ORD���ݱȽϣ����¼���������־��Ϣ��
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
				 * ���ԭORD����ΪD����ǰORD����ΪD����ô���ı�
				 */
				ord.setOperFlag(KeyValues.OPER_FLAG_K);
			}

		}
		return ords;

	}
	
	//ȡ�������ݵ�OrderItem��
	private static OrderItem setCommonDataToOrdItem (OrderItem ordItem, Map common) {
		//���ö�����Ŀ�Ŀͻ�������ʶ

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
