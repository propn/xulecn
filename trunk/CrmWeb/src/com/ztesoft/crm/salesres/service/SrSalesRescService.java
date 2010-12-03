package com.ztesoft.crm.salesres.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.bo.SrSalesRescBo;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.DcDeviceVO;
import com.ztesoft.crm.salesres.vo.SaleRescPricVO;
import com.ztesoft.crm.salesres.vo.SalesRescIdRelaVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


public class SrSalesRescService extends DictService {
    private static Logger logger = Logger.getLogger(SrSalesRescService.class);

    public SrSalesRescService() {
    }


    public PageModel qrySalesResource(String sales_resource_id,
        String sales_resource_cod, String sales_resource_nam,
        String sales_resource_fad, String fee_item_id, int pageIndex,
        int pageSize) throws Exception {
    	//设置参数,把参数组装到HashMap中
        HashMap map = new HashMap();
        map.put("sales_resource_id", sales_resource_id);
        map.put("sales_resource_cod", sales_resource_cod);
        map.put("sales_resource_nam", sales_resource_nam);
        map.put("sales_resource_fad", sales_resource_fad);
        map.put("fee_item_id", fee_item_id);
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "qrySalesResource", map);

        return pm;
    }

    public String saveSalesResource(SalesRescVO salesRescVO)
        throws Exception {
    	//设置参数,把参数组装到HashMap中
        HashMap map = new HashMap();
        map.put("vo", salesRescVO);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "saveSalesResource", map);
        return pm;
    }

    public String updateSalesResource(SalesRescVO salesRescVO,
        SalesRescVO oldSalesRescVO) throws Exception {
    	//设置参数,把参数组装到HashMap中
        HashMap map = new HashMap();
        map.put("voNew", salesRescVO);
        map.put("voOld", oldSalesRescVO);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "updateSalesResource", map);
        return pm;
    }

    public String removeSalesResource(SalesRescVO salesRescVO)
        throws Exception {
    	//设置参数,把参数组装到HashMap中
        HashMap map = new HashMap();
        map.put("vo", salesRescVO);
        
        HashMap reMap = (HashMap)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "removeSalesResource", map);
                
        String reString = (String) reMap.get("ERROR_MESSAGE");

        if (reString.equals("")) {
            reString = "操作成功！";
        }

        return reString;
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 根据设备编码和设备名称查询设备（分页查找）
     * @param scode String
     * @param sname String
     * @param pageIndex int
     * @param pageSize int
     * @return PageModel
     * @throws Exception
     */
    public PageModel qryDevice(String scode, String sname, int pageIndex,
        int pageSize) throws Exception {
    	//设置参数,把参数组装到HashMap中
        HashMap map = new HashMap();
        if ((scode != null) && (scode.trim().length() > 0)) {
        	if (this.getGlobalVar(GlobalVariableHelper.PROV_ID)
        			.equals(ParamsConsConfig.PROV_ID_GX)) {
        		map.put("devId", scode);
        	} else {
        		map.put("scode", scode);
        	}
        }
        
        if ((sname != null) && (sname.trim().length() > 0)) {
        	map.put("sname", sname);
        }
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "qryDevice", map);

        return pm;
    }

    /**
     * 删除设备
     * 同时查看相应的Sale_Resource表里的纪录在rc_entity表内是否存在纪录，如果存在则退出，不能删除该纪录
     * @param DcDeviceVO vo
     * @throws Exception
     */
    public Map removeDevice(DcDeviceVO vo) throws Exception {
    	HashMap map = new HashMap();
        map.put("vo", vo);
        
        Map pm = (Map)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "removeDevice", map);
        return pm;
                
    }

    /**
     * 保存设备
     *
     * @param DcDeviceVO vo
     * @throws Exception
     * @return String
     */
    public String saveDevice(DcDeviceVO vo) throws Exception {
    	HashMap map = new HashMap();
        map.put("vo", vo);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "saveDevice", map);
        return pm;

    }

    /**
     * 修改设备
     *
     * @param SalesRescVO
     *            salesRescVO
     * @throws Exception
     */
    public void updateDevice(DcDeviceVO vo) throws Exception {
    	HashMap map = new HashMap();
    	map.put("vo", vo);
    	String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
    	map.put("provId", provId);
    	
    	String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
    			"updateDevice", map);
                
    }

    // 以下是对设备营销资源关系表的操作

    /**
     * 检查物资系统编码、设备编码、营销资源ID是否在关系表中已存在
     * 如果存在则返回信息，如果都不存在则返回空
     * @param ncSalesRescId String
     * @param dcDeviceScode String
     * @param salesRescId String
     * @return String
     */
    public String checkExistsResc(String ncSalesRescId, String dcDeviceScode,
        String salesRescId, SalesRescVO salesRescVO) throws Exception {
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        HashMap map = new HashMap();
        map.put("ncSalesRescId", ncSalesRescId);
        map.put("dcDeviceScode", dcDeviceScode);
        map.put("salesRescId", salesRescId);
        map.put("salesRescVO", salesRescVO);
        map.put("provId", provId);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"checkExistsResc", map);
        return pm;
    }

    /**
     * 根据营销资源id得到相应的设备营销资源关系纪录
     * @param salesRescId String
     * @return SalesRescIdRelaVO
     * @throws Exception
     */
    public List qryDeviceSalesRel(String ncSalesRescId, String dcDeviceScode,
        String salesRescId) throws Exception {
    	HashMap map = new HashMap();
        map.put("ncSalesRescId", ncSalesRescId);
        map.put("dcDeviceScode", dcDeviceScode);
        map.put("salesRescId", salesRescId);
        
        List pm = (List)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"qryDeviceSalesRel", map);
        return pm;
                
    }

    /**
     * 根据设备编码把对应关系中的相关纪录删除,然后重新插入一条设备和资源对应的纪录
     * @param dcDeviceScode String
     * @param vo SalesRescIdRelaVO
     * @return String
     */
    public String updateDeviceSalesRel(String dcDeviceScode,
        SalesRescIdRelaVO vo) throws Exception {
    	HashMap map = new HashMap();
        map.put("dcDeviceScode", dcDeviceScode);
        map.put("vo", vo);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"updateDeviceSalesRel", map);
        return pm;
    }

    // 以下是整体对设备资源操作的方法

    /**
     * 根据查询出来的营销资源删除设备和营销资源
     * @param DcDeviceVO vo
     * @throws Exception
     */
    public String removeDeviceRes(String salesRescId) throws Exception {
    	HashMap mp = new HashMap();
    	mp.put("salesRescId", salesRescId);
        mp.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
        
        Map map = (Map)ServiceManager.callJavaBeanService("SrSalesRescBo",
                "removeDeviceRes", mp);
        if ((map == null) || !map.containsKey("ERROR_MESSAGE") ||
                (map.get("ERROR_MESSAGE") == null) ||
                (((String) map.get("ERROR_MESSAGE")).trim().length() <= 0)) {
            return "删除成功!";
        } else {
            return (String) map.get("ERROR_MESSAGE");
        }
    }

    /**
     * 保存设备,营销资源和他们相应的关系
     * @param DcDeviceVO vo
     * @throws Exception
     * @return String:营销资源编码
     */
    public String saveDeviceRes(DcDeviceVO deviceVO, SalesRescVO salesVO,
        SalesRescIdRelaVO relVO) throws Exception {
    	HashMap map = new HashMap();
    	map.put("deviceVO", deviceVO);
    	map.put("salesVO", salesVO);
        map.put("relVO", relVO);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        map.put("provId", provId);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"saveDeviceRes", map);
        return pm;
    }

    /**
     * 修改设备和营销资源及他们对应的关系,如果成功则返回null,否则返回错误消息
     * @param SalesRescVO
     * @salesRescVO
     * @throws Exception
     */
    public String updateDeviceRes(DcDeviceVO deviceVO, SalesRescVO salesVO,
        SalesRescIdRelaVO relVO, SalesRescVO odlSalesRescVO)
        throws Exception {
    	HashMap map = new HashMap();
    	map.put("deviceVO", deviceVO);
    	map.put("salesVO", salesVO);
    	map.put("relVO", relVO);
        map.put("odlSalesRescVO", odlSalesRescVO);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        map.put("provId", provId);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"updateDeviceRes", map);
        return pm;
    }

    ////////////////////////////////////////////////////////////////////////////
    public List qrySrAttrInfo(String salesRescId) throws Exception {
        HashMap map = new HashMap();
    	map.put("salesRescId", salesRescId);
        
    	List pm = (List)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"qrySrAttrInfo", map);
        return pm;
                
    }

    public String insertAttrInfo(String salesRescId, String attrId,
        String attrValue) throws Exception {
    	HashMap map = new HashMap();
    	map.put("salesRescId", salesRescId);
    	map.put("attrId", attrId);
    	map.put("attrValue", attrValue);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"insertAttrInfo", map);
        return pm;
                
    }

    public int deleteAttrInfo(String salesRescId, String attrId)
        throws Exception {
    	HashMap map = new HashMap();
    	map.put("salesRescId", salesRescId);
    	map.put("attrId", attrId);
        
        Integer pm = (Integer)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"deleteAttrInfo", map);
        return pm.intValue();
    }

    ////////////////////////////////////////营销资源价格////////////////////////////
    /**
      * 根据lanId查询营业区
      * @param lanId
      * @return
      */
    public List qryRrBusiness(String lanId) throws Exception {
    	HashMap map = new HashMap();
    	map.put("lanId", lanId);
        
    	List pm = (List)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"qryRrBusiness", map);
        return pm;

    }

    /**
     * 根据营销资源id查询该营销资源的价格集合
     * @param salesRescId
     * @return
     */
    public List qrySalesRescPrics(String salesRescId) throws Exception {
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        HashMap map = new HashMap();
        map.put("salesRescId", salesRescId);
    	map.put("provId", provId);
        
    	List pm = (List)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"getSalesRescPrics", map);
        return pm;
                

    }

    /**
     * 增加某一营销资源的价格，其中的salesRescId和businssIds不能为空，添加多个营业区的价格，价格相同
     * 如果有重复的记录则抱错
     * @param vo
     * @return Map:result:-1,表示发生错误；mess:错误信息
     * result:0,表示成功，mess:成功信息
     */
    public Map addSalesRescPrics(SaleRescPricVO vo) throws Exception {
        HashMap map = new HashMap();
        map.put("vo", vo);
        
    	Map pm = (Map)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"addSalesRescPrics", map);
        return pm;
                
    }

    /**
     * 根据主键删除营销资源的价格记录
     * @param vo
     * @return
     */
    public int delSingleSalesRescPric(SaleRescPricVO vo)
        throws Exception {
    	 HashMap map = new HashMap();
         map.put("vo", vo);
         
     	Integer pm = (Integer)ServiceManager.callJavaBeanService("SrSalesRescBo",
         		"delSingleSalesRescPric", map);
         return pm.intValue();
    }

    public int delSingleSalesRescPric2(String businessIds, String revels,
        String saleReIds) throws Exception {
        String[] businessId = businessIds.split(",");
        String[] revel = revels.split(",");
        String[] saleReId = saleReIds.split(",");

        for (int i = 0; i < businessId.length; i++) {
            SaleRescPricVO vo = new SaleRescPricVO();
            vo.setBusinessId(businessId[i]);
            vo.setRescLevel(revel[i]);
            vo.setSalesRescId(saleReId[i]);
            delSingleSalesRescPric(vo);
        }

        return 1;
    }

    /**
     * 根据营销资源id得到该营销资源的管理模式
     * @param salesRescId String
     * @return String
     */
    public String getManageMode(String salesRescId) throws Exception {
    	HashMap map = new HashMap();
    	map.put("salesRescId", salesRescId);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"getManageMode", map);
        return pm;
                

    }

    public String checkSalesRescInfo(String salesRescId)
        throws Exception {
    	HashMap map = new HashMap();
    	map.put("salesRescId", salesRescId);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"checkSalesRescInfo", map);
        return pm;
                

    }

    public String updateAttrInfos(List attrObjs, String salesRescId)
        throws Exception {
        if ((attrObjs == null) || (attrObjs.size() <= 0)) {
            return "blank";
        }

        String attrValue = "";

        for (int i = 0; i < attrObjs.size(); i++) {
            attrValue = (String) (((HashMap) (attrObjs.get(i))).get("attrValue"));

            if ((attrValue == null) || (attrValue.length() <= 0)) {
                return "-1";
            } else if (attrValue.length() > 50) {
                return "50";
            }
        }
        HashMap map = new HashMap();
        map.put("salesRescId", salesRescId);
    	map.put("attrObjs", attrObjs);
        
        String pm = (String)ServiceManager.callJavaBeanService("SrSalesRescBo",
        		"updateAttrInfos", map);
        return pm;

    }

    /**
     * 营销资源管理界面面，控制终端的开关
     * @param deptID
     * @param saleIds
     * @return
     */
    public String isCanModify() throws Exception {
        String isCanSql = "select 1 from dc_public where stype = 94913 and codea = '1'";
        SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
        boolean isCan = comdao.checkExist(isCanSql);

        if (isCan) {
            return "1";
        }

        return "0";
    }

    //////////////////////检查该营销资源是否是其他系统传来的，如果是，不允许任何修改（包括其属性）//////////////////////////UR42227
    public String checkRes(String salesRescId) {
        SrSalesRescBo bo = new SrSalesRescBo();

        if (bo.checkSalesRescRela(salesRescId)) {
            return "-2";
        } else {
            return "1";
        }
    }
}
