package com.ztesoft.vsop.simulate.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductRelationDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select product_rel_id,(select p.product_name from si_product p where p.product_id=r.product_id) product_name ,(select p.product_name from si_product p where p.product_id=r.pro_product_id) pro_product_name ,relation_type_cd,product_id,pro_product_id,state_cd,state_date,create_date from si_product_relation r where 1=1";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_product_relation r where 1=1  ";

	//	insert SQl
	private String SQL_INSERT = "insert into si_product_relation (product_rel_id, relation_type_cd, product_id, pro_product_id, state_cd, state_date, create_date) values (?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update si_product_relation set product_rel_id=?, relation_type_cd=?, product_id=?, pro_product_id=?, state_cd=?, state_date=?, create_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from si_product_relation where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from si_product_relation where product_rel_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update si_product_relation set product_rel_id=?, relation_type_cd=?, product_id=?, pro_product_id=?, state_cd=?, state_date=?, create_date=? where product_rel_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select product_rel_id,(select p.product_name from si_product p where p.product_id=r.product_id) product_name ,(select p.product_name from si_product p where p.product_id=r.pro_product_id) pro_product_name ,relation_type_cd,product_id,pro_product_id,state_cd,state_date,create_date from si_product_relation r where product_rel_id=?";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public ProductRelationDAO() {

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
							
		params.add(map.get("product_rel_id")) ;
									
		params.add(map.get("relation_type_cd")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("pro_product_id")) ;
									
		params.add(map.get("state_cd")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
						
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
				
					
		params.add(vo.get("product_rel_id")) ;
						
					
		params.add(vo.get("relation_type_cd")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("pro_product_id")) ;
						
					
		params.add(vo.get("state_cd")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
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
							
		params.add(vo.get("product_rel_id")) ;
									
		params.add(vo.get("relation_type_cd")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("pro_product_id")) ;
									
		params.add(vo.get("state_cd")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
							
		params.add(keyCondMap.get("product_rel_id")) ;
						
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
							
		params.add(keyCondMap.get("product_rel_id")) ;
						
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
