package com.ztesoft.vsop.order.manager.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import org.apache.log4j.Logger;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.DesEncrypt;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.SystemCode;

public class OrderRelationService {

	private static Logger logger = Logger.getLogger(OrderRelationService.class);

	public PageModel orderRelationSta(String product_ids,
			String product_provider_id, String partner_name, int pageIndex,
			int pageSize) throws Exception {

		Map param = new HashMap();
		param.put("product_ids", product_ids);
		param.put("product_provider_id", product_provider_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"searchOrderRelationStaData", param));
		// �Ѵ�������partner_name,ֱ�Ӽӻص�pageModel�����sql����
		List rel = result.getList();
		Map rem = null;
		for (int i = 0; i < rel.size(); i++) {
			rem = (Map) rel.get(i);
			rem.put("partner_name", partner_name);
		}
		return result;
	}

	public PageModel searchOrderRelationData(String iParam1, String iParam2,
			String iParam3, int pageIndex, int pageSize) throws Exception {

		Map param = new HashMap();
		param.put("iParam1", iParam1);
		param.put("iParam2", iParam2);
		param.put("iParam3", iParam3);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"searchOrderRelationData", param));

		return result;
	}

	public PageModel getOrderProductByCond(String accNbr, String lanCode,
			String mProductName, String mProductId, String prodOfferId,
			String state, int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		param.put("accNbr", accNbr);
		param.put("lanCode", lanCode);
		param.put("mProductId", mProductId);
		param.put("mProductName", mProductName);
		param.put("prodOfferId", prodOfferId);
		param.put("state", state);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"getOrderProductByCond", param));

		return result;
	}
	


	public PageModel searchProductDataByNbr(String accNbr, String offer_nbr,
			String prod_offer_name, String orderId, String proOfferIds,
			String lanCode, String mProductId,String offerState, int pageIndex, int pageSize)
			throws Exception {
		Map param = new HashMap();
		param.put("accNbr", accNbr); // �û�����
		param.put("orderId", orderId); // ����Ʒ�ڵ�ID
		param.put("prod_offer_name", prod_offer_name); // ����Ʒ����
		param.put("offer_nbr", offer_nbr); // ����Ʒ����
		param.put("proOfferIds", proOfferIds);// �Ѿ�ѡ��Ĳ�Ʒ
		param.put("lanCode", lanCode); // ���������
		param.put("mProductId", mProductId); // ����Ʒ����
		param.put("state", offerState);//����Ʒ״̬
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"searchProductDataByNbr", param));

		return result;
	}

	public PageModel getOrderHistory(String accNbr, String lanCode,
			String mProductName, String state, int pageIndex, int pageSize)
			throws Exception {
		Map param = new HashMap();
		param.put("accNbr", accNbr);
		param.put("lanCode", lanCode);
		param.put("mProductName", mProductName);
		param.put("state", state);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"getOrderHistory", param));

		return result;
	}

	public PageModel getOrderHistoryByProduct(String prod_inst_id,
			int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		// param.put("accNbr", accNbr) ;
		// param.put("product_Id", product_Id);
		param.put("prod_inst_id", prod_inst_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"getOrderHistoryByProduct", param));
		return result;
	}

	public Map getOrderRelationById(String order_relation_id) throws Exception {
		Map param = getOrderRelationKeyMap(order_relation_id);

		Map result = DataTranslate._Map(ServiceManager.callJavaBeanService(
				ServiceList.MyService, "getOrderRelationById", param));

		return result;
	}

	public List findOrderRelationByCond(String order_relation_id)
			throws Exception {
		Map param = getOrderRelationKeyMap(order_relation_id);

		List result = DataTranslate._List(ServiceManager.callJavaBeanService(
				ServiceList.MyService, "findOrderRelationByCond", param));

		return result;
	}

	public boolean deleteOrderRelationById(String order_relation_id)
			throws Exception {
		Map param = getOrderRelationKeyMap(order_relation_id);

		boolean result = DataTranslate._boolean(ServiceManager
				.callJavaBeanService(ServiceList.MyService,
						"deleteOrderRelationById", param));

		return result;
	}

	private Map getOrderRelationKeyMap(String order_relation_id) {
		Map param = new HashMap();

		param.put("order_relation_id", order_relation_id);

		return param;
	}

	/**
	 * �˶���ֵ��Ʒ(�������)
	 * @param systemCode ������ϵͳ���ɽ��棬���ԴӸ�ֵ��ȡ����������Ϣ
	 * @param relationIds
	 *            ��-�ָ���relationId����,��1,2,3-4,5,6,��ʾ�˶���������Ʒ,��һ������Ʒ�Ķ�����ϵ��ϸ��1,2,3����,�ڶ�������Ʒ�Ķ�����ϵ��ϸ��4,5,6����.
	 *            
	 * @return
	 * @throws Exception 
	 */
	public Map delOrders(String relationIds,String staff,String systemCode) throws Exception {
		logger.debug("relationIds:" + relationIds);
		
		Map ret = new HashMap();
		relationIds = relationIds.replace('-', ',');
		Map param = new HashMap();
		param.put("relationIds", relationIds);
		
		if("".equals(staff)||null==staff){
			Map session =(Map) RequestContext.getContext().
			getHttpSession().getAttribute("LoginRtnParamsHashMap");	
			staff=(String)session.get("vg_oper_code");
		}
		String orderChannel = null;
		if(null != systemCode && !"".equals(systemCode)){
			orderChannel = systemCode;
		}else{
			orderChannel = SystemCode.VSOP;
		}
		param.put("staff", staff);
		param.put("orderChannel", orderChannel);
	
		 ret = (Map)ServiceManager.callJavaBeanService(
				ServiceList.OrderRelationBO, "delOrdersByRelationId", param);
		return ret;	
	}
	/**
	 * ȫ���˶���ֵ��Ʒ(�������)
	 * 
	 * @param accNbr
	 * @param systemCode ������ϵͳ���ɽ��棬���ԴӸ�ֵ��ȡ����������Ϣ
	 * @return
	 * @throws Exception 
	 */
	public Map delAllOrders(String accNbr,String staff,String systemCode) throws Exception {
		Map ret = new HashMap();
		logger.debug("accNbr:" + accNbr);
		//return new OrderBo().delOrdersByAccNbr(accNbr);
		
		if("".equals(staff)||null==staff){
			Map session =(Map) RequestContext.getContext().
			getHttpSession().getAttribute("LoginRtnParamsHashMap");	
			staff=(String)session.get("vg_oper_code");
		}
		String orderChannel = null;
		if(null != systemCode && !"".equals(systemCode)){
			orderChannel = systemCode;
		}else{
			orderChannel = SystemCode.VSOP;
		}
		Map param = new HashMap();
		param.put("accNbr", accNbr);
		param.put("staff", staff);
		param.put("orderChannel", orderChannel);
		
		 ret = DataTranslate._Map(ServiceManager.callJavaBeanService(
				ServiceList.OrderRelationBO, "delOrdersByAccNbr", param));
		return ret;		
	}

	/**
	 * ������ֵ��Ʒ(�������)(�����ع�ǰ����)
	 * 
	 * @param relationIds
	 * @return
	 * @throws SQLException
	 */
	public Map orderProducts1(String prodOfferIds, String productNbr,
			String lanCode, String mProductId) throws SQLException {
		logger.debug("prodOfferIds:" + prodOfferIds + ",productNbr:"
				+ productNbr);
		Map map = new OrderBo().orderProducts(prodOfferIds, productNbr,
				lanCode, mProductId);
		return map;
	}
	
	/**
	 * �ع���ֵ��Ʒ����(vsopҳ�����,�����ع��󷽷�)
	 * @param prodOfferNbrs ����Ʒ����
	 * @param accNbr ��Ʒ����
	 * @param lanCode 
	 * @param mProductId ��Ʒ����
	 * @param systemCode ������ϵͳ���ɽ��棬���ԴӸ�ֵ��ȡ����������Ϣ
	 * @return
	 * @throws Exception
	 */
	public Map orderProducts(String prodOfferNbrs, String accNbr,
			String lanCode, String mProductId,String staffCode,String systemCode) throws Exception {
		logger.debug("prodOfferNbrs:" + prodOfferNbrs + ",accNbr:" + accNbr);
		Map param = new HashMap();
		param.put("prodOfferNbrs", prodOfferNbrs);
		param.put("accNbr", accNbr);
		param.put("lanCode", lanCode);
		param.put("mProductId", mProductId);
		String staff = null;
		String orderChannel = null;
		if( null != staffCode && !"".equals(staffCode) ){
			staff = staffCode;
		}else{
			HttpSession session = RequestContext.getContext().getHttpSession() ;
			Map m = (Map)session.getAttribute("LoginRtnParamsHashMap");
			String operCode = (String)m.get("vg_oper_code");
			staff = operCode;
		}
		if(null != systemCode && !"".equals(systemCode)){
			orderChannel = systemCode;
		}else{
			orderChannel = SystemCode.VSOP;
		}
		param.put("staffCode", staff);
		param.put("orderChannel", orderChannel);
		Map result=new HashMap();
		try {
			 result = DataTranslate._Map(ServiceManager.callJavaBeanService(
					ServiceList.OrderRelationBO, "orderProducts", param));
		}catch (Exception e){
			e.printStackTrace();
			result.put("ResultCode", "-1");
			String resultMsg=e.getMessage();
			if(null==resultMsg||"".equals(resultMsg)){
				
				resultMsg=e.getCause().getMessage();
			}
			result.put("ResultDesc", resultMsg);
		}finally{
		}
		return result;
	}

	/**
	 * ��������Ʒ������
	 * 
	 * @param typeId
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public String loadProductType(String typeId, String orderId,
			String systemCode) throws Exception {
		Map param = new HashMap();
		param.put("typeId", typeId);
		param.put("orderId", orderId);
		param.put("systemCode", systemCode);
		String result = new OrderBo().loadProductType(param);
		return result;
	}

	/**
	 * ������ϵ��ѯ-��ȡ��ֵ����Ʒ.
	 * 
	 * @param accNbr
	 * @param lanCode
	 * @param mProductName
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public PageModel getOffersByCond(String accNbr, String lanCode,
			String mProductId, String mProductName, String state,String activeState)
			throws Exception {
		Map param = new HashMap();
		param.put("accNbr", accNbr);
		param.put("lanCode", lanCode);
		param.put("mProductId", mProductId);
		param.put("mProductName", mProductName);
		param.put("state", state);
		param.put("activeState", activeState);

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"getOffersByCond", param));

		return result;
	}

//	public boolean delAllOrders(String productNo, String systemId) {
//		String actionType = OrderConstant.orderTypeOfAll;
//		String orderId = "";
//		String productId = null;
//		try {
//			productId = new OrderDao().getProductIdByProductNo(productNo);
//		} catch (SQLException e) {
//			logger.error("#delAllOrders ex.", e);
//		} finally {
//			LegacyDAOUtil.releaseConnection();
//		}
//		String mainXml = ServiceRequestXmlHelper.instance()
//				.unSubscribeAllToVSOP(systemId, actionType, orderId, productId,
//						productNo);
//		String resp = new OrderBo().subscribeToVSOP(mainXml, true, false);
//		String resultCode = StringUtil.getInstance().getTagValue("ResultCode",
//				resp);
//		if (BaseResponse.RESULT_SUCCESS.equals(resultCode))
//			return true;
//		else
//			return false;
//	}

	public boolean getProdInst(String accNbr, String lanCode, String mProductId)
			throws SQLException {
		boolean result = new OrderBo().getProdInst(accNbr, lanCode, mProductId);
		return result;
	}
	/**
	 * ������ϵ��ʷ��ѯ
	 * @param accNbr
	 * @param mProductId
	 * @param startTime
	 * @param endTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel getOrderProductHisByCond(String accNbr, String mProductId,
			String startTime,String endTime, String activeType,String stateCode,int pageIndex, int pageSize) throws Exception {
		Map param = new HashMap();
		param.put("accNbr", accNbr);
		param.put("mProductId", mProductId);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("activeType", activeType);
		param.put("stateCode",stateCode);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));

		PageModel result = DataTranslate._PageModel(ServiceManager
				.callJavaBeanService(ServiceList.OrderRelationBO,
						"getOrderProductHisByCond", param));
		return result;
	}
}
