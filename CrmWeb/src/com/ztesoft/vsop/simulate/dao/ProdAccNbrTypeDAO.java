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

public class ProdAccNbrTypeDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_acc_nbr_type_id,acc_nbr_type_cd,product_id,access_no,feature_no,match_mode from si_prod_acc_nbr_type where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_prod_acc_nbr_type where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into si_prod_acc_nbr_type (prod_acc_nbr_type_id, acc_nbr_type_cd, product_id, access_no, feature_no, match_mode) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update si_prod_acc_nbr_type set prod_acc_nbr_type_id=?, acc_nbr_type_cd=?, product_id=?, access_no=?, feature_no=?, match_mode=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from si_prod_acc_nbr_type where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from si_prod_acc_nbr_type where prod_acc_nbr_type_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update si_prod_acc_nbr_type set prod_acc_nbr_type_id=?, acc_nbr_type_cd=?, product_id=?, access_no=?, feature_no=?, match_mode=? where prod_acc_nbr_type_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_acc_nbr_type_id,acc_nbr_type_cd,product_id,access_no,feature_no,match_mode from si_prod_acc_nbr_type where prod_acc_nbr_type_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;



	private String existsRow = "select count(1) existRow from si_prod_acc_nbr_type t where t.product_id=? and t.access_no=? " ;
		
	
	public boolean checkExistsRow(String product_id ,String access_no , String prod_acc_nbr_type_id) throws Exception{
		if(prod_acc_nbr_type_id != null && 
				!"".equals(prod_acc_nbr_type_id)){
			existsRow+=" and prod_acc_nbr_type_id<>?" ;
			String[] para = {product_id ,access_no , prod_acc_nbr_type_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}else{
			String[] para =  {product_id ,access_no }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}
	}
	
	public ProdAccNbrTypeDAO() {

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
							
		params.add(map.get("prod_acc_nbr_type_id")) ;
									
		params.add(map.get("acc_nbr_type_cd")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("access_no")) ;
									
		params.add(map.get("feature_no")) ;
									
		params.add(map.get("match_mode")) ;
						
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
				
					
		params.add(vo.get("prod_acc_nbr_type_id")) ;
						
					
		params.add(vo.get("acc_nbr_type_cd")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("access_no")) ;
						
					
		params.add(vo.get("feature_no")) ;
						
					
		params.add(vo.get("match_mode")) ;
						
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
							
		params.add(vo.get("prod_acc_nbr_type_id")) ;
									
		params.add(vo.get("acc_nbr_type_cd")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("access_no")) ;
									
		params.add(vo.get("feature_no")) ;
									
		params.add(vo.get("match_mode")) ;
						
							
		params.add(keyCondMap.get("prod_acc_nbr_type_id")) ;
						
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
							
		params.add(keyCondMap.get("prod_acc_nbr_type_id")) ;
						
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
