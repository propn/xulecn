package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.crm.business.common.order.vo.OrdContactVO;

public class OrdCustOrderDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select cust_ord_id,cust_so_number,cust_id,state,status_date,pre_handle_flag,priority,business_id,lan_id,staff_no,site_no,ask_source,ask_source_srl from ORD_CUSTOMER_ORDER where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_customer_order where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_CUSTOMER_ORDER (cust_ord_id, cust_so_number, cust_id, state, status_date, pre_handle_flag, priority, business_id, lan_id, staff_no, site_no, ask_source, ask_source_srl) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_CUSTOMER_ORDER set cust_ord_id=?, cust_so_number=?, cust_id=?, state=?, status_date=?, pre_handle_flag=?, priority=?, business_id=?, lan_id=?, staff_no=?, site_no=?, ask_source=?, ask_source_srl=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_CUSTOMER_ORDER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_CUSTOMER_ORDER where cust_ord_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_CUSTOMER_ORDER set cust_ord_id=?, cust_so_number=?, cust_id=?, state=?, status_date=?, pre_handle_flag=?, priority=?, business_id=?, lan_id=?, staff_no=?, site_no=?, ask_source=?, ask_source_srl=? where cust_ord_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select cust_ord_id,cust_so_number,cust_id,state,status_date,pre_handle_flag,priority,business_id,lan_id,staff_no,site_no,ask_source,ask_source_srl from ORD_CUSTOMER_ORDER where cust_ord_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdCustOrderDAO() {

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
							
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("cust_so_number")) ;
									
		params.add(map.get("cust_id")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("status_date" ))) ;
									
		params.add(map.get("pre_handle_flag")) ;
									
		params.add(map.get("priority")) ;
									
		params.add(map.get("business_id")) ;
									
		params.add(map.get("lan_id")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
									
		params.add(map.get("ask_source")) ;
									
		params.add(map.get("ask_source_srl")) ;
						
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
				
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("cust_so_number")) ;
						
					
		params.add(vo.get("cust_id")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("status_date" ))) ;
						
					
		params.add(vo.get("pre_handle_flag")) ;
						
					
		params.add(vo.get("priority")) ;
						
					
		params.add(vo.get("business_id")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
					
		params.add(vo.get("ask_source")) ;
						
					
		params.add(vo.get("ask_source_srl")) ;
						
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
							
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("cust_so_number")) ;
									
		params.add(vo.get("cust_id")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("status_date" ))) ;
									
		params.add(vo.get("pre_handle_flag")) ;
									
		params.add(vo.get("priority")) ;
									
		params.add(vo.get("business_id")) ;
									
		params.add(vo.get("lan_id")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
									
		params.add(vo.get("ask_source")) ;
									
		params.add(vo.get("ask_source_srl")) ;
						
							
		params.add(keyCondMap.get("cust_ord_id")) ;
						
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
							
		params.add(keyCondMap.get("cust_ord_id")) ;
						
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
	
	/**
	 * 根据条件查询客户订单信息。
	 * @param where
	 * @param params 
	 * @return
	 * @throws FrameException 
	 */
	public List getCustOrderByWhere(String where, ArrayList params) throws FrameException{
		
		return (ArrayList)findByCond(where,params);
	}
}
