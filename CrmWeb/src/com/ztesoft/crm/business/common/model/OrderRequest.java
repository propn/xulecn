package com.ztesoft.crm.business.common.model;

public class OrderRequest implements java.io.Serializable{
	
	//客户标识
	String cust_id = "";
	//客户订单
	String cust_ord_id = "";
	//员工号
	String staff_no = "";
	//营业网点
	String site_no = "";	
	//业务类型,决定调什么方法
	String busi_type = "";
	//本地网
	String lan_id = "";
	//营业区
	String business_id = "";
	//同笔订单号
	String ask_id = "";
	
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_ord_id() {
		return cust_ord_id;
	}
	public void setCust_ord_id(String cust_ord_id) {
		this.cust_ord_id = cust_ord_id;
	}
	public String getSite_no() {
		return site_no;
	}
	public void setSite_no(String site_no) {
		this.site_no = site_no;
	}
	public String getStaff_no() {
		return staff_no;
	}
	public void setStaff_no(String staff_no) {
		this.staff_no = staff_no;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	//从前台传入数据取基本信息
	public void getBaseInfofromVOrderRequest(VOrderRequest vOrdReq) {
		this.cust_id = vOrdReq.getCust_id();
		this.cust_ord_id = vOrdReq.getCust_ord_id();
		this.site_no = vOrdReq.getSite_no();
		this.staff_no = vOrdReq.getStaff_no();
		this.busi_type = vOrdReq.getBusi_type();
		this.lan_id = vOrdReq.getLan_id();
		this.business_id = vOrdReq.getBusiness_id();
		this.ask_id = vOrdReq.getAsk_id();
	}
	public String getAsk_id() {
		return ask_id;
	}
	public void setAsk_id(String ask_id) {
		this.ask_id = ask_id;
	}
	
}
