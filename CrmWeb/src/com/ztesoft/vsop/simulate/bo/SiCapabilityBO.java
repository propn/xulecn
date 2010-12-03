package com.ztesoft.vsop.simulate.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.simulate.dao.SiCapabilityDAO ;

public class SiCapabilityBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchSiCapabilityData 改方法的参数名称
 		3. findSiCapabilityByCond(String id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiCapability(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiCapability = (Map) param.get("SiCapability") ;
		
		SiCapabilityDAO dao = new SiCapabilityDAO();
		boolean result = dao.insert(SiCapability) ;
		return result ;
	}

	
	public boolean updateSiCapability(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiCapability = (Map) param.get("SiCapability") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SiCapability,  keyStr) ;
		SiCapabilityDAO dao = new SiCapabilityDAO();
		boolean result = dao.updateByKey( SiCapability, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSiCapabilityData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "app_id")){
			whereCond.append(" and app_id = ? ");
			para.add(Const.getStrValue(param , "app_id")) ;
		}
		/*if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}*/
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SiCapabilityDAO dao = new SiCapabilityDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSiCapabilityById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SiCapabilityDAO dao = new SiCapabilityDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSiCapabilityByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SiCapabilityDAO dao = new SiCapabilityDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSiCapabilityById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SiCapabilityDAO dao = new SiCapabilityDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
