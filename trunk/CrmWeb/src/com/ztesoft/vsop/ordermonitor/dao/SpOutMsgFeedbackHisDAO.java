package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames ;

public class SpOutMsgFeedbackHisDAO extends AbstractDAO {
	
	//查询SQL oracle
	private String SQL_SELECT_ORACLE = "select t.id,t.in_time,t.order_id,t.oiut_order_id,t.out_stream_no,t.order_result,t.feeback_result,t.state,t.feeback_desc,t.feeback_time,t.int_sys_id,(select s.name from sp_int_sys s where int_sys_id= s.int_sys_id and rownum = 1) int_sys_name,t.thread_id,writer,t.prod_no from sp_out_msg_feedback_his t where 1=1 ";
	
	//	查询SQL informix
	private String SQL_SELECT_INFORMIX = "select t.id,t.in_time,t.order_id,t.oiut_order_id,t.out_stream_no,t.order_result,t.feeback_result,t.state,t.feeback_desc,t.feeback_time,t.int_sys_id,(select min(s.name) from sp_int_sys s where int_sys_id= s.int_sys_id ) int_sys_name,t.thread_id,writer,t.prod_no from sp_out_msg_feedback_his t where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from sp_out_msg_feedback_his t where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SP_OUT_MSG_FEEDBACK_HIS (id, in_time, order_id, oiut_order_id, out_stream_no, order_result, msg, feeback_result, feeback_msg, state, feeback_desc, feeback_time, int_sys_id, thread_id, writer) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SP_OUT_MSG_FEEDBACK_HIS set id=?, in_time=?, order_id=?, oiut_order_id=?, out_stream_no=?, order_result=?, msg=?, feeback_result=?, feeback_msg=?, state=?, feeback_desc=?, feeback_time=?, int_sys_id=?, thread_id=?, writer=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SP_OUT_MSG_FEEDBACK_HIS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SP_OUT_MSG_FEEDBACK_HIS where id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SP_OUT_MSG_FEEDBACK_HIS set id=?, in_time=?, order_id=?, oiut_order_id=?, out_stream_no=?, order_result=?, msg=?, feeback_result=?, feeback_msg=?, state=?, feeback_desc=?, feeback_time=?, int_sys_id=?, thread_id=?, writer=? where id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY_ORACLE = "select id,in_time,order_id,oiut_order_id,out_stream_no,order_result,msg,feeback_result,feeback_msg,state,feeback_desc,feeback_time,int_sys_id,thread_id,prod_no,(select s.name from sp_int_sys s where t.int_sys_id= s.int_sys_id and rownum = 1) int_sys_name,writer from SP_OUT_MSG_FEEDBACK_HIS where id=? ";
	
	//	根据主键查询SQL
	private String SQL_SELECT_KEY_INFORMIX = "select id,in_time,order_id,oiut_order_id,out_stream_no,order_result,msg,feeback_result,feeback_msg,state,feeback_desc,feeback_time,int_sys_id,thread_id,prod_no,(select min(s.name) from sp_int_sys s where t.int_sys_id= s.int_sys_id ) int_sys_name,writer from SP_OUT_MSG_FEEDBACK_HIS where id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SpOutMsgFeedbackHisDAO() {

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
							
		params.add(map.get("id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("in_time" ))) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("oiut_order_id")) ;
									
		params.add(map.get("out_stream_no")) ;
									
		params.add(map.get("order_result")) ;
									
		params.add(map.get("msg")) ;
									
		params.add(map.get("feeback_result")) ;
									
		params.add(map.get("feeback_msg")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("feeback_desc")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("feeback_time" ))) ;
									
		params.add(map.get("int_sys_id")) ;
									
		params.add(map.get("thread_id")) ;
									
		params.add(map.get("writer")) ;
						
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
				
					
		params.add(vo.get("id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("in_time" ))) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("oiut_order_id")) ;
						
					
		params.add(vo.get("out_stream_no")) ;
						
					
		params.add(vo.get("order_result")) ;
						
					
		params.add(vo.get("msg")) ;
						
					
		params.add(vo.get("feeback_result")) ;
						
					
		params.add(vo.get("feeback_msg")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("feeback_desc")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("feeback_time" ))) ;
						
					
		params.add(vo.get("int_sys_id")) ;
						
					
		params.add(vo.get("thread_id")) ;
						
					
		params.add(vo.get("writer")) ;
						
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
							
		params.add(vo.get("id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("in_time" ))) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("oiut_order_id")) ;
									
		params.add(vo.get("out_stream_no")) ;
									
		params.add(vo.get("order_result")) ;
									
		params.add(vo.get("msg")) ;
									
		params.add(vo.get("feeback_result")) ;
									
		params.add(vo.get("feeback_msg")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("feeback_desc")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("feeback_time" ))) ;
									
		params.add(vo.get("int_sys_id")) ;
									
		params.add(vo.get("thread_id")) ;
									
		params.add(vo.get("writer")) ;
						
							
		params.add(keyCondMap.get("id")) ;
						
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
							
		params.add(keyCondMap.get("id")) ;
						
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
		//根据数据库类型返回 默认oracle数据库
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_INFORMIX;
		}
		if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_ORACLE ;
		}
		return this.SQL_SELECT_ORACLE ;

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
		//根据数据库类型返回 默认oracle数据库
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_KEY_INFORMIX;
		}
		if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_KEY_ORACLE ;
		}
		return this.SQL_SELECT_KEY_ORACLE ;
				
	}
	
}
