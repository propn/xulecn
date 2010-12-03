package com.ztesoft.vsop.protocol.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class NeCommunicateProtocolDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select n.communicate_protocol_id,n.business_obj_id,n.name,n.cat_code,n.cat_desc,n.is_syn,n.memo,o.business_obj_name obj_name from NE_COMMUNICATE_PROTOCOL n left join PB_BUSINESS_OBJ o on o.business_obj_id = n.business_obj_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_communicate_protocol n where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_COMMUNICATE_PROTOCOL (communicate_protocol_id, business_obj_id, name, cat_code, cat_desc, is_syn, memo) values (?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_COMMUNICATE_PROTOCOL set communicate_protocol_id=?, business_obj_id=?, name=?, cat_code=?, cat_desc=?, is_syn=?, memo=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_COMMUNICATE_PROTOCOL where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from NE_COMMUNICATE_PROTOCOL where communicate_protocol_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update NE_COMMUNICATE_PROTOCOL set communicate_protocol_id=?, business_obj_id=?, name=?, cat_code=?, cat_desc=?, is_syn=?, memo=? where communicate_protocol_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select n.communicate_protocol_id,n.business_obj_id,n.name,n.cat_code,n.cat_desc,n.is_syn,n.memo,o.business_obj_name obj_name from NE_COMMUNICATE_PROTOCOL n left join PB_BUSINESS_OBJ o on o.business_obj_id = n.business_obj_id where n.communicate_protocol_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeCommunicateProtocolDAO() {

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
							
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		params.add(seqDao.getNextSequence("Ne_Communicate_Protocol_Seq"));
									
		params.add(map.get("business_obj_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("cat_code")) ;
									
		params.add(map.get("cat_desc")) ;
									
		params.add(map.get("is_syn")) ;
									
		params.add(map.get("memo")) ;
						
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
				
					
		params.add(vo.get("communicate_protocol_id")) ;
						
					
		params.add(vo.get("business_obj_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("cat_code")) ;
						
					
		params.add(vo.get("cat_desc")) ;
						
					
		params.add(vo.get("is_syn")) ;
						
					
		params.add(vo.get("memo")) ;
						
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
							
		params.add(vo.get("communicate_protocol_id")) ;
									
		params.add(vo.get("business_obj_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("cat_code")) ;
									
		params.add(vo.get("cat_desc")) ;
									
		params.add(vo.get("is_syn")) ;
									
		params.add(vo.get("memo")) ;
						
							
		params.add(keyCondMap.get("communicate_protocol_id")) ;
						
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
							
		params.add(keyCondMap.get("communicate_protocol_id")) ;
						
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
