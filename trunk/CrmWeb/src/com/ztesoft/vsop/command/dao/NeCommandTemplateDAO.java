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
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class NeCommandTemplateDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select  t.template_id,t.back_template_id,t.name,t.state,t.is_feedback_cmd,t.seq_command_id,t.record_id,t.template_class,t.template_type,t.template_cmd_type,t.template_content,p.name command_name,r.record_name "
			+" from NE_COMMAND_TEMPLATE t left join Ne_Command_Para p on t.seq_command_id = p.command_id  left join SP_PARA_RECORD r on t.record_id = r.record_id where 1=1 ";

	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_command_template t where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_COMMAND_TEMPLATE (template_id, back_template_id, name, state, is_feedback_cmd, seq_command_id, record_id, template_class, template_type, template_cmd_type, template_content) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_COMMAND_TEMPLATE set template_id=?, back_template_id=?, name=?, state=?, is_feedback_cmd=?, seq_command_id=?, record_id=?, template_class=?, template_type=?, template_cmd_type=?, template_content=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_COMMAND_TEMPLATE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from NE_COMMAND_TEMPLATE where template_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update NE_COMMAND_TEMPLATE set template_id=?, back_template_id=?, name=?, state=?, is_feedback_cmd=?, seq_command_id=?, record_id=?, template_class=?, template_type=?, template_cmd_type=?, template_content=? where template_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select  t.template_id,t.back_template_id,t.name,t.state,t.is_feedback_cmd,t.seq_command_id,t.record_id,t.template_class,t.template_type,t.template_cmd_type,t.template_content,p.name command_name,r.record_name "
			+" from NE_COMMAND_TEMPLATE t left join Ne_Command_Para p on t.seq_command_id = p.command_id  left join SP_PARA_RECORD r on t.record_id = r.record_id where template_id=? ";

	
	private String SQL_SELECT_COUNT_INFO = "select c.name from wo_service_cmd t,WO_COMMAND_COLLECT c where t.command_collect_id = c.command_collect_id and t.template_id = ? ";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeCommandTemplateDAO() {

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
		params.add(seqDao.getNextSequence("Ne_Command_Template_Seq")) ;
									
		params.add(map.get("back_template_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("is_feedback_cmd")) ;
									
		params.add(map.get("seq_command_id")) ;
									
		params.add(map.get("record_id")) ;
									
		params.add(map.get("template_class")) ;
									
		params.add(map.get("template_type")) ;
									
		params.add(map.get("template_cmd_type")) ;
									
		params.add(map.get("template_content")) ;
						
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
						
					
		params.add(vo.get("back_template_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("is_feedback_cmd")) ;
						
					
		params.add(vo.get("seq_command_id")) ;
						
					
		params.add(vo.get("record_id")) ;
						
					
		params.add(vo.get("template_class")) ;
						
					
		params.add(vo.get("template_type")) ;
						
					
		params.add(vo.get("template_cmd_type")) ;
						
					
		params.add(vo.get("template_content")) ;
						
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
							
		params.add(vo.get("template_id")) ;
									
		params.add(vo.get("back_template_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("is_feedback_cmd")) ;
									
		params.add(vo.get("seq_command_id")) ;
									
		params.add(vo.get("record_id")) ;
									
		params.add(vo.get("template_class")) ;
									
		params.add(vo.get("template_type")) ;
									
		params.add(vo.get("template_cmd_type")) ;
									
		params.add(vo.get("template_content")) ;
						
							
		params.add(keyCondMap.get("template_id")) ;
						
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
							
		params.add(keyCondMap.get("template_id")) ;
						
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
	public String isRelate(String paraId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String collName = "";
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_SELECT_COUNT_INFO);
			int index = 1;
			stmt.setString( index++,  paraId );
			rs  = stmt.executeQuery();
			if(rs.next()){
				collName = rs.getString("name");
				return collName;
			}
		}
		catch (SQLException se) {
			Debug.print(SQL_SELECT_COUNT_INFO,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return collName;
	}
}
