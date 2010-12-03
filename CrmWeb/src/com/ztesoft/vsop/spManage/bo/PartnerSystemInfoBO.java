package com.ztesoft.vsop.spManage.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.spManage.dao.PartnerSystemInfoDAO ;

public class PartnerSystemInfoBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchPartnerSystemInfoData �ķ����Ĳ�������
 		3. findPartnerSystemInfoByCond(String partner_system_info_id) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertPartnerSystemInfo(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PartnerSystemInfo = (Map) param.get("PartnerSystemInfo") ;
		PartnerSystemInfo.put("partner_system_info_id", SeqUtil.getInst().getNext("PARTNER_SYSTEM_SEQ"));
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		boolean result = dao.insert(PartnerSystemInfo) ;
		return result ;
	}

	
	public boolean updatePartnerSystemInfo(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PartnerSystemInfo = (Map) param.get("PartnerSystemInfo") ;
		String keyStr = "partner_system_info_id";
		Map keyCondMap  = Const.getMapForTargetStr(PartnerSystemInfo,  keyStr) ;
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		boolean result = dao.updateByKey( PartnerSystemInfo, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchPartnerSystemInfoData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "partner_id")){
			whereCond.append(" and partner_id = ? ");
			para.add(Const.getStrValue(param , "partner_id")) ;
		}	
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getPartnerSystemInfoById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findPartnerSystemInfoByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	
	public boolean deletePartnerSystemInfoById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
