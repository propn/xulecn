package com.ztesoft.vsop.order.manager.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.product.dao.ProductOffSyncLogDAO;

public class ProductOffSyncLogBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchProductOffSyncLogData 改方法的参数名称
 		3. findProductOffSyncLogByCond() 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProductOffSyncLog(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductOffSyncLog = (Map) param.get("ProductOffSyncLog") ;
		
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		boolean result = dao.insert(ProductOffSyncLog) ;
		return result ;
	}
	
	
	
	public boolean updateProductOffSyncLog(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductOffSyncLog = (Map) param.get("ProductOffSyncLog") ;
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		boolean result = dao.update( ProductOffSyncLog, (String) ProductOffSyncLog.get("id") );
		
		return result ;
	}
	
	
	public PageModel searchProductOffSyncLogData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "param1")){
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param , "param1")) ;
		}
		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductOffSyncLogById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductOffSyncLogByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductOffSyncLogById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	public String addProductOffSyncLog(DynamicDict dto ) throws FrameException{
		Map param = Const.getParam(dto) ;
		Map ProductOffSyncLog = (Map) param.get("ProductOffSyncLog") ;
		SequenceManageDAOImpl seqManage = new SequenceManageDAOImpl();
		String seq = seqManage.getNextSequence("seq_product_offer_sync_log_id");
		
		ProductOffSyncLogDAO dao = new ProductOffSyncLogDAO();
		String fileName = (String)ProductOffSyncLog.get("file_name");
		String procType = (String)ProductOffSyncLog.get("proc_type");
		String result = (String)ProductOffSyncLog.get("result");
		dao.insert(seq,fileName ,procType,result) ;
		return seq;
	}
}
