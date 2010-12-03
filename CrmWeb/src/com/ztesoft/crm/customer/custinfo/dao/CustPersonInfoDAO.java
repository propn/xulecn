package com.ztesoft.crm.customer.custinfo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.customer.custinfo.common.CustHelper;


public class CustPersonInfoDAO extends AbstractDAO {
    //查询SQL
    private String SQL_SELECT = "select cust_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,name,name_spell,name_en,cust_gender,birth_date,marry_status,cust_religion,cust_nationality,native_place,cust_education_level,cust_occupation,cust_org,income_level,skill,burden_like,consume_character,comm_attention_level,taboo_things,website,notes from CUST_PERSON_INFO where 1=1 ";

    //	统计总数SQL
    private String SQL_SELECT_COUNT = "select count(*) as col_counts from cust_person_info where 1=1 ";

    //	insert SQl
    private String SQL_INSERT = "insert into CUST_PERSON_INFO (cust_id, seq_nbr, cust_order_id, order_id, action_type, record_eff_date, record_exp_date, state_date, name, name_spell, name_en, cust_gender, birth_date, marry_status, cust_religion, cust_nationality, native_place, cust_education_level, cust_occupation, cust_org, income_level, skill, burden_like, consume_character, comm_attention_level, taboo_things, website, notes) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    //	普通update SQL
    private String SQL_UPDATE = "update CUST_PERSON_INFO set cust_id=?, seq_nbr=?, cust_order_id=?, order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, name=?, name_spell=?, name_en=?, cust_gender=?, birth_date=?, marry_status=?, cust_religion=?, cust_nationality=?, native_place=?, cust_education_level=?, cust_occupation=?, cust_org=?, income_level=?, skill=?, burden_like=?, consume_character=?, comm_attention_level=?, taboo_things=?, website=?, notes=? where 1=1 ";

    //	普通delete SQL
    private String SQL_DELETE = "delete from CUST_PERSON_INFO where 1=1 ";

    //	根据主键delete SQL
    private String SQL_DELETE_KEY = "delete from CUST_PERSON_INFO where cust_id=? and seq_nbr=?";

    //	根据主键update SQL
    private String SQL_UPDATE_KEY = "update CUST_PERSON_INFO set record_exp_date=?, state_date=? where cust_id=? and seq_nbr=?";

    //	根据主键查询SQL
    private String SQL_SELECT_KEY = "select cust_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,name,name_spell,name_en,cust_gender,birth_date,marry_status,cust_religion,cust_nationality,native_place,cust_education_level,cust_occupation,cust_org,income_level,skill,burden_like,consume_character,comm_attention_level,taboo_things,website,notes from CUST_PERSON_INFO where cust_id=? and record_exp_date = "+CustHelper.getDefaultExpDate();

    //	当前DAO 所属数据库name
    private String dbName = JNDINames.CM_DATASOURCE;

    public CustPersonInfoDAO() {
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

        params.add(map.get("cust_id"));

        params.add(map.get("seq_nbr"));

        params.add(map.get("cust_order_id"));

        params.add(map.get("order_id"));

        params.add(map.get("action_type"));

        params.add(DAOUtils.parseTimestamp((String)map.get("record_eff_date")));

        params.add(DAOUtils.parseDateTime(map.get("record_exp_date")));

        params.add(DAOUtils.parseTimestamp((String)map.get("state_date")));

        params.add(map.get("name"));

        params.add(map.get("name_spell"));

        params.add(map.get("name_en"));

        params.add(map.get("cust_gender"));

        params.add(DAOUtils.parseDate((String)map.get("birth_date")));

        params.add(map.get("marry_status"));

        params.add(map.get("cust_religion"));

        params.add(map.get("cust_nationality"));

        params.add(map.get("native_place"));

        params.add(map.get("cust_education_level"));

        params.add(map.get("cust_occupation"));

        params.add(map.get("cust_org"));

        params.add(map.get("income_level"));

        params.add(map.get("skill"));

        params.add(map.get("burden_like"));

        params.add(map.get("consume_character"));

        params.add(map.get("comm_attention_level"));

        params.add(map.get("taboo_things"));

        params.add(map.get("website"));

        params.add(map.get("notes"));

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

        params.add(vo.get("cust_id"));

        params.add(vo.get("seq_nbr"));

        params.add(vo.get("cust_order_id"));

        params.add(vo.get("order_id"));

        params.add(vo.get("action_type"));

        params.add(DAOUtils.parseDateTime(vo.get("record_eff_date")));

        params.add(DAOUtils.parseTimestamp((String)vo.get("record_exp_date")));

        params.add(DAOUtils.parseTimestamp((String)vo.get("state_date")));

        params.add(vo.get("name"));

        params.add(vo.get("name_spell"));

        params.add(vo.get("name_en"));

        params.add(vo.get("cust_gender"));

        params.add(DAOUtils.parseDate((String)vo.get("birth_date")));

        params.add(vo.get("marry_status"));

        params.add(vo.get("cust_religion"));

        params.add(vo.get("cust_nationality"));

        params.add(vo.get("native_place"));

        params.add(vo.get("cust_education_level"));

        params.add(vo.get("cust_occupation"));

        params.add(vo.get("cust_org"));

        params.add(vo.get("income_level"));

        params.add(vo.get("skill"));

        params.add(vo.get("burden_like"));

        params.add(vo.get("consume_character"));

        params.add(vo.get("comm_attention_level"));

        params.add(vo.get("taboo_things"));

        params.add(vo.get("website"));

        params.add(vo.get("notes"));

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

//        params.add(vo.get("cust_id"));
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

//        params.add(vo.get("name"));
//
//        params.add(vo.get("name_spell"));
//
//        params.add(vo.get("name_en"));
//
//        params.add(vo.get("cust_gender"));
//
//        params.add(DAOUtils.parseTimestamp((String)vo.get("birth_date")));
//
//        params.add(vo.get("marry_status"));
//
//        params.add(vo.get("cust_religion"));
//
//        params.add(vo.get("cust_nationality"));
//
//        params.add(vo.get("native_place"));
//
//        params.add(vo.get("cust_education_level"));
//
//        params.add(vo.get("cust_occupation"));
//
//        params.add(vo.get("cust_org"));
//
//        params.add(vo.get("income_level"));
//
//        params.add(vo.get("skill"));
//
//        params.add(vo.get("burden_like"));
//
//        params.add(vo.get("consume_character"));
//
//        params.add(vo.get("comm_attention_level"));
//
//        params.add(vo.get("taboo_things"));
//
//        params.add(vo.get("website"));
//
//        params.add(vo.get("notes"));

        params.add(keyCondMap.get("cust_id"));

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

        params.add(keyCondMap.get("cust_id"));

        //params.add(keyCondMap.get("seq_nbr"));

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
