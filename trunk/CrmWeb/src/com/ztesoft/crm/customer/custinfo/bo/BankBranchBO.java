package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.customer.custinfo.dao.BankBranchDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BankBranchBO extends DictAction {
    /**
        ��Ҫ�滻λ�� ˵�� ��
             1. �����������,����ʵ������޸�
             2. searchBankBranchData �ķ����Ĳ�������
             3. findBankBranchByCond(String bank_branch_id) ������Ҫ����ʵ������޸�
             4. ����Ҫ�ķ��������Ը���ʵ��������вü�
             5. �˶Ά��»�����ɺ��滻��������ɾ����
     */
    public boolean insertBankBranch(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map BankBranch = (Map) param.get("BankBranch");

        BankBranchDAO dao = new BankBranchDAO();
        boolean result = dao.insert(BankBranch);

        return result;
    }

    public boolean updateBankBranch(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map BankBranch = (Map) param.get("BankBranch");
        String keyStr = "bank_branch_id";
        Map keyCondMap = Const.getMapForTargetStr(BankBranch, keyStr);
        BankBranchDAO dao = new BankBranchDAO();
        boolean result = dao.updateByKey(BankBranch, keyCondMap);

        return result;
    }

    public PageModel searchBankBranchData(DynamicDict dto)
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
        BankBranchDAO dao = new BankBranchDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }

    /**
     * ���ݿ��������ѯ����
     */
    public Map getBankBranchById(DynamicDict dto) throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        BankBranchDAO dao = new BankBranchDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findBankBranchByCond(DynamicDict dto) throws Exception {
        //��������
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //��֯����DAO���������
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //����DAO����
        BankBranchDAO dao = new BankBranchDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteBankBranchById(DynamicDict dto)
        throws Exception {
        //����DAO����
        Map keyCondMap = Const.getParam(dto);
        BankBranchDAO dao = new BankBranchDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
