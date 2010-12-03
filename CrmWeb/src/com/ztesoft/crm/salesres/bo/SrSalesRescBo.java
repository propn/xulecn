package com.ztesoft.crm.salesres.bo;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.salesres.dao.DcDeviceDAO;
import com.ztesoft.crm.salesres.dao.RcEntityDAO2;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.SaleRescPricDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SalesRescIdRelaDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.DcDeviceVO;
import com.ztesoft.crm.salesres.vo.RrBusinessVO;
import com.ztesoft.crm.salesres.vo.SaleRescPricVO;
import com.ztesoft.crm.salesres.vo.SalesRescIdRelaVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SrSalesRescBo extends DictAction {
    public SrSalesRescBo() {
    }

    /**
     * ��ѯӪ����Դ
     *
     * @param map
     *            HashMap
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qrySalesResource(DynamicDict dto)
        throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        int pageIndex = ((Integer) map.get("pageIndex")).intValue();
        int pageSize = ((Integer) map.get("pageSize")).intValue();

        StringBuffer cond = new StringBuffer();

        if (!"".equals(map.get("fee_item_id"))) {
            cond.append(" AND A.fee_item_id = " + map.get("fee_item_id"));
        }

        if (!"".equals(map.get("sales_resource_id"))) {
            cond.append(" AND A.sales_resource_id = " +
                map.get("sales_resource_id"));
        }

        if (!"".equals(map.get("sales_resource_cod"))) {
            cond.append(" AND A.sales_resource_code = '" +
                map.get("sales_resource_cod") + "'");
        }

        if (!"".equals(map.get("sales_resource_nam"))) {
            //UR46479,������ƴ��пո񣬾Ͳ鲻������
            //        cond.append(" AND A.sales_resource_name like '%"
            //                    + map.get("sales_resource_nam")+ "%'");
            cond.append(" AND A.sales_resource_name like '%" +
                ((String) map.get("sales_resource_nam")).replaceAll(" ", "_") +
                "%'");
        }

        if (!"".equals(map.get("sales_resource_fad"))) {
            cond.append(" AND B.family_id =" + map.get("sales_resource_fad"));
        }

        SalesRescDAO salesRescDAO = SrDAOFactory.getInstance().getSalesRescDAO();

        return PageHelper.popupPageModel(salesRescDAO, cond.toString(),
            pageIndex, pageSize);
    }

    /**
     * ����Ӫ����Դ
     *
     * @param SalesRescVO
     *            salesRescVO
     * @throws DAOSystemException
     * @return String
     */
    public String saveSalesResource(DynamicDict dto) throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        SalesRescVO salesRescVO = (SalesRescVO) map.get("vo");

        return doSaveSalesResource(salesRescVO);
    }

    private String doSaveSalesResource(SalesRescVO salesRescVO) {
        SalesRescDAO salesRescDAO = SrDAOFactory.getInstance().getSalesRescDAO();
        SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
        SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();

        List CodeList = salesRescDAO.findByCond("  FAMILY_ID = " +
                salesRescVO.getFamilyId() + " AND SALES_RESOURCE_CODE = '" +
                salesRescVO.getSalesRescCode() + "'");

        if (CodeList.size() > 0) {
            return "-1";
        } else {
            List NameList = salesRescDAO.findByCond("   FAMILY_ID = " +
                    salesRescVO.getFamilyId() + " AND SALES_RESOURCE_NAME = '" +
                    salesRescVO.getSalesRescName() + "'");

            if (NameList.size() > 0) {
                return "-2";
            }
        }

        String SalesRescId = sequenceManageDAO.getNextSequence("SALES_RESOURCE",
                "SALES_RESOURCE_ID");
        salesRescVO.setSalesRescId(SalesRescId);
        salesRescDAO.insert(salesRescVO);

        return SalesRescId;
    }

    /**
     * �޸�Ӫ����Դ
     *
     * @param SalesRescVO
     *            salesRescVO
     * @throws DAOSystemException
     */
    public String updateSalesResource(DynamicDict dto)
        throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        SalesRescVO salesRescVO = (SalesRescVO) map.get("voNew");
        SalesRescVO oldSalesRescVO = (SalesRescVO) map.get("voOld");

        return doUpdateSalesResource(salesRescVO, oldSalesRescVO);
    }

    private String doUpdateSalesResource(SalesRescVO salesRescVO,
        SalesRescVO oldSalesRescVO) {
        String result = "0";
        SalesRescDAO salesRescDAO = SrDAOFactory.getInstance().getSalesRescDAO();

        List NameList = salesRescDAO.findByCond("   FAMILY_ID = " +
                salesRescVO.getFamilyId() + " AND SALES_RESOURCE_NAME = '" +
                salesRescVO.getSalesRescName() + "'");

        //		 ������Ƹı��ˣ�����Ҫ����µ������Ƿ��������
        if (!oldSalesRescVO.getSalesRescName()
                               .equalsIgnoreCase(salesRescVO.getSalesRescName()) ||
                !oldSalesRescVO.getFamilyId()
                                   .equalsIgnoreCase(salesRescVO.getFamilyId())) {
            if (NameList.size() > 0) {
                return "-2";
            }
        } else { // �������û�иı䣬����Ҫ��⵱ǰ�����Ƿ��������
                 /*if (NameList.size() > 1) {
              return "-2";
            }*/
        }

        salesRescDAO.update(" sales_resource_id=" +
            salesRescVO.getSalesRescId(), salesRescVO);

        return result;
    }

    /**
     * ɾ��Ӫ����Դ
     *
     * @param SalesRescVO
     *            salesRescVO
     * @throws DAOSystemException
     */
    public java.util.HashMap removeSalesResource(DynamicDict dto)
        throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        SalesRescVO salesRescVO = (SalesRescVO) map.get("vo");
        String reString = "";
        java.util.HashMap reMap = new java.util.HashMap();

        if (this.checkSalesRescRela(salesRescVO.getSalesRescId())) {
            reMap.put("ERROR_MESSAGE", "����Դ������ϵͳ��Դ��������ɾ��");

            return reMap;
        }

        if ((salesRescVO.getManageMode() != null) &&
                salesRescVO.getManageMode().equals("0")) {
            SqlComDAO comDAO = SrDAOFactory.getInstance().getSqlComDAO();
            List comList = comDAO.qryComSql(
                    "select T.DC_NAME from dc_sql T where T.dc_sql  = '" +
                    salesRescVO.getFamilyId() + "'", new String[] { "DC_NAME" });

            if ((comList != null) && (comList.size() > 0)) {
                long nolon = 0;
                String comFamilyId = String.valueOf(((Map) comList.get(0)).get(
                            "DC_NAME"));

                if (comFamilyId.equalsIgnoreCase("Rc_No_FamilyId")) {
                    nolon = comDAO.count(
                            "select count(*)as COL_COUNTS from rc_no where sales_resource_id=" +
                            salesRescVO.getSalesRescId());
                } else if (comFamilyId.equalsIgnoreCase("Rc_SIM_FamilyId")) {
                    nolon = comDAO.count(
                            "select count(*)as COL_COUNTS from rc_sim where sales_resource_id=" +
                            salesRescVO.getSalesRescId());
                }

                if (nolon > 0) {
                    reString = "Ӫ����Դ��������Դʵ��,����ɾ����";
                    reMap.put("ERROR_MESSAGE", reString);

                    return reMap;
                }
            } else {
                RcEntityDAO2 rcEntityDAO = SrDAOFactory.getInstance()
                                                       .getRcEntityDAO2();
                String whereCond = " a.SALES_RESOURCE_ID=" +
                    salesRescVO.getSalesRescId();
                List entityList = rcEntityDAO.findByCond(whereCond);

                if (entityList.size() > 0) {
                    reString = "Ӫ����Դ��������Դʵ��,����ɾ����";
                    reMap.put("ERROR_MESSAGE", reString);

                    return reMap;
                }
            }
        } else if ((salesRescVO.getManageMode() != null) &&
                salesRescVO.getManageMode().equals("1")) {
            SqlComDAO comDAO = SrDAOFactory.getInstance().getSqlComDAO();
            long lon = comDAO.count(
                    "select count(*) as COL_COUNTS from rc_stock where STOCK_AMOUNT>0 and sales_resource_id=" +
                    salesRescVO.getSalesRescId());

            if (lon > 0) {
                reString = "Ӫ����Դ��������Դ�����,����ɾ����";
                reMap.put("ERROR_MESSAGE", reString);

                return reMap;
            }
        }

        //�ȰѶ�Ӧ����Դ���ԡ���Դ����ɾ��-----added  by ������
        SqlComDAO com = SrDAOFactory.getInstance().getSqlComDAO();
        String[] params = new String[] { salesRescVO.getSalesRescId() };
        String sql = "delete from sales_resource_attr WHERE  sales_resource_id=?";
        com.excute(sql, params);
        sql = "delete from SALE_RESOURCE_PRIC WHERE  sales_resource_id=?";
        com.excute(sql, params);

        SalesRescDAO salesRescDAO = SrDAOFactory.getInstance().getSalesRescDAO();
        salesRescDAO.deleteByCond(" sales_resource_id=" +
            salesRescVO.getSalesRescId());
        reMap.put("ERROR_MESSAGE", reString);

        return reMap;
    }

    /**
     * ����������ѯ�����еĶ�Ӧ��֤����Ϣ��
     * @param sType
     * @param sContent
     * @param beginDate
     * @param endDate
     * @return
     */
    public PageModel getSalesResourceInfo(DynamicDict dto)
        throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        String sContent = (String) map.get("sContent");
        String sType = (String) map.get("sType");
        String familyId = (String) map.get("familyId");
        int pi = ((Integer) map.get("pageIndex")).intValue();
        int ps = ((Integer) map.get("pageSize")).intValue();
        PageModel pm = new PageModel();
        String sql = "";

        try {
            if (sContent == null) {
                sContent = "";
            }

            if (sType == null) {
                sType = "";
            }

            sql += (" and A.family_id =" + familyId);

            if (!("".equals(sContent))) {
                if ("sid".equals(sType)) {
                    sql += ("  and A.sales_resource_id='" + sContent + "' ");
                }

                if ("sname".equals(sType)) {
                    sql += (" and A.sales_resource_name like '%" + sContent +
                    "%' ");
                }
            }

            //sql +=" order by sales_resource_id asc";
            SalesRescDAO pdao = SrDAOFactory.getInstance().getSalesRescDAO();
            pm = PageHelper.popupPageModel(pdao, sql, pi, ps);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pm;
    }

    public List qrySrAttrInfo(DynamicDict dto) throws FrameException {
        Map map = (Map) dto.getValueByName("parameter");
        String salesRescId = (String) map.get("salesRescId");
        SalesRescDAO salesRescDAO = SrDAOFactory.getInstance().getSalesRescDAO();

        return salesRescDAO.qrySrAttrInfo(salesRescId);
    }

    /**
     * ����ʵ����Դ��������Ϣ��
     * @param rescInstanceId,attrId,attrValueId
     * @return
     */
    public String insertAttrInfo(String rescInstanceId, String attrId,
        String attrValue) {
        if (this.checkSalesRescRela(rescInstanceId)) {
            return "-1";
        }

        String flag = "";
        boolean bFlag = false;

        try {
            SalesRescDAO pdao = SrDAOFactory.getInstance().getSalesRescDAO();
            long lCount = pdao.countAttrInfo(rescInstanceId, attrId);

            if (lCount != 0) {
                flag = "1";

                return flag;
            }

            bFlag = pdao.insertAttrInfo(rescInstanceId, attrId, attrValue);

            if (bFlag) {
                flag = "2";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    /**
     * ɾ��һ��������Դ�����ԡ�
     * @param rcFamilyId,attrValue
     * @return
     */
//    public int deleteAttrInfo(String rescInstanceId, String attrValue) {
    public int deleteAttrInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String rescInstanceId  = (String)map.get("salesRescId");
        String attrValue  = (String)map.get("attrId");
        
        
        int retResult = 0;

        try {
            if (this.checkSalesRescRela(rescInstanceId)) {
                return -1;
            }

            SalesRescDAO pdao = SrDAOFactory.getInstance().getSalesRescDAO();

            if ((attrValue == null) || "".equals(attrValue) ||
                    (rescInstanceId == null) || "".equals(rescInstanceId)) {
                return retResult;
            } else {
                retResult = pdao.deleteAttrInfo(rescInstanceId, attrValue);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return retResult;
    }

    // ������DcDeviceVO�Ĵ���

    /**
     * �����豸������豸���Ʋ�ѯ�豸����ҳ���ң�
     *
     * @param map
     *            HashMap
     * @param pageIndex
     *            int
     * @param pageSize
     *            int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryDevice(Map map, int pageIndex, int pageSize)
        throws DAOSystemException {
        StringBuffer cond = new StringBuffer();
        String condStr = null;

        if ((map.get("devId") != null) && !"".equals(map.get("devId"))) {
            cond.append(" dev_id=" + map.get("devId")).append(" and ");
        }

        if ((map.get("scode") != null) &&
                !"".equals((String) map.get("scode"))) {
            cond.append(" scode = '" + (String) map.get("scode") + "'")
                .append(" and ");
        }

        if ((map.get("sname") != null) &&
                !"".equals((String) map.get("sname"))) {
            cond.append(" sname = '" + (String) map.get("sname") + "'")
                .append(" and ");
        }

        if ((map.get("devType") != null) &&
                !"".equals((String) map.get("devType"))) {
            cond.append(" DEV_TYPE = '" + (String) map.get("devType") + "'")
                .append(" and ");
        }

        if ((map.get("dflag") != null) &&
                !"".equals((String) map.get("dflag"))) {
            cond.append(" dflag = '" + (String) map.get("dflag") + "'")
                .append(" and ");
        }

        if ((map.get("sortby") != null) &&
                !"".equals((String) map.get("sortby"))) {
            cond.append(" sortby = " + (String) map.get("sortby"))
                .append(" and ");
        }

        if ((map.get("vflag") != null) &&
                !"".equals((String) map.get("vflag"))) {
            cond.append(" vflag = '" + (String) map.get("vflag") + "'")
                .append(" and ");
        }

        condStr = cond.toString().trim();

        if (condStr.endsWith("and")) {
            condStr = condStr.substring(0, condStr.lastIndexOf("and"));
        }

        DcDeviceDAO dao = SrDAOFactory.getInstance().getDeviceDAO();

        /*    if(map.get("provId")!=null && map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)){
                    dao.setSQL_SELECT("SELECT DEV_ID AS SCODE,SNAME,DEV_TYPE,DFLAG,SORTBY,VFLAG,COMMENTS FROM DC_DEVICE");
            }*/
        return PageHelper.popupPageModel(dao, condStr, pageIndex, pageSize);
    }

    /**
     * ɾ���豸
     * ͬʱ�鿴��Ӧ��Sale_Resource����ļ�¼��rc_entity�����Ƿ���ڼ�¼������������˳�������ɾ���ü�¼
     * @param DcDeviceVO vo
     * @throws DAOSystemException
     */
    public Map removeDevice(DcDeviceVO vo) throws DAOSystemException {
        if ((vo == null) || (vo.getScode() == null) ||
                (vo.getScode().trim().length() <= 0)) {
            return null;
        }

        String reString = "";
        Map reMap = new HashMap();
        DcDeviceDAO dcDeviceDAO = SrDAOFactory.getInstance().getDeviceDAO();

        //�鿴��Ӧ��Sale_Resource����ļ�¼��rc_entity�����Ƿ���ڼ�¼������������˳�������ɾ���ü�¼
        SalesRescDAO salesRescDao = SrDAOFactory.getInstance().getSalesRescDAO();
        String sql1 = " SALES_RESOURCE_CODE='" + vo.getScode() + "'";
        List listRes = salesRescDao.findByCond(sql1);
        RcEntityDAO2 rcEntityDAO = SrDAOFactory.getInstance().getRcEntityDAO2();
        SalesRescVO salesRescVO = null;

        if (listRes != null) {
            for (int i = 0; i < listRes.size(); i++) {
                salesRescVO = (SalesRescVO) listRes.get(i);

                if (salesRescVO != null) {
                    String whereCond = " a.SALES_RESOURCE_ID=" +
                        salesRescVO.getSalesRescId();
                    List entityList = rcEntityDAO.findByCond(whereCond);

                    if (entityList.size() > 0) {
                        reString = "Ӫ����Դ��������Դʵ��,����ɾ����";
                        reMap.put("ERROR_MESSAGE", reString);

                        return reMap;
                    }
                }
            }
        }

        dcDeviceDAO.deleteByCond(" scode='" + vo.getScode() + "'");
        reMap.put("ERROR_MESSAGE", "");

        return reMap;
    }

    /**
     * �����豸
     *
     * @param DcDeviceVO vo
     * @throws DAOSystemException
     * @return String
     */
    public String saveDevice(DcDeviceVO vo) throws DAOSystemException {
        if (vo == null) {
            return null;
        }

        DcDeviceDAO dcDeviceDAO = SrDAOFactory.getInstance().getDeviceDAO();

        if ((vo.getScode() == null) || (vo.getScode().trim().length() <= 0)) {
            throw new DAOSystemException("�豸���벻��Ϊ��!");
        }

        dcDeviceDAO.insert(vo);

        return vo.getScode();
    }

    /**
     * �޸��豸
     *
     * @param SalesRescVO
     *            salesRescVO
     * @throws DAOSystemException
     */
    public void updateDevice(DcDeviceVO vo, String provId)
        throws DAOSystemException {
        if (vo == null) {
            return;
        }

        DcDeviceDAO dcDeviceDAO = SrDAOFactory.getInstance().getDeviceDAO();
        /*    if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
                    dcDeviceDAO.setUpdate_flag(true);
                    dcDeviceDAO.setSQL_UPDATE("UPDATE DC_DEVICE SET  DEV_ID = ?, SNAME = ?, DEV_TYPE = ?, DFLAG = ?, SORTBY = ?, VFLAG = ?, COMMENTS = ? WHERE");
                    dcDeviceDAO.update(" DEV_ID=" + vo.getScode(), vo);
            }else*/
        {
            dcDeviceDAO.update(" scode='" + vo.getScode() + "'", vo);
        }
    }

    // �����Ƕ��豸Ӫ����Դ��ϵ��Ĳ���

    /**
     * �������ϵͳ���롢�豸���롢Ӫ����ԴID�Ƿ��ڹ�ϵ�����Ѵ���
     * ��������򷵻���Ϣ��������������򷵻ؿ�
     * @param ncSalesRescId String
     * @param dcDeviceScode String
     * @param salesRescId String
     * @return String
     */
    public String checkExistsResc(String ncSalesRescId, String dcDeviceScode,
        String salesRescId, SalesRescVO salesRescVO, String provId)
        throws DAOSystemException {
        StringBuffer buffer = new StringBuffer();
        List listRtn = null;

        if ((ncSalesRescId != null) && (ncSalesRescId.trim().length() > 0)) {
            listRtn = this.qryDeviceSalesRel(ncSalesRescId, null, null);

            if ((listRtn != null) && (listRtn.size() > 0)) {
                buffer.append("����ϵͳ�����Ѵ��ڣ�������ѡ��!").append("\n");
            }
        }

        if ((dcDeviceScode != null) && (dcDeviceScode.trim().length() > 0)) {
            Map map1 = new HashMap();
            /*      if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
                          map1.put("scode", dcDeviceScode);
                  }else*/
            {
                map1.put("scode", dcDeviceScode);
            }

            map1.put("provId", provId);

            PageModel page = this.qryDevice(map1, -1, -1);

            if ((page != null) && (page.getList() != null) &&
                    (page.getList().size() > 0)) {
                buffer.append("�豸�����Ѵ��ڣ�������ѡ��!").append("\n");
            }
        }

        if ((salesRescId != null) && (!"".equals(salesRescId))) {
            SalesRescDAO salesRescDAO = SrDAOFactory.getInstance()
                                                    .getSalesRescDAO();
            String cond = " sales_resource_id=" + salesRescId;
            listRtn = salesRescDAO.findByCond(cond);

            if ((listRtn != null) && (listRtn.size() > 0)) {
                buffer.append("Ӫ����ԴID�Ѵ��ڣ�������ѡ��!").append("\n");
            }
        }

        SalesRescDAO salesRescDAOI = SrDAOFactory.getInstance().getSalesRescDAO();
        List CodeList = salesRescDAOI.findByCond("  FAMILY_ID = " +
                salesRescVO.getFamilyId() + " AND SALES_RESOURCE_CODE = '" +
                DAOUtils.filterQureyCond(salesRescVO.getSalesRescCode()) + "'");

        if (CodeList.size() > 0) {
            buffer.append("���ã�����ѡ����Դ�����У���Ӫ����Դ�����Ѿ����ڣ�����������!").append("\n");
        } else {
            List NameList = salesRescDAOI.findByCond("   FAMILY_ID = " +
                    salesRescVO.getFamilyId() + " AND SALES_RESOURCE_NAME = '" +
                    salesRescVO.getSalesRescName() + "'");

            if (NameList.size() > 0) {
                buffer.append("���ã�����ѡ����Դ�����У���Ӫ����Դ�����Ѿ����ڣ�����������!").append("\n");
            }
        }

        String strRtn = buffer.toString().trim();

        if (strRtn.length() > 0) {
            return strRtn;
        } else {
            return null;
        }
    }

    /**
     * ��ѯ�豸��Դ��ϵ��¼
     * @param ncSalesRescId String
     * @param dcDeviceScode String
     * @param salesRescId String
     * @return List
     * @throws DAOSystemException
     */
    public List qryDeviceSalesRel(String ncSalesRescId, String dcDeviceScode,
        String salesRescId) throws DAOSystemException {
        if (((salesRescId == null) || (salesRescId.trim().length() <= 0)) &&
                ((ncSalesRescId == null) ||
                (ncSalesRescId.trim().length() <= 0)) &&
                ((dcDeviceScode == null) ||
                (dcDeviceScode.trim().length() <= 0))) {
            return null;
        }

        SalesRescIdRelaDAO dao = SrDAOFactory.getInstance()
                                             .getSalesRescIdRelaDAO();
        StringBuffer buffer = new StringBuffer();

        if ((ncSalesRescId != null) && (ncSalesRescId.trim().length() > 0)) {
            buffer.append(" NC_SALES_RESOURCE_ID='").append(ncSalesRescId)
                  .append("'").append(" and");
        }

        if ((dcDeviceScode != null) && (dcDeviceScode.trim().length() > 0)) {
            buffer.append(" DC_DEVICE_SCODE='").append(dcDeviceScode).append("'")
                  .append(" and");
        }

        if ((salesRescId != null) && (salesRescId.trim().length() > 0)) {
            buffer.append(" SALES_RESOURCE_ID=").append(salesRescId)
                  .append(" and");
        }

        String cond = buffer.toString().trim();

        if ((cond != null) && cond.endsWith("and")) {
            cond = cond.substring(0, cond.lastIndexOf("and"));
        }

        return dao.findByCond(cond);
    }

    /**
     * �����豸����Ѷ�Ӧ��ϵ�е���ؼ�¼ɾ��,Ȼ�����²���һ���豸����Դ��Ӧ�ļ�¼
     * �������ϵͳ�����Ӫ����ԴID���ظ��򷵻ش�����Ϣ���������ɹ��򷵻ؿ�
     * @param dcDeviceScode String
     * @param vo SalesRescIdRelaVO
     * @return String
     */
    public String updateDeviceSalesRel(String dcDeviceScode,
        SalesRescIdRelaVO vo) throws DAOSystemException {
        if ((dcDeviceScode == null) || (dcDeviceScode.trim().length() <= 0) ||
                (vo == null) || (vo.getDcDeviceScode() == null) ||
                (vo.getDcDeviceScode().trim().length() <= 0) ||
                (vo.getNcSalesRescId() == null) ||
                (vo.getNcSalesRescId().trim().length() <= 0) ||
                (vo.getSalesRescId() == null) ||
                (vo.getSalesRescId().trim().length() <= 0)) {
            return "����Ĳ������������޷�����ϵͳ�������Ϣ!";
        }

        SalesRescIdRelaDAO dao = SrDAOFactory.getInstance()
                                             .getSalesRescIdRelaDAO();
        String cond1 = " DC_DEVICE_SCODE = '" + dcDeviceScode + "'";
        List listRes = null;

        // �����Ƿ����ncSalesRescId�ֶκ�salesRescId�ֶ��Ƿ����ظ���¼
        StringBuffer buffer = new StringBuffer();
        String cond2 = " NC_SALES_RESOURCE_ID='" + vo.getNcSalesRescId() +
            "' and DC_DEVICE_SCODE!='" + dcDeviceScode + "'";
        listRes = dao.findByCond(cond2);

        if ((listRes != null) && (listRes.size() > 0)) {
            buffer.append("����ϵͳ�������ظ�����������д!").append("\n");
        }

        cond2 = " SALES_RESOURCE_ID=" + vo.getSalesRescId() +
            " and DC_DEVICE_SCODE!='" + dcDeviceScode + "'";
        listRes = dao.findByCond(cond2);

        if ((listRes != null) && (listRes.size() > 0)) {
            buffer.append("Ӫ����ԴID���ظ�����������д!");
        }

        if (buffer.toString().trim().length() > 0) {
            return buffer.toString();
        }

        // ɾ����¼
        dao.deleteByCond(cond1);
        // �������
        dao.insert(vo);

        return null;
    }

    // ������������豸��Դ�����ķ���

    /**
     * ���ݲ�ѯ������Ӫ����Դɾ���豸��Ӫ����Դ
     * @param DcDeviceVO vo
     * @throws DAOSystemException
     */
    public Map removeDeviceRes(String salesRescId, String provId)
        throws DAOSystemException {
        if (salesRescId == null) {
            return null;
        }

        String reString = "";
        java.util.HashMap reMap = new java.util.HashMap();

        if (this.checkSalesRescRela(salesRescId)) {
            reMap.put("ERROR_MESSAGE", "����Դ������ϵͳ��Դ�й���,����ɾ��!");

            return reMap;
        }

        // ��Ӫ����Դ���в�ѯӪ����¼
        SalesRescIdRelaDAO salesRescRelDao = SrDAOFactory.getInstance()
                                                         .getSalesRescIdRelaDAO();
        SalesRescDAO salesRescDao = SrDAOFactory.getInstance().getSalesRescDAO();
        DcDeviceDAO dcDeviceDao = SrDAOFactory.getInstance().getDeviceDAO();

        String condRes = " sales_resource_id=" + salesRescId;
        List listRes = salesRescDao.findByCond(condRes);

        if ((listRes == null) || (listRes.size() <= 0)) {
            reMap.put("ERROR_MESSAGE", "�����ڸ�Ӫ����Դ!");

            return reMap;
        }

        SalesRescVO vo = (SalesRescVO) listRes.get(0);

        // �����ж�rc_entity�����Ƿ������ü�¼������ļ�¼�����������ɾ��
        RcEntityDAO2 rcEntityDAO = SrDAOFactory.getInstance().getRcEntityDAO2();
        String whereCond = " a.SALES_RESOURCE_ID=" + vo.getSalesRescId();
        List entityList = rcEntityDAO.findByCond(whereCond);

        if (entityList.size() > 0) {
            reString = "Ӫ����Դ��������Դʵ��,����ɾ����";
            reMap.put("ERROR_MESSAGE", reString);

            return reMap;
        }

        //�����ж�rc_stock�����Ƿ������ü�¼�����ļ�¼�����������ɾ��
        RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
        List list = dao.findBySql("select storage_id,sales_resource_id,stock_amount ,'sales_resource_name' as sales_resource_name,'storage_name' as storage_name , 'UP_LIMIT' as UP_LIMIT,'DOWN_LIMIT' as DOWN_LIMIT,'DEPART_ID' as DEPART_ID from rc_stock where sales_resource_id=?",
                new String[] { vo.getSalesRescId() });

        if ((list != null) && (list.size() > 0)) {
            reString = "Ӫ����Դ��rc_stock���й�������Դʵ����������ɾ����";
            reMap.put("ERROR_MESSAGE", reString);

            return reMap;
        }

        // ɾ��Ӫ����Դ�۸���Ϣ
        this.delMutiSalesRescPric(vo.getSalesRescId());

        // ɾ��������SALES_RESOURCE_ID_RELA�е���ؼ�¼
        String cond = "DC_DEVICE_SCODE='" + vo.getSalesRescCode() +
            "' and SALES_RESOURCE_ID=" + vo.getSalesRescId();
        salesRescRelDao.deleteByCond(cond);
        // ��ѯ��ü�¼��Ӧ���ն��豸��¼����ɾ��
        /*    if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
                    dcDeviceDao.deleteByCond("dev_id=" + vo.getSalesRescCode());
            }else*/
        {
            dcDeviceDao.deleteByCond("scode='" + vo.getSalesRescCode() + "'");
        }

        // ɾ��Ӫ����Դ��¼
        salesRescDao.deleteByCond(" sales_resource_id=" + vo.getSalesRescId());
        reMap.put("ERROR_MESSAGE", reString);

        return reMap;
    }

    /**
     * �����豸,Ӫ����Դ��������Ӧ�Ĺ�ϵ
     * @param DcDeviceVO vo
     * @throws DAOSystemException
     * @return String:Ӫ����Դ����
     */
    public String saveDeviceRes(DcDeviceVO deviceVO, SalesRescVO salesVO,
        SalesRescIdRelaVO relVO, String provId) throws DAOSystemException {
        if ((deviceVO == null) || (deviceVO.getScode() == null) ||
                (deviceVO.getScode().trim().length() <= 0) ||
                (salesVO == null)) {
            return "��Ҫ��������ݲ�����!";
        }

        if (relVO == null) {
            relVO = new SalesRescIdRelaVO();
            relVO.setDcDeviceScode(deviceVO.getScode());
            relVO.setNcSalesRescId(deviceVO.getScode());
        }

        DcDeviceDAO deviceDao = SrDAOFactory.getInstance().getDeviceDAO();
        SalesRescDAO salesDao = SrDAOFactory.getInstance().getSalesRescDAO();
        SalesRescIdRelaDAO relDao = SrDAOFactory.getInstance()
                                                .getSalesRescIdRelaDAO();
        // �����豸��¼
        /*   if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
                   deviceDao.setSQL_INSERT("INSERT INTO DC_DEVICE ( DEV_ID,SNAME,DEV_TYPE,DFLAG,SORTBY,VFLAG,COMMENTS ) VALUES ( ?,?,?,?,?,?,? )");
           }*/
        deviceDao.insert(deviceVO);

        // ����Ӫ����Դ��¼
        String resourceId = this.doSaveSalesResource(salesVO);
        relVO.setSalesRescId(resourceId);
        relDao.insert(relVO);

        return resourceId;
    }

    /**
     * �޸��豸��Ӫ����Դ�����Ƕ�Ӧ�Ĺ�ϵ,����ɹ��򷵻�null,���򷵻ش�����Ϣ
     * @param SalesRescVO
     * @salesRescVO
     * @throws DAOSystemException
     */
    public String updateDeviceRes(DcDeviceVO deviceVO, SalesRescVO salesVO,
        SalesRescIdRelaVO relVO, SalesRescVO odlSalesRescVO, String provId)
        throws DAOSystemException {
        if ((deviceVO == null) || (salesVO == null) || (relVO == null)) {
            return null;
        }

        DcDeviceDAO deviceDao = SrDAOFactory.getInstance().getDeviceDAO();
        SalesRescDAO salesDao = SrDAOFactory.getInstance().getSalesRescDAO();
        SalesRescIdRelaDAO relDao = SrDAOFactory.getInstance()
                                                .getSalesRescIdRelaDAO();

        String mess = this.updateDeviceSalesRel(deviceVO.getScode(), relVO);

        if (mess != null) {
            return mess;
        }

        this.updateDevice(deviceVO, provId);

        return this.doUpdateSalesResource(salesVO, odlSalesRescVO);
    }

    ////////////////////////////////////////Ӫ����Դ�ļ۸����/////////////////////////////
    /**
     * ����lanId��ѯӪҵ��
     * @param lanId
     * @return
     */
    public List qryRrBusiness(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");
        String lanId = (String) m.get("lanId");

        return doQryRrBusiness(lanId);
    }

    private List doQryRrBusiness(String lanId) {
        RrBusinessDAO dao = SrDAOFactory.getInstance().getRrBusinessDAO();
        String cond = " lan_id=";

        if ((lanId != null) && (lanId.trim().length() > 0)) {
            cond += lanId;
        } else {
            cond = " 1=1 ";
        }

        List list = dao.findByCond(cond);

        return list;
    }

    /**
     * ����Ӫ����Դid��ѯ��Ӫ����Դ�ļ۸񼯺�
     * @param salesRescId
     * @return
     */
    public List getSalesRescPrics(DynamicDict dto) throws FrameException {
        Map m = (Map) dto.getValueByName("parameter");

        //	int pi  = ((Integer)m.get("pageIndex")).intValue();
        //	int ps  = ((Integer)m.get("pageSize")).intValue();
        String salesRescId = (String) m.get("salesRescId");

        //String provId = (String)m.get("provId");
        return doQrySalesRescPrics(salesRescId);
    }

    private List doQrySalesRescPrics(String salesRescId) {
        if ((salesRescId == null) || (salesRescId.trim().length() < 1)) {
            return new ArrayList();
        }

        //    if(provId==null || provId.trim().length()<1){
        //    	return new ArrayList();
        //    }
        String sql_select = "SELECT a.sales_resource_id,a.business_id,a.resource_level,a.price,acct_item_id,b.business_name,c.fee_item_name  as feeitem_name " +
            " FROM SALE_RESOURCE_PRIC a left outer join RR_BUSINESS b on a.business_id=b.business_id left outer join df_fee_item c on a.acct_item_id = c.fee_item_code where 1=1";

        SaleRescPricDAO dao = SrDAOFactory.getInstance().getSaleRescPricDAO();

        //    if( provId.equals(ParamsConsConfig.PROV_ID_GX) || provId.equals(ParamsConsConfig.PROV_ID_CQ))
        //    	dao.setSQL_SELECT(sql_select);
        String cond = " and a.sales_resource_id=" + salesRescId;
        List list = dao.findByCond(cond);

        return list;
    }

    /**
     * ����ĳһӪ����Դ�ļ۸����е�salesRescId��businssIds����Ϊ�գ���Ӷ��Ӫҵ���ļ۸񣬼۸���ͬ
     * ������ظ��ļ�¼�򱧴�
     * @param vo
     * @return Map:result:-1,��ʾ��������mess:������Ϣ
     * result:0,��ʾ�ɹ���mess:�ɹ���Ϣ
     */
//    public Map addSalesRescPrics(SaleRescPricVO vo) {
    public Map addSalesRescPrics(DynamicDict dto) throws FrameException {
         
        Map mp = (Map)dto.getValueByName("parameter");
        SaleRescPricVO vo  = (SaleRescPricVO)mp.get("vo");
        
        //���������������⴦����vo.getBusinessIds()Ϊ-1ʱ��ʾѡ������Ӫҵ��
        if ((vo.getBusinessIds() != null) && vo.getBusinessIds().equals("-1")) {
            List list = doQryRrBusiness("");

            if ((list != null) && (list.size() > 0)) {
                RrBusinessVO tempvo = new RrBusinessVO();
                String busIds = "";

                for (int i = 0; i < list.size(); i++) {
                    tempvo = (RrBusinessVO) list.get(i);
                    busIds += (tempvo.getBusinessId() + ",");
                }

                vo.setBusinessIds(busIds.substring(0, busIds.length() - 1));
            }
        }

        Map map = new HashMap();

        if ((vo == null) || (vo.getSalesRescId() == null) ||
                (vo.getSalesRescId().trim().length() < 1) ||
                (vo.getPrice() == null) || (vo.getPrice().trim().length() < 1) ||
                (vo.getBusinessIds() == null) ||
                (vo.getBusinessIds().trim().length() < 1)) {
            map.put("result", "-1");
            map.put("mess", "����ȱ�٣�����ʧ��!");

            return map;
        }

        if ((vo.getRescLevel() == null) ||
                (vo.getRescLevel().trim().length() < 1)) {
            vo.setRescLevel("-1");

            // �ж���Ҫ���������ļ�¼�����ݿ����Ƿ��Ѿ����ڣ���������ڷ��ش�����Ϣ
        }

        System.out.println(">>>>>>>>>>>>> " + vo.getSalesRescId() + " " +
            vo.getBusinessIds() + " " + vo.getProvId());

        String existMess = this.existsPrices(vo.getSalesRescId(),
                vo.getBusinessIds(), vo.getProvId());

        if ((existMess != null) && (existMess.trim().length() > 0)) {
            map.put("result", "-1");
            map.put("mess", existMess);

            return map;
        }

        String str = "�ɹ�����";
        int count = 0;
        SaleRescPricDAO dao = SrDAOFactory.getInstance().getSaleRescPricDAO();
        String[] businessIdArr = vo.splitBusinessIds();

        if ((businessIdArr != null) && (businessIdArr.length > 0)) {
            for (int i = 0; i < businessIdArr.length; i++) {
                vo.setBusinessId(businessIdArr[i]);
                dao.insert(vo);
                count++;
            }
        }

        str += (count + "���۸��¼!");
        map.put("result", "0");
        map.put("mess", str);

        return map;
    }

    /**
     * �жϸ�Ӫ����Դ��Ӫҵ���ļ۸��¼�Ƿ��Ѿ����ڣ����û���ظ���¼���򷵻�null�����򷵻��ظ���Ϣ
     * @param salesRescId
     * @param businessIds
     * @return
     */
    private String existsPrices(String salesRescId, String businessIds,
        String provId) {
        if ((salesRescId == null) || (salesRescId.trim().length() < 1) ||
                (businessIds == null) || (businessIds.trim().length() < 1)) {
            return null;
        }

        StringBuffer buffer = new StringBuffer("");
        SaleRescPricVO vo = new SaleRescPricVO();
        vo.setSalesRescId(salesRescId);
        vo.setBusinessIds(businessIds);
        vo.setRescLevel("-1");

        String[] arr = vo.splitBusinessIds();

        // ��ѯ��Ӫ����Դ�µ����м۸��¼�����ҳ��Ƿ����ظ���¼
        SaleRescPricVO voTemp = null;
        List list = this.doQrySalesRescPrics(salesRescId);

        if ((arr != null) && (arr.length > 0) && (list != null) &&
                (list.size() > 0)) {
            for (int i = 0; i < arr.length; i++) {
                vo.setBusinessId(arr[i]);

                for (int j = 0; j < list.size(); j++) {
                    voTemp = (SaleRescPricVO) list.get(j);

                    if ((voTemp != null) && voTemp.equalsByPk(vo)) {
                        buffer.append("\"").append(voTemp.getBusinessName())
                              .append("\",");

                        break;
                    }
                }
            }
        }

        if ((buffer == null) || (buffer.length() == 0)) {
            return null;
        } else {
            String strTemp = buffer.substring(0, buffer.length() - 1);

            return "����ʧ�ܣ���Ӫ����Դ��Ӫҵ����" + strTemp + "���Ѿ��м۸��¼����ȷ��!";
        }
    }

    /**
     * ��������ɾ��Ӫ����Դ�ļ۸��¼
     * @param vo
     * @return
     */
//    public int delSingleSalesRescPric(SaleRescPricVO vo) {
    public int delSingleSalesRescPric(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        SaleRescPricVO vo  = (SaleRescPricVO)map.get("vo");
        
        if ((vo == null) || (vo.getSalesRescId() == null) ||
                (vo.getSalesRescId().trim().length() < 1) ||
                (vo.getRescLevel() == null) ||
                (vo.getRescLevel().trim().length() < 1) ||
                (vo.getBusinessId() == null) ||
                (vo.getBusinessId().trim().length() < 1)) {
            return -1;
        }

        SaleRescPricDAO dao = SrDAOFactory.getInstance().getSaleRescPricDAO();

        return (int) dao.delete(vo.getBusinessId(), vo.getRescLevel(),
            vo.getSalesRescId());
    }

    /**
     * ����Ӫ����Դidɾ������Դ�����м۸��¼
     * @param salesRescId
     * @return
     */
    public int delMutiSalesRescPric(String salesRescId) {
        if ((salesRescId == null) || (salesRescId.trim().length() < 1)) {
            return 0;
        }

        String cond = " sales_resource_id=" + salesRescId;
        SaleRescPricDAO dao = SrDAOFactory.getInstance().getSaleRescPricDAO();

        return (int) dao.deleteByCond(cond);
    }

    /**
     * ����Ӫ����Դid�õ���Ӫ����Դ�Ĺ���ģʽ
     * @param salesRescId String
     * @return String
     */
    public String getManageMode(String salesRescId) {
        String manageMode = "";
        SalesRescDAO dao = SrDAOFactory.getInstance().getSalesRescDAO();
        SalesRescVO vo = dao.findByPrimaryKey(salesRescId);

        if ((vo != null) && (vo.getManageMode() != null)) {
            manageMode = vo.getManageMode();
        }

        return manageMode;
    }

    /**
     * ������Դ����id��������Ƿ��������rc_entity,rc_stock�����ļ�¼��
     * true:�й���,false:�޹�����"":�쳣��
     */
    public String checkSalesRescInfo(String salesRescId) {
        if ((salesRescId == null) || salesRescId.equals("")) {
            return "";
        }

        RcEntityDAO2 rcEntityDAO = SrDAOFactory.getInstance().getRcEntityDAO2();
        String whereCond = " a.SALES_RESOURCE_ID=" + salesRescId;
        List entityList = rcEntityDAO.findByCond(whereCond);

        if (entityList.size() > 0) {
            return "true";
        }

        RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
        List list = dao.findBySql("select storage_id,sales_resource_id,stock_amount ,'sales_resource_name' as sales_resource_name,'storage_name' as storage_name , 'UP_LIMIT' as UP_LIMIT,'DOWN_LIMIT' as DOWN_LIMIT,'DEPART_ID' as DEPART_ID from rc_stock where sales_resource_id=?",
                new String[] { salesRescId });

        if ((list != null) && (list.size() > 0)) {
            return "true";
        }

        return "false";
    }

    //////////////////////����Ӫ����Դ�Ƿ�������ϵͳ�����ģ�����ǣ��������κ��޸ģ����������ԣ�//////////////////////////
    public boolean checkSalesRescRela(String salesRescId) {
        // System.out.println("------------------------------:::"+salesRescId);
        if ((salesRescId == null) || "".equals(salesRescId)) {
            return false;
        }

        long lon = SrDAOFactory.getInstance().getSalesRescIdRelaDAO()
                               .countByCond(" sales_resource_id = " +
                salesRescId);

        if (lon > 0) {
            return true;
        }

        return false;
    }

//    public String updateAttrInfos(String salesRescId, List attrObjs) {
    public String updateAttrInfos(DynamicDict dto) throws FrameException {
         
        Map mp = (Map)dto.getValueByName("parameter");
        String salesRescId  = (String)mp.get("salesRescId");
        List attrObjs  = (List)mp.get("attrObjs");
        
        
        if ((salesRescId == null) || "".equals(salesRescId) ||
                (attrObjs == null) || (attrObjs.size() == 0)) {
            return "-1";
        }

        // ,delete,,insert,,,
        SalesRescDAO pdao = SrDAOFactory.getInstance().getSalesRescDAO();

        if (checkSalesRescRela(salesRescId)) {
            return "-2";
        }

        pdao.deleteAttrInfos(salesRescId, attrObjs);
        pdao.insertAttrInfos(salesRescId, attrObjs);

        String returnString = "";

        for (Iterator it = attrObjs.iterator(); it.hasNext();) {
            HashMap map = (HashMap) it.next();
            returnString += map.get("attrId");

            if (it.hasNext()) {
                returnString += ",";
            }
        }

        return returnString;
    }
}
