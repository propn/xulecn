package com.ztesoft.crm.business.common.history.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;

public class UhAskLogDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,field_name,field_value,old_field_value from UH_ASK_LOG where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from uh_ask_log where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into UH_ASK_LOG (ord_item_id, cust_ord_id, field_name, field_value, old_field_value) values (?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update UH_ASK_LOG set ord_item_id=?, cust_ord_id=?, field_name=?, field_value=?, old_field_value=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from UH_ASK_LOG where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from UH_ASK_LOG where field_name=? and field_value=? and ord_item_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update UH_ASK_LOG set ord_item_id=?, cust_ord_id=?, field_name=?, field_value=?, old_field_value=? where field_name=? and field_value=? and ord_item_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,field_name,field_value,old_field_value from UH_ASK_LOG where field_name=? and field_value=? and ord_item_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = "ORD" ;


	public UhAskLogDAO() {

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
									
		params.add(map.get("field_name")) ;
									
		params.add(map.get("field_value")) ;
									
		params.add(map.get("old_field_value")) ;
						
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
						
					
		params.add(vo.get("field_name")) ;
						
					
		params.add(vo.get("field_value")) ;
						
					
		params.add(vo.get("old_field_value")) ;
						
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
									
		params.add(vo.get("field_name")) ;
									
		params.add(vo.get("field_value")) ;
									
		params.add(vo.get("old_field_value")) ;
						
							
		params.add(keyCondMap.get("field_name")) ;
									
		params.add(keyCondMap.get("field_value")) ;
									
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
							
		params.add(keyCondMap.get("field_name")) ;
									
		params.add(keyCondMap.get("field_value")) ;
									
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
