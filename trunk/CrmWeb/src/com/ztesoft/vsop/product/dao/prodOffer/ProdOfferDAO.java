package com.ztesoft.vsop.product.dao.prodOffer;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProdOfferDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_offer_id,fee_set_flag,prod_offer_sub_type,prod_offer_name,state_date,prod_offer_publisher,state,eff_date,exp_date,manage_grade,offer_nbr,offer_provider_id,brand_id,serv_brand_id,templet_id,default_time_period,offer_desc,pricing_desc,pname_cn,pname_en,pdes_cn,pdes_en,chargingpolicy_cn,chargingpolicy_id,sub_option,present_option,corp_only,scope,package_host,offer_code from PROD_OFFER where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from prod_offer where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PROD_OFFER (prod_offer_id, fee_set_flag, prod_offer_sub_type, prod_offer_name, state_date, prod_offer_publisher, state, eff_date, exp_date, manage_grade, offer_nbr, offer_provider_id, brand_id, serv_brand_id, templet_id, default_time_period, offer_desc, pricing_desc, pname_cn, pname_en, pdes_cn, pdes_en, chargingpolicy_cn, chargingpolicy_id, sub_option, present_option, corp_only, scope, package_host, offer_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PROD_OFFER set prod_offer_id=?, fee_set_flag=?, prod_offer_sub_type=?, prod_offer_name=?, state_date=?, prod_offer_publisher=?, state=?, eff_date=?, exp_date=?, manage_grade=?, offer_nbr=?, offer_provider_id=?, brand_id=?, serv_brand_id=?, templet_id=?, default_time_period=?, offer_desc=?, pricing_desc=?, pname_cn=?, pname_en=?, pdes_cn=?, pdes_en=?, chargingpolicy_cn=?, chargingpolicy_id=?, sub_option=?, present_option=?, corp_only=?, scope=?, package_host=?, offer_code=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PROD_OFFER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PROD_OFFER where prod_offer_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PROD_OFFER set prod_offer_id=?, fee_set_flag=?, prod_offer_sub_type=?, prod_offer_name=?, state_date=?, prod_offer_publisher=?, state=?, eff_date=?, exp_date=?, manage_grade=?, offer_nbr=?, offer_provider_id=?, brand_id=?, serv_brand_id=?, templet_id=?, default_time_period=?, offer_desc=?, pricing_desc=?, pname_cn=?, pname_en=?, pdes_cn=?, pdes_en=?, chargingpolicy_cn=?, chargingpolicy_id=?, sub_option=?, present_option=?, corp_only=?, scope=?, package_host=?, offer_code=? where prod_offer_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_offer_id,fee_set_flag,prod_offer_sub_type,prod_offer_name,state_date,prod_offer_publisher,state,eff_date,exp_date,manage_grade,offer_nbr,offer_provider_id,brand_id,serv_brand_id,templet_id,default_time_period,offer_desc,pricing_desc,pname_cn,pname_en,pdes_cn,pdes_en,chargingpolicy_cn,chargingpolicy_id,sub_option,present_option,corp_only,scope,package_host,offer_code from PROD_OFFER where prod_offer_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public ProdOfferDAO() {

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
							
		params.add(map.get("prod_offer_id")) ;
									
		params.add(map.get("fee_set_flag")) ;
									
		params.add(map.get("prod_offer_sub_type")) ;
									
		params.add(map.get("prod_offer_name")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("prod_offer_publisher")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("manage_grade")) ;
									
		params.add(map.get("offer_nbr")) ;
									
		params.add(map.get("offer_provider_id")) ;
									
		params.add(map.get("brand_id")) ;
									
		params.add(map.get("serv_brand_id")) ;
									
		params.add(map.get("templet_id")) ;
									
		params.add(map.get("default_time_period")) ;
									
		params.add(map.get("offer_desc")) ;
									
		params.add(map.get("pricing_desc")) ;
									
		params.add(map.get("pname_cn")) ;
									
		params.add(map.get("pname_en")) ;
									
		params.add(map.get("pdes_cn")) ;
									
		params.add(map.get("pdes_en")) ;
									
		params.add(map.get("chargingpolicy_cn")) ;
									
		params.add(map.get("chargingpolicy_id")) ;
									
		params.add(map.get("sub_option")) ;
									
		params.add(map.get("present_option")) ;
									
		params.add(map.get("corp_only")) ;
									
		params.add(map.get("scope")) ;
									
		params.add(map.get("package_host")) ;
									
		params.add(map.get("offer_code")) ;
						
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
				
					
		params.add(vo.get("prod_offer_id")) ;
						
					
		params.add(vo.get("fee_set_flag")) ;
						
					
		params.add(vo.get("prod_offer_sub_type")) ;
						
					
		params.add(vo.get("prod_offer_name")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("prod_offer_publisher")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("manage_grade")) ;
						
					
		params.add(vo.get("offer_nbr")) ;
						
					
		params.add(vo.get("offer_provider_id")) ;
						
					
		params.add(vo.get("brand_id")) ;
						
					
		params.add(vo.get("serv_brand_id")) ;
						
					
		params.add(vo.get("templet_id")) ;
						
					
		params.add(vo.get("default_time_period")) ;
						
					
		params.add(vo.get("offer_desc")) ;
						
					
		params.add(vo.get("pricing_desc")) ;
						
					
		params.add(vo.get("pname_cn")) ;
						
					
		params.add(vo.get("pname_en")) ;
						
					
		params.add(vo.get("pdes_cn")) ;
						
					
		params.add(vo.get("pdes_en")) ;
						
					
		params.add(vo.get("chargingpolicy_cn")) ;
						
					
		params.add(vo.get("chargingpolicy_id")) ;
						
					
		params.add(vo.get("sub_option")) ;
						
					
		params.add(vo.get("present_option")) ;
						
					
		params.add(vo.get("corp_only")) ;
						
					
		params.add(vo.get("scope")) ;
						
					
		params.add(vo.get("package_host")) ;
						
					
		params.add(vo.get("offer_code")) ;
						
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
							
		params.add(vo.get("prod_offer_id")) ;
									
		params.add(vo.get("fee_set_flag")) ;
									
		params.add(vo.get("prod_offer_sub_type")) ;
									
		params.add(vo.get("prod_offer_name")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("prod_offer_publisher")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("manage_grade")) ;
									
		params.add(vo.get("offer_nbr")) ;
									
		params.add(vo.get("offer_provider_id")) ;
									
		params.add(vo.get("brand_id")) ;
									
		params.add(vo.get("serv_brand_id")) ;
									
		params.add(vo.get("templet_id")) ;
									
		params.add(vo.get("default_time_period")) ;
									
		params.add(vo.get("offer_desc")) ;
									
		params.add(vo.get("pricing_desc")) ;
									
		params.add(vo.get("pname_cn")) ;
									
		params.add(vo.get("pname_en")) ;
									
		params.add(vo.get("pdes_cn")) ;
									
		params.add(vo.get("pdes_en")) ;
									
		params.add(vo.get("chargingpolicy_cn")) ;
									
		params.add(vo.get("chargingpolicy_id")) ;
									
		params.add(vo.get("sub_option")) ;
									
		params.add(vo.get("present_option")) ;
									
		params.add(vo.get("corp_only")) ;
									
		params.add(vo.get("scope")) ;
									
		params.add(vo.get("package_host")) ;
									
		params.add(vo.get("offer_code")) ;
						
							
		params.add(keyCondMap.get("prod_offer_id")) ;
						
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
							
		params.add(keyCondMap.get("prod_offer_id")) ;
						
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
