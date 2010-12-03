package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductConfGrpProdDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select conf_group_id,group_type,conf_group_code,conf_group_name,conf_group_desc,state,state_date,create_date,relation_type_id from PRODUCT_CONF_GROUP where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_conf_group where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_CONF_GROUP (conf_group_id, group_type, conf_group_code, conf_group_name, conf_group_desc, state, state_date, create_date, relation_type_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_CONF_GROUP set conf_group_id=?, group_type=?, conf_group_code=?, conf_group_name=?, conf_group_desc=?, state=?, state_date=?, create_date=?, relation_type_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_CONF_GROUP where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_CONF_GROUP where conf_group_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_CONF_GROUP set conf_group_id=?, group_type=?, conf_group_code=?, conf_group_name=?, conf_group_desc=?, state=?, state_date=?, create_date=?, relation_type_id=? where conf_group_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select conf_group_id,group_type,conf_group_code,conf_group_name,conf_group_desc,state,state_date,create_date,relation_type_id from PRODUCT_CONF_GROUP where conf_group_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductConfGrpProdDAO() {

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
							
		params.add(map.get("conf_group_id")) ;
									
		params.add(map.get("group_type")) ;
									
		params.add(map.get("conf_group_code")) ;
									
		params.add(map.get("conf_group_name")) ;
									
		params.add(map.get("conf_group_desc")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
									
		params.add(map.get("relation_type_id")) ;
						
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
				
					
		params.add(vo.get("conf_group_id")) ;
						
					
		params.add(vo.get("group_type")) ;
						
					
		params.add(vo.get("conf_group_code")) ;
						
					
		params.add(vo.get("conf_group_name")) ;
						
					
		params.add(vo.get("conf_group_desc")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("relation_type_id")) ;
						
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
							
		params.add(vo.get("conf_group_id")) ;
									
		params.add(vo.get("group_type")) ;
									
		params.add(vo.get("conf_group_code")) ;
									
		params.add(vo.get("conf_group_name")) ;
									
		params.add(vo.get("conf_group_desc")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("relation_type_id")) ;
						
							
		params.add(keyCondMap.get("conf_group_id")) ;
						
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
							
		params.add(keyCondMap.get("conf_group_id")) ;
						
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
