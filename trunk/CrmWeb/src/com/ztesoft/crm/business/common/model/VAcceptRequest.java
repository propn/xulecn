package com.ztesoft.crm.business.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VAcceptRequest extends VOrderRequest {
	/** *****************��Ʒ������Ϣ*********************** */
	// ��Ʒʵ������
	List servs = new ArrayList();

	// ������Ʒ��������
	List servAttrs = new ArrayList();
	
	// ������Ʒ����
	List servProducts = new ArrayList();
	
	// ������Ʒ��������
	List servProductAttrs = new ArrayList();

	// ����������
	List servAccts = new ArrayList();

	// ��Ʒʵ��״̬
	List servStates = new ArrayList();

	// ��Ʒʵ��״̬
	List servAccNbrs = new ArrayList();

	// �˵�Ͷ����Ϣ
	List servBillPosts = new ArrayList();

	/** ****************����Ʒ������Ϣ************************ */
	// ����Ʒʵ������
	List offerInsts = new ArrayList();
	
	// ����Ʒ����ʵ������
	List offerInstAttrs = new ArrayList();

	// ����Ʒʵ����������
	List offerInstDetails = new ArrayList();

	// ��Դʵ���Ʒ
	List entProducts = new ArrayList();

	// ����Ʒ��������Ϣ
	List offerInstParties = new ArrayList();

	// ����Ʒ��������Ϣ
	List offerInstAssures = new ArrayList();
	
	// ����Ʒ��ϸ��Ӧ��Ʒʵ��ID
	HashMap servIds = new HashMap();
	
	public List getEntProducts() {
		return entProducts;
	}

	public void setEntProducts(List entProducts) {
		this.entProducts = entProducts;
	}

	public List getOfferInstAssures() {
		return offerInstAssures;
	}

	public void setOfferInstAssures(List offerInstAssures) {
		this.offerInstAssures = offerInstAssures;
	}

	public List getOfferInstDetails() {
		return offerInstDetails;
	}

	public void setOfferInstDetails(List offerInstDetails) {
		this.offerInstDetails = offerInstDetails;
	}

	public List getOfferInstParties() {
		return offerInstParties;
	}

	public void setOfferInstParties(List offerInstParties) {
		this.offerInstParties = offerInstParties;
	}

	public List getOfferInsts() {
		return offerInsts;
	}

	public void setOfferInsts(List offerInsts) {
		this.offerInsts = offerInsts;
	}



	public List getServAccNbrs() {
		return servAccNbrs;
	}

	public void setServAccNbrs(List servAccNbrs) {
		this.servAccNbrs = servAccNbrs;
	}

	

	public List getServAccts() {
		return servAccts;
	}

	public void setServAccts(List servAccts) {
		this.servAccts = servAccts;
	}

	
	public List getServBillPosts() {
		return servBillPosts;
	}

	public void setServBillPosts(List servBillPosts) {
		this.servBillPosts = servBillPosts;
	}

	

	public List getServProducts() {
		return servProducts;
	}

	public void setServProducts(List servProducts) {
		this.servProducts = servProducts;
	}

	public List getServs() {
		return servs;
	}

	public void setServs(List servs) {
		this.servs = servs;
	}

	

	public List getServStates() {
		return servStates;
	}

	public void setServStates(List servStates) {
		this.servStates = servStates;
	}

	public List getOfferInstAttrs() {
		return offerInstAttrs;
	}

	public void setOfferInstAttrs(List offerInstAttrs) {
		this.offerInstAttrs = offerInstAttrs;
	}

	public List getServAttrs() {
		return servAttrs;
	}

	public void setServAttrs(List servAttrs) {
		this.servAttrs = servAttrs;
	}

	public List getServProductAttrs() {
		return servProductAttrs;
	}

	public void setServProductAttrs(List servProductAttrs) {
		this.servProductAttrs = servProductAttrs;
	}

	public HashMap getServIds() {
		return servIds;
	}

	public void setServIds(HashMap servIds) {
		this.servIds = servIds;
	}


}
