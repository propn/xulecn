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

public class NeCmdTemplateParaDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select p.name,p.para_code,p.get_method,p.is_key,p.node_path,p.node_attr,t.template_id,t.command_id from NE_CMD_TEMPLATE_PARA t,Ne_Command_Para p where t.command_id = p.command_id ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_cmd_template_para t where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_CMD_TEMPLATE_PARA (template_id, command_id, is_key) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_CMD_TEMPLATE_PARA set template_id=?, command_id=?, is_key=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_CMD_TEMPLATE_PARA where template_id=? and command_id=? ";
	
	private String SQL_SELECT_COUNT_INFO =" select 1 from NE_CMD_TEMPLATE_PARA where template_id=? ";
		
	//根据模板标识、数据项标识在ne_cmd_template_para中检查模板输入数据项唯一性
	private String SQL_VALIDATE_TEMP_PARA = "select t.template_id, t.command_id, t.is_key from ne_cmd_template_para t where t.template_id = ? and t.command_id = ?";
		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeCmdTemplateParaDAO() {

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
							
		params.add(map.get("template_id")) ;
									
		params.add(map.get("command_id")) ;
									
		params.add(map.get("is_key")) ;
						
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
				
					
		params.add(vo.get("template_id")) ;
						
					
		params.add(vo.get("command_id")) ;
						
					
		params.add(vo.get("is_key")) ;
						
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
					
		return 	SQL_DELETE;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("template_id")) ;
		params.add(keyCondMap.get("command_id")) ;
						
		return params  ;
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
	public boolean deleteById(Map keyCondMap ) throws FrameException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_DELETE);
			int index = 1;
			stmt.setString( index++,  keyCondMap.get("template_id").toString() );
			stmt.setString( index++,  keyCondMap.get("command_id").toString() );
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
	
	public String getSqlForValidateTemplatePara() {
		return this.SQL_VALIDATE_TEMP_PARA;
	}
}
