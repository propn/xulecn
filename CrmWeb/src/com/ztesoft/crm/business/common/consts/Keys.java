package com.ztesoft.crm.business.common.consts;

public final class Keys {

	
	//服务类型
	public final static String SERVICE_TYPE = "service_type";	
	// 业务动作
	public final static String ACTION_TYPE = "action_type";
	// 客户订单
	public final static String CUST_ORD_ID = "cust_ord_id";
	// 订单标识
	public final static String ORD_ID = "ord_id";
	// 订单项标识
	public final static String ORD_ITEM_ID = "ord_item_id";
	// 销售品实例标识
	public final static String PRODUCT_OFFER_INSTANCE_ID = "product_offer_instance_id";
	// 父销售品实例标识
	public final static String COMP_INST_ID = "comp_inst_id";
	// 字段名
	public final static String FIELD_NAME = "field_name";	
	//字段值
	public final static String FIELD_VALUE = "field_value";
    //字段原值
	public final static String OLD_FIELD_VALUE = "old_field_value";
	//营业工号	
	public final static String STAFF_NO="staff_no";	
	//营业网点	
	public final static String SITE_NO="site_no";	
	//服务提供标识	
	public final static String SERVICE_OFFER_ID="service_offer_id";	
	//操作标志
	public final static String OPER_FLAG="operFlag";	
	//产品实例标识
	public final static String SERV_ID = "serv_id";
	//帐户标识
	public final static String ACCT_ID = "acct_id";
	//帐目组标识
	public final static String ACCT_ITEM_GROUP_ID = "acct_item_group_id";
	//停机类型
	public final static String STOP_TYPE = "stop_type";
    
	public final static String BILL_TYPE = "bill_type";
	
	public final static String ACC_NBR_TYPE_CD = "acc_nbr_type_cd";
	
	//关系标识
	public final static String REL_ID = "rel_id";	
	//关系类型
	public final static String REL_TYPE = "rel_type";
    //销售品明细标识
	public final static String OFFER_DETAIL_ID = "offer_detail_id";
	//销售品标识
	public final static String PRODUCT_OFFER_ID = "product_offer_id";	
	//客户ID
	public final static String CUST_ID = "cust_id";	
	//公共的比对方法diff中使用
	public final static String STATE_DATE="state_date";
	public final static String END_TIME ="end_time";
	public final static String BEG_TIME="beg_time";
	
	// 用于唯一标识产品/服务的内部编号
	public final static String PRODUCT_ID = "product_id";	
	//附属产品实例标识
	public final static String SERV_PRODUCT_ID = "serv_product_id";
	//账户实例标识
	public final static String SERV_ACCT_ID = "serv_acct_id";	
	//销售品标识
	public final static String OFFER_ID = "offer_id";	
	//产品名称
	public final static String PRODUCT_NAME = "product_name";	
	//同一订单号
	public final static String SAME_ASK_NO = "same_ask_no";	
	//属性标识
	public final static String ATTR_ID = "attr_id";	
	//属性取值
	public final static String ATTR_VAL = "attr_val";	
	//序列号
	public final static String SEQ = "seq";
	//产品家族标识
	public final static String PRODUCT_FAMILY_ID = "product_family_id";
	//营业区
	public final static String BUSINESS_ID = "business_id";
	//同笔订单号
	public final static String ASK_ID = "ask_id";
	//本地网
	public final static String LAN_ID = "lan_id";
	//对应到计费的基础商品
	public final static String PRICE_ID = "price_id";
	//涉及实例的唯一标识
	public final static String INSTANCE_ID = "instance_id";
    //创建时间
    public final static String CREATE_DATE = "create_date";
	
	
	
	
	
	
	
	
	
	//-------------------------------表-----------------------
	//产品实例表
	public final static String TABLE_SERV ="serv";	
	//产品实例附加属性
	public final static String TABLE_SERV_ATTR = "serv_attr";
	//附属产品实例表
	public final static String TABLE_SERV_PRODUCT = "serv_product";
	//附属产品实例附加属性
	public final static String TABLE_SERV_PRODUCT_ATTR = "serv_product_attr";
	//务定制关系
	public final static String TABLE_SERV_ACCT = "serv_acct";	
	//销售品实例表
	public final static String TABLE_OFFER_INST = "offer_inst";
	//销售品实例参数
	public final static String TABLE_OFFER_INST_ATTR = "offer_inst_attr";	
		
    //-------------------------------表-----------------------

    
    //各表在新老数据比较时，不要比较的字段
    public static final String IGNORE_FIELD_SERV               = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_SERV_ATTR          = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_SERV_PRODUCT       = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_SERV_PRODUCT_ATTR  = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_OFFER_INST         = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,create_date,eff_date,exp_date,"; //生效时间暂时没有比较
    public static final String IGNORE_FIELD_OFFER_INST_ATTR    = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,";
    public static final String IGNORE_FIELD_OFFER_INST_DETAIL  = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time,eff_date,exp_date,";
    public static final String IGNORE_FIELD_SERV_ACCT          = ",ord_item_id,cust_ord_id,ord_id,flow_id,action_type,state_date,end_time,beg_time," ;

}
