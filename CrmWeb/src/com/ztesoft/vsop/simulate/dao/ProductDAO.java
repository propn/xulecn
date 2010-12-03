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
import com.ztesoft.vsop.product.vo.ProductType;

public class ProductDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select product_id,product_nbr,product_name,product_desc,manage_grade,prod_bundle_type,product_provider_id,(select t.partner_name from si_partner t where t.partner_id=product_provider_id) product_provider_name,product_state_cd,state_date,eff_date,exp_date,create_date,prod_func_type,order_host,charging_policy_cn,charging_policy_id,sub_option,present,corp_only,package_only,settlement_cycle,settlement_paytype,settlement_percent,scope,system_code from si_PRODUCT p where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_product where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into si_PRODUCT (product_id, product_nbr, product_name, product_desc, manage_grade, prod_bundle_type, product_provider_id, product_state_cd, state_date, eff_date, exp_date, create_date, prod_func_type, order_host, charging_policy_cn, charging_policy_id, sub_option, present, corp_only, package_only, settlement_cycle, settlement_paytype, settlement_percent, scope, system_code) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update si_PRODUCT set product_id=?, product_nbr=?, product_name=?, product_desc=?, manage_grade=?, prod_bundle_type=?, product_provider_id=?, product_state_cd=?, state_date=?, eff_date=?, exp_date=?, create_date=?, prod_func_type=?, order_host=?, charging_policy_cn=?, charging_policy_id=?, sub_option=?, present=?, corp_only=?, package_only=?, settlement_cycle=?, settlement_paytype=?, settlement_percent=?, scope=?, system_code=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from si_PRODUCT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from si_PRODUCT where product_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update si_PRODUCT set product_id=?, product_nbr=?, product_name=?, product_desc=?, manage_grade=?, prod_bundle_type=?, product_provider_id=?, product_state_cd=?, state_date=?, eff_date=?, exp_date=?, create_date=?, prod_func_type=?, order_host=?, charging_policy_cn=?, charging_policy_id=?, sub_option=?, present=?, corp_only=?, package_only=?, settlement_cycle=?, settlement_paytype=?, settlement_percent=?, scope=?, system_code=? where product_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select product_id,product_nbr,product_name,product_desc,manage_grade,prod_bundle_type,product_provider_id,(select t.partner_name from si_partner t where t.partner_id=product_provider_id) product_provider_name,product_state_cd,state_date,eff_date,exp_date,create_date,prod_func_type,order_host,charging_policy_cn,charging_policy_id,sub_option,present,corp_only,package_only,settlement_cycle,settlement_paytype,settlement_percent,scope,system_code from si_PRODUCT where product_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;

	
	private String productTypeSQL = "select t.product_type_id,t.product_type_name,t.service_code from si_product_type t" ;

	private String existsRow = "select count(1) existRow from si_product t where t.product_nbr=? and t.system_code=? " ;
		
	
	public boolean checkExistsRow(String product_nbr ,String system_code , String product_id) throws Exception{
		if(product_id != null && 
				!"".equals(product_id)){
			existsRow+=" and product_id<>?" ;
			String[] para = {product_nbr ,system_code , product_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}else{
			String[] para =  {product_nbr ,system_code}  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}
	}
	
	public ProductDAO() {

	}

	/**
	 * 加载产品类型列表
	 * @return
	 * @throws Exception
	 */
	public ArrayList loadProductType(String typeId) throws Exception {
		//type = -1 时，标识根节点(增值产品)
		if("-1".equals(typeId) ){
			ProductType rootNode = new ProductType( "0" , "增值产品" , "0");
			ArrayList treeList = new ArrayList() ;
			treeList.add(rootNode) ;
			return treeList ;
		}
		if("0".equals(typeId)){
			return (ArrayList)this.findBySql(productTypeSQL) ;
		}
		return null ;
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
							
		params.add(map.get("product_id")) ;
									
		params.add(map.get("product_nbr")) ;
									
		params.add(map.get("product_name")) ;
									
		params.add(map.get("product_desc")) ;
									
		params.add(map.get("manage_grade")) ;
									
		params.add(map.get("prod_bundle_type")) ;
									
		params.add(map.get("product_provider_id")) ;
									
		params.add(map.get("product_state_cd")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
									
		params.add(map.get("prod_func_type")) ;
									
		params.add(map.get("order_host")) ;
									
		params.add(map.get("charging_policy_cn")) ;
									
		params.add(map.get("charging_policy_id")) ;
									
		params.add(map.get("sub_option")) ;
									
		params.add(map.get("present")) ;
									
		params.add(map.get("corp_only")) ;
									
		params.add(map.get("package_only")) ;
									
		params.add(map.get("settlement_cycle")) ;
									
		params.add(map.get("settlement_paytype")) ;
									
		params.add(map.get("settlement_percent")) ;
									
		params.add(map.get("scope")) ;
									
		params.add(map.get("system_code")) ;
						
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
				
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("product_nbr")) ;
						
					
		params.add(vo.get("product_name")) ;
						
					
		params.add(vo.get("product_desc")) ;
						
					
		params.add(vo.get("manage_grade")) ;
						
					
		params.add(vo.get("prod_bundle_type")) ;
						
					
		params.add(vo.get("product_provider_id")) ;
						
					
		params.add(vo.get("product_state_cd")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("prod_func_type")) ;
						
					
		params.add(vo.get("order_host")) ;
						
					
		params.add(vo.get("charging_policy_cn")) ;
						
					
		params.add(vo.get("charging_policy_id")) ;
						
					
		params.add(vo.get("sub_option")) ;
						
					
		params.add(vo.get("present")) ;
						
					
		params.add(vo.get("corp_only")) ;
						
					
		params.add(vo.get("package_only")) ;
						
					
		params.add(vo.get("settlement_cycle")) ;
						
					
		params.add(vo.get("settlement_paytype")) ;
						
					
		params.add(vo.get("settlement_percent")) ;
						
					
		params.add(vo.get("scope")) ;
						
					
		params.add(vo.get("system_code")) ;
						
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
							
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("product_nbr")) ;
									
		params.add(vo.get("product_name")) ;
									
		params.add(vo.get("product_desc")) ;
									
		params.add(vo.get("manage_grade")) ;
									
		params.add(vo.get("prod_bundle_type")) ;
									
		params.add(vo.get("product_provider_id")) ;
									
		params.add(vo.get("product_state_cd")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("prod_func_type")) ;
									
		params.add(vo.get("order_host")) ;
									
		params.add(vo.get("charging_policy_cn")) ;
									
		params.add(vo.get("charging_policy_id")) ;
									
		params.add(vo.get("sub_option")) ;
									
		params.add(vo.get("present")) ;
									
		params.add(vo.get("corp_only")) ;
									
		params.add(vo.get("package_only")) ;
									
		params.add(vo.get("settlement_cycle")) ;
									
		params.add(vo.get("settlement_paytype")) ;
									
		params.add(vo.get("settlement_percent")) ;
									
		params.add(vo.get("scope")) ;
									
		params.add(vo.get("system_code")) ;
						
							
		params.add(keyCondMap.get("product_id")) ;
						
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
							
		params.add(keyCondMap.get("product_id")) ;
						
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
