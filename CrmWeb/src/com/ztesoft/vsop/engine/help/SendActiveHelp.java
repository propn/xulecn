package com.ztesoft.vsop.engine.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * �ͼ������
 * 
 * @author cooltan
 * 
 */
public class SendActiveHelp {
	private static Logger logger = Logger.getLogger(SendActiveHelp.class);

	/**
	 * ���ݶ������󴴽�����XML��Ϣ��
	 * 
	 * @param order
	 * @return
	 */
	public String createSpiXml(CustomerOrder order)
			throws ProductHasNoPlatformException {
		StringBuffer bf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<root>").append("<msg_head>").append("<from>").append(
				order.getOrderSystem()).append("</from>").append("<to>")
				.append("VSOP").append("</to>").append("<msg_type>").append(
						"message_request").append("</msg_type>").append(
						"<serial>").append(order.getCustSoNumber()).append(
						"</serial>").append("<out_order_type>").append(
						"VSOP001").append("</out_order_type>").append(
						"</msg_head>")

				.append("<interface_msg>").append("<public>").append(
						"<order_id>").append(order.getCustOrderId()).append(
						"</order_id>").append("<out_order_id>").append(
						order.getOtherSysOrderId()).append("</out_order_id>")
				.append("<order_act_type>").append(order.getCustOrderType())
				.append("</order_act_type>").append("</public>")

				.append("<user_info>").append("<pproductoffer_info>").append(
						"<ppproduct_act_type>")
				.append(order.getCustOrderType()).append(
						"</ppproduct_act_type>")
				.append("</pproductoffer_info>").append("<prod_info>").append(
						"<prod_no>").append(order.getAccNbr()).append(
						"</prod_no>").append("<prod_type>").append(
						order.getProdId()).append("</prod_type>").append(
						"<area_code>").append(order.getLanId()).append(
						"</area_code>").append("<prod_inst_id>").append(
						order.getProdInstId()).append("</prod_inst_id>")
				.append("</prod_info>");

		String subProdXml = createSubProductsXml(order);
		bf.append(subProdXml);
		bf.append("</user_info>").append("</interface_msg>").append("</root>");
		return bf.toString();
	}

	/**
	 * ��������XML��sub_products�ڵ㣬����<sub_products><sub_product></sub_product>......</sub_products>
	 * 
	 * @param order
	 * @return
	 * @throws SQLException
	 * @throws ProductHasNoPlatformException
	 */
	private String createSubProductsXml(CustomerOrder order)
			throws ProductHasNoPlatformException {
		StringBuffer subProductNodeBuffer = new StringBuffer();
		subProductNodeBuffer.append("<sub_products>");
		List productIdArray = new ArrayList();// ������ֵ��Ʒ��ʶ�б����
		Map prodHolder = new HashMap(); // Map<productId,VproductInfo>
		// 1׼������
		// 1.1׼�����ݣ���������ֵ��ƷIDƴװ���ַ���������productid1,productid2,******
		List productOfferInfos = order.getProductOfferInfoList();
		List vProductList = new ArrayList();
		for (Iterator iterator = productOfferInfos.iterator(); iterator
				.hasNext();) {
			ProductOfferInfo productOffer = (ProductOfferInfo) iterator.next();
			vProductList.addAll(productOffer.getVproductInfoList());
		}
		for (Iterator iterator = vProductList.iterator(); iterator.hasNext();) {
			VproductInfo p = (VproductInfo) iterator.next();
			String productId = p.getVProductId();
			productIdArray.add(productId);
			prodHolder.put(productId, p);
		}
		String productids = joinStringList(productIdArray);
		logger.info("createSubProductsXml productids: " + productids);
		// 1.2׼�����ݣ����ݻ����ȡ����Ʒ��ʶ-ƽ̨�б�ƽ̨-��Ʒ��ʶ�б� ����HashMap
		Map[] ret = null;
		ret = this.findPlatform(productids);
		// 2�����ڵ�
		if (ret != null && ret.length == 2) {
			// 2.1����control�ڵ�
			logger.info("create control node.");
			Map prodMapPlamform = ret[0];
			subProductNodeBuffer.append("<control>");
			int i = 0;
			for (Iterator iterator = vProductList.iterator(); iterator
					.hasNext();) {
				VproductInfo p = (VproductInfo) iterator.next();
				String productId = p.getVProductId();
				List plamforms = (List) prodMapPlamform.get(productId);
				if (plamforms == null){
					logger.info("cannot find any platform for product->"
									+ productId);
					throw new ProductHasNoPlatformException(
							"��ֵ��Ʒû�ж�Ӧ�Ľ���ƽ̨");
				}
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
			// 2.2����ҵ��ƽ̨N����ڵ�
			logger.info("create sub plamform node.");
			Map PlamformMapProd = ret[1];
			Set plamformIdSet = PlamformMapProd.keySet();
			for (Iterator iterator = plamformIdSet.iterator(); iterator
					.hasNext();) {
				String plamformId = (String) iterator.next();
				List productIdList = (List) PlamformMapProd.get(plamformId);
				subProductNodeBuffer.append("<p").append(plamformId)
						.append(">");
				for (Iterator productItr = productIdList.iterator(); productItr
						.hasNext();) {
					String productId = (String) productItr.next();
					VproductInfo p = (VproductInfo) prodHolder.get(productId);
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

	/**
	 * �������е����ж���ƴװ���ַ��� ���� string1,string2....
	 * 
	 * @param productIdArray
	 * @return
	 */
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
	 * ������ֵ��Ʒ��ʶ����ȡ����Map: ��Ʒ��ʶ-ƽ̨�б�ƽ̨-��Ʒ��ʶ�б�
	 * 
	 * @param productids
	 * @return
	 * @throws SQLException
	 */
	private Map[] findPlatform(String productids) {
		Map prodMapPlatform = new HashMap();
		Map platformMapProd = new HashMap();
		// cooltan �޸ĳɻ����ȡ����
		String[] products = productids.split(",");
		if (products != null && products.length > 0) {
			for (int i = 0; i < products.length; i++) {
				String productId = products[i];
				List prodPlatforms = DcSystemParamManager.getInstance()
						.getProductPlatformsById(productId);
				prodMapPlatform.put(productId, prodPlatforms);
				if (prodPlatforms != null) {
					for (int j = 0; j < prodPlatforms.size(); j++) {
						String platformId = (String) prodPlatforms.get(j);
						List tmp = (List) platformMapProd.get(platformId);
						if (tmp == null) {
							tmp = new ArrayList();
							tmp.add(productId);
							platformMapProd.put(platformId, tmp);
						} else {
							tmp.add(productId);
						}
					}
				}
			}
		}
		return new Map[] { prodMapPlatform, platformMapProd };
	}
}
