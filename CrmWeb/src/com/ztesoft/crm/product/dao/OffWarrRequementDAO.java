package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OffWarrRequementDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select offer_id,warr_content,warr_mode,warr_value,restrict_services,warr_len,unite_acct_request,max_warr_num,max_unite_acct_num,offer_warr_requement_id,must_flag,conflicted_services,control_type,control_value,operation_flag,hint_message,warr_calc_mode,inc_warr_rule from OFFER_WARR_REQUEMENT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from offer_warr_requement where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into OFFER_WARR_REQUEMENT (offer_id, warr_content, warr_mode, warr_value, restrict_services, warr_len, unite_acct_request, max_warr_num, max_unite_acct_num, offer_warr_requement_id, must_flag, conflicted_services, control_type, control_value, operation_flag, hint_message, warr_calc_mode, inc_warr_rule) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update OFFER_WARR_REQUEMENT set offer_id=?, warr_content=?, warr_mode=?, warr_value=?, restrict_services=?, warr_len=?, unite_acct_request=?, max_warr_num=?, max_unite_acct_num=?, offer_warr_requement_id=?, must_flag=?, conflicted_services=?, control_type=?, control_value=?, operation_flag=?, hint_message=?, warr_calc_mode=?, inc_warr_rule=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from OFFER_WARR_REQUEMENT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from OFFER_WARR_REQUEMENT where offer_warr_requement_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update OFFER_WARR_REQUEMENT set offer_id=?, warr_content=?, warr_mode=?, warr_value=?, restrict_services=?, warr_len=?, unite_acct_request=?, max_warr_num=?, max_unite_acct_num=?, offer_warr_requement_id=?, must_flag=?, conflicted_services=?, control_type=?, control_value=?, operation_flag=?, hint_message=?, warr_calc_mode=?, inc_warr_rule=? where offer_warr_requement_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select offer_id,warr_content,warr_mode,warr_value,restrict_services,warr_len,unite_acct_request,max_warr_num,max_unite_acct_num,offer_warr_requement_id,must_flag,conflicted_services,control_type,control_value,operation_flag,hint_message,warr_calc_mode,inc_warr_rule from OFFER_WARR_REQUEMENT where offer_warr_requement_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public OffWarrRequementDAO() {

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
							
		params.add(map.get("offer_id")) ;
									
		params.add(map.get("warr_content")) ;
									
		params.add(map.get("warr_mode")) ;
									
		params.add(map.get("warr_value")) ;
									
		params.add(map.get("restrict_services")) ;
									
		params.add(map.get("warr_len")) ;
									
		params.add(map.get("unite_acct_request")) ;
									
		params.add(map.get("max_warr_num")) ;
									
		params.add(map.get("max_unite_acct_num")) ;
									
		params.add(map.get("offer_warr_requement_id")) ;
									
		params.add(map.get("must_flag")) ;
									
		params.add(map.get("conflicted_services")) ;
									
		params.add(map.get("control_type")) ;
									
		params.add(map.get("control_value")) ;
									
		params.add(map.get("operation_flag")) ;
									
		params.add(map.get("hint_message")) ;
									
		params.add(map.get("warr_calc_mode")) ;
									
		params.add(map.get("inc_warr_rule")) ;
						
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
				
					
		params.add(vo.get("offer_id")) ;
						
					
		params.add(vo.get("warr_content")) ;
						
					
		params.add(vo.get("warr_mode")) ;
						
					
		params.add(vo.get("warr_value")) ;
						
					
		params.add(vo.get("restrict_services")) ;
						
					
		params.add(vo.get("warr_len")) ;
						
					
		params.add(vo.get("unite_acct_request")) ;
						
					
		params.add(vo.get("max_warr_num")) ;
						
					
		params.add(vo.get("max_unite_acct_num")) ;
						
					
		params.add(vo.get("offer_warr_requement_id")) ;
						
					
		params.add(vo.get("must_flag")) ;
						
					
		params.add(vo.get("conflicted_services")) ;
						
					
		params.add(vo.get("control_type")) ;
						
					
		params.add(vo.get("control_value")) ;
						
					
		params.add(vo.get("operation_flag")) ;
						
					
		params.add(vo.get("hint_message")) ;
						
					
		params.add(vo.get("warr_calc_mode")) ;
						
					
		params.add(vo.get("inc_warr_rule")) ;
						
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
							
		params.add(vo.get("offer_id")) ;
									
		params.add(vo.get("warr_content")) ;
									
		params.add(vo.get("warr_mode")) ;
									
		params.add(vo.get("warr_value")) ;
									
		params.add(vo.get("restrict_services")) ;
									
		params.add(vo.get("warr_len")) ;
									
		params.add(vo.get("unite_acct_request")) ;
									
		params.add(vo.get("max_warr_num")) ;
									
		params.add(vo.get("max_unite_acct_num")) ;
									
		params.add(vo.get("offer_warr_requement_id")) ;
									
		params.add(vo.get("must_flag")) ;
									
		params.add(vo.get("conflicted_services")) ;
									
		params.add(vo.get("control_type")) ;
									
		params.add(vo.get("control_value")) ;
									
		params.add(vo.get("operation_flag")) ;
									
		params.add(vo.get("hint_message")) ;
									
		params.add(vo.get("warr_calc_mode")) ;
									
		params.add(vo.get("inc_warr_rule")) ;
						
							
		params.add(keyCondMap.get("offer_warr_requement_id")) ;
						
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
							
		params.add(keyCondMap.get("offer_warr_requement_id")) ;
						
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
