package com.ztesoft.crm.business.views.dynamic;

import java.util.ArrayList;
import java.util.Iterator;

import com.ztesoft.component.ui.taglib.DatasetVo;
import com.ztesoft.component.ui.taglib.FieldVo;
import com.ztesoft.component.ui.taglib.FormTag;



public class Form extends FormTag{
	
	private DatasetVo datasetVo;
	
	public Form(DatasetVo _datasetVo){
		this.datasetVo=_datasetVo;
		this.setDataset(datasetVo.getDatasetId());
		this.setType("form");
		this.setLabelLayout("10%");
		this.setInputLayout("14%");
	}
	
	public Form(String type,DatasetVo _datasetVo){
		this(_datasetVo);
		this.setType(type);
	}
	
	public Form(String type,String labelLayout,String inputLayout,DatasetVo _datasetVo){
		this(_datasetVo);
		this.setType(type);
		this.setLabelLayout(inputLayout);
		this.setInputLayout(inputLayout);
	} 
	
	public String getScript() {
		
		return "Form.initial('"+this.getId()+"');";
	}
	
	public String getHTML(){
		
		StringBuffer content=new StringBuffer();
		
		content.append(getBegin()).append(getBody()).append(getEnd());
		
		return content.toString();
	}
	
	//DIV区域，用来控制是否隐藏或显示
	private String getBegin(){
		return "<div id='FormDiv"+this.datasetVo.getDatasetId()+"'>";
	}
	
	private String getEnd(){
		
		return "</div>";
	}
	private String getBody(){	
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
					sbuffer.append("<form id='"+this.getId()+"' class='formLayout3'  onsubmit='return false;' "+_submit+">");
					sbuffer.append(this.createTableLayout(fieldVos));	
					sbuffer.append("</form>");	
				}
				else{						
					sbuffer.append("<form id='"+this.getId()+"' class='formLayout2' onsubmit='return false;' "+_submit+">");
					Iterator iterator = fieldVos.iterator();
					
					//解决页面布局混乱的问题，重新计算输入框的宽度。
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
					//结束
					sbuffer.append("</form>");	
				}
				//sbuffer.append("<script>Form.initial('"+this.getId()+"');</script>");
			}					
		return sbuffer.toString();
	}

	public DatasetVo getDatasetVo() {
		return datasetVo;
	}

	public void setDatasetVo(DatasetVo datasetVo) {
		this.datasetVo = datasetVo;
	}
}
