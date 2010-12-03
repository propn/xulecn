package com.ztesoft.vsop.engine.help;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdVO;

public class OrderRelationToGroupHelp {
	public List createToGroupXML(CustomerOrder order){
		List XmlList = new ArrayList();
		String timeStamp = DateUtil.formatDate(CrmConstants.DATE_FORMAT_8);//yyyymmdd
		List prodOfferList = order.getProductOfferInfoList();
		for (int i = 0; i < prodOfferList.size(); i++) {
			ProductOfferInfo prodOffer = (ProductOfferInfo) prodOfferList.get(i);
			List productList = prodOffer.getVproductInfoList();
			for (int j = 0; j < productList.size(); j++) {
				VproductInfo product = (VproductInfo) productList.get(j);
				ProdVO prodVo = DcSystemParamManager.getInstance().getProductVOByid(product.getVProductId());
				if("0".equals(prodVo.getScope())){ //如果是全网业务
					String state = "0";
					if(OrderConstant.orderStateOfDelete.equals(product.getState()))
						state = "6";
					StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					sb.append("<SubsInstSynBWVSOPReq>")
						.append("<StreamingNo>").append(order.getCustSoNumber()).append("</StreamingNo>")
						.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
						.append("<SystemId>").append("200").append("</SystemId>")
						.append("<ProdSpecCode>").append(order.getProdId()).append("</ProdSpecCode>")
						.append("<ProductNo>").append(order.getAccNbr()).append("</ProductNo>")
						.append("<VproductID>").append(prodVo.getProdId()).append("</VproductID>")
						.append("<SPID>").append(prodVo.getProdProviderId()).append("</SPID>")
						.append("<ProductOfferType>").append(prodOffer.getOfferType()).append("</ProductOfferType>")
						.append("<ProductOfferID>").append(prodOffer.getOfferId()).append("</ProductOfferID>")
						.append("<Status>").append(state).append("</Status>")
						.append("<SubscribeTime>").append(DAOUtils.getCurrentTimestamp()).append("</SubscribeTime>")
						.append("<EffDate>").append(product.getEffDate()).append("</EffDate>")
						.append("<ExpDate>").append(product.getExpDate()).append("</ExpDate>")
						.append("<ChannelPlayerID>").append(order.getOrderChn()).append("</ChannelPlayerID>")		
						.append("</SubsInstSynBWVSOPReq>");
						XmlList.add(sb.toString());
					}
				}
		}
		return null;
	}
}
