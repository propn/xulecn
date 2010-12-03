package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.CustPersonInfoDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustPersonInfoBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchCustPersonInfoData �ķ����Ĳ�������
             3. findCustPersonInfoByCond(String cust_id,String seq_nbr) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertCustPersonInfo(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map CustPersonInfo = (Map) param.get("CustPersonInfo");

        CustPersonInfoDAO dao = new CustPersonInfoDAO();
        boolean result = dao.insert(CustPersonInfo);

        return result;
    }

    public boolean updateCustPersonInfo(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map CustPersonInfo = (Map) param.get("CustPersonInfo");
        String keyStr = "cust_id,seq_nbr";
        Map keyCondMap = Const.getMapForTargetStr(CustPersonInfo, keyStr);
        CustPersonInfoDAO dao = new CustPersonInfoDAO();
        boolean result = dao.updateByKey(CustPersonInfo, keyCondMap);

        return result;
    }

    public PageModel searchCustPersonInfoData(DynamicDict dto)
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
        CustPersonInfoDAO dao = new CustPersonInfoDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getCustPersonInfoById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        CustPersonInfoDAO dao = new CustPersonInfoDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findCustPersonInfoByCond(DynamicDict dto)
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
        CustPersonInfoDAO dao = new CustPersonInfoDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteCustPersonInfoById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        CustPersonInfoDAO dao = new CustPersonInfoDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
