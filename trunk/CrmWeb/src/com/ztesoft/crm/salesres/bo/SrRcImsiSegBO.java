package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.RcImsiSegDAO;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcImsiSegVO;


public class SrRcImsiSegBO extends DictAction{
    public SrRcImsiSegBO() {
    }

    //ÐÂÔöIMSIºÅ¶Î
    public String addRcImsiSeg(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
RcImsiSegVO vo=(RcImsiSegVO) m.get("vo");
        String flag = "0";
        long lCount = 0;
        vo.setInitTime(DAOUtils.getFormatedDate());

        String whereCond = " imsi_seg_id = " + vo.getImsiSegId();
        RcImsiSegDAO pdao = SrNSDAOFactory.getInstance().getRcImsiSegDAO();
        lCount = pdao.countByCond(whereCond);

        if (lCount == 0) {
            pdao.insert(vo);
            flag = "1";
        } else {
            flag = "2";
        }

        return flag;
    }

    //²éÑ¯ºÅ¶Î
    public PageModel qryRcImsiSeg(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		int pi = ((Integer) m.get("pageIndex")).intValue();
		int ps = ((Integer) m.get("pageSize")).intValue();
		String imsiSegId = (String) m.get("imsiSegId");
		String state = (String) m.get("state");
        PageModel pm = new PageModel();
        StringBuffer sql = new StringBuffer();

        try {
            if (!"".equals(imsiSegId) && (imsiSegId != null)) {
                sql.append("imsi_seg_id like '%" + imsiSegId + "%' and ");
            }

            if ("".equals(state)) {
                sql.append("1=1");
            } else {
                sql.append("state = " + state);
            }

            RcImsiSegDAO pdao = SrNSDAOFactory.getInstance().getRcImsiSegDAO();
            pm = PageHelper.popupPageModel(pdao, sql.toString(), pi, ps);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    //É¾³ýºÅ¶Î
    public String delRcImsiSeg(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		String imsiSegId = (String) m.get("imsiSegId");
        String flag = "0";
        long lCount = 0;
        String whereCond = " imsi_seg_id = " + imsiSegId;
        RcImsiSegDAO pdao = SrNSDAOFactory.getInstance().getRcImsiSegDAO();
        lCount = pdao.findRcImsiBySegId(whereCond);

        if (lCount != 0) {
            flag = "2";
        } else {
            lCount = pdao.deleteByCond(whereCond);

            if (lCount == 1) {
                flag = "1";
            }
        }

        return flag;
    }

    //¸üÐÂºÅ¶Î
    public String updateImsiSeg(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		RcImsiSegVO vo=(RcImsiSegVO) m.get("vo");
        String flag = "0";
        vo.setInitTime(DAOUtils.getFormatedDate());

        RcImsiSegDAO pdao = SrNSDAOFactory.getInstance().getRcImsiSegDAO();
        String whereCond = " imsi_seg_id = " + vo.getImsiSegId();
        boolean bFlag = pdao.update(whereCond, vo);

        if (bFlag) {
            flag = "1";
        }

        return flag;
    }

    //
    //²éÑ¯ºÅ¶Î
    public List qryRcImsiSegById(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		String imsiSegId = (String) m.get("imsiSegId");
        List list = new ArrayList();
        String sql = "";

        try {
            sql = " imsi_seg_id = " + imsiSegId;

            RcImsiSegDAO pdao = SrNSDAOFactory.getInstance().getRcImsiSegDAO();
            list = pdao.findByCond(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
