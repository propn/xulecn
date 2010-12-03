package com.ztesoft.crm.business.common.logic.model;

import com.ztesoft.common.dao.SequenceManageDAOImpl;

public class OrdItemId {
	private static long nextValue = 1000;

	public static String getNextVal() {

		// 1.����Ѿ�������һ��500��ˮ����ô�����ݿ�������һ��500��ˮ
		if (nextValue % 500 == 0) {
			SequenceManageDAOImpl seq = new SequenceManageDAOImpl();
			String nextVal = seq.getNextSequence("ORD_SERV", "ORD_ITEM_ID");
			nextValue = Long.parseLong(nextVal);
		}

		return (++nextValue) + "";

	}

}
