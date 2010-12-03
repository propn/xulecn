package com.ztesoft.vsop.order;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.crm.product.dao.ProductCatgItemDAO;
import com.ztesoft.crm.product.vo.ProdCatgItemVO;
import com.ztesoft.vsop.CRMSynchUtil;
import com.ztesoft.vsop.ConstantsState;
import com.ztesoft.vsop.InfOrderRelationVo;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.order.dao.InfOrderRelationDao;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.exception.EmptyPayTypeException;
import com.ztesoft.vsop.order.exception.EmptyRequestException;
import com.ztesoft.vsop.order.exception.ProductHasNoPlatformException;
import com.ztesoft.vsop.order.exception.ProductInstanceIdNotExists;
import com.ztesoft.vsop.order.exception.VerifyException;
import com.ztesoft.vsop.order.vo.AProduct;
import com.ztesoft.vsop.order.vo.AProductInfo;
import com.ztesoft.vsop.order.vo.InfMsg;
import com.ztesoft.vsop.order.vo.OrderInfoResponse;
import com.ztesoft.vsop.order.vo.ProductOfferInfo;
import com.ztesoft.vsop.order.vo.SecondConfirmMsg;
import com.ztesoft.vsop.order.vo.UserInfoSyncMsg;
import com.ztesoft.vsop.order.vo.VProductInfo;
import com.ztesoft.vsop.order.vo.VSubProdInfo;
import com.ztesoft.vsop.order.vo.request.SubInstHisQueryRequest;
import com.ztesoft.vsop.order.vo.request.SubInstQueryRequest;
import com.ztesoft.vsop.order.vo.request.SubscribeToVSOPRequest;
import com.ztesoft.vsop.order.vo.request.WorkListFKToVSOPRequest;
import com.ztesoft.vsop.order.vo.response.BaseResponse;
import com.ztesoft.vsop.order.vo.response.SubInstHisQueryResponse;
import com.ztesoft.vsop.order.vo.response.SubInstQueryResponse;
import com.ztesoft.vsop.order.vo.response.SubscribeToVSOPResp;
import com.ztesoft.vsop.order.vo.response.WorkListFKToVSOPResp;
import com.ztesoft.vsop.provinceUtil.AppendLanCode;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.client.SoapClient;

/**
 * 订购业务类 处理订购、变更、退订
 * 
 * @author yi.chengfeng Mar 3, 2010 9:44:14 AM
 * 
 */
public class OrderBo {

	private static Logger logger = Logger.getLogger(OrderBo.class);
	private OrderDao orderDao;
	private InfOrderRelationDao infOrderRelationDao;// add by qin.guoquan at

	// GuangXi on 2010-06-04

	public OrderBo() {
		orderDao = new OrderDao();
		infOrderRelationDao = new InfOrderRelationDao();// add by qin.guoquan at
		// GuangXi on 2010-06-04
	}

	/**
	 * 订购、退订方法
	 * 
	 * @param requestXML
	 * @param requireVerify
	 *            是否需要鉴权
	 * @param needReconfirm
	 *            是否需要二次确认
	 * @return
	 */
	public String subscribeToVSOP(String requestXML, boolean requireVerify,
			boolean needReconfirm) {
		// 解析XML
		String streamNo = "";
		if (requestXML == null || "".equals(requestXML.trim())) {
			logger.info("requestXML is null or empty string!");
			return new SubscribeToVSOPResp(SubscribeToVSOPResp.RESULT_FAILURE,
					"request xml is null or empty string!", "").toXml();
		}
		SubscribeToVSOPRequest order = new SubscribeToVSOPRequest();
		long s = System.currentTimeMillis();
		try {
			String xmlParseType = DcSystemParamManager
					.getParameter(VsopConstants.XML_PARSE_TYPE);
			if ("string".equalsIgnoreCase(xmlParseType)) {
				order.loadFromXML(requestXML, "string parse");
			} else {
				order.loadFromXML(requestXML);
			}
			logger.info("parse xml cost:" + (System.currentTimeMillis() - s));
			streamNo = order.getStreamingNo();
		} catch (DocumentException e) {
			logger.error("subscribeToVSOP ex.", e);
			return new SubscribeToVSOPResp(e, order.getStreamingNo()).toXml();
		} catch (Exception e) {
			logger.error("subscribeToVSOP ex.", e);
			return new SubscribeToVSOPResp(e, order.getStreamingNo()).toXml();
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		SubscribeToVSOPResp subResp = new SubscribeToVSOPResp(
				SubscribeToVSOPResp.RESULT_SUCCESS, "", streamNo);
		s = System.currentTimeMillis();
		try {
			if ("".equals(order.getProdInstId())) {
				String[] prodInstIdAndLanCode = orderDao.getProductInstId(order
						.getProductNo(), order.getProdSpecCode());
				order.setProdInstId(prodInstIdAndLanCode[0]);
				order.setLanCode(prodInstIdAndLanCode[1]);
				order.setUserState(prodInstIdAndLanCode[2]);
				order.setPayMode(prodInstIdAndLanCode[3]);
				// order.setProductId(prodInstIdAndLanCode[4]);
			}
			if ("".equals(order.getProdInstId()))
				throw new ProductInstanceIdNotExists(
						"product instance not exists,please check.");
		} catch (Exception ex) {
			return new SubscribeToVSOPResp(ex, streamNo).toXml();
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		// 需要鉴权则对数据鉴权
		if (requireVerify) {
			boolean valid = false;
			IOrderVeriFy orderVerify = new SimpleOrderVerify();
			try {
				valid = orderVerify.auth(order, subResp);
			} catch (Exception e) {
				logger.error("#subscribeToVSOP ex:", e);
				e.printStackTrace();
				return new SubscribeToVSOPResp(new VerifyException(
						"auth fail,SQLException."), streamNo).toXml();
			}
			if (!valid) {
				subResp.setResultCode(SubscribeToVSOPResp.RESULT_FAILURE);
				return subResp.toXml();
			}
		}
		logger.info(" auth total cost:" + (System.currentTimeMillis() - s));
		s = System.currentTimeMillis();
		// 生成订单和订单项，保存订购关系
		try {
			String orderType = order.getActionType();
			needConfirm(order, needReconfirm);
			if (OrderConstant.orderTypeOfAdd.equals(orderType)) {
				doAddOrderAction(order);
			} else if (OrderConstant.orderTypeOfDel.equals(orderType)) {
				doDelOrderAction(order);
			} else if (OrderConstant.orderTypeOfAll.equals(orderType)) {
				doAllOrderAction(order);
			}
			logger.info("order process cost:"
					+ (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();

			// 二次确认处理
			reconfirmHandle(order, needReconfirm, requestXML);
			logger.info("reconfirm cost:" + (System.currentTimeMillis() - s));

			// 填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1, //add by qin.guoquan at
			// GuangXi on 2010-06-04
			if (null != CRMSynchUtil.CRM_SYN
					&& "1".equals(CRMSynchUtil.CRM_SYN.trim()))
				writeInfOrderRelation(order);
			// end
		} catch (Exception e) {
			logger.error("subscribeToVSOP Exception: ", e);
			LegacyDAOUtil.rollbackOnException();
			return new SubscribeToVSOPResp(e, streamNo).toXml();
		}

		finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		// 返回信息
		String returnXml = subResp.toXml();
		return returnXml;
	}

	/**
	 * 增加一个写表的动作,填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1 add by qin.guoquan at
	 * GuangXi on 2010-06-04
	 * 
	 * @param order
	 */
	private void writeInfOrderRelation(SubscribeToVSOPRequest order) {
		if (null != order.getProductOfferInfo()) {
			for (Iterator productOfferItr = order.getProductOfferInfo()
					.iterator(); productOfferItr.hasNext();) {
				ProductOfferInfo productOffer = (ProductOfferInfo) productOfferItr
						.next();
				for (Iterator vSubProdInfos = productOffer
						.getVSubProdInfoList().iterator(); vSubProdInfos
						.hasNext();) {
					VSubProdInfo vSubProdInfo = (VSubProdInfo) vSubProdInfos
							.next();

					if ("0".equals(vSubProdInfo.getProductOfferType())) {// 当销售品类型为0：纯增值销售品时,只有一个增值产品
						InfOrderRelationVo infOrderRelationVo = new InfOrderRelationVo();
						infOrderRelationVo.setUserId(order.getProductNo());
						infOrderRelationVo.setUserIdType(infOrderRelationDao
								.getUserIdType(order.getProdSpecCode()));
						infOrderRelationVo.setProductId(vSubProdInfo
								.getVProductID());
						infOrderRelationVo.setPackageId(null);
						infOrderRelationVo.setOpType("0".equals(order
								.getActionType()) ? "0" : "3");
						infOrderRelationVo.setState("U");
						infOrderRelationVo.setSendTimes("1");
						CRMSynchUtil.getInstance().writeInfOrderRelation(
								infOrderRelationVo);
					} else {
						InfOrderRelationVo infOrderRelationVo = new InfOrderRelationVo();
						infOrderRelationVo.setUserId(order.getProductNo());
						infOrderRelationVo.setUserIdType(infOrderRelationDao
								.getUserIdType(order.getProdSpecCode()));
						infOrderRelationVo.setProductId(null);// 当为捆绑销售品时productId为空
						infOrderRelationVo.setPackageId(vSubProdInfo
								.getProductOfferNbr());
						infOrderRelationVo.setOpType("0".equals(order
								.getActionType()) ? "0" : "3");
						infOrderRelationVo.setState("U");
						infOrderRelationVo.setSendTimes("1");
						CRMSynchUtil.getInstance().writeInfOrderRelation(
								infOrderRelationVo);
					}
				}
			}
		} else {
			InfOrderRelationVo infOrderRelationVo = new InfOrderRelationVo();
			infOrderRelationVo.setUserId(order.getProductNo());
			infOrderRelationVo.setUserIdType(infOrderRelationDao
					.getUserIdType(order.getProdSpecCode()));
			infOrderRelationVo
					.setOpType("0".equals(order.getActionType()) ? "0" : "3");
			infOrderRelationVo.setState("U");
			infOrderRelationVo.setSendTimes("1");
			CRMSynchUtil.getInstance()
					.writeInfOrderRelation(infOrderRelationVo);
		}
	}

	/**
	 * 退订增值产品
	 * 
	 * @param order
	 * @throws SQLException
	 */
	private void doDelOrderAction(SubscribeToVSOPRequest order)
			throws SQLException {

		Connection conn = LegacyDAOUtil.getConnection();
		try {
			// 保存订单
			if (order.isNeedSendConfirm())
				order.setState(OrderConstant.orderStateOfUnconfirm);
			else
				order.setState(OrderConstant.orderStateOfCreated);
			orderDao.saveOrderAndOrderItems(order);
			orderDao.delOrder(order, conn);
		} catch (SQLException e) {
			logger.error("#doDelOrderAction ex:", e);
			LegacyDAOUtil.rollbackOnException(conn);
			throw e;
		}
	}

	/**
	 * 全部退订All
	 * 
	 * @param order
	 * @throws SQLException
	 * @throws FrameException
	 */
	private void doAllOrderAction(SubscribeToVSOPRequest order)
			throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			// 保存订单，订单状态为未确定
			order.setState(OrderConstant.orderStateOfCreated);
			orderDao.saveOrderAndOrderItems(order);
			// 更新增值产品状态为失效
			order.setState(OrderConstant.orderStateOfDelete);
			orderDao.updateIncProductState(order, conn);
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException(conn);
			logger.error("#doAllOrderAction ex:", e);
			throw e;
		}
	}

	/**
	 * 记录订单和增加增值产品实例
	 * 
	 * @param order
	 * @throws FrameException
	 * @throws FrameException
	 * @throws FrameException
	 * @throws ProductInstanceIdNotExists
	 * @throws SQLException
	 */
	private void doAddOrderAction(SubscribeToVSOPRequest order)
			throws RuntimeException, FrameException, ProductInstanceIdNotExists {
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			// 保存订单
			if (order.isNeedSendConfirm())
				order.setState(OrderConstant.orderStateOfUnconfirm);
			else
				order.setState(OrderConstant.orderStateOfCreated);
			// 保存增值产品
			orderDao.saveOrderRelation(order, conn);
			orderDao.saveOrderAndOrderItems(order);
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException(conn);
			e.printStackTrace();
			logger.error("#doAddOrderAction ex.", e);
			throw new RuntimeException("SQLException");
		}
	}

	/**
	 * 二次确认
	 * 
	 * @param order
	 * @param needComfirm
	 * @param requestXML
	 * @return
	 * @throws Exception
	 */
	private void reconfirmHandle(SubscribeToVSOPRequest order,
			boolean needComfirm, String requestXML) throws Exception {
		// 需要二次确认则通过短信平台发送短信确认
		if (needComfirm && order.isNeedSendConfirm()) {
			logger.info("#reconfirm needConfirm->" + needComfirm);
			String sendMsgPerVproduct = DcSystemParamManager
					.getParameter(VsopConstants.SEND_RECONFIRM_MSG_TYPE);
			logger.info("#reconfirm SEND_RECONFIRM_MSG_TYPE->"
					+ sendMsgPerVproduct);
			if (null != sendMsgPerVproduct
					&& "vproduct".equalsIgnoreCase(sendMsgPerVproduct)) {
				// 逐个增值产品发送
				sendMsgPerVproduct(order);
			} else if (null != sendMsgPerVproduct
					&& "productoffer".equalsIgnoreCase(sendMsgPerVproduct)) {
				sendMsgPerOffer(order);
			} else {
				// 整个订单一次发送
				sendMsgPerOrder(order, order.orderProductstoXml());
			}
		}// 否则给激活模块送订单
		else {
			createOrderForSPI(order);
		}
	}

	/**
	 * 逐个销售品发二次确认短信
	 * 
	 * @param order
	 * @throws Exception
	 * @throws SQLException
	 * @throws RemoteException
	 */
	private void sendMsgPerOffer(SubscribeToVSOPRequest order)
			throws RemoteException, SQLException, Exception {
		List prodofferList = order.getProductOfferInfo();
		for (Iterator pofferIter = prodofferList.iterator(); pofferIter
				.hasNext();) {
			ProductOfferInfo prodOffer = (ProductOfferInfo) pofferIter.next();
			SubscribeToVSOPRequest tmpOrder = new SubscribeToVSOPRequest();
			tmpOrder.setActionType(order.getActionType());
			tmpOrder.setStreamingNo(order.getStreamingNo());
			tmpOrder.setTimeStamp(order.getTimeStamp());
			tmpOrder.setOrderId(order.getOrderId());
			tmpOrder.setSystemId(order.getSystemId());
			tmpOrder.setProdSpecCode(order.getProdSpecCode());
			tmpOrder.setProductNo(order.getProductNo());
			tmpOrder.setSequence(order.getSequence());
			tmpOrder.setProdInstId(order.getProdInstId());
			List tempProdOfferList = new ArrayList();
			tempProdOfferList.add(prodOffer);
			tmpOrder.setProductOfferInfo(tempProdOfferList);
			sendMsgPerOrder(tmpOrder, tmpOrder.orderProductstoXml());
		}
	}

	/**
	 * 以增值产品来发二次确认短信
	 * 
	 * @param order
	 * @throws SQLException
	 * @throws RemoteException
	 * @throws Exception
	 */
	private void sendMsgPerVproduct(SubscribeToVSOPRequest order)
			throws SQLException, RemoteException, Exception {
		List prodofferList = order.getProductOfferInfo();
		for (Iterator pofferIter = prodofferList.iterator(); pofferIter
				.hasNext();) {
			ProductOfferInfo prodOffer = (ProductOfferInfo) pofferIter.next();
			List vsubProdList = prodOffer.getVSubProdInfoList();
			for (Iterator vproditer = vsubProdList.iterator(); vproditer
					.hasNext();) {
				VSubProdInfo vprod = (VSubProdInfo) vproditer.next();
				SubscribeToVSOPRequest vsubOrder = new SubscribeToVSOPRequest();
				vsubOrder.setActionType(order.getActionType());
				vsubOrder.setStreamingNo(order.getStreamingNo());
				vsubOrder.setTimeStamp(order.getTimeStamp());
				vsubOrder.setOrderId(order.getOrderId());
				vsubOrder.setSystemId(order.getSystemId());
				vsubOrder.setProdSpecCode(order.getProdSpecCode());
				vsubOrder.setProductNo(order.getProductNo());
				vsubOrder.setSequence(order.getSequence());
				List tempProdOfferList = new ArrayList();
				ProductOfferInfo tmpProdOffer = new ProductOfferInfo();
				tmpProdOffer.setActionType(prodOffer.getActionType());
				tmpProdOffer.setProductOfferType(prodOffer
						.getProductOfferType());
				tmpProdOffer.setProductOfferID(prodOffer.getProductOfferID());
				tmpProdOffer.setEffDate(prodOffer.getEffDate());
				tmpProdOffer.setExpDate(prodOffer.getExpDate());
				List tmpSubList = new ArrayList();
				tmpSubList.add(vprod);
				tmpProdOffer.setVSubProdInfoList(tmpSubList);
				tempProdOfferList.add(tmpProdOffer);
				vsubOrder.setProductOfferInfo(tempProdOfferList);
				// String rqcode = order.getSequence() + vprod.getVProductID();
				sendMsgPerOrder(vsubOrder, vsubOrder.orderProductstoXml());
			}
		}
	}

	private void sendMsgPerOrder(SubscribeToVSOPRequest order, String requestXML)
			throws SQLException, RemoteException, Exception {
		String rqcode = orderDao.createRQCode();
		String replayXml = new OrderMessConfirmBO().createRequestXml(order
				.getStreamingNo(), order.getSystemId(),
				order.getProdSpecCode(), order.getProductNo(), order
						.getProductNo(), "rqContent");
		orderDao.saveToSecondConfirmMsg(rqcode, String.valueOf(order
				.getSequence()), requestXML, replayXml, order.getProductNo());

		// 异步处理二次确认,下面代码去掉
		/*
		 * String ret = new
		 * OrderMessConfirmBO().sendSecondMesComfirm(order.getStreamingNo(),
		 * order.getSystemId(), order.getProdSpecCode(), order.getProductNo(),
		 * rqcode, ""); String resultCode =
		 * StringUtil.getInstance().getTagValue("ResultCode", ret);
		 * if(!BaseResponse.RESULT_SUCCESS.equals(resultCode)) { String
		 * resultDesc = StringUtil.getInstance().getTagValue("ResultDesc", ret);
		 * throw new Exception(resultDesc); }
		 */
	}

	private void needConfirm(SubscribeToVSOPRequest order, boolean needReconfirm) {
		// 只有订购需要二次确认，退订和退订全部不需要
		if (needReconfirm
				&& OrderConstant.orderTypeOfAdd.equals(order.getActionType())) {
			String needSystemIds = DcSystemParamManager
					.getParameter(VsopConstants.RECONFIRM_SYSTEM);// "211,212,214,215";
			if (null != needSystemIds
					&& needSystemIds.indexOf(order.getSystemId()) != -1) {
				order.setNeedSendConfirm(true);
			}
		}
	}

	/**
	 * 给激活模块生成订单
	 * 
	 * @throws ProductHasNoPlatformException
	 * @throws SQLException
	 */
	private void createOrderForSPI(SubscribeToVSOPRequest order)
			throws ProductHasNoPlatformException, SQLException {
		// 创建适合的xml
		logger.info("createOrderForSPI start");
		try {
			String spiXML = createSPIXML(order);
			logger.info(spiXML);
			// 保存到数据库
			long inOrderId = order.getSequence();
			String unuseClob = DcSystemParamManager
					.getParameter(VsopConstants.UNUSE_CLOB);
			if ("true".equalsIgnoreCase(unuseClob)) {
				orderDao.saveXMLWithoutClob(spiXML, order.getSystemId(),
						inOrderId, order.getProductNo(), order.getLanCode());
			} else {
				orderDao.saveXML(spiXML, order.getSystemId(), inOrderId, order
						.getProductNo(), order.getLanCode());
			}
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException();
			logger.error("#createOrderForSPI ex.", e);
			throw e;
		}
	}

	private String createSPIXML(SubscribeToVSOPRequest order)
			throws SQLException, ProductHasNoPlatformException {
		StringBuffer bf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<root>").append("<msg_head>").append("<from>").append(
				order.getSystemId()).append("</from>").append("<to>").append(
				"VSOP").append("</to>").append("<msg_type>").append(
				"message_request").append("</msg_type>").append("<serial>")
				.append(order.getStreamingNo()).append("</serial>").append(
						"<out_order_type>").append("VSOP001").append(
						"</out_order_type>").append("</msg_head>")

				.append("<interface_msg>").append("<public>").append(
						"<order_id>").append(order.getSequence()).append(
						"</order_id>").append("<out_order_id>").append(
						order.getOrderId()).append("</out_order_id>").append(
						"<order_act_type>").append(order.getActionType())
				.append("</order_act_type>").append("</public>")

				.append("<user_info>").append("<pproductoffer_info>").append(
						"<ppproduct_act_type>").append(order.getActionType())
				.append("</ppproduct_act_type>")
				.append("</pproductoffer_info>").append("<prod_info>").append(
						"<prod_no>").append(order.getProductNo()).append(
						"</prod_no>").append("<prod_type>").append(
						order.getProdSpecCode()).append("</prod_type>").append(
						"<area_code>").append(order.getLanCode()).append(
						"</area_code>").append("<prod_inst_id>").append(
						order.getProdInstId()).append("</prod_inst_id>")
				.append("</prod_info>");

		String subProdXml = createSubProductsXml(order);
		bf.append(subProdXml);
		bf.append("</user_info>").append("</interface_msg>").append("</root>");
		return bf.toString();
	}

	private String createSubProductsXml(SubscribeToVSOPRequest order)
			throws SQLException, ProductHasNoPlatformException {
		StringBuffer subProductNodeBuffer = new StringBuffer();
		subProductNodeBuffer.append("<sub_products>");
		List productIdArray = new ArrayList();
		Map prodHolder = new HashMap();
		List products = order.getProductOfferInfo();
		List vProductList = new ArrayList();
		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
			ProductOfferInfo productOffer = (ProductOfferInfo) iterator.next();
			vProductList.addAll(productOffer.getVSubProdInfoList());
		}
		for (Iterator iterator = vProductList.iterator(); iterator.hasNext();) {
			VSubProdInfo p = (VSubProdInfo) iterator.next();
			String productId = p.getVProductID();
			productIdArray.add(productId);
			prodHolder.put(productId, p);
		}
		String productids = joinStringList(productIdArray);
		logger.info("createSubProductsXml productids: " + productids);
		Map[] ret = null;
		ret = orderDao.findPlatform(productids);
		if (ret != null && ret.length == 2) {
			// 生成control节点
			Map prodMapPlamform = ret[0];
			logger.info("create control node.");
			subProductNodeBuffer.append("<control>");
			int i = 0;
			for (Iterator iterator = vProductList.iterator(); iterator
					.hasNext();) {
				VSubProdInfo p = (VSubProdInfo) iterator.next();
				String productId = p.getVProductID();
				List plamforms = (List) prodMapPlamform.get(productId);
				if (plamforms == null)
					throw new ProductHasNoPlatformException(
							"cannot find any platform for product->"
									+ productId);
				if (i == 0) {
					subProductNodeBuffer.append(joinStringList(plamforms));
				} else {
					subProductNodeBuffer.append(";").append(
							joinStringList(plamforms));
				}
				i++;
			}
			subProductNodeBuffer.append("</control>");
			logger.info("create control node done.");
			// 生成业务平台N编码节点
			Map PlamformMapProd = ret[1];
			Set plamformIdSet = PlamformMapProd.keySet();
			logger.info("create sub plamform node.");
			for (Iterator iterator = plamformIdSet.iterator(); iterator
					.hasNext();) {
				String plamformId = (String) iterator.next();
				List productIdList = (List) PlamformMapProd.get(plamformId);
				subProductNodeBuffer.append("<p").append(plamformId)
						.append(">");
				for (Iterator productItr = productIdList.iterator(); productItr
						.hasNext();) {
					String productId = (String) productItr.next();
					VSubProdInfo p = (VSubProdInfo) prodHolder.get(productId);
					String tmp = p.toXmlForSpi();
					subProductNodeBuffer.append(tmp);
				}

				subProductNodeBuffer.append("</p").append(plamformId).append(
						">");
			}
			logger.info("create sub plamform node end.");
		}
		subProductNodeBuffer.append("</sub_products>");
		return subProductNodeBuffer.toString();
	}

	private String joinStringList(List productIdArray) {
		int indx = 0;
		StringBuffer bf = new StringBuffer("");
		for (Iterator iterator = productIdArray.iterator(); iterator.hasNext();) {
			String tmp = (String) iterator.next();
			if (indx == 0) {
				bf.append(tmp);
			} else {
				bf.append(",").append(tmp);
			}
			indx++;
		}
		return bf.toString();
	}

	/**
	 * 从接口消息表获取待处理的记录
	 * 
	 * @param threadId
	 * @param size
	 * @return
	 */
	public List getUnDealOrders(int limit, String threadId) {
		List ret = null;
		try {
			ret = orderDao.getUnDealOrders(limit, threadId);
		} catch (SQLException e) {
			logger.info("#getUnDealOrders ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}

	public void successInfMsg(InfMsg infMsg) {
		try {
			orderDao.updateInfMsgState(infMsg.getInfMagId(), true, "");
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
			logger.info("#successInfMsg ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	public void failInfMsg(InfMsg infMsg) {
		try {
			orderDao.updateInfMsgState(infMsg.getInfMagId(), false, infMsg
					.getResultMsg());
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
			logger.info("#successInfMsg ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	/**
	 * 从定时任务过来的订单处理(目前是服务开通系统)
	 * 
	 * @param requestXML
	 * @return
	 * @throws Exception
	 * @throws EmptyPayTypeException
	 * @throws ProductInstanceIdNotExists
	 * @throws FrameException
	 * @throws RuntimeException
	 * @throws ProductHasNoPlatformException
	 */
	public String sendOrderToActive_FK(String requestXML) throws Exception {
		WorkListFKToVSOPRequest order = new WorkListFKToVSOPRequest();
		String orderId = "";
		if (requestXML == null || "".equals(requestXML.trim())) {
			logger.info("requestXML is null or empty string!");
			return new OrderInfoResponse(new EmptyRequestException(
					"requestXML is null or empty string!"), orderId).toXML();
		}
		try {
			String xmlParseType = DcSystemParamManager
					.getParameter(VsopConstants.XML_PARSE_TYPE);
			if ("string".equalsIgnoreCase(xmlParseType)) {
				order.loadFromXml(requestXML, "string");
			} else {
				order.loadFromXml(requestXML);
			}
			orderId = order.getOrderId();
		} catch (DocumentException e) {
			logger.info("#sendOrderToActive_FK ex:", e);
			return new OrderInfoResponse(e, orderId).toXML();
		} catch (Exception e) {
			logger.info("#sendOrderToActive_FK ex:", e);
			return new OrderInfoResponse(e, orderId).toXML();
		}
		String actionType = order.getActionType();
		String prodInstId = order.getPorductInstID();
		boolean success = false;
		try {
			// 广西本地化：除宽带，手机其他产品类型的用户号码前面都需要加区号再填入产品实例表的acc_nbr中 by liu.yuming
			// start
			String provinceCode = DcSystemParamManager
					.getParameter(VsopConstants.DC_PROVINCE_CODE);
			// 不需要加区号以区分唯一性的产品类型
			// String noAppendLan =
			// CrmParamsConfig.getInstance().getParamValue("NoAppendLanCode");
			if (CrmConstants.GX_PROV_CODE.equals(provinceCode)) {
				String accNbr = AppendLanCode.getInstance().appendAccNbrLan(
						order.getProductNo(), order.getProdSpecCode(),
						order.getReginID());
				order.setProductNo(accNbr);

			}
			// 广西本地化：除宽带，手机其他产品类型的用户号码前面都需要加区号再填入产品实例表的acc_nbr中 by liu.yuming
			// end

			if ("".equals(order.getPorductInstID())) {
				String[] prodInstIdAndLanCode = orderDao.getProductInstId(order
						.getProductNo(), order.getProdSpecCode());
				order.setPorductInstID(prodInstIdAndLanCode[0]);
				order.setLanCode(prodInstIdAndLanCode[1]);
			}
			if (!OrderConstant.orderTypeOfInstall.equals(actionType)
					&& !OrderConstant.orderTypeOfModifyNo.equals(actionType)) {
				if ("".equals(order.getPorductInstID()))
					throw new ProductInstanceIdNotExists(
							"product instance not exists,please check.");
			}
			if (OrderConstant.orderTypeOfInstall.equals(actionType)) { // 新装
				doInstallAction_fk(order);
			} else if (OrderConstant.orderTypeOfModifyState.equals(actionType)) { // 用户状态变更
				doChangeStateAction_fk(order);
			} else if (OrderConstant.orderTypeOfModifyNo.equals(actionType)) { // 改号

				doChangeNoAction_fk(order);
			} else if (OrderConstant.orderTypeOfModifyAProduct
					.equals(actionType)) { // 改附属产品 orderTypeOfModifyAProduct
				doChangeAproduct_fk(order);
			} else if (OrderConstant.orderTypeOfUninstall.equals(actionType)) { // 拆机
				doUninstallAction_fk(order);
			} else if (OrderConstant.orderTypeOfModifyVProduct
					.equals(actionType)) { // 改增值产品
				doModifyVproduct(order);
			} else if (OrderConstant.orderTypeOfModifyPayType
					.equals(actionType)) { // 付费类型变更
				String newPayType = order.getUserPayType();
				if (null == newPayType || "".equals(newPayType)) {
					throw new EmptyPayTypeException("UserPayType required!");
				}
				orderDao.updateProdInstPayType(newPayType, order
						.getProdSpecCode(), order.getProductNo(), prodInstId);

			} else if (CrmConstants.JX_PROV_CODE.equals(provinceCode)
					&& OrderConstant.orderTypeOfModifyService
							.equals(actionType)) {
				doModifyVproduct(order);
				doChangeAproduct_fk(order);
			}
			success = true;
		} catch (Exception e) {
			logger.info("#sendOrderToActive_FK ex.", e);
			LegacyDAOUtil.rollbackOnException();
			throw e;
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		userInfoSyncFromVSOP(order);
		fkFeedback(order, success);
		return new OrderInfoResponse(orderId).success().toXML();
	}

	/**
	 * 服务开通回单,保存信息到表，用定时任务扫描 新增和改增值产品由激活模块处理
	 * 
	 * @param order
	 * @param success
	 * @throws SQLException
	 */
	private void fkFeedback(WorkListFKToVSOPRequest order, boolean success) {
		String actionType = order.getActionType();
		if (OrderConstant.orderTypeOfInstall.equals(actionType)) { // 新装
			if (order.getVProductInfoList().size() > 0) {
				return;
			}
		} else if (OrderConstant.orderTypeOfModifyVProduct.equals(actionType)) { // 改增值产品
			return;
		}
		logger.info("fkFeedback");
		String msg = createSpOutFeedbackMsg(order);
		try {
			orderDao.saveSpOutMsgFeedback(order.getOrderId(), order
					.getSequence(), order.getSystemId(),
					order.getStreamingNo(), msg, order.getProductNo());
		} catch (SQLException e) {
			logger.error("#fkFeedback ex.", e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	private String createSpOutFeedbackMsg(WorkListFKToVSOPRequest order) {
		StringBuffer bf = new StringBuffer("");
		bf.append("<Request><SessionBody><WorkListVSOPToPFReq>").append(
				"<StreamingNo>").append(order.getStreamingNo()).append(
				"</StreamingNo>").append("<TimeStamp>").append(
				System.currentTimeMillis()).append("</TimeStamp>").append(
				"<SystemId>").append(order.getSystemId()).append("</SystemId>")
				.append("<OrigOrderId>").append(order.getOrderId()).append(
						"</OrigOrderId>").append(
						"<OrderResultCode>0</OrderResultCode>").append(
						"<OrderResultDesc></OrderResultDesc>").append(
						"</WorkListVSOPToPFReq></SessionBody></Request>");
		return bf.toString();
	}

	/**
	 * 用户状态信息同步给x平台 异步处理
	 * 
	 * @param order
	 * @throws SQLException
	 */
	private void userInfoSyncFromVSOP(WorkListFKToVSOPRequest order) {
		// 11：用户状态变更,12：改号,15：拆机,16：付费类型变更 需要同步
		// 10:新装 14:改增值产品,13:改附属产品 不需要
		if (OrderConstant.orderTypeOfInstall.equals(order.getActionType())
				|| OrderConstant.orderTypeOfModifyVProduct.equals(order
						.getActionType())
				|| OrderConstant.orderTypeOfModifyAProduct.equals(order
						.getActionType())) {
			return;
		}
		// 信息同步开关
		boolean openUserInfoSync = false;
		String openUserInfoSyncValue = DcSystemParamManager
				.getParameter(VsopConstants.OPEN_USER_INFOSYNC);
		if (null != openUserInfoSyncValue
				&& "true".equals(openUserInfoSyncValue))
			openUserInfoSync = true;
		logger.info("userInfoSyncFromVSOP -> " + openUserInfoSync);
		if (!openUserInfoSync)
			return;
		try {
			String streamNo = VsopStreamNoHelper.getInstance().genReqStreamNo();
			String timestamp = StringUtil.getInstance().getCurrentTimeStamp();
			String requestBodyXml = createUserInfoSyncFromVSOPRequestBodyXml(
					order, streamNo, timestamp);
			String prodInstId = order.getPorductInstID();
			List serviceUrl = orderDao.getXplatformServiceUrl(order
					.getProdSpecCode(), order.getProductNo(), prodInstId);
			long inOrderId = order.getSequence();// 内部orderId
			for (Iterator iterator = serviceUrl.iterator(); iterator.hasNext();) {
				String url = (String) iterator.next();
				logger.info("x serviceUrl:" + url);
				// 如果号码在业务平台上没有存在有效的订购关系，则不用送x平台
				if (null == url || "".equals(url))
					return;
				orderDao.saveUserInfoSyncMsg(order.getSystemId(), url,
						inOrderId, order.getProductNo(), StringUtil
								.getInstance().getVsopRequest(streamNo,
										timestamp, requestBodyXml));
				// String resp =
				// SoapClient.getInstance().userInfoSyncFromVSOP(StringUtil.getInstance().getVsopRequest(streamNo,timestamp,requestBodyXml),
				// url );
				// logger.info("#userInfoSyncFromVSOP response:" + resp);
			}
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException();
			logger.error("", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		// String resultCode =
		// StringUtil.getInstance().getTagValue("ResultCode", resp);
	}

	private String createUserInfoSyncFromVSOPRequestBodyXml(
			WorkListFKToVSOPRequest order, String streamNo, String timestamp) {
		String systemId = order.getSystemId();
		String actionType = order.getActionType();
		String prodSpecCode = order.getProdSpecCode();
		String productNo = order.getProductNo();
		String oldProductNo = null;
		String userState = null;
		String userPayType = null;
		if (OrderConstant.orderTypeOfModifyState.equals(actionType)) { // 用户状态变更
			userState = order.getUserState();
			if (ConstantsState.USER_STATE_USED.equals(userState)) {
				userState = "0";
			} else if (ConstantsState.USER_STATE_STOP.equals(userState)) {
				userState = "1";
			} else if (ConstantsState.USER_STATE_00C.equals(userState)) {
				userState = "2";
			} else if (ConstantsState.USER_STATE_UNUSED.equals(userState)) {
				userState = "3";
			}
		} else if (OrderConstant.orderTypeOfModifyNo.equals(actionType)) { // 改号
			oldProductNo = order.getOldProductNo();
		} else if (OrderConstant.orderTypeOfUninstall.equals(actionType)) { // 拆机

		} else if (OrderConstant.orderTypeOfModifyPayType.equals(actionType)) { // 付费类型变更
			userPayType = order.getUserPayType();
		}
		return createUserInfoSyncFromVSOPRequestBodyXml(streamNo, timestamp,
				systemId, actionType, prodSpecCode, productNo, oldProductNo,
				userState, userPayType);
	}

	private String createUserInfoSyncFromVSOPRequestBodyXml(String streamingNo,
			String timeStamp, String systemId, String actionType,
			String prodSpecCode, String productNo, String oldProductNo,
			String userState, String userPayType) {
		StringBuffer bf = new StringBuffer("");
		bf.append("<UserInfoSyncFromVSOPReq>").append("<StreamingNo>").append(
				streamingNo).append("</StreamingNo>").append("<TimeStamp>")
				.append(timeStamp).append("</TimeStamp>").append("<SystemId>")
				.append(systemId).append("</SystemId>").append("<ActionType>")
				.append(actionType).append("</ActionType>").append(
						"<ProdSpecCode>").append(prodSpecCode).append(
						"</ProdSpecCode>").append("<ProductNo>").append(
						productNo).append("</ProductNo>");
		if (oldProductNo != null)
			bf.append("<OldProductNo>").append(oldProductNo).append(
					"</OldProductNo>");
		if (userState != null)
			bf.append("<UserState>").append(userState).append("</UserState>");
		if (userPayType != null)
			bf.append("<UserPayType>").append(userPayType).append(
					"</UserPayType>");
		bf.append("</UserInfoSyncFromVSOPReq>");
		return bf.toString();
	}

	private void doModifyVproduct(WorkListFKToVSOPRequest order)
			throws RuntimeException, FrameException,
			ProductInstanceIdNotExists, ProductHasNoPlatformException,
			SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			handleVproduct(order, conn);
			// LegacyDAOUtil.commitAndCloseConnection(conn);
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException(conn);
			logger.info("#doModifyVproduct ex:", e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 改附属产品:根据附属产品的动作，如果是新增则BIZ_CAPABILITY_INST新加一条记录，如果是取消则将记录改成00X
	 * 
	 * @param order
	 * @throws SQLException
	 */
	private void doChangeAproduct_fk(WorkListFKToVSOPRequest order)
			throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			handleAproduct(order, conn);
			// LegacyDAOUtil.commitAndCloseConnection(conn);
		} catch (SQLException e) {
			logger.info("#doChangeAproduct_fk ex.", e);
			LegacyDAOUtil.rollbackOnException(conn);
			throw e;
		}
	}

	/**
	 * 拆机:将PROD_INST表的状态改成00X
	 * 
	 * @param order
	 * @throws SQLException
	 */
	private void doUninstallAction_fk(WorkListFKToVSOPRequest order)
			throws SQLException {
		String productNo = order.getProductNo();
		String prodSpecCode = order.getProdSpecCode();
		String newState = OrderConstant.orderStateOfDelete;
		String prodInstId = order.getPorductInstID();
		orderDao.updateProdInstanceState(newState, productNo, prodSpecCode,
				prodInstId);
		orderDao.deleleOrderRelation(productNo, prodSpecCode,
				OrderConstant.orderStateOfDelete, prodInstId);
	}

	/**
	 * 改号:更新PROD_INST表的号码为新号码
	 * 
	 * @param order
	 * @throws SQLException
	 */
	private void doChangeNoAction_fk(WorkListFKToVSOPRequest order)
			throws SQLException {
		String productNo = order.getOldProductNo();
		String newProductNo = order.getProductNo();
		String prodInstId = order.getPorductInstID();
		orderDao.updateProdInstanceNo(newProductNo, productNo, order
				.getProdSpecCode(), prodInstId);
	}

	/**
	 * 用户状态变更：更新PROD_INST的状态为新状态
	 * 
	 * @param order
	 * @throws SQLException
	 */
	private void doChangeStateAction_fk(WorkListFKToVSOPRequest order)
			throws SQLException {
		String productNo = order.getProductNo();
		String newState = order.getUserState();
		String prodInstId = order.getPorductInstID();
		orderDao.updateProdInstanceState(newState, productNo, order
				.getProdSpecCode(), prodInstId);
	}

	/**
	 * 新装:将用户信息保存在用户信息表PROD_INST，附属产品填写在PROD_OFFER_INST
	 * 
	 * @param workList
	 * @throws Exception
	 * @throws
	 */
	private void doInstallAction_fk(WorkListFKToVSOPRequest workList)
			throws Exception {
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			// String prodInstId = workList.getPorductInstID();
			// if(prodInstId == null || "".equals(prodInstId))
			// prodInstId =
			// orderDao.findProdInstIdByProductNo(workList.getProductNo(),
			// conn);
			workList.setState(OrderConstant.orderStateOfCreated);
			orderDao.addProdInstance(workList, conn);
			handleAproduct(workList, conn);
			handleVproduct(workList, conn);

		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException(conn);
			logger.info(e.getMessage());
			throw e;
		}
	}

	/**
	 * 增值产品处理，若有传增值产品则检查互斥或者依赖，新增订单和订单项
	 * 
	 * @param order
	 * @param conn
	 * @throws SQLException
	 * @throws FrameException
	 * @throws RuntimeException
	 * @throws ProductInstanceIdNotExists
	 * @throws ProductHasNoPlatformException
	 */
	private void handleVproduct(WorkListFKToVSOPRequest order, Connection conn)
			throws SQLException, RuntimeException, FrameException,
			ProductInstanceIdNotExists, ProductHasNoPlatformException {
		List VproductList = order.getVProductInfoList();
		if (VproductList != null && VproductList.size() > 0) {
			// int idx = 0;
			for (Iterator iterator = VproductList.iterator(); iterator
					.hasNext();) {
				VProductInfo vproduct = (VProductInfo) iterator.next();
				// if(vproduct.getActionType().equals(OrderConstant.VProductActionTypeOfAdd)){
				// String[] ret =
				// orderDao.checkRelationType(vproduct.getProductNo(),vproduct.getVProductID(),
				// conn);
				// String anotherProductId = ret[0];
				// String relationType = ret[1];
				// //如果relation_type_cd=0,则是互斥单,需要生成一条退订product_id的工单
				// if(relationType .equals("0")){
				// createDelOrder_fk(order, vproduct, anotherProductId);
				// createAddOrder(order,vproduct);
				// VproductList.remove(idx);//特殊处理不需要再处理
				// }else if(relationType.equals("2")){
				// //如果relation_type_cd=2,则是替换,在订购单中的旧增值产品中填入这个id,动作为替换
				// vproduct.setOldVproductId(anotherProductId);
				// vproduct.setActionType(OrderConstant.VProductActionTypeOfReplace);
				// }else
				// if(orderDao.existOrderRelation(vproduct.getProductNo(),vproduct.getVProductID(),conn)){
				// if(!"".equals(order.getProductOfferId())){
				// vproduct.setActionType(OrderConstant.VProductActionTypeOfPendding);
				// }
				// }
				// }
				// idx++;
				if (OrderConstant.orderTypeOfAdd.equals(vproduct
						.getActionType())) {
					if (orderDao.existOrderRelation(vproduct.getProductNo(),
							vproduct.getVProductID(), conn)) {
						if (!"".equals(order.getProductOfferId())) {
							vproduct
									.setActionType(OrderConstant.VProductActionTypeOfPendding);
						}
					}
				}
			}
			updateOrderRelation_fk(order);
			createOrderForSPI_fk(order);
		}
	}

	private void updateOrderRelation_fk(WorkListFKToVSOPRequest order)
			throws SQLException {
		String prodInstId = order.getPorductInstID();
		List VproductList = order.getVProductInfoList();
		if (VproductList != null && VproductList.size() > 0) {
			for (Iterator iterator = VproductList.iterator(); iterator
					.hasNext();) {
				VProductInfo vproduct = (VProductInfo) iterator.next();
				if (OrderConstant.orderTypeOfAdd.equals(vproduct
						.getActionType())) {
					vproduct.setState(ConstantsState.USER_STATE_USED);
					List tmpList = new ArrayList();
					tmpList.add(vproduct);
					orderDao.saveOrderRelation_fk(order.getSystemId(), tmpList,
							order.getState(), order.getProductNo(), order
									.getProdSpecCode(), order
									.getPorductInstID());
				} else if (OrderConstant.VProductActionTypeOfCancelFromPackage
						.equals(vproduct.getActionType())) {
					orderDao.cancelOrderRelationFromPackage(prodInstId,
							vproduct, order.getProductOfferId());
				} else if (OrderConstant.orderTypeOfDel.equals(vproduct
						.getActionType())) {
					vproduct.setState(ConstantsState.USER_STATE_UNUSED);
					orderDao.deleleOrderRelation(prodInstId, vproduct);
				} else if (OrderConstant.VProductActionTypeOfPendding
						.equals(vproduct.getActionType())) {
					orderDao.penndingPackage(prodInstId, vproduct, order
							.getProductOfferId());
				} else if (OrderConstant.VProductActionTypeOfReplace
						.equals(vproduct.getActionType())) {
					orderDao.replaceOrderRelation(prodInstId, vproduct);
				}
			}
		}
	}

	private void createAddOrder(WorkListFKToVSOPRequest order,
			VProductInfo vproduct) throws SQLException, RuntimeException,
			FrameException, ProductInstanceIdNotExists,
			ProductHasNoPlatformException {
		WorkListFKToVSOPRequest delOrder = cloneOrder(order);
		delOrder.setActionType(OrderConstant.orderTypeOfDel);
		List vProductList = new ArrayList();
		vProductList.add(vproduct);
		delOrder.setVProductInfoList(vProductList);
		doAddOrderAction_fk(order);
		createOrderForSPI_fk(order);
	}

	private void doAddOrderAction_fk(WorkListFKToVSOPRequest order)
			throws SQLException {
		orderDao.saveOrderAndOrderItems_fk(order);
		// 保存增值产品
		orderDao.saveOrderRelation_fk(order.getSystemId(), order
				.getVProductInfoList(), order.getState(), order.getProductNo(),
				order.getProdSpecCode(), order.getPorductInstID());
	}

	private void createDelOrder_fk(WorkListFKToVSOPRequest order,
			VProductInfo vProduct, String anotherProductId)
			throws SQLException, ProductHasNoPlatformException {
		WorkListFKToVSOPRequest delOrder = cloneOrder(order);
		delOrder.setActionType(OrderConstant.orderTypeOfDel);
		List vProductList = new ArrayList();
		VProductInfo delProduct = new VProductInfo();
		delProduct.setActionType(OrderConstant.orderTypeOfDel);
		delProduct.setEffDate(vProduct.getEffDate());
		delProduct.setExpDate(vProduct.getExpDate());
		delProduct.setVProductID(anotherProductId);
		delProduct.setProductId(vProduct.getProductId());
		delProduct.setProductNo(vProduct.getProductNo());
		delProduct.setVProductInstID(vProduct.getVProductInstID());
		vProductList.add(delProduct);
		delOrder.setVProductInfoList(vProductList);
		doDelOrderAction_fk(delOrder);
		createOrderForSPI_fk(delOrder);
	}

	private void createOrderForSPI_fk(WorkListFKToVSOPRequest order)
			throws ProductHasNoPlatformException, SQLException {
		logger.info("createOrderForSPI_fk start");
		try {
			String spiXML = createSPIXML_fk(order);
			logger.info(spiXML);
			// 保存到数据库
			logger.info("saveXML_fk start.");
			long inOrderId = order.getSequence();
			String unuseClob = DcSystemParamManager
					.getParameter(VsopConstants.UNUSE_CLOB);
			if ("true".equalsIgnoreCase(unuseClob)) {
				orderDao.saveXMLWithoutClob(spiXML, order.getSystemId(),
						inOrderId, order.getProductNo(), order.getReginID());
			} else {
				orderDao.saveXML(spiXML, order.getSystemId(), inOrderId, order
						.getProductNo(), order.getReginID());
			}
		} catch (SQLException e) {
			LegacyDAOUtil.rollbackOnException();
			logger.error("#createOrderForSPI_fk ex.", e);
			e.printStackTrace();
			throw e;
		}
		logger.info("saveXML_fk done.");
	}

	private String createSPIXML_fk(WorkListFKToVSOPRequest order)
			throws SQLException, ProductHasNoPlatformException {
		StringBuffer bf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<root>").append("<msg_head>").append("<from>").append(
				order.getSystemId()).append("</from>").append("<to>").append(
				"VSOP").append("</to>").append("<msg_type>").append(
				"message_request").append("</msg_type>").append("<serial>")
				.append(order.getStreamingNo()).append("</serial>").append(
						"<out_order_type>").append("VSOP001").append(
						"</out_order_type>").append("</msg_head>")

				.append("<interface_msg>").append("<public>").append(
						"<order_id>").append(order.getSequence()).append(
						"</order_id>").append("<out_order_id>").append(
						order.getOrderId()).append("</out_order_id>").append(
						"<order_act_type>").append(order.getActionType())
				.append("</order_act_type>").append("</public>")

				.append("<user_info>").append("<pproductoffer_info>").append(
						"<ppproduct_act_type>").append(order.getActionType())
				.append("</ppproduct_act_type>")
				.append("</pproductoffer_info>").append("<prod_info>").append(
						"<prod_no>").append(order.getProductNo()).append(
						"</prod_no>").append("<prod_type>").append(
						order.getProdSpecCode()).append("</prod_type>").append(
						"<area_code>").append(order.getReginID()).append(
						"</area_code>").append("<prod_inst_id>").append(
						order.getPorductInstID()).append("</prod_inst_id>")
				.append("</prod_info>");

		String subProdXml = createSubProductsXml_fk(order);
		bf.append(subProdXml);
		bf.append("</user_info>").append("</interface_msg>").append("</root>");
		return bf.toString();
	}

	private String createSubProductsXml_fk(WorkListFKToVSOPRequest order)
			throws SQLException, ProductHasNoPlatformException {
		StringBuffer subProductNodeBuffer = new StringBuffer();
		subProductNodeBuffer.append("<sub_products>");
		List productIdArray = new ArrayList();
		Map prodHolder = new HashMap();
		List vProductList = order.getVProductInfoList();
		for (Iterator iterator = vProductList.iterator(); iterator.hasNext();) {
			VProductInfo p = (VProductInfo) iterator.next();
			String productId = p.getVProductID();
			productIdArray.add(productId);
			prodHolder.put(productId, p);
		}
		String productids = joinStringList(productIdArray);
		logger.info("createSubProductsXml productids: " + productids);
		Map[] ret = null;
		if (!"".equals(productids) && productids != null)
			ret = orderDao.findPlatform(productids);
		if (ret != null && ret.length == 2) {
			// 生成control节点
			Map prodMapPlamform = ret[0];
			logger.info("create control node.");
			subProductNodeBuffer.append("<control>");
			int i = 0;
			for (Iterator iterator = vProductList.iterator(); iterator
					.hasNext();) {
				VProductInfo p = (VProductInfo) iterator.next();
				String productId = p.getVProductID();
				List plamforms = (List) prodMapPlamform.get(productId);
				if (plamforms == null)
					throw new ProductHasNoPlatformException(
							"cannot find any platform for product->"
									+ productId);
				if (i == 0) {
					subProductNodeBuffer.append(joinStringList(plamforms));
				} else {
					subProductNodeBuffer.append(";").append(
							joinStringList(plamforms));
				}
				i++;
			}
			subProductNodeBuffer.append("</control>");
			logger.info("create control node done.");
			// 生成业务平台N编码节点
			Map PlamformMapProd = ret[1];
			Set plamformIdSet = PlamformMapProd.keySet();
			logger.info("create sub plamform node.");
			for (Iterator iterator = plamformIdSet.iterator(); iterator
					.hasNext();) {
				String plamformId = (String) iterator.next();
				List productIdList = (List) PlamformMapProd.get(plamformId);
				subProductNodeBuffer.append("<p").append(plamformId)
						.append(">");
				for (Iterator productItr = productIdList.iterator(); productItr
						.hasNext();) {
					String productId = (String) productItr.next();
					VProductInfo p = (VProductInfo) prodHolder.get(productId);
					String tmp = p.toXmlForSpi();
					subProductNodeBuffer.append(tmp);
				}

				subProductNodeBuffer.append("</p").append(plamformId).append(
						">");
			}
			logger.info("create sub plamform node end.");
		}
		subProductNodeBuffer.append("</sub_products>");
		return subProductNodeBuffer.toString();
	}

	private void doDelOrderAction_fk(WorkListFKToVSOPRequest delOrder)
			throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		delOrder.setState(OrderConstant.orderStateOfCreated);
		orderDao.saveOrderAndOrderItems_fk(delOrder);
		orderDao.delOrder_fk(delOrder, conn);
	}

	private WorkListFKToVSOPRequest cloneOrder(WorkListFKToVSOPRequest order) {
		WorkListFKToVSOPRequest newOrder = new WorkListFKToVSOPRequest();
		newOrder.setActionType(order.getActionType());
		newOrder.setOldProductNo(order.getOldProductNo());
		newOrder.setOrderId(order.getOrderId());
		newOrder.setPorductInstID(order.getPorductInstID());
		newOrder.setProdSpecCode(order.getProdSpecCode());
		newOrder.setProductNo(order.getProductNo());
		newOrder.setProductOfferId(order.getProductOfferId());
		newOrder.setReginID(order.getReginID());
		newOrder.setState(order.getState());
		newOrder.setStreamingNo(order.getStreamingNo());
		newOrder.setSystemId(order.getSystemId());
		newOrder.setTimeStamp(order.getTimeStamp());
		newOrder.setUserPayType(order.getUserPayType());
		newOrder.setUserState(order.getUserState());
		return newOrder;
	}

	/**
	 * 附属产品
	 * 
	 * @param order
	 * @param conn
	 * @throws SQLException
	 */
	private void handleAproduct(WorkListFKToVSOPRequest order, Connection conn)
			throws SQLException {
		List aProducts = order.getAProductInfoList();
		if (aProducts != null && aProducts.size() > 0) {
			String prodInstId = order.getPorductInstID();
			for (Iterator iterator = aProducts.iterator(); iterator.hasNext();) {
				AProductInfo aproduct = (AProductInfo) iterator.next();
				if (AProduct.actionTypeOfAdd.equals(aproduct.getActionType())) {
					orderDao.addAproduct(aproduct, conn, prodInstId);
				} else if (AProduct.actionTypeOfCancel.equals(aproduct
						.getActionType())) {
					orderDao.deleteAproduct(aproduct, prodInstId, conn);
				}
			}
		}
	}

	/**
	 * 全部退订选择的增值产品
	 * 
	 * @param relationIds
	 * @throws SQLException
	 * @throws ProductHasNoPlatformException
	 */
	public boolean delOrdersByAccNbr(String accNbr) {
		try {
			OrderRelationHelpDao orderRelationHelpDao = new OrderRelationHelpDao();
			List relations = orderRelationHelpDao.qryORSByAccNbr(accNbr);
			Iterator iterator = relations.iterator();
			String relationIds = "";
			while(iterator.hasNext()){
				OrderRelationVO vo = (OrderRelationVO)iterator.next();
				relationIds = relationIds+","+vo.getOrderRelationId();
			}
			if(!"".equals(relationIds)){
				VariedServerEngine.getInstance().delOrdersByRelationId(relationIds.substring(1),"",SystemCode.VSOP);	
			}
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
			logger.info("#delOrdersByRelationId ex.", e);
			return false;
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return true;
	}


	/**
	 * 退订选择的增值产品
	 * 
	 * @param relationIds
	 * @throws SQLException
	 * @throws ProductHasNoPlatformException
	 */
	public boolean delOrdersByRelationId(String relationIds) {
		try {
//			SubscribeToVSOPRequest order = getOrderByRelationIds(relationIds);
//			logger.info("del order" + order.getProductNo());
//			doDelOrderAction(order);
//			createOrderForSPI(order);
//
//			// 填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1, add by qin.guoquan
//			// 2010-06-05,guangxi
//			if (null != CRMSynchUtil.CRM_SYN
//					&& "1".equals(CRMSynchUtil.CRM_SYN.trim())
//					&& !"201".equals(order.getSystemId())
//					&& !"206".equals(order.getSystemId()))
//				writeInfOrderRelation(order);
			VariedServerEngine.getInstance().delOrdersByRelationId(relationIds,"",SystemCode.VSOP);
		} catch (Exception e) {
			LegacyDAOUtil.rollbackOnException();
			logger.info("#delOrdersByRelationId ex.", e);
			return  false;
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return true;
	}

	private SubscribeToVSOPRequest getOrderByRelationIds(String relationIds)
			throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		List relations = orderDao.getReletionsByIds(relationIds, conn);
		SubscribeToVSOPRequest order = createOrders(relations);
		LegacyDAOUtil.releaseConnection(conn);
		return order;
	}

	private SubscribeToVSOPRequest createOrders(List relations) {
		List vproductList = new ArrayList();
		SubscribeToVSOPRequest order = new SubscribeToVSOPRequest();
		for (Iterator iterator = relations.iterator(); iterator.hasNext();) {
			Map m = (Map) iterator.next();
			VSubProdInfo subProd = new VSubProdInfo();
			subProd.setActionType(OrderConstant.orderTypeOfDel);
			subProd.setEffDate((String) m.get("EFF_DATE"));
			subProd.setExpDate((String) m.get("EXP_DATE"));
			subProd.setVProductID((String) m.get("SUB_PRODUCT_ID"));
			subProd.setVproductNbr((String) m.get("product_nbr"));
			subProd.setProductOfferId((String) m.get("PROD_OFFER_ID"));
			subProd.setPprodOfferId((String) m.get("PPROD_OFFER_ID"));
			subProd.setPackageId((String) m.get("PACKAGE_ID"));
			subProd.setProductOfferNbr((String) m.get("PROD_OFFER_NBR"));
			subProd.setProductOfferType((String) m.get("PROD_OFFER_TYPE"));
			subProd.setActionType(OrderConstant.orderTypeOfDel);
			subProd.setVproductInstId((String) m.get("ORDER_RELATION_ID"));
			order.setActionType(OrderConstant.orderTypeOfDel);
			order.setState(OrderConstant.orderStateOfDelete);
			order.setProductId((String) m.get("PRODUCT_ID"));
			order.setProdSpecCode((String) m.get("PRODUCT_ID"));
			order.setProductNo((String) m.get("ACC_NBR"));
			order.setProdInstId((String) m.get("PROD_INST_ID"));
			vproductList.add(subProd);
		}
		order.setStreamingNo(String.valueOf(System.currentTimeMillis()));
		order.setSystemId("200");
		ProductOfferInfo pOfferInfo = new ProductOfferInfo();
		pOfferInfo.setVSubProdInfoList(vproductList);
		List pOfferList = new ArrayList();
		pOfferList.add(pOfferInfo);
		order.setProductOfferInfo(pOfferList);
		return order;
	}

	/**
	 * 先查找增值产品信息，然后调用已存在的订购接口订购
	 * 
	 * @param vproductIds
	 *            增值产品id字符串，用,分割
	 * @param productNbr
	 *            订购增值产品的用户编码
	 * @param effDate
	 * @param expDate
	 * @throws SQLException
	 */
	public Map orderProducts(String prodOfferIds, String productNbr,
			String lanCode, String mProductId) {
		Map map = new HashMap();
		OrderDao orderDao = new OrderDao();
		ProdOffDAO prodDao = new ProdOffDAO();
		try {
			List prodOffers = prodDao.getProdOffersByProdOfferId(prodOfferIds);
			List subProducts = new ArrayList();
			for (Iterator iterator = prodOffers.iterator(); iterator.hasNext();) {
				ProductOfferInfo prodOfferInfo = (ProductOfferInfo) iterator
						.next();
				subProducts = orderDao.getVProductsByProdOfferId(prodOfferInfo
						.getProductOfferID(), OrderConstant.orderTypeOfAdd,
						prodOfferInfo.getEffDate(), prodOfferInfo.getExpDate());
				prodOfferInfo.setVSubProdInfoList(subProducts);
			}

			SubscribeToVSOPRequest order = new SubscribeToVSOPRequest();
			order.setActionType(OrderConstant.orderTypeOfAdd);
			order.setSystemId("200"); // 测试用 ，
			order.setProductNo(productNbr);
			order.setProductOfferInfo(prodOffers);
			order.setProductId(mProductId);
			order.setProdSpecCode(mProductId);
			order.setLanCode(lanCode);
			// orderDao.getOrderByNbr(order);
			order.setStreamingNo(String.valueOf(System.currentTimeMillis()));
			String reqXml = order.orderProductstoXml();
			logger.info("#orderProducts reqXml:" + reqXml);
			OrderService orderService = new OrderService();
			String respXml = orderService.subscribeToVSOP(reqXml);
			map = order.getOrderProductResult(respXml);
			logger.info("#orderProducts respXml:" + respXml);

		} catch (Exception e) {
			logger.error("#orderProducts ex.", e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return map;
	}

//	public Map orderProducts(String prodOfferNbrs, String productNbr,
//			String lanCode, String mProductId){
//		//转为销售品ID
//		String[] prodOfferIds = VariedServerEngine.getInstance()
//				.prodOfferNbrsToProdOfferIds(prodOfferNbrs);
//		//请求服务 
//		return VariedServerEngine.getInstance().orderProducts(prodOfferIds,
//				productNbr, lanCode, mProductId);
//
//	}

	/**
	 * 订购关系查询
	 * 
	 * @param requestXml
	 * @return subInstQueryResponse
	 */
	public String subInstQuery(String requestXml) {
		SubInstQueryRequest subInstQueryVo = new SubInstQueryRequest();
		SubInstQueryResponse subInstQueryResponse = null;
		try {
			subInstQueryVo.loadFromXml(requestXml);
		} catch (DocumentException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new SubInstQueryResponse(subInstQueryVo.getStreamingNo(), e)
					.toXml();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new SubInstQueryResponse(subInstQueryVo.getStreamingNo(), e)
					.toXml();
		} finally {
			LegacyDAOUtil.releaseConnection();
		}

		// 统一用产品实例id来查询
		try {
			if ("".equals(subInstQueryVo.getProdInstId())) {
				String[] prodInstIdAndLanCode;
				prodInstIdAndLanCode = orderDao.getProductInstId(subInstQueryVo
						.getProductNo(), subInstQueryVo.getProdSpecCode());
				subInstQueryVo.setProdInstId(prodInstIdAndLanCode[0]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("", e);
		} finally {
			LegacyDAOUtil.releaseConnection();
		}

		// 检查产品号码是否存在
		if ("".equals(subInstQueryVo.getProdInstId())) {
			subInstQueryResponse = new SubInstQueryResponse();
			subInstQueryResponse
					.setStreamingNo(subInstQueryVo.getStreamingNo());
			subInstQueryResponse.setResultDesc("查询的产品号码不存在"); // 查询的产品号码不存在
			subInstQueryResponse.setResultCode("2");
			return subInstQueryResponse.toXml();
		}

		List products = null;
		try {
			products = orderDao.subInstQuery(subInstQueryVo,
					OrderConstant.orderStateOfCreated);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#subInstQuery", e);
			subInstQueryResponse = new SubInstQueryResponse();
			subInstQueryResponse
					.setStreamingNo(subInstQueryVo.getStreamingNo());
			subInstQueryResponse.setResultDesc("发生异常!"); // 查询的产品号码不存在
			subInstQueryResponse.setResultCode("-1");
			return subInstQueryResponse.toXml();
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		subInstQueryResponse = new SubInstQueryResponse(subInstQueryVo
				.getStreamingNo(), products);
		return subInstQueryResponse.toXml();
	}

	/**
	 * 接收来自服务开通的工单请求，包括用户状态工单、用户能力工单及订购/退订工单 直接保存xml到接口表，后续定时任务扫描处理
	 * 
	 * @param requestXml
	 * @return
	 */
	public String workListFKToVSOP(String requestXml) {
		logger.info("workListFKToVSOP request:" + requestXml);
		boolean result = false;
		;
		WorkListFKToVSOPResp resp = new WorkListFKToVSOPResp();
		try {
			WorkListFKToVSOPRequest order = new WorkListFKToVSOPRequest();
			String xmlParseType = DcSystemParamManager
					.getParameter(VsopConstants.XML_PARSE_TYPE);
			if ("string".equalsIgnoreCase(xmlParseType)) {
				order.loadFromXml(requestXml, "string");
			} else {
				order.loadFromXml(requestXml);
			}
			resp.setStreamingNo(order.getStreamingNo());
			if ("".equals(order.getPorductInstID())) {
				String[] prodInstIdAndLanCode = orderDao.getProductInstId(order
						.getProductNo(), order.getProdSpecCode());
				order.setPorductInstID(prodInstIdAndLanCode[0]);
				order.setLanCode(prodInstIdAndLanCode[1]);
			}
			order.setState(OrderConstant.orderStateOfCreated);
			orderDao.saveOrderAndOrderItems_fk(order);
			String unuseClob = DcSystemParamManager
					.getParameter(VsopConstants.UNUSE_CLOB);
			if ("true".equalsIgnoreCase(unuseClob)) {
				result = orderDao
						.saveWorkListToInfMsgWithoutClob(order.getSystemId(),
								order.toXml(), order.getProductNo(), String
										.valueOf(order.getSequence()), order
										.getOrderId());
			} else {
				result = orderDao.saveWorkListToInfMsg(order.getSystemId(),
						order.toXml(), order.getProductNo(), String
								.valueOf(order.getSequence()), order
								.getOrderId());
			}
		} catch (Exception e) {
			logger.info("#workListFKToVSOP ex.", e);
			resp.setResultDesc(e.getMessage());
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		if (result) {
			resp.setResultCode(WorkListFKToVSOPResp.RESULT_SUCCESS);
		} else {
			resp.setResultCode(WorkListFKToVSOPResp.RESULT_FAILURE);
		}
		return resp.toXml();
	}

	public boolean reConfirmYes(String custOrderId) throws Exception {
		try {
			// String custOrderId =
			// orderDao.updateSecondMsgAndReturnOrderId(RQCode, RQResult);
			doReConfirm(custOrderId);
			return true;
		} catch (Exception e) {
			logger.info("#reConfirmYes ex.", e);
			e.printStackTrace();
			LegacyDAOUtil.rollbackOnException();
			throw e;
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	private boolean doReConfirm(String custOrderId) throws Exception {
		// try {
		// String custOrderId = orderDao.updateSecondMsgAndReturnOrderId(RQCode,
		// "00B");
		boolean isLastRecieveMsg = orderDao.isLastMsg(custOrderId);
		List orders = null;
		if (isLastRecieveMsg) {
			orders = orderDao.findSecondOrders(custOrderId);
		}
		// 如果短信都已经回复了，送激活
		if (orders != null && orders.size() > 0) {
			logger.info("all sms recieve,start activation.");
			SubscribeToVSOPRequest toActiveOrder = null;
			List productOfferInfoList = new ArrayList();
			int reconfirmNoCount = 0;
			for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
				Map m = (Map) iterator.next();
				String rqResult = (String) m.get("RQResult");
				if (ConstantsState.SecondConfirmMsg_ReplyNo.equals(rqResult)) {
					reconfirmNoCount++;
					SubscribeToVSOPRequest order = new SubscribeToVSOPRequest();
					order.loadFromXML((String) m.get("request_xml"));
					order.setSequence(Long.parseLong(custOrderId));
					orderDao.updateOrderAndOrderRelationState(order,
							ConstantsState.USER_STATE_UNUSED);
				} else {
					SubscribeToVSOPRequest order = new SubscribeToVSOPRequest();
					order.loadFromXML((String) m.get("request_xml"));
					order.setSequence(Long.parseLong(custOrderId));
					if (toActiveOrder == null)
						toActiveOrder = order;
					orderDao.updateOrderAndOrderRelationState(order,
							ConstantsState.USER_STATE_USED);
					productOfferInfoList.addAll(order.getProductOfferInfo());
				}
			}
			if (productOfferInfoList != null
					&& productOfferInfoList.size() != 0) {
				// 组装需要激活的order
				toActiveOrder.setProductOfferInfo(productOfferInfoList);
				createOrderForSPI(toActiveOrder);
			}
			if (reconfirmNoCount == orders.size()) {// 如果全部都回复 否，则归档
				orderDao.archiveOrder(custOrderId);
			}
		}
		return true;
	}

	/**
	 * 
	 * 加载销售品类型树
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 * 
	 */
	public String loadProductType(Map params) throws Exception {
		String result=null;
		try {
			String typeId = (String) params.get("typeId");
			String orderId = (String) params.get("orderId");
			String systemCode = (String) params.get("systemCode");

			List param = new ArrayList();
			StringBuffer sbf = new StringBuffer("and catalog_id=100000 ");
			if (typeId != null && !typeId.equals("")) {
				sbf.append("and p.parent_catalog_item_id=? ");
				param.add(typeId);
			}
			if (orderId != null && !orderId.equals("") && orderId.equals("-100")) { // 获取在用的业务能力
				sbf.append("and p.order_id=s.service_code ");
				sbf.append("and s.is_using=0 ");
			}
			if (systemCode != null && (systemCode.equals("100"))
					|| systemCode.equals("200")) { // VSOP系统则不能获取传统加增值销售品
				sbf.append("and p.order_id<>-3 ");
			}

			StringBuffer querySQL = new StringBuffer("select ");
			querySQL
					.append("p.catalog_item_id,p.parent_catalog_item_id,p.catalog_id,p.catalog_item_name,p.catalog_item_desc,p.catalog_item_type,");
			querySQL
					.append("p.ord_action_type,p.ord_no,p.cancel_ord_no,p.seq,p.state,p.party_id,p.party_role_id,p.oper_region_id,p.oper_date,p.order_id ");
			querySQL.append("from PRODUCT_CATALOG_ITEM p ");
			if (orderId != null && !orderId.equals("") && orderId.equals("-100")) { // 获取在用的业务能力
				querySQL.append(", SERVICE_ABILITY s ");
			}
			querySQL.append("where 1=1 ").append(sbf);

			ProductCatgItemDAO dao = new ProductCatgItemDAO();
			ArrayList treeList = (ArrayList) dao.findBySql(querySQL.toString(),
					param);
			if (null == treeList || treeList.size() < 1) {
				return null;
			}
			result = XMLSegBuilder.toXmlItems(ptMap2VO(treeList));
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			logger.error("",e);
			e.printStackTrace();
		}finally{
			LegacyDAOUtil.releaseConnection();
		}
		return result;
	}

	private ArrayList ptMap2VO(ArrayList treeList) {
		if (treeList == null || treeList.isEmpty())
			return null;

		ArrayList result = null;
		for (int i = 0; i < treeList.size(); i++) {
			Object o = treeList.get(i);
			if (o instanceof ProdCatgItemVO)
				return treeList;

			if (result == null) {
				result = new ArrayList();
			}

			HashMap m = (HashMap) o;
			ProdCatgItemVO type = new ProdCatgItemVO();
			type.loadFromHashMap(m);
			result.add(type);
		}
		return result;
	}

	public boolean getProdInst(String accNbr, String lanCode, String product_id) {
		StringBuffer bf = new StringBuffer();
		List list = new ArrayList();
		if (null != accNbr && !"".equals(accNbr)) {
			bf.append(" and ACC_NBR = ? ");
			list.add(accNbr);
		}
		if (null != lanCode && !"".equals(lanCode)) {
			bf.append(" and LAN_ID = ? ");
			list.add(lanCode);
		}
		if (null != product_id && !"".equals(product_id)) {
			bf.append(" and product_id = ? ");
			list.add(product_id);
		}
		boolean b = false;
		try {
			b = orderDao.getInstByNBR(bf.toString(), list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#getProdInst ex.", e);
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		return b;
	}

	/**
	 * 如果二次确认回复是 否，则让队列失效
	 * 
	 * @param custOrderId
	 * @return
	 */
	public boolean reConfirmNo(String custOrderId) {

		try {
			// String custOrderId =
			// orderDao.updateSecondMsgAndReturnOrderId(code, result);
			doReConfirm(custOrderId);
			return true;

		} catch (Exception e) {
			logger.error("#reConfirmNo ex.", e);
			e.printStackTrace();
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return false;
	}

	public String reConfirmException(String code) throws SQLException {
		return updateSecondMsgAndReturnOrderId(code, "Err");
	}

	public String updateSecondMsgAndReturnOrderId(String rqCode, String rqResult) {
		String orderId = "";
		try {
			orderId = orderDao
					.updateSecondMsgAndReturnOrderId(rqCode, rqResult);
		} catch (SQLException e) {
			logger.error("#reConfirmNo ex.", e);
			e.printStackTrace();
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return orderId;
	}

	public List getUndealReplay(String recordsPerOnce, String threadId) {
		List ret = null;
		try {
			ret = orderDao.getUndealReplay(recordsPerOnce, threadId);
		} catch (SQLException e) {
			logger.error("#getUndealReplay ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}

	/**
	 * 二次确认下发短信 根据发送失败，尝试再次发送，发送次数多于VsopConstants.RECONFIRM_TRY_SEND_COUNT，停止发送
	 * 
	 * @param replyMsg
	 * @throws Exception
	 */
	public boolean sendReconfirmMsg(SecondConfirmMsg replyMsg) {
		String trySendCount = DcSystemParamManager
				.getParameter(VsopConstants.RECONFIRM_TRY_SEND_COUNT);
		if (null == trySendCount || "".equals(trySendCount))
			trySendCount = "2"; // 默认发送2次
		int trycount = Integer.parseInt(trySendCount);
		boolean success = false;
		for (int i = 0; i < trycount; i++) {
			String ret;
			try {
				ret = new OrderMessConfirmBO().sendMessage(replyMsg
						.getRequestXml());
				String resultCode = StringUtil.getInstance().getTagValue(
						"ResultCode", ret);
				if (BaseResponse.RESULT_SUCCESS.equals(resultCode)) {
					success = true;
					break;
				} else {
					String resultDesc = StringUtil.getInstance().getTagValue(
							"ResultDesc", ret);
					replyMsg.setResultDesc(resultDesc);
					success = false;
					break;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
				if (i == trycount)
					replyMsg.setResultDesc(e.getMessage());
				logger.error("", e);
				continue;
			}
		}
		return success;
	}

	public void updateReconfirmMsg(boolean success, SecondConfirmMsg replyMsg) {
		String state = success ? ConstantsState.SecondConfirmMsg_SendSuccess
				: ConstantsState.SecondConfirmMsg_SendFailure;
		try {
			orderDao.updateReconfirmMsg(state, replyMsg);
		} catch (SQLException e) {
			logger.error("", e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	public SecondConfirmMsg getSecondConfirmMsg(String rqCode) {
		SecondConfirmMsg msg = null;
		try {
			msg = orderDao.getSecondConfirmMsg(rqCode);
		} catch (SQLException e) {
			logger.error("", e);
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		return msg;
	}

	public void archivingSecondMsg(String orderId) {
		try {
			orderDao.archivingSecondMsg(orderId);
		} catch (SQLException e) {
			logger.error("", e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	/**
	 * 
	 * @param recordsPerOnce
	 * @param threadId
	 * @return
	 */
	public List getUndealUserInfoSyncMsg(String recordsPerOnce, String threadId) {
		List ret = null;
		try {
			ret = orderDao.getUndealUserInfoSyncMsg(recordsPerOnce, threadId);
		} catch (SQLException e) {
			logger.error("#getUndealUserInfoSyncMsg ex.", e);
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
		return ret;
	}

	public boolean sendUserInfoSyncFromVSOPMsg(UserInfoSyncMsg userInfoSyncMsg) {
		String trySendCount = DcSystemParamManager
				.getParameter(VsopConstants.USERINFO_SYNC_TRY_SEND_COUNT);
		if (null == trySendCount || "".equals(trySendCount))
			trySendCount = "2"; // 默认发送2次
		int trycount = Integer.parseInt(trySendCount);
		boolean success = false;
		for (int i = 0; i < trycount; i++) {
			logger.info("sendUserInfoSyncFromVSOPMsg invoke service count->"
					+ i);
			String ret;
			try {
				ret = SoapClient.getInstance().userInfoSyncFromVSOP(
						userInfoSyncMsg.getRequestXml(),
						userInfoSyncMsg.getServiceUrl());
				userInfoSyncMsg.setResponseXml(ret);
				String resultCode = StringUtil.getInstance().getTagValue(
						"ResultCode", ret);
				if (BaseResponse.RESULT_SUCCESS.equals(resultCode)) {
					success = true;
					break;
				} else {
					String resultDesc = StringUtil.getInstance().getTagValue(
							"ResultDesc", ret);
					userInfoSyncMsg.setResultDesc(resultDesc);
					success = false;
					break;
				}
			} catch (RemoteException e) {
				if (i == (trycount - 1))
					userInfoSyncMsg.setResultDesc(e.getMessage());
				logger.error("", e);
				continue;
			}
		}
		return success;
	}

	public void updateUserInfoSyncMsg(boolean success,
			UserInfoSyncMsg userInfoSyncMsg) {
		String state = success ? ConstantsState.UserInfoSyncMsg_SendSuccess
				: ConstantsState.UserInfoSyncMsg_SendFailure;
		try {
			orderDao.updateUserInfoSyncMsg(state, userInfoSyncMsg);
			if (success)
				orderDao.archiveUserInfoSyncMsg(userInfoSyncMsg);
		} catch (SQLException e) {
			logger.error("", e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
		}
	}

	/**
	 * 订购历史查询
	 * 
	 * @param requestXml
	 */
	public String subInstHisQryFromVSOP(String requestXml) {
		SubInstHisQueryRequest subInstQueryVo = new SubInstHisQueryRequest();
		SubInstHisQueryResponse subInstHisQueryResponse = null;
		try {
			subInstQueryVo.loadFromXml(requestXml);
		} catch (DocumentException e) {
			logger.info(e.getMessage());
			return new SubInstHisQueryResponse(subInstQueryVo.getStreamingNo(),
					e).toXml();
		} catch (SQLException e) {
			logger.info(e.getMessage());
			return new SubInstHisQueryResponse(subInstQueryVo.getStreamingNo(),
					e).toXml();
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		// 检查产品号码是否存在
		if ("".equals(subInstQueryVo.getProdInstId())) {
			subInstHisQueryResponse = new SubInstHisQueryResponse();
			subInstHisQueryResponse.setStreamingNo(subInstQueryVo
					.getStreamingNo());
			subInstHisQueryResponse.setResultDesc("查询的产品号码不存在"); // 查询的产品号码不存在
			subInstHisQueryResponse.setResultCode("2");
			return subInstHisQueryResponse.toXml();
		}

		List products = null;
		try {
			products = orderDao.subscribeHisQry(subInstQueryVo,
					OrderConstant.orderStateOfCreated);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#subInstHisQryFromVSOP", e);
			subInstHisQueryResponse = new SubInstHisQueryResponse();
			subInstHisQueryResponse.setStreamingNo(subInstQueryVo
					.getStreamingNo());
			subInstHisQueryResponse.setResultDesc("发生异常"); // 查询的产品号码不存在
			subInstHisQueryResponse.setResultCode("-1");
			return subInstHisQueryResponse.toXml();
		} finally {
			LegacyDAOUtil.releaseConnection();
		}
		subInstHisQueryResponse = new SubInstHisQueryResponse(subInstQueryVo
				.getStreamingNo(), products);

		return subInstHisQueryResponse.toXml();
	}

}
