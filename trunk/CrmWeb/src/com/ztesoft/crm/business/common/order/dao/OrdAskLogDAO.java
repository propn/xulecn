package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdAskLogDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,field_name,field_value,old_field_value from ORD_ASK_LOG where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_ask_log where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_ASK_LOG (ord_item_id, cust_ord_id, field_name, field_value, old_field_value) values (?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_ASK_LOG set ord_item_id=?, cust_ord_id=?, field_name=?, field_value=?, old_field_value=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_ASK_LOG where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_ASK_LOG where ord_item_id=? and field_name=? and field_value=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_ASK_LOG set ord_item_id=?, cust_ord_id=?, field_name=?, field_value=?, old_field_value=? where ord_item_id=? and field_name=? and field_value=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,field_name,field_value,old_field_value from ORD_ASK_LOG where ord_item_id=? and field_name=? and field_value=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdAskLogDAO() {

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
									
		params.add(map.get("field_name")) ;
									
		params.add(map.get("field_value")) ;
									
		params.add(map.get("old_field_value")) ;
						
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
						
					
		params.add(vo.get("field_name")) ;
						
					
		params.add(vo.get("field_value")) ;
						
					
		params.add(vo.get("old_field_value")) ;
						
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
									
		params.add(vo.get("field_name")) ;
									
		params.add(vo.get("field_value")) ;
									
		params.add(vo.get("old_field_value")) ;
						
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("field_name")) ;
									
		params.add(keyCondMap.get("field_value")) ;
						
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
									
		params.add(keyCondMap.get("field_name")) ;
									
		params.add(keyCondMap.get("field_value")) ;
						
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
