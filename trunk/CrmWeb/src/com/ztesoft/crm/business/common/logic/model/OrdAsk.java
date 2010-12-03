package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunny
 * @category功能：服务定单
 */
public class OrdAsk extends Ord {
	// 1主产品订单项
	private OrderItem ordServ = null;

	// 2产品附加属性订单项
	private List ordServAttrs = new ArrayList();

	// 3附属产品订单项
	private List ordServproducts = new ArrayList();

	// 4 附属产品附加属性订单项
	private List ordServproductAttrs = new ArrayList();

	// 5.帐务定制订单项
	private List ordServAccts = new ArrayList();

	// 6账单投递订单项
	private List ordServBillPosts = new ArrayList();

	// 7附加号码订单项
	private List ordAccNbrs = new ArrayList();

	// 8产品实例状态订单项
	private List ordServStates = new ArrayList();

	private boolean invalidFlag = false;

	// 9预约信息
	private List ordBookDates = new ArrayList();

	// 10业务原因信息
	private List ordCauses = new ArrayList();

	// 合并方法
	public void merge(OrdAsk mergeOrdAsk) {
		// 合并主产品信息变动
		if (this.ordServ == null)
			this.ordServ = mergeOrdAsk.ordServ;
		else if (mergeOrdAsk.ordServ != null
				&& mergeOrdAsk.ordServ.getOrderItemDetails() != null)
			this.ordServ.getOrderItemDetails().addAll(
					mergeOrdAsk.ordServ.getOrderItemDetails());
		// 合并主产品实例扩展属性
		this.ordServAttrs.addAll(mergeOrdAsk.ordServAttrs);
		// ….
		// ….
		mergeOrdAsk.invalidFlag = true;
	}

	// 设置定单信息
	/**
	 * 
	 */
	public void setOrdAskInfo() {
		// 设置各个ordXXX，
		// 以及ordXXX.ordItemDetail的ord_id为自己的ord_id;service_offer_id为自己的service_offer_id，
		// 如果相应的staff_no, sitte_no为空，那么也设置为服务定单的信息。

	}

}
