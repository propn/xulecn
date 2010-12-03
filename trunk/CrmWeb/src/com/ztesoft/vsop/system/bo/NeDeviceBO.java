package com.ztesoft.vsop.system.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.command.dao.WoCmdCollectTypeDAO;
import com.ztesoft.vsop.system.dao.NeDeviceDAO ;

public class NeDeviceBO extends DictAction  {
	
	public boolean insertNeDevice(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeDevice = (Map) param.get("NeDevice") ;
		
		NeDeviceDAO dao = new NeDeviceDAO();
		boolean result = dao.insert(NeDevice) ;
		return result ;
	}

	
	public boolean updateNeDevice(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeDevice = (Map) param.get("NeDevice") ;
		String keyStr = "device_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeDevice,  keyStr) ;
		NeDeviceDAO dao = new NeDeviceDAO();
		boolean result = dao.updateByKey( NeDevice, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeDeviceData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param, "sys_code")){
			String code = Const.getStrValue(param, "sys_code");
			whereCond.append(" and (sys_code like '%"+code+"%' or sys_code like '%"+code.toUpperCase()+"%')");
		}if(Const.containStrValue(param, "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (name like '%"+name+"%' or name like '%"+name.toUpperCase()+"%')");
		}

		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeDeviceDAO dao = new NeDeviceDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeDeviceById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeDeviceDAO dao = new NeDeviceDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeDeviceByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeDeviceDAO dao = new NeDeviceDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	public List getAll(DynamicDict dto ) throws Exception {
		List para = new ArrayList();
		//调用DAO代码
		NeDeviceDAO dao = new NeDeviceDAO();
		List result = dao.findByCond( "", para) ;
		
		return result ;
	}

	
	public boolean deleteNeDeviceById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeDeviceDAO dao = new NeDeviceDAO();
		boolean delMapp=dao.deleteDispatchDevice(Const.getStrValue(keyCondMap, "device_id"));
		
		//级联删除该对端系统关联的指令模板集路由规则
		WoCmdCollectTypeDAO woCmdColTypeDao=new WoCmdCollectTypeDAO();
		String whereCond="and device_id=?";
		List list=new ArrayList();
		list.add(Const.getStrValue(keyCondMap, "device_id"));
		woCmdColTypeDao.delete(whereCond,list);

		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		if(delMapp && result)
			return true;
		return false ;
	}
	public boolean updateStateDevice(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		NeDeviceDAO dao = new NeDeviceDAO();
		boolean result = dao.updateStateDevice(Const.getStrValue(keyCondMap, "deviceId"),Const.getStrValue(keyCondMap, "state"));
		
		return result ;
	}
	public boolean changeRuleDevice(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		NeDeviceDAO dao = new NeDeviceDAO();
		boolean result = dao.changeRuleDevice(Const.getStrValue(keyCondMap, "deviceId"),Const.getStrValue(keyCondMap, "ruleId"));
		return result;
	}
	public boolean checkCodeDevice(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		NeDeviceDAO dao = new NeDeviceDAO();
		boolean result = dao.checkCodeDevice(Const.getStrValue(keyCondMap, "sysCode"));
		return result;
	}
}
