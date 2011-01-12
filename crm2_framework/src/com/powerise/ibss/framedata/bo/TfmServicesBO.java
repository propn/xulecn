package com.powerise.ibss.framedata.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framedata.dao.TfmActArgsDAO;
import com.powerise.ibss.framedata.dao.TfmServClassDefDAO;
import com.powerise.ibss.framedata.dao.TfmServExtDAO;
import com.powerise.ibss.framedata.dao.TfmServRelationDAO;
import com.powerise.ibss.framedata.dao.TfmServicesDAO;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
/**
 * 后台框架数据维护实现类
 * 
 * @author governlee 2010-02-23
 *
 */
public class TfmServicesBO extends DictAction {
	

	public boolean insertTfmServices(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map tfmServices = (Map) param.get("TfmServices");
		TfmServicesDAO daoTfmServices = new TfmServicesDAO();
		TfmServClassDefDAO daoTfmServClassDef = new TfmServClassDefDAO();
		boolean result = daoTfmServices.insert(tfmServices)
				&& daoTfmServClassDef.insert(tfmServices);
		return result;
	}

	public boolean updateTfmServicesByKey(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map tfmServices = (Map) param.get("TfmServices");
		Map keyCondMap = new HashMap();
		keyCondMap.put("service_name", tfmServices.get("service_name"));// 主键
		TfmServicesDAO daoTfmServices = new TfmServicesDAO();
		TfmServClassDefDAO daoTfmServClassDef = new TfmServClassDefDAO();
		boolean result = daoTfmServices.updateByKey(tfmServices, keyCondMap)
				&& daoTfmServClassDef.updateByKey(tfmServices, keyCondMap);
		return result;
	}

	public PageModel searchTfmServicesData(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "service_name")) {
			whereCond.append(" and service_name like ? ");
			para.add("%" + Const.getStrValue(param, "service_name") + "%");
		}
		if (Const.containStrValue(param, "service_type")) {
			whereCond.append(" and service_type = ? ");
			para.add(Const.getStrValue(param, "service_type"));
		}
		if (Const.containStrValue(param, "service_desc")) {
			whereCond.append(" and service_desc like ? ");
			para.add("%" + Const.getStrValue(param, "service_desc") + "%");
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		TfmServicesDAO dao = new TfmServicesDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getTfmServicesById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		TfmServicesDAO dao = new TfmServicesDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findTfmServicesByCond(DynamicDict dto) throws Exception {
		// 条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// 组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// 调用DAO代码
		TfmServicesDAO dao = new TfmServicesDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteTfmServicesById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map param = Const.getParam(dto);
		String serviceType = (String)param.get("service_type");
		param.remove("service_type");
		TfmServicesDAO daoTfmServices = new TfmServicesDAO();
		if("1".equals(serviceType)){
			TfmServClassDefDAO daoTfmServClassDef = new TfmServClassDefDAO();	
			return daoTfmServClassDef.deleteByKey(param) > 0 && daoTfmServices.deleteByKey(param) > 0;
				
		}
		if("2".equals(serviceType)){
			TfmServRelationDAO daoTfmServRelation = new TfmServRelationDAO();
			String whereCond = "and service_name = ?";
			String whereCond2 = "and (co_service_name = ? or cond_service_name = ?)";
			List whereCondParams = new ArrayList();
			List whereCondParams2 = new ArrayList();
			whereCondParams.add(param.get("service_name"));
			whereCondParams2.add(param.get("service_name"));
			whereCondParams2.add(param.get("service_name"));
			return daoTfmServRelation.delete(whereCond, whereCondParams) >0 && 
			daoTfmServRelation.delete(whereCond2, whereCondParams2) >0  &&
			       daoTfmServices.deleteByKey(param) > 0; 
			
		}
		if("3".equals(serviceType)){
			TfmActArgsDAO daoTfmActArgs = new TfmActArgsDAO();
			TfmServExtDAO daoTfmServExt = new TfmServExtDAO();
			String whereCond = "and action_id = ?";
			String whereCond2 = "and service_name = ?";
			List whereCondParams = new ArrayList();
			whereCondParams.add(param.get("service_name"));
			
			return daoTfmActArgs.delete(whereCond, whereCondParams) > 0 && 
			       daoTfmServExt.delete(whereCond2, whereCondParams)> 0 &&
			       daoTfmServices.deleteByKey(param) > 0;		
		}
		
	
		return false;
	}

	public boolean checkExistServiceName(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		TfmServicesDAO dao = new TfmServicesDAO();
		Map serviceName = dao.findByPrimaryKey(param);
		if (serviceName.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean insertTfmServRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map TfmServRelation = (Map) param.get("TfmServRelation");

		TfmServRelationDAO dao = new TfmServRelationDAO();
		boolean result = dao.insert(TfmServRelation);
		return result;
	}

	public boolean updateTfmServRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map TfmServRelation = (Map) param.get("TfmServRelation");
//		String keyStr = "service_name,seq";
//		Map keyCondMap = Const.getMapForTargetStr(TfmServRelation, keyStr);
		Map keyCondMap = new HashMap();
		keyCondMap.put("service_name", TfmServRelation.get("service_name"));
		keyCondMap.put("seq", TfmServRelation.get("seq"));
		TfmServRelationDAO dao = new TfmServRelationDAO();
		boolean result = dao.updateByKey(TfmServRelation, keyCondMap);

		return result;
	}

	public PageModel searchTfmServRelationData(DynamicDict dto)
			throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "service_name")) {
			whereCond.append(" and service_name = ? ");
			para.add(Const.getStrValue(param, "service_name"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		TfmServRelationDAO dao = new TfmServRelationDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getTfmServRelationById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		TfmServRelationDAO dao = new TfmServRelationDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findTfmServRelationOrderBySeq(DynamicDict dto) throws Exception {
		// 条件处理

		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// 组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		whereCond += "order by seq asc";
		List para = where.getPara();
		// 调用DAO代码
		TfmServRelationDAO dao = new TfmServRelationDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteTfmServRelationById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		TfmServRelationDAO dao = new TfmServRelationDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}
	
	public boolean checkExistServiceSeq(DynamicDict dto) throws Exception{
		Map tfmServRelationKeyMap = Const.getParam(dto);
		TfmServRelationDAO dao = new TfmServRelationDAO();
		Map result = dao.findByPrimaryKey(tfmServRelationKeyMap);
		if(result.size() > 0){
			return true;
		}
		else{
			return false;
		}	
	}
	
	public boolean insertTfmServExt(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TfmServExt = (Map) param.get("TfmServExt") ;
		
		TfmServExtDAO dao = new TfmServExtDAO();
		boolean result = dao.insert(TfmServExt) ;
		return result ;
	}

	
	public boolean updateTfmServExt(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TfmServExt = (Map) param.get("TfmServExt") ;
		//String keyStr = "service_name,seq";
		//Map keyCondMap  = Const.getMapForTargetStr(TfmServExt,  keyStr) ;
		Map keyCondMap = new HashMap();
		keyCondMap.put("service_name", TfmServExt.get("service_name"));
		keyCondMap.put("seq", TfmServExt.get("seq"));
		TfmServExtDAO dao = new TfmServExtDAO();
		boolean result = dao.updateByKey( TfmServExt, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchTfmServExtData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "service_name")){
			whereCond.append(" and service_name = ? ");
			para.add(Const.getStrValue(param , "service_name")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		TfmServExtDAO dao = new TfmServExtDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getTfmServExtById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		TfmServExtDAO dao = new TfmServExtDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findTfmServExtOrderBySeq(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		whereCond +="order by seq asc";
		List para = where.getPara() ;
		//调用DAO代码
		TfmServExtDAO dao = new TfmServExtDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	
	
	
	
	public boolean deleteTfmServExtById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		TfmServExtDAO dao = new TfmServExtDAO();
		List whereCondParams = new ArrayList();
		whereCondParams.add(keyCondMap.get("service_name"));
		List countList = dao.findBySql("select count(*) as col_counts from tfm_serv_ext where service_name = ?", whereCondParams);
		if(countList.size() == 0){
			return false;
		}
		String col_counts = (String)((Map)countList.get(0)).get("col_counts");
		int size = Integer.parseInt(col_counts);
		if(size == 1){
			Map param = new HashMap();
			param.put("action_id",keyCondMap.get("service_name"));
			dto.setValueByName(Const.ACTION_PARAMETER, param);
			deleteTfmActArgsByCond(dto);			
		}	
		return dao.deleteByKey( keyCondMap ) > 0;
	}
	
	public boolean deleteTfmActArgsByCond(DynamicDict dto) throws Exception{
		Map param = Const.getParam(dto);
		List whereCondParams = new ArrayList();
		String action_id = (String)param.get("action_id");
		if(action_id == null){
			return false;
		}
		whereCondParams.add(action_id);
		TfmActArgsDAO dao = new TfmActArgsDAO();
		String whereCond = "and action_id = ?"; 
		return dao.delete(whereCond, whereCondParams) > 0;		
	}
	
	
	
	
	
	public boolean insertTfmActArgs(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TfmActArgs = (Map) param.get("TfmActArgs") ;
		
		TfmActArgsDAO dao = new TfmActArgsDAO();
		boolean result = dao.insert(TfmActArgs) ;
		return result ;
	}

	
	public boolean updateTfmActArgs(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TfmActArgs = (Map) param.get("TfmActArgs") ;
		//String keyStr = "action_id,arg_seq";
		//Map keyCondMap  = Const.getMapForTargetStr(TfmActArgs,  keyStr) ;
		Map keyCondMap = new HashMap();
		keyCondMap.put("action_id", TfmActArgs.get("action_id"));
		keyCondMap.put("arg_seq", TfmActArgs.get("arg_seq"));
		TfmActArgsDAO dao = new TfmActArgsDAO();
		boolean result = dao.updateByKey( TfmActArgs, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchTfmActArgsData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "action_id")){
			whereCond.append(" and action_id = ? ");
			para.add(Const.getStrValue(param , "action_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		TfmActArgsDAO dao = new TfmActArgsDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getTfmActArgsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		TfmActArgsDAO dao = new TfmActArgsDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findTfmActArgsOrderBySeq(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		whereCond += "order by arg_seq asc";
		List para = where.getPara() ;
		//调用DAO代码
		TfmActArgsDAO dao = new TfmActArgsDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteTfmActArgsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		TfmActArgsDAO dao = new TfmActArgsDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	public boolean checkTfmServExtSeq(DynamicDict dto) throws Exception{
		Map keyCondMap = Const.getParam(dto) ;
		TfmServExtDAO dao = new TfmServExtDAO();		
		return dao.findByPrimaryKey(keyCondMap).size() > 0;
	}
	
	public boolean checkTfmActArgsSeq(DynamicDict dto) throws Exception{
		Map keyCondMap = Const.getParam(dto) ;
		TfmActArgsDAO dao = new TfmActArgsDAO();
		return dao.findByPrimaryKey(keyCondMap).size() > 0;
	}
	
}
