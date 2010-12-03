package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustContactInfoDAO extends AbstractDAO {
    //查询SQL
    private String SQL_SELECT = "select contact_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,contact_name,contact_gender,contact_addr,contact_company,mobile_phone,office_phone,home_phone,post_addr,post_code,email_address,fax,cust_contact_info_desc from CUST_CONTACT_INFO where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from cust_contact_info where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into CUST_CONTACT_INFO (contact_id, seq_nbr, cust_order_id, order_id, action_type, record_eff_date, record_exp_date, state_date, cust_id, contact_name, contact_gender, contact_addr, contact_company, mobile_phone, office_phone, home_phone, post_addr, post_code, email_address, fax, cust_contact_info_desc) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update CUST_CONTACT_INFO set record_exp_date=?, state_date=? where contact_id=? and seq_nbr=?";

    //	普通delete SQL
    private String SQL_DELETE = "delete from CUST_CONTACT_INFO where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from CUST_CONTACT_INFO where contact_id=? and seq_nbr=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update CUST_CONTACT_INFO set record_exp_date=?, state_date=? where contact_id=? and seq_nbr=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select contact_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,contact_name,contact_gender,contact_addr,contact_company,mobile_phone,office_phone,home_phone,post_addr,post_code,email_address,fax,cust_contact_info_desc from CUST_CONTACT_INFO where contact_id=? and seq_nbr=? ";

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public CustContactInfoDAO() {
    }

    /**
     * Insert参数设置
     * @param map
     * @return
     * @throws FrameException
     *
     */
    public List translateInsertParams(Map map) throws FrameException {
        if ((map == null) || map.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

        params.add(map.get("contact_id"));

        params.add(map.get("seq_nbr"));

        params.add(map.get("cust_order_id"));

        params.add(map.get("order_id"));

        params.add(map.get("action_type"));

        params.add(DAOUtils.parseTimestamp((String)map.get("record_eff_date")));

        params.add(DAOUtils.parseTimestamp((String)map.get("record_exp_date")));

        params.add(DAOUtils.parseTimestamp((String)map.get("state_date")));

        params.add(map.get("cust_id"));

        params.add(map.get("contact_name"));

        params.add(map.get("contact_gender"));

        params.add(map.get("contact_addr"));

        params.add(map.get("contact_company"));

        params.add(map.get("mobile_phone"));

        params.add(map.get("office_phone"));

        params.add(map.get("home_phone"));

        params.add(map.get("post_addr"));

        params.add(map.get("post_code"));

        params.add(map.get("email_address"));

        params.add(map.get("fax"));

        params.add(map.get("cust_contact_info_desc"));

        return params;
    }

    /**
     * update 参数设置
     * @param vo
     * @param condParas
     * @return
     * @throws FrameException
     */
    public List translateUpdateParams(Map vo, List condParas)
        throws FrameException {
        if ((vo == null) || vo.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

//        params.add(vo.get("contact_id"));
//
//        params.add(vo.get("seq_nbr"));
//
//        params.add(vo.get("cust_order_id"));
//
//        params.add(vo.get("order_id"));
//
//        params.add(vo.get("action_type"));
//
//        params.add(DAOUtils.parseTimestamp((String)vo.get("record_eff_date")));

        params.add(DAOUtils.parseTimestamp((String)vo.get("record_exp_date")));

        params.add(DAOUtils.parseTimestamp((String)vo.get("state_date")));

//        params.add(vo.get("cust_id"));
//
//        params.add(vo.get("contact_name"));
//
//        params.add(vo.get("contact_gender"));
//
//        params.add(vo.get("contact_addr"));
//
//        params.add(vo.get("contact_company"));
//
//        params.add(vo.get("mobile_phone"));
//
//        params.add(vo.get("office_phone"));
//
//        params.add(vo.get("home_phone"));
//
//        params.add(vo.get("post_addr"));
//
//        params.add(vo.get("post_code"));
//
//        params.add(vo.get("email_address"));
//
//        params.add(vo.get("fax"));
//
//        params.add(vo.get("cust_contact_info_desc"));

        if ((condParas != null) && !condParas.isEmpty()) {
            for (int i = 0, j = condParas.size(); i < j; i++) {
                params.add(condParas.get(i));
            }
        }

        return params;
    }

    /**
    * 根据主键更新参数设置
    * @param vo
    * @param keyCondMap
    * @return
    * @throws FrameException
    */
    public List translateUpdateParamsByKey(Map vo, Map keyCondMap)
        throws FrameException {
        if ((vo == null) || vo.isEmpty()) {
            return null;
        }

        if ((keyCondMap == null) || keyCondMap.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

//        params.add(vo.get("contact_id"));
//
//        params.add(vo.get("seq_nbr"));
//
//        params.add(vo.get("cust_order_id"));
//
//        params.add(vo.get("order_id"));
//
//        params.add(vo.get("action_type"));
//
//        params.add(DAOUtils.parseTimestamp((String)vo.get("record_eff_date")));

        params.add(DAOUtils.parseTimestamp((String)vo.get("record_exp_date")));

        params.add(DAOUtils.parseTimestamp((String)vo.get("state_date")));

//        params.add(vo.get("cust_id"));
//
//        params.add(vo.get("contact_name"));
//
//        params.add(vo.get("contact_gender"));
//
//        params.add(vo.get("contact_addr"));
//
//        params.add(vo.get("contact_company"));
//
//        params.add(vo.get("mobile_phone"));
//
//        params.add(vo.get("office_phone"));
//
//        params.add(vo.get("home_phone"));
//
//        params.add(vo.get("post_addr"));
//
//        params.add(vo.get("post_code"));
//
//        params.add(vo.get("email_address"));
//
//        params.add(vo.get("fax"));
//
//        params.add(vo.get("cust_contact_info_desc"));

        params.add(keyCondMap.get("contact_id"));

        params.add(keyCondMap.get("seq_nbr"));

        return params;
    }

    /**
    * 主键条件参数设置
    * @param keyCondMap
    * @return
    * @throws FrameException
    *
    */
    public List translateKeyCondMap(Map keyCondMap) throws FrameException {
        if ((keyCondMap == null) || keyCondMap.isEmpty()) {
            return null;
        }

        List params = new ArrayList();

        params.add(keyCondMap.get("contact_id"));

        params.add(keyCondMap.get("seq_nbr"));

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
}
