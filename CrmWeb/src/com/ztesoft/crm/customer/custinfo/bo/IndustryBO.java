package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.IndustryDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IndustryBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchIndustryData �ķ����Ĳ�������
             3. findIndustryByCond(String industry_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertIndustry(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Industry = (Map) param.get("Industry");

        IndustryDAO dao = new IndustryDAO();
        boolean result = dao.insert(Industry);

        return result;
    }

    public boolean updateIndustry(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Industry = (Map) param.get("Industry");
        String keyStr = "industry_id";
        Map keyCondMap = Const.getMapForTargetStr(Industry, keyStr);
        IndustryDAO dao = new IndustryDAO();
        boolean result = dao.updateByKey(Industry, keyCondMap);

        return result;
    }

    public PageModel searchIndustryData(DynamicDict dto)
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
        IndustryDAO dao = new IndustryDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getIndustryById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        IndustryDAO dao = new IndustryDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findIndustryByCond(DynamicDict dto) throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        IndustryDAO dao = new IndustryDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteIndustryById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        IndustryDAO dao = new IndustryDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
