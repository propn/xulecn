package com.ztesoft.crm.customer.custinfo.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;

import com.ztesoft.crm.customer.custinfo.dao.BankDAO;
import com.ztesoft.crm.customer.custinfo.vo.BankVO;
import com.ztesoft.crm.salesres.common.CommonDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Iterator;


public class BankBO extends DictAction {
    /**
        需要替换位置 说明 ：
             1. 错误参数处理,根据实际情况修改
             2. searchBankData 改方法的参数名称
             3. findBankByCond(String bank_id) 参数需要根据实际情况修改
             4. 不需要的方法，可以根据实际情况进行裁剪
             5. 此段啰嗦话，完成后替换工作后，请删除！
     */
    public boolean insertBank(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Bank = (Map) param.get("Bank");

        BankDAO dao = new BankDAO();
        boolean result = dao.insert(Bank);

        return result;
    }

    public boolean updateBank(DynamicDict dto) throws Exception {
        Map param = Const.getParam(dto);
        Map Bank = (Map) param.get("Bank");
        String keyStr = "bank_id";
        Map keyCondMap = Const.getMapForTargetStr(Bank, keyStr);
        BankDAO dao = new BankDAO();
        boolean result = dao.updateByKey(Bank, keyCondMap);

        return result;
    }

    public PageModel searchBankData(DynamicDict dto) throws Exception {
        //条件处理
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        if (Const.containStrValue(param, "sType")) {
            whereCond.append(" and sType = ? ");
            para.add(Const.getStrValue(param, "sType"));
        }

        if (Const.containStrValue(param, "sContent")) {
            whereCond.append(" and sContent = ? ");
            para.add(Const.getStrValue(param, "sContent"));
        }
        
        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        //调用DAO代码
        BankDAO dao = new BankDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }
    
    public PageModel getBankData(DynamicDict dto) throws Exception {
        //条件处理
        Map param = Const.getParam(dto);
        StringBuffer whereCond = new StringBuffer();
        List para = new ArrayList();

        String sType  = (String)param.get("sType");
        String sContent  = (String)param.get("sContent");
        
        if(sContent == null){ sContent = "";}
        if(sType == null ){ sType = "" ;}
        
        if(!("".equals(sContent)))
        {
            if("scode".equals(sType))
            {
                whereCond.append("  and bank_code like  ? ");
                para.add("%" + Const.getStrValue(param, "sContent") + "%");
            }
            if("sname".equals(sType))
            {
                whereCond.append("  and bank_name like  ? ");
                para.add("%" + Const.getStrValue(param, "sContent") + "%");
            }
        }

        int pageSize = Const.getPageSize(param);
        int pageIndex = Const.getPageIndex(param);

        //调用DAO代码
        BankDAO dao = new BankDAO();
        PageModel result = dao.searchByCond(whereCond.toString(), para,
                pageIndex, pageSize);

        return result;
    }
    
    /**
     * 银行查询
     * @param dto
     * @return
     * @throws Exception
     */
    public String queryBankXml(DynamicDict dto) throws Exception{
        Map param = Const.getParam(dto);
        String region_id = (String) param.get("region_id");
        String bank_id = (String) param.get("parent_bank_id");
        
        List retlist = null;
        
        String sql = "";
        CommonDAO dao = new CommonDAO();
        if(!"0".equals(region_id)){
            sql =" select a.bank_id, a.bank_name, a.parent_bank_id, a.region_id from bank a where a.region_id = ?  ";
            retlist = (ArrayList)dao.executeQueryForMapList(sql, new String[]{region_id});
        }else{
            sql =" select a.bank_id, a.bank_name, a.parent_bank_id, a.region_id from bank a where a.parent_bank_id = ? and a.region_id = ? ";
            retlist = dao.executeQueryForMapList(sql, new String[]{bank_id,region_id});
        }
        ArrayList vos = new ArrayList();
        for (Iterator iterator = retlist.iterator(); iterator.hasNext();) {
            HashMap item = (HashMap) iterator.next();
            BankVO vo = new BankVO();
            vo.loadFromHashMap(item);
            vos.add(vo);
        }
        String retXml = XMLSegBuilder.toXmlItems(vos);
        return retXml;
    }

    /**
     * 根据库表主键查询对象
     */
    public Map getBankById(DynamicDict dto) throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        BankDAO dao = new BankDAO();
        Map result = dao.findByPrimaryKey(keyCondMap);

        return result;
    }

    public List findBankByCond(DynamicDict dto) throws Exception {
        //条件处理
        String filterStr = "";
        Map changeName = null;
        SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
                SQLWhereClause.NO_PAGING);

        //组织调用DAO代码参数、
        String whereCond = where.getWhereCond();
        List para = where.getPara();

        //调用DAO代码
        BankDAO dao = new BankDAO();
        List result = dao.findByCond(whereCond, para);

        return result;
    }

    public boolean deleteBankById(DynamicDict dto) throws Exception {
        //调用DAO代码
        Map keyCondMap = Const.getParam(dto);
        BankDAO dao = new BankDAO();
        boolean result = dao.deleteByKey(keyCondMap) > 0;

        return result;
    }
}
