package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IndustryNameKeyDAO extends AbstractDAO {
    //��ѯSQL
    private String SQL_SELECT = "select name_key_id,industry_id,name_key,name_key_desc from INDUSTRY_NAME_KEY where 1=1 ";

    //	ͳ������SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from industry_name_key where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into INDUSTRY_NAME_KEY (name_key_id, industry_id, name_key, name_key_desc) values (?, ?, ?, ?)";

    //	��ͨupdate SQL
    private String SQL_UPDATE = "update INDUSTRY_NAME_KEY set name_key_id=?, industry_id=?, name_key=?, name_key_desc=? where 1=1 ";

    //	��ͨdelete SQL
    private String SQL_DELETE = "delete from INDUSTRY_NAME_KEY where 1=1 ";

    //	��������delete SQL
    private String SQL_DELETE_KEY = "delete from INDUSTRY_NAME_KEY where name_key_id=?";

    //	��������update SQL
    private String SQL_UPDATE_KEY = "update INDUSTRY_NAME_KEY set name_key_id=?, industry_id=?, name_key=?, name_key_desc=? where name_key_id=?";

    //	����������ѯSQL
    private String SQL_SELECT_KEY = "select name_key_id,industry_id,name_key,name_key_desc from INDUSTRY_NAME_KEY where name_key_id=? ";

    //	��ǰDAO �������ݿ�name
    private String dbName = JNDINames.CM_DATASOURCE;

    public IndustryNameKeyDAO() {
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

        params.add(map.get("name_key_id"));

        params.add(map.get("industry_id"));

        params.add(map.get("name_key"));

        params.add(map.get("name_key_desc"));

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

        params.add(vo.get("name_key_id"));

        params.add(vo.get("industry_id"));

        params.add(vo.get("name_key"));

        params.add(vo.get("name_key_desc"));

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

        params.add(vo.get("name_key_id"));

        params.add(vo.get("industry_id"));

        params.add(vo.get("name_key"));

        params.add(vo.get("name_key_desc"));

        params.add(keyCondMap.get("name_key_id"));

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

        params.add(keyCondMap.get("name_key_id"));

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
