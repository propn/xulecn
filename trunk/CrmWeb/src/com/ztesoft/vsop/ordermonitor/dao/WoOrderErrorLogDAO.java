package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class WoOrderErrorLogDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select error_serial_id,order_id,create_date,error_content,error_code from WO_ORDER_ERROR_LOG where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_order_error_log where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_ORDER_ERROR_LOG (error_serial_id, order_id, create_date, error_content, error_code) values (?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update WO_ORDER_ERROR_LOG set error_serial_id=?, order_id=?, create_date=?, error_content=?, error_code=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from WO_ORDER_ERROR_LOG where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from WO_ORDER_ERROR_LOG where error_serial_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update WO_ORDER_ERROR_LOG set error_serial_id=?, order_id=?, create_date=?, error_content=?, error_code=? where error_serial_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select error_serial_id,order_id,create_date,error_content,error_code from WO_ORDER_ERROR_LOG where error_serial_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoOrderErrorLogDAO() {

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
							
		params.add(map.get("error_serial_id")) ;
									
		params.add(map.get("order_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
									
		params.add(map.get("error_content")) ;
									
		params.add(map.get("error_code")) ;
						
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
				
					
		params.add(vo.get("error_serial_id")) ;
						
					
		params.add(vo.get("order_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("error_content")) ;
						
					
		params.add(vo.get("error_code")) ;
						
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
							
		params.add(vo.get("error_serial_id")) ;
									
		params.add(vo.get("order_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("error_content")) ;
									
		params.add(vo.get("error_code")) ;
						
							
		params.add(keyCondMap.get("error_serial_id")) ;
						
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
							
		params.add(keyCondMap.get("error_serial_id")) ;
						
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
