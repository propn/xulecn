package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class AttrValueRelaDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select a_attr_value_id,a_attr_id,z_attr_value_id,z_attr_id from ATTRIBUTE_VALUE_RELA where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from attribute_value_rela where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ATTRIBUTE_VALUE_RELA (a_attr_value_id, a_attr_id, z_attr_value_id, z_attr_id) values (?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ATTRIBUTE_VALUE_RELA set a_attr_value_id=?, a_attr_id=?, z_attr_value_id=?, z_attr_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ATTRIBUTE_VALUE_RELA where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ATTRIBUTE_VALUE_RELA where a_attr_value_id=? and z_attr_value_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ATTRIBUTE_VALUE_RELA set a_attr_value_id=?, a_attr_id=?, z_attr_value_id=?, z_attr_id=? where a_attr_value_id=? and z_attr_value_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select a_attr_value_id,a_attr_id,z_attr_value_id,z_attr_id from ATTRIBUTE_VALUE_RELA where a_attr_value_id=? and z_attr_value_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public AttrValueRelaDAO() {

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
							
		params.add(map.get("a_attr_value_id")) ;
									
		params.add(map.get("a_attr_id")) ;
									
		params.add(map.get("z_attr_value_id")) ;
									
		params.add(map.get("z_attr_id")) ;
						
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
				
					
		params.add(vo.get("a_attr_value_id")) ;
						
					
		params.add(vo.get("a_attr_id")) ;
						
					
		params.add(vo.get("z_attr_value_id")) ;
						
					
		params.add(vo.get("z_attr_id")) ;
						
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
							
		params.add(vo.get("a_attr_value_id")) ;
									
		params.add(vo.get("a_attr_id")) ;
									
		params.add(vo.get("z_attr_value_id")) ;
									
		params.add(vo.get("z_attr_id")) ;
						
							
		params.add(keyCondMap.get("a_attr_value_id")) ;
									
		params.add(keyCondMap.get("z_attr_value_id")) ;
						
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
							
		params.add(keyCondMap.get("a_attr_value_id")) ;
									
		params.add(keyCondMap.get("z_attr_value_id")) ;
						
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
