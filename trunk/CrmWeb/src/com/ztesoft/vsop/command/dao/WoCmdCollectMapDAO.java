package com.ztesoft.vsop.command.dao;

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
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class WoCmdCollectMapDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select m.command_collect_id,m.value,m.command_id,m.order_type_id,m.order_act_type,o.order_type_name from WO_CMD_COLLECT_MAP m , SP_OUT_ORDER_TYPE o where  m.order_type_id = o.out_order_type_id ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_cmd_collect_map m where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_CMD_COLLECT_MAP (command_collect_id, value, command_id, order_type_id, order_act_type) values (?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_CMD_COLLECT_MAP set  command_collect_id=?, value=?,  order_type_id=?, order_act_type=? ,command_id=? where  command_collect_id= ? and order_type_id = ? ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_CMD_COLLECT_MAP where  command_collect_id= ? and order_type_id = ? ";
	
	private String SQL_BY_KEY ="select command_collect_id,value,command_id,order_type_id,order_act_type,o.order_type_name from WO_CMD_COLLECT_MAP , SP_OUT_ORDER_TYPE o where order_type_id = o.out_order_type_id and command_collect_id= ? and order_type_id = ?  ";
	
	private String SQL_SELECT_COUNT_INFO =" select * from WO_CMD_COLLECT_MAP where command_collect_id=? ";
	
	private String SQL_VALIDATE_ORDER_TYPE = "select t.order_type_id,t.order_act_type from wo_cmd_collect_map t where t.command_collect_id = ? and t.order_type_id = ?";
	
	private String SQL_VALIDATE_ACT_TYPE = "select t.order_type_id,t.order_act_type from wo_cmd_collect_map t where t.command_collect_id = ? and t.order_act_type = ?";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoCmdCollectMapDAO() {

	}
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("command_collect_id")) ;
		params.add(keyCondMap.get("order_type_id")) ;
		//params.add(keyCondMap.get("order_act_type")) ;
						
		return params  ;
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
							
		params.add(map.get("command_collect_id")) ;
									
		params.add(map.get("value")) ;
									
		params.add(map.get("command_id")) ;
									
		params.add(map.get("order_type_id")) ;
									
		params.add(map.get("order_act_type")) ;
						
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
				
					
		params.add(vo.get("command_collect_id")) ;
						
					
		params.add(vo.get("value")) ;
						
					
		params.add(vo.get("command_id")) ;
						
					
		params.add(vo.get("order_type_id")) ;
						
					
		params.add(vo.get("order_act_type")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
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

	public String getSqlForValidateOrderType() {
		return this.SQL_VALIDATE_ORDER_TYPE;
	}
	
	public String getSqlForValidateOrderActType() {
		return this.SQL_VALIDATE_ACT_TYPE;
	}
	
	
	public boolean updateByMap(Map param) {
		Map keyCondMap = (Map) param.get("WoCmdCollectMap");
		Map paraMap = (Map) param.get("paraMap");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			
		
			stmt.setObject(index++,   keyCondMap.get("command_collect_id"));
			stmt.setObject( index++,  keyCondMap.get("value"));
			stmt.setObject( index++,  keyCondMap.get("order_type_id"));
			stmt.setObject( index++,  keyCondMap.get("order_act_type"));
			stmt.setObject( index++,  paraMap.get("command_collect_id"));
			stmt.setObject( index++,  paraMap.get("order_type_id"));
			//stmt.setObject( index++,  paraMap.get("order_act_type"));
			//Object command_id=keyCondMap.get("command_id");
			
			stmt.setObject( index++,  keyCondMap.get("command_id"));
			
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
			stmt.setObject( index++,  paraMap.get("command_collect_id"));
			stmt.setObject( index++,  paraMap.get("order_type_id"));
			//stmt.setObject( index++,  paraMap.get("order_act_type"));
			
			
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
	public boolean isRelate(String paraId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_SELECT_COUNT_INFO);
			int index = 1;
			stmt.setString( index++,  paraId );
			//informix不支持executeUpdate()返回统计数； 徐雷
			ResultSet rst= stmt.executeQuery();
			 while(rst.next()){
				 int rows = rst.getInt(1);
     			 if(rows>0)
     				return true;
			 }
		}
		catch (SQLException se) {
			Debug.print(SQL_SELECT_COUNT_INFO,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
}
