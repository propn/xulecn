package com.ztesoft.crm.salesres.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.RcNosegAppVO;
import com.ztesoft.crm.salesres.vo.RcNosegWanVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class SrRcNoSegWanService extends DictService {
    /**
     * 获取全局变量(登陆信息)
     * 
     * @param strGlobalName
     * @return
     * @throws Exception
     */
    public String getGlobalVar(String strGlobalName) throws Exception {
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();

        // GlobalVariableHelper helper = new GlobalVariableHelper(getRequest());
        GlobalVariableHelper helper = new GlobalVariableHelper(req);

        String strGlobal = helper.getVariable(strGlobalName);

        if (strGlobal != null) {
            return strGlobal;
        } else {
            return "0";
        }
    }

    // 查询万号段
    public PageModel qryRcNoSegWan(String segName, String state, String lanId,
            int pi, int ps) throws Exception {
        PageModel pm = new PageModel();

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // pm = bean.qryRcNoSegWan(segName, state, lanId, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("segName", segName);
            map.put("state", state);
            map.put("lanId", lanId);

            pm = (PageModel) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "qryRcNoSegWan", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 查询万号段
    public PageModel qryRcNoSegWanById(String segId, int pi, int ps)
            throws Exception {
        PageModel pm = new PageModel();

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // pm = bean.qryRcNoSegWanById(segId, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("segId", segId);

            pm = (PageModel) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "qryRcNoSegWanById", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 新增万号段
    public String addRcNoSegWan(RcNosegWanVO vo) throws Exception {
        // GlobalVariableHelper helper = new GlobalVariableHelper(getRequest());
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        GlobalVariableHelper helper = new GlobalVariableHelper(req);
        String lanId = helper.getVariable("vg_lan_id");
        String operCode = helper.getVariable("vg_oper_code");
        String departId = helper.getVariable("vg_depart_id");
        // vo.setLanId(lanId);
        vo.setOperCode(operCode);
        vo.setDepartId(departId);

        Date currentTime = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        vo.setCreateTime(dateString);

        // SrRcNo", SrRcNoHome.class);
        //
        // return bean.addRcNoSegWan(vo);

        Map map = new HashMap();
        map.put("vo", vo);

        return (String) ServiceManager.callJavaBeanService("SrRcNoSegWanBO",
                "addRcNoSegWan", map);
    }

    // 更新万号段
    public String updateRcNoSegWan(RcNosegWanVO vo) throws Exception {
        Map map = new HashMap();
        map.put("vo", vo);

        HttpServletRequest req = RequestContext.getContext().getHttpRequest();

        // String reworkIp = (this.getRequest() == null) ? "" :
        // (this.getRequest()
        // .getRemoteAddr());
        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // SrRcNo", SrRcNoHome.class);
        //
        // return bean.updateNoSegWan(map);

        return (String) ServiceManager.callJavaBeanService("SrRcNoSegWanBO",
                "updateNoSegWan", map);
    }

    // 删除万号段
    public String delRcNoSegWan(String segId) throws Exception {
        Map map = new HashMap();
        map.put("segId", segId);
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();

        // String reworkIp = (this.getRequest() == null) ? ""
        // : (this.getRequest()
        // .getRemoteAddr());

        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // SrRcNo", SrRcNoHome.class);
        //
        // return bean.delRcNoSegWan(map);

        return (String) ServiceManager.callJavaBeanService("SrRcNoSegWanBO",
                "delRcNoSegWan", map);
    }

    // 查询审批日志
    public PageModel qryRcNoSegAppLog(String segId, int pi, int ps)
            throws Exception {
        PageModel pm = new PageModel();

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // pm = bean.qryRcNoSegAppLog(segId, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("segId", segId);

            pm = (PageModel) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "qryRcNoSegAppLog", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 查询审批日志
    public PageModel qryRcNoSegApp(String segId, int pi, int ps)
            throws Exception {
        PageModel pm = new PageModel();

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // pm = bean.qryRcNoSegApp(segId, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("segId", segId);

            pm = (PageModel) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "qryRcNoSegApp", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 查询审批日志
    public PageModel qryRcNoSegAppForCheck(String lanId, String state,
            String segName, int pi, int ps) throws Exception {
        PageModel pm = new PageModel();

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // pm = bean.qryRcNoSegAppForCheck(lanId, state, segName, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("lanId", lanId);
            map.put("state", state);
            map.put("segName", segName);

            pm = (PageModel) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "qryRcNoSegAppForCheck", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 重新申请
    public String appAgain(RcNosegAppVO vo) {
        String flag = "";

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // flag = bean.appAgain(vo);
            Map map = new HashMap();
            map.put("vo", vo);
            flag = (String) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "appAgain", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public String checkWan(RcNosegAppVO vo, String isPass) {
        String flag = "";

        try {
            // SrRcNo",
            // SrRcNoHome.class);
            // flag = bean.checkWan(vo, isPass);
            Map map = new HashMap();
            map.put("vo", vo);
            map.put("isPass", isPass);
            flag = (String) ServiceManager.callJavaBeanService(
                    "SrRcNoSegWanBO", "checkWan", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flag;
    }
}
