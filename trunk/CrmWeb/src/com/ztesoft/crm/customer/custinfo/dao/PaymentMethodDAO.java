package com.ztesoft.crm.customer.custinfo.dao;

import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PaymentMethodDAO extends AbstractDAO {
    //查询SQL
    private String SQL_SELECT = "select payment_method,payment_method_name,front_display,bank_relative,need_append_info,table_name,payment_code,order_id from PAYMENT_METHOD where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from payment_method where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into PAYMENT_METHOD (payment_method, payment_method_name, front_display, bank_relative, need_append_info, table_name, payment_code, order_id) values (?, ?, ?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update PAYMENT_METHOD set payment_method=?, payment_method_name=?, front_display=?, bank_relative=?, need_append_info=?, table_name=?, payment_code=?, order_id=? where 1=1 ";

    //	普通delete SQL
    private String SQL_DELETE = "delete from PAYMENT_METHOD where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from PAYMENT_METHOD where payment_method=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update PAYMENT_METHOD set payment_method=?, payment_method_name=?, front_display=?, bank_relative=?, need_append_info=?, table_name=?, payment_code=?, order_id=? where payment_method=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select payment_method,payment_method_name,front_display,bank_relative,need_append_info,table_name,payment_code,order_id from PAYMENT_METHOD where payment_method=? ";

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public PaymentMethodDAO() {
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

        params.add(map.get("payment_method"));

        params.add(map.get("payment_method_name"));

        params.add(map.get("front_display"));

        params.add(map.get("bank_relative"));

        params.add(map.get("need_append_info"));

        params.add(map.get("table_name"));

        params.add(map.get("payment_code"));

        params.add(map.get("order_id"));

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

        params.add(vo.get("payment_method"));

        params.add(vo.get("payment_method_name"));

        params.add(vo.get("front_display"));

        params.add(vo.get("bank_relative"));

        params.add(vo.get("need_append_info"));

        params.add(vo.get("table_name"));

        params.add(vo.get("payment_code"));

        params.add(vo.get("order_id"));

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

        params.add(vo.get("payment_method"));

        params.add(vo.get("payment_method_name"));

        params.add(vo.get("front_display"));

        params.add(vo.get("bank_relative"));

        params.add(vo.get("need_append_info"));

        params.add(vo.get("table_name"));

        params.add(vo.get("payment_code"));

        params.add(vo.get("order_id"));

        params.add(keyCondMap.get("payment_method"));

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

        params.add(keyCondMap.get("payment_method"));

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
