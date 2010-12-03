package com.ztesoft.crm.salesres.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class SrRegionManageService extends DictService {
    

    /**
     * 获取登陆员工本地网信息
     * @return
     * @throws Exception
     */
    public String getLanId() throws Exception {
        return getGlobalVar(GlobalVariableHelper.LAN_ID);
    }

    public String getStrRegionInfo() {
        String lanId = "";

        try {
            HttpSession session = RequestContext.getContext().getHttpRequest().getSession();

            HashMap hm = (HashMap) session.getAttribute("LoginRtnParamsHashMap");
            Set entries = hm.entrySet();
            Iterator iter = entries.iterator();
            System.out.println(
                "***********************begin*************************");

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                System.out.println("key=" + key + "  ||  value=" + value);
            }

            System.out.println(
                "***********************end*************************");

            lanId = (String) ((HashMap) session.getAttribute(
                    "LoginRtnParamsHashMap")).get("vg_lan_id");

            //HttpSession session = getRequest().getSession();
            String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);

            if ((provId != null) &&
                    (provId.equals(ParamsConsConfig.PROV_ID_JX) ||
                    provId.equals(ParamsConsConfig.PROV_ID_GX))) {
                lanId = (String) ((HashMap) session.getAttribute(
                        "LoginRtnParamsHashMap")).get("vg_lan_id");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lanId;
    }

    /**
     * 根据本地网的名称查询本地网
     * @throws Exception Thrown if method fails due to system-level error.
     */
//    public PageModel qryLanInfo(String lanCode, String lanName, int pageIndex,
//        int pageSize) throws Exception {
//SrDepart",
//                SrDepartHome.class);
//
//        if ((lanCode == null) || (lanCode.trim().length() < 1)) {
//            lanCode = null;
//        }
//
//        if ((lanName == null) || (lanName.trim().length() < 1)) {
//            lanCode = null;
//        }
//
//        return SrDepart.qryLanInfo(lanCode, lanName, pageIndex, pageSize);
//    }
//
//    /**
//     * 根据营业厅的名称查询营业厅
//     * @throws Exception Thrown if method fails due to system-level error.
//     */
//    public PageModel qryRegionInfo(String lanId, String regionCode,
//        String regionName, int pageIndex, int pageSize)
//        throws Exception {
//        Map map = new HashMap();
//
//        if ((lanId != null) && (lanId.trim().length() > 0)) {
//            map.put("lanId", lanId);
//        }
//
//        if ((regionCode != null) && (regionCode.trim().length() > 0)) {
//            map.put("regionCode", regionCode);
//        }
//
//        if ((regionName != null) && (regionName.trim().length() > 0)) {
//            map.put("regionName", regionName);
//        }
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryRegionInfo(map, pageIndex, pageSize);
//    }
//
//    /**
//     * 根据部门的名称查询部门
//     * @throws Exception Thrown if method fails due to system-level error.
//     */
//    public PageModel qryDepartInfo(String regionId, String departCode,
//        String departName, int pageIndex, int pageSize)
//        throws Exception {
//        String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
//        Map map = new HashMap();
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        if ((departCode != null) && (departCode.trim().length() > 0)) {
//            map.put("departCode", departCode);
//        }
//
//        if ((departName != null) && (departName.trim().length() > 0)) {
//            map.put("departName", departName);
//        }
//
//        if ((operId != null) && (operId.trim().length() > 0)) {
//            map.put("operId", operId);
//        }
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryDepartInfo(map, pageIndex, pageSize);
//    }
//
//    /**
//     * 根据操作人员的ID,查询其能查看部门的id的字符串。
//     * @throws Exception
//     * @return String
//     */
//    public String qryRightFilterDeptIds(String regionId)
//        throws Exception {
//        Map map = new HashMap();
//        String operId = getGlobalVar(GlobalVariableHelper.OPER_ID);
//
//        if ((regionId != null) && (regionId.trim().length() > 0)) {
//            map.put("regionId", regionId);
//        }
//
//        if ((operId != null) && (operId.trim().length() > 0)) {
//            map.put("operId", operId);
//        }
//
//        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryRightFilterDeptIds(map);
//    }
//
//    /**
//     * 根据营业厅的ID,查询其能查看区域的id的字符串。
//     * @throws Exception
//     * @return PageModel
//     */
//    public PageModel qryArea(String regionId, String areaName, int pageIndex,
//        int pageSize) throws Exception {
//        Map map = new HashMap();
//        map.put("regionId", regionId);
//        map.put("areaName", areaName);
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryArea(map, pageIndex, pageSize);
//    }
//
//    /**
//    * 根据部门的名称查询部门
//    * @throws Exception Thrown if method fails due to system-level error.
//    */
//    public PageModel qryDepartInfoByAreaId(String areaId, String departCode,
//        String departName, int pageIndex, int pageSize)
//        throws Exception {
//        Map map = new HashMap();
//        map.put("regionId", areaId);
//        map.put("departCode", departCode);
//        map.put("departName", departName);
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryDepartInfoByAreaId(map, pageIndex, pageSize);
//    }
//
//    /**
//     * 根据部门的ID,查询其能查看代理商的id的字符串。
//     * @throws Exception
//     * @return PageModel
//     */
//    public PageModel qryAgent(String departId, String agentName, int pageIndex,
//        int pageSize) throws Exception {
//        Map map = new HashMap();
//        map.put("departId", departId);
//        map.put("agentName", agentName);
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryAgent(map, pageIndex, pageSize);
//    }
//
//    /**
//     * 根据查询条件查询代理商领取资源明细表。
//     * @throws Exception
//     * @return PageModel
//     */
//    public PageModel qryAgentStatistics(String lanId, String regionId,
//        String areaId, String departId, String agentId, String salesRescId,
//        String agentKindCode, String startDate, String endDate, int pageIndex,
//        int pageSize) throws Exception {
//        HashMap map = new HashMap();
//        map.put("lanId", lanId);
//        map.put("regionId", regionId);
//        map.put("areaId", areaId);
//        map.put("departId", departId);
//        map.put("agentId", agentId);
//        map.put("salesRescId", salesRescId);
//        map.put("agentKindCode", agentKindCode);
//        map.put("startDate", startDate);
//        map.put("endDate", endDate);
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryAgentStatistics(map, pageIndex, pageSize);
//    }
//
//    /**
//      * 根据订单ID查询代理商领取资源明细。
//      * @throws Exception
//      * @return PageModel
//      */
//    public PageModel qryDetail(String orderId, int pageIndex, int pageSize)
//        throws Exception {
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryDetail(orderId, pageIndex, pageSize);
//    }
//
//    /**
//      * 操作者列表。
//      * @throws Exception
//      * @return PageModel
//      */
//    public PageModel getOperInfo(String sType, String sContent, int pi, int ps)
//        throws Exception {
//SrDepart",
//                SrDepartHome.class);
//        HashMap map = new HashMap();
//        map.put("sType", sType);
//        map.put("sContent", sContent);
//
//        return SrDepart.getOperInfo(map, pi, ps);
//    }
//
//    /**
//      * 根据查询条件查询代理商库存资源明细报表。
//      * @throws Exception
//      * @return PageModel
//      */
//    public PageModel qryAgStroageStatistics(String lanId, String regionId,
//        String areaId, String departId, String agentId, String salesRescId,
//        String agentKindCode, String operId, String storageId, int pageIndex,
//        int pageSize) throws Exception {
//        HashMap map = new HashMap();
//        map.put("lanId", lanId);
//        map.put("regionId", regionId);
//        map.put("areaId", areaId);
//        map.put("departId", departId);
//        map.put("agentId", agentId);
//        map.put("salesRescId", salesRescId);
//        map.put("agentKindCode", agentKindCode);
//        map.put("operId", operId);
//        map.put("storageId", storageId);
//
//SrDepart",
//                SrDepartHome.class);
//
//        return SrDepart.qryAgStorageStatistics(map, pageIndex, pageSize);
//    }
}
