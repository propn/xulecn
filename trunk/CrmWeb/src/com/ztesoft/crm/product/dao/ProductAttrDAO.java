package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductAttrDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select attr_seq,seq,product_id,table_name,attr_value,allow_customized_flag,attr_value_type_id,attr_value_unit_id,attr_character,if_default_value,default_value,order_id,attr_length,is_null,action_id,is_check,is_make_to_table,is_edit,is_make,check_message,attr_format,colspan,page_url,page_field_name,page_field,make_field,param_field,param_field_name,is_send_bill,is_rela_price,is_print,state,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,party_id,party_role_id,oper_region_id,field_name,cname,staticparam_type from PRODUCT_ATTR where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_attr where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_ATTR (attr_seq, seq, product_id,table_name, attr_value, allow_customized_flag, attr_value_type_id, attr_value_unit_id, attr_character, if_default_value, default_value, order_id, attr_length, is_null, action_id, is_check, is_make_to_table, is_edit, is_make, check_message, attr_format, colspan, page_url, page_field_name, page_field, make_field, param_field, param_field_name, is_send_bill, is_rela_price, is_print, state, oper_date, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq, party_id, party_role_id, oper_region_id, field_name,cname,staticparam_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_ATTR set attr_seq=?, seq=?, product_id=?, table_name=?, attr_value=?, allow_customized_flag=?, attr_value_type_id=?, attr_value_unit_id=?, attr_character=?, if_default_value=?, default_value=?, order_id=?, attr_length=?, is_null=?, action_id=?, is_check=?, is_make_to_table=?, is_edit=?, is_make=?, check_message=?, attr_format=?, colspan=?, page_url=?, page_field_name=?, page_field=?, make_field=?, param_field=?, param_field_name=?, is_send_bill=?, is_rela_price=?, is_print=?, state=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, party_id=?, party_role_id=?, oper_region_id=?, field_name=? ,cname=?,staticparam_type=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_ATTR where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_ATTR where attr_seq=? and product_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_ATTR set attr_seq=?, seq=?, product_id=?, table_name=?, attr_value=?, allow_customized_flag=?, attr_value_type_id=?, attr_value_unit_id=?, attr_character=?, if_default_value=?, default_value=?, order_id=?, attr_length=?, is_null=?, action_id=?, is_check=?, is_make_to_table=?, is_edit=?, is_make=?, check_message=?, attr_format=?, colspan=?, page_url=?, page_field_name=?, page_field=?, make_field=?, param_field=?, param_field_name=?, is_send_bill=?, is_rela_price=?, is_print=?, state=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, party_id=?, party_role_id=?, oper_region_id=?, field_name=? ,cname=?,staticparam_type=?  where attr_seq=? and product_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select a.*,b.attr_id,b.attr_desc ATTR_NAME,b.SEND_BILL,b.ATTR_LENGTH, b.input_method,b.value_type" +
			"  from product_attr a, ATTRIBUTE b where a.attr_seq = ? and a.product_id = ? and   a.attr_seq = b.attr_id and b.state <> '00X' and a.state <> '00X'";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductAttrDAO() {

	}
	

	/**
	 * Insert参数设置
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
							
		params.add(map.get("attr_seq")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("product_id")) ;
		
		params.add(map.get("table_name")) ;
									
		params.add(map.get("attr_value")) ;
									
		params.add(map.get("allow_customized_flag")) ;
									
		params.add(map.get("attr_value_type_id")) ;
									
		params.add(map.get("attr_value_unit_id")) ;
									
		params.add(map.get("attr_character")) ;
									
		params.add(map.get("if_default_value")) ;
									
		params.add(map.get("default_value")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("attr_length")) ;
									
		params.add(map.get("is_null")) ;
									
		params.add(map.get("action_id")) ;
									
		params.add(map.get("is_check")) ;
									
		params.add(map.get("is_make_to_table")) ;
									
		params.add(map.get("is_edit")) ;
									
		params.add(map.get("is_make")) ;
									
		params.add(map.get("check_message")) ;
									
		params.add(map.get("attr_format")) ;
									
		params.add(map.get("colspan")) ;
									
		params.add(map.get("page_url")) ;
									
		params.add(map.get("page_field_name")) ;
									
		params.add(map.get("page_field")) ;
									
		params.add(map.get("make_field")) ;
									
		params.add(map.get("param_field")) ;
									
		params.add(map.get("param_field_name")) ;
									
		params.add(map.get("is_send_bill")) ;
									
		params.add(map.get("is_rela_price")) ;
									
		params.add(map.get("is_print")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("cancel_event_seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
									
		params.add(map.get("field_name")) ;
		
		params.add(map.get("cname")) ;
		
		params.add(map.get("staticparam_type")) ;
		
		
						
		return params ;
	}
	

	/**
	 * update 参数设置
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
					
		params.add(vo.get("attr_seq")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("product_id")) ;
		
		params.add(vo.get("table_name")) ;
					
		params.add(vo.get("attr_value")) ;
						
					
		params.add(vo.get("allow_customized_flag")) ;
						
					
		params.add(vo.get("attr_value_type_id")) ;
						
					
		params.add(vo.get("attr_value_unit_id")) ;
						
					
		params.add(vo.get("attr_character")) ;
						
					
		params.add(vo.get("if_default_value")) ;
						
					
		params.add(vo.get("default_value")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("attr_length")) ;
						
					
		params.add(vo.get("is_null")) ;
						
					
		params.add(vo.get("action_id")) ;
						
					
		params.add(vo.get("is_check")) ;
						
					
		params.add(vo.get("is_make_to_table")) ;
						
					
		params.add(vo.get("is_edit")) ;
						
					
		params.add(vo.get("is_make")) ;
						
					
		params.add(vo.get("check_message")) ;
						
					
		params.add(vo.get("attr_format")) ;
						
					
		params.add(vo.get("colspan")) ;
						
					
		params.add(vo.get("page_url")) ;
						
					
		params.add(vo.get("page_field_name")) ;
						
					
		params.add(vo.get("page_field")) ;
						
					
		params.add(vo.get("make_field")) ;
						
					
		params.add(vo.get("param_field")) ;
						
					
		params.add(vo.get("param_field_name")) ;
						
					
		params.add(vo.get("is_send_bill")) ;
						
					
		params.add(vo.get("is_rela_price")) ;
						
					
		params.add(vo.get("is_print")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("cancel_event_seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
					
		params.add(vo.get("field_name")) ;
		
		params.add(vo.get("cname")) ;
		
		
		params.add(vo.get("staticparam_type")) ;
		
		
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * 根据主键更新参数设置
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(vo.get("attr_seq")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("product_id")) ;
		
		params.add(vo.get("table_name")) ;
									
		params.add(vo.get("attr_value")) ;		
									
		params.add(vo.get("allow_customized_flag")) ;
									
		params.add(vo.get("attr_value_type_id")) ;
									
		params.add(vo.get("attr_value_unit_id")) ;
									
		params.add(vo.get("attr_character")) ;
									
		params.add(vo.get("if_default_value")) ;
									
		params.add(vo.get("default_value")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("attr_length")) ;
									
		params.add(vo.get("is_null")) ;
									
		params.add(vo.get("action_id")) ;
									
		params.add(vo.get("is_check")) ;
									
		params.add(vo.get("is_make_to_table")) ;
									
		params.add(vo.get("is_edit")) ;
									
		params.add(vo.get("is_make")) ;
									
		params.add(vo.get("check_message")) ;
									
		params.add(vo.get("attr_format")) ;
									
		params.add(vo.get("colspan")) ;
									
		params.add(vo.get("page_url")) ;
									
		params.add(vo.get("page_field_name")) ;
									
		params.add(vo.get("page_field")) ;
									
		params.add(vo.get("make_field")) ;
									
		params.add(vo.get("param_field")) ;
									
		params.add(vo.get("param_field_name")) ;
									
		params.add(vo.get("is_send_bill")) ;
									
		params.add(vo.get("is_rela_price")) ;
									
		params.add(vo.get("is_print")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
									
		params.add(vo.get("field_name")) ;
		
		params.add(vo.get("cname")) ;
		
		params.add(vo.get("staticparam_type")) ;
		
								
							
		params.add(keyCondMap.get("attr_seq")) ;
									
		params.add(keyCondMap.get("product_id")) ;
						
		return params ;
	}
		
		/**
	 * 主键条件参数设置
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("attr_seq")) ;
									
		params.add(keyCondMap.get("product_id")) ;
						
		return params  ;
	}
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE_KEY ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return this.SQL_UPDATE_KEY ;
				
	}
	
	public String getSelectSQL(){
		return this.SQL_SELECT ;
	}
	
	public String getSelectCountSQL(){
		return this.SQL_SELECT_COUNT ;
	}
	
	public String getInsertSQL(){
		return this.SQL_INSERT ;
	}
	
	public String getUpdateSQL(){
		return this.SQL_UPDATE ;
	}
	
	public String getDeleteSQL(){
		return this.SQL_DELETE ;
	}
	
	public String getSQLSQLByKey() throws FrameException {
					
		return this.SQL_SELECT_KEY ;
				
	}
	
}
