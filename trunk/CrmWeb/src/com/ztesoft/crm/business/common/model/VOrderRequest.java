package com.ztesoft.crm.business.common.model;

public class VOrderRequest implements java.io.Serializable{
	
	//�ͻ���ʶ
	String cust_id = "";
	//�ͻ�����
	String cust_ord_id = "";
	//Ա����
	String staff_no = "";
	//Ӫҵ����
	String site_no = "";
	//ҵ������,������ʲô����
	String busi_type = "";
	//������
	String lan_id = "";
	//ͬ�ʶ�����
	String ask_id = "";
	
	String business_id = "";
	
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
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
	public String getAsk_id() {
		return ask_id;
	}
	public void setAsk_id(String ask_id) {
		this.ask_id = ask_id;
	}	
}
