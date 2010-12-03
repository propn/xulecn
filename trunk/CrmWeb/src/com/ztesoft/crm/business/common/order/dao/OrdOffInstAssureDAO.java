package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdOffInstAssureDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_id,cust_ord_id,ord_item_id,action_type,state_date,end_time,beg_time,assure_id,comp_inst_id,assure_method,assure_info,assure_name,card_type,card_no,tel,mobile_phone,fax,bp,email,addr,post_code,notes from ORD_OFFER_INST_ASSURE where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_offer_inst_assure where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_OFFER_INST_ASSURE (ord_id, cust_ord_id, ord_item_id, action_type, state_date, end_time, beg_time, assure_id, comp_inst_id, assure_method, assure_info, assure_name, card_type, card_no, tel, mobile_phone, fax, bp, email, addr, post_code, notes) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORD_OFFER_INST_ASSURE set ord_id=?, cust_ord_id=?, ord_item_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, assure_id=?, comp_inst_id=?, assure_method=?, assure_info=?, assure_name=?, card_type=?, card_no=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=?, notes=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORD_OFFER_INST_ASSURE where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_OFFER_INST_ASSURE where ord_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ORD_OFFER_INST_ASSURE set ord_id=?, cust_ord_id=?, ord_item_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, assure_id=?, comp_inst_id=?, assure_method=?, assure_info=?, assure_name=?, card_type=?, card_no=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=?, notes=? where ord_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_id,cust_ord_id,ord_item_id,action_type,state_date,end_time,beg_time,assure_id,comp_inst_id,assure_method,assure_info,assure_name,card_type,card_no,tel,mobile_phone,fax,bp,email,addr,post_code,notes from ORD_OFFER_INST_ASSURE where ord_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdOffInstAssureDAO() {

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
							
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ord_item_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("assure_id")) ;
									
		params.add(map.get("comp_inst_id")) ;
									
		params.add(map.get("assure_method")) ;
									
		params.add(map.get("assure_info")) ;
									
		params.add(map.get("assure_name")) ;
									
		params.add(map.get("card_type")) ;
									
		params.add(map.get("card_no")) ;
									
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
				
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ord_item_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("assure_id")) ;
						
					
		params.add(vo.get("comp_inst_id")) ;
						
					
		params.add(vo.get("assure_method")) ;
						
					
		params.add(vo.get("assure_info")) ;
						
					
		params.add(vo.get("assure_name")) ;
						
					
		params.add(vo.get("card_type")) ;
						
					
		params.add(vo.get("card_no")) ;
						
					
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
							
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ord_item_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("assure_id")) ;
									
		params.add(vo.get("comp_inst_id")) ;
									
		params.add(vo.get("assure_method")) ;
									
		params.add(vo.get("assure_info")) ;
									
		params.add(vo.get("assure_name")) ;
									
		params.add(vo.get("card_type")) ;
									
		params.add(vo.get("card_no")) ;
									
		params.add(vo.get("tel")) ;
									
		params.add(vo.get("mobile_phone")) ;
									
		params.add(vo.get("fax")) ;
									
		params.add(vo.get("bp")) ;
									
		params.add(vo.get("email")) ;
									
		params.add(vo.get("addr")) ;
									
		params.add(vo.get("post_code")) ;
									
		params.add(vo.get("notes")) ;
						
							
		params.add(keyCondMap.get("ord_id")) ;
						
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
							
		params.add(keyCondMap.get("ord_id")) ;
						
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
