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
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchPartnerSystemInfoData 改方法的参数名称
 		3. findPartnerSystemInfoByCond(String partner_system_info_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
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
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "partner_id")){
			whereCond.append(" and partner_id = ? ");
			para.add(Const.getStrValue(param , "partner_id")) ;
		}	
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getPartnerSystemInfoById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findPartnerSystemInfoByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	
	public boolean deletePartnerSystemInfoById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		PartnerSystemInfoDAO dao = new PartnerSystemInfoDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
