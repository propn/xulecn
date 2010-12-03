package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ArcProdOffGrpRelationDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select group_a_id,group_z_id,relation_type_id,baseon_type,offer_a_rela_type,offer_z_rela_type,operation_flag,eff_date,exp_date,state,seq,party_id,party_role_id,oper_region_id,oper_date from ARC_PROD_OFFER_GROUP_RELATION where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from arc_prod_offer_group_relation where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ARC_PROD_OFFER_GROUP_RELATION (group_a_id, group_z_id, relation_type_id, baseon_type, offer_a_rela_type, offer_z_rela_type, operation_flag, eff_date, exp_date, state, seq, party_id, party_role_id, oper_region_id, oper_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ARC_PROD_OFFER_GROUP_RELATION set group_a_id=?, group_z_id=?, relation_type_id=?, baseon_type=?, offer_a_rela_type=?, offer_z_rela_type=?, operation_flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ARC_PROD_OFFER_GROUP_RELATION where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ARC_PROD_OFFER_GROUP_RELATION where group_a_id=? and group_z_id=? and relation_type_id=? and baseon_type=? and offer_a_rela_type=? and offer_z_rela_type=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ARC_PROD_OFFER_GROUP_RELATION set group_a_id=?, group_z_id=?, relation_type_id=?, baseon_type=?, offer_a_rela_type=?, offer_z_rela_type=?, operation_flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=? where group_a_id=? and group_z_id=? and relation_type_id=? and baseon_type=? and offer_a_rela_type=? and offer_z_rela_type=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select group_a_id,group_z_id,relation_type_id,baseon_type,offer_a_rela_type,offer_z_rela_type,operation_flag,eff_date,exp_date,state,seq,party_id,party_role_id,oper_region_id,oper_date from ARC_PROD_OFFER_GROUP_RELATION where group_a_id=? and group_z_id=? and relation_type_id=? and baseon_type=? and offer_a_rela_type=? and offer_z_rela_type=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ArcProdOffGrpRelationDAO() {

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
							
		params.add(map.get("group_a_id")) ;
									
		params.add(map.get("group_z_id")) ;
									
		params.add(map.get("relation_type_id")) ;
									
		params.add(map.get("baseon_type")) ;
									
		params.add(map.get("offer_a_rela_type")) ;
									
		params.add(map.get("offer_z_rela_type")) ;
									
		params.add(map.get("operation_flag")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
						
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
				
					
		params.add(vo.get("group_a_id")) ;
						
					
		params.add(vo.get("group_z_id")) ;
						
					
		params.add(vo.get("relation_type_id")) ;
						
					
		params.add(vo.get("baseon_type")) ;
						
					
		params.add(vo.get("offer_a_rela_type")) ;
						
					
		params.add(vo.get("offer_z_rela_type")) ;
						
					
		params.add(vo.get("operation_flag")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
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
							
		params.add(vo.get("group_a_id")) ;
									
		params.add(vo.get("group_z_id")) ;
									
		params.add(vo.get("relation_type_id")) ;
									
		params.add(vo.get("baseon_type")) ;
									
		params.add(vo.get("offer_a_rela_type")) ;
									
		params.add(vo.get("offer_z_rela_type")) ;
									
		params.add(vo.get("operation_flag")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
							
		params.add(keyCondMap.get("group_a_id")) ;
									
		params.add(keyCondMap.get("group_z_id")) ;
									
		params.add(keyCondMap.get("relation_type_id")) ;
									
		params.add(keyCondMap.get("baseon_type")) ;
									
		params.add(keyCondMap.get("offer_a_rela_type")) ;
									
		params.add(keyCondMap.get("offer_z_rela_type")) ;
						
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
							
		params.add(keyCondMap.get("group_a_id")) ;
									
		params.add(keyCondMap.get("group_z_id")) ;
									
		params.add(keyCondMap.get("relation_type_id")) ;
									
		params.add(keyCondMap.get("baseon_type")) ;
									
		params.add(keyCondMap.get("offer_a_rela_type")) ;
									
		params.add(keyCondMap.get("offer_z_rela_type")) ;
						
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
