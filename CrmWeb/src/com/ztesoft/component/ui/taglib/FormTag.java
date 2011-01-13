package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class FormTag extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FormTag.class);

	private String id;
	private String type;
	private String dataset;
	private String fields;
	
	private String labelLayout;
	private String inputLayout;
	private String textareaLayout;
	private String labelAlign;
	private String labelPadding;
	private String labelIndicator;
	private String submit;
	
	private String scope ;
	
	private static String scopeRequest = "request" ;
	
	public int getTagScope() {
		if(scopeRequest.equals(scope))
			return PageContext.REQUEST_SCOPE;
		
		return PageContext.PAGE_SCOPE;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getLabelIndicator() {
		return (labelIndicator!=null ? labelIndicator : "��");
	}
	public void setLabelIndicator(String labelIndicator) {
		this.labelIndicator = labelIndicator;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getId() {
		return (id!=null ? id : "form_"+this.getDataset());
	}
	public void setId(String id) {
		this.id = id;
	}

	public int doEndTag() throws JspException {
			
		Object ds = pageContext.getAttribute(this.getDataset() , getTagScope());

//		System.out.println("xx============"+(getTagScope()==PageContext.REQUEST_SCOPE)+">>>"+(ds!=null && ds instanceof DatasetVo)) ;
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
				
					String _submit = "";
					if(this.getSubmit()!=null){
						_submit = "submit='"+this.getSubmit()+"'";
					}
					else{
						_submit = "submit=''";							
					}
					
					if(this.getType()=="table"){
						sbuffer.append("<form id='"+this.getId()+"' class='formLayout3' onsubmit='return false;' "+_submit+">");
						sbuffer.append(this.createTableLayout(fieldVos));	
						sbuffer.append("</form>");	
					}
					else{						
						sbuffer.append("<form id='"+this.getId()+"' class='formLayout2' onsubmit='return false;' "+_submit+">");
						Iterator iterator = fieldVos.iterator();
						
						//���ҳ�沼�ֻ��ҵ����⣬���¼��������Ŀ�ȡ�
						int increasing = 0;
						int __labelLayout = getRealWidth(this.getLabelLayout());
						int __inputLayout = getRealWidth(this.getInputLayout());		
						int trWith=0;
						while(true){			
							if(trWith+__labelLayout+__inputLayout>100){
								break;
							}
							trWith=trWith+__labelLayout+__inputLayout;
						}	
						int currentLabelWith=0,currentInputWith=0;					
						int left=0;	
						while(iterator.hasNext()){
							FieldVo fieldVo = (FieldVo)iterator.next();	
							if (!("false".equalsIgnoreCase(fieldVo.getVisible()))) {
								currentLabelWith = getRealWidth(fieldVo.getRealLabelLayout(this));
								currentInputWith = getRealWidth(fieldVo.getRealInputLayout(this));
								left=increasing%trWith;
								if(left+currentLabelWith+currentInputWith>trWith){	
									currentInputWith=trWith-left-currentLabelWith;
									fieldVo.setInputLayout(String.valueOf(trWith-left-currentLabelWith)+"%");
								}
								increasing+=currentLabelWith+currentInputWith;
							}
							sbuffer.append(fieldVo.toControlString(this));
						}
						//����
						sbuffer.append("</form>");	
					}
					
					sbuffer.append("<script>Form.initial('"+this.getId()+"');</script>");
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
			
			String _submit = "";
			if(this.getSubmit()!=null){
				_submit = "submit='"+this.getSubmit()+"'";
			}

			sbuffer.append("<form id='"+this.getId()+"' class='autoform' onsubmit='return false;' "+_submit);
			if(this.getType()!=null)
			  sbuffer.append(" type='"+this.getType()+"'");
			if(this.getDataset()!=null)
			  sbuffer.append(" dataset='"+this.getDataset()+"'");
			if(this.getFields()!=null)
			  sbuffer.append(" fields='"+this.getFields()+"'");
			
			if(this.getLabelLayout()!=null)
			    sbuffer.append(" labelLayout='"+this.getLabelLayout()+"'");
			if(this.getInputLayout()!=null)
			    sbuffer.append(" inputLayout='"+this.getInputLayout()+"'");
			if(this.getTextareaLayout()!=null)
				sbuffer.append(" textareaLayout='"+this.getTextareaLayout()+"'");
			if(this.getLabelAlign()!=null)
				sbuffer.append(" labelAlign='"+this.getLabelAlign()+"'");
			if(this.getLabelPadding()!=null)
				sbuffer.append(" labelPadding='"+this.getLabelPadding()+"'");
			if(this.getLabelIndicator()!=null)
				sbuffer.append(" labelIndicator='"+this.getLabelIndicator()+"'");								
			
			sbuffer.append(">");
			sbuffer.append("</form>");	

			sbuffer.append("<script>Form.initial('"+this.getId()+"');</script>");			
						
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
	
	public int getRealWidth(String layout){
		return Integer.parseInt(layout.split("%")[0]);	
	}
	
	public String createTableLayout(ArrayList fieldVos){
		
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append("<table cellspacing='0' cellpadding='0' border-collapse='true' width='100%'>");
		
		int increasing = 0;
		
		int __labelLayout = getRealWidth(this.getLabelLayout());
		int __inputLayout = getRealWidth(this.getInputLayout());
		int maxCols = (100 / (__labelLayout + __inputLayout)) * 3; 
		int currentCols = 0;
		
		Iterator iterator = fieldVos.iterator();
		while(iterator.hasNext()){
			FieldVo fieldVo = (FieldVo)iterator.next();
			
			if("false".equalsIgnoreCase(fieldVo.getVisible())){	
				continue;				
			}
			
			int currentWidth = 0;	
			currentWidth = getRealWidth(fieldVo.getRealLabelLayout(this));			
			
			if(increasing+currentWidth>100){
				currentCols = 0;
				sbuffer.append("</tr>");	
			}
			
			if(increasing==0 || increasing+currentWidth>100){
				sbuffer.append("<tr>");
			}				
			if(increasing+currentWidth>100){
				increasing = currentWidth; 
			}		
			else{
				increasing += currentWidth;
			}				
			
			sbuffer.append("<td style='width:"+fieldVo.getRealLabelLayout(this)+"; text-align:"+this.getLabelAlign()+"; vertical-align: top; padding-top: 2px;'>");
			sbuffer.append(fieldVo.createTableLabel(this));
			sbuffer.append("</td>");
						
			if("true".equalsIgnoreCase(fieldVo.getTextarea())){
				currentWidth = getRealWidth(fieldVo.getRealTextareaLayout(this));			
			}
			else{
				currentWidth = getRealWidth(fieldVo.getRealInputLayout(this));				
			}
			if(currentWidth>100){
				currentWidth = 100;				
			}
			int colspan = 2;			  
			int _labelLayout = getRealWidth(this.getLabelLayout());
			int _inputLayout = getRealWidth(this.getInputLayout());
			int width = _inputLayout;		
			while(width<currentWidth){
				width += _labelLayout+_inputLayout;		      
			    colspan+=3;	    
			}
			colspan--;	
			currentWidth = width;
			
			sbuffer.append("<td colspan='"+colspan+"' style='width:"+currentWidth+"%;'>");
			sbuffer.append(fieldVo.createTableInput(this));
			sbuffer.append("</td>");
			
			sbuffer.append("<td id='_label_"+fieldVo.getDataset().getDatasetId()+"_"+fieldVo.getField()+"'");
			
			if("true".equalsIgnoreCase(fieldVo.getRequired())){
				sbuffer.append(" class='require_td'>");
				sbuffer.append("<span class='require'>*</span>");				
			}	
			else{
				sbuffer.append(" class='not_require_td'>&nbsp;");
			}
			sbuffer.append("</td>");	
			
			increasing += currentWidth;					
			
			if(increasing>100){
				currentCols = 0;
				sbuffer.append("<td width='20'>&nbsp;</td></tr>");				
			}						
			else{
				currentCols += 2+colspan;				
			}
						
		}
		
		int remainCols = maxCols - currentCols;
		if(remainCols>0)
			sbuffer.append("<td width='20' colspan='"+remainCols+"'>&nbsp;</td>");
		sbuffer.append("</tr>");
		sbuffer.append("</table>");
		
		return sbuffer.toString();
	}

	
	public String getInputLayout() {
		return (inputLayout!=null ? inputLayout : "56%");
	}
	public void setInputLayout(String inputLayout) {
		this.inputLayout = inputLayout;
	}
	public String getLabelAlign() {
		return (labelAlign!=null ? labelAlign : "right");
	}
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}
	public String getLabelLayout() {
		return (labelLayout!=null ? labelLayout : "38%");
	}
	public void setLabelLayout(String labelLayout) {
		this.labelLayout = labelLayout;
	}
	public String getLabelPadding() {
		return (labelPadding!=null ? labelPadding : "0px");
	}
	public void setLabelPadding(String labelPadding) {
		this.labelPadding = labelPadding;
	}
	public String getTextareaLayout() {
		return (textareaLayout!=null ? textareaLayout : "57%");
	}
	public void setTextareaLayout(String textareaLayout) {
		this.textareaLayout = textareaLayout;
	}	
	
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.id = null;
		this.dataset = null;
		this.fields = null;
		
		this.labelLayout = null;
		this.inputLayout = null;
		this.textareaLayout = null;
		this.labelAlign = null;
		this.labelPadding = null;
		this.labelIndicator = null;
		this.submit = null;
		this.type = null;
		this.scope = null ;
		
	}
	
	public String getType() {
		return (type!=null ? type : "table");
	}
	public void setType(String type) {
		this.type = type;
	}
	

	
}
