package com.ztesoft.vsop.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductServAbilityRelDAO  extends AbstractProdDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select a.product_service_ability_rel_id,a.product_id,a.service_code,a.rel_type,s.service_name from PRODUCT_SERVICE_ABILITY_REL a inner join SERVICE_ABILITY s on a.service_code=s.service_code where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from (select a.product_service_ability_rel_id,a.product_id,a.service_code,a.rel_type,s.service_name from PRODUCT_SERVICE_ABILITY_REL a inner join SERVICE_ABILITY s on a.service_code=s.service_code ) where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_SERVICE_ABILITY_REL (product_service_ability_rel_id, product_id, service_code, rel_type) values (?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update PRODUCT_SERVICE_ABILITY_REL set product_service_ability_rel_id=?, product_id=?, service_code=?, rel_type=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PRODUCT_SERVICE_ABILITY_REL where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_SERVICE_ABILITY_REL where product_service_ability_rel_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_SERVICE_ABILITY_REL set product_service_ability_rel_id=?, product_id=?, service_code=?, rel_type=? where product_service_ability_rel_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select product_service_ability_rel_id,product_id,service_code,rel_type from PRODUCT_SERVICE_ABILITY_REL where product_service_ability_rel_id=? ";
	
	private String existsRow = "select count(*) existsRow from product_service_ability_rel r where r.service_code=? and r.product_id=? " ;
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.VSOP_DATASOURCE ;

	protected String BATCH_DELETE = "delete from product_service_ability_rel where product_id=?" ;

	public String getBatchDeleteSQL(){
		return BATCH_DELETE ;
	}
	public ProductServAbilityRelDAO() {

	}
	
	public boolean checkExistsRow(String service_code ,String product_id , String product_service_ability_rel_id) throws Exception{
		if(product_service_ability_rel_id != null && 
				!"".equals(product_service_ability_rel_id)){
			existsRow+=" and product_service_ability_rel_id<>?" ;
			String[] para = {service_code ,product_id , product_service_ability_rel_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
		}else{
			String[] para =  {service_code ,product_id }  ;
			return Base.query_int(dbName,  Const.UN_JUDGE_ERROR, existsRow,para) > 0 ;
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
							
		params.add(map.get("product_service_ability_rel_id")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("service_code")) ;
									
		params.add(map.get("rel_type")) ;
						
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
				
					
		params.add(vo.get("product_service_ability_rel_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("service_code")) ;
						
					
		params.add(vo.get("rel_type")) ;
						
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
							
		params.add(vo.get("product_service_ability_rel_id")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("service_code")) ;
									
		params.add(vo.get("rel_type")) ;
						
							
		params.add(keyCondMap.get("product_service_ability_rel_id")) ;
						
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
							
		params.add(keyCondMap.get("product_service_ability_rel_id")) ;
						
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
