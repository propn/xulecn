package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.List;

public class OrdItemInfo extends Ord {
	// 1. ���ӱ�־
	private boolean newFlag = false;

	// 2. ɾ����־
	private boolean removeFlag = false;

	/*
	 * // . �ͻ�������ʶ private String cust_ord_id;
	 *  // 4. �ͻ���ʶ private String cust_id;
	 *  // 5. ����Ա private String staff_no;
	 *  // 6. ������ private String site_no;
	 */

	private List ordAsks = new ArrayList(); // ������Ϣ

	private List ordServs = new ArrayList(); // ����Ʒ������,����ΪOrderItem

	private List ordServAttrs = new ArrayList(); // ��Ʒ�������Զ�����,����ΪOrderItem

	private List ordServproducts = new ArrayList(); // ������Ʒ������,����ΪOrderItem

	private List ordServproductAttrs = new ArrayList(); // ������Ʒ�������Զ�����,����ΪOrderItem

	private List ordServAccts = new ArrayList(); // �����ƶ�����,����ΪOrderItem

	private List ordServBillPosts = new ArrayList(); // �˵�Ͷ�ݶ�����,����ΪOrderItem

	private List ordAccNbrs = new ArrayList(); // ���Ӻ��붩����,����ΪOrderItem

	private List ordServStates = new ArrayList(); // ��Ʒʵ��״̬������,����ΪOrderItem

	private List ordOfferInsts = new ArrayList(); // ����Ʒʵ��������,����ΪOrderItem

	private List ordOfferInstAttrs = new ArrayList(); // ����Ʒʵ��������,����ΪOrderItem

	private List ordOfferInstDetail = new ArrayList(); // ����Ʒʵ�����ɶ�����,����ΪOrderItem

	private List offerInstPartys = null; // 1. ����Ʒ��������Ϣ,����ΪOrderItem

	private List offerInstContacts = null; // 2. ����Ʒʵ����ϵ����Ϣ,����ΪOrderItem

	private List offerInstAssures = null; // 3. ����Ʒ������Ϣ,����ΪOrderItem

	private List ordBookDates = null; // 4. ҵ��ԤԼ��Ϣ,����ΪOrderItem

	private List ordRemoveCauses = null; // 5. ҵ�����ԭ��,����ΪOrderItem

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
