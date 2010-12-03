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

public class WoServCmdDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select m.command_collect_id,m.template_id,m.seq,t.name template_name from WO_SERVICE_CMD m,NE_COMMAND_TEMPLATE t where m.template_id = t.template_id ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_service_cmd m where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_SERVICE_CMD (command_collect_id, template_id, seq) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_SERVICE_CMD set command_collect_id=?, template_id=?, seq=? where  command_collect_id= ? and template_id = ? ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_SERVICE_CMD where command_collect_id= ? and template_id = ? ";
	
	private String SQL_BY_KEY ="select c.command_collect_id,c.template_id,c.seq,t.name template_name from WO_SERVICE_CMD c,NE_COMMAND_TEMPLATE t where c.template_id = t.template_id and c.command_collect_id= ? and c.template_id = ? ";	
		
	private String SQL_SELECT_COUNT_INFO =" select 1 from WO_SERVICE_CMD where command_collect_id=? ";
	
	private String SQL_VALIDATE_TEMPLATE_ID = "select b.command_collect_id, b.template_id, b.seq from (select command_collect_id, template_id, seq from wo_service_cmd where command_collect_id=?) b where b.template_id=?";
	
	private String SQL_VALIDATE_SEQ = "select b.command_collect_id, b.template_id, b.seq from (select command_collect_id, template_id, seq from wo_service_cmd where command_collect_id=?) b where b.seq=?";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoServCmdDAO() {

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
									
		params.add(map.get("template_id")) ;
									
		params.add(map.get("seq")) ;
						
		return params ;
	}
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("command_collect_id")) ;
		params.add(keyCondMap.get("template_id")) ;
		//params.add(keyCondMap.get("seq")) ;
						
		return params  ;
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
						
					
		params.add(vo.get("template_id")) ;
						
					
		params.add(vo.get("seq")) ;
						
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
	
	public String getSQLValidateTemplateId() {
		return this.SQL_VALIDATE_TEMPLATE_ID;
	}
	
	public String getSQLValidateSeq() {
		return this.SQL_VALIDATE_SEQ;
	}
	
	public boolean updateByMap(Map param) {
		Map keyCondMap = (Map) param.get("WoServCmd");
		Map paraMap = (Map) param.get("paraMap");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setObject(index++,   keyCondMap.get("command_collect_id"));
			stmt.setObject( index++,  keyCondMap.get("template_id"));
			stmt.setObject( index++,  keyCondMap.get("seq"));
			
			stmt.setObject( index++,  paraMap.get("command_collect_id"));
			stmt.setObject( index++,  paraMap.get("template_id"));
			//stmt.setObject( index++,  paraMap.get("seq"));
			
			
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
			stmt.setObject( index++,  paraMap.get("template_id"));
			//stmt.setObject( index++,  paraMap.get("seq"));
			
			
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
