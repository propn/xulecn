package com.ztesoft.vsop.engine.vo;
/**
 * 订单业务能力附属产品
 * @author cooltan
 *
 */

public class AproductInfo {
	public static final String actionTypeOfAdd = "0";//订购 
	public static final String actionTypeOfCancel = "1";//退订
	private String actionType  ;//业务能力附属产品动作
	private String aProductID ;//附属产品标识
	private String aProductInstID ;//业务能力实例标识
	private String lanCode;//本地网区号
	private String dbActionType;
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getAProductID() {
		return aProductID;
	}
	public void setAProductID(String productID) {
		aProductID = productID;
	}
	public String getAProductInstID() {
		return aProductInstID;
	}
	public void setAProductInstID(String productInstID) {
		aProductInstID = productInstID;
	}
	public String getDbActionType() {
		return dbActionType;
	}
	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}
	public String getLanCode() {
		return lanCode;
	}
	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}
}
