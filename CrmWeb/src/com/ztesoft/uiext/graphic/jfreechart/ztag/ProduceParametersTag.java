package com.ztesoft.uiext.graphic.jfreechart.ztag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ProduceParametersTag extends TagSupport {

	private String parameterId;

	private String type;

	private String value;

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub

		Tag dataset = this.getParent();
		System.out.println("dataset=" + dataset);
		if (dataset != null) {
			((ProduceGraphicTag) dataset).setParameter(new ParameterVo(this
					.getParameterId(), this.getType(), this.getValue()));
		}

		return super.doEndTag();
	}

	public void release() {
		// TODO Auto-generated method stub
		super.release();

		this.parameterId = null;
		this.type = null;
		this.value = null;
	}
}
