package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustRelationDAO extends AbstractDAO {
    //查询SQL
    //private String SQL_SELECT = "select cust_relation_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,party_id,party_role_type,relation_type,relation_role from CUST_RELATION where 1=1 ";
    private String SQL_SELECT = "select cust_relation_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,party_id,party_role_type,relation_type,relation_role from CUST_RELATION where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from cust_relation where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into CUST_RELATION (cust_relation_id, seq_nbr, cust_order_id, order_id, action_type, record_eff_date, record_exp_date, state_date, cust_id, party_id, party_role_type, relation_type, relation_role) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update CUST_RELATION set cust_relation_id=?, seq_nbr=?, cust_order_id=?, order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, cust_id=?, party_id=?, party_role_type=?, relation_type=?, relation_role=? where 1=1 ";

    //	普通delete SQL
    private String SQL_DELETE = "delete from CUST_RELATION where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from CUST_RELATION where cust_relation_id=? and seq_nbr=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update CUST_RELATION set cust_relation_id=?, seq_nbr=?, cust_order_id=?, order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, cust_id=?, party_id=?, party_role_type=?, relation_type=?, relation_role=? where cust_relation_id=? and seq_nbr=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select cust_relation_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_id,party_id,party_role_type,relation_type,relation_role from CUST_RELATION where cust_relation_id=? and seq_nbr=? ";

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public CustRelationDAO() {
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

        params.add(map.get("cust_relation_id"));

        params.add(map.get("seq_nbr"));

        params.add(map.get("cust_order_id"));

        params.add(map.get("order_id"));

        params.add(map.get("action_type"));

        params.add(DAOUtils.parseDateTime(map.get("record_eff_date")));

        params.add(DAOUtils.parseDateTime(map.get("record_exp_date")));

        params.add(DAOUtils.parseDateTime(map.get("state_date")));

        params.add(map.get("cust_id"));

        params.add(map.get("party_id"));

        params.add(map.get("party_role_type"));

        params.add(map.get("relation_type"));

        params.add(map.get("relation_role"));

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

        params.add(vo.get("cust_relation_id"));

        params.add(vo.get("seq_nbr"));

        params.add(vo.get("cust_order_id"));

        params.add(vo.get("order_id"));

        params.add(vo.get("action_type"));

        params.add(DAOUtils.parseDateTime(vo.get("record_eff_date")));

        params.add(DAOUtils.parseDateTime(vo.get("record_exp_date")));

        params.add(DAOUtils.parseDateTime(vo.get("state_date")));

        params.add(vo.get("cust_id"));

        params.add(vo.get("party_id"));

        params.add(vo.get("party_role_type"));

        params.add(vo.get("relation_type"));

        params.add(vo.get("relation_role"));

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

        params.add(vo.get("cust_relation_id"));

        params.add(vo.get("seq_nbr"));

        params.add(vo.get("cust_order_id"));

        params.add(vo.get("order_id"));

        params.add(vo.get("action_type"));

        params.add(DAOUtils.parseDateTime(vo.get("record_eff_date")));

        params.add(DAOUtils.parseDateTime(vo.get("record_exp_date")));

        params.add(DAOUtils.parseDateTime(vo.get("state_date")));

        params.add(vo.get("cust_id"));

        params.add(vo.get("party_id"));

        params.add(vo.get("party_role_type"));

        params.add(vo.get("relation_type"));

        params.add(vo.get("relation_role"));

        params.add(keyCondMap.get("cust_relation_id"));

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

        params.add(keyCondMap.get("cust_relation_id"));

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
