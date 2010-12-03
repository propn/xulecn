package com.ztesoft.vsop.dispatchrule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class WoDispatchRuleDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select dispatch_rule_id,name,code,state,dispatch_rule,dispatch_queue_id from WO_DISPATCH_RULE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_dispatch_rule where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_DISPATCH_RULE (dispatch_rule_id, name, code, state, dispatch_rule, dispatch_queue_id) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_DISPATCH_RULE set dispatch_rule_id=?, name=?, code=?, state=?, dispatch_rule=?, dispatch_queue_id=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_DISPATCH_RULE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from WO_DISPATCH_RULE where dispatch_rule_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update WO_DISPATCH_RULE set dispatch_rule_id=?, name=?, code=?, state=?, dispatch_rule=?, dispatch_queue_id=? where dispatch_rule_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select dispatch_rule_id,name,code,state,dispatch_rule,dispatch_queue_id from WO_DISPATCH_RULE where dispatch_rule_id=? ";
	
	private String SQL_UPDATE_STATE = "update WO_DISPATCH_RULE set state=? where dispatch_rule_id =? ";
	private String SQL_RELATE_DEVICE = "select 1 from WO_DISPATCH_DEVICE t  where t.dispatch_rule_id =? ";

	private String SQL_COUNT_CODE = "select 1 from WO_DISPATCH_RULE where code =? ";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoDispatchRuleDAO() {

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
							
		SequenceManageDAO dao = new SequenceManageDAOImpl();
		params.add(dao.getNextSequence("Wo_Dispatch_Rule_Seq")) ;
		
		params.add(map.get("name")) ;
									
		params.add(map.get("code")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("dispatch_rule")) ;
									
		params.add(map.get("dispatch_queue_id")) ;
						
		return params ;
	}
	public boolean insert(Map map) throws FrameException {
		if(map == null || map.isEmpty())
			return false ;
		List params = new ArrayList() ;
							
		SequenceManageDAO dao = new SequenceManageDAOImpl();
		String ruleId = dao.getNextSequence("Wo_Dispatch_Rule_Seq");
		params.add(ruleId) ;
		
		params.add(map.get("name")) ;
									
		params.add(map.get("code")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("dispatch_rule")) ;
									
		params.add(map.get("dispatch_queue_id")) ;

		String SQL = getInsertSQL() ;
		boolean saveRule=Base.update(this.getDbName(), SQL, params,  1, Const.UN_JUDGE_ERROR, "") > 0 ;
		if(saveRule){
			saveStartegyItem(ruleId);
		}
		return saveRule;
	}
	private boolean saveStartegyItem(String ruleId) throws FrameException{
		WoStartegyItemDAO itemdao = new WoStartegyItemDAO();
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		List list = itemdao.findByCond("");
		for (int i = 0; i < list.size(); i++) {
			Map listMap = (Map) list.get(i);
			Map map = new HashMap();
			map.put("dispatch_rule_id",ruleId);
			map.put("item_val", listMap.get("default_value"));
			map.put("item_code",listMap.get("item_code"));
			dao.insert(map);
		}
		return true;
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
				
					
		params.add(vo.get("dispatch_rule_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("code")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("dispatch_rule")) ;
						
					
		params.add(vo.get("dispatch_queue_id")) ;
						
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
							
		params.add(vo.get("dispatch_rule_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("code")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("dispatch_rule")) ;
									
		params.add(vo.get("dispatch_queue_id")) ;
						
							
		params.add(keyCondMap.get("dispatch_rule_id")) ;
						
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
							
		params.add(keyCondMap.get("dispatch_rule_id")) ;
						
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
	public String getSqlCountCode(){
		return SQL_COUNT_CODE;
	}

	public boolean changeStateWoDispatchRuleById(String dispatch_rule_id,String state) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE_STATE);
			int index = 1;
			stmt.setString( index++,  state );
			stmt.setString( index++,  dispatch_rule_id );
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_UPDATE_STATE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;

	}


	public boolean isRelate(String ruleId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_RELATE_DEVICE);
			int index = 1;
			stmt.setString( index++,  ruleId );
			int rows = stmt.executeQuery().getRow();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_RELATE_DEVICE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	
}
