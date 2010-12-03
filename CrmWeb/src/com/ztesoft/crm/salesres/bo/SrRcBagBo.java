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
     * 查询包规则
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
     * 新增或修改包规则
     * @param vo
     * @return 1:成功；-1：缺少参数；-2：包名重复；-3：操作员没有权限修改该包信息
     */
    public int saveNoBag(Map map) {
        if ((map == null) || (map.get("vo") == null)) {
            return -1;
        }

        RcNobagRuleVO vo = (RcNobagRuleVO) map.get("vo");
        int result = 1;
        RcNobagRuleDAO dao = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();

        // 检验包名是否重复
        String cond = " and a.BAGRULE_NAME='" + vo.getBagruleName() + "'";

        if ((vo.getBagruleId() != null) &&
                (vo.getBagruleId().trim().length() > 0)) {
            cond += (" and a.BAGRULE_ID!=" + vo.getBagruleId());
        }

        List list = dao.findByCond(cond);

        if ((list != null) && (list.size() > 0)) {
            return -2; // 包名重复
        }

        if ((vo.getBagruleId() == null) ||
                (vo.getBagruleId().trim().length() < 1)) {
            // 新增包规则
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

            // 更新号码包数量
            dao.updateNoNum(bagruleId);
        } else { // 更新
                 // 检查是否是该操作员创建的记录

            String operId = (String) map.get("operId");

            if ((vo.getOperId() != null) && (operId != null) &&
                    !operId.equals(vo.getOperId())) {
                return -3;
            }

            dao.updateByPk(vo);
            // 更新号码包数量
            dao.updateNoNum(vo.getBagruleId());
        }

        return result;
    }

    /**
     * 删除号码包规则
     * @param bagruleId
     * @param operId
     * @param nowOperId
     * @return 1:成功；-1：缺少参数；-2：操作员没有权限；-3：该记录已经被引用，不能删除
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

        // 查看该包是否被其他表引用
        // 检查表rc_nobag_no是否有引用。
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
     * 根据号码包id查找该包的详细规则
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
     * 新增号码包规则
     * @param list
     * @return
     */
    public Map addRuleDetail(List list) {
        Map map = new HashMap();

        if ((list == null) || (list.size() < 1)) {
            map.put("result", "-1");
            map.put("info", "缺少参数!");
            map.put("num", "0");
        }

        long count = 0L;
        long noNum = 0L; // 号码数量
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
        map.put("info", "成功新增" + count + "条记录!");
        map.put("num", String.valueOf(noNum));

        return map;
    }

    /**
     * 修改号码包界面上的新增号码包细则
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

        // 更新号码包数量
        RcNobagRuleDAO dao2 = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();
        dao2.updateNoNum(bagruleId);

        return 1;
    }

    /**
     * 根据条件删除号码包详细规则
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

        // 更新号码包数量
        RcNobagRuleDAO dao2 = SrNSDAOFactory.getInstance().getRcNobagRuleDAO();
        dao2.updateNoNum(bagruleId);

        return count;
    }

    /**
     * 查询号码包号码
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
     * 为包随机加入号码
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
            info = "缺少参数!";
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

            for (int i = 0; i < detailList.size(); i++) { // 对每个级别的规则细节进行处理
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
                            (arrs[i].size() < Long.parseLong(voTemp.getNoNum()))) { // 该等级的可用数量不足
                        result = "0";
                        info = "号码等级：" +
                            this.getNoLevelName(voTemp.getRescLevel()) +
                            "，在号码池中数量不足，生成失败!";
                        rtnMap.put("result", result);
                        rtnMap.put("info", info);

                        return rtnMap;
                    } else {
                        arrs[i] = util.selRandomList(arrs[i],
                                Integer.parseInt(voTemp.getNoNum()));
                        query = "";
                    }
                } else if (voTemp != null) {
                    // 该包规则细则参数缺少，错误
                    result = "0";
                    info = "包规则中有缺少等级、号码数量的规则明细，生成失败!";
                    rtnMap.put("result", result);
                    rtnMap.put("info", info);

                    return rtnMap;
                } else {
                    arrs[i] = null;
                }
            }

            // 生成号码的数量验证通过。下面是插入操作
            String nobagId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                          .getNextSequence("RC_NOBAG_NO",
                    "NOBAG_ID");
            RcNobagNoVO noVO = new RcNobagNoVO();
            RcNobagNoDAO bagNoDao = SrNSDAOFactory.getInstance()
                                                  .getRcNobagNoDAO();

            for (int j = 0; j < arrs.length; j++) { // 每一个arrs的元素都是一个级别的号码的集合

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
            info = "号码包中成功加入" + String.valueOf(numCount) + "个号码!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);
        } else {
            result = "0";
            info = "号码包没有规则明细，生成失败!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);

            return rtnMap;
        }

        return rtnMap;
    }

    /**
     * 返回号码等级名字
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
     * 查询需要删除的号码包的列表，需要统计号码数量
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
     * 删除号码包号码
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
