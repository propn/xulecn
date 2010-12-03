package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhAskDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_id,cust_ord_id,same_ask_no,service_offer_id,ord_type,instance_type,instance_type_id,instance_id,solution_id,notes,state,state_reason_id,ask_date,confirm_date,fin_date,beg_time,staff_no,site_no,agreement_id,last_ord_id from UH_ASK where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from UH_ASK where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_ASK (ord_id, cust_ord_id, same_ask_no, service_offer_id, ord_type, instance_type, instance_type_id, instance_id, solution_id, notes, state, state_reason_id, ask_date, confirm_date, fin_date, beg_time, staff_no, site_no, agreement_id, last_ord_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update UH_ASK set ord_id=?, cust_ord_id=?, same_ask_no=?, service_offer_id=?, ord_type=?, instance_type=?, instance_type_id=?, instance_id=?, solution_id=?, notes=?, state=?, state_reason_id=?, ask_date=?, confirm_date=?, fin_date=?, beg_time=?, staff_no=?, site_no=?, agreement_id=?, last_ord_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from UH_ASK where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from UH_ASK where ord_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update UH_ASK set ord_id=?, cust_ord_id=?, same_ask_no=?, service_offer_id=?, ord_type=?, instance_type=?, instance_type_id=?, instance_id=?, solution_id=?, notes=?, state=?, state_reason_id=?, ask_date=?, confirm_date=?, fin_date=?, beg_time=?, staff_no=?, site_no=?, agreement_id=?, last_ord_id=? where ord_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_id,cust_ord_id,same_ask_no,service_offer_id,ord_type,instance_type,instance_type_id,instance_id,solution_id,notes,state,state_reason_id,ask_date,confirm_date,fin_date,beg_time,staff_no,site_no,agreement_id,last_ord_id from UH_ASK where ord_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = "ORD" ;


	public UhAskDAO() {

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
							
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("same_ask_no")) ;
									
		params.add(map.get("service_offer_id")) ;
									
		params.add(map.get("ord_type")) ;
									
		params.add(map.get("instance_type")) ;
									
		params.add(map.get("instance_type_id")) ;
									
		params.add(map.get("instance_id")) ;
									
		params.add(map.get("solution_id")) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("state_reason_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("ask_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("confirm_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("fin_date" ))) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
									
		params.add(map.get("agreement_id")) ;
									
		params.add(map.get("last_ord_id")) ;
						
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
				
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("same_ask_no")) ;
						
					
		params.add(vo.get("service_offer_id")) ;
						
					
		params.add(vo.get("ord_type")) ;
						
					
		params.add(vo.get("instance_type")) ;
						
					
		params.add(vo.get("instance_type_id")) ;
						
					
		params.add(vo.get("instance_id")) ;
						
					
		params.add(vo.get("solution_id")) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("state_reason_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("ask_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("confirm_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("fin_date" ))) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
					
		params.add(vo.get("agreement_id")) ;
						
					
		params.add(vo.get("last_ord_id")) ;
						
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
							
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("same_ask_no")) ;
									
		params.add(vo.get("service_offer_id")) ;
									
		params.add(vo.get("ord_type")) ;
									
		params.add(vo.get("instance_type")) ;
									
		params.add(vo.get("instance_type_id")) ;
									
		params.add(vo.get("instance_id")) ;
									
		params.add(vo.get("solution_id")) ;
									
		params.add(vo.get("notes")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("state_reason_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("ask_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("confirm_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("fin_date" ))) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
									
		params.add(vo.get("agreement_id")) ;
									
		params.add(vo.get("last_ord_id")) ;
						
							
		params.add(keyCondMap.get("ord_id")) ;
						
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
							
		params.add(keyCondMap.get("ord_id")) ;
						
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
