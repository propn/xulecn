package com.ztesoft.crm.business.common.inst.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ServAddrDAO extends BusiDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,fin_date,end_time,beg_time,serv_id,address_id,detail_address,branch_id from SERV_ADDRESS where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from serv_address where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SERV_ADDRESS (ord_item_id, cust_ord_id, ord_id, action_type, state_date, fin_date, end_time, beg_time, serv_id, address_id, detail_address, branch_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update SERV_ADDRESS set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, fin_date=?, end_time=?, beg_time=?, serv_id=?, address_id=?, detail_address=?, branch_id=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from SERV_ADDRESS where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from SERV_ADDRESS where address_id=? and serv_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update SERV_ADDRESS set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, fin_date=?, end_time=?, beg_time=?, serv_id=?, address_id=?, detail_address=?, branch_id=? where address_id=? and serv_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,fin_date,end_time,beg_time,serv_id,address_id,detail_address,branch_id from SERV_ADDRESS where address_id=? and serv_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public ServAddrDAO() {

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
						
		params.add(DAOUtils.parseDateTime(map.get("fin_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("address_id")) ;
									
		params.add(map.get("detail_address")) ;
									
		params.add(map.get("branch_id")) ;
						
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
						
		
		params.add(DAOUtils.parseDateTime(vo.get("fin_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("address_id")) ;
						
					
		params.add(vo.get("detail_address")) ;
						
					
		params.add(vo.get("branch_id")) ;
						
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
						
		params.add(DAOUtils.parseDateTime(vo.get("fin_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("address_id")) ;
									
		params.add(vo.get("detail_address")) ;
									
		params.add(vo.get("branch_id")) ;
						
							
		params.add(keyCondMap.get("address_id")) ;
									
		params.add(keyCondMap.get("serv_id")) ;
						
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
							
		params.add(keyCondMap.get("address_id")) ;
									
		params.add(keyCondMap.get("serv_id")) ;
						
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
	
//	�����޸ģ����Ż�
	public void batchUpdate(List servstatemaps) throws FrameException {
		
		for (int i = 0; i < servstatemaps.size(); i++) {
            List params = new ArrayList();
            boolean isfirstchange = true;
            StringBuffer sql = new StringBuffer();
            
            sql.append(" update serv_address set ");
            
			Map map = (Map)servstatemaps.get(i);			
			Iterator it=map.keySet().iterator();   
			while(it.hasNext()){
			    String skey = it.next().toString();   
			    String svalue = (String)map.get(skey);   
			    System.out.println(skey+"--"+svalue);   
			    
			    //���Բ����Ǹ������޸�
			    if (   "ord_item_id".equalsIgnoreCase(skey)
			    	 || "cust_ord_id".equalsIgnoreCase(skey)
			    	 || "ord_id".equalsIgnoreCase(skey)
			    	 || "action_type".equalsIgnoreCase(skey)
			    	 || "end_time".equalsIgnoreCase(skey)
			    	 || "beg_time".equalsIgnoreCase(skey)			    	 
			    	 || "serv_id".equalsIgnoreCase(skey)
			    	 || "address_id".equalsIgnoreCase(skey)
			    	 || "detail_address".equalsIgnoreCase(skey)
			    	 || "branch_id".equalsIgnoreCase(skey)) {
                    //���ǵ�һ��set��������Ҫ�Ӷ���
                    if (isfirstchange) {
                        isfirstchange = false;
                    }else{
                        sql.append(" , ");
                    }
			    	
			    	sql.append(skey.toString());
			    	sql.append(" = ? ");
			    	params.add(svalue);
			    }
			    else if (   "state_date".equalsIgnoreCase(skey)
				    	 || "fin_date".equalsIgnoreCase(skey)) {
                    //���ǵ�һ��set��������Ҫ�Ӷ���
                    if (isfirstchange) {
                        isfirstchange = false;
                    }else{
                        sql.append(" , ");
                    }
			    	
			    	sql.append(skey.toString());
			    	sql.append(" = ? ");
			    	
			    	params.add(DAOUtils.parseDateTime(svalue)) ;
			    }				
			} 
			
			//��������
			sql.append(" where address_id=? and serv_id=? ");
			
			params.add(map.get("address_id"));
			params.add(map.get("serv_id"));
			
			update(sql.toString(), params);
		}
	}
	
}
