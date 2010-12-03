package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.common.SqlExcuteByStr;
import com.ztesoft.crm.salesres.dao.RcApplicationDAO;
import com.ztesoft.crm.salesres.dao.RcImsiDAO;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.RcNoImsiRelateDAO;
import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.dao.RcOrderDAO;
import com.ztesoft.crm.salesres.dao.RcOrderExcDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.RcSimDAO;
import com.ztesoft.crm.salesres.dao.RcSimEvdoDAO;
import com.ztesoft.crm.salesres.dao.RcSimRelaDAO;
import com.ztesoft.crm.salesres.dao.RcStorageDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.exception.BizLogicException;
import com.ztesoft.crm.salesres.exception.DuplicateException;
import com.ztesoft.crm.salesres.exception.LogicInfoException;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcImsiVO;
import com.ztesoft.crm.salesres.vo.RcNoImsiRelateVO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;
import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.crm.salesres.vo.RcOrderExcVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;
import com.ztesoft.crm.salesres.vo.RcSimEvdoVO;
import com.ztesoft.crm.salesres.vo.RcSimRelaVO;
import com.ztesoft.crm.salesres.vo.RcSimVO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;


/**
 * RcSimBO.java
 * function:	
 * created by: nik
 * 2010-1-12下午07:29:40
 * modifid: ...
 */
public class RcSimBO extends DictAction {
    public RcSimBO() {
    }

    /**
     *
      SIM卡查询；
     */
    //public PageModel QuerySIMInfo(Map mp, int pageIndex, int pageSize)
      //  throws DAOSystemException {
    public PageModel QuerySIMInfo(DynamicDict dto)
      throws FrameException {
        String sql = " and s.rc_type=-1 ";
  
        Map mp = (Map)dto.getValueByName("parameter");
        int pageIndex = ((Integer)mp.get("pageIndex")).intValue();
        int pageSize = ((Integer)mp.get("pageSize")).intValue();
        
        if (mp == null) {
            return new PageModel();
        }

        String operId = null;
        String departId = null;

        if ((mp.get("backup") != null) && !mp.get("backup").equals("")) {
            sql += (" and R.backup='" + mp.get("backup").toString().trim() +
            "' ");
        }

        if ((mp.get("startCode") != null) && !mp.get("startCode").equals("")) {
            sql += (" and R.RESOURCE_INSTANCE_CODE >= " +
            mp.get("startCode").toString().trim());
        }

        if ((mp.get("endCode") != null) && !mp.get("endCode").equals("")) {
            sql += (" and R.RESOURCE_INSTANCE_CODE <= " +
            mp.get("endCode").toString().trim());
        }

        if ((mp.get("rescState") != null) && !mp.get("rescState").equals("")) {
            sql += (" and R.RESOURCE_STATE = '" +
            mp.get("rescState").toString().trim() + "'");
        }

        if ((mp.get("simType") != null) && !mp.get("simType").equals("")) {
            sql += (" and R.SIM_TYPE = '" +
            mp.get("simType").toString().trim() + "'");
        }

        if ((mp.get("startNoCode") != null) &&
                !mp.get("startNoCode").equals("")) {
            sql += (" and R.DN_NO >= " +
            mp.get("startNoCode").toString().trim());
        }

        if ((mp.get("endNoCode") != null) && !mp.get("endNoCode").equals("")) {
            sql += (" and R.DN_NO <= " + mp.get("endNoCode").toString().trim());
        }

        if ((mp.get("startExpDate") != null) &&
                !mp.get("startExpDate").equals("")) {
            sql += (" and R.EXP_DATE >= to_date('" +
            mp.get("startExpDate").toString().trim() + "','yyyy-mm-dd')");
        }

        if ((mp.get("endExpDate") != null) && !mp.get("endExpDate").equals("")) {
            sql += (" and R.EXP_DATE <= to_date('" +
            mp.get("endExpDate").toString().trim() + "','yyyy-mm-dd')");
        }

        if ((mp.get("storageId") != null) && !mp.get("storageId").equals("")) {
            sql += (" and R.STORAGE_ID =" + mp.get("storageId"));
        }

        if ((mp.get("capacity") != null) && !mp.get("capacity").equals("")) {
            sql += (" and R.capacity =" + mp.get("capacity"));
        }

        if ((mp.get("cServType") != null) && !mp.get("cServType").equals("")) {
            sql += (" and R.c_serv_type ='" + mp.get("cServType") + "'");
        }

        if ((mp.get("simChipType") != null) &&
                !mp.get("simChipType").equals("")) {
            sql += (" and R.sim_chip_type='" +
            mp.get("simChipType").toString().trim() + "' ");
        }

        String cond_right = null;

        if (mp.get("operId") != null) {
            operId = (String) mp.get("operId");
            cond_right = " (select STORAGE_ID from mp_storage where oper_id=" +
                operId + ") ";
        }

        if (mp.get("departId") != null) {
            departId = (String) mp.get("departId");

            if (cond_right != null) {
                cond_right += (" union (select STORAGE_ID from STORAGE_DEPART_RELA where depart_id=" +
                departId + ")");
            } else {
                cond_right = " (select STORAGE_ID from STORAGE_DEPART_RELA where depart_id=" +
                    departId + ")";
            }
        }

        if ((cond_right != null) && (cond_right.trim().length() > 0)) {
            sql += (" and R.STORAGE_ID in(" + cond_right + ")");
        }

        sql += " order by r.resource_instance_id";

        RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();

        return PageHelper.popupPageModel(dao, sql, pageIndex, pageSize);
    }

    /**
     * importRcSimInfo
     *SIM按文件导入，增加SIM,IMSI表记录
     * @param pList List
     * @param fileList Collection
     * @return List
     * //营销资源ID暂时为空，留待以后处理；
     */
    //public List importRcSimInfo(Map map) throws DAOSystemException {
    public List importRcSimInfo(DynamicDict dto) throws FrameException {
        
        Map map = (Map)dto.getValueByName("parameter") ;
        
        List backList = new ArrayList();
        List params = new ArrayList();
        List listTemp = null;

        if (map == null) {
            return backList;
        }

        List pList = null;
        Collection fileList = null;

        if (map.get("pList") != null) {
            pList = (List) map.get("pList");
        }

        if (map.get("fileList") != null) {
            fileList = (Collection) map.get("fileList");
        }

        if ((pList == null) || (pList.size() < 1) || (fileList == null) ||
                (fileList.size() < 1)) {
            return new ArrayList();
        }

        String operId = null;
        String departId = null;

        if (map.get("operId") != null) {
            operId = (String) map.get("operId");
        }

        if (map.get("departId") != null) {
            departId = (String) map.get("departId");
        }

        String nowDate = DAOUtils.getFormatedDate();
        String exceptionMsg = "";
        String storageId = (String) pList.get(0);
        String simType = (String) pList.get(1);
        String effDate = (String) pList.get(2);
        String expDate = (String) pList.get(3);
        String salesRescId = (String) pList.get(4);

        Iterator iter = fileList.iterator();
        int counter = 0;
        String[] arrays = null;

        String[] sqlArrs = new String[fileList.size()]; // rc_order_list需要批量执行的语句
                                                        // 插入rc_order_list中的主键

        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");
        RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcSimEvdoDAO evdoDao = SrNSDAOFactory.getInstance().getRcSimEvdoDAO();

        //对循环的每对数据操作，先插SIM表，再插IMSI表（需要先到rc_imsi_seg表去做判断），处在同个事物里；
        while (iter.hasNext()) {
            try {
                arrays = (String[]) iter.next();

                //SIM表主键生成序列；
                String lon = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                          .getNextSequence("RC_ENTITY",
                        "RcEntity_ID");

                String imsiId = arrays[0];
                String esn = arrays[1];
                String resInsCode = arrays[2];
                String pin1 = arrays[3];
                String pin2 = arrays[4];
                String pukNo = arrays[5];
                String puk2No = arrays[6];
                String imsiId2 = arrays[7];
                String ki = arrays[8];
                String acc = arrays[9];
                String smsp = arrays[10];
                String hrpdSs = arrays[11];
                String hrpdUpp = arrays[12];

                //----modified by panyazong 改为变量绑定，预编译方式   20090618
                //    	listTemp = simDao.findByCond(" and RESOURCE_INSTANCE_CODE='" +resInsCode+ "'");
                String SQL_SELECT = "SELECT * FROM RC_SIM where RESOURCE_INSTANCE_CODE=?";
                String[] sqlParams = new String[1];
                sqlParams[0] = resInsCode;

                SqlComDAO com = SrDAOFactory.getInstance().getSqlComDAO();
                boolean b = com.checkExist(SQL_SELECT, sqlParams);

                //		if (listTemp != null && listTemp.size() > 0) {
                if (b == true) {
                    throw new DuplicateException(" -- ICCD号已经存在！");
                }

                //封装成VO
                RcSimVO vo = new RcSimVO();
                vo.setInitTime(nowDate);
                vo.setImsiId(imsiId);
                vo.setRescInstanceId(lon);
                vo.setPukNo(pukNo);
                vo.setEsn(esn);
                vo.setPin1(pin1);
                vo.setPin2(pin2);
                vo.setPuk2No(puk2No);
                vo.setStorageId(storageId);
                vo.setEffDate(effDate);
                vo.setExpDate(expDate);
                vo.setRescInstanceCode(resInsCode);
                vo.setSimType(simType);
                vo.setState(ParamsConsConfig.RcEntityStateValide); //状态有效
                vo.setRescState(ParamsConsConfig.RcEntityFreeState); //资源状态可用A:可用，B：占用
                vo.setBackup("1"); // 设置为备用卡
                vo.setSalesRescId(salesRescId);
                vo.setImsiId2(imsiId2);
                vo.setKi(ki);
                vo.setAcc(acc);
                vo.setSmsp(smsp);
                //将SIMVO插入到表中
                simDao.insert(vo);

                //封装成VO
                RcImsiDAO imsiDao = SrNSDAOFactory.getInstance().getRcImsiDAO();
                RcImsiVO voo = new RcImsiVO();
                voo.setImsiId(imsiId);
                voo.setRescInstanceId(lon);
                //去SEG表中去查询一把
                voo = (RcImsiVO) SrNSDAOFactory.getInstance().getRcImsiDAO()
                                               .getResultForValidate(voo);

                if (voo != null) {
                    voo.setMasterFlag("0"); ////主IMSI号
                    voo.setState(ParamsConsConfig.RescState_preMadeCard); // imsi状态已制卡
                }

                //将IMSIVO插入到表中（有可能主键冲突）
                imsiDao.insert(voo);

                // 如果上传的imsi2码不为空，则插入imsi2
                if ((vo.getImsiId2() != null) &&
                        (vo.getImsiId2().trim().length() > 0)) {
                    RcImsiVO voo2 = new RcImsiVO();
                    voo2.setImsiId(imsiId2);
                    voo2.setRescInstanceId(lon);
                    //去SEG表中去查询一把
                    voo2 = (RcImsiVO) imsiDao.getResultForValidate(voo2);

                    if (voo2 != null) {
                        voo2.setMasterFlag("1"); ////次IMSI号
                        voo2.setState(ParamsConsConfig.RescState_preMadeCard); // 状态已制卡
                    }

                    //将IMSIVO2插入到表中（有可能主键冲突）
                    imsiDao.insert(voo2);
                }

                // 新增evdo信息
                if (RcSimVO.SimType_evdo.equals(simType)) {
                    if ((hrpdSs != null) && (hrpdSs.trim().length() > 0)) {
                        RcSimEvdoVO evdoVO1 = new RcSimEvdoVO();
                        evdoVO1.setAttrvalue(hrpdSs);
                        evdoVO1.setRescInstanceId(lon);
                        evdoVO1.setAttrid("1");
                        evdoVO1.setAttrname("HRPD_SS");
                        evdoDao.insert(evdoVO1);
                    }

                    if ((hrpdUpp != null) && (hrpdUpp.trim().length() > 0)) {
                        RcSimEvdoVO evdoVO2 = new RcSimEvdoVO();
                        evdoVO2.setAttrvalue(hrpdUpp);
                        evdoVO2.setRescInstanceId(lon);
                        evdoVO2.setAttrid("1");
                        evdoVO2.setAttrname("HRPDUPP");
                        evdoDao.insert(evdoVO2);
                    }
                }

                // 插入rc_order_list订单记录
                //sqlArrs[counter] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE )"
                //              + " values(" + orderId + ","+lon+","+salesRescId+",'" + resInsCode + "')";
                //      插入rc_order_list订单记录
                sqlArrs = new String[4];
                sqlArrs[0] = orderId;
                sqlArrs[1] = lon;
                sqlArrs[2] = salesRescId;
                sqlArrs[3] = resInsCode;
                params.add(sqlArrs);
                counter++;
            } catch (DAOSystemException dex) {
                dex.printStackTrace();

                //将出错的数据一起抛出
                String errinfo = "--IMSI码重复，或未知错误!";

                if (dex instanceof LogicInfoException) {
                    errinfo = " -- " + dex.getMessage();
                }

                throw new DAOSystemException(arrays[0] + " " + arrays[1] + " " +
                    arrays[2] + " " + arrays[3] + " " + arrays[4] + " " +
                    arrays[5] + " " + arrays[6] + " " + arrays[7] + " " +
                    arrays[8] + " " + arrays[9] + " " + arrays[10] + " " +
                    arrays[11] + " " + arrays[12] + " " + errinfo);
            }
        } //循环结束

        // 插入rc_order_list的日志
        this.insertOrderInfo("C", new String[] { salesRescId }, operId,
            departId, storageId, "", String.valueOf(counter), null, null,
            new String[] { orderId }, "");

        if ((sqlArrs != null) && (sqlArrs.length > 0)) {
            SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
            String sql = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE ) values( ?,?,?,?)";
            comDao.batchExecuteExtends(params, sql); // 批量执行插入rc_order_list的记录
                                                     //comDao.batchExecute(sqlArrs); // 批量执行插入rc_order_list的记录
        }

        //返回值。。。
        backList.add(String.valueOf(counter));

        return backList;
    }

    /**
     * importRcBaikaSimInfo
     * SIM白卡按文件导入，增加SIM表记录
     * @param pList List
     * @param fileList Collection
     * @return List
     * //营销资源ID暂时为空，留待以后处理；
     */
    //public List importRcBaikaSimInfo(Map map) throws DAOSystemException {
    public List importRcBaikaSimInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        //返回值。。。
        List backList = new ArrayList();

        if (map == null) {
            return backList;
        }

        int counter = 0;
        List pList = null;
        List fileList = null;

        if (map.get("pList") != null) {
            pList = (List) map.get("pList");
        }

        if (map.get("fileList") != null) {
            fileList = (List) map.get("fileList");
        }

        if ((pList == null) || (pList.size() < 1) || (fileList == null) ||
                (fileList.size() < 1)) {
            backList.add(String.valueOf(0));

            return backList;
        }

        String operId = null;
        String departId = null;

        if (map.get("operId") != null) {
            operId = (String) map.get("operId");
        }

        if (map.get("departId") != null) {
            departId = (String) map.get("departId");
        }

        String nowDate = DAOUtils.getFormatedDate();
        String exceptionMsg = "";
        String storageId = (String) pList.get(0);
        String simType = (String) pList.get(1);
        String effDate = (String) pList.get(2);
        String expDate = (String) pList.get(3);
        String salesRescId = (String) pList.get(4);
        String[] arrays = null;
        RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcSimEvdoDAO evdoDao = SrNSDAOFactory.getInstance().getRcSimEvdoDAO();
        String[] sqlArrs = new String[fileList.size()]; // rc_order_list需要批量执行的语句
                                                        // 插入rc_order_list中的主键

        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");

        //对循环的每对数据操作，插SIM表，再插IMSI表（需要先到rc_imsi_seg表去做判断），处在同个事务里；
        String esn = null;
        String resInsCode = null;
        String pin1 = null;
        String pin2 = null;
        String pukNo = null;
        String puk2No = null;
        String hrpdSs = null;
        String hrpdUpp = null;

        for (int i = 0; i < fileList.size(); i++) {
            try {
                arrays = (String[]) fileList.get(i);
                // ESN ICCID PIN1 PIN2 PUK1 PUK2
                esn = arrays[0];
                resInsCode = arrays[1];
                pin1 = arrays[2];
                pin2 = arrays[3];
                pukNo = arrays[4];
                puk2No = arrays[5];
                hrpdSs = arrays[6];
                hrpdUpp = arrays[7];

                RcSimVO vo = null;

                // 查询号码是否重复
                //        List list = simDao.findByCond(" and RESOURCE_INSTANCE_CODE='" + resInsCode + "'");
                //----modified by panyazong 改为变量绑定，预编译方式   20090618
                //    	listTemp = simDao.findByCond(" and RESOURCE_INSTANCE_CODE='" +resInsCode+ "'");
                String SQL_SELECT = "SELECT * FROM RC_SIM where RESOURCE_INSTANCE_CODE=?";
                String[] sqlParams = new String[1];
                sqlParams[0] = resInsCode;

                List list = simDao.findBySql(SQL_SELECT, sqlParams);

                if ((list != null) && (list.size() > 0)) {
                    throw new DuplicateException(esn + " " + resInsCode + " " +
                        pin1 + " " + pin2 + " " + pukNo + " " + puk2No +
                        "  -- 错误，ICCID号重复!");
                }

                //SIM表主键生成序列；
                String lon = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                          .getNextSequence("RC_ENTITY",
                        "RcEntity_ID");

                //封装成VO
                vo = new RcSimVO();
                vo.setInitTime(nowDate);
                vo.setRescInstanceId(lon);
                vo.setPukNo(pukNo);
                vo.setEsn(esn);
                vo.setPin1(pin1);
                vo.setPin2(pin2);
                vo.setPuk2No(puk2No);
                vo.setStorageId(storageId);
                vo.setSimType(simType);
                vo.setEffDate(effDate);
                vo.setExpDate(expDate);
                vo.setRescInstanceCode(resInsCode);
                vo.setState(ParamsConsConfig.RcEntityStateValide); //状态有效
                vo.setRescState(ParamsConsConfig.RcEntityFreeState); //资源状态可用A:可用，B：占用
                vo.setBackup("2"); // 设置为白卡
                vo.setSalesRescId(salesRescId);
                //将SIMVO插入到表中
                simDao.insert(vo);

                // 新增evdo信息
                if (RcSimVO.SimType_evdo.equals(simType)) {
                    if ((hrpdSs != null) && (hrpdSs.trim().length() > 0)) {
                        RcSimEvdoVO evdoVO1 = new RcSimEvdoVO();
                        evdoVO1.setAttrvalue(hrpdSs);
                        evdoVO1.setRescInstanceId(lon);
                        evdoVO1.setAttrid("1");
                        evdoVO1.setAttrname("HRPD_SS");
                        evdoDao.insert(evdoVO1);
                    }

                    if ((hrpdUpp != null) && (hrpdUpp.trim().length() > 0)) {
                        RcSimEvdoVO evdoVO2 = new RcSimEvdoVO();
                        evdoVO2.setAttrvalue(hrpdUpp);
                        evdoVO2.setRescInstanceId(lon);
                        evdoVO2.setAttrid("1");
                        evdoVO2.setAttrname("HRPDUPP");
                        evdoDao.insert(evdoVO2);
                    }
                }

                // 插入rc_order_list订单记录
                sqlArrs[i] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE )" +
                    " values(" + orderId + "," + lon + "," + salesRescId +
                    ",'" + resInsCode + "')";

                counter++;
            } catch (LogicInfoException e) {
                throw e;
            } catch (DAOSystemException dex) {
                dex.printStackTrace();

                //将出错的数据一起抛出
                throw new DAOSystemException(arrays[0] + " " + arrays[1] +
                    "  -- 未知的数据库错误!");
            }
        } //循环结束

        // 插入rc_order_list的日志
        this.insertOrderInfo("C", new String[] { salesRescId }, operId,
            departId, storageId, "", String.valueOf(counter), null, null,
            new String[] { orderId }, "");

        if ((sqlArrs != null) && (sqlArrs.length > 0)) {
            SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
            comDao.batchExecute(sqlArrs); // 批量执行插入rc_order_list的记录
        }

        backList.add(String.valueOf(counter));

        return backList;
    }

    /**
     * 根据文件上传方式生成号码
     * @param vo RcNoVO
     * @param list List
     * @return String
     */
    //public Map importRcTaoSimInfo(Map map, int importType) {
    public Map importRcTaoSimInfo(DynamicDict dto) throws FrameException {
        
        Map map = (Map)dto.getValueByName("parameter");
        int importType = ((Integer)map.get("importType")).intValue();
        
        if (map == null) {
            return null;
        }

        Map rtnMap = new HashMap();
        RcSimVO vo = (RcSimVO) map.get("vo");
        List list = (List) map.get("list");

        String result = "1";
        String alertInfo = "";
        String txtInfo = "";

        if ((vo == null) || (list == null) || (list.size() < 1)) {
            alertInfo = "缺少主卡的属性或上传主卡个数为0,导入主卡失败!";
            txtInfo = "缺少主卡的属性或上传主卡号码个数为0,导入主卡失败!";
            rtnMap.put("result", "0");
            rtnMap.put("alertInfo", alertInfo);
            rtnMap.put("txtInfo", txtInfo);

            return rtnMap;
        }

        String operId = null;
        String departId = null;

        if (map.get("operId") != null) {
            operId = (String) map.get("operId");
        }

        if (map.get("departId") != null) {
            departId = (String) map.get("departId");
        }

        if (importType == 1) {
            rtnMap = this.importRcTaoSimInfoExistNo(vo, list, operId, departId);
        } else if (importType == 2) {
            rtnMap = this.importRcTaoSimInfoNotExistNo(vo, list, operId,
                    departId);
        }

        return rtnMap;
    }

    /**
     * 根据文件上传方式生成号码
     * @param vo RcNoVO
     * @param list List
     * @return String
     */
    public Map importRcTaoSimInfoExistNo(RcSimVO vo, List list, String operId,
        String departId) {
        Map rtnMap = new HashMap();
        String alertInfo = "";
        int successCount = 0;
        int failCount = 0;
        String currDate = DAOUtils.getFormatedDate();
        RcImsiVO ismiVOTemp = null;
        RcImsiVO ismiVOTemp2 = null;
        RcSimEvdoVO simVOTemp = null;
        RcNoVO noVOTemp = null;
        List txtIngoList = new ArrayList();
        String imsiSegId = null;
        String imsiSegId2 = null;
        String lon = null;
        RcImsiDAO imsiDao = SrNSDAOFactory.getInstance().getRcImsiDAO();
        RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcNoDAO noDao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcNoImsiRelateDAO relateDao = SrNSDAOFactory.getInstance()
                                                    .getRcNoImsiRelateDAO();
        RcSimEvdoDAO evdoDao = SrNSDAOFactory.getInstance().getRcSimEvdoDAO();
        List listTemp = null;
        String[] sqlArrs = new String[list.size()]; // rc_order_list需要批量执行的语句
                                                    // 插入rc_order_list中的主键

        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");

        for (int i = 0; i < list.size(); i++) {
            simVOTemp = (RcSimEvdoVO) list.get(i);
            // 修改rc_no表中的号码信息
            noVOTemp = noDao.findByCode(simVOTemp.getDnNo());

            if ((noVOTemp == null) || (noVOTemp.getRescState() == null) ||
                    !ParamsConsConfig.RescState_frozen.equals(
                        noVOTemp.getRescState())) {
                txtIngoList.add("号码:" + simVOTemp.getDnNo() +
                    "不存在,或状态不为封存状态，处理失败!");
                failCount++;

                continue;
            } else {
                // 处理插入sim卡和imsi卡号
                //----modified by panyazong 改为变量绑定，预编译方式   20090618
                //            listTemp = simDao.findByCond(" and RESOURCE_INSTANCE_CODE='" +
                //                                         simVOTemp.getRescInstanceCode() + "'");
                String SQL_SELECT = "SELECT * FROM RC_SIM where RESOURCE_INSTANCE_CODE=?";
                String[] sqlParams = new String[1];
                sqlParams[0] = simVOTemp.getRescInstanceCode();
                listTemp = simDao.findBySql(SQL_SELECT, sqlParams);

                if ((listTemp != null) && (listTemp.size() > 0)) {
                    txtIngoList.add("sim卡:" + simVOTemp.getRescInstanceCode() +
                        "已经存在，新增失败!");
                    failCount++;

                    continue;
                } else {
                    // 检查imsi码是否重复
                    ismiVOTemp = imsiDao.findByPrimaryKey(simVOTemp.getImsiId());

                    if ((ismiVOTemp != null) &&
                            (ismiVOTemp.getImsiId() != null) &&
                            (ismiVOTemp.getImsiId().trim().length() > 0)) {
                        txtIngoList.add("sim卡:" +
                            simVOTemp.getRescInstanceCode() + "的imsi码" +
                            simVOTemp.getImsiId() + "已经存在，新增失败!");
                        failCount++;

                        continue;
                    }

                    //////检验sim卡中的imsi码是否在rc_imsi表中，且在相应的imsi_seg段中
                    imsiSegId = imsiDao.qryImsiSeg(simVOTemp.getImsiId());

                    if (imsiSegId == null) {
                        txtIngoList.add("sim卡:" +
                            simVOTemp.getRescInstanceCode() + "的imsi码" +
                            simVOTemp.getImsiId() + "不在任何imsi段中，新增失败!");
                        failCount++;

                        continue;
                    }

                    // 检查次imsi2码是否有效
                    if ((simVOTemp.getImsiId2() != null) &&
                            (simVOTemp.getImsiId2().trim().length() > 0)) {
                        // 检验sim卡中的次imsi2码是否在rc_imsi表中
                        ismiVOTemp2 = imsiDao.findByPrimaryKey(simVOTemp.getImsiId2());

                        if ((ismiVOTemp2 != null) &&
                                (ismiVOTemp2.getImsiId() != null) &&
                                (ismiVOTemp2.getImsiId().trim().length() > 0)) {
                            txtIngoList.add("sim卡:" +
                                simVOTemp.getRescInstanceCode() + "的次imsi2码" +
                                simVOTemp.getImsiId2() + "已经存在，新增失败!");
                            failCount++;

                            continue;
                        }

                        // 检验sim卡中的imsi码是否在相应的imsi_seg段中
                        imsiSegId2 = imsiDao.qryImsiSeg(simVOTemp.getImsiId2());

                        if (imsiSegId2 == null) {
                            txtIngoList.add("sim卡:" +
                                simVOTemp.getRescInstanceCode() + "的次imsi2码" +
                                simVOTemp.getImsiId2() + "不在任何imsi段中，新增失败!");
                            failCount++;

                            continue;
                        }
                    }

                    // 插入sim表
                    lon = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                       .getNextSequence("RC_ENTITY",
                            "RcEntity_ID");
                    simVOTemp.setRescInstanceId(lon);
                    simVOTemp.setStorageId(vo.getStorageId());
                    simVOTemp.setSimType(vo.getSimType());
                    simVOTemp.setEffDate(vo.getEffDate());
                    simVOTemp.setExpDate(vo.getExpDate());
                    simVOTemp.setSalesRescId(vo.getSalesRescId());
                    simVOTemp.setState(ParamsConsConfig.RcEntityStateValide); //状态有效
                    simVOTemp.setRescState(ParamsConsConfig.RescState_preAssemble); //预配
                    simVOTemp.setBackup("0"); // 设置为主用卡
                    simVOTemp.setInitTime(currDate); // 设置入库时间
                    simDao.insert(simVOTemp); // 插入sim卡
                    ismiVOTemp = new RcImsiVO();
                    ismiVOTemp.setImsiId(simVOTemp.getImsiId());
                    ismiVOTemp.setRescInstanceId(simVOTemp.getRescInstanceId());
                    ismiVOTemp.setImsiSegId(imsiSegId);
                    ismiVOTemp.setMasterFlag("0"); ////0:master imsi no
                    ismiVOTemp.setState(ParamsConsConfig.RescState_preMadeCard); ////已制卡
                    imsiDao.insert(ismiVOTemp); // 插入imsi
                                                // 如果imsi2码不为空，则插入次imsi2

                    if ((simVOTemp.getImsiId2() != null) &&
                            (simVOTemp.getImsiId2().trim().length() > 0)) {
                        ismiVOTemp2 = new RcImsiVO();
                        ismiVOTemp2.setImsiId(simVOTemp.getImsiId2());
                        ismiVOTemp2.setRescInstanceId(simVOTemp.getRescInstanceId());
                        ismiVOTemp2.setImsiSegId(imsiSegId2);
                        ismiVOTemp2.setMasterFlag("1"); ///
                        ismiVOTemp2.setState(ParamsConsConfig.RescState_preMadeCard); ////已制卡
                        imsiDao.insert(ismiVOTemp2); // 插入imsi
                    }

                    // 更新号码状态
                    noVOTemp.setRescState(ParamsConsConfig.RescState_preAssemble);
                    noVOTemp.setImsiId(simVOTemp.getImsiId());
                    noDao.update(noVOTemp.getRescInstanceId(), noVOTemp);

                    // 写RC_NO_IMSI_RELATE表
                    RcNoImsiRelateVO relateVO = new RcNoImsiRelateVO();
                    relateVO.setRescInstanceId(noVOTemp.getRescInstanceId());
                    relateVO.setImsiId(simVOTemp.getImsiId());
                    relateVO.setWTime(currDate);
                    relateDao.insert(relateVO);

                    // 新增evdo
                    if (RcSimVO.SimType_evdo.equals(simVOTemp.getSimType())) {
                        if ((simVOTemp.getHrpdSs() != null) &&
                                (simVOTemp.getHrpdSs().trim().length() > 0)) {
                            RcSimEvdoVO evdoVO1 = new RcSimEvdoVO();
                            evdoVO1.setAttrvalue(simVOTemp.getHrpdSs());
                            evdoVO1.setRescInstanceId(lon);
                            evdoVO1.setAttrid("1");
                            evdoVO1.setAttrname("HRPD_SS");
                            evdoDao.insert(evdoVO1);
                        }

                        if ((simVOTemp.getHrpdUpp() != null) &&
                                (simVOTemp.getHrpdUpp().trim().length() > 0)) {
                            RcSimEvdoVO evdoVO2 = new RcSimEvdoVO();
                            evdoVO2.setAttrvalue(simVOTemp.getHrpdUpp());
                            evdoVO2.setRescInstanceId(lon);
                            evdoVO2.setAttrid("1");
                            evdoVO2.setAttrname("HRPDUPP");
                            evdoDao.insert(evdoVO2);
                        }
                    }

                    // 插入rc_order_list订单记录

                    //----modified by panyazong 改为变量绑定，预编译方式   20090618
                    sqlArrs[successCount] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE )" +
                        " values(" + orderId + "," + lon + "," +
                        vo.getSalesRescId() + ",'" +
                        simVOTemp.getRescInstanceCode() + "')";
                    successCount++;
                }
            }
        }

        if (successCount > 0) {
            // 插入rc_order_list的日志
            this.insertOrderInfo("C", new String[] { vo.getSalesRescId() },
                operId, departId, vo.getStorageId(), "",
                String.valueOf(successCount), null, null,
                new String[] { orderId }, "");

            if ((sqlArrs != null) && (sqlArrs.length > 0)) {
                SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
                comDao.batchExecute(sqlArrs); // 批量执行插入rc_order_list的记录
            }
        }

        alertInfo = "共成功导入" + successCount + "个SIM卡，失败" + failCount + "个!";
        txtIngoList.add(alertInfo);
        rtnMap.put("alertInfo", alertInfo);
        rtnMap.put("txtIngoList", txtIngoList);

        return rtnMap;
    }

    /**
     * 根据文件上传方式生成号码
     * @param vo RcNoVO
     * @param list List
     * @return String
     */
    public Map importRcTaoSimInfoNotExistNo(RcSimVO vo, List list,
        String operId, String departId) {
        Map map = new HashMap();
        String alertInfo = "";
        int successCount = 0;
        int failCount = 0;
        RcStorageVO storageVO = null;
        String storageName = "";
        String currDate = DAOUtils.getFormatedDate();
        RcImsiVO ismiVOTemp = null;
        RcImsiVO ismiVOTemp2 = null;
        RcSimEvdoVO simVOTemp = null;
        RcNoVO noVOTemp = null;
        List txtIngoList = new ArrayList();
        String imsiSegId = null;
        String imsiSegId2 = null;
        RcNoSegVO segVO = null;
        String noPk = null;
        String lon = null;
        RcImsiDAO imsiDao = SrNSDAOFactory.getInstance().getRcImsiDAO();
        RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcNoDAO noDao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcStorageDAO storageDao = SrDAOFactory.getInstance().getRcStorageDAO();
        RcNoBo noBo = new RcNoBo();
        RcNoImsiRelateDAO relateDao = SrNSDAOFactory.getInstance()
                                                    .getRcNoImsiRelateDAO();
        RcSimEvdoDAO evdoDao = SrNSDAOFactory.getInstance().getRcSimEvdoDAO();
        List listTemp = null;

        // 查询仓库名称
        if ((vo.getStorageId() != null) &&
                (vo.getStorageId().trim().length() > 0)) {
            storageVO = storageDao.findByPrimaryKey(vo.getStorageId());

            if (storageVO != null) {
                storageName = storageVO.getStorageName();
            }
        }

        String[] sqlArrs = new String[list.size() * 2]; // rc_order_list需要批量执行的语句
                                                        // 插入rc_order_list中的主键

        String orderId_no = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                         .getNextSequence("rc_order", "order_id");
        String orderId_sim = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                          .getNextSequence("rc_order",
                "order_id");
        String salesRescId_no = null;
        String salesRescId_sim = vo.getSalesRescId();
        int index = 0;

        for (int i = 0; i < list.size(); i++) {
            simVOTemp = (RcSimEvdoVO) list.get(i);

            if (simVOTemp != null) {
                // 检查号码是否存在
                noVOTemp = noDao.findByCode(simVOTemp.getDnNo());

                if (noVOTemp != null) {
                    txtIngoList.add("号码:" + simVOTemp.getDnNo() + "已经存在，处理失败!");
                    failCount++;

                    continue;
                } else {
                    segVO = noDao.findNoSegByNoCode(simVOTemp.getDnNo());

                    if (segVO == null) {
                        txtIngoList.add("号码:" + simVOTemp.getDnNo() +
                            "不在任何号段内，处理失败!");
                        failCount++;

                        continue;
                    } else {
                        if (salesRescId_no == null) {
                            salesRescId_no = segVO.getSalesRescId(); // 加载号码的营销资源id
                                                                     // 处理插入sim卡和imsi卡号
                                                                     //----modified by panyazong 改为变量绑定，预编译方式   20090618
                                                                     //            listTemp = simDao.findByCond(" and RESOURCE_INSTANCE_CODE='" +
                                                                     //                                         simVOTemp.getRescInstanceCode() + "'");
                        }

                        String SQL_SELECT = "SELECT * FROM RC_SIM where RESOURCE_INSTANCE_CODE=?";
                        String[] sqlParams = new String[1];
                        sqlParams[0] = simVOTemp.getRescInstanceCode();
                        listTemp = simDao.findBySql(SQL_SELECT, sqlParams);

                        if ((listTemp != null) && (listTemp.size() > 0)) {
                            txtIngoList.add("sim卡:" +
                                simVOTemp.getRescInstanceCode() + "已经存在，新增失败!");
                            failCount++;

                            continue;
                        } else {
                            // 检查imsi码是否重复
                            ismiVOTemp = imsiDao.findByPrimaryKey(simVOTemp.getImsiId());

                            if ((ismiVOTemp != null) &&
                                    (ismiVOTemp.getImsiId() != null) &&
                                    (ismiVOTemp.getImsiId().trim().length() > 0)) {
                                txtIngoList.add("sim卡:" +
                                    simVOTemp.getRescInstanceCode() + "的imsi码" +
                                    simVOTemp.getImsiId() + "已经存在，新增失败!");
                                failCount++;

                                continue;
                            }

                            //////检验sim卡中的imsi码是否在相应的imsi_seg段中
                            imsiSegId = imsiDao.qryImsiSeg(simVOTemp.getImsiId());

                            if (imsiSegId == null) {
                                txtIngoList.add("sim卡:" +
                                    simVOTemp.getRescInstanceCode() + "的imsi码" +
                                    simVOTemp.getImsiId() + "不在任何imsi段中，新增失败!");
                                failCount++;

                                continue;
                            }

                            // 检查次imsi2码是否有效
                            if ((simVOTemp.getImsiId2() != null) &&
                                    (simVOTemp.getImsiId2().trim().length() > 0)) {
                                // 检验sim卡中的次imsi2码是否在rc_imsi表中
                                ismiVOTemp2 = imsiDao.findByPrimaryKey(simVOTemp.getImsiId2());

                                if ((ismiVOTemp2 != null) &&
                                        (ismiVOTemp2.getImsiId() != null) &&
                                        (ismiVOTemp2.getImsiId().trim().length() > 0)) {
                                    txtIngoList.add("sim卡:" +
                                        simVOTemp.getRescInstanceCode() +
                                        "的次imsi2码" + simVOTemp.getImsiId2() +
                                        "已经存在，新增失败!");
                                    failCount++;

                                    continue;
                                }

                                // 检验sim卡中的imsi码是否在相应的imsi_seg段中
                                imsiSegId2 = imsiDao.qryImsiSeg(simVOTemp.getImsiId2());

                                if (imsiSegId2 == null) {
                                    txtIngoList.add("sim卡:" +
                                        simVOTemp.getRescInstanceCode() +
                                        "的次imsi2码" + simVOTemp.getImsiId() +
                                        "不在任何imsi段中，新增失败!");
                                    failCount++;

                                    continue;
                                }
                            }

                            noVOTemp = new RcNoVO();
                            noVOTemp.setRescInstanceCode(simVOTemp.getDnNo());
                            noVOTemp.setNoSegId(segVO.getNoSegId());
                            noVOTemp.setNoSegName(segVO.getNoSegName());
                            noVOTemp.setStorageId(vo.getStorageId());
                            noVOTemp.setStorageName(storageName);
                            noVOTemp.setEffDate(vo.getEffDate());
                            noVOTemp.setExpDate(vo.getExpDate());
                            noVOTemp.setInitTime(currDate);
                            noVOTemp.setSalesRescId(segVO.getSalesRescId());
                            noVOTemp.setSalesRescName(segVO.getSalesRescName());
                            noVOTemp.setBalaMode(segVO.getBalaMode()); // 设置收费方式
                            noVOTemp.setState(ParamsConsConfig.RcEntityStateValide);
                            noVOTemp.setRescState(ParamsConsConfig.RescState_preAssemble); // 预配
                            noVOTemp.setImsiId(simVOTemp.getImsiId());
                            noPk = noBo.doAddRcNo(noVOTemp);

                            if ((noPk == null) || "-1".equals(noPk) ||
                                    "-2".equals(noPk) || "-3".equals(noPk)) {
                                txtIngoList.add("号码:" + simVOTemp.getDnNo() +
                                    "已经存在或所在号段的状态为禁用，新增失败!");
                                failCount++;

                                continue;
                            } else {
                                // 插入sim表
                                lon = SeqDAOFactory.getInstance()
                                                   .getSequenceManageDAO()
                                                   .getNextSequence("RC_ENTITY",
                                        "RcEntity_ID");
                                simVOTemp.setRescInstanceId(lon);
                                simVOTemp.setStorageId(vo.getStorageId());
                                simVOTemp.setSimType(vo.getSimType());
                                simVOTemp.setEffDate(vo.getEffDate());
                                simVOTemp.setExpDate(vo.getExpDate());
                                simVOTemp.setSalesRescId(vo.getSalesRescId());
                                simVOTemp.setState(ParamsConsConfig.RcEntityStateValide); //状态有效
                                simVOTemp.setRescState(ParamsConsConfig.RescState_preAssemble); // 预配
                                simVOTemp.setBackup("0"); // 设置为主用卡
                                simVOTemp.setInitTime(currDate);
                                simDao.insert(simVOTemp); // 插入sim卡
                                ismiVOTemp = new RcImsiVO();
                                ismiVOTemp.setImsiId(simVOTemp.getImsiId());
                                ismiVOTemp.setRescInstanceId(simVOTemp.getRescInstanceId());
                                ismiVOTemp.setImsiSegId(imsiSegId);
                                ismiVOTemp.setMasterFlag("0"); ////0:master imsi no
                                ismiVOTemp.setState(ParamsConsConfig.RescState_preMadeCard); // 已制卡
                                imsiDao.insert(ismiVOTemp); // 插入imsi
                                                            // 如果imsi2码不为空，则插入次imsi2

                                if ((simVOTemp.getImsiId2() != null) &&
                                        (simVOTemp.getImsiId2().trim().length() > 0)) {
                                    ismiVOTemp2 = new RcImsiVO();
                                    ismiVOTemp2.setImsiId(simVOTemp.getImsiId2());
                                    ismiVOTemp2.setRescInstanceId(simVOTemp.getRescInstanceId());
                                    ismiVOTemp2.setImsiSegId(imsiSegId2);
                                    ismiVOTemp2.setMasterFlag("1"); ///
                                    ismiVOTemp2.setState(ParamsConsConfig.RescState_preMadeCard); ////已制卡
                                    imsiDao.insert(ismiVOTemp2); // 插入imsi
                                }

                                // 写RC_NO_IMSI_RELATE表
                                RcNoImsiRelateVO relateVO = new RcNoImsiRelateVO();
                                relateVO.setRescInstanceId(noPk);
                                relateVO.setImsiId(simVOTemp.getImsiId());
                                relateVO.setWTime(currDate);
                                relateDao.insert(relateVO);

                                //  新增evdo
                                if (RcSimVO.SimType_evdo.equals(
                                            simVOTemp.getSimType())) {
                                    if ((simVOTemp.getHrpdSs() != null) &&
                                            (simVOTemp.getHrpdSs().trim()
                                                          .length() > 0)) {
                                        RcSimEvdoVO evdoVO1 = new RcSimEvdoVO();
                                        evdoVO1.setAttrvalue(simVOTemp.getHrpdSs());
                                        evdoVO1.setRescInstanceId(lon);
                                        evdoVO1.setAttrid("1");
                                        evdoVO1.setAttrname("HRPD_SS");
                                        evdoDao.insert(evdoVO1);
                                    }

                                    if ((simVOTemp.getHrpdUpp() != null) &&
                                            (simVOTemp.getHrpdUpp().trim()
                                                          .length() > 0)) {
                                        RcSimEvdoVO evdoVO2 = new RcSimEvdoVO();
                                        evdoVO2.setAttrvalue(simVOTemp.getHrpdUpp());
                                        evdoVO2.setRescInstanceId(lon);
                                        evdoVO2.setAttrid("1");
                                        evdoVO2.setAttrname("HRPDUPP");
                                        evdoDao.insert(evdoVO2);
                                    }
                                }

                                // 插入rc_order_list订单记录
                                sqlArrs[index] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE )" +
                                    " values(" + orderId_no + "," + noPk + "," +
                                    salesRescId_no + ",'" +
                                    noVOTemp.getRescInstanceCode() + "')";
                                index++;
                                sqlArrs[index] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE )" +
                                    " values(" + orderId_sim + "," + lon + "," +
                                    salesRescId_sim + ",'" +
                                    simVOTemp.getRescInstanceCode() + "')";
                                index++;

                                successCount++;
                            }
                        }
                    }
                }
            }
        }

        if (successCount > 0) {
            // 插入rc_order_list的日志
            this.insertOrderInfo("C",
                new String[] { salesRescId_no, salesRescId_sim }, operId,
                departId, vo.getStorageId(), "", String.valueOf(successCount),
                null, null, new String[] { orderId_no, orderId_sim }, "");

            if ((sqlArrs != null) && (sqlArrs.length > 0)) {
                SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
                comDao.batchExecute(sqlArrs); // 批量执行插入rc_order_list的记录
            }
        }

        alertInfo = "共成功导入" + successCount + "个号码和SIM卡，失败" + failCount + "个!";
        txtIngoList.add(alertInfo);
        map.put("alertInfo", alertInfo);
        map.put("txtIngoList", txtIngoList);

        return map;
    }

    /**
     * assignSim
     *SIM卡分配
     * @param pmp Map
     * @param fileList Collection
     * @return List
     */
    //public List assignSim(Map pmp, Collection fileList)t hrows DAOSystemException {
    public List assignSim(DynamicDict dto) throws FrameException {
         
        Map pmp = (Map)dto.getValueByName("parameter");
        Collection fileList = (Collection) pmp.get("fileList");
        
        //首先判断是文件上传还是卡号；
        if ((pmp == null) || (fileList == null)) {
            return new ArrayList();
        }

        String operId = null;
        String departId = null;

        if (pmp.get("operId") != null) {
            operId = (String) pmp.get("operId");
        }

        if (pmp.get("departId") != null) {
            departId = (String) pmp.get("departId");
        }

        String cardType = (String) pmp.get("cardType");
        boolean fileFlag = ((Boolean) pmp.get("fileFlag")).booleanValue();
        String inStorageId = (String) pmp.get("inStorageId");
        String outStorageId = (String) pmp.get("outStorageId");
        String startCardNo = (String) pmp.get("startCardNo");
        String endCardNo = (String) pmp.get("endCardNo");
        int counter = 0;

        String salesRescId = null;
        String[] sqlArrs = null; // 批处理的语句
                                 // 插入rc_order_list中的主键

        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");

        //如果是文件上传
        if (fileFlag) {
            Iterator iter = fileList.iterator();
            sqlArrs = new String[fileList.size()]; // rc_order_list需要批量执行的语句

            int index = 0;

            while (iter.hasNext()) {
                String simNo = (String) iter.next();

                //----modified by panyazong 改为变量绑定，预编译方式   20090618
                //        String cond = " and  RESOURCE_INSTANCE_CODE ='" + simNo + "' and storage_id ="+outStorageId;
                //        List li = SrNSDAOFactory.getInstance().getRcSimDAO().findByCond(cond);
                String SQL_SELECT = "SELECT CARDMILL,INIT_TIME,PUK_NO,SIM_TYPE,CAPACITY,TEL_CAPACITY,SMS_CAPACITY,BACKUP,EXP_DATE,EFF_DATE,STATE," +
                    " RESOURCE_STATE,STORAGE_ID,SALES_RESOURCE_ID,RESOURCE_LEVEL,RESOURCE_INSTANCE_CODE,RESOURCE_INSTANCE_ID,WLAN_pwd,WLAN_acct,MSISDN,KI,pin2,pin1,puk2_no,ESN,IMSI_ID,DN_NO,IMSI_ID2,DN_NO2,UMID,SERV_CODE,SERV_NAME,CARD_TYPE,C_SERV_TYPE ,C_SERV_TYPE_NAME,ACC,SMSP,SIM_CHIP_TYPE " +
                    " FROM RC_SIM where RESOURCE_INSTANCE_CODE=? and storage_id = ?";
                String[] sqlParams = new String[2];
                sqlParams[0] = simNo;
                sqlParams[1] = outStorageId;

                List li = SrNSDAOFactory.getInstance().getRcSimDAO()
                                        .findBySql(SQL_SELECT, sqlParams);

                if ((li == null) || (li.size() <= 0)) {
                    return new ArrayList();
                }

                for (int i = 0; i < li.size(); i++) {
                    RcSimVO vo = (RcSimVO) li.get(i);
                    System.out.println(vo.getState() + " >sim card state");

                    if (!vo.getState().equals("00A") ||
                            !vo.getRescState()
                                   .equals(ParamsConsConfig.RcEntityFreeState)) {
                        String msg = "sim卡" + simNo + "不是有效的或不可用!";
                        throw new DAOSystemException(msg);
                    }

                    if ((cardType != null) && cardType.equals("0") &&
                            !"1".equals(vo.getBackup())) {
                        String msg = "sim卡" + simNo + "不是规定的备卡!";
                        throw new DAOSystemException(msg);
                    } else if ((cardType != null) && cardType.equals("1") &&
                            !"0".equals(vo.getBackup())) {
                        String msg = "sim卡" + simNo + "不是规定的主卡!";
                        throw new DAOSystemException(msg);
                    } else if ((cardType != null) && cardType.equals("2") &&
                            !"2".equals(vo.getBackup())) {
                        String msg = "sim卡" + simNo + "不是规定的白卡!";
                        throw new DAOSystemException(msg);
                    }

                    vo.setStorageId(inStorageId);
                    SrNSDAOFactory.getInstance().getRcSimDAO()
                                  .update(vo.getRescInstanceId(), vo);

                    // 如果营销资源id为空，则加载营销资源信息
                    if ((salesRescId == null) ||
                            (salesRescId.trim().length() < 1)) {
                        salesRescId = vo.getSalesRescId();
                    }

                    counter++;
                }

                // 插入rc_order_list订单记录
                sqlArrs[index] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE )" +
                    " values(" + orderId + ",null," + salesRescId + ",'" +
                    simNo + "')";
                index++;
            } // end iterator

            this.insertOrderInfo("J", new String[] { salesRescId }, operId,
                departId, inStorageId, outStorageId, String.valueOf(counter),
                "", "", new String[] { orderId }, "f");
        } else {
            //----modified by panyazong 改为变量绑定，预编译方式   20090618
            //      String sql = " and RESOURCE_INSTANCE_CODE>='" +
            //          startCardNo + "' and   RESOURCE_INSTANCE_CODE<='" + endCardNo + "' and storage_id ="+outStorageId;
            //      List li = SrNSDAOFactory.getInstance().getRcSimDAO().findByCond(sql);
            String SQL_SELECT = "SELECT CARDMILL,INIT_TIME,PUK_NO,SIM_TYPE,CAPACITY,TEL_CAPACITY,SMS_CAPACITY,BACKUP,EXP_DATE,EFF_DATE,STATE," +
                " RESOURCE_STATE,STORAGE_ID,SALES_RESOURCE_ID,RESOURCE_LEVEL,RESOURCE_INSTANCE_CODE,RESOURCE_INSTANCE_ID,WLAN_pwd,WLAN_acct,MSISDN,KI,pin2,pin1,puk2_no,ESN,IMSI_ID,DN_NO,IMSI_ID2,DN_NO2,UMID,SERV_CODE,SERV_NAME,CARD_TYPE,C_SERV_TYPE ,C_SERV_TYPE_NAME,ACC,SMSP,SIM_CHIP_TYPE " +
                " FROM RC_SIM where RESOURCE_INSTANCE_CODE>=? and RESOURCE_INSTANCE_CODE<= ? and storage_id = ?";
            String[] sqlParams = new String[3];
            sqlParams[0] = startCardNo;
            sqlParams[1] = endCardNo;
            sqlParams[2] = outStorageId;

            List li = SrNSDAOFactory.getInstance().getRcSimDAO()
                                    .findBySql(SQL_SELECT, sqlParams);

            if (li == null) {
                return new ArrayList();
            }

            for (int i = 0; i < li.size(); i++) {
                RcSimVO vo = (RcSimVO) li.get(i);

                if (!vo.getState().equals("00A") ||
                        !vo.getRescState()
                               .equals(ParamsConsConfig.RcEntityFreeState)) {
                    String msg = " 卡号为 " + vo.getRescInstanceCode() +
                        " SIM卡不是有效的或不可用!";
                    throw new DAOSystemException(msg);
                }

                if ((cardType != null) && cardType.equals("0") &&
                        !"1".equals(vo.getBackup())) {
                    String msg = vo.getRescInstanceCode() + "不是规定的备卡!";
                    throw new DAOSystemException(msg);
                } else if ((cardType != null) && cardType.equals("1") &&
                        !"0".equals(vo.getBackup())) {
                    String msg = vo.getRescInstanceCode() + "不是规定的主卡!";
                    throw new DAOSystemException(msg);
                } else if ((cardType != null) && cardType.equals("2") &&
                        !"2".equals(vo.getBackup())) {
                    String msg = vo.getRescInstanceCode() + "不是规定的白卡!";
                    throw new DAOSystemException(msg);
                }

                vo.setStorageId(inStorageId);
                SrNSDAOFactory.getInstance().getRcSimDAO()
                              .update(vo.getRescInstanceId(), vo);

                // 加载salesRescId
                if ((salesRescId == null) && (vo != null)) {
                    salesRescId = vo.getSalesRescId();
                }

                counter++;
            }

            this.insertOrderInfo("J", new String[] { salesRescId }, operId,
                departId, inStorageId, outStorageId, String.valueOf(counter),
                startCardNo, endCardNo, new String[] { orderId }, "b");
        }

        // 插入rc_order_list的日志
        if ((sqlArrs != null) && (sqlArrs.length > 0)) {
            SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
            comDao.batchExecute(sqlArrs); // 批量执行插入rc_order_list的记录
        }

        List backList = new ArrayList();
        backList.add(String.valueOf(counter));

        return backList;
    }

    /**
     * addRcSimInfo
     * 增加SIM,IMSI
     * @param vo RcSimVO
     * @return String：-2：sim卡卡号重复；-3：imsi码不在imsi_seg中
     */
    //public String addRcSimInfo(RcSimEvdoVO vo) throws DAOSystemException {
    public String addRcSimInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        RcSimEvdoVO vo  = (RcSimEvdoVO)map.get("vo");
        
        if (vo == null) {
            return "";
        }

        String lon = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                  .getNextSequence("RC_ENTITY", "RcEntity_ID");
        vo.setRescInstanceId(lon);

        String imsiSegId = "";
        boolean needSetDoubleSim = false;

        try {
            RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
            RcImsiDAO imsiDao = SrNSDAOFactory.getInstance().getRcImsiDAO();
            RcSimRelaDAO relaDao = SrNSDAOFactory.getInstance().getRcSimRelaDAO();

            // RcSim2RelaDAO sim2RelaDao = SrDAOFactory.getInstance().getRcSim2RelaDAO();
            // 查询号码是否重复
            List list = simDao.findByCond(" and RESOURCE_INSTANCE_CODE='" +
                    vo.getRescInstanceCode() + "'");

            if ((list != null) && (list.size() > 0)) {
                return "-2";
            }

            // 判断imsi码是否存在
            RcImsiVO imsiTemp = imsiDao.findByPrimaryKey(vo.getImsiId());

            if ((imsiTemp != null) && (imsiTemp.getImsiId() != null) &&
                    (imsiTemp.getImsiId().trim().length() > 0)) {
                return "-4";
            }

            if ((vo.getImsiId2() != null) && !vo.getImsiId2().equals("")) {
                imsiTemp = imsiDao.findByPrimaryKey(vo.getImsiId2());

                if ((imsiTemp != null) && (imsiTemp.getImsiId() != null) &&
                        (imsiTemp.getImsiId().trim().length() > 0)) {
                    return "-5";
                }
            }

            //如果是备卡，检验sim卡中的imsi码是否在相应的imsi_seg段中
            if ((vo.getBackup() != null) && "1".equals(vo.getBackup())) {
                imsiSegId = imsiDao.qryImsiSeg(vo.getImsiId());

                if (imsiSegId == null) {
                    return "-3";
                }
            }

            // 判断是否需要设置双芯卡
            if ((vo.getSimChipType() != null) &&
                    ("3".equals(vo.getSimChipType()) ||
                    "4".equals(vo.getSimChipType())) &&
                    (vo.getSimCodeCorr() != null) &&
                    (vo.getSimCodeCorr().trim().length() > 0)) {
                needSetDoubleSim = true;

                String relaRtn = simDao.isAlreadyExist(vo.getSimCodeCorr(),
                        vo.getOperId(), vo.getDepartId());

                if ("-1".equals(relaRtn)) {
                    return "-6"; // 不存在或操作员没有权限操作该iccid号
                } else if ("-2".equals(relaRtn)) {
                    return "-7"; // 输入的双芯卡卡号已经存在了
                }
            }

            // 设置sim卡入库时间
            vo.setInitTime(DAOUtils.getFormatedDate());
            SrNSDAOFactory.getInstance().getRcSimDAO().insert(vo); // 插入sim卡

            if ((vo.getBackup() != null) && "1".equals(vo.getBackup())) { // 如果是备卡，则插入imsi卡信息

                RcImsiVO voo = new RcImsiVO();
                voo.setImsiId(vo.getImsiId());
                voo.setRescInstanceId(vo.getRescInstanceId());
                voo.setImsiSegId(imsiSegId);

                if (voo != null) {
                    voo.setMasterFlag("0"); ////0:master imsi no
                    voo.setState(ParamsConsConfig.RescState_preMadeCard); ////
                }

                //将IMSIVO插入到表中（有可能主键冲突）
                try {
                    imsiDao.insert(voo);
                } catch (DAOSystemException ee) {
                    ee.printStackTrace();
                    throw new FrameException(":插入IMSI数据时失败！");
                }

                // if there is a assistant table id ,then ....
                if ((vo.getImsiId2() != null) && !vo.getImsiId2().equals("")) {
                    voo.setImsiId(vo.getImsiId2());
                    voo = (RcImsiVO) SrNSDAOFactory.getInstance().getRcImsiDAO()
                                                   .getResultForValidate(voo);

                    if (voo != null) {
                        voo.setMasterFlag("1"); ////1:assistant imsi no;
                        voo.setState(ParamsConsConfig.RescState_preMadeCard); ////
                    }

                    //将IMSIVO插入到表中（有可能主键冲突）
                    try {
                        SrNSDAOFactory.getInstance().getRcImsiDAO().insert(voo);
                    } catch (DAOSystemException ee) {
                        throw new FrameException(":插入IMSI数据时失败！");
                    }
                }
            }

            // 如果是evdo卡，还需要新增evdo信息
            if (RcSimVO.SimType_evdo.equals(vo.getSimType())) {
                RcSimEvdoVO evdo1 = new RcSimEvdoVO();
                RcSimEvdoVO evdo2 = new RcSimEvdoVO();
                evdo1.setAttrid("1");
                evdo1.setRescInstanceId(vo.getRescInstanceId());
                evdo1.setAttrname("HRPD_SS");
                evdo1.setAttrvalue(vo.getHrpdSs());
                evdo2.setAttrid("1");
                evdo2.setRescInstanceId(vo.getRescInstanceId());
                evdo2.setAttrname("HRPDUPP");
                evdo2.setAttrvalue(vo.getHrpdUpp());

                List evdoList = new ArrayList();
                evdoList.add(evdo1);
                evdoList.add(evdo2);
                //插入
                SrNSDAOFactory.getInstance().getRcSimEvdoDAO()
                              .insertBatch(evdoList);
            }

            // 如果是设置了双芯卡，需要在此处理
            if (needSetDoubleSim) {
                RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();
                SqlExcuteByStr G = new SqlExcuteByStr();

                //    	  String serialSql = "select seq_serial_id.nextval from dual";
                //    	  String serial=G.getString(serialSql);       	  
                String imsi2Sql = "select imsi_id as result from rc_sim where resource_instance_code = '" +
                    vo.getSimCodeCorr() + "'";
                String imsi2 = G.getString(imsi2Sql);
                RcSimRelaVO rcSimRelaVO = new RcSimRelaVO();
                rcSimRelaVO.setSerialId("-1");

                //这里要判断那个是语音卡那个是数据卡
                if (vo.getSimChipType().equals("3")) {
                    rcSimRelaVO.setMainImsi(vo.getImsiId());
                    rcSimRelaVO.setAdditionalImsi(imsi2);
                    rcSimRelaVO.setMainIccid(vo.getRescInstanceCode());
                    rcSimRelaVO.setAdditionalIccid(vo.getSimCodeCorr());
                    dao.updateSimChipType(vo.getSimCodeCorr(), "4");
                }

                if (vo.getSimChipType().equals("4")) {
                    rcSimRelaVO.setMainImsi(imsi2);
                    rcSimRelaVO.setAdditionalImsi(vo.getImsiId());
                    rcSimRelaVO.setMainIccid(vo.getSimCodeCorr());
                    rcSimRelaVO.setAdditionalIccid(vo.getRescInstanceCode());
                    dao.updateSimChipType(vo.getSimCodeCorr(), "3");
                }

                relaDao.insert(rcSimRelaVO);
            }
        } catch (DAOSystemException ex) {
            String expmsg = ex.getMessage();

            ex.printStackTrace();
            throw new FrameException(expmsg);
        }

        return vo.getRescInstanceCode() + "-" + vo.getStorageId(); // return
    }

    /**
     * modifyRcSimInfo
     *SIM卡修改
     * @param vo RcSimVO
     * @return String
     */
    //public String modifyRcSimInfo(Map map) throws DAOSystemException {
    public String modifyRcSimInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        if ((map == null) || (map.get("vo") == null)) {
            return "";
        }

        RcSimVO vo = (RcSimVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();

        String relaRtn = modifySimAndRela(vo);

        if ("-1".equals(relaRtn) || "-2".equals(relaRtn)) {
            return relaRtn;
        }

        // 插入相关通用日至
        RcSimVO vo_old = dao.findByPrimaryKey(vo.getRescInstanceId());
        dao.update(vo.getRescInstanceId(), vo);

        RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
        RcPublicLogVO logVO = new RcPublicLogVO();
        logVO.setAct("M");
        logVO.setReworkTime(DAOUtils.getFormatedDate());
        logVO.setReworkTable("rc_sim");
        logVO.setReworkWen(operId);
        logVO.setReworkIp(reworkIp);
        logDao.logVO(logVO, vo_old, vo);

        return vo.getRescInstanceCode() + "-" + vo.getStorageId();
    }

    /**
     * SIM卡删除 , CASCADE DELETE IMSI RECORDS
     * @param vo RcSimVO
     * @throws DAOSystemException
     * @return String
     */
    //public String deleteRcSimInfo(Map map) throws DAOSystemException {
    public String deleteRcSimInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        if ((map == null) || (map.get("vo") == null)) {
            return "";
        }

        RcSimVO vo = (RcSimVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        //first delete all the records in the imsi table which referenced the sim table 's one record by foreign key;
        RcImsiVO voo = new RcImsiVO();
        voo.setRescInstanceId(vo.getRescInstanceId());
        System.out.println(vo.getRescInstanceId());
        SrNSDAOFactory.getInstance().getRcImsiDAO()
                      .deleteByCond(" resource_instance_id=" +
            voo.getRescInstanceId());
        // 如果是evdo卡则删除evdo信息
        SrNSDAOFactory.getInstance().getRcSimEvdoDAO()
                      .deleteBySimId(vo.getRescInstanceId());

        // 插入相关通用日至
        RcSimVO vo_old = null;
        RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
        vo_old = SrNSDAOFactory.getInstance().getRcSimDAO()
                               .findByPrimaryKey(vo.getRescInstanceId());

        RcPublicLogVO logVO = new RcPublicLogVO();
        logVO.setAct("D");
        logVO.setReworkTime(DAOUtils.getFormatedDate());
        logVO.setReworkTable("rc_sim");
        logVO.setReworkWen(operId);
        logVO.setReworkIp(reworkIp);
        logDao.logVO(logVO, vo_old, null);
        //then delete the record in table sim;
        SrNSDAOFactory.getInstance().getRcSimDAO().delete(vo.getRescInstanceId());

        return vo.getRescInstanceId();
    }

    /**
     * sim新增、分配时需要生成订单
     * @param appType String
     * @param salesRescId String
     * @param operId String
     * @param departId String
     * @param inStorageId String
     * @param appAmount String
     * @param resBCode String
     * @param resECode String
     * @return boolean
     */
    public boolean insertOrderInfo(String appType, String[] salesRescIds,
        String operId, String departId, String inStorageId,
        String outStorageId, String appAmount, String resBCode,
        String resECode, String[] orderIds, String flag) {
        if ((appType == null) || (appType.trim().length() < 1) ||
                (inStorageId == null) || (inStorageId.trim().length() < 1)) {
            return false;
        }

        RcOrderDAO orderDao = (RcOrderDAO) SrDAOFactory.getInstance()
                                                       .getRcOrderDAO();
        RcApplicationDAO appDao = (RcApplicationDAO) SrDAOFactory.getInstance()
                                                                 .getRcApplicationDAO();
        RcOrderExcDAO execDao = (RcOrderExcDAO) SrDAOFactory.getInstance()
                                                            .getRcOrderExcDAO();
        SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
                                                           .getSequenceManageDAO();
        String appId = sequenceManageDAO.getNextSequence("rc_application",
                "app_id");

        if ((orderIds == null) || (orderIds.length < 1)) { // 如果orderIds为空，根据salesRescIds的尺寸进行构造

            if ((salesRescIds != null) && (salesRescIds.length > 0)) {
                orderIds = new String[salesRescIds.length];

                for (int i = 0; i < orderIds.length; i++) {
                    orderIds[i] = sequenceManageDAO.getNextSequence("rc_order",
                            "order_id");
                }
            } else {
                orderIds = new String[1];
                orderIds[0] = sequenceManageDAO.getNextSequence("rc_order",
                        "order_id");
            }
        }

        String date = DateFormatUtils.getFormatedDateTime();

        RcApplicationVO appVO = new RcApplicationVO();
        RcOrderVO rcOrderVO = new RcOrderVO();
        RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();

        // 装载rc_application
        appVO.setAppId(appId);
        appVO.setAppTime(date);
        appVO.setAppType(appType);
        appVO.setDepartId(departId);
        appVO.setOperId(operId);

        appDao.insert(appVO);

        // 装载rc_order和rc_order_exc
        for (int j = 0; j < orderIds.length; j++) {
            rcOrderVO.setOrderId(orderIds[j]);
            rcOrderVO.setAppId(appId);
            rcOrderVO.setAppType(appType);
            rcOrderVO.setOperId(operId);
            rcOrderVO.setDepartId(departId);
            rcOrderVO.setTacheId("5");
            rcOrderVO.setStateId("n");
            rcOrderVO.setAppTime(date);
            rcOrderVO.setTacheTime(date);
            rcOrderVO.setEndTime(date);
            rcOrderVO.setOutStorageId(outStorageId);
            rcOrderVO.setInStorageId(inStorageId);
            rcOrderVO.setAppStorageId(inStorageId);

            if (j < salesRescIds.length) {
                rcOrderVO.setSalesRescId(salesRescIds[j]);
            }

            rcOrderVO.setAppAmount(appAmount);
            rcOrderVO.setActAmount(appAmount);
            rcOrderVO.setResBCode(resBCode);
            rcOrderVO.setResECode(resECode);
            rcOrderVO.setRecOptType(flag);

            // 装载rc_order_exc
            String logId = sequenceManageDAO.getNextSequence("rc_order_exc",
                    "log_id");
            rcOrderExcVO.setLogId(logId);
            rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
            rcOrderExcVO.setTacheId("4");
            rcOrderExcVO.setExcTime(rcOrderVO.getTacheTime());
            rcOrderExcVO.setDepartId(departId);
            rcOrderExcVO.setOperId(operId);
            rcOrderExcVO.setStateId(rcOrderVO.getStateId());

            orderDao.insert(rcOrderVO);
            execDao.insert(rcOrderExcVO);
        }

        return true;
    }

    /**
     * 查询sim卡的订单
     * @param map Map
     * @return List
     */
    public List qryOrderSim(Map map) {
        if (map == null) {
            map = new HashMap();
        }

        String attrCode = "Rc_SIM_FamilyId";
        List list = null;
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        RcOrderDAO orderDao = SrDAOFactory.getInstance().getRcOrderDAO();
        String familyId = segDao.findFamilyId(attrCode);
        map.put("familyId", familyId);
        map.put("appType", "C");
        list = orderDao.qryOrderNoSim(map);

        if (list == null) {
            list = new ArrayList();
        }

        return list;
    }

    /**
     * 删除订单sim卡信息及可能包含的号码信息(主卡)
     * @param map Map
     * @return Map
     */
    //public Map delSimOrder(Map map) {
    public Map delSimOrder(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        if (map == null) {
            return null;
        }

        String orderId = null;
        String appId = null;
        String salesRescId = null;
        String storageId = null;
        String operId = null;
        String actAmount = null;
        String departId = null;

        if (map.get("orderId") != null) {
            orderId = map.get("orderId").toString();
        }

        if (map.get("appId") != null) {
            appId = map.get("appId").toString();
        }

        if (map.get("salesRescId") != null) {
            salesRescId = map.get("salesRescId").toString();
        }

        if (map.get("storageId") != null) {
            storageId = map.get("storageId").toString();
        }

        if (map.get("operId") != null) {
            operId = map.get("operId").toString();
        }

        if (map.get("actAmount") != null) {
            actAmount = map.get("actAmount").toString();
        }

        if (map.get("departId") != null) {
            departId = map.get("departId").toString();
        }

        if ((orderId == null) || (orderId.trim().length() < 1) ||
                (salesRescId == null) || (salesRescId.trim().length() < 1) ||
                (storageId == null) || (storageId.trim().length() < 1) ||
                (operId == null) || (operId.trim().length() < 1) ||
                (actAmount == null) || (actAmount.trim().length() < 1) ||
                (departId == null) || (departId.trim().length() < 1)) {
            return null;
        }

        Map retMap = new HashMap();
        String result = "1"; // 代表操作成功
        String message = "";
        String backup = null;
        int successNum = 0;
        SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();

        // 检查是否有权限操作该仓库
        String sql_right = " select count(a.storage_id) as COL_COUNTS from rc_storage a where a.storage_id=" +
            storageId +
            " and (exists (select 1 from mp_storage b where a.storage_id=b.storage_id and " +
            " b.oper_id=" + operId + ") or " +
            " exists(select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id " +
            " and c.depart_id=" + departId + ")) ";
        long storage_num = comDao.count(sql_right);

        if (storage_num < 1) {
            result = "2"; // 操作员没有权限操作该仓库
            retMap.put("result", result);
            retMap.put("message", "没有权限操作该仓库，操作失败!");

            return retMap;
        }

        // 检查该订单下的sim卡是否已经全部删除
        String sql0 = "select count(a.resource_instance_id) as COL_COUNTS from rc_sim a where " +
            " exists(select * from rc_order_list b where a.SALES_RESOURCE_ID=b.SALES_RESOURCE_ID " +
            " and a.RESOURCE_INSTANCE_CODE=b.RESOURCE_INSTANCE_CODE and b.ORDER_ID=" +
            orderId + ")";
        long num = comDao.count(sql0);

        if (num == 0) {
            result = "3"; // sim卡已被删除
            retMap.put("result", result);
            retMap.put("message", "生成的sim卡已经不存在!");

            return retMap;
        }

        // 检查所有卡是否都是同一类型的(主、备、白卡)，并且得到卡类型
        String sql1 = "select a.backup from rc_sim a where " +
            " exists(select * from rc_order_list b where a.SALES_RESOURCE_ID=b.SALES_RESOURCE_ID " +
            " and a.RESOURCE_INSTANCE_CODE=b.RESOURCE_INSTANCE_CODE and b.ORDER_ID=" +
            orderId + ") group by a.backup";
        String[] arrs = new String[] { "backup" };
        List list1 = comDao.qryComSql(sql1, arrs);

        if ((list1 == null) || (list1.size() != 1)) {
            result = "4"; // 所有的sim卡不是同一类型的
            retMap.put("result", result);
            retMap.put("message", "该单生成的sim卡不是同一类型的，操作失败!");

            return retMap;
        }

        backup = (String) ((Map) (list1.get(0))).get("backup");

        if ((backup == null) || (backup.trim().length() < 1)) {
            result = "5"; // 缺少sim卡的类型
            retMap.put("result", result);
            retMap.put("message", "缺少生成的sim卡类型，操作失败!");

            return retMap;
        }

        // 检查改单中的物资是否都在同一仓库并且都是可用(备、白卡)或预配(主卡)
        String resourceState = ParamsConsConfig.RcEntityFreeState;

        if (RcSimVO.Backup_MainCard.equals(backup)) {
            resourceState = ParamsConsConfig.RescState_preAssemble;
        }

        String sql2 = "select count(a.storage_id) as COL_COUNTS from rc_sim a where a.STORAGE_ID=" +
            storageId + " and a.RESOURCE_STATE='" + resourceState + "' ";
        String cond2 = " and exists(select * from rc_order_list b where a.SALES_RESOURCE_ID=b.SALES_RESOURCE_ID " +
            " and a.RESOURCE_INSTANCE_CODE=b.RESOURCE_INSTANCE_CODE and b.ORDER_ID=" +
            orderId + ")";
        num = comDao.count(sql2 + cond2);

        if (Long.parseLong(actAmount) != num) {
            result = "6"; // 该订单生成的sim卡不在同一仓库中或有不可用的(备卡、白卡)

            if (RcSimVO.Backup_MainCard.equals(backup)) {
                result = "7"; // 该订单生成的sim卡不在同一仓库中或有不是预配状态的(主卡)
            }

            retMap.put("result", result);

            if ("6".equals(result)) {
                retMap.put("message", "该订单生成的sim卡不在同一仓库中或存在不是可用状态的sim卡!");
            } else if ("7".equals(result)) {
                retMap.put("message", "该订单生成的sim卡不在同一仓库中或存在不是预配状态的主卡!");
            }

            return retMap;
        }

        // 如果不是白卡则删除rc_imsi表
        if (!RcSimVO.Backup_BlankCard.equals(backup)) {
            String sql3 = "delete from rc_imsi a where exists " +
                "(select * from rc_sim b,rc_order_list c " +
                " where a.resource_instance_id = b.resource_instance_id " +
                " and b.sales_resource_id = c.sales_resource_id " +
                " and b.resource_instance_code = c.resource_instance_code " +
                " and c.order_id = " + orderId + ")";

            comDao.excute(sql3);
        }

        // 如果是主卡，如果号码是已经存在的，则把对应rc_no表的数据状态从“预配”改为“冻结”
        // 如果号码是新生成的，则删除这些号码
        if (RcSimVO.Backup_MainCard.equals(backup)) {
            String sql5 = "select order_id from rc_order where app_id=" +
                appId + " and order_id!=" + orderId;
            String[] arrs2 = new String[] { "order_id" };
            String orderId_no = null; // 主卡对应的号码的生成订单号
            List list2 = comDao.qryComSql(sql5, arrs2);

            if ((list2 != null) && (list2.size() > 0)) {
                orderId_no = (String) ((Map) (list2.get(0))).get("order_id");
            }

            if ((orderId_no != null) && (orderId_no.trim().length() > 0)) {
                // 需要把orderId_no对应的号码都删除
                String sql6 = "delete from rc_no a where exists(select * from rc_order_list b where a.SALES_RESOURCE_ID=b.SALES_RESOURCE_ID " +
                    " and a.RESOURCE_INSTANCE_CODE=b.RESOURCE_INSTANCE_CODE and b.ORDER_ID=" +
                    orderId_no + ")";
                comDao.excute(sql6);
            } else {
                // 把sim卡对应的号码的状态从"预配"改为"冻结"
                String sql7 = "update rc_no c set c.RESOURCE_STATE='" +
                    ParamsConsConfig.RescState_frozen + "' where " +
                    " exists(select a.dn_no from rc_sim a,rc_order_list b " +
                    " where c.RESOURCE_INSTANCE_CODE = a.DN_NO " +
                    " and a.SALES_RESOURCE_ID = b.SALES_RESOURCE_ID " +
                    " and a.RESOURCE_INSTANCE_CODE = b.RESOURCE_INSTANCE_CODE " +
                    " and b.ORDER_ID = " + orderId + ")";
                comDao.excute(sql7);
            }
        }

        // 删除rc_sim表
        String sql4 = "delete from rc_sim a where 1=1 " + cond2;
        successNum = comDao.excute(sql4);

        retMap.put("result", result);

        if ("1".equals(result)) {
            message = "取消sim卡导入成功，共取消" + successNum + "张sim卡!";
        } else {
            message = "取消sim卡导入失败，未知错误!";
        }

        retMap.put("message", message);

        return retMap;
    }

    /**
     * 预配号码，list中是sim、号码的map集合
     * @param list List
     * @throws BizLogicException
     * @return long
     */
    //public long preAssemSimNo(List list) throws BizLogicException {
    public Map preAssemSimNo(DynamicDict dto) throws FrameException {  
        Map map = new HashMap();
        
        String info = "";
        String accept_id = "";
        Map mapTemp = null;
        
        Map mp = (Map)dto.getValueByName("parameter") ;
        List list = (List) mp.get("list");

        if ((list == null) || (list.size() < 2)) {
            //throw new BizLogicException("传入的要预配的sim卡号码为空");
            map.put("result","0");
            map.put("message","传入的要取消预配的sim卡数量为0");
            return map;
        }

        mapTemp = (Map) list.get(0);
        accept_id = (String) mapTemp.get("accept_id");

        String nowDate = DAOUtils.getFormatedDate();
        long count = 0L;

        RcSimVO simVO = null;
        RcNoVO noVO = null;
        RcNoImsiRelateVO relaVO = null;
        String sim = null;
        String no = null;
        RcNoDAO noDao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcNoImsiRelateDAO relaDao = SrNSDAOFactory.getInstance()
                                                  .getRcNoImsiRelateDAO();

        for (int i = 0; i < list.size(); i++) {
            mapTemp = (Map) list.get(i);

            if ((mapTemp != null) && (mapTemp.get("sim") != null) &&
                    (mapTemp.get("sim").toString().trim().length() > 0) &&
                    (mapTemp.get("no") != null) &&
                    (mapTemp.get("sim").toString().trim().length() > 0)) {
                sim = (String) mapTemp.get("sim");
                no = (String) mapTemp.get("no");
                simVO = simDao.findByCode(sim);
                noVO = noDao.findByCode(no);

                if ((simVO != null) &&
                        RcSimVO.Backup_BackCard.equals(simVO.getBackup()) &&
                        ParamsConsConfig.RcEntityFreeState.equals(
                            simVO.getRescState())) {
                    if ((noVO != null) &&
                            ParamsConsConfig.RescState_frozen.equals(
                                noVO.getRescState())) {
                        // 正式预配
                        simVO.setAccept_id(accept_id);
                        simVO.setRescState(ParamsConsConfig.RescState_preAssemble);
                        simVO.setBackup(RcSimVO.Backup_MainCard);
                        simVO.setDnNo(no);
                        simDao.update(simVO.getRescInstanceId(), simVO);

                        noVO.setRescState(ParamsConsConfig.RescState_preAssemble);
                        noVO.setImsiId(simVO.getImsiId());
                        noDao.update(noVO.getRescInstanceId(), noVO);
                        // 写表RC_NO_IMSI_RELATE
                        relaVO = new RcNoImsiRelateVO();
                        relaVO.setWTime(nowDate);
                        relaVO.setImsiId(simVO.getImsiId());
                        relaVO.setRescInstanceId(noVO.getRescInstanceId());
                        relaDao.insert(relaVO);
                        count++;
                    } else {
                        info = "号码" + no + "不存在或不是封存状态";
                        //throw new BizLogicException(info);
                        map.put("result","0");
                        map.put("message",info);
                        return map;
                    }
                } else {
                    info = "ICCID号" + sim + "的SIM卡不存在或不是备卡或不可用";
                    //throw new BizLogicException(info);
                    map.put("result","0");
                    map.put("message",info);
                    return map;
                }
            }
        }

        //return count;
        map.put("result","1");
        map.put("message", "?0?0?1?7?1?7?0?0?1?7?1?7?0?6?1?7?1?7?1?7?1?7?1?7?1?7?0?6?1?7?0?0?1?7?1?7"+count+"?1?7?1?7?0?0?1?7?1?7");
        return map;
    }

    /**
     * 取消预配关系
     * @param codes String[] sim卡码集合
     * @throws BizLogicException
     * @return long
     */
    //public long cancelPreAssemSimNo(List codes) throws BizLogicException {
    public Map cancelPreAssemSimNo(DynamicDict dto) throws FrameException {
    
        Map mp = (Map)dto.getValueByName("parameter");
        List codes = (List) mp.get("codes");
        
        Map map = new HashMap();
        
        if ((codes == null) || (codes.size() < 1)) {
            //throw new FrameException("传入的要取消预配的sim卡数量为0");
            map.put("result","0");
            map.put("message","传入的要取消预配的sim卡数量为0");
            return map;
        }

        long count = 0L;
        RcSimVO simVO = null;
        RcNoVO noVO = null;
        String sim = null;
        String no = null;
        RcNoDAO noDao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcNoImsiRelateDAO relaDao = SrNSDAOFactory.getInstance()
                                                  .getRcNoImsiRelateDAO();

        for (int i = 0; i < codes.size(); i++) {
            sim = (String) codes.get(i);
            simVO = simDao.findByCode(sim);

            if (simVO != null) {
                if (RcSimVO.Backup_MainCard.equals(simVO.getBackup()) &&
                        ParamsConsConfig.RescState_preAssemble.equals(
                            simVO.getRescState())) {
                    no = simVO.getDnNo();
                    // 预配取消sim
                    simVO.setRescState(ParamsConsConfig.RcEntityFreeState);
                    simVO.setBackup(RcSimVO.Backup_BackCard);
                    simVO.setDnNo("");
                    simDao.update(simVO.getRescInstanceId(), simVO);

                    // 预配取消no
                    if ((no != null) && (no.trim().length() > 0)) {
                        noVO = noDao.findByCode(no);

                        if ((noVO != null) &&
                                (noVO.getRescInstanceId() != null) &&
                                (noVO.getRescInstanceId().trim().length() > 0)) {
                            noVO.setRescState(ParamsConsConfig.RescState_frozen);
                            noVO.setImsiId("");
                            noDao.update(noVO.getRescInstanceId(), noVO);
                        } else {
                            //throw new FrameException("SIM卡" + sim + "对应的号码" + no + "不存在");
                            map.put("result","0");
                            map.put("message","SIM卡" + sim + "对应的号码" + no + "不存在");
                            return map;
                        }
                    } else {
                        //throw new FrameException("SIM卡" + sim + "没有对应号码");
                        map.put("result","0");
                        map.put("message","SIM卡" + sim + "没有对应号码");
                        return map;
                    }

                    // 删除RC_NO_IMSI_RELATE关系
                    String cond = " RESOURCE_INSTANCE_ID=" +
                        noVO.getRescInstanceId() + " and IMSI_ID=" +
                        simVO.getImsiId();
                    relaDao.deleteByCond(cond);
                    count++;
                } else {
                    //throw new FrameException("ICCID号" + sim + "的sim卡不是主卡或不是预配状态");
                    map.put("result","0");
                    map.put("message", "ICCID号" + sim + "的sim卡不是主卡或不是预配状态");
                    return map;
                }
            } else {
                //throw new FrameException("sim卡" + sim + "不存在，操作失败");
                map.put("result","0");
                map.put("message", "sim卡" + sim + "不存在，操作失败");
                return map;
            }
        }

        map.put("result","1");
        map.put("message", "?0?0?1?7?1?7?0?0?1?7?1?7?0?6?1?7?1?7?1?7?1?7?1?7?1?7?0?6?1?7?0?0?1?7?1?7"+count+"?1?7?1?7?0?0?1?7?1?7");
        return map;
    }

    /**
     * modify sim state,this method 's mean is check the exists of sim card in table pd_sim or pdn_sim
     * @param vo
     * @return
     * @throws DAOSystemException
     */
    //public String SIMstateModify(RcSimVO vo) throws DAOSystemException {
    public String SIMstateModify(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        RcSimVO vo  = (RcSimVO)map.get("vo");
        
        if (vo == null) {
            return "无效的参数";
        }

        String sql = "select 1 from Pd_sim where iccid='" +
            vo.getRescInstanceCode() + "'" +
            " union all select 1 from pdn_sim where iccid='" +
            vo.getRescInstanceCode() + "'";
        System.out.println(sql);

        SqlComDAO cdao = SrDAOFactory.getInstance().getSqlComDAO();

        if (cdao.checkExist(sql)) {
            return "此卡已经被使用，不能修改";
        }

        RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();
        String whereCond = " and ccid='" + vo.getRescInstanceCode() + "'";
        dao.update(vo.getRescInstanceId(), vo);

        return null;
    }

    /**
     * 根据sim卡主键查询evdo卡信息
     * @param rescInstanceId
     * @return
     */
    //public RcSimEvdoVO qryEvdo(String rescInstanceId) {
    public RcSimEvdoVO qryEvdo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String rescInstanceId  = (String)map.get("rescInstanceId");
        
        RcSimEvdoVO vo = null;
        RcSimEvdoDAO dao = SrNSDAOFactory.getInstance().getRcSimEvdoDAO();

        if ((rescInstanceId != null) && (rescInstanceId.trim().length() > 0)) {
            vo = dao.findEvdoBySimId(rescInstanceId);
        } else {
            vo = new RcSimEvdoVO();
        }

        return vo;
    }

    public String modifySimAndRela(RcSimVO vo) {
        //1、根据sim_chip_type更新RC_SIM_RELA表  added by panyazong 
        RcSimRelaDAO reladao = SrNSDAOFactory.getInstance().getRcSimRelaDAO();
        RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();

        SqlExcuteByStr G = new SqlExcuteByStr();
        String sql = "select sim_chip_type as result from rc_sim where resource_instance_code = '" +
            vo.getRescInstanceCode() + "'";
        String oldType = G.getString(sql);
        String newType = vo.getSimChipType();
        String simCodeCorr = vo.getSimCodeCorr();

        RcSimRelaVO rcSimRelaVO = new RcSimRelaVO();
        String imsi2 = "";

        if ((simCodeCorr != null) && (simCodeCorr.length() > 0)) {
            //	  	    String serialSql = "select seq_serial_id.nextval as result from dual";
            //	  	    String serial=G.getString(serialSql);       	  
            String imsi2Sql = "select imsi_id as result from rc_sim where resource_instance_code = '" +
                vo.getSimCodeCorr() + "'";
            imsi2 = G.getString(imsi2Sql);

            rcSimRelaVO.setSerialId("-1");
        }

        //单芯卡(0)，单芯语音卡(1)、单芯数据卡(2)，一卡双芯语音卡(3)，一卡双芯数据卡(4)。	
        if (!oldType.equals(newType)) {
            //情况1：变成0、1、2
            if ((newType.equals("0") || newType.equals("1") ||
                    newType.equals("2"))) {
                //如果原来是双芯卡，即要修改原来副卡rc_sim表的sim_chip_type字段为0，以及在RC_SIM_RELA表中删除记录			
                if (oldType.equals("3")) {
                    //查原来副卡的iccid
                    String addiIccid = reladao.getAddiIccid(vo.getRescInstanceCode());
                    reladao.deleteByMainIccid(vo.getRescInstanceCode());
                    dao.updateSimChipType(addiIccid, "0");
                } else if (oldType.equals("4")) {
                    String mainIccid = reladao.getMainIccid(vo.getRescInstanceCode());
                    reladao.deleteByAddiIccid(vo.getRescInstanceCode());
                    dao.updateSimChipType(mainIccid, "0");
                }
            } else { //情况2：变成3、4

                String relaRtn = dao.isAlreadyExist(vo.getSimCodeCorr(),
                        vo.getOperId(), vo.getDepartId());

                if ("-1".equals(relaRtn) || "-2".equals(relaRtn)) {
                    return relaRtn;
                }

                //由0、1、2变成3
                if ((oldType.equals("0") || oldType.equals("1") ||
                        oldType.equals("2")) && newType.equals("3")) {
                    dao.updateSimChipType(simCodeCorr, "4");
                    rcSimRelaVO.setMainImsi(vo.getImsiId());
                    rcSimRelaVO.setAdditionalImsi(imsi2);
                    rcSimRelaVO.setMainIccid(vo.getRescInstanceCode());
                    rcSimRelaVO.setAdditionalIccid(vo.getSimCodeCorr());
                    reladao.insert(rcSimRelaVO);
                }
                //由0、1、2变成4
                else if ((oldType.equals("0") || oldType.equals("1") ||
                        oldType.equals("2")) && newType.equals("4")) {
                    dao.updateSimChipType(simCodeCorr, "3");
                    rcSimRelaVO.setMainImsi(imsi2);
                    rcSimRelaVO.setAdditionalImsi(vo.getImsiId());
                    rcSimRelaVO.setMainIccid(vo.getSimCodeCorr());
                    rcSimRelaVO.setAdditionalIccid(vo.getRescInstanceCode());
                    reladao.insert(rcSimRelaVO);
                }
                //如果原来是双芯卡，即要修改原来副卡rc_sim表的sim_chip_type字段为0，以及在RC_SIM_RELA表中删除记录
                //由3变成4
                else if (oldType.equals("3") && newType.equals("4")) {
                    dao.updateSimChipType(simCodeCorr, "3");

                    String addiIccid = reladao.getAddiIccid(vo.getRescInstanceCode());
                    dao.updateSimChipType(addiIccid, "0");
                    reladao.deleteByMainIccid(vo.getRescInstanceCode());
                    dao.updateSimChipType(simCodeCorr, "3");
                    rcSimRelaVO.setMainImsi(imsi2);
                    rcSimRelaVO.setAdditionalImsi(vo.getImsiId());
                    rcSimRelaVO.setMainIccid(vo.getSimCodeCorr());
                    rcSimRelaVO.setAdditionalIccid(vo.getRescInstanceCode());
                    reladao.insert(rcSimRelaVO);
                }
                //由4变成3
                else if (oldType.equals("4") && newType.equals("3")) {
                    dao.updateSimChipType(simCodeCorr, "4");

                    String mainIccid = reladao.getMainIccid(vo.getRescInstanceCode());
                    dao.updateSimChipType(mainIccid, "0");
                    reladao.deleteByAddiIccid(vo.getRescInstanceCode());
                    rcSimRelaVO.setMainImsi(vo.getImsiId());
                    rcSimRelaVO.setAdditionalImsi(imsi2);
                    rcSimRelaVO.setMainIccid(vo.getRescInstanceCode());
                    rcSimRelaVO.setAdditionalIccid(vo.getSimCodeCorr());
                    reladao.insert(rcSimRelaVO);
                }
            }
        }

        return "1";
    }

    /**
     * 批量插入双芯卡关系
     * @param map
     * @return
     */
    //public Map importDoubleSimInfo(Map map) {
    public Map importDoubleSimInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        if ((map == null) || (map.get("dataList") == null) ||
                (map.get("operId") == null) || (map.get("departId") == null)) {
            throw new LogicInfoException("方法importDoubleSimInfo缺少参数");
        }

        RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();
        RcSimRelaDAO reladao = SrNSDAOFactory.getInstance().getRcSimRelaDAO();
        List dataList = (List) map.get("dataList");
        String operId = (String) map.get("operId");
        String departId = (String) map.get("departId");
        List useList = new ArrayList();
        List useList1 = new ArrayList();
        List useList2 = new ArrayList();
        List failList = new ArrayList();
        String errInfo1 = "号码不存在或没有权限";
        String errInfo2 = "号码已经是双芯卡";
        String errInfo3 = "该卡没有绑定的IMSI信息，不能设置为双芯卡";
        String errInfo4 = "语音卡和数据卡不能是同一张卡";

        // 检验数据
        String[] param = null;
        String imsiId1 = null;
        String imsiId2 = null;
        Map simMap = new HashMap();

        List isExistList = new ArrayList();

        for (int i = 0; i < dataList.size(); i++) {
            param = (String[]) dataList.get(i);

            if (param[0].trim().equals(param[1].trim())) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    errInfo4);

                continue;
            }

            //校验在文本里面是否出现重复的iccid
            if (isExistList.contains(param[0])) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    param[0] + errInfo2);

                continue;
            }

            if (isExistList.contains(param[1])) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    param[1] + errInfo2);

                continue;
            }

            isExistList.add(param[0]);
            isExistList.add(param[1]);

            //校验iccid是否存在且有权限、是否已经是双芯卡
            imsiId1 = dao.isAlreadyExist(param[0], operId, departId);

            if ((imsiId1 == null) || (imsiId1.trim().length() < 1)) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    errInfo3);

                continue;
            } else if ("-1".equals(imsiId1)) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    param[0] + errInfo1);

                continue;
            } else if ("-2".equals(imsiId1)) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    param[0] + errInfo2);

                continue;
            }

            imsiId2 = dao.isAlreadyExist(param[1], operId, departId);

            if ((imsiId2 == null) || (imsiId2.trim().length() < 1)) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    errInfo3);

                continue;
            } else if ("-1".equals(imsiId2)) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    param[1] + errInfo1);

                continue;
            } else if ("-2".equals(imsiId2)) {
                failList.add(" " + param[0] + " " + param[1] + " --- " +
                    param[1] + errInfo2);

                continue;
            }

            useList1.add(param[0]);
            useList2.add(param[1]);
            useList.add(new String[] { "-1", imsiId1, param[0], imsiId2, param[1] });
        }

        System.out.println("==========可以导入的双芯卡列表==useList===============");

        for (int i = 0; i < useList.size(); i++) {
            System.out.println(((String[]) useList.get(i))[0] + " " +
                ((String[]) useList.get(i))[2]);
        }

        dao.updateBatchSimChipType(useList1, "3");
        dao.updateBatchSimChipType(useList2, "4");

        int successNum = reladao.insertBatch(useList);
        Map retMap = new HashMap();
        retMap.put("failList", failList);
        retMap.put("successNum", String.valueOf(successNum));

        return retMap;
    }

    /**
     * 批量设置单芯卡
     * @param map
     * @return
     */
    //public Map updateBatchSimChipType(Map map, String simChipType) {
    public Map updateBatchSimChipType(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String simChipType = (String) map.get("flag");
        
        if ((map == null) || (map.get("dataList") == null) ||
                (map.get("operId") == null) || (map.get("departId") == null)) {
            throw new LogicInfoException("方法importDoubleSimInfo缺少参数");
        }

        RcSimDAO dao = SrNSDAOFactory.getInstance().getRcSimDAO();
        List dataList = (List) map.get("dataList");
        String operId = (String) map.get("operId");
        String departId = (String) map.get("departId");
        List useList = new ArrayList();
        List failList = new ArrayList();
        String errInfo1 = "号码不存在或没有权限";
        String errInfo2 = "号码已经是双芯卡";
        String errInfo3 = "失败，未知错误";
        String errInfo4 = "语音卡和数据卡不能是同一张卡";

        // 检验数据
        String param = null;
        String imsiId1 = null;
        String imsiId2 = null;
        Map simMap = new HashMap();

        List isExistList = new ArrayList();

        for (int i = 0; i < dataList.size(); i++) {
            param = (String) dataList.get(i);

            //校验在文本里面是否出现重复的iccid
            if (isExistList.contains(param)) {
                failList.add(" " + param + " --- " + "号码重复出现");

                continue;
            }

            isExistList.add(param);

            //校验iccid是否存在且有权限、是否已经是双芯卡
            imsiId1 = dao.isAlreadyExist(param, operId, departId);

            if ((imsiId1 == null) || (imsiId1.trim().length() < 1)) {
                failList.add(" " + param + " --- " + errInfo3);

                continue;
            } else if ("-1".equals(imsiId1)) {
                failList.add(" " + param + " --- " + errInfo1);

                continue;
            } else if ("-2".equals(imsiId1)) {
                failList.add(" " + param + " --- " + errInfo2);

                continue;
            }

            useList.add(param);
        }
        
        if (useList == null) {
            return null;
        }

        for (int i = 0; i < useList.size(); i++) {
            System.out.println(((String) useList.get(i)));
        }

        int successNum = dao.updateBatchSimChipType(useList, simChipType);
        Map retMap = new HashMap();
        retMap.put("failList", failList);
        retMap.put("successNum", String.valueOf(successNum));

        return retMap;
    }
}
