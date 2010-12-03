package com.ztesoft.crm.salesres.bo;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.PageHelperFlx;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcSaleLogDAO;
import com.ztesoft.crm.salesres.dao.RcServAcceptDAO;
import com.ztesoft.crm.salesres.dao.RcServReturnDAO;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcSaleLogVO;
import com.ztesoft.crm.salesres.vo.RcServAcceptVO;
import com.ztesoft.crm.salesres.vo.RcServReturnVO;
import com.ztesoft.crm.salesres.vo.RcStockVO;

public class SrSalesBo {

	public RcServAcceptVO qryServAccrptByLogId(String logId){
		if(logId==null||logId.trim().length()<1)
			return new RcServAcceptVO();
		RcServAcceptDAO dao = SrDAOFactory.getInstance().getRcServAcceptDAO();
		RcServAcceptVO vo = dao.findByLogId(logId);
		return vo;
	}
	
    /**
     * 保存营销资源为修改下单信息
     * @param logId
     * @return
     * @throws Exception
     */
    public String saveServAccept(RcServAcceptVO vo) 
    {
    	if(vo==null)
    	   return "-1";
    	RcServAcceptDAO dao = SrDAOFactory.getInstance().getRcServAcceptDAO();
    	SqlComDAO dao2 = SrDAOFactory.getInstance().getSqlComDAO();
    	// 更新资源实例的状态
    	if(vo.getRescInstanceId()!=null&&vo.getRescInstanceId().trim().length()>0
    	   &&vo.getSalesRescId()!=null&&vo.getSalesRescId().trim().length()>0){
    	   SrStockBo bo = new SrStockBo();
    	   String tableName = bo.getEntityTableName(vo.getSalesRescId());
    	   String sql = "update "+tableName+" set STATE='"+ParamsConsConfig.RcEntityStateValide+"' , ";
    	   String restStateCond = " RESOURCE_STATE ";
    	   if(tableName!=null&&tableName.trim().length()>0){
    		   if(tableName.toLowerCase().equals("rc_entity")){
    			   restStateCond = " CURR_STATE ";
    		   }
    	   }
    	   sql += restStateCond+"='"+ParamsConsConfig.RescState_fixing+"' where RESOURCE_INSTANCE_ID="+vo.getRescInstanceId();
           dao2.excute(sql);
           // 插入维修记录
           SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
           SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();
           String sAcceptId = sequenceManageDAO.getNextSequence("RC_SERVICE_ACCEPT", "S_ACCEPT_ID");
	       if(sAcceptId==null||sAcceptId.trim().length()<1)
	        	return "0";
	       vo.setSAcceptId(sAcceptId);
	       vo.setATime(DAOUtils.getFormatedDate());
	       vo.setState("0");
	       dao.insert(vo);
    	} else
    		return "-1";
    	
    	return "1";
    }
    
    /**
     * 查询资源维修下单
     * @param map,包含的参数有：departIds，salesRescIds，rescInstance2，beginDate，endDate
     * @param pi
     * @param ps
     * @return
     */
    public PageModel qryRcServiceAccept(Map map, int pi, int ps) {
  	    String lanId = null;
  	    String operId = null;
  	    String familyId = null;
  	    String custName = null;
  		String salesRescIds = null;
  		String rescInstanceCode = null;
  		String beginDate = null;
  		String endDate = null;
  		String prodNo = null;
  		
  		if(map!=null){
  			if(map.get("lanId")!=null)
  				lanId = (String)map.get("lanId");
  			if(map.get("operId")!=null)
  				operId = (String)map.get("operId");
  			if(map.get("familyId")!=null)
  				familyId = (String)map.get("familyId");
  			if(map.get("custName")!=null)
  				custName = (String)map.get("custName");
  			if(map.get("salesRescIds")!=null)
  				salesRescIds = (String)map.get("salesRescIds");
  			if(map.get("rescInstanceCode")!=null)
  				rescInstanceCode = (String)map.get("rescInstanceCode");
  			if(map.get("beginDate")!=null)
  				beginDate = (String)map.get("beginDate");
  			if(map.get("endDate")!=null)
  				endDate = (String)map.get("endDate");
  			if(map.get("prodNo")!=null){
  				prodNo = (String)map.get("prodNo");
  			    prodNo = DAOUtils.filterQureyCond(prodNo);
  			}
  		}
  		PageModel pm = new PageModel();
  		String joinStr = "";
  		String joinCondStr = "";
  		String instanceIdStr = "";
  		if(salesRescIds!=null&&salesRescIds.trim().length()>0){
 
  		}
  		if(custName!=null&&custName.trim().length()>0){
			instanceIdStr += ",g.CUST_NAME";
			joinStr += ",cust g ";
			joinCondStr += " and a.cust_id = g.cust_id and g.CUST_NAME like '%"+DAOUtils.filterQureyCond(custName)+"%' ";
			if(lanId!=null&&lanId.trim().length()>0)
				joinCondStr += " and a.lan_id="+lanId;
		}else{
			instanceIdStr += ",a.cust_name";
		}
  		
  		String sql = "SELECT a.S_ACCEPT_ID,a.RESOURCE_INSTANCE_ID,a.sales_resource_id,a.RESOURCE_INSTANCE_CODE,a.S_INFO,a.R_CUST_NAME,a.R_CUST_TEL,a.LOG_ID,"+
  		             "a.R_DATE,a.OPER_ID,a.A_TIME,a.LAN_ID,a.STATE,a.PRODUCT_ID,a.PRODUCT_NO,"+
  		             "a.CUST_ID"+instanceIdStr+
  		             " ,b.family_id,b.sales_resource_name " +
  		             " FROM RC_SERVICE_ACCEPT a,SALES_RESOURCE b " +joinStr+
  		             " where a.sales_resource_id=b.sales_resource_id "+joinCondStr;
  		
  		String sql_count = "SELECT COUNT(*) AS COL_COUNTS "+
                             " FROM RC_SERVICE_ACCEPT a,SALES_RESOURCE b " +joinStr+
                             " where a.sales_resource_id=b.sales_resource_id "+joinCondStr;
  		
  		StringBuffer cond = new StringBuffer();
  		String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
  		if(lanId!=null&&lanId.trim().length()>0)
  			cond.append(" and a.LAN_ID = ").append(lanId).append(" ");
  		if(operId!=null&&operId.trim().length()>0)
  			cond.append(" and a.oper_id = ").append(operId).append(" ");
  		if(salesRescIds!=null&&salesRescIds.trim().length()>0)
  			cond.append(" and a.sales_resource_id = ").append(salesRescIds).append(" ");
  		else if(familyId!=null&&familyId.trim().length()>0)
  			cond.append(" and b.FAMILY_ID = ").append(familyId).append(" ");
  		if(rescInstanceCode!=null&&rescInstanceCode.trim().length()>0)
  			cond.append(" and a.RESOURCE_INSTANCE_CODE like '%").append(DAOUtils.filterQureyCond(rescInstanceCode)).append("%' ");
  		if(beginDate!=null&&beginDate.trim().length()>0){
  			if("INFORMIX".equals(databaseType))
  			{
  				cond.append(" and a.a_time>=to_date('"+beginDate+"','%Y-%m-%d')");
  			}else{
  				cond.append(" and a.a_time>=to_date('"+beginDate+"','yyyy-mm-dd')");
  			}
  		}
  		if(endDate!=null&&endDate.trim().length()>0){
  			if("INFORMIX".equals(databaseType))
  			{
  				cond.append(" and a.a_time<=to_date('"+endDate+"','%Y-%m-%d')+interval(1) day to day");
  			}else{
  				cond.append(" and a.a_time<=to_date('"+endDate+"','yyyy-mm-dd')+1");
  			}
  		}
  		if(prodNo!=null&&prodNo.trim().length()>0)
  			cond.append(" and a.product_no = ").append(prodNo).append(" ");
  		
  		sql += cond;
  		sql_count += cond;
  		
  		RcServAcceptDAO dao = SrDAOFactory.getInstance().getRcServAcceptDAO();

  		dao.setSQL_SELECT(sql);
  		dao.setSQL_SELECT_COUNT(sql_count);
  		dao.setFlag(1);

  		pm = PageHelper.popupPageModel(dao, "", pi, ps);
  		return pm;
    }
    
    /**
     * 根据下单序列查询资源维修取机信息
     * @param sAcceptId
     * @return
     */
	public RcServReturnVO qryServReturnByAcceptId(String sAcceptId){
		if(sAcceptId==null||sAcceptId.trim().length()<1)
			return new RcServReturnVO();
		RcServReturnVO vo = null;
		RcServReturnDAO dao = SrDAOFactory.getInstance().getRcServReturnDAO();
		List list = dao.findByCond(" S_ACCEPT_ID = "+sAcceptId);
		if(list!=null&&list.size()>0)
			vo = (RcServReturnVO)list.get(0);
		return vo;
	}
    
    /**
     * 保存营销资源为修改下单信息
     * @param logId
     * @return
     * @throws Exception
     */
    public String saveServReturn(RcServReturnVO vo) 
    {
    	if(vo==null)
    	   return "-1";
    	RcServReturnDAO dao = SrDAOFactory.getInstance().getRcServReturnDAO();
    	RcServAcceptDAO dao1 = SrDAOFactory.getInstance().getRcServAcceptDAO();
    	SqlComDAO dao2 = SrDAOFactory.getInstance().getSqlComDAO();
    	// 更新资源实例的状态
    	if(vo.getRescInstanceId()!=null&&vo.getRescInstanceId().trim().length()>0
    	   &&vo.getSalesRescId()!=null&&vo.getSalesRescId().trim().length()>0){
    	   SrStockBo bo = new SrStockBo();
    	   String tableName = bo.getEntityTableName(vo.getSalesRescId());
    	   String sql = "update "+tableName+" set STATE='"+ParamsConsConfig.RcEntityStateInValide+"' , ";
    	   String restStateCond = " RESOURCE_STATE ";
    	   if(tableName!=null&&tableName.trim().length()>0){
    		   if(tableName.toLowerCase().equals("rc_entity")){
    			   restStateCond = " CURR_STATE ";
    		   }
    	   }
    	   sql += restStateCond+"='"+ParamsConsConfig.RcEntityUseState+"' where RESOURCE_INSTANCE_ID="+vo.getRescInstanceId();
           dao2.excute(sql);
           
	       	SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
	        SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();
	        String rAcceptId = sequenceManageDAO.getNextSequence("RC_SERVICE_RETURN", "R_ACCEPT_ID");
	    	if(rAcceptId==null||rAcceptId.trim().length()<1)
	    		return "0";
	    	vo.setRAcceptId(rAcceptId);
	    	vo.setATime(DAOUtils.getFormatedDate());
	    	// 更新RC_SERVICE_ACCEPT
	    	dao1.updateState("1", vo.getSAcceptId());
	    	// 插入RC_SERVICE_RETURN
	    	dao.insert(vo);
    	}
    	return "1";
    }
    
	/**
	 * 根据部门id和营销资源id的字符串查询该部门所属仓库终端资源的库存
	 * 
	 * @param deptID:必须是id1,id2,id3的形式
	 * @param saleIds：必须是'id1','id2','id3'的形式
	 * @return
	 */
	public PageModel satTerminalStockNum(Map map,int pi,int ps) {
		PageModel pm = new PageModel();
		String lanId = null;
		String rescState = null;
		String deptId = null;
		String saleIds = null;
		String operId = null;
		String upStorageId = null;
		String familyId = null;
		if (map != null) {
			if (map.get("lanId") != null
					&& ((String) map.get("lanId")).trim().length() > 0)
				lanId = (String) map.get("lanId");
			if (map.get("rescState") != null
					&& ((String) map.get("rescState")).trim().length() > 0)
				rescState = (String) map.get("rescState");
			if (map.get("deptId") != null
					&& ((String) map.get("deptId")).trim().length() > 0)
				deptId = (String) map.get("deptId");
			if (map.get("saleIds") != null
					&& ((String) map.get("saleIds")).trim().length() > 0)
				saleIds = (String) map.get("saleIds");
			if (map.get("operId") != null
					&& ((String) map.get("operId")).trim().length() > 0)
				operId = (String) map.get("operId");
			if (map.get("upStorageId") != null
					&& ((String) map.get("upStorageId")).trim().length() > 0)
				upStorageId = (String) map.get("upStorageId");
			if (map.get("familyId") != null
					&& ((String) map.get("familyId")).trim().length() > 0)
				familyId = (String) map.get("familyId");
		}
		String cond = "";
		String storage_cond = "";
		RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
		String tableName = "rc_entity"; 
		
		if (getRealCfg(lanId, tableName)) {
			String sql = " select a.storage_id,a.storage_name,a.sales_resource_id,a.sales_resource_name,a.st_count as stock_amount,a.resource_state,a.UP_LIMIT,a.DOWN_LIMIT, '' as DEPART_ID,"
					     + "'' as OPER_ID from rc_rp_st_count a ";
            String sql_count = " select count(a.sales_resource_id) as COL_COUNTS,sum(st_count) as totalAmount from rc_rp_st_count a ";
            String sql_cond = " where ";
			if(rescState!=null)
				sql_cond += " a.resource_state='"+rescState+"' ";
			else
				sql_cond += " 1=1 ";
			if (upStorageId != null && upStorageId.trim().length() > 0) {
				sql_cond = " inner join rc_storage b on ( b.storage_id = a.storage_id and b.rc_type =-1 and  b.UP_STORAGE_ID = "
						     + upStorageId + " ) "+sql_cond;
			} 

			if (saleIds != null && saleIds.trim().length() > 0) {
				sql_cond += " and a.sales_resource_id in (" + saleIds + ")";
			}else if(familyId!=null){
				sql_cond += " and exists(select 1 from sales_resource c where a.sales_resource_id=c.sales_resource_id and c.family_id="+familyId+" ) ";
			}

			sql_cond += " and exists(select g.storage_id from STORAGE_DEPART_RELA g " +
	                    " where g.storage_id=a.storage_id and g.DEPART_ID="+ deptId +
	                    " union all select j.storage_id from mp_storage j where j.storage_id=a.storage_id and j.STATE='00'" +
	                    " and j.OPER_ID="+operId+")";

			sql += sql_cond;
			sql_count += sql_cond;
			
			dao.setFlag(2); // 设置要查询仓库模板的上下限
			dao.setFiltered(true);

			// 查询实体管理的库存
			dao.setSQL_SELECT(sql);
			dao.setSQL_SELECT_COUNT(sql_count);
			System.out.println("----SrSalesBo:satTerminalStockNum:sql1:"+sql);
			System.out.println("----SrSalesBo:satTerminalStockNum:sql_count1:"+sql_count);
			pm = PageHelperFlx.popupPageModel(dao, "","countSumByCond", pi, ps);
		} else {  // 实时动态查询
			
			// start 组装查询条件
			if (saleIds != null && saleIds.trim().length() > 0) {
				cond += " and a.sales_resource_id in (" + saleIds + ")";
			}else if(familyId!=null){
				cond += " and exists(select 1 from sales_resource c where a.sales_resource_id=c.sales_resource_id and c.family_id="+familyId+" ) ";
			}
			
			if(rescState!=null)
				cond += " and a.CURR_STATE='"+rescState+"' ";
				
			cond += " and exists(select g.storage_id from STORAGE_DEPART_RELA g " +
			        " where g.storage_id=a.storage_id and g.DEPART_ID="+ deptId +
			        " union all select j.storage_id from mp_storage j where j.storage_id=a.storage_id and j.STATE='00'" +
			        " and j.OPER_ID="+operId+")";
			
			if (upStorageId != null && upStorageId.trim().length() > 0) {
				storage_cond = " and d.UP_STORAGE_ID = " + upStorageId + " ";
			}

			// end 组装查询条件

			// 主sql语句
			StringBuffer sql_buff = new StringBuffer();
			StringBuffer sql_count_buff = new StringBuffer();
			
			sql_buff.append("select c.storage_id,d.storage_name,c.sales_resource_id,c.CURR_STATE as resource_state,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT, '");
	        sql_buff.append( deptId).append( "' as DEPART_ID,").append( operId).append( " as OPER_ID  from ");
	        sql_buff.append( "( select a.storage_id,a.sales_resource_id,a.CURR_STATE,count(a.resource_instance_id) as stock_amount from ");
	        sql_buff.append( tableName).append(" a where a.state='");
	        sql_buff.append( ParamsConsConfig.RcEntityStateValide).append( "' ");
	        sql_buff.append( cond).append( " group by a.storage_id, a.sales_resource_id,a.CURR_STATE ) c ");
	        sql_buff.append( "inner join RC_STORAGE d on (c.storage_id=d.storage_id ").append( ") ");
	        sql_buff.append( " inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ");
	        sql_buff.append( " left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");
	        sql_buff.append( " where d.rc_type=-1 "+storage_cond ); 
		        
	        sql_count_buff.append("select count(c.sales_resource_id) as COL_COUNTS,sum(c.stock_amount) as totalAmount from ");
	        sql_count_buff.append( "( select a.storage_id,a.sales_resource_id,a.CURR_STATE,count(a.resource_instance_id) as stock_amount from ");
	        sql_count_buff.append( tableName).append(" a where a.state='");
	        sql_count_buff.append( ParamsConsConfig.RcEntityStateValide).append( "' ");
	        sql_count_buff.append( cond).append( " group by a.storage_id, a.sales_resource_id,a.CURR_STATE  ) c ");
	        sql_count_buff.append( "inner join RC_STORAGE d on (c.storage_id=d.storage_id ").append( ") ");
	        sql_count_buff.append( " where d.rc_type=-1 "+storage_cond ); 
			

			String sql = sql_buff.toString();
			String sql_count = sql_count_buff.toString();

			dao.setFlag(2); // 设置要查询仓库模板的上下限
			dao.setFiltered(true);

			dao.setSQL_SELECT(sql);
			dao.setSQL_SELECT_COUNT(sql_count);
			pm = PageHelperFlx.popupPageModel(dao, "","countSumByCond", pi, ps);
		}
		// 加上统计的库存总数
		RcStockVO tempVO = null;
		List pageList = pm.getList();
		if(pageList!=null){
		   tempVO = new RcStockVO();
		   //tempVO.setStockAmount("总计："+pm.getTotalSum()+"个");
		   pageList.add(tempVO);
		}
		return pm;
	}
	
	
	/**
	 * 统计终端物资的业务受理情况，不包括销售情况
	 * 
	 * @param deptID:必须是id1,id2,id3的形式
	 * @param saleIds：必须是'id1','id2','id3'的形式
	 * @return
	 */
	public PageModel satTerminalInOutNum(Map map,int pi,int ps) {
		PageModel pm = new PageModel();
		String deptId = null;
		String saleIds = null;
		String operId = null;
		String upStorageId = null;
		String beginDate = null;
		String endDate = null;
		String biztype = null;
		String isOut = null;
		String familyId = null;
		String addSub = ""; // 因为如果是出库类型，表中的字段记为负数，便于统计，在界面展现时就要变成正值
		if (map != null) {
			if (map.get("deptId") != null
					&& ((String) map.get("deptId")).trim().length() > 0)
				deptId = (String) map.get("deptId");
			if (map.get("saleIds") != null
					&& ((String) map.get("saleIds")).trim().length() > 0)
				saleIds = (String) map.get("saleIds");
			if (map.get("operId") != null
					&& ((String) map.get("operId")).trim().length() > 0)
				operId = (String) map.get("operId");
			if (map.get("upStorageId") != null
					&& ((String) map.get("upStorageId")).trim().length() > 0)
				upStorageId = (String) map.get("upStorageId");
			if (map.get("beginDate") != null
					&& ((String) map.get("beginDate")).trim().length() > 0)
				beginDate = String.valueOf(map.get("beginDate"));
			if (map.get("endDate") != null
					&& ((String) map.get("endDate")).trim().length() > 0)
				endDate = String.valueOf(map.get("endDate"));
			if (map.get("bizType") != null
					&& ((String) map.get("bizType")).trim().length() > 0)
				biztype = String.valueOf(map.get("bizType"));
			if (map.get("isOut") != null
					&& ((String) map.get("isOut")).trim().length() > 0)
				isOut = String.valueOf(map.get("isOut"));
			if (map.get("familyId") != null
					&& ((String) map.get("familyId")).trim().length() > 0)
				familyId = (String) map.get("familyId");
		}
		String storage_cond = "";
		RcSaleLogDAO dao = SrDAOFactory.getInstance().getRcSaleLogDAO();
			
        String sql_cond = " where 1=1 ";
        if (saleIds != null && saleIds.trim().length() > 0) {
			sql_cond += " and a.sales_resource_id in (" + saleIds + ")";
		}else if(familyId!=null){
			sql_cond += " and exists(select 1 from sales_resource z where a.sales_resource_id=z.sales_resource_id and z.family_id="+familyId+" ) ";
		}
        if(isOut!=null){
			sql_cond += " and a.IS_OUT='"+isOut+"' ";
            if("o".equals(isOut)){
            	addSub = "-";
            }
        }
		if(biztype!=null)
			sql_cond += " and a.BIZ_TYPE='"+biztype+"' ";
		else
			sql_cond += " and a.BIZ_TYPE!='SAL' ";  // 不查询销售的
		if(beginDate!=null)
			sql_cond += "  and a.SALE_TIME>=to_date('" + beginDate
			            + "','yyyy-mm-dd')  ";
		if(endDate!= null) {
			sql_cond += "  and a.SALE_TIME<to_date('" + endDate
					    + "','yyyy-mm-dd')+1  ";
		}
		
		sql_cond += " and exists(select g.storage_id from STORAGE_DEPART_RELA g " +
                    " where g.storage_id=a.storage_id and g.DEPART_ID="+ deptId +
                    " union all select j.storage_id from mp_storage j where j.storage_id=a.storage_id and j.STATE='00'" +
                    " and j.OPER_ID="+operId+")";

		if (upStorageId != null && upStorageId.trim().length() > 0) {
			storage_cond = " and c.UP_STORAGE_ID = " + upStorageId + " ";
		}
	
		// end 组装查询条件
	
		// 主sql语句
		StringBuffer sql_buff = new StringBuffer();
		StringBuffer sql_count_buff = new StringBuffer();
		
		sql_buff.append("select b.storage_id,c.storage_name,d.sales_resource_id,b.BIZ_TYPE,d.sales_resource_name,b.stock_amount ");
	    sql_buff.append(" from ");
	    sql_buff.append( "( select a.storage_id,a.sales_resource_id,a.BIZ_TYPE,"+addSub+"sum(a.STOCK_AMOUNT) as stock_amount from ");
	    sql_buff.append(" Rc_BizOrder_Log a  ");
	    sql_buff.append( sql_cond).append( " group by a.storage_id, a.sales_resource_id,a.BIZ_TYPE ) b ");
	    sql_buff.append( "inner join RC_STORAGE c on (b.storage_id=c.storage_id ").append( ") ");
	    sql_buff.append( " inner join SALES_RESOURCE d on b.sales_resource_id=d.sales_resource_id ");
	    sql_buff.append( " where c.rc_type=-1 "+storage_cond ); 
	        
	    sql_count_buff.append("select count(1) as COL_COUNTS,sum(b.stock_amount) as totalAmount from ");
	    sql_count_buff.append( "( select a.storage_id,a.sales_resource_id,a.BIZ_TYPE,"+addSub+"sum(a.STOCK_AMOUNT) as stock_amount from ");
	    sql_count_buff.append(" Rc_BizOrder_Log a  ");
	    sql_count_buff.append(sql_cond).append( " group by a.storage_id, a.sales_resource_id,a.BIZ_TYPE ) b ");
	    sql_count_buff.append("inner join RC_STORAGE c on (b.storage_id=c.storage_id ").append( ") ");
	    sql_count_buff.append(" where c.rc_type=-1 "+storage_cond ); 
		
		String sql = sql_buff.toString();
		String sql_count = sql_count_buff.toString();
				
		dao.setFlag(3); // 设置要查询仓库模板的上下限
		//dao.setFiltered(true);

		dao.setSQL_SELECT(sql);
		dao.setSQL_SELECT_COUNT(sql_count);
		System.out.println("----SrSalesBo:satTerminalInOutNum:sql:"+sql);
		System.out.println("----SrSalesBo:satTerminalInOutNum:sql_count:"+sql_count);
		pm = PageHelperFlx.popupPageModel(dao, "","countSumByCond", pi, ps);
		 
		// 加上统计的库存总数
		RcSaleLogVO tempVO = null;
		//long currPageSum = 0;
		List pageList = pm.getList();
		if(pageList!=null){
//		   for(int i=0;i<pageList.size();i++){
//			   tempVO = (RcSaleLogVO)pageList.get(i);
//			   if(tempVO!=null&&tempVO.getStockAmount()!=null&&tempVO.getStockAmount().trim().length()>0){
//				   currPageSum += Long.parseLong(tempVO.getStockAmount().trim());
//			   }
//		   }
		   tempVO = new RcSaleLogVO();
		   //tempVO.setStockAmount("总计："+pm.getTotalSum()+"个");
		   pageList.add(tempVO);
		}
		//pm.setCurrPageSum(currPageSum);
		return pm;
	}
	
	
	private boolean getRealCfg(String lanId, String tableName) {
		RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
		return dao.getRealCfg(lanId, tableName);
	}
	
}
