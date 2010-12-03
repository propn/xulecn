package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderFeebackHisDAO ;

public class WoNeOrderFeebackHisBO extends DictAction  {
	public boolean insertWoNeOrderFeebackHis(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderFeebackHis = (Map) param.get("WoNeOrderFeebackHis") ;
		
		WoNeOrderFeebackHisDAO dao = new WoNeOrderFeebackHisDAO();
		boolean result = dao.insert(WoNeOrderFeebackHis) ;
		return result ;
	}

	
	public boolean updateWoNeOrderFeebackHis(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderFeebackHis = (Map) param.get("WoNeOrderFeebackHis") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(WoNeOrderFeebackHis,  keyStr) ;
		WoNeOrderFeebackHisDAO dao = new WoNeOrderFeebackHisDAO();
		boolean result = dao.updateByKey( WoNeOrderFeebackHis, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoNeOrderFeebackHisData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "ne_order_id")){
			whereCond.append(" and ne_order_id = ? ");
			para.add(Const.getStrValue(param , "ne_order_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		WoNeOrderFeebackHisDAO dao = new WoNeOrderFeebackHisDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getWoNeOrderFeebackHisById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderFeebackHisDAO dao = new WoNeOrderFeebackHisDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoNeOrderFeebackHisByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		WoNeOrderFeebackHisDAO dao = new WoNeOrderFeebackHisDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoNeOrderFeebackHisById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		WoNeOrderFeebackHisDAO dao = new WoNeOrderFeebackHisDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
