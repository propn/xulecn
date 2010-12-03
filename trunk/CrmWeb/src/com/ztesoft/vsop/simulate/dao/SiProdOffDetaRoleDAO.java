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

public class SiProdOffDetaRoleDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_offer_role_cd,role_name,up_prod_offer_role_cd,prod_offer_id,role_down_num,role_up_num,state,create_date,state_date,rule_type,min_count,max_count,product_id,(select PRODUCT_NAME from PRODUCT p where p.PRODUCT_ID = a.product_id) product_name from PROD_OFFER_DETAIL_ROLE a where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from prod_offer_detail_role where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PROD_OFFER_DETAIL_ROLE (prod_offer_role_cd, role_name, up_prod_offer_role_cd, prod_offer_id, role_down_num, role_up_num, state, create_date, state_date, rule_type, min_count, max_count, product_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PROD_OFFER_DETAIL_ROLE set prod_offer_role_cd=?, role_name=?, up_prod_offer_role_cd=?, prod_offer_id=?, role_down_num=?, role_up_num=?, state=?, create_date=?, state_date=?, rule_type=?, min_count=?, max_count=?, product_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PROD_OFFER_DETAIL_ROLE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PROD_OFFER_DETAIL_ROLE where prod_offer_role_cd=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PROD_OFFER_DETAIL_ROLE set prod_offer_role_cd=?, role_name=?, up_prod_offer_role_cd=?, prod_offer_id=?, role_down_num=?, role_up_num=?, state=?, create_date=?, state_date=?, rule_type=?, min_count=?, max_count=?, product_id=? where prod_offer_role_cd=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_offer_role_cd,role_name,up_prod_offer_role_cd,prod_offer_id,role_down_num,role_up_num,state,create_date,state_date,rule_type,min_count,max_count,product_id, (select PRODUCT_NAME from PRODUCT p where p.PRODUCT_ID = a.product_id) product_name from PROD_OFFER_DETAIL_ROLE a where prod_offer_role_cd=? ";
	
	private String SQL_SELECT_BY_PROD_OFFER_ID = "select prod_offer_role_cd,role_name,up_prod_offer_role_cd,prod_offer_id,role_down_num,role_up_num,state,create_date,state_date,rule_type,min_count,max_count,product_id from PROD_OFFER_DETAIL_ROLE t where t.prod_offer_id=?";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SiProdOffDetaRoleDAO() {

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
							
		//params.add(map.get("prod_offer_role_cd")) ;
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOfferRoleId = seqDao.getNextSequence("SEQ_PROD_OFFER_ROLE_CD");
		params.add(prodOfferRoleId);
									
		params.add(map.get("role_name")) ;
									
		params.add(map.get("up_prod_offer_role_cd")) ;
									
		params.add(map.get("prod_offer_id")) ;
									
		params.add(map.get("role_down_num")) ;
									
		params.add(map.get("role_up_num")) ;
									
		params.add(map.get("state")) ;
		
		//暂把生效时间和实效时间都设置为系统当前时间，尚不知逻辑如何				
		//params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());			
		//params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());
		
		params.add(map.get("rule_type")) ;
									
		params.add(map.get("min_count")) ;
									
		params.add(map.get("max_count")) ;
									
		params.add(map.get("product_id")) ;
						
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
				
					
		params.add(vo.get("prod_offer_role_cd")) ;
						
					
		params.add(vo.get("role_name")) ;
						
					
		params.add(vo.get("up_prod_offer_role_cd")) ;
						
					
		params.add(vo.get("prod_offer_id")) ;
						
					
		params.add(vo.get("role_down_num")) ;
						
					
		params.add(vo.get("role_up_num")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("rule_type")) ;
						
					
		params.add(vo.get("min_count")) ;
						
					
		params.add(vo.get("max_count")) ;
						
					
		params.add(vo.get("product_id")) ;
						
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
							
		params.add(vo.get("prod_offer_role_cd")) ;
									
		params.add(vo.get("role_name")) ;
									
		params.add(vo.get("up_prod_offer_role_cd")) ;
									
		params.add(vo.get("prod_offer_id")) ;
									
		params.add(vo.get("role_down_num")) ;
									
		params.add(vo.get("role_up_num")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("rule_type")) ;
									
		params.add(vo.get("min_count")) ;
									
		params.add(vo.get("max_count")) ;
									
		params.add(vo.get("product_id")) ;
						
							
		params.add(keyCondMap.get("prod_offer_role_cd")) ;
						
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
							
		params.add(keyCondMap.get("prod_offer_role_cd")) ;
						
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
	
	public String getSQLByProdOfferId() {
		return this.SQL_SELECT_BY_PROD_OFFER_ID;
	}
	
}
