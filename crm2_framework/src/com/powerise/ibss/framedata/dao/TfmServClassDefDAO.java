package com.powerise.ibss.framedata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class TfmServClassDefDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select service_name,class_name from TFM_SERV_CLASS_DEF where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tfm_serv_class_def where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into TFM_SERV_CLASS_DEF (service_name, class_name) values (?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update TFM_SERV_CLASS_DEF set service_name=?, class_name=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from TFM_SERV_CLASS_DEF where 1=1 ";
	
	private String SQL_SELECT_KEY ="select service_name,class_name from TFM_SERV_CLASS_DEF where service_name = ?";
	
	private String SQL_UPDATE_KEY = "update TFM_SERV_CLASS_DEF set service_name=?, class_name=? where service_name = ?";	
		
	private String SQL_DELETE_KEY = "delete from TFM_SERV_CLASS_DEF where service_name = ? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public TfmServClassDefDAO() {

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
							
		params.add(map.get("service_name")) ;
									
		params.add(map.get("class_name")) ;
						
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
				
					
		params.add(vo.get("service_name")) ;
						
					
		params.add(vo.get("class_name")) ;
						
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
		
		params.add(vo.get("service_name")) ;
									
		params.add(vo.get("class_name")) ;
		
		params.add(keyCondMap.get("service_name"));
		
		return params;
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
							
		params.add(keyCondMap.get("service_name")) ;
						
		return params  ;
	}
	
	
	public String getDbName(){
		return this.dbName ;
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

	public String getDeleteSQLByKey() throws FrameException {
		
		return this.SQL_DELETE_KEY ;
				
	}
}
