package com.ztesoft.vsop.order.vo;

import java.sql.Connection;
import java.sql.SQLException;

import org.dom4j.Element;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 增值产品，用于服务开通订购
 * @see OrderBo#sendOrderToActive_FK(String)
 * @author yi.chengfeng Apr 13, 2010 3:14:00 PM
 *
 */
public class VProductInfo {
	private String ActionType = "";
	private String VProductID = "";
	private String VProductInstID = "";
	private String EffDate = "";
	private String ExpDate = "";
	
	private String productId = "";
	private String productNo = "";
	private long orderItemId;
	private String productOfferId = "";//PRODUCT_OFFER_ID
	private String userPayType = "";
	private String oldVproductId = "";
	private String productOfferType = OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID; //服务开通的只有这个类型
	private String state = "";
	private String vproductNbr;  //增值产品外部系统编码，接口协议定义的VProductID，然后用该信息查询product.product_id
	private String lanCode = "";
	private String offerNbr = "";

	public VProductInfo(Element elem, String productId, String productNo, String ProductOfferId, String UserPayType,String productInstId, String lanCode, String offerNbr) throws SQLException {
		setActionType(XMLUtils.getElementStringValue(elem,"ActionType"));
		String vproductNbr = XMLUtils.getElementStringValue(elem, "VProductNbr");
		if("".equals(vproductNbr)){ //若没有VProductNbr节点则取VProductID，然后转换
			setVproductNbr(XMLUtils.getElementStringValue(elem,"VProductID"));
			//转换外部编码为内部编码
			replaceVproductId(getVproductNbr());
		}else{  //下面是服务开通定时任务的时候解析报文用的
			setVproductNbr(vproductNbr);
			setVProductID(XMLUtils.getElementStringValue(elem, "VProductID"));
		}
		setVProductInstID(XMLUtils.getElementStringValue(elem,"VProductInstID"));
		//业务动作是订购，并且没有传实例id，用序列生成一个
		if(getActionType().equals(OrderConstant.orderTypeOfAdd) && "".equals(getVProductInstID())){
			String s = getSequence();
			setVProductInstID(s);
		}else if(getActionType().equals(OrderConstant.orderTypeOfDel)){
			String vprodInstId = getVprodInstId();
			setVProductInstID(vprodInstId);
		}
		setEffDate(XMLUtils.getElementStringValue(elem,"EffDate"));
		setExpDate(XMLUtils.getElementStringValue(elem,"ExpDate"));
		setProductId(productId);
		setProductNo(productNo);
		setProductOfferId(ProductOfferId);
		setUserPayType(UserPayType);
		setLanCode(lanCode);
		setOfferNbr(offerNbr);
	}

	private String getVprodInstId() throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		String seq = new OrderDao().getVprodInstId(conn,getVProductID(),getProductNo(),getProductId());
		return seq;
//		try {
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection(conn);
//		}
	}

	private void replaceVproductId(String vproductNbr2) throws SQLException {
		String productId = null;
		productId=DcSystemParamManager.getInstance().getProductIdByNbr(vproductNbr2);
		//productId = new OrderDao().getProductId(getVproductNbr());
//		try {
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection();
//		}
		setVProductID(productId);
	}

	/**
	 * 获取序列
	 * @return
	 * @throws SQLException 
	 */
	private String getSequence() throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		long seq = new OrderDao().getSequence("SEQ_ORDER_RELATION_ID", conn);
		return String.valueOf(seq);
//		try {
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection(conn);
//		}
	}
	
	public VProductInfo() {
	}
	
	public VProductInfo(String xml, String productId, String productNo, String ProductOfferId, String UserPayType,String productInstId, String lanCode, String offerNbr) throws SQLException {
		
		setActionType(XMLUtils.getSingleTagValue(xml, "ActionType"));
		String vproductNbr = XMLUtils.getSingleTagValue(xml, "VProductNbr");
		if("".equals(vproductNbr)){ //若没有VProductNbr节点则取VProductID，然后转换
			setVproductNbr(XMLUtils.getSingleTagValue(xml,"VProductID"));
			//转换外部编码为内部编码
			replaceVproductId(getVproductNbr());
		}else{  //下面是服务开通定时任务的时候解析报文用的
			setVproductNbr(vproductNbr);
			setVProductID(XMLUtils.getSingleTagValue(xml, "VProductID"));
		}
		setVProductInstID(XMLUtils.getSingleTagValue(xml,"VProductInstID"));
		//业务动作是订购，并且没有传实例id，用序列生成一个
		if(getActionType().equals(OrderConstant.orderTypeOfAdd) && "".equals(getVProductInstID())){
			String s = getSequence();
			setVProductInstID(s);
		}else if(getActionType().equals(OrderConstant.orderTypeOfDel)){
			String vprodInstId = getVprodInstId();
			setVProductInstID(vprodInstId);
		}
		setEffDate(XMLUtils.getSingleTagValue(xml,"EffDate"));
		setExpDate(XMLUtils.getSingleTagValue(xml,"ExpDate"));
		setProductId(productId);
		setProductNo(productNo);
		setProductOfferId(ProductOfferId);
		setUserPayType(UserPayType);
		setLanCode(lanCode);
		setOfferNbr(offerNbr);
	}

	public String toXmlForSpi() {
		StringBuffer bf = new StringBuffer();
		bf 
		.append("<sub_product>")
			.append("<sub_product_code>").append(getVproductNbr()).append("</sub_product_code>")
			.append("<oldsub_product_code>").append(getOldVproductId()).append("</oldsub_product_code>")
			.append("<act_type>").append(getActType()).append("</act_type>")
			.append("<idtype>").append("2").append("</idtype>") //服开只有2类型
			.append("<id>").append(getOfferNbr()).append("</id>")
			.append("<prodCharacters>").append("").append("</prodCharacters>")
		.append("</sub_product>");
		return bf.toString();
	}
	
	private String getActType() {
		String actType = getActionType();
		if(OrderConstant.orderTypeOfDel.equals(getActionType())) actType = "3";
		if(OrderConstant.orderTypeOfAll.equals(getActionType())) actType = "3";
		if(OrderConstant.VProductActionTypeOfPendding.equals(getActionType())) actType = "1";
		return actType;
	}

	public String getActionType() {
		return ActionType;
	}

	public void setActionType(String actionType) {
		ActionType = actionType;
	}

	public String getVProductID() {
		return VProductID;
	}

	public void setVProductID(String productID) {
		VProductID = productID;
	}

	public String getEffDate() {
		return EffDate;
	}

	public void setEffDate(String effDate) {
		EffDate = effDate;
	}

	public String getExpDate() {
		return ExpDate;
	}

	public void setExpDate(String expDate) {
		ExpDate = expDate;
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

	public String getProductOfferId() {
		return productOfferId;
	}

	public void setProductOfferId(String productOfferId) {
		this.productOfferId = productOfferId;
	}

	public String getUserPayType() {
		return userPayType;
	}

	public void setUserPayType(String userPayType) {
		this.userPayType = userPayType;
	}

	public String getOldVproductId() {
		return oldVproductId;
	}

	public void setOldVproductId(String oldVproductId) {
		this.oldVproductId = oldVproductId;
	}


	public String getVProductInstID() {
		return VProductInstID;
	}


	public void setVProductInstID(String productInstID) {
		this.VProductInstID = productInstID;
	}

	public String getProductOfferType() {
		return productOfferType;
	}

	public void setProductOfferType(String productOfferType) {
		this.productOfferType = productOfferType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toXml() {
		StringBuffer bf = new StringBuffer("");
		bf
		.append("<VProductInfo>")
			.append("<ActionType>").append(getActionType()).append("</ActionType>")
			.append("<VProductID>").append(getVProductID()).append("</VProductID>")
			.append("<VProductNbr>").append(getVproductNbr()).append("</VProductNbr>")
			.append("<VProductInstID>").append(getVProductInstID()).append("</VProductInstID>")
			.append("<EffDate>").append(getEffDate()).append("</EffDate>")
			.append("<ExpDate>").append(getExpDate()).append("</ExpDate>")
		.append("</VProductInfo>");
		return bf.toString();
	}

	public String getVproductNbr() {
		return vproductNbr;
	}

	public void setVproductNbr(String vproductNbr) {
		this.vproductNbr = vproductNbr;
	}

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	public String getOfferNbr() {
		return offerNbr;
	}

	public void setOfferNbr(String offerNbr) {
		this.offerNbr = offerNbr;
	}
	
}
