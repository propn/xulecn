package com.ztesoft.vsop.dispatchrule.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.dispatchrule.dao.WoDispatchDeviceDAO ;

public class WoDispatchDeviceBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchWoDispatchDeviceData �ķ����Ĳ�������
 		3. findWoDispatchDeviceByCond() ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertWoDispatchDevice(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoDispatchDevice = (Map) param.get("WoDispatchDevice") ;
		
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		boolean result = dao.insert(WoDispatchDevice) ;
		return result ;
	}

	
	public boolean updateWoDispatchDevice(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoDispatchDevice = (Map) param.get("WoDispatchDevice") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(WoDispatchDevice,  keyStr) ;
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		boolean result = dao.updateByKey( WoDispatchDevice, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoDispatchDeviceData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "dispatch_rule_id")){
			whereCond.append(" and d.dispatch_rule_id = ? ");
			para.add(Const.getStrValue(param , "dispatch_rule_id")) ;
		}

		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getWoDispatchDeviceById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoDispatchDeviceByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchDeviceById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
