package com.ztesoft.vsop.system.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class SystemInfoDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select s.system_code,s.system_name,s.system_desc,s.is_user_state from SYSTEM_INFO s where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from system_info s where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SYSTEM_INFO (system_code, system_name, system_desc, is_user_state) values (?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update SYSTEM_INFO set system_code=?, system_name=?, system_desc=?, is_user_state=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from SYSTEM_INFO where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from SYSTEM_INFO where system_code=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update SYSTEM_INFO set system_code=?, system_name=?, system_desc=?, is_user_state=? where system_code=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select system_code,system_name,system_desc,is_user_state from SYSTEM_INFO where system_code=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SystemInfoDAO() {

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
							
		params.add(map.get("system_code")) ;
									
		params.add(map.get("system_name")) ;
									
		params.add(map.get("system_desc")) ;
									
		params.add(map.get("is_user_state")) ;
						
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
				
					
		params.add(vo.get("system_code")) ;
						
					
		params.add(vo.get("system_name")) ;
						
					
		params.add(vo.get("system_desc")) ;
						
					
		params.add(vo.get("is_user_state")) ;
						
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
							
		params.add(vo.get("system_code")) ;
									
		params.add(vo.get("system_name")) ;
									
		params.add(vo.get("system_desc")) ;
									
		params.add(vo.get("is_user_state")) ;
						
							
		params.add(keyCondMap.get("system_code")) ;
						
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
							
		params.add(keyCondMap.get("system_code")) ;
						
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
