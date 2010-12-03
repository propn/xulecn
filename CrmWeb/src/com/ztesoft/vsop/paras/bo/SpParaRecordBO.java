package com.ztesoft.vsop.paras.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.paras.dao.SpParaRecordDAO;

public class SpParaRecordBO extends DictAction  {
	
	public boolean insertSpParaRecord(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaRecord = (Map) param.get("SpParaRecord") ;
		
		SpParaRecordDAO dao = new SpParaRecordDAO();
		boolean result = dao.insert(SpParaRecord) ;
		return result ;
	}

	
	public boolean updateSpParaRecord(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaRecord = (Map) param.get("SpParaRecord") ;
		String keyStr = "record_id";
		Map keyCondMap  = Const.getMapForTargetStr(SpParaRecord,  keyStr) ;
		SpParaRecordDAO dao = new SpParaRecordDAO();
		boolean result = dao.updateByKey( SpParaRecord, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpParaRecordData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "record_name")){
			String name = Const.getStrValue(param , "record_name");
			whereCond.append(" and (record_name like '%"+name+"%' or record_name like '%"+name.toUpperCase()+"%')");
		}
		if(Const.containStrValue(param , "record_path")){
			String path = Const.getStrValue(param , "record_path");
			whereCond.append(" and (record_path like '%"+path+"%' or record_path like '%"+path.toUpperCase()+"%')");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpParaRecordDAO dao = new SpParaRecordDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpParaRecordById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpParaRecordDAO dao = new SpParaRecordDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpParaRecordByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpParaRecordDAO dao = new SpParaRecordDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaRecordById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpParaRecordDAO dao = new SpParaRecordDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * 根据数据项记录标识在SP_ORDER_Type_RECORDS检查是否被工单类型引用
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateRecordId(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("record_id"));
		
		SpParaRecordDAO dao = new SpParaRecordDAO();
		List l = dao.findBySql(dao.getSQLForValidateRecordId(), list);
		if (null != l && !l.isEmpty()) {
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("order_type_name");
			return obj != null ? String.valueOf(obj) : "";
		}
		//如果直接返回null值(非字符串)的话，在js页面拿到的也是""这样的值，
		//与当order_type_name为""值时的结果一样，所以返回字符串"-1"，以便js判断，
		//弊端是万一order_type_name的值为"-1"，不过这种情况应该不会出现
		return "-1";
	}
	
	/**
	 * 数据项记录管理中添加目标数据项时验证目标数据项的唯一性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateCommandPara(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("record_id"));
		list.add(map.get("command_id"));
		SpParaRecordDAO dao = new SpParaRecordDAO();
		List l = dao.findBySql(dao.getSqlForValidateCommandPara(), list);
		if (null != l && !l.isEmpty()) {
			return false;//即已存在相同的目标数据项
		}
		return true;
	}
}
