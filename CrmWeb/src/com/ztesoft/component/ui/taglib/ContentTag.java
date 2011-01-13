package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class ContentTag extends BodyTagSupport {
	
	private String id;
	private String use;
	
	private String content;
	
	private ArrayList contentList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		
		String _content = "";
		
		if(this.getUse()!=null){
		  String content1 = (String)pageContext.getRequest().getAttribute(this.getUse());
		  _content += (content1==null)?"":content1.toString();
		}	
		
		this.setContent(_content);		
		
		return super.doStartTag();
	}

	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		
		String content1 = this.getBodyContent().getString();
		String _content = (content1==null)?"":content1;
		
		this.setContent(_content+this.content);
	
		
		return super.doAfterBody();
	}
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		Tag panel = this.getParent();
		if(panel!=null){
		    if(panel instanceof BarTag){
		        ((BarTag)panel).setContent(this.getContent());
		    }			
		    else if(panel instanceof PanelTag){
		        ((PanelTag)panel).setContent(this.getContent());
		    }
		    else if(panel instanceof TabpageTag){
		    	((TabpageTag)panel).setContent(this.getContent());
	    	}	
		}
		
		return super.doEndTag();
	}	

	public void setUse(String use) {
		this.use = use;
	}

	public String getUse() {
		return use;
	}
	
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.use = null;
		this.content = null;
		this.contentList = null;
	}

	public String getContent() {
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append((content!=null ? content : ""));
		if(this.contentList!=null){
			for(int i=0; i<this.contentList.size(); i++){
				sbuffer.append(this.contentList.get(i));			
			}
		}
		return sbuffer.toString();
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList getContentList() {
		return contentList;
	}

	public void setContentList(ArrayList contentList) {
		this.contentList = contentList;
	}		
	
	public void setContentListChild(Object content) {
		if(this.contentList==null){
			this.contentList = new ArrayList();
		}
		this.contentList.add(content);
	}	

}
