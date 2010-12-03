package com.ztesoft.crm.salesres.bo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.common.SqlExcuteByStr;
import com.ztesoft.crm.salesres.dao.AgDepartRelaDAO;
import com.ztesoft.crm.salesres.dao.AgSrStatisticsDAO;
import com.ztesoft.crm.salesres.dao.AgStorageStatisticsDAO;
import com.ztesoft.crm.salesres.dao.MpDepartDAO;
import com.ztesoft.crm.salesres.dao.MpOperDAO;
import com.ztesoft.crm.salesres.dao.RrRegionDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.StAreaDAO;
import com.ztesoft.crm.salesres.dao.StDepartAreaDAO;
import com.ztesoft.crm.salesres.vo.MpDepartVO;
import com.ztesoft.crm.salesres.vo.OrganizationVO;

import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;


public class SrDepartBo {
    public SrDepartBo() {
    }

    /**
     * 根据本地网的名称查询本地网
     * @param lanName String
     * @param pageIndex int
     * @param pageSize int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryLanInfo(String lanCode, String lanName, int pageIndex,
                                int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        if (lanCode != null && lanCode.trim().length() > 0) {
            sqlBuffer.append(" lan_code like '%").append(lanCode.trim()).append(
                "%'");
        }
        if (lanName != null && lanName.trim().length() > 0) {
            String hasAnd = "";
            if (sqlBuffer != null && sqlBuffer.toString().trim().length() > 0) {
                hasAnd = " and ";
            }
            sqlBuffer.append(hasAnd).append(" lan_name like '%" + lanName.trim() +
                                            "%'");
        }
        RrLanDAO rrLanDAO = SrDAOFactory.getInstance().getRrLanDAO();
        return PageHelper.popupPageModel(rrLanDAO,
                                         " where " + sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }

    /**
     * 根据营业厅的名称查询营业厅
     * @param lanName String
     * @param pageIndex int
     * @param pageSize int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryRegionInfo(Map map, int pageIndex, int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        String lanId = null;
        String regionCode = null;
        String regionName = null;
        if (map != null) {
            if (map.get("lanId") != null) {
                lanId = (String) map.get("lanId");
                if (lanId.trim().length() > 0) {
                    sqlBuffer.append(" LAN_ID in (").append(lanId).append(" ) ");
                }
            }
            if (map.get("regionCode") != null) {
                regionCode = (String) map.get("regionCode");
                if (regionCode.trim().length() > 0) {
                    String hasAnd = " and ";
                    if (sqlBuffer.length() < 1) {
                        hasAnd = "";
                    }
                    sqlBuffer.append(hasAnd).append(" REGION_CODE like '%").
                        append(regionCode.trim()).append("%'");
                }
            }

            if (map.get("regionName") != null) {
                regionName = (String) map.get("regionName");
                if (regionName.trim().length() > 0) {
                    String hasAnd = " and ";
                    if (sqlBuffer.length() < 1) {
                        hasAnd = "";
                    }
                    sqlBuffer.append(hasAnd).append(" REGION_NAME like '%").
                        append(regionName.trim()).append("%'");
                }
            }
        }
        RrRegionDAO rrRegionDAO = SrDAOFactory.getInstance().getRrRegionDAO();
        return PageHelper.popupPageModel(rrRegionDAO, sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }

    /**
     * 根据部门的名称查询部门
     * @param lanName String
     * @param pageIndex int
     * @param pageSize int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryDepartInfo(Map map, int pageIndex, int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        String regionId = null;
        String departCode = null;
        String departName = null;
        String operId = null;
        if (map != null) {
            if (map.get("regionId") != null) {
                regionId = (String) map.get("regionId");
                if (regionId.trim().length() > 0) {
                    sqlBuffer.append(" and a.REGION_ID in (").append(regionId).append(") ");
                }
            }
            if (map.get("departCode") != null) {
                departCode = (String) map.get("departCode");
                if (departCode.trim().length() > 0) {
                    sqlBuffer.append(" and a.DEPART_CODE like '%").
                        append(departCode.trim()).append("%'");
                }
            }
            if (map.get("departName") != null) {
                departName = (String) map.get("departName");
                if (departName.trim().length() > 0) {
                    sqlBuffer.append(" and a.DEPART_NAME like '%").
                        append(departName.trim()).append("%'");
                }
            }
            // 权限控制
            if (map.get("operId") != null) {
                operId = (String) map.get("operId");
                if (operId.trim().length() > 0) {
                    sqlBuffer.append(" and b.OPER_ID=").append(operId);
               }
           }
        }
        System.out.println("____________________________________"+sqlBuffer.toString());
        MpDepartDAO mpDepartDAO = SrDAOFactory.getInstance().getMpDepartDAO();
        mpDepartDAO.setFlag(2);
        return PageHelper.popupPageModel(mpDepartDAO, sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }

    /**
     * 根据操作人员的ID,查询其能查看部门的id的字符串。
     * @param operId String
     * @throws Exception
     * @return String
     */
    public String qryRightFilterDeptIds(Map map) throws Exception{
        StringBuffer deptIds = new StringBuffer();
        String deptIdsStr = "";
        String regionId = null;
        String operId = null;
        String provId = null;
        if(map.get("provId")!=null)
        	provId = (String)map.get("provId");
        if(map.get("regionId")!=null)
            regionId = (String)map.get("regionId");
        if(map.get("operId")!=null)
            operId = (String)map.get("operId");
        //广西特殊化处理
        /*if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
        	return this.qryDepartByOrgId(map);
        }*/
        if(operId==null||operId.trim().length()<1)
            return deptIdsStr;
        MpDepartDAO mpDepartDAO = SrDAOFactory.getInstance().getMpDepartDAO();
        StringBuffer cond = new StringBuffer();
        if(regionId!=null&&regionId.trim().length()>0)
            cond.append(" and a.REGION_ID in (").append(regionId).append(") ");
        if(operId!=null&&operId.trim().length()>0){
        	/*  if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX))
             	cond.append(" and b.party_role_id=").append(operId);
             else*/
            	cond.append(" and b.OPER_ID=").append(operId);
        }
        mpDepartDAO.setFlag(2);
        //对广西本地做特殊化处理
       /* if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
	        String sql_select2 = "SELECT a.PARTY_ID,a.PARTY_ID AS DEPART_CODE,a.DEPART_TYPE,a.PARTY_ID as DEPART_NAME, a.SUPER_PARTY_ID,"+
	        "A.REGION_ID AS TOWN_ID,a.REGION_ID,a.TERM_FLAG,'1' AS UNIT_TYPE, '1' AS UNIT_CODE,'1' AS VALID_FLAG,'1' AS COMMENTS  "+
	        " FROM MP_DEPARTMENT a,mp_operator_depart b where a.PARTY_ID=b.DEPART_ID ";
	        mpDepartDAO.setSQL_SELECT2(sql_select2);
        }*/
        List list = mpDepartDAO.findByCond(cond.toString());
        MpDepartVO vo = null;
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                vo = (MpDepartVO) list.get(i);
                if(vo!=null)
                    deptIds.append(vo.getDepartId()).append(",");
            }
        }
        if(deptIds.toString().endsWith(","))
            deptIdsStr = deptIds.substring(0,deptIds.length()-1);
        return deptIdsStr;
    }

    /**
     * 根据部门id查找所在的营业区，如果不存在则返回null
     * @param deptId String
     * @return String
     */
    public String qryBusinessByDept(String deptId){
        if(deptId==null||deptId.trim().length()<1)
          return null;
        String businessId = null;
        String sql = "select b.BUSINESS_ID from mp_department a,ra_town b where a.town_id=b.town_id and a.DEPART_ID="
                     + DAOUtils.filterQureyCond(deptId);
        String[] arrs = new String[]{"BUSINESS_ID"};
        SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
        List list = comDao.qryComSql(sql,arrs);
        if(list!=null&&list.size()>0){
           Map map = (Map)list.get(0);
           if(map!=null&&map.get("BUSINESS_ID")!=null){
              businessId = (String)map.get("BUSINESS_ID");
           }
        }
        return businessId;
    }

    /**
     * 根据区域的名称查询区域ID
     * @param map Map
     * @param pageIndex int
     * @param pageSize int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryArea(Map map, int pageIndex, int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        String regionId = null;
        String areaName = null;
        if (map != null) {
            sqlBuffer.append(" 1=1 ");
            if (map.get("regionId") != null) {
                regionId = (String) map.get("regionId");
                if (regionId.trim().length() > 0) {
                    sqlBuffer.append(" and REGION_ID in (").append(regionId).append(") ");
                }
            }
            if (map.get("areaName") != null) {
                areaName = (String) map.get("areaName");
                if (areaName.trim().length() > 0) {
                    sqlBuffer.append(" and area_name like '%").
                        append(areaName.trim()).append("%'");
                }
            }
        }
        StAreaDAO stAreaDAO = SrDAOFactory.getInstance().getStAreaDAO();
        return PageHelper.popupPageModel(stAreaDAO, sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }

    /**
     * 根据部门的名称查询部门
     * @param lanName String
     * @param pageIndex int
     * @param pageSize int
     * @throws DAOSystemException
     * @return PageModel
     */
    public PageModel qryDepartInfoByAreaId(Map map, int pageIndex, int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        String areaId = null;
        String departCode = null;
        String departName = null;
        if (map != null) {
            sqlBuffer.append(" A.DEPART_ID = B.DEPART_ID ");
            if (map.get("regionId") != null) {
                areaId = (String) map.get("regionId");
                if (areaId.trim().length() > 0) {
                    sqlBuffer.append(" and a.area_id in (").append(areaId).append(") ");
                }
            }
            if (map.get("departCode") != null) {
                departCode = (String) map.get("departCode");
                if (departCode.trim().length() > 0) {
                    sqlBuffer.append(" and b.DEPART_CODE like '%").
                        append(departCode.trim()).append("%'");
                }
            }
            if (map.get("departName") != null) {
                departName = (String) map.get("departName");
                if (departName.trim().length() > 0) {
                    sqlBuffer.append(" and b.DEPART_NAME like '%").
                        append(departName.trim()).append("%'");
                }
            }
        }
        StDepartAreaDAO stDepartAreaDAO = SrDAOFactory.getInstance().getStDepartAreaDAO();
        return PageHelper.popupPageModel(stDepartAreaDAO, sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }


    /**
     * 根据部门的ID,查询其能查看代理商的id的字符串。
     * @throws Exception
     * @return PageModel
     */

    public PageModel qryAgent(Map map, int pageIndex, int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        String departId = null;
        String agentName = null;
        if (map != null) {
            sqlBuffer.append(" a.agent_id=b.agent_id ");
            if (map.get("departId") != null) {
                departId = (String) map.get("departId");
                if (departId.trim().length() > 0) {
                    sqlBuffer.append(" and a.depart_id in (").append(departId).append(") ");
                }
            }
            if (map.get("agentName") != null) {
                agentName = (String) map.get("agentName");
                if (agentName.trim().length() > 0) {
                    sqlBuffer.append(" and b.agent_name like '%").
                        append(agentName.trim()).append("%'");
                }
            }
        }
        AgDepartRelaDAO agDepartRelaDAO = SrDAOFactory.getInstance().getAgDepartRelaDAO();
        return PageHelper.popupPageModel(agDepartRelaDAO, sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }

    /**
     * 根据查询条件查询代理商领取资源明细表。
     * @throws Exception
     * @return PageModel
     */
    public PageModel qryAgentStatistics(Map map, int pageIndex, int pageSize) throws
       DAOSystemException {

       StringBuffer sqlBuffer = new StringBuffer();
       String lanId = null;
       String regionId = null;
       String areaId = null;
       String departId = null;
       String agentId = null;
       String salesRescId = null;
       String agentKindCode = null;
       String startDate = null;
       String endDate = null;
       if (map != null) {
           sqlBuffer.append(" 1=1 ");
           if (map.get("lanId") != null) {
               lanId = (String) map.get("lanId");
               if (lanId.trim().length() > 0) {
                   sqlBuffer.append(" and lan_id in (").append(lanId).append(") ");
               }
           }
           if (map.get("regionId") != null) {
               regionId = (String) map.get("regionId");
                 if (regionId.trim().length() > 0) {
                     sqlBuffer.append(" and region_id in (").append(regionId).append(") ");
                 }
           }
           if (map.get("areaId") != null) {
               areaId = (String) map.get("areaId");
               if (areaId.trim().length() > 0) {
                   sqlBuffer.append(" and area_id in (").append(areaId).append(") ");
               }
           }
           if (map.get("departId") != null) {
               departId = (String) map.get("departId");
                 if (departId.trim().length() > 0) {
                     sqlBuffer.append(" and depart_id in (").append(departId).append(") ");
                 }
           }
           if (map.get("agentId") != null) {
              agentId = (String) map.get("agentId");
              if (agentId.trim().length() > 0) {
                  sqlBuffer.append(" and agent_id in (").append(agentId).append(") ");
              }
          }
          if (map.get("salesRescId") != null) {
              salesRescId = (String) map.get("salesRescId");
                if (salesRescId.trim().length() > 0) {
                    sqlBuffer.append(" and sales_resource_id = ").append(salesRescId);
                }
          }
          if (map.get("agentKindCode") != null) {
              agentKindCode = (String) map.get("agentKindCode");
              if (agentKindCode.trim().length() > 0) {
                  sqlBuffer.append(" and agent_kind_code = '").append(agentKindCode).append("'");
              }
          }
          if (map.get("startDate") != null) {
              startDate = (String) map.get("startDate");
                if (startDate.trim().length() > 0) {
                    sqlBuffer.append(" and to_date('").append(startDate).append("','yyyy-mm-dd') <= end_time");
                }
          }
          if (map.get("endDate") != null) {
              endDate = (String) map.get("endDate");
                if (endDate.trim().length() > 0) {
                    sqlBuffer.append(" and to_date('").append(endDate).append("','yyyy-mm-dd') >= end_time");
                }
          }

       }
       AgSrStatisticsDAO agSrStatisticsDAO = SrDAOFactory.getInstance().getAgSrStatisticsDAO();
       return PageHelper.popupPageModel(agSrStatisticsDAO, sqlBuffer.toString(),
                                        pageIndex, pageSize);
   }

   /**
    * 根据订单ID查询代理商领取资源明细。
    * @throws Exception
    * @return PageModel
    */

    public PageModel qryDetail(String orderId, int pageIndex, int pageSize) throws
        DAOSystemException {
        String sqlStr = "";
        String whereCond = "";
        String qrySql = "";
        String countSql = "";
        String qryTable = "RC_ENTITY";
        SqlExcuteByStr G = new SqlExcuteByStr();
        sqlStr = " select SALES_RESOURCE_ID as result from rc_order where order_id = "+orderId;
        String salesRescId = G.getString(sqlStr);
        sqlStr = " select RES_B_CODE as result from rc_order where order_id = "+orderId;
        String resBCode = G.getString(sqlStr);
        sqlStr = " select RES_E_CODE as result from rc_order where order_id = "+orderId;
        String resECode = G.getString(sqlStr);
        sqlStr = " select family_id as result from SALES_RESOURCE where SALES_RESOURCE_ID = "+salesRescId;
        String familyId = G.getString(sqlStr);
        sqlStr = " select ENTITY_TAB_NAME as result from RC_FAMILY_ENTITY_RELA where family_id = "+familyId;
        String tabName = G.getString(sqlStr);
        if(!"".equals(tabName)&&tabName!=null){
            qryTable = tabName;
        }
        if(!"".equals(resBCode)&&resBCode!=null&&!"".equals(resECode)&&resECode!=null){//订单为非导入订单
            qrySql = " select a.RESOURCE_INSTANCE_CODE,b.STATE_NAME from "+qryTable+" a,RC_STATE b";
            countSql = " SELECT COUNT(a.RESOURCE_INSTANCE_CODE) AS COL_COUNTS from "+qryTable+" a,RC_STATE b";
            if("RC_ENTITY".equals(qryTable)){
                whereCond =
                    " a.CURR_STATE = b.STATE and a.SALES_RESOURCE_ID = " +
                    salesRescId + " and " + resBCode +
                    " <= a.RESOURCE_INSTANCE_CODE and " + resECode +
                    " >= a.RESOURCE_INSTANCE_CODE";
            }else{
                whereCond =
                   " a.RESOURCE_STATE = b.STATE and a.SALES_RESOURCE_ID = " +
                   salesRescId + " and " + resBCode +
                   " <= a.RESOURCE_INSTANCE_CODE and " + resECode +
                   " >= a.RESOURCE_INSTANCE_CODE";
            }
        }else{
            qrySql = " select c.RESOURCE_INSTANCE_CODE,b.STATE_NAME from "+qryTable+" a,RC_STATE b,rc_order_list c";
            countSql = " SELECT COUNT(c.RESOURCE_INSTANCE_CODE) AS COL_COUNTS from "+qryTable+" a,RC_STATE b,rc_order_list c";
            if("RC_ENTITY".equals(qryTable)){
                whereCond = " a.CURR_STATE = b.STATE and c.RESOURCE_INSTANCE_CODE = a.RESOURCE_INSTANCE_CODE and c.order_id = "+orderId;
            }else{
                whereCond = " a.RESOURCE_STATE = b.STATE and c.RESOURCE_INSTANCE_CODE = a.RESOURCE_INSTANCE_CODE and c.order_id = "+orderId;
            }
        }
        AgSrStatisticsDAO agSrStatisticsDAO = SrDAOFactory.getInstance().getAgSrStatisticsDAO();
        agSrStatisticsDAO.setAcctCode("1");
        agSrStatisticsDAO.setSql(qrySql,countSql);
        return PageHelper.popupPageModel(agSrStatisticsDAO, whereCond,
                                         pageIndex, pageSize);
    }

    /**
     * getOperInfo
     *
     * @param map Map
     * @param pi int
     * @param ps int
     * @return PageModel
     */
    public PageModel getOperInfo(Map map, int pi, int ps) {
        String whereCond = "";
        String sType = (String) map.get("sType");
        String sContent = (String)map.get("sContent");
        whereCond = " 1=1 ";
        if (sContent == null) {
            sContent = "";
        }
        if (sType == null) {
            sType = "";
        }
        if (! ("".equals(sContent))) {
            if ("scode".equals(sType)) {
                whereCond += "  and oper_code like '%" + sContent + "%' ";
            }
            if ("sname".equals(sType)) {
                whereCond += "  and oper_name like '%" + sContent + "%' ";
            }
        }

        MpOperDAO pdao = SrDAOFactory.getInstance().getMpOperDAO();
        return PageHelper.popupPageModel(pdao, whereCond,pi, ps);

    }

    /**
     * 根据查询条件查询代理商库存资源明细报表。
     * @throws Exception
     * @return PageModel
     */
    public PageModel qryAgStorageStatistics(Map map, int pageIndex,
                                            int pageSize) throws
        DAOSystemException {

        StringBuffer sqlBuffer = new StringBuffer();
        String lanId = null;
        String regionId = null;
        String areaId = null;
        String departId = null;
        String agentId = null;
        String salesRescId = null;
        String agentKindCode = null;
        String operId = null;
        String storageId = null;
        String sqlStr = "";
        String qrySql = "";
        String countSql = "";
        String qryTable = "RC_ENTITY";
        SqlExcuteByStr G = new SqlExcuteByStr();
        if (map != null) {
            salesRescId = (String) map.get("salesRescId");
            sqlStr =
                " select family_id as result from SALES_RESOURCE where SALES_RESOURCE_ID = " +
                salesRescId;
            String familyId = G.getString(sqlStr);
            sqlStr = " select ENTITY_TAB_NAME as result from RC_FAMILY_ENTITY_RELA where family_id = " +
                familyId;
            String tabName = G.getString(sqlStr);
            if (!"".equals(tabName) && tabName != null) {
                qryTable = tabName;
            }

            if (map.get("operId") != null) {
                operId = (String) map.get("operId");
                if (operId.trim().length() > 0) {
                    qrySql = " select aa.*,rownum from( SELECT a.AGENT_ID,a.AGENT_NAME,a.AGENT_KIND_CODE, a.AGENT_CODE,d.sales_resource_name,e.resource_instance_code "
                        +  " FROM AG_STORAGE_STATISTICS a, RC_STORAGE b, SALES_RESOURCE d," +
                        qryTable + " e,MP_STORAGE f ";
                    countSql = " select count(a.AGENT_ID) as COL_COUNTS FROM AG_STORAGE_STATISTICS a, RC_STORAGE b, SALES_RESOURCE d, " +
                        qryTable + " e,MP_STORAGE f   ";

                    sqlBuffer.append(" a.storage_id = b.storage_id and b.storage_id = e.storage_id and d.sales_resource_id = e.sales_resource_id and f.storage_id = a.storage_id and e.sales_resource_id = ").
                        append(salesRescId);
                }
                else {
                    qrySql = "  select aa.*,rownum from( SELECT a.AGENT_ID,a.AGENT_NAME,a.AGENT_KIND_CODE, a.AGENT_CODE,d.sales_resource_name,e.resource_instance_code "
                        + " FROM AG_STORAGE_STATISTICS a, RC_STORAGE b, SALES_RESOURCE d," +
                        qryTable + " e ";
                    countSql = " select count(a.AGENT_ID) as COL_COUNTS FROM AG_STORAGE_STATISTICS a, RC_STORAGE b, SALES_RESOURCE d," +
                        qryTable + " e ";

                    sqlBuffer.append(" a.storage_id = b.storage_id and b.storage_id = e.storage_id and d.sales_resource_id = e.sales_resource_id and e.sales_resource_id = ").
                        append(salesRescId);

                }
            }
            if (map.get("lanId") != null) {
                lanId = (String) map.get("lanId");
                if (lanId.trim().length() > 0) {
                    sqlBuffer.append(" and a.lan_id in (").append(lanId).append(
                        ") ");
                }
            }
            if (map.get("regionId") != null) {
                regionId = (String) map.get("regionId");
                if (regionId.trim().length() > 0) {
                    sqlBuffer.append(" and a.region_id in (").append(regionId).
                        append(") ");
                }
            }
            if (map.get("areaId") != null) {
                areaId = (String) map.get("areaId");
                if (areaId.trim().length() > 0) {
                    sqlBuffer.append(" and a.area_id in (").append(areaId).
                        append(") ");
                }
            }
            if (map.get("departId") != null) {
                departId = (String) map.get("departId");
                if (departId.trim().length() > 0) {
                    sqlBuffer.append(" and a.depart_id in (").append(departId).
                        append(") ");
                }
            }
            if (map.get("agentId") != null) {
                agentId = (String) map.get("agentId");
                if (agentId.trim().length() > 0) {
                    sqlBuffer.append(" and a.agent_id in (").append(agentId).
                        append(") ");
                }
            }

            if (map.get("agentKindCode") != null) {
                agentKindCode = (String) map.get("agentKindCode");
                if (agentKindCode.trim().length() > 0) {
                    sqlBuffer.append(" and a.agent_kind_code = '").append(
                        agentKindCode).append("'");
                }
            }
            if (map.get("operId") != null) {
                operId = (String) map.get("operId");
                if (operId.trim().length() > 0) {
                    sqlBuffer.append(" and f.oper_id = ").append(operId);
                }
            }
            if (map.get("storageId") != null) {
                storageId = (String) map.get("storageId");
                if (storageId.trim().length() > 0) {
                    sqlBuffer.append(" and a.storage_id = ").append(storageId);
                }
            }
        }
        sqlBuffer.append(" order by (a.agent_id) ) aa");
        AgStorageStatisticsDAO agStorageStatisticsDAO = SrDAOFactory.
            getInstance().getAgStorageStatisticsDAO();
        agStorageStatisticsDAO.setAcctCode("1");
        agStorageStatisticsDAO.setSql(qrySql, countSql);
        return PageHelper.popupPageModel(agStorageStatisticsDAO,
                                         sqlBuffer.toString(),
                                         pageIndex, pageSize);
    }
    
    public String qryDepartByOrgId(Map map){
    	StringBuffer sb = new StringBuffer();
    	if(map==null)return "";
    	if(map.get("provId")!=null && map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)&&map.get("regionId")!=null &&!map.get("regionId").equals("") ){
    		OrganizationDAO orgDao =  SrDAOFactory.getInstance().getOrganizationDAO();
    		List list = orgDao.findByCond("  org_type_id=6 and path_code like (select a.path_code from ORGANIZATION a where  a.party_id="+map.get("regionId")+")||'.%' ");
    		if(list!=null){
    			Iterator it = list.iterator();
    			while(it.hasNext()){
    				OrganizationVO vo = (OrganizationVO)it.next();
    				if(vo!=null){
    					sb.append(vo.getPartyId());
        				sb.append(",");
    				}
    			}
    		}
    	}
    	String str = sb.toString();
    	if(str.endsWith(","))
    		str = str.substring(0,str.length()-1);
    			
		return str;
    	
    }

}
