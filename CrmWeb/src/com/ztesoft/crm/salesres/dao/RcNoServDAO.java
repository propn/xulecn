package com.ztesoft.crm.salesres.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class RcNoServDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select a.resource_instance_id,a.resource_instance_code,a.resource_level,a.sales_resource_id,a.storage_id,a.resource_state,a.state,a.eff_date,a.exp_date,a.imsi_id,a.no_seg_id,a.bala_mode,a.rec_time,a.init_time,a.pre_code,a.middle_code,a.post_code,a.lan_id,a.region_id,a.exch_id,a.storage_name,a.no_seg_name,a.sales_resource_name,a.self_help_flag from RC_NO a where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from rc_no a where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into RC_NO (resource_instance_id, resource_instance_code, resource_level, sales_resource_id, storage_id, resource_state, state, eff_date, exp_date, imsi_id, no_seg_id, bala_mode, rec_time, init_time, pre_code, middle_code, post_code, lan_id, region_id, exch_id, storage_name, no_seg_name, sales_resource_name, self_help_flag) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update RC_NO set resource_instance_id=?, resource_instance_code=?, resource_level=?, sales_resource_id=?, storage_id=?, resource_state=?, state=?, eff_date=?, exp_date=?, imsi_id=?, no_seg_id=?, bala_mode=?, rec_time=?, init_time=?, pre_code=?, middle_code=?, post_code=?, lan_id=?, region_id=?, exch_id=?, storage_name=?, no_seg_name=?, sales_resource_name=?, self_help_flag=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from RC_NO where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from RC_NO where resource_instance_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update RC_NO set resource_instance_id=?, resource_instance_code=?, resource_level=?, sales_resource_id=?, storage_id=?, resource_state=?, state=?, eff_date=?, exp_date=?, imsi_id=?, no_seg_id=?, bala_mode=?, rec_time=?, init_time=?, pre_code=?, middle_code=?, post_code=?, lan_id=?, region_id=?, exch_id=?, storage_name=?, no_seg_name=?, sales_resource_name=?, self_help_flag=? where resource_instance_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select resource_instance_id,resource_instance_code,resource_level,sales_resource_id,storage_id,resource_state,state,eff_date,exp_date,imsi_id,no_seg_id,bala_mode,rec_time,init_time,pre_code,middle_code,post_code,lan_id,region_id,exch_id,storage_name,no_seg_name,sales_resource_name,self_help_flag from RC_NO where resource_instance_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.RM_DATASOURCE ;


	public RcNoServDAO() {

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
							
		params.add(map.get("resource_instance_id")) ;
									
		params.add(map.get("resource_instance_code")) ;
									
		params.add(map.get("resource_level")) ;
									
		params.add(map.get("sales_resource_id")) ;
									
		params.add(map.get("storage_id")) ;
									
		params.add(map.get("resource_state")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("imsi_id")) ;
									
		params.add(map.get("no_seg_id")) ;
									
		params.add(map.get("bala_mode")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("rec_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("init_time" ))) ;
									
		params.add(map.get("pre_code")) ;
									
		params.add(map.get("middle_code")) ;
									
		params.add(map.get("post_code")) ;
									
		params.add(map.get("lan_id")) ;
									
		params.add(map.get("region_id")) ;
									
		params.add(map.get("exch_id")) ;
									
		params.add(map.get("storage_name")) ;
									
		params.add(map.get("no_seg_name")) ;
									
		params.add(map.get("sales_resource_name")) ;
									
		params.add(map.get("self_help_flag")) ;
						
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
				
					
		params.add(vo.get("resource_instance_id")) ;
						
					
		params.add(vo.get("resource_instance_code")) ;
						
					
		params.add(vo.get("resource_level")) ;
						
					
		params.add(vo.get("sales_resource_id")) ;
						
					
		params.add(vo.get("storage_id")) ;
						
					
		params.add(vo.get("resource_state")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("imsi_id")) ;
						
					
		params.add(vo.get("no_seg_id")) ;
						
					
		params.add(vo.get("bala_mode")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("rec_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("init_time" ))) ;
						
					
		params.add(vo.get("pre_code")) ;
						
					
		params.add(vo.get("middle_code")) ;
						
					
		params.add(vo.get("post_code")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
					
		params.add(vo.get("region_id")) ;
						
					
		params.add(vo.get("exch_id")) ;
						
					
		params.add(vo.get("storage_name")) ;
						
					
		params.add(vo.get("no_seg_name")) ;
						
					
		params.add(vo.get("sales_resource_name")) ;
						
					
		params.add(vo.get("self_help_flag")) ;
						
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
							
		params.add(vo.get("resource_instance_id")) ;
									
		params.add(vo.get("resource_instance_code")) ;
									
		params.add(vo.get("resource_level")) ;
									
		params.add(vo.get("sales_resource_id")) ;
									
		params.add(vo.get("storage_id")) ;
									
		params.add(vo.get("resource_state")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("imsi_id")) ;
									
		params.add(vo.get("no_seg_id")) ;
									
		params.add(vo.get("bala_mode")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("rec_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("init_time" ))) ;
									
		params.add(vo.get("pre_code")) ;
									
		params.add(vo.get("middle_code")) ;
									
		params.add(vo.get("post_code")) ;
									
		params.add(vo.get("lan_id")) ;
									
		params.add(vo.get("region_id")) ;
									
		params.add(vo.get("exch_id")) ;
									
		params.add(vo.get("storage_name")) ;
									
		params.add(vo.get("no_seg_name")) ;
									
		params.add(vo.get("sales_resource_name")) ;
									
		params.add(vo.get("self_help_flag")) ;
						
							
		params.add(keyCondMap.get("resource_instance_id")) ;
						
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
							
		params.add(keyCondMap.get("resource_instance_id")) ;
						
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
