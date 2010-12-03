package com.ztesoft.crm.business.common.logic.model;

/**
 * 因为系统需要使用比较多的订单项流水，所有一次获取500个流水，
 * 并通过一个管理器管理起来，具体逻辑如下：
 * @author sunny
 *
 */
public class OrdItemIdManager {
	private static ThreadLocal ordItemIdContainer = new ThreadLocal();

	public static String getNextVal() {
		OrdItemId ordItemId = (OrdItemId) ordItemIdContainer.get();
		if (ordItemId == null) {
			ordItemId = new OrdItemId();
			ordItemIdContainer.set(ordItemId);
		}
		return OrdItemId.getNextVal();
	}
}
