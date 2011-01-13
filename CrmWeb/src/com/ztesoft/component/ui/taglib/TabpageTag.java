package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

public class TabpageTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TabpageTag.class);
	
	private String desc;
	private String use;
	private String target;
	private String href;
	private String checkable;
	private String autoLoad;
	private String autoScroll;
	private String visible;
	
	private String id;
	private String title;
	private String content;
	
	private ArrayList contentList;
	
	private String dynaControlId;
	
	/**
	 * @return the dynaControlId
	 */
	public String getDynaControlId() {
		return dynaControlId;
	}

	/**
	 * @param dynaControlId the dynaControlId to set
	 */
	public void setDynaControlId(String dynaControlId) {
		this.dynaControlId = dynaControlId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return (desc!=null ? desc : "");
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getHref() {
		return (href!=null ? href : "");
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return (target!=null ? target : "");
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getCheckable() {
		return (checkable!=null ? checkable : "false");
	}
	public void setCheckable(String checkable) {
		this.checkable = checkable;
	}	
	public String getAutoLoad() {
		return (autoLoad!=null ? autoLoad : "false");
	}
	public void setAutoLoad(String autoLoad) {
		this.autoLoad = autoLoad;
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
	
	public String getTitle() {
		return this.getDesc()+(title!=null ? title : "");
	}
	public void setTitle(String title) {
		this.title = title;
	}		
	
	
	public String getPageTitle(int index, String id, String type){
		
		String tdStyleText = ( (index==0) ? "style='background-position-y:-20px;'" : "" );
		String tdClassName = ( (index==0) ? "selected='true'" : "" );
		
		StringBuffer sbuffer = new StringBuffer();	
		
		
		
		try {
			if(this.getDynaControlId()!=null&&!"".equals(this.getDynaControlId()))
			PageTagUtils.getInstance().setTabpageTagDynaElement(this);
		} catch (JspException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String style = "";
		if("false".equalsIgnoreCase(this.getVisible())){
			style = "style='display:none;'";			
		}
		
		sbuffer.append("<td id='"+id+"_"+index+"' "+style+" tabset='"+id+"' onclick='showPage(this);' "+tdClassName+">");
		if(!"".equalsIgnoreCase(this.getTarget())){
		    sbuffer.append("<a href='"+this.getHref()+"' target='"+this.getTarget()+"' hideFocus='true' open='"+this.getAutoLoad()+"' onclick='return switchPage(this);'>");
		}
		else{
		    sbuffer.append("<a hideFocus='true'>");
		}
		sbuffer.append("<span id='"+id+"_"+index+"_titleLeft' class='tabsetTitleTdLeft' "+tdStyleText+">��</span>");
		sbuffer.append("<span id='"+id+"_"+index+"_titleCenter' class='tabsetTitleTdCenter' "+tdStyleText+">");
		if("true".equalsIgnoreCase(this.getCheckable())){
			sbuffer.append("<input type='checkbox' id='"+id+"_"+index+"_checkbox' hideFocus='true'/>");			
		    sbuffer.append("<label for='"+id+"_"+index+"_checkbox'>");
		    sbuffer.append(this.getTitle());
		    sbuffer.append("</label>");
		}
		else{
			sbuffer.append(this.getTitle());
		}
		sbuffer.append("</span>");
		sbuffer.append("<span id='"+id+"_"+index+"_titleRight' class='tabsetTitleTdRight' "+tdStyleText+">��</span>");
		sbuffer.append("</a>");
		sbuffer.append("</td>");
		
		return sbuffer.toString();			
	}
	public String getPageContent(int index, String id, String type){
		StringBuffer sbuffer = new StringBuffer();
		
		//sbuffer.append("<div id='"+id+"_"+index+"_div' style='width:100%;height:100%;display:"+(index==0 ? "" : "none")+"'>");
		sbuffer.append("<tr id='"+id+"_"+index+"_div' style='display:"+(index==0 ? "" : "none")+"'>");
		sbuffer.append("<td style='vertical-align:top;padding:2px;'>");
		if(!"".equals(this.getTarget())){
			String src = "";
			if("true".equalsIgnoreCase(this.getAutoLoad())){
				src = this.getHref();
			}
			sbuffer.append("<iframe name='"+this.getTarget()+"' src='"+src+"' style='height:100%;width:100%' frameborder='0' allowTransparency='true' scrolling='auto'></iframe>");
		}
		else{
			sbuffer.append("<div style='overflow:auto;width:100%;height:100%;'>");
    		sbuffer.append(this.getContent());			
    		sbuffer.append("</div>");
		}
		sbuffer.append("</td>");
		sbuffer.append("</tr>");	
		//sbuffer.append("</div>");
		
		return sbuffer.toString();	
	}
		
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub

			Tag tabpane = this.getParent();
			if(tabpane!=null){
				TabpaneTag pane = (TabpaneTag)tabpane;
				pane.setTitle(this.getPageTitle(pane.getTitles().size(), pane.getId(), pane.getType()));	
				pane.setContent(this.getPageContent(pane.getContents().size(), pane.getId(), pane.getType()));
			}

		
		return super.doEndTag();
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
	
	public void release() {
	    super.release();

	    this.setDesc(null);
	    this.setUse(null);
	    this.setTarget(null);
		this.setHref(null);
		this.setCheckable(null);
		this.setAutoLoad(null);
		this.setAutoScroll(null);
		this.setVisible(null);
		
		this.setTitle(null);
		this.setContent(null);
		this.setContentList(null);

	}
	public String getAutoScroll() {
		return (autoScroll!=null ? autoScroll : "false");
	}
	public void setAutoScroll(String autoScroll) {
		this.autoScroll = autoScroll;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}

}
