package com.ztesoft.vsop.spManage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.product.dao.AbstractProdDAO;

public class PartnerServAbilityDAO extends AbstractProdDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select partner_service_ability_id,service_code,partner_id,state,state_date,eff_date,exp_date " +
									" from PARTNER_SERVICE_ABILITY where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from partner_service_ability where 1=1 ";

	//	insert SQl
	private static String SQL_INSERT = "insert into PARTNER_SERVICE_ABILITY (partner_service_ability_id, service_code, partner_id, state, state_date, eff_date, exp_date) values (?, ?, ?, ?, sysdate, ?, ?)";

	//֧��informix
	private static String SQL_INSERT_informix = "insert into PARTNER_SERVICE_ABILITY (partner_service_ability_id, service_code, partner_id, state, state_date, eff_date, exp_date) values (?, ?, ?, ?, current, ?, ?)";

	//	��ͨupdate SQL
	private static String SQL_UPDATE = "update PARTNER_SERVICE_ABILITY set partner_service_ability_id=?, service_code=?, partner_id=?, state=?, state_date=sysdate, eff_date=?, exp_date=? where 1=1 ";

	//	��ͨupdate SQL
	private static String SQL_UPDATE_informix = "update PARTNER_SERVICE_ABILITY set partner_service_ability_id=?, service_code=?, partner_id=?, state=?, state_date=current, eff_date=?, exp_date=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PARTNER_SERVICE_ABILITY where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PARTNER_SERVICE_ABILITY where partner_service_ability_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PARTNER_SERVICE_ABILITY set partner_service_ability_id=?, service_code=?, partner_id=?, state=?, state_date=?, eff_date=?, exp_date=? where partner_service_ability_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select partner_service_ability_id,service_code,partner_id,state,state_date,eff_date,exp_date " +
									" from PARTNER_SERVICE_ABILITY where partner_service_ability_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.VSOP_DATASOURCE ;

	private String BATCH_DELETE = "delete from PARTNER_SERVICE_ABILITY a where a.partner_id=?"  ;
	
	public  String getBatchDeleteSQL() {
		return this.BATCH_DELETE ;
	}

	public PartnerServAbilityDAO() {

	}
	
	/**
	 * ����oracle��informix�в���sql��ʹ�����ַ�ʽ�滻
	 * ���� 
	 */
	static{
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			SQL_UPDATE=SQL_UPDATE_informix;
			SQL_INSERT=SQL_INSERT_informix;
		} else if (CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			
		}
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
							
		params.add(map.get("partner_service_ability_id")) ;
									
		params.add(map.get("service_code")) ;
									
		params.add(map.get("partner_id")) ;
									
		params.add(map.get("state")) ;
						
//		params.add("current") ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
						
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
