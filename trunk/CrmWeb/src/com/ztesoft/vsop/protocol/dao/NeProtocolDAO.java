package com.ztesoft.vsop.protocol.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class NeProtocolDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select n.ne_protocol_id,n.business_obj_id,n.name,n.max_timeout,n.retry_times,n.need_heart_beat,n.retry_inter,n.effect_date,n.expire_date,n.comments,n.max_connect,o.business_obj_name obj_name  from NE_PROTOCOL n left join PB_BUSINESS_OBJ o on o.business_obj_id = n.business_obj_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_protocol n where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_PROTOCOL (ne_protocol_id, business_obj_id, name, max_timeout, retry_times, need_heart_beat, retry_inter, effect_date, expire_date, comments, max_connect) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_PROTOCOL set ne_protocol_id=?, business_obj_id=?, name=?, max_timeout=?, retry_times=?, need_heart_beat=?, retry_inter=?, effect_date=?, expire_date=?, comments=?, max_connect=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_PROTOCOL where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from NE_PROTOCOL where ne_protocol_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update NE_PROTOCOL set ne_protocol_id=?, business_obj_id=?, name=?, max_timeout=?, retry_times=?, need_heart_beat=?, retry_inter=?, effect_date=?, expire_date=?, comments=?, max_connect=? where ne_protocol_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select n.ne_protocol_id,n.business_obj_id,n.name,n.max_timeout,n.retry_times,n.need_heart_beat,n.retry_inter,n.effect_date,n.expire_date,n.comments,n.max_connect,o.business_obj_name obj_name  from NE_PROTOCOL n left join PB_BUSINESS_OBJ o on o.business_obj_id = n.business_obj_id where n.ne_protocol_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE  ;


	public NeProtocolDAO() {

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
		params.add(seqDao.getNextSequence("Ne_Protocol_Seq"));
									
		params.add(map.get("business_obj_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("max_timeout")) ;
									
		params.add(map.get("retry_times")) ;
									
		params.add(map.get("need_heart_beat")) ;
									
		params.add(map.get("retry_inter")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("effect_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("expire_date" ))) ;
									
		params.add(map.get("comments")) ;
									
		params.add(map.get("max_connect")) ;
						
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
				
					
		params.add(vo.get("ne_protocol_id")) ;
						
					
		params.add(vo.get("business_obj_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("max_timeout")) ;
						
					
		params.add(vo.get("retry_times")) ;
						
					
		params.add(vo.get("need_heart_beat")) ;
						
					
		params.add(vo.get("retry_inter")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("effect_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("expire_date" ))) ;
						
					
		params.add(vo.get("comments")) ;
						
					
		params.add(vo.get("max_connect")) ;
						
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
							
		params.add(vo.get("ne_protocol_id")) ;
									
		params.add(vo.get("business_obj_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("max_timeout")) ;
									
		params.add(vo.get("retry_times")) ;
									
		params.add(vo.get("need_heart_beat")) ;
									
		params.add(vo.get("retry_inter")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("effect_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("expire_date" ))) ;
									
		params.add(vo.get("comments")) ;
									
		params.add(vo.get("max_connect")) ;
						
							
		params.add(keyCondMap.get("ne_protocol_id")) ;
						
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
							
		params.add(keyCondMap.get("ne_protocol_id")) ;
						
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
