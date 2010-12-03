package com.ztesoft.crm.customer.custinfo.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class PaymentPlanDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select payment_plan_id,seq_nbr,order_id,cust_order_id,action_type,record_eff_date,record_exp_date,state_date,acct_id,payment_method,priority,payment_supple_id,payment_account,payment_account_name,state from PAYMENT_PLAN where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from payment_plan where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PAYMENT_PLAN (payment_plan_id, seq_nbr, order_id, cust_order_id, action_type, record_eff_date, record_exp_date, state_date, acct_id, payment_method, priority, payment_supple_id, payment_account, payment_account_name, state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PAYMENT_PLAN set payment_plan_id=?, seq_nbr=?, order_id=?, cust_order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, acct_id=?, payment_method=?, priority=?, payment_supple_id=?, payment_account=?, payment_account_name=?, state=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PAYMENT_PLAN where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PAYMENT_PLAN where payment_plan_id=? and seq_nbr=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PAYMENT_PLAN set payment_plan_id=?, seq_nbr=?, order_id=?, cust_order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, acct_id=?, payment_method=?, priority=?, payment_supple_id=?, payment_account=?, payment_account_name=?, state=? where payment_plan_id=? and seq_nbr=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select payment_plan_id,seq_nbr,order_id,cust_order_id,action_type,record_eff_date,record_exp_date,state_date,acct_id,payment_method,priority,payment_supple_id,payment_account,payment_account_name,state from PAYMENT_PLAN where payment_plan_id=? and seq_nbr=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.CM_DATASOURCE ;


	public PaymentPlanDAO() {

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
							
		params.add(map.get("payment_plan_id")) ;
									
		params.add(map.get("seq_nbr")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("cust_order_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("record_eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("record_exp_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("acct_id")) ;
									
		params.add(map.get("payment_method")) ;
									
		params.add(map.get("priority")) ;
									
		params.add(map.get("payment_supple_id")) ;
									
		params.add(map.get("payment_account")) ;
									
		params.add(map.get("payment_account_name")) ;
									
		params.add(map.get("state")) ;
						
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
				
					
		params.add(vo.get("payment_plan_id")) ;
						
					
		params.add(vo.get("seq_nbr")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("cust_order_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("record_eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("record_exp_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("acct_id")) ;
						
					
		params.add(vo.get("payment_method")) ;
						
					
		params.add(vo.get("priority")) ;
						
					
		params.add(vo.get("payment_supple_id")) ;
						
					
		params.add(vo.get("payment_account")) ;
						
					
		params.add(vo.get("payment_account_name")) ;
						
					
		params.add(vo.get("state")) ;
						
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
							
		params.add(vo.get("payment_plan_id")) ;
									
		params.add(vo.get("seq_nbr")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("cust_order_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("record_eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("record_exp_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("acct_id")) ;
									
		params.add(vo.get("payment_method")) ;
									
		params.add(vo.get("priority")) ;
									
		params.add(vo.get("payment_supple_id")) ;
									
		params.add(vo.get("payment_account")) ;
									
		params.add(vo.get("payment_account_name")) ;
									
		params.add(vo.get("state")) ;
						
							
		params.add(keyCondMap.get("payment_plan_id")) ;
									
		params.add(keyCondMap.get("seq_nbr")) ;
						
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
							
		params.add(keyCondMap.get("payment_plan_id")) ;
									
		params.add(keyCondMap.get("seq_nbr")) ;
						
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
