package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdBillPostDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,serv_id,bill_type,owner_type,post_method,post_code,incept_person,post_address,email,contact_name,contact_phone,guard_post,certificate_type,certificate_no,query_password from ORD_BILL_POST where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_bill_post where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_BILL_POST (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, serv_id, bill_type, owner_type, post_method, post_code, incept_person, post_address, email, contact_name, contact_phone, guard_post, certificate_type, certificate_no, query_password) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_BILL_POST set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, serv_id=?, bill_type=?, owner_type=?, post_method=?, post_code=?, incept_person=?, post_address=?, email=?, contact_name=?, contact_phone=?, guard_post=?, certificate_type=?, certificate_no=?, query_password=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_BILL_POST where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_BILL_POST where ord_item_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_BILL_POST set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, serv_id=?, bill_type=?, owner_type=?, post_method=?, post_code=?, incept_person=?, post_address=?, email=?, contact_name=?, contact_phone=?, guard_post=?, certificate_type=?, certificate_no=?, query_password=? where ord_item_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,serv_id,bill_type,owner_type,post_method,post_code,incept_person,post_address,email,contact_name,contact_phone,guard_post,certificate_type,certificate_no,query_password from ORD_BILL_POST where ord_item_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdBillPostDAO() {

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
							
		params.add(map.get("ord_item_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("bill_type")) ;
									
		params.add(map.get("owner_type")) ;
									
		params.add(map.get("post_method")) ;
									
		params.add(map.get("post_code")) ;
									
		params.add(map.get("incept_person")) ;
									
		params.add(map.get("post_address")) ;
									
		params.add(map.get("email")) ;
									
		params.add(map.get("contact_name")) ;
									
		params.add(map.get("contact_phone")) ;
									
		params.add(map.get("guard_post")) ;
									
		params.add(map.get("certificate_type")) ;
									
		params.add(map.get("certificate_no")) ;
									
		params.add(map.get("query_password")) ;
						
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
				
					
		params.add(vo.get("ord_item_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("bill_type")) ;
						
					
		params.add(vo.get("owner_type")) ;
						
					
		params.add(vo.get("post_method")) ;
						
					
		params.add(vo.get("post_code")) ;
						
					
		params.add(vo.get("incept_person")) ;
						
					
		params.add(vo.get("post_address")) ;
						
					
		params.add(vo.get("email")) ;
						
					
		params.add(vo.get("contact_name")) ;
						
					
		params.add(vo.get("contact_phone")) ;
						
					
		params.add(vo.get("guard_post")) ;
						
					
		params.add(vo.get("certificate_type")) ;
						
					
		params.add(vo.get("certificate_no")) ;
						
					
		params.add(vo.get("query_password")) ;
						
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
							
		params.add(vo.get("ord_item_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("bill_type")) ;
									
		params.add(vo.get("owner_type")) ;
									
		params.add(vo.get("post_method")) ;
									
		params.add(vo.get("post_code")) ;
									
		params.add(vo.get("incept_person")) ;
									
		params.add(vo.get("post_address")) ;
									
		params.add(vo.get("email")) ;
									
		params.add(vo.get("contact_name")) ;
									
		params.add(vo.get("contact_phone")) ;
									
		params.add(vo.get("guard_post")) ;
									
		params.add(vo.get("certificate_type")) ;
									
		params.add(vo.get("certificate_no")) ;
									
		params.add(vo.get("query_password")) ;
						
							
		params.add(keyCondMap.get("ord_item_id")) ;
						
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
							
		params.add(keyCondMap.get("ord_item_id")) ;
						
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
