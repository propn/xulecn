package com.ztesoft.crm.business.common.consts;

//销售品相关的静态数据常量定义
public final class OfferConsts {

	//可以单独售卖
	public final static String CAN_BUY_ALONE="0";
	//不可以单独售卖
	public final static String CANNOT_BUY_ALONE="1";
	//基础销售品
	public final static String BASE_OFFER="0";
	//组合销售品
	public final static String COMP_OFFER="1";
	
	/*******业务动作*******/
	//增加
	public final static String ACTION_TYPE_A="A";
	//修改
	public final static String ACTION_TYPE_M="M";
	//删除
	public final static String ACTION_TYPE_D="D";
	//保持
	public final static String ACTION_TYPE_K="K";
	
	/*******销售品类型 0 基础销售品 1 基础包 2 可选包*******/
	
	public final static String OFFER_KIND_0="0";
	
	public final static String OFFER_KIND_1="1";
	
	public final static String OFFER_KIND_2="2";
	
}
