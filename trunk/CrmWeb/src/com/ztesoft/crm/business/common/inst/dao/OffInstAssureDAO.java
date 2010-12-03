package com.ztesoft.crm.business.common.inst.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;

public class OffInstAssureDAO extends BusiDAO {

	// 查询SQL
	private String SQL_SELECT = "select ord_id,cust_ord_id,ord_item_id,state_date,action_type,end_time,beg_time,guarantor_id,comp_inst_id,assure_method,assure_info,assure_name,card_type,card_no,tel,mobile_phone,fax,bp,email,addr,post_code,notes from OFFER_INST_ASSURE where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from offer_inst_assure where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into OFFER_INST_ASSURE (ord_id, cust_ord_id, ord_item_id, state_date, action_type, end_time, beg_time, guarantor_id, comp_inst_id, assure_method, assure_info, assure_name, card_type, card_no, tel, mobile_phone, fax, bp, email, addr, post_code, notes) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update OFFER_INST_ASSURE set ord_id=?, cust_ord_id=?, ord_item_id=?, state_date=?, action_type=?, end_time=?, beg_time=?, guarantor_id=?, comp_inst_id=?, assure_method=?, assure_info=?, assure_name=?, card_type=?, card_no=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=?, notes=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from OFFER_INST_ASSURE where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from OFFER_INST_ASSURE where guarantor_id=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update OFFER_INST_ASSURE set ord_id=?, cust_ord_id=?, ord_item_id=?, state_date=?, action_type=?, end_time=?, beg_time=?, guarantor_id=?, comp_inst_id=?, assure_method=?, assure_info=?, assure_name=?, card_type=?, card_no=?, tel=?, mobile_phone=?, fax=?, bp=?, email=?, addr=?, post_code=?, notes=? where guarantor_id=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select ord_id,cust_ord_id,ord_item_id,state_date,action_type,end_time,beg_time,guarantor_id,comp_inst_id,assure_method,assure_info,assure_name,card_type,card_no,tel,mobile_phone,fax,bp,email,addr,post_code,notes from OFFER_INST_ASSURE where guarantor_id=? ";

	// 当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE;

	public OffInstAssureDAO() {

	}

	/**
	 * Insert参数设置
	 * 
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException {
		if (map == null || map.isEmpty())
			return null;
		List params = new ArrayList();

		params.add(map.get("ord_id"));

		params.add(map.get("cust_ord_id"));

		params.add(map.get("ord_item_id"));

		params.add(DAOUtils.parseDateTime(map.get("state_date")));

		params.add(map.get("action_type"));

		params.add(map.get("end_time"));

		params.add(map.get("beg_time"));

		params.add(map.get("guarantor_id"));

		params.add(map.get("comp_inst_id"));

		params.add(map.get("assure_method"));

		params.add(map.get("assure_info"));

		params.add(map.get("assure_name"));

		params.add(map.get("card_type"));

		params.add(map.get("card_no"));

		params.add(map.get("tel"));

		params.add(map.get("mobile_phone"));

		params.add(map.get("fax"));

		params.add(map.get("bp"));

		params.add(map.get("email"));

		params.add(map.get("addr"));

		params.add(map.get("post_code"));

		params.add(map.get("notes"));

		return params;
	}

	/**
	 * update 参数设置
	 * 
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo, List condParas)
			throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(vo.get("ord_id"));

		params.add(vo.get("cust_ord_id"));

		params.add(vo.get("ord_item_id"));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(vo.get("action_type"));

		params.add(vo.get("end_time"));

		params.add(vo.get("beg_time"));

		params.add(vo.get("guarantor_id"));

		params.add(vo.get("comp_inst_id"));

		params.add(vo.get("assure_method"));

		params.add(vo.get("assure_info"));

		params.add(vo.get("assure_name"));

		params.add(vo.get("card_type"));

		params.add(vo.get("card_no"));

		params.add(vo.get("tel"));

		params.add(vo.get("mobile_phone"));

		params.add(vo.get("fax"));

		params.add(vo.get("bp"));

		params.add(vo.get("email"));

		params.add(vo.get("addr"));

		params.add(vo.get("post_code"));

		params.add(vo.get("notes"));

		if (condParas != null && !condParas.isEmpty()) {
			for (int i = 0, j = condParas.size(); i < j; i++) {
				params.add(condParas.get(i));
			}
		}
		return params;

	}

	/**
	 * 根据主键更新参数设置
	 * 
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo, Map keyCondMap)
			throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(vo.get("ord_id"));

		params.add(vo.get("cust_ord_id"));

		params.add(vo.get("ord_item_id"));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(vo.get("action_type"));

		params.add(vo.get("end_time"));

		params.add(vo.get("beg_time"));

		params.add(vo.get("guarantor_id"));

		params.add(vo.get("comp_inst_id"));

		params.add(vo.get("assure_method"));

		params.add(vo.get("assure_info"));

		params.add(vo.get("assure_name"));

		params.add(vo.get("card_type"));

		params.add(vo.get("card_no"));

		params.add(vo.get("tel"));

		params.add(vo.get("mobile_phone"));

		params.add(vo.get("fax"));

		params.add(vo.get("bp"));

		params.add(vo.get("email"));

		params.add(vo.get("addr"));

		params.add(vo.get("post_code"));

		params.add(vo.get("notes"));

		params.add(keyCondMap.get("guarantor_id"));

		return params;
	}

	/**
	 * 主键条件参数设置
	 * 
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateKeyCondMap(Map keyCondMap) throws FrameException {
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(keyCondMap.get("guarantor_id"));

		return params;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getDeleteSQLByKey() throws FrameException {

		return this.SQL_DELETE_KEY;

	}

	public String getUpdateSQLByKey() throws FrameException {

		return this.SQL_UPDATE_KEY;

	}

	public String getSelectSQL() {
		return this.SQL_SELECT;
	}

	public String getSelectCountSQL() {
		return this.SQL_SELECT_COUNT;
	}

	public String getInsertSQL() {
		return this.SQL_INSERT;
	}

	public String getUpdateSQL() {
		return this.SQL_UPDATE;
	}

	public String getDeleteSQL() {
		return this.SQL_DELETE;
	}

	public String getSQLSQLByKey() throws FrameException {

		return this.SQL_SELECT_KEY;

	}

    //list of OrderItem
    public void batchUpdate(List offerInstAssureOrderItem) throws FrameException {
        String sTableKey = getTableColString(BusiTables.OFFER_INST_ASSURE.TABLE_FIELDS); //取得用逗号分隔的表字段        
        
        for (int i = 0; i < offerInstAssureOrderItem.size(); i++) {
            List params = new ArrayList();
            boolean isFirstChange = true;
            StringBuffer sql = new StringBuffer();
            
            OrderItem ordItem = (OrderItem)offerInstAssureOrderItem.get(i);
            
            if (    null == ordItem 
                 || null == ordItem.getOrderItemDetails() 
                 || ordItem.getOrderItemDetails().isEmpty()) {
                continue;
            }
            
            List ordItemDetails = ordItem.getOrderItemDetails(); 
            Iterator it = ordItemDetails.iterator(); 
            sql.append(" update offer_inst_assure set ");            
            
            while(it.hasNext()){
                OrderItemDetail orditemdetail = (OrderItemDetail)it.next();
                
                if (null == orditemdetail) {
                    continue;
                } 
                
                String skey = orditemdetail.get(BusiTables.ORD_ASK_LOG.field_name);   
                String svalue = orditemdetail.get(BusiTables.ORD_ASK_LOG.field_value);                
                String tempKey = KeyValues.SPLIT_STRING + skey + KeyValues.SPLIT_STRING;
                
                //判断字段是否在OFFER_INST_ASSURE表中
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
            sql.append(" where guarantor_id=?  ");            
            params.add(ordItem.get(BusiTables.OFFER_INST_ASSURE.guarantor_id));  
            update(sql.toString(), params);
        }
    }
}
