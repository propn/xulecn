package com.ztesoft.vsop.webservice.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.FeeCheckSVStub;
import com.ztesoft.vsop.webservice.skeleton.IsmpCrmEngineServiceStub;
import com.ztesoft.vsop.webservice.skeleton.OfferInfoSynSVStub;
import com.ztesoft.vsop.webservice.skeleton.ProdInfoSynSVStub;
import com.ztesoft.vsop.webservice.skeleton.ProdOfferInfoSynCrmSVStub;
import com.ztesoft.vsop.webservice.skeleton.ReconfirmRTProSVStub;
import com.ztesoft.vsop.webservice.skeleton.SPCPSyncServiceStub;
import com.ztesoft.vsop.webservice.skeleton.SendSubRTSVStub;
import com.ztesoft.vsop.webservice.skeleton.SubsInstQrySVStub;
import com.ztesoft.vsop.webservice.skeleton.SubsInstSynFromISMPStub;
import com.ztesoft.vsop.webservice.skeleton.SubsInstSynSVStub;
import com.ztesoft.vsop.webservice.skeleton.SubscribeAuthSVStub;
import com.ztesoft.vsop.webservice.skeleton.SubscribeReqSVStub;
import com.ztesoft.vsop.webservice.skeleton.UserInfoSynSVStub;
import com.ztesoft.vsop.webservice.skeleton.WorkSheetAcceptSVStub;
import com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReq;
import com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReqResponse;
import com.ztesoft.vsop.webservice.vo.ProductInfoSyncReq;
import com.ztesoft.vsop.webservice.vo.ProductInfoSyncReqResponse;
import com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse;
import com.ztesoft.vsop.webservice.vo.SubsInstSynFromISMPReqResponse;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp;
import com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse;
import com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;
import com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.jx.Response20;
import com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq;
import com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0;


public class SoapClient {
	public final String VSOP_SERVICE_PREFIX="http://127.0.0.1:9001/VsopWeb/services/";
//	public final String VSOP_SERVICE_PREFIX="http://127.0.0.1:7001/VsopWeb/services/";
	public final String URL_SPCPSyncService=VSOP_SERVICE_PREFIX+"SCPInfoSynSV";//10.40.198.240:7001
//	public final String URL_AllOutSystemService="http://localhost:7878/VsopWeb/services/AllOutSystemService";
//	public final String URL_ProdOfferSyncService="http://localhost:7878/VsopWeb/services/ProdOfferSyncService";
	public final String URL_SubscribeService=VSOP_SERVICE_PREFIX+"SubscribeService";
	
	//费用鉴权请求的服务器地址，在系统参数表里配置
	public final String SERVICE_FEE_CHECK_URL = DcSystemParamManager.getParameter("SERVICE_FEE_CHECK_URL");
	
	//订购关系同步(省级VSOP->集团VSOP)请求的服务器地址
	public final String SYNCH_TO_GROUP_VSOP_URL = DcSystemParamManager.getParameter("SYNCH_TO_GROUP_VSOP_URL");
	
	public static SoapClient instance=new SoapClient();
	private static Logger logger = Logger.getLogger(SoapClient.class);
	
	public static SoapClient getInstance(){
		return instance;
	}

	/**
	 * 增值销售品同步处理
	 * @param requestXml
	 * @return
	 */
	public String offerSynToVSOP(String requestXml){
		String ret=null;
		try {
			OfferInfoSynSVStub prodOfferSyncServiceStub=new OfferInfoSynSVStub(VSOP_SERVICE_PREFIX+"OfferInfoSynFromISMPSV");
			System.out.println("地址为:"+VSOP_SERVICE_PREFIX+"OfferInfoSynFromISMPSV");
			PPOfferInfoSyncReq offerInfoSynReq = new PPOfferInfoSyncReq() ;
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			offerInfoSynReq.setPPOfferInfoSyncReq( param );
			PPOfferInfoSyncReqResponse offerInfoSyncReqResponse=prodOfferSyncServiceStub.PPOfferInfoSync(offerInfoSynReq);
			ret=offerInfoSyncReqResponse.getPPOfferInfoSyncReqResponse().getResponse();
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 增值销售品同步处理
	 * @param requestXml
	 * @return OfferInfoSynFromISMPSV
	 */
	public String xofferSynToVSOP(String requestXml){
		String ret=null;
		try {
			OfferInfoSynSVStub prodOfferSyncServiceStub=new OfferInfoSynSVStub(VSOP_SERVICE_PREFIX+"OfferInfoSynSV");
			System.out.println("地址为:"+VSOP_SERVICE_PREFIX+"OfferInfoSynSV");
			PPOfferInfoSyncReq offerInfoSynReq = new PPOfferInfoSyncReq() ;
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			offerInfoSynReq.setPPOfferInfoSyncReq(param );
			com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReqResponse offerInfoSyncReqResponse=prodOfferSyncServiceStub.PPOfferInfoSync(offerInfoSynReq);
			ret=offerInfoSyncReqResponse.getPPOfferInfoSyncReqResponse().getResponse();
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ret;
	}
	

	/**
	 * 增值产品同步处理
	 * @param requestXml
	 * @return
	 */
	public String prodSynToVSOP(String requestXml){
		String ret=null;
		try {
			ProdInfoSynSVStub prodSyncServiceStub=new ProdInfoSynSVStub(VSOP_SERVICE_PREFIX+"ProdInfoSynSV");
			System.out.println("地址为:"+VSOP_SERVICE_PREFIX+"ProdInfoSynSV");
			ProductInfoSyncReq productInfoSyncReq=new ProductInfoSyncReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			productInfoSyncReq.setProductInfoSyncReq( param );
			ProductInfoSyncReqResponse productInfoSyncReqResponse=prodSyncServiceStub.productInfoSync(productInfoSyncReq);
			ret=productInfoSyncReqResponse.getProductInfoSyncReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	
	public String spcpSyn2VSOP(String requestXml){
		String ret=null;
		try {
			SPCPSyncServiceStub subscribeServiceStub=new SPCPSyncServiceStub(URL_SPCPSyncService);
			System.out.println("地址为:"+URL_SPCPSyncService);
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq spcpReq=new 
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			spcpReq.setCPSPInfoSyncReq(param);
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse cpspResponse=subscribeServiceStub.CPSPInfoSync(spcpReq);
			ret=cpspResponse.getCPSPInfoSyncReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	

	
	public String spcpSynStub(String requestXml){
		String ret=null;
		try {
			SPCPSyncServiceStub subscribeServiceStub=new SPCPSyncServiceStub(URL_SPCPSyncService);
			System.out.println("地址为:"+URL_SPCPSyncService);
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq spcpReq=new 
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			spcpReq.setCPSPInfoSyncReq(param);
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse cpspResponse=subscribeServiceStub.CPSPInfoSync(spcpReq);
			ret=cpspResponse.getCPSPInfoSyncReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public String subsInstSynToVSOP(String requestXml){
		String ret=null;
		try {
			SubsInstSynFromISMPStub subscribeServiceStub=
				new SubsInstSynFromISMPStub(this.VSOP_SERVICE_PREFIX+"SubsInstSynFromISMP");
			System.out.println("地址为:"+this.VSOP_SERVICE_PREFIX+"SubsInstSynFromISMP");
			com.ztesoft.vsop.webservice.vo.SubsInstSynFromISMPReq subsInstSynToVSOPReq80=new 
			com.ztesoft.vsop.webservice.vo.SubsInstSynFromISMPReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			subsInstSynToVSOPReq80.setSubsInstSynFromISMPReq(param);
			SubsInstSynFromISMPReqResponse subsInstSynToVSOPReqResponse=
				subscribeServiceStub.subsInstSynToVSOP(subsInstSynToVSOPReq80);
			ret=subsInstSynToVSOPReqResponse.getSubsInstSynFromISMPReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	
	public String recvRQMessage(String requestXml){
		String ret=null;
		try {
			ReconfirmRTProSVStub ReconfirmStub=new ReconfirmRTProSVStub(VSOP_SERVICE_PREFIX+"ReconfirmRTProSV");
			System.out.println("地址为:"+this.VSOP_SERVICE_PREFIX+"ReconfirmRTProSV");
			com.ztesoft.vsop.webservice.vo.RecvRQMessageReq sendMessageReq=new 
			com.ztesoft.vsop.webservice.vo.RecvRQMessageReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest param=new VsopServiceRequest();
			param.setRequest(requestXml);
			sendMessageReq.setRecvRQMessageReq(param);
			RecvRQMessageReqResponse sendMessageReqResponse=ReconfirmStub.recvRQMessage(sendMessageReq);
			ret=sendMessageReqResponse.getRecvRQMessageReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public String subscribeToVSOP(String requestXml){
		String ret=null;
		try {
			SubscribeReqSVStub subscribeServiceStub=new 
			SubscribeReqSVStub(VSOP_SERVICE_PREFIX+"SubscribeReqSV");
			System.out.println("地址为:"+this.VSOP_SERVICE_PREFIX+"SubscribeReqSV");
			com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq subscribeToVSOPReq72=new 
			com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			subscribeToVSOPReq72.setSubscribeToVSOPReq(param);
			SubscribeToVSOPReqResponse subscribeToVSOPReqResponse=subscribeServiceStub.subscribeToVSOP(subscribeToVSOPReq72);
			ret=subscribeToVSOPReqResponse.getSubscribeToVSOPReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public String workListFKToVSOP(String requestXml){
		String ret=null;
		try {
			WorkSheetAcceptSVStub subscribeServiceStub=new 
			WorkSheetAcceptSVStub(VSOP_SERVICE_PREFIX+"WorkSheetAcceptSV");
			System.out.println("地址为:"+this.VSOP_SERVICE_PREFIX+"WorkSheetAcceptSV");
			com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq workListFKToVSOPReq78=new 
			com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			workListFKToVSOPReq78.setWorkListFKToVSOPReq(param);
			WorkListFKToVSOPReqResponse workListFKToVSOPReqResponse =subscribeServiceStub.workListFKToVSOP(workListFKToVSOPReq78);
			ret=workListFKToVSOPReqResponse.getWorkListFKToVSOPReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public String subscribeAuth(String requestXml){
		logger.debug("#subscribeAuth requestXml->"+requestXml);
		String ret=null;
		try {
			SubscribeAuthSVStub subscribeServiceStub=new SubscribeAuthSVStub(VSOP_SERVICE_PREFIX+"SubscribeAuthSV");
			System.out.println("地址为:"+this.VSOP_SERVICE_PREFIX+"SubscribeAuthSV");
			com.ztesoft.vsop.webservice.vo.SubscribeAuthReq subscribeAuthReq76=new 
			com.ztesoft.vsop.webservice.vo.SubscribeAuthReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			subscribeAuthReq76.setSubscribeAuthReq(param);
			SubscribeAuthReqResponse subscribeAuthReqResponse=subscribeServiceStub.subscribeAuth(subscribeAuthReq76);
			ret=subscribeAuthReqResponse.getSubscribeAuthReqResponse().getResponse();
		} catch (AxisFault e) {
			logger.error("#subscribeAuth", e);
			e.printStackTrace();
		} catch (RemoteException e) {
			logger.error("#subscribeAuth", e);
			e.printStackTrace();
		}catch (Exception e) {
			logger.error("#subscribeAuth", e);
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * OCS费用鉴权请求
	 * @param requestXml
	 * @return
	 */
	public String feeCheckReq(String requestXml) {
		String ret=null;
		try {
			FeeCheckSVStub feeCheckSVStub = new FeeCheckSVStub(SERVICE_FEE_CHECK_URL);
			System.out.println("地址为:"+SERVICE_FEE_CHECK_URL);
			com.ztesoft.vsop.webservice.vo.FeeCheckReq feeCheckReq = new com.ztesoft.vsop.webservice.vo.FeeCheckReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest param = new VsopServiceRequest();
			param.setRequest(requestXml);
			feeCheckReq.setFeeCheckReq(param);
			com.ztesoft.vsop.webservice.vo.FeeCheckResp feeCheckReqResponse = feeCheckSVStub.feeCheck(feeCheckReq);
			ret = feeCheckReqResponse.getFeeCheckResp().getResponse();
		} catch (AxisFault e) {
			logger.error("error", e);
			e.printStackTrace();
		} catch (RemoteException e) {
			logger.error("error", e);
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 订购关系同步(省级VSOP->集团VSOP)请求
	 * @param requestXml
	 * @return
	 */
	public String subsInstSynSV(String requestXml) {
		String ret=null;
		try {
			SubsInstSynSVStub subsInstSynSVStub = new SubsInstSynSVStub(SYNCH_TO_GROUP_VSOP_URL);
			System.out.println("地址为:"+SYNCH_TO_GROUP_VSOP_URL);
			SubsInstSynToVSOPReq subsInstSynToSOPReq = new com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest param = new VsopServiceRequest();
			param.setRequest(requestXml);
			subsInstSynToSOPReq.setSubsInstSynToVSOPReq(param);
			SubsInstSynToVSOPResp subsInstSynToVSOPResp = subsInstSynSVStub.subsInstSynToGroup(subsInstSynToSOPReq);
			ret = subsInstSynToVSOPResp.getSubsInstSynToVSOPResp().getResponse();
		} catch (AxisFault e) {
			logger.error("error", e);
			e.printStackTrace();
		} catch (RemoteException e) {
			logger.error("error", e);
			e.printStackTrace();
		}
		return ret;
	
	}
	
	public String spcpSynCapStub(String requestXml){
		String ret=null;
		try {
			SPCPSyncServiceStub subscribeServiceStub=new SPCPSyncServiceStub(URL_SPCPSyncService);
			System.out.println("地址为:"+URL_SPCPSyncService);
			com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq spcpReq=new 
			com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq();
			com.ztesoft.vsop.webservice.vo.VsopServiceRequest  param=new VsopServiceRequest();
			param.setRequest(requestXml);
			spcpReq.setCPSPCapabilitySyncReq(param);
			com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse cpspResponse=subscribeServiceStub.CPSPCapabilitySync(spcpReq);
			ret=cpspResponse.getCPSPCapabilitySyncReqResponse().getResponse();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public String userInfoSynSV(String requestXml){
		logger.info("userInfoSynSV request:"+requestXml);
		UserInfoSynSVStub userInfoSynSVStub=null;
		com.ztesoft.vsop.webservice.vo.UserInfoSynSVReqResponse userInfoSynSVReqResponse=null;
		try {
			userInfoSynSVStub = new UserInfoSynSVStub(
					DcSystemParamManager.getParameter(VsopConstants.UserInfoSynSV_URL_PARAMCODE));
			com.ztesoft.vsop.webservice.vo.UserInfoSynSVReq userInfoSynSVReq12=new 
			com.ztesoft.vsop.webservice.vo.UserInfoSynSVReq();
			
			 com.ztesoft.vsop.webservice.vo.VsopServiceRequest param=
				 new  com.ztesoft.vsop.webservice.vo.VsopServiceRequest();
			 param.setRequest(requestXml);
			userInfoSynSVReq12.setUserInfoSynSVReq(param);
			userInfoSynSVReqResponse=
				userInfoSynSVStub.userInfoSynSV(userInfoSynSVReq12);
		} catch (AxisFault e) {
			logger.error("error", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("error", e);
			e.printStackTrace();
		}

		logger.info("userInfoSynSV response:"+userInfoSynSVReqResponse.getUserInfoSynSVReqResponse().getResponse());
		return userInfoSynSVReqResponse.getUserInfoSynSVReqResponse().getResponse();
	}
	public String userInfoSyncFromVSOP(String requestXml, String serviceUrl) throws AxisFault{
		logger.info("userInfoSynSV request:"+requestXml);
		logger.info("userInfoSyncFromVSOP serviceUrl:" + serviceUrl);
		UserInfoSynSVStub userInfoSynSVStub=null;
		com.ztesoft.vsop.webservice.vo.UserInfoSynSVReqResponse userInfoSynSVReqResponse=null;
		try {
			userInfoSynSVStub = new UserInfoSynSVStub(serviceUrl);
			com.ztesoft.vsop.webservice.vo.UserInfoSynSVReq userInfoSynSVReq12=new 
			com.ztesoft.vsop.webservice.vo.UserInfoSynSVReq();
			
			 com.ztesoft.vsop.webservice.vo.VsopServiceRequest param=
				 new  com.ztesoft.vsop.webservice.vo.VsopServiceRequest();
			 param.setRequest(requestXml);
			userInfoSynSVReq12.setUserInfoSynSVReq(param);
			userInfoSynSVReqResponse=
				userInfoSynSVStub.userInfoSynSV(userInfoSynSVReq12);
		} catch (AxisFault e) {
			logger.error("error", e);
			throw e;
		} catch (Exception e) {
			logger.error("error", e);
			e.printStackTrace();
		}

		logger.info("userInfoSynSV response:"+userInfoSynSVReqResponse.getUserInfoSynSVReqResponse().getResponse());
		return userInfoSynSVReqResponse.getUserInfoSynSVReqResponse().getResponse();
	}

	/**
	 * OCS费用鉴权获取实时余额
	 * @param systemId
	 * @param productId
	 * @param productNo
	 * @param contectType
	 * @return
	 */
	public String getBalance(String systemId, String productId, String productNo, String contectType) {

		String streamingNo = VsopStreamNoHelper.getInstance().genReqStreamNo();
		//yyyymmdd
		String timeStamp = DateUtil.formatDate(CrmConstants.DATE_FORMAT_8);
		
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		sb.append("<FeeCheckReq>")
			.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
			.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
			.append("<SystemId>").append(systemId).append("</SystemId>")
			.append("<ProdSpecCode>").append(productId).append("</ProdSpecCode>")
			.append("<ProductNo>").append(productNo).append("</ProductNo>")
			.append("<ContectType>").append(contectType).append("</ContectType>");
	    sb.append("</FeeCheckReq>");
		
		String response = feeCheckReq(sb.toString());
		String balance = StringUtil.getInstance().getTagValue("Balance", response);
		return balance;
	}
	/**
	 * 订购关系查询
	 * @param requestXml
	 * @param serviceUrl
	 * @return
	 */
	private static String orderRelationQuery(){
		String requestXml = "<Request><SessionBody><SubInstQryFromVSOPReq><StreamingNo>111</StreamingNo><TimeStamp>20100508</TimeStamp>" +
							"<SystemId>211</SystemId><ProdSpecCode>2020966</ProdSpecCode><ProductNo>13277002544</ProductNo>" +
							"</SubInstQryFromVSOPReq></SessionBody></Request>";
		String serviceUrl = "http://134.201.27.140:5033/VsopWeb/services/SubsInstQrySV";
		SubsInstQrySVStub qrySVStub=null;
		com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse qrySVReqResp=null;
		try {
			qrySVStub = new SubsInstQrySVStub(serviceUrl);
			com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq Req12=new 
			com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq();
			
			 com.ztesoft.vsop.webservice.vo.VsopServiceRequest param=
				 new  com.ztesoft.vsop.webservice.vo.VsopServiceRequest();
			 param.setRequest(requestXml);
			 Req12.setSubInstQryFromVSOPReq(param);
			 qrySVReqResp = qrySVStub.subInstQryFromVSOP(Req12);
		} catch (AxisFault e) {
			logger.error("error", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("error", e);
			e.printStackTrace();
		}

		logger.info("userInfoSynSV response:"+qrySVReqResp.getSubInstQryFromVSOPReqResponse().getResponse());
		return qrySVReqResp.getSubInstQryFromVSOPReqResponse().getResponse();
	}
	/**
	 * 订购结果通知用户
	 * @param xml
	 * @return
	 */
	private static String sendMsg() {
		String requestXml = "<SendSubRTToUserReq><StreamingNo>1234566789</StreamingNo><TimeStamp>20100521</TimeStamp><SystemId>200</SystemId>" +
							"<ProdSpecCode>2020966</ProdSpecCode><ProductNo>18977110773</ProductNo><SubResult>您在XX厅订购的XX增值产品已经订购成功!</SubResult></SendSubRTToUserReq>";
		String serviceUrl = "http://134.192.92.173:8093/SL/services/SendSubRTSV";
		SendSubRTSVStub sendSVStub = null;
		com.ztesoft.vsop.webservice.vo.SendSubRTToUserResp sendSVReqResp = null;
		try {
			sendSVStub = new SendSubRTSVStub(serviceUrl);
			com.ztesoft.vsop.webservice.vo.SendSubRTToUserReq Req12 = new com.ztesoft.vsop.webservice.vo.SendSubRTToUserReq();

			com.ztesoft.vsop.webservice.vo.VsopServiceRequest param = new com.ztesoft.vsop.webservice.vo.VsopServiceRequest();
			param.setRequest(requestXml);
			Req12.setSendSubRTToUserReq(param);
			sendSVReqResp = sendSVStub.subResulToUser(Req12);
		} catch (AxisFault e) {
			logger.error("error", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("error", e);
			e.printStackTrace();
		}

		logger.info("userInfoSynSV response:" + sendSVReqResp.getSendSubRTToUserResp().getResponse());
		return sendSVReqResp.getSendSubRTToUserResp().getResponse();
	}
	/**
	 * crm优惠同步
	 * @return
	 */
	private static String prodOfferSynToVsop() {
		String requestXml = "<Request><SessionBody><OfferInstFromCRMReq><CompOfferId>301000143</CompOfferId><OfferInstDetail><InstanceRelationId>620050172874</InstanceRelationId>" +
							"<OfferDetailId>4538</OfferDetailId><ProductId>41002132796</ProductId></OfferInstDetail><OfferId>10010095</OfferId>"+
							"<ExpDate>2030-01-01 00:00:00</ExpDate><StreamingNo>1277520085947</StreamingNo><OfferInstId>611123608220</OfferInstId>"+
							"<TimeStamp>20100626</TimeStamp><EffDate>2010-06-17 17:37:36</EffDate><LanId>1100</LanId><OperType>A</OperType>" +
							"<SystemId>1</SystemId></OfferInstFromCRMReq></SessionBody></Request>";
		String serviceUrl = "http://127.0.0.1:7001/VsopWeb/services/ProdOfferInfoSynCrmSV";
		ProdOfferInfoSynCrmSVStub sendSVStub = null;
		com.ztesoft.vsop.webservice.vo.ProdOfferSyncCrmToVSOPResp resp = null;
		try {
			sendSVStub = new ProdOfferInfoSynCrmSVStub(serviceUrl);
			com.ztesoft.vsop.webservice.vo.ProdOfferSyncCrmToVSOPReq Req12 = new com.ztesoft.vsop.webservice.vo.ProdOfferSyncCrmToVSOPReq();

			com.ztesoft.vsop.webservice.vo.VsopServiceRequest param = new com.ztesoft.vsop.webservice.vo.VsopServiceRequest();
			param.setRequest(requestXml);
			Req12.setProdOfferSyncCrmToVSOPReq(param);
			resp = sendSVStub.ProdOfferInfoSynCrmSyn(Req12);
		} catch (AxisFault e) {
			logger.error("error", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("error", e);
			e.printStackTrace();
		}

		return resp.getProdOfferSyncCrmToVSOPResp().getResponse();
	}
	/**
	 * 江西 订购关系同步
	 * @return
	 */
	public static String subsInstSynToVSOPFromJXISMP(){
		String ret=null;
	try {
		String serviceUrl = "http://127.0.0.1:9001/VsopWeb/services/IsmpCrmEngineService";
	IsmpCrmEngineServiceStub ismpCrmEngineServiceStub=
				new IsmpCrmEngineServiceStub(serviceUrl);
			System.out.println("地址为:"+serviceUrl);
	
			SubscriptionSyncReq0 subscriptionSyncReq=new SubscriptionSyncReq0();
			SubscriptionSyncReq param= new SubscriptionSyncReq();
			//param.serialize(parentQName, factory, xmlWriter)
			param.setOPType(0);
			param.setPackageID("41002132796");
			param.setProductID("20001");
			param.setStreamingNo("1277520085947");
			param.setUserID("37563270");
			param.setUserIDType(0);
			subscriptionSyncReq.setSubscriptionSyncReq(param);
			Response20 response20=ismpCrmEngineServiceStub.subscriptionSync(subscriptionSyncReq);
			ret="StreamingNo:"+response20.getResponse().getStreamingNo()+"----ResultCode:"+response20.getResponse().getResultCode();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	
	/**
	 * 订购关系同步(X平台->省VSOP)请求
	 * @param requestXml
	 * @return
	 */
	public static String subsInstSynSV1(String requestXml) {
		String ret=null;
		try {
			String serviceUrl = "http://127.0.0.1:9001/VsopWeb/services/SubsInstSynSV";
			SubsInstSynSVStub subsInstSynSVStub = new SubsInstSynSVStub(serviceUrl);
			System.out.println("地址为:"+serviceUrl);
			SubsInstSynToVSOPReq subsInstSynToSOPReq = new com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq();
			VsopServiceRequest param = new VsopServiceRequest();
			param.setRequest(requestXml);
			subsInstSynToSOPReq.setSubsInstSynToVSOPReq(param);
			SubsInstSynToVSOPResp subsInstSynToVSOPResp = subsInstSynSVStub.subsInstSynToGroup(subsInstSynToSOPReq);
			ret = subsInstSynToVSOPResp.getSubsInstSynToVSOPResp().getResponse();
			
		} catch (AxisFault e) {
			logger.error("error", e);
			e.printStackTrace();
		} catch (RemoteException e) {
			logger.error("error", e);
			e.printStackTrace();
		}
		return ret;
	
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String requestXml="<Request>"+
	"<SessionBody>"+
		"<SubsInstSynToVSOPReq>"+
			"<StreamingNo>1277520085</StreamingNo>"+
			"<TimeStamp>20100626</TimeStamp>"+
			"<SystemId>1</SystemId>"+
			"<ProdSpecCode>0</ProdSpecCode>"+
			"<ProductNo>18900000106</ProductNo>"+
			"<VproductID>20001</VproductID>"+
			"<SPID>9</SPID>"+
			"<ProductOfferType>0</ProductOfferType>"+
			"<ProductOfferID>20001</ProductOfferID>"+
			"<Status>0</Status>"+
			"<SubscribeTime>A</SubscribeTime>"+
			"<ExpDate>2010-09-01 00:00:00</ExpDate>"+
			"<EffDate>2010-08-17 17:37:36</EffDate>"+
			"<ChannelPlayerID>301</ChannelPlayerID>"+
		"</SubsInstSynToVSOPReq>"+
	"</SessionBody>"+
"</Request>";
		String ret = subsInstSynSV1(requestXml);
		System.out.println("返回结果："+ret);
	}
}
