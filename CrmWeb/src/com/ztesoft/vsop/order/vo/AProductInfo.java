package com.ztesoft.vsop.order.vo;

import java.sql.Connection;
import java.sql.SQLException;

import org.dom4j.Element;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.dao.VsopSubOrderInfoDAO;

/**
 * 附属产品，用于服务开通订购
 * @see OrderBo#sendOrderToActive_FK(String)
 * @author yi.chengfeng Apr 13, 2010 3:23:18 PM
 *
 */
public class AProductInfo {
	private String ActionType = "";
	private String AProductID = "";
	private String AProductInstID = "";
	private String productId = "";
	private String productNo = "";
	
	private long orderItemId;
	private String lanCode = "";
	
	public AProductInfo(Element elem, String productId, String productNo, String lanCode) {
		setActionType(XMLUtils.getElementStringValue(elem,"ActionType"));
		setAProductID(XMLUtils.getElementStringValue(elem,"AProductID"));
		setAProductInstID(XMLUtils.getElementStringValue(elem,"AProductInstID"));
		//业务动作是订购，并且没有传实例id，用序列生成一个
		if(getActionType().equals(OrderConstant.orderTypeOfAdd) && "".equals(getAProductInstID())){
			String s = getSequence();
			setAProductInstID(s);
		}
		setProductId(productId);
		setProductNo(productNo);
		setLanCode(lanCode);
	}
	public AProductInfo(String xml, String productId, String productNo, String lanCode) {
		setActionType(XMLUtils.getSingleTagValue(xml,"ActionType"));
		setAProductID(XMLUtils.getSingleTagValue(xml,"AProductID"));
		setAProductInstID(XMLUtils.getSingleTagValue(xml,"AProductInstID"));
		//业务动作是订购，并且没有传实例id，用序列生成一个
		if(getActionType().equals(OrderConstant.orderTypeOfAdd) && "".equals(getAProductInstID())){
			String s = getSequence();
			setAProductInstID(s);
		}
		setProductId(productId);
		setProductNo(productNo);
		setLanCode(lanCode);
	}
	/**
	 * 获取序列
	 * @return
	 */
	private String getSequence() {
		Connection conn = LegacyDAOUtil.getConnection();
		try {
			long seq = new OrderDao().getSequence("SEQ_BIZ_CAPABILITY_INST_ID", conn);
			
			return String.valueOf(seq);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			LegacyDAOUtil.releaseConnection(conn);
		}
		return "";
	}
	
	public String getActionType() {
		return ActionType;
	}

	public void setActionType(String actionType) {
		ActionType = actionType;
	}

	public String getAProductID() {
		return AProductID;
	}

	public void setAProductID(String productID) {
		AProductID = productID;
	}

	public String getAProductInstID() {
		return AProductInstID;
	}

	public void setAProductInstID(String productInstID) {
		AProductInstID = productInstID;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public long getOrderItemId() {
		return orderItemId;
	}


	public void setOrderItemId(long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String toXml() {
		StringBuffer bf = new StringBuffer("");
		bf
		.append("<AProductInfo>")
			.append("<ActionType>").append(getActionType()).append("</ActionType>")
			.append("<AProductID>").append(getAProductID()).append("</AProductID>")
			.append("<AProductInstID>").append(getAProductInstID()).append("</AProductInstID>")
		.append("</AProductInfo>");
		return bf.toString();
	}
	public String getLanCode() {
		return lanCode;
	}
	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	
}
