package com.ztesoft.crm.business.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.buffalo.request.RequestContext;

import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class ServData implements Serializable {

	
	//��Ʒ��Ϣ���ݡ�
	public List servs = new ArrayList();
	
	//��¼��Ʒ�����Ϣ��
	public List servNormal = new ArrayList();
	
	//��Ʒ������Ϣ����
	public List servAttrs = new ArrayList();
	
	//��Ʒ����������Ϣ
	public List servCommon = new ArrayList();
	
	//��ѡ������Ʒ
	public List servProducts = new ArrayList();
	
	//��ѡ������Ʒ����
	public List servProductAttrs = new ArrayList();
	
	//�����ƹ�ϵ
	public List servAccts = new ArrayList();
	
	//���ŵȹ�����Ϣ
	public GlobalVariableHelper helper = new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
	
	//��������Ϣ
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
