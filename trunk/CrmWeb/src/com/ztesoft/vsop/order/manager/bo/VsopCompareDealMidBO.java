package com.ztesoft.vsop.order.manager.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;

import com.ztesoft.vsop.order.dao.SqlUtil;
import com.ztesoft.vsop.order.dao.VsopCompareDealMidDAO ;
import com.ztesoft.vsop.order.manager.dao.OrderRelationDAO;

public class VsopCompareDealMidBO extends DictAction  {
	private static Logger logger = Logger.getLogger(VsopCompareDealMidBO.class);
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchVsopCompareDealMidData 改方法的参数名称
 		3. findVsopCompareDealMidByCond() 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertVsopCompareDealMid(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map VsopCompareDealMid = (Map) param.get("VsopCompareDealMid") ;
		
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		boolean result = dao.insert(VsopCompareDealMid) ;
		return result ;
	}

	
	public boolean updateVsopCompareDealMid(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map VsopCompareDealMid = (Map) param.get("VsopCompareDealMid") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(VsopCompareDealMid,  keyStr) ;
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		boolean result = dao.updateByKey( VsopCompareDealMid, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchVsopCompareDealMidData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "oa")){
			whereCond.append(" and oa = ? ");
			para.add(Const.getStrValue(param , "oa")) ;
		}
		if(Const.containStrValue(param , "oaType")){
			whereCond.append(" and oa_type = ? ");
			para.add(Const.getStrValue(param , "oaType")) ;
		}
		if(Const.containStrValue(param , "status")){
			whereCond.append(" and status = ? ");
			para.add(Const.getStrValue(param , "status")) ;
		}
		if(Const.containStrValue(param , "fileName")){
			whereCond.append(" and file_name like '%'|| ? || '%' ");
			para.add(Const.getStrValue(param , "fileName")) ;
		}
		if(Const.containStrValue(param , "isAdjust")){
			whereCond.append(" and is_adjust = ? ");
			para.add(Const.getStrValue(param , "isAdjust")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getVsopCompareDealMidById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findVsopCompareDealMidByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteVsopCompareDealMidById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	/**
	 * 批量手工调整订购关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public int batchAdjustOrder(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto);
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		//0:VSOP侧多出的订购关系，1：VSOP侧缺少的订购关系，2：差异订购关系
		String flag = (String)param.get("flag");
		OrderRelationDAO dao = new OrderRelationDAO();
		boolean target = false;
		String result = dao.isOrderRelaChange(prodType, accNbr, productId);
		if("".equals(result))
			result ="0";
		double num = Double.parseDouble(result);
		//当前要手工调整的订购关系在当日已经又被调整过
		if(num < 0 ){
			updateAjustState(param);
			return 1;
		}
		String reason = "";//归档时操作原因
		String disposalResult="";//归档时操作原因
		if("0".equals(flag)){
			target = delOrderRelation(param);
			reason = "VSOP比对多余订购关系撤单";
			disposalResult = "撤单成功";
		}else if("1".equals(flag)){
			target = addOrderRelation(param);
			reason = "VSOP比对缺少的订购关系增补";
			disposalResult = "增补成功";
		}else{
			target = adjustOrderRelation(param);
			reason = "VSOP比对差异订购关系更新";
			disposalResult = "更新成功";
		}
		if(!target)return 2;
		//根据号码和产品类型得到产品实例id和lan_id
		OrderRelationDAO orderRelationDao = new OrderRelationDAO();
		String[] prodInstIdStr = orderRelationDao.getIdTypeStr(accNbr,prodType).split(",");
		param.put("prod_inst_id", prodInstIdStr[0]);
		param.put("lan_id", prodInstIdStr[2]);
		//归档
		//订单归档
		String custOrderId = SqlUtil.getInstance().getSequence("SEQ_ORDER_INFO_ID");
		addCustomerHis(param,custOrderId,reason,disposalResult);
		//订单项归档
		addOrderItemHis(param,custOrderId,reason,disposalResult);
		//插入比对日志表
		insertOrderLog(param);
		//删除比对表记录  应该最后删除比对记录
		delOrderComMid(param);
		return 0;
	}

	/**
	 * 增补订购关系
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean addOrderRelation(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.addOrderRelation(prodType, accNbr, productId);
		
	}
	/**
	 * 删除订购关系
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean delOrderRelation(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.delOrderRelation(prodType, accNbr, productId);
		
	}
	/**
	 * 更新订购关系
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean adjustOrderRelation(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String packageId = (String)param.get("packageId");
		String pprodOfferId = (String)param.get("pprodOfferId");
		String status = (String)param.get("status");
		String productId = (String)param.get("productId");
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.adjustOrderRelation(prodType, accNbr, packageId, pprodOfferId, status, productId);
		
	}
	/**
	 * 订单归档
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean addCustomerHis(Map param,
								   String custOrderId, 
								   String reason,
								   String disposalResult)throws Exception {
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.addCustomerHis(param, custOrderId, reason, disposalResult);
		
	}
	/**
	 * 订单项归档
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean addOrderItemHis(Map param,
								   String custOrderId, 
								   String reason,
								   String disposalResult)throws Exception {
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.addOrderItemHis(param, custOrderId, reason, disposalResult);
		
	}
	/**
	 * 手工调整订购关系日志
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean insertOrderLog(Map param)throws Exception {
		OrderRelationDAO dao = new OrderRelationDAO();
		return dao.insertOrderLog(param);
		
	}
	/**
	 * 删除比对中间表已执行手工调整的记录
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean delOrderComMid(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		return dao.delOrderComMid(prodType, accNbr, productId);
		
	}
	/**
	 * 更新手工调整状态
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private boolean updateAjustState(Map param)throws Exception {
		String prodType = (String)param.get("oaType");
		String accNbr = (String)param.get("oa");
		String productId = (String)param.get("productId");
		VsopCompareDealMidDAO dao = new VsopCompareDealMidDAO();
		return dao.updateOrderComMid(prodType, accNbr, productId);
		
	}
}
