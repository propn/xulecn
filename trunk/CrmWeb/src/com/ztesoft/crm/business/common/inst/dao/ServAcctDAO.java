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

public class ServAcctDAO extends BusiDAO {
	
	//查询SQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_id,acct_item_group_id,acct_id,invoice_require_id,bill_require_id,payment_rule_id,self_flag from SERV_ACCT where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from serv_acct where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SERV_ACCT (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, staff_no, site_no, serv_id, acct_item_group_id, acct_id, invoice_require_id, bill_require_id, payment_rule_id, self_flag) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SERV_ACCT set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_id=?, acct_item_group_id=?, acct_id=?, invoice_require_id=?, bill_require_id=?, payment_rule_id=?, self_flag=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SERV_ACCT where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SERV_ACCT where acct_item_group_id=? and serv_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SERV_ACCT set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, serv_id=?, acct_item_group_id=?, acct_id=?, invoice_require_id=?, bill_require_id=?, payment_rule_id=?, self_flag=? where acct_item_group_id=? and serv_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,serv_id,acct_item_group_id,acct_id,invoice_require_id,bill_require_id,payment_rule_id,self_flag from SERV_ACCT where acct_item_group_id=? and serv_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;


	public ServAcctDAO() {

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
							
		params.add(map.get("ord_item_id")) ;
									
		params.add(map.get("cust_ord_id")) ;
									
		params.add(map.get("ord_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;
									
		params.add(map.get("end_time")) ;
									
		params.add(map.get("beg_time")) ;
									
		params.add(map.get("staff_no")) ;
									
		params.add(map.get("site_no")) ;
									
		params.add(map.get("serv_id")) ;
									
		params.add(map.get("acct_item_group_id")) ;
									
		params.add(map.get("acct_id")) ;
									
		params.add(map.get("invoice_require_id")) ;
									
		params.add(map.get("bill_require_id")) ;
									
		params.add(map.get("payment_rule_id")) ;
									
		params.add(map.get("self_flag")) ;
						
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
				
					
		params.add(vo.get("ord_item_id")) ;
						
					
		params.add(vo.get("cust_ord_id")) ;
						
					
		params.add(vo.get("ord_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
					
		params.add(vo.get("end_time")) ;
						
					
		params.add(vo.get("beg_time")) ;
						
					
		params.add(vo.get("staff_no")) ;
						
					
		params.add(vo.get("site_no")) ;
						
					
		params.add(vo.get("serv_id")) ;
						
					
		params.add(vo.get("acct_item_group_id")) ;
						
					
		params.add(vo.get("acct_id")) ;
						
					
		params.add(vo.get("invoice_require_id")) ;
						
					
		params.add(vo.get("bill_require_id")) ;
						
					
		params.add(vo.get("payment_rule_id")) ;
						
					
		params.add(vo.get("self_flag")) ;
						
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
							
		params.add(vo.get("ord_item_id")) ;
									
		params.add(vo.get("cust_ord_id")) ;
									
		params.add(vo.get("ord_id")) ;
									
		params.add(vo.get("action_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
									
		params.add(vo.get("end_time")) ;
									
		params.add(vo.get("beg_time")) ;
									
		params.add(vo.get("staff_no")) ;
									
		params.add(vo.get("site_no")) ;
									
		params.add(vo.get("serv_id")) ;
									
		params.add(vo.get("acct_item_group_id")) ;
									
		params.add(vo.get("acct_id")) ;
									
		params.add(vo.get("invoice_require_id")) ;
									
		params.add(vo.get("bill_require_id")) ;
									
		params.add(vo.get("payment_rule_id")) ;
									
		params.add(vo.get("self_flag")) ;
						
							
		params.add(keyCondMap.get("acct_item_group_id")) ;
									
		params.add(keyCondMap.get("serv_id")) ;
						
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
							
		params.add(keyCondMap.get("acct_item_group_id")) ;
									
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
    
    //批量修改，待优化
    //list of OrderItem
    public void batchUpdate(List servAcctItem) throws FrameException {
        String sTableKey = getTableColString(BusiTables.SERV_ACCT.TABLE_FIELDS); //取得用逗号分隔的表字段        
        
        for (int i = 0; i < servAcctItem.size(); i++) {
            List params = new ArrayList();
            boolean isFirstChange = true;
            StringBuffer sql = new StringBuffer();
            
            OrderItem ordItem = (OrderItem)servAcctItem.get(i);
            
            if (    null == ordItem 
                 || null == ordItem.getOrderItemDetails() 
                 || ordItem.getOrderItemDetails().isEmpty()) {
                continue;
            }
            
            List servItemDetails = ordItem.getOrderItemDetails(); 
            Iterator it = servItemDetails.iterator(); 
            sql.append(" update serv_acct set ");            
            
            while(it.hasNext()){
                OrderItemDetail orditemdetail = (OrderItemDetail)it.next();
                
                if (null == orditemdetail) {
                    continue;
                } 
                
                String skey = orditemdetail.get(BusiTables.ORD_ASK_LOG.field_name);   
                String svalue = orditemdetail.get(BusiTables.ORD_ASK_LOG.field_value);
                
                String tempKey = KeyValues.SPLIT_STRING + skey + KeyValues.SPLIT_STRING;
                
                //判断字段是否在SERV_ACCT表中
                if (sTableKey.indexOf(tempKey) < 0) {
                    continue;
                }
                
                //时间字段要特殊处理
                if ("state_date".equalsIgnoreCase(skey)) {
                    //不是第一个set变量，需要加逗号
                    if (isFirstChange) {
                        isFirstChange = false;
                    }else{
                        sql.append(" , ");
                    }
                    
                    sql.append(skey);
                    sql.append(" = ? ");
                    
                    params.add(DAOUtils.parseDateTime(svalue)) ;
                    
                } 
                else {
                    //不是第一个set变量，需要加逗号
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
            
            //加上主键条件
            sql.append(" where acct_item_group_id=? and serv_id=?  ");
            
            params.add(ordItem.get(BusiTables.SERV_ACCT.acct_item_group_id));
            params.add(ordItem.get(BusiTables.SERV_ACCT.serv_id));
            
            update(sql.toString(), params);
        }
    }
	
}
