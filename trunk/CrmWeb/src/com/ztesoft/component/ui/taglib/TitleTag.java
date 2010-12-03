package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class TitleTag extends BodyTagSupport {

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		
		Tag panel = this.getParent();
		if(panel!=null){
			String title = this.getBodyContent().getString();
			if(title!=null){
			    if(panel instanceof BarTag){
			        ((BarTag)panel).setTitle(title);
			    }				
			    else if(panel instanceof PanelTag){
			        ((PanelTag)panel).setTitle(title);
			    }
			    else if(panel instanceof TabpageTag){
			    	((TabpageTag)panel).setTitle(title);
		    	}			  
			}
		}
		
		return super.doAfterBody();
	}	
	
}
