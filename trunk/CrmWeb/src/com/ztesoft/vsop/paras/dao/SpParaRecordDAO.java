package com.ztesoft.vsop.paras.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;

public class SpParaRecordDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select record_id,record_name,record_path,rec_type,groupby_codes,orderby_asc_codes,orderby_desc_codes,group_order_type,target_path from SP_PARA_RECORD where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from sp_para_record where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_PARA_RECORD (record_id, record_name, record_path, rec_type, groupby_codes, orderby_asc_codes, orderby_desc_codes, group_order_type, target_path) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_PARA_RECORD set record_id=?, record_name=?, record_path=?, rec_type=?, groupby_codes=?, orderby_asc_codes=?, orderby_desc_codes=?, group_order_type=?, target_path=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_PARA_RECORD where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SP_PARA_RECORD where record_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SP_PARA_RECORD set record_id=?, record_name=?, record_path=?, rec_type=?, groupby_codes=?, orderby_asc_codes=?, orderby_desc_codes=?, group_order_type=?, target_path=? where record_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select record_id,record_name,record_path,rec_type,groupby_codes,orderby_asc_codes,orderby_desc_codes,group_order_type,target_path from SP_PARA_RECORD where record_id=? ";
	
	private String SQL_VALIDATE_RECORD_ID = "select t.OUT_ORDER_TYPE_ID, t.RECORD_ID, (select a.ORDER_TYPE_NAME from SP_OUT_ORDER_TYPE a where a.out_order_type_id = t.out_order_type_id) ORDER_TYPE_NAME from sp_order_type_records t where RECORD_ID=?";
	
	//数据项记录管理中添加目标数据项时验证目标数据项的唯一性
	private String SQL_VALIDATE_COMMAND_PARA = "select t.record_id,t.command_id from sp_para_record_list t where  t.record_id = ? and t.command_id = ?";
	
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SpParaRecordDAO() {

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
		params.add(seqDao.getNextSequence("Sp_Para_Record_Seq")) ;
									
		params.add(map.get("record_name")) ;
									
		params.add(map.get("record_path")) ;
									
		params.add(map.get("rec_type")) ;
									
		params.add(map.get("groupby_codes")) ;
									
		params.add(map.get("orderby_asc_codes")) ;
									
		params.add(map.get("orderby_desc_codes")) ;
									
		params.add(map.get("group_order_type")) ;
									
		params.add(map.get("target_path")) ;
						
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
				
					
		params.add(vo.get("record_id")) ;
						
					
		params.add(vo.get("record_name")) ;
						
					
		params.add(vo.get("record_path")) ;
						
					
		params.add(vo.get("rec_type")) ;
						
					
		params.add(vo.get("groupby_codes")) ;
						
					
		params.add(vo.get("orderby_asc_codes")) ;
						
					
		params.add(vo.get("orderby_desc_codes")) ;
						
					
		params.add(vo.get("group_order_type")) ;
						
					
		params.add(vo.get("target_path")) ;
						
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
							
		params.add(vo.get("record_id")) ;
									
		params.add(vo.get("record_name")) ;
									
		params.add(vo.get("record_path")) ;
									
		params.add(vo.get("rec_type")) ;
									
		params.add(vo.get("groupby_codes")) ;
									
		params.add(vo.get("orderby_asc_codes")) ;
									
		params.add(vo.get("orderby_desc_codes")) ;
									
		params.add(vo.get("group_order_type")) ;
									
		params.add(vo.get("target_path")) ;
						
							
		params.add(keyCondMap.get("record_id")) ;
						
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
							
		params.add(keyCondMap.get("record_id")) ;
						
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
	
	public String getSQLForValidateRecordId() {
		return this.SQL_VALIDATE_RECORD_ID;
	}
	
	public String getSqlForValidateCommandPara() {
		return this.SQL_VALIDATE_COMMAND_PARA;
	}
}
