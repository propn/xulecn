package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ServBureauDAO ;
import com.ztesoft.crm.product.dao.ServRelationGrpDAO;
import com.ztesoft.oaas.bo.PartyBO;

public class ServBureauBO extends DictAction  {
	/**
	 * 保存
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean saveServBureau(DynamicDict dto  ) throws Exception {
		 Map param = Const.getParam(dto);
		 ServBureauDAO dao = new ServBureauDAO();
		 List ServBureauList = (ArrayList)param.get("ServBureauList");
		 Map key = (HashMap)ServBureauList.get(0);
		 String servofferId = Const.getStrValue(key,"service_offer_id");
		 this.saveOldData(servofferId);
		 dao.deleteByKey(key);
		 Map loginMap = (HashMap)RequestContext.getContext().
			getHttpSession().getAttribute("LoginRtnParamsHashMap");
		 java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		 java.util.Date currentTime = new java.util.Date();//得到当前系统时间
   		 String str_date1 = formatter.format(currentTime); //将日期时间格式化
		 for(int i=0;i<ServBureauList.size();i++){
			 Map temp = (HashMap)ServBureauList.get(i);
			 temp.put("state","00A");
			 temp.put("seq","0");
			 temp.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
			 temp.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
			 temp.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
	   		 temp.put("oper_date",str_date1);
			 dao.insert(temp) ;
		 }
		 return true;
	}

	/**
	 * 查询已选区域
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchServBureauOld(DynamicDict dto ) throws Exception {
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String servOfferId = Const.getStrValue(param,"service_offer_id");
		para.add(servOfferId);

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码
		ServBureauDAO dao = new ServBureauDAO();
		String sql ="select a.region_id, b.region_name,a.eff_date,a.exp_date from service_bureau a, region b " +
				"where a.region_id = b.region_id  and a.service_offer_id =? and a.state ='00A'";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;
	}
	/**
	 * 查询登录员工 本地网列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List getBureauLeftLan(DynamicDict dto) throws Exception {
		List reList = new ArrayList();
		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
		String lanId = Const.getStrValue(loginMap,"vg_lan_id");
		String queryRegionLevel="select region_level from region where region_id =?";
		String regionLevel = Base.query_string(JNDINames.PM_DATASOURCE,queryRegionLevel,new String[]{lanId},1);
		if("97B".equals(regionLevel)){
			String queryRegion = "select region_id,region_name from region where parent_region_id =?";
			DynamicDict aDict = Base.query(JNDINames.PM_DATASOURCE,queryRegion,new String[]{lanId},null,"listName",1);
			reList = (ArrayList)aDict.getValueByName("listName",-1);
		}
		if("97C".equals(regionLevel)){
			String queryRegion = "select region_id,region_name from region where region_id =?";
			DynamicDict aDict = Base.query(JNDINames.PM_DATASOURCE,queryRegion,new String[]{lanId},null,"listName",1);
			reList = (ArrayList)aDict.getValueByName("listName",-1);
		}
		return reList;
	}
	/**
	 * 查询所有市县
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel getBureauLeftRegion(DynamicDict dto ) throws Exception {
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List reList = new ArrayList();
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码

		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
		String lanId = Const.getStrValue(loginMap,"vg_lan_id");
		String queryRegionLevel="select region_level from region where region_id =?";
		String regionLevel = Base.query_string(JNDINames.PM_DATASOURCE,queryRegionLevel,new String[]{lanId},1);
		if("97B".equals(regionLevel)){//省级
			String queryLan = "select region_id from region where parent_region_id =?";//查出本地网
			DynamicDict aDict = Base.query(JNDINames.PM_DATASOURCE,queryLan,new String[]{lanId},null,"listName",1);
			List tempList = (ArrayList)aDict.getValueByName("listName",-1);
			for(int i=0;i<tempList.size();i++){
				Map tempMap = (HashMap)tempList.get(i);
				String parentRegionId = Const.getStrValue(tempMap,"region_id");
				String queryRegion = "select region_id,region_name from region where parent_region_id =?";//查出本地网下地域
				DynamicDict regionDict = Base.query(JNDINames.PM_DATASOURCE,queryRegion,new String[]{parentRegionId},null,"listName",1);
				List regionList = (ArrayList)regionDict.getValueByName("listName",-1);
				for(int j=0;j<regionList.size();j++){
					Map regionMap = (HashMap)regionList.get(j);
					reList.add(regionMap);
				}
			}
		}
		if("97C".equals(regionLevel)){//本地网 查询市县
			String queryRegion = "select region_id,region_name from region where parent_region_id =?";
			DynamicDict aDict = Base.query(JNDINames.PM_DATASOURCE,queryRegion,new String[]{lanId},null,"listName",1);
			reList = (ArrayList)aDict.getValueByName("listName",-1);
		}
		result.setList(reList);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;
	}
	/**
	 * 查询可选区域 未用
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String searchServBureauAll(DynamicDict dto ) throws Exception {

		Map loginMap = (HashMap)RequestContext.getContext().
		getHttpSession().getAttribute("LoginRtnParamsHashMap");
		String lanId = Const.getStrValue(loginMap,"vg_lan_id");
		dto.setValueByName("parentId",lanId);
		PartyBO partyBo = new PartyBO();
		return partyBo.getTelecomOrganizationListByParentId(dto);

	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getServBureauById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ServBureauDAO dao = new ServBureauDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;

		return result ;
	}


	public List findServBureauByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ServBureauDAO dao = new ServBureauDAO();
		List result = dao.findByCond( whereCond, para) ;

		return result ;
	}


	public boolean deleteServBureauById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		String servofferId = Const.getStrValue(keyCondMap,"service_offer_id");
		this.saveOldData(servofferId);
		ServBureauDAO dao = new ServBureauDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}

	private void saveOldData(String servofferId)  throws Exception{
		int ecode=11;
		DynamicDict servOfferOldDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from service_bureau a where service_offer_id = ?  and state = '00A' ", new String[]{servofferId}, ecode);
		String seqmax = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from arc_service_bureau where service_offer_id = ? ", new String[]{servofferId}, ecode);
		for(int i=0;i<servOfferOldDict.getCountByName("service_offer_id");i++){
			Base.dict__set_val(servOfferOldDict,"seq",i,seqmax);
			Base.action__execute(JNDINames.PM_DATASOURCE, ecode,servOfferOldDict,i," insert into arc_service_bureau ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}
	}
}
