package com.ztesoft.crm.business.common.specs.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames;

public class ProductOffAttrDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select offer_attr_seq,offer_id,offer_attr_value,allow_customized_flag,excluablity,element_type,element_id,attr_value_type_id,attr_value_unit_id,offer_attr_character,if_default_value,order_id,is_make_to_table,is_key,is_make,colspan,staticparam_type,staticparam_table_name,staticparam_field_name,action_id,page_url,page_field_name,page_field,message,recv_message,make_field,param_field,param_field_name,control_field_name,is_send_bill,is_rela_price,extend_a,source_card_type,source_field_name,batch_flag,batch_field,is_print,pricing_param_id,state,seq,party_id,party_role_id,oper_region_id,oper_date,more_flag,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq from PRODUCT_OFFER_ATTR where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_offer_attr where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_OFFER_ATTR (offer_attr_seq, offer_id, offer_attr_value, allow_customized_flag, excluablity, element_type, element_id, attr_value_type_id, attr_value_unit_id, offer_attr_character, if_default_value, order_id, is_make_to_table, is_key, is_make, colspan, staticparam_type, staticparam_table_name, staticparam_field_name, action_id, page_url, page_field_name, page_field, message, recv_message, make_field, param_field, param_field_name, control_field_name, is_send_bill, is_rela_price, extend_a, source_card_type, source_field_name, batch_flag, batch_field, is_print, pricing_param_id, state, seq, party_id, party_role_id, oper_region_id, oper_date, more_flag, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_OFFER_ATTR set offer_attr_seq=?, offer_id=?, offer_attr_value=?, allow_customized_flag=?, excluablity=?, element_type=?, element_id=?, attr_value_type_id=?, attr_value_unit_id=?, offer_attr_character=?, if_default_value=?, order_id=?, is_make_to_table=?, is_key=?, is_make=?, colspan=?, staticparam_type=?, staticparam_table_name=?, staticparam_field_name=?, action_id=?, page_url=?, page_field_name=?, page_field=?, message=?, recv_message=?, make_field=?, param_field=?, param_field_name=?, control_field_name=?, is_send_bill=?, is_rela_price=?, extend_a=?, source_card_type=?, source_field_name=?, batch_flag=?, batch_field=?, is_print=?, pricing_param_id=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, more_flag=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_OFFER_ATTR where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_OFFER_ATTR where offer_attr_seq=? and offer_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_OFFER_ATTR set offer_attr_seq=?, offer_id=?, offer_attr_value=?, allow_customized_flag=?, excluablity=?, element_type=?, element_id=?, attr_value_type_id=?, attr_value_unit_id=?, offer_attr_character=?, if_default_value=?, order_id=?, is_make_to_table=?, is_key=?, is_make=?, colspan=?, staticparam_type=?, staticparam_table_name=?, staticparam_field_name=?, action_id=?, page_url=?, page_field_name=?, page_field=?, message=?, recv_message=?, make_field=?, param_field=?, param_field_name=?, control_field_name=?, is_send_bill=?, is_rela_price=?, extend_a=?, source_card_type=?, source_field_name=?, batch_flag=?, batch_field=?, is_print=?, pricing_param_id=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, more_flag=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=? where offer_attr_seq=? and offer_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select offer_attr_seq,offer_id,offer_attr_value,allow_customized_flag,excluablity,element_type,element_id,attr_value_type_id,attr_value_unit_id,offer_attr_character,if_default_value,order_id,is_make_to_table,is_key,is_make,colspan,staticparam_type,staticparam_table_name,staticparam_field_name,action_id,page_url,page_field_name,page_field,message,recv_message,make_field,param_field,param_field_name,control_field_name,is_send_bill,is_rela_price,extend_a,source_card_type,source_field_name,batch_flag,batch_field,is_print,pricing_param_id,state,seq,party_id,party_role_id,oper_region_id,oper_date,more_flag,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq from PRODUCT_OFFER_ATTR where offer_attr_seq=? and offer_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductOffAttrDAO() {

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
							
		params.add(map.get("offer_attr_seq")) ;
									
		params.add(map.get("offer_id")) ;
									
		params.add(map.get("offer_attr_value")) ;
									
		params.add(map.get("allow_customized_flag")) ;
									
		params.add(map.get("excluablity")) ;
									
		params.add(map.get("element_type")) ;
									
		params.add(map.get("element_id")) ;
									
		params.add(map.get("attr_value_type_id")) ;
									
		params.add(map.get("attr_value_unit_id")) ;
									
		params.add(map.get("offer_attr_character")) ;
									
		params.add(map.get("if_default_value")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("is_make_to_table")) ;
									
		params.add(map.get("is_key")) ;
									
		params.add(map.get("is_make")) ;
									
		params.add(map.get("colspan")) ;
									
		params.add(map.get("staticparam_type")) ;
									
		params.add(map.get("staticparam_table_name")) ;
									
		params.add(map.get("staticparam_field_name")) ;
									
		params.add(map.get("action_id")) ;
									
		params.add(map.get("page_url")) ;
									
		params.add(map.get("page_field_name")) ;
									
		params.add(map.get("page_field")) ;
									
		params.add(map.get("message")) ;
									
		params.add(map.get("recv_message")) ;
									
		params.add(map.get("make_field")) ;
									
		params.add(map.get("param_field")) ;
									
		params.add(map.get("param_field_name")) ;
									
		params.add(map.get("control_field_name")) ;
									
		params.add(map.get("is_send_bill")) ;
									
		params.add(map.get("is_rela_price")) ;
									
		params.add(map.get("extend_a")) ;
									
		params.add(map.get("source_card_type")) ;
									
		params.add(map.get("source_field_name")) ;
									
		params.add(map.get("batch_flag")) ;
									
		params.add(map.get("batch_field")) ;
									
		params.add(map.get("is_print")) ;
									
		params.add(map.get("pricing_param_id")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("more_flag")) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("cancel_event_seq")) ;
						
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
				
					
		params.add(vo.get("offer_attr_seq")) ;
						
					
		params.add(vo.get("offer_id")) ;
						
					
		params.add(vo.get("offer_attr_value")) ;
						
					
		params.add(vo.get("allow_customized_flag")) ;
						
					
		params.add(vo.get("excluablity")) ;
						
					
		params.add(vo.get("element_type")) ;
						
					
		params.add(vo.get("element_id")) ;
						
					
		params.add(vo.get("attr_value_type_id")) ;
						
					
		params.add(vo.get("attr_value_unit_id")) ;
						
					
		params.add(vo.get("offer_attr_character")) ;
						
					
		params.add(vo.get("if_default_value")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("is_make_to_table")) ;
						
					
		params.add(vo.get("is_key")) ;
						
					
		params.add(vo.get("is_make")) ;
						
					
		params.add(vo.get("colspan")) ;
						
					
		params.add(vo.get("staticparam_type")) ;
						
					
		params.add(vo.get("staticparam_table_name")) ;
						
					
		params.add(vo.get("staticparam_field_name")) ;
						
					
		params.add(vo.get("action_id")) ;
						
					
		params.add(vo.get("page_url")) ;
						
					
		params.add(vo.get("page_field_name")) ;
						
					
		params.add(vo.get("page_field")) ;
						
					
		params.add(vo.get("message")) ;
						
					
		params.add(vo.get("recv_message")) ;
						
					
		params.add(vo.get("make_field")) ;
						
					
		params.add(vo.get("param_field")) ;
						
					
		params.add(vo.get("param_field_name")) ;
						
					
		params.add(vo.get("control_field_name")) ;
						
					
		params.add(vo.get("is_send_bill")) ;
						
					
		params.add(vo.get("is_rela_price")) ;
						
					
		params.add(vo.get("extend_a")) ;
						
					
		params.add(vo.get("source_card_type")) ;
						
					
		params.add(vo.get("source_field_name")) ;
						
					
		params.add(vo.get("batch_flag")) ;
						
					
		params.add(vo.get("batch_field")) ;
						
					
		params.add(vo.get("is_print")) ;
						
					
		params.add(vo.get("pricing_param_id")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("more_flag")) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("cancel_event_seq")) ;
						
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
							
		params.add(vo.get("offer_attr_seq")) ;
									
		params.add(vo.get("offer_id")) ;
									
		params.add(vo.get("offer_attr_value")) ;
									
		params.add(vo.get("allow_customized_flag")) ;
									
		params.add(vo.get("excluablity")) ;
									
		params.add(vo.get("element_type")) ;
									
		params.add(vo.get("element_id")) ;
									
		params.add(vo.get("attr_value_type_id")) ;
									
		params.add(vo.get("attr_value_unit_id")) ;
									
		params.add(vo.get("offer_attr_character")) ;
									
		params.add(vo.get("if_default_value")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("is_make_to_table")) ;
									
		params.add(vo.get("is_key")) ;
									
		params.add(vo.get("is_make")) ;
									
		params.add(vo.get("colspan")) ;
									
		params.add(vo.get("staticparam_type")) ;
									
		params.add(vo.get("staticparam_table_name")) ;
									
		params.add(vo.get("staticparam_field_name")) ;
									
		params.add(vo.get("action_id")) ;
									
		params.add(vo.get("page_url")) ;
									
		params.add(vo.get("page_field_name")) ;
									
		params.add(vo.get("page_field")) ;
									
		params.add(vo.get("message")) ;
									
		params.add(vo.get("recv_message")) ;
									
		params.add(vo.get("make_field")) ;
									
		params.add(vo.get("param_field")) ;
									
		params.add(vo.get("param_field_name")) ;
									
		params.add(vo.get("control_field_name")) ;
									
		params.add(vo.get("is_send_bill")) ;
									
		params.add(vo.get("is_rela_price")) ;
									
		params.add(vo.get("extend_a")) ;
									
		params.add(vo.get("source_card_type")) ;
									
		params.add(vo.get("source_field_name")) ;
									
		params.add(vo.get("batch_flag")) ;
									
		params.add(vo.get("batch_field")) ;
									
		params.add(vo.get("is_print")) ;
									
		params.add(vo.get("pricing_param_id")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("more_flag")) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
						
							
		params.add(keyCondMap.get("offer_attr_seq")) ;
									
		params.add(keyCondMap.get("offer_id")) ;
						
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
							
		params.add(keyCondMap.get("offer_attr_seq")) ;
									
		params.add(keyCondMap.get("offer_id")) ;
						
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
