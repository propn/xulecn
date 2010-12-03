package com.ztesoft.crm.salesres.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.vo.RcSimEvdoVO;
import com.ztesoft.crm.salesres.vo.RcSimVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class RcSimService extends DictService {
    public RcSimService() {
    }

 

    public PageModel QuerySIMInfo(String backup, String startCode,
        String endCode, String rescState, String simType, String startNoCode,
        String endNoCode, String startExpDate, String endExpDate,
        String storageId, String simChipType, int pageIndex, int pageSize)
        throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        HashMap map = new HashMap();
        map.put("backup", backup);
        map.put("startCode", startCode);
        map.put("endCode", endCode);
        map.put("rescState", rescState);
        map.put("simType", simType);
        map.put("startNoCode", startNoCode);
        map.put("endNoCode", endNoCode);
        map.put("startExpDate", startExpDate);
        map.put("endExpDate", endExpDate);
        map.put("storageId", storageId);
        map.put("operId", operId);
        map.put("departId", departId);
        map.put("simChipType", simChipType);

        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        
        PageModel obj = (PageModel)ServiceManager.callJavaBeanService("RcSimBO", "QuerySIMInfo" , map) ;

		return  obj ;

    }

    public String addRcSimInfo(RcSimEvdoVO vo) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        vo.setOperId(operId);
        vo.setDepartId(departId);
        Map param = new HashMap();
        param.put("vo", vo);
        Object obj = ServiceManager.callJavaBeanService("RcSimBO", "addRcSimInfo" , param) ;
		return obj == null ? "" : (String) obj ;
    }

    public String modifyRcSimInfo(RcSimVO vo) throws Exception {
        
    	Map param = new HashMap();
        param.put("vo", vo);
		String reworkIp = RequestContext.getContext().getHttpRequest().getRemoteAddr();
        
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        vo.setOperId(operId);
        vo.setDepartId(departId);
        param.put("reworkIp", reworkIp);
        param.put("operId", operId);

        Object obj = ServiceManager.callJavaBeanService("RcSimBO", "modifyRcSimInfo" , param) ;
        return obj == null ? "" : (String) obj ;
    }

    public String deleteRcSimInfo(RcSimVO vo) throws Exception {
    	Map param = new HashMap();
        param.put("vo", vo);
		String reworkIp = RequestContext.getContext().getHttpRequest().getRemoteAddr();
        
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        vo.setOperId(operId);
        vo.setDepartId(departId);
        param.put("reworkIp", reworkIp);
        param.put("operId", operId);

        Object obj = ServiceManager.callJavaBeanService("RcSimBO", "deleteRcSimInfo" , param) ;
        return obj == null ? "" : (String) obj ;
    }

    /**
     * ��ѯsim���Ķ���
     * @param map Map
     * @return List
     */
    public List qryOrderSim(String startDate, String endDate)
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

        List obj = (List)ServiceManager.callJavaBeanService("RcSimBO", "qryOrderSim" , map) ;

        return obj;
    }

    /**
     * ɾ������sim����Ϣ�����ܰ����ĺ�����Ϣ(����)
     * @param map Map
     * @return Map
     */
    public Map delSimOrder(String orderId, String appId, String salesRescId,
        String storageId, String actAmount) throws Exception {
        Map map = new HashMap();
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        map.put("orderId", orderId);
        map.put("appId", appId);
        map.put("salesRescId", salesRescId);
        map.put("storageId", storageId);
        map.put("actAmount", actAmount);
        map.put("operId", operId);
        map.put("departId", departId);

        Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcSimBO", "delSimOrder" , map) ;

        if (rtnMap == null) {
            rtnMap = new HashMap();
            rtnMap.put("result", "-1");
            rtnMap.put("message", "ϵͳ�ڲ�����ȱ�ٲ���!");
        }

        return rtnMap;
    }

    /**
     * ����sim������Ĺ�����ϵ��������������ķ�ʽ
     * @param startSimStr String
     * @param endSimStr String
     * @param startNoStr String
     * @param endNoStr String
     * @throws Exception
     * @return Map
     */
    public Map preAssemSimNoSerial(String accept_id, String startSimStr,
        String endSimStr, String startNoStr, String endNoStr)
        throws Exception {
        Map map = new HashMap();

        if ((startSimStr == null) || (startSimStr.trim().length() < 1) ||
                (endSimStr == null) || (endSimStr.trim().length() < 1) ||
                (startNoStr == null) || (startNoStr.trim().length() < 1) ||
                (endNoStr == null) || (endNoStr.trim().length() < 1)) {
            map.put("result", "0");
            map.put("message", "Ԥ��ʧ�ܣ�ȱ�ٲ���!");

            return map;
        }

        long startSim = Long.parseLong(startSimStr);
        long endSim = Long.parseLong(endSimStr);
        long startNo = Long.parseLong(startNoStr);
        long endNo = Long.parseLong(endNoStr);

        if ((endSim - startSim) != (endNo - startNo)) {
            map.put("result", "0");
            map.put("message", "Ԥ��ʧ�ܣ���ҪԤ���Sim�����ͺ�����������ͬ!");

            return map;
        }

        long size = endSim - startSim + 1;
        List list = new ArrayList();
        Map maptemp = new HashMap();
        maptemp.put("accept_id", String.valueOf(accept_id));
        list.add(maptemp);

        for (long i = 0; i < size; i++) {
            Map paraMap = new HashMap();
            paraMap.put("sim", String.valueOf(startSim));
            paraMap.put("no", String.valueOf(startNo));

            startSim++;
            startNo++;
            list.add(paraMap);
        }
        Map param = new HashMap();
        param.put("list", list);
        Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcSimBO", "preAssemSimNo" , param);

        return rtnMap;
    }

    /**
     * ȡ��sim���ͺ���Ĺ����ϵ��������������ķ�ʽ
     * @param startSimStr String
     * @param endSimStr String
     * @throws Exception
     * @return Map
     */
    public Map cancelPreAssemSimNoSerial(String startSimStr, String endSimStr)
        throws Exception {
        Map map = new HashMap();

        if ((startSimStr == null) || (startSimStr.trim().length() < 1) ||
                (endSimStr == null) || (endSimStr.trim().length() < 1)) {
            map.put("result", "0");
            map.put("message", "ȡ��Ԥ��ʧ�ܣ�ȱ�ٲ���!");

            return map;
        }

        long startSim = Long.parseLong(startSimStr);
        long endSim = Long.parseLong(endSimStr);
        long size = endSim - startSim + 1;
        List codes = new ArrayList();

        for (int i = 0; i < size; i++) {
            codes.add(String.valueOf(startSim));
            startSim++;
        }

        Map param = new HashMap();
        param.put("codes", codes);
        
        Map rtnMap = (Map)ServiceManager.callJavaBeanService("RcSimBO", "cancelPreAssemSimNo" , param);

        return rtnMap;
    }

    /**
     * modify the SIM's rescstate and state...
     * @param vo
     * @return
     * @throws Exception
     */
    public String SIMstateModify(RcSimVO vo) throws Exception {
    	Map param = new HashMap();
        param.put("vo", vo);
        String result = (String)ServiceManager.callJavaBeanService("RcSimBO", "SIMstateModify" , param) ;
        return result;
    }

    /**
     * ����sim��������ѯevdo����Ϣ
     * @param rescInstanceId
     * @return
     */
    public RcSimEvdoVO qryEvdo(String rescInstanceId) throws Exception {
    	Map param = new HashMap();
        param.put("rescInstanceId", rescInstanceId);
        RcSimEvdoVO result = (RcSimEvdoVO)ServiceManager.callJavaBeanService("RcSimBO", "qryEvdo" , param) ;
        return result;
    }

    //excel����SIM����Ϣ	
    public PageModel QuerySIMInfoForExcel(String backup, String startCode,
        String endCode, String rescState, String simType, String startNoCode,
        String endNoCode, String startExpDate, String endExpDate,
        String storageId, String simChipType, int pageIndex, int pageSize)
        throws Exception {
        PageModel pm = new PageModel();
        pm = QuerySIMInfo(backup, startCode, endCode, rescState, simType,
                startNoCode, endNoCode, startExpDate, endExpDate, storageId,
                simChipType, pageIndex, pageSize);

        if (pm == null) {
            return null;
        }

        List list = pm.getList();
        String chipType = "";

        if ((list == null) || (list.size() <= 0)) {
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            RcSimVO vo = (RcSimVO) list.get(i);

            if (vo == null) {
                continue;
            }

            vo.setRescInstanceCode("ICCID:" + vo.getRescInstanceCode());
            vo.setImsiId("IMSI:" + vo.getImsiId());
            chipType = vo.getSimChipType();

            if ((vo.getSimChipType() != null) &&
                    (vo.getSimChipType().length() > 0)) {
                if ("0".equals(chipType)) {
                    vo.setSimChipType("��ͨUIM��");
                }

                if ("1".equals(chipType)) {
                    vo.setSimChipType("��о������");
                }

                if ("2".equals(chipType)) {
                    vo.setSimChipType("��о���ݿ�");
                }

                if ("3".equals(chipType)) {
                    vo.setSimChipType("һ��˫о������");
                }

                if ("4".equals(chipType)) {
                    vo.setSimChipType("һ��˫о���ݿ�");
                }
            }
        }

        pm.setList(list);

        return pm;
    }
}
