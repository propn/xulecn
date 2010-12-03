package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgDAO;
import com.ztesoft.vsop.ordermonitor.dao.WoQueueOrderDAO ;

public class WoQueueOrderBO extends DictAction  {
	public boolean updateWoQueueOrder(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoQueueOrder = (Map) param.get("WoQueueOrder") ;
		String keyStr = "ne_order_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoQueueOrder,  keyStr) ;
		WoQueueOrderDAO dao = new WoQueueOrderDAO();
		boolean result = dao.updateByKey( WoQueueOrder, keyCondMap );
		
		return result ;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoQueueOrderById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoQueueOrderDAO dao = new WoQueueOrderDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
}
