package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class AcctItemTypeDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select acct_item_type_id,acct_item_class_id,party_role_id,name,charge_mark,total_mark,acct_item_type_code,summary_item_id,cash_pri,type_type,acct_balance_type,type_adsc from ACCT_ITEM_TYPE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from acct_item_type where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ACCT_ITEM_TYPE (acct_item_type_id, acct_item_class_id, party_role_id, name, charge_mark, total_mark, acct_item_type_code, summary_item_id, cash_pri, type_type, acct_balance_type, type_adsc) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ACCT_ITEM_TYPE set acct_item_type_id=?, acct_item_class_id=?, party_role_id=?, name=?, charge_mark=?, total_mark=?, acct_item_type_code=?, summary_item_id=?, cash_pri=?, type_type=?, acct_balance_type=?, type_adsc=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ACCT_ITEM_TYPE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ACCT_ITEM_TYPE where acct_item_type_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ACCT_ITEM_TYPE set acct_item_type_id=?, acct_item_class_id=?, party_role_id=?, name=?, charge_mark=?, total_mark=?, acct_item_type_code=?, summary_item_id=?, cash_pri=?, type_type=?, acct_balance_type=?, type_adsc=? where acct_item_type_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select acct_item_type_id,acct_item_class_id,party_role_id,name,charge_mark,total_mark,acct_item_type_code,summary_item_id,cash_pri,type_type,acct_balance_type,type_adsc from ACCT_ITEM_TYPE where acct_item_type_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public AcctItemTypeDAO() {

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
							
		params.add(map.get("acct_item_type_id")) ;
									
		params.add(map.get("acct_item_class_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("charge_mark")) ;
									
		params.add(map.get("total_mark")) ;
									
		params.add(map.get("acct_item_type_code")) ;
									
		params.add(map.get("summary_item_id")) ;
									
		params.add(map.get("cash_pri")) ;
									
		params.add(map.get("type_type")) ;
									
		params.add(map.get("acct_balance_type")) ;
									
		params.add(map.get("type_adsc")) ;
						
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
				
					
		params.add(vo.get("acct_item_type_id")) ;
						
					
		params.add(vo.get("acct_item_class_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("charge_mark")) ;
						
					
		params.add(vo.get("total_mark")) ;
						
					
		params.add(vo.get("acct_item_type_code")) ;
						
					
		params.add(vo.get("summary_item_id")) ;
						
					
		params.add(vo.get("cash_pri")) ;
						
					
		params.add(vo.get("type_type")) ;
						
					
		params.add(vo.get("acct_balance_type")) ;
						
					
		params.add(vo.get("type_adsc")) ;
						
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
							
		params.add(vo.get("acct_item_type_id")) ;
									
		params.add(vo.get("acct_item_class_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("charge_mark")) ;
									
		params.add(vo.get("total_mark")) ;
									
		params.add(vo.get("acct_item_type_code")) ;
									
		params.add(vo.get("summary_item_id")) ;
									
		params.add(vo.get("cash_pri")) ;
									
		params.add(vo.get("type_type")) ;
									
		params.add(vo.get("acct_balance_type")) ;
									
		params.add(vo.get("type_adsc")) ;
						
							
		params.add(keyCondMap.get("acct_item_type_id")) ;
						
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
							
		params.add(keyCondMap.get("acct_item_type_id")) ;
						
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
