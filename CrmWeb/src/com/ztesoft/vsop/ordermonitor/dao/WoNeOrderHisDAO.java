package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class WoNeOrderHisDAO extends AbstractDAO {

	//查询SQL
	private String SQL_SELECT = "select t.ne_order_id,t.order_id,o.prod_id,o.nbr,o.area_id, t.state_code, t.device_id,d.name device_name,t.command_collect_id,t.rela_ne_order_id,t.execute_time,t.create_date,t.finish_date,t.alert_date,  t.finish_limit,t.workitem_id, t.is_success,t.sub_order_type_id "+
						" from WO_NE_ORDER_HIS t left join wo_order_info_his o  on t.order_id = o.order_id left join ne_device d on t.device_id = d.device_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from WO_NE_ORDER_HIS t left join wo_order_info_his o  on t.order_id = o.order_id left join ne_device d on t.device_id = d.device_id where 1=1";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_NE_ORDER_HIS (ne_order_id, order_id, state_code, device_id, command_collect_id, rela_ne_order_id, execute_time, create_date, finish_date, alert_date, finish_limit, workitem_id, cmd_content, is_success, result_comment, sub_order_type_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_NE_ORDER_HIS set ne_order_id=?, order_id=?, state_code=?, device_id=?, command_collect_id=?, rela_ne_order_id=?, execute_time=?, create_date=?, finish_date=?, alert_date=?, finish_limit=?, workitem_id=?, cmd_content=?, is_success=?, result_comment=?, sub_order_type_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_NE_ORDER_HIS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_NE_ORDER_HIS where ne_order_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_NE_ORDER_HIS set ne_order_id=?, order_id=?, state_code=?, device_id=?, command_collect_id=?, rela_ne_order_id=?, execute_time=?, create_date=?, finish_date=?, alert_date=?, finish_limit=?, workitem_id=?, cmd_content=?, is_success=?, result_comment=?, sub_order_type_id=? where ne_order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select t.ne_order_id,t.order_id,o.prod_id,o.nbr, t.state_code, t.device_id,d.name device_name,t.command_collect_id,t.rela_ne_order_id,t.execute_time,t.create_date,t.finish_date,t.alert_date,  t.finish_limit,t.workitem_id, t.is_success,t.sub_order_type_id,t.cmd_content,t.result_comment from WO_NE_ORDER_HIS t left join wo_order_info o "+
						" on t.order_id = o.order_id  left join ne_device d on t.device_id = d.device_id where t.ne_order_id=? ";

		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoNeOrderHisDAO() {

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
							
		params.add(map.get("ne_order_id")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("state_code")) ;
									
		params.add(map.get("device_id")) ;
									
		params.add(map.get("command_collect_id")) ;
									
		params.add(map.get("rela_ne_order_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("execute_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("finish_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("alert_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("finish_limit" ))) ;
									
		params.add(map.get("workitem_id")) ;
									
		params.add(map.get("cmd_content")) ;
									
		params.add(map.get("is_success")) ;
									
		params.add(map.get("result_comment")) ;
									
		params.add(map.get("sub_order_type_id")) ;
						
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
				
					
		params.add(vo.get("ne_order_id")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("state_code")) ;
						
					
		params.add(vo.get("device_id")) ;
						
					
		params.add(vo.get("command_collect_id")) ;
						
					
		params.add(vo.get("rela_ne_order_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("execute_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("finish_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("alert_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("finish_limit" ))) ;
						
					
		params.add(vo.get("workitem_id")) ;
						
					
		params.add(vo.get("cmd_content")) ;
						
					
		params.add(vo.get("is_success")) ;
						
					
		params.add(vo.get("result_comment")) ;
						
					
		params.add(vo.get("sub_order_type_id")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("ne_order_id")) ;
						
		return params  ;
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


	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE_KEY ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return this.SQL_UPDATE_KEY ;
				
	}
	
}
