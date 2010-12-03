package com.ztesoft.crm.business.common.logic.model;

import com.ztesoft.common.dao.SequenceManageDAOImpl;

public class OrdItemId {
	private static long nextValue = 1000;

	public static String getNextVal() {

		// 1.如果已经用完上一个500流水，那么向数据库申请下一个500流水
		if (nextValue % 500 == 0) {
			SequenceManageDAOImpl seq = new SequenceManageDAOImpl();
			String nextVal = seq.getNextSequence("ORD_SERV", "ORD_ITEM_ID");
			nextValue = Long.parseLong(nextVal);
		}

		return (++nextValue) + "";

	}

}
