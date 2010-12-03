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
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchSiCapabilityData �ķ����Ĳ�������
 		3. findSiCapabilityByCond(String id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
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
		//��������
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
		
		
		//����DAO����
		SiCapabilityDAO dao = new SiCapabilityDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getSiCapabilityById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		SiCapabilityDAO dao = new SiCapabilityDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSiCapabilityByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		SiCapabilityDAO dao = new SiCapabilityDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSiCapabilityById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		SiCapabilityDAO dao = new SiCapabilityDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
