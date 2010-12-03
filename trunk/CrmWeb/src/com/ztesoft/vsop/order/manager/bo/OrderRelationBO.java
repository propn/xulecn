package com.ztesoft.vsop.order.manager.bo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.exception.ProductHasNoPlatformException;
import com.ztesoft.vsop.order.manager.dao.OrderRelationDAO;
import com.ztesoft.vsop.order.vo.VproducInfo;
import com.ztesoft.vsop.order.vo.request.SubInstHisQueryRequest;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class OrderRelationBO extends DictAction {

	private static Logger logger = Logger.getLogger(OrderRelationBO.class);
	/**
	 * 订购关系查询统计
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchOrderRelationStaData(DynamicDict dto)
			throws Exception {

		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		// 存在产品主键序列串时
		if (Const.containStrValue(param, "product_ids")) {
			String[] ids = Const.getStrValue(param, "product_ids").split(",");
			whereCond.append(" and product_id in( ");
			for (int i = 0; i < ids.length; i++) {
				whereCond.append(" ? ");
				if ((i + 1) != ids.length) {
					whereCond.append(",");
				}
				para.add(ids[i]);
			}
			whereCond.append(" )");
			int pageSize = Const.getPageSize(param);
			int pageIndex = Const.getPageIndex(param);
			// 调用DAO代码
			OrderRelationDAO dao = new OrderRelationDAO();
			PageModel result = dao.orderRelationSta(whereCond.toString(), para,
					pageIndex, pageSize);
			return result;
			// 查找此SP所有对应的产品
		} else if (Const.containStrValue(param, "product_provider_id")) {
			String product_provider_id = Const.getStrValue(param,
					"product_provider_id");
			whereCond.append(" and p.product_provider_id=? ");
			para.add(product_provider_id);
			int pageSize = Const.getPageSize(param);
			int pageIndex = Const.getPageIndex(param);
			// 调用DAO代码
			OrderRelationDAO dao = new OrderRelationDAO();
			PageModel result = dao.orderRelationAllSta(whereCond.toString(),
					para, pageIndex, pageSize);
			return result;
		} else {
			return null;
		}

	}
/**
 * 为重构代码调用新引擎的增值页面订购BO
 * @param dto
 * @return
 */
	public Map orderProducts(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		String prodOfferNbrs = (String) param.get("prodOfferNbrs");
		String productNbr = (String) param.get("accNbr");
		String lanCode = (String) param.get("lanCode");
		String mProductId = (String) param.get("mProductId");
		String staffCode = (String) param.get("staffCode");
		String orderChannel = (String) param.get("orderChannel");
		Map result=new HashMap();
		
		// 请求服务
			try{
				 result= VariedServerEngine.getInstance().orderProducts(prodOfferNbrs,
							productNbr, lanCode, mProductId, staffCode, orderChannel);
				String resultCode=(String) result.get("ResultCode");
				String resultDesc=(String) result.get("ResultDesc");
				if(!"0".equals(resultCode)){
					Exception e=new Exception(resultDesc);
					throw e;
				}
				return result;
			}catch (Exception e) {
				throw e;
			}
	}

	public boolean insertOrderRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map OrderRelation = (Map) param.get("OrderRelation");

		OrderRelationDAO dao = new OrderRelationDAO();
		boolean result = dao.insert(OrderRelation);
		return result;
	}

	public boolean updateOrderRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map OrderRelation = (Map) param.get("OrderRelation");
		String keyStr = "order_relation_id";
		Map keyCondMap = Const.getMapForTargetStr(OrderRelation, keyStr);
		OrderRelationDAO dao = new OrderRelationDAO();
		boolean result = dao.updateByKey(OrderRelation, keyCondMap);

		return result;
	}

	public PageModel searchOrderRelationData(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "param1")) {
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param, "param1"));
		}
		if (Const.containStrValue(param, "param2")) {
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param, "param2"));
		}
		if (Const.containStrValue(param, "param3")) {
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param, "param3"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		OrderRelationDAO dao = new OrderRelationDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	public PageModel getOrderProductByCond(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		if (Const.containStrValue(param, "accNbr")) {
			whereCond.append(" and PRODUCT_NO = ? ");
			para.add(Const.getStrValue(param, "accNbr"));
		}

		if (Const.containStrValue(param, "lanCode")) {
			whereCond.append(" and LAN_ID = ? ");
			para.add(Const.getStrValue(param, "lanCode"));
		}

		if (Const.containStrValue(param, "mProductName")) {
			whereCond.append(" and M_PRODUCT_NAME = ? ");
			para.add(Const.getStrValue(param, "mProductName"));
		}

		if (Const.containStrValue(param, "mProductId")) {
			whereCond.append(" and M_PRODUCT_ID = ? ");
			para.add(Const.getStrValue(param, "mProductId"));
		}

		if (Const.containStrValue(param, "prodOfferId")) {
			String prodOfferId = Const.getStrValue(param, "prodOfferId");
			whereCond.append(" and (PPROD_OFFER_ID = " + prodOfferId
					+ " or PACKAGE_ID = " + prodOfferId
					+ " or PROD_OFFER_ID = " + prodOfferId + ") ");
		}
		
		
		/*
		if (Const.containStrValue(param, "state")) {
			String state = Const.getStrValue(param, "state");
			if (state.equals("!00X")) {
				whereCond.append(" and STATE <> '00X' ");
			} else {
				whereCond.append(" and STATE = '" + state + "' ");
			}
		}*/

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		OrderRelationDAO dao = new OrderRelationDAO();
		PageModel result = dao.getOrderProductByCond(whereCond.toString(),
				para, pageIndex, pageSize);
		return result;
	}

	
	public PageModel searchProductDataByNbr(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		if (Const.containStrValue(param, "prod_offer_name")) {
			whereCond.append(" and o.prod_offer_name like ? ");
			para.add("%" + Const.getStrValue(param, "prod_offer_name") + "%");
		}

		if (Const.containStrValue(param, "offer_nbr")) {
			whereCond.append(" and o.offer_nbr like ? ");
			para.add("%" + Const.getStrValue(param, "offer_nbr") + "%");
		}
		if(Const.containStrValue(param, "state")){
			whereCond.append(" and o.state = ?");
			para.add(Const.getStrValue(param, "state"));
		}
		String excludePlatforms = DcSystemParamManager.getParameter(VsopConstants.EXCLUDE_PLATFORMS);
		if(null != excludePlatforms && !"".equalsIgnoreCase(excludePlatforms)){
			String[] plats = excludePlatforms.split(",");
			for (int i = 0; i < plats.length; i++) {
				String plat = plats[i];
				whereCond.append(" and package_host <> ? ");
				para.add(plat);
			}
		}
		// TODO: 互斥判断
		// if(Const.containStrValue(param , "accNbr")){
		// whereCond.append(" and o.prod_offer_id not in(select distinct
		// t.prod_offer_id from ORDER_RELATION t, PROD_INST i, product p ");
		// whereCond.append(" where i.PROD_INST_ID = t.prod_inst_id and
		// p.product_id = t.product_id and i.ACC_NBR = ? ");
		// whereCond.append(" and t.STATE != '00X' and t.prod_offer_id is not
		// null ");
		// para.add(Const.getStrValue(param , "accNbr")) ;
		// if(Const.containStrValue(param , "lanCode")){
		// whereCond.append(" and i.LAN_ID = ? ");
		// para.add(Const.getStrValue(param , "lanCode")) ;
		// }
		// if(Const.containStrValue(param , "mProductName")){
		// whereCond.append(" and i.PRODUCT_id = ? ");
		// para.add(Const.getStrValue(param , "mProductName")) ;
		// }
		// whereCond.append(" ) ");
		// }

		// TODO: 互斥判断
		// if (Const.containStrValue(param, "proOfferIds")) {
		// String product_ids = Const.getStrValue(param , "proOfferIds");
		// whereCond.append(" and o.prod_offer_id not in ("+product_ids+") ");
		// }

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		ProdOffDAO dao = new ProdOffDAO();
		PageModel result = dao.getProdOfferByNbr(whereCond.toString(), para,
				param, pageIndex, pageSize);

		return result;

	}

	public PageModel getOrderHistory(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "accNbr")) {
			whereCond.append(" and ACC_NBR = ? ");
			para.add(Const.getStrValue(param, "accNbr"));
		}
		if (Const.containStrValue(param, "lanCode")) {
			whereCond.append(" and LAN_ID = ? ");
			para.add(Const.getStrValue(param, "lanCode"));
		}
		if (Const.containStrValue(param, "mProductName")) {
			whereCond.append(" and M_PRODUCT_NAME = ? ");
			para.add(Const.getStrValue(param, "mProductName"));
		}
		if (Const.containStrValue(param, "state")) {
			whereCond.append(" and STATE = ? ");
			para.add(Const.getStrValue(param, "state"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		OrderRelationDAO dao = new OrderRelationDAO();
		PageModel result = dao.getOrderHistory(whereCond.toString(), para,
				pageIndex, pageSize);
		return result;
	}

	public PageModel getOrderHistoryByProduct(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		// if(Const.containStrValue(param , "accNbr")){
		// whereCond.append(" and ACC_NBR = ? ");
		// para.add(Const.getStrValue(param , "accNbr")) ;
		// }
		// if(Const.containStrValue(param , "product_Id")){
		// whereCond.append(" and PRODUCT_ID = ? ");
		// para.add(Const.getStrValue(param , "product_Id")) ;
		// }

		if (Const.containStrValue(param, "prod_inst_id")) {
			whereCond.append(" and prod_inst_id = ? ");
			para.add(Const.getStrValue(param, "prod_inst_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		OrderRelationDAO dao = new OrderRelationDAO();
		PageModel result = dao.getOrderHistoryByProduct(whereCond.toString(),
				para, pageIndex, pageSize);
		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getOrderRelationById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		OrderRelationDAO dao = new OrderRelationDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findOrderRelationByCond(DynamicDict dto) throws Exception {
		// 条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// 组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// 调用DAO代码
		OrderRelationDAO dao = new OrderRelationDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteOrderRelationById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		OrderRelationDAO dao = new OrderRelationDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	/**
	 * 只查询有效的订购关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel getOffersByCond(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		whereCond.append("and state =?");
		para.add("00A");
		if (Const.containStrValue(param, "accNbr")) {
			whereCond.append(" and ACC_NBR = ? ");
			para.add(Const.getStrValue(param, "accNbr"));
		}
		if (Const.containStrValue(param, "lanCode")) {
			whereCond.append(" and LAN_ID = ? ");
			para.add(Const.getStrValue(param, "lanCode"));
		}
		if (Const.containStrValue(param, "mProductId")) {
			whereCond.append(" and prod_type_cd = ? ");
			para.add(Const.getStrValue(param, "mProductId"));
		}
		if (Const.containStrValue(param, "activeState")) {
			whereCond.append(" and active_state = ? ");
			para.add(Const.getStrValue(param, "activeState"));
		}
		/*
		if (Const.containStrValue(param, "activeState")) {
			String activeState = Const.getStrValue(param, "activeState");
			activeState = "'" + activeState.replaceAll(",", "','") + "'";
			whereCond.append(" and active_state in (" + activeState + ") ");
		}*/
		
		OrderRelationDAO dao = new OrderRelationDAO();
		ArrayList list = dao.getOffersByCond(whereCond.toString(), para);

		if (list == null || list.isEmpty()) {
			return new PageModel();
		}

		return _getOffersByCond(list);
	}

	/**
	 * 对增值销售品结果集进行处理.
	 * 
	 * @param results
	 * @return
	 */
	private PageModel _getOffersByCond(List results) {
		// 对结果集进行合并处理,将产品维度转换成销售品维度
		Map aMap = new LinkedHashMap(); // PROD_OFFER_ID 虚拟销售品,类型为0
		Map bMap = new LinkedHashMap(); // PACKAGE_ID 纯增值销售品,类型为1
		Map cMap = new LinkedHashMap(); // PPROD_OFFER_ID 捆绑销售品(传统+增值),类型为2

		String prod_offer_id = "";
		String pprod_offer_id = "";
		String package_id = "";

		// 每个产品对应一条ORDER_RELATION记录,这里合并OR记录时必须把对应销售品的OR记录的ORDER_RELATION_ID合并成一个字符串
		Map oMap = new HashMap();
		String order_relation_id = "";

		for (int i = 0; i < results.size(); i++) {
			Map each = (Map) results.get(i);

			order_relation_id = (String) each.get("order_relation_id");
			prod_offer_id = (String) each.get("prod_offer_id");
			pprod_offer_id = (String) each.get("pprod_offer_id");
			package_id = (String) each.get("package_id");

			// 传统加增值捆绑销售品优先级第一
			if (!pprod_offer_id.equals("")) {
				if (!cMap.containsKey(pprod_offer_id)) {
					cMap.put(pprod_offer_id, each);
				}

				if (!oMap.containsKey(pprod_offer_id)) {
					oMap.put(pprod_offer_id, order_relation_id);
				} else {
					oMap.put(pprod_offer_id, oMap.get(pprod_offer_id) + ","
							+ order_relation_id);
				}
			}

			// 纯增值捆绑销售品优先级第二
			if (!package_id.equals("")) {
				if (!bMap.containsKey(package_id)) {
					if (!cMap.containsKey(pprod_offer_id)) {
						bMap.put(package_id, each);
					}
				}

				if (!oMap.containsKey(pprod_offer_id)
						&& !oMap.containsKey(package_id)) {
					oMap.put(package_id, order_relation_id);
				} else if (oMap.containsKey(package_id)) {
					oMap.put(package_id, oMap.get(package_id) + ","
							+ order_relation_id);
				}
			}

			// 单增值销售品优先级最低
			if (!prod_offer_id.equals("")) {
				if (!aMap.containsKey(prod_offer_id)) {
					if (!bMap.containsKey(package_id)
							&& !cMap.containsKey(pprod_offer_id)) {
						aMap.put(prod_offer_id, each);
					}
				}

				if (!oMap.containsKey(pprod_offer_id)
						&& !oMap.containsKey(package_id)
						&& !oMap.containsKey(prod_offer_id)) {
					oMap.put(prod_offer_id, order_relation_id);
				}
			}
		}

		// 重新构造PageModel
		List vList = new ArrayList();
		Set aset = aMap.keySet();
		for (Iterator aitr = aset.iterator(); aitr.hasNext();) {
			Object akey = (Object) aitr.next();
			Object a = aMap.get(akey);
			vList.add(a);
		}

		Set bset = bMap.keySet();
		for (Iterator bitr = bset.iterator(); bitr.hasNext();) {
			Object bkey = (Object) bitr.next();
			Object b = bMap.get(bkey);
			vList.add(b);
		}

		Set cset = cMap.keySet();
		for (Iterator iterator = cset.iterator(); iterator.hasNext();) {
			Object ckey = (Object) iterator.next();
			Object c = cMap.get(ckey);
			vList.add(c);
		}

		String poid = "";
		String poname = "";
		List newVList = new ArrayList();
		for (int i = 0; i < vList.size(); i++) {
			Map each = (Map) vList.get(i);
			prod_offer_id = (String) each.get("prod_offer_id");
			pprod_offer_id = (String) each.get("pprod_offer_id");
			package_id = (String) each.get("package_id");

			if (!pprod_offer_id.equals("")) {
				poid = pprod_offer_id;
				poname = (String) each.get("pprod_offer_name");
			} else if (!package_id.equals("")) {
				poid = package_id;
				poname = (String) each.get("package_name");
			} else if (!prod_offer_id.equals("")) {
				poid = prod_offer_id;
				poname = (String) each.get("prod_offer_name");
			}

			Map newEach = new HashMap();
			newEach.put("combin_or_ids", oMap.get(poid));
			newEach.put("acc_nbr", each.get("acc_nbr"));
			newEach.put("lan_id", each.get("lan_id"));
			newEach.put("product_id", each.get("product_id"));
			newEach.put("m_product_name", each.get("m_product_name"));
			newEach.put("poid", poid);
			newEach.put("poname", poname);
			newVList.add(newEach);
		}

		PageModel pageModel = new PageModel();
		pageModel.setPageCount(1);
		pageModel.setPageIndex(1);
		pageModel.setPageSize(1000);
		pageModel.setTotalCount(newVList.size());
		pageModel.setList(newVList);

		return pageModel;
	}
	/**
	 * 退订选择的增值产品
	 * 
	 * @param relationIds
	 * @throws SQLException
	 * @throws ProductHasNoPlatformException
	 */
	public Map delOrdersByRelationId(DynamicDict dto) {
		Map ret = new HashMap();
		try {
			Map map = Const.getParam(dto);
			String relationIds = (String)map.get("relationIds");
			String staff =(String)map.get("staff");
			String orderChannel=(String)map.get("orderChannel");
			if(null == orderChannel || "".equals(orderChannel)) orderChannel = SystemCode.VSOP;
			ret = VariedServerEngine.getInstance().delOrdersByRelationId(relationIds,staff,orderChannel);
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
			logger.info("#delOrdersByRelationId ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}

	/**
	 * 全部退订选择的增值产品
	 * 
	 * @param relationIds
	 * @throws SQLException
	 * @throws ProductHasNoPlatformException
	 */
	public Map delOrdersByAccNbr(DynamicDict dto) {
		Map ret = new HashMap();
		try {
			Map map = Const.getParam(dto);
			String accNbr = (String)map.get("accNbr");
			String staff=(String)map.get("staff");
			String orderChannel=(String)map.get("orderChannel");
			if( null == orderChannel || "".equalsIgnoreCase(orderChannel)) orderChannel = SystemCode.VSOP;
			OrderRelationHelpDao orderRelationHelpDao = new OrderRelationHelpDao();
			List relations = orderRelationHelpDao.qryORSByAccNbr(accNbr);
			Iterator iterator = relations.iterator();
			String relationIds = "";
			
			while(iterator.hasNext()){
				OrderRelationVO vo = (OrderRelationVO)iterator.next();
				relationIds = relationIds+","+vo.getOrderRelationId();
			}
			if(!"".equals(relationIds)){
				String str = relationIds.substring(1);
				ret = VariedServerEngine.getInstance().delOrdersByRelationId(relationIds.substring(1),staff,orderChannel);	
			}
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
			logger.info("#delOrdersByRelationId ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}
	
	/**
	 * 页面-订购历史查询，(江西新增订购历史 供crm调用)
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	
	public PageModel getOrderProductHisByCond(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		String startTime="";
		String endTime="";
		String activeType="";
		String accNbr="";
		String stateCode="";
		if (Const.containStrValue(param, "startTime")) {
			 startTime=Const.getStrValue(param, "startTime");
		}
		if (Const.containStrValue(param, "endTime")) {
			endTime=Const.getStrValue(param, "endTime");
		}
		if (Const.containStrValue(param, "activeType")) {
			activeType=Const.getStrValue(param, "activeType");
		}
		if (Const.containStrValue(param, "accNbr")) {
			accNbr=Const.getStrValue(param, "accNbr");
		}
		if (Const.containStrValue(param, "stateCode")) {
			stateCode=Const.getStrValue(param, "stateCode");
		}
		//每页的记录行数
		int pageSize = Const.getPageSize(param);
		//当前页索引
		int pageIndex = Const.getPageIndex(param);
		
		ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
	
		ProdInstVO prodInstvo=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, Const.getStrValue(param, "mProductId"),false);
		String prodInstId=prodInstvo.getProdInstId();

		SubInstHisQueryRequest subInstQueryVo = new SubInstHisQueryRequest();
			subInstQueryVo.setProdInstId(prodInstId);
			subInstQueryVo.setBeginTime(startTime);
			subInstQueryVo.setEndTime(endTime);
		
		OrderDao orderDao = new OrderDao();
		List products = orderDao.subscribeHisQry(subInstQueryVo,OrderConstant.orderStateOfCreated);
		List totalList=new ArrayList();
		
		PageModel result = new PageModel();
		if("".equals(activeType)||null==activeType){//订购动作送空 取全部值
			totalList=getProductListByStateCode(products,accNbr,stateCode);//加入手机号
		}else{
			totalList=getProductListByActive(products,activeType,accNbr,stateCode);//过滤，加入手机号
		}
		
		int statrIndex=(pageIndex-1)*pageSize;//开始记录
		int endIndex=pageIndex*pageSize;//结束记录
		int pageCount=0;
		if(null!=totalList){
			pageCount=totalList.size()/pageSize+1;//总页数
		}
		int totalCount=totalList.size();//总记录数
		if(endIndex>=totalCount-1){
			endIndex=totalCount;
		}
		
		if(null!=totalList&&totalList.size()>0){//记录为空不处理
			List pls = totalList.subList(statrIndex, endIndex);//分页当前历史	
			result.setList(pls);
			result.setPageCount(pageCount);
			result.setPageIndex(pageIndex);
			result.setTotalCount(totalCount);
			result.setPageSize(pageSize);
		}
		return result;
	}
	
	/**
	 * 设置号码,订购时间格式
	 * @param products
	 * @param activeType
	 * @return
	 */
	private  List getProductListByStateCode(List products,String accNbr,String stateCode){
		List resultList=new ArrayList();
		
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			VproducInfo vProducInfo = (VproducInfo) iterator.next();
				vProducInfo.setAccNbr(accNbr);
				//转换时间格式
				Timestamp stamp=DAOUtils.parseTimestamp(vProducInfo.getSubscribeTime(),CrmConstants.DATE_TIME_FORMAT_14);
				String time=DAOUtils.getFormatedDateTime(stamp);
				vProducInfo.setSubscribeTime(time);
				vProducInfo.setChannel_id(vProducInfo.getChannelPlayerID());
				String code=vProducInfo.getState_code();
				if(vProducInfo.FINISH.equals(code)||vProducInfo.MANUAL_FEEDBACK_SUCC.equals(code)){
					vProducInfo.setState_code("1");//处理成功
				}else if(vProducInfo.INIT.equals(code)||vProducInfo.EXECUTING.equals(code)){
					vProducInfo.setState_code("0");//处理中
				}else{
					vProducInfo.setState_code("2");//处理失败
				}
				if ("".equals(stateCode)||null==stateCode){
					resultList.add(vProducInfo);
				}else{
					if (vProducInfo.getState_code().equals(stateCode)){
						resultList.add(vProducInfo);
					}
				}
		}
		return resultList;
	}
	/**
	 * 根据订购动作，过滤并设置号码,订购时间格式
	 * @param products
	 * @param activeType
	 * @return
	 */
	private  List getProductListByActive(List products, String activeType,String accNbr,String stateCode){
		DateFormatUtils dateFormatUtils=new DateFormatUtils();
		List resultList=new ArrayList();
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			VproducInfo vProducInfo = (VproducInfo) iterator.next();
			String vPactiveType = vProducInfo.getActionType();
			if(activeType.equals(vPactiveType)){
				vProducInfo.setAccNbr(accNbr);
				//转换时间格式
				Timestamp stamp=DAOUtils.parseTimestamp(vProducInfo.getSubscribeTime(),CrmConstants.DATE_TIME_FORMAT_14);
				String time=DAOUtils.getFormatedDateTime(stamp);
				vProducInfo.setSubscribeTime(time);
				vProducInfo.setChannel_id(vProducInfo.getChannelPlayerID());
				
				String code=vProducInfo.getState_code();
				
				if(vProducInfo.FINISH.equals(code)||vProducInfo.MANUAL_FEEDBACK_SUCC.equals(code)){
					vProducInfo.setState_code("1");//处理成功
				}else if(vProducInfo.INIT.equals(code)||code.equals(vProducInfo.EXECUTING)){
					vProducInfo.setState_code("0");//处理中
				}else{
					vProducInfo.setState_code("2");//处理失败
				}
				if ("".equals(stateCode)||null==stateCode){
					resultList.add(vProducInfo);
				}else{
					if (vProducInfo.getState_code().equals(stateCode)){
						resultList.add(vProducInfo);
					}
				}
			}
		}
		return resultList;
	}

}
