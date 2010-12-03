package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IndustryDAO extends AbstractDAO {
    //��ѯSQL
    private String SQL_SELECT = "select industry_id,industry_name,industry_code,sup_industry_id,type_flag,layer_flag,notes from INDUSTRY where 1=1 ";

    //	ͳ������SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from industry where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into INDUSTRY (industry_id, industry_name, industry_code, sup_industry_id, type_flag, layer_flag, notes) values (?, ?, ?, ?, ?, ?, ?)";

    //	��ͨupdate SQL
    private String SQL_UPDATE = "update INDUSTRY set industry_id=?, industry_name=?, industry_code=?, sup_industry_id=?, type_flag=?, layer_flag=?, notes=? where 1=1 ";

    //	��ͨdelete SQL
    private String SQL_DELETE = "delete from INDUSTRY where 1=1 ";

    //	��������delete SQL
    private String SQL_DELETE_KEY = "delete from INDUSTRY where industry_id=?";

    //	��������update SQL
    private String SQL_UPDATE_KEY = "update INDUSTRY set industry_id=?, industry_name=?, industry_code=?, sup_industry_id=?, type_flag=?, layer_flag=?, notes=? where industry_id=?";

    //	����������ѯSQL
    private String SQL_SELECT_KEY = "select industry_id,industry_name,industry_code,sup_industry_id,type_flag,layer_flag,notes from INDUSTRY where industry_id=? ";

    //	��ǰDAO �������ݿ�name
    private String dbName = JNDINames.CM_DATASOURCE;

    public IndustryDAO() {
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

        params.add(map.get("industry_id"));

        params.add(map.get("industry_name"));

        params.add(map.get("industry_code"));

        params.add(map.get("sup_industry_id"));

        params.add(map.get("type_flag"));

        params.add(map.get("layer_flag"));

        params.add(map.get("notes"));

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

        params.add(vo.get("industry_id"));

        params.add(vo.get("industry_name"));

        params.add(vo.get("industry_code"));

        params.add(vo.get("sup_industry_id"));

        params.add(vo.get("type_flag"));

        params.add(vo.get("layer_flag"));

        params.add(vo.get("notes"));

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

        params.add(vo.get("industry_id"));

        params.add(vo.get("industry_name"));

        params.add(vo.get("industry_code"));

        params.add(vo.get("sup_industry_id"));

        params.add(vo.get("type_flag"));

        params.add(vo.get("layer_flag"));

        params.add(vo.get("notes"));

        params.add(keyCondMap.get("industry_id"));

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

        params.add(keyCondMap.get("industry_id"));

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
