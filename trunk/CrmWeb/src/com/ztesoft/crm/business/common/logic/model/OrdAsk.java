package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunny
 * @category���ܣ����񶨵�
 */
public class OrdAsk extends Ord {
	// 1����Ʒ������
	private OrderItem ordServ = null;

	// 2��Ʒ�������Զ�����
	private List ordServAttrs = new ArrayList();

	// 3������Ʒ������
	private List ordServproducts = new ArrayList();

	// 4 ������Ʒ�������Զ�����
	private List ordServproductAttrs = new ArrayList();

	// 5.�����ƶ�����
	private List ordServAccts = new ArrayList();

	// 6�˵�Ͷ�ݶ�����
	private List ordServBillPosts = new ArrayList();

	// 7���Ӻ��붩����
	private List ordAccNbrs = new ArrayList();

	// 8��Ʒʵ��״̬������
	private List ordServStates = new ArrayList();

	private boolean invalidFlag = false;

	// 9ԤԼ��Ϣ
	private List ordBookDates = new ArrayList();

	// 10ҵ��ԭ����Ϣ
	private List ordCauses = new ArrayList();

	// �ϲ�����
	public void merge(OrdAsk mergeOrdAsk) {
		// �ϲ�����Ʒ��Ϣ�䶯
		if (this.ordServ == null)
			this.ordServ = mergeOrdAsk.ordServ;
		else if (mergeOrdAsk.ordServ != null
				&& mergeOrdAsk.ordServ.getOrderItemDetails() != null)
			this.ordServ.getOrderItemDetails().addAll(
					mergeOrdAsk.ordServ.getOrderItemDetails());
		// �ϲ�����Ʒʵ����չ����
		this.ordServAttrs.addAll(mergeOrdAsk.ordServAttrs);
		// ��.
		// ��.
		mergeOrdAsk.invalidFlag = true;
	}

	// ���ö�����Ϣ
	/**
	 * 
	 */
	public void setOrdAskInfo() {
		// ���ø���ordXXX��
		// �Լ�ordXXX.ordItemDetail��ord_idΪ�Լ���ord_id;service_offer_idΪ�Լ���service_offer_id��
		// �����Ӧ��staff_no, sitte_noΪ�գ���ôҲ����Ϊ���񶨵�����Ϣ��

	}

}
