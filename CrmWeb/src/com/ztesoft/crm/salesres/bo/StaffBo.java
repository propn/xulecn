package com.ztesoft.crm.salesres.bo;

import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.StaffDAO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;

public class StaffBo  extends DictAction {


//	public PageModel getStaffInfo(String sType,String sContent,String sLanId,String provId, int pi,int ps)
	public PageModel getStaffInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String sType  = (String)map.get("sType");
        String sContent  = (String)map.get("sContent");
        String sLanId  = (String)map.get("sLanId");
        String provId  = (String)map.get("PROV_ID");
        
        int pi  = ((Integer)map.get("pageIndex")).intValue();
        int ps  = ((Integer)map.get("pageSize")).intValue();
        
		PageModel pm = new PageModel();
		String sql= "  state='00A' AND lan_id="+sLanId+" ";
		try
		{
			if(sContent == null){ sContent = "";}
			if(sType == null ){ sType = "" ;}
			
			/*if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
					
					sql +=" and a.party_role_id = b.party_role_id ";
					if(!("".equals(sContent)))
					{
					if("scode".equals(sType))
					{
					  sql += "  and b.staff_code like '%"+sContent+"%' ";
					}
					if("sname".equals(sType))
					{
					  sql += "  and a.party_role_name like '%"+sContent+"%' ";
					}
					}
				}else*/{
					if(!("".equals(sContent)))
					{
					if("scode".equals(sType))
					{
					  sql += "  and staff_code like '%"+sContent+"%' ";
					}
					if("sname".equals(sType))
					{
					  sql += "  and party_role_name like '%"+sContent+"%' ";
					}
					}
				}
			
			StaffDAO sdao = (StaffDAO) SrDAOFactory.getInstance().getStaffDAO();
			if(provId!=null && provId.equals(ParamsConsConfig.PROV_ID_GX)){
			  sdao.setFlag(0);
			}else if(provId!=null && (provId.equals(ParamsConsConfig.PROV_ID_JX)||provId.equals(ParamsConsConfig.PROV_ID_HN))){
				sdao.setFlag(0);
			}
			pm = PageHelper.popupPageModel(sdao, sql, pi, ps);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return pm;
	}

//	public PageModel getOrganizationInfo(String sType,String sContent,String sLanId,int pi,int ps)
	public PageModel getOrganizationInfo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        String sType  = (String)map.get("sType");
        String sContent  = (String)map.get("sContent");
        String sLanId  = (String)map.get("sLanId");
		
        int pi  = ((Integer)map.get("pageIndex")).intValue();
        int ps  = ((Integer)map.get("pageSize")).intValue();
	    
	    
	    PageModel pm = new PageModel();
		//String sql= "org_type_id=6 AND  state='00A' AND lan_id="+sLanId+" ";
		String sql= "org_type_id=6 AND  state='00A'";
		try
		{
			if(sContent == null){ sContent = "";}
			if(sType == null ){ sType = "" ;}
			if(!("".equals(sContent)))
			{
				if("scode".equals(sType))
				{
				  sql += "  and org_Code like '%"+sContent+"%' ";
				}
				if("sname".equals(sType))
				{
				  sql += "  and org_name like '%"+sContent+"%' ";
				}
			}

			OrganizationDAO odao = (OrganizationDAO) SrDAOFactory.getInstance().getOrganizationDAO();
			pm = PageHelper.popupPageModel(odao, sql, pi, ps);

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return pm;
	}
	
	/**
	 * 根据操作员编码查询操作员相关信息，以map的形式返回
	 * @param operCode
	 * @return
	 */
	public Map qryOperatorByCode(String operCode){
		if(operCode==null||operCode.trim().length()<1)
			return null;
		Map map = null;
		SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		String sql = "select LAN_ID,OPER_ID,OPER_CODE,PASSWORD,DEPART_ID,OPER_NAME,VALID_FLAG,LOGIN_STATUS"
			         +" from mp_operator where OPER_CODE='"+operCode+"'";
		String[] arrs = new String[]{"LAN_ID","OPER_ID","OPER_CODE","PASSWORD","DEPART_ID","OPER_NAME","VALID_FLAG","LOGIN_STATUS"};
	    List list = dao.qryComSql(sql, arrs);
	    if(list!=null&&list.size()>0)
	    	map = (Map)list.get(0);
	    return map;
	}
	
}

