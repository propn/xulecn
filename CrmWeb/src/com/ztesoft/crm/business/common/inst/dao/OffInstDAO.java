package com.ztesoft.crm.business.common.inst.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.logic.model.OrderItem;
import com.ztesoft.crm.business.common.logic.model.OrderItemDetail;

public class OffInstDAO extends BusiDAO {

	// ��ѯSQL
	private String SQL_SELECT = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,comp_inst_id,product_offer_instance_id,cust_id,product_offer_id,offer_kind,create_date,eff_date,exp_date,state from OFFER_INST where 1=1 ";

	// ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from offer_inst where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into OFFER_INST (ord_item_id, cust_ord_id, ord_id, action_type, state_date, end_time, beg_time, staff_no, site_no, comp_inst_id, product_offer_instance_id, cust_id, product_offer_id, offer_kind, create_date, eff_date, exp_date, state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// ��ͨupdate SQL
	private String SQL_UPDATE = "update OFFER_INST set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, comp_inst_id=?, product_offer_instance_id=?, cust_id=?, product_offer_id=?, offer_kind=?, create_date=?, eff_date=?, exp_date=?, state=? where 1=1 ";

	// ��ͨdelete SQL
	private String SQL_DELETE = "delete from OFFER_INST where 1=1 ";

	// ��������delete SQL
	private String SQL_DELETE_KEY = "delete from OFFER_INST where product_offer_instance_id=?";

	// ��������update SQL
	private String SQL_UPDATE_KEY = "update OFFER_INST set ord_item_id=?, cust_ord_id=?, ord_id=?, action_type=?, state_date=?, end_time=?, beg_time=?, staff_no=?, site_no=?, comp_inst_id=?, product_offer_instance_id=?, cust_id=?, product_offer_id=?, offer_kind=?, create_date=?, eff_date=?, exp_date=?, state=? where product_offer_instance_id=?";

	// ����������ѯSQL
	private String SQL_SELECT_KEY = "select ord_item_id,cust_ord_id,ord_id,action_type,state_date,end_time,beg_time,staff_no,site_no,comp_inst_id,product_offer_instance_id,cust_id,product_offer_id,offer_kind,create_date,eff_date,exp_date,state from OFFER_INST where product_offer_instance_id=? ";

	// ��ǰDAO �������ݿ�name
	private String dbName = JNDINames.ORD_DATASOURCE;

	// ��̬SQL��װ
	private String SQL_UPDATE_BATCH = "";

	public OffInstDAO() {

	}

	/**
	 * Insert��������
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

		params.add(map.get("ord_item_id"));

		params.add(map.get("cust_ord_id"));

		params.add(map.get("ord_id"));

		params.add(map.get("action_type"));

		params.add(DAOUtils.parseDateTime(map.get("state_date")));

		params.add(map.get("end_time"));

		params.add(map.get("beg_time"));

		params.add(map.get("staff_no"));

		params.add(map.get("site_no"));

		params.add(map.get("comp_inst_id"));

		params.add(map.get("product_offer_instance_id"));

		params.add(map.get("cust_id"));

		params.add(map.get("product_offer_id"));

		params.add(map.get("offer_kind"));

		params.add(DAOUtils.parseDateTime(map.get("create_date")));

		params.add(DAOUtils.parseDateTime(map.get("eff_date")));

		params.add(DAOUtils.parseDateTime(map.get("exp_date")));

		params.add(map.get("state"));

		return params;
	}

	/**
	 * update ��������
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

		params.add(vo.get("ord_item_id"));

		params.add(vo.get("cust_ord_id"));

		params.add(vo.get("ord_id"));

		params.add(vo.get("action_type"));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(vo.get("end_time"));

		params.add(vo.get("beg_time"));

		params.add(vo.get("staff_no"));

		params.add(vo.get("site_no"));

		params.add(vo.get("comp_inst_id"));

		params.add(vo.get("product_offer_instance_id"));

		params.add(vo.get("cust_id"));

		params.add(vo.get("product_offer_id"));

		params.add(vo.get("offer_kind"));

		params.add(DAOUtils.parseDateTime(vo.get("create_date")));

		params.add(DAOUtils.parseDateTime(vo.get("eff_date")));

		params.add(DAOUtils.parseDateTime(vo.get("exp_date")));

		params.add(vo.get("state"));

		if (condParas != null && !condParas.isEmpty()) {
			for (int i = 0, j = condParas.size(); i < j; i++) {
				params.add(condParas.get(i));
			}
		}
		return params;

	}

	/**
	 * �����������²�������
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

		params.add(vo.get("ord_item_id"));

		params.add(vo.get("cust_ord_id"));

		params.add(vo.get("ord_id"));

		params.add(vo.get("action_type"));

		params.add(DAOUtils.parseDateTime(vo.get("state_date")));

		params.add(vo.get("end_time"));

		params.add(vo.get("beg_time"));

		params.add(vo.get("staff_no"));

		params.add(vo.get("site_no"));

		params.add(vo.get("comp_inst_id"));

		params.add(vo.get("product_offer_instance_id"));

		params.add(vo.get("cust_id"));

		params.add(vo.get("product_offer_id"));

		params.add(vo.get("offer_kind"));

		params.add(DAOUtils.parseDateTime(vo.get("create_date")));

		params.add(DAOUtils.parseDateTime(vo.get("eff_date")));

		params.add(DAOUtils.parseDateTime(vo.get("exp_date")));

		params.add(vo.get("state"));

		params.add(keyCondMap.get("product_offer_instance_id"));

		return params;
	}

	/**
	 * ����������������
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

		params.add(keyCondMap.get("product_offer_instance_id"));

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
    public void batchUpdate(List offerInstOrderItem) throws FrameException {
        String sTableKey = getTableColString(BusiTables.OFFER_INST.TABLE_FIELDS); //ȡ���ö��ŷָ��ı��ֶ�        
        
        for (int i = 0; i < offerInstOrderItem.size(); i++) {
            List params = new ArrayList();
            boolean isFirstChange = true;
            StringBuffer sql = new StringBuffer();
            
            OrderItem ordItem = (OrderItem)offerInstOrderItem.get(i);
            
            if (    null == ordItem 
                 || null == ordItem.getOrderItemDetails() 
                 || ordItem.getOrderItemDetails().isEmpty()) {
                continue;
            }
            
            List ordItemDetails = ordItem.getOrderItemDetails(); 
            Iterator it = ordItemDetails.iterator(); 
            sql.append(" update offer_inst set ");            
            
            while(it.hasNext()){
                OrderItemDetail orditemdetail = (OrderItemDetail)it.next();
                
                if (null == orditemdetail) {
                    continue;
                } 
                
                String skey = orditemdetail.get(BusiTables.ORD_ASK_LOG.field_name);   
                String svalue = orditemdetail.get(BusiTables.ORD_ASK_LOG.field_value);                
                String tempKey = KeyValues.SPLIT_STRING + skey + KeyValues.SPLIT_STRING;
                
                //�ж��ֶ��Ƿ���OFFER_INST����
                if (sTableKey.indexOf(tempKey) < 0) {
                    continue;
                }
                
                //ʱ���ֶ�Ҫ���⴦��
                if (   "state_date".equalsIgnoreCase(skey)
                    || "create_date".equalsIgnoreCase(skey)
                    || "eff_date".equalsIgnoreCase(skey)
                    || "exp_date".equalsIgnoreCase(skey)) {
                    //���ǵ�һ��set��������Ҫ�Ӷ���
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
            
            //������������
            sql.append(" where product_offer_instance_id=?  ");            
            params.add(ordItem.get(BusiTables.OFFER_INST.product_offer_instance_id));            
            update(sql.toString(), params);
        }
    }

}
