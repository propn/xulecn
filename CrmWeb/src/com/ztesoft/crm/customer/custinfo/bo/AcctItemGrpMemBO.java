package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.AcctItemGrpMemDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AcctItemGrpMemBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchAcctItemGrpMemData �ķ����Ĳ�������
             3. findAcctItemGrpMemByCond(String acct_item_group_id,String acct_item_type_id,String item_source_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertAcctItemGrpMem(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map AcctItemGrpMem = (Map) param.get("AcctItemGrpMem");

        AcctItemGrpMemDAO dao = new AcctItemGrpMemDAO();
        boolean result = dao.insert(AcctItemGrpMem);

        return result;
    }

    public boolean updateAcctItemGrpMem(DynamicDict dto)
        throws Exception {
        Map param = Const.getParam(dto);
        Map AcctItemGrpMem = (Map) param.get("AcctItemGrpMem");
        String keyStr = "acct_item_group_id,acct_item_type_id,item_source_id";
        Map keyCondMap = Const.getMapForTargetStr(AcctItemGrpMem, keyStr);
        AcctItemGrpMemDAO dao = new AcctItemGrpMemDAO();
        boolean result = dao.updateByKey(AcctItemGrpMem, keyCondMap);

        return result;
    }

    public PageModel searchAcctItemGrpMemData(DynamicDict dto)
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
        AcctItemGrpMemDAO dao = new AcctItemGrpMemDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getAcctItemGrpMemById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        AcctItemGrpMemDAO dao = new AcctItemGrpMemDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findAcctItemGrpMemByCond(DynamicDict dto)
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
        AcctItemGrpMemDAO dao = new AcctItemGrpMemDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteAcctItemGrpMemById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        AcctItemGrpMemDAO dao = new AcctItemGrpMemDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
