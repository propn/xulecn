package com.ztesoft.vsop.simulate.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductSystemInfoDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select partner_system_info_id,product_id,system_code,create_date,state,state_date from SI_PRODUCT_SYSTEM_INFO where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from SI_PRODUCT_SYSTEM_INFO where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SI_PRODUCT_SYSTEM_INFO (partner_system_info_id, product_id, system_code, create_date, state, state_date) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SI_PRODUCT_SYSTEM_INFO set partner_system_info_id=?, product_id=?, system_code=?, create_date=?, state=?, state_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SI_PRODUCT_SYSTEM_INFO where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SI_PRODUCT_SYSTEM_INFO where partner_system_info_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SI_PRODUCT_SYSTEM_INFO set partner_system_info_id=?, product_id=?, system_code=?, create_date=?, state=?, state_date=? where partner_system_info_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select partner_system_info_id,product_id,system_code,create_date,state,state_date from SI_PRODUCT_SYSTEM_INFO where partner_system_info_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public ProductSystemInfoDAO() {

	}
	
	
	
  private String existsRow = "select  count(1) existRow  from SI_PRODUCT_SYSTEM_INFO p where p.system_code=? and p.product_id = ? " ;
		
	
	public boolean checkExistsRow(String system_code ,String product_id , String partner_system_info_id) throws Exception{
		if(partner_system_info_id != null && 
				!"".equals(partner_system_info_id)){
			existsRow+=" and partner_system_info_id<>?" ;
			String[] para = {system_code ,product_id , partner_system_info_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}else{
			String[] para =  {system_code ,product_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}
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
							
		params.add(map.get("partner_system_info_id")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("system_code")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
						
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
				
					
		params.add(vo.get("partner_system_info_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("system_code")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
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
							
		params.add(vo.get("partner_system_info_id")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("system_code")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
							
		params.add(keyCondMap.get("partner_system_info_id")) ;
						
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
							
		params.add(keyCondMap.get("partner_system_info_id")) ;
						
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
