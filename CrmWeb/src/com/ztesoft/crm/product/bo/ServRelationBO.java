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
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.crm.product.dao.ServBureauDAO;
import com.ztesoft.crm.product.dao.ServRelationDAO ;

public class ServRelationBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchServRelationData 改方法的参数名称
 		3. findServRelationByCond(String relation_id,String seq) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	private SequenceManageDAOImpl  sequenceManageDAO=new SequenceManageDAOImpl();

	public boolean saveServRelation(DynamicDict dto  ) throws Exception {
		/*Map param = Const.getParam(dto) ;
		Map ServRelation = (Map) param.get("servRela") ;
		String relationId = sequenceManageDAO.getNextSequenceWithDB("RELATION_ID",JNDINames.PM_DATASOURCE);
		ServRelation.put("relation_id",relationId);
		ServRelationDAO dao = new ServRelationDAO();
		boolean result = dao.insert(ServRelation) ;
		return result ;*/
		 Map param = Const.getParam(dto);
		 ServRelationDAO dao = new ServRelationDAO();
		 List servRelaList = (ArrayList)param.get("servRelaList");

		 Map loginMap = (HashMap)RequestContext.getContext().
			getHttpSession().getAttribute("LoginRtnParamsHashMap");
		 java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		 java.util.Date currentTime = new java.util.Date();//得到当前系统时间
  		 String str_date1 = formatter.format(currentTime); //将日期时间格式化
		 for(int i=0;i<servRelaList.size();i++){
			 Map temp = (HashMap)servRelaList.get(i);
			 String relationId = Const.getStrValue(temp,"relation_id");
			 if("".equals(relationId)){
				 String relationIdNew = sequenceManageDAO.getNextSequenceWithDB("RELATION_ID",JNDINames.PM_DATASOURCE);
				 temp.put("relation_id",relationIdNew);
				 temp.put("state","00A");
				 temp.put("seq","0");
				 temp.put("party_id",Const.getStrValue(loginMap,"vg_depart_id"));//操作点
				 temp.put("party_role_id",Const.getStrValue(loginMap,"vg_oper_id"));//操作员
				 temp.put("oper_region_id",Const.getStrValue(loginMap,"vg_business_id"));//操作员地域
		   		 temp.put("oper_date",str_date1);

				 dao.insert(temp) ;
			 }
		 }
		 return true;
	}


	public boolean updateServRelation(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ServRelation = (Map) param.get("ServRelation") ;
		String keyStr = "relation_id,seq";
		Map keyCondMap  = Const.getMapForTargetStr(ServRelation,  keyStr) ;
		ServRelationDAO dao = new ServRelationDAO();
		boolean result = dao.updateByKey( ServRelation, keyCondMap );

		return result ;
	}
	public PageModel searchServData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
   		int pageIndex = Const.getPageIndex(param) ;
		String prodCatType = Const.getStrValue(param,"prod_cat_type");
		String actionType = Const.getStrValue(param,"action_type");
		String sql ="select service_offer_id , service_offer_name "+
						  " from service_offer a, product_cat b "+
						  " where a.prod_cat_type = b.prod_cat_type "+
						  " and a.state = '00A' "+
						  " and b.prod_cat_type = ? "+
						  " and a.action_type = ?";
		ServRelationDAO dao = new ServRelationDAO();
		List para = new ArrayList();
		para.add(prodCatType);
		para.add(actionType);
		List list = dao.findBySql(sql,para);
		PageModel result = new PageModel();
		result.setList(list);
		result.setPageIndex(pageIndex);
		result.setPageSize(pageSize);
		return result;
	}

	public List searchServRelationData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "serv_a_id")){
			//whereCond.append(" and serv_a_id = ? ");
			para.add(Const.getStrValue(param , "serv_a_id")) ;
		}
		/*if(Const.containStrValue(param , "relation_type_id")){
			//whereCond.append(" and relation_type_id = ? ");
			para.add(Const.getStrValue(param , "relation_type_id")) ;
		}*/

		//调用DAO代码
		String sql ="select a.relation_id,a.serv_z_id,a.relation_type_id,a.operation_flag,a.eff_date,a.exp_date,b.service_offer_name " +
				"from service_relation a,service_offer b " +
				"where a.serv_z_id = b.service_offer_id and a.serv_a_id =? ";
		ServRelationDAO dao = new ServRelationDAO();
		return dao.findBySql( sql , para) ;

	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getServRelationById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ServRelationDAO dao = new ServRelationDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;

		return result ;
	}


	public List findServRelationByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ServRelationDAO dao = new ServRelationDAO();
		List result = dao.findByCond( whereCond, para) ;

		return result ;
	}


	public boolean deleteServRelationById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ServRelationDAO dao = new ServRelationDAO();
		String relationId = Const.getStrValue(keyCondMap,"relation_id");
		this.saveOldData(relationId);
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
	private void saveOldData(String relationId)  throws Exception{
		int ecode=12;
		DynamicDict servOfferOldDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from service_relation a where relation_id=? and state = '00A' ", new String[]{relationId}, ecode);
		String seqmax = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from arc_service_relation where relation_id = ? ", new String[]{relationId}, ecode);
		for(int i=0;i<servOfferOldDict.getCountByName("relation_id");i++){
			Base.dict__set_val(servOfferOldDict,"seq",0,seqmax);
			Base.action__execute(JNDINames.PM_DATASOURCE, ecode,servOfferOldDict,0," insert into arc_service_relation ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}
	}
}
