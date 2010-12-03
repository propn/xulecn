package com.ztesoft.crm.business.views.dynamic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.exception.ExceptionVO;
import com.ztesoft.component.common.staticdata.service.StaticAttrService;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
import com.ztesoft.component.ui.taglib.DatasetVo;
import com.ztesoft.component.ui.taglib.FieldVo;
import com.ztesoft.component.ui.taglib.MyUtil;
import com.ztesoft.component.ui.taglib.ParameterVo;
import com.ztesoft.crm.business.views.dao.FieldsDAO;



public class DataSet extends DatasetVo{
	
	public String entryId="";
	public String servId="";
	
	public DataSet(String entryId,String servId){

		this.entryId=entryId;
		this.servId=servId;
		genDataSet();
		genFields();
	}
	
	private void genDataSet(){
		this.setDatasetId("dataset"+servId);
		this.setReadOnly("false");
		this.setStaticDataSource("true");
		this.setEditable("true");
		this.setAutoLoadPage("false");
		this.setAsync("true");
		this.set_queryType("dataset");
		this.setPaginate("false");
		this.setXmlFormat("records");
		this.setLoadAsNeeded("false");
	}
	
	//生成数据集的字段信息
	private void genFields(){
		ArrayList fields = new ArrayList();

		Iterator fieldIt=search().iterator();
		
		while(fieldIt.hasNext()){
			Map fieldsMap=(Map)fieldIt.next();
			genField(fieldsMap,fields);
		}
		this.setFields(fields);
	}
	
	private List search(){
		List whereCondParams=Arrays.asList(new String[]{this.entryId});
		try {
			return new FieldsDAO().findBySql(whereCondParams);
		} catch (FrameException e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}
	/*
	 * 已经支持的字段类型: 98A 单选下拉框 98C 文本输入 98D 日期控件 98E 密码输入 98O 弹出选择 98F
	 * 下拉框,需要根据场景或者套餐数据去过滤该数据
	 * 
	 * 
	 *    98A 单选下拉框
		  98C 文本输入
		  98D 日期控件
		  98E 密码输入
		  98G ip输入
		  98H 后台自动生成
		  98I 整数输入
		  98J 浮点数输入
		  98K 后台序列生成
		  98M 弹出控件 （以上字段暂供销售品属性使用 产品属性待确定）
	 */

	public static final String POP = "98M";

	public static final String DROPDOWN = "98A";

	public static final String INPUT = "98C";

	public static final String DATE = "98D";

	public static final String PASSWORD = "98E";

	public static final String DROPDOWNFILTER = "98F";
	
	public static final String BUTTON = "98X";

	public static final String YES = "T";

	public static final String NO = "F";

	private void genField(Map fieldsMap,
			List fields) {

		/*//fieldVo.setPageContext(pageContext);
			product_id,产品标识
	       attr_id,属性标识
	       a.field_name,字段编码
	       attr_name,属性名称
	       attr_code,属性编码，下拉框取
	       a.attr_length,属性长度
	       is_null,是否允许为空
	       is_check,是否需要验证
	       is_edit,是否允许编辑
	       is_make,是否显示
	       check_message,校验信息
	       colspan,所占几列
	       attr_value_type_id,属性取值类型
	       make_field联动属性值*/
		String is_edit=Const.getStrValue(fieldsMap, "is_edit");// 是否允许编辑
		String is_null=Const.getStrValue(fieldsMap, "is_null");// 是否允许为空
		String is_make=Const.getStrValue(fieldsMap, "is_make");// 是否显示
		String inputType=Const.getStrValue(fieldsMap, "attr_value_type_id");// 属性取值类型
		String attr_code=Const.getStrValue(fieldsMap, "attr_code");// 下拉属性编码
		String make_field=Const.getStrValue(fieldsMap, "make_field");// 子下拉属性编码
		String field_name=Const.getStrValue(fieldsMap, "field_name");// 字段编码
		String attr_name=Const.getStrValue(fieldsMap, "attr_name");// 属性名称
		
		String attr_length=Const.getStrValue(fieldsMap, "attr_length");// 属性长度
		String is_check=Const.getStrValue(fieldsMap, "is_check");// 是否需要验证
		String check_message=Const.getStrValue(fieldsMap, "check_message");// 校验信息
		
		String page_url=Const.getStrValue(fieldsMap, "page_url");// 弹出页面的触发事件
		
		
		FieldVo fieldVo =new FieldVo(field_name,attr_name);
		
		fieldVo.setDataset(this);

		fieldVo.setReadOnly(is_edit.equals(NO) ? "true"
				: "false");// 1是只读，0是非只读
		fieldVo.setRequired(is_null.equals(NO) ? "true"
				: "false");// 1是必须，0是非必须
		fieldVo.setVisible(is_make.equals(YES) ? "true"
				: "false");// 0显示,1不显示
		// fieldVo.setValidMsg(setFieldVo.getValidMsg());

		if (is_null.equals(NO)) {
			fieldVo.setValidType("require");
			fieldVo.setType("string");
			fieldVo.setValidMsg(attr_name+"不能允许为空!");
		}
		if (DATE.equals(inputType)) {
			fieldVo.setType("date");
		} else if (PASSWORD.equals(inputType)) {
			fieldVo.setPassword("true");
			fieldVo.setReadOnly("true");
			fieldVo.setPopup("true");
		} else if (POP.equals(inputType)) {
			fieldVo.setPopup("true");
			fieldVo.setElementEventName(page_url);
			//如果是弹出的则不显示当前的,构造一个名称的field出来
			fieldVo.setVisible("false");
			
		} else if (DROPDOWN.equals(inputType)) {
			fieldVo.setAttrCode(attr_code);
			fieldVo.setDropDown(attr_code);
			fieldVo.setSubList(make_field);
			if (is_null.equals(YES))// 0可空，1非空
				fieldVo.setBlankValue("true");
			else
				fieldVo.setBlankValue("false");
		}else if(DROPDOWNFILTER.equals(inputType)){
			
			fieldVo.setType("specdropdown");
			fieldVo.setAttrCode(attr_code);
			fieldVo.setDropDown(attr_code);
			fieldVo.setSubList(make_field);
			if (is_null.equals(YES))//0可空，1非空
				fieldVo.setBlankValue("true");
			else
				fieldVo.setBlankValue("false");
			
		}
		
		fieldVo.setSize(attr_length);
		if (is_check.equals(YES)) {
			fieldVo.setValidType("require");
			fieldVo.setType("string");
			fieldVo.setValidMsg(check_message);
		}
		
		
		 if (POP.equals(inputType)) {
			 fields.add(clone(fieldVo));
		 }
		
		fields.add(fieldVo);
	}
	
	public FieldVo clone(FieldVo orgfieldVo){
		
		FieldVo fieldVo =new FieldVo(orgfieldVo.getField()+"_name",orgfieldVo.getLabel());
		fieldVo.setVisible("true");
		fieldVo.setDataset(orgfieldVo.getDataset());
		fieldVo.setReadOnly(orgfieldVo.getReadOnly());// 1是只读，0是非只读
		fieldVo.setRequired(orgfieldVo.getRequired());// 1是必须，0是非必须
		fieldVo.setValidType(orgfieldVo.getValidType());
		fieldVo.setType(orgfieldVo.getType());
		fieldVo.setValidMsg(orgfieldVo.getValidMsg());
		fieldVo.setPassword(orgfieldVo.getPassword());
		fieldVo.setPopup(orgfieldVo.getPopup());
		fieldVo.setElementEventName(orgfieldVo.getElementEventName());
		fieldVo.setAttrCode(orgfieldVo.getAttrCode());
		fieldVo.setDropDown(orgfieldVo.getDropDown());
		fieldVo.setSubList(orgfieldVo.getSubList());
		fieldVo.setBlankValue(orgfieldVo.getBlankValue());
		
		return fieldVo;
	}
	
	
	public String getDropDownAttrCode(){
		
		StringBuffer sbuffer = new StringBuffer();

		if(this.getFields()!=null){
			for(int i=0; i<this.getFields().size(); i++){
				FieldVo fieldVo= (FieldVo)this.getFields().get(i);
				String attrCode = fieldVo.getAttrCode();
				if(attrCode!=null && !"".equalsIgnoreCase(attrCode)){
						sbuffer.append(attrCode).append(",");
				}
			}
		}
		return sbuffer.toString();
	}
	
	
	//获取XML数据
	public  String getXml() throws Exception{
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("<xml id='__"+this.getDatasetId()+"'></xml>");
		
		if(this.getFields()!=null){
			for(int i=0; i<this.getFields().size(); i++){
				FieldVo fieldVo= (FieldVo)this.getFields().get(i);
				String attrCode = fieldVo.getAttrCode();
				if(attrCode!=null && !"".equalsIgnoreCase(attrCode)){
						sbuffer.append( this.getDropdownControl(attrCode,fieldVo.getBlankId(), fieldVo.getBlankValue(), fieldVo.getMaxHeight(),fieldVo.getConfigParams()));
				}
			}
		}	
		
		return sbuffer.toString();
	}
	
	
	private static String getDropdownControl(String attrCode,  String blankId, String blankValue, String maxHeight, String configParams) throws Exception{
		if(attrCode==null) return "";
		
		StringBuffer sbuffer = new StringBuffer();
		StringBuffer errbuffer = new StringBuffer();
		CrmException crmEx = null;			
        
		
		sbuffer.append("<xml id='__" + attrCode + "'>");
		sbuffer.append("<items>");
		
		String bak_attrCode = attrCode;

				
		ArrayList items = null;
		try {
			if(!"".equals(attrCode)){


					if( items == null ){
						items = (new StaticAttrService()).getStaticAttr(attrCode);
					}

			}
			
		} catch (Exception e) {
			e.printStackTrace();

			if (e instanceof CrmException) {
				crmEx = (CrmException) e;
				errbuffer.append((new ExceptionVO(crmEx)).toJsObject());
			} else {
				crmEx = new CommonException(new CommonError(CommonError.COMMON_ERROR), e);
				errbuffer.append((new ExceptionVO(crmEx)).toJsObject());
			}
		}
		if (items != null) {

			Iterator iterator = items.iterator();
			while (iterator.hasNext()) {
				StaticAttrVO item = (StaticAttrVO) iterator.next();
				
				sbuffer.append("<item label='" + item.getAttrValueDesc() + "' value='"
						+ item.getAttrValue() + "' valueId='" + item.getAttrValueId() + "'></item>");
			}
		}	
		
		
		

		sbuffer.append("</items>");
		sbuffer.append("</xml>");
		
		sbuffer.append("<CODE id='" + bak_attrCode + "' attrCode='"+attrCode+"'");
		if(blankValue!=null){
			if("true".equalsIgnoreCase(blankValue)){
				sbuffer.append(" blankValue='' blankId='"+(blankId==null?"":blankId)+"'");				
			}else{
				sbuffer.append(" blankValue='"+blankValue+"' blankId='"+(blankId==null?"":blankId)+"'");					
			}							
		}
		if(maxHeight!=null)sbuffer.append(" maxHeight='"+maxHeight+"'");
		if(configParams!=null)sbuffer.append(" configParams='"+configParams+"'");
		
		sbuffer.append("></CODE>");
		if (crmEx != null) {
			sbuffer.append(errbuffer);
		}
		
		return sbuffer.toString();
		
	}
//	获取脚本数据
	public String getScript() throws Exception{

		StringBuffer sbuffer = new StringBuffer();
		
		
		StringBuffer d = new StringBuffer("{") ;
		
		
		MyUtil.wrapFieldAttrWithCheck(d, "t", this.getType() , "reference") ;//type
		
		MyUtil.wrapFieldAttrWithCheck(d, "r", this.getReadOnly() , "false" , MyUtil.NO_NEED) ;//readOnly
		
		MyUtil.wrapFieldAttrWithCheck(d, "e", this.getEditable() , "false" , MyUtil.NO_NEED) ;//editable
		
		MyUtil.wrapFieldAttrWithCheck(d, "al", this.getAutoLoadPage() , "false" , MyUtil.NO_NEED) ;//autoLoadPage
		
		MyUtil.wrapFieldAttrWithCheck(d, "ps", this.getPageSize() , "9999" , MyUtil.NO_NEED) ;//pageSize
		
		MyUtil.wrapFieldAttrWithCheck(d, "pi", this.getPageIndex() , "1" , MyUtil.NO_NEED) ;//pageIndex
		
		MyUtil.wrapFieldAttrWithCheck(d, "pc", this.getPageCount() , "1" , MyUtil.NO_NEED) ;//pageCount
		
		MyUtil.wrapFieldAttrWithCheck(d, "rc", this.getRecordCount() , "0" , MyUtil.NO_NEED) ;//recordCount
		
		MyUtil.wrapFieldAttrWithCheck(d, "async", this.getAsync() , "true" , MyUtil.NO_NEED) ;//async
		
		MyUtil.wrapFieldAttrWithCheck(d, "la", this.getLoadDataAction() , "" ) ;//loadDataAction
		
		
		MyUtil.wrapFieldAttrWithCheck(d, "lm", this.getLoadDataActionMethod() , "") ;//loadDataActionMethod
		
		MyUtil.wrapFieldAttrWithCheck(d, "qt", this.get_queryType() , "dataset" ) ;//queryType
		
		MyUtil.wrapFieldAttrWithCheck(d, "p", this.getPaginate() , "false" , MyUtil.NO_NEED) ;//paginate
		
		MyUtil.wrapFieldAttrWithCheck(d, "sd", this.getStaticDataSource() , "true" , MyUtil.NO_NEED) ;//staticDataSource
		
		MyUtil.wrapFieldAttrWithCheck(d, "x", this.getXmlFormat() , "records") ;//xmlFormat
		
		MyUtil.wrapFieldAttrWithCheck(d, "ln", this.getLoadAsNeeded() , "false" , MyUtil.NO_NEED) ;//loadAsNeeded
		
		MyUtil.wrapFieldAttrWithCheck(d, "md", this.getMasterDataset() , "") ;//masterDataset
		
		MyUtil.wrapFieldAttrWithCheck(d, "mf", this.getMasterKeyFields() , "" ) ;//masterKeyFields
		
		MyUtil.wrapFieldAttrWithCheck(d, "df", this.getDetailKeyFields() , "") ;//detailKeyFields
		
		MyUtil.wrapFieldAttrWithCheck(d, "mc", this.getMaskControl() , "false" , MyUtil.NO_NEED) ;//maskControl
		
		
		d.append("id:'"+this.getDatasetId()+"'}" );
		
		sbuffer.append(" var __t=createDataset("+d.toString()+"); ");
		
		
		sbuffer.append(" "+this.getDatasetId()+"=__t; ");		
		
		if(this.getFields()!=null){
			for(int i=0; i<this.getFields().size(); i++){
				sbuffer.append( ((FieldVo)this.getFields().get(i)).toString() ); 			
			}
		}
		if(this.getParameters()!=null){
			for(int i=0; i<this.getParameters().size(); i++){
				sbuffer.append( ((ParameterVo)this.getParameters().get(i)).toString() ); 			
			}	
		}		
		
		sbuffer.append(" fd(__t , __t.masterDataset); ");    
	    
		return sbuffer.toString();
		
	}

}
