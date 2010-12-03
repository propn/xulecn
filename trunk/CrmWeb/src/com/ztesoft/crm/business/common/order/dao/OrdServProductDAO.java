package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdServProductDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_product_id,serv_id,product_id,rent_date,r_serv_id,state from ORD_SERV_PRODUCT where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_serv_product where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_SERV_PRODUCT (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, staff_no, site_no, serv_product_id, serv_id, product_id, rent_date, r_serv_id, state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update ORD_SERV_PRODUCT set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_product_id=?, serv_id=?, product_id=?, rent_date=?, r_serv_id=?, state=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from ORD_SERV_PRODUCT where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_SERV_PRODUCT where ord_item_id=? and serv_product_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update ORD_SERV_PRODUCT set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_product_id=?, serv_id=?, product_id=?, rent_date=?, r_serv_id=?, state=? where ord_item_id=? and serv_product_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_product_id,serv_id,product_id,rent_date,r_serv_id,state from ORD_SERV_PRODUCT where ord_item_id=? and serv_product_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdServProductDAO() {

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
							
		params.add(map.get("ord_item_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
									
		params.add(map.get("serv_product_id")) ;
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("product_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("rent_date" ))) ;
									
		params.add(map.get("r_serv_id")) ;
									
		params.add(map.get("state")) ;
						
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
				
					
		params.add(vo.get("ord_item_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
					
		params.add(vo.get("serv_product_id")) ;
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("rent_date" ))) ;
						
					
		params.add(vo.get("r_serv_id")) ;
						
					
		params.add(vo.get("state")) ;
						
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
							
		params.add(vo.get("ord_item_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
									
		params.add(vo.get("serv_product_id")) ;
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("product_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("rent_date" ))) ;
									
		params.add(vo.get("r_serv_id")) ;
									
		params.add(vo.get("state")) ;
						
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("serv_product_id")) ;
						
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
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("serv_product_id")) ;
						
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
