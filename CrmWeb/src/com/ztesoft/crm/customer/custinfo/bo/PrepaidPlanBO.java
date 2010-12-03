package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.PrepaidPlanDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PrepaidPlanBO extends DictAction {
    /**
        需要替换位置 说明 ：
             1. 错误参数处理,根据实际情况修改
             2. searchPrepaidPlanData 改方法的参数名称
             3. findPrepaidPlanByCond(String payment_plan_id) 参数需要根据实际情况修改
             4. 不需要的方法，可以根据实际情况进行裁剪
             5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertPrepaidPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map PrepaidPlan = (Map) param.get("PrepaidPlan");

        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        boolean result = dao.insert(PrepaidPlan);

        return result;
    }

    public boolean updatePrepaidPlan(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map PrepaidPlan = (Map) param.get("PrepaidPlan");
        String keyStr = "payment_plan_id";
        Map keyCondMap = Const.getMapForTargetStr(PrepaidPlan, keyStr);
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        boolean result = dao.updateByKey(PrepaidPlan, keyCondMap);

        return result;
    }

    public PageModel searchPrepaidPlanData(DynamicDict dto)
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
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getPrepaidPlanById(DynamicDict dto) throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findPrepaidPlanByCond(DynamicDict dto)
        throws Exception {
        //条件处理
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //组织调用DAO代码参数、
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //调用DAO代码
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deletePrepaidPlanById(DynamicDict dto)
        throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        PrepaidPlanDAO dao = new PrepaidPlanDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
