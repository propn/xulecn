package com.ztesoft.crm.customer.custinfo.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class AcctDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select acct_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,serv_id,acct_name,acct_code,def_acct_flag,address_id,passwd,credit_limit_plan_id,staff_locked,balance,agreement_id,billing_flag_id,latn_id,party_id,bureau_no,owe_tel,post_name,post_code,bill_format_id,self_quota,bil_quota,no_stop_flag,pay_info,owe_units,cycle_type_id,notes,bill_post_mode,bill_post_addr,province_code,city_no,acct_type,grp_acct_id,state from ACCT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from acct where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ACCT (acct_id, seq_nbr, cust_order_id, order_id, action_type, record_eff_date, record_exp_date, state_date, cust_id, serv_id, acct_name, acct_code, def_acct_flag, address_id, passwd, credit_limit_plan_id, staff_locked, balance, agreement_id, billing_flag_id, latn_id, party_id, bureau_no, owe_tel, post_name, post_code, bill_format_id, self_quota, bil_quota, no_stop_flag, pay_info, owe_units, cycle_type_id, notes, bill_post_mode, bill_post_addr, province_code, city_no, acct_type, grp_acct_id, state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ACCT set acct_id=?, seq_nbr=?, cust_order_id=?, order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, cust_id=?, serv_id=?, acct_name=?, acct_code=?, def_acct_flag=?, address_id=?, passwd=?, credit_limit_plan_id=?, staff_locked=?, balance=?, agreement_id=?, billing_flag_id=?, latn_id=?, party_id=?, bureau_no=?, owe_tel=?, post_name=?, post_code=?, bill_format_id=?, self_quota=?, bil_quota=?, no_stop_flag=?, pay_info=?, owe_units=?, cycle_type_id=?, notes=?, bill_post_mode=?, bill_post_addr=?, province_code=?, city_no=?, acct_type=?, grp_acct_id=?, state=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ACCT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ACCT where acct_id=? and seq_nbr=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ACCT set acct_id=?, seq_nbr=?, cust_order_id=?, order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, cust_id=?, serv_id=?, acct_name=?, acct_code=?, def_acct_flag=?, address_id=?, passwd=?, credit_limit_plan_id=?, staff_locked=?, balance=?, agreement_id=?, billing_flag_id=?, latn_id=?, party_id=?, bureau_no=?, owe_tel=?, post_name=?, post_code=?, bill_format_id=?, self_quota=?, bil_quota=?, no_stop_flag=?, pay_info=?, owe_units=?, cycle_type_id=?, notes=?, bill_post_mode=?, bill_post_addr=?, province_code=?, city_no=?, acct_type=?, grp_acct_id=?, state=? where acct_id=? and seq_nbr=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select acct_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,serv_id,acct_name,acct_code,def_acct_flag,address_id,passwd,credit_limit_plan_id,staff_locked,balance,agreement_id,billing_flag_id,latn_id,party_id,bureau_no,owe_tel,post_name,post_code,bill_format_id,self_quota,bil_quota,no_stop_flag,pay_info,owe_units,cycle_type_id,notes,bill_post_mode,bill_post_addr,province_code,city_no,acct_type,grp_acct_id,state from ACCT where acct_id=? and seq_nbr=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.CM_DATASOURCE ;


	public AcctDAO() {

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
							
		params.add(map.get("acct_id")) ;
									
		params.add(map.get("seq_nbr")) ;
									
		params.add(map.get("cust_order_id")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("record_eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("record_exp_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("cust_id")) ;
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("acct_name")) ;
									
		params.add(map.get("acct_code")) ;
									
		params.add(map.get("def_acct_flag")) ;
									
		params.add(map.get("address_id")) ;
									
		params.add(map.get("passwd")) ;
									
		params.add(map.get("credit_limit_plan_id")) ;
									
		params.add(map.get("staff_locked")) ;
									
		params.add(map.get("balance")) ;
									
		params.add(map.get("agreement_id")) ;
									
		params.add(map.get("billing_flag_id")) ;
									
		params.add(map.get("latn_id")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("bureau_no")) ;
									
		params.add(map.get("owe_tel")) ;
									
		params.add(map.get("post_name")) ;
									
		params.add(map.get("post_code")) ;
									
		params.add(map.get("bill_format_id")) ;
									
		params.add(map.get("self_quota")) ;
									
		params.add(map.get("bil_quota")) ;
									
		params.add(map.get("no_stop_flag")) ;
									
		params.add(map.get("pay_info")) ;
									
		params.add(map.get("owe_units")) ;
									
		params.add(map.get("cycle_type_id")) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("bill_post_mode")) ;
									
		params.add(map.get("bill_post_addr")) ;
									
		params.add(map.get("province_code")) ;
									
		params.add(map.get("city_no")) ;
									
		params.add(map.get("acct_type")) ;
									
		params.add(map.get("grp_acct_id")) ;
									
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
				
					
		params.add(vo.get("acct_id")) ;
						
					
		params.add(vo.get("seq_nbr")) ;
						
					
		params.add(vo.get("cust_order_id")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("record_eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("record_exp_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("cust_id")) ;
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("acct_name")) ;
						
					
		params.add(vo.get("acct_code")) ;
						
					
		params.add(vo.get("def_acct_flag")) ;
						
					
		params.add(vo.get("address_id")) ;
						
					
		params.add(vo.get("passwd")) ;
						
					
		params.add(vo.get("credit_limit_plan_id")) ;
						
					
		params.add(vo.get("staff_locked")) ;
						
					
		params.add(vo.get("balance")) ;
						
					
		params.add(vo.get("agreement_id")) ;
						
					
		params.add(vo.get("billing_flag_id")) ;
						
					
		params.add(vo.get("latn_id")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("bureau_no")) ;
						
					
		params.add(vo.get("owe_tel")) ;
						
					
		params.add(vo.get("post_name")) ;
						
					
		params.add(vo.get("post_code")) ;
						
					
		params.add(vo.get("bill_format_id")) ;
						
					
		params.add(vo.get("self_quota")) ;
						
					
		params.add(vo.get("bil_quota")) ;
						
					
		params.add(vo.get("no_stop_flag")) ;
						
					
		params.add(vo.get("pay_info")) ;
						
					
		params.add(vo.get("owe_units")) ;
						
					
		params.add(vo.get("cycle_type_id")) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("bill_post_mode")) ;
						
					
		params.add(vo.get("bill_post_addr")) ;
						
					
		params.add(vo.get("province_code")) ;
						
					
		params.add(vo.get("city_no")) ;
						
					
		params.add(vo.get("acct_type")) ;
						
					
		params.add(vo.get("grp_acct_id")) ;
						
					
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
							
		params.add(vo.get("acct_id")) ;
									
		params.add(vo.get("seq_nbr")) ;
									
		params.add(vo.get("cust_order_id")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("record_eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("record_exp_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("cust_id")) ;
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("acct_name")) ;
									
		params.add(vo.get("acct_code")) ;
									
		params.add(vo.get("def_acct_flag")) ;
									
		params.add(vo.get("address_id")) ;
									
		params.add(vo.get("passwd")) ;
									
		params.add(vo.get("credit_limit_plan_id")) ;
									
		params.add(vo.get("staff_locked")) ;
									
		params.add(vo.get("balance")) ;
									
		params.add(vo.get("agreement_id")) ;
									
		params.add(vo.get("billing_flag_id")) ;
									
		params.add(vo.get("latn_id")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("bureau_no")) ;
									
		params.add(vo.get("owe_tel")) ;
									
		params.add(vo.get("post_name")) ;
									
		params.add(vo.get("post_code")) ;
									
		params.add(vo.get("bill_format_id")) ;
									
		params.add(vo.get("self_quota")) ;
									
		params.add(vo.get("bil_quota")) ;
									
		params.add(vo.get("no_stop_flag")) ;
									
		params.add(vo.get("pay_info")) ;
									
		params.add(vo.get("owe_units")) ;
									
		params.add(vo.get("cycle_type_id")) ;
									
		params.add(vo.get("notes")) ;
									
		params.add(vo.get("bill_post_mode")) ;
									
		params.add(vo.get("bill_post_addr")) ;
									
		params.add(vo.get("province_code")) ;
									
		params.add(vo.get("city_no")) ;
									
		params.add(vo.get("acct_type")) ;
									
		params.add(vo.get("grp_acct_id")) ;
									
		params.add(vo.get("state")) ;
						
							
		params.add(keyCondMap.get("acct_id")) ;
									
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
							
		params.add(keyCondMap.get("acct_id")) ;
									
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
