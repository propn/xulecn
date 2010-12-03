package com.ztesoft.vsop.order.vo;

import org.dom4j.Element;

import com.ztesoft.vsop.order.XMLUtils;

/**
 * 附属产品信息
 * @author yi.chengfeng Mar 2, 2010 4:10:02 PM
 *
 */
public class AProduct {
	private String ActionType;
	private String AProductID;
	public static final String actionTypeOfAdd = "0";//订购 
	public static final String actionTypeOfCancel = "1";//订购 
	
	public String getActionType() {
		return ActionType;
	}
	public void setActionType(String actionType) {
		ActionType = actionType;
	}
	
	public String getAProductID() {
		return AProductID;
	}
	public void setAProductID(String productID) {
		AProductID = productID;
	}
	public AProduct() {
		super();
	}
	public AProduct(Element root) {
		super();
		setActionType(XMLUtils.getElementStringValue(root, "ActionType"));
		setAProductID(XMLUtils.getElementStringValue(root, "AProductID"));
	}
	
	
}
