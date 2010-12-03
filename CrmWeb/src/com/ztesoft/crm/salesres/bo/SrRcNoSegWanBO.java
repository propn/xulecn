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
    //��ѯ��Ŷ�
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

    //������Ŷ�
    public String addRcNoSegWan(RcNosegWanVO vo) {
        String noSegId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("RC_NOSEG_WAN", "SEG_ID");
        vo.setSegId(noSegId); //no_seg_id�ֶ�ֵ��no_seg_name�ֶ�ֵһ����

        String flag = "0";

        //        long lCount = 0;
        //        long lRsCount = 0;
        //----modified by panyazong ��Ϊ�����󶨣�Ԥ���뷽ʽ   20090617
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

        //ͬʱ����������
        addRcNoSegApp(vo);

        //ͬʱ����������־��
        addRcNoSegLogApp(vo);

        return flag;
    }

    //����������
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

    //������־��
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

    //������Ŷ�
    public String updateNoSegWan(Map map) {
        RcNosegWanVO vo = (RcNosegWanVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        String oldNoSegId = vo.getSegId(); //ȡ�ý�Ҫ���µļ�¼��ID
                                           //vo.setNoSegId(vo.getNoSegName()); //no_seg_id�ֶ�ֵ��no_seg_name�ֶ�ֵһ����

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

                    // �������ͨ������
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

    //ɾ����Ŷ�
    public String delRcNoSegWan(Map map) {
        String noSegId = (String) map.get("segId");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        String flag = "0";

        // �����Ŷ����Ƿ��кŶ�
        RcNoSegDAO daoNo = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        long noNum = daoNo.countByCond(" seg_id=" + noSegId);

        if (noNum > 0) {
            flag = "3";

            return flag;
        }

        // �����Ŷ��Ƿ�������
        String whereCond = " seg_id = " + noSegId;

        // �������ͨ������
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
        // ɾ�������Ŷ�
        pdao.deleteByCond(whereCond);

        //ɾ�������Ŷε������¼
        RcNosegAppDAO appdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        appdao.deleteByCond(whereCond);

        //ɾ�������Ŷε�������־
        RcNosegApplogDAO applogdao = SrNSDAOFactory.getInstance()
                                                   .getRcNosegApplogDAO();
        applogdao.deleteByCond(whereCond);

        flag = "1";

        return flag;
    }

    //��ѯ������־
    public PageModel qryRcNoSegAppLog(String segId, int pi, int ps) {
        RcNosegApplogDAO pdao = SrNSDAOFactory.getInstance()
                                              .getRcNosegApplogDAO();
        PageModel pm = pdao.qryRcNoSegAppLog(segId, pi, ps);

        return pm;
    }

    //��ѯ����
    public PageModel qryRcNoSegApp(String segId, int pi, int ps) {
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        PageModel pm = pdao.qryRcNoSegAppForCheck("", "", segId, "", pi, ps);

        return pm;
    }

    //��ѯ����
    public PageModel qryRcNoSegAppForCheck(String lanId, String state,
        String segName, int pi, int ps) {
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        String segId = "";
        PageModel pm = pdao.qryRcNoSegAppForCheck(lanId, state, segId, segName,
                pi, ps);

        return pm;
    }

    //��������
    public String appAgain(RcNosegAppVO vo) {
        String flag = "";

        try {
            appAgainInsertToLog(vo, "1"); //������־��

            RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
            flag = pdao.appAgain(vo, "1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    //��������
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

    //���
    public String checkWan(RcNosegAppVO vo, String isPass) {
        String flag = "";
        String segId = vo.getSegId();

        //����Ŷα�
        RcNosegWanDAO wandao = SrNSDAOFactory.getInstance().getRcNosegWanDAO();
        wandao.checkWan(segId, isPass);

        //��������
        RcNosegAppDAO pdao = SrNSDAOFactory.getInstance().getRcNosegAppDAO();
        flag = pdao.appAgain(vo, isPass);
        //����־��
        appAgainInsertToLog(vo, isPass); //������־��

        return flag;
    }

    /**
     * ������Ŷ���Ϣ
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
        String messCode1 = " ----��Ŷκ��뷶Χ�����ص�������";
        String messCode2 = " ----��Ŷ��Ѿ����ڣ�����";
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
                    // ����ʵ��VO
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
