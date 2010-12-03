package com.ztesoft.crm.business.common.specs.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;


public class OffCollectionDAO extends AbstractDAO {
	

	private String SQL_SELECT = "select product_offer_id,collection_type,collection_id,state,seq,party_id,party_role_id,oper_region_id,oper_date from OFFER_COLLECTION where 1=1 ";
	
	
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from offer_collection where 1=1 ";

	
	private String SQL_INSERT = "insert into OFFER_COLLECTION (product_offer_id, collection_type, collection_id, state, seq, party_id, party_role_id, oper_region_id, oper_date) values (?, ?, ?, ?, ?, ?, ?, ?, to_date(?,'yyyy-mm-dd hh24:mi:ss'))";

	
	private String SQL_UPDATE = "update OFFER_COLLECTION set product_offer_id=?, collection_type=?, collection_id=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=? where 1=1 ";
	
	
	private String SQL_DELETE = "delete from OFFER_COLLECTION where 1=1 ";
	
	
	private String SQL_DELETE_KEY = "delete from OFFER_COLLECTION where collection_id=? and collection_type=? and product_offer_id=? and seq=?";
		
		
	private String SQL_UPDATE_KEY = "update OFFER_COLLECTION set product_offer_id=?, collection_type=?, collection_id=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=? where collection_id=? and collection_type=? and product_offer_id=? and seq=?";
		
	
	private String SQL_SELECT_KEY = "select product_offer_id,collection_type,collection_id,state,seq,party_id,party_role_id,oper_region_id,oper_date from OFFER_COLLECTION where collection_id=? and collection_type=? and product_offer_id=? and seq=? ";
	
	

	private String dbName = JNDINames.CRM_DATASOURCE ;



	public OffCollectionDAO() {

	}
	

	/**
	 * Insert
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
				
		params.add((String)map.get("product_offer_id" ));
				
		params.add((String)map.get("collection_type" ));
				
		params.add((String)map.get("collection_id" ));
				
		params.add((String)map.get("state" ));
				
		params.add((String)map.get("seq" ));
				
		params.add((String)map.get("party_id" ));
				
		params.add((String)map.get("party_role_id" ));
				
		params.add((String)map.get("oper_region_id" ));
				
		params.add((String)map.get("oper_date" ));
				
		return params ;
	}
	

	/**
	 * update
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
		params.add((String)vo.get("product_offer_id" ));
				
		params.add((String)vo.get("collection_type" ));
				
		params.add((String)vo.get("collection_id" ));
				
		params.add((String)vo.get("state" ));
				
		params.add((String)vo.get("seq" ));
				
		params.add((String)vo.get("party_id" ));
				
		params.add((String)vo.get("party_role_id" ));
				
		params.add((String)vo.get("oper_region_id" ));
				
		params.add((String)vo.get("oper_date" ));
				
		
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add((String)condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * 
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
				
		params.add((String)vo.get("product_offer_id" ));
				
		params.add((String)vo.get("collection_type" ));
				
		params.add((String)vo.get("collection_id" ));
				
		params.add((String)vo.get("state" ));
				
		params.add((String)vo.get("seq" ));
				
		params.add((String)vo.get("party_id" ));
				
		params.add((String)vo.get("party_role_id" ));
				
		params.add((String)vo.get("oper_region_id" ));
				
		params.add((String)vo.get("oper_date" ));
				
				
		params.add((String)keyCondMap.get("collection_id")) ;
				
		params.add((String)keyCondMap.get("collection_type")) ;
				
		params.add((String)keyCondMap.get("product_offer_id")) ;
				
		params.add((String)keyCondMap.get("seq")) ;
				
		return params ;
	}
		
		/**
	 * 
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
				
		params.add((String)keyCondMap.get("collection_id")) ;
				
		params.add((String)keyCondMap.get("collection_type")) ;
				
		params.add((String)keyCondMap.get("product_offer_id")) ;
				
		params.add((String)keyCondMap.get("seq")) ;
				
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
