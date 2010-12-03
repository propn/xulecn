package com.ztesoft.vsop.order.manager.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.order.manager.dao.BatchImportRelationDAO ;

public class BatchImportRelationBO extends DictAction  {
	public PageModel searchBatchImportRelationData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "operator")){
			whereCond.append(" and t.operator = ? ");
			para.add(Const.getStrValue(param , "operator")) ;
		}
		if(Const.containStrValue(param , "batch")){
			whereCond.append(" and t.batch = ? ");
			para.add(Const.getStrValue(param , "batch")) ;
		}
		if(Const.containStrValue(param , "state")){
			whereCond.append(" and t.state = ? ");
			para.add(Const.getStrValue(param , "state")) ;
		}
		//whereCond.append(" order by t.id desc ");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		BatchImportRelationDAO dao = new BatchImportRelationDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
}
