package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhCustOrderDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select cust_ord_id,cust_ord_no,cust_id,state,status_date,pre_handle_flag,priority,business_id,lan_id,staff_no,site_no,ask_source,ask_source_srl from UH_CUSTOMER_ORDER where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_customer_order where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_CUSTOMER_ORDER (cust_ord_id, cust_ord_no, cust_id, state, status_date, pre_handle_flag, priority, business_id, lan_id, staff_no, site_no, ask_source, ask_source_srl) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update UH_CUSTOMER_ORDER set cust_ord_id=?, cust_ord_no=?, cust_id=?, state=?, status_date=?, pre_handle_flag=?, priority=?, business_id=?, lan_id=?, staff_no=?, site_no=?, ask_source=?, ask_source_srl=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from UH_CUSTOMER_ORDER where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from UH_CUSTOMER_ORDER where cust_ord_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update UH_CUSTOMER_ORDER set cust_ord_id=?, cust_ord_no=?, cust_id=?, state=?, status_date=?, pre_handle_flag=?, priority=?, business_id=?, lan_id=?, staff_no=?, site_no=?, ask_source=?, ask_source_srl=? where cust_ord_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select cust_ord_id,cust_ord_no,cust_id,state,status_date,pre_handle_flag,priority,business_id,lan_id,staff_no,site_no,ask_source,ask_source_srl from UH_CUSTOMER_ORDER where cust_ord_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = "ORD" ;


	public UhCustOrderDAO() {

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
							
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("cust_ord_no")) ;
									
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
				
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("cust_ord_no")) ;
						
					
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
							
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("cust_ord_no")) ;
									
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
	
}
