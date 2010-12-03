package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhServDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_id,comp_inst_id,cust_id,product_family_id,user_cust_id,product_id,area_code,acc_nbr,physical_nbr,lan_id,business_id,billing_mode_id,rent_date,user_password,state,user_prop,notes,user_name,address_id,detail_address,branch_id from UH_SERV where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_serv where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_SERV (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, staff_no, site_no, serv_id, comp_inst_id, cust_id, product_family_id, user_cust_id, product_id, area_code, acc_nbr, physical_nbr, lan_id, business_id, billing_mode_id, rent_date, user_password, state, user_prop, notes, user_name, address_id, detail_address, branch_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update UH_SERV set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_id=?, comp_inst_id=?, cust_id=?, product_family_id=?, user_cust_id=?, product_id=?, area_code=?, acc_nbr=?, physical_nbr=?, lan_id=?, business_id=?, billing_mode_id=?, rent_date=?, user_password=?, state=?, user_prop=?, notes=?, user_name=?, address_id=?, detail_address=?, branch_id=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from UH_SERV where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from UH_SERV where ord_item_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update UH_SERV set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_id=?, comp_inst_id=?, cust_id=?, product_family_id=?, user_cust_id=?, product_id=?, area_code=?, acc_nbr=?, physical_nbr=?, lan_id=?, business_id=?, billing_mode_id=?, rent_date=?, user_password=?, state=?, user_prop=?, notes=?, user_name=?, address_id=?, detail_address=?, branch_id=? where ord_item_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_id,comp_inst_id,cust_id,product_family_id,user_cust_id,product_id,area_code,acc_nbr,physical_nbr,lan_id,business_id,billing_mode_id,rent_date,user_password,state,user_prop,notes,user_name,address_id,detail_address,branch_id from UH_SERV where ord_item_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = "ORD" ;


	public UhServDAO() {

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
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("comp_inst_id")) ;
									
		params.add(map.get("cust_id")) ;
									
		params.add(map.get("product_family_id")) ;
									
		params.add(map.get("user_cust_id")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("area_code")) ;
									
		params.add(map.get("acc_nbr")) ;
									
		params.add(map.get("physical_nbr")) ;
									
		params.add(map.get("lan_id")) ;
									
		params.add(map.get("business_id")) ;
									
		params.add(map.get("billing_mode_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("rent_date" ))) ;
									
		params.add(map.get("user_password")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("user_prop")) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("user_name")) ;
									
		params.add(map.get("address_id")) ;
									
		params.add(map.get("detail_address")) ;
									
		params.add(map.get("branch_id")) ;
						
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
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("comp_inst_id")) ;
						
					
		params.add(vo.get("cust_id")) ;
						
					
		params.add(vo.get("product_family_id")) ;
						
					
		params.add(vo.get("user_cust_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("area_code")) ;
						
					
		params.add(vo.get("acc_nbr")) ;
						
					
		params.add(vo.get("physical_nbr")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
					
		params.add(vo.get("business_id")) ;
						
					
		params.add(vo.get("billing_mode_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("rent_date" ))) ;
						
					
		params.add(vo.get("user_password")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("user_prop")) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("user_name")) ;
						
					
		params.add(vo.get("address_id")) ;
						
					
		params.add(vo.get("detail_address")) ;
						
					
		params.add(vo.get("branch_id")) ;
						
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
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("comp_inst_id")) ;
									
		params.add(vo.get("cust_id")) ;
									
		params.add(vo.get("product_family_id")) ;
									
		params.add(vo.get("user_cust_id")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("area_code")) ;
									
		params.add(vo.get("acc_nbr")) ;
									
		params.add(vo.get("physical_nbr")) ;
									
		params.add(vo.get("lan_id")) ;
									
		params.add(vo.get("business_id")) ;
									
		params.add(vo.get("billing_mode_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("rent_date" ))) ;
									
		params.add(vo.get("user_password")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("user_prop")) ;
									
		params.add(vo.get("notes")) ;
									
		params.add(vo.get("user_name")) ;
									
		params.add(vo.get("address_id")) ;
									
		params.add(vo.get("detail_address")) ;
									
		params.add(vo.get("branch_id")) ;
						
							
		params.add(keyCondMap.get("ord_item_id")) ;
						
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
