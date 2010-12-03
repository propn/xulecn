package com.ztesoft.crm.business.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.buffalo.request.RequestContext;

import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class ServData implements Serializable {

	
	//产品信息数据。
	public List servs = new ArrayList();
	
	//记录产品横表信息。
	public List servNormal = new ArrayList();
	
	//产品属性信息数据
	public List servAttrs = new ArrayList();
	
	//产品公共属性信息
	public List servCommon = new ArrayList();
	
	//已选附属产品
	public List servProducts = new ArrayList();
	
	//已选附属产品属性
	public List servProductAttrs = new ArrayList();
	
	//帐务定制关系
	public List servAccts = new ArrayList();
	
	//工号等公共信息
	public GlobalVariableHelper helper = new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
	
	//公共的信息
	public List commonData = new ArrayList();


	public GlobalVariableHelper getHelper() {
		return helper;
	}

	public void setHelper(GlobalVariableHelper helper) {
		this.helper = helper;
	}

	public List getServAttrs() {
		return servAttrs;
	}

	public void setServAttrs(List servAttrs) {
		this.servAttrs = servAttrs;
	}

	public List getServCommon() {
		return servCommon;
	}

	public void setServCommon(List servCommon) {
		this.servCommon = servCommon;
	}

	public List getServProductAttrs() {
		return servProductAttrs;
	}

	public void setServProductAttrs(List servProductAttrs) {
		this.servProductAttrs = servProductAttrs;
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

	public List getCommonData() {
		return commonData;
	}

	public void setCommonData(List commonData) {
		this.commonData = commonData;
	}

	public List getServNormal() {
		return servNormal;
	}

	public void setServNormal(List servNormal) {
		this.servNormal = servNormal;
	}

	public List getServAccts() {
		return servAccts;
	}

	public void setServAccts(List servAccts) {
		this.servAccts = servAccts;
	}


}
