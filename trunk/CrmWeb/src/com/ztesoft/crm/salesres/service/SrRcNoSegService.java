package com.ztesoft.crm.salesres.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.RcImsiSegVO;
import com.ztesoft.crm.salesres.vo.RcImsiVO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class SrRcNoSegService extends DictService {

    // 新增号段
    public String addRcNoSeg(RcNoSegVO vo) throws Exception {

        // return bean.addRcNoSeg(vo);

        Map map = new HashMap();
        map.put("vo", vo);
        return (String) ServiceManager.callJavaBeanService("SrRcNoSegBO",
                "addRcNoSeg", map);

    }

    // 查询号段
    public PageModel qryRcNoSeg(String noSegId, String noState, String segId,
            int pi, int ps) throws Exception {
        PageModel pm = new PageModel();

        try {
            // pm = bean.qryRcNoSeg(noSegId, noState, segId, pi, ps);

            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("noSegId", noSegId);
            map.put("noState", noState);
            map.put("segId", segId);

            pm = (PageModel) ServiceManager.callJavaBeanService("SrRcNoSegBO",
                    "qryRcNoSeg", map);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 删除号段
    public String delRcNoSeg(String noSegId) throws Exception {
        Map map = new HashMap();
        map.put("noSegId", noSegId);

        HttpServletRequest req = RequestContext.getContext().getHttpRequest();

        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // return bean.delRcNoSeg(map);
        return (String) ServiceManager.callJavaBeanService("SrRcNoSegBO",
                "delRcNoSeg", map);

    }

    // 更新号段
    public String updateNoSeg(RcNoSegVO vo) throws Exception {
        Map map = new HashMap();
        map.put("vo", vo);

        HttpServletRequest req = RequestContext.getContext().getHttpRequest();

        // String reworkIp = (this.getRequest() == null) ? ""
        // : (this.getRequest()
        // .getRemoteAddr());

        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());

        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // return bean.updateNoSeg(map);
        return (String) ServiceManager.callJavaBeanService("SrRcNoSegBO",
                "updateNoSeg", map);
    }

    // 通过号段查询ID
    public List qryRcNoSegBySeg(String noSegId, String noState)
            throws Exception {

        // return bean.qryRcNoSegBySeg(noSegId, noState);

        Map map = new HashMap();
        map.put("noSegId", noSegId);
        map.put("noState", noState);

        return (List) ServiceManager.callJavaBeanService("SrRcNoSegBO",
                "qryRcNoSegBySeg", map);
    }

    // 新增IMSI号段
    public String addRcImsiSeg(RcImsiSegVO vo) throws Exception {

        // RcSIM", RcSIMHome.class);
        // return bean.addRcImsiSeg(vo);
        Map map = new HashMap();
        map.put("vo", vo);
        return (String) ServiceManager.callJavaBeanService("SrRcImsiSegBO",
                "addRcImsiSeg", map);

    }

    // 查询号IMSI段
    public PageModel qryRcImsiSeg(String imsiSegId, String state, int pi, int ps)
            throws Exception {
        PageModel pm = new PageModel();

        try {
            // RcSIM", RcSIMHome.class);
            // pm = bean.qryRcImsiSeg(imsiSegId, state, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("imsiSegId", imsiSegId);
            map.put("state", state);

            pm = (PageModel) ServiceManager.callJavaBeanService("SrRcImsiSegBO",
                    "qryRcImsiSeg", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 删除IMSI号段
    public String delRcImsiSeg(String imsiSegId) throws Exception {
        // RcSIM", RcSIMHome.class);

        // return bean.delRcImsiSeg(imsiSegId);
        Map map = new HashMap();
        map.put("imsiSegId", imsiSegId);
        return (String) ServiceManager.callJavaBeanService("SrRcImsiSegBO",
                "delRcImsiSeg", map);
    }

    // 更新Imsi号段
    public String updateImsiSeg(RcImsiSegVO vo) throws Exception {
        // RcSIM", RcSIMHome.class);
        //
        // return bean.updateImsiSeg(vo);
        Map map = new HashMap();
        map.put("vo", vo);
        return (String) ServiceManager.callJavaBeanService("SrRcImsiSegBO",
                "updateImsiSeg", map);
    }

    // 通过Imsi号段查询信息
    public List qryRcImsiSegById(String imsiSegId) throws Exception {
        // RcSIM", RcSIMHome.class);
        //
        // return bean.qryRcImsiSegById(imsiSegId);
        Map map = new HashMap();
        map.put("imsiSegId", imsiSegId);
        return (List) ServiceManager.callJavaBeanService("SrRcImsiSegBO",
                "qryRcImsiSegById", map);
    }

    // 新增IMSI号
    public String addRcImsi(RcImsiVO vo) throws Exception {
        // RcSIM", RcSIMHome.class);
        HashMap map = new HashMap();

        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        // String reworkIp = (this.getRequest() == null) ? ""
        // : (this.getRequest()
        // .getRemoteAddr());
        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());

        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("vo", vo);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // return bean.addRcImsi(map);

        return (String) ServiceManager.callJavaBeanService("SrRcImsiBO",
                "addRcImsi", map);
    }

    // 查询号IMSI号
    public PageModel qryRcImsi(String imsiId, String simCode, String state,
            String masterFlag, int pi, int ps) throws Exception {
        PageModel pm = new PageModel();

        try {
            // RcSIM", RcSIMHome.class);
            // pm = bean.qryRcImsi(imsiId, simCode, state, masterFlag, pi, ps);
            Map map = new HashMap();
            map.put("pageIndex", new Integer(pi));
            map.put("pageSize", new Integer(ps));
            map.put("imsiId", imsiId);
            map.put("simCode", simCode);
            map.put("state", state);
            map.put("masterFlag", masterFlag);

            pm = (PageModel) ServiceManager.callJavaBeanService("SrRcImsiBO",
                    "qryRcImsi", map);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    // 删除IMSI号
    public String delRcImsi(String imsiId) throws Exception {
        // RcSIM", RcSIMHome.class);
        HashMap map = new HashMap();
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        // String reworkIp = (this.getRequest() == null) ? "" :
        // (this.getRequest()
        // .getRemoteAddr());
        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("imsiId", imsiId);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // return bean.delRcImsi(map);
        return (String) ServiceManager.callJavaBeanService("SrRcImsiBO",
                "delRcImsi", map);
    }

    // 更新Imsi号
    public String updateImsi(RcImsiVO vo) throws Exception {
        // RcSIM", RcSIMHome.class);
        HashMap map = new HashMap();
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        // String reworkIp = (this.getRequest() == null) ? ""
        // : (this.getRequest()
        // .getRemoteAddr());
        String reworkIp = (req == null) ? "" : (req.getRemoteAddr());
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("vo", vo);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        // return bean.updateImsi(map);
        return (String) ServiceManager.callJavaBeanService("SrRcImsiBO",
                "updateImsi", map);
    }

    // 通过Imsi号查询信息
    public List qryRcImsiById(String imsiId) throws Exception {
        // RcSIM", RcSIMHome.class);
        //
        // return bean.qryRcImsiById(imsiId);
        HashMap map = new HashMap();
        map.put("imsiId", imsiId);
        return (List) ServiceManager.callJavaBeanService("SrRcImsiBO",
                "qryRcImsiById", map);
    }

    public PageModel QuerySIMInfo(String rescInstanceCode, int pageIndex,
            int pageSize) throws Exception {
        PageModel pm = new PageModel();
        // RcSIM", RcSIMHome.class);
        // pm = bean.QuerySIMInfo(rescInstanceCode, pageIndex, pageSize);

        Map map = new HashMap();
        map.put("pageIndex", new Integer(pageIndex));
        map.put("pageSize", new Integer(pageSize));
        map.put("rescInstanceCode", rescInstanceCode);

        pm = (PageModel) ServiceManager.callJavaBeanService("SrRcImsiBO",
                "QuerySIMInfo", map);

        return pm;
    }
}
