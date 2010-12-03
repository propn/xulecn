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

public class WoParaCatgDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select para_dir_id,parent_para_dir_id,dir_name from WO_PARA_CATALOG where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_para_catalog where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_PARA_CATALOG (para_dir_id, parent_para_dir_id, dir_name) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_PARA_CATALOG set para_dir_id=?, parent_para_dir_id=?, dir_name=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_PARA_CATALOG where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_PARA_CATALOG where para_dir_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_PARA_CATALOG set para_dir_id=?, parent_para_dir_id=?, dir_name=? where para_dir_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select para_dir_id,parent_para_dir_id,dir_name from WO_PARA_CATALOG where para_dir_id=? ";
	
	private String SQL_SELECT_COUNT_INFO="select 1 from SP_PARA_INFO where para_dir_id = ?";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoParaCatgDAO() {

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
		params.add(seqDao.getNextSequence("WO_PARA_CATALOG_SEQ")) ;
									
		params.add(map.get("parentParaDirId")) ;
									
		params.add(map.get("dir_name")) ;
						
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
				
					
		params.add(vo.get("para_dir_id")) ;
						
					
		params.add(vo.get("parent_para_dir_id")) ;
						
					
		params.add(vo.get("dir_name")) ;
						
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
							
		params.add(vo.get("para_dir_id")) ;
									
		params.add(vo.get("parent_para_dir_id")) ;
									
		params.add(vo.get("dir_name")) ;
						
							
		params.add(keyCondMap.get("para_dir_id")) ;
						
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
							
		params.add(keyCondMap.get("para_dir_id")) ;
						
		return params  ;
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
