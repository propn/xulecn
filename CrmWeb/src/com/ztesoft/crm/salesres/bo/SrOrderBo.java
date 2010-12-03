package com.ztesoft.crm.salesres.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.RcAppTypeDAO;
import com.ztesoft.crm.salesres.dao.RcFamilyEntityRelaDAO;
import com.ztesoft.crm.salesres.dao.RcOrderDAO;
import com.ztesoft.crm.salesres.dao.RcOrderListDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcAppTypeVO;
import com.ztesoft.crm.salesres.vo.RcFamilyEntityRelaVO;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

public class SrOrderBo {
  public SrOrderBo() {
  }

  /**
   * 查询输入订单号对应的订单，为查询回退订单服务的
   * @param map Map
   * @return RcOrderVO
   */
  public RcOrderVO qryBackOrder(Map map){
      if(map==null)
        return null;
      RcOrderVO vo = null;
      String orderId = null;
      String operId = null;
      if(map.get("operId")!=null&&((String)map.get("operId")).trim().length()>0)
         operId = (String)map.get("operId");
      if(map.get("orderId")!=null&&((String)map.get("orderId")).trim().length()>0)
         orderId = DAOUtils.filterQureyCond((String)map.get("orderId"));
      String cond = " a.app_id=b.app_id and b.app_type=c.app_type and a.TACHE_ID = d.TACHE_ID and a.STATE_id = e.STATE_id  and a.sales_resource_id=f.sales_resource_id "
                    +" and a.APP_ID=b.APP_ID and a.STATE_ID='n' and a.TACHE_ID=5 ";
      if(orderId!=null)
         cond += " and a.ORDER_ID="+orderId;
      if(orderId!=null)
         cond += " and b.OPER_ID="+operId;
      RcOrderDAO dao = (RcOrderDAO) SrDAOFactory.getInstance().getRcOrderDAO();
      RcAppTypeDAO appTypeDao = (RcAppTypeDAO) SrDAOFactory.getInstance().getRcAppTypeDAO();
      List list = dao.findByCond(cond);
      if(list!=null&&list.size()>0){
        vo = (RcOrderVO) list.get(0);
        vo.setAppStorageName(dao.getRcStorageName(vo.getAppStorageId())); // 设置申请仓库名
        RcAppTypeVO appTypeVO = appTypeDao.findByPk(vo.getAppType());
        if(appTypeVO!=null)
           vo.setUpAppType(appTypeVO.getUpAppType());
      }
      return vo;
  }

  /**
   * 查找代理商对于某种营销资源还可以领取多少数量
   * @param departId String
   * @param salesRescId String
   * @return long
   */
  public String qryLeftTakeResNum(String departId,String salesRescId){
    if(departId==null||departId.trim().length()<1||salesRescId==null||salesRescId.trim().length()<1)
         return "null";
    long takenResNum = this.qryAgentTakenResNum(departId,salesRescId);
    long canTakeMaxNum = this.qryAgentCanTakeMaxNum(departId,salesRescId);
    long inMidwayNum = this.qryInMidwayNum(departId,salesRescId);
    if(takenResNum==-1||canTakeMaxNum==-1)
       return "null";
    else
       return String.valueOf(canTakeMaxNum-takenResNum-inMidwayNum);
  }

  /**
   * 查找代理商对于指定的营销资源已经领取的数量
   * @param departId String
   * @param salesRescId String
   * @return long
   */
  public long qryAgentTakenResNum(String departId,String salesRescId){
      if(departId==null||departId.trim().length()<1||salesRescId==null||salesRescId.trim().length()<1)
         return -1;
      long sumNum = -1;
      departId = DAOUtils.filterQureyCond(departId);
      salesRescId = DAOUtils.filterQureyCond(salesRescId);
      SalesRescDAO rescDao = SrDAOFactory.getInstance().getSalesRescDAO();
      SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
      String manageMode = null;
      SalesRescVO rescVO = rescDao.findByPrimaryKey(salesRescId);
      manageMode = rescVO.getManageMode();
      if (SalesRescVO.ManageMode_Entity.equals(manageMode)) { // 实例管理模式
         String tableName = this.getEntityTableName(salesRescId);
         if(tableName!=null&&tableName.trim().length()>0){
            String sql1 = "select count(*) as sumNum from "+tableName+" a where a.sales_resource_id="+salesRescId
                +" and exists(select b.storage_id from STORAGE_DEPART_RELA b where a.storage_id=b.storage_id "
                +" and b.depart_id="+departId+")";
            String[] arrs1 = new String[]{"sumNum"};
            List list1 = comDao.qryComSql(sql1,arrs1);
            if(list1!=null&&list1.size()>0){
               Map map1 = (Map)list1.get(0);
               if(map1.get("sumNum")!=null&&((String)map1.get("sumNum")).trim().length()>0)
                  sumNum = Long.parseLong((String)map1.get("sumNum"));
               else
                  sumNum = 0l;
            }else
               sumNum = 0l;
         }
      }else if (SalesRescVO.ManageMode_Stock.equals(manageMode)) { // 存量管理模式
         String sql2 = "select sum(a.stock_amount) as sumNum from rc_stock a where a.sales_resource_id="+salesRescId
            +" and exists(select b.storage_id from STORAGE_DEPART_RELA b where a.storage_id=b.storage_id "
            +" and b.depart_id="+departId+")";
         String[] arrs2 = new String[]{"sumNum"};
         List list2 = comDao.qryComSql(sql2,arrs2);
         if(list2!=null&&list2.size()>0){
             Map map2 = (Map)list2.get(0);
             if(map2.get("sumNum")!=null&&((String)map2.get("sumNum")).trim().length()>0)
                sumNum = Long.parseLong((String)map2.get("sumNum"));
             else
                sumNum = 0l;
         }else
            sumNum = 0l;
      }
      return sumNum;
  }

  /**
   * 查询代理商没有结束的订单的数量
   * @param departId String
   * @param salesRescId String
   * @return long
   */
  public long qryInMidwayNum(String departId,String salesRescId){
     if(departId==null||departId.trim().length()<1||salesRescId==null||salesRescId.trim().length()<1)
        return 0;
      departId = DAOUtils.filterQureyCond(departId);
      salesRescId = DAOUtils.filterQureyCond(salesRescId);
     long sumNum = 0;
     SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
     String sql1 = "select sum(APP_AMOUNT) as resNum from rc_order a,rc_order_agent b where a.ORDER_ID=b.ORDER_ID "
                   +" and a.TACHE_ID!=5 and a.SALES_RESOURCE_ID="+salesRescId+" and b.DEPART_ID="+departId;
     String[] arrs = new String[]{"resNum"};
     List list = comDao.qryComSql(sql1,arrs);
     Map map = null;
     if(list!=null&&list.size()>0)
       map = (Map)list.get(0);
     if(map!=null&&map.get("resNum")!=null&&(((String)map.get("resNum")).trim().length()>0))
        sumNum = Long.parseLong((String)map.get("resNum"));
     return sumNum;
  }

  /**
   * 根据代理商departId查找该代理商对于该营销资源能领取的最大数额
   * @param departId String
   * @return long
   */
  public long qryAgentCanTakeMaxNum(String departId,String salesRescId){
     if(departId==null||departId.trim().length()<1||salesRescId==null||salesRescId.trim().length()<1)
        return -1;
     departId = DAOUtils.filterQureyCond(departId);
     salesRescId = DAOUtils.filterQureyCond(salesRescId);
     SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
     String sql1 = "select a.RES_NUMBER from AG_SALE_RES_CONTROL a,ag_depart_rela b "
                   +" where a.agent_id=b.AGENT_ID and a.STATE='00A' and a.SALES_RESOURCE_ID="+salesRescId+" and b.DEPART_ID="+departId;
     String[] arrs = new String[]{"RES_NUMBER"};
     List list = comDao.qryComSql(sql1,arrs);
     if(list==null||list.size()<1)
        return -1;
     Map map = (Map)list.get(0);
     String resNumStr = (String)map.get("RES_NUMBER");
     return Long.parseLong(resNumStr);
  }

  /**
   * 根据营销资源id查找其相对应的表名
   * @param salesRescId String
   * @return String
   */
  public String getEntityTableName(String salesRescId) {
    if(salesRescId==null||salesRescId.trim().length()<1) {
       return null;
    }
    String tableName = "rc_entity";
    SalesRescDAO salesRescDao = SrDAOFactory.getInstance().getSalesRescDAO();
    RcFamilyEntityRelaDAO relaDao = SrDAOFactory.getInstance().
        getRcFamilyEntityRelaDAO();
    SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(salesRescId);
    if (salesRescVO != null && salesRescVO.getFamilyId() != null &&
        salesRescVO.getFamilyId().trim().length() > 0) {
      List list = relaDao.findByCond(" FAMILY_ID=" + salesRescVO.getFamilyId());
      if (list != null && list.size() > 0 && list.get(0) != null) {
        tableName = ( (RcFamilyEntityRelaVO) list.get(0)).getEntityTabName();
      }
    }
    return tableName;
  }
  


  /**
   * 新增提交的出入库上传实例列表
   * @param lists
   * @return Map: result:0 失败，1 成功；info: 错误信息
   */
  public Map addOrderListEntity(RcOrderListVO[] lists){
	 Map map = new HashMap();
	 if(lists==null||lists.length<1)
	 {
		 map.put("result", "0");
		 map.put("info", "出入的资源实例数为0，操作失败!");
		 return map;
	 }
	 String result = "0";
	 String info = "";
	 StringBuffer buffer = new StringBuffer();
	 long retCount = 0;
	 int dupCount = 0;
	 List listTemp = null;
	 String[] sqls = new String[lists.length];
	 RcOrderListDAO listDao = SrDAOFactory.getInstance().getRcOrderListDAO();
	 SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
	 for(int i=0;i<lists.length;i++){
		 if(lists[i]!=null&&lists[i].getRescInstanceCode()!=null&&lists[i].getRescInstanceCode().trim().length()>0
			&&lists[i].getSalesRescId()!=null&&lists[i].getSalesRescId().trim().length()>0
			&&lists[i].getOrderId()!=null&&lists[i].getOrderId().trim().length()>0)
		 {
			 String cond = " ORDER_ID="+lists[i].getOrderId()+" and SALES_RESOURCE_ID="+lists[i].getSalesRescId()
			               +" and RESOURCE_INSTANCE_CODE='"+DAOUtils.filterQureyCond(lists[i].getRescInstanceCode())+"'";
			 listTemp = listDao.findByCond(cond);
			 if(listTemp!=null&&listTemp.size()>0){
				 result = "0";
				 buffer.append(lists[i].getRescInstanceCode()).append(",");
				 dupCount++;
			 }else{
				 sqls[i] = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE ) "
					       +" VALUES ( "+lists[i].getOrderId()+","+lists[i].getRescInstanceId()+","+lists[i].getSalesRescId()+",'"
					       +DAOUtils.filterQureyCond(lists[i].getRescInstanceCode())+"' )";
			 }
		 }
			 
	 }
	 if(dupCount>0){
		 result = "0";
		 info = "物资实例:"+buffer.toString()+"已经在改订单中存在，操作失败!";
	 }else{
		 retCount = comDao.batchExecute(sqls);
		 result = "1";
		 info = "成功新增"+retCount+"条实例记录!";
	 }
     map.put("result", result);
     map.put("info", info);
     return map;
  }
  
	  /**
	   * 查询库存订单报表
	   * @param map
	   * @param pageIndex
	   * @param pageSize
	   * @return
	   * @throws Exception
	   */
	  public PageModel qryRcOrderReport(Map map, int pageIndex, int pageSize)
	  {
		  PageModel pm = new PageModel();
		  if(map==null)
			  return pm;
		  String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
		  String regionId = null;
		  String familyId = null;
		  String salesResourceIDs = null;
		  String outStorageId = null;
		  String inStorageId = null;
		  String orderId = null;
		  String beginDate = null;
		  String endDate = null;
		  
		    if(map.get("regionId")!=null&&((String)map.get("regionId")).trim().length()>0)
		       regionId = (String)map.get("regionId");
		    if(map.get("familyId")!=null&&((String)map.get("familyId")).trim().length()>0){
		    	familyId = (String)map.get("familyId");
			}
		    if(map.get("salesResourceIDs")!=null&&((String)map.get("salesResourceIDs")).trim().length()>0){
		    	salesResourceIDs = (String)map.get("salesResourceIDs");
			}
		    if(map.get("outStorageId")!=null&&((String)map.get("outStorageId")).trim().length()>0){
		    	outStorageId = (String)map.get("outStorageId");
			}
		    if(map.get("inStorageId")!=null&&((String)map.get("inStorageId")).trim().length()>0){
		    	inStorageId = (String)map.get("inStorageId");
			}
		    if(map.get("orderId")!=null&&((String)map.get("orderId")).trim().length()>0){
		    	orderId = (String)map.get("orderId");
			}
		    if(map.get("beginDate")!=null&&((String)map.get("beginDate")).trim().length()>0){
		    	beginDate = (String)map.get("beginDate");
			}
		    if(map.get("endDate")!=null&&((String)map.get("endDate")).trim().length()>0){
		    	endDate = (String)map.get("endDate");
			}
		  
		  String sql = "select a.*,c.SALES_RESOURCE_NAME,d.FAMILY_NAME,e.storage_name as out_storage_name,f.storage_name as in_storage_name,g.DEPART_NAME,h.OPER_NAME"
			  +" from rc_order a inner join rc_application b on a.APP_ID=b.APP_ID " 
			  +" inner join sales_resource c on a.sales_resource_id=c.sales_resource_id "
			  +" inner join rc_family d on c.FAMILY_ID=d.FAMILY_ID "
			  +" left outer join rc_storage e on a.out_storage_id = e.storage_id "
              +" left outer join rc_storage f on a.in_storage_id = f.storage_id "
              +" inner join mp_department g on b.DEPART_ID = g.DEPART_ID "
              +" left outer join mp_operator h on b.OPER_ID = h.OPER_ID"
              +" where a.TACHE_ID=5 and a.STATE_ID='n' and (e.rc_type=-1 or e.rc_type is null) and (f.rc_type=-1 or f.rc_type is null) ";
		  
		  String sql_count = "select count(*) as COL_COUNTS "
			  +" from rc_order a inner join rc_application b on a.APP_ID=b.APP_ID " 
			  +" inner join sales_resource c on a.sales_resource_id=c.sales_resource_id "
			  +" inner join rc_family d on c.FAMILY_ID=d.FAMILY_ID "
			  +" left outer join rc_storage e on a.out_storage_id = e.storage_id "
              +" left outer join rc_storage f on a.in_storage_id = f.storage_id "
              +" inner join mp_department g on b.DEPART_ID = g.DEPART_ID "
              +" left outer join mp_operator h on b.OPER_ID = h.OPER_ID"
              +" where a.TACHE_ID=5 and a.STATE_ID='n' and (e.rc_type=-1 or f.rc_type=-1) ";
			  
		  String cond = "";
		  if(regionId!=null)
			  cond += " and g.REGION_ID="+regionId;
		  if(familyId!=null)
			  cond += " and d.FAMILY_ID="+familyId;
		  if(salesResourceIDs!=null)
			  cond += " and a.sales_resource_id in("+salesResourceIDs+")";
		  if(outStorageId!=null)
			  cond += " and a.out_storage_id="+outStorageId;
		  if(inStorageId!=null)
			  cond += " and a.in_storage_id="+inStorageId;
		  if(orderId!=null)
			  cond += " and a.order_id ="+DAOUtils.filterQureyCond(orderId);
		  
		  if(beginDate!=null&&beginDate.trim().length()>0){
			 if("INFORMIX".equals(databaseType))
			 {
				cond += " and a.TACHE_TIME>=to_date('"+beginDate+"','%Y-%m-%d')";
			 }else{
				cond += " and a.TACHE_TIME>=to_date('"+beginDate+"','yyyy-mm-dd')";
			 }
		  }
		  if(endDate!=null&&endDate.trim().length()>0){
			 if("INFORMIX".equals(databaseType))
			 {
				cond += " and a.TACHE_TIME<=to_date('"+endDate+"','%Y-%m-%d')+interval(1) day to day";
			 }else{
				cond += " and a.TACHE_TIME<=to_date('"+endDate+"','yyyy-mm-dd')+1";
			 }
		  }
		  
		  RcOrderDAO dao = SrDAOFactory.getInstance().getRcOrderDAO();
          dao.setFlag(3);
		  dao.setSQL_SELECT(sql);
		  dao.setSQL_SELECT_COUNT(sql_count);
		  pm = PageHelper.popupPageModel(dao, cond, pageIndex, pageSize);
		  return pm;
	  }
  
}
