package com.ztesoft.crm.salesres.bo;



import java.util.List;
import java.util.Map;

import com.ztesoft.crm.salesres.dao.RcNoRecRuleDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcNoRecRuleVO;



/**
 * RcNoConfigBo.java
 * function:	
 * created by: nik
 * 2010-1-12下午07:03:02
 * modifid: ...
 */
public class RcNoConfigBo {
    /**
     * 查询出所有的本地网配置
     * @return
     */
    public List qryNoRecLanConf() {
        RcNoRecRuleDAO dao = SrNSDAOFactory.getInstance().getRcNoRecRuleDAO();
        List list = dao.findAllLanConf();

        return list;
    }

    /**
     * 查询是否进行号码回收释放的标志
     * 1：释放；0：不释放；-911：没有配置相关记录，请配置
     * @return
     */
    public String getNoRecSet() {
        String result = "";
        String sql = "select CODEA,CODEB from dc_public where stype=94911 and PKEY='1' ";
        SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
        String[] arrs = new String[] { "CODEA", "CODEB" };
        List list = comDao.qryComSql(sql, arrs);

        if ((list == null) || (list.size() != 1)) {
            return "-911";
        }

        if (((Map) list.get(0)).get("CODEA") != null) {
            result = (String) ((Map) list.get(0)).get("CODEA") + ",";

            if (((Map) list.get(0)).get("CODEB") != null) {
                result += (String) ((Map) list.get(0)).get("CODEB");
            }
        }

        return result;
    }

    /**
     * 修改号码是否回收释放的标志
     * @param pkey
     * @return
     */
    public String saveNoRecSet(String pkey, String defaultDayNum) {
        String result = "-1";

        if ((pkey != null) && (pkey.trim().length() > 0)) {
            String sql = "update dc_public set CODEA=?,CODEB=? where stype=94911 and PKEY='1' ";
            SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
            comDao.excute(sql, new String[] { pkey, defaultDayNum });
            result = "1";
        }

        return result;
    }

    /**
     * 批量更新回收规则
     * @param rules
     * @return
     */
    public String saveNoRecLanConf(RcNoRecRuleVO[] rules) {
        RcNoRecRuleDAO dao = SrNSDAOFactory.getInstance().getRcNoRecRuleDAO();
        long num = dao.updateBatch(rules);

        return String.valueOf(num);
    }
}
