package com.powerise.ibss.framedata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class TfmErrorLogDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select error_seq,error_code,error_log,occur_time,terminal,error_pos,service_name from TFM_ERROR_LOG where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tfm_error_log where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into TFM_ERROR_LOG (error_seq, error_code, error_log, occur_time, terminal, error_pos, service_name) values (?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update TFM_ERROR_LOG set error_seq=?, error_code=?, error_log=?, occur_time=?, terminal=?, error_pos=?, service_name=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from TFM_ERROR_LOG where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TFM_ERROR_LOG where error_seq=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update TFM_ERROR_LOG set error_seq=?, error_code=?, error_log=?, occur_time=?, terminal=?, error_pos=?, service_name=? where error_seq=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select error_seq,error_code,error_log,occur_time,terminal,error_pos,service_name from TFM_ERROR_LOG where error_seq=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public TfmErrorLogDAO() {

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
							
		params.add(map.get("error_seq")) ;
									
		params.add(map.get("error_code")) ;
									
		params.add(map.get("error_log")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("occur_time" ))) ;
									
		params.add(map.get("terminal")) ;
									
		params.add(map.get("error_pos")) ;
									
		params.add(map.get("service_name")) ;
						
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
				
					
		params.add(vo.get("error_seq")) ;
						
					
		params.add(vo.get("error_code")) ;
						
					
		params.add(vo.get("error_log")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("occur_time" ))) ;
						
					
		params.add(vo.get("terminal")) ;
						
					
		params.add(vo.get("error_pos")) ;
						
					
		params.add(vo.get("service_name")) ;
						
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
							
		params.add(vo.get("error_seq")) ;
									
		params.add(vo.get("error_code")) ;
									
		params.add(vo.get("error_log")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("occur_time" ))) ;
									
		params.add(vo.get("terminal")) ;
									
		params.add(vo.get("error_pos")) ;
									
		params.add(vo.get("service_name")) ;
						
							
		params.add(keyCondMap.get("error_seq")) ;
						
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
							
		params.add(keyCondMap.get("error_seq")) ;
						
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
