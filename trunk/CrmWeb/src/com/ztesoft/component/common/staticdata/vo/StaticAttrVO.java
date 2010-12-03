package com.ztesoft.component.common.staticdata.vo;

import com.ztesoft.common.valueobject.ValueObject;

/**
 * 
 * @Classname     : StaticAttrVO
 * @Description   : ��̬����ֵ����
 * @Copyright ? 2006 ZTEsoft.
 * @Author        : fjy
 * @Create Date   : 2006-3-28
 *
 * @Last Modified : 
 * @Modified by   : 
 * @Version       : 1.0
 */
public class StaticAttrVO extends ValueObject {
	
	private String attrId = "";     //��̬����id
	private String attrValue = "";  //��̬����ֵ
	private String attrValueId = "";  //��̬����ֵId
	private String attrValueDesc = "";  //��̬��������    
    private String attrCode = "";   //��̬���Դ���
    private String parentValueId = ""; //�ϼ�����
    private String param1 = ""; //��������1����Ϊ����������
    
    public StaticAttrVO() {
    }


	public String getAttrCode() {
		return attrCode;
	}


	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}


	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getParentValueId() {
		return parentValueId;
	}

	public void setParentValueId(String parentValueId) {
		this.parentValueId = parentValueId;
	}

	public String getAttrValueDesc() {
		return attrValueDesc;
	}

	public void setAttrValueDesc(String attrValueDesc) {
		this.attrValueDesc = attrValueDesc; 
	}


	public String getAttrValueId() {
		return attrValueId;
	}


	public void setAttrValueId(String attrValueId) {
		this.attrValueId = attrValueId;
	}


	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}
    
}
