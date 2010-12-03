package com.ztesoft.vsop.ordermonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.vsop.InfOrderRelationVo;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.ordermonitor.vo.WoNeOrderVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class WoNeOrderDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select t.ne_order_id,t.order_id,o.prod_id,o.nbr,o.area_id, t.state_code, t.device_id,d.name device_name,t.command_collect_id,t.rela_ne_order_id,t.execute_time,t.create_date,t.finish_date,t.alert_date,  t.finish_limit,t.workitem_id, t.is_success,t.sub_order_type_id,t.cmd_content "+
						" from WO_NE_ORDER t left join wo_order_info o  on t.order_id = o.order_id left join ne_device d on t.device_id = d.device_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from WO_NE_ORDER t left join wo_order_info o  on t.order_id = o.order_id left join ne_device d on t.device_id = d.device_id where 1=1";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_NE_ORDER (ne_order_id, order_id, state_code, device_id, command_collect_id, rela_ne_order_id, execute_time, create_date, finish_date, alert_date, finish_limit, workitem_id, cmd_content, is_success, result_comment, sub_order_type_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_NE_ORDER set ne_order_id=?, order_id=?, state_code=?, device_id=?, command_collect_id=?, rela_ne_order_id=?, execute_time=?, create_date=?, finish_date=?, alert_date=?, finish_limit=?, workitem_id=?, cmd_content=?, is_success=?, result_comment=?, sub_order_type_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_NE_ORDER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_NE_ORDER where ne_order_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_NE_ORDER set ne_order_id=?, order_id=?, state_code=?, device_id=?, command_collect_id=?, rela_ne_order_id=?, execute_time=?, create_date=?, finish_date=?, alert_date=?, finish_limit=?, workitem_id=?, cmd_content=?, is_success=?, result_comment=?, sub_order_type_id=? where ne_order_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select t.ne_order_id,t.order_id,o.prod_id,o.nbr, t.state_code, t.device_id,d.name device_name,t.command_collect_id,t.rela_ne_order_id,t.execute_time,t.create_date,t.finish_date,t.alert_date,  t.finish_limit,t.workitem_id, t.is_success,t.sub_order_type_id,t.cmd_content,t.result_comment from WO_NE_ORDER t left join wo_order_info o "+
						" on t.order_id = o.order_id  left join ne_device d on t.device_id = d.device_id where t.ne_order_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoNeOrderDAO() {

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
						
							
		params.add(keyCondMap.get("ne_order_id")) ;
						
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
							
		params.add(keyCondMap.get("ne_order_id")) ;
						
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


	public boolean successWoNeOrder(String ne_order_id, String state,
			String is_success) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String UPDATE_NE_ORDER_SUCCESS = " update WO_NE_ORDER set  state_code=?, is_success=? where ne_order_id = ? ";
		try {
			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(UPDATE_NE_ORDER_SUCCESS);
			int index = 1;
			stmt.setString( index++,  state );
			stmt.setString( index++,  is_success );
			stmt.setString( index++,  ne_order_id );
			int row =stmt.executeUpdate();
			if(row>0)
				return true;
			
		}
		catch (SQLException se) {
			Debug.print(UPDATE_NE_ORDER_SUCCESS,this);
			throw new DAOSystemException("SQLException while :\n"+UPDATE_NE_ORDER_SUCCESS, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}


	public boolean changeState(String ne_order_id, String state) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String UPDATE_NE_ORDER_SUCCESS = " update WO_NE_ORDER set  state_code=? where ne_order_id = ? ";
		try {
			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(UPDATE_NE_ORDER_SUCCESS);
			int index = 1;
			stmt.setString( index++,  state );
			stmt.setString( index++,  ne_order_id );
			int row =stmt.executeUpdate();
			if(row>0)
				return true;
			
		}
		catch (SQLException se) {
			Debug.print(UPDATE_NE_ORDER_SUCCESS,this);
			throw new DAOSystemException("SQLException while :\n"+UPDATE_NE_ORDER_SUCCESS, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	
	/**
	 * 取重送业务平台的子工单记录
	 */
	public List getReSendWoNeOrderList(String stateCodes,String deviceId ) throws Exception {
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;

		PreparedStatement updatePsmt = null;
		ResultSet rs = null;
		List woNeOrderList = new ArrayList();
		int maxTimes=3;//默认送三次
		String updateSql = "UPDATE wo_ne_order SET state_code ='10C' WHERE  ne_order_id =? ";
		String sql = " select * from wo_ne_order b ,wo_ne_order_sub_prod c where b.ne_order_id=c.ne_order_id " +
				" and b.state_code<>'10C' and c.result_code<>'0' and b.WORKITEM_ID<?  ";
		StringBuffer whereCond=new StringBuffer();
		if( !"".equals(DcSystemParamManager.getParameter("RESEND_PLATFORM_NUM"))&&null!=DcSystemParamManager.getParameter("RESEND_PLATFORM_NUM")){
			try {
				maxTimes=new Integer(DcSystemParamManager.getParameter("RESEND_PLATFORM_NUM")).intValue();
			} catch(Exception e){
				maxTimes=3;
			}
		}
		if (stateCodes != null && !"".equals(stateCodes.trim())){
			stateCodes =stateCodes.replaceAll(",", "','");
			stateCodes="'"+stateCodes+"'";
			whereCond.append(" and  c.result_code in ( "+stateCodes+")");
		}
		
		if (deviceId != null && !"".equals(deviceId.trim())){
			whereCond.append(" and  b.DEVICE_ID in ( "+deviceId+")");
		}
		whereCond.append("order by b.ne_order_id  ASC");
		sql=sql+whereCond.toString();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, maxTimes);
			rs = psmt.executeQuery();
			Map updateMap=new HashMap();
			
			updatePsmt = conn.prepareStatement(updateSql);
			while(rs.next()){
				WoNeOrderVO woNeOrderVO=new WoNeOrderVO();
				String neOrderId = rs.getString("NE_ORDER_ID");
				woNeOrderVO.setNe_order_id(neOrderId);
				woNeOrderVO.setCmd_content(rs.getString("cmd_content"));	
				woNeOrderVO.setWorkitem_id(rs.getString("workitem_id"));
				woNeOrderVO.setState_code(rs.getString("state_code"));	
				String state="";//10C重送处理中
				if(null!=updateMap&&updateMap.size()>0){//根据子工单id取状态
					 state=(String) updateMap.get(neOrderId);
				}
				
				if(!"10C".equals(state)||state==null){
					woNeOrderList.add(woNeOrderVO);
					updatePsmt.setString(1, neOrderId);
					updatePsmt.addBatch();
					//ne_order_id 有重复，已经修改的排除
					updateMap.put(neOrderId, "10C");
				}
				
			}
			if(woNeOrderList.size() > 0){
				updatePsmt.executeBatch();
				return woNeOrderList;
			}

		} catch(Exception e){
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
			DAOUtils.closeStatement(updatePsmt);
		}
		return null;
	}
	
	/**
	 * 将重送业务平台的次数加1
	 * @param list
	 * @return
	 */
	public boolean updateWoNeOrderSendTimesWhenFail(String sendTimes,String neOrderId ,String stateCode) throws Exception{
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update wo_ne_order set WORKITEM_ID = ? where ne_order_id =?";
		String sqlB = "update wo_ne_order set WORKITEM_ID = ?, state_code =? where ne_order_id =?";
		try {
			con = LegacyDAOUtil.getConnection();
			if("".equals(stateCode)||stateCode==null){
				ps = con.prepareStatement(sql);
				ps.setString(1, sendTimes);
				ps.setString(2, neOrderId);
			}else{
				ps = con.prepareStatement(sqlB);
				ps.setString(1, sendTimes);
				ps.setString(2, stateCode);
				ps.setString(3, neOrderId);
			}
			
			
			int row =ps.executeUpdate();
			if(row>0)
				return true;
		} catch (Exception e) {
			throw e;
		} finally {
			DAOUtils.closeStatement(ps, this);
		}
		return false;
	}
	
}
