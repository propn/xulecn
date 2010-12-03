package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class TableTag extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FormTag.class);

	private String id;
	private String dataset;
	private String fields;
	private String readOnly;
	private String editable;
	private String showHeader;
	private String showFooter;
	private String showIndicator;
	private String highlightSelection;
	private String maxRow;
	private String skipRebuild;	


	private String scope = "" ;
	private static String scopeRequest = "request" ;
		
	public int getTagScope() {
		if(scopeRequest.equals(scope))
			return PageContext.REQUEST_SCOPE;
			
		return PageContext.PAGE_SCOPE;
	}
		
	public String getScope() {
		return this.scope ;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
		
		
	public String getDataset() {
		return dataset;
	}


	public void setDataset(String dataset) {
		this.dataset = dataset;
	}


	public String getEditable() {
		return (editable!=null ? editable : "false");
	}


	public void setEditable(String editable) {
		this.editable = editable;
	}


	public String getHighlightSelection() {
		return (highlightSelection!=null ? highlightSelection : "true");
	}


	public void setHighlightSelection(String highlightSelection) {
		this.highlightSelection = highlightSelection;
	}


	public String getId() {
		return (id!=null ? id : "table_"+this.getDataset());
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMaxRow() {
		return (maxRow!=null ? maxRow : "0");
	}


	public void setMaxRow(String maxRow) {
		this.maxRow = maxRow;
	}


	public String getReadOnly() {
		return (readOnly!=null ? readOnly : "false");
	}


	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}


	public String getShowFooter() {
		return (showFooter!=null ? showFooter : "false");
	}


	public void setShowFooter(String showFooter) {
		this.showFooter = showFooter;
	}


	public String getShowHeader() {
		return (showHeader!=null ? showHeader : "true");
	}


	public void setShowHeader(String showHeader) {
		this.showHeader = showHeader;
	}


	public String getShowIndicator() {
		return (showIndicator!=null ? showIndicator : "true");
	}


	public void setShowIndicator(String showIndicator) {
		this.showIndicator = showIndicator;
	}


	public String getSkipRebuild() {
		return (skipRebuild!=null ? skipRebuild : "false");
	}


	public void setSkipRebuild(String skipRebuild) {
		this.skipRebuild = skipRebuild;
	}

	
	public String getFields() {
		return fields;
	}


	public void setFields(String fields) {
		this.fields = fields;
	}
	

	public int doEndTag() throws JspException {
			
		Object ds = pageContext.getAttribute(this.getDataset() , this.getTagScope());
		if(ds!=null && ds instanceof DatasetVo){
			
			DatasetVo datasetVo = (DatasetVo)ds;
			
			if(datasetVo!=null){
				StringBuffer sbuffer = new StringBuffer();					
				
				ArrayList fieldVos = new ArrayList();
				if(this.getFields()!=null){
					String[] fieldNames = null;
					fieldNames = this.getFields().split(","); 		
					for(int i=0; i<fieldNames.length; i++){
						FieldVo fieldVo = datasetVo.getField(fieldNames[i]);
						if(fieldVo!=null){
							fieldVos.add(fieldVo);								
						}
					}
				}
				else{
					fieldVos = datasetVo.getFields();					
				}
				
				if(fieldVos!=null){
					
					boolean _showIndicator = "true".equalsIgnoreCase(this.getShowIndicator());
					
					sbuffer.append("<table id='"+this.getId()+"' dataset='"+this.getDataset()+"' class='datatable' initialized='true' highlightSelection='"+this.getHighlightSelection()+"' ");
					sbuffer.append("  style='width:100%;' cellspacing='0' cellpadding='2' border='0' rules='all' ");
					if(this.getMaxRow()!=null)
						sbuffer.append(" maxRow='"+this.getMaxRow()+"'");
					//sbuffer.append("  activeRow='null' activeRowIndex='null' _activeCell='null' _activeCellIndex='null' activeCellIndex='null' hoverRow='null' hoverRowIndex='null'");
					if(_showIndicator)
						sbuffer.append("  minCellIndex='1'");
					else
						sbuffer.append("  minCellIndex='0'");
					sbuffer.append(">");
					
					/*��ͷ*/
					if("true".equalsIgnoreCase(this.getShowHeader())){
						sbuffer.append("<thead>");
						sbuffer.append("  <tr>");
						if(_showIndicator){
							//20090424
							//sbuffer.append("<td align='middle' class='indicate' width='10'>&nbsp;</td>");
							sbuffer.append("<td align='left' class='indicate' width='10'>&nbsp;</td>");
						}	
						boolean isFirst = true;
						for(int i=0; i<fieldVos.size(); i++){
							FieldVo fieldVo = ((FieldVo)fieldVos.get(i));
							if("false".equalsIgnoreCase(fieldVo.getVisible()))
								continue;
							sbuffer.append("<td id='"+this.getDataset()+"_head_"+fieldVo.getField()+"' class='columnheader' extra='columnheader' name='"+fieldVo.getField()+"' field='"+fieldVo.getField()+"' sortable='"+fieldVo.getSortable()+"' titlt='"+fieldVo.getToolTip()+"'");
							//sbuffer.append(" nowrap align='middle'");
							//sbuffer.append(" nowrap align='left'");
							sbuffer.append(" nowrap align='" + fieldVo.getAlign() + "'");
							//20090424
							if(isFirst){
								sbuffer.append(" style='border-left:0px;'");	
								isFirst = false;
							}
							sbuffer.append(">");
							
							if("select".equalsIgnoreCase(fieldVo.getField())){
							    sbuffer.append("<FONT face=Marlett size=2>a</FONT>");
							}
							else{
								if("true".equalsIgnoreCase(fieldVo.getRequired())){
									sbuffer.append("<span class='require'>*</span>");				
								}
								sbuffer.append(fieldVo.getLabel());
							}
							
							sbuffer.append("</td>");
						}
						sbuffer.append("  </tr>");
						sbuffer.append("</thead>");							
					}
					
					/*���*/						
					if("true".equalsIgnoreCase(this.getShowFooter())){
						sbuffer.append("<tfoot>");
						sbuffer.append("  <tr>");
						if(_showIndicator){
							//sbuffer.append("<td align='middle' class='indicate'>&nbsp;</td>");
							sbuffer.append("<td align='left' class='indicate'>&nbsp;</td>");
							//20090424
						}	
						boolean isFirst = true;
						for(int i=0; i<fieldVos.size(); i++){
							FieldVo fieldVo = ((FieldVo)fieldVos.get(i));
							if("false".equalsIgnoreCase(fieldVo.getVisible()))
								continue;
							sbuffer.append("<td id='"+this.getDataset()+"_footer_"+fieldVo.getField()+"' class='columnfooter' extra='columnfooter' name='"+fieldVo.getField()+"' field='"+fieldVo.getField()+"'");
							//sbuffer.append(" align='middle'");
							//sbuffer.append(" align='left'");
							sbuffer.append(" align='" + fieldVo.getAlign() + "'");
							//20090424
							if(isFirst){
								sbuffer.append(" style='border-left:0px;'");	
								isFirst = false;
							}						
							sbuffer.append(">");
														
							sbuffer.append("</td>");
						}
						sbuffer.append("  </tr>");
						sbuffer.append("</tfoot>");	
					}
					
					/*������*/
					sbuffer.append("<tbody>");
					sbuffer.append("  <tr extra='tablerow'>");
					if(_showIndicator){
						//sbuffer.append("<td class='indicate' align='middle' width='9' isIndicate='true'>&nbsp;</td>");
						sbuffer.append("<td class='indicate' align='left' width='9' isIndicate='true'>&nbsp;</td>");
						//20090424
					}	
					boolean isFirst = true;
					for(int i=0; i<fieldVos.size(); i++){
						FieldVo fieldVo = ((FieldVo)fieldVos.get(i));
						if("false".equalsIgnoreCase(fieldVo.getVisible()))
							continue;
						sbuffer.append("<td id='"+this.getId()+"_"+fieldVo.getField()+"' class='tablecell' extra='tablecell' name='"+fieldVo.getField()+"' field='"+fieldVo.getField()+"' editable='"+fieldVo.getEditable()+"' readOnly='"+fieldVo.getReadOnly()+"' dropDown='"+fieldVo.getDropDown()+"' dataType='string'");
						//sbuffer.append(" valign='center' nowrap align='middle'");
						//sbuffer.append(" valign='center' nowrap align='left'");
						sbuffer.append(" valign='center' nowrap align='" + fieldVo.getAlign()+"'");
						//20090424
						if(isFirst){
							sbuffer.append(" style='border-left:0px;'");	
							isFirst = false;
						}						
						sbuffer.append(">");
						
						if("select".equalsIgnoreCase(fieldVo.getField())){
							sbuffer.append("<input style='height:16px' onclick='return TableCheckbox.onclick();' type='checkbox'>");
						}
						
						sbuffer.append("</td>");
					}
					sbuffer.append("  </tr>");
					sbuffer.append("</tbody>");						
				
					
					sbuffer.append("</table>");
				}			
							
				try{
					String result = sbuffer.toString();
					if(!ContentManage.isSetParentContent(this, result)){
						pageContext.getOut().println(result);	
					}
				}catch(Exception e){
					logger.error("�Զ�����ǩ����ʧ��", e);			
				}
			}
		}
		else{
			
			StringBuffer sbuffer = new StringBuffer();								
			
			sbuffer.append("<table class='datatable' initialized='false' ");
			
			if(this.getId()!=null)
				sbuffer.append(" id='"+this.getId()+"'");
			if(this.getDataset()!=null)
				sbuffer.append(" dataset='"+this.getDataset()+"'");
			if(this.getFields()!=null)
				sbuffer.append(" fields='"+this.getFields()+"'");
			if(this.getReadOnly()!=null)
				sbuffer.append(" readOnly='"+this.getReadOnly()+"'");
			if(this.getEditable()!=null)
				sbuffer.append(" editable='"+this.getEditable()+"'");
			if(this.getShowHeader()!=null)
				sbuffer.append(" showHeader='"+this.getShowHeader()+"'");
			if(this.getShowFooter()!=null)
				sbuffer.append(" showFooter='"+this.getShowFooter()+"'");
			if(this.getShowIndicator()!=null)
				sbuffer.append(" showIndicator='"+this.getShowIndicator()+"'");
			if(this.getHighlightSelection()!=null)
				sbuffer.append(" highlightSelection='"+this.getHighlightSelection()+"'");
			if(this.getMaxRow()!=null)
				sbuffer.append(" maxRow='"+this.getMaxRow()+"'");
			if(this.getSkipRebuild()!=null)
				sbuffer.append(" skipRebuild='"+this.getSkipRebuild()+"'");
			
			sbuffer.append(">");				
			sbuffer.append("</table>");
			
			try{	
				String result = sbuffer.toString();
				if(!ContentManage.isSetParentContent(this, result)){
					pageContext.getOut().println(result);	
				}
			}catch(Exception e){
				logger.error("�Զ�����ǩ����ʧ��", e);			
			}
							
		}
		  

		return super.doEndTag();
	}	
	
	
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.id = null;
		this.dataset = null;
		this.fields = null;
		this.readOnly = null;
		this.editable = null;
		this.showHeader = null;
		this.showFooter = null;
		this.showIndicator = null;
		this.highlightSelection = null;
		this.maxRow = null;
		this.skipRebuild = null;			
		this.scope = null ;
	}


	
}
