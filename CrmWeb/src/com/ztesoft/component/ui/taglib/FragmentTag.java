package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FragmentTag extends BodyTagSupport {

	private String id;
	
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		
		String body = this.getBodyContent().getString();
		if(body!=null){
			pageContext.getRequest().setAttribute(this.getId(), body);
		}
		
		return super.doAfterBody();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}	
	
}
