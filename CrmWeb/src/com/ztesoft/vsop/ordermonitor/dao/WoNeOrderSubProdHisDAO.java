package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class WoNeOrderSubProdHisDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ne_sub_id,ne_order_id,sys_code,product_code,act_type,product_offer_id,result_code,result_msg,effect_time,exp_time,create_time" +
	",(SELECT B.PRODUCT_NAME FROM PRODUCT B WHERE B.PRODUCT_NBR = A.PRODUCT_CODE) PRODUCT_NAME from WO_NE_ORDER_SUB_PROD_HIS a where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_ne_order_sub_prod_his where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_NE_ORDER_SUB_PROD_HIS (ne_sub_id, ne_order_id, sys_code, product_code, act_type, product_offer_id, result_code, result_msg, effect_time, exp_time, create_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_NE_ORDER_SUB_PROD_HIS set ne_sub_id=?, ne_order_id=?, sys_code=?, product_code=?, act_type=?, product_offer_id=?, result_code=?, result_msg=?, effect_time=?, exp_time=?, create_time=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_NE_ORDER_SUB_PROD_HIS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_NE_ORDER_SUB_PROD_HIS where ne_sub_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_NE_ORDER_SUB_PROD_HIS set ne_sub_id=?, ne_order_id=?, sys_code=?, product_code=?, act_type=?, product_offer_id=?, result_code=?, result_msg=?, effect_time=?, exp_time=?, create_time=? where ne_sub_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ne_sub_id,ne_order_id,sys_code,product_code,act_type,product_offer_id,result_code,result_msg,effect_time,exp_time,create_time from WO_NE_ORDER_SUB_PROD_HIS where ne_sub_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoNeOrderSubProdHisDAO() {

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
							
		params.add(map.get("ne_sub_id")) ;
									
		params.add(map.get("ne_order_id")) ;
									
		params.add(map.get("sys_code")) ;
									
		params.add(map.get("product_code")) ;
									
		params.add(map.get("act_type")) ;
									
		params.add(map.get("product_offer_id")) ;
									
		params.add(map.get("result_code")) ;
									
		params.add(map.get("result_msg")) ;
									
		params.add(map.get("effect_time")) ;
									
		params.add(map.get("exp_time")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_time" ))) ;
						
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
				
					
		params.add(vo.get("ne_sub_id")) ;
						
					
		params.add(vo.get("ne_order_id")) ;
						
					
		params.add(vo.get("sys_code")) ;
						
					
		params.add(vo.get("product_code")) ;
						
					
		params.add(vo.get("act_type")) ;
						
					
		params.add(vo.get("product_offer_id")) ;
						
					
		params.add(vo.get("result_code")) ;
						
					
		params.add(vo.get("result_msg")) ;
						
					
		params.add(vo.get("effect_time")) ;
						
					
		params.add(vo.get("exp_time")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_time" ))) ;
						
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
							
		params.add(vo.get("ne_sub_id")) ;
									
		params.add(vo.get("ne_order_id")) ;
									
		params.add(vo.get("sys_code")) ;
									
		params.add(vo.get("product_code")) ;
									
		params.add(vo.get("act_type")) ;
									
		params.add(vo.get("product_offer_id")) ;
									
		params.add(vo.get("result_code")) ;
									
		params.add(vo.get("result_msg")) ;
									
		params.add(vo.get("effect_time")) ;
									
		params.add(vo.get("exp_time")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_time" ))) ;
						
							
		params.add(keyCondMap.get("ne_sub_id")) ;
						
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
							
		params.add(keyCondMap.get("ne_sub_id")) ;
						
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
