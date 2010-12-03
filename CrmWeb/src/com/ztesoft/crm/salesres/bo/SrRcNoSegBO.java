package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;


public class SrRcNoSegBO extends DictAction{
    //新增号段
//    public String addRcNoSeg(RcNoSegVO vo) {
    public String addRcNoSeg(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        RcNoSegVO vo  = (RcNoSegVO)map.get("vo");
        
        String noSegId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("RC_NO_SEG", "NO_SEG_ID");
        vo.setNoSegId(noSegId); //no_seg_id字段值与no_seg_name字段值一样；

        String flag = "0";
        long lCount = 0;
        long lRsCount = 0;
        String sql = "(beginn <= " + vo.getBeginn() + " and endno >= " +
            vo.getBeginn() + ") OR (beginn <= " + vo.getEndno() +
            " and endno >= " + vo.getEndno() + ")";
        String whereCond = " no_seg_name = '" + vo.getNoSegName() + "'";
        RcNoSegDAO pdao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        lRsCount = pdao.findNoSegByCond(sql);

        if (lRsCount > 0) {
            flag = "5";
        } else {
            lCount = pdao.countRsByCond(whereCond);

            if (lCount == 0) {
                pdao.insert(vo);
                flag = "1";
            } else {
                flag = "2";
            }
        }

        return flag;
    }

    //查询号段

    public PageModel qryRcNoSeg(DynamicDict dto) throws FrameException {
	  	Map m = (Map)dto.getValueByName("parameter") ;
    		
        int pi  = ((Integer)m.get("pageIndex")).intValue();
		int ps  = ((Integer)m.get("pageSize")).intValue();
		String noSegId = (String)m.get("noSegId");
		String noState = (String)m.get("noState");
		String segId = (String)m.get("segId");
        PageModel pm = new PageModel();
        String sql = " a.sales_resource_id=c.sales_resource_id and a.no_group_id = b.no_group_id ";

        try {
            if ((noSegId != null) && (noSegId.trim().length() > 0)) {
                sql += (" and a.no_seg_name like '%" + noSegId + "%' ");
            }

            if ((noState != null) && (noState.trim().length() > 0)) {
                sql += (" and a.state =" + noState + " ");
            }

            if ((segId != null) && (segId.trim().length() > 0)) {
                sql += (" and a.seg_id =" + segId + " ");
            }

            RcNoSegDAO pdao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
            pm = PageHelper.popupPageModel(pdao, sql, pi, ps);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    //删除号段
//    public String delRcNoSeg(Map map) {
    public String delRcNoSeg(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        String noSegId = (String) map.get("noSegId");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        String flag = "0";

        // 检查号段下是否有号码，如果有则不允许删除
        RcNoDAO daoNo = SrNSDAOFactory.getInstance().getRcNoDAO();
        long noNum = daoNo.countByCond("  and a.no_seg_id=" + noSegId);

        if (noNum > 0) {
            flag = "3";
        }

        // 检查号段是否已启用
        String lCount = "";
        String whereCond = " no_seg_id = " + noSegId;
        RcNoSegDAO pdao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        lCount = pdao.findStateByCond(whereCond);

        if ("2".equals(lCount)) {
            flag = "2";
        } else if (!"2".equals(flag) && !"3".equals(flag)) {
            // 插入相关通用日至
            RcNoSegVO vo_old = null;
            RcPublicLogDAO logDao = SrDAOFactory.getInstance()
                                                .getRcPublicLogDAO();
            vo_old = pdao.findByPrimaryKey(noSegId);

            RcPublicLogVO logVO = new RcPublicLogVO();
            logVO.setAct("D");
            logVO.setReworkTime(DAOUtils.getFormatedDate());
            logVO.setReworkTable("rc_no_seg");
            logVO.setReworkWen(operId);
            logVO.setReworkIp(reworkIp);
            logDao.logVO(logVO, vo_old, null);
            // 删除相关号段
            pdao.deleteByCond(whereCond);
            flag = "1";
        }

        return flag;
    }

    //更新号段
//    public String updateNoSeg(Map map) {
    public String updateNoSeg(DynamicDict dto) throws FrameException {
            
        Map map = (Map)dto.getValueByName("parameter");
        RcNoSegVO vo = (RcNoSegVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        String oldNoSegId = vo.getNoSegId(); //取得将要更新的记录的ID
                                             //vo.setNoSegId(vo.getNoSegName()); //no_seg_id字段值与no_seg_name字段值一样；

        String flag = "0";
        long lCount = 0;
        long lRsCount = 0;
        String sql = "((beginn <= " + vo.getBeginn() + " and endno >= " +
            vo.getBeginn() + ") OR (beginn <= " + vo.getEndno() +
            " and endno >= " + vo.getEndno() + ")) and no_seg_id != " +
            oldNoSegId;

        RcNoSegDAO pdao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        String whereCond = " no_seg_name = '" + vo.getNoSegName() +
            "' and NO_SEG_ID!=" + oldNoSegId;
        lRsCount = pdao.findNoSegByCond(sql);

        if (lRsCount > 0) {
            flag = "5";
        } else {
            lCount = pdao.countRsByCond(whereCond);

            if (lCount == 0) {
                RcNoSegVO vo_old = pdao.findByPrimaryKey(vo.getNoSegId());
                boolean bFlag = pdao.update(oldNoSegId, vo);

                if (bFlag) {
                    flag = "1";

                    // 插入相关通用日至
                    RcPublicLogDAO logDao = SrDAOFactory.getInstance()
                                                        .getRcPublicLogDAO();
                    RcPublicLogVO logVO = new RcPublicLogVO();
                    logVO.setAct("M");
                    logVO.setReworkTime(DAOUtils.getFormatedDate());
                    logVO.setReworkTable("rc_no_seg");
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

    //
    //查询号段
//    public List qryRcNoSegBySeg(String noSegId, String noState) {
    public List qryRcNoSegBySeg(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String noSegId = (String)map.get("noSegId");
        String noState = (String)map.get("noState");
        
        List list = new ArrayList();
        String sql = "";

        //noSegId实际上为noSegName
        try {
            sql = " a.no_seg_id = " + noSegId +
                " and a.sales_resource_id = c.sales_resource_id and a.no_group_id = b.no_group_id and a.state = " +
                noState + "";

            RcNoSegDAO pdao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
            list = pdao.findByCond(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
