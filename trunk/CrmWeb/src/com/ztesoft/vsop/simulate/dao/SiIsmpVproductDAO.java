package com.ztesoft.vsop.simulate.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class SiIsmpVproductDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select id,product_no,user_type,vproduct_id,package_id,pprodoffer_id,create_time,eff_time,exp_time,state,state_date,old_vproduct_id,old_package_id,old_pprodoffer_id,sync_type from SI_ISMP_VPRODUCT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_ismp_vproduct where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SI_ISMP_VPRODUCT (id, product_no, user_type, vproduct_id, package_id, pprodoffer_id, create_time, eff_time, exp_time, state, state_date, old_vproduct_id, old_package_id, old_pprodoffer_id, sync_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SI_ISMP_VPRODUCT set id=?, product_no=?, user_type=?, vproduct_id=?, package_id=?, pprodoffer_id=?, create_time=?, eff_time=?, exp_time=?, state=?, state_date=?, old_vproduct_id=?, old_package_id=?, old_pprodoffer_id=?, sync_type=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SI_ISMP_VPRODUCT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SI_ISMP_VPRODUCT where id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SI_ISMP_VPRODUCT set id=?, product_no=?, user_type=?, vproduct_id=?, package_id=?, pprodoffer_id=?, create_time=?, eff_time=?, exp_time=?, state=?, state_date=?, old_vproduct_id=?, old_package_id=?, old_pprodoffer_id=?, sync_type=? where id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select id,product_no,user_type,vproduct_id,package_id,pprodoffer_id,create_time,eff_time,exp_time,state,state_date,old_vproduct_id,old_package_id,old_pprodoffer_id,sync_type from SI_ISMP_VPRODUCT where id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SiIsmpVproductDAO() {

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
		
		SequenceManageDAO dao = new SequenceManageDAOImpl();
		String id = dao.getNextSequence("Si_ismp_vproduct_seq");
		params.add(id) ;
									
		params.add(map.get("product_no")) ;
									
		params.add(map.get("user_type")) ;
									
		params.add(map.get("vproduct_id")) ;
									
		params.add(map.get("package_id")) ;
									
		params.add(map.get("pprodoffer_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_time" ))) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("old_vproduct_id")) ;
									
		params.add(map.get("old_package_id")) ;
									
		params.add(map.get("old_pprodoffer_id")) ;
									
		params.add(map.get("sync_type")) ;
						
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
						
					
		params.add(vo.get("product_no")) ;
						
					
		params.add(vo.get("user_type")) ;
						
					
		params.add(vo.get("vproduct_id")) ;
						
					
		params.add(vo.get("package_id")) ;
						
					
		params.add(vo.get("pprodoffer_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_time" ))) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("old_vproduct_id")) ;
						
					
		params.add(vo.get("old_package_id")) ;
						
					
		params.add(vo.get("old_pprodoffer_id")) ;
						
					
		params.add(vo.get("sync_type")) ;
						
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
									
		params.add(vo.get("product_no")) ;
									
		params.add(vo.get("user_type")) ;
									
		params.add(vo.get("vproduct_id")) ;
									
		params.add(vo.get("package_id")) ;
									
		params.add(vo.get("pprodoffer_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_time" ))) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("old_vproduct_id")) ;
									
		params.add(vo.get("old_package_id")) ;
									
		params.add(vo.get("old_pprodoffer_id")) ;
									
		params.add(vo.get("sync_type")) ;
						
							
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
