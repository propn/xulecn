package com.ztesoft.vsop.system.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class SpIntSysDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select int_sys_id,communicate_protocol_id,sys_code,name,state,sys_ip,sys_port,sys_conn,sys_user,sys_pwd,sys_vendor,comments,sys_driver,sys_url,ne_protocol_id from SP_INT_SYS where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from sp_int_sys where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_INT_SYS (int_sys_id, communicate_protocol_id, sys_code, name, state, sys_ip, sys_port, sys_conn, sys_user, sys_pwd, sys_vendor, comments, sys_driver, sys_url, ne_protocol_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_INT_SYS set int_sys_id=?, communicate_protocol_id=?, sys_code=?, name=?, state=?, sys_ip=?, sys_port=?, sys_conn=?, sys_user=?, sys_pwd=?, sys_vendor=?, comments=?, sys_driver=?, sys_url=?, ne_protocol_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_INT_SYS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SP_INT_SYS where int_sys_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SP_INT_SYS set int_sys_id=?, communicate_protocol_id=?, sys_code=?, name=?, state=?, sys_ip=?, sys_port=?, sys_conn=?, sys_user=?, sys_pwd=?, sys_vendor=?, comments=?, sys_driver=?, sys_url=?, ne_protocol_id=? where int_sys_id=?";
		
	//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select s.int_sys_id,s.communicate_protocol_id,s.sys_code,s.name,s.state,s.sys_ip,s.sys_port,s.sys_conn,s.sys_user,s.sys_pwd,s.sys_vendor,s.comments,s.sys_driver,s.sys_url,s.ne_protocol_id ,p.name nename,c.name communname " +
			" from SP_INT_SYS s left join NE_PROTOCOL p on s.ne_protocol_id = p.ne_protocol_id left join  NE_COMMUNICATE_PROTOCOL c on s.communicate_protocol_id = c.communicate_protocol_id "+
			" where int_sys_id=? ";
	
	private String SQL_VALIDATE_SYS_CODE = "select t.sys_code,t.int_sys_id from sp_int_sys t where t.sys_code = ?";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE  ;


	public SpIntSysDAO() {

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
		params.add(seqDao.getNextSequence("SP_INT_SYS_SEQ")) ;
									
		params.add(map.get("communicate_protocol_id")) ;
									
		params.add(map.get("sys_code")) ;
									
		params.add(map.get("name")) ;
									
		params.add("10A") ;
									
		params.add(map.get("sys_ip")) ;
									
		params.add(map.get("sys_port")) ;
									
		params.add(map.get("sys_conn")) ;
									
		params.add(map.get("sys_user")) ;
									
		params.add(map.get("sys_pwd")) ;
									
		params.add(map.get("sys_vendor")) ;
									
		params.add(map.get("comments")) ;
									
		params.add(map.get("sys_driver")) ;
									
		params.add(map.get("sys_url")) ;
									
		params.add(map.get("ne_protocol_id")) ;
						
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
				
					
		params.add(vo.get("int_sys_id")) ;
						
					
		params.add(vo.get("communicate_protocol_id")) ;
						
					
		params.add(vo.get("sys_code")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add("10A") ;
						
					
		params.add(vo.get("sys_ip")) ;
						
					
		params.add(vo.get("sys_port")) ;
						
					
		params.add(vo.get("sys_conn")) ;
						
					
		params.add(vo.get("sys_user")) ;
						
					
		params.add(vo.get("sys_pwd")) ;
						
					
		params.add(vo.get("sys_vendor")) ;
						
					
		params.add(vo.get("comments")) ;
						
					
		params.add(vo.get("sys_driver")) ;
						
					
		params.add(vo.get("sys_url")) ;
						
					
		params.add(vo.get("ne_protocol_id")) ;
						
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
							
		params.add(vo.get("int_sys_id")) ;
									
		params.add(vo.get("communicate_protocol_id")) ;
									
		params.add(vo.get("sys_code")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("sys_ip")) ;
									
		params.add(vo.get("sys_port")) ;
									
		params.add(vo.get("sys_conn")) ;
									
		params.add(vo.get("sys_user")) ;
									
		params.add(vo.get("sys_pwd")) ;
									
		params.add(vo.get("sys_vendor")) ;
									
		params.add(vo.get("comments")) ;
									
		params.add(vo.get("sys_driver")) ;
									
		params.add(vo.get("sys_url")) ;
									
		params.add(vo.get("ne_protocol_id")) ;
						
							
		params.add(keyCondMap.get("int_sys_id")) ;
						
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
							
		params.add(keyCondMap.get("int_sys_id")) ;
						
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
	
	public String getSqlForValidateSysCode() {
		return this.SQL_VALIDATE_SYS_CODE;
	}
	
}
