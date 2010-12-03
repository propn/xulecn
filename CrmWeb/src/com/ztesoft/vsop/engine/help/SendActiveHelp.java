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
 * 送激活辅助类
 * 
 * @author cooltan
 * 
 */
public class SendActiveHelp {
	private static Logger logger = Logger.getLogger(SendActiveHelp.class);

	/**
	 * 根据订单对象创建激活XML信息；
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
	 * 创建激活XML的sub_products节点，形如<sub_products><sub_product></sub_product>......</sub_products>
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
		List productIdArray = new ArrayList();// 所有增值产品标识列表对象
		Map prodHolder = new HashMap(); // Map<productId,VproductInfo>
		// 1准备数据
		// 1.1准备数据－将所有增值产品ID拼装成字符串，形如productid1,productid2,******
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
		// 1.2准备数据－根据缓存获取：产品标识-平台列表；平台-产品标识列表 两个HashMap
		Map[] ret = null;
		ret = this.findPlatform(productids);
		// 2创建节点
		if (ret != null && ret.length == 2) {
			// 2.1生成control节点
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
							"增值产品没有对应的接入平台");
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
			// 2.2生成业务平台N编码节点
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
	 * 将数组中的所有对象拼装成字符串 形如 string1,string2....
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
	 * 根据增值产品标识串获取两个Map: 产品标识-平台列表；平台-产品标识列表
	 * 
	 * @param productids
	 * @return
	 * @throws SQLException
	 */
	private Map[] findPlatform(String productids) {
		Map prodMapPlatform = new HashMap();
		Map platformMapProd = new HashMap();
		// cooltan 修改成缓存读取数据
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
