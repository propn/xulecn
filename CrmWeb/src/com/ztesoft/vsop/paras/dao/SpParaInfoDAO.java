package com.ztesoft.vsop.paras.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class SpParaInfoDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select para_id,parent_para_id,para_group_id,para_dir_id,int_sys_id,name,para_code,node_path,node_attr,comments,is_reflect,para_type,is_key from SP_PARA_INFO where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from sp_para_info where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_PARA_INFO (para_id, parent_para_id, para_group_id, para_dir_id, int_sys_id, name, para_code, node_path, node_attr, comments, is_reflect, para_type, is_key) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_PARA_INFO set para_id=?, parent_para_id=?, para_group_id=?, para_dir_id=?, int_sys_id=?, name=?, para_code=?, node_path=?, node_attr=?, comments=?, is_reflect=?, para_type=?, is_key=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_PARA_INFO where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SP_PARA_INFO where para_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SP_PARA_INFO set para_id=?, parent_para_id=?, para_group_id=?, para_dir_id=?, int_sys_id=?, name=?, para_code=?, node_path=?, node_attr=?, comments=?, is_reflect=?, para_type=?, is_key=? where para_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select para_id,parent_para_id,para_group_id,para_dir_id,int_sys_id,name,para_code,node_path,node_attr,comments,is_reflect,para_type,is_key from SP_PARA_INFO where para_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SpParaInfoDAO() {

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
		params.add(seqDao.getNextSequence("Sp_Para_Info_Seq")) ;
									
		params.add(map.get("parent_para_id")) ;
									
		params.add(map.get("para_group_id")) ;
									
		params.add(map.get("para_dir_id")) ;
									
		params.add(map.get("int_sys_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("para_code")) ;
									
		params.add(map.get("node_path")) ;
									
		params.add(map.get("node_attr")) ;
									
		params.add(map.get("comments")) ;
									
		params.add(map.get("is_reflect")) ;
									
		params.add(map.get("para_type")) ;
									
		params.add(map.get("is_key")) ;
						
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
				
					
		params.add(vo.get("para_id")) ;
						
					
		params.add(vo.get("parent_para_id")) ;
						
					
		params.add(vo.get("para_group_id")) ;
						
					
		params.add(vo.get("para_dir_id")) ;
						
					
		params.add(vo.get("int_sys_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("para_code")) ;
						
					
		params.add(vo.get("node_path")) ;
						
					
		params.add(vo.get("node_attr")) ;
						
					
		params.add(vo.get("comments")) ;
						
					
		params.add(vo.get("is_reflect")) ;
						
					
		params.add(vo.get("para_type")) ;
						
					
		params.add(vo.get("is_key")) ;
						
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
							
		params.add(vo.get("para_id")) ;
									
		params.add(vo.get("parent_para_id")) ;
									
		params.add(vo.get("para_group_id")) ;
									
		params.add(vo.get("para_dir_id")) ;
									
		params.add(vo.get("int_sys_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("para_code")) ;
									
		params.add(vo.get("node_path")) ;
									
		params.add(vo.get("node_attr")) ;
									
		params.add(vo.get("comments")) ;
									
		params.add(vo.get("is_reflect")) ;
									
		params.add(vo.get("para_type")) ;
									
		params.add(vo.get("is_key")) ;
						
							
		params.add(keyCondMap.get("para_id")) ;
						
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
							
		params.add(keyCondMap.get("para_id")) ;
						
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
