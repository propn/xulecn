package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhNotPayListDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_id,ord_type,cust_id,bureau_no,instance_type,instance_id,price_object_type,price_object_id,object_id,service_offer_id,acct_id,acct_item_type_id,need_cash,type_type,type_adsc,price_id,base_instance_type,base_instance_id,time_limit_type,time_limit_value,check_date,charge_mode,fact_cash,pay_type,pay_date,send_date,notes,fee_serialno,pan_serial,state,staff_no,site_no from UH_NOT_PAY_LIST where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_not_pay_list where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_NOT_PAY_LIST (ord_id, ord_type, cust_id, bureau_no, instance_type, instance_id, price_object_type, price_object_id, object_id, service_offer_id, acct_id, acct_item_type_id, need_cash, type_type, type_adsc, price_id, base_instance_type, base_instance_id, time_limit_type, time_limit_value, check_date, charge_mode, fact_cash, pay_type, pay_date, send_date, notes, fee_serialno, pan_serial, state, staff_no, site_no) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update UH_NOT_PAY_LIST set ord_id=?, ord_type=?, cust_id=?, bureau_no=?, instance_type=?, instance_id=?, price_object_type=?, price_object_id=?, object_id=?, service_offer_id=?, acct_id=?, acct_item_type_id=?, need_cash=?, type_type=?, type_adsc=?, price_id=?, base_instance_type=?, base_instance_id=?, time_limit_type=?, time_limit_value=?, check_date=?, charge_mode=?, fact_cash=?, pay_type=?, pay_date=?, send_date=?, notes=?, fee_serialno=?, pan_serial=?, state=?, staff_no=?, site_no=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from UH_NOT_PAY_LIST where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from UH_NOT_PAY_LIST where acct_id=? and acct_item_type_id=? and object_id=? and ord_id=? and price_object_type=? and service_offer_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update UH_NOT_PAY_LIST set ord_id=?, ord_type=?, cust_id=?, bureau_no=?, instance_type=?, instance_id=?, price_object_type=?, price_object_id=?, object_id=?, service_offer_id=?, acct_id=?, acct_item_type_id=?, need_cash=?, type_type=?, type_adsc=?, price_id=?, base_instance_type=?, base_instance_id=?, time_limit_type=?, time_limit_value=?, check_date=?, charge_mode=?, fact_cash=?, pay_type=?, pay_date=?, send_date=?, notes=?, fee_serialno=?, pan_serial=?, state=?, staff_no=?, site_no=? where acct_id=? and acct_item_type_id=? and object_id=? and ord_id=? and price_object_type=? and service_offer_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_id,ord_type,cust_id,bureau_no,instance_type,instance_id,price_object_type,price_object_id,object_id,service_offer_id,acct_id,acct_item_type_id,need_cash,type_type,type_adsc,price_id,base_instance_type,base_instance_id,time_limit_type,time_limit_value,check_date,charge_mode,fact_cash,pay_type,pay_date,send_date,notes,fee_serialno,pan_serial,state,staff_no,site_no from UH_NOT_PAY_LIST where acct_id=? and acct_item_type_id=? and object_id=? and ord_id=? and price_object_type=? and service_offer_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = "ORD" ;


	public UhNotPayListDAO() {

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
							
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("ord_type")) ;
									
		params.add(map.get("cust_id")) ;
									
		params.add(map.get("bureau_no")) ;
									
		params.add(map.get("instance_type")) ;
									
		params.add(map.get("instance_id")) ;
									
		params.add(map.get("price_object_type")) ;
									
		params.add(map.get("price_object_id")) ;
									
		params.add(map.get("object_id")) ;
									
		params.add(map.get("service_offer_id")) ;
									
		params.add(map.get("acct_id")) ;
									
		params.add(map.get("acct_item_type_id")) ;
									
		params.add(map.get("need_cash")) ;
									
		params.add(map.get("type_type")) ;
									
		params.add(map.get("type_adsc")) ;
									
		params.add(map.get("price_id")) ;
									
		params.add(map.get("base_instance_type")) ;
									
		params.add(map.get("base_instance_id")) ;
									
		params.add(map.get("time_limit_type")) ;
									
		params.add(map.get("time_limit_value")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("check_date" ))) ;
									
		params.add(map.get("charge_mode")) ;
									
		params.add(map.get("fact_cash")) ;
									
		params.add(map.get("pay_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("pay_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("send_date" ))) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("fee_serialno")) ;
									
		params.add(map.get("pan_serial")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
						
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
				
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("ord_type")) ;
						
					
		params.add(vo.get("cust_id")) ;
						
					
		params.add(vo.get("bureau_no")) ;
						
					
		params.add(vo.get("instance_type")) ;
						
					
		params.add(vo.get("instance_id")) ;
						
					
		params.add(vo.get("price_object_type")) ;
						
					
		params.add(vo.get("price_object_id")) ;
						
					
		params.add(vo.get("object_id")) ;
						
					
		params.add(vo.get("service_offer_id")) ;
						
					
		params.add(vo.get("acct_id")) ;
						
					
		params.add(vo.get("acct_item_type_id")) ;
						
					
		params.add(vo.get("need_cash")) ;
						
					
		params.add(vo.get("type_type")) ;
						
					
		params.add(vo.get("type_adsc")) ;
						
					
		params.add(vo.get("price_id")) ;
						
					
		params.add(vo.get("base_instance_type")) ;
						
					
		params.add(vo.get("base_instance_id")) ;
						
					
		params.add(vo.get("time_limit_type")) ;
						
					
		params.add(vo.get("time_limit_value")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("check_date" ))) ;
						
					
		params.add(vo.get("charge_mode")) ;
						
					
		params.add(vo.get("fact_cash")) ;
						
					
		params.add(vo.get("pay_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("pay_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("send_date" ))) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("fee_serialno")) ;
						
					
		params.add(vo.get("pan_serial")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
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
							
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("ord_type")) ;
									
		params.add(vo.get("cust_id")) ;
									
		params.add(vo.get("bureau_no")) ;
									
		params.add(vo.get("instance_type")) ;
									
		params.add(vo.get("instance_id")) ;
									
		params.add(vo.get("price_object_type")) ;
									
		params.add(vo.get("price_object_id")) ;
									
		params.add(vo.get("object_id")) ;
									
		params.add(vo.get("service_offer_id")) ;
									
		params.add(vo.get("acct_id")) ;
									
		params.add(vo.get("acct_item_type_id")) ;
									
		params.add(vo.get("need_cash")) ;
									
		params.add(vo.get("type_type")) ;
									
		params.add(vo.get("type_adsc")) ;
									
		params.add(vo.get("price_id")) ;
									
		params.add(vo.get("base_instance_type")) ;
									
		params.add(vo.get("base_instance_id")) ;
									
		params.add(vo.get("time_limit_type")) ;
									
		params.add(vo.get("time_limit_value")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("check_date" ))) ;
									
		params.add(vo.get("charge_mode")) ;
									
		params.add(vo.get("fact_cash")) ;
									
		params.add(vo.get("pay_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("pay_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("send_date" ))) ;
									
		params.add(vo.get("notes")) ;
									
		params.add(vo.get("fee_serialno")) ;
									
		params.add(vo.get("pan_serial")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
						
							
		params.add(keyCondMap.get("acct_id")) ;
									
		params.add(keyCondMap.get("acct_item_type_id")) ;
									
		params.add(keyCondMap.get("object_id")) ;
									
		params.add(keyCondMap.get("ord_id")) ;
									
		params.add(keyCondMap.get("price_object_type")) ;
									
		params.add(keyCondMap.get("service_offer_id")) ;
						
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
									
		params.add(keyCondMap.get("acct_item_type_id")) ;
									
		params.add(keyCondMap.get("object_id")) ;
									
		params.add(keyCondMap.get("ord_id")) ;
									
		params.add(keyCondMap.get("price_object_type")) ;
									
		params.add(keyCondMap.get("service_offer_id")) ;
						
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
