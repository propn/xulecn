package com.ztesoft.vsop.paras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class NeTranRuleDAO extends AbstractDAO {
	

	//查询SQL
	private String SQL_SELECT = "select r.tran_rule_id, r.business_obj_id, r.id, r.map_type_id, r.int_sys_id,r.name, r.get_method,r.execute_sql,r.create_date,o.business_obj_name,m.name map_type_name,e.name engine_name "+
						"  from NE_TRAN_RULE r left join PB_BUSINESS_OBJ o on r.business_obj_id = o.business_obj_id left join NE_PARA_MAP_TYPE m on r.map_type_id= m.map_type_id left join NE_JAVA_ENGINE e on r.id = e.id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_tran_rule r where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_TRAN_RULE (tran_rule_id, business_obj_id, id, map_type_id, int_sys_id, name, get_method, execute_sql, create_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_TRAN_RULE set tran_rule_id=?, business_obj_id=?, id=?, map_type_id=?, int_sys_id=?, name=?, get_method=?, execute_sql=?, create_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_TRAN_RULE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from NE_TRAN_RULE where tran_rule_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update NE_TRAN_RULE set tran_rule_id=?, business_obj_id=?, id=?, map_type_id=?, int_sys_id=?, name=?, get_method=?, execute_sql=?, create_date=? where tran_rule_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select r.tran_rule_id, r.business_obj_id, r.id, r.map_type_id, r.int_sys_id,r.name, r.get_method,r.execute_sql,r.create_date,o.business_obj_name,m.name map_type_name,e.name engine_name "+
			"  from NE_TRAN_RULE r left join PB_BUSINESS_OBJ o on r.business_obj_id = o.business_obj_id left join NE_PARA_MAP_TYPE m on r.map_type_id= m.map_type_id left join NE_JAVA_ENGINE e on r.id = e.id where r.tran_rule_id=? ";
	private String SQL_COUNT_COMMAND_PARA = "select name from NE_COMMAND_PARA where tran_rule_id=? ";
	private String SQL_DELETE_COMMAND_PARA = "delete from NE_COMMAND_PARA where tran_rule_id=? ";
	
	private String SQL_COUNT_RULE_PARA = "select 1 from NE_TRAN_RULE_PARA where tran_rule_id=? ";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeTranRuleDAO() {

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
							
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		params.add(seqDao.getNextSequence("Ne_Tran_Rule_Seq")) ;
									
		params.add(map.get("business_obj_id")) ;
									
		params.add(map.get("id")) ;
									
		params.add(map.get("map_type_id")) ;
									
		params.add(map.get("int_sys_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("get_method")) ;
									
		params.add(map.get("execute_sql")) ;
						
		params.add(DAOUtils.getCurrentDate()) ;
						
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
				
					
		params.add(vo.get("tran_rule_id")) ;
						
					
		params.add(vo.get("business_obj_id")) ;
						
					
		params.add(vo.get("id")) ;
						
					
		params.add(vo.get("map_type_id")) ;
						
					
		params.add(vo.get("int_sys_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("get_method")) ;
						
					
		params.add(vo.get("execute_sql")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
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
							
		params.add(vo.get("tran_rule_id")) ;
									
		params.add(vo.get("business_obj_id")) ;
									
		params.add(vo.get("id")) ;
									
		params.add(vo.get("map_type_id")) ;
									
		params.add(vo.get("int_sys_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("get_method")) ;
									
		params.add(vo.get("execute_sql")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
							
		params.add(keyCondMap.get("tran_rule_id")) ;
						
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
							
		params.add(keyCondMap.get("tran_rule_id")) ;
						
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


	public boolean deleteById(String tran_rule_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_DELETE_COMMAND_PARA);
			stmt.setString( 1,  tran_rule_id );
			stmt.executeUpdate();
			stmt = conn.prepareStatement(SQL_DELETE_KEY);
			stmt.setString( 1,  tran_rule_id );
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE_COMMAND_PARA,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_DELETE_COMMAND_PARA, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}


	public String isRelateByCommPara(String tran_rule_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String name = "";
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_COUNT_COMMAND_PARA);
			int index = 1;
			stmt.setString( index++,  tran_rule_id );
			rs = stmt.executeQuery();
			if(rs.next())
				name = rs.getString("name");
		}
		catch (SQLException se) {
			Debug.print(SQL_COUNT_COMMAND_PARA,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_COUNT_COMMAND_PARA, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return name;
	}


	public boolean isRelateByRulePara(String tran_rule_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_COUNT_RULE_PARA);
			int index = 1;
			stmt.setString( index++,  tran_rule_id );
			int rows = stmt.executeQuery().getRow();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_COUNT_RULE_PARA,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_COUNT_RULE_PARA, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	
}
