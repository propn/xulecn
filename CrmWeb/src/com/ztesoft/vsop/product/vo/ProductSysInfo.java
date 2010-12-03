package com.ztesoft.vsop.product.vo;

import java.util.List;
import java.util.Map;

public class ProductSysInfo {
	
	public ProductSysInfo(){
		
	}
	
	public ProductSysInfo(Map product , List platForm ,List servAbility ,List rel  ,List accNbr){
		this.product = product ;
		this.platForm = platForm ;
		this.servAbility = servAbility ;
		this.rel = rel ;
		this.accNbr = accNbr ;
	}
	

	private List accNbr = null ;
	private List rel = null ;
	private List platForm  = null ;
	private List servAbility = null ;
	private Map product = null ;
	private List systems = null;
	public List getAccNbr() {
		return accNbr;
	}
	public void setAccNbr(List accNbr) {
		this.accNbr = accNbr;
	}
	public List getPlatForm() {
		return platForm;
	}
	public void setPlatForm(List platForm) {
		this.platForm = platForm;
	}
	public Map getProduct() {
		return product;
	}
	public void setProduct(Map product) {
		this.product = product;
	}
	public List getRel() {
		return rel;
	}
	public void setRel(List rel) {
		this.rel = rel;
	}
	public List getServAbility() {
		return servAbility;
	}
	public void setServAbility(List servAbility) {
		this.servAbility = servAbility;
	}


	public List getSystems() {
		return systems;
	}

	public void setSystems(List systems) {
		this.systems = systems;
	}
	

}
