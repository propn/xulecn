package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.IndustryNameKeyDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IndustryNameKeyBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchIndustryNameKeyData �ķ����Ĳ�������
             3. findIndustryNameKeyByCond(String name_key_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertIndustryNameKey(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map IndustryNameKey = (Map) param.get("IndustryNameKey");

        IndustryNameKeyDAO dao = new IndustryNameKeyDAO();
        boolean result = dao.insert(IndustryNameKey);

        return result;
    }

    public boolean updateIndustryNameKey(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map IndustryNameKey = (Map) param.get("IndustryNameKey");
        String keyStr = "name_key_id";
        Map keyCondMap = Const.getMapForTargetStr(IndustryNameKey, keyStr);
        IndustryNameKeyDAO dao = new IndustryNameKeyDAO();
        boolean result = dao.updateByKey(IndustryNameKey, keyCondMap);

        return result;
    }

    public PageModel searchIndustryNameKeyData(DynamicDict dto)
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
        IndustryNameKeyDAO dao = new IndustryNameKeyDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getIndustryNameKeyById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        IndustryNameKeyDAO dao = new IndustryNameKeyDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findIndustryNameKeyByCond(DynamicDict dto)
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
        IndustryNameKeyDAO dao = new IndustryNameKeyDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteIndustryNameKeyById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        IndustryNameKeyDAO dao = new IndustryNameKeyDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
