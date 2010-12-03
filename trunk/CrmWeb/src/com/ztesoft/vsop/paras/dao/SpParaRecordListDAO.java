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

public class SpParaRecordListDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select p.name,p.para_code,p.get_method,p.is_key,p.node_path,p.node_attr,r.record_id,r.command_id from SP_PARA_RECORD_LIST r,Ne_Command_Para p where r.command_id = p.command_id ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from SP_PARA_RECORD_LIST r,Ne_Command_Para p where r.command_id = p.command_id ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_PARA_RECORD_LIST (record_id, command_id) values (?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_PARA_RECORD_LIST set record_id=?, command_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_PARA_RECORD_LIST where record_id=? and command_id=? ";
	
	private String SQL_CELECT_COUNT_RELATE ="select 1 from SP_PARA_RECORD_LIST where record_id = ?" ;
		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SpParaRecordListDAO() {

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
							
		params.add(map.get("record_id")) ;
									
		params.add(map.get("command_id")) ;
						
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
				
					
		params.add(vo.get("record_id")) ;
						
					
		params.add(vo.get("command_id")) ;
						
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
								
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}


	public boolean deleteById(Map keyCondMap) {
			Connection conn = null;
			PreparedStatement stmt = null;
			try {

				conn = ConnectionContext.getContext().getConnection(dbName);
				stmt = conn.prepareStatement(SQL_DELETE);
				int index = 1;
				stmt.setString( index++,  keyCondMap.get("recordId").toString() );
				stmt.setString( index++,  keyCondMap.get("commandId").toString() );
				int rows = stmt.executeUpdate();
				if(rows>0)
					return true;
			}
			catch (SQLException se) {
				Debug.print(SQL_DELETE,this);
				throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
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
			stmt = conn.prepareStatement(SQL_CELECT_COUNT_RELATE);
			int index = 1;
			stmt.setString( index++,  paraId );
			int rows = stmt.executeQuery().getRow();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_CELECT_COUNT_RELATE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}

}
