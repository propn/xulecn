package com.ztesoft.crm.salesres.bo;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;

import com.ztesoft.crm.salesres.dao.RcLevelDefDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * RcLevelDefBO.java
 * @function:
 * 
 * @author nik
 * @since 2010-1-21
 * @modified  
 */
public class RcLevelDefBO extends DictAction {
    public RcLevelDefBO() {
    }

    public List qryFamilyLevel(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        String familyId = (String) m.get("familyId");

        if ((familyId == null) || familyId.equals("")) {
            return new ArrayList();
        }

        String sql = "SELECT distinct null as LAN_ID,FAMILY_ID,RC_LEVEL_ID,RC_LEVEL_NAME,RULE_STRING,pri_id ,LEVEL_COMMENTS,null as pre_fee,null as limit_lfee,is_lucky FROM RC_LEVEL_DEF WHERE  FAMILY_ID=?";

        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();

        return dao.findBySql(sql, new String[] { familyId });
    }

    public List qryLevelFee(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        String rcLevelId = (String) m.get("rcLevelId");

        return doQryLevelFee(rcLevelId);
    }

    private List doQryLevelFee(String rcLevelId) {
        if ((rcLevelId == null) || rcLevelId.equals("")) {
            return new ArrayList();
        }

        String sql = "SELECT LAN_ID,FAMILY_ID,RC_LEVEL_ID,RC_LEVEL_NAME,RULE_STRING,pri_id ,LEVEL_COMMENTS,pre_fee,limit_lfee,is_lucky FROM RC_LEVEL_DEF WHERE RC_LEVEL_ID=?";

        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();

        return dao.findBySql(sql, new String[] { rcLevelId });
    }

    public String addRcLevelDef(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        RcLevelDefVO vo = (RcLevelDefVO) m.get("vo");

        if (vo == null) {
            return "";
        }

        RcLevelDefVO voTemp1 = null;
        RcLevelDefVO voTemp2 = null;
        SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();

        if ((vo.getRcLevelId() == null) ||
                (vo.getRcLevelId().trim().length() < 1)) {
            SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();
            String lon = sequenceManageDAO.getNextSequence("RC_LEVEL_DEF",
                    "RC_LEVEL_ID");
            vo.setRcLevelId(lon);
        } else {
            List list1 = doQryLevelFee(vo.getRcLevelId());

            if ((list1 == null) || (list1.size() < 1)) {
                throw new DAOSystemException(
                    "--class:[RcLevelDefBO]:method[addRcLevelDef]:levelId:" +
                    vo.getRcLevelId() + "no values!");
            }

            voTemp1 = (RcLevelDefVO) list1.get(0);

            if (voTemp1 != null) {
                vo.setFamilyId(voTemp1.getFamilyId());
                vo.setIsLucky(voTemp1.getIsLucky());
                vo.setLevelComments(voTemp1.getLevelComments());
                vo.setPriId(voTemp1.getPriId());
                vo.setRcLevelName(voTemp1.getRcLevelName());
                vo.setRuleString(voTemp1.getRuleString());
            }
        }

        String lanId = vo.getLanId();
        String[] lanIds = null;
        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();

        if ((lanId != null) && !"".equals(lanId) && (lanId.indexOf(",") > 0)) { //广西
            lanIds = lanId.split(",");

            for (int i = 0; i < lanIds.length; i++) {
                vo.setLanId(lanIds[i]);
                voTemp2 = dao.findByLogicPK(vo.getRcLevelId(), vo.getLanId());

                if (voTemp2 == null) {
                    dao.insert(vo);
                }
            }
        } else {
            voTemp2 = dao.findByLogicPK(vo.getRcLevelId(), vo.getLanId());

            if (voTemp2 == null) {
                dao.insert(vo);
            }
        }

        return vo.getRcLevelId();
    }

    public String updateRcLevelDef(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        RcLevelDefVO vo = (RcLevelDefVO) m.get("vo");

        if (vo == null) {
            return "";
        }

        //String cond = " rc_level_id = "+vo.getRcLevelId();
        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();
        dao.update(vo.getFamilyId(), vo.getRcLevelId(), vo.getLanId(), vo);

        // 刷新静态数据
        return vo.getRcLevelId();
    }

    /**
     * 根据levelId修改字段,修改的字段包括：RC_LEVEL_NAME，LEVEL_COMMENTS，PRI_ID，RULE_STRING，is_lucky
     * @param vo
     * @return
     * @throws Exception
     */
    public String updateByLevelId(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        RcLevelDefVO vo = (RcLevelDefVO) m.get("vo");

        if (vo == null) {
            return "";
        }

        //String cond = " rc_level_id = "+vo.getRcLevelId();
        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();
        dao.updateByLevelId(vo.getRcLevelId(), vo);

        // 刷新静态数据
        return vo.getRcLevelId();
    }

    public String delRcLevelDef(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        RcLevelDefVO vo = (RcLevelDefVO) m.get("vo");

        if (vo == null) {
            return "";
        }

        String sql = "";
        String familyId = vo.getFamilyId();
        String rcLevelId = vo.getRcLevelId();
        String lanId = vo.getLanId();

        if ((familyId != null) && !familyId.equals("")) {
            sql += (" and d.family_id=" + familyId);
        }

        if ((rcLevelId != null) && !rcLevelId.equals("")) {
            sql += (" and d.rc_level_id=" + rcLevelId);
        }

        if ((lanId != null) && !lanId.equals("")) {
            sql += (" and d.lan_id=" + lanId);
        }

        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();
        boolean isref = dao.isLevelRef(familyId, rcLevelId);

        if (!isref) {
            return String.valueOf(SrDAOFactory.getInstance().getRcLevelDefDAO()
                                              .deleteByCond(" 1=1 " + sql));
        }

        return "-1";
    }
}
