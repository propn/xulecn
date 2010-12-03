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
 * 2010-1-12����07:03:02
 * modifid: ...
 */
public class RcNoConfigBo {
    /**
     * ��ѯ�����еı���������
     * @return
     */
    public List qryNoRecLanConf() {
        RcNoRecRuleDAO dao = SrNSDAOFactory.getInstance().getRcNoRecRuleDAO();
        List list = dao.findAllLanConf();

        return list;
    }

    /**
     * ��ѯ�Ƿ���к�������ͷŵı�־
     * 1���ͷţ�0�����ͷţ�-911��û��������ؼ�¼��������
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
     * �޸ĺ����Ƿ�����ͷŵı�־
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
     * �������»��չ���
     * @param rules
     * @return
     */
    public String saveNoRecLanConf(RcNoRecRuleVO[] rules) {
        RcNoRecRuleDAO dao = SrNSDAOFactory.getInstance().getRcNoRecRuleDAO();
        long num = dao.updateBatch(rules);

        return String.valueOf(num);
    }
}
