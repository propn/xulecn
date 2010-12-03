package com.ztesoft.vsop.ordertype.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class SpOutOrderTypeDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select out_order_type_id,out_order_type_code,catalog_id,int_sys_id,order_feedback_type_id,order_type_name,is_rule,comments,is_syn,type_class from SP_OUT_ORDER_TYPE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from sp_out_order_type where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_OUT_ORDER_TYPE (out_order_type_id, out_order_type_code, catalog_id, int_sys_id, order_feedback_type_id, order_type_name, is_rule, comments, is_syn, type_class) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_OUT_ORDER_TYPE set out_order_type_id=?, out_order_type_code=?, catalog_id=?, int_sys_id=?, order_feedback_type_id=?, order_type_name=?, is_rule=?, comments=?, is_syn=?, type_class=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_OUT_ORDER_TYPE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SP_OUT_ORDER_TYPE where out_order_type_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SP_OUT_ORDER_TYPE set out_order_type_id=?, out_order_type_code=?, catalog_id=?, int_sys_id=?, order_feedback_type_id=?, order_type_name=?, is_rule=?, comments=?, is_syn=?, type_class=? where out_order_type_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select out_order_type_id,out_order_type_code,catalog_id,int_sys_id,order_feedback_type_id,order_type_name,is_rule,comments,is_syn,type_class from SP_OUT_ORDER_TYPE where out_order_type_id=? ";
	
	private String SQL_COUNT_CODE ="select * from SP_OUT_ORDER_TYPE where out_order_type_code = ?";
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SpOutOrderTypeDAO() {

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
		params.add(seqDao.getNextSequence("Sp_Out_Order_Type_Seq")) ;
									
		params.add(map.get("out_order_type_code")) ;
									
		params.add(map.get("catalog_id")) ;
									
		params.add(map.get("int_sys_id")) ;
									
		params.add(map.get("order_feedback_type_id")) ;
									
		params.add(map.get("order_type_name")) ;
									
		params.add(map.get("is_rule")) ;
									
		params.add(map.get("comments")) ;
									
		params.add(map.get("is_syn")) ;
									
		params.add(map.get("type_class")) ;
						
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
				
					
		params.add(vo.get("out_order_type_id")) ;
						
					
		params.add(vo.get("out_order_type_code")) ;
						
					
		params.add(vo.get("catalog_id")) ;
						
					
		params.add(vo.get("int_sys_id")) ;
						
					
		params.add(vo.get("order_feedback_type_id")) ;
						
					
		params.add(vo.get("order_type_name")) ;
						
					
		params.add(vo.get("is_rule")) ;
						
					
		params.add(vo.get("comments")) ;
						
					
		params.add(vo.get("is_syn")) ;
						
					
		params.add(vo.get("type_class")) ;
						
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
							
		params.add(vo.get("out_order_type_id")) ;
									
		params.add(vo.get("out_order_type_code")) ;
									
		params.add(vo.get("catalog_id")) ;
									
		params.add(vo.get("int_sys_id")) ;
									
		params.add(vo.get("order_feedback_type_id")) ;
									
		params.add(vo.get("order_type_name")) ;
									
		params.add(vo.get("is_rule")) ;
									
		params.add(vo.get("comments")) ;
									
		params.add(vo.get("is_syn")) ;
									
		params.add(vo.get("type_class")) ;
						
							
		params.add(keyCondMap.get("out_order_type_id")) ;
						
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
							
		params.add(keyCondMap.get("out_order_type_id")) ;
						
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


	public boolean checkCode(Map keyCondMap) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {

			conn = ConnectionContext.getContext().getConnection(dbName);
			stmt = conn.prepareStatement(SQL_COUNT_CODE);
			int index = 1;
			stmt.setString( index++,  keyCondMap.get("code").toString() );
			//informix不支持executeUpdate()返回统计数； 徐雷
			ResultSet rst= stmt.executeQuery();
			 while(rst.next()){
				 int rows = rst.getInt(1);
     			 if(rows>0)
     				return true;
			 }
		}
		catch (SQLException se) {
			Debug.print(SQL_COUNT_CODE,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return false;
	}
	
}
