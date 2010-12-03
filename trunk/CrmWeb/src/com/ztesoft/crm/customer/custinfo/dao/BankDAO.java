package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BankDAO extends AbstractDAO {
    //��ѯSQL
    private String SQL_SELECT = "select bank_id,bank_name,bank_code,parent_bank_id,region_id,acct_len from BANK where 1=1 ";

    //	ͳ������SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from bank where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into BANK (bank_id, bank_name, bank_code, parent_bank_id, region_id, acct_len) values (?, ?, ?, ?, ?, ?)";

    //	��ͨupdate SQL
    private String SQL_UPDATE = "update BANK set bank_id=?, bank_name=?, bank_code=?, parent_bank_id=?, region_id=?, acct_len=? where 1=1 ";

    //	��ͨdelete SQL
    private String SQL_DELETE = "delete from BANK where 1=1 ";

    //	��������delete SQL
    private String SQL_DELETE_KEY = "delete from BANK where bank_id=?";

    //	��������update SQL
    private String SQL_UPDATE_KEY = "update BANK set bank_id=?, bank_name=?, bank_code=?, parent_bank_id=?, region_id=?, acct_len=? where bank_id=?";

    //	����������ѯSQL
    private String SQL_SELECT_KEY = "select bank_id,bank_name,bank_code,parent_bank_id,region_id,acct_len from BANK where bank_id=? ";

    //	��ǰDAO �������ݿ�name
    private String dbName = JNDINames.CM_DATASOURCE;

    public BankDAO() {
    }

    /**
     * Insert��������
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

        params.add(map.get("bank_id"));

        params.add(map.get("bank_name"));

        params.add(map.get("bank_code"));

        params.add(map.get("parent_bank_id"));

        params.add(map.get("region_id"));

        params.add(map.get("acct_len"));

        return params;
    }

    /**
     * update ��������
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

        params.add(vo.get("bank_id"));

        params.add(vo.get("bank_name"));

        params.add(vo.get("bank_code"));

        params.add(vo.get("parent_bank_id"));

        params.add(vo.get("region_id"));

        params.add(vo.get("acct_len"));

        if ((condParas != null) && !condParas.isEmpty()) {
            for (int i = 0, j = condParas.size(); i < j; i++) {
                params.add(condParas.get(i));
            }
        }

        return params;
    }

    /**
    * �����������²�������
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

        params.add(vo.get("bank_id"));

        params.add(vo.get("bank_name"));

        params.add(vo.get("bank_code"));

        params.add(vo.get("parent_bank_id"));

        params.add(vo.get("region_id"));

        params.add(vo.get("acct_len"));

        params.add(keyCondMap.get("bank_id"));

        return params;
    }

    /**
    * ����������������
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

        params.add(keyCondMap.get("bank_id"));

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
