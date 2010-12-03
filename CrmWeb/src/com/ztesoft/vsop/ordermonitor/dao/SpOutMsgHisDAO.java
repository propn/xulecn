package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class SpOutMsgHisDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select t.id,t.in_time,t.msg_id,t.sys,t.order_id,t.deal_time,t.state,t.prod_no,t.lan_id,s.name from SP_OUT_MSG_HIS t left join sp_int_sys s on t.sys= s.int_sys_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from sp_out_msg_his t where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_OUT_MSG_HIS (id, in_time, msg, msg_id, sys, order_id, deal_time, state, error_content, prod_no, lan_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_OUT_MSG_HIS set id=?, in_time=?, msg=?, msg_id=?, sys=?, order_id=?, deal_time=?, state=?, error_content=?, prod_no=?, lan_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_OUT_MSG_HIS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SP_OUT_MSG_HIS where id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SP_OUT_MSG_HIS set id=?, in_time=?, msg=?, msg_id=?, sys=?, order_id=?, deal_time=?, state=?, error_content=?, prod_no=?, lan_id=? where id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select t.id,t.in_time,msg_id,t.sys,t.order_id,t.deal_time,t.state,t.prod_no,t.lan_id,s.name,t.msg,t.error_content from SP_OUT_MSG_HIS t left join sp_int_sys s on t.sys= s.int_sys_id where id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SpOutMsgHisDAO() {

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
						
		params.add(DAOUtils.parseDateTime(map.get("in_time" ))) ;
									
		params.add(map.get("msg")) ;
									
		params.add(map.get("msg_id")) ;
									
		params.add(map.get("sys")) ;
									
		params.add(map.get("order_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("deal_time" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("error_content")) ;
									
		params.add(map.get("prod_no")) ;
									
		params.add(map.get("lan_id")) ;
						
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
						
		
		params.add(DAOUtils.parseDateTime(vo.get("in_time" ))) ;
						
					
		params.add(vo.get("msg")) ;
						
					
		params.add(vo.get("msg_id")) ;
						
					
		params.add(vo.get("sys")) ;
						
					
		params.add(vo.get("order_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("deal_time" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("error_content")) ;
						
					
		params.add(vo.get("prod_no")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
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
						
		params.add(DAOUtils.parseDateTime(vo.get("in_time" ))) ;
									
		params.add(vo.get("msg")) ;
									
		params.add(vo.get("msg_id")) ;
									
		params.add(vo.get("sys")) ;
									
		params.add(vo.get("order_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("deal_time" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("error_content")) ;
									
		params.add(vo.get("prod_no")) ;
									
		params.add(vo.get("lan_id")) ;
						
							
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
	
}
