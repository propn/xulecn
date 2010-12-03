package com.ztesoft.crm.business.common.model;

import java.util.HashMap;
import java.util.Map;


public class InitRequest extends VOrderRequest {
	//销售品实例标识 
	private String comp_inst_id = null;
	
	//服务提供标识 必填
	private String service_offer_id = null;
	
	//主产品实例标识
	private String serv_id = null;
	
	//新销售品标识
	private String new_product_offer_id = null;
	
	//新产品标识
	private String new_product_id = null;
	
	//新资费标识
	private String new_price_id = null;

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

	public String getService_offer_id() {
		return service_offer_id;
	}

	public void setService_offer_id(String service_offer_id) {
		this.service_offer_id = service_offer_id;
	}
	
	//将map中的信息加载到成员变量中
	public void loadFromMap(Map map) {
		comp_inst_id = (String)map.get("comp_inst_id");
		service_offer_id = (String)map.get("service_offer_id");
		serv_id = (String)map.get("serv_id");
		new_product_offer_id = (String)map.get("new_product_offer_id");
		new_product_id = (String)map.get("new_product_id");
		new_price_id = (String)map.get("new_price_id");		
		cust_id = (String)map.get("cust_id");
		cust_ord_id = (String)map.get("cust_ord_id");
		staff_no = (String)map.get("staff_no");
		site_no = (String)map.get("site_no");
	}
	
	//将map中的信息加载到成员变量中
	public HashMap unloadtoMap() {
		HashMap map = new HashMap();
		
		if (null != comp_inst_id) {
			map.put("comp_inst_id", comp_inst_id);
		}
		
		if (null != service_offer_id) {
			map.put("service_offer_id", service_offer_id);
		}
		if (null != serv_id) {
			map.put("serv_id", serv_id);
		}
		if (null != new_product_offer_id) {
			map.put("new_product_offer_id", new_product_offer_id);
		}
		if (null != new_product_id) {
			map.put("new_product_id", new_product_id);
		}
		if (null != new_price_id) {
			map.put("new_price_id", new_price_id);
		}
		if (null != cust_id) {
			map.put("cust_id", cust_id);
		}
		if (null != cust_ord_id) {
			map.put("cust_ord_id", cust_ord_id);
		}
		if (null != staff_no) {
			map.put("staff_no", staff_no);
		}
		if (null != site_no) {
			map.put("site_no", site_no);
		}
		
		return map;		
	}
}
