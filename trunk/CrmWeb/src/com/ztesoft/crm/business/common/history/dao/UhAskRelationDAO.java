package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhAskRelationDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select a_ord_id,z_ord_no,rela_type,state from UH_ASK_RELATION where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_ask_relation where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_ASK_RELATION (a_ord_id, z_ord_no, rela_type, state) values (?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update UH_ASK_RELATION set a_ord_id=?, z_ord_no=?, rela_type=?, state=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from UH_ASK_RELATION where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from UH_ASK_RELATION where a_ord_id=? and rela_type=? and z_ord_no=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update UH_ASK_RELATION set a_ord_id=?, z_ord_no=?, rela_type=?, state=? where a_ord_id=? and rela_type=? and z_ord_no=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select a_ord_id,z_ord_no,rela_type,state from UH_ASK_RELATION where a_ord_id=? and rela_type=? and z_ord_no=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = "ORD" ;


	public UhAskRelationDAO() {

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
							
		params.add(map.get("a_ord_id")) ;
									
		params.add(map.get("z_ord_no")) ;
									
		params.add(map.get("rela_type")) ;
									
		params.add(map.get("state")) ;
						
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
				
					
		params.add(vo.get("a_ord_id")) ;
						
					
		params.add(vo.get("z_ord_no")) ;
						
					
		params.add(vo.get("rela_type")) ;
						
					
		params.add(vo.get("state")) ;
						
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
							
		params.add(vo.get("a_ord_id")) ;
									
		params.add(vo.get("z_ord_no")) ;
									
		params.add(vo.get("rela_type")) ;
									
		params.add(vo.get("state")) ;
						
							
		params.add(keyCondMap.get("a_ord_id")) ;
									
		params.add(keyCondMap.get("rela_type")) ;
									
		params.add(keyCondMap.get("z_ord_no")) ;
						
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
							
		params.add(keyCondMap.get("a_ord_id")) ;
									
		params.add(keyCondMap.get("rela_type")) ;
									
		params.add(keyCondMap.get("z_ord_no")) ;
						
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
