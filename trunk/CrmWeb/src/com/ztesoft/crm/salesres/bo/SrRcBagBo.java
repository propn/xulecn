package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.CollectionUtil;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcLevelDefDAO;
import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.dao.RcNobagNoDAO;
import com.ztesoft.crm.salesres.dao.RcNobagRuleDAO;
import com.ztesoft.crm.salesres.dao.RcNobagRuleDetaDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;
import com.ztesoft.crm.salesres.vo.RcNobagNoVO;
import com.ztesoft.crm.salesres.vo.RcNobagRuleDetaVO;
import com.ztesoft.crm.salesres.vo.RcNobagRuleVO;


public class SrRcBagBo extends DictAction {
    /**
     * ��ѯ������
     * @param map
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryNoBagRule(Map map, int pi, int ps) {
        RcNobagRuleDAO dao = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();
        PageModel pm = new PageModel();
        String cond = "";

        if (map != null) {
            if ((map.get("lanId") != null) && !map.get("lanId").equals("")) {
                cond += (" and a.LAN_ID=" + map.get("lanId").toString().trim());
            }

            if ((map.get("bagruleName") != null) &&
                    !map.get("bagruleName").equals("")) {
                String bagruleName = map.get("bagruleName").toString();
                cond += (" and a.BAGRULE_NAME like '%" +
                DAOUtils.filterQureyCond(bagruleName) + "%'");
            }

            if ((map.get("operId") != null) && !map.get("operId").equals("")) {
                cond += (" and a.OPER_ID=" +
                map.get("operId").toString().trim());
            }
        }

        pm = PageHelper.popupPageModel(dao, cond, pi, ps);

        return pm;
    }

    /**
     * �������޸İ�����
     * @param vo
     * @return 1:�ɹ���-1��ȱ�ٲ�����-2�������ظ���-3������Աû��Ȩ���޸ĸð���Ϣ
     */
    public int saveNoBag(Map map) {
        if ((map == null) || (map.get("vo") == null)) {
            return -1;
        }

        RcNobagRuleVO vo = (RcNobagRuleVO) map.get("vo");
        int result = 1;
        RcNobagRuleDAO dao = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();

        // ��������Ƿ��ظ�
        String cond = " and a.BAGRULE_NAME='" + vo.getBagruleName() + "'";

        if ((vo.getBagruleId() != null) &&
                (vo.getBagruleId().trim().length() > 0)) {
            cond += (" and a.BAGRULE_ID!=" + vo.getBagruleId());
        }

        List list = dao.findByCond(cond);

        if ((list != null) && (list.size() > 0)) {
            return -2; // �����ظ�
        }

        if ((vo.getBagruleId() == null) ||
                (vo.getBagruleId().trim().length() < 1)) {
            // ����������
            String bagruleId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                            .getNextSequence("RC_NOBAG_RULE",
                    "BAGRULE_ID");
            vo.setBagruleId(bagruleId);

            String nowDate = DAOUtils.getFormatedDate();
            vo.setCDate(nowDate);
            dao.insert(vo);

            if ((vo.getDetailList() != null) &&
                    (vo.getDetailList().size() > 0)) {
                RcNobagRuleDetaVO detailVO = null;

                for (int i = 0; i < vo.getDetailList().size(); i++) {
                    detailVO = (RcNobagRuleDetaVO) vo.getDetailList().get(i);
                    detailVO.setBagruleId(bagruleId);
                }

                this.addRuleDetail(vo.getDetailList());
            }

            // ���º��������
            dao.updateNoNum(bagruleId);
        } else { // ����
                 // ����Ƿ��Ǹò���Ա�����ļ�¼

            String operId = (String) map.get("operId");

            if ((vo.getOperId() != null) && (operId != null) &&
                    !operId.equals(vo.getOperId())) {
                return -3;
            }

            dao.updateByPk(vo);
            // ���º��������
            dao.updateNoNum(vo.getBagruleId());
        }

        return result;
    }

    /**
     * ɾ�����������
     * @param bagruleId
     * @param operId
     * @param nowOperId
     * @return 1:�ɹ���-1��ȱ�ٲ�����-2������Աû��Ȩ�ޣ�-3���ü�¼�Ѿ������ã�����ɾ��
     */
    public int deleteNoBagRule(String bagruleId, String operId, String nowOperId) {
        if ((bagruleId == null) || (bagruleId.trim().length() < 1) ||
                (operId == null) || (operId.trim().length() < 1) ||
                (nowOperId == null) || (nowOperId.trim().length() < 1)) {
            return -1;
        }

        if (!operId.equals(nowOperId)) {
            return -2;
        }

        // �鿴�ð��Ƿ�����������
        // ����rc_nobag_no�Ƿ������á�
        RcNobagNoDAO bagDao = SrNSDAOFactory.getInstance().getRcNobagNoDAO();
        long count = bagDao.countByCond(" and a.BAGRULE_ID=" + bagruleId);

        if (count > 0) {
            return -3;
        }

        RcNobagRuleDAO dao = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();
        RcNobagRuleDetaDAO dao2 = SrNSDAOFactory.getInstance()
                                                .getRcNobagRuleDetaDAO();
        dao2.deleteByCond(" BAGRULE_ID=" + bagruleId);
        dao.deleteByCond(" BAGRULE_ID=" + bagruleId);

        return 1;
    }

    /**
     * ���ݺ����id���Ҹð�����ϸ����
     * @param bagruleId
     * @return
     */
    public List qryNoBagRuleDetailList(String bagruleId) {
        if ((bagruleId == null) || (bagruleId.trim().length() < 1)) {
            return new ArrayList();
        }

        RcNobagRuleDetaDAO dao = SrNSDAOFactory.getInstance()
                                               .getRcNobagRuleDetaDAO();
        String cond = " BAGRULE_ID = " + bagruleId;
        List list = dao.findByCond(cond);

        return list;
    }

    /**
     * �������������
     * @param list
     * @return
     */
    public Map addRuleDetail(List list) {
        Map map = new HashMap();

        if ((list == null) || (list.size() < 1)) {
            map.put("result", "-1");
            map.put("info", "ȱ�ٲ���!");
            map.put("num", "0");
        }

        long count = 0L;
        long noNum = 0L; // ��������
        RcNobagRuleDetaDAO dao = SrNSDAOFactory.getInstance()
                                               .getRcNobagRuleDetaDAO();
        RcNobagRuleDetaVO vo = null;

        for (int i = 0; i < list.size(); i++) {
            vo = (RcNobagRuleDetaVO) list.get(i);

            if ((vo != null) && (vo.getBagruleId() != null) &&
                    (vo.getBagruleId().trim().length() > 0) &&
                    (vo.getRescLevel() != null) &&
                    (vo.getRescLevel().trim().length() > 0)) {
                dao.insert(vo);
                count++;
                noNum += Long.parseLong(vo.getNoNum());
            }
        }

        map.put("result", "1");
        map.put("info", "�ɹ�����" + count + "����¼!");
        map.put("num", String.valueOf(noNum));

        return map;
    }

    /**
     * �޸ĺ���������ϵ����������ϸ��
     * @param bagruleId
     * @param rescLevel
     * @param noNum
     * @return
     */
    public int addRuleDetailNum(String bagruleId, String rescLevel, String noNum) {
        if ((bagruleId == null) || (bagruleId.trim().length() < 1) ||
                (rescLevel == null) || (rescLevel.trim().length() < 1) ||
                (noNum == null) || (noNum.trim().length() < 1)) {
            return -1;
        }

        RcNobagRuleDetaVO vo = new RcNobagRuleDetaVO();
        vo.setBagruleId(bagruleId);
        vo.setRescLevel(rescLevel);
        vo.setNoNum(noNum);

        RcNobagRuleDetaDAO dao = SrNSDAOFactory.getInstance()
                                               .getRcNobagRuleDetaDAO();
        dao.insert(vo);

        // ���º��������
        RcNobagRuleDAO dao2 = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();
        dao2.updateNoNum(bagruleId);

        return 1;
    }

    /**
     * ��������ɾ���������ϸ����
     * @param bagruleId
     * @param rescLevel
     * @return
     */
    public long delRuleDetail(String bagruleId, String rescLevel) {
        if ((bagruleId == null) || (bagruleId.trim().length() < 1)) {
            return -1;
        }

        long count = 0L;
        RcNobagRuleDetaDAO dao = SrNSDAOFactory.getInstance()
                                               .getRcNobagRuleDetaDAO();
        String cond = " BAGRULE_ID=" + bagruleId.trim();

        if ((rescLevel != null) && (rescLevel.trim().length() > 0)) {
            cond += (" and RESOURCE_LEVEL=" + rescLevel);
        }

        count = dao.deleteByCond(cond);

        // ���º��������
        RcNobagRuleDAO dao2 = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();
        dao2.updateNoNum(bagruleId);

        return count;
    }

    /**
     * ��ѯ���������
     * @param map
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryNoBagNo(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
		int pi  = ((Integer)map.get("pageIndex")).intValue();
		int ps  = ((Integer)map.get("pageSize")).intValue();
        RcNobagNoDAO dao = SrNSDAOFactory.getInstance().getRcNobagNoDAO();
        PageModel pm = new PageModel();
        String cond = "";

        if (map != null) {
            if ((map.get("bagruleId") != null) &&
                    !map.get("bagruleId").equals("")) {
                String bagruleId = map.get("bagruleId").toString().trim();
                cond += (" and a.BAGRULE_ID=" +
                DAOUtils.filterQureyCond(bagruleId));
            }

            if ((map.get("rescInstanceCode") != null) &&
                    !map.get("rescInstanceCode").equals("")) {
                String rescInstanceCode = map.get("rescInstanceCode").toString();
                cond += (" and a.RESOURCE_INSTANCE_CODE ='" +
                DAOUtils.filterQureyCond(rescInstanceCode) + "'");
            }

            if ((map.get("nobagId") != null) && !map.get("nobagId").equals("")) {
                String nobagId = map.get("nobagId").toString();
                cond += (" and a.NOBAG_ID=" +
                DAOUtils.filterQureyCond(nobagId));
            }
        }

        pm = PageHelper.popupPageModel(dao, cond, pi, ps);

        return pm;
    }

    /**
     * Ϊ������������
     * @param paraMap
     * @return
     */
    public Map geneNoBagNo(Map paraMap) {
        Map rtnMap = new HashMap();
        String result = "1";
        String info = "";

        if ((paraMap == null) || (paraMap.get("storageId") == null) ||
                (paraMap.get("bagruleId") == null)) {
            result = "-1";
            info = "ȱ�ٲ���!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);

            return rtnMap;
        }

        String databaseType = CrmParamsConfig.getInstance()
                                             .getParamValue("DATABASE_TYPE");
        int numCount = 0;
        String storageId = (String) paraMap.get("storageId");
        String bagruleId = (String) paraMap.get("bagruleId");
        RcNobagRuleDetaVO voTemp = null;
        CollectionUtil util = new CollectionUtil();
        List detailList = this.qryNoBagRuleDetailList(bagruleId);

        if ((detailList != null) && (detailList.size() > 0)) {
            SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
            long noNum = 0;
            List[] arrs = new List[detailList.size()];
            String query1 = " a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE from rc_no a where " +
                " a.RESOURCE_STATE='" + ParamsConsConfig.RcEntityFreeState +
                "' " + " and a.STORAGE_ID = " + storageId + " ";
            String query2 = " and not exists(select * from rc_nobag_no b where a.RESOURCE_INSTANCE_ID=b.RESOURCE_INSTANCE_ID)";
            String query = "";
            String[] paraArrs = new String[] {
                    "RESOURCE_INSTANCE_ID", "RESOURCE_INSTANCE_CODE"
                };

            for (int i = 0; i < detailList.size(); i++) { // ��ÿ������Ĺ���ϸ�ڽ��д���
                voTemp = (RcNobagRuleDetaVO) detailList.get(i);

                if ((voTemp != null) && (voTemp.getRescLevel() != null) &&
                        (voTemp.getRescLevel().length() > 0) &&
                        (voTemp.getNoNum() != null) &&
                        (voTemp.getNoNum().length() > 0)) {
                    noNum = Long.parseLong(voTemp.getNoNum()) * 6;

                    if (!"INFORMIX".equals(databaseType)) {
                        query = "select " + query1 + " and a.RESOURCE_LEVEL=" +
                            voTemp.getRescLevel() + " and rownum<=" +
                            String.valueOf(noNum) + query2;
                    } else {
                        query = "select first " + String.valueOf(noNum) +
                            query1 + " and a.RESOURCE_LEVEL=" +
                            voTemp.getRescLevel() + query2;
                    }

                    arrs[i] = comDao.qryComSql(query, paraArrs);

                    if ((arrs[i] != null) &&
                            (arrs[i].size() < Long.parseLong(voTemp.getNoNum()))) { // �õȼ��Ŀ�����������
                        result = "0";
                        info = "����ȼ���" +
                            this.getNoLevelName(voTemp.getRescLevel()) +
                            "���ں�������������㣬����ʧ��!";
                        rtnMap.put("result", result);
                        rtnMap.put("info", info);

                        return rtnMap;
                    } else {
                        arrs[i] = util.selRandomList(arrs[i],
                                Integer.parseInt(voTemp.getNoNum()));
                        query = "";
                    }
                } else if (voTemp != null) {
                    // �ð�����ϸ�����ȱ�٣�����
                    result = "0";
                    info = "����������ȱ�ٵȼ������������Ĺ�����ϸ������ʧ��!";
                    rtnMap.put("result", result);
                    rtnMap.put("info", info);

                    return rtnMap;
                } else {
                    arrs[i] = null;
                }
            }

            // ���ɺ����������֤ͨ���������ǲ������
            String nobagId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                          .getNextSequence("RC_NOBAG_NO",
                    "NOBAG_ID");
            RcNobagNoVO noVO = new RcNobagNoVO();
            RcNobagNoDAO bagNoDao = SrNSDAOFactory.getInstance()
                                                  .getRcNobagNoDAO();

            for (int j = 0; j < arrs.length; j++) { // ÿһ��arrs��Ԫ�ض���һ������ĺ���ļ���

                if ((arrs[j] != null) && (arrs[j].size() > 0)) {
                    for (int k = 0; k < arrs[j].size(); k++) {
                        Map tempMap = (Map) arrs[j].get(k);

                        if (tempMap != null) {
                            noVO.setBagruleId(bagruleId);
                            noVO.setNobagId(nobagId);
                            noVO.setRescInstanceId((String) tempMap.get(
                                    "RESOURCE_INSTANCE_ID"));
                            noVO.setRescInstanceCode((String) tempMap.get(
                                    "RESOURCE_INSTANCE_CODE"));
                            bagNoDao.insert(noVO);
                            numCount++;
                        }
                    }
                }
            }

            result = "1";
            info = "������гɹ�����" + String.valueOf(numCount) + "������!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);
        } else {
            result = "0";
            info = "�����û�й�����ϸ������ʧ��!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);

            return rtnMap;
        }

        return rtnMap;
    }

    /**
     * ���غ���ȼ�����
     * @param rcLevelId
     * @return
     */
    public String getNoLevelName(String rcLevelId) {
        String name = "";
        RcLevelDefDAO dao = SrDAOFactory.getInstance().getRcLevelDefDAO();
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        String familyId = segDao.findNoFamilyId();
        RcLevelDefVO vo = dao.findByPrimaryKey(familyId, rcLevelId);

        if (vo != null) {
            name = vo.getRcLevelName();
        }

        return name;
    }

    /**
     * ��ѯ��Ҫɾ���ĺ�������б���Ҫͳ�ƺ�������
     * @param bagruleId
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryNoBagByRule(String bagruleId, int pi, int ps) {
        PageModel pm = new PageModel();

        if ((bagruleId == null) || (bagruleId.trim().length() < 1)) {
            return pm;
        }

        RcNobagNoDAO dao = SrNSDAOFactory.getInstance().getRcNobagNoDAO();
        String sql = "select NOBAG_ID," + bagruleId +
            " as BAGRULE_ID,null as RESOURCE_INSTANCE_ID,null as RESOURCE_INSTANCE_CODE,'' as BAGRULE_NAME,count(RESOURCE_INSTANCE_ID) as noNum " +
            " from rc_nobag_no where BAGRULE_ID=" + bagruleId +
            " group by NOBAG_ID ";
        String sql_count = "select count(NOBAG_ID) as COL_COUNTS from rc_nobag_no where BAGRULE_ID=" +
            bagruleId + " group by NOBAG_ID ";
        dao.setSQL_SELECT(sql);
        dao.setSQL_SELECT_COUNT(sql_count);
        dao.setFlag(1);
        pm = PageHelper.popupPageModel(dao, "", pi, ps);

        List list = pm.getList();

        if ((list != null) && (list.size() > 0)) {
            RcNobagRuleVO vo = null;
            RcNobagNoVO bagNo = null;
            RcNobagRuleDAO ruleDao = SrNSDAOFactory.getInstance()
                                                   .getRcNobagRuleDAO();
            vo = ruleDao.findByPk(bagruleId);

            for (int i = 0; i < list.size(); i++) {
                bagNo = (RcNobagNoVO) list.get(i);

                if ((bagNo != null) && (vo != null)) {
                    bagNo.setBagruleName(vo.getBagruleName());
                }
            }
        }

        return pm;
    }

    /**
     * ɾ�����������
     * @param nobagId
     * @return
     */
    public long delNoBag(String nobagId) {
        if ((nobagId == null) || (nobagId.trim().length() < 1)) {
            return -1;
        }

        RcNobagNoDAO dao = SrNSDAOFactory.getInstance().getRcNobagNoDAO();
        String cond = " NOBAG_ID = " + nobagId;
        long count = dao.deleteByCond(cond);

        return count;
    }
}
