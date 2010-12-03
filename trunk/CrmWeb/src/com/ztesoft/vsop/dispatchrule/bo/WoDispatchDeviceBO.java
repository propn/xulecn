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
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchWoDispatchDeviceData 改方法的参数名称
 		3. findWoDispatchDeviceByCond() 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
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
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "dispatch_rule_id")){
			whereCond.append(" and d.dispatch_rule_id = ? ");
			para.add(Const.getStrValue(param , "dispatch_rule_id")) ;
		}

		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoDispatchDeviceById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoDispatchDeviceByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchDeviceById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoDispatchDeviceDAO dao = new WoDispatchDeviceDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
