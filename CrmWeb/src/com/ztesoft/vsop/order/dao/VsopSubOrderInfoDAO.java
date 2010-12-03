package com.ztesoft.vsop.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class VsopSubOrderInfoDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select sub_order_id,order_id,product_id,acc_nbr,order_type,acct_type,service_id,product_offer_id,package_id,eff_time,exp_time,is_cancel,state_time,create_time,sub_order_state,result_type,result_info,lan_id from VSOP_SUB_ORDER_INFO where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from vsop_sub_order_info where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into VSOP_SUB_ORDER_INFO (sub_order_id, order_id, product_id, acc_nbr, order_type, acct_type, service_id, product_offer_id, package_id, eff_time, exp_time, is_cancel, state_time, create_time, sub_order_state, result_type, result_info, lan_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update VSOP_SUB_ORDER_INFO set sub_order_id=?, order_id=?, product_id=?, acc_nbr=?, order_type=?, acct_type=?, service_id=?, product_offer_id=?, package_id=?, eff_time=?, exp_time=?, is_cancel=?, state_time=?, create_time=?, sub_order_state=?, result_type=?, result_info=?, lan_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from VSOP_SUB_ORDER_INFO where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from VSOP_SUB_ORDER_INFO where sub_order_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update VSOP_SUB_ORDER_INFO set sub_order_id=?, order_id=?, product_id=?, acc_nbr=?, order_type=?, acct_type=?, service_id=?, product_offer_id=?, package_id=?, eff_time=?, exp_time=?, is_cancel=?, state_time=?, create_time=?, sub_order_state=?, result_type=?, result_info=?, lan_id=? where sub_order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select sub_order_id,order_id,product_id,acc_nbr,order_type,acct_type,service_id,product_offer_id,package_id,eff_time,exp_time,is_cancel,state_time,create_time,sub_order_state,result_type,result_info,lan_id from VSOP_SUB_ORDER_INFO where sub_order_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;
	public static final String SEQUENCE = "SEQ_SUB_ORDER_INFO_ID";


	public VsopSubOrderInfoDAO() {

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
							
		params.add(map.get("sub_order_id")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("acc_nbr")) ;
									
		params.add(map.get("order_type")) ;
									
		params.add(map.get("acct_type")) ;
									
		params.add(map.get("service_id")) ;
									
		params.add(map.get("product_offer_id")) ;
									
		params.add(map.get("package_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_time" ))) ;
									
		params.add(map.get("is_cancel")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_time" ))) ;
									
		params.add(map.get("sub_order_state")) ;
									
		params.add(map.get("result_type")) ;
									
		params.add(map.get("result_info")) ;
									
		params.add(map.get("lan_id")) ;
						
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
				
					
		params.add(vo.get("sub_order_id")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("acc_nbr")) ;
						
					
		params.add(vo.get("order_type")) ;
						
					
		params.add(vo.get("acct_type")) ;
						
					
		params.add(vo.get("service_id")) ;
						
					
		params.add(vo.get("product_offer_id")) ;
						
					
		params.add(vo.get("package_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_time" ))) ;
						
					
		params.add(vo.get("is_cancel")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_time" ))) ;
						
					
		params.add(vo.get("sub_order_state")) ;
						
					
		params.add(vo.get("result_type")) ;
						
					
		params.add(vo.get("result_info")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
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
							
		params.add(vo.get("sub_order_id")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("acc_nbr")) ;
									
		params.add(vo.get("order_type")) ;
									
		params.add(vo.get("acct_type")) ;
									
		params.add(vo.get("service_id")) ;
									
		params.add(vo.get("product_offer_id")) ;
									
		params.add(vo.get("package_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_time" ))) ;
									
		params.add(vo.get("is_cancel")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_time" ))) ;
									
		params.add(vo.get("sub_order_state")) ;
									
		params.add(vo.get("result_type")) ;
									
		params.add(vo.get("result_info")) ;
									
		params.add(vo.get("lan_id")) ;
						
							
		params.add(keyCondMap.get("sub_order_id")) ;
						
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
							
		params.add(keyCondMap.get("sub_order_id")) ;
						
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
