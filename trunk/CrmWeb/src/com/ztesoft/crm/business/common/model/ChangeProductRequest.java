package com.ztesoft.crm.business.common.model;

public class ChangeProductRequest extends OrderRequest {
	private String serv_id;
	private String comp_inst_id;
	private String new_product_id;
	private String new_product_offer_id;
	private String new_price_id;
	public String getComp_inst_id() {
		return comp_inst_id;
	}
	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}
	public String getNew_price_id() {
		return new_price_id;
	}
	public void setNew_price_id(String new_price_id) {
		this.new_price_id = new_price_id;
	}
	public String getNew_product_id() {
		return new_product_id;
	}
	public void setNew_product_id(String new_product_id) {
		this.new_product_id = new_product_id;
	}
	public String getNew_product_offer_id() {
		return new_product_offer_id;
	}
	public void setNew_product_offer_id(String new_product_offer_id) {
		this.new_product_offer_id = new_product_offer_id;
	}
	public String getServ_id() {
		return serv_id;
	}
	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}
	

}
