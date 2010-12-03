package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

public class BarTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BarTag.class);

	private String id;
	private String type;
	private String desc;
	private String style;
    
	private String title;
	private String content;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() { 
		return type;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return (desc!=null ? desc : "");
	}
	
	public void setStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}	
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return (content!=null ? content : "");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.getDesc()+(title!=null ? title : "");
	}	
		    
	private String createBarPart(String id){
		
		StringBuffer sbuffer = new StringBuffer();
	     
		if("titleDiv".equals(id)){
			sbuffer.append("<div class='"+this.getType()+"_"+id+"'>"+this.getTitle()+"</div>");			
		}
		else if("contentDiv".equals(id)){
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'>"+this.getContent()+"</td>"); 			
		}
		else if("titleTable_center".equals(id)){
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'>"+createBarPart("titleDiv")+"</td>");			
		}
		else if("top_center".equals(id)){
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'>");
			sbuffer.append(" <table class='"+this.getType()+"_titleTable'>");
			sbuffer.append("  <tr>");
			sbuffer.append(createBarPart("titleTable_left"));
			sbuffer.append(createBarPart("titleTable_center"));
			sbuffer.append(createBarPart("titleTable_right"));
			sbuffer.append("  </tr>");
			sbuffer.append(" </table>");
			sbuffer.append("</td>");
		}
		else if("top_left".equals(id) || "top_right".equals(id)){ 
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'><div style='width:6px;'></div></td>"); 
		}
		else{ 
			sbuffer.append("<td class='"+this.getType()+"_"+id+"'></td>"); 
		}		
		
		return sbuffer.toString();
	}
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub

		StringBuffer sbuffer = new StringBuffer();			
		
		sbuffer.append("<table class='"+this.getType()+"_barPanel'");
		if(this.getStyle()!=null)
			sbuffer.append(" style='"+this.getStyle()+"'");
		sbuffer.append(">");
		sbuffer.append("<tr>");
		sbuffer.append(createBarPart("top_left"));
		sbuffer.append(createBarPart("top_center"));
		sbuffer.append(createBarPart("contentDiv"));
		sbuffer.append(createBarPart("top_right"));		
		sbuffer.append(" </tr>");
		sbuffer.append("</table>"); 	
			
		try{
			String result = sbuffer.toString();
			if(!ContentManage.isSetParentContent(this, result)){				
				pageContext.getOut().println(result);	
			}
		}catch(Exception e){
			logger.error("Bar������ʧ��", e);
		}
		
		return super.doEndTag();
	}
	
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.type = null;
		this.desc = null;
		this.style = null;	
		
		this.title = null;
		this.content = null;
	
	}		
}
