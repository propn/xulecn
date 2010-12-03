package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhRemoveCauseDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select remove_cause_id,cust_ord_id,product_offer_instance_id,remove_date,out_cause,out_to from UH_REMOVE_CAUSE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_remove_cause where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_REMOVE_CAUSE (remove_cause_id, cust_ord_id, product_offer_instance_id, remove_date, out_cause, out_to) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update UH_REMOVE_CAUSE set remove_cause_id=?, cust_ord_id=?, product_offer_instance_id=?, remove_date=?, out_cause=?, out_to=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from UH_REMOVE_CAUSE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from UH_REMOVE_CAUSE where remove_cause_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update UH_REMOVE_CAUSE set remove_cause_id=?, cust_ord_id=?, product_offer_instance_id=?, remove_date=?, out_cause=?, out_to=? where remove_cause_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select remove_cause_id,cust_ord_id,product_offer_instance_id,remove_date,out_cause,out_to from UH_REMOVE_CAUSE where remove_cause_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = "ORD" ;


	public UhRemoveCauseDAO() {

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
							
		params.add(map.get("remove_cause_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("product_offer_instance_id")) ;
						
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
				
					
		params.add(vo.get("remove_cause_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("product_offer_instance_id")) ;
						
		
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
							
		params.add(vo.get("remove_cause_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("product_offer_instance_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("remove_date" ))) ;
									
		params.add(vo.get("out_cause")) ;
									
		params.add(vo.get("out_to")) ;
						
							
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
