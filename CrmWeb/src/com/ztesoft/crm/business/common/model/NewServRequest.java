package com.ztesoft.crm.business.common.model;
/**
 * 主产品实例
 * @author sunny
 *
 */
public class NewServRequest extends OrderRequest {
	// 主产品实例标识
	private String serv_id;

	// 产品标识
	private String product_id;

	// 父销售品实例标识
	private String comp_inst_id;

	public String getComp_inst_id() {
		return comp_inst_id;
	}

	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getServ_id() {
		return serv_id;
	}

	public void setServ_id(String serv_id) {
		this.serv_id = serv_id;
	}

}
