package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ArcTpmBusiPriceDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select price_id,seq,price_object_type,price_object_id,service_offer_id,rate_type,rate_method,rate,scaled_rate,acct_item_type_id,time_limit_type,time_limit_vale,notes,exists_para,limit_object_type,limit_object_id,state,party_id,party_role_id,oper_date from ARC_TPM_BUSI_PRICE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from arc_tpm_busi_price where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ARC_TPM_BUSI_PRICE (price_id, seq, price_object_type, price_object_id, service_offer_id, rate_type, rate_method, rate, scaled_rate, acct_item_type_id, time_limit_type, time_limit_vale, notes, exists_para, limit_object_type, limit_object_id, state, party_id, party_role_id, oper_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ARC_TPM_BUSI_PRICE set price_id=?, seq=?, price_object_type=?, price_object_id=?, service_offer_id=?, rate_type=?, rate_method=?, rate=?, scaled_rate=?, acct_item_type_id=?, time_limit_type=?, time_limit_vale=?, notes=?, exists_para=?, limit_object_type=?, limit_object_id=?, state=?, party_id=?, party_role_id=?, oper_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ARC_TPM_BUSI_PRICE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ARC_TPM_BUSI_PRICE where price_id=? and seq=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ARC_TPM_BUSI_PRICE set price_id=?, seq=?, price_object_type=?, price_object_id=?, service_offer_id=?, rate_type=?, rate_method=?, rate=?, scaled_rate=?, acct_item_type_id=?, time_limit_type=?, time_limit_vale=?, notes=?, exists_para=?, limit_object_type=?, limit_object_id=?, state=?, party_id=?, party_role_id=?, oper_date=? where price_id=? and seq=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select price_id,seq,price_object_type,price_object_id,service_offer_id,rate_type,rate_method,rate,scaled_rate,acct_item_type_id,time_limit_type,time_limit_vale,notes,exists_para,limit_object_type,limit_object_id,state,party_id,party_role_id,oper_date from ARC_TPM_BUSI_PRICE where price_id=? and seq=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ArcTpmBusiPriceDAO() {

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
							
		params.add(map.get("price_id")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("price_object_type")) ;
									
		params.add(map.get("price_object_id")) ;
									
		params.add(map.get("service_offer_id")) ;
									
		params.add(map.get("rate_type")) ;
									
		params.add(map.get("rate_method")) ;
									
		params.add(map.get("rate")) ;
									
		params.add(map.get("scaled_rate")) ;
									
		params.add(map.get("acct_item_type_id")) ;
									
		params.add(map.get("time_limit_type")) ;
									
		params.add(map.get("time_limit_vale")) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("exists_para")) ;
									
		params.add(map.get("limit_object_type")) ;
									
		params.add(map.get("limit_object_id")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
						
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
				
					
		params.add(vo.get("price_id")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("price_object_type")) ;
						
					
		params.add(vo.get("price_object_id")) ;
						
					
		params.add(vo.get("service_offer_id")) ;
						
					
		params.add(vo.get("rate_type")) ;
						
					
		params.add(vo.get("rate_method")) ;
						
					
		params.add(vo.get("rate")) ;
						
					
		params.add(vo.get("scaled_rate")) ;
						
					
		params.add(vo.get("acct_item_type_id")) ;
						
					
		params.add(vo.get("time_limit_type")) ;
						
					
		params.add(vo.get("time_limit_vale")) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("exists_para")) ;
						
					
		params.add(vo.get("limit_object_type")) ;
						
					
		params.add(vo.get("limit_object_id")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
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
							
		params.add(vo.get("price_id")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("price_object_type")) ;
									
		params.add(vo.get("price_object_id")) ;
									
		params.add(vo.get("service_offer_id")) ;
									
		params.add(vo.get("rate_type")) ;
									
		params.add(vo.get("rate_method")) ;
									
		params.add(vo.get("rate")) ;
									
		params.add(vo.get("scaled_rate")) ;
									
		params.add(vo.get("acct_item_type_id")) ;
									
		params.add(vo.get("time_limit_type")) ;
									
		params.add(vo.get("time_limit_vale")) ;
									
		params.add(vo.get("notes")) ;
									
		params.add(vo.get("exists_para")) ;
									
		params.add(vo.get("limit_object_type")) ;
									
		params.add(vo.get("limit_object_id")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
							
		params.add(keyCondMap.get("price_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
							
		params.add(keyCondMap.get("price_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
