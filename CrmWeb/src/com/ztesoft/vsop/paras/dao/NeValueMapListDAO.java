package com.ztesoft.vsop.paras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.common.util.tracer.Debug;

public class NeValueMapListDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select map_type_id,para_value,map_value from NE_VALUE_MAP_LIST where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_value_map_list where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_VALUE_MAP_LIST (map_type_id, para_value, map_value) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_VALUE_MAP_LIST set map_type_id=?, para_value=?, map_value=? where map_type_id=? and para_value=? and map_value=? ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_VALUE_MAP_LIST where map_type_id=? and para_value=? and map_value=? ";
	
	private String SQL_BY_KEY ="select map_type_id,para_value,map_value from NE_VALUE_MAP_LIST where map_type_id=? and para_value=? and map_value=? ";	

		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeValueMapListDAO() {

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
							
		params.add(map.get("map_type_id")) ;
									
		params.add(map.get("para_value")) ;
									
		params.add(map.get("map_value")) ;
						
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
				
					
		params.add(vo.get("map_type_id")) ;
						
					
		params.add(vo.get("para_value")) ;
						
					
		params.add(vo.get("map_value")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("map_type_id")) ;
		params.add(keyCondMap.get("para_value")) ;
		params.add(keyCondMap.get("map_value")) ;
						
		return params  ;
	}
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
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
		
		return SQL_BY_KEY;
				
	}
	public boolean updateByMap(Map param) {
		Map keyCondMap = (Map) param.get("NeValueMapList");
		Map paraMap = (Map) param.get("paraMap");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setObject(index++,   keyCondMap.get("map_type_id"));
			stmt.setObject( index++,  keyCondMap.get("para_value"));
			stmt.setObject( index++,  keyCondMap.get("map_value"));
			
			stmt.setObject( index++,  paraMap.get("map_type_id"));
			stmt.setObject( index++,  paraMap.get("para_value"));
			stmt.setObject( index++,  paraMap.get("map_value"));
			
			
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_UPDATE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_UPDATE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;	
		}
	public boolean deleteByMap(Map param) {
		Map paraMap = (Map) param.get("paraMap");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_DELETE);
			int index = 1;
			stmt.setObject( index++,  paraMap.get("map_type_id"));
			stmt.setObject( index++,  paraMap.get("para_value"));
			stmt.setObject( index++,  paraMap.get("map_value"));
			
			
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_DELETE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;	
		}
	
}
