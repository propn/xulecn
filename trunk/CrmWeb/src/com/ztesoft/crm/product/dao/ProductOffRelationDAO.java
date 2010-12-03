package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductOffRelationDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select relation_id,offer_a_id,offer_z_id,relation_type_id,baseon_type,offer_a_rela_type,offer_z_rela_type,instance_flag,operation_flag,eff_date,exp_date,comp_role_id,role_min_num,role_max_num,relation_desc,time_limit_type,end_time,state,seq,party_id,party_role_id,oper_region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,order_id from PRODUCT_OFFER_RELATION where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_offer_relation where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_OFFER_RELATION (relation_id, offer_a_id, offer_z_id, relation_type_id, baseon_type, offer_a_rela_type, offer_z_rela_type, instance_flag, operation_flag, eff_date, exp_date, comp_role_id, role_min_num, role_max_num, relation_desc, time_limit_type, end_time, state, seq, party_id, party_role_id, oper_region_id, oper_date, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq, order_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_OFFER_RELATION set relation_id=?, offer_a_id=?, offer_z_id=?, relation_type_id=?, baseon_type=?, offer_a_rela_type=?, offer_z_rela_type=?, instance_flag=?, operation_flag=?, eff_date=?, exp_date=?, comp_role_id=?, role_min_num=?, role_max_num=?, relation_desc=?, time_limit_type=?, end_time=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, order_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_OFFER_RELATION where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_OFFER_RELATION where relation_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_OFFER_RELATION set relation_id=?, offer_a_id=?, offer_z_id=?, relation_type_id=?, baseon_type=?, offer_a_rela_type=?, offer_z_rela_type=?, instance_flag=?, operation_flag=?, eff_date=?, exp_date=?, comp_role_id=?, role_min_num=?, role_max_num=?, relation_desc=?, time_limit_type=?, end_time=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, order_id=? where relation_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select relation_id,offer_a_id,offer_z_id,relation_type_id,baseon_type,offer_a_rela_type,offer_z_rela_type,instance_flag,operation_flag,eff_date,exp_date,comp_role_id,role_min_num,role_max_num,relation_desc,time_limit_type,end_time,state,seq,party_id,party_role_id,oper_region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,order_id from PRODUCT_OFFER_RELATION where relation_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductOffRelationDAO() {

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
							
		params.add(map.get("relation_id")) ;
									
		params.add(map.get("offer_a_id")) ;
									
		params.add(map.get("offer_z_id")) ;
									
		params.add(map.get("relation_type_id")) ;
									
		params.add(map.get("baseon_type")) ;
									
		params.add(map.get("offer_a_rela_type")) ;
									
		params.add(map.get("offer_z_rela_type")) ;
									
		params.add(map.get("instance_flag")) ;
									
		params.add(map.get("operation_flag")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("comp_role_id")) ;
									
		params.add(map.get("role_min_num")) ;
									
		params.add(map.get("role_max_num")) ;
									
		params.add(map.get("relation_desc")) ;
									
		params.add(map.get("time_limit_type")) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("cancel_event_seq")) ;
									
		params.add(map.get("order_id")) ;
						
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
				
					
		params.add(vo.get("relation_id")) ;
						
					
		params.add(vo.get("offer_a_id")) ;
						
					
		params.add(vo.get("offer_z_id")) ;
						
					
		params.add(vo.get("relation_type_id")) ;
						
					
		params.add(vo.get("baseon_type")) ;
						
					
		params.add(vo.get("offer_a_rela_type")) ;
						
					
		params.add(vo.get("offer_z_rela_type")) ;
						
					
		params.add(vo.get("instance_flag")) ;
						
					
		params.add(vo.get("operation_flag")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("comp_role_id")) ;
						
					
		params.add(vo.get("role_min_num")) ;
						
					
		params.add(vo.get("role_max_num")) ;
						
					
		params.add(vo.get("relation_desc")) ;
						
					
		params.add(vo.get("time_limit_type")) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("cancel_event_seq")) ;
						
					
		params.add(vo.get("order_id")) ;
						
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
							
		params.add(vo.get("relation_id")) ;
									
		params.add(vo.get("offer_a_id")) ;
									
		params.add(vo.get("offer_z_id")) ;
									
		params.add(vo.get("relation_type_id")) ;
									
		params.add(vo.get("baseon_type")) ;
									
		params.add(vo.get("offer_a_rela_type")) ;
									
		params.add(vo.get("offer_z_rela_type")) ;
									
		params.add(vo.get("instance_flag")) ;
									
		params.add(vo.get("operation_flag")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("comp_role_id")) ;
									
		params.add(vo.get("role_min_num")) ;
									
		params.add(vo.get("role_max_num")) ;
									
		params.add(vo.get("relation_desc")) ;
									
		params.add(vo.get("time_limit_type")) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
									
		params.add(vo.get("order_id")) ;
						
							
		params.add(keyCondMap.get("relation_id")) ;
						
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
							
		params.add(keyCondMap.get("relation_id")) ;
						
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
