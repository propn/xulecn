package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.CashPlanDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CashPlanBO extends DictAction {
    /**
        需要替换位置 说明 ：
             1. 错误参数处理,根据实际情况修改
             2. searchCashPlanData 改方法的参数名称
             3. findCashPlanByCond(String payment_plan_id) 参数需要根据实际情况修改
             4. 不需要的方法，可以根据实际情况进行裁剪
             5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertCashPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CashPlan = (Map) param.get("CashPlan");

        CashPlanDAO dao = new CashPlanDAO();
        boolean result = dao.insert(CashPlan);

        return result;
    }

    public boolean updateCashPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map CashPlan = (Map) param.get("CashPlan");
        String keyStr = "payment_plan_id";
        Map keyCondMap = Const.getMapForTargetStr(CashPlan, keyStr);
        CashPlanDAO dao = new CashPlanDAO();
        boolean result = dao.updateByKey(CashPlan, keyCondMap);

        return result;
    }

    public PageModel searchCashPlanData(DynamicDict dto)
        throws Exception {
        //条件处理
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

        //调用DAO代码
        CashPlanDAO dao = new CashPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getCashPlanById(DynamicDict dto) throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        CashPlanDAO dao = new CashPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findCashPlanByCond(DynamicDict dto) throws Exception {
        //条件处理
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //组织调用DAO代码参数、
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //调用DAO代码
        CashPlanDAO dao = new CashPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteCashPlanById(DynamicDict dto)
        throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        CashPlanDAO dao = new CashPlanDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
