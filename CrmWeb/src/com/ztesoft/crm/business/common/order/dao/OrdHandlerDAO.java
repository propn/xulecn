package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdHandlerDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select cust_ord_id,handler_name,handler_card_type,handler_card_no,tel,mobile_phone,fax,bp,email,addr,post_code,notes from ORD_HANDLER where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_handler where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_HANDLER (cust_ord_id, handler_name, handler_card_type, handler_card_no, tel, mobile_phone, fax, bp, email, addr, post_code, notes) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_HANDLER set cust_ord_id=?, handler_name=?, handler_card_type=?, handler_card_no=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=?, notes=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_HANDLER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_HANDLER where cust_ord_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_HANDLER set cust_ord_id=?, handler_name=?, handler_card_type=?, handler_card_no=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=?, notes=? where cust_ord_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select cust_ord_id,handler_name,handler_card_type,handler_card_no,tel,mobile_phone,fax,bp,email,addr,post_code,notes from ORD_HANDLER where cust_ord_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdHandlerDAO() {

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
							
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("handler_name")) ;
									
		params.add(map.get("handler_card_type")) ;
									
		params.add(map.get("handler_card_no")) ;
									
		params.add(map.get("tel")) ;
									
		params.add(map.get("mobile_phone")) ;
									
		params.add(map.get("fax")) ;
									
		params.add(map.get("bp")) ;
									
		params.add(map.get("email")) ;
									
		params.add(map.get("addr")) ;
									
		params.add(map.get("post_code")) ;
									
		params.add(map.get("notes")) ;
						
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
				
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("handler_name")) ;
						
					
		params.add(vo.get("handler_card_type")) ;
						
					
		params.add(vo.get("handler_card_no")) ;
						
					
		params.add(vo.get("tel")) ;
						
					
		params.add(vo.get("mobile_phone")) ;
						
					
		params.add(vo.get("fax")) ;
						
					
		params.add(vo.get("bp")) ;
						
					
		params.add(vo.get("email")) ;
						
					
		params.add(vo.get("addr")) ;
						
					
		params.add(vo.get("post_code")) ;
						
					
		params.add(vo.get("notes")) ;
						
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
							
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("handler_name")) ;
									
		params.add(vo.get("handler_card_type")) ;
									
		params.add(vo.get("handler_card_no")) ;
									
		params.add(vo.get("tel")) ;
									
		params.add(vo.get("mobile_phone")) ;
									
		params.add(vo.get("fax")) ;
									
		params.add(vo.get("bp")) ;
									
		params.add(vo.get("email")) ;
									
		params.add(vo.get("addr")) ;
									
		params.add(vo.get("post_code")) ;
									
		params.add(vo.get("notes")) ;
						
							
		params.add(keyCondMap.get("cust_ord_id")) ;
						
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
							
		params.add(keyCondMap.get("cust_ord_id")) ;
						
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
