package com.ztesoft.vsop.dispatchrule.dao;

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

public class WoDispatchStartegyDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select d.dispatch_rule_id,d.item_val,d.item_code,i.item_name from WO_DISPATCH_STARTEGY d,WO_STARTEGY_ITEM i where d.item_code  = i.item_code ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_dispatch_startegy d where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_DISPATCH_STARTEGY (dispatch_rule_id, item_val, item_code) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_DISPATCH_STARTEGY set dispatch_rule_id=?, item_val=?, item_code=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_DISPATCH_STARTEGY where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_DISPATCH_STARTEGY where dispatch_rule_id=? ";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_DISPATCH_STARTEGY set dispatch_rule_id=?, item_val=?, item_code=? where dispatch_rule_id=? and item_code=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select dispatch_rule_id,item_val,item_code from WO_DISPATCH_STARTEGY where dispatch_rule_id=? and item_code=? ";
	//	根据 dispatch_rule_id delete SQL
	private String SQL_DELETE_RULEID = "delete from WO_DISPATCH_STARTEGY where dispatch_rule_id=? ";

	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoDispatchStartegyDAO() {

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
							
		params.add(map.get("dispatch_rule_id")) ;
									
		params.add(map.get("item_val")) ;
									
		params.add(map.get("item_code")) ;
						
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
				
					
		params.add(vo.get("dispatch_rule_id")) ;
						
					
		params.add(vo.get("item_val")) ;
						
					
		params.add(vo.get("item_code")) ;
						
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
							
		params.add(vo.get("dispatch_rule_id")) ;
									
		params.add(vo.get("item_val")) ;
									
		params.add(vo.get("item_code")) ;
						
							
		params.add(keyCondMap.get("dispatch_rule_id")) ;
									
		params.add(keyCondMap.get("item_code")) ;
						
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
							
		params.add(keyCondMap.get("dispatch_rule_id")) ;
									
		params.add(keyCondMap.get("item_code")) ;
						
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


	public boolean deleteByRuleId(String dispatch_rule_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_DELETE_RULEID);
			int index = 1;
			stmt.setString( index++,  dispatch_rule_id );
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE_RULEID,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	
}
