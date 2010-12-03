package com.ztesoft.vsop.webservice.bo;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.ztesoft.vsop.AuthenticationForCRMMain;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.ismpSyn.vo.SubscriptionSyncReq;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.OrderRelationProcess;
import com.ztesoft.vsop.order.OrderService;
import com.ztesoft.vsop.order.ProdOfferSynBO;

/**
 * 
 * @author cooltan
 *
 */
public class SubscribeServiceBo {
	private static Logger logger = Logger.getLogger(SubscribeServiceBo.class);
	
	public static SubscribeServiceBo instance=new SubscribeServiceBo();
	
	public static SubscribeServiceBo getInstance(){
		return instance;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 增值产品订购
	 * @param requestXml
	 * @return
	 */
	public String subscribeToVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		logger.info("requestXml:"+requestXml);
		OrderService orderService = new OrderService();
		String responseXml = orderService.subscribeToVSOP(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subscribeToVSOP cost:"+processTime);
		logger.info("subscribeToVSOP responseXml:"+responseXml);
		 return responseXml;
	}
	
	/**
	 * 订购关系查询
	 * @param requestXml
	 * @return
	 */
	public String subInstQryFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		logger.info("requestXml:"+requestXml);
		OrderService orderService = new OrderService();
		requestXml=StringUtil.getInstance().getTagValue("SessionBody", requestXml);
		String responseXml = orderService.subInstQuery(requestXml);
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subInstQryFromVSOP cost:"+processTime);
		logger.info("subInstQryFromVSOP responseXml:"+responseXml);
		return responseXml;
	}
	
	public String subscribeAuth(String requestXml) {
		long star = System.currentTimeMillis();

		String retXml = null;
		try {
			retXml = AuthenticationForCRMMain.process(requestXml);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subscribeAuth cost:"+processTime);
		logger.info("subscribeAuth retXml:"+retXml);
		 return retXml;
	}
	/**
	 * 服务开通工单接收服务
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 */
	public String workListFKToVSOP(String requestXml) throws Exception {
		long star = System.currentTimeMillis();
		String retXml = new OrderBo().workListFKToVSOP(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("workListFKToVSOP cost:"+processTime);
		logger.info("workListFKToVSOP retXml:"+retXml);
		return retXml;
	}
	public String subsInstSynToVSOP(String requestXml) throws DocumentException, SQLException {
		long star = System.currentTimeMillis();
		logger.info("subsInstSynToVSOP req XML:"+requestXml);
		requestXml=StringUtil.getInstance().getTagValue("SessionBody", requestXml);
		String retXml = OrderRelationProcess.process(requestXml);
		retXml=StringUtil.getInstance().getVsopResponse(retXml);
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subsInstSynToVSOP cost:"+processTime);
		logger.info("subsInstSynToVSOP response XML:"+retXml);
		return retXml;
		
	}
	public String recvRQMessage(String requestXml) {
		long star = System.currentTimeMillis();
		//.......
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("recvRQMessage cost:"+processTime);
		 throw new java.lang.UnsupportedOperationException("Please implement " +
		            this.getClass().getName() + "#recvRQMessage");
		
	}
	/**
	 * crm同步优惠处理逻辑
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 */
	public String prodOfferCrmToVsopSyn(String requestXml) throws Exception {
		logger.info("SubscribeServiceBo.prodOfferCrmToVsopSyn start");
		long star = System.currentTimeMillis();
		String retXml = new ProdOfferSynBO().prodOfferCrmToVsopSyn(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("workListFKToVSOP cost:"+processTime);
		logger.info("workListFKToVSOP retXml:"+retXml);
		logger.info("SubscribeServiceBo.prodOfferCrmToVsopSyn end");
		return retXml;
	}
	/**
	 * 广西本地化 广西ismp方向同步订购关系数据到vsop ismp保留反向同步给crm的接口
	 * @param requestXml
	 * @return
	 * @throws DocumentException
	 * @throws SQLException
	 */
	public com.ztesoft.vsop.ismpSyn.vo.Response subsInstSynIsmpToVsopGX(SubscriptionSyncReq req) throws DocumentException, SQLException {
		long star = System.currentTimeMillis();
		logger.info("SubscribeServiceBo.subsInstSynIsmpToVsopGX start");
		com.ztesoft.vsop.ismpSyn.vo.Response resp = OrderRelationProcess.synIsmpToVsopGXProc(req);
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subsInstSynIsmpToVsopGX cost:"+processTime);
		logger.info("subsInstSynIsmpToVsopGX response ResultCode:"+resp.getResultCode());
		return resp;
		
	}
	
	/**
	 * 订购历史查询
	 * @param requestXml
	 * @return
	 */
	public String subInstHisQryFromVSOP(String requestXml) {
		long star = System.currentTimeMillis();
		logger.info("requestXml:"+requestXml);
		OrderService orderService = new OrderService();
		String responseXml = orderService.subInstHisQryFromVSOP(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
		long end = System.currentTimeMillis();
		long processTime = end - star;
		logger.info("subscribeToVSOP cost:"+processTime);
		logger.info("subscribeToVSOP responseXml:"+responseXml);
		 return responseXml;
	}
}
