package com.ztesoft.crm.business.common.logic.basis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.order.dao.OrdAskDAO;
import com.ztesoft.crm.business.common.query.Sqls;

public class SaveOrdAsk {
	private static OrdAskDAO ordaskdao = new OrdAskDAO();
	
	

	/**
	 * 服务订单保存方法
	 * @param ordAskList 服务订单列表
	 * @param common
	 * @param ask_id 同笔订单号
	 * @throws Exception
	 */
	public static void perform(List ordAskList, Map common,String ask_id) throws Exception {
				List whereCondParams = new ArrayList();
				whereCondParams.add(ask_id);
				//先将之前生成的相同的同笔订单号删除后 再增加新的服务订单
				ordaskdao.delete("and ask_id = ? ", whereCondParams);
				ordaskdao.batchInsert(ordAskList, -1, -1, "---");
	}

	/**
	 * 将主单的ORD_ID更新成ASK_ID
	 * @param comp_ord_id
	 * @param ask_id
	 * @throws Exception
	 */
	public static void updateOrdAsk(String comp_ord_id,String ask_id) throws Exception {
		List whereCondParams = new ArrayList();
		whereCondParams.add(ask_id);
		whereCondParams.add(comp_ord_id);
		//先将之前生成的相同的同笔订单号删除后 再增加新的服务订单
		ordaskdao.update(Sqls.getSql("updateCompOrdAsk"), whereCondParams);
		//ordaskdao.batchInsert(ordAskList, -1, -1, "---");
	}
	

/*	*//**
	 *  删除已经生成的服务订单
	 * @param ordAskList
	 * @param common
	 * @param ask_id
	 * @throws Exception
	 *//*
	public static void deleteOrdAsk(List ordAskList, Map common,String ask_id) throws Exception {
				List whereCondParams = new ArrayList();
				whereCondParams.add(ask_id);
				//先将之前生成的相同的同笔订单号删除后 再增加新的服务订单
				ordaskdao.delete("and ask_id = ? ", whereCondParams);
				
	}*/
	
}
