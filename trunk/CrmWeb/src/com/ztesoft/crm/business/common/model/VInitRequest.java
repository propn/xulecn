package com.ztesoft.crm.business.common.model;


public class VInitRequest extends VOrderRequest {
	//销售品实例标识 必填
	private String comp_inst_id = null;
	
	//服务提供标识 必填
	private String service_offer_id = null;

	public String getComp_inst_id() {
		return comp_inst_id;
	}

	public void setComp_inst_id(String comp_inst_id) {
		this.comp_inst_id = comp_inst_id;
	}

	public String getService_offer_id() {
		return service_offer_id;
	}

	public void setService_offer_id(String service_offer_id) {
		this.service_offer_id = service_offer_id;
	}
	
	
}
