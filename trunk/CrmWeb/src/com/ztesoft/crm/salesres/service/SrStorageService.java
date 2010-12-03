package com.ztesoft.crm.salesres.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.buffalo.request.RequestContext;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.bo.SrStorageBo;
import com.ztesoft.crm.salesres.common.LocalType;
import com.ztesoft.crm.salesres.vo.MpStorageVO;
import com.ztesoft.crm.salesres.vo.RcStockLimitVO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;
import com.ztesoft.crm.salesres.vo.StorageDepartRelaVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;


/**
 * SrStorageService.java
 * @function: �ֿ����
 * 
 * @author nik
 * @since 2010-1-14
 * @modified  
 */
public class SrStorageService extends DictService {
    private static Logger logger = Logger.getLogger(SrStorageService.class);

    public SrStorageService() {
    }

    
    public PageModel getStaffInfo(String sType, String sContent, String sLanId,
        int pi, int ps) throws Exception {
//        PageModel pm = new PageModel();
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//        pm = bean.getStaffInfo(sType, sContent, sLanId,
//                this.getGlobalVar(GlobalVariableHelper.PROV_ID), pi, ps);
//
//        return pm;
        
        Map map = new HashMap();
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        
        map.put("sType", sType);
        map.put("sContent", sContent);
        map.put("sLanId", sLanId);
        map.put("PROV_ID", this.getGlobalVar(GlobalVariableHelper.PROV_ID));
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("STAFFBO","getStaffInfo" ,map);
        return result;
    }

    /**
     * �ɵĲ�ѯ�ֿ�ķ���
     *
     * @param storageId
     *            String
     * @param storageName
     *            String
     * @param townId
     *            String
     * @param lanId
     *            String
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @throws Exception
     * @return PageModel
     */
    public PageModel qryRcStorage(String storageId, String storageName,
        String townId, String lanId, int pageIndex, int pageSize)
        throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
        HashMap map = new HashMap();
        map.put("storageId", storageId);
        map.put("storageName", storageName);
        map.put("townId", townId);
        map.put("lanId", lanId);
        map.put("provId", this.getGlobalVar(GlobalVariableHelper.PROV_ID));

        //return bean.qryRcStorage(map, pageIndex, pageSize);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryRcStorage" ,map);
        return result;
        
        
    }

    /**
     * �ɵĲ�ѯ�ֿ�ķ���
     *
     * @param storageId
     *            String
     * @param storageName
     *            String
     * @param townId
     *            String
     * @param lanId
     *            String
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @throws Exception
     * @return PageModel
     */
    public PageModel qryAllRcStorage(String storageId, String storageCode,
        String storageName, String lanId, int pageIndex, int pageSize)
        throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
        HashMap map = new HashMap();

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            map.put("lanId", lanId);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        if ((storageName != null) && (storageName.trim().length() > 0)) {
            map.put("storageName", storageName);
        }

        if ((storageCode != null) && (storageCode.trim().length() > 0)) {
            map.put("storageCode", storageCode);
        }

        //return bean.qryRcStorage(map, pageIndex, pageSize);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryRcStorage" ,map);
        return result;
    }

    /**
     * ���ϵ����Ӳֿ����Ͳ�ѯ����
     */
    public PageModel qryAllRcStorage_HN(String storageId, String storageCode,
        String storageName, String storageType, String lanId, int pageIndex,
        int pageSize) throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
        HashMap map = new HashMap();

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            map.put("lanId", lanId);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        if ((storageName != null) && (storageName.trim().length() > 0)) {
            map.put("storageName", storageName);
        }

        if ((storageCode != null) && (storageCode.trim().length() > 0)) {
            map.put("storageCode", storageCode);
        }

        if ((storageType != null) && (storageType.trim().length() > 0)) {
            map.put("storageType", storageType);
        }

        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);

        if ((provId != null) && (provId.trim().length() > 0)) {
            map.put("provId", provId);
        }

        map.put("operId", operId);
        map.put("departId", departId);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        //return bean.qryRcStorage(map, pageIndex, pageSize);
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryRcStorage" ,map);
        return result;
    }

    /**
     * �ɵĲ�ѯ�ֿ�ķ���
     *
     * @param storageId
     *            String
     * @param storageName
     *            String
     * @param townId
     *            String
     * @param lanId
     *            String
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @throws Exception
     * @return PageModel
     */
    public PageModel qryAllRcStorage1(String storageId, String storageCode,
        String storageName, int pageIndex, int pageSize)
        throws Exception {
        if (!"".equals(storageId) && (storageId != null)) {
            RcStorageVO tt = qrySingleStorage(storageId);
            PageModel tmp = new PageModel();
            tmp.setPageCount(1);
            tmp.setPageIndex(1);
            tmp.setPageSize(20);
            tmp.setTotalCount(1);

            ArrayList t = new ArrayList(1);
            t.add(tt);
            tmp.setList(t);

            return tmp;
        } else {
            //SrInvoiceStockBo bo = new SrInvoiceStockBo();
            HashMap map = new HashMap();
            String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);

            if ((lanId != null) && (lanId.trim().length() > 0)) {
                map.put("lanId", lanId);
            }

            if ((storageName != null) && (storageName.trim().length() > 0)) {
                map.put("storageName", storageName);
            }

            if ((storageCode != null) && (storageCode.trim().length() > 0)) {
                map.put("storageCode", storageCode);
            }

            //PageModel pm = bo.qryRcStorage(map, pageIndex, pageSize);
            
            map.put("pageIndex",new Integer(pageIndex));
            map.put("pageSize",new Integer(pageSize));
            
            PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryRcStorage" ,map);
            return result;  

            //return pm;
        }
    }

    /**
     * ����storageId��ѯ��Ӧ�Ĳֿ���ϸ��Ϣ
     *
     * @param storageId
     *            String
     * @return RcStorageVO
     */
    public RcStorageVO qrySingleStorage(String storageId)
        throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.qrySingleStorage(storageId);
        
        Map map = new HashMap();
        map.put("storageId", storageId);
        RcStorageVO result = (RcStorageVO )ServiceManager.callJavaBeanService("SRSTORAGEBO","qrySingleStorage" ,map);
        return result;   
    }

    /**
     * ����storageId��ѯ�òֿ�ĸ����ֿ����Ϣ
     *
     * @param storageId
     *            String
     * @return RcStorageVO
     */
    public RcStorageVO qryParentStorage(String storageId)
        throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.qryParentStorage(storageId);
        
        Map map = new HashMap();
        map.put("storageId", storageId);
        RcStorageVO result = (RcStorageVO )ServiceManager.callJavaBeanService("SRSTORAGEBO","qryParentStorage" ,map);
        return result;   
    }


    /**
     * �޸Ĺ����������زֿ���
     * @param storageId
     * @param storageName
     * @return
     * @throws Exception
     */
    public String initStorageTree(String storageId, String storageName)
        throws Exception {
        Map map = new HashMap();
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);

        //�����������ŵ����⴦��
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);

        if (operLan.equals("1") && provId.equals(String.valueOf(LocalType.GX))) {
            lanId = "";
        }

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            map.put("lanId", lanId);
        }

        if ((storageId != null) && (storageId.trim().length() > 0)) {
            map.put("storageId", storageId);
        }

        if ((storageName != null) && (storageName.trim().length() > 0)) {
            map.put("storageName", storageName);
        }

        String result = (String)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStorageTree" , map) ;
		return result;

        //return bean.qryStorageTree(map);
    }

    //	/**
    //	 * ѡ��ֿ�ҳ�棬��ʼ���ֿ�����Ϣ---���ֿ���Ϣ����ҳ��ֿ����������ǣ�����Ҫ����Ȩ��
    //	 * 
    //	 * @param map
    //	 *            Map
    //	 * @return String
    //	 */
    //	public String initStorageTree2(String storageId, String storageName)
    //			throws Exception {
    //		Map map = new HashMap();
    //		String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);
    //		//�����������ŵ����⴦��
    //		String operLan =  this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
    //		String provId =  this.getGlobalVar(GlobalVariableHelper.PROV_ID);
    //		String operId =  this.getGlobalVar(GlobalVariableHelper.OPER_ID);
    //		String departId =  this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
    //		if(operLan.equals("1") && provId.equals(String.valueOf(LocalType.GX))){
    //			lanId="";
    //		}
    //		if (lanId != null && lanId.trim().length() > 0) {
    //			map.put("lanId", lanId);
    //		}
    //		if (storageId != null && storageId.trim().length() > 0) {
    //			map.put("storageId", storageId);
    //		}
    //		if (storageName != null && storageName.trim().length() > 0) {
    //			map.put("storageName", storageName);
    //		}
    //		if (operId != null && operId.trim().length() > 0) {
    //			map.put("operId", operId);
    //		}
    //		if (departId != null && departId.trim().length() > 0) {
    //			map.put("departId", departId);
    //		}
    //		map.put("type", "1");//���룬���ֿ���Ϣ����ҳ��ֿ���������
    //		SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
    //				SrStorageHome.class);
    //		return bean.qryStorageTree(map);
    //	}

    /**
     * �����Ӳֿ�����Ϣ
     *
     * @param map
     *            Map
     * @return String
     */
    public String qryChildStorageTree(String upStorageId)
        throws Exception {
        Map map = new HashMap();
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);

        //�����������ŵ����⴦��
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);

        if (operLan.equals("1") && provId.equals(String.valueOf(LocalType.GX))) {
            lanId = "";
        }

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            map.put("lanId", lanId);
        }

        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
            map.put("upStorageId", upStorageId);
        }

        /*SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
                        SrStorageHome.class);
        return bean.qryStorageTree(map);*/
//        SrStorageBo bo = new SrStorageBo();
//
//        return bo.qryStorageTree(map);
        
         return  (String )ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStorageTree" ,map);
    }

    public String saveRcStorage(RcStorageVO rcStorageVO, String lanId)
        throws Exception {
        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
        //        SrStorageHome.class);
        System.out.println("lan" + lanId);

        String oper_id = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        rcStorageVO.setC_time(DAOUtils.getFormatedDate());
        rcStorageVO.setC_oper_id(oper_id);

        //return bean.saveRcStorage(rcStorageVO, lanId + "@" + provId); //����provid��bo����
        Map map = new HashMap();
        map.put("rcStorageVO", rcStorageVO);
        map.put("lanIdprovId", lanId + "@" + provId);
        
        return  (String)ServiceManager.callJavaBeanService("SRSTORAGEBO","saveRcStorage" ,map);
    }

    /**
     * ��������ֿⲿ�Ŷ�Ӧ��ϵ
     *
     * @return
    * @throws Exception
     *
     */
    public boolean saveMultiStorageDepartRela(
        StorageDepartRelaVO storageDepartRelaVO) throws Exception {
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        storageDepartRelaVO.setStorageId(storageDepartRelaVO.getStorageId() +
            "@" + provId); //���룬�Ա��������Ǹ�ʡ�ݣ���bo���ٽ�operId��ԭ

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.saveMultiStorageDepartRela(storageDepartRelaVO);
        
        Map map = new HashMap();
        map.put("storageDepartRelaVO", storageDepartRelaVO);
        Boolean result = (Boolean )ServiceManager.callJavaBeanService("SRSTORAGEBO","saveMultiStorageDepartRela" ,map);
        return result.booleanValue();
        
        
    }

    public void updateRcStorage(RcStorageVO rcStorageVO)
        throws Exception {
        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
        //        SrStorageHome.class);
        Map map = new HashMap();
        map.put("vo", rcStorageVO);
        
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();

//        String reworkIp = (this.getRequest() == null) ? ""
//                                                      : (this.getRequest()
//                                                             .getRemoteAddr());
        
        String reworkIp = (req == null) ? "": (req.getRemoteAddr());
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);
        //bean.updateRcStorage(map);
        ServiceManager.callJavaBeanService("SRSTORAGEBO","updateRcStorage" ,map);
    }

    public int removeRcStorage(RcStorageVO rcStorageVO)
        throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",SrStorageHome.class);
        Map map = new HashMap();
        map.put("vo", rcStorageVO);

//        String reworkIp = (this.getRequest() == null) ? "": (this.getRequest().getRemoteAddr());
        
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        String reworkIp = (req == null) ? "": (req.getRemoteAddr());
        
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

        //int retv = bean.removeRcStorage(map);
        Integer  result = (Integer )ServiceManager.callJavaBeanService("SRSTORAGEBO","removeRcStorage" ,map);
        return result.intValue();   
    }

    /**
     * ��ѯ�ֿⲿ�Ŷ�Ӧ��ϵ
     *
     * @throws RemoteException
     *
     */
    //public PageModel qryStorageDepartRela(String storageId, String departId,int pageIndex, int pageSize) throws DAOSystemException, RemoteException {
    public PageModel qryStorageDepartRela(String storageId, String departId,int pageIndex, int pageSize) throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
        HashMap map = new HashMap();
        map.put("storageId", storageId);
        map.put("storageName", departId);

        //return bean.qryStorageDepartRela(map, pageIndex, pageSize);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStorageDepartRela" ,map);
        return result;        
    }

    /**
     * ����ֿⲿ�Ŷ�Ӧ��ϵ
     *
     * @return
     * @throws Exception
     *
     */
    public boolean saveStorageDepartRela(
        StorageDepartRelaVO storageDepartRelaVO) throws Exception {
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        storageDepartRelaVO.setStorageId(storageDepartRelaVO.getStorageId() +
            "@" + provId); //���룬�Ա��������Ǹ�ʡ�ݣ���bo���ٽ�operId��ԭ

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);

//        return bean.saveStorageDepartRela(storageDepartRelaVO);
        
        Map map = new HashMap();
        map.put("storageDepartRelaVO", storageDepartRelaVO);
        Boolean result = (Boolean )ServiceManager.callJavaBeanService("SRSTORAGEBO","saveStorageDepartRela" ,map);
        return result.booleanValue();
    }

    /**
     * �޸Ĳֿⲿ�Ŷ�Ӧ��ϵ
     * @throws Exception
     *
     */
    public void updateStorageDepartRela(
        StorageDepartRelaVO storageDepartRelaVO, String oldDepartId)
        throws Exception {
        //		String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        //		String newDepId = storageDepartRelaVO.getDepartId();
        //		storageDepartRelaVO.setStorageId(storageDepartRelaVO.getStorageId()+"@"+provId);//���룬�Ա��������Ǹ�ʡ�ݣ���bo���ٽ�operId��ԭ
        //		if(provId.equals("11")){
        //			storageDepartRelaVO.setDepartId(oldDepartId);
        //			removeStorageDepartRela(storageDepartRelaVO);//ɾ���ɵ�
        //			
        //			storageDepartRelaVO.setDepartId(newDepId);
        //			removeStorageDepartRela(storageDepartRelaVO);//�����µ�
        //		}else{

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//        bean.updateStorageDepartRela(storageDepartRelaVO, oldDepartId);
        
        Map map = new HashMap();
        map.put("storageDepartRelaVO", storageDepartRelaVO);
        map.put("oldDepartId", oldDepartId);
        ServiceManager.callJavaBeanService("SRSTORAGEBO","updateStorageDepartRela" ,map);

        //		}
    }

    /**
     * ɾ���ֿⲿ�Ŷ�Ӧ��ϵ
     * @throws Exception
     *
     */
    public String removeStorageDepartRela(
        StorageDepartRelaVO storageDepartRelaVO) throws Exception {
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        storageDepartRelaVO.setStorageId(storageDepartRelaVO.getStorageId() +
            "@" + provId); //���룬�Ա��������Ǹ�ʡ�ݣ���bo���ٽ�operId��ԭ

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.removeStorageDepartRela(storageDepartRelaVO);
        
        Map map = new HashMap();
        map.put("storageDepartRelaVO", storageDepartRelaVO);
        String result = (String )ServiceManager.callJavaBeanService("SRSTORAGEBO","removeStorageDepartRela" ,map);
        return result;
    }

    /**
     * ��ѯ����Ա�ֿ��ϵ
     *
     * @throws RemoteException
     *
     */
    //ublic PageModel qryMpStorage(String storageId, String operId,int pageIndex, int pageSize) throws DAOSystemException, RemoteException {
    public PageModel qryMpStorage(String storageId, String operId,
        int pageIndex, int pageSize) throws Exception {
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
        HashMap map = new HashMap();
        map.put("storageId", storageId);
        map.put("operId", operId);

        //return bean.qryMpStorage(map, pageIndex, pageSize);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryMpStorage" ,map);
        return result;   
    }

    /**
     * �������Ա�ֿ��ϵ
     *
     * @return
     * @throws Exception
     *
     */
    public boolean saveMpStorage(MpStorageVO mpStorageVO)
        throws Exception {
        
        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
          //      SrStorageHome.class);

        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        mpStorageVO.setStorageId(mpStorageVO.getStorageId() + "@" + provId); //���룬�Ա��������Ǹ�ʡ�ݣ���bo���ٽ�operId��ԭ

        //return bean.saveMpStorage(mpStorageVO);
        Map map = new HashMap();
        map.put("mpStorageVO", mpStorageVO);
        Boolean result = (Boolean )ServiceManager.callJavaBeanService("SRSTORAGEBO","saveMpStorage" ,map);
        return result.booleanValue();
         
    }

    /**
     * �޸Ĳ���Ա�ֿ��ϵ
     * @throws Exception
     *
     */
    public void updateMpStorage(MpStorageVO mpStorageVO)
        throws Exception {
        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
          //      SrStorageHome.class);
        //bean.updateMpStorage(mpStorageVO);
        
        Map map = new HashMap();
        map.put("mpStorageVO", mpStorageVO);
        ServiceManager.callJavaBeanService("SRSTORAGEBO","updateMpStorage" ,map);
    }

    /**
     * ɾ������Ա�ֿ��ϵ
     * @throws Exception
     *
     */
    public String removeMpStorage(MpStorageVO mpStorageVO)
        throws Exception {
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        mpStorageVO.setStorageId(mpStorageVO.getStorageId() + "@" + provId); //���룬�Ա��������Ǹ�ʡ�ݣ���bo���ٽ�operId��ԭ

        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
         //       SrStorageHome.class);
        //return bean.removeMpStorage(mpStorageVO);
         Map map = new HashMap();
         map.put("mpStorageVO", mpStorageVO);
         return (String )ServiceManager.callJavaBeanService("SRSTORAGEBO","removeMpStorage" ,map);
    }

    public PageModel getOrganizationInfo(String sType, String sContent,
        String sLanId, int pi, int ps) throws Exception {
        PageModel pm = new PageModel();
////        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
////                SrStorageHome.class);
//        pm = bean.getOrganizationInfo(sType, sContent, sLanId, pi, ps);
        
        Map map = new HashMap();
        
        map.put("sType", sType);
        map.put("sContent", sContent);
        map.put("sLanId", sLanId);
        
        map.put("pageIndex",new Integer(pi));
        map.put("pageSize",new Integer(ps));
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("STAFFBO","getOrganizationInfo" ,map);
        return result;  
    }

    /**
     * ��ѯ�ֿ���������
     *
     * @param map
     *            Map
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryStockLimit(String storageId, int pageIndex, int pageSize)
        throws Exception {
        if ((storageId == null) || (storageId.trim().length() < 1)) {
            return new PageModel();
        }

        Map map = new HashMap();
        map.put("storageId", storageId);

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.qryStockLimit(map, pageIndex, pageSize);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStockLimit" ,map);
        return result;
    }

    /**
     * ����ֿ�Ŀ����������
     *
     * @param MpStorageVO
     *            mpStorageVO
     * @throws DAOSystemException
     * @return String
     */
    public int saveStockLimit(RcStockLimitVO vo, String opeType)
        throws Exception {
        Map map = new HashMap();
        map.put("vo", vo);
        map.put("opeType", opeType);

//        String reworkIp = (this.getRequest() == null) ? ""
//                                                      : (this.getRequest()
//                                                             .getRemoteAddr());
        
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        String reworkIp = (req == null) ? "": (req.getRemoteAddr());
        
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.saveStockLimit(map);
        
        Integer  result = (Integer )ServiceManager.callJavaBeanService("SRSTORAGEBO","saveStockLimit" ,map);
        return result.intValue();
    }

    /**
     * ɾ���ֿ�����������
     *
     * @param RcStockLimitVO
     * @throws DAOSystemException
     */
    public long removeStockLimit(RcStockLimitVO vo) throws Exception {
        Map map = new HashMap();
        map.put("vo", vo);

//        String reworkIp = (this.getRequest() == null) ? ""
//                                                      : (this.getRequest()
//                                                             .getRemoteAddr());
        
        HttpServletRequest req = RequestContext.getContext().getHttpRequest();
        String reworkIp = (req == null) ? "": (req.getRemoteAddr());
        
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        map.put("reworkIp", reworkIp);
        map.put("operId", operId);

//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
//
//        return bean.removeStockLimit(map);
        
        Long result = (Long )ServiceManager.callJavaBeanService("SRSTORAGEBO","removeStockLimit" ,map);
        return result.longValue();
        
        
    }

    /**
     * ��ѯ�ֿ���Ϣ�����ݲ���Ա�Ĺ��ź������ڲ�����Ȩ�����Ĳ��ż��ϡ�����֮��
     *
     * @param map
     *            Map
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @return PageModel
     */
    public PageModel qryStorageByOperDept(String storageName,
        String storageCode, String upStorageId, int pageIndex, int pageSize)
        throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);

        if (operLan.equals("1") && provId.equals(String.valueOf(LocalType.GX))) {
            lanId = "";
        }

        Map map = new HashMap();
        map.put("lanId", lanId);
        map.put("provId", provId);
        map.put("operId", operId);
        map.put("departId", departId);

        if ((storageName != null) && (storageName.trim().length() > 0)) {
            map.put("storageName", storageName);
        }

        if ((storageCode != null) && (storageCode.trim().length() > 0)) {
            map.put("storageCode", storageCode);
        }

        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
            map.put("upStorageId", upStorageId);
        }

        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStorageByOperDept" ,map);
        return result;
    }

    public PageModel qryStorageByOper(String storageName, String storageCode,
        String operId, int pageIndex, int pageSize) throws Exception {
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        String lanId = this.getGlobalVar(GlobalVariableHelper.LAN_ID);

        if (operLan.equals("1") && provId.equals(String.valueOf(LocalType.GX))) {
            lanId = "";
        }

        Map map = new HashMap();
        map.put("lanId", lanId);
        map.put("provId", provId);
        map.put("operId", operId);
        map.put("departId", departId);

        if ((storageName != null) && (storageName.trim().length() > 0)) {
            map.put("storageName", storageName);
        }

        if ((storageCode != null) && (storageCode.trim().length() > 0)) {
            map.put("storageCode", storageCode);
        }

        SrStorageBo bo = new SrStorageBo();

        return bo.qryStorageByOper(map, pageIndex, pageSize);
    }

    //��ѯ�ֿ���Ϣ�����ݲ���Ա�Ĺ��ź������ڲ�����Ȩ�����Ĳ���/lanId������֮��
    public PageModel qryStorageByOperDeptAndLanId(String storageName,
        String storageCode, String upStorageId, String lanId, int pageIndex,
        int pageSize) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
        String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);

        //		String lanId =  this.getGlobalVar(GlobalVariableHelper.LAN_ID);
        if (operLan.equals("1") && provId.equals(String.valueOf(LocalType.GX))) {
            lanId = "";
        }

        Map map = new HashMap();
        map.put("lanId", lanId);
        map.put("provId", provId);
        map.put("operId", operId);
        map.put("departId", departId);

        if ((storageName != null) && (storageName.trim().length() > 0)) {
            map.put("storageName", storageName);
        }

        if ((storageCode != null) && (storageCode.trim().length() > 0)) {
            map.put("storageCode", storageCode);
        }

        if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
            map.put("upStorageId", upStorageId);
        }

        SrStorageBo bo = new SrStorageBo();

        return bo.qryStorageByOperDeptAndLanId(map, pageIndex, pageSize);
    }

    public int detectRela(String storageId) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);

        //		String provId = this.getGlobalVar(GlobalVariableHelper.PROV_ID);
        if ((operLan != null) && operLan.equals("1")) {
            return 1;
        }

        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage", SrStorageHome.class);
        //return bean.detectRela(operId, departId, storageId);
        
        HashMap map = new HashMap();
        map.put("operId", operId);
        map.put("departId", departId);
        map.put("storageId", storageId);
        
        Integer result = (Integer )ServiceManager.callJavaBeanService("SRSTORAGEBO","detectRela" ,map);
        return result.intValue(); 
    }

    /**
     * ����������ѯ��Ӧ�Ĳֿ���ϸ��Ϣ
     *
     */
    public PageModel qryStorageByCond(String lanId, String departId,
        String operId, String storageName, String storageId, int pageIndex,
        int pageSize) throws Exception {
        String loginId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String loginDepartId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
        //SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage", SrStorageHome.class);
        
        HashMap map = new HashMap();

        map.put("operLan", operLan);
        map.put("lanId", lanId);
        map.put("departId", departId);
        map.put("operId", operId);
        map.put("storageName", storageName);
        map.put("loginId", loginId);
        map.put("loginDepartId", loginDepartId);
        map.put("storageId", storageId);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        PageModel pm = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStorageByCond" ,map);
        //PageModel pm = bean.qryStorageByCond(map, pageIndex, pageSize);

        return pm;
    }

    /**
     * ����������ѯ��Ӧ�Ĳֿ���ϸ��Ϣ,���Ӳֿ�����
     *
     */
    public PageModel qryStorageByCond2(String lanId, String departId,
        String operId, String storageName, String storageId,
        String storageType, int pageIndex, int pageSize)
        throws Exception {
        String loginId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String loginDepartId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);
        String operLan = this.getGlobalVar(GlobalVariableHelper.OPER_LAN);
//        SrStorage bean = (SrStorage) EJBHelper.creatEJB("ejb/SrStorage",
//                SrStorageHome.class);
        HashMap map = new HashMap();

        map.put("operLan", operLan);
        map.put("lanId", lanId);
        map.put("departId", departId);
        map.put("operId", operId);
        map.put("storageName", storageName);
        map.put("loginId", loginId);
        map.put("loginDepartId", loginDepartId);
        map.put("storageId", storageId);
        map.put("storageType", storageType);
        
        map.put("pageIndex",new Integer(pageIndex));
        map.put("pageSize",new Integer(pageSize));

        //PageModel pm = bean.qryStorageByCond(map, pageIndex, pageSize);
        
        PageModel result = (PageModel)ServiceManager.callJavaBeanService("SRSTORAGEBO","qryStorageByCond" ,map);
        return result; 
    }

    public String checkRight(String storageId) throws Exception {
        String operId = this.getGlobalVar(GlobalVariableHelper.OPER_ID);
        String departId = this.getGlobalVar(GlobalVariableHelper.DEPART_ID);

        return new SrStorageBo().checkRight(storageId, operId, departId);
    }
}
