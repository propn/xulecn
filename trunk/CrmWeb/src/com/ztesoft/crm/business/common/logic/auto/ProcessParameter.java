package com.ztesoft.crm.business.common.logic.auto;

import java.util.List;
import java.util.Map;

import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.Serv;

public class ProcessParameter {
	//销售品实例列表
	 List compInsts;
	
	public CompInst compInst;
	
	public Serv serv;
	
	String priceId;
	
	//产品实例对应的销售品明细实例ID列表
	List offerDetailIds;
	//产品实例列表
	List servs;
	
	Map common;

	public List getCompInsts() {
		return compInsts;
	}

	public void setCompInsts(List compInsts) {
		this.compInsts = compInsts;
	}

	public CompInst getCompInst() {
		return compInst;
	}

	public void setCompInst(CompInst compInst) {
		this.compInst = compInst;
	}

	public Serv getServ() {
		return serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public String getPriceId() {
		return priceId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

	public List getOfferDetailIds() {
		return offerDetailIds;
	}

	public void setOfferDetailIds(List offerDetailIds) {
		this.offerDetailIds = offerDetailIds;
	}

	public List getServs() {
		return servs;
	}

	public void setServs(List servs) {
		this.servs = servs;
	}

	public Map getCommon() {
		return common;
	}

	public void setCommon(Map common) {
		this.common = common;
	}
}
