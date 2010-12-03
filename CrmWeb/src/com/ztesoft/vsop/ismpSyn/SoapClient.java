package com.ztesoft.vsop.ismpSyn;


import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;

import com.ztesoft.vsop.ismpSyn.vo.Response;
import com.ztesoft.vsop.ismpSyn.vo.SubscriptionSyncReq;
import com.ztesoft.vsop.web.DcSystemParamManager;



public class SoapClient {
	
	public static SoapClient instance=new SoapClient();
	private static Logger logger = Logger.getLogger(SoapClient.class);
	private static String CRM_ISMP_SYNURL = DcSystemParamManager.getParameter("SERVICE_CRM_SYN_URL");
	public static SoapClient getInstance(){
		return instance;
	}

	/**
	 *  订购关系反向同步crm
	 * @param opType
	 * @param packageId
	 * @param productId
	 * @param streamingNo
	 * @param userId
	 * @param userIdType
	 * @return Response 此节点包含ResultCode:结果代码；StreamingNo:流水号
	 */
	public Response subscriptionSync(String opType,
								   String packageId,
								   String productId,
								   String streamingNo,
								   String userId,
								   String userIdType) {
		logger.info("-------subscriptionSync start");
		String serviceUrl = CRM_ISMP_SYNURL;
		logger.info("-------serviceUrl:"+serviceUrl);
		logger.info("userId:"+userId+",opType:"+opType+",productId:"+productId);
//		IsmpCrmEngineSoapBindingStub sendSVStub = null;
		IsmpCrmEngineServiceLocator service = null;
		Response rep = null;
		try {
			service = new IsmpCrmEngineServiceLocator();
			service.setIsmpCrmEngineEndpointAddress(serviceUrl);
//			sendSVStub = new IsmpCrmEngineSoapBindingStub(service);
			SubscriptionSyncReq Req12 = new SubscriptionSyncReq();

			Req12.setOPType(Integer.parseInt(opType));
			Req12.setPackageID(packageId);
			Req12.setProductID(productId);
			Req12.setStreamingNo(streamingNo);
			Req12.setUserID(userId);
			Req12.setUserIDType(Integer.parseInt(userIdType));
			rep = service.getIsmpCrmEngine().subscriptionSync(Req12);
			logger.info("resultCode:"+rep.getResultCode());
		} catch (AxisFault e) {
			logger.error("error", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("error", e);
			e.printStackTrace();
		}
		logger.info("-------subscriptionSync end");
		return rep;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SoapClient().subscriptionSync("0", "200003590", "13500000000000000212311", "270", "133177115531", "0");
		
	}
}
