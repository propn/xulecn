package com.ztesoft.vsop.command.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class WoCommandCollectDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select command_collect_id,device_id,name,collect_seq from WO_COMMAND_COLLECT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_command_collect where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_COMMAND_COLLECT (command_collect_id, device_id, name, collect_seq) values (?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_COMMAND_COLLECT set command_collect_id=?, device_id=?, name=?, collect_seq=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_COMMAND_COLLECT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_COMMAND_COLLECT where command_collect_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_COMMAND_COLLECT set command_collect_id=?, device_id=?, name=?, collect_seq=? where command_collect_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select command_collect_id,device_id,name,collect_seq from WO_COMMAND_COLLECT where command_collect_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;

	//验证系统集合是否唯一时用
	private String SQL_VALIDATE_COLLECT_SEQ = "select t.collect_seq,t.device_id,n.device_id from wo_command_collect t,ne_device n where t.device_id = n.device_id and t.device_id = ? and t.collect_seq = ?";

	//	验证对端系统是否有关联的指令模板集
	private String SQL_SELECT_DEVICE_ID = "select 1 from WO_COMMAND_COLLECT where device_id=? ";

	public WoCommandCollectDAO() {

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
		params.add(seqDao.getNextSequence("Wo_Command_Collect_Seq")) ;
									
		params.add(map.get("device_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("collect_seq")) ;
						
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
				
					
		params.add(vo.get("command_collect_id")) ;
						
					
		params.add(vo.get("device_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("collect_seq")) ;
						
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
							
		params.add(vo.get("command_collect_id")) ;
									
		params.add(vo.get("device_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("collect_seq")) ;
						
							
		params.add(keyCondMap.get("command_collect_id")) ;
						
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
							
		params.add(keyCondMap.get("command_collect_id")) ;
						
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
	
	public String getSqlForValidateCollectSeq() {
		return this.SQL_VALIDATE_COLLECT_SEQ;
	}
	public String getCollectByDeviceId(){
		return SQL_SELECT_DEVICE_ID;
	}
}
