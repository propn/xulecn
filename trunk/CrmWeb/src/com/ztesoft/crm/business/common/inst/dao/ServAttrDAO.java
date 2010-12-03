package com.ztesoft.crm.business.common.inst.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;

public class ServAttrDAO extends BusiDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,serv_id,attr_id,field_name,product_id,attr_val from SERV_ATTR where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from serv_attr where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SERV_ATTR (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, serv_id, attr_id, field_name, product_id, attr_val) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update SERV_ATTR set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, serv_id=?, attr_id=?, field_name=?, product_id=?, attr_val=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from SERV_ATTR where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from SERV_ATTR where attr_id=? and serv_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update SERV_ATTR set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, serv_id=?, attr_id=?, field_name=?, product_id=?, attr_val=? where attr_id=? and serv_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,serv_id,attr_id,field_name,product_id,attr_val from SERV_ATTR where attr_id=? and serv_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public ServAttrDAO() {

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
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("attr_id")) ;
									
		params.add(map.get("field_name")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("attr_val")) ;
						
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
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("attr_id")) ;
						
					
		params.add(vo.get("field_name")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("attr_val")) ;
						
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
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("attr_id")) ;
									
		params.add(vo.get("field_name")) ;
									
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("attr_val")) ;
						
							
		params.add(keyCondMap.get("attr_id")) ;
									
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
							
		params.add(keyCondMap.get("attr_id")) ;
									
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
	
    //�����޸ģ����Ż�
	public void batchUpdate(List servAttrItem) throws FrameException {        
        String sTableKey = getTableColString(BusiTables.SERV_ATTR.TABLE_FIELDS); //ȡ���ö��ŷָ��ı��ֶ�
        
		for (int i = 0; i < servAttrItem.size(); i++) {
			List params = new ArrayList();
			boolean isFirstChange = true;
			StringBuffer sql = new StringBuffer();			
			sql.append(" update serv_attr set ");
			
            OrderItem ordItem = (OrderItem)servAttrItem.get(i);
            
            if (  null == ordItem 
                || null == ordItem.getOrderItemDetails() 
                || ordItem.getOrderItemDetails().isEmpty()) {
                   continue;
               }
            
            List itemDetails = ordItem.getOrderItemDetails();
			Iterator it = itemDetails.iterator();
			while(it.hasNext()){
                OrderItemDetail ordItemDetail = (OrderItemDetail)it.next();
                
                if (null == ordItemDetail) {
                    continue;
                } 
                
                String skey = ordItemDetail.get(BusiTables.ORD_ASK_LOG.field_name);   
                String svalue = ordItemDetail.get(BusiTables.ORD_ASK_LOG.field_value);
                
                String tempKey = KeyValues.SPLIT_STRING + skey + KeyValues.SPLIT_STRING;
                
                //�ж��ֶ��Ƿ���SERV_ATTR����
                if (sTableKey.indexOf(tempKey) < 0) {
                    continue;
                }
                
                if ("state_date".equalsIgnoreCase(skey)) {
                    //���ǵ�һ��set��������Ҫ�Ӷ���
                    if (isFirstChange) {
                        isFirstChange = false;
                    }else{
                        sql.append(" , ");
                    }
                    
                    sql.append(skey.toString());
                    sql.append(" = ? ");
                    
                    params.add(DAOUtils.parseDateTime(svalue)) ;
                }
                else {
                    //���ǵ�һ��set��������Ҫ�Ӷ���
                    if (isFirstChange) {
                        isFirstChange = false;
                    }else{
                        sql.append(" , ");
                    }
                    
                    sql.append(skey);
                    sql.append(" = ? ");
                    
                    params.add(svalue);
                }
            }
            
			//��������
			sql.append(" where attr_id=? and serv_id=? ");
			
			params.add(ordItem.get(BusiTables.SERV_ATTR.attr_id));
			params.add(ordItem.get(BusiTables.SERV_ATTR.serv_id));
			
			update(sql.toString(), params);
		}
	}
	
}
