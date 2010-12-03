package com.ztesoft.component.ui.taglib;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.ztesoft.component.common.staticdata.service.StaticAttrService;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;

//annotate by easonwu 
public class FieldVo {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DatasetTag.class);

	private String field; 

	private String label;

	private String type;

	private String defaultValue;

	private String format;

	private String visible;

	private String sortable;

	private String readOnly;

	private String editable;

	private String required;

	private String editorType;

	private String toolTip;

	private String tag;

	private String popup;

	private String labelLayout;

	private String labelCheckbox;

	private String inputLayout;

	private String textareaLayout;

	private String textarea;

	private String textareaHeight;

	private String validType;

	private String validMsg;

	private String minLen;

	private String maxLen;

	private String password;

	private String keyField;

	private String dropDown;

	private String attrCode;

	private String subList;

	private String subListDataset;

	private String blankValue;

	private String blankId;

	private String maxHeight;

	private String configParams;

	private DatasetVo dataset;

	private String dbField;

	private String dbFieldControl;

	private String dynaControlId;
	
	private PageContext pageContext ;
	
	
	private String align ;
	public String getAlign(){
		return this.align;
	}
	public void setAlign(String align){
		this.align = align;
	}

	
	private String size;
	
    private String popupEnable;
    
    
    private String checkbox;
    
    private String checkboxAttrCode;
    
    private String radiobox;
    
    private String radioboxAttrCode;
    
    private String dataType2 ="" ;
    
    private boolean isResult;
    
    
    //�Ƿ��������button�ı�־����ǰ��һ������һ�� ��������Ϊfield_button1 ��field_button2
    private String twoButtonFlag="false";

    //������������⴦���Ƿ��Ǻ������
    private String tplProdNoFlag = "false";
    
    //������������⴦���ʱ����Ҫ���⴦��
    private String elementEventName;

	/**
	 * @return the twoButtonFlag
	 */
	public String getTwoButtonFlag() {
		return twoButtonFlag;
	}

	/**
	 * @param twoButtonFlag the twoButtonFlag to set
	 */
	public void setTwoButtonFlag(String twoButtonFlag) {
		this.twoButtonFlag = twoButtonFlag;
	}

	public boolean isResult() {
		return isResult;
	}

	public void setResult(boolean isResult) {
		this.isResult = isResult;
	}

	/**
	 * @return Returns the checkboxAttrCode.
	 */
	public String getCheckboxAttrCode() {
		return checkboxAttrCode;
	}

	/**
	 * @param checkboxAttrCode The checkboxAttrCode to set.
	 */
	public void setCheckboxAttrCode(String checkboxAttrCode) {
		this.checkboxAttrCode = checkboxAttrCode;
	}

	/**
	 * @return Returns the radiobox.
	 */
	public String getRadiobox() {
		return radiobox;
	}

	/**
	 * @param radiobox The radiobox to set.
	 */
	public void setRadiobox(String radiobox) {
		this.radiobox = radiobox;
	}

	/**
	 * @return Returns the radioboxAttrCode.
	 */
	public String getRadioboxAttrCode() {
		return radioboxAttrCode;
	}

	/**
	 * @param radioboxAttrCode The radioboxAttrCode to set.
	 */
	public void setRadioboxAttrCode(String radioboxAttrCode) {
		this.radioboxAttrCode = radioboxAttrCode;
	}

	/**
	 * @return Returns the checkbox.
	 */
	public String getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox The checkbox to set.
	 */
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getPopupEnable(){
            return popupEnable;
    }
   
    public void setPopupEnable(String popupEnable){
            this.popupEnable = popupEnable;
    }


	public String getDbFieldControl() {
		return dbFieldControl;
	}

	public void setDbFieldControl(String dbFieldControl) {
		this.dbFieldControl = dbFieldControl;
	}

	public String getDbField() {
		return dbField;
	}

	public void setDbField(String dbField) {
		this.dbField = dbField;
	}

	public DatasetVo getDataset() {
		return dataset;
	}

	public void setDataset(DatasetVo dataset) {
		this.dataset = dataset;
	}

	public FieldVo(String field, String label) {
		super();
		// TODO Auto-generated constructor stub
		this.field = field;
		this.label = label;
		this.defaultValue = "";
		this.format = "";
		this.visible = "true";
		this.sortable = "false";
		this.readOnly = "true";
		this.editable = "false";
		this.required = "false";
		this.editorType = "";
		this.toolTip = "";
		this.tag = "";
		this.popup = "";
		this.labelLayout = "";
		this.inputLayout = "";
		this.textareaLayout = "";
		this.textarea = "";
		this.textareaHeight = "";
		this.validType = "";
		this.validMsg = "";
		this.password = "false";
		this.keyField = "";
		this.dropDown = "";
	}
	public FieldVo(String field, String label, String type, String attrCode) {
		super();
		// TODO Auto-generated constructor stub
		this.field = field;
		this.label = label;
		this.type = type;
		this.attrCode = attrCode;

		this.defaultValue = "";
		this.format = "";
		this.visible = "true";
		this.sortable = "false";
		this.readOnly = "true";
		this.editable = "false";
		this.required = "false";
		this.dropDown = attrCode;
		this.editorType = "";
		this.toolTip = "";
		this.tag = "";
		this.popup = "false";
		this.labelLayout = "";
		this.inputLayout = "";
		this.textareaLayout = "";
		this.textarea = "";
		this.textareaHeight = "";
		this.validType = "";
		this.validMsg = "";
		this.password = "false";
		this.keyField = "";
	}

	public FieldVo(String field, String label, String type, String defaultValue, String format, String visible, String sortable,
			String readOnly, String editable, String required, String dropDown, String editorType, String toolTip, String tag,
			String popup, String labelLayout, String inputLayout, String textareaLayout, String textarea, String textareaHeight,
			String validType, String validMsg, String password, String keyField) {
		super();
		// TODO Auto-generated constructor stub
		this.field = field;
		this.label = label;
		this.type = type;
		this.defaultValue = defaultValue;
		this.format = format;
		this.visible = visible;
		this.sortable = sortable;
		this.readOnly = readOnly;
		this.editable = editable;
		this.required = required;
		this.dropDown = dropDown;
		this.editorType = editorType;
		this.toolTip = toolTip;
		this.tag = tag;
		this.popup = popup;
		this.labelLayout = labelLayout;
		this.inputLayout = inputLayout;
		this.textareaLayout = textareaLayout;
		this.textarea = textarea;
		this.textareaHeight = textareaHeight;
		this.validType = validType;
		this.validMsg = validMsg;
		this.password = password;
		this.keyField = keyField;
	}
	
	/**
	 * 
	 */
	public String toString() {
		pageContext = this.getPageContext();
		
		StringBuffer sbuffer = new StringBuffer();

		String realType = this.getType();
		boolean isDateType = false;
		this.setDataType2(this.getType()) ;
		if ("date".equalsIgnoreCase(this.getType()) || "datetime".equalsIgnoreCase(this.getType())|| "time".equalsIgnoreCase(this.getType()) || "yearmonth".equalsIgnoreCase(this.getType())) {
			isDateType = true;
		}
		
		StringBuffer d = new StringBuffer("{") ;

		MyUtil.wrapFieldAttr(d, "fn", this.getField()  ) ;//fieldName
		MyUtil.wrapFieldAttr(d, "dt", realType ) ;//dataType
		
		if (!"".equalsIgnoreCase(this.getLabel()))//label
			MyUtil.wrapFieldAttr(d, "l", this.getLabel()) ;

		if (!"".equalsIgnoreCase(this.getFormat()))//format
			MyUtil.wrapFieldAttr(d, "f", this.getFormat()) ;

		if (!"true".equalsIgnoreCase(this.getVisible()))//visible
			MyUtil.wrapFieldAttr(d, "v", this.getVisible() , MyUtil.NO_NEED) ;
		

		if (!"false".equalsIgnoreCase(this.getReadOnly()))//readOnlyu
			MyUtil.wrapFieldAttr(d, "r", this.getReadOnly(), MyUtil.NO_NEED) ;

		if (!"false".equalsIgnoreCase(this.getEditable()))//editable
			MyUtil.wrapFieldAttr(d, "e", this.getEditable(), MyUtil.NO_NEED) ;

		if (!"false".equalsIgnoreCase(this.getRequired()))//required
			MyUtil.wrapFieldAttr(d, "req", this.getRequired(), MyUtil.NO_NEED) ;

		if (!"".equalsIgnoreCase(this.getDropDown()))//dropDown
			MyUtil.wrapFieldAttr(d, "dd", this.getDropDown()) ;
		else if (isDateType)
			MyUtil.wrapFieldAttr(d, "dd", "dropDownDate") ;

		if (!"".equalsIgnoreCase(this.getEditorType()))//editorType
			MyUtil.wrapFieldAttr(d, "et", this.getEditorType()) ;

		if ("true".equalsIgnoreCase(this.getRequired()) && (this.getValidType() == null || "".equals(this.getValidType()))) {
			MyUtil.wrapFieldAttr(d, "vt", "required") ;
		}

		if (!"".equalsIgnoreCase(this.getValidType())) {//validType
			MyUtil.wrapFieldAttr(d, "vt", this.getValidType()) ;
		}

		if (!"".equalsIgnoreCase(this.getValidMsg()))//validMsg
			MyUtil.wrapFieldAttr(d, "vm", this.getValidMsg()) ;

		if (this.getDbField() != null && !"".equalsIgnoreCase(this.getDbField()))
			MyUtil.wrapFieldAttr(d, "df", this.getDbField()) ;

		if (!"".equalsIgnoreCase(this.getKeyField()))//keyField
			MyUtil.wrapFieldAttr(d, "kf", this.getKeyField()) ;

		if (!"".equalsIgnoreCase(this.getDefaultValue()))//defaultValue
			MyUtil.wrapFieldAttr(d, "dv", this.getDefaultValue()) ;

		if (this.getSize() != null && !"".equalsIgnoreCase(this.getSize()))//size
			MyUtil.wrapFieldAttr(d, "s", this.getSize()) ;
		
		if (this.getCheckbox() != null && !"".equalsIgnoreCase(this.getCheckbox()))//checkbox
			MyUtil.wrapFieldAttr(d, "cb", this.getCheckbox()) ;
		
		if (this.getRadiobox() != null && !"".equalsIgnoreCase(this.getRadiobox()))//radiobox
			MyUtil.wrapFieldAttr(d, "rb", this.getRadiobox()) ;

		if (this.getCheckboxAttrCode() != null && !"".equalsIgnoreCase(this.getCheckboxAttrCode()))//checkboxAttrCode
			MyUtil.wrapFieldAttr(d, "ca", this.getCheckboxAttrCode()) ;

		if (this.getRadioboxAttrCode() != null && !"".equalsIgnoreCase(this.getRadioboxAttrCode()))//radioboxAttrCode
			MyUtil.wrapFieldAttr(d, "ra", this.getRadioboxAttrCode()) ;
		
		if ( this.getType() != null && !"".equalsIgnoreCase(this.getType()))
			MyUtil.wrapFieldAttr(d, "d2", this.getType()) ;//datatype2

	    d.append("ir:"+isResult) ;//isResult
		
		d.append("}") ;
		
		sbuffer.append(" __t.addField("+d.toString()+"); ");
		
		return sbuffer.toString();
	}

	/*
	 public String toString(){
	 StringBuffer sbuffer = new StringBuffer();
	 
	 sbuffer.append("<dt");
	 
	 sbuffer.append(" field='"+this.getField()+"' ");
	 sbuffer.append(" label='"+this.getLabel()+"' ");
	 
	 if(!"string".equalsIgnoreCase(this.getType()))
	 sbuffer.append(" type='"+this.getType()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getDefaultValue()))
	 sbuffer.append(" defaultValue='"+this.getDefaultValue()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getFormat()))
	 sbuffer.append(" format='"+this.getFormat()+"' ");
	 
	 if(!"true".equalsIgnoreCase(this.getVisible()))
	 sbuffer.append(" visible='"+this.getVisible()+"' ");
	 
	 if(!"false".equalsIgnoreCase(this.getSortable()))
	 sbuffer.append(" sortable='"+this.getSortable()+"' ");
	 
	 if(!"false".equalsIgnoreCase(this.getReadOnly()))
	 sbuffer.append(" readOnly='"+this.getReadOnly()+"' ");
	 
	 if(!"false".equalsIgnoreCase(this.getEditable()))
	 sbuffer.append(" editable='"+this.getEditable()+"' ");
	 
	 if(this.getRequired()!=null)
	 sbuffer.append(" required='"+this.getRequired()+"' ");
	 if("true".equalsIgnoreCase(this.getRequired()) && (this.getValidType()==null || "".equals(this.getValidType()))){
	 sbuffer.append(" validType='required' ");
	 }
	 
	 if(!"".equalsIgnoreCase(this.getDropDown()))
	 sbuffer.append(" dropDown='"+this.getDropDown()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getEditorType()))
	 sbuffer.append(" editorType='"+this.getEditorType()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getToolTip()))
	 sbuffer.append(" toolTip='"+this.getToolTip()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getTag()))
	 sbuffer.append(" tag='"+this.getTag()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getPopup()))
	 sbuffer.append(" popup='"+this.getPopup()+"' ");
	 
	 //		if(!"".equalsIgnoreCase(this.getLabelLayout()))
	 //			sbuffer.append(" labelLayout='"+this.getLabelLayout()+"' ");
	 //		
	 //		if(!"".equalsIgnoreCase(this.getInputLayout()))
	 //			sbuffer.append(" inputLayout='"+this.getInputLayout()+"' ");
	 //		
	 //		if(!"".equalsIgnoreCase(this.getTextareaLayout()))
	 //			sbuffer.append(" textareaLayout='"+this.getTextareaLayout()+"' ");
	 //		
	 //		if(!"".equalsIgnoreCase(this.getTextarea()))
	 //			sbuffer.append(" textarea='"+this.getTextarea()+"' ");
	 //		
	 //		if(!"".equalsIgnoreCase(this.getTextareaHeight()))
	 //			sbuffer.append(" textareaHeight='"+this.getTextareaHeight()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getValidType()))
	 sbuffer.append(" validType='"+this.getValidType()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getValidMsg()))
	 sbuffer.append(" validMsg='"+this.getValidMsg()+"' ");
	 
	 if(this.getMinLen()!=null && !"".equalsIgnoreCase(this.getMinLen()))
	 sbuffer.append(" minLen='"+this.getMinLen()+"' ");
	 if(this.getMaxLen()!=null && !"".equalsIgnoreCase(this.getMaxLen()))
	 sbuffer.append(" maxLen='"+this.getMaxLen()+"' ");
	 
	 if(!"false".equalsIgnoreCase(this.getPassword()))
	 sbuffer.append(" password='"+this.getPassword()+"' ");
	 
	 if(!"".equalsIgnoreCase(this.getKeyField()))
	 sbuffer.append(" keyField='"+this.getKeyField()+"' ");
	 
	 
	 if(!"".equalsIgnoreCase(this.getSize()))
	 sbuffer.append(" size='"+this.getSize()+"' ");
	 
	 sbuffer.append("></dt>");
	 
	 return sbuffer.toString();		
	 }
	 */

	public String getRealLabelLayout(FormTag form) {
		String labelLayout = this.getLabelLayout();
		if ("".equalsIgnoreCase(labelLayout)) {
			labelLayout = form.getLabelLayout();
		}
		return labelLayout;
	}

	public String getRealInputLayout(FormTag form) {
		String inputLayout = this.getInputLayout();
		if ("".equalsIgnoreCase(inputLayout)) {
			inputLayout = form.getInputLayout();
		}
		return inputLayout;
	}

	public String getRealTextareaLayout(FormTag form) {

		String textareaLayout = this.getTextareaLayout();
		if ("".equalsIgnoreCase(textareaLayout)) {
			textareaLayout = form.getTextareaLayout();
		}
		return textareaLayout;

	}

	public String getReadOnlyStyle() {
		String style = "";
		if ("true".equalsIgnoreCase(this.getDataset().getReadOnly()) || "true".equalsIgnoreCase(this.getReadOnly())) {
			style = "color:dimgray;background-color:#E5F3F8;";
		} else {
			style = "color:black;background-color:;";
		}
		return style;
	}

	public String getTableStyle(FormTag form, String type) {
		String style = "";

		if ("label".equalsIgnoreCase(type)) {
			String labelAlign = form.getLabelAlign();
			String labelPadding = form.getLabelPadding();
			String paddingDirect = "";
			if ("left".equalsIgnoreCase(labelAlign)) {
				paddingDirect = "padding-left";
			} else {
				paddingDirect = "padding-right";
			}
			style = "width:100%;" + paddingDirect + ":" + labelPadding + ";";
		} else if ("input".equalsIgnoreCase(type)) {
			style = "width:100%;" + this.getReadOnlyStyle();
		} else if ("textarea".equalsIgnoreCase(type)) {
			String textareaHeight = this.getTextareaHeight();

			style = "width:100%;height:" + textareaHeight + ";" + this.getReadOnlyStyle();
		}

		return style;
	}

	public String getCssStyle(FormTag form, String type) {
		String style = "";

		if ("label".equalsIgnoreCase(type)) {
			String labelAlign = form.getLabelAlign();
			String labelPadding = form.getLabelPadding();
			String paddingDirect = "";
			if ("left".equalsIgnoreCase(labelAlign)) {
				paddingDirect = "padding-left";
			} else {
				paddingDirect = "padding-right";
			}
			style = "float:left;width:" + this.getRealLabelLayout(form) + ";text-align:" + labelAlign + ";" + paddingDirect + ":"
					+ labelPadding + ";";
		} else if ("input".equalsIgnoreCase(type)) {

			style = "float:left;width:" + this.getRealInputLayout(form) + ";" + this.getReadOnlyStyle();
		} else if ("textarea".equalsIgnoreCase(type)) {

			String textareaHeight = this.getTextareaHeight();

			style = "float:left;width:" + this.getRealTextareaLayout(form) + ";height:" + textareaHeight + ";"
					+ this.getReadOnlyStyle();
		}

		return style;
	}

	public String getStyle(FormTag form, String type) {
		if (form.getType() == "table") {
			return this.getTableStyle(form, type);
		} else {
			return this.getCssStyle(form, type);
		}
	}

	public String getAbsoluteReadOnly() {
		String readOnly = "";
		if ("true".equalsIgnoreCase(this.getDataset().getReadOnly()) || "true".equalsIgnoreCase(this.getReadOnly()))
			readOnly = "readOnly='true'";
		return readOnly;
	}

	public String createLabel(FormTag form) {

		if (!"".equalsIgnoreCase(this.getPopup())) {
			StringBuffer sbuffer = new StringBuffer();

			
			
			String style = this.getStyle(form, "label");
			//if("true".equals(this.getTplProdNoFlag())){
				//String width = this.getRealLabelLayout(form);
				//int widthInt = Integer.parseInt(width.substring(0, width.length()-1))+6;
				//style = "float:left;width:"+width+"%;text-align:right;padding-right:0px;";
				//this.setLabelLayout(widthInt+"%");
			//}
			
			
			String disable = "";
			if (this.getDbField() != null) {
				if ("true".equalsIgnoreCase(this.getDataset().isDBFieldReadOnly(this.getDbField(), this.getDbFieldControl()))) {
					disable = "disabled=true";
				}
			}

			sbuffer.append("<label id='label_" + this.getDataset().getDatasetId() + "_" + this.getField() + "' ");

			if ("true".equalsIgnoreCase(this.getTextarea()))
				sbuffer.append(" for='textarea_" + this.getDataset().getDatasetId() + "_" + this.getField() + "' ");
			else
				sbuffer.append(" for='text_" + this.getDataset().getDatasetId() + "_" + this.getField() + "' ");

			sbuffer.append(" style='" + style + "' " + disable + ">");
			
			if("true".equals(this.getTplProdNoFlag())){
				String productNoStyle = this.getStyle(form, "label");
				sbuffer.append(createTplBox("��װ",productNoStyle,"new"));
				sbuffer.append(createTplBox("����",productNoStyle,"old")+"&nbsp;");
				
			}
			
			if (!"table".equalsIgnoreCase(form.getType())) {
				if ("true".equalsIgnoreCase(this.getRequired())) {
					sbuffer.append("<span class='require'>*</span>");
				}
			}

			if (this.getKeyField() != null && !"".equalsIgnoreCase(this.getKeyField())) {      
		        if ("".equalsIgnoreCase(disable))
		          sbuffer.append("<button title='���' class='resetButton' id='button_reset_" + this.getDataset().getDatasetId()
		              + "_" + this.getField() + "' onclick='resetValue(this, \"" + this.getDataset().getDatasetId()
		              + "\", \"" + this.getField() + "\");' style='width:17px;' " + disable + "></button>");
		        else
		          sbuffer.append("<button title='���' class='resetButton_disable' id='button_reset_"
		              + this.getDataset().getDatasetId() + "_" + this.getField() + "' onclick='resetValue(this, \""
		              + this.getDataset().getDatasetId() + "\", \"" + this.getField() + "\");' style='width:17px;' "
		              + disable + "></button>");
		      }	
			ButtonTag button=new ButtonTag();
			button.setPageContext(this.getPageContext());
			sbuffer.append(button.getButtonHTML("button_" + this.getDataset().getDatasetId() + "_" + this.getField(),
					"", this.getLabel(), "", disable,this.elementEventName,this.getDataset().getDatasetId(),this.field));
			
      //���Ӷ�type=form�����ֶ���popup=trueʱ����հ�ť		

      
      sbuffer.append("</label>");
		
			return sbuffer.toString();
		} else {
			return this.createTableLabel(form);
		}

	}

	public String createTableLabel(FormTag form) {
		StringBuffer sbuffer = new StringBuffer();

		String style = this.getStyle(form, "label");

		sbuffer.append("<label id='label_" + this.getDataset().getDatasetId() + "_" + this.getField() + "' class='fieldlabel' ");

		if ("true".equalsIgnoreCase(this.getTextarea()))
			sbuffer.append(" for='textarea_" + this.getDataset().getDatasetId() + "_" + this.getField() + "' ");
		else
			sbuffer.append(" for='text_" + this.getDataset().getDatasetId() + "_" + this.getField() + "' ");

		sbuffer.append(" style='" + style + "'>");

		if (!"table".equalsIgnoreCase(form.getType())) {
			if ("true".equalsIgnoreCase(this.getRequired())) {
				sbuffer.append("<span class='require'>*</span>");
			}
		}

		if ("true".equalsIgnoreCase(this.getLabelCheckbox())) {
			sbuffer.append("<input type='checkbox' id='label_checkbox_" + this.getDataset().getDatasetId() + "_"
					+ this.getField() + "'");
			//sbuffer.append(" onclick='" + this.getDataset().getDatasetId() + ".setFieldReadOnly(\"" + this.getField()
			//		+ "\", !this.checked)'");
			sbuffer.append(" />");
			sbuffer.append("<label class='fieldlabel' hideFocus='true' for='label_checkbox_" + this.getDataset().getDatasetId()
					+ "_" + this.getField() + "'>");
			sbuffer.append(this.getLabel() + form.getLabelIndicator());
			sbuffer.append("</label>");
		} else {
			sbuffer.append(this.getLabel() + form.getLabelIndicator());
		}

		sbuffer.append("</label>");

		return sbuffer.toString();
	}

	public String createInput(FormTag form) {
		
		//�����Ҫ��ǰ������botton����Ҫ��������
		if("true".equals(this.getTwoButtonFlag())){
			//return this.genTwoButtonDeal(form, pageContext);
		}
		//�ؼ�����popup����
		//�ؼ���ʽ��һ��
		StringBuffer sbuffer = new StringBuffer();
		if ("true".equalsIgnoreCase(this.getTextarea())) {
			String style = this.getStyle(form, "textarea");
			sbuffer.append("<textarea id='textarea_" + this.getDataset().getDatasetId() + "_" + this.getField()
					+ "' class='editor' dataset='" + this.getDataset().getDatasetId() + "' field='" + this.getField() + "' "
					+ this.getAbsoluteReadOnly() + " style='" + style + "' ");

			if (this.getValidType() != null && !"".equals(this.getValidType()))
				sbuffer.append(" validType='" + this.getValidType() + "'");
			if (this.getValidMsg() != null && !"".equals(this.getValidMsg()))
				sbuffer.append(" msg='" + this.getValidMsg() + "'");
			if (this.getRequired() != null)
				sbuffer.append(" required='" + this.getRequired() + "' ");
			if ("true".equalsIgnoreCase(this.getRequired()) && (this.getValidType() == null || "".equals(this.getValidType()))) {
				sbuffer.append(" validType='required' ");
			}
			if (this.getMinLen() != null && !"".equals(this.getMinLen())) {
				sbuffer.append(" minLen='" + this.getMinLen() + "' ");
			}
			if (this.getMaxLen() != null && !"".equals(this.getMaxLen())) {
				sbuffer.append(" maxLen='" + this.getMaxLen() + "' ");
			}
			if (this.getSize() != null && !"".equals(this.getSize())) {
				//sbuffer.append(" maxlength='"+this.getSize()+"' ");
			}

			sbuffer.append(" ></textarea>");
		}else if("true".equalsIgnoreCase(this.getCheckbox())){
			
			String style = this.getStyle(form, "label");
			
			
			ArrayList items=new ArrayList();
			try {
				items = (new StaticAttrService()).getStaticAttr(this.getCheckboxAttrCode());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	
			
			if (items.size()>0) {
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {
					StaticAttrVO item = (StaticAttrVO) iterator.next();	
					sbuffer.append(createBox(item,"checkbox",style));
				}
			}
		}else if("true".equalsIgnoreCase(this.getRadiobox())){
			
			String style = this.getStyle(form, "label");
			
			
			ArrayList items=new ArrayList();
			try {
				items = (new StaticAttrService()).getStaticAttr(this.getRadioboxAttrCode());
			} catch (Exception e) {
				
			}
			
			if (items.size()>0) {
				Iterator iterator = items.iterator();
				while (iterator.hasNext()) {

					StaticAttrVO item = (StaticAttrVO) iterator.next();	
					sbuffer.append(createBox(item,"radio",style));

				}
			}
		}else {
			
			String style = this.getStyle(form, "input");
			String type = "text";
			if ("true".equalsIgnoreCase(this.getPassword())) {
				type = "password";
			}
			
			if("BUTTON".equals(this.getType())){
				style+=";display:none";
			}
			String _subList = "";
			if (this.getSubList() != null) {
				if (getSubListDataset() != null) {
					_subList = "subList='text_" + this.getSubListDataset() + "_" + this.getSubList() + "'";
				} else {
					_subList = "subList='text_" + this.getDataset().getDatasetId() + "_" + this.getSubList() + "'";
				}
			}
			sbuffer.append("<input type='" + type + "' id='text_" + this.getDataset().getDatasetId() + "_" + this.getField()
					+ "' class='editor' dataset='" + this.getDataset().getDatasetId() + "' field='" + this.getField() + "' "
					+ this.getAbsoluteReadOnly() + " style='" + style + "' " + _subList + " ");
			//
			
			
			//

			if (this.getValidType() != null && !"".equals(this.getValidType()))
				sbuffer.append(" validType='" + this.getValidType() + "'");
			if (this.getValidMsg() != null && !"".equals(this.getValidMsg()))
				sbuffer.append(" msg='" + this.getValidMsg() + "'");
			if (this.getDropDown() != null && !"".equals(this.getDropDown()))
				sbuffer.append(" dropDown='" + this.getDropDown() + "'");
			if (this.getRequired() != null)
				sbuffer.append(" required='" + this.getRequired() + "' ");
			if ("true".equalsIgnoreCase(this.getRequired()) && (this.getValidType() == null || "".equals(this.getValidType()))) {
				sbuffer.append(" validType='required' ");
			}
			if (this.getMinLen() != null && !"".equals(this.getMinLen())) {
				sbuffer.append(" minLen='" + this.getMinLen() + "' ");
			}
			if (this.getMaxLen() != null && !"".equals(this.getMaxLen())) {
				sbuffer.append(" maxLen='" + this.getMaxLen() + "' ");
			}
			if (this.getPopup() != null && !"".equals(this.getPopup())) {
				sbuffer.append(" popup='" + this.getPopup() + "' ");
			}
			if (this.getDataType2() != null && !"".equals(this.getDataType2())) {
				sbuffer.append(" dataType2='" + this.getDataType2() + "' ");
			}
			sbuffer.append(" />");	
			if("true".equals(this.getTwoButtonFlag())){
				//style = "float:left;width:" + this.getRealInputLayout(form) + ";" + this.getReadOnlyStyle();
				sbuffer.append("<button title='����' class='popupButton' style='float:left;width:2%'"  
									+" id='button_"
									+ this.getDataset().getDatasetId()
									+ "_"
									+ this.getField()
									+"_button1"
									+ "' onclick='Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);' style='width:16px;' "
								    + "></button>");
				
				}
		}

		return sbuffer.toString();
	}

	public String createTplBox(String desc,String style,String redioValue){

		StringBuffer sbuffer=new StringBuffer();
		
		sbuffer.append("<input type='radio' name='radio_" + this.getDataset().getDatasetId() + "_"
				+ this.getField() + "'");
		if("new".equals(redioValue)){
			sbuffer.append(" checked='true'");
		}
		sbuffer.append(" onclick='radio_prodInfo_productNo_onClick(this);' value='"+redioValue+"'");
		sbuffer.append(" />");
		sbuffer.append("<label  for='radio_" + this.getDataset().getDatasetId()
				+ "_" + this.getField() + "'>");
		sbuffer.append(desc);
		sbuffer.append("</label>");
		return sbuffer.toString();
	}
	
	public String createBox(StaticAttrVO item,String type,String style){

		StringBuffer sbuffer=new StringBuffer();
		
		sbuffer.append("<input type='"+type+"' name='"+type+"_" + this.getDataset().getDatasetId() + "_"
				+ this.getField() + "'");
		sbuffer.append(" onclick='setBoxValueToDataset("+this.getDataset().getDatasetId()+",\""+this.getField()+"\",this)' value='"+item.getAttrValue()+"'");
		sbuffer.append(" />");
		sbuffer.append("<label  for='"+type+"_" + this.getDataset().getDatasetId()
				+ "_" + this.getField() + "'>");
		sbuffer.append(item.getAttrValueDesc());
		sbuffer.append("</label>");
		return sbuffer.toString();
	}
	public String createTableInput(FormTag form) {
		//�ؼ���ʽ��һ��		

		if (!"".equalsIgnoreCase(this.getPopup())) {
			StringBuffer sbuffer = new StringBuffer();

			String disable = "";
			if (this.getDbField() != null) {
				if ("true".equalsIgnoreCase(this.getDataset().isDBFieldReadOnly(this.getDbField(), this.getDbFieldControl()))) {
					disable = "disabled=true";
				}
			}
			if ("false".equalsIgnoreCase(this.getPopupEnable()))
                disable = "true"; 

			sbuffer.append("<table cellpadding='0' cellspacing='0' border-collapse='collapse' width='100%'>");
			sbuffer.append("<tr><td>");
			sbuffer.append(this.createInput(form));
			sbuffer.append("</td>");
			sbuffer.append("<td style='width:17px;padding-left:1px;'>");
			if ("".equalsIgnoreCase(disable))
				sbuffer
						.append("<button title='����' class='popupButton' id='button_"
								+ this.getDataset().getDatasetId()
								+ "_"
								+ this.getField()
								+ "' onclick='Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);' style='width:16px;' "
								+ disable + "></button>");
			else
				sbuffer
						.append("<button title='����' class='popupButton_disable' id='button_"
								+ this.getDataset().getDatasetId()
								+ "_"
								+ this.getField()
								+ "' onclick='Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);' style='width:16px;' "
								+ disable + "></button>");
			sbuffer.append("</td>");

			if (this.getKeyField() != null && !"".equalsIgnoreCase(this.getKeyField())) {
				sbuffer.append("<td style='width:16px;'>");
				if ("".equalsIgnoreCase(disable))
					sbuffer.append("<button title='���' class='resetButton' id='button_reset_" + this.getDataset().getDatasetId()
							+ "_" + this.getField() + "' onclick='resetValue(this, \"" + this.getDataset().getDatasetId()
							+ "\", \"" + this.getField() + "\");' style='width:17px;' " + disable + "></button>");
				else
					sbuffer.append("<button title='���' class='resetButton_disable' id='button_reset_"
							+ this.getDataset().getDatasetId() + "_" + this.getField() + "' onclick='resetValue(this, \""
							+ this.getDataset().getDatasetId() + "\", \"" + this.getField() + "\");' style='width:17px;' "
							+ disable + "></button>");
				sbuffer.append("</td>");
			}
			sbuffer.append("</tr>");
			sbuffer.append("</table>");

			return sbuffer.toString();
		} else {
			return this.createInput(form);
		}

	}

	public String toControlString(FormTag form) {

		StringBuffer sbuffer = new StringBuffer();

		if (!("false".equalsIgnoreCase(this.getVisible()))) {
			sbuffer.append(this.createLabel(form));
			sbuffer.append(this.createInput(form));
		}
		return sbuffer.toString();
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDropDown() {
		return dropDown;
	}

	public void setDropDown(String dropDown) {
		this.dropDown = dropDown;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getEditorType() {
		return editorType;
	}

	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getInputLayout() {
		return inputLayout;
	}

	public void setInputLayout(String inputLayout) {
		this.inputLayout = inputLayout;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabelLayout() {
		return labelLayout;
	}

	public void setLabelLayout(String labelLayout) {
		this.labelLayout = labelLayout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPopup() {
		return popup;
	}

	public void setPopup(String popup) {
		this.popup = popup;
	}

	public String getReadOnly() {
		if (!"true".equalsIgnoreCase(readOnly)) {
			if (this.getDbField() != null) {
				readOnly = this.getDataset().isDBFieldReadOnly(this.getDbField(), this.getDbFieldControl());
				//logger.debug(this.getDbField()+"*********"+readOnly);
				if ("reverse".equalsIgnoreCase(this.getDbFieldControl())) {
					if ("true".equalsIgnoreCase(readOnly))
						readOnly = "false";
					else
						readOnly = "true";
				}
			}
		}
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTextarea() {
		return textarea;
	}

	public void setTextarea(String textarea) {
		this.textarea = textarea;
	}

	public String getTextareaHeight() {
		return textareaHeight;
	}

	public void setTextareaHeight(String textareaHeight) {
		this.textareaHeight = textareaHeight;
	}

	public String getTextareaLayout() {
		return textareaLayout;
	}

	public void setTextareaLayout(String textareaLayout) {
		this.textareaLayout = textareaLayout;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidMsg() {
		return validMsg;
	}

	public void setValidMsg(String validMsg) {
		this.validMsg = validMsg;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public String getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(String maxLen) {
		this.maxLen = maxLen;
	}

	public String getMinLen() {
		return minLen;
	}

	public void setMinLen(String minLen) {
		this.minLen = minLen;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getSubList() {
		return subList;
	}

	public void setSubList(String subList) {
		this.subList = subList;
	}

	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getLabelCheckbox() {
		return labelCheckbox;
	}

	public void setLabelCheckbox(String labelCheckbox) {
		this.labelCheckbox = labelCheckbox;
	}

	public String getBlankId() {
		return blankId;
	}

	public void setBlankId(String blankId) {
		this.blankId = blankId;
	}

	public String getBlankValue() {
		return blankValue;
	}

	public void setBlankValue(String blankValue) {
		this.blankValue = blankValue;
	}

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getSubListDataset() {
		return subListDataset;
	}

	public void setSubListDataset(String subListDataset) {
		this.subListDataset = subListDataset;
	}

	public String getConfigParams() {
		return configParams;
	}

	public void setConfigParams(String configParams) {
		this.configParams = configParams;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getParentValue(PageContext pageContext) {

		String parentValue = "";

//		if ("LAN_ID".equalsIgnoreCase(this.dbField)) {
//			HttpSession session = ((HttpServletRequest) pageContext.getRequest()).getSession();
//
//			ProductInstallService installService = new ProductInstallService();
//			installService.setSession(session);
//			PdnPdinfoVO pdnPdInfoVO = new PdnPdinfoVO();
//			HashMap map = new HashMap();
//			try {
//				map = installService.getServiceSession().getBusinessMap();
//				if (map.get(Constants.PdnPdInfo) != null) {
//					pdnPdInfoVO = (PdnPdinfoVO) map.get(Constants.PdnPdInfo);
//					pageContext.setAttribute("LAN_ID", pdnPdInfoVO.getLanId());
//					pageContext.setAttribute("BUSINESS_ID", pdnPdInfoVO.getBusinessId());
//					pageContext.setAttribute("DEAL_EXCH_ID", pdnPdInfoVO.getDealExchId());
//					pageContext.setAttribute("MASTER_EXCH_ID", pdnPdInfoVO.getMasterExchId());
//				}
//			} catch (Exception e) {
//				logger.error("Dataset��ǩ�в�ѯ����Ʒʵ����Ϣ����,����ԭ��:" + e);
//			}
//		} else if ("Z_LAN_ID".equalsIgnoreCase(this.dbField)) {
//			HttpSession session = ((HttpServletRequest) pageContext.getRequest()).getSession();
//
//			ProductInstallService installService = new ProductInstallService();
//			installService.setSession(session);
//			PdnSimuSpecialVO pdnSimuSpecialVO = new PdnSimuSpecialVO();
//			HashMap map = new HashMap();
//			try {
//				map = installService.getServiceSession().getBusinessMap();
//				if (map.get("simuSpecialVO") != null) {
//					pdnSimuSpecialVO = (PdnSimuSpecialVO) map.get("simuSpecialVO");
//					pageContext.setAttribute("Z_LAN_ID", pdnSimuSpecialVO.getZLanId());
//					pageContext.setAttribute("Z_BUSINESS_ID", pdnSimuSpecialVO.getZBusinessId());
//					pageContext.setAttribute("Z_DEAL_EXCH_ID", pdnSimuSpecialVO.getZDealExchId());
//					pageContext.setAttribute("Z_MASTER_EXCH_ID", pdnSimuSpecialVO.getZMasterExchId());
//				}
//			} catch (Exception e) {
//				logger.error("Dataset��ǩ�в�ѯ����Ʒʵ����Ϣ����,����ԭ��:" + e);
//			}
//		} else if ("BUSINESS_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("LAN_ID");
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		} else if ("Z_BUSINESS_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("Z_LAN_ID");
//			
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		} else if ("DEAL_EXCH_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("BUSINESS_ID");
//			
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		} else if ("Z_DEAL_EXCH_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("Z_BUSINESS_ID");
//			
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		} else if ("MASTER_EXCH_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("DEAL_EXCH_ID");
//			
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		} else if ("Z_MASTER_EXCH_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("Z_DEAL_EXCH_ID");
//			
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		} else if ("SUB_EXCH_ID".equalsIgnoreCase(this.dbField)) {
//			HttpSession session = ((HttpServletRequest) pageContext.getRequest()).getSession();
//
//			ProductInstallService installService = new ProductInstallService();
//			installService.setSession(session);
//			PdnPdinfoVO pdnPdInfoVO = new PdnPdinfoVO();
//			HashMap map = new HashMap();
//			try {
//				map = installService.getServiceSession().getBusinessMap();
//				if (map.get(Constants.PdnPdInfo) != null) {
//					pdnPdInfoVO = (PdnPdinfoVO) map.get(Constants.PdnPdInfo);
//
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			parentValue = (String) pageContext.getAttribute("MASTER_EXCH_ID");
//			
//			if (parentValue == null || "".equals(parentValue) || "".equals(pdnPdInfoVO.getProdId()))
//				parentValue = "null";
//			//parentValue = "null";
//
//		} else if ("Z_SUB_EXCH_ID".equalsIgnoreCase(this.dbField)) {
//			parentValue = (String) pageContext.getAttribute("Z_MASTER_EXCH_ID");
//			
//			if (parentValue == null || "".equals(parentValue))
//				parentValue = "null";
//			//parentValue = "null";
//		}
//
//		return parentValue;
//	}
//	
//	//��������button�Ĵ���
//	private String genTwoButtonDeal(FormTag form, PageContext pageContext){
//		StringBuffer sbuffer = new StringBuffer();
//
//		String disable = "";
//		if (this.getDbField() != null) {
//			if ("true".equalsIgnoreCase(this.getDataset().isDBFieldReadOnly(this.getDbField(), this.getDbFieldControl()))) {
//				disable = "disabled=true";
//			}
//		}
//		if ("false".equalsIgnoreCase(this.getPopupEnable()))
//            disable = "true"; 
//
//		sbuffer.append("<table cellpadding='0' cellspacing='0' border-collapse='collapse' width='100%'>");
//		sbuffer.append("<tr><td>");
//		sbuffer.append(this.createInput(form));
//		sbuffer.append("</td>");
//		sbuffer.append("<td style='width:17px;padding-left:1px;'>");
//		if ("".equalsIgnoreCase(disable))
//			sbuffer
//					.append("<button title='����' class='popupButton' id='button_"
//							+ this.getDataset().getDatasetId()
//							+ "_"
//							+ this.getField()
//							+"_button1"
//							+ "' onclick='Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);' style='width:16px;' "
//							+ disable + "></button>");
//		else
//			sbuffer
//					.append("<button title='����' class='popupButton_disable' id='button_"
//							+ this.getDataset().getDatasetId()
//							+ "_"
//							+ this.getField()
//							+ "' onclick='Document.fireUserEvent(Document.getElementEventName(this, \"onClick\"), [this]);' style='width:16px;' "
//							+ disable + "></button>");
//		sbuffer.append("</td>");
//
//		if (this.getKeyField() != null && !"".equalsIgnoreCase(this.getKeyField())) {
//			sbuffer.append("<td style='width:16px;'>");
//			if ("".equalsIgnoreCase(disable))
//				sbuffer.append("<button title='���' class='resetButton' id='button_reset_" + this.getDataset().getDatasetId()
//						+ "_" + this.getField() + "' onclick='resetValue(this, \"" + this.getDataset().getDatasetId()
//						+ "\", \"" + this.getField() + "\");' style='width:17px;' " + disable + "></button>");
//			else
//				sbuffer.append("<button title='���' class='resetButton_disable' id='button_reset_"
//						+ this.getDataset().getDatasetId() + "_" + this.getField() + "' onclick='resetValue(this, \""
//						+ this.getDataset().getDatasetId() + "\", \"" + this.getField() + "\");' style='width:17px;' "
//						+ disable + "></button>");
//			sbuffer.append("</td>");
//		}
//		sbuffer.append("</tr>");
//		sbuffer.append("</table>");

//		return sbuffer.toString();
		return null ;
	}
	

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

	/**
	 * @return Returns the pageContext.
	 */
	public PageContext getPageContext() {
		return pageContext;
	}

	/**
	 * @param pageContext The pageContext to set.
	 */
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	/**
	 * @return Returns the dataType2.
	 */
	public String getDataType2() {
		return dataType2;
	}

	/**
	 * @param dataType2 The dataType2 to set.
	 */
	public void setDataType2(String dataType2) {
		this.dataType2 = dataType2;
	}

	/**
	 * @return the tplProdNoFlag
	 */
	public String getTplProdNoFlag() {
		return tplProdNoFlag;
	}

	/**
	 * @param tplProdNoFlag the tplProdNoFlag to set
	 */
	public void setTplProdNoFlag(String tplProdNoFlag) {
		this.tplProdNoFlag = tplProdNoFlag;
	}

	/**
	 * @return the elementEventName
	 */
	public String getElementEventName() {
		return elementEventName;
	}

	/**
	 * @param elementEventName the elementEventName to set
	 */
	public void setElementEventName(String elementEventName) {
		this.elementEventName = elementEventName;
	}



}
