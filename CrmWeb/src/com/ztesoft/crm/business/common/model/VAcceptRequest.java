package com.ztesoft.crm.business.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VAcceptRequest extends VOrderRequest {
	/** *****************产品受理信息*********************** */
	// 产品实例数据
	List servs = new ArrayList();

	// 附属产品属性数据
	List servAttrs = new ArrayList();
	
	// 附属产品数据
	List servProducts = new ArrayList();
	
	// 附属产品属性数据
	List servProductAttrs = new ArrayList();

	// 帐务定制数据
	List servAccts = new ArrayList();

	// 产品实例状态
	List servStates = new ArrayList();

	// 产品实例状态
	List servAccNbrs = new ArrayList();

	// 账单投递信息
	List servBillPosts = new ArrayList();

	/** ****************销售品受理信息************************ */
	// 销售品实例数据
	List offerInsts = new ArrayList();
	
	// 销售品属性实例数据
	List offerInstAttrs = new ArrayList();

	// 销售品实例构成数据
	List offerInstDetails = new ArrayList();

	// 资源实体产品
	List entProducts = new ArrayList();

	// 销售品参与人信息
	List offerInstParties = new ArrayList();

	// 销售品担保人信息
	List offerInstAssures = new ArrayList();
	
	// 销售品明细对应产品实例ID
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
