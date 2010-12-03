package com.ztesoft.crm.business.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferData implements java.io.Serializable {
	
	
	public String cust_ord_id=  "";//客户订单号
	
	public String cust_id = "";//客户ID
	
	public String ask_id = "";//同笔订单号

	public String offerType = "";//0为基础销售品业务 1为组合销售品业务
	
	public String action_type = "";//A为装 M为二次业务
	//客户信息
	public List custInfo = new ArrayList();
	//销售品本身实例数据
	public List offers = new ArrayList();

	//包外优惠实例数据
	public List offerSales = new ArrayList();

	//销售品构成明细数据
	public List offersDetail = new ArrayList();

	//销售品属性数据
	public List offersAttr = new ArrayList();
	
	public HashMap loginInfo = new HashMap();//登陆信息
	
	public String prefix = "offerData_prefix_20100129_";

	public List getOffers() {
		return offers;
	}

	public void setOffers(List offers) {
		this.offers = offers;
	}

	public List getOfferSales() {
		return offerSales;
	}

	public void setOfferSales(List offerSales) {
		this.offerSales = offerSales;
	}

	public List getOffersDetail() {
		return offersDetail;
	}

	public void setOffersDetail(List offersDetail) {
		this.offersDetail = offersDetail;
	}

	public List getOffersAttr() {
		return offersAttr;
	}

	public void setOffersAttr(List offersAttr) {
		this.offersAttr = offersAttr;
	}



	public List getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(List custInfo) {
		this.custInfo = custInfo;
	}

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getCust_ord_id() {
		return cust_ord_id;
	}

	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}

	public HashMap getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(HashMap loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getAsk_id() {
		return ask_id;
	}

	public void setAsk_id(String ask_id) {
		this.ask_id = ask_id;
	}
}
