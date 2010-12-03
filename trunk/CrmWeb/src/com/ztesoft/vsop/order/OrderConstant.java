package com.ztesoft.vsop.order;

public interface OrderConstant {
	
	/*
	 * 订单项类型
	 */
	public static final String ORDER_ITEM_CD_CUST = "01"; //ORDER_ITEM_CD 订单项类型分为：01=客户订单项
	public static final String ORDER_ITEM_CD_ACC = "02"; //订单项类型,02=账户订单项
	public static final String ORDER_ITEM_CD_PRODOFFER = "03"; //ORDER_ITEM_CD 订单项类型 03=销售品订单项
	public static final String ORDER_ITEM_CD_PRODUCT = "04"; //ORDER_ITEM_CD 订单项类型 04=产品订单项
	/*
	 * 增值产品类型
	 */
	public static final String PROD_OFFER_TYPE_PRODUCT_OFFER_ID = "0";  //纯增值销售品
	public static final String PROD_OFFER_TYPE_PACKAGE_ID = "1";    //增值捆绑套餐
	public static final String PROD_OFFER_TYPE_PPROD_OFFER_ID = "2";  //（传统+增值）捆绑套餐
	//	订单动作
	public static final String orderTypeOfAdd = "0"; //订购
	public static final String orderTypeOfDel = "1"; //退订
	public static final String orderTypeOfAll = "2"; //全部退订
	//服务开通过来的订单动作
	public static final String orderTypeOfInstall = "10"; //新装
	public static final String orderTypeOfModifyState = "11";//用户状态变更
	public static final String orderTypeOfModifyNo = "12"; //改号
	public static final String orderTypeOfModifyAProduct = "13"; //改业务能力附属产品
	public static final String orderTypeOfModifyVProduct = "14"; //改增值产品
	public static final String orderTypeOfUninstall = "15"; //拆机
	public static final String orderTypeOfModifyPayType = "16"; //付费类型变更
	public static final String orderTypeOfModifyService = "20"; //改服务功能
	
	public static final String orderStateOfCreated = "00A"; //生效
	public static final String orderStateOfUnconfirm = "00B"; //待确认
	public static final String orderStateOfDelete = "00X"; //失效
	
	public static final String VProductActionTypeOfAdd = "0";  //新订产品
	public static final String VProductActionTypeOfPendding = "01";  //加入已有产品
	public static final String VProductActionTypeOfReplace = "2";  //替换已有产品
	public static final String VProductActionTypeOfDel = "3";  //退订产品
	public static final String VProductActionTypeOfCancelFromPackage = "4";  //产品退出套餐
	
	public static final String ORDER_ACTIVE_STATE_UNACTIVE = "0";  //待激活
//	public static final String ORDER_ACTIVE_STATE_UNACTIVE = "1";  //
	public static final String ORDER_ACTIVE_STATE_SUCCESS = "2";  //激活成功
	
	public static final String PROD_SPEC_CODE_TELEPHONE = "001"; //固话
	public static final String PROD_SPEC_CODE_PHS = "002";  //小灵通
	public static final String PROD_SPEC_CODE_MOBILE = "003";  //手机
	public static final String PROD_SPEC_CODE_WAN = "004";  //宽带
	
}
