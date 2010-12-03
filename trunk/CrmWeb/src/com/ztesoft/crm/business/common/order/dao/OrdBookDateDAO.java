package com.ztesoft.crm.business.common.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OrdBookDateDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,book_seq,comp_inst_id,book_type,time_name_id,book_date,book_date2,book_dow,book_dow2,book_time,book_time2 from ORD_BOOK_DATE where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_book_date where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_BOOK_DATE (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, book_seq, comp_inst_id, book_type, time_name_id, book_date, book_date2, book_dow, book_dow2, book_time, book_time2) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update ORD_BOOK_DATE set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, book_seq=?, comp_inst_id=?, book_type=?, time_name_id=?, book_date=?, book_date2=?, book_dow=?, book_dow2=?, book_time=?, book_time2=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from ORD_BOOK_DATE where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_BOOK_DATE where ord_item_id=? and book_seq=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update ORD_BOOK_DATE set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, book_seq=?, comp_inst_id=?, book_type=?, time_name_id=?, book_date=?, book_date2=?, book_dow=?, book_dow2=?, book_time=?, book_time2=? where ord_item_id=? and book_seq=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,book_seq,comp_inst_id,book_type,time_name_id,book_date,book_date2,book_dow,book_dow2,book_time,book_time2 from ORD_BOOK_DATE where ord_item_id=? and book_seq=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public OrdBookDateDAO() {

	}
	

	/**
	 * Insert��������
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
							
		params.add(map.get("ord_item_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("book_seq")) ;
									
		params.add(map.get("comp_inst_id")) ;
									
		params.add(map.get("book_type")) ;
									
		params.add(map.get("time_name_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_date2" ))) ;
									
		params.add(map.get("book_dow")) ;
									
		params.add(map.get("book_dow2")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("book_time2" ))) ;
						
		return params ;
	}
	

	/**
	 * update ��������
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
					
		params.add(vo.get("ord_item_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("book_seq")) ;
						
					
		params.add(vo.get("comp_inst_id")) ;
						
					
		params.add(vo.get("book_type")) ;
						
					
		params.add(vo.get("time_name_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_date2" ))) ;
						
					
		params.add(vo.get("book_dow")) ;
						
					
		params.add(vo.get("book_dow2")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("book_time2" ))) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * �����������²�������
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
							
		params.add(vo.get("ord_item_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("book_seq")) ;
									
		params.add(vo.get("comp_inst_id")) ;
									
		params.add(vo.get("book_type")) ;
									
		params.add(vo.get("time_name_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_date2" ))) ;
									
		params.add(vo.get("book_dow")) ;
									
		params.add(vo.get("book_dow2")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("book_time2" ))) ;
						
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("book_seq")) ;
						
		return params ;
	}
		
		/**
	 * ����������������
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("ord_item_id")) ;
									
		params.add(keyCondMap.get("book_seq")) ;
						
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
