package com.ztesoft.crm.salesres.bo;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.NochangelogDAO;
import com.ztesoft.crm.salesres.dao.RcFamilyEntityRelaDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcFamilyEntityRelaVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;


public class CommonUtilBo {
	/**
	 * 检查特定资源编码是否在指定的仓库存在
	 * @param resouceId
	 * @param storageId
	 * @param resouceInstanceCode
	 * @return
	 */
	public static boolean checkRescInstanceCodeExist(String resouceId,String storageId,String resourceInstanceCode){
		String tableName = getTableNameByResouceId(resouceId);
		tableName = ("".equals(tableName))?"rc_entity2":tableName;
		StringBuffer sql= new StringBuffer();
		sql.append(" select 'x' from ").append(tableName).append(" where sales_resource_id=");
		sql.append(resouceId).append(" and ").append(" storage_id = ").append(storageId);
		sql.append(" and resource_instance_code='").append(resourceInstanceCode).append("'");
		SqlComDAO com = SrDAOFactory.getInstance().getSqlComDAO();
		return com.checkExist(sql.toString());
	}
    /**
     * 获得可以看到的仓库的id的集合
     *
     * @param operId
     * @param departId
     * @return
     */
    public static String getStorageRightFilteSql(String operId,
        String departId, String mainTableAlias) {
        String cond_right = "";

        if ((mainTableAlias == null) || (mainTableAlias.trim().length() < 1)) {
            mainTableAlias = "a.STORAGE_ID";
        }

        if ((operId == null) || (operId.trim().length() == 0)) {
            operId = "-1";
        }

        if ((departId == null) || (departId.trim().length() == 0)) {
            departId = "-1";
        }

        cond_right = " and ( exists(select x.STORAGE_ID from mp_storage x where x.STORAGE_ID=" +
            mainTableAlias + " and x.oper_id=" + operId + 
            " union all select y.STORAGE_ID from STORAGE_DEPART_RELA y where y.STORAGE_ID=" +
            mainTableAlias + " and y.depart_id=" + departId + ") )";

        return cond_right;
    }

    /**
     * 根据传入的资源家族ID， 获得该资源对应的存储表
     *
     * @param familyId
     * @return
     */
    public static String getTableNameByFamilyId(String familyId) {
        RcFamilyEntityRelaDAO relaDao = SrDAOFactory.getInstance()
                                                    .getRcFamilyEntityRelaDAO();
        String tableName = "";
        if (familyId==null||familyId.length()<=0) {
			return tableName;
		}
        List list = relaDao.findByCond(" FAMILY_ID=" + familyId);

        if ((list != null) && (list.size() > 0) && (list.get(0) != null)) {
            tableName = ((RcFamilyEntityRelaVO) list.get(0)).getEntityTabName();
        }

        return tableName;
    }

    /**
     * 根据传入的营销资源ID， 获得该资源对应的存储表
     *
     * @param resouceId
     * @return
     */
    public static String getTableNameByResouceId(String resouceId) {
        SalesRescDAO salesRescDao = SrDAOFactory.getInstance().getSalesRescDAO();
        SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(resouceId);
        String tableName = "";
        if (resouceId==null||resouceId.length()<=0) {
			return tableName;
		}
        if (salesRescVO != null) {
        	if(SalesRescVO.ManageMode_Stock.equals(salesRescVO.getManageMode()))
        		tableName = "rc_stock";
        	else 
                tableName = getTableNameByFamilyId(salesRescVO.getFamilyId());
        }
        if ("".equals(tableName)) {
			tableName="rc_entity2";
		}
        return tableName;
    }
    /**
     * 根据表名获得特定的资源状态字段名.
     * @param tableName
     * @return
     */
    public static String getRescStateNameByTableName(String tableName){
    	if(tableName!=null && !tableName.trim().equals("")){
    		if("RC_ENTITY".equalsIgnoreCase(tableName) || "rc_entity2".equalsIgnoreCase(tableName)){
    			return "CURR_STATE";
    		}
    		return "RESOURCE_STATE";
    	}
    	return "";
    }
    
    /**
     * 查询号码修改的通用日志
     * @param map
     * @return
     */
    public PageModel qryComEntityChgLog(Map map,int pi, int ps){
    	
    	NochangelogDAO dao = SrNSDAOFactory.getInstance().getNochangelogDAO();
		String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
    	String sql = " 1=1 ";
    	String logcode = null;
    	String beginDate = null;
    	String endDate = null;
    	String startCode = null;
    	String endCode = null;
    	String changeType = null;
    	if(map.get("logcode")!=null&&map.get("logcode").toString().trim().length()>0){
    		logcode = map.get("logcode").toString();
    	}
    	if(map.get("beginDate")!=null&&map.get("beginDate").toString().trim().length()>0){
    		beginDate = map.get("beginDate").toString();
    	}
    	if(map.get("endDate")!=null&&map.get("endDate").toString().trim().length()>0){
    		endDate = map.get("endDate").toString();
    	}
    	if(map.get("startCode")!=null&&map.get("startCode").toString().trim().length()>0){
    		startCode = map.get("startCode").toString();
    	}
    	if(map.get("endCode")!=null&&map.get("endCode").toString().trim().length()>0){
    		endCode = map.get("endCode").toString();
    	}
    	if(map.get("changeType")!=null&&map.get("changeType").toString().trim().length()>0){
    		changeType = map.get("changeType").toString();
    	}
    	
    	if (logcode != null) {
			sql += " and logcode = " + logcode;
		}
		if (startCode != null) {
			sql += " and RESOURCE_INSTANCE_CODE >= " + startCode;
		}
		if (endCode != null) {
			sql += " and RESOURCE_INSTANCE_CODE <= " + endCode;
		}
		if (changeType != null) {
			if("1".equals(changeType)){
				sql += " and (self_help_flag_old is not null or self_help_flag_new is not null) ";
			}
			if("2".equals(changeType)){
				sql += " and (resc_level_from is not null or resc_level_to is not null) ";
			}
			if("3".equals(changeType)){
				sql += " and (storage_id_from is not null or storage_id_to is not null) ";
			}
			if("4".equals(changeType)){
				sql += " and (resource_state_from is not null or resource_state_to is not null) ";
			}
			if("5".equals(changeType)){
				sql += " and (state_from is not null or state_to is not null) ";
			}
		}
    	
		if ("INFORMIX".equals(databaseType)) {
			if (beginDate!=null) {
				sql += "  and CHANGEDATE>=to_date('" + beginDate
						+ "','%Y-%m-%d')  ";
			}
			if (endDate!=null) {
				sql += "  and CHANGEDATE<=to_date('" + endDate
						+ "','%Y-%m-%d')  ";
			}
		} else {
			if (beginDate!=null) {
				sql += "  and CHANGEDATE>=to_date('" + beginDate
						+ "','yyyy-mm-dd')  ";
			}
			if (endDate!=null) {
				sql += "  and CHANGEDATE<=to_date('" + endDate
						+ "','yyyy-mm-dd')+1  ";
			}
		}
		
		sql += " order by LOGCODE desc ";
		
    	PageModel pm = PageHelper.popupPageModel(dao, sql, pi, ps);
		return pm;
    }
    
}
