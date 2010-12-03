package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdNotPayListDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_id,ord_type,cust_id,bureau_no,instance_type,instance_id,price_object_type,price_object_id,object_id,service_offer_id,acct_id,acct_item_type_id,need_cash,type_type,type_adsc,price_id,base_instance_type,base_instance_id,time_limit_type,time_limit_vale,check_date,charge_mode,fact_cash,pay_type,pay_date,send_date,notes,fee_serialno,plan_serial,state,staff_no,site_no from ORD_NOT_PAY_LIST where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_not_pay_list where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_NOT_PAY_LIST (ord_id, ord_type, cust_id, bureau_no, instance_type, instance_id, price_object_type, price_object_id, object_id, service_offer_id, acct_id, acct_item_type_id, need_cash, type_type, type_adsc, price_id, base_instance_type, base_instance_id, time_limit_type, time_limit_vale, check_date, charge_mode, fact_cash, pay_type, pay_date, send_date, notes, fee_serialno, plan_serial, state, staff_no, site_no) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_NOT_PAY_LIST set ord_id=?, ord_type=?, cust_id=?, bureau_no=?, instance_type=?, instance_id=?, price_object_type=?, price_object_id=?, object_id=?, service_offer_id=?, acct_id=?, acct_item_type_id=?, need_cash=?, type_type=?, type_adsc=?, price_id=?, base_instance_type=?, base_instance_id=?, time_limit_type=?, time_limit_vale=?, check_date=?, charge_mode=?, fact_cash=?, pay_type=?, pay_date=?, send_date=?, notes=?, fee_serialno=?, plan_serial=?, state=?, staff_no=?, site_no=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_NOT_PAY_LIST where 1=1 ";
	
		
		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdNotPayListDAO() {

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
									
		params.add(map.get("time_limit_vale")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("check_date" ))) ;
									
		params.add(map.get("charge_mode")) ;
									
		params.add(map.get("fact_cash")) ;
									
		params.add(map.get("pay_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("pay_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("send_date" ))) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("fee_serialno")) ;
									
		params.add(map.get("plan_serial")) ;
									
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
						
					
		params.add(vo.get("time_limit_vale")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("check_date" ))) ;
						
					
		params.add(vo.get("charge_mode")) ;
						
					
		params.add(vo.get("fact_cash")) ;
						
					
		params.add(vo.get("pay_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("pay_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("send_date" ))) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("fee_serialno")) ;
						
					
		params.add(vo.get("plan_serial")) ;
						
					
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
	
		
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
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
								
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
}
