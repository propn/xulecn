package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class TpmConstraintDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select constraint_id,constraint_name,strategy_id,agree_id,rule_id,plan_object_type,plan_object_id,poffer_id,prole_type,pgroup_no,offer_id,role_type,group_no,product_id,sub_product_id,path_id,tar_object_type,tar_object_id,tar_service_offer_id,constraint_type,para_value,para_value2,para_values_id,time_limit_type,time_limit_vale,operation_flag,group_id,order_id,bureau_values_id,channel_values_id,privilege_values_id,eff_date,exp_date,state,party_id,party_role_id,oper_date,para_cvalue,para_cvalue2,para_name,para_owner,except_channel_values_id,para_cname,oper_region_id from TPM_CONSTRAINT where 1=1 and state <> '00X'";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tpm_constraint where 1=1 and state <> '00X'";

	//	insert SQl
	private String SQL_INSERT = "insert into TPM_CONSTRAINT (constraint_id, constraint_name, strategy_id, agree_id, rule_id, plan_object_type, plan_object_id, poffer_id, prole_type, pgroup_no, offer_id, role_type, group_no, product_id, sub_product_id, path_id, tar_object_type, tar_object_id, tar_service_offer_id, constraint_type, para_value, para_value2, para_values_id, time_limit_type, time_limit_vale, operation_flag, group_id, order_id, bureau_values_id, channel_values_id, privilege_values_id, eff_date, exp_date, state, party_id, party_role_id, oper_date, para_cvalue, para_cvalue2, para_name, para_owner, except_channel_values_id, para_cname, oper_region_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update TPM_CONSTRAINT set constraint_id=?, constraint_name=?, strategy_id=?, agree_id=?, rule_id=?, plan_object_type=?, plan_object_id=?, poffer_id=?, prole_type=?, pgroup_no=?, offer_id=?, role_type=?, group_no=?, product_id=?, sub_product_id=?, path_id=?, tar_object_type=?, tar_object_id=?, tar_service_offer_id=?, constraint_type=?, para_value=?, para_value2=?, para_values_id=?, time_limit_type=?, time_limit_vale=?, operation_flag=?, group_id=?, order_id=?, bureau_values_id=?, channel_values_id=?, privilege_values_id=?, eff_date=?, exp_date=?, state=?, party_id=?, party_role_id=?, oper_date=?, para_cvalue=?, para_cvalue2=?, para_name=?, para_owner=?, except_channel_values_id=?, para_cname=?, oper_region_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from TPM_CONSTRAINT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TPM_CONSTRAINT where constraint_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update TPM_CONSTRAINT set constraint_id=?, constraint_name=?, strategy_id=?, agree_id=?, rule_id=?, plan_object_type=?, plan_object_id=?, poffer_id=?, prole_type=?, pgroup_no=?, offer_id=?, role_type=?, group_no=?, product_id=?, sub_product_id=?, path_id=?, tar_object_type=?, tar_object_id=?, tar_service_offer_id=?, constraint_type=?, para_value=?, para_value2=?, para_values_id=?, time_limit_type=?, time_limit_vale=?, operation_flag=?, group_id=?, order_id=?, bureau_values_id=?, channel_values_id=?, privilege_values_id=?, eff_date=?, exp_date=?, state=?, party_id=?, party_role_id=?, oper_date=?, para_cvalue=?, para_cvalue2=?, para_name=?, para_owner=?, except_channel_values_id=?, para_cname=?, oper_region_id=? where constraint_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select constraint_id,constraint_name,strategy_id,agree_id,rule_id,plan_object_type,plan_object_id,poffer_id,prole_type,pgroup_no,offer_id,role_type,group_no,product_id,sub_product_id,path_id,tar_object_type,tar_object_id,tar_service_offer_id,constraint_type,para_value,para_value2,para_values_id,time_limit_type,time_limit_vale,operation_flag,group_id,order_id,bureau_values_id,channel_values_id,privilege_values_id,eff_date,exp_date,state,party_id,party_role_id,oper_date,para_cvalue,para_cvalue2,para_name,para_owner,except_channel_values_id,para_cname,oper_region_id from TPM_CONSTRAINT where constraint_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public TpmConstraintDAO() {

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
							
		params.add(map.get("constraint_id")) ;
									
		params.add(map.get("constraint_name")) ;
									
		params.add(map.get("strategy_id")) ;
									
		params.add(map.get("agree_id")) ;
									
		params.add(map.get("rule_id")) ;
									
		params.add(map.get("plan_object_type")) ;
									
		params.add(map.get("plan_object_id")) ;
									
		params.add(map.get("poffer_id")) ;
									
		params.add(map.get("prole_type")) ;
									
		params.add(map.get("pgroup_no")) ;
									
		params.add(map.get("offer_id")) ;
									
		params.add(map.get("role_type")) ;
									
		params.add(map.get("group_no")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("sub_product_id")) ;
									
		params.add(map.get("path_id")) ;
									
		params.add(map.get("tar_object_type")) ;
									
		params.add(map.get("tar_object_id")) ;
									
		params.add(map.get("tar_service_offer_id")) ;
									
		params.add(map.get("constraint_type")) ;
									
		params.add(map.get("para_value")) ;
									
		params.add(map.get("para_value2")) ;
									
		params.add(map.get("para_values_id")) ;
									
		params.add(map.get("time_limit_type")) ;
									
		params.add(map.get("time_limit_vale")) ;
									
		params.add(map.get("operation_flag")) ;
									
		params.add(map.get("group_id")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("bureau_values_id")) ;
									
		params.add(map.get("channel_values_id")) ;
									
		params.add(map.get("privilege_values_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("para_cvalue")) ;
									
		params.add(map.get("para_cvalue2")) ;
									
		params.add(map.get("para_name")) ;
									
		params.add(map.get("para_owner")) ;
									
		params.add(map.get("except_channel_values_id")) ;
									
		params.add(map.get("para_cname")) ;
									
		params.add(map.get("oper_region_id")) ;
						
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
				
					
		params.add(vo.get("constraint_id")) ;
						
					
		params.add(vo.get("constraint_name")) ;
						
					
		params.add(vo.get("strategy_id")) ;
						
					
		params.add(vo.get("agree_id")) ;
						
					
		params.add(vo.get("rule_id")) ;
						
					
		params.add(vo.get("plan_object_type")) ;
						
					
		params.add(vo.get("plan_object_id")) ;
						
					
		params.add(vo.get("poffer_id")) ;
						
					
		params.add(vo.get("prole_type")) ;
						
					
		params.add(vo.get("pgroup_no")) ;
						
					
		params.add(vo.get("offer_id")) ;
						
					
		params.add(vo.get("role_type")) ;
						
					
		params.add(vo.get("group_no")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("sub_product_id")) ;
						
					
		params.add(vo.get("path_id")) ;
						
					
		params.add(vo.get("tar_object_type")) ;
						
					
		params.add(vo.get("tar_object_id")) ;
						
					
		params.add(vo.get("tar_service_offer_id")) ;
						
					
		params.add(vo.get("constraint_type")) ;
						
					
		params.add(vo.get("para_value")) ;
						
					
		params.add(vo.get("para_value2")) ;
						
					
		params.add(vo.get("para_values_id")) ;
						
					
		params.add(vo.get("time_limit_type")) ;
						
					
		params.add(vo.get("time_limit_vale")) ;
						
					
		params.add(vo.get("operation_flag")) ;
						
					
		params.add(vo.get("group_id")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("bureau_values_id")) ;
						
					
		params.add(vo.get("channel_values_id")) ;
						
					
		params.add(vo.get("privilege_values_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("para_cvalue")) ;
						
					
		params.add(vo.get("para_cvalue2")) ;
						
					
		params.add(vo.get("para_name")) ;
						
					
		params.add(vo.get("para_owner")) ;
						
					
		params.add(vo.get("except_channel_values_id")) ;
						
					
		params.add(vo.get("para_cname")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
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
							
		params.add(vo.get("constraint_id")) ;
									
		params.add(vo.get("constraint_name")) ;
									
		params.add(vo.get("strategy_id")) ;
									
		params.add(vo.get("agree_id")) ;
									
		params.add(vo.get("rule_id")) ;
									
		params.add(vo.get("plan_object_type")) ;
									
		params.add(vo.get("plan_object_id")) ;
									
		params.add(vo.get("poffer_id")) ;
									
		params.add(vo.get("prole_type")) ;
									
		params.add(vo.get("pgroup_no")) ;
									
		params.add(vo.get("offer_id")) ;
									
		params.add(vo.get("role_type")) ;
									
		params.add(vo.get("group_no")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("sub_product_id")) ;
									
		params.add(vo.get("path_id")) ;
									
		params.add(vo.get("tar_object_type")) ;
									
		params.add(vo.get("tar_object_id")) ;
									
		params.add(vo.get("tar_service_offer_id")) ;
									
		params.add(vo.get("constraint_type")) ;
									
		params.add(vo.get("para_value")) ;
									
		params.add(vo.get("para_value2")) ;
									
		params.add(vo.get("para_values_id")) ;
									
		params.add(vo.get("time_limit_type")) ;
									
		params.add(vo.get("time_limit_vale")) ;
									
		params.add(vo.get("operation_flag")) ;
									
		params.add(vo.get("group_id")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("bureau_values_id")) ;
									
		params.add(vo.get("channel_values_id")) ;
									
		params.add(vo.get("privilege_values_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("para_cvalue")) ;
									
		params.add(vo.get("para_cvalue2")) ;
									
		params.add(vo.get("para_name")) ;
									
		params.add(vo.get("para_owner")) ;
									
		params.add(vo.get("except_channel_values_id")) ;
									
		params.add(vo.get("para_cname")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
							
		params.add(keyCondMap.get("constraint_id")) ;
						
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
							
		params.add(keyCondMap.get("constraint_id")) ;
						
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
