package com.ztesoft.vsop.protocol.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.protocol.dao.NeCommunicateProtocolDAO;

public class NeCommunicateProtocolBO extends DictAction  {
	public boolean insertNeCommunicateProtocol(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommunicateProtocol = (Map) param.get("NeCommunicateProtocol") ;
		
		NeCommunicateProtocolDAO dao = new NeCommunicateProtocolDAO();
		boolean result = dao.insert(NeCommunicateProtocol) ;
		return result ;
	}

	
	public boolean updateNeCommunicateProtocol(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommunicateProtocol = (Map) param.get("NeCommunicateProtocol") ;
		String keyStr = "communicate_protocol_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeCommunicateProtocol,  keyStr) ;
		NeCommunicateProtocolDAO dao = new NeCommunicateProtocolDAO();
		boolean result = dao.updateByKey( NeCommunicateProtocol, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCommunicateProtocolData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param, "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (n.name like '%"+name+"%' or n.name like '%"+name.toUpperCase()+"%')");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeCommunicateProtocolDAO dao = new NeCommunicateProtocolDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeCommunicateProtocolById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeCommunicateProtocolDAO dao = new NeCommunicateProtocolDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCommunicateProtocolByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeCommunicateProtocolDAO dao = new NeCommunicateProtocolDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommunicateProtocolById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommunicateProtocolDAO dao = new NeCommunicateProtocolDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
