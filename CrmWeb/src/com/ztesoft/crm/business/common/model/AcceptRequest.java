package com.ztesoft.crm.business.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptRequest extends OrderRequest {
	/** *****************��Ʒ������Ϣ*********************** */
	//��Ʒʵ������
	List servs = new ArrayList();
	/** *****************����Ʒʵ��ID*********************** */
	HashMap servIds = new HashMap();
	
	/** ****************����Ʒ������Ϣ************************ */
	//����Ʒʵ������
	List compInsts = new ArrayList();
    
    /** ****************��Դ��Ʒʵ����Ϣ************************ */
    //��Դ��Ʒʵ����Ϣ
	List entProdcust = new ArrayList();
	
	public List getCompInsts() {
		return compInsts;
	}
	public void setCompInsts(List compInsts) {
		this.compInsts = compInsts;
	}
	public List getEntProdcust() {
		return entProdcust;
	}
	public void setEntProdcust(List entProdcust) {
		this.entProdcust = entProdcust;
	}
	public List getServs() {
		return servs;
	}
	public void setServs(List servs) {
		this.servs = servs;
	}
	public HashMap getServIds() {
		return servIds;
	}
	public void setServIds(HashMap servIds) {
		this.servIds = servIds;
	}
}
