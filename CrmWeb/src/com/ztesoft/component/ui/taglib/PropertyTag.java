package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class PropertyTag extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PropertyTag.class);

	private String dataset;
	private String id;
	private String fields;
	
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		this.dataset = null;
		this.id = null;
		this.fields = null;
	}
	
	public String getDatasource(DatasetVo datasetVo, ArrayList fieldVos){
		
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append("<xml id='__"+datasetVo.getDatasetId()+"_tableForm'>");
		sbuffer.append("<records>");

		if(fieldVos!=null){
			Iterator iterator = fieldVos.iterator();
			while(iterator.hasNext()){
				FieldVo fieldVo = (FieldVo)iterator.next();
				if("true".equalsIgnoreCase(fieldVo.getVisible()))
				  sbuffer.append("<record><new>"+fieldVo.getField()+","+fieldVo.getLabel()+",,</new></record>");
			}
		}					
		
		sbuffer.append("</records>");
		sbuffer.append("</xml>");		
		
		return sbuffer.toString();		
	}
	
    public String getScript(DatasetVo datasetVo, ArrayList fieldVos){

		StringBuffer sbuffer = new StringBuffer();		
		
		sbuffer.append("<script defer>");
		sbuffer.append("  function table_"+datasetVo.getDatasetId()+"_tableForm_onInitCell(table, row, cell, field){");
		sbuffer.append("    if(field.getName()=='propertyValue'){");		
		
		if(fieldVos!=null){
			Iterator iterator = fieldVos.iterator();
			while(iterator.hasNext()){
				FieldVo fieldVo = (FieldVo)iterator.next();
				if("true".equalsIgnoreCase(fieldVo.getVisible()) && !"".equalsIgnoreCase(fieldVo.getDropDown())){
				  sbuffer.append("if(row.record.getValue('propertyId')=='"+fieldVo.getField()+"')");
				  sbuffer.append("  cell.dropDown = '"+fieldVo.getDropDown()+"';");
				}
			}
		}
		
		sbuffer.append("    }");
  	    sbuffer.append("  }");
		sbuffer.append("</script>"); 			
		
		return sbuffer.toString();
    	
    }	
    
    public String getFormDataset(DatasetVo datasetVo, ArrayList fieldVos){

		StringBuffer sbuffer = new StringBuffer();		
		
		sbuffer.append("<dl datasetId='"+datasetVo.getDatasetId()+"_tableForm' relaDataset='"+datasetVo.getDatasetId()+"' editable='true'>");
		sbuffer.append("  <dt field='propertyId' label='���Ա���' visible='false'></dt>");
		sbuffer.append("  <dt field='propertyName' label='��������' readOnly='true'></dt>");
		sbuffer.append("  <dt field='propertyValue' label='����ֵ'></dt>");
		sbuffer.append("</dl>");
		
		return sbuffer.toString();
    	
    }	
    
    public String getFormTable(DatasetVo datasetVo, ArrayList fieldVos){

		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("<div style='display:none'><table class='datatable' dataset='"+datasetVo.getDatasetId()+"' style='visibility:hidden;'></table></div>");
		sbuffer.append("<table class='datatable' dataset='"+datasetVo.getDatasetId()+"_tableForm'></table>");	
		
		return sbuffer.toString();
    	
    }	    
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub

			
		Object ds = pageContext.getAttribute(this.getDataset());
		if(ds!=null && ds instanceof DatasetVo){
			
			DatasetVo datasetVo = (DatasetVo)ds;
			
			if(datasetVo!=null){					
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
				StringBuffer sbuffer = new StringBuffer();
				sbuffer.append(getDatasource(datasetVo, fieldVos));
				sbuffer.append(getScript(datasetVo, fieldVos));
				sbuffer.append(getFormDataset(datasetVo, fieldVos));
				sbuffer.append(getFormTable(datasetVo, fieldVos));
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
		
		return super.doEndTag();
	}
	
	
	
	
}
