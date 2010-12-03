package com.ztesoft.crm.business.common.consts;

public final class ReceptionActions {
	
	//往DTO里面传入的参数
//	public final static String PARAMETER="parameter";
	
	//DICACTION的名称, 客户接待
	public final static String CUST_RECEPT_ACTION="CUSTRECEPTIONBO";
	
	//DICACTION的执行方法。查询客户
	public final static String GET_CUST_LIST = "getCustList";
	
	//DICACTION的执行方法。查询客户的帐户
	public final static String GET_ACCT_LIST = "getAcctList";
	
	//DICACTION的执行方法。展示推荐商品的描述。
	public final static String SHOW_OFFER_RECOMMEND="showOfferRecommend";
	
	//DICACTION的执行方法。获取推荐给客户的销售品。
	public final static String GET_OFFER_RECOMMEND="getCustRecommend";
	
	//DICACTION的执行方法。获取客户的实例信息。
	public final static String SHOW_OFFER_INSTANCE = "showOffInstance"; 
	
	//DICACTION的执行方法。获取客户的实例的详细信息。
	public final static String SHOW_OFFER_INSTANCE_DETAIL = "showOffInstanceDetail"; 
	
	//DICACTION的执行方法。获取销售品服务提供。
	public final static String SHOW_OFFER_SERVICE = "showOffService"; 
	
	//DICACTION的执行方法。获取客户过程单信息。
	public final static String GET_CUST_ORD_INFO = "showCustOrdInfo"; 
	
	//DICACTION的执行方法。获取实例的详细信息。
	public final static String GET_INSTANCE_DETAIL = "showOffInstanceDetail";
	
	//DICACTION的执行方法。获取序列。
	public final static String GET_SEQ_ID = "getSeqId";
	
	//DICACTION的执行方法。客户接待页面查询出客户后，获取信息。
	public final static String QUERY_CUST_INIT_INFO = "getInitInfoByCustId";
}
