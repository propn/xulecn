package com.ztesoft.vsop.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.common.util.tracer.Debug;

public class NeDeviceDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select device_id,name,device_state_code,area_id,backup_device_id,ip,port,user_id,password,conn,driver,url,max_connect,priv_code,is_syn,communicate_protocol_id,ne_protocol_id,sys_code from NE_DEVICE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_device where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_DEVICE (device_id, name, device_state_code, area_id, backup_device_id, ip, port, user_id, password, conn, driver, url, max_connect, priv_code, is_syn, communicate_protocol_id, ne_protocol_id, sys_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_DEVICE set device_id=?, name=?, device_state_code=?, area_id=?, backup_device_id=?, ip=?, port=?, user_id=?, password=?, conn=?, driver=?, url=?, max_connect=?, priv_code=?, is_syn=?, communicate_protocol_id=?, ne_protocol_id=?, sys_code=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_DEVICE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from NE_DEVICE where device_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update NE_DEVICE set device_id=?, name=?, device_state_code=?, area_id=?, backup_device_id=?, ip=?, port=?, user_id=?, password=?, conn=?, driver=?, url=?, max_connect=?, priv_code=?, is_syn=?, communicate_protocol_id=?, ne_protocol_id=?, sys_code=? where device_id=?";
	
	//	根据主键update SQL
	private String SQL_UPDATE_STATE_KEY = "update NE_DEVICE set  device_state_code=? where device_id=?";
	// 根据主键更新对端系统策略update_sql
	private String SQL_UPDATE_RULE_KEY = "update WO_DISPATCH_DEVICE set  dispatch_rule_id=? where device_id=?";
	// 根据主键删除对端系统策略update_sql
	private String SQL_DELETE_RULE_KEY = "delete from  WO_DISPATCH_DEVICE where device_id=?";

		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select s.device_id,s.name,s.device_state_code,s.area_id,s.backup_device_id,s.ip,s.port,s.user_id,s.password,s.conn,s.driver,s.url,s.max_connect,s.priv_code,s.is_syn,s.communicate_protocol_id,s.ne_protocol_id,s.sys_code ,p.name nename,c.name communname ,r.dispatch_rule_id ruleId, r.name rulename" +
			" from NE_DEVICE s left join NE_PROTOCOL p on s.ne_protocol_id = p.ne_protocol_id left join  NE_COMMUNICATE_PROTOCOL c on s.communicate_protocol_id = c.communicate_protocol_id "+
			"  left join WO_DISPATCH_DEVICE dd on dd.device_id = s.device_id left join WO_DISPATCH_RULE r  on dd.dispatch_rule_id = r.dispatch_rule_id "+
			" where s.device_id=? ";
	private String SQL_INSERT_DISPATCH_RULE =" insert into WO_DISPATCH_DEVICE (device_id ,dispatch_rule_id ) values(?,?)";
	
	private String SQL_CHECKCODE =" select * from NE_DEVICE where sys_code = ?";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeDeviceDAO() {

	}
	private String sequ = "";
	private Object ruleId = "";
	public boolean insert(Map vo) throws FrameException {
		Map t  = vo ;
		List insertParams = translateInsertParams(vo) ;
		String SQL = getInsertSQL() ;
		boolean success = Base.update(this.getDbName(), SQL, insertParams,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
		if(success && null != sequ && null != ruleId){
			return insertDispRule(sequ,ruleId.toString());
		}
		return false;
	}
	public boolean insertDispRule(String deviceId,String ruleId) throws FrameException{
		List insertParams =new ArrayList();
		insertParams.add(deviceId);
		insertParams.add(ruleId);
		return Base.update(this.getDbName(), SQL_INSERT_DISPATCH_RULE, insertParams,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
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
		sequ=seqDao.getNextSequence("NE_DEVICE_SEQ");
		params.add(sequ) ;		

									
		params.add(map.get("name")) ;
		
		params.add(map.get("device_state_code")) ;
									
		params.add(map.get("area_id")) ;
									
		params.add(map.get("backup_device_id")) ;
									
		params.add(map.get("ip")) ;
									
		params.add(map.get("port")) ;
									
		params.add(map.get("user_id")) ;
									
		params.add(map.get("password")) ;
									
		params.add(map.get("conn")) ;
									
		params.add(map.get("driver")) ;
									
		params.add(map.get("url")) ;
									
		params.add(map.get("max_connect")) ;
									
		params.add(map.get("priv_code")) ;
									
		params.add(map.get("is_syn")) ;
									
		params.add(map.get("communicate_protocol_id")) ;
									
		params.add(map.get("ne_protocol_id")) ;
									
		params.add(map.get("sys_code")) ;
		ruleId = map.get("ruleId");				
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
				
					
		params.add(vo.get("device_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("device_state_code")) ;
						
					
		params.add(vo.get("area_id")) ;
						
					
		params.add(vo.get("backup_device_id")) ;
						
					
		params.add(vo.get("ip")) ;
						
					
		params.add(vo.get("port")) ;
						
					
		params.add(vo.get("user_id")) ;
						
					
		params.add(vo.get("password")) ;
						
					
		params.add(vo.get("conn")) ;
						
					
		params.add(vo.get("driver")) ;
						
					
		params.add(vo.get("url")) ;
						
					
		params.add(vo.get("max_connect")) ;
						
					
		params.add(vo.get("priv_code")) ;
						
					
		params.add(vo.get("is_syn")) ;
						
					
		params.add(vo.get("communicate_protocol_id")) ;
						
					
		params.add(vo.get("ne_protocol_id")) ;
						
					
		params.add(vo.get("sys_code")) ;
						
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
							
		params.add(vo.get("device_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("device_state_code")) ;
									
		params.add(vo.get("area_id")) ;
									
		params.add(vo.get("backup_device_id")) ;
									
		params.add(vo.get("ip")) ;
									
		params.add(vo.get("port")) ;
									
		params.add(vo.get("user_id")) ;
									
		params.add(vo.get("password")) ;
									
		params.add(vo.get("conn")) ;
									
		params.add(vo.get("driver")) ;
									
		params.add(vo.get("url")) ;
									
		params.add(vo.get("max_connect")) ;
									
		params.add(vo.get("priv_code")) ;
									
		params.add(vo.get("is_syn")) ;
									
		params.add(vo.get("communicate_protocol_id")) ;
									
		params.add(vo.get("ne_protocol_id")) ;
									
		params.add(vo.get("sys_code")) ;
						
							
		params.add(keyCondMap.get("device_id")) ;
						
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
							
		params.add(keyCondMap.get("device_id")) ;
						
		return params  ;
	}
	public boolean updateStateDevice(String deviceId,String state){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE_STATE_KEY);
			int index = 1;
			stmt.setString( index++,  state );
			stmt.setString( index++,  deviceId );
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_UPDATE_STATE_KEY,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	public boolean changeRuleDevice(String deviceId, String ruleId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE_RULE_KEY);
			int index = 1;
			stmt.setString( index++,  ruleId );
			stmt.setString( index++,  deviceId );
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_UPDATE_RULE_KEY,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	public boolean checkCodeDevice(String sysCode) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_CHECKCODE);
			stmt.setString( 1,  sysCode );
			int rows = stmt.executeQuery().getRow();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_CHECKCODE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	public boolean deleteDispatchDevice(String deviceId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_DELETE_RULE_KEY);
			stmt.setString( 1,  deviceId );
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE_RULE_KEY,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
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
