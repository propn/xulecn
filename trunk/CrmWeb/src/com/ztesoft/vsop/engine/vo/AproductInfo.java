package com.ztesoft.vsop.engine.vo;
/**
 * ����ҵ������������Ʒ
 * @author cooltan
 *
 */

public class AproductInfo {
	public static final String actionTypeOfAdd = "0";//���� 
	public static final String actionTypeOfCancel = "1";//�˶�
	private String actionType  ;//ҵ������������Ʒ����
	private String aProductID ;//������Ʒ��ʶ
	private String aProductInstID ;//ҵ������ʵ����ʶ
	private String lanCode;//����������
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
