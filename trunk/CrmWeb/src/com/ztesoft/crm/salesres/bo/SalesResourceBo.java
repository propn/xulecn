package com.ztesoft.crm.salesres.bo;

import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.DfFeeItemDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;


public class SalesResourceBo  extends DictAction {


	/**
	 * 根据条件查询得所有的对应保证金信息。
	 * @param sType
	 * @param sContent
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public PageModel getSalesResourceInfo(String sType,String sContent,int pi,int ps)
	{
		PageModel pm = new PageModel();
		String sql= "";
		try
		{
			if(sContent == null){ sContent = "";}
			if(sType == null ){ sType = "" ;}
			if(!("".equals(sContent)))
			{
				if("sid".equals(sType))
				{
				  sql += "  and A.sales_resource_id='"+sContent+"' ";
				}
				if("sname".equals(sType))
				{
				  sql += " and A.sales_resource_name like '%"+sContent+"%' ";
				}
			}
			//sql +=" order by sales_resource_id asc";

			SalesRescDAO pdao = SrDAOFactory.getInstance().getSalesRescDAO();
			pm = PageHelper.popupPageModel(pdao, sql, pi, ps);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return pm;
	}

//	public PageModel qryFeeItem(String searchType, String searchKeyword,int pageIndex, int pageSize,String provId) throws Exception {
	public PageModel qryFeeItem(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String searchType  = (String)map.get("searchType");
        String searchKeyword  = (String)map.get("searchKeyword");
        String provId  = (String)map.get("provId");
        
        int pageIndex  = ((Integer)map.get("pageIndex")).intValue();
        int pageSize  = ((Integer)map.get("pageSize")).intValue();
        
    	if(provId==null){
    		return new PageModel();
    	}
         DfFeeItemDAO pdao = SrDAOFactory.getInstance().getDfFeeItemDAO();
         PageModel pm = new PageModel();
         String sql = "";
         if("0".equals(searchType)){
             if(!"".equals(searchKeyword) && searchKeyword != null){
            	 /*if(provId.equals(ParamsConsConfig.PROV_ID_GX)){
            		 sql = " FEE_ITEM_ID = '"+searchKeyword+"'";
            	 }
            	 else*/{
            		 sql = " FEEITEM_CODE = '"+searchKeyword+"'";
            	 }
             }else{
                 sql = " 1=1";
             }
         }else{
        	 /*if(provId.equals(ParamsConsConfig.PROV_ID_GX)){
        		 sql = " FEE_ITEM_NAME like '%"+searchKeyword+"%'";
        		 }else*/{
        			 sql = " FEEITEM_NAME like '%"+searchKeyword+"%'";
        		 }
              
         }
         /*if(provId.equals(ParamsConsConfig.PROV_ID_GX)){
        	 String sql_select = "SELECT FEE_ITEM_CODE AS FEEITEM_CODE,FEE_ITEM_NAME AS FEEITEM_NAME,FEE_KIND_CODE AS FEEKIND_CODE FROM DF_FEE_ITEM";
        	 pdao.setSQL_SELECT(sql_select);
        	}else*/ if(provId.equals(ParamsConsConfig.PROV_ID_HN)){
                String sql_select = "SELECT FEEITEM_CODE,FEEITEM_NAME,FEEKIND_CODE,INVOICE_TYPE,FEERULE,FEERULE_TYPE,RETURNRULE,RETURNRULE_TYPE, null as BILL_CODE,1 as CATALOG_ID, stat as VALID_FLAG FROM DF_FEE_ITEM ";
                pdao.setSQL_SELECT(sql_select);
            }
         pm = PageHelper.popupPageModel(pdao, sql, pageIndex, pageSize);
         return pm;
    }
}
