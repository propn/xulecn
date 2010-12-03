package com.ztesoft.crm.business.common.logic.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.sem.engine.BusiEngine;

public class CommonData {
	private List servs = new ArrayList(); // ��ص�����Ʒʵ����Ϣ�б�

	private List compInsts = new ArrayList(); // ��ص�����Ʒʵ����Ϣ�б�

	private List entProducts = new ArrayList(); // ��ص���Դ��Ʒʵ����Ϣ�б�
	
	private HashMap servIds = new HashMap(); // 

    //��������Ϣ���� cust_ord_id, cust_id,staff_no, site_no,service_offer_id, ord_id
	private HashMap attributes = new HashMap(); 

	public void set(String name, String value) {
		this.attributes.put(name, value);
	}

	public String get(String name) {
		return (String) this.attributes.get(name);
	}

	public HashMap getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap attributes) {
		this.attributes = attributes;
	}

	public List getCompInsts() {
		return compInsts;
	}

	public void setCompInsts(List compInsts) {
		this.compInsts = compInsts;
	}

	public List getEntProducts() {
		return entProducts;
	}

	public void setEntProducts(List entProducts) {
		this.entProducts = entProducts;
	}

	public List getServs() {
		return servs;
	}

	public void setServs(List servs) {
		this.servs = servs;
	}

	public static CommonData getData(){
		return (CommonData) BusiEngine.get(Actions.COMMON_DATA);
	}

	public HashMap getServIds() {
		return servIds;
	}

	public void setServIds(HashMap servIds) {
		this.servIds = servIds;
	}
	
}
