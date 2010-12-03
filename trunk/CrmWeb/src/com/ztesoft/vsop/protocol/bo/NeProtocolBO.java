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

import com.ztesoft.vsop.protocol.dao.NeProtocolDAO;

public class NeProtocolBO extends DictAction  {

	
	public boolean insertNeProtocol(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeProtocol = (Map) param.get("NeProtocol") ;
		
		NeProtocolDAO dao = new NeProtocolDAO();
		boolean result = dao.insert(NeProtocol) ;
		return result ;
	}

	
	public boolean updateNeProtocol(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeProtocol = (Map) param.get("NeProtocol") ;
		String keyStr = "ne_protocol_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeProtocol,  keyStr) ;
		NeProtocolDAO dao = new NeProtocolDAO();
		boolean result = dao.updateByKey( NeProtocol, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeProtocolData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;

		if(Const.containStrValue(param, "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (n.name like '%"+name+"%' or n.name like '%"+name.toUpperCase()+"%')");
		}
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		NeProtocolDAO dao = new NeProtocolDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getNeProtocolById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		NeProtocolDAO dao = new NeProtocolDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeProtocolByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		NeProtocolDAO dao = new NeProtocolDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeProtocolById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		NeProtocolDAO dao = new NeProtocolDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
