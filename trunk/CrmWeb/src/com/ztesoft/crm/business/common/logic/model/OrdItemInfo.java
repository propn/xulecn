package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.List;

public class OrdItemInfo extends Ord {
	// 1. 增加标志
	private boolean newFlag = false;

	// 2. 删除标志
	private boolean removeFlag = false;

	/*
	 * // . 客户订单标识 private String cust_ord_id;
	 *  // 4. 客户标识 private String cust_id;
	 *  // 5. 操作员 private String staff_no;
	 *  // 6. 操作点 private String site_no;
	 */

	private List ordAsks = new ArrayList(); // 定单信息

	private List ordServs = new ArrayList(); // 主产品订单项,类型为OrderItem

	private List ordServAttrs = new ArrayList(); // 产品附加属性订单项,类型为OrderItem

	private List ordServproducts = new ArrayList(); // 附属产品订单项,类型为OrderItem

	private List ordServproductAttrs = new ArrayList(); // 附属产品附加属性订单项,类型为OrderItem

	private List ordServAccts = new ArrayList(); // 帐务定制订单项,类型为OrderItem

	private List ordServBillPosts = new ArrayList(); // 账单投递订单项,类型为OrderItem

	private List ordAccNbrs = new ArrayList(); // 附加号码订单项,类型为OrderItem

	private List ordServStates = new ArrayList(); // 产品实例状态订单项,类型为OrderItem

	private List ordOfferInsts = new ArrayList(); // 销售品实例订单项,类型为OrderItem

	private List ordOfferInstAttrs = new ArrayList(); // 销售品实例订单项,类型为OrderItem

	private List ordOfferInstDetail = new ArrayList(); // 销售品实例构成订单项,类型为OrderItem

	private List offerInstPartys = null; // 1. 销售品参与人信息,类型为OrderItem

	private List offerInstContacts = null; // 2. 销售品实例联系人信息,类型为OrderItem

	private List offerInstAssures = null; // 3. 销售品担保信息,类型为OrderItem

	private List ordBookDates = null; // 4. 业务预约信息,类型为OrderItem

	private List ordRemoveCauses = null; // 5. 业务办理原因,类型为OrderItem

	public List getOfferInstAssures() {
		return offerInstAssures;
	}

	public void setOfferInstAssures(List offerInstAssures) {
		this.offerInstAssures = offerInstAssures;
	}

	public List getOfferInstContacts() {
		return offerInstContacts;
	}

	public void setOfferInstContacts(List offerInstContacts) {
		this.offerInstContacts = offerInstContacts;
	}

	public List getOfferInstPartys() {
		return offerInstPartys;
	}

	public void setOfferInstPartys(List offerInstPartys) {
		this.offerInstPartys = offerInstPartys;
	}

	public List getOrdBookDates() {
		return ordBookDates;
	}

	public void setOrdBookDates(List ordBookDates) {
		this.ordBookDates = ordBookDates;
	}

	public List getOrdOfferInstDetail() {
		return ordOfferInstDetail;
	}

	public void setOrdOfferInstDetail(List ordOfferInstDetail) {
		this.ordOfferInstDetail = ordOfferInstDetail;
	}

	public List getOrdOfferInsts() {
		return ordOfferInsts;
	}

	public void setOrdOfferInsts(List ordOfferInsts) {
		this.ordOfferInsts = ordOfferInsts;
	}

	public List getOrdRemoveCauses() {
		return ordRemoveCauses;
	}

	public void setOrdRemoveCauses(List ordRemoveCauses) {
		this.ordRemoveCauses = ordRemoveCauses;
	}

	public boolean isNewFlag() {
		return newFlag;
	}

	public void setNewFlag(boolean newFlag) {
		this.newFlag = newFlag;
	}

	public List getOrdAccNbrs() {
		return ordAccNbrs;
	}

	public void setOrdAccNbrs(List ordAccNbrs) {
		this.ordAccNbrs = ordAccNbrs;
	}

	public List getOrdAsks() {
		return ordAsks;
	}

	public void setOrdAsks(List ordAsks) {
		this.ordAsks = ordAsks;
	}

	public List getOrdServAccts() {
		return ordServAccts;
	}

	public void setOrdServAccts(List ordServAccts) {
		this.ordServAccts = ordServAccts;
	}

	public List getOrdServAttrs() {
		return ordServAttrs;
	}

	public void setOrdServAttrs(List ordServAttrs) {
		this.ordServAttrs = ordServAttrs;
	}

	public List getOrdServBillPosts() {
		return ordServBillPosts;
	}

	public void setOrdServBillPosts(List ordServBillPosts) {
		this.ordServBillPosts = ordServBillPosts;
	}

	public List getOrdServproductAttrs() {
		return ordServproductAttrs;
	}

	public void setOrdServproductAttrs(List ordServproductAttrs) {
		this.ordServproductAttrs = ordServproductAttrs;
	}

	public List getOrdServproducts() {
		return ordServproducts;
	}

	public void setOrdServproducts(List ordServproducts) {
		this.ordServproducts = ordServproducts;
	}

	public List getOrdServStates() {
		return ordServStates;
	}

	public void setOrdServStates(List ordServStates) {
		this.ordServStates = ordServStates;
	}

	public boolean isRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}

	public List getOrdOfferInstAttrs() {
		return ordOfferInstAttrs;
	}

	public void setOrdOfferInstAttrs(List ordOfferInstAttrs) {
		this.ordOfferInstAttrs = ordOfferInstAttrs;
	}

	public List getOrdServs() {
		return ordServs;
	}

	public void setOrdServs(List ordServs) {
		this.ordServs = ordServs;
	}

}
