package com.ztesoft.crm.business.common.inst.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class EntProductDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,state_date,action_type,end_time,beg_time,staff_no,site_no,ent_product_id,product_id,mkt_res_id,mkt_res_inst_id from ENT_PRODUCT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ent_product where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ENT_PRODUCT (ord_item_id, cust_ord_id, ord_id, state_date, action_type, end_time, beg_time, staff_no, site_no, ent_product_id, product_id, mkt_res_id, mkt_res_inst_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ENT_PRODUCT set ord_item_id=?, cust_ord_id=?, ord_id=?, state_date=?, action_type=?, end_time=?, beg_time=?, staff_no=?, site_no=?, ent_product_id=?, product_id=?, mkt_res_id=?, mkt_res_inst_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ENT_PRODUCT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ENT_PRODUCT where ent_product_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ENT_PRODUCT set ord_item_id=?, cust_ord_id=?, ord_id=?, state_date=?, action_type=?, end_time=?, beg_time=?, staff_no=?, site_no=?, ent_product_id=?, product_id=?, mkt_res_id=?, mkt_res_inst_id=? where ent_product_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,state_date,action_type,end_time,beg_time,staff_no,site_no,ent_product_id,product_id,mkt_res_id,mkt_res_inst_id from ENT_PRODUCT where ent_product_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public EntProductDAO() {

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
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("action_type")) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
									
		params.add(map.get("ent_product_id")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("mkt_res_id")) ;
									
		params.add(map.get("mkt_res_inst_id")) ;
						
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
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("action_type")) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
					
		params.add(vo.get("ent_product_id")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("mkt_res_id")) ;
						
					
		params.add(vo.get("mkt_res_inst_id")) ;
						
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
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("action_type")) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
									
		params.add(vo.get("ent_product_id")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("mkt_res_id")) ;
									
		params.add(vo.get("mkt_res_inst_id")) ;
						
							
		params.add(keyCondMap.get("ent_product_id")) ;
						
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
							
		params.add(keyCondMap.get("ent_product_id")) ;
						
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
