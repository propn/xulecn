package com.ztesoft.vsop.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

public class ProductOffSyncLogDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select id,deal_time,file_name,deal_result,proc_type,finish_time from PRODUCT_OFFER_SYNC_LOG where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_offer_sync_log where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_OFFER_SYNC_LOG (id, deal_time, file_name, deal_result, proc_type, finish_time) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_OFFER_SYNC_LOG set id=?, deal_time=?, file_name=?, deal_result=?, proc_type=?, finish_time=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_OFFER_SYNC_LOG where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_OFFER_SYNC_LOG where id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_OFFER_SYNC_LOG set id=?, deal_time=?, file_name=?, deal_result=?, proc_type=?, finish_time=? where id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select id,deal_time,file_name,deal_result,proc_type,finish_time from PRODUCT_OFFER_SYNC_LOG where id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public ProductOffSyncLogDAO() {

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
						
		params.add(DAOUtils.parseDateTime(map.get("deal_time" ))) ;
									
		params.add(map.get("file_name")) ;
									
		params.add(map.get("deal_result")) ;
									
		params.add(map.get("proc_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("finish_time" ))) ;
						
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
						
		
		params.add(DAOUtils.parseDateTime(vo.get("deal_time" ))) ;
						
					
		params.add(vo.get("file_name")) ;
						
					
		params.add(vo.get("deal_result")) ;
						
					
		params.add(vo.get("proc_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("finish_time" ))) ;
						
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
						
		params.add(DAOUtils.parseDateTime(vo.get("deal_time" ))) ;
									
		params.add(vo.get("file_name")) ;
									
		params.add(vo.get("deal_result")) ;
									
		params.add(vo.get("proc_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("finish_time" ))) ;
						
							
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
	
	public boolean update(Map vo,String id) throws FrameException {
		Connection conn = ConnectionContext.getContext().getConnection(dbName);
		PreparedStatement updatePsmt = null;
		try{
			updatePsmt = conn.prepareStatement("update product_offer_sync_log set deal_result=?,finish_time=? where id=?");
			int index = 1;
			updatePsmt.setString(index++, (String) vo.get("deal_result"));
			updatePsmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			updatePsmt.setString(index++, id);
			updatePsmt.executeUpdate();
		}catch(Exception e){
//			logger.error("#updateInfMsgState ex.",e);
			throw new FrameException(e);
		}finally{
			DAOUtils.closeStatement(updatePsmt);
		}
		return true;
	}


	public void insert(String id,String fileName, String procType, String result) throws FrameException {
		Connection conn = ConnectionContext.getContext().getConnection(dbName);
		PreparedStatement updatePsmt = null;
		try{
			updatePsmt = conn.prepareStatement("insert into product_offer_sync_log(id,deal_time,file_name,proc_type,deal_result) values(?,?,?,?,?)");
			int index = 1;
			updatePsmt.setString(index++, id);
			updatePsmt.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			updatePsmt.setString(index++, fileName);
			updatePsmt.setString(index++, procType);
			updatePsmt.setString(index++, result);
			updatePsmt.executeUpdate();
		}catch(Exception e){
			throw new FrameException(e);
		}finally{
			DAOUtils.closeStatement(updatePsmt);
		}
	}
	
}
