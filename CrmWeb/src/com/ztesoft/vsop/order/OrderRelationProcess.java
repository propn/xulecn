package com.ztesoft.vsop.order;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.CRMSynchUtil;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.InfOrderRelationVo;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.ismpSyn.vo.SubscriptionSyncReq;
import com.ztesoft.vsop.order.vo.MessSecondReplyVO;
import com.ztesoft.vsop.order.vo.SecondConfirmMsg;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.client.SoapClient;
import common.Logger;



public class OrderRelationProcess {
	
	private static Logger log = Logger.getLogger(OrderRelationProcess.class);
	
	public static OrderRelationISMPVO parseXml(Connection conn,String xmlStr) throws DocumentException, SQLException{
		
		long t1 = System.currentTimeMillis();
		
//		SAXReader saxReader = new SAXReader(false);
//		StringReader reader = new StringReader(xmlStr);
//		Document doc = saxReader.read(reader);
//		Element root = doc.getRootElement();
		
		String StreamingNo = XMLUtils.getSingleTagValue(xmlStr,"StreamingNo");
		String TimeStamp = XMLUtils.getSingleTagValue(xmlStr,"TimeStamp");
		String SystemId = XMLUtils.getSingleTagValue(xmlStr,"SystemId");
		String UserID = XMLUtils.getSingleTagValue(xmlStr,"UserID");
		String UserIDType = XMLUtils.getSingleTagValue(xmlStr,"UserIDType");
		String ProductNbr = XMLUtils.getSingleTagValue(xmlStr,"ProductID");
		String ProductID = "";
		String Scope = "";
		String spId = "";
		
		if(ProductNbr!=null&&!ProductNbr.equals("")){
			OrderRelationISMPDAO orderRelationISMPDAO = new OrderRelationISMPDAO();
			HashMap map = orderRelationISMPDAO.getProductId(ProductNbr,conn);
			ProductID = (String)map.get("PRODUCT_ID");
			Scope = (String)map.get("SCOPE");
			spId = (String)map.get("PRODUCT_PROVIDER_ID");
		}
		
		String OldProductNbr = XMLUtils.getSingleTagValue(xmlStr,"OldProductID");
		String OldProductID = "";
		if(OldProductNbr!=null&&!OldProductNbr.equals("")){
			OrderRelationISMPDAO orderRelationISMPDAO = new OrderRelationISMPDAO();
			HashMap map = orderRelationISMPDAO.getProductId(OldProductNbr,conn);
			OldProductID = (String)map.get("PRODUCT_ID");
		}
		
		String PackageID = XMLUtils.getSingleTagValue(xmlStr,"PackageID");
		if(PackageID!=null&&!PackageID.equals("")){
			OrderRelationISMPDAO orderRelationISMPDAO = new OrderRelationISMPDAO();
			HashMap map = orderRelationISMPDAO.getProductOfferId(PackageID,conn);
			PackageID = (String)map.get("PRODUCT_OFFER_ID");
		}		
		String OldpackageID = XMLUtils.getSingleTagValue(xmlStr,"OldpackageID");
		if(OldpackageID!=null&&!OldpackageID.equals("")){
			OrderRelationISMPDAO orderRelationISMPDAO = new OrderRelationISMPDAO();
			HashMap map = orderRelationISMPDAO.getProductOfferId(OldpackageID,conn);
			OldpackageID = (String)map.get("PRODUCT_OFFER_ID");
		}
		String PProductOfferID = XMLUtils.getSingleTagValue(xmlStr,"PProductOfferID");
		if(PProductOfferID!=null&&!PProductOfferID.equals("")){
			OrderRelationISMPDAO orderRelationISMPDAO = new OrderRelationISMPDAO();
			HashMap map = orderRelationISMPDAO.getProductOfferId(PProductOfferID,conn);
			PProductOfferID = (String)map.get("PRODUCT_OFFER_ID");
		}
		String OldPProductOfferID = XMLUtils.getSingleTagValue(xmlStr,"OldPProductOfferID");
		if(OldPProductOfferID!=null&&!OldPProductOfferID.equals("")){
			OrderRelationISMPDAO orderRelationISMPDAO = new OrderRelationISMPDAO();
			HashMap map = orderRelationISMPDAO.getProductOfferId(OldPProductOfferID,conn);
			OldPProductOfferID = (String)map.get("PRODUCT_OFFER_ID");
		}
		String OPType = XMLUtils.getSingleTagValue(xmlStr,"OPType");
		String EffDate = XMLUtils.getSingleTagValue(xmlStr,"EffDate");
		String ExpDate = XMLUtils.getSingleTagValue(xmlStr,"ExpDate");
		String VerUserId = XMLUtils.getSingleTagValue(xmlStr,"VerUserId");
		
		OrderRelationISMPVO orderRelationISMPVO = new OrderRelationISMPVO();
		orderRelationISMPVO.setStreamingNo(StreamingNo);
		orderRelationISMPVO.setUserID(UserID);
		orderRelationISMPVO.setUserIDType(UserIDType);
		orderRelationISMPVO.setProductID(ProductID);
		orderRelationISMPVO.setOldProductID(OldProductID);
		orderRelationISMPVO.setPackageID(PackageID);
		orderRelationISMPVO.setOldpackageID(OldpackageID);
		orderRelationISMPVO.setPProductOfferID(PProductOfferID);
		orderRelationISMPVO.setOldPProductOfferID(OldPProductOfferID);
		orderRelationISMPVO.setOPType(OPType);
		orderRelationISMPVO.setEffectiveTime(EffDate);
		orderRelationISMPVO.setExpireTime(ExpDate);
		orderRelationISMPVO.setVerUserId(VerUserId);
		orderRelationISMPVO.setSystemId(SystemId);
		orderRelationISMPVO.setScope(Scope);
		orderRelationISMPVO.setSpId(spId);
		
		long t2 = System.currentTimeMillis();
		log.info("OrderRelationProcess.parseXml====>" + (t2 - t1));
		
		return orderRelationISMPVO;
	}
	
	public static String process(String xmlStr) throws SQLException{
		OrderRelationISMPVO orderRelationISMPVO = null;
		String retXml = "";
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			orderRelationISMPVO = parseXml(conn,xmlStr);
		} catch (DocumentException e) {
			e.printStackTrace();
			orderRelationISMPVO = new OrderRelationISMPVO();
			retXml = getResult(orderRelationISMPVO.getStreamingNo(),"1");
			return retXml;
		}
		try{
			OrderRelationISMPDAO.saveOrderInfo(orderRelationISMPVO,orderRelationISMPVO.getSystemId(),conn);
			
			
			//增加一个写表的动作,填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1,add by qin.guoquan 2010-06-04,guangxi start
			//需要CRM同步的（1）并且动作时退订（3）或订购（0）
			if (null != CRMSynchUtil.CRM_SYN && "1".equals(CRMSynchUtil.CRM_SYN.trim()) 
					&& ("0".equals(orderRelationISMPVO.getOPType()) || "3".equals(orderRelationISMPVO.getOPType()))) {
				InfOrderRelationVo infOrderRelationVo = new InfOrderRelationVo();
				infOrderRelationVo.setUserId(orderRelationISMPVO.getUserID());
				infOrderRelationVo.setUserIdType(orderRelationISMPVO.getUserIDType());
				infOrderRelationVo.setProductId(orderRelationISMPVO.getProductID());
				infOrderRelationVo.setPackageId(orderRelationISMPVO.getPackageID());
				infOrderRelationVo.setOpType("0".equals(orderRelationISMPVO.getOPType()) ? "0" : "3");
				infOrderRelationVo.setState("U");
				infOrderRelationVo.setSendTimes("1");
				CRMSynchUtil.getInstance().writeInfOrderRelation(infOrderRelationVo);
			}
			//增加一个写表的动作,填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1,add by qin.guoquan 2010-06-04,guangxi end
			
			retXml = getResult(orderRelationISMPVO.getStreamingNo(),"0");	
			
			//如果是全网(0)的产品，则同步给集团VSOP
			if(null != orderRelationISMPVO.getScope() && "0".equals(orderRelationISMPVO.getScope())) {
				String xmlParams = appendXMLToGroupVsop(orderRelationISMPVO);
				try {
					SoapClient.getInstance().subsInstSynSV(xmlParams);//调用集团VSOP接口
				} catch (Exception e) {
					log.info("订购关系同步给集团VSOP失败!", e);
					e.printStackTrace();
					retXml = getResult(orderRelationISMPVO.getStreamingNo(),"1");
				}
				
			}			
		}
		catch(Exception se){
			LegacyDAOUtil.rollbackOnException(conn);
			retXml = getResult(orderRelationISMPVO.getStreamingNo(),"1");
			se.printStackTrace();
			log.error("",se);
			return retXml;
		}
		finally{
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}
		return retXml;
	}
	public String recvRQMessage(String xmlStr) throws DocumentException, SQLException{
		MessSecondReplyVO mesReplyVO = loadMesReplyVO(xmlStr);
		String retXml = "";
		OrderBo order = new OrderBo();
		//更新回复
		String orderId = order.updateSecondMsgAndReturnOrderId(mesReplyVO.getRQCode(), mesReplyVO.getRQResult());
		//回复期限处理
		String exptimeString = DcSystemParamManager.getParameter(VsopConstants.RECONFIRM_EXP_TIME);
		if(null == exptimeString || !"".equals(exptimeString)) exptimeString = "24";
		int expHour = Integer.parseInt(exptimeString);
		long expMillis = 60l * 60l * 1000 * expHour;
		SecondConfirmMsg secondMsg = order.getSecondConfirmMsg(mesReplyVO.getRQCode());
		String createDateString = secondMsg.getCreateDate();
		Timestamp tm = DAOUtils.parseTimestamp(createDateString);
		long createAt = tm.getTime();
		long nowAt = new Date().getTime();
		long millis = nowAt - createAt;
		//如果超过期限，则作废
		if((millis -expMillis) > 0){
			order.archivingSecondMsg(orderId);
			StringUtil su = StringUtil.getInstance();
			retXml = su.getVsopResponse("ReplyRQMsgToVSOPReq", 
										mesReplyVO.getStreamingNo(),
										"1",
								   		"短信回复不在期限内回复。", 
								   		null);
			return retXml;
		}
		boolean target = false;
		if("0".equals(mesReplyVO.getRQResult())){  //二次确认回复 否  不处理，直接返回成功
			target = order.reConfirmNo(orderId);
		}else{
			try {
				target = order.reConfirmYes(orderId);
			} catch (Exception e) {
				order.reConfirmException(mesReplyVO.getRQCode());
			}
		}

		if(target){
//			retXml = getResultByMes(mesReplyVO.getStreamingNo(),"0","处理成功!");
			StringUtil su = StringUtil.getInstance();
			retXml = su.getVsopResponse("ReplyRQMsgToVSOPReq", 
										mesReplyVO.getStreamingNo(),
										"0",
								   		"处理成功!", 
								   		null);
		}else{
//			retXml = getResultByMes(mesReplyVO.getStreamingNo(),"1","处理失败，确认信息还没有更新!");
			StringUtil su = StringUtil.getInstance();
			retXml = su.getVsopResponse("ReplyRQMsgToVSOPReq", 
										mesReplyVO.getStreamingNo(),
										"1",
								   		"处理失败，确认信息还没有更新!!", 
								   		null);
		}
		return retXml;
	}
	public static String getResult(String streamingNo,String resultCode){
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
		String resultName = "";
		if(resultCode.equals("0")) resultName = "成功";
		else resultName = "失败";
		bf.append("<SubsInstSynFromISMPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultName).append("</ResultDesc>")
		.append("</SubsInstSynFromISMPResp>");
		retXml = bf.toString();
		return retXml;
	}	
	/**
	 * 解析二次短信确认回复报文
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public static MessSecondReplyVO loadMesReplyVO(String xml) throws DocumentException{
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xml);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		Element sessionBody = root.element("SessionBody");
		Element req = sessionBody.element("ReplyRQMsgToVSOPReq");
		MessSecondReplyVO mesReplyVO = new MessSecondReplyVO();
		mesReplyVO.setStreamingNo( req.element("StreamingNo").getStringValue());
		mesReplyVO.setTimeStamp( req.element("TimeStamp").getStringValue());
		mesReplyVO.setSystemId( req.element("SystemId").getStringValue());
		mesReplyVO.setProdSpecCode(req.element("ProdSpecCode").getStringValue());
		mesReplyVO.setProductNo(req.element("ProductNo").getStringValue());
		mesReplyVO.setRQCode(req.element("RQCode").getStringValue());
		mesReplyVO.setRQResult(req.element("RQResult").getStringValue());
		return mesReplyVO;
	}
	public static String getResultByMes(String streamingNo,
										String resultCode,
										String resultDesc){
		String retXml = "";
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<Request>")
		.append("<ReplyRQMsgToVSOPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultDesc).append("</ResultDesc>")
		.append("</ReplyRQMsgToVSOPResp>")
		.append("</Request>");
		retXml = bf.toString();
		return retXml;
	}	
	public static void main(String[] args) throws DocumentException, SQLException{
		
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<SubsInstSynFromISMPReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("2010-04-13 00:00:00").append("</TimeStamp>")
			.append("<SystemId>").append("204").append("</SystemId>")
			.append("<UserID>").append("13377050985").append("</UserID>")
			.append("<UserIDType>").append("0").append("</UserIDType>")
			.append("<ProductID>").append("2021145").append("</ProductID>")
			.append("<OldProductID>").append("2021146").append("</OldProductID>")
			.append("<PackageID>").append("").append("</PackageID>")
			.append("<OldpackageID>").append("").append("</OldpackageID>")
			.append("<PProductOfferID>").append("").append("</PProductOfferID>")
			.append("<OldPProductOfferID>").append("").append("</OldPProductOfferID>")
			.append("<OPType>").append("6").append("</OPType>")
			.append("<EffDate>").append("").append("</EffDate>")
			.append("<ExpDate>").append("").append("</ExpDate>")
			.append("<VerUserId>").append("0").append("</VerUserId>")		
		.append("</SubsInstSynFromISMPReq>");
		
		System.out.println(bf.toString());
		//process(bf.toString());
		
	}
	
	/**
	 * 将同步订购关系所需的相关信息拼装成xml字符串，作为参数调以用集团VSOP的接口
	 * @param orderRelationISMPVO
	 * @return
	 */
	public static String appendXMLToGroupVsop(OrderRelationISMPVO orderRelationISMPVO) {
		
		String streamingNo = VsopStreamNoHelper.getInstance().genReqStreamNo();
		String timeStamp = DateUtil.formatDate(CrmConstants.DATE_FORMAT_8);//yyyymmdd
		
		//判断增值产品类型，和销售品编号
		String prodOfferType = "0";//默认为单增值
		String prodOfferId = orderRelationISMPVO.getProductID();
		
		String packageId = orderRelationISMPVO.getPackageID();
		if(null != packageId && !"".equals(packageId)){
			prodOfferType = "1";//纯增值
			prodOfferId = packageId;
		}
		
		String ppId = orderRelationISMPVO.getPProductOfferID();
		if(null != ppId && !"".equals(ppId)){
			prodOfferType = "2";//传统+增值
			prodOfferId = ppId;
		}
			
		String state = "0";//除了00X其他的状态均标志为0，正常
		if(null != orderRelationISMPVO.getOPType() && "3".equals(orderRelationISMPVO.getOPType())) state="6";
		
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<SubsInstSynBWVSOPReq>")
			.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
			.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
			.append("<SystemId>").append("200").append("</SystemId>")
			.append("<ProdSpecCode>").append(orderRelationISMPVO.getProductID()).append("</ProdSpecCode>")
			.append("<ProductNo>").append(orderRelationISMPVO.getUserID()).append("</ProductNo>")
			.append("<VproductID>").append(orderRelationISMPVO.getProductID()).append("</VproductID>")
			.append("<SPID>").append(orderRelationISMPVO.getSpId()).append("</SPID>")
			.append("<ProductOfferType>").append(prodOfferType).append("</ProductOfferType>")
			.append("<ProductOfferID>").append(prodOfferId).append("</ProductOfferID>")
			.append("<Status>").append(state).append("</Status>")
			.append("<SubscribeTime>").append(orderRelationISMPVO.getEffectiveTime()).append("</SubscribeTime>")
			.append("<EffDate>").append(orderRelationISMPVO.getEffectiveTime()).append("</EffDate>")
			.append("<ExpDate>").append(orderRelationISMPVO.getExpireTime()).append("</ExpDate>")
			.append("<ChannelPlayerID>").append(orderRelationISMPVO.getSystemId()).append("</ChannelPlayerID>")		
			.append("</SubsInstSynBWVSOPReq>");
		
		return sb.toString();
	}
	/**
	 * 广西本地化 广西ismp方向同步订购关系数据到vsop ismp保留反向同步给crm的接口
	 * @param req
	 * @return
	 * @throws SQLException
	 */
	public static com.ztesoft.vsop.ismpSyn.vo.Response synIsmpToVsopGXProc(SubscriptionSyncReq req) throws SQLException{
		Connection conn = LegacyDAOUtil.getConnection();
		com.ztesoft.vsop.ismpSyn.vo.Response resp = new com.ztesoft.vsop.ismpSyn.vo.Response();
		try{
			OrderRelationISMPDAO.saveOrderInfoIsmpGX(req,"204",conn);
			
			
			//增加一个写表的动作,填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1,add by qin.guoquan 2010-06-04,guangxi start
			//需要CRM同步的（1）并且动作时退订（3）或订购（0）
			if (null != CRMSynchUtil.CRM_SYN && "1".equals(CRMSynchUtil.CRM_SYN.trim()) 
					&& (0 == req.getOPType() || 3 == req.getOPType())) {
				InfOrderRelationVo infOrderRelationVo = new InfOrderRelationVo();
				infOrderRelationVo.setUserId(req.getUserID());
				infOrderRelationVo.setUserIdType(req.getUserIDType()+"");
				infOrderRelationVo.setProductId(req.getProductID());
				infOrderRelationVo.setPackageId(req.getPackageID());
				infOrderRelationVo.setOpType(0 == req.getOPType() ? "0" : "3");
				infOrderRelationVo.setState("U");
				infOrderRelationVo.setSendTimes("1");
				CRMSynchUtil.getInstance().writeInfOrderRelation(infOrderRelationVo);
			}
			//增加一个写表的动作,填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1,add by qin.guoquan 2010-06-04,guangxi end
			resp.setStreamingNo(req.getStreamingNo());
			resp.setResultCode(0);
//			retXml = getResult(req.getStreamingNo(),"0");	
			/*
			//如果是全网(0)的产品，则同步给集团VSOP
			if(null != req.getScope() && "0".equals(req.getScope())) {
				String xmlParams = appendXMLToGroupVsop(req);
				try {
					SoapClient.getInstance().subsInstSynSV(xmlParams);//调用集团VSOP接口
				} catch (Exception e) {
					log.info("订购关系同步给集团VSOP失败!", e);
					e.printStackTrace();
					retXml = getResult(orderRelationISMPVO.getStreamingNo(),"1");
				}
				
			}*/		
		}
		catch(Exception se){
			LegacyDAOUtil.rollbackOnException(conn);
			resp.setResultCode(1);
			se.printStackTrace();
			log.error("",se);
			return resp;
		}
		finally{
			LegacyDAOUtil.commitAndCloseConnection(conn);
		}
		return resp;
	}
}
