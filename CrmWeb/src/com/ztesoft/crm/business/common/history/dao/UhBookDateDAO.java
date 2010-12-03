package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhBookDateDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select book_seq,cust_ord_id,product_offer_instance_id,book_type,time_name_id,book_date,book_date2,book_dow,book_dow2,book_time,book_time2 from UH_BOOK_DATE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_book_date where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_BOOK_DATE (book_seq, cust_ord_id, product_offer_instance_id, book_type, time_name_id, book_date, book_date2, book_dow, book_dow2, book_time, book_time2) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update UH_BOOK_DATE set book_seq=?, cust_ord_id=?, product_offer_instance_id=?, book_type=?, time_name_id=?, book_date=?, book_date2=?, book_dow=?, book_dow2=?, book_time=?, book_time2=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from UH_BOOK_DATE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from UH_BOOK_DATE where book_seq=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update UH_BOOK_DATE set book_seq=?, cust_ord_id=?, product_offer_instance_id=?, book_type=?, time_name_id=?, book_date=?, book_date2=?, book_dow=?, book_dow2=?, book_time=?, book_time2=? where book_seq=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select book_seq,cust_ord_id,product_offer_instance_id,book_type,time_name_id,book_date,book_date2,book_dow,book_dow2,book_time,book_time2 from UH_BOOK_DATE where book_seq=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = "ORD" ;


	public UhBookDateDAO() {

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
							
		params.add(map.get("book_seq")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("product_offer_instance_id")) ;
									
		params.add(map.get("book_type")) ;
									
		params.add(map.get("time_name_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_date2" ))) ;
									
		params.add(map.get("book_dow")) ;
									
		params.add(map.get("book_dow2")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_time2" ))) ;
						
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
				
					
		params.add(vo.get("book_seq")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("product_offer_instance_id")) ;
						
					
		params.add(vo.get("book_type")) ;
						
					
		params.add(vo.get("time_name_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_date2" ))) ;
						
					
		params.add(vo.get("book_dow")) ;
						
					
		params.add(vo.get("book_dow2")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_time2" ))) ;
						
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
							
		params.add(vo.get("book_seq")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("product_offer_instance_id")) ;
									
		params.add(vo.get("book_type")) ;
									
		params.add(vo.get("time_name_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_date2" ))) ;
									
		params.add(vo.get("book_dow")) ;
									
		params.add(vo.get("book_dow2")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_time2" ))) ;
						
							
		params.add(keyCondMap.get("book_seq")) ;
						
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
							
		params.add(keyCondMap.get("book_seq")) ;
						
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
