package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BankBranchDAO extends AbstractDAO {
    //查询SQL
    private String SQL_SELECT = "select bank_branch_id,bank_id,bank_branch_name,bank_acct,bank_acct_name,branch_code,tele_bank_name,can_auto_batch,acct_len from BANK_BRANCH where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from bank_branch where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into BANK_BRANCH (bank_branch_id, bank_id, bank_branch_name, bank_acct, bank_acct_name, branch_code, tele_bank_name, can_auto_batch, acct_len) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update BANK_BRANCH set bank_branch_id=?, bank_id=?, bank_branch_name=?, bank_acct=?, bank_acct_name=?, branch_code=?, tele_bank_name=?, can_auto_batch=?, acct_len=? where 1=1 ";

    //	普通delete SQL
    private String SQL_DELETE = "delete from BANK_BRANCH where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from BANK_BRANCH where bank_branch_id=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update BANK_BRANCH set bank_branch_id=?, bank_id=?, bank_branch_name=?, bank_acct=?, bank_acct_name=?, branch_code=?, tele_bank_name=?, can_auto_batch=?, acct_len=? where bank_branch_id=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select bank_branch_id,bank_id,bank_branch_name,bank_acct,bank_acct_name,branch_code,tele_bank_name,can_auto_batch,acct_len from BANK_BRANCH where bank_branch_id=? ";

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public BankBranchDAO() {
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

        params.add(map.get("bank_branch_id"));

        params.add(map.get("bank_id"));

        params.add(map.get("bank_branch_name"));

        params.add(map.get("bank_acct"));

        params.add(map.get("bank_acct_name"));

        params.add(map.get("branch_code"));

        params.add(map.get("tele_bank_name"));

        params.add(map.get("can_auto_batch"));

        params.add(map.get("acct_len"));

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

        params.add(vo.get("bank_branch_id"));

        params.add(vo.get("bank_id"));

        params.add(vo.get("bank_branch_name"));

        params.add(vo.get("bank_acct"));

        params.add(vo.get("bank_acct_name"));

        params.add(vo.get("branch_code"));

        params.add(vo.get("tele_bank_name"));

        params.add(vo.get("can_auto_batch"));

        params.add(vo.get("acct_len"));

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

        params.add(vo.get("bank_branch_id"));

        params.add(vo.get("bank_id"));

        params.add(vo.get("bank_branch_name"));

        params.add(vo.get("bank_acct"));

        params.add(vo.get("bank_acct_name"));

        params.add(vo.get("branch_code"));

        params.add(vo.get("tele_bank_name"));

        params.add(vo.get("can_auto_batch"));

        params.add(vo.get("acct_len"));

        params.add(keyCondMap.get("bank_branch_id"));

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

        params.add(keyCondMap.get("bank_branch_id"));

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
