package com.ztesoft.crm.business.common.consts;

//DICACTION的静态定义
public final class Actions {

	//后台服务名静态常量，例如产品受理
	public final static String ENTRY_SERVICE_NAME="service";
	
	//往DTO里面传入的参数
	public final static String PARAMETER="parameter";
	
	//DICACTION的执行方法
	public final static String EXECUTE="execute";
	
//	COMMONDATA
	public final static String COMMON_DATA="commonData";
	
	/***********DICACTION的名称,后台订单动作****************/
	public final static String ORDER_ENTRY_ACTION="ORDER_ENTRY_ACTION";
	//处理销售品数据 方便后台使用
	public final static String DATA_ACTION="DATA_ACTION";
	/*******************************/
	//受理方法
	public final static String ACCEPT="ACCEPT";
	//创建客户订单方法
	public final static String CREATE_CUST_ORDER="CREATE_CUST_ORDER";
	
	//组装销售品后台数据
	public final static String CREATE_OFFER_BACK_DATA="CREATE_OFFER_BACK_DATA";

	//组装产品的后台保存数据。
	public final static String CREATE_SERV_DATA="CREATE_SERV_DATA";
	
	//后台执行方法
	public final static String EXECUTE_METHOD="execute_method";
	
	public final static String INIT = "INIT";
	/***********销售品业务动作 装 或 改****************/
	public final static String OFFER_INSTALL = "A";//装
	public final static String OFFER_MODIFY = "M";//改
	
}
