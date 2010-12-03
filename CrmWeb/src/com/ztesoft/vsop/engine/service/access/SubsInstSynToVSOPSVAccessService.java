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
 * Title:���Ź淶 Xƽ̨ͬ��������ϵ��VSOP
 * Description: ������ϵͬ��(Xƽ̨-VSOP)
 * </pre>
 * 
 * @author xulei xu.lei55@zte.com.cn
 * @version 1.00.00
 * 
 * <pre>
 * �޸ļ�¼
 * �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
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
	 * ���ݱ��Ĺ����û���������
	 * @param xmlStr webservice������
	 * @return CustomerOrder����
	 * @throws Exception
	 * <pre>
	 * ƽ̨ͬ���� ������ϵ״̬������6(ע��)��Ӧvsop״̬Ϊע��������״̬��ӦVSOP������ϵ״̬Ϊ��Ч
	 * </pre>
	 */
	public CustomerOrder parseXml(String xmlStr) throws Exception {
		
		CustomerOrder order = new CustomerOrder();
		long t1 = System.currentTimeMillis();

		String StreamingNo = XMLUtils.getSingleTagValue(xmlStr, "StreamingNo");// ��ˮ�ţ������ڵ��� �ṩ˫���ȽϽӿڵ������
		String TimeStamp = XMLUtils.getSingleTagValue(xmlStr, "TimeStamp");// ʱ���
		String SystemId = XMLUtils.getSingleTagValue(xmlStr, "SystemId");// ���׷���ϵͳ��ʶ
		
		String ProdSpecCode = XMLUtils.getSingleTagValue(xmlStr, "ProdSpecCode");//�����Ʒ����
		String ProductNo = XMLUtils.getSingleTagValue(xmlStr, "ProductNo");// �û�����
		String ProductNbr = XMLUtils.getSingleTagValue(xmlStr, "VproductID");// ��ֵ��Ʒ��ʶ
		String SPID = XMLUtils.getSingleTagValue(xmlStr, "SPID");// SPID
		
		String offerType = XMLUtils.getSingleTagValue(xmlStr,"ProductOfferType");// ��ֵ����Ʒ���ͣ�0������ֵ����Ʒ1����ֵ�����ײ�
		String prodOfferNbr = XMLUtils.getSingleTagValue(xmlStr,"ProductOfferID");// ����ƷID
		String Status = XMLUtils.getSingleTagValue(xmlStr, "Status");// ������ϵ״̬0������6��ע��
		
		String SubscribeTime = XMLUtils.getSingleTagValue(xmlStr,"SubscribeTime");// ����ʱ��
		
		String EffDate = XMLUtils.getSingleTagValue(xmlStr, "EffDate");// ������ϵ��Чʱ��
		String ExpDate = XMLUtils.getSingleTagValue(xmlStr, "ExpDate");// ������ϵʧЧʱ��
		String ChannelPlayerID = XMLUtils.getSingleTagValue(xmlStr,	"ChannelPlayerID");//��������
		
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
			// ������Ǵ�ͳ+��ֵ���� Ҳ���������ײͣ�ֱ��ȡ��Ʒ��Ӧ������Ʒ
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
			orderType = "1"; // ����Ǵ�������ʱ���˶��Ļ�����תΪ�ڲ��˶���ʶ
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
			resultName = "�ɹ�";
		else
			resultName = "ʧ��";
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
