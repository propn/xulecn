package com.ztesoft.crm.business.common.order.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.crm.business.common.order.vo.OrdCustOrderVO;

public class OrdAskDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_id,cust_ord_id,ask_id,service_offer_id,ord_type,ord_prop,instance_type,instance_type_id,instance_id,solution_id,notes,state,state_reason_id,ask_date,confirm_date,fin_date,beg_time,staff_no,site_no,agreement_id,last_ord_id from ORD_ASK where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ord_ask where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORD_ASK (ord_id, cust_ord_id, ask_id, service_offer_id, ord_type, ord_prop, instance_type, instance_type_id, instance_id, solution_id, notes, state, state_reason_id, ask_date, confirm_date, fin_date, beg_time, staff_no, site_no, agreement_id, last_ord_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update ORD_ASK set ord_id=?, cust_ord_id=?, ask_id=?, service_offer_id=?, ord_type=?, ord_prop=?, instance_type=?, instance_type_id=?, instance_id=?, solution_id=?, notes=?, state=?, state_reason_id=?, ask_date=?, confirm_date=?, fin_date=?, beg_time=?, staff_no=?, site_no=?, agreement_id=?, last_ord_id=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from ORD_ASK where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from ORD_ASK where ord_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update ORD_ASK set ord_id=?, cust_ord_id=?, ask_id=?, service_offer_id=?, ord_type=?, ord_prop=?, instance_type=?, instance_type_id=?, instance_id=?, solution_id=?, notes=?, state=?, state_reason_id=?, ask_date=?, confirm_date=?, fin_date=?, beg_time=?, staff_no=?, site_no=?, agreement_id=?, last_ord_id=? where ord_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_id,cust_ord_id,ask_id,service_offer_id,ord_type,ord_prop,instance_type,instance_type_id,instance_id,solution_id,notes,state,state_reason_id,ask_date,confirm_date,fin_date,beg_time,staff_no,site_no,agreement_id,last_ord_id from ORD_ASK where ord_id=? ";
	
	//���ݶ����ţ���ѯ�ͻ�������Ϣ��
	private static final String GET_CUST_ORD_BY_ORD_ID = 
				" select a.cust_ord_id,a.cust_so_number,a.cust_id,a.state,a.status_date,a.pre_handle_flag,a.priority,a.business_id," +
				"		a.lan_id,a.staff_no,a.site_no,a.ask_source,a.ask_source_srl " +
				"	from ord_customer_order a, ord_ask b where a.cust_ord_id=b.cust_ord_id and b.ord_id= ? ";

	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.CRM_DATASOURCE;


	public OrdAskDAO() {

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
							
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ask_id")) ;
									
		params.add(map.get("service_offer_id")) ;
									
		params.add(map.get("ord_type")) ;
									
		params.add(map.get("ord_prop")) ;
									
		params.add(map.get("instance_type")) ;
									
		params.add(map.get("instance_type_id")) ;
									
		params.add(map.get("instance_id")) ;
									
		params.add(map.get("solution_id")) ;
									
		params.add(map.get("notes")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("state_reason_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("ask_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("confirm_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("fin_date" ))) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
									
		params.add(map.get("agreement_id")) ;
									
		params.add(map.get("last_ord_id")) ;
						
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
				
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ask_id")) ;
						
					
		params.add(vo.get("service_offer_id")) ;
						
					
		params.add(vo.get("ord_type")) ;
						
					
		params.add(vo.get("ord_prop")) ;
						
					
		params.add(vo.get("instance_type")) ;
						
					
		params.add(vo.get("instance_type_id")) ;
						
					
		params.add(vo.get("instance_id")) ;
						
					
		params.add(vo.get("solution_id")) ;
						
					
		params.add(vo.get("notes")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("state_reason_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("ask_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("confirm_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("fin_date" ))) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
					
		params.add(vo.get("agreement_id")) ;
						
					
		params.add(vo.get("last_ord_id")) ;
						
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
							
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ask_id")) ;
									
		params.add(vo.get("service_offer_id")) ;
									
		params.add(vo.get("ord_type")) ;
									
		params.add(vo.get("ord_prop")) ;
									
		params.add(vo.get("instance_type")) ;
									
		params.add(vo.get("instance_type_id")) ;
									
		params.add(vo.get("instance_id")) ;
									
		params.add(vo.get("solution_id")) ;
									
		params.add(vo.get("notes")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("state_reason_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("ask_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("confirm_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("fin_date" ))) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
									
		params.add(vo.get("agreement_id")) ;
									
		params.add(vo.get("last_ord_id")) ;
						
							
		params.add(keyCondMap.get("ord_id")) ;
						
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
	/**
	 * ����������ѯ�ͻ�������Ϣ��
	 * @param where
	 * @param params 
	 * @return
	 * @throws FrameException 
	 */
	public List getAskListByWhere(String where, ArrayList params) throws FrameException{
		
		return (ArrayList)findByCond(where,params);
	}
	
	/**
	 * ���ݶ�����ʶ��ѯ�ͻ�����
	 * @param ordId
	 * @return
	 * @throws FrameException
	 */
	public OrdCustOrderVO getCustOrderByOrdId(String ordId) throws FrameException{
		ArrayList params = new ArrayList();
		params.add(ordId);
		//��ѯ���ͻ�������Ϣ��
		ArrayList custOrderList = (ArrayList) findBySql(GET_CUST_ORD_BY_ORD_ID,params);
		
		OrdCustOrderVO ordCustOrderVo = new OrdCustOrderVO();
		Iterator custOrderIter = custOrderList.iterator();
		if(custOrderIter.hasNext()){
			Map custOrderMap = (Map) custOrderIter.next();
			//��HashMap ת���� VO 
			ordCustOrderVo.loadFromHashMap((HashMap) custOrderMap);
		}
		return ordCustOrderVo;
	}
}
