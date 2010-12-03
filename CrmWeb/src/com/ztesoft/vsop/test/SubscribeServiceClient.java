package com.ztesoft.vsop.test;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.SubsInstQrySVStub;
import com.ztesoft.vsop.webservice.skeleton.SubscribeReqSVStub;
import com.ztesoft.vsop.webservice.skeleton.WorkSheetAcceptSVStub;
import com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;
import com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq;
import com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse;


public class SubscribeServiceClient {
	private static Logger logger = Logger.getLogger(SubscribeServiceClient.class);
	public final String VSOP_SERVICE_PREFIX=DcSystemParamManager.getParameter("VSOP_SERVICE_PREFIX");
//	private String endPoint = "http://127.0.0.1:8919/VsopWeb/services/SubscribeReqSV";
	private String subscribeEndPoint = VSOP_SERVICE_PREFIX + "SubscribeReqSV";
	private String subsInstQryEndPoint = VSOP_SERVICE_PREFIX + "SubsInstQrySV";
	//private String subsInstQryEndPoint = "http://127.0.0.1:7008/VsopWeb/services/SubsInstQrySV";
	private String workSheetAcceptSVEndPoint = VSOP_SERVICE_PREFIX + "WorkSheetAcceptSV";
//	private String endPoint = "http://10.40.198.240:7001/VsopWeb/services/SubscribeService";
//	private String endPoint = "http://127.0.0.1:9221/VsopWeb/services/OrderService";
//	private String endPoint = "http://127.0.0.1:9221/VsopWeb/services/OrderService";
	//http://localhost:7001/VsopWeb/services/OrderService
	
	private SubscribeReqSVStub ubscribeReqSVStub;
	private SubsInstQrySVStub subsInstQrySVStub;
	private WorkSheetAcceptSVStub workSheetAcceptSVStub;
	public SubscribeServiceClient() throws AxisFault{
		ubscribeReqSVStub = new SubscribeReqSVStub(subscribeEndPoint);
		subsInstQrySVStub = new SubsInstQrySVStub(subsInstQryEndPoint);
		workSheetAcceptSVStub = new WorkSheetAcceptSVStub(workSheetAcceptSVEndPoint);
	}
	
	public String subscribeTovsop() throws RemoteException{
		return subscribeToVsop(createSendOrderRequest());
	}
	public String subscribeToVsop(String inxml) throws RemoteException{
		SubscribeToVSOPReq subscribeToVSOPReq12 = new SubscribeToVSOPReq();
		VsopServiceRequest param = new VsopServiceRequest();
		subscribeToVSOPReq12.setSubscribeToVSOPReq(param );
		String streamNo = VsopStreamNoHelper.getInstance().genReqStreamNo();
		String timestamp = StringUtil.getInstance().getCurrentTimeStamp();
		String requ = StringUtil.getInstance().getVsopRequest(streamNo , timestamp , inxml) ;
		param.setRequest(requ);
		System.out.println("start:");
		System.out.println("request:" + requ);
		SubscribeToVSOPReqResponse resp = ubscribeReqSVStub.subscribeToVSOP(subscribeToVSOPReq12 );
		VsopServiceResponse respObj = resp.getSubscribeToVSOPReqResponse();
		System.out.println("response:");
		System.out.println(respObj.getResponse());
		return respObj.getResponse();
	}

	private String createSendOrderRequest() {
		StringBuffer bf = new StringBuffer("");
		bf.append("<SubscribeToVSOPReq>")
			.append("<StreamingNo>").append(VsopStreamNoHelper.getInstance().genReqStreamNo()).append("</StreamingNo>")
			.append("<TimeStamp>").append(generateTimeMillis()).append("</TimeStamp>")
			.append("<SystemId>").append("123").append("</SystemId>")
			.append("<ActionType>").append("0").append("</ActionType>")
			.append("<OrderId>").append("1210001").append("</OrderId>")
			.append("<ProdSpecCode>").append("2020966").append("</ProdSpecCode>")
			.append("<ProductNo>").append("13377050499").append("</ProductNo>")
			.append("<ProductOfferInfo>")
				.append("<ProductOfferType>").append("0").append("</ProductOfferType>")
				.append("<ProductOfferID>").append("2021146").append("</ProductOfferID>")
				.append("<EffDate>").append("2010-5-19").append("</EffDate>")
				.append("<ExpDate>").append("2011-5-19").append("</ExpDate>")
				.append("<VSubProdInfo>")
					.append("<VProductID>").append("331002").append("</VProductID>")
					.append("<EffDate>").append("2010-5-19").append("</EffDate>")
					.append("<ExpDate>").append("2011-5-19").append("</ExpDate>")
				.append("</VSubProdInfo>")
				.append("<VSubProdInfo>")
				.append("<VProductID>").append("331000").append("</VProductID>")
				.append("<EffDate>").append("2010-5-19").append("</EffDate>")
				.append("<ExpDate>").append("2011-5-19").append("</ExpDate>")
				.append("</VSubProdInfo>")
			.append("</ProductOfferInfo>")
		.append("</SubscribeToVSOPReq>");
		return bf.toString();
	}
	
	private long generateTimeMillis() {
		long l = System.currentTimeMillis();
		return l;
	}

	public String subInstQryFromVSOP(String inXml) throws RemoteException{
		SubInstQryFromVSOPReq subInstQryFromVSOPReq74 = new SubInstQryFromVSOPReq();
		String streamNo = "1";
		String timestamp = StringUtil.getInstance().getCurrentTimeStamp();
		String requ = inXml ;
		VsopServiceRequest param = new VsopServiceRequest();
		param.setRequest(requ);
		subInstQryFromVSOPReq74.setSubInstQryFromVSOPReq(param );
		System.out.println("request xml:" + requ);
		SubInstQryFromVSOPReqResponse resp = subsInstQrySVStub.subInstQryFromVSOP(subInstQryFromVSOPReq74);
		String ret = resp.getSubInstQryFromVSOPReqResponse().getResponse();
		logger.info("response xml:" + ret);
		ret=StringUtil.getInstance().getTagValue("SubInstQryFromVSOPResp", ret);
		
		return ret;
	}
//	public String subInstQryFromVSOP() throws RemoteException {
//		String inXml = createSubinstQryRequest();
//		return subInstQryFromVSOP(inXml );
//	}
	private String createSubinstQryRequest() {
		StringBuffer bf = new StringBuffer();
		bf.append("<msg>")
			.append("<StreamingNo>").append(VsopStreamNoHelper.getInstance().genReqStreamNo()).append("</StreamingNo>")
			.append("<TimeStamp>").append(generateTimeMillis()).append("</TimeStamp>")
			.append("<ProdSpecCode>").append("").append("</ProdSpecCode>")
			.append("<ProductNo>").append("13377050985").append("</ProductNo>")
		.append("</msg>")
		;
		return bf.toString();
	}

	public static void main(String[] args){
		try {
			new SubscribeServiceClient().subscribeTovsop();
//			new OrderServiceTest().subInstQryFromVSOP();
//			new OrderServiceTest().workListFKToVSOPTest();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 退订增值产品
	 * @param productIds 增值产品id字符串逗号分隔
	 * @param accNbr     产品号码，退订的产品号码，如手机号码
	 * @return
	 * @throws RemoteException 
	 */
	public String delProduct(String accNbr, String productIds) throws RemoteException{
		String requestXml = createDelProductRequest(accNbr,productIds);
		String resp = subscribeToVsop(requestXml);
		return resp;
	}

	private String createDelProductRequest(String accNbr, String productIds) {
		StringBuffer bf = new StringBuffer("");
		bf.append("<SubscribeToVSOPReq>")
			.append("<StreamingNo>").append(VsopStreamNoHelper.getInstance().genReqStreamNo()).append("</StreamingNo>")
			.append("<TimeStamp>").append(generateTimeMillis()).append("</TimeStamp>")
			.append("<SystemId>").append("121").append("</SystemId>")
			.append("<ActionType>").append("0").append("</ActionType>")
			.append("<OrderId>").append("1210001").append("</OrderId>")
			.append("<ProdSpecCode>").append("2020966").append("</ProdSpecCode>")
			.append("<ProductNo>").append(accNbr).append("</ProductNo>")
			.append("<ProductOfferInfo>")
				.append("<ProductOfferType>").append("0").append("</ProductOfferType>")
				.append("<ProductOfferID>").append("2021146").append("</ProductOfferID>")
				.append("<EffDate>").append("2010-5-19").append("</EffDate>")
				.append("<ExpDate>").append("2011-5-19").append("</ExpDate>")
				.append("<VSubProdInfo>")
					.append("<VProductID>").append("331002").append("</VProductID>")
					.append("<EffDate>").append("2010-5-19").append("</EffDate>")
					.append("<ExpDate>").append("2011-5-19").append("</ExpDate>")
				.append("</VSubProdInfo>")
				.append("<VSubProdInfo>")
				.append("<VProductID>").append("331000").append("</VProductID>")
				.append("<EffDate>").append("2010-5-19").append("</EffDate>")
				.append("<ExpDate>").append("2011-5-19").append("</ExpDate>")
				.append("</VSubProdInfo>")
			.append("</ProductOfferInfo>")
		.append("</SubscribeToVSOPReq>");
		return bf.toString();
	}
	private String createDelProductRequest_old(String accNbr, String productIds) {
		StringBuffer bf = new StringBuffer("");
		bf.append("<Order>")
			.append("<ActionType>").append("Del").append("</ActionType>")
			.append("<OrderId>").append(generateTimeMillis()).append("</OrderId>")
			.append("<SystemId>").append("101").append("</SystemId>")
			.append("<ProductNo>").append(accNbr).append("</ProductNo>")
			.append("<ProdSpecCode>").append("").append("</ProdSpecCode>")
			.append("<ProductId>").append("").append("</ProductId>")
			.append("<SubProdSpecInfo>")
				.append("<ActionType>").append("Del").append("</ActionType>")
				.append("<SubProdSpecCode>").append("spscode1").append("</SubProdSpecCode>")
				.append("<SubProdSpecId>").append("221000").append("</SubProdSpecId>")
			.append("</SubProdSpecInfo>");
			String[] productIdArray = productIds.split(",");
			for (int i = 0; i < productIdArray.length; i++) {
				String productId = productIdArray[i];
				
				bf.append("<SubProductInfo>")
				.append("<ActionType>").append("Del").append("</ActionType>")
				.append("<SPProdSpecCode>").append("").append("</SPProdSpecCode>")
				.append("<SPProdSpecID>").append(productId).append("</SPProdSpecID>")
				.append("<EffDate>").append("").append("</EffDate>")
				.append("<ExpDate>").append("").append("</ExpDate>")
				.append("</SubProductInfo>");
			}
			bf.append("<ProductOfferId>").append("44100").append("</ProductOfferId>")
			.append("<PackageId>").append("55100").append("</PackageId>")
			.append("<UserState>").append("00A").append("</UserState>")
		.append("</Order>");
		
		return bf.toString();
	}
	
	public String workListFKToVSOPTest() throws RemoteException{
		WorkListFKToVSOPReq workListFKToVSOPReq12 = new WorkListFKToVSOPReq();
		VsopServiceRequest param = new VsopServiceRequest();
		String requestXml = createWorkListFKToVSOPRequest();
		param.setRequest(requestXml );
		workListFKToVSOPReq12.setWorkListFKToVSOPReq(param );
		WorkListFKToVSOPReqResponse respObj = workSheetAcceptSVStub.workListFKToVSOP(workListFKToVSOPReq12);
		
		return respObj.getWorkListFKToVSOPReqResponse().getResponse();
	}

	private String createWorkListFKToVSOPRequest() {
		StringBuffer bf = new StringBuffer();
		bf.append("<msg>")
			.append("<StreamingNo>").append(VsopStreamNoHelper.getInstance().genReqStreamNo()).append("</StreamingNo>")
			.append("<TimeStamp>").append(generateTimeMillis()).append("</TimeStamp>")
			.append("<SystemId>").append("122").append("</SystemId>")
			.append("<OrderId>").append("").append("</OrderId>")
			.append("<ActionType>").append("").append("</ActionType>")
			.append("<PorductInstID>").append("").append("</PorductInstID>")
			.append("<ReginID>").append("").append("</ReginID>")
			.append("<ProdSpecCode>").append("").append("</ProdSpecCode>")
			.append("<ProductNo>").append("").append("</ProductNo>")
			.append("<OldProductNo>").append("").append("</OldProductNo>")
			.append("<ProductOfferId>").append("").append("</ProductOfferId>")
			.append("<UserState>").append("").append("</UserState>")
			.append("<UserPayType>").append("").append("</UserPayType>")
			.append("<VProductInfo>")
				.append("<ActionType>").append("").append("</ActionType>")
				.append("<VProductID>").append("").append("</VProductID>")
				.append("<VProductInstID>").append("").append("</VProductInstID>")
				.append("<EffDate>").append("").append("</EffDate>")
				.append("<ExpDate>").append("").append("</ExpDate>")
			.append("</VProductInfo>")
			.append("<AProductInfo>")
				.append("<ActionType>").append("").append("</ActionType>")
				.append("<AProductID>").append("").append("</AProductID>")
				.append("<AProductInstID>").append("").append("</AProductInstID>")
			.append("</AProductInfo>")
			
		.append("</msg>")
		;
		return bf.toString();
	}

}
