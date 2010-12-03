package com.ztesoft.vsop.paras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.common.util.tracer.Debug;

public class NeCommandParaDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select command_id,p.command_catalog_id,tran_rule_id,p.name,para_code,get_method,para_id,para_type,cmd_regexp,default_value,node_path,node_attr,is_key,c.name catalogname from NE_COMMAND_PARA p left join NE_COMMAND_CATALOG c on p.command_catalog_id = c.command_catalog_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_command_para p where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_COMMAND_PARA (command_id, command_catalog_id, tran_rule_id, name, para_code, get_method, para_id, para_type, cmd_regexp, default_value, node_path, node_attr, is_key) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update NE_COMMAND_PARA set command_id=?, command_catalog_id=?, tran_rule_id=?, name=?, para_code=?, get_method=?, para_id=?, para_type=?, cmd_regexp=?, default_value=?, node_path=?, node_attr=?, is_key=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from NE_COMMAND_PARA where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from NE_COMMAND_PARA where command_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update NE_COMMAND_PARA set command_id=?, command_catalog_id=?, tran_rule_id=?, name=?, para_code=?, get_method=?, para_id=?, para_type=?, cmd_regexp=?, default_value=?, node_path=?, node_attr=?, is_key=? where command_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select s.command_id,s.command_catalog_id,s.tran_rule_id,s.name,s.para_code,s.get_method,s.para_id,s.para_type,s.cmd_regexp,s.default_value,s.node_path,s.node_attr,s.is_key, r.name rulename,i.name paraname" +
			" from NE_COMMAND_PARA s left join NE_TRAN_RULE r on s.tran_rule_id =r.tran_rule_id  left join sp_para_info i on s.para_id = i.para_id " +
			"where command_id=? ";
	
	//删除目标数据项时检查是否被数据项记录引用
	private String SQL_VALIDATE_PARA_RECORD = "select t.command_id,t.record_id,p.record_name record_name from sp_para_record_list t,SP_PARA_RECORD p where t.record_id = p.record_id and t.command_id = ?";
	
	//删除目标数据项时检查是否被工单类型引用
	private String SQL_VALIDATE_ORDER_TYPE = "select t.out_order_type_id,t.command_id,p.order_type_name order_type_name from sp_order_type_commands t,sp_out_order_type p where t.out_order_type_id = p.out_order_type_id and t.command_id = ?";
	
	//删除目标数据项时检查是否被指令模板引用
	private String SQL_VALIDATE_CMD_TEMPLATE="select tp.name as template_name,p.name,t.command_id from NE_CMD_TEMPLATE_PARA t,Ne_Command_Para p,NE_COMMAND_TEMPLATE tp where t.command_id = p.command_id and t.template_id=tp.template_id and p.command_id=?";
	
	//验证编码的唯一性
	private String SQL_VALIDATE_PARA_CODE = "select t.command_catalog_id,t.para_code,n.command_catalog_id from ne_command_para t,ne_command_catalog n where t.command_catalog_id = n.command_catalog_id and t.command_catalog_id = ? and t.para_code = ?";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeCommandParaDAO() {

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
		params.add(seqDao.getNextSequence("Ne_Command_Para_Seq")) ;
									
		params.add(map.get("command_catalog_id")) ;
									
		params.add(map.get("tran_rule_id")) ;
									
		params.add(map.get("name")) ;
									
		params.add(map.get("para_code")) ;
									
		params.add(map.get("get_method")) ;
									
		params.add(map.get("para_id")) ;
									
		params.add(map.get("para_type")) ;
									
		params.add(map.get("cmd_regexp")) ;
									
		params.add(map.get("default_value")) ;
									
		params.add(map.get("node_path")) ;
									
		params.add(map.get("node_attr")) ;
									
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
				
					
		params.add(vo.get("command_id")) ;
						
					
		params.add(vo.get("command_catalog_id")) ;
						
					
		params.add(vo.get("tran_rule_id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("para_code")) ;
						
					
		params.add(vo.get("get_method")) ;
						
					
		params.add(vo.get("para_id")) ;
						
					
		params.add(vo.get("para_type")) ;
						
					
		params.add(vo.get("cmd_regexp")) ;
						
					
		params.add(vo.get("default_value")) ;
						
					
		params.add(vo.get("node_path")) ;
						
					
		params.add(vo.get("node_attr")) ;
						
					
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
							
		params.add(vo.get("command_id")) ;
									
		params.add(vo.get("command_catalog_id")) ;
									
		params.add(vo.get("tran_rule_id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("para_code")) ;
									
		params.add(vo.get("get_method")) ;
									
		params.add(vo.get("para_id")) ;
									
		params.add(vo.get("para_type")) ;
									
		params.add(vo.get("cmd_regexp")) ;
									
		params.add(vo.get("default_value")) ;
									
		params.add(vo.get("node_path")) ;
									
		params.add(vo.get("node_attr")) ;
									
		params.add(vo.get("is_key")) ;
						
							
		params.add(keyCondMap.get("command_id")) ;
						
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
							
		params.add(keyCondMap.get("command_id")) ;
						
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
	
	public String getSqlForValidateParaRecord() {
		return this.SQL_VALIDATE_PARA_RECORD;
	}
	
	public String getSqlForValidateOrderType() {
		return this.SQL_VALIDATE_ORDER_TYPE;
	}
	
	public String getSqlForValidateParaCode() {
		return this.SQL_VALIDATE_PARA_CODE;
	}

	public String getSQL_VALIDATE_CMD_TEMPLATE() {
		return SQL_VALIDATE_CMD_TEMPLATE;
	}


	public void setSQL_VALIDATE_CMD_TEMPLATE(String sql_validate_cmd_template) {
		SQL_VALIDATE_CMD_TEMPLATE = sql_validate_cmd_template;
	}
}
