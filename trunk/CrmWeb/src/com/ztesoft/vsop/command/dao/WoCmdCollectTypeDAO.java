package com.ztesoft.vsop.command.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class WoCmdCollectTypeDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select cmd_collect_type_id,name,is_rule,device_id from WO_CMD_COLLECT_TYPE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_cmd_collect_type where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_CMD_COLLECT_TYPE (cmd_collect_type_id, name, is_rule, device_id) values (?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_CMD_COLLECT_TYPE set cmd_collect_type_id=?, name=?, is_rule=?, device_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_CMD_COLLECT_TYPE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_CMD_COLLECT_TYPE where cmd_collect_type_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_CMD_COLLECT_TYPE set cmd_collect_type_id=?, name=?, is_rule=?, device_id=? where cmd_collect_type_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select cmd_collect_type_id,name,is_rule,device_id from WO_CMD_COLLECT_TYPE where cmd_collect_type_id=? ";
	//	根据Deviceid查询SQL
	
	private static String SQL_SELECT_DEVICE_ID = "select cmd_collect_type_id,name,is_rule,device_id from WO_CMD_COLLECT_TYPE where device_id=? and rownum=1 ";
	
	//informix兼容
	private static String SQL_SELECT_DEVICE_ID_informix = "select first 1 cmd_collect_type_id,name,is_rule,device_id from WO_CMD_COLLECT_TYPE where device_id=? ";

	
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoCmdCollectTypeDAO() {

	}
	
	static{
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			SQL_SELECT_DEVICE_ID=SQL_SELECT_DEVICE_ID_informix;
		}
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
		params.add(seqDao.getNextSequence("WO_CMD_COLLECT_TYPE_SEQ")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("is_rule")) ;
									
		params.add(map.get("device_id")) ;
						
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
				
					
		params.add(vo.get("cmd_collect_type_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("is_rule")) ;
						
					
		params.add(vo.get("device_id")) ;
						
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
							
		params.add(vo.get("cmd_collect_type_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("is_rule")) ;
									
		params.add(vo.get("device_id")) ;
						
							
		params.add(keyCondMap.get("cmd_collect_type_id")) ;
						
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
							
		params.add(keyCondMap.get("cmd_collect_type_id")) ;
						
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


	public Map findByDeviceId(String device_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map result = new HashMap();
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
		//做了修改
			stmt = conn.prepareStatement(SQL_SELECT_DEVICE_ID);
			int index = 1;
			stmt.setString( index++,  device_id);
			rs = stmt.executeQuery();
			if(rs.next()){
				result.put("cmd_collect_type_id", rs.getString("cmd_collect_type_id"));
				result.put("name", rs.getString("name"));
				result.put("is_rule", rs.getString("is_rule"));
				result.put("device_id", rs.getString("device_id"));
			}
		}
		catch (SQLException se) {
			Debug.print(SQL_SELECT_DEVICE_ID,this);
			throw new DAOSystemException("SQLException while  sql:\n"+SQL_SELECT_DEVICE_ID, se);
		}
		finally {
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return result;
	}
	
}
