package com.ztesoft.vsop.product.dao.prodItemElement;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ProductCatgItemElementDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select catalog_item_id,element_type,element_id,order_id,seq,state,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,party_id,party_role_id,oper_region_id from PRODUCT_CATALOG_ITEM_ELEMENT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_catalog_item_element where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_CATALOG_ITEM_ELEMENT (catalog_item_id, element_type, element_id, order_id, seq, state, oper_date, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq, party_id, party_role_id, oper_region_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PRODUCT_CATALOG_ITEM_ELEMENT set catalog_item_id=?, element_type=?, element_id=?, order_id=?, seq=?, state=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, party_id=?, party_role_id=?, oper_region_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PRODUCT_CATALOG_ITEM_ELEMENT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_CATALOG_ITEM_ELEMENT where catalog_item_id=? and element_type=? and element_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_CATALOG_ITEM_ELEMENT set catalog_item_id=?, element_type=?, element_id=?, order_id=?, seq=?, state=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, party_id=?, party_role_id=?, oper_region_id=? where catalog_item_id=? and element_type=? and element_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select catalog_item_id,element_type,element_id,order_id,seq,state,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,party_id,party_role_id,oper_region_id from PRODUCT_CATALOG_ITEM_ELEMENT where catalog_item_id=? and element_type=? and element_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public ProductCatgItemElementDAO() {

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
							
		params.add(map.get("catalog_item_id")) ;
									
		params.add(map.get("element_type")) ;
									
		params.add(map.get("element_id")) ;
						
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
				
					
		params.add(vo.get("catalog_item_id")) ;
						
					
		params.add(vo.get("element_type")) ;
						
					
		params.add(vo.get("element_id")) ;
						
					
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
							
		params.add(vo.get("catalog_item_id")) ;
									
		params.add(vo.get("element_type")) ;
									
		params.add(vo.get("element_id")) ;
									
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
							
		params.add(keyCondMap.get("catalog_item_id")) ;
									
		params.add(keyCondMap.get("element_type")) ;
									
		params.add(keyCondMap.get("element_id")) ;
						
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
