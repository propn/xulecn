package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.common.CustHelper;
import com.ztesoft.crm.customer.custinfo.dao.CustRelationDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustRelationBO extends DictAction {
    /**
     * 需要替换位置 说明 ： 1. 错误参数处理,根据实际情况修改 2. searchCustRelationData 改方法的参数名称 3.
     * findCustRelationByCond(String cust_relation_id,String seq_nbr)
     * 参数需要根据实际情况修改 4. 不需要的方法，可以根据实际情况进行裁剪 5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertCustRelation(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CustRelation = (Map) param.get("CustRelation");

        CustRelationDAO dao = new CustRelationDAO();
        boolean result = dao.insert(CustRelation);

        return result;
    }

    public String insertCustRelationOne(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CustRelation = (Map) param.get("CustRelation");

        String cust_relation_id = SeqDAOFactory.getInstance()
                .getSequenceManageDAO().getNextSequence("CUST_RELATION",
                        "CUST_RELATION_ID");

        CustRelation.put("cust_relation_id", cust_relation_id);
        CustRelation.put("seq_nbr", "0");
        CustRelation.put("record_eff_date", DateFormatUtils.getFormatedDate());
        CustRelation.put("record_exp_date", CustHelper.DEFAULT_EXPIRE_DATE);
        CustRelation.put("state_date", DateFormatUtils.getFormatedDateTime());
        CustRelation.put("action_type", AcctBO.ACTION_TYPE_ADD);

        CustRelationDAO dao = new CustRelationDAO();
        boolean result = dao.insert(CustRelation);

        return cust_relation_id;
    }

    public boolean updateCustRelation(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CustRelation = (Map) param.get("CustRelation");
        String keyStr = "cust_relation_id,seq_nbr";
        Map keyCondMap = Const.getMapForTargetStr(CustRelation, keyStr);
        CustRelationDAO dao = new CustRelationDAO();

        Map omp = dao.findByPrimaryKey(keyCondMap);
        Map nmp = AcctBO.getMapForTargetMap(omp, CustRelation);
        int seq_nbr = Integer.parseInt((String) omp.get("seq_nbr")) + 1;
        nmp.put("seq_nbr", String.valueOf(seq_nbr));
        nmp.put("record_eff_date", DateFormatUtils.getFormatedDate());
        nmp.put("record_exp_date", CustHelper.DEFAULT_EXPIRE_DATE);
        nmp.put("state_date", DateFormatUtils.getFormatedDateTime());
        nmp.put("action_type", AcctBO.ACTION_TYPE_MODIFY);

        if (!dao.insert(nmp)) {
            return false;
        }

        omp.put("record_exp_date", DateFormatUtils.getFormatedDate());

        boolean result = dao.updateByKey(omp, keyCondMap);

        return result;
    }

    public boolean updateCustRelationAll(DynamicDict dto) throws Exception {
        DynamicDict adict = new DynamicDict();

        Map param = Const.getParam(dto);

        Map allinfo = (Map) param.get("all");
        Map srcInfo = (Map) allinfo.get("SrcInfo");
        Map targetInfo = (Map) allinfo.get("TargetInfo");
        String custRelationId = (String) allinfo.get("custRelationId");
        String custId = (String) allinfo.get("custId");

        List currentEntitySrcInfo = (List) srcInfo.get("currentEntity");
        if (currentEntitySrcInfo == null) {
            currentEntitySrcInfo = new ArrayList();
        }
        for (Iterator it = currentEntitySrcInfo.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            // mp.put("cust_id", custId);
            Map pa = new HashMap();
            pa.put("CustRelation", mp);
            adict.setValueByName(Const.ACTION_PARAMETER, pa);
            String actionType = (String) mp.get("action_type");
            String cust_relation_id = (String) mp.get("cust_relation_id");
            if ("M".equals(actionType) && !"".equals(cust_relation_id)) {// 对原记录的修改
                updateCustRelation(adict);
            }
            if ("A".equals(actionType)) {// 新增记录的修改
                if ("".equals(cust_relation_id)) {
                    custRelationId = insertCustRelationOne(adict);
                }
            }
        }

        List deleteEntitySrcInfo = (List) srcInfo.get("deleteEntity");
        if (deleteEntitySrcInfo == null) {
            deleteEntitySrcInfo = new ArrayList();
        }
        for (Iterator it = deleteEntitySrcInfo.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            String cust_relation_id = (String) mp.get("cust_relation_id");
            if ("" != cust_relation_id) {
                adict.setValueByName(Const.ACTION_PARAMETER, mp);
                deleteCustRelationById(adict);
            }
        }

        List currentEntityTargetInfo = (List) targetInfo.get("currentEntity");
        if (currentEntityTargetInfo == null) {
            currentEntityTargetInfo = new ArrayList();
        }
        for (Iterator it = currentEntityTargetInfo.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            // mp.put("cust_id", custId);
            Map pa = new HashMap();
            pa.put("CustRelation", mp);
            adict.setValueByName(Const.ACTION_PARAMETER, pa);
            String actionType = (String) mp.get("action_type");
            String cust_relation_id = (String) mp.get("cust_relation_id");
            if ("M".equals(actionType) && !"".equals(cust_relation_id)) {// 对原记录的修改
                updateCustRelation(adict);
            }
            if ("A".equals(actionType)) {// 新增记录的修改
                if ("".equals(cust_relation_id)) {
                    custRelationId = insertCustRelationOne(adict);
                }
            }
        }

        List deleteEntityTargetInfo = (List) targetInfo.get("deleteEntity");
        if (deleteEntityTargetInfo == null) {
            deleteEntityTargetInfo = new ArrayList();
        }
        for (Iterator it = deleteEntityTargetInfo.iterator(); it.hasNext();) {
            Map mp = (Map) it.next();
            String cust_relation_id = (String) mp.get("cust_relation_id");
            if ("" != cust_relation_id) {
                adict.setValueByName(Const.ACTION_PARAMETER, mp);
                deleteCustRelationById(adict);
            }
        }

        return true;

    }

    public PageModel searchCustRelationData(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        if (Const.containStrValue(param, "param1")) {
            whereCond.append(" and param1 = ? ");
            para.add(Const.getStrValue(param, "param1"));
        }

        if (Const.containStrValue(param, "param2")) {
            whereCond.append(" and param2 = ? ");
            para.add(Const.getStrValue(param, "param2"));
        }

        if (Const.containStrValue(param, "param3")) {
            whereCond.append(" and param3 = ? ");
            para.add(Const.getStrValue(param, "param3"));
        }

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        // 调用DAO代码
        CustRelationDAO dao = new CustRelationDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    public List searchCustRelationDataList(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);
        String SQL = null;

        String cust_id = (String) param.get("cust_id");
        String datatype = (String) param.get("datatype");

        if (datatype.trim().equals("SRC")) {
            SQL = "select distinct 'SRC' AS location , c.cust_name, t.$columns  from CUST_RELATION t , cust c  "
                    + " where t.party_id = ?  "
                    + " and c.cust_id = t.cust_id and  sysdate < c.record_exp_date "
                    + " and sysdate < t.record_exp_date ";
        } else if (datatype.trim().equals("TARGET")) {
            SQL = "select distinct 'TARGET' AS location, c.cust_name, t.$columns from  CUST_RELATION t, cust c  "
                    + " where t.cust_id = ?  "
                    + " and c.cust_id = t.party_id and sysdate < c.record_exp_date "
                    + " and sysdate < t.record_exp_date ";
        }

        List whereCondParams = new ArrayList();
        whereCondParams.add(cust_id);

        CustRelationDAO dao = new CustRelationDAO();
        List result = dao.findBySql(SQL, whereCondParams);

        return result;
    }

    public List searchCustRelationDataListHis(DynamicDict dto) throws Exception {
        // 条件处理
        Map param = Const.getParam(dto);
        String SQL = null;
        String CNT_SQL = null;

        String cust_id = (String) param.get("cust_id");
        String datatype = (String) param.get("datatype");
        String queryDate = (String) param.get("queryDate");

//        if (queryDate == null || queryDate.trim().equals("")) {
//            queryDate = DateFormatUtils.getFormatedDate();
//        }
        
        List whereCondParams = new ArrayList();
        whereCondParams.add(cust_id);

        if (datatype.trim().equals("SRC")) { //客户关系源对象
            if (!"".equals(queryDate) && queryDate != null) { //历史查询
                whereCondParams.add(DAOUtils.parseDateTime(queryDate));
                SQL = "select distinct 'SRC' AS location, rownum as cur_cnt, c.cust_name, t.$columns  from CUST_RELATION t , cust c  "
                    + " where t.party_id = ?  "
                    + " and c.cust_id = t.cust_id and  sysdate < c.record_exp_date "
                    + " and t.state_date <= ? "
                    + " and rownum <= 10 " + " order by t.state_date desc ";

                CNT_SQL = "select count(1) cnt from CUST_RELATION t , cust c  "
                    + " where t.party_id = ?  "
                    + " and c.cust_id = t.cust_id and  sysdate < c.record_exp_date "
                    + " and t.state_date <= ? "
                    + " and rownum <= 10 " + " order by t.state_date desc ";
            }else{ //当前查询
                SQL = "select distinct 'SRC' AS location, rownum as cur_cnt, c.cust_name, t.$columns  from CUST_RELATION t , cust c  "
                    + " where t.party_id = ?  "
                    + " and c.cust_id = t.cust_id and  sysdate < c.record_exp_date "
                    + " and sysdate < t.record_exp_date  "
                    + " and rownum <= 10 " + " order by t.state_date desc ";

                CNT_SQL = "select count(1) cnt from CUST_RELATION t , cust c  "
                    + " where t.party_id = ?  "
                    + " and c.cust_id = t.cust_id and  sysdate < c.record_exp_date "
                    + " and sysdate < t.record_exp_date "
                    + " and rownum <= 10 " + " order by t.state_date desc ";
            }

        } else if (datatype.trim().equals("TARGET")) {  //客户关系目标对象
            if (!"".equals(queryDate) && queryDate != null) { //历史查询
                whereCondParams.add(DAOUtils.parseDateTime(queryDate));
                SQL = "select distinct 'TARGET' AS location, rownum as cur_cnt, c.cust_name, t.$columns from  CUST_RELATION t, cust c  "
                    + " where t.cust_id = ?  "
                    + " and c.cust_id = t.party_id and sysdate < c.record_exp_date "
                    + " and t.state_date <= ?  "
                    + " and rownum <= 10 " + " order by t.state_date desc ";

                CNT_SQL = "select count(1) cnt from  CUST_RELATION t, cust c  "
                    + " where t.cust_id = ?  "
                    + " and c.cust_id = t.party_id and sysdate < c.record_exp_date "
                    + " and t.state_date <= ?   "
                    + " and rownum <= 10 " + " order by t.state_date desc ";
            }else {//当前查询
                SQL = "select distinct 'TARGET' AS location, rownum as cur_cnt, c.cust_name, t.$columns from  CUST_RELATION t, cust c  "
                    + " where t.cust_id = ?  "
                    + " and c.cust_id = t.party_id and sysdate < c.record_exp_date "
                    + " and sysdate < t.record_exp_date  "
                    + " and rownum <= 10 " + " order by t.state_date desc ";

                CNT_SQL = "select count(1) cnt from  CUST_RELATION t, cust c  "
                    + " where t.cust_id = ?  "
                    + " and c.cust_id = t.party_id and sysdate < c.record_exp_date "
                    + " and sysdate < t.record_exp_date    "
                    + " and rownum <= 10 " + " order by t.state_date desc ";
            }
        }

       

        CustRelationDAO dao = new CustRelationDAO();
        List cntList = dao.findBySql(CNT_SQL, whereCondParams);
        String cnt = "0";
        if (cntList != null && cntList.size()>0) {
            Map mp=(HashMap)cntList.get(0);
            cnt = (String)mp.get("cnt");
        }
        
//        String cnt=Base.query_string(dao.getDbName(),CNT_SQL, new String[]{cust_id,queryDate}, Const.UN_JUDGE_ERROR);
        
        List result = dao.findBySql(SQL, whereCondParams);
        for (Iterator it=result.iterator();it.hasNext();) {
            Map tm = (Map)it.next();
            tm.put("cnt",cnt);
        }

        return result;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getCustRelationById(DynamicDict dto) throws Exception {
        // 调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        CustRelationDAO dao = new CustRelationDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findCustRelationByCond(DynamicDict dto) throws Exception {
        // 条件处理
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        // 组织调用DAO代码参数、
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        // 调用DAO代码
        CustRelationDAO dao = new CustRelationDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteCustRelationById(DynamicDict dto) throws Exception {
        // 调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        CustRelationDAO dao = new CustRelationDAO();

        String SQL = "update cust_relation set action_type = ?, state_date = ?, record_exp_date = ?  "
                + " where cust_relation_id = ? and seq_nbr = ?  ";

        List updateParams = new ArrayList();
        updateParams.add(AcctBO.ACTION_TYPE_DELETE);
        updateParams.add(DAOUtils.getCurrentDate());
        updateParams.add(DAOUtils.getCurrentDate());
        updateParams.add(keyCondMap.get("cust_relation_id"));
        updateParams.add(keyCondMap.get("seq_nbr"));

        // boolean result = dao.deleteByKey(keyCondMap) > 0;

        boolean result = dao.update(SQL, updateParams);

        return result;
    }
}
