package com.ztesoft.vsop.ordermonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

public class WoNeOrderFeebackDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select id,ne_order_id,feeback_info,create_date,deal_date,state,deal_desc from WO_NE_ORDER_FEEBACK where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_ne_order_feeback where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_NE_ORDER_FEEBACK (id, ne_order_id, feeback_info, create_date, deal_date, state, deal_desc) values (?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_NE_ORDER_FEEBACK set id=?, ne_order_id=?, feeback_info=?, create_date=?, deal_date=?, state=?, deal_desc=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_NE_ORDER_FEEBACK where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_NE_ORDER_FEEBACK where id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_NE_ORDER_FEEBACK set id=?, ne_order_id=?, feeback_info=?, create_date=?, deal_date=?, state=?, deal_desc=? where ne_order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select id,ne_order_id,feeback_info,create_date,deal_date,state,deal_desc from WO_NE_ORDER_FEEBACK where ne_order_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoNeOrderFeebackDAO() {

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
							
		params.add(map.get("id")) ;
									
		params.add(map.get("ne_order_id")) ;
									
		params.add(map.get("feeback_info")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("deal_date" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("deal_desc")) ;
						
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
				
					
		params.add(vo.get("id")) ;
						
					
		params.add(vo.get("ne_order_id")) ;
						
					
		params.add(vo.get("feeback_info")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("deal_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("deal_desc")) ;
						
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
							
		params.add(vo.get("id")) ;
									
		params.add(vo.get("ne_order_id")) ;
									
		params.add(vo.get("feeback_info")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("deal_date" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("deal_desc")) ;
						
							
		params.add(keyCondMap.get("id")) ;
						
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
							
		params.add(keyCondMap.get("id")) ;
						
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


	public boolean successWoNeOrderFeeback(String ne_order_id, String state,
			String feeback_info) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String UPDATE_NE_FEEBACK_SUCCESS = " update WO_NE_ORDER_FEEBACK set state=?, feeback_info='"+feeback_info+"' where ne_order_id=? ";
		try {
			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(UPDATE_NE_FEEBACK_SUCCESS);
			int index = 1;
			stmt.setString( index++,  state );
			stmt.setString( index++,  ne_order_id );
			int row =stmt.executeUpdate();
			if(row>0)
				return true;
			
		}
		catch (SQLException se) {
			Debug.print(UPDATE_NE_FEEBACK_SUCCESS,this);
			throw new DAOSystemException("SQLException while :\n"+UPDATE_NE_FEEBACK_SUCCESS, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}


	public boolean changeState(String ne_order_id, String state) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String UPDATE_NE_FEEBACK_SUCCESS = " update WO_NE_ORDER_FEEBACK set state=? where ne_order_id=? ";
		try {
			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(UPDATE_NE_FEEBACK_SUCCESS);
			int index = 1;
			stmt.setString( index++,  state );
			stmt.setString( index++,  ne_order_id );
			int row =stmt.executeUpdate();
			if(row>0)
				return true;
			
		}
		catch (SQLException se) {
			Debug.print(UPDATE_NE_FEEBACK_SUCCESS,this);
			throw new DAOSystemException("SQLException while :\n"+UPDATE_NE_FEEBACK_SUCCESS, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}	
}
