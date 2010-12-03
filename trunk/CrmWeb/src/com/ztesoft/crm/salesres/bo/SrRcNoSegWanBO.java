package com.ztesoft.crm.salesres.bo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.dao.RcNosegAppDAO;
import com.ztesoft.crm.salesres.dao.RcNosegApplogDAO;
import com.ztesoft.crm.salesres.dao.RcNosegWanDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcNosegAppVO;
import com.ztesoft.crm.salesres.vo.RcNosegApplogVO;
import com.ztesoft.crm.salesres.vo.RcNosegWanVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;


public class SrRcNoSegWanBO {
    //查询万号段
    public PageModel qryRcNoSegWan(String noSegName, String state,
        String lanId, int pi, int ps) {
        PageModel pm = new PageModel();
        String sql = "1 = 1 ";

        try {
            if ((noSegName != null) && (noSegName.trim().length() > 0)) {
                sql += (" and seg_name like '%" + noSegName + "%' ");
            }

            if ((state != null) && (state.trim().length() > 0)) {
                sql += (" and state =" + state + " ");
            }

            if ((lanId != null) && (lanId.trim().length() > 0)) {
                sql += (" and lan_id =" + lanId + " ");
            }

            RcNosegWanDAO pdao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
            pm = PageHelper.popupPageModel(pdao, sql, pi, ps);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    public PageModel qryRcNoSegWanById(String segId, int pi, int ps) {
        PageModel pm = new PageModel();
        String sql = "1 = 1 ";

        if ((segId != null) && (segId.trim().length() > 0)) {
            sql += (" and seg_id =" + segId + " ");
        }

        RcNosegWanDAO pdao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
        pm = PageHelper.popupPageModel(pdao, sql, pi, ps);

        return pm;
    }

    //新增万号段
    public String addRcNoSegWan(RcNosegWanVO vo) {
        String noSegId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("RC_NOSEG_WAN", "SEG_ID");
        vo.setSegId(noSegId); //no_seg_id字段值与no_seg_name字段值一样；

        String flag = "0";

        //        long lCount = 0;
        //        long lRsCount = 0;
        //----modified by panyazong 改为变量绑定，预编译方式   20090617
        String sql = "SELECT * FROM RC_NOSEG_WAN WHERE";

        //        sql += "(begin_no <= "+vo.getBeginNo()+" and end_no >= "+vo.getBeginNo()+") OR (begin_no <= "+vo.getEndNo()+" and end_no >= "+vo.getEndNo()+")";
        String sql1 = sql +
            " (begin_no <= ? and end_no >= ?) OR (begin_no <= ? and end_no >= ?)";

        //        String whereCond = " seg_name = '" +vo.getSegName()+"'";
        String whereCond = " seg_name =?";
        String sql2 = sql + whereCond;
        RcNosegWanDAO pdao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
        String[] sqlParams = new String[4];
        sqlParams[0] = vo.getBeginNo();
        sqlParams[1] = vo.getBeginNo();
        sqlParams[2] = vo.getEndNo();
        sqlParams[3] = vo.getEndNo();

        List list = pdao.findBySql(sql1, sqlParams);

        //        if(lRsCount>0){
        if ((list != null) && (list.size() > 0)) {
            flag = "5";
        } else {
            String[] sqlParams2 = new String[1];
            sqlParams2[0] = vo.getSegName();
            list = pdao.findBySql(sql2, sqlParams2);

            //            lCount = pdao.countRsByCond(whereCond);
            //            if (lCount == 0) {
            if ((list == null) || (list.size() <= 0)) {
                pdao.insert(vo);
                flag = vo.getSegName();
            } else {
                flag = "2";
            }
        }

        //同时新增审批表
        addRcNoSegApp(vo);

        //同时新增审批日志表
        addRcNoSegLogApp(vo);

        return flag;
    }

    //新增审批表
    public String addRcNoSegApp(RcNosegWanVO vo) {
        RcNosegAppVO rcNosegAppVO = new RcNosegAppVO();
        rcNosegAppVO.setSegId(vo.getSegId());
        rcNosegAppVO.setState("1");
        rcNosegAppVO.setLanId(vo.getLanId());
        rcNosegAppVO.setOperCode(vo.getOperCode());
        rcNosegAppVO.setDepartId(vo.getDepartId());

        Date currentTime = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        rcNosegAppVO.setOpertime(dateString);
        rcNosegAppVO.setNotes("");

        String flag = "0";
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        pdao.insert(rcNosegAppVO);

        return flag;
    }

    //新增日志表
    public String addRcNoSegLogApp(RcNosegWanVO vo) {
        RcNosegApplogVO rcNosegAppVO = new RcNosegApplogVO();
        rcNosegAppVO.setSegId(vo.getSegId());
        rcNosegAppVO.setState("1");
        rcNosegAppVO.setLanId(vo.getLanId());
        rcNosegAppVO.setOperCode(vo.getOperCode());
        rcNosegAppVO.setDepartId(vo.getDepartId());

        Date currentTime = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        rcNosegAppVO.setOpertime(dateString);
        rcNosegAppVO.setNotes("");

        String flag = "0";
        RcNosegApplogDAO pdao = SrNSDAOFactory.getInstance()
                                              .getRcNosegApplogDAO();
        pdao.insert(rcNosegAppVO);

        return flag;
    }

    //更新万号段
    public String updateNoSegWan(Map map) {
        RcNosegWanVO vo = (RcNosegWanVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        String oldNoSegId = vo.getSegId(); //取得将要更新的记录的ID
                                           //vo.setNoSegId(vo.getNoSegName()); //no_seg_id字段值与no_seg_name字段值一样；

        String flag = "0";
        long lCount = 0;
        long lRsCount = 0;
        String sql = "((begin_no <= " + vo.getBeginNo() + " and end_no >= " +
            vo.getBeginNo() + ") OR (begin_no <= " + vo.getEndNo() +
            " and end_no >= " + vo.getEndNo() + ")) and seg_id != " +
            oldNoSegId;
        RcNosegWanDAO pdao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
        String whereCond = " seg_name = '" + vo.getSegName() +
            "' and SEG_ID!=" + oldNoSegId;
        String updateWhereCond = " SEG_ID =" + oldNoSegId;
        lRsCount = pdao.findNoSegWanByCond(sql);

        if (lRsCount > 0) {
            flag = "5";
        } else {
            lCount = pdao.countRsByCond(whereCond);

            if (lCount == 0) {
                RcNosegWanVO vo_old = pdao.findByPrimaryKey(vo.getSegId());
                boolean bFlag = pdao.update(updateWhereCond, vo);

                if (bFlag) {
                    flag = vo.getSegName();

                    // 插入相关通用日至
                    RcPublicLogDAO logDao = SrDAOFactory.getInstance()
                                                        .getRcPublicLogDAO();
                    RcPublicLogVO logVO = new RcPublicLogVO();
                    logVO.setAct("M");
                    logVO.setReworkTime(DAOUtils.getFormatedDate());
                    logVO.setReworkTable("rc_noseg_wan");
                    logVO.setReworkWen(operId);
                    logVO.setReworkIp(reworkIp);
                    logDao.logVO(logVO, vo_old, vo);
                } else {
                    flag = "2";
                }
            } else {
                flag = "4";
            }
        }

        return flag;
    }

    //删除万号段
    public String delRcNoSegWan(Map map) {
        String noSegId = (String) map.get("segId");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        String flag = "0";

        // 检查万号段下是否有号段
        RcNoSegDAO daoNo = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        long noNum = daoNo.countByCond(" seg_id=" + noSegId);

        if (noNum > 0) {
            flag = "3";

            return flag;
        }

        // 检查万号段是否已启用
        String whereCond = " seg_id = " + noSegId;

        // 插入相关通用日至
        RcNosegWanVO vo_old = null;
        RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
        RcNosegWanDAO pdao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
        vo_old = pdao.findByPrimaryKey(noSegId);

        RcPublicLogVO logVO = new RcPublicLogVO();
        logVO.setAct("D");
        logVO.setReworkTime(DAOUtils.getFormatedDate());
        logVO.setReworkTable("rc_no_seg");
        logVO.setReworkWen(operId);
        logVO.setReworkIp(reworkIp);
        logDao.logVO(logVO, vo_old, null);
        // 删除相关万号段
        pdao.deleteByCond(whereCond);

        //删除相关万号段的申请记录
        RcNosegAppDAO appdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        appdao.deleteByCond(whereCond);

        //删除相关万号段的申请日志
        RcNosegApplogDAO applogdao = SrNSDAOFactory.getInstance()
                                                   .getRcNosegApplogDAO();
        applogdao.deleteByCond(whereCond);

        flag = "1";

        return flag;
    }

    //查询审批日志
    public PageModel qryRcNoSegAppLog(String segId, int pi, int ps) {
        RcNosegApplogDAO pdao = SrNSDAOFactory.getInstance()
                                              .getRcNosegApplogDAO();
        PageModel pm = pdao.qryRcNoSegAppLog(segId, pi, ps);

        return pm;
    }

    //查询审批
    public PageModel qryRcNoSegApp(String segId, int pi, int ps) {
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        PageModel pm = pdao.qryRcNoSegAppForCheck("", "", segId, "", pi, ps);

        return pm;
    }

    //查询审批
    public PageModel qryRcNoSegAppForCheck(String lanId, String state,
        String segName, int pi, int ps) {
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        String segId = "";
        PageModel pm = pdao.qryRcNoSegAppForCheck(lanId, state, segId, segName,
                pi, ps);

        return pm;
    }

    //重新申请
    public String appAgain(RcNosegAppVO vo) {
        String flag = "";

        try {
            appAgainInsertToLog(vo, "1"); //插入日志表

            RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
            flag = pdao.appAgain(vo, "1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    //重新申请
    public String appAgainInsertToLog(RcNosegAppVO vo, String isPass) {
        RcNosegApplogVO rcNosegApplogVO = new RcNosegApplogVO();
        rcNosegApplogVO.setSegId(vo.getSegId());
        rcNosegApplogVO.setState(isPass);
        rcNosegApplogVO.setLanId(vo.getLanId());
        rcNosegApplogVO.setOperCode(vo.getOperCode());
        rcNosegApplogVO.setDepartId(vo.getDepartId());

        Date currentTime = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        rcNosegApplogVO.setOpertime(dateString);
        rcNosegApplogVO.setNotes(vo.getNotes());

        String flag = "";

        try {
            RcNosegApplogDAO pdao = SrNSDAOFactory.getInstance()
                                                  .getRcNosegApplogDAO();
            pdao.insert(rcNosegApplogVO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    //审核
    public String checkWan(RcNosegAppVO vo, String isPass) {
        String flag = "";
        String segId = vo.getSegId();

        //改万号段表
        RcNosegWanDAO wandao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
        wandao.checkWan(segId, isPass);

        //改审批表
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        flag = pdao.appAgain(vo, isPass);
        //插日志表
        appAgainInsertToLog(vo, isPass); //插入日志表

        return flag;
    }

    /**
     * 导入万号段信息
     * @param paraMap
     * @return
     */
    public Map noSegWanUpLoad(Map paraMap) {
        if (paraMap == null) {
            return null;
        }

        Map map = new HashMap();
        RcNosegWanVO vo = (RcNosegWanVO) paraMap.get("paramVO");
        List list = (List) paraMap.get("InputList");
        String nowDate = DAOUtils.getFormatedDate();
        int successCount = 0;
        int failCount = 0;
        String messCode1 = " ----万号段号码范围存在重叠，错误";
        String messCode2 = " ----万号段已经存在，错误";
        List failList = new ArrayList();

        if ((vo == null) || (list == null) || (list.size() < 1) ||
                (vo.getNoGrpId() == null) ||
                (vo.getNoGrpId().trim().length() < 1)) {
            map.put("result", "-1");
            map.put("successCount", String.valueOf(successCount));
            map.put("failCount",
                String.valueOf((list == null) ? 0 : list.size()));

            return map;
        }

        RcNosegWanVO insertVO = null;
        String rtn = null;

        for (int i = 0; i < list.size(); i++) {
            insertVO = (RcNosegWanVO) list.get(i);

            if (insertVO != null) {
                if ((insertVO.getBeginNo() == null) ||
                        (insertVO.getBeginNo().trim().length() < 1)) {
                    failCount++;

                    continue;
                } else {
                    // 设置实例VO
                    insertVO.setCreateTime(nowDate);
                    insertVO.setNoGrpId(vo.getNoGrpId());
                    insertVO.setState("1");
                    rtn = addRcNoSegWan(insertVO);

                    if (rtn != null) {
                        if ("5".equals(rtn)) {
                            failList.add(insertVO.getBeginNo() + " " +
                                insertVO.getEndNo() + messCode1);
                            failCount++;
                        } else if ("2".equals(rtn)) {
                            failList.add(insertVO.getBeginNo() + " " +
                                insertVO.getEndNo() + messCode2);
                            failCount++;
                        } else {
                            successCount++;
                        }
                    }
                }
            }
        }

        map.put("result", "1");
        map.put("successCount", String.valueOf(successCount));
        map.put("failCount", String.valueOf(failCount));
        map.put("failList", failList);

        return map;
    }
}
