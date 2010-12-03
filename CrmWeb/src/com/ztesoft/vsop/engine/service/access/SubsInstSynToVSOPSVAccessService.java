package com.ztesoft.vsop.engine.service.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.OrderEngine;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * <pre>
 * Title:集团规范 X平台同步订购关系到VSOP
 * Description: 订购关系同步(X平台-VSOP)
 * </pre>
 * 
 * @author xulei xu.lei55@zte.com.cn
 * @version 1.00.00
 * 
 * <pre>
 * 修改记录
 * 修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SubsInstSynToVSOPSVAccessService extends CommonAccessService implements IAccess {

	public Map concreteInOpertion(Map in) throws Exception {
		CustomerOrder order = new CustomerOrder();
		String requestXML = (String) in.get("accessInObject");
		order = parseXml(requestXML);
		in.put("busiObject", order);
		in.put("serviceCode", String.valueOf(OrderEngine.SERVICE_ORSYNFROMISMP30));
		return in;
	}

	public Map concreteOutOpertion(Map in) throws Exception {
		CustomerOrder order = (CustomerOrder) in.get("busiObject");
		String streamingNo = order.getCustSoNumber();
		String resultCode = (String) in.get("resultCode");
		List resultInfo = (List) in.get("retBusiObject");
		String responseXML = getResult(streamingNo, resultCode);
		in.put("accessOutObject", responseXML);
		return in;
	}


	/**
	 * 根据报文构造用户订单对象
	 * @param xmlStr webservice请求报文
	 * @return CustomerOrder对象
	 * @throws Exception
	 * <pre>
	 * 平台同步的 订购关系状态，除了6(注销)对应vsop状态为注销，其他状态对应VSOP订购关系状态为有效
	 * </pre>
	 */
	public CustomerOrder parseXml(String xmlStr) throws Exception {
		
		CustomerOrder order = new CustomerOrder();
		long t1 = System.currentTimeMillis();

		String StreamingNo = XMLUtils.getSingleTagValue(xmlStr, "StreamingNo");// 流水号，可用于调用 提供双方比较接口调用情况
		String TimeStamp = XMLUtils.getSingleTagValue(xmlStr, "TimeStamp");// 时间戳
		String SystemId = XMLUtils.getSingleTagValue(xmlStr, "SystemId");// 交易发起系统标识
		
		String ProdSpecCode = XMLUtils.getSingleTagValue(xmlStr, "ProdSpecCode");//接入产品编码
		String ProductNo = XMLUtils.getSingleTagValue(xmlStr, "ProductNo");// 用户号码
		String ProductNbr = XMLUtils.getSingleTagValue(xmlStr, "VproductID");// 增值产品标识
		String SPID = XMLUtils.getSingleTagValue(xmlStr, "SPID");// SPID
		
		String offerType = XMLUtils.getSingleTagValue(xmlStr,"ProductOfferType");// 增值销售品类型：0：纯增值销售品1：增值捆绑套餐
		String prodOfferNbr = XMLUtils.getSingleTagValue(xmlStr,"ProductOfferID");// 销售品ID
		String Status = XMLUtils.getSingleTagValue(xmlStr, "Status");// 订购关系状态0：正常6：注销
		
		String SubscribeTime = XMLUtils.getSingleTagValue(xmlStr,"SubscribeTime");// 定购时间
		
		String EffDate = XMLUtils.getSingleTagValue(xmlStr, "EffDate");// 定购关系生效时间
		String ExpDate = XMLUtils.getSingleTagValue(xmlStr, "ExpDate");// 定购关系失效时间
		String ChannelPlayerID = XMLUtils.getSingleTagValue(xmlStr,	"ChannelPlayerID");//订购渠道
		
		String ProductID = "";
		if (ProductNbr != null && !ProductNbr.equals("")) {
			ProductID = DcSystemParamManager.getInstance().getProductIdByNbr(
					ProductNbr);
		}
		
		String prodOfferId = "";
		if (prodOfferNbr != null && !prodOfferNbr.equals("")) {
			prodOfferId = DcSystemParamManager.getInstance()
					.getProdOfferIdByNbr(prodOfferNbr);
		}

		if ("0".equals(offerType) || "".equals(offerType)) {
			// 如果不是传统+增值捆绑， 也不是捆绑套餐，直接取产品对应的销售品
			offerType = OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			if (ProductID != null && !ProductID.equals("")) {
				prodOfferId = DcSystemParamManager.getInstance()
						.getofferIdByProductId(ProductID);
			}

		}

		String OPType = "";
		if ("6".equals(Status)) {
			OPType = "3";
		} else {
			OPType = "0";
		}

		
		order.setProdId(ProdSpecCode);
		order.setOrderSystem(SystemId);
		order.setAccNbr(ProductNo);
		
		ProdInstVO prodInst = getProdInstByProcProd(ProdSpecCode, ProductNo);
		
		order.setProdInst(prodInst);
		order.setProdInstId(prodInst.getProdInstId());
		order.setCustSoNumber(StreamingNo);
		order.setProdId(prodInst.getProdId());
		order.setStatus(OrderConstant.orderStateOfCreated);

		String orderType = "0";
		if ("3".equals(OPType)) {
			orderType = "1"; // 如果是传过来的时候退订的话，就转为内部退订标识
		}
		
		order.setCustOrderType(orderType);
		order.setChnId(ChannelPlayerID);

		ProductOfferInfo productOffer = new ProductOfferInfo();
		productOffer.setEffDate(EffDate);
		productOffer.setExpDate(ExpDate);
		productOffer.setActioType(orderType);
		productOffer.setOfferId(prodOfferId);
		productOffer.setOfferType(offerType);

		VproductInfo product = new VproductInfo();
		product.setVProductId(ProductID);
		product.setOfferId(prodOfferId);
		product.setEffDate(EffDate);
		product.setExpDate(ExpDate);
		SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
		String productInstID = aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
		product.setVProductInstID(productInstID);
		product.setActionType(orderType);
		List vproductInfoList = new ArrayList();
		vproductInfoList.add(product);

		productOffer.setVproductInfoList(vproductInfoList);
		productOffer.setOfferType(offerType);
		productOffer.setOfferId(DcSystemParamManager.getInstance()
				.getProdOfferIdByNbr(prodOfferNbr));
		productOffer.setOfferNbr(prodOfferNbr);
		List productOfferInfoList = new ArrayList();
		productOfferInfoList.add(productOffer);
		order.setProductOfferInfoList(productOfferInfoList);

		return order;
		
	}

	private ProdInstVO getProdInstByProcProd(String prodTypeStr, String accNbr)
			throws Exception {
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		ProdInstVO prodInst = prodInstDao.qryProdInstByAccNbrAndProductIds(
				accNbr, prodTypeStr);
		return prodInst;
	}

	public static String getResult(String streamingNo, String resultCode) {
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
		String resultName = "";
		if (resultCode.equals("0"))
			resultName = "成功";
		else
			resultName = "失败";
		bf.append("<Response>");
		bf.append("<SubsInstSynToVSOPResp>").append("<StreamingNo>").append(
				streamingNo).append("</StreamingNo>").append("<ResultCode>")
				.append(resultCode).append("</ResultCode>").append(
						"<ResultDesc>").append(resultName).append(
						"</ResultDesc>").append("</SubsInstSynToVSOPResp>");
		bf.append("</Response>");
		retXml = bf.toString();
		return retXml;
	}

}
