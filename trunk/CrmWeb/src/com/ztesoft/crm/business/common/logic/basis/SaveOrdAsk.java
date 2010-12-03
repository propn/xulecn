package com.ztesoft.crm.business.common.logic.basis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.order.dao.OrdAskDAO;
import com.ztesoft.crm.business.common.query.Sqls;

public class SaveOrdAsk {
	private static OrdAskDAO ordaskdao = new OrdAskDAO();
	
	

	/**
	 * ���񶩵����淽��
	 * @param ordAskList ���񶩵��б�
	 * @param common
	 * @param ask_id ͬ�ʶ�����
	 * @throws Exception
	 */
	public static void perform(List ordAskList, Map common,String ask_id) throws Exception {
				List whereCondParams = new ArrayList();
				whereCondParams.add(ask_id);
				//�Ƚ�֮ǰ���ɵ���ͬ��ͬ�ʶ�����ɾ���� �������µķ��񶩵�
				ordaskdao.delete("and ask_id = ? ", whereCondParams);
				ordaskdao.batchInsert(ordAskList, -1, -1, "---");
	}

	/**
	 * ��������ORD_ID���³�ASK_ID
	 * @param comp_ord_id
	 * @param ask_id
	 * @throws Exception
	 */
	public static void updateOrdAsk(String comp_ord_id,String ask_id) throws Exception {
		List whereCondParams = new ArrayList();
		whereCondParams.add(ask_id);
		whereCondParams.add(comp_ord_id);
		//�Ƚ�֮ǰ���ɵ���ͬ��ͬ�ʶ�����ɾ���� �������µķ��񶩵�
		ordaskdao.update(Sqls.getSql("updateCompOrdAsk"), whereCondParams);
		//ordaskdao.batchInsert(ordAskList, -1, -1, "---");
	}
	

/*	*//**
	 *  ɾ���Ѿ����ɵķ��񶩵�
	 * @param ordAskList
	 * @param common
	 * @param ask_id
	 * @throws Exception
	 *//*
	public static void deleteOrdAsk(List ordAskList, Map common,String ask_id) throws Exception {
				List whereCondParams = new ArrayList();
				whereCondParams.add(ask_id);
				//�Ƚ�֮ǰ���ɵ���ͬ��ͬ�ʶ�����ɾ���� �������µķ��񶩵�
				ordaskdao.delete("and ask_id = ? ", whereCondParams);
				
	}*/
	
}
