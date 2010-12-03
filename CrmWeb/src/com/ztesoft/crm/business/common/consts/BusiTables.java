package com.ztesoft.crm.business.common.consts;

public class BusiTables {
	
	private BusiTables(){
		
	}
		
	public static class BILL_POST {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String bill_type = "bill_type" ;
			
		public static final String owner_type = "owner_type" ;
			
		public static final String post_method = "post_method" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String incept_person = "incept_person" ;
			
		public static final String post_address = "post_address" ;
			
		public static final String email = "email" ;
			
		public static final String contact_name = "contact_name" ;
			
		public static final String contact_phone = "contact_phone" ;
			
		public static final String guard_post = "guard_post" ;
			
		public static final String certificate_type = "certificate_type" ;
			
		public static final String certificate_no = "certificate_no" ;
			
		public static final String query_password = "query_password" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","bill_type","owner_type","post_method","post_code","incept_person","post_address","email","contact_name","contact_phone","guard_post","certificate_type","certificate_no","query_password"};
	}
		
	public static class ENT_PRODUCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String ent_product_id = "ent_product_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String mkt_res_id = "mkt_res_id" ;
			
		public static final String mkt_res_inst_id = "mkt_res_inst_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","state_date","action_type","end_time","beg_time","staff_no","site_no","ent_product_id","product_id","mkt_res_id","mkt_res_inst_id"};
	}
		
	public static class ENT_PRODUCT_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String ent_product_id = "ent_product_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","ent_product_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class OFFER_INST {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String product_offer_id = "product_offer_id" ;
			
		public static final String offer_kind = "offer_kind" ;
			
		public static final String create_date = "create_date" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String state = "state" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","comp_inst_id","product_offer_instance_id","cust_id","product_offer_id","offer_kind","create_date","eff_date","exp_date","state"};
	}
		
	public static class OFFER_INST_ASSURE {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String guarantor_id = "guarantor_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String assure_method = "assure_method" ;
			
		public static final String assure_info = "assure_info" ;
			
		public static final String assure_name = "assure_name" ;
			
		public static final String card_type = "card_type" ;
			
		public static final String card_no = "card_no" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String notes = "notes" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","cust_ord_id","ord_item_id","state_date","action_type","end_time","beg_time","guarantor_id","comp_inst_id","assure_method","assure_info","assure_name","card_type","card_no","tel","mobile_phone","fax","bp","email","addr","post_code","notes"};
	}
		
	public static class OFFER_INST_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String product_offer_id = "product_offer_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","product_offer_instance_id","product_offer_id","attr_id","field_name","attr_val","comp_inst_id"};
	}
		
	public static class OFFER_INST_DETAIL {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String flow_id = "flow_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String offer_detail_id = "offer_detail_id" ;
			
		public static final String comp_role_id = "comp_role_id" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","flow_id","action_type","state_date","end_time","beg_time","staff_no","site_no","comp_inst_id","product_offer_instance_id","offer_detail_id","comp_role_id","instance_type","instance_id","lan_id","eff_date","exp_date"};
	}
		
	public static class OFFER_INST_PARTY {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String rel_type = "rel_type" ;
			
		public static final String rel_id = "rel_id" ;
			
		public static final String party_org_id = "party_org_id" ;
			
		public static final String party_id = "party_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","comp_inst_id","rel_type","rel_id","party_org_id","party_id","eff_date","exp_date"};
	}
		
	public static class ORD_ASK {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ask_id = "ask_id" ;
			
		public static final String service_offer_id = "service_offer_id" ;
			
		public static final String ord_type = "ord_type" ;
			
		public static final String ord_prop = "ord_prop" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_type_id = "instance_type_id" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String solution_id = "solution_id" ;
			
		public static final String notes = "notes" ;
			
		public static final String state = "state" ;
			
		public static final String state_reason_id = "state_reason_id" ;
			
		public static final String ask_date = "ask_date" ;
			
		public static final String confirm_date = "confirm_date" ;
			
		public static final String fin_date = "fin_date" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String agreement_id = "agreement_id" ;
			
		public static final String last_ord_id = "last_ord_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","cust_ord_id","ask_id","service_offer_id","ord_type","ord_prop","instance_type","instance_type_id","instance_id","solution_id","notes","state","state_reason_id","ask_date","confirm_date","fin_date","beg_time","staff_no","site_no","agreement_id","last_ord_id"};
	}
		
	public static class ORD_ASK_LIST {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String same_ask_no = "same_ask_no" ;
			
		public static final String service_offer_id = "service_offer_id" ;
			
		public static final String ord_type = "ord_type" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_type_id = "instance_type_id" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String solution_id = "solution_id" ;
			
		public static final String notes = "notes" ;
			
		public static final String state = "state" ;
			
		public static final String state_reason_id = "state_reason_id" ;
			
		public static final String ask_date = "ask_date" ;
			
		public static final String confirm_date = "confirm_date" ;
			
		public static final String fin_date = "fin_date" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String agreement_id = "agreement_id" ;
			
		public static final String last_ord_id = "last_ord_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","cust_ord_id","same_ask_no","service_offer_id","ord_type","instance_type","instance_type_id","instance_id","solution_id","notes","state","state_reason_id","ask_date","confirm_date","fin_date","beg_time","staff_no","site_no","agreement_id","last_ord_id"};
	}
		
	public static class ORD_ASK_LOG {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String field_value = "field_value" ;
			
		public static final String old_field_value = "old_field_value" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","field_name","field_value","old_field_value"};
	}
		
	public static class ORD_ASK_RELATION {
			
		public static final String a_ord_id = "a_ord_id" ;
			
		public static final String z_ord_no = "z_ord_no" ;
			
		public static final String rela_type = "rela_type" ;
			
		public static final String state = "state" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"a_ord_id","z_ord_no","rela_type","state"};
	}
		
	public static class ORD_BILL_POST {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String bill_type = "bill_type" ;
			
		public static final String owner_type = "owner_type" ;
			
		public static final String post_method = "post_method" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String incept_person = "incept_person" ;
			
		public static final String post_address = "post_address" ;
			
		public static final String email = "email" ;
			
		public static final String contact_name = "contact_name" ;
			
		public static final String contact_phone = "contact_phone" ;
			
		public static final String guard_post = "guard_post" ;
			
		public static final String certificate_type = "certificate_type" ;
			
		public static final String certificate_no = "certificate_no" ;
			
		public static final String query_password = "query_password" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","bill_type","owner_type","post_method","post_code","incept_person","post_address","email","contact_name","contact_phone","guard_post","certificate_type","certificate_no","query_password"};
	}
		
	public static class ORD_BOOK_DATE {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String book_seq = "book_seq" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String book_type = "book_type" ;
			
		public static final String time_name_id = "time_name_id" ;
			
		public static final String book_date = "book_date" ;
			
		public static final String book_date2 = "book_date2" ;
			
		public static final String book_dow = "book_dow" ;
			
		public static final String book_dow2 = "book_dow2" ;
			
		public static final String book_time = "book_time" ;
			
		public static final String book_time2 = "book_time2" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","book_seq","comp_inst_id","book_type","time_name_id","book_date","book_date2","book_dow","book_dow2","book_time","book_time2"};
	}
		
	public static class ORD_CONTACT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_contact_id = "serv_contact_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String contact_name = "contact_name" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_contact_id","product_offer_instance_id","contact_name","tel","mobile_phone","fax","bp","email","addr","post_code"};
	}
		
	public static class ORD_CUSTOMER_ORDER {
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String cust_so_number = "cust_so_number" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String state = "state" ;
			
		public static final String status_date = "status_date" ;
			
		public static final String pre_handle_flag = "pre_handle_flag" ;
			
		public static final String priority = "priority" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String ask_source = "ask_source" ;
			
		public static final String ask_source_srl = "ask_source_srl" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"cust_ord_id","cust_so_number","cust_id","state","status_date","pre_handle_flag","priority","business_id","lan_id","staff_no","site_no","ask_source","ask_source_srl"};
	}
		
	public static class ORD_ENT_PRODUCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String ent_product_id = "ent_product_id" ;
			
		public static final String product_qunantity = "product_qunantity" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String mkt_res_id = "mkt_res_id" ;
			
		public static final String mkt_res_inst_id = "mkt_res_inst_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","ent_product_id","product_qunantity","product_id","instance_type","instance_id","mkt_res_id","mkt_res_inst_id"};
	}
		
	public static class ORD_ENT_PRODUCT_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String ent_product_id = "ent_product_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","ent_product_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class ORD_HANDLER {
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String handler_name = "handler_name" ;
			
		public static final String handler_card_type = "handler_card_type" ;
			
		public static final String handler_card_no = "handler_card_no" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String notes = "notes" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"cust_ord_id","handler_name","handler_card_type","handler_card_no","tel","mobile_phone","fax","bp","email","addr","post_code","notes"};
	}
		
	public static class ORD_NOT_PAY_LIST {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String ord_type = "ord_type" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String bureau_no = "bureau_no" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String price_object_type = "price_object_type" ;
			
		public static final String price_object_id = "price_object_id" ;
			
		public static final String object_id = "object_id" ;
			
		public static final String service_offer_id = "service_offer_id" ;
			
		public static final String acct_id = "acct_id" ;
			
		public static final String acct_item_type_id = "acct_item_type_id" ;
			
		public static final String need_cash = "need_cash" ;
			
		public static final String type_type = "type_type" ;
			
		public static final String type_adsc = "type_adsc" ;
			
		public static final String price_id = "price_id" ;
			
		public static final String base_instance_type = "base_instance_type" ;
			
		public static final String base_instance_id = "base_instance_id" ;
			
		public static final String time_limit_type = "time_limit_type" ;
			
		public static final String time_limit_vale = "time_limit_vale" ;
			
		public static final String check_date = "check_date" ;
			
		public static final String charge_mode = "charge_mode" ;
			
		public static final String fact_cash = "fact_cash" ;
			
		public static final String pay_type = "pay_type" ;
			
		public static final String pay_date = "pay_date" ;
			
		public static final String send_date = "send_date" ;
			
		public static final String notes = "notes" ;
			
		public static final String fee_serialno = "fee_serialno" ;
			
		public static final String plan_serial = "plan_serial" ;
			
		public static final String state = "state" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","ord_type","cust_id","bureau_no","instance_type","instance_id","price_object_type","price_object_id","object_id","service_offer_id","acct_id","acct_item_type_id","need_cash","type_type","type_adsc","price_id","base_instance_type","base_instance_id","time_limit_type","time_limit_vale","check_date","charge_mode","fact_cash","pay_type","pay_date","send_date","notes","fee_serialno","plan_serial","state","staff_no","site_no"};
	}
		
	public static class ORD_OFFER_INST {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String flow_id = "flow_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String product_offer_id = "product_offer_id" ;
			
		public static final String offer_kind = "offer_kind" ;
			
		public static final String price_id = "price_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String create_date = "create_date" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","flow_id","action_type","state_date","end_time","beg_time","staff_no","site_no","comp_inst_id","product_offer_instance_id","cust_id","product_offer_id","offer_kind","price_id","lan_id","business_id","create_date","eff_date","exp_date"};
	}
		
	public static class ORD_OFFER_INST_ASSURE {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String assure_id = "assure_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String assure_method = "assure_method" ;
			
		public static final String assure_info = "assure_info" ;
			
		public static final String assure_name = "assure_name" ;
			
		public static final String card_type = "card_type" ;
			
		public static final String card_no = "card_no" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String notes = "notes" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","cust_ord_id","ord_item_id","action_type","state_date","end_time","beg_time","assure_id","comp_inst_id","assure_method","assure_info","assure_name","card_type","card_no","tel","mobile_phone","fax","bp","email","addr","post_code","notes"};
	}
		
	public static class ORD_OFFER_INST_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String product_offer_id = "product_offer_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","product_offer_instance_id","product_offer_id","attr_id","field_name","attr_val","comp_inst_id"};
	}
		
	public static class ORD_OFFER_INST_DETAIL {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String flow_id = "flow_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String offer_detail_id = "offer_detail_id" ;
			
		public static final String comp_role_id = "comp_role_id" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","flow_id","action_type","state_date","end_time","beg_time","staff_no","site_no","comp_inst_id","product_offer_instance_id","offer_detail_id","comp_role_id","instance_type","instance_id","lan_id","eff_date","exp_date"};
	}
		
	public static class ORD_OFFER_INST_PARTY {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String rel_type = "rel_type" ;
			
		public static final String rel_id = "rel_id" ;
			
		public static final String party_org_id = "party_org_id" ;
			
		public static final String party_id = "party_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","rel_type","rel_id","party_org_id","party_id","eff_date","exp_date"};
	}
		
	public static class ORD_REMOVE_CAUSE {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String remove_cause_id = "remove_cause_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String remove_date = "remove_date" ;
			
		public static final String out_cause = "out_cause" ;
			
		public static final String out_to = "out_to" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","remove_cause_id","comp_inst_id","remove_date","out_cause","out_to"};
	}
		
	public static class ORD_SERV {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String product_family_id = "product_family_id" ;
			
		public static final String user_cust_id = "user_cust_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String area_code = "area_code" ;
			
		public static final String acc_nbr = "acc_nbr" ;
			
		public static final String physical_nbr = "physical_nbr" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String billing_mode_id = "billing_mode_id" ;
			
		public static final String rent_date = "rent_date" ;
			
		public static final String user_password = "user_password" ;
			
		public static final String state = "state" ;
			
		public static final String user_prop = "user_prop" ;
			
		public static final String notes = "notes" ;
			
		public static final String user_name = "user_name" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_id","comp_inst_id","cust_id","product_family_id","user_cust_id","product_id","area_code","acc_nbr","physical_nbr","lan_id","business_id","billing_mode_id","rent_date","user_password","state","user_prop","notes","user_name"};
	}
		
	public static class ORD_SERV_ACCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String acct_item_group_id = "acct_item_group_id" ;
			
		public static final String acct_id = "acct_id" ;
			
		public static final String invoice_require_id = "invoice_require_id" ;
			
		public static final String bill_require_id = "bill_require_id" ;
			
		public static final String payment_rule_id = "payment_rule_id" ;
			
		public static final String self_flag = "self_flag" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_id","acct_item_group_id","acct_id","invoice_require_id","bill_require_id","payment_rule_id","self_flag"};
	}
		
	public static class ORD_SERV_ACC_NBR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String acc_nbr_type_cd = "acc_nbr_type_cd" ;
			
		public static final String add_acc_nbr = "add_acc_nbr" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","site_no","staff_no","serv_id","acc_nbr_type_cd","add_acc_nbr"};
	}
		
	public static class ORD_SERV_ADDRESS {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String address_id = "address_id" ;
			
		public static final String detail_address = "detail_address" ;
			
		public static final String branch_id = "branch_id" ;
			
		public static final String seq_ = "seq_" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","address_id","detail_address","branch_id","seq_"};
	}
		
	public static class ORD_SERV_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class ORD_SERV_PRODUCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_product_id = "serv_product_id" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String rent_date = "rent_date" ;
			
		public static final String r_serv_id = "r_serv_id" ;
			
		public static final String state = "state" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_product_id","serv_id","product_id","rent_date","r_serv_id","state"};
	}
		
	public static class ORD_SERV_PRODUCT_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_product_id = "serv_product_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_product_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class ORD_SERV_STATE {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String stop_type = "stop_type" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","site_no","staff_no","serv_id","stop_type","eff_date"};
	}
		
	public static class PLAN_TABLE {
			
		public static final String statement_id = "statement_id" ;
			
		public static final String plan_id = "plan_id" ;
			
		public static final String timestamp = "timestamp" ;
			
		public static final String remarks = "remarks" ;
			
		public static final String operation = "operation" ;
			
		public static final String options = "options" ;
			
		public static final String object_node = "object_node" ;
			
		public static final String object_owner = "object_owner" ;
			
		public static final String object_name = "object_name" ;
			
		public static final String object_alias = "object_alias" ;
			
		public static final String object_instance = "object_instance" ;
			
		public static final String object_type = "object_type" ;
			
		public static final String optimizer = "optimizer" ;
			
		public static final String search_columns = "search_columns" ;
			
		public static final String id = "id" ;
			
		public static final String parent_id = "parent_id" ;
			
		public static final String depth = "depth" ;
			
		public static final String position = "position" ;
			
		public static final String cost = "cost" ;
			
		public static final String cardinality = "cardinality" ;
			
		public static final String bytes = "bytes" ;
			
		public static final String other_tag = "other_tag" ;
			
		public static final String partition_start = "partition_start" ;
			
		public static final String partition_stop = "partition_stop" ;
			
		public static final String partition_id = "partition_id" ;
			
		public static final String other = "other" ;
			
		public static final String distribution = "distribution" ;
			
		public static final String cpu_cost = "cpu_cost" ;
			
		public static final String io_cost = "io_cost" ;
			
		public static final String temp_space = "temp_space" ;
			
		public static final String access_predicates = "access_predicates" ;
			
		public static final String filter_predicates = "filter_predicates" ;
			
		public static final String projection = "projection" ;
			
		public static final String time = "time" ;
			
		public static final String qblock_name = "qblock_name" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"statement_id","plan_id","timestamp","remarks","operation","options","object_node","object_owner","object_name","object_alias","object_instance","object_type","optimizer","search_columns","id","parent_id","depth","position","cost","cardinality","bytes","other_tag","partition_start","partition_stop","partition_id","other","distribution","cpu_cost","io_cost","temp_space","access_predicates","filter_predicates","projection","time","qblock_name"};
	}
		
	public static class SERV {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String product_family_id = "product_family_id" ;
			
		public static final String user_cust_id = "user_cust_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String area_code = "area_code" ;
			
		public static final String acc_nbr = "acc_nbr" ;
			
		public static final String physical_nbr = "physical_nbr" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String billing_mode_id = "billing_mode_id" ;
			
		public static final String rent_date = "rent_date" ;
			
		public static final String user_password = "user_password" ;
			
		public static final String state = "state" ;
			
		public static final String user_prop = "user_prop" ;
			
		public static final String notes = "notes" ;
			
		public static final String user_name = "user_name" ;
			
		public static final String address_id = "address_id" ;
			
		public static final String detail_address = "detail_address" ;
			
		public static final String branch_id = "branch_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","site_no","staff_no","serv_id","comp_inst_id","cust_id","product_family_id","user_cust_id","product_id","area_code","acc_nbr","physical_nbr","lan_id","business_id","billing_mode_id","rent_date","user_password","state","user_prop","notes","user_name","address_id","detail_address","branch_id"};
	}
		
	public static class SERV_ACCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String acct_item_group_id = "acct_item_group_id" ;
			
		public static final String acct_id = "acct_id" ;
			
		public static final String invoice_require_id = "invoice_require_id" ;
			
		public static final String bill_require_id = "bill_require_id" ;
			
		public static final String payment_rule_id = "payment_rule_id" ;
			
		public static final String self_flag = "self_flag" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_id","acct_item_group_id","acct_id","invoice_require_id","bill_require_id","payment_rule_id","self_flag"};
	}
		
	public static class SERV_ACC_NBR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String acc_nbr_type_cd = "acc_nbr_type_cd" ;
			
		public static final String add_acc_nbr = "add_acc_nbr" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","site_no","staff_no","serv_id","acc_nbr_type_cd","add_acc_nbr"};
	}
		
	public static class SERV_ADDRESS {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String fin_date = "fin_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String address_id = "address_id" ;
			
		public static final String detail_address = "detail_address" ;
			
		public static final String branch_id = "branch_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","fin_date","end_time","beg_time","serv_id","address_id","detail_address","branch_id"};
	}
		
	public static class SERV_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class SERV_PARTY {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String seq = "seq" ;
			
		public static final String old_seq = "old_seq" ;
			
		public static final String fcomp_inst_id = "fcomp_inst_id" ;
			
		public static final String rel_type = "rel_type" ;
			
		public static final String rel_id = "rel_id" ;
			
		public static final String party_org_id = "party_org_id" ;
			
		public static final String party_id = "party_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","seq","old_seq","fcomp_inst_id","rel_type","rel_id","party_org_id","party_id","eff_date","exp_date"};
	}
		
	public static class SERV_PRODUCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_product_id = "serv_product_id" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String rent_date = "rent_date" ;
			
		public static final String r_serv_id = "r_serv_id" ;
			
		public static final String state = "state" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_product_id","serv_id","product_id","rent_date","r_serv_id","state"};
	}
		
	public static class SERV_PRODUCT_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_product_id = "serv_product_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_product_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class SERV_STATE {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String stop_type = "stop_type" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","site_no","staff_no","serv_id","stop_type","eff_date"};
	}
		
	public static class UH_ASK_LIST {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String same_ask_no = "same_ask_no" ;
			
		public static final String service_offer_id = "service_offer_id" ;
			
		public static final String ord_type = "ord_type" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_type_id = "instance_type_id" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String solution_id = "solution_id" ;
			
		public static final String notes = "notes" ;
			
		public static final String state = "state" ;
			
		public static final String state_reason_id = "state_reason_id" ;
			
		public static final String ask_date = "ask_date" ;
			
		public static final String confirm_date = "confirm_date" ;
			
		public static final String fin_date = "fin_date" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String agreement_id = "agreement_id" ;
			
		public static final String last_ord_id = "last_ord_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","cust_ord_id","same_ask_no","service_offer_id","ord_type","instance_type","instance_type_id","instance_id","solution_id","notes","state","state_reason_id","ask_date","confirm_date","fin_date","beg_time","staff_no","site_no","agreement_id","last_ord_id"};
	}
		
	public static class UH_ASK_LOG {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String field_value = "field_value" ;
			
		public static final String old_field_value = "old_field_value" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","field_name","field_value","old_field_value"};
	}
		
	public static class UH_ASK_RELATION {
			
		public static final String a_ord_id = "a_ord_id" ;
			
		public static final String z_ord_no = "z_ord_no" ;
			
		public static final String rela_type = "rela_type" ;
			
		public static final String state = "state" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"a_ord_id","z_ord_no","rela_type","state"};
	}
		
	public static class UH_BOOK_DATE {
			
		public static final String book_seq = "book_seq" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String book_type = "book_type" ;
			
		public static final String time_name_id = "time_name_id" ;
			
		public static final String book_date = "book_date" ;
			
		public static final String book_date2 = "book_date2" ;
			
		public static final String book_dow = "book_dow" ;
			
		public static final String book_dow2 = "book_dow2" ;
			
		public static final String book_time = "book_time" ;
			
		public static final String book_time2 = "book_time2" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"book_seq","cust_ord_id","product_offer_instance_id","book_type","time_name_id","book_date","book_date2","book_dow","book_dow2","book_time","book_time2"};
	}
		
	public static class UH_CONTACT {
			
		public static final String serv_contact_id = "serv_contact_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String contact_name = "contact_name" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"serv_contact_id","cust_ord_id","product_offer_instance_id","contact_name","tel","mobile_phone","fax","bp","email","addr","post_code"};
	}
		
	public static class UH_CUSTOMER_ORDER {
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String cust_ord_no = "cust_ord_no" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String state = "state" ;
			
		public static final String status_date = "status_date" ;
			
		public static final String pre_handle_flag = "pre_handle_flag" ;
			
		public static final String priority = "priority" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String ask_source = "ask_source" ;
			
		public static final String ask_source_srl = "ask_source_srl" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"cust_ord_id","cust_ord_no","cust_id","state","status_date","pre_handle_flag","priority","business_id","lan_id","staff_no","site_no","ask_source","ask_source_srl"};
	}
		
	public static class UH_ENT_PRODUCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String ent_product_id = "ent_product_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String mkt_res_id = "mkt_res_id" ;
			
		public static final String mkt_res_inst_id = "mkt_res_inst_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","ent_product_id","product_id","instance_type","instance_id","mkt_res_id","mkt_res_inst_id"};
	}
		
	public static class UH_ENT_PRODUCT_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String ent_product_id = "ent_product_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","ent_product_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class UH_HANDLER {
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String handler_name = "handler_name" ;
			
		public static final String handler_card_type = "handler_card_type" ;
			
		public static final String handler_card_no = "handler_card_no" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String notes = "notes" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"cust_ord_id","handler_name","handler_card_type","handler_card_no","tel","mobile_phone","fax","bp","email","addr","post_code","notes"};
	}
		
	public static class UH_NOT_PAY_LIST {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String ord_type = "ord_type" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String bureau_no = "bureau_no" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String price_object_type = "price_object_type" ;
			
		public static final String price_object_id = "price_object_id" ;
			
		public static final String object_id = "object_id" ;
			
		public static final String service_offer_id = "service_offer_id" ;
			
		public static final String acct_id = "acct_id" ;
			
		public static final String acct_item_type_id = "acct_item_type_id" ;
			
		public static final String need_cash = "need_cash" ;
			
		public static final String type_type = "type_type" ;
			
		public static final String type_adsc = "type_adsc" ;
			
		public static final String price_id = "price_id" ;
			
		public static final String base_instance_type = "base_instance_type" ;
			
		public static final String base_instance_id = "base_instance_id" ;
			
		public static final String time_limit_type = "time_limit_type" ;
			
		public static final String time_limit_value = "time_limit_value" ;
			
		public static final String check_date = "check_date" ;
			
		public static final String charge_mode = "charge_mode" ;
			
		public static final String fact_cash = "fact_cash" ;
			
		public static final String pay_type = "pay_type" ;
			
		public static final String pay_date = "pay_date" ;
			
		public static final String send_date = "send_date" ;
			
		public static final String notes = "notes" ;
			
		public static final String fee_serialno = "fee_serialno" ;
			
		public static final String pan_serial = "pan_serial" ;
			
		public static final String state = "state" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","ord_type","cust_id","bureau_no","instance_type","instance_id","price_object_type","price_object_id","object_id","service_offer_id","acct_id","acct_item_type_id","need_cash","type_type","type_adsc","price_id","base_instance_type","base_instance_id","time_limit_type","time_limit_value","check_date","charge_mode","fact_cash","pay_type","pay_date","send_date","notes","fee_serialno","pan_serial","state","staff_no","site_no"};
	}
		
	public static class UH_OFFER_INST {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String product_offer_id = "product_offer_id" ;
			
		public static final String offer_kind = "offer_kind" ;
			
		public static final String price_id = "price_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String create_date = "create_date" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","comp_inst_id","product_offer_instance_id","cust_id","product_offer_id","offer_kind","price_id","lan_id","business_id","create_date","eff_date","exp_date"};
	}
		
	public static class UH_OFFER_INST_ASSURE {
			
		public static final String ord_id = "ord_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String assure_id = "assure_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String assure_method = "assure_method" ;
			
		public static final String assure_info = "assure_info" ;
			
		public static final String assure_name = "assure_name" ;
			
		public static final String card_type = "card_type" ;
			
		public static final String card_no = "card_no" ;
			
		public static final String tel = "tel" ;
			
		public static final String mobile_phone = "mobile_phone" ;
			
		public static final String fax = "fax" ;
			
		public static final String bp = "bp" ;
			
		public static final String email = "email" ;
			
		public static final String addr = "addr" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String notes = "notes" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_id","cust_ord_id","ord_item_id","action_type","state_date","end_time","beg_time","assure_id","comp_inst_id","assure_method","assure_info","assure_name","card_type","card_no","tel","mobile_phone","fax","bp","email","addr","post_code","notes"};
	}
		
	public static class UH_OFFER_INST_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String product_offer_id = "product_offer_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","product_offer_instance_id","product_offer_id","attr_id","field_name","attr_val","comp_inst_id"};
	}
		
	public static class UH_OFFER_INST_DETAIL {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String offer_detail_id = "offer_detail_id" ;
			
		public static final String comp_role_id = "comp_role_id" ;
			
		public static final String instance_type = "instance_type" ;
			
		public static final String instance_id = "instance_id" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","product_offer_instance_id","offer_detail_id","comp_role_id","instance_type","instance_id","lan_id","eff_date","exp_date"};
	}
		
	public static class UH_REMOVE_CAUSE {
			
		public static final String remove_cause_id = "remove_cause_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String product_offer_instance_id = "product_offer_instance_id" ;
			
		public static final String remove_date = "remove_date" ;
			
		public static final String out_cause = "out_cause" ;
			
		public static final String out_to = "out_to" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"remove_cause_id","cust_ord_id","product_offer_instance_id","remove_date","out_cause","out_to"};
	}
		
	public static class UH_SERV {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String cust_id = "cust_id" ;
			
		public static final String product_family_id = "product_family_id" ;
			
		public static final String user_cust_id = "user_cust_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String area_code = "area_code" ;
			
		public static final String acc_nbr = "acc_nbr" ;
			
		public static final String physical_nbr = "physical_nbr" ;
			
		public static final String lan_id = "lan_id" ;
			
		public static final String business_id = "business_id" ;
			
		public static final String billing_mode_id = "billing_mode_id" ;
			
		public static final String rent_date = "rent_date" ;
			
		public static final String user_password = "user_password" ;
			
		public static final String state = "state" ;
			
		public static final String user_prop = "user_prop" ;
			
		public static final String notes = "notes" ;
			
		public static final String user_name = "user_name" ;
			
		public static final String address_id = "address_id" ;
			
		public static final String detail_address = "detail_address" ;
			
		public static final String branch_id = "branch_id" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_id","comp_inst_id","cust_id","product_family_id","user_cust_id","product_id","area_code","acc_nbr","physical_nbr","lan_id","business_id","billing_mode_id","rent_date","user_password","state","user_prop","notes","user_name","address_id","detail_address","branch_id"};
	}
		
	public static class UH_SERV_ACCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String acct_item_group_id = "acct_item_group_id" ;
			
		public static final String acct_id = "acct_id" ;
			
		public static final String invoice_require_id = "invoice_require_id" ;
			
		public static final String bill_require_id = "bill_require_id" ;
			
		public static final String payment_rule_id = "payment_rule_id" ;
			
		public static final String self_flag = "self_flag" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_id","acct_item_group_id","acct_id","invoice_require_id","bill_require_id","payment_rule_id","self_flag"};
	}
		
	public static class UH_SERV_ACC_NBR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String acc_nbr_type_cd = "acc_nbr_type_cd" ;
			
		public static final String add_acc_nbr = "add_acc_nbr" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","site_no","staff_no","serv_id","acc_nbr_type_cd","add_acc_nbr"};
	}
		
	public static class UH_SERV_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class UH_SERV_BILL_POST {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String bill_type = "bill_type" ;
			
		public static final String owner_type = "owner_type" ;
			
		public static final String post_method = "post_method" ;
			
		public static final String post_code = "post_code" ;
			
		public static final String incept_person = "incept_person" ;
			
		public static final String post_address = "post_address" ;
			
		public static final String email = "email" ;
			
		public static final String contact_name = "contact_name" ;
			
		public static final String contact_phone = "contact_phone" ;
			
		public static final String guard_post = "guard_post" ;
			
		public static final String certificate_type = "certificate_type" ;
			
		public static final String certificate_no = "certificate_no" ;
			
		public static final String query_password = "query_password" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_id","bill_type","owner_type","post_method","post_code","incept_person","post_address","email","contact_name","contact_phone","guard_post","certificate_type","certificate_no","query_password"};
	}
		
	public static class UH_SERV_PARTY {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String comp_inst_id = "comp_inst_id" ;
			
		public static final String rel_type = "rel_type" ;
			
		public static final String rel_id = "rel_id" ;
			
		public static final String party_org_id = "party_org_id" ;
			
		public static final String party_id = "party_id" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String exp_date = "exp_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","comp_inst_id","rel_type","rel_id","party_org_id","party_id","eff_date","exp_date"};
	}
		
	public static class UH_SERV_PRODUCT {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String serv_product_id = "serv_product_id" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String rent_date = "rent_date" ;
			
		public static final String r_serv_id = "r_serv_id" ;
			
		public static final String state = "state" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","staff_no","site_no","serv_product_id","serv_id","product_id","rent_date","r_serv_id","state"};
	}
		
	public static class UH_SERV_PRODUCT_ATTR {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String serv_product_id = "serv_product_id" ;
			
		public static final String attr_id = "attr_id" ;
			
		public static final String field_name = "field_name" ;
			
		public static final String product_id = "product_id" ;
			
		public static final String attr_val = "attr_val" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","end_time","beg_time","serv_product_id","attr_id","field_name","product_id","attr_val"};
	}
		
	public static class UH_SERV_STATE {
			
		public static final String ord_item_id = "ord_item_id" ;
			
		public static final String cust_ord_id = "cust_ord_id" ;
			
		public static final String ord_id = "ord_id" ;
			
		public static final String action_type = "action_type" ;
			
		public static final String state_date = "state_date" ;
			
		public static final String beg_time = "beg_time" ;
			
		public static final String end_time = "end_time" ;
			
		public static final String site_no = "site_no" ;
			
		public static final String staff_no = "staff_no" ;
			
		public static final String serv_id = "serv_id" ;
			
		public static final String stop_type = "stop_type" ;
			
		public static final String eff_date = "eff_date" ;
			
		public static final String[] TABLE_FIELDS = 
			new String[]{"ord_item_id","cust_ord_id","ord_id","action_type","state_date","beg_time","end_time","site_no","staff_no","serv_id","stop_type","eff_date"};
	}
	
}
