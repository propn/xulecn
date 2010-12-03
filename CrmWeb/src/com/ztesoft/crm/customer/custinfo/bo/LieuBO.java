package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.LieuDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LieuBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchLieuData �ķ����Ĳ�������
             3. findLieuByCond(String lieu_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertLieu(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Lieu = (Map) param.get("Lieu");

        LieuDAO dao = new LieuDAO();
        boolean result = dao.insert(Lieu);

        return result;
    }

    public boolean updateLieu(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Lieu = (Map) param.get("Lieu");
        String keyStr = "lieu_id";
        Map keyCondMap = Const.getMapForTargetStr(Lieu, keyStr);
        LieuDAO dao = new LieuDAO();
        boolean result = dao.updateByKey(Lieu, keyCondMap);

        return result;
    }

    public PageModel searchLieuData(DynamicDict dto) throws Exception {
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
        LieuDAO dao = new LieuDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getLieuById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        LieuDAO dao = new LieuDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findLieuByCond(DynamicDict dto) throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        LieuDAO dao = new LieuDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteLieuById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        LieuDAO dao = new LieuDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
