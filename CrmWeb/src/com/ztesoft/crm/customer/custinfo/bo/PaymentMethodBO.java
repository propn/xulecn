package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.PaymentMethodDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PaymentMethodBO extends DictAction {
    /**
        需要替换位置 说明 ：
             1. 错误参数处理,根据实际情况修改
             2. searchPaymentMethodData 改方法的参数名称
             3. findPaymentMethodByCond(String payment_method) 参数需要根据实际情况修改
             4. 不需要的方法，可以根据实际情况进行裁剪
             5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertPaymentMethod(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map PaymentMethod = (Map) param.get("PaymentMethod");

        PaymentMethodDAO dao = new PaymentMethodDAO();
        boolean result = dao.insert(PaymentMethod);

        return result;
    }

    public boolean updatePaymentMethod(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map PaymentMethod = (Map) param.get("PaymentMethod");
        String keyStr = "payment_method";
        Map keyCondMap = Const.getMapForTargetStr(PaymentMethod, keyStr);
        PaymentMethodDAO dao = new PaymentMethodDAO();
        boolean result = dao.updateByKey(PaymentMethod, keyCondMap);

        return result;
    }

    public PageModel searchPaymentMethodData(DynamicDict dto)
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
        PaymentMethodDAO dao = new PaymentMethodDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getPaymentMethodById(DynamicDict dto) throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        PaymentMethodDAO dao = new PaymentMethodDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findPaymentMethodByCond(DynamicDict dto)
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
        PaymentMethodDAO dao = new PaymentMethodDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deletePaymentMethodById(DynamicDict dto)
        throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        PaymentMethodDAO dao = new PaymentMethodDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
