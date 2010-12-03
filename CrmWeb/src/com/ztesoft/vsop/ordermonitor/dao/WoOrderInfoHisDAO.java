package com.ztesoft.vsop.ordermonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.common.util.tracer.Debug;

public class WoOrderInfoHisDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select t.order_id,t.prod_id,t.nbr,t.rela_order_id,t.state_code,t.dispatch_grade_id,t.area_id,t.receive_date,t.execute_date,t.finish_limit,t.finish_date,t.process_instance_id,t.pre_nbr,t.order_type_id,t.type_grade,t.control_str,t.executed_str,t.act_type,t.int_sys_id ,s.name,o.order_type_name from WO_ORDER_INFO_HIS t left join sp_int_sys s on t.int_sys_id = s.int_sys_id left join sp_out_order_type o on t.order_type_id = o.out_order_type_id where 1=1 ";
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_order_info_his t where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_ORDER_INFO_HIS (order_id, prod_id, nbr, rela_order_id, state_code, dispatch_grade_id, area_id, receive_date, execute_date, finish_limit, finish_date, process_instance_id, pre_nbr, order_type_id, type_grade, control_str, executed_str, act_type, int_sys_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_ORDER_INFO_HIS set order_id=?, prod_id=?, nbr=?, rela_order_id=?, state_code=?, dispatch_grade_id=?, area_id=?, receive_date=?, execute_date=?, finish_limit=?, finish_date=?, process_instance_id=?, pre_nbr=?, order_type_id=?, type_grade=?, control_str=?, executed_str=?, act_type=?, int_sys_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_ORDER_INFO_HIS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_ORDER_INFO_HIS where order_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_ORDER_INFO_HIS set order_id=?, prod_id=?, nbr=?, rela_order_id=?, state_code=?, dispatch_grade_id=?, area_id=?, receive_date=?, execute_date=?, finish_limit=?, finish_date=?, process_instance_id=?, pre_nbr=?, order_type_id=?, type_grade=?, control_str=?, executed_str=?, act_type=?, int_sys_id=? where order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select t.order_id,t.prod_id,t.nbr,t.rela_order_id,t.state_code,t.dispatch_grade_id,t.area_id,t.receive_date,t.execute_date,t.finish_limit,t.finish_date,t.process_instance_id,t.pre_nbr,t.order_type_id,t.type_grade,t.control_str,t.executed_str,t.act_type,t.int_sys_id ,s.name,o.order_type_name from WO_ORDER_INFO_HIS t left join sp_int_sys s on t.int_sys_id = s.int_sys_id left join sp_out_order_type o on t.order_type_id = o.out_order_type_id where t.order_id=? ";

	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoOrderInfoHisDAO() {

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
							
		params.add(map.get("order_id")) ;
									
		params.add(map.get("prod_id")) ;
									
		params.add(map.get("nbr")) ;
									
		params.add(map.get("rela_order_id")) ;
									
		params.add(map.get("state_code")) ;
									
		params.add(map.get("dispatch_grade_id")) ;
									
		params.add(map.get("area_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("receive_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("execute_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("finish_limit" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("finish_date" ))) ;
									
		params.add(map.get("process_instance_id")) ;
									
		params.add(map.get("pre_nbr")) ;
									
		params.add(map.get("order_type_id")) ;
									
		params.add(map.get("type_grade")) ;
									
		params.add(map.get("control_str")) ;
									
		params.add(map.get("executed_str")) ;
									
		params.add(map.get("act_type")) ;
									
		params.add(map.get("int_sys_id")) ;
						
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
				
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("prod_id")) ;
						
					
		params.add(vo.get("nbr")) ;
						
					
		params.add(vo.get("rela_order_id")) ;
						
					
		params.add(vo.get("state_code")) ;
						
					
		params.add(vo.get("dispatch_grade_id")) ;
						
					
		params.add(vo.get("area_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("receive_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("execute_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("finish_limit" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("finish_date" ))) ;
						
					
		params.add(vo.get("process_instance_id")) ;
						
					
		params.add(vo.get("pre_nbr")) ;
						
					
		params.add(vo.get("order_type_id")) ;
						
					
		params.add(vo.get("type_grade")) ;
						
					
		params.add(vo.get("control_str")) ;
						
					
		params.add(vo.get("executed_str")) ;
						
					
		params.add(vo.get("act_type")) ;
						
					
		params.add(vo.get("int_sys_id")) ;
						
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
							
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("prod_id")) ;
									
		params.add(vo.get("nbr")) ;
									
		params.add(vo.get("rela_order_id")) ;
									
		params.add(vo.get("state_code")) ;
									
		params.add(vo.get("dispatch_grade_id")) ;
									
		params.add(vo.get("area_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("receive_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("execute_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("finish_limit" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("finish_date" ))) ;
									
		params.add(vo.get("process_instance_id")) ;
									
		params.add(vo.get("pre_nbr")) ;
									
		params.add(vo.get("order_type_id")) ;
									
		params.add(vo.get("type_grade")) ;
									
		params.add(vo.get("control_str")) ;
									
		params.add(vo.get("executed_str")) ;
									
		params.add(vo.get("act_type")) ;
									
		params.add(vo.get("int_sys_id")) ;
						
							
		params.add(keyCondMap.get("order_id")) ;
						
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
							
		params.add(keyCondMap.get("order_id")) ;
						
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
	public List getBatchList(String contral_str,String order_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		String SQL_NE_ORDER_STATE = " select d.name,o.state_code from WO_ORDER_INFO_HIS o,ne_device d where o.device_id = d.device_id and d.sys_code in ("+contral_str+") and o.order_id = ? ";
		try {
			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_NE_ORDER_STATE);
			int index = 1;
			stmt.setString( index++,  order_id );
			rs = stmt.executeQuery();
			while(rs.next()){
				Map map = new HashMap();
				map.put("name", rs.getString("name"));
				map.put("state_code", rs.getString("state_code"));
				list.add(map);
			}
		}
		catch (SQLException se) {
			Debug.print(SQL_NE_ORDER_STATE,this);
			throw new DAOSystemException("SQLException while :\n"+SQL_NE_ORDER_STATE, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return list;
	}
}
