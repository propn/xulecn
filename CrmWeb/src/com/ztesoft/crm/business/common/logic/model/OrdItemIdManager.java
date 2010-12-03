package com.ztesoft.crm.business.common.logic.model;

/**
 * ��Ϊϵͳ��Ҫʹ�ñȽ϶�Ķ�������ˮ������һ�λ�ȡ500����ˮ��
 * ��ͨ��һ�����������������������߼����£�
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
