package com.ztesoft.vsop.simulate.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.common.util.PageModel;

public class SiProdOffDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_offer_id,fee_set_flag,prod_offer_sub_type,prod_offer_name,state_date,prod_offer_publisher,state,eff_date,exp_date,manage_grade,offer_nbr,offer_provider_id,brand_id,serv_brand_id,templet_id,default_time_period,offer_desc,pricing_desc,pname_cn,pname_en,pdes_cn,pdes_en,chargingpolicy_cn,chargingpolicy_id,sub_option,present_option,corp_only,scope,package_host from PROD_OFFER where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from prod_offer where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PROD_OFFER (prod_offer_id, fee_set_flag, prod_offer_sub_type, prod_offer_name, state_date, prod_offer_publisher, state, eff_date, exp_date, manage_grade, offer_nbr, offer_provider_id, brand_id, serv_brand_id, templet_id, default_time_period, offer_desc, pricing_desc, pname_cn, pname_en, pdes_cn, pdes_en, chargingpolicy_cn, chargingpolicy_id, sub_option, present_option, corp_only, scope, package_host) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PROD_OFFER set prod_offer_id=?, fee_set_flag=?, prod_offer_sub_type=?, prod_offer_name=?, state_date=?, prod_offer_publisher=?, state=?, eff_date=?, exp_date=?, manage_grade=?, offer_nbr=?, offer_provider_id=?, brand_id=?, serv_brand_id=?, templet_id=?, default_time_period=?, offer_desc=?, pricing_desc=?, pname_cn=?, pname_en=?, pdes_cn=?, pdes_en=?, chargingpolicy_cn=?, chargingpolicy_id=?, sub_option=?, present_option=?, corp_only=?, scope=?, package_host=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PROD_OFFER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PROD_OFFER where prod_offer_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PROD_OFFER set prod_offer_id=?, fee_set_flag=?, prod_offer_sub_type=?, prod_offer_name=?, state_date=?, prod_offer_publisher=?, state=?, eff_date=?, exp_date=?, manage_grade=?, offer_nbr=?, offer_provider_id=?, brand_id=?, serv_brand_id=?, templet_id=?, default_time_period=?, offer_desc=?, pricing_desc=?, pname_cn=?, pname_en=?, pdes_cn=?, pdes_en=?, chargingpolicy_cn=?, chargingpolicy_id=?, sub_option=?, present_option=?, corp_only=?, scope=?, package_host=? where prod_offer_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_offer_id,fee_set_flag,prod_offer_sub_type,prod_offer_name,state_date,prod_offer_publisher,state,eff_date,exp_date,manage_grade,offer_nbr,offer_provider_id,brand_id,serv_brand_id,templet_id,default_time_period,offer_desc,pricing_desc,pname_cn,pname_en,pdes_cn,pdes_en,chargingpolicy_cn,chargingpolicy_id,sub_option,present_option,corp_only,scope,package_host from PROD_OFFER where prod_offer_id=? ";

	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SiProdOffDAO() {

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
				
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOffId = seqDao.getNextSequence("SEQ_PROD_OFFER_ID");
		params.add(prodOffId);
		
		//params.add(map.get("prod_offer_id")) ;
									
		params.add(map.get("fee_set_flag")) ;
									
		params.add(map.get("prod_offer_sub_type")) ;
									
		params.add(map.get("prod_offer_name")) ;
						
		//params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());		
		
		params.add(map.get("prod_offer_publisher")) ;
									
		params.add(map.get("state")) ;
						
		//暂把生效时间和实效时间都设置为系统当前时间，尚不知逻辑如何	
		//params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());				
		
		//params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());	
									
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
	
	public String getSqlForProdOff(String prodOffId) {		
		String sql = "select p.prod_offer_id,p.offer_nbr,p.prod_offer_name,p.fee_set_flag,p.prod_offer_sub_type, p.offer_desc " +
			"from prod_offer p where p.prod_offer_id <> " + prodOffId + " and p.prod_offer_id not in " +
			"(select t.offer_z_id from prod_offer_rel t where t.offer_a_id = "+ prodOffId +")";
		return sql;
	}
	
	public String getSqlForProdOffCount(String prodOffId) {
		String sql_count = "select count(*) as col_counts from prod_offer p where p.prod_offer_id <> " 
			+ prodOffId + " and p.prod_offer_id not in " +
			"(select t.offer_z_id from prod_offer_rel t where t.offer_a_id = "+ prodOffId +")";
		return sql_count;
	}
	
	/**
	 * 返回分页查询结果
	 * @param whereCond 查询条件
	 * @param para 条件参数集合
	 * @param pageIndex 开始数据索引
	 * @param pageSize 每页数据
	 * @return
	 * @throws FrameException
	 * 
	 */
	public PageModel searchByCond(String prodOffId, String whereCond,List para,int pageIndex, int pageSize) throws FrameException {
		String querySQL= getSqlForProdOff(prodOffId) + whereCond;
		String countSQL= getSqlForProdOffCount(prodOffId) + whereCond;

		DynamicDict dto = Base.queryPage(this.getDbName(), querySQL, countSQL, para, pageIndex, pageSize, 
				null,Const.ACTION_RESULT, 1, Const.UN_JUDGE_ERROR, "") ;
		return DataTranslate._PageModel(dto.m_Values.get(Const.ACTION_RESULT)) ;
	}	
	
	/**
	 * 执行新增销售品操作，若新增成功，则返回该销售品的id值
	 * @param vo
	 * @return
	 * @throws FrameException
	 */
	public String insertProdOff(Map vo) throws FrameException {
		Map t = vo;
		List insertParams = translateInsertParams(t);
		String prodOfferId = String.valueOf(insertParams.get(0));
		String SQL = getInsertSQL() ;
		if (Base.update(this.getDbName(), SQL, insertParams, 1, Const.UN_JUDGE_ERROR, "") > 0) {
			return prodOfferId;
		} else {
			return null;
		}
	}
}
