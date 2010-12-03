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
import com.ztesoft.vsop.paras.dao.NeCommandParaDAO;

public class NeCommandParaBO extends DictAction  {

	
	public boolean insertNeCommandPara(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandPara = (Map) param.get("NeCommandPara") ;
		
		NeCommandParaDAO dao = new NeCommandParaDAO();
		boolean result = dao.insert(NeCommandPara) ;
		return result ;
	}

	
	public boolean updateNeCommandPara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandPara = (Map) param.get("NeCommandPara") ;
		String keyStr = "command_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeCommandPara,  keyStr) ;
		NeCommandParaDAO dao = new NeCommandParaDAO();
		boolean result = dao.updateByKey( NeCommandPara, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCommandParaData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (p.name like '%"+name+"%' or p.name like '%"+name+"%' )");
		}
		if(Const.containStrValue(param , "para_code")){
			String code = Const.getStrValue(param, "para_code");
			whereCond.append(" and (para_code like '%"+code+"%' or para_code like '%"+code+"%')");
		}
		if(Const.containStrValue(param , "command_catalog_id")){
			whereCond.append(" and p.command_catalog_id = ? ");
			para.add(Const.getStrValue(param , "command_catalog_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeCommandParaDAO dao = new NeCommandParaDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeCommandParaById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeCommandParaDAO dao = new NeCommandParaDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCommandParaByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandParaById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommandParaDAO dao = new NeCommandParaDAO();
			boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
			return result ;
	}
	
	/**
	 * 删除目标数据项时检查是否被数据项记录引用
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateForParaRecord(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_id"));
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List l = dao.findBySql(dao.getSqlForValidateParaRecord(), list);
		if (null != l && !l.isEmpty()) {//所删除的目标数据项有被数据项记录所引用
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("record_name");//有引用则返回对应数据项记录的名称，以在js端提示用户时显示
			return obj != null ? String.valueOf(obj) : "";
		}
		return "-1";
	}
	
	/**
	 * 删除目标数据项时检查是否被工单类型引用
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateForOrderType(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_id"));
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List l = dao.findBySql(dao.getSqlForValidateOrderType(), list);
		if (null != l && !l.isEmpty()) {//所删除的目标数据项有被工单类型所引用
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("order_type_name");//有引用则返回对应工单类型的名称，以在js端提示用户时显示
			return obj != null ? String.valueOf(obj) : "";
		}
		return "-1";
	}
	
	/**
	 * 删除目标数据项时检查是否被指令模板数据项引用
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String validateForTemplatePara(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_id"));
		NeCommandParaDAO dao = new NeCommandParaDAO();
		List l = dao.findBySql(dao.getSQL_VALIDATE_CMD_TEMPLATE(), list);
		if (null != l && !l.isEmpty()) {//所删除的目标数据项有被指令模板数据项所引用
			HashMap hm = (HashMap) l.get(0);
			Object obj = hm.get("template_name");//有引用则返回对应指令模板的名称，以在js端提示用户时显示
			return obj != null ? String.valueOf(obj) : "";
		}
		return "-1";
	}
	
	/**
	 * 验证编码的唯一性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateParaCode(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("command_catalog_id"));
		list.add(map.get("para_code"));
		
		NeCommandParaDAO dao = new NeCommandParaDAO();
		String SQL_Query=dao.getSqlForValidateParaCode();
		if(map.get("command_id")!=null&&!"".equals(map.get("command_id").toString())){//修改操作时，commmand_id不为空，所以判断编码唯一性时，不与自己比较
			SQL_Query+=" and command_id!=?";
			list.add(map.get("command_id").toString());
		}
		List l = dao.findBySql(SQL_Query, list);
		if (null != l && !l.isEmpty()) {
			return false;
		}
		return true;
	}
}
