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

public class ProdPlatformDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_platform_id,product_id,platform_id from si_PROD_PLATFORM where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_prod_platform where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into si_PROD_PLATFORM (prod_platform_id, product_id, platform_id) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update si_PROD_PLATFORM set prod_platform_id=?, product_id=?, platform_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from si_PROD_PLATFORM where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from si_PROD_PLATFORM where prod_platform_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update si_PROD_PLATFORM set prod_platform_id=?, product_id=?, platform_id=? where prod_platform_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_platform_id,product_id,platform_id from si_PROD_PLATFORM where prod_platform_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public ProdPlatformDAO() {

	}

	private String existsRow = "select  count(1) existRow  from si_prod_platform p where p.platform_id=? and p.product_id = ? " ;
		
	
	public boolean checkExistsRow(String platform_id ,String product_id , String prod_platform_id) throws Exception{
		if(prod_platform_id != null && 
				!"".equals(prod_platform_id)){
			existsRow+=" and prod_platform_id<>?" ;
			String[] para = {platform_id ,product_id , prod_platform_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}else{
			String[] para =  {platform_id ,product_id }  ;
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
							
		params.add(map.get("prod_platform_id")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("platform_id")) ;
						
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
				
					
		params.add(vo.get("prod_platform_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("platform_id")) ;
						
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
							
		params.add(vo.get("prod_platform_id")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("platform_id")) ;
						
							
		params.add(keyCondMap.get("prod_platform_id")) ;
						
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
							
		params.add(keyCondMap.get("prod_platform_id")) ;
						
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
