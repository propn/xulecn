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
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchPaymentMethodData �ķ����Ĳ�������
             3. findPaymentMethodByCond(String payment_method) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
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
        //��������
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

        //����DAO����
        PaymentMethodDAO dao = new PaymentMethodDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getPaymentMethodById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        PaymentMethodDAO dao = new PaymentMethodDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findPaymentMethodByCond(DynamicDict dto)
        throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        PaymentMethodDAO dao = new PaymentMethodDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deletePaymentMethodById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        PaymentMethodDAO dao = new PaymentMethodDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
