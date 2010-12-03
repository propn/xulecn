package com.ztesoft.vsop.paras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class NeTranRuleParaDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select p.tran_rule_id,p.para_id,p.seq,p.code,i.name para_name from NE_TRAN_RULE_PARA p,sp_para_info i where p.para_id =i.para_id ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_tran_rule_para p where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_TRAN_RULE_PARA (tran_rule_id, para_id, seq, code) values (?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_TRAN_RULE_PARA set tran_rule_id=?, para_id=?, seq=?, code=? where tran_rule_id=?  and para_id=? ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_TRAN_RULE_PARA where tran_rule_id=?  and para_id=? ";
	
	private String SQL_BY_KEY =	"select p.tran_rule_id,p.para_id,p.seq,p.code,i.name para_name from NE_TRAN_RULE_PARA p,sp_para_info i where p.para_id =i.para_id and p.tran_rule_id=?  and p.para_id=? ";
		
	public String SQL_COUNT_PARA = "select 1 from NE_TRAN_RULE_PARA where tran_rule_id=? and para_id=?";
	public String SQL_COUNT_SEQ = "select 1 from NE_TRAN_RULE_PARA where tran_rule_id=? and seq=?";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeTranRuleParaDAO() {

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
							
		params.add(map.get("tran_rule_id")) ;
									
		params.add(map.get("para_id")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("code")) ;
						
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
				
					
		params.add(vo.get("tran_rule_id")) ;
						
					
		params.add(vo.get("para_id")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("code")) ;
						
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
							
		params.add(keyCondMap.get("tran_rule_id")) ;
		params.add(keyCondMap.get("para_id")) ;
		//params.add(keyCondMap.get("seq")) ;
						
		return params  ;
	}
		
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
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
								
		return SQL_BY_KEY;	
				
	}
	public boolean updateByMap(Map param) {
		Map keyCondMap = (Map) param.get("NeTranRulePara");
		Map paraMap = (Map) param.get("paraMap");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_UPDATE);
			int index = 1;
			stmt.setObject(index++,   keyCondMap.get("tran_rule_id"));
			stmt.setObject( index++,  keyCondMap.get("para_id"));
			stmt.setObject( index++,  keyCondMap.get("seq"));
			stmt.setObject( index++,  keyCondMap.get("code"));
			
			stmt.setObject( index++,  paraMap.get("tran_rule_id"));
			stmt.setObject( index++,  paraMap.get("para_id"));
			//stmt.setObject( index++,  paraMap.get("seq"));
			
			
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_UPDATE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_UPDATE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;	
		}
	public boolean deleteByMap(Map param) {
		Map paraMap = (Map) param.get("paraMap");
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_DELETE);
			int index = 1;
			stmt.setObject( index++,  paraMap.get("tran_rule_id"));
			stmt.setObject( index++,  paraMap.get("para_id"));
			//stmt.setObject( index++,  paraMap.get("seq"));
			
			
			int rows = stmt.executeUpdate();
			if(rows>0)
				return true;
		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_DELETE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;	
		}
	
}
