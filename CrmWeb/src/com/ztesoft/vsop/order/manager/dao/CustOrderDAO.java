package com.ztesoft.vsop.order.manager.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class CustOrderDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select cust_order_id,other_sys_order_id,cust_so_number,cust_order_type,time_name_id,cust_id,staff_id,channel_id,status,status_date,pre_handle_flag,handle_people_name,contact_phone,contact_people,priority,reason,order_channel,order_system,receive_date,disposal_result,disposal_result_desc,acc_nbr,(select product_name from product p where p.PROD_FUNC_TYPE='0' and p.product_id=o.product_id) as product_name,product_id,lan_id,prod_inst_id from CUSTOMER_ORDER o where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from customer_order where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into CUSTOMER_ORDER (cust_order_id, other_sys_order_id, cust_so_number, cust_order_type, time_name_id, cust_id, staff_id, channel_id, status, status_date, pre_handle_flag, handle_people_name, contact_phone, contact_people, priority, reason, order_channel, order_system, receive_date, disposal_result, disposal_result_desc, acc_nbr, product_id, lan_id, prod_inst_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update CUSTOMER_ORDER set cust_order_id=?, other_sys_order_id=?, cust_so_number=?, cust_order_type=?, time_name_id=?, cust_id=?, staff_id=?, channel_id=?, status=?, status_date=?, pre_handle_flag=?, handle_people_name=?, contact_phone=?, contact_people=?, priority=?, reason=?, order_channel=?, order_system=?, receive_date=?, disposal_result=?, disposal_result_desc=?, acc_nbr=?, product_id=?, lan_id=?, prod_inst_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from CUSTOMER_ORDER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from CUSTOMER_ORDER where cust_order_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update CUSTOMER_ORDER set cust_order_id=?, other_sys_order_id=?, cust_so_number=?, cust_order_type=?, time_name_id=?, cust_id=?, staff_id=?, channel_id=?, status=?, status_date=?, pre_handle_flag=?, handle_people_name=?, contact_phone=?, contact_people=?, priority=?, reason=?, order_channel=?, order_system=?, receive_date=?, disposal_result=?, disposal_result_desc=?, acc_nbr=?, product_id=?, lan_id=?, prod_inst_id=? where cust_order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select cust_order_id,other_sys_order_id,cust_so_number,cust_order_type,time_name_id,cust_id,staff_id,channel_id,status,status_date,pre_handle_flag,handle_people_name,contact_phone,contact_people,priority,reason,order_channel,order_system,receive_date,disposal_result,disposal_result_desc,acc_nbr,product_id,lan_id,prod_inst_id from CUSTOMER_ORDER where cust_order_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public CustOrderDAO() {

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
							
		params.add(map.get("cust_order_id")) ;
									
		params.add(map.get("other_sys_order_id")) ;
									
		params.add(map.get("cust_so_number")) ;
									
		params.add(map.get("cust_order_type")) ;
									
		params.add(map.get("time_name_id")) ;
									
		params.add(map.get("cust_id")) ;
									
		params.add(map.get("staff_id")) ;
									
		params.add(map.get("channel_id")) ;
									
		params.add(map.get("status")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("status_date" ))) ;
									
		params.add(map.get("pre_handle_flag")) ;
									
		params.add(map.get("handle_people_name")) ;
									
		params.add(map.get("contact_phone")) ;
									
		params.add(map.get("contact_people")) ;
									
		params.add(map.get("priority")) ;
									
		params.add(map.get("reason")) ;
									
		params.add(map.get("order_channel")) ;
									
		params.add(map.get("order_system")) ;
									
		params.add(map.get("receive_date")) ;
									
		params.add(map.get("disposal_result")) ;
									
		params.add(map.get("disposal_result_desc")) ;
									
		params.add(map.get("acc_nbr")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("lan_id")) ;
									
		params.add(map.get("prod_inst_id")) ;
						
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
				
					
		params.add(vo.get("cust_order_id")) ;
						
					
		params.add(vo.get("other_sys_order_id")) ;
						
					
		params.add(vo.get("cust_so_number")) ;
						
					
		params.add(vo.get("cust_order_type")) ;
						
					
		params.add(vo.get("time_name_id")) ;
						
					
		params.add(vo.get("cust_id")) ;
						
					
		params.add(vo.get("staff_id")) ;
						
					
		params.add(vo.get("channel_id")) ;
						
					
		params.add(vo.get("status")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("status_date" ))) ;
						
					
		params.add(vo.get("pre_handle_flag")) ;
						
					
		params.add(vo.get("handle_people_name")) ;
						
					
		params.add(vo.get("contact_phone")) ;
						
					
		params.add(vo.get("contact_people")) ;
						
					
		params.add(vo.get("priority")) ;
						
					
		params.add(vo.get("reason")) ;
						
					
		params.add(vo.get("order_channel")) ;
						
					
		params.add(vo.get("order_system")) ;
						
					
		params.add(vo.get("receive_date")) ;
						
					
		params.add(vo.get("disposal_result")) ;
						
					
		params.add(vo.get("disposal_result_desc")) ;
						
					
		params.add(vo.get("acc_nbr")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
					
		params.add(vo.get("prod_inst_id")) ;
						
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
							
		params.add(vo.get("cust_order_id")) ;
									
		params.add(vo.get("other_sys_order_id")) ;
									
		params.add(vo.get("cust_so_number")) ;
									
		params.add(vo.get("cust_order_type")) ;
									
		params.add(vo.get("time_name_id")) ;
									
		params.add(vo.get("cust_id")) ;
									
		params.add(vo.get("staff_id")) ;
									
		params.add(vo.get("channel_id")) ;
									
		params.add(vo.get("status")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("status_date" ))) ;
									
		params.add(vo.get("pre_handle_flag")) ;
									
		params.add(vo.get("handle_people_name")) ;
									
		params.add(vo.get("contact_phone")) ;
									
		params.add(vo.get("contact_people")) ;
									
		params.add(vo.get("priority")) ;
									
		params.add(vo.get("reason")) ;
									
		params.add(vo.get("order_channel")) ;
									
		params.add(vo.get("order_system")) ;
									
		params.add(vo.get("receive_date")) ;
									
		params.add(vo.get("disposal_result")) ;
									
		params.add(vo.get("disposal_result_desc")) ;
									
		params.add(vo.get("acc_nbr")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("lan_id")) ;
									
		params.add(vo.get("prod_inst_id")) ;
						
							
		params.add(keyCondMap.get("cust_order_id")) ;
						
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
							
		params.add(keyCondMap.get("cust_order_id")) ;
						
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
	
	public void setSelectSQL(String sql){
		this.SQL_SELECT=sql ;
	}
	
	public String getSelectCountSQL(){
		return this.SQL_SELECT_COUNT ;
	}
	
	public void setSelectCountSQL(String sql){
		this.SQL_SELECT_COUNT=sql ;
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

	public List getPfOrder(String whereCond, List para) throws FrameException {
		List m = findBySql(whereCond,para);
		//if(null != m && m.size()>0) return (Map)m.get(0);
		return m;
	}
	
}
