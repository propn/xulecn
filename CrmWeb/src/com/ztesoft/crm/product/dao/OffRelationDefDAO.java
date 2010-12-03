package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class OffRelationDefDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select relation_id,product_offer_id,relation_type,state,state_date,seq,party_id,party_role_id,oper_region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,relation_name from OFFER_RELATION_DEF where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from offer_relation_def where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into OFFER_RELATION_DEF (relation_id, product_offer_id, relation_type, state, state_date, seq, party_id, party_role_id, oper_region_id, oper_date, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq, relation_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update OFFER_RELATION_DEF set relation_id=?, product_offer_id=?, relation_type=?, state=?, state_date=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, relation_name=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from OFFER_RELATION_DEF where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from OFFER_RELATION_DEF where relation_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update OFFER_RELATION_DEF set relation_id=?, product_offer_id=?, relation_type=?, state=?, state_date=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, relation_name=? where relation_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select relation_id,product_offer_id,relation_type,state,state_date,seq,party_id,party_role_id,oper_region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,relation_name from OFFER_RELATION_DEF where relation_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public OffRelationDefDAO() {

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
							
		params.add(map.get("relation_id")) ;
									
		params.add(map.get("product_offer_id")) ;
									
		params.add(map.get("relation_type")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("cancel_event_seq")) ;
									
		params.add(map.get("relation_name")) ;
						
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
				
					
		params.add(vo.get("relation_id")) ;
						
					
		params.add(vo.get("product_offer_id")) ;
						
					
		params.add(vo.get("relation_type")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("cancel_event_seq")) ;
						
					
		params.add(vo.get("relation_name")) ;
						
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
							
		params.add(vo.get("relation_id")) ;
									
		params.add(vo.get("product_offer_id")) ;
									
		params.add(vo.get("relation_type")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
									
		params.add(vo.get("relation_name")) ;
						
							
		params.add(keyCondMap.get("relation_id")) ;
						
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
							
		params.add(keyCondMap.get("relation_id")) ;
						
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
