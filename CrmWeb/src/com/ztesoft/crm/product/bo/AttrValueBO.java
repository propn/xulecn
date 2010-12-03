package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.AttrValueDAO ;

public class AttrValueBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchAttrValueData 改方法的参数名称
 		3. findAttrValueByCond(String attr_value_id,String seq) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */

	public boolean insertAttrValue(DynamicDict dto  ) throws Exception {
		//错误参数处理
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "查询错误" ;

		Map param = Const.getParam(dto) ;
		Map AttrValue = (Map) param.get("AttrValue") ;
		String sql="select nvl(max(attr_value_id),0)+1 from attribute_value ";
		String attrValueId = Base.query_string( JNDINames.PM_DATASOURCE,sql,1);
		AttrValue.put("attr_value_id",attrValueId);

		AttrValueDAO dao = new AttrValueDAO();
		boolean result = dao.insert(AttrValue ,  ecode,  etype,  edesc) ;
		return result ;
	}


	public boolean updateAttrValue(DynamicDict dto ) throws Exception {
		//错误参数处理
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "查询错误" ;

		Map param = Const.getParam(dto) ;
		Map AttrValue = (Map) param.get("AttrValue") ;
		String keyStr = "attr_value_id,seq";
		Map keyCondMap  = Const.getMapForTargetStr(AttrValue,  keyStr) ;
		AttrValueDAO dao = new AttrValueDAO();
		boolean result = dao.updateByKey( AttrValue, keyCondMap ,
				etype ,  ecode , edesc);

		return result ;
	}


	public List searchAttrValueData(DynamicDict dto ) throws Exception {
		//错误参数处理

		//条件处理
		Map param = Const.getParam(dto) ;
		String whereCond = "" ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "attr_id")){
			whereCond = " and attr_id = ? ";
			para.add(Const.getStrValue(param , "attr_id")) ;
		}
		//调用DAO代码
		AttrValueDAO dao = new AttrValueDAO();
		List result = dao.findByCond(whereCond,para);

		return result ;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getAttrValueById(DynamicDict dto ) throws Exception {
		//错误参数处理
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "查询错误" ;

		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		AttrValueDAO dao = new AttrValueDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ,  etype ,  ecode , edesc ) ;

		return result ;
	}


	public List findAttrValueByCond(DynamicDict dto ) throws Exception {
		//错误参数处理
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "查询错误" ;

		//条件处理
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		AttrValueDAO dao = new AttrValueDAO();
		List result = dao.findByCond( whereCond, para ,
				  etype ,  ecode , edesc) ;

		return result ;
	}


	public boolean deleteAttrValueById(DynamicDict dto ) throws Exception {
		//错误参数处理
		int ecode = 1 ;
		int etype = 1 ;
		String edesc = "查询错误" ;

		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		AttrValueDAO dao = new AttrValueDAO();
		boolean result = dao.deleteByKey( keyCondMap ,  etype ,  ecode , edesc ) > 0 ;

		return result ;
	}
}
