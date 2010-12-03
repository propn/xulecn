package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductOffGrpRelationDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select group_a_id,group_z_id,relation_type_id,operation_flag,eff_date,exp_date,state,seq,party_id,party_role_id,oper_region_id,oper_date,baseon_type,offer_a_rela_type,offer_z_rela_type from PRODUCT_OFFER_GROUP_RELATION where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_offer_group_relation where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_OFFER_GROUP_RELATION (group_a_id, group_z_id, relation_type_id, operation_flag, eff_date, exp_date, state, seq, party_id, party_role_id, oper_region_id, oper_date, baseon_type, offer_a_rela_type, offer_z_rela_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update PRODUCT_OFFER_GROUP_RELATION set group_a_id=?, group_z_id=?, relation_type_id=?, operation_flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, baseon_type=?, offer_a_rela_type=?, offer_z_rela_type=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PRODUCT_OFFER_GROUP_RELATION where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_OFFER_GROUP_RELATION where group_a_id=? and group_z_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_OFFER_GROUP_RELATION set group_a_id=?, group_z_id=?, relation_type_id=?, operation_flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, baseon_type=?, offer_a_rela_type=?, offer_z_rela_type=? where group_a_id=? and group_z_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select group_a_id,group_z_id,relation_type_id,operation_flag,eff_date,exp_date,state,seq,party_id,party_role_id,oper_region_id,oper_date,baseon_type,offer_a_rela_type,offer_z_rela_type from PRODUCT_OFFER_GROUP_RELATION where group_a_id=? and group_z_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductOffGrpRelationDAO() {

	}
	

	/**
	 * Insert��������
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
									
		params.add(map.get("operation_flag")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("baseon_type")) ;
									
		params.add(map.get("offer_a_rela_type")) ;
									
		params.add(map.get("offer_z_rela_type")) ;
						
		return params ;
	}
	

	/**
	 * update ��������
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
						
					
		params.add(vo.get("operation_flag")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("baseon_type")) ;
						
					
		params.add(vo.get("offer_a_rela_type")) ;
						
					
		params.add(vo.get("offer_z_rela_type")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * �����������²�������
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
									
		params.add(vo.get("operation_flag")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("baseon_type")) ;
									
		params.add(vo.get("offer_a_rela_type")) ;
									
		params.add(vo.get("offer_z_rela_type")) ;
						
							
		params.add(keyCondMap.get("group_a_id")) ;
									
		params.add(keyCondMap.get("group_z_id")) ;
						
		return params ;
	}
		
		/**
	 * ����������������
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
