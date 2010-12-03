package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ServRelationGrpDAO;
import com.ztesoft.crm.product.dao.TpmCardAttrControlDAO ;

public class TpmCardAttrControlBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchTpmCardAttrControlData 改方法的参数名称
 		3. findTpmCardAttrControlByCond(String service_offer_id,String action_no,String object_type,String object_id,String field_name) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */

	public boolean saveTpmCardAttrControl(DynamicDict dto  ) throws Exception {
		 Map param = Const.getParam(dto);
		 TpmCardAttrControlDAO dao = new TpmCardAttrControlDAO();
		 List cardList = (ArrayList)param.get("cardList");

		 for(int i=0;i<cardList.size();i++){
			 Map temp = (HashMap)cardList.get(i);
			 temp.put("action_no","0");
			 dao.deleteByKey(temp);
			 dao.insert(temp) ;
		 }
		 return true;
	}


	public boolean updateTpmCardAttrControl(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map TpmCardAttrControl = (Map) param.get("TpmCardAttrControl") ;
		String keyStr = "service_offer_id,action_no,object_type,object_id,field_name";
		Map keyCondMap  = Const.getMapForTargetStr(TpmCardAttrControl,  keyStr) ;
		TpmCardAttrControlDAO dao = new TpmCardAttrControlDAO();
		boolean result = dao.updateByKey( TpmCardAttrControl, keyCondMap );

		return result ;
	}


	public List searchTpmCardAttrControlData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		para.add(Const.getStrValue(param,"service_offer_id"));
		String sql = "select service_offer_id,object_type,object_id,field_name,is_visible,is_readonly from TPM_CARD_ATTR_CONTROL where service_offer_id=?";
		TpmCardAttrControlDAO dao = new TpmCardAttrControlDAO();
		List result = dao.findBySql(sql,para);
		return result ;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getTpmCardAttrControlById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		TpmCardAttrControlDAO dao = new TpmCardAttrControlDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;

		return result ;
	}


	public List findTpmCardAttrControlByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ;
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		TpmCardAttrControlDAO dao = new TpmCardAttrControlDAO();
		List result = dao.findByCond( whereCond, para) ;

		return result ;
	}


	public boolean deleteTpmCardAttrControlById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		TpmCardAttrControlDAO dao = new TpmCardAttrControlDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
}
