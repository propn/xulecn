package com.ztesoft.vsop.command.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.command.dao.WoCommandCollectDAO;

public class WoCommandCollectBO extends DictAction  {
	public boolean insertWoCommandCollect(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoCommandCollect = (Map) param.get("WoCommandCollect") ;
		
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		boolean result = dao.insert(WoCommandCollect) ;
		return result ;
	}

	
	public boolean updateWoCommandCollect(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoCommandCollect = (Map) param.get("WoCommandCollect") ;
		String keyStr = "command_collect_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoCommandCollect,  keyStr) ;
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		boolean result = dao.updateByKey( WoCommandCollect, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoCommandCollectData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "device_id")){
			whereCond.append(" and device_id = ? ");
			para.add(Const.getStrValue(param , "device_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoCommandCollectById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoCommandCollectByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoCommandCollectById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * 验证集合顺序，不可以重复
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateCollectSeq(DynamicDict dto) throws Exception {
		Map m = Const.getParam(dto);
		List list = new ArrayList();
		list.add(m.get("device_id"));
		list.add(m.get("collect_seq"));
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		List l = dao.findBySql(dao.getSqlForValidateCollectSeq(), list);
		if (null != l && !l.isEmpty()) {
			return false;//即已存在相同的系统集合
		}
		return true;
	}
	/**
	 * 验证对端系统是否有关联的指令模板集
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateCollectByDeviceId(DynamicDict dto) throws Exception {
		Map m = Const.getParam(dto);
		List list = new ArrayList();
		list.add(m.get("device_id"));
		WoCommandCollectDAO dao = new WoCommandCollectDAO();
		List result = dao.findBySql(dao.getCollectByDeviceId(), list);
		if (null != result && result.size()>0) {
			return false;//此系统关联有指令模板集
		}
		return true;
	}
}
