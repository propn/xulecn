/**
 * 
 */
package com.ztesoft.crm.business.common.model;

import java.util.HashMap;

/**
 * @author sunny
 *
 */
//创建Serv必须有的属性包括: service_type, action_type, serv_id, lan_id
public class VServ {
	
	private HashMap serv_attrs = null;
	
	//附属产品实例加载标志 F – 未加载 T -  加载	
	private String serv_product_loaded_flag = null;
	
	//帐务定制信息加载标志
	private String serv_acct_loaded_flag = null;
	
	//账单投递信息加载标志
	private String serv_bill_post_loaded_flag = null;
	
	//附加号码加载标志
	private String serv_acc_nbr_loaded_falg = null;
	
	//主产品实例信息加载标志
	private String serv_state_loaded_flag = null;

	public String getServ_acc_nbr_loaded_falg() {
		return serv_acc_nbr_loaded_falg;
	}

	public void setServ_acc_nbr_loaded_falg(String serv_acc_nbr_loaded_falg) {
		this.serv_acc_nbr_loaded_falg = serv_acc_nbr_loaded_falg;
	}

	public String getServ_acct_loaded_flag() {
		return serv_acct_loaded_flag;
	}

	public void setServ_acct_loaded_flag(String serv_acct_loaded_flag) {
		this.serv_acct_loaded_flag = serv_acct_loaded_flag;
	}

	public HashMap getServ_attrs() {
		return serv_attrs;
	}

	public void setServ_attrs(HashMap serv_attrs) {
		this.serv_attrs = serv_attrs;
	}

	public String getServ_bill_post_loaded_flag() {
		return serv_bill_post_loaded_flag;
	}

	public void setServ_bill_post_loaded_flag(String serv_bill_post_loaded_flag) {
		this.serv_bill_post_loaded_flag = serv_bill_post_loaded_flag;
	}

	public String getServ_product_loaded_flag() {
		return serv_product_loaded_flag;
	}

	public void setServ_product_loaded_flag(String serv_product_loaded_flag) {
		this.serv_product_loaded_flag = serv_product_loaded_flag;
	}

	public String getServ_state_loaded_flag() {
		return serv_state_loaded_flag;
	}

	public void setServ_state_loaded_flag(String serv_state_loaded_flag) {
		this.serv_state_loaded_flag = serv_state_loaded_flag;
	}
	

}
