package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdRemoveCauseDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,remove_cause_id,comp_inst_id,remove_date,out_cause,out_to from ORD_REMOVE_CAUSE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_remove_cause where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_REMOVE_CAUSE (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, remove_cause_id, comp_inst_id, remove_date, out_cause, out_to) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_REMOVE_CAUSE set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, remove_cause_id=?, comp_inst_id=?, remove_date=?, out_cause=?, out_to=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_REMOVE_CAUSE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_REMOVE_CAUSE where ord_item_id=? and remove_cause_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_REMOVE_CAUSE set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, remove_cause_id=?, comp_inst_id=?, remove_date=?, out_cause=?, out_to=? where ord_item_id=? and remove_cause_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,remove_cause_id,comp_inst_id,remove_date,out_cause,out_to from ORD_REMOVE_CAUSE where ord_item_id=? and remove_cause_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdRemoveCauseDAO() {

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
							
		params.add(map.get("ord_item_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("remove_cause_id")) ;
									
		params.add(map.get("comp_inst_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("remove_date" ))) ;
									
		params.add(map.get("out_cause")) ;
									
		params.add(map.get("out_to")) ;
						
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
				
					
		params.add(vo.get("ord_item_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("remove_cause_id")) ;
						
					
		params.add(vo.get("comp_inst_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("remove_date" ))) ;
						
					
		params.add(vo.get("out_cause")) ;
						
					
		params.add(vo.get("out_to")) ;
						
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
							
		params.add(vo.get("ord_item_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("remove_cause_id")) ;
									
		params.add(vo.get("comp_inst_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("remove_date" ))) ;
									
		params.add(vo.get("out_cause")) ;
									
		params.add(vo.get("out_to")) ;
						
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("remove_cause_id")) ;
						
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
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("remove_cause_id")) ;
						
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
