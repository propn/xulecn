package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.common.SqlExcuteByStr;
import com.ztesoft.crm.salesres.dao.RcImsiDAO;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.RcNoImsiRelateDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.RcSimImsiDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcImsiVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;


public class SrRcImsiBO extends DictAction {
    public SrRcImsiBO() {
    }

    //新增IMSI号
    public String addRcImsi(DynamicDict dto) throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
      
        String flag = "0";
        long lCount = 0;
        RcImsiVO vo = (RcImsiVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        //vo.setInitTime(DAOUtils.getFormatedDate());
        String whereCond = " imsi_id = " + vo.getImsiId();
        RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();

        lCount = pdao.countByCond(whereCond);

        if (lCount == 0) {
            pdao.insert(vo);

            RcPublicLogDAO pdaoLog = SrDAOFactory.getInstance()
                                                 .getRcPublicLogDAO();
            RcPublicLogVO logVo = new RcPublicLogVO();
            logVo.setAct("A");
            logVo.setReworkIp(reworkIp);
            logVo.setReworkTable("RC_IMSI");
            logVo.setReworkTime(DAOUtils.getFormatedDate());
            logVo.setReworkWen(operId);
            pdaoLog.logVO(logVo, null, vo);

            flag = "1";
        } else {
            flag = "2";
        }

        return flag;
    }

    //查询IMSI号
    public PageModel qryRcImsi(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		int pi = ((Integer) m.get("pageIndex")).intValue();
		int ps = ((Integer) m.get("pageSize")).intValue();
		String imsiId = (String) m.get("imsiId");
		String simCode = (String) m.get("simCode");
		String state = (String) m.get("state");
		String masterFlag = (String) m.get("masterFlag");
        PageModel pm = new PageModel();
        StringBuffer sql = new StringBuffer();

        try {
            if (!"".equals(imsiId) && (imsiId != null)) {
                sql.append("a.imsi_id like '%" + imsiId + "%' and ");
            }

            if (!"".equals(simCode) && (simCode != null)) {
                sql.append("b.RESOURCE_INSTANCE_CODE like '%" + simCode +
                    "%' and ");
            }

            if (!"".equals(masterFlag) && (masterFlag != null)) {
                sql.append("a.MASTER_FLAG = '" + masterFlag + "' and ");
            }

            if ("".equals(state)) {
                sql.append(" 1=1");
            } else {
                sql.append(" a.state = '" + state + "'");
            }

            RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();
            pdao.setActCode("1");
            pm = PageHelper.popupPageModel(pdao, sql.toString(), pi, ps);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    //删除IMSI号
    public String delRcImsi(DynamicDict dto) throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        String flag = "0";
        long lCount = 0;
        String simCode = "";
        String imsiId = (String) map.get("imsiId");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");
        List list = new ArrayList();
        String whereCond = " imsi_id = " + imsiId;
        RcNoImsiRelateDAO noImsiReDAO = SrNSDAOFactory.getInstance()
                                                      .getRcNoImsiRelateDAO();
        RcNoDAO rcNoDAO = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();
        lCount = noImsiReDAO.countByCond(whereCond);

        if (lCount > 0) {
            flag = "2";
        } else {
            lCount = rcNoDAO.countByCond(whereCond);

            if (lCount > 0) {
                flag = "2";
            } else {
                SqlExcuteByStr G = new SqlExcuteByStr();
                String sqlStr = " select RESOURCE_INSTANCE_ID as result from rc_imsi where IMSI_ID = '" +
                    DAOUtils.filterQureyCond(imsiId) + "'";
                simCode = G.getString(sqlStr);

                if ("".equals(simCode) || (simCode == null)) {
                    RcImsiVO oldVo = new RcImsiVO();
                    list = pdao.findByCond(whereCond);
                    oldVo = (RcImsiVO) list.get(0);
                    lCount = pdao.deleteByCond(whereCond);

                    if (lCount == 1) {
                        RcPublicLogDAO pdaoLog = SrDAOFactory.getInstance()
                                                             .getRcPublicLogDAO();
                        RcPublicLogVO logVo = new RcPublicLogVO();
                        logVo.setAct("A");
                        logVo.setReworkIp(reworkIp);
                        logVo.setReworkTable("RC_IMSI");
                        logVo.setReworkTime(DAOUtils.getFormatedDate());
                        logVo.setReworkWen(operId);
                        pdaoLog.logVO(logVo, oldVo, null);

                        flag = "1";
                    }
                } else {
                    flag = "2";
                }
            }
        }

        return flag;
    }

    //更新IMSI号
    public String updateImsi(DynamicDict dto) throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        String flag = "0";
        List list = new ArrayList();
        RcImsiVO oldVo = new RcImsiVO();
        RcImsiVO vo = (RcImsiVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();
        String whereCond = " imsi_id = " + vo.getImsiId();
        pdao.setActCode("1");
        list = pdao.findByCond(whereCond);
        oldVo = (RcImsiVO) list.get(0);

        boolean bFlag = pdao.update(vo.getImsiId(), vo);

        if (bFlag) {
            RcPublicLogDAO pdaoLog = SrDAOFactory.getInstance()
                                                 .getRcPublicLogDAO();
            RcPublicLogVO logVo = new RcPublicLogVO();
            logVo.setAct("A");
            logVo.setReworkIp(reworkIp);
            logVo.setReworkTable("RC_IMSI");
            logVo.setReworkTime(DAOUtils.getFormatedDate());
            logVo.setReworkWen(operId);
            pdaoLog.logVO(logVo, oldVo, vo);
            flag = "1";
        }

        return flag;
    }

    //查询IMSI号
    public List qryRcImsiById(DynamicDict dto) throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        String imsiId= (String)map.get("imsiId");
        List list = new ArrayList();
        String sql = "";

        try {
            sql = " a.imsi_id = " + imsiId;

            RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();
            pdao.setActCode("1");
            list = pdao.findByCond(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
    *
     SIM卡查询；
    */
    public PageModel QuerySIMInfo(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		int pageIndex = ((Integer) m.get("pageIndex")).intValue();
		int pageSize = ((Integer) m.get("pageSize")).intValue();
		String rescInstanceCode = (String) m.get("rescInstanceCode");
        RcSimImsiDAO dao = SrNSDAOFactory.getInstance().getRcSimImsiDAO();
        String sql = "RESOURCE_INSTANCE_CODE like '%" + rescInstanceCode +
            "%' and state = '00A'";

        return PageHelper.popupPageModel(dao, sql, pageIndex, pageSize);
    }

    /**
     * 导入IMSI码
     * @param String
     * @return Map
     */
    public Map uploadImsi(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		String nos = (String) m.get("nos");
        Map rtnMap = new HashMap();
        long lCount = 0;
        String imsiSegId = "";
        RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();

        if ((nos != null) && (nos.trim().length() > 0)) {
            if (nos.endsWith(",")) {
                nos = nos.substring(0, nos.length() - 1);
            }

            String[] arrayValue = nos.split(",");
            String SQL_SELECT = "SELECT a.* FROM RC_IMSI a left outer join rc_sim b on a.RESOURCE_INSTANCE_ID = b.RESOURCE_INSTANCE_ID where a.imsi_id = ?";
            String[] sqlParams = new String[1];

            for (int i = 0; i < arrayValue.length; i++) {
                //----modified by panyazong 改为变量绑定，预编译方式   20090618
                //               String whereCond = " imsi_id = "+arrayValue[i];
                //               lCount = pdao.countByCond(whereCond);     		
                sqlParams[0] = arrayValue[i];

                List listTempt = pdao.findBySql(SQL_SELECT, sqlParams);

                //             if (lCount > 0) {
                if ((listTempt != null) && (listTempt.size() > 0)) {
                    rtnMap.put("result", "0");
                    rtnMap.put("info", "导入的IMSI码" + arrayValue[i] + "已存在");

                    return rtnMap;
                }

                imsiSegId = pdao.qryImsiSeg(arrayValue[i]);

                if ("".equals(imsiSegId) || (imsiSegId == null)) {
                    rtnMap.put("result", "0");
                    rtnMap.put("info",
                        "导入的IMSI码" + arrayValue[i] + "找不到与其对应的IMSI码段");

                    return rtnMap;
                }
            }

            for (int i = 0; i < arrayValue.length; i++) {
                imsiSegId = pdao.qryImsiSeg(arrayValue[i]);

                RcImsiVO vo = new RcImsiVO();
                vo.setImsiId(arrayValue[i]);
                vo.setImsiSegId(imsiSegId);
                vo.setMasterFlag("0");
                vo.setState(ParamsConsConfig.RescState_preMakeCard);
                pdao.insert(vo);
            }

            rtnMap.put("result", "1");
            rtnMap.put("info",
                "成功导入" + String.valueOf(arrayValue.length) + "个IMSI码");

            return rtnMap;
        } else {
            rtnMap.put("result", "0");
            rtnMap.put("info", "导入的IMSI码为空,请检查上传文件");

            return rtnMap;
        }
    }

    public String recImsi(DynamicDict dto) throws FrameException {
        String flag = "0";
        boolean bFlag = false;
        SqlExcuteByStr G = new SqlExcuteByStr();
        String recDay = "";
//        String sqlStr = " select codea as result from dc_public where stype = 204 ";
//        recDay = G.getString(sqlStr);

        String whereCond = "state = 'G' and (trunc(sysdate) - trunc(rec_time)) >= " +
        ParamsConsConfig.Rc_No_Recycle;
        RcImsiDAO pdao = SrNSDAOFactory.getInstance().getRcImsiDAO();
        bFlag = pdao.updateState(whereCond);

        if (bFlag) {
            flag = "1";
        } else {
            flag = "2";
        }

        return flag;
    }
}
