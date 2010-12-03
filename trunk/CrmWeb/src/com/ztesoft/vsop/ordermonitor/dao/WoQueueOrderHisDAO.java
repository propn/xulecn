package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class WoQueueOrderHisDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ne_order_id,dispatch_queue_id,device_id,state,execute_time from WO_QUEUE_ORDER_HIS where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_queue_order_his where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_QUEUE_ORDER_HIS (ne_order_id, dispatch_queue_id, device_id, state, execute_time) values (?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_QUEUE_ORDER_HIS set ne_order_id=?, dispatch_queue_id=?, device_id=?, state=?, execute_time=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_QUEUE_ORDER_HIS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_QUEUE_ORDER_HIS where ne_order_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_QUEUE_ORDER_HIS set ne_order_id=?, dispatch_queue_id=?, device_id=?, state=?, execute_time=? where ne_order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ne_order_id,dispatch_queue_id,device_id,state,execute_time from WO_QUEUE_ORDER_HIS where ne_order_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoQueueOrderHisDAO() {

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
							
		params.add(map.get("ne_order_id")) ;
									
		params.add(map.get("dispatch_queue_id")) ;
									
		params.add(map.get("device_id")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("execute_time" ))) ;
						
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
				
					
		params.add(vo.get("ne_order_id")) ;
						
					
		params.add(vo.get("dispatch_queue_id")) ;
						
					
		params.add(vo.get("device_id")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("execute_time" ))) ;
						
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
							
		params.add(vo.get("ne_order_id")) ;
									
		params.add(vo.get("dispatch_queue_id")) ;
									
		params.add(vo.get("device_id")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("execute_time" ))) ;
						
							
		params.add(keyCondMap.get("ne_order_id")) ;
						
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
							
		params.add(keyCondMap.get("ne_order_id")) ;
						
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
	
}
