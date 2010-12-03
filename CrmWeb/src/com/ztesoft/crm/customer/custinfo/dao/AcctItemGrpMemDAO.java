package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AcctItemGrpMemDAO extends AbstractDAO {
    //查询SQL
    private String SQL_SELECT = "select acct_item_group_id,acct_item_type_id,item_source_id,acc_acct_item_group_id,state,state_date from ACCT_ITEM_GROUP_MEMBER where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from acct_item_group_member where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into ACCT_ITEM_GROUP_MEMBER (acct_item_group_id, acct_item_type_id, item_source_id, acc_acct_item_group_id, state, state_date) values (?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update ACCT_ITEM_GROUP_MEMBER set acct_item_group_id=?, acct_item_type_id=?, item_source_id=?, acc_acct_item_group_id=?, state=?, state_date=? where 1=1 ";

    //	普通delete SQL
    private String SQL_DELETE = "delete from ACCT_ITEM_GROUP_MEMBER where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from ACCT_ITEM_GROUP_MEMBER where acct_item_group_id=? and acct_item_type_id=? and item_source_id=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update ACCT_ITEM_GROUP_MEMBER set acct_item_group_id=?, acct_item_type_id=?, item_source_id=?, acc_acct_item_group_id=?, state=?, state_date=? where acct_item_group_id=? and acct_item_type_id=? and item_source_id=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select acct_item_group_id,acct_item_type_id,item_source_id,acc_acct_item_group_id,state,state_date from ACCT_ITEM_GROUP_MEMBER where acct_item_group_id=? and acct_item_type_id=? and item_source_id=? ";

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public AcctItemGrpMemDAO() {
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

        params.add(map.get("acct_item_group_id"));

        params.add(map.get("acct_item_type_id"));

        params.add(map.get("item_source_id"));

        params.add(map.get("acc_acct_item_group_id"));

        params.add(map.get("state"));

        params.add(DAOUtils.parseDateTime(map.get("state_date")));

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

        params.add(vo.get("acct_item_group_id"));

        params.add(vo.get("acct_item_type_id"));

        params.add(vo.get("item_source_id"));

        params.add(vo.get("acc_acct_item_group_id"));

        params.add(vo.get("state"));

        params.add(DAOUtils.parseDateTime(vo.get("state_date")));

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

        params.add(vo.get("acct_item_group_id"));

        params.add(vo.get("acct_item_type_id"));

        params.add(vo.get("item_source_id"));

        params.add(vo.get("acc_acct_item_group_id"));

        params.add(vo.get("state"));

        params.add(DAOUtils.parseDateTime(vo.get("state_date")));

        params.add(keyCondMap.get("acct_item_group_id"));

        params.add(keyCondMap.get("acct_item_type_id"));

        params.add(keyCondMap.get("item_source_id"));

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

        params.add(keyCondMap.get("acct_item_group_id"));

        params.add(keyCondMap.get("acct_item_type_id"));

        params.add(keyCondMap.get("item_source_id"));

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
