package com.ztesoft.crm.salesres.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.bo.RcNoBo;
import com.ztesoft.crm.salesres.bo.RcNoConfigBo;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcNoRecRuleVO;
import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;
import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.crm.salesres.vo.RcNobagRuleDetaVO;
import com.ztesoft.crm.salesres.vo.RcNobagRuleVO;
import com.ztesoft.crm.salesres.vo.RnNumberPreVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


/**
 * SrRcNoService.java
 * @function: 
 * 
 * @author nik
 * @since 2010-1-13
 * @modified  
 */
public class SrRcNoService extends DictService{
    public SrRcNoService() {
    }

    public String getOperId() throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        return operId;
    }

    /**
     * 根据号码的家族id，得到号码类型的所有号段集合
     * @param familyId String
     * @return PageModel
     */
    public PageModel qryRcNoSegByFamilyId(String noSegName, String lanId,
        int pi, int ps) throws Exception {
        Map map = new HashMap();
        map.put("noSegName", noSegName);
        map.put("lanId", lanId);
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));

        PageModel result = (PageModel)ServiceManager.callJavaBeanService("RcNoBO","qryRcNoSegByFamilyId" , map) ;
		return result;
    }

    /**
     * 根据界面条件查询号码
     * @param map Map
     * @param pi int
     * @param ps int
     * @return PageModel
     */
    public PageModel qryRcNo(String localLanId, String startCode,
        String endCode, String rescInstanceCode, String rescState,
        String rescLevel, String noSegId, String storageId, String lanId,
        String regionId, String exchId, String qryType, String searchContent,
        int pi, int ps) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);

        Map map = new HashMap();
        map.put("localLanId", localLanId);
        map.put("startCode", startCode);
        map.put("endCode", endCode);
        map.put("rescInstanceCode", rescInstanceCode);
        map.put("rescState", rescState);
        map.put("rescLevel", rescLevel);
        map.put("noSegId", noSegId);
        map.put("storageId", storageId);
        map.put("operId", operId);
        map.put("departId", departId);
        map.put("lanId", lanId);
        map.put("regionId", regionId);
        map.put("exchId", exchId);
        map.put("qryType", qryType);
        map.put("provId", provId);
        map.put("searchContent", searchContent);
        
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("RcNoBO","qryRcNo" ,map) ;
		return result;
        //pm = bean.qryRcNo(map, pi, ps);
        //return pm;
    }

    /**
     * 根据界面条件查询号码,针对广西--号码查询菜单
     * @param map Map
     * @param pi int
     * @param ps int
     * @return PageModel
     */
    public PageModel qryRcNo2_guangxi(String startCode, String endCode, int pi,
        int ps) throws Exception {
        PageModel pm = new PageModel();
        Map map = new HashMap();
        map.put("startCode", startCode);
        map.put("endCode", endCode);

        RcNoBo bo = new RcNoBo();
        pm = bo.qryRcNo2(map, pi, ps);

        return pm;
    }

    /**
     * 增加一个号码，返回新增号码的主键，如果不成功则返回-1；
     * @param vo RcNoVO
     * @return String
     */
    public String addRcNo(RcNoVO vo) throws Exception {
    	Map map = new HashMap();
    	map.put("vo", vo);
    	String result = (String)ServiceManager.callJavaBeanService("RcNoBO","addRcNo" ,map) ;
		return result;
    }

    /**
     * 更新一个号码
     * @param vo RcNoVO
     * @return boolean
     */
    public String updateNo(RcNoVO vo) throws Exception {

        Map map = new HashMap();
        map.put("vo", vo);

        String reworkIp = RequestContext.getContext().getHttpRequest().getRemoteAddr();
        reworkIp = reworkIp == null?"":reworkIp; 
                                                      
                                                             
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String operCode = this.getGlobalVar(GlobalVariableHelper.OPER_CODE);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);
        map.put("operCode", operCode);

        String result = (String)ServiceManager.callJavaBeanService("RcNoBO","updateNo" ,map) ;
		return result;
    }

    /**
     * 按号段更新号码状态
     * @param vo RcNoVO
     * @return boolean
     */
    public Map updateNosState(RcNoVO vo, String begNo, String endNo,
        String checkType, String states) throws Exception {
        long begInt = Long.parseLong(begNo.trim());
        long endInt = Long.parseLong(endNo.trim());
        long range = endInt - begInt;

        if ((endInt < begInt) || (range > 3000)) {
            return null;
        }

        String nos = "";

        for (int i = 0; i <= range; i++) {
            nos += (String.valueOf(begInt + i) + ",");
        }

        nos = nos.substring(0, nos.length() - 1);

        return updateNoState(vo, nos, checkType, states);
    }

    public String getStates() throws Exception {
        String provId = "";
        String states = "";
        String statesInfo = "";
        String ret = "";
        provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);

        String selStates = "select pkey,pname from dc_public where stype = '94910' and codea = '" +
            provId + "'";
        SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
        List list = comdao.qryComSql(selStates, new String[] { "pkey", "pname" });

        if ((list != null) && (list.size() >= 0)) {
            for (int i = 0; i < list.size(); i++) {
                HashMap hm = (HashMap) list.get(i);

                if (hm == null) {
                    continue;
                }

                states += ("'" + (String) hm.get("pkey") + "'" + ",");
                statesInfo += ((String) hm.get("pname") + "、");
            }
        }

        if ((states != null) && (states.length() > 0)) {
            states = states.substring(0, states.length() - 1);
        } else {
            states = "'000'";
        }

        if ((statesInfo != null) && (statesInfo.length() > 0)) {
            statesInfo = statesInfo.substring(0, statesInfo.length() - 1);
            statesInfo = "系统对文件里是的号码进行判断，" + statesInfo + "状态的号码不允许修改状态。";
        } else {
            statesInfo = "0";
        }

        ret = provId + "@" + states + "@" + statesInfo;

        return ret;
    }

    /**
     * 更新号码状态
     * @param vo RcNoVO
     * @return boolean
     */
    public Map updateNoState(RcNoVO vo, String nos, String checkType,
        String states) throws Exception {

        Map map = new HashMap();
        map.put("vo", vo);

        String operCode = vo.getOperCode();
        String operId = vo.getOperId();
        String departId = "";
        String[] strs = operId.split("@");

        if ((strs != null) && (strs.length == 2)) {
            operId = strs[0];
            departId = strs[1];
        }

        if ((operCode == null) || (operCode.length() < 1)) {
            operCode = this.getGlobalVar(GlobalVariableHelper.OPER_CODE);
        }

        if ((operId == null) || (operId.length() < 1)) {
            operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        }

        if ((departId == null) || (departId.length() < 1)) {
            departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        }

        map.put("operCode", operCode);
        map.put("departId", departId);
        map.put("operId", operId);
        map.put("checkType", checkType);
        map.put("states", states);

        String[] noArr = null;

        if ((nos != null) && (nos.trim().length() > 0)) {
            noArr = nos.split(",");
        }

        map.put("noArr", noArr);

        //Map rtnMap1 = bean.updateNoState(map);
        Map rtnMap1 = (HashMap) ServiceManager.callJavaBeanService("RcNoBO","updatMultiNoState" ,map) ;
        Map rtnMap2 = new HashMap();
        String result = (String) rtnMap1.get("result");
        String info = "系统异常";
        String errTip1 = "号码不存在,或者工号没权限，或者已被业务前台使用";
        String errTip2 = "号码不存在,或者工号没权限，或状态配置为不可修改,或已被业务前台使用";
        StringBuffer failNos = new StringBuffer();

        if ((result != null) && "1".equals(result)) {
            if (rtnMap1.get("failList") != null) {
                List rtnList = (List) rtnMap1.get("failList");

                if (rtnList.size() > 0) {
                    rtnMap2.put("result", "0");
                    info = "成功修改" + (noArr.length - rtnList.size()) + "个号码，失败" +
                        rtnList.size() + "个号码，出错号码请见界面出错信息!";

                    for (int i = 0; i < rtnList.size(); i++) {
                        failNos.append((String) rtnList.get(i) + "  --  ");

                        if ((checkType != null) && "1".equals(checkType)) {
                            failNos.append(errTip2);
                        } else {
                            failNos.append(errTip1);
                        }

                        failNos.append(";\n");
                    }

                    rtnMap2.put("failNos", failNos.toString());
                } else {
                    rtnMap2.put("result", "1");
                    info = "成功修改" + noArr.length + "个号码，无失败号码!";
                }
            } else {
                rtnMap2.put("result", "1");
                info = "成功修改" + noArr.length + "个号码，无失败号码!";
            }
        } else {
            rtnMap2.put("result", "-1");
        }

        rtnMap2.put("info", info);

        return rtnMap2;
    }

    /**
     * 删除号码,支持多个号码的删除,号码间以","隔开
     * @param vo RcNoVO
     * @return boolean
     */
    public long delNo(String pks) throws Exception {
        Map map = new HashMap();

        String reworkIp = RequestContext.getContext().getHttpRequest().getRemoteAddr();
        reworkIp = reworkIp ==null?"":reworkIp; 
        	
                                                             
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);
        map.put("pks", pks);
        Long result = (Long)ServiceManager.callJavaBeanService("RcNoBO","delNo" , map) ;
		return result.longValue();
    }

    /**
     * 根据界面的按号段方式生成号码
     * @param map Map
     * @return int
     */
    public Map geneNos(RcNoVO vo, String beginno, String endno)
        throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        Map map = new HashMap();
        map.put("No", vo);
        map.put("beginno", beginno);
        map.put("endno", endno);
        map.put("operId", operId);
        map.put("departId", departId);      

        Map result = (Map)ServiceManager.callJavaBeanService("RcNoBO","geneNos" , map) ;
		return result;

    }

    /**
     * 根据文件上传方式生成号码
     * @param vo RcNoVO
     * @param list List
     * @return String
     */
    public Map geneNosFromFile(RcNoVO vo, List list) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        Map map = new HashMap();
        map.put("No", vo);
        map.put("NoList", list);
        map.put("operId", operId);
        map.put("departId", departId);
        Map result = (Map)ServiceManager.callJavaBeanService("RcNoBO","geneNosFromFile" , map) ;
		return result;
    }

    /**
     * 分配号码
     * @param map Map
     * @return Map
     */
    public Map deliverNos(RcNoVO vo, String deliverType, String beginno,
        String endno) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        Map map = new HashMap();
        map.put("deliverType", deliverType);
        map.put("No", vo);
        map.put("beginno", beginno);
        map.put("endno", endno);
        map.put("operId", operId);
        map.put("departId", departId);

        Map result = (Map)ServiceManager.callJavaBeanService("RcNoBO","deliverNos" , map) ;
		return result;

    }

    /**
     * 回收号码,支持多个号码的回收,号码间以","隔开
     * @param vo RcNoVO
     * @return
     */
    public Map recycleNo(String pks) throws Exception {
        Map map = new HashMap();

        map.put("pks", pks);

        Map retMap = null;

        try {
            retMap =  (Map)ServiceManager.callJavaBeanService("RcNoBO","recycleNo" , map) ;
        } catch (Exception e) {
            e.printStackTrace();

            if (e.getMessage().indexOf("<info:begin>") != -1) {
                int ind1 = e.getMessage().indexOf("<info:begin>");
                int ind2 = e.getMessage().indexOf("<info:end>");
                String message = e.getMessage()
                                  .substring(ind1 + "<info:begin>".length(),
                        ind2);
                retMap = new HashMap();
                retMap.put("result", "0");
                retMap.put("message", message);
            } else {
                throw e;
            }
        }

        return retMap;
    }

    /**
     * 冻结号码
     * @param map Map
     * @return Map
     */
    public Map freezeNos(String storageId, String rescLevel, String noNumber,
        String noSegId, String deliverType, String beginno, String endno)
        throws Exception {
        Map map = new HashMap();
        map.put("storageId", storageId);
        map.put("rescLevel", rescLevel);
        map.put("noNumber", noNumber);
        map.put("noSegId", noSegId);
        map.put("deliverType", deliverType);
        map.put("beginno", beginno);
        map.put("endno", endno);
        Map result = (Map)ServiceManager.callJavaBeanService("RcNoBO","deliverNos" , map) ;
		return result;

    }

    /**
     * 解冻号码
     * @param map Map
     * @return Map
     */
    public Map deFreezeNos(String noSegId, String deliverType, String beginno,
        String endno) throws Exception {
        Map map = new HashMap();
        map.put("noSegId", noSegId);
        map.put("deliverType", deliverType);
        map.put("beginno", beginno);
        map.put("endno", endno);
        Map result = (Map)ServiceManager.callJavaBeanService("RcNoBO","deFreezeNos" , map) ;
		return result;

    }

    public String recImsi() throws Exception {
    	Map map = new HashMap();
    	String result = (String)ServiceManager.callJavaBeanService("SrRcImsiBO","recImsi" , map) ;
		return result;
    }

    /**
     * 号码库存盘点，根据条件查询号码的实例数量，
     * @param
     * @return
     */
    public PageModel satNoStockNum(String departId, String regionId,
        String rescState, String rescLevel, String balaMode,
        String upStorageId, String storageId, int pi, int ps)
        throws Exception {
        Map map = new HashMap();
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        // 所有系统在界面都只按仓库查询，不再按营业区部门查询
        //regionId其实是lanId，针对广西RC-296 (ID:41564)
        departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);

        if ((departId != null) && (departId.trim().length() > 0)) {
            map.put("departId", departId);
        }

        if ((regionId != null) && (regionId.trim().length() > 0)) {
            if (!"undefined".equals(regionId)) {
                map.put("regionId", regionId);
            }
        }

        if ((operId != null) && (operId.trim().length() > 0)) {
            map.put("operId", operId);
        }

        if ((rescState != null) && (rescState.trim().length() > 0)) {
            map.put("rescState", rescState);
        }

        if ((rescLevel != null) && (rescLevel.trim().length() > 0)) {
            map.put("rescLevel", rescLevel);
        }

        if ((balaMode != null) && (balaMode.trim().length() > 0)) {
            map.put("balaMode", balaMode);
        }

        /*if (startDate != null && startDate.trim().length() > 0)
           map.put("startDate", startDate);
        if (endDate != null && endDate.trim().length() > 0)
           map.put("endDate", endDate);*/
        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
            map.put("upStorageId", upStorageId);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        if ((provId != null) && (provId.trim().length() > 0)) {
            map.put("provId", provId);
        }

        map.put("tableName", "rc_no");

        //*****************************
        map.put("lanId", lanId);
        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));

        RcNoBo bean = new RcNoBo();
        PageModel pm = bean.satNoSimStockNum(map, pi, ps);

        return pm;
    }

    /**
     * 根据条件查询sim卡的实例数量，目前可以接受的参数有：deptID、rescState、state、tableName
     * @param deptID:必须是id1,id2,id3的形式
     * @return
     */
    public PageModel satSimStockNum(String departId, String regionId,
        String rescState, String startDate, String endDate, String upStorageId,
        String storageId, int pi, int ps) throws Exception {
        Map map = new HashMap();
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        //     所有系统在界面都只按仓库查询，不再按营业区部门查询
        departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);

        if ((departId != null) && (departId.trim().length() > 0)) {
            map.put("departId", departId);
        }

        if ((regionId != null) && (regionId.trim().length() > 0)) {
            map.put("regionId", regionId);
        }

        if ((operId != null) && (operId.trim().length() > 0)) {
            map.put("operId", operId);
        }

        if ((rescState != null) && (rescState.trim().length() > 0)) {
            map.put("rescState", rescState);
        }

        if ((startDate != null) && (startDate.trim().length() > 0)) {
            map.put("startDate", startDate);
        }

        if ((endDate != null) && (endDate.trim().length() > 0)) {
            map.put("endDate", endDate);
        }

        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
            map.put("upStorageId", upStorageId);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        map.put("tableName", "rc_sim");

        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
        //*****************************
        map.put("lanId", lanId);
        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));

        RcNoBo bean = new RcNoBo();

        return bean.satNoSimStockNum(map, pi, ps);
    }

    public String addRcNoPre(RnNumberPreVO vo) throws Exception {

        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
        vo.setLanId(lanId);
        Map map = new HashMap();
        map.put("vo", vo);
        String result = (String)ServiceManager.callJavaBeanService("RcNoBO","addRcNoPre" , map) ;
		return result;
    }

    /**
     * 查询号码或sim卡的订单
     * @param map Map
     * @return List
     */
    public List qryOrderNo(String startDate, String endDate)
        throws Exception {
        Map map = new HashMap();
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("operId", operId);

        if ((startDate != null) && (startDate.trim().length() > 0)) {
            map.put("startDate", startDate);
        }

        if ((endDate != null) && (endDate.trim().length() > 0)) {
            map.put("endDate", endDate);
        }
        List result = (List)ServiceManager.callJavaBeanService("RcNoBO","qryOrderNo" , map) ;
		return result;
    }

    /**
     * 删除订单的号码或sim卡
     * @param map Map
     * @return Map
     */
    public Map delNoOrder(String orderId, String salesRescId, String storageId,
        String actAmount, String resBCode, String resECode)
        throws Exception {
        Map map = new HashMap();
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String tableName = "rc_no";
        map.put("operId", operId);
        map.put("departId", departId);
        map.put("tableName", tableName);
        map.put("orderId", orderId);
        map.put("salesRescId", salesRescId);
        map.put("storageId", storageId);
        map.put("actAmount", actAmount);
        map.put("resBCode", resBCode);
        map.put("resECode", resECode);

        Map retMap = (Map)ServiceManager.callJavaBeanService("RcNoBO","delNoSimOrder" , map) ;

        if (retMap == null) {
            retMap = new HashMap();
            retMap.put("result", "-1");
            retMap.put("message", "系统内部错误，缺少参数!");
        } else if (retMap.get("result") != null) {
            String result = retMap.get("result").toString();

            if ("1".equals(result)) {
                String num = null;

                if (retMap.get("message") != null) {
                    num = retMap.get("message").toString();
                }

                String message = "取消生成号码成功";

                if ((num != null) && (num.trim().length() > 0)) {
                    message += ("，共取消" + num + "个号码");
                }

                retMap.put("message", message + "!");
            }

            if ("2".equals(result)) {
                retMap.put("message", "操作员没有权限操作该仓库中物资，操作失败!");
            }

            if ("3".equals(result)) {
                retMap.put("message", "生成的号码已经不存在!");
            }

            if ("4".equals(result)) {
                retMap.put("message", "生成的号码已被分配过或则存在不是可用状态的号码，操作失败!");
            }
        }

        return retMap;
    }

    //////////////// 号码包操作 ////////////////////////////

    /**
          * 查询包规则
          * @param map
          * @param pi
          * @param ps
          * @return
          */
    public PageModel qryNoBagRule(String lanId, String bagruleName,
        String operId, int pi, int ps) throws Exception {
        Map map = new HashMap();
        map.put("lanId", lanId);
        map.put("bagruleName", bagruleName);
        map.put("operId", operId);
        map.put("operId", operId);
        map.put("pageIndex", new Integer(pi));
        map.put("pageSize", new Integer(ps));
        
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("RcNoBO","qryNoBagRule" , map) ;


        return pm;
    }

    /**
     * 查询包规则,为弹出页面服务
     * @param map
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryPopNoBagRule(String bagruleName, int pi, int ps)
        throws Exception {
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
        Map map = new HashMap();
        map.put("lanId", lanId);
        map.put("bagruleName", bagruleName);
        map.put("operId", "");
        map.put("pageIndex", new Integer(pi));
        map.put("pageSize", new Integer(ps));
        
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("SrRcBagBo","qryNoBagRule" , map) ;


        return pm;
    }

    /**
     * 新增或修改包规则
     * @param vo
     * @return 1:成功；-1：缺少参数；-2：包名重复；-3：操作员没有权限修改该包信息
     */
    public int updateNoBag(RcNobagRuleVO vo) throws Exception {
        Map map = new HashMap();
        map.put("vo", vo);

        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("operId", operId);

        Integer pm = (Integer)ServiceManager.callJavaBeanService("SrRcBagBo","saveNoBag" , map) ;


        return pm.intValue();

    }

    /**
     * 新增包规则
     * @param vo
     * @return 1:成功；-1：缺少参数；-2：包名重复；-3：操作员没有权限修改该包信息
     */
    public int addNoBag(RcNobagRuleVO vo, RcNobagRuleDetaVO[] arrs)
        throws Exception {
        Map map = new HashMap();

        if (vo == null) {
            return -1;
        }

        List list = vo.getDetailList();

        if ((arrs != null) && (arrs.length > 0)) {
            for (int i = 0; i < arrs.length; i++) {
                list.add(arrs[i]);
            }
        }

        vo.setDetailList(list);

        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
        vo.setOperId(operId);
        vo.setLanId(lanId);
        map.put("vo", vo);
        map.put("operId", operId);

        Integer pm = (Integer)ServiceManager.callJavaBeanService("SrRcBagBo","saveNoBag" , map) ;


        return pm.intValue();
    }

    /**
     * 删除号码包规则
     * @param bagruleId
     * @param operId
     * @param nowOperId
     * @return 1:成功；-1：缺少参数；-2：操作员没有权限；-3：该记录已经被引用，不能删除
     */
    public int deleteNoBagRule(String bagruleId, String operId)
        throws Exception {
        String nowOperId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        
        Map map = new HashMap();
        map.put("bagruleId", bagruleId);
        map.put("operId", operId);
        map.put("nowOperId", nowOperId);
        Integer pm = (Integer)ServiceManager.callJavaBeanService("SrRcBagBo","deleteNoBagRule" , map) ;


        return pm.intValue();

    }

    /**
     * 根据号码包id查找该包的详细规则
     * @param bagruleId
     * @return
     */
    public List qryNoBagRuleDetailList(String bagruleId)
        throws Exception {
    	Map map = new HashMap();
        map.put("bagruleId", bagruleId);
        List pm = (List)ServiceManager.callJavaBeanService("SrRcBagBo","qryNoBagRuleDetailList" , map) ;


        return pm;

    }

    /**
     * 修改号码包界面上的新增号码包细则
     * @param bagruleId
     * @param rescLevel
     * @param noNum
     * @return
     */
    public int addRuleDetailNum(String bagruleId, String rescLevel, String noNum)
        throws Exception {
    	Map map = new HashMap();
        map.put("bagruleId", bagruleId);
        map.put("rescLevel", rescLevel);
        map.put("noNum", noNum);
        Integer pm = (Integer)ServiceManager.callJavaBeanService("SrRcBagBo","addRuleDetailNum" , map) ;


        return pm.intValue();

    }

    /**
     * 根据条件删除号码包详细规则
     * @param bagruleId
     * @param rescLevel
     * @return
     */
    public int delRuleDetail(String bagruleId, String rescLevel)
        throws Exception {
    	Map map = new HashMap();
        map.put("bagruleId", bagruleId);
        map.put("rescLevel", rescLevel);
        Integer pm = (Integer)ServiceManager.callJavaBeanService("SrRcBagBo","delRuleDetail" , map) ;


        return pm.intValue();

    }

    /**
     * 查询号码包号码
     * @param map
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryNoBagNo(String bagruleId, String rescInstanceCode,
        String nobagId, int pi, int ps) throws Exception {
        Map map = new HashMap();
        map.put("bagruleId", bagruleId);
        map.put("rescInstanceCode", rescInstanceCode);
        map.put("nobagId", nobagId);
        map.put("pageIndex", new Integer(pi));
        map.put("pageSize", new Integer(ps));
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("SrRcBagBo","qryNoBagNo" , map) ;


        return pm;
    }

    /**
     * 为包随机加入号码
     * @param paraMap
     * @return
     */
    public Map geneNoBagNo(String bagruleId, String storageId)
        throws Exception {
        Map map = new HashMap();
        map.put("bagruleId", bagruleId);
        map.put("storageId", storageId);
        Map pm = (Map)ServiceManager.callJavaBeanService("SrRcBagBo","geneNoBagNo" , map) ;


        return pm;


    }

    /**
     * 查询需要删除的号码包的列表，需要统计号码数量
     * @param bagruleId
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryNoBagByRule(String bagruleId, int pi, int ps)
        throws Exception {
    	 Map map = new HashMap();
         map.put("bagruleId", bagruleId);
         map.put("pageIndex", new Integer(pi));
         map.put("pageSize", new Integer(ps));
         PageModel pm = (PageModel)ServiceManager.callJavaBeanService("SrRcBagBo","qryNoBagByRule" , map) ;


         return pm;
    }

    /**
     * 删除号码包号码
     * @param nobagId
     * @return
     */
    public long delNoBag(String nobagId) throws Exception {
    	Map map = new HashMap();
        map.put("nobagId", nobagId);
        Long pm = (Long)ServiceManager.callJavaBeanService("SrRcBagBo","delNoBag" , map) ;


        return pm.longValue();

    }

    ///////////////////////////////////////////

    /**
       * 根据界面条件查询号码
       * @param map Map
       * @param pi int
       * @param ps int
       * @return PageModel
       */
    public PageModel qryRcNo2(String logId, String startCode, String endCode,
        String storageId, int pi, int ps) throws Exception {
        if ((logId != null) && (logId.length() > 0) && "1".equals(startCode) &&
                "1".equals(endCode) && "1".equals(storageId)) { //按本地网方式

            RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
            String SQL_SELECT_2 = "SELECT a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_LEVEL,a.SALES_RESOURCE_ID,a.STORAGE_ID,a.RESOURCE_STATE,a.STATE,a.EFF_DATE,a.EXP_DATE,a.IMSI_ID,a.NO_SEG_ID,a.REC_TIME,a.BALA_MODE,a.INIT_TIME,null as NO_SEG_NAME,null as SALES_RESOURCE_NAME,d.STORAGE_NAME " +
                ",a.LAN_ID,a.REGION_ID,a.EXCH_ID,a.self_help_flag" +
                " FROM RC_NO a,RC_STORAGE d where a.STORAGE_ID=d.STORAGE_ID and a.lan_id=?";
            String[] params = new String[] { logId };
            List list = dao.findBySql(SQL_SELECT_2, params);

            return PageHelper.popupPageModel(list, pi, ps);
        }

        PageModel pm = new PageModel();
        Map map = new HashMap();
        map.put("startCode", startCode);
        map.put("endCode", endCode);
        map.put("rescState", "");
        map.put("rescLevel", "");
        map.put("noSegId", "");
        map.put("storageId", storageId);
        //map.put("getOldLevel", "1");
        System.out.println(map);
        map.put("pageIndex", new Integer(pi));
        map.put("pageSize", new Integer(ps));
        pm = (PageModel)ServiceManager.callJavaBeanService("RcNoBo","qryRcNo" , map) ;


        return pm;

    }

    /**
     *
     * @param storageId
     * @param startCode
     * @param endCode
     * @return
     * @throws Exception
     */
    public String reCountLevel(String storageId, String startCode,
        String endCode) throws Exception {
        Map map = new HashMap();
        map.put("storageId", storageId);
        map.put("startCode", startCode);
        map.put("endCode", endCode);
        map.put("operId", this.getOperId());

        String returns= (String)ServiceManager.callJavaBeanService("RcNoBo","reCountLevel" , map) ;


        //System.out.println("===================before: "+new Date());
        //String returns = bean.reCountLevel(map);

        //System.out.println("===================after: "+new Date());
        return returns;
    }

    /**
     *
     * @param storageId
     * @param startCode
     * @param endCode
     * @return
     * @throws Exception
     */
    public PageModel qryRcNoLogInfo(String logId, String startCode,
        String endCode, String storageId, int pageIndex, int pageSize)
        throws Exception {
    	PageModel pm = new PageModel();
        Map map = new HashMap();
        map.put("logId", logId);
        map.put("storageId", storageId);
        map.put("startCode", startCode);
        map.put("endCode", endCode);
        map.put("pageIndex", new Integer(pageIndex));
        map.put("pageSize", new Integer(pageSize));
        pm = (PageModel)ServiceManager.callJavaBeanService("RcNoBo","qryRcNoLogInfo" , map) ;



        return pm;
    }

    /**
     * 更加上级仓库查询下级仓库的号码，SIM卡资源实例数量
     */
    public PageModel queryDownSIMNOStockNum(String departId, String regionId,
        String rescState, String rescLevel, String balaMode, String startDate,
        String endDate, String upStorageId, String storageId, String rescFlag,
        int pi, int ps) throws Exception {
        PageModel pm = new PageModel();

        if ((rescFlag == null) || (rescFlag.trim().length() < 1)) {
            return pm;
        }

        rescFlag = rescFlag.trim();

        if ("rc_no".equalsIgnoreCase(rescFlag)) {
            pm = this.satNoStockNum(departId, regionId, rescState, rescLevel,
                    balaMode, upStorageId, storageId, pi, ps);
        } else if ("rc_sim".equalsIgnoreCase(rescFlag)) {
            pm = this.satSimStockNum(departId, regionId, rescState, startDate,
                    endDate, upStorageId, storageId, pi, ps);
        }

        return pm;
    }

    /*           public List qryRcNoForExcel(String startCode, String endCode, String rescState, String rescLevel,
                   String noSegId, String storageId,String lanId,String regionId,String exchId) throws Exception {
                       int pi = 1 ;
                       int ps = 20;
                       List noList = new ArrayList();
                       PageModel pm = null;
                       do{
                       pm = this.qryRcNo(startCode, endCode, rescState, rescLevel,
                                            noSegId, storageId, lanId, regionId, exchId, pi++, ps);
                       if(pm!=null){
                               noList.addAll(pm.getList());
                       }
                       }while(pm!=null && pm.getTotalCount()>(noList.size()));
                       return noList;
    
               }*/
    public PageModel qryNoRemainInfo(String departId, String operId,
        String remainId, String startDate, String endDate, String remained,
        String remainExp, int pi, int ps) throws Exception {
        HashMap map = new HashMap();
        map.put("departId", departId);
        map.put("operId", operId);
        map.put("remainId", remainId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("remained", remained);
        map.put("remainExp", remainExp);
        map.put("pi", String.valueOf(pi));
        map.put("ps", String.valueOf(ps));
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("RcNoBo","qryNoRemainInfo" , map) ;



        return pm;
    }

    //号码封存
    public List geneNoRemain(RcNoRemainInfoVO vo)
        throws Exception {
        String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String provId = getGlobalVar(GlobalVariableHelper.PROV_ID);

        if (vo != null) {
            vo.setOperId(operId + "@" + provId); //传入，表示是否广西
            vo.setDepartId(departId);
        }

        List list = new ArrayList();
        String startcode = vo.getStartNo();
        String endcode = vo.getEndNo();
        long nocount = Long.parseLong(endcode) - Long.parseLong(startcode) + 1;

        if (nocount > 100000) {
            list.add("1");
            list.add("请不要一次处理超过10000个号码！");
            list.add("");

            return list;
        }

        String nostr = "";

        for (long i = Long.parseLong(startcode); i <= Long.parseLong(endcode);
                i++) {
            nostr += (i + ",");
        }

        if ((nostr != null) && (nostr.length() > 0)) {
            nostr = nostr.substring(0, nostr.length() - 1);
        }

        String type = "0";
        Map map = geneNoRemain2(vo, nostr, type);
        list.add((String) map.get("result"));
        list.add((String) map.get("info"));
        list.add((String) map.get("failNos"));

        return list;
    }

    //按文件上传方式号码封存、解封
    public Map geneNoRemain2(RcNoRemainInfoVO vo, String nostr, String type)
        throws Exception {
        String[] nos = nostr.split(",");
        Map retMap = new HashMap();
        String ret = "";
        String result = "1";
        String info = "";

        if (nos.length > 10000) {
            info = "文件包含的号码数超过10000，请不要一次处理超过10000个号码！";
            retMap.put("failNos", "");
            retMap.put("info", info);
            retMap.put("result", result);
        }


        String errTip2 = "";
        String[] str = vo.getOperId().split("@");
        String provId = str[1];

        if (provId.equals("20")) {
            if (vo.getRemainFlag().equals("1")) {
                errTip2 = "号码无效或状态不是封存，或您无仓库权限；\n";
            } else {
                errTip2 = "号码无效或不可用，或您无仓库权限；\n";
            }
        } else {
            if (vo.getRemainFlag().equals("1")) {
                errTip2 = "号码无效或号码状态不是封存；\n";
            } else {
                errTip2 = "号码无效或号码状态不满足封存条件；\n";
            }
        }
        Map map = new HashMap();
        map.put("vo", vo);
        map.put("nos", nos);
        map.put("type", type);
        ret = (String)ServiceManager.callJavaBeanService("RcNoBo","handleNoRemain" , map) ;

        //ret = bean.handleNoRemain(vo, nos, type);

        String[] failnos = ret.split(",");

        StringBuffer failNos = new StringBuffer();

        try {
            int failcount = failnos.length;

            if (failnos[0].length() > 0) {
                for (int i = 0; i < failnos.length; i++) {
                    failNos.append(failnos[i] + "  --  ");
                    failNos.append(errTip2);
                }

                if (type.equals("2")) {
                    info = "操作成功，共有" + failcount + "个号码解封失败，详见页面显示！";
                } else {
                    info = "操作成功，共有" + failcount + "个号码封存失败，详见页面显示！";
                }
            } else {
                failcount = 0;

                if (type.equals("2")) {
                    info = "全部号码解封成功！";
                } else {
                    info = "全部号码封存成功！";
                }
            }

            retMap.put("failNos", failNos.toString());
            retMap.put("info", info);

            if ((failcount > 0) && (failnos[0].length() > 0)) {
                result = "0";
            }

            retMap.put("result", result);
        } catch (Exception ex) {
            info = "发生未知错误！";
            retMap.put("failNos", failNos.toString());
            retMap.put("info", info);
            retMap.put("result", "-1");

            return retMap;
        }

        return retMap;
    }

    public PageModel qryNoRemains(String remainId, int pi, int ps)
        throws Exception {
    	HashMap map = new HashMap();
        map.put("remainId", remainId);
        map.put("pi", new Integer(pi));
        map.put("ps", new Integer(ps));
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("RcNoBo","qryNoRemains" , map) ;



        return pm;

    }

    public List qryNoRemain(String remainId) throws Exception {

        return qryNoRemains(remainId, 1, 20).getList();
    }

    //号码解封
    public List realseNoRemain(RcNoRemainInfoVO vo)
        throws Exception {
        String relOperId = getGlobalVar(GlobalVariableHelper.OPER_ID);
        String relDepartId = getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String provId = getGlobalVar(GlobalVariableHelper.PROV_ID);

        if (vo != null) {
            vo.setOperId(relOperId + "@" + provId); //传入，表示是否广西
                                                    //					  vo.setRelOperId(relOperId+"@"+provId);

            vo.setRelDepartId(relDepartId);
            vo.setRelOperId(relOperId); //传入，表示是否广西
                                        //					  vo.setRelOperId(relOperId+"@"+provId);

            vo.setRelDepartId(relDepartId);
        }

        List list = new ArrayList();
        String startcode = vo.getStartNo();
        String endcode = vo.getEndNo();
        String nostr = "";

        for (long i = Long.parseLong(startcode); i <= Long.parseLong(endcode);
                i++) {
            nostr += (i + ",");
        }

        if ((nostr != null) && (nostr.length() > 0)) {
            nostr = nostr.substring(0, nostr.length() - 1);
        }

        String type = "2";
        Map map = geneNoRemain2(vo, nostr, type);
        list.add((String) map.get("result"));
        list.add((String) map.get("info"));
        list.add((String) map.get("failNos"));

        return list;
    }

    public List qryNoRemainsByAll(String remainId) throws Exception {
        int pi = 1;
        int ps = 20;
        List noList = new ArrayList();
        PageModel pm = null;

        do {
            pm = this.qryNoRemains(remainId, (pi++), (ps));

            if (pm != null) {
                noList.addAll(pm.getList());
            }
        } while ((pm != null) && (pm.getTotalCount() > (noList.size())));

        return noList;
    }

    public List qryRescAllState() throws Exception {
    	Map map = new HashMap();
        List pm = (List)ServiceManager.callJavaBeanService("RcNoBo","qryRescAllState" , map) ;


        return pm;

    }

    /**
     * 更新号码
     * @param vo RcNoVO
     * @return boolean
     */
    public Map updateTxtBatch(Map map) throws Exception {
        List noList = (List) map.get("list");
        Map rtnMap1 = (Map)ServiceManager.callJavaBeanService("RcNoBo","updateTxtBatch" , map) ;

        //Map rtnMap1 = bean.updateTxtBatch(map);
        Map rtnMap2 = new HashMap();
        String result = (String) rtnMap1.get("result");
        String info = "系统异常";
        String errTip2 = "号码不存在,或者号码不在所选仓库内";
        StringBuffer failNos = new StringBuffer();

        if ((result != null) && "1".equals(result)) {
            if (rtnMap1.get("failList") != null) {
                List rtnList = (List) rtnMap1.get("failList");

                if (rtnList.size() > 0) {
                    rtnMap2.put("result", "0");
                    info = "成功修改" + (noList.size() - rtnList.size()) +
                        "个号码，失败" + rtnList.size() + "个号码，出错号码请见界面出错信息!";

                    for (int i = 0; i < rtnList.size(); i++) {
                        failNos.append((String) rtnList.get(i) + "  --  ");
                        failNos.append(errTip2);
                        failNos.append(";");
                    }

                    rtnMap2.put("failNos", failNos.toString());
                } else {
                    rtnMap2.put("result", "1");
                    info = "成功修改" + (noList.size()) + "个号码，无失败号码!";
                }
            } else {
                rtnMap2.put("result", "1");
                info = "成功修改" + (noList.size()) + "个号码，无失败号码!";
            }
        } else {
            rtnMap2.put("result", "-1");
        }

        rtnMap2.put("info", info);

        return rtnMap2;
    }

    /**
     * 查询出所有的本地网配置
     * @return
     */
    public List qryNoRecLanConf() {
        RcNoConfigBo bo = new RcNoConfigBo();
        List list = bo.qryNoRecLanConf();

        return list;
    }

    /**
     * 查询是否进行号码回收释放的标志
     * 1：释放；0：不释放；-911：没有配置相关记录，请配置
     * @return
     */
    public String getNoRecSet() {
        RcNoConfigBo bo = new RcNoConfigBo();

        return bo.getNoRecSet();
    }

    /**
     * 修改号码是否回收释放的标志
     * @param pkey
     * @return
     */
    public String saveNoRecSet(String pkey, String defaultDayNum)
        throws Exception {
    	Map map = new HashMap();
        map.put("pkey", pkey);
        map.put("defaultDayNum", defaultDayNum);

        String returns= (String)ServiceManager.callJavaBeanService("RcNoBo","saveNoRecSet" , map) ;



        return returns;
    }

    /**
     * 批量更新回收规则
     * @param rules
     * @return
     */
    public String saveNoRecLanConf(RcNoRecRuleVO[] rules)
        throws Exception {
    	Map map = new HashMap();
        map.put("rules", rules);

        String returns= (String)ServiceManager.callJavaBeanService("RcNoBo","saveNoRecLanConf" , map) ;



        return returns;
    }

    public String reCountLevelByLanId(String lanId) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        Map map = new HashMap();
        map.put("lanId", lanId);
        map.put("operId", operId);

        String returns= (String)ServiceManager.callJavaBeanService("RcNoBo","saveNoRecLanConf" , map) ;
        return returns;
    }
}
