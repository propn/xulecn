package com.ztesoft.crm.business.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AcceptRequest extends OrderRequest {
	/** *****************产品受理信息*********************** */
	//产品实例数据
	List servs = new ArrayList();
	/** *****************主产品实例ID*********************** */
	HashMap servIds = new HashMap();
	
	/** ****************销售品受理信息************************ */
	//销售品实例数据
	List compInsts = new ArrayList();
    
    /** ****************资源产品实例信息************************ */
    //资源产品实例信息
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
