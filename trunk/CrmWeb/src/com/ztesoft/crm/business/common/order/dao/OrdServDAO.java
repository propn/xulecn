package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdServDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_id,comp_inst_id,cust_id,product_family_id,user_cust_id,product_id,area_code,acc_nbr,physical_nbr,lan_id,business_id,billing_mode_id,rent_date,user_password,state,user_prop,notes,user_name from ORD_SERV where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_serv where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_SERV (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, staff_no, site_no, serv_id, comp_inst_id, cust_id, product_family_id, user_cust_id, product_id, area_code, acc_nbr, physical_nbr, lan_id, business_id, billing_mode_id, rent_date, user_password, state, user_prop, notes, user_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_SERV set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_id=?, comp_inst_id=?, cust_id=?, product_family_id=?, user_cust_id=?, product_id=?, area_code=?, acc_nbr=?, physical_nbr=?, lan_id=?, business_id=?, billing_mode_id=?, rent_date=?, user_password=?, state=?, user_prop=?, notes=?, user_name=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_SERV where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_SERV where ord_item_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_SERV set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_id=?, comp_inst_id=?, cust_id=?, product_family_id=?, user_cust_id=?, product_id=?, area_code=?, acc_nbr=?, physical_nbr=?, lan_id=?, business_id=?, billing_mode_id=?, rent_date=?, user_password=?, state=?, user_prop=?, notes=?, user_name=? where ord_item_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_id,comp_inst_id,cust_id,product_family_id,user_cust_id,product_id,area_code,acc_nbr,physical_nbr,lan_id,business_id,billing_mode_id,rent_date,user_password,state,user_prop,notes,user_name from ORD_SERV where ord_item_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;
	
	// 根据产品实例标识和定单号，查询订单项标识最大数据。
	private static final String SQL_QUERY_ACC_NBR = 
				" select acc_nbr, product_name from " +
				"	( select a.acc_nbr, b.product_name from ord_serv a, product b            " +
				"          where a.serv_id = ?  and a.ord_id = ?             				 " +
				"            and a.product_id = b.product_id order by a.ord_item_id desc )   " +
				"  where rownum = 1                               							 ";


	public OrdServDAO() {

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
	
	/**
	 * 根据主产品实例标识和定单标识，查询出定单项最大的产品信息。
	 * @param servId
	 * @param ordId
	 * @return
	 * @throws FrameException
	 */
	public List getOrdServByServId(String servId,String ordId) throws FrameException{
		//组织查询数据的参数
		ArrayList params = new ArrayList();
		params.add(servId);
		params.add(ordId);
		
		return findBySql(SQL_QUERY_ACC_NBR, params);
		
	}
}
