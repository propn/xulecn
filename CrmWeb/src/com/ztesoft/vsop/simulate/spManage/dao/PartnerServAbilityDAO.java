package com.ztesoft.vsop.simulate.spManage.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.vsop.product.dao.AbstractProdDAO;

public class PartnerServAbilityDAO extends AbstractProdDAO {
	
	//查询SQL
	private String SQL_SELECT = "select partner_service_ability_id,service_code,partner_id,state,state_date,eff_date,exp_date " +
									" from si_partner_service_ability where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_partner_service_ability where 1=1 ";

	//	insert SQl
	private static String SQL_INSERT= "insert into si_partner_service_ability (partner_service_ability_id, service_code, partner_id, state, state_date, eff_date, exp_date) values (?, ?, ?, ?, sysdate, ?, ?)";

	private static String SQL_INSERT_informix = "insert into si_partner_service_ability (partner_service_ability_id, service_code, partner_id, state, state_date, eff_date, exp_date) values (?, ?, ?, ?, current, ?, ?)";

	//	普通update SQL
	private static String SQL_UPDATE = "update si_partner_service_ability set partner_service_ability_id=?, service_code=?, partner_id=?, state=?, state_date=sysdate, eff_date=?, exp_date=? where 1=1 ";
	
	
	private static String SQL_UPDATE_informix = "update si_partner_service_ability set partner_service_ability_id=?, service_code=?, partner_id=?, state=?, state_date=current, eff_date=?, exp_date=? where 1=1 ";
	

	//	普通delete SQL
	private String SQL_DELETE = "delete from si_partner_service_ability where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from si_partner_service_ability where partner_service_ability_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update si_partner_service_ability set partner_service_ability_id=?, service_code=?, partner_id=?, state=?, state_date=?, eff_date=?, exp_date=? where partner_service_ability_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select partner_service_ability_id,service_code,partner_id,state,state_date,eff_date,exp_date " +
									" from si_partner_service_ability where partner_service_ability_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;

	private String BATCH_DELETE = "delete from PARTNER_SERVICE_ABILITY a where a.partner_id=?"  ;
	
	public  String getBatchDeleteSQL() {
		return this.BATCH_DELETE ;
	}

	public PartnerServAbilityDAO() {

	}
	
	/**
	 * 对于oracle和informix有差别的sql，使用这种方式替换
	 *
	 */
	static{
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			SQL_UPDATE=SQL_UPDATE_informix;
			SQL_INSERT=SQL_INSERT_informix;
		} else if (CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			
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
							
		params.add(map.get("partner_service_ability_id")) ;
									
		params.add(map.get("service_code")) ;
									
		params.add(map.get("partner_id")) ;
									
		params.add(map.get("state")) ;
						
//		params.add("sysdate") ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
						
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
				
					
		params.add(vo.get("partner_service_ability_id")) ;
						
					
		params.add(vo.get("service_code")) ;
						
					
		params.add(vo.get("partner_id")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
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
		
		params.add(vo.get("partner_service_ability_id")) ;
									
		params.add(vo.get("service_code")) ;
									
		params.add(vo.get("partner_id")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
							
		params.add(keyCondMap.get("partner_service_ability_id")) ;
						
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
							
		params.add(keyCondMap.get("partner_service_ability_id")) ;
						
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
