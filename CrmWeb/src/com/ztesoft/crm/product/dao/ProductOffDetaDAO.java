package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames;

public class ProductOffDetaDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select offer_detail_id,seq,offer_id,comp_role_id,role_min_num,role_max_num,element_type,include_flag,element_id,element_desc,time_limit_type,end_time,object_amount_start,object_amount_end,rela_type,join_max_count,pricing_object_id,send_bill,group_opt,state,order_id,party_id,party_role_id,oper_region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,default_flag,cancel_event_seq from PRODUCT_OFFER_DETAIL where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_offer_detail where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_OFFER_DETAIL (offer_detail_id, seq, offer_id, comp_role_id, role_min_num, role_max_num, element_type, include_flag, element_id, element_desc, time_limit_type, end_time, object_amount_start, object_amount_end, rela_type, join_max_count, pricing_object_id, send_bill, group_opt, state, order_id, party_id, party_role_id, oper_region_id, oper_date, ord_action_type, ord_no, cancel_ord_no, event_seq, default_flag, cancel_event_seq) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_OFFER_DETAIL set offer_detail_id=?, seq=?, offer_id=?, comp_role_id=?, role_min_num=?, role_max_num=?, element_type=?, include_flag=?, element_id=?, element_desc=?, time_limit_type=?, end_time=?, object_amount_start=?, object_amount_end=?, rela_type=?, join_max_count=?, pricing_object_id=?, send_bill=?, group_opt=?, state=?, order_id=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, default_flag=?, cancel_event_seq=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_OFFER_DETAIL where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_OFFER_DETAIL where offer_detail_id=? and seq=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_OFFER_DETAIL set offer_detail_id=?, seq=?, offer_id=?, comp_role_id=?, role_min_num=?, role_max_num=?, element_type=?, include_flag=?, element_id=?, element_desc=?, time_limit_type=?, end_time=?, object_amount_start=?, object_amount_end=?, rela_type=?, join_max_count=?, pricing_object_id=?, send_bill=?, group_opt=?, state=?, order_id=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, default_flag=?, cancel_event_seq=? where offer_detail_id=? and seq=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select offer_detail_id,seq,offer_id,comp_role_id,role_min_num,role_max_num,element_type,include_flag,element_id,element_desc,time_limit_type,end_time,object_amount_start,object_amount_end,rela_type,join_max_count,pricing_object_id,send_bill,group_opt,state,order_id,party_id,party_role_id,oper_region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,default_flag,cancel_event_seq from PRODUCT_OFFER_DETAIL where offer_detail_id=? and seq=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductOffDetaDAO() {

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
							
		params.add(map.get("offer_detail_id")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("offer_id")) ;
									
		params.add(map.get("comp_role_id")) ;
									
		params.add(map.get("role_min_num")) ;
									
		params.add(map.get("role_max_num")) ;
									
		params.add(map.get("element_type")) ;
									
		params.add(map.get("include_flag")) ;
									
		params.add(map.get("element_id")) ;
									
		params.add(map.get("element_desc")) ;
									
		params.add(map.get("time_limit_type")) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("object_amount_start")) ;
									
		params.add(map.get("object_amount_end")) ;
									
		params.add(map.get("rela_type")) ;
									
		params.add(map.get("join_max_count")) ;
									
		params.add(map.get("pricing_object_id")) ;
									
		params.add(map.get("send_bill")) ;
									
		params.add(map.get("group_opt")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("default_flag")) ;
									
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
				
					
		params.add(vo.get("offer_detail_id")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("offer_id")) ;
						
					
		params.add(vo.get("comp_role_id")) ;
						
					
		params.add(vo.get("role_min_num")) ;
						
					
		params.add(vo.get("role_max_num")) ;
						
					
		params.add(vo.get("element_type")) ;
						
					
		params.add(vo.get("include_flag")) ;
						
					
		params.add(vo.get("element_id")) ;
						
					
		params.add(vo.get("element_desc")) ;
						
					
		params.add(vo.get("time_limit_type")) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("object_amount_start")) ;
						
					
		params.add(vo.get("object_amount_end")) ;
						
					
		params.add(vo.get("rela_type")) ;
						
					
		params.add(vo.get("join_max_count")) ;
						
					
		params.add(vo.get("pricing_object_id")) ;
						
					
		params.add(vo.get("send_bill")) ;
						
					
		params.add(vo.get("group_opt")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("default_flag")) ;
						
					
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
							
		params.add(vo.get("offer_detail_id")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("offer_id")) ;
									
		params.add(vo.get("comp_role_id")) ;
									
		params.add(vo.get("role_min_num")) ;
									
		params.add(vo.get("role_max_num")) ;
									
		params.add(vo.get("element_type")) ;
									
		params.add(vo.get("include_flag")) ;
									
		params.add(vo.get("element_id")) ;
									
		params.add(vo.get("element_desc")) ;
									
		params.add(vo.get("time_limit_type")) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("object_amount_start")) ;
									
		params.add(vo.get("object_amount_end")) ;
									
		params.add(vo.get("rela_type")) ;
									
		params.add(vo.get("join_max_count")) ;
									
		params.add(vo.get("pricing_object_id")) ;
									
		params.add(vo.get("send_bill")) ;
									
		params.add(vo.get("group_opt")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("default_flag")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
						
							
		params.add(keyCondMap.get("offer_detail_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
							
		params.add(keyCondMap.get("offer_detail_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
