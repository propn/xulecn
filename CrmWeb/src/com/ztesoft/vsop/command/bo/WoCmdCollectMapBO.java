package com.ztesoft.vsop.command.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.command.dao.WoCmdCollectMapDAO;

public class WoCmdCollectMapBO extends DictAction  {
	public boolean insertWoCmdCollectMap(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoCmdCollectMap = (Map) param.get("WoCmdCollectMap") ;
		
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		boolean result = dao.insert(WoCmdCollectMap) ;
		return result ;
	}

	
	public boolean updateWoCmdCollectMap(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		boolean result = dao.updateByMap(param);
		
		return result ;
	}
	
	
	public PageModel searchWoCmdCollectMapData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "command_collect_id")){
			whereCond.append(" and m.command_collect_id = ? ");
			para.add(Const.getStrValue(param , "command_collect_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoCmdCollectMapById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoCmdCollectMapByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoCmdCollectMapById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		boolean result = dao.deleteByMap( keyCondMap);
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "command_collect_id"));
		
		return result ;
	}
	
	/**
	 * 验证工单类型是否唯一
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateOrderType(DynamicDict dto) throws Exception {
		Map m = Const.getParam(dto);
		List list = new ArrayList();
		list.add(m.get("command_collect_id"));
		list.add(m.get("order_type_id"));
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		List l = dao.findBySql(dao.getSqlForValidateOrderType(), list);
		if (null != l && !l.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证工单动作是否唯一
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateOrderActType(DynamicDict dto) throws Exception {
		Map m = Const.getParam(dto);
		List list = new ArrayList();
		list.add(m.get("command_collect_id"));
		list.add(m.get("order_act_type"));
		WoCmdCollectMapDAO dao = new WoCmdCollectMapDAO();
		List l = dao.findBySql(dao.getSqlForValidateOrderActType(), list);
		if (null != l && !l.isEmpty()) {
			return false;
		}
		return true;
	}
}
