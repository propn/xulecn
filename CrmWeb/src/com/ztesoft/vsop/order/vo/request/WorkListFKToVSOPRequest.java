package com.ztesoft.vsop.order.vo.request;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.exception.ProductExistsException;
import com.ztesoft.vsop.order.exception.ProductNotExistsException;
import com.ztesoft.vsop.order.vo.AProductInfo;
import com.ztesoft.vsop.order.vo.VProductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class WorkListFKToVSOPRequest {
	private String StreamingNo = "";
	private String TimeStamp = "";
	private String SystemId = "";
	private String OrderId = "";
	private String ActionType = "";
	private String PorductInstID = "";
	private String ReginID = "";
	private String ProdSpecCode = "";
	private String ProductNo = "";
	private String OldProductNo = "";
	private String ProductOfferId = "";
	private String UserState = "";
	private String UserPayType = "";
	private List VProductInfoList; 
	private List AProductInfoList;
	private String lanCode;
	private String offerNbr="";
	
	private long sequence;
	
	private String state;

	public void loadFromXml(String inXML) throws DocumentException, SQLException, ProductExistsException, ProductNotExistsException{
		Document doc = XMLUtils.parse(inXML);
		Element root = doc.getRootElement();
		setStreamingNo(XMLUtils.getElementStringValue(root,"StreamingNo"));
		setTimeStamp(XMLUtils.getElementStringValue(root,"TimeStamp"));
		setSystemId(XMLUtils.getElementStringValue(root,"SystemId"));
		setOrderId(XMLUtils.getElementStringValue(root,"OrderId"));
		setActionType(XMLUtils.getElementStringValue(root,"ActionType"));
		setPorductInstID(XMLUtils.getElementStringValue(root,"PorductInstID"));
		setReginID(XMLUtils.getElementStringValue(root,"ReginID"));
		setProdSpecCode(XMLUtils.getElementStringValue(root,"ProdSpecCode"));
		setProductNo(XMLUtils.getElementStringValue(root,"ProductNo"));
		setOldProductNo(XMLUtils.getElementStringValue(root,"OldProductNo"));
		String offNbr = XMLUtils.getElementStringValue(root, "OffNbr");
		if("".equals(offNbr)){
			setOfferNbr(XMLUtils.getElementStringValue(root, "ProductOfferId"));//ProductOfferId
			replaceProductOfferId(getOfferNbr());
		}else{
			setProductOfferId(XMLUtils.getElementStringValue(root, "ProductOfferId"));
			setOfferNbr(offNbr);
		}
		String seq = XMLUtils.getElementStringValue(root,"InOrderId");
		if(!"".equals(seq)){
			setSequence(Long.parseLong(seq));
		}
		setUserState(XMLUtils.getElementStringValue(root,"UserState"));
		setUserPayType(XMLUtils.getElementStringValue(root,"UserPayType"));
		//如果是新增产品则新建一个产品实例
		if(OrderConstant.orderTypeOfInstall.equals(getActionType())){
			if("".equals(getPorductInstID())){
				String prodInstId = createProductInstId();
				setPorductInstID(prodInstId);
			}
		}
		parseVProductInfoList(root);
		parseAProductInfoList(root);
	}

	private void replaceProductOfferId(String offerNbr2) {
		String productOfferId = null;
		//productOfferId = new OrderDao().getProductOfferId(offerNbr);
		productOfferId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(offerNbr2);
		setProductOfferId(productOfferId);
	}

	private String getProductInstId() throws ProductNotExistsException, SQLException {
		OrderDao orderDao = new OrderDao();
		String[] prodInstIdAndLanCode = orderDao.getProductInstId(getProductNo(),getProdSpecCode());
//		try {
//			if("".equals(prodInstIdAndLanCode[0])) throw new ProductNotExistsException("product->" + getProductNo() + " not exists.");
//			return prodInstIdAndLanCode[0];
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection(conn);
//		}
		return prodInstIdAndLanCode[0];
	}

	private String createProductInstId() throws ProductExistsException, SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		OrderDao orderDao = new OrderDao();
//		String[] prodInstIdAndLanCode = orderDao.getProductInstId(getProductNo(),getProdSpecCode());
//		if(!"".equals(prodInstIdAndLanCode[0])) throw new ProductExistsException("product->" + getProductNo() + " already exists.");
		long seq = orderDao.getSequence("seq_prod_inst_id", conn);
		return String.valueOf(seq);
//		try {
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection(conn);
//		}
	}

	private void parseAProductInfoList(Element root) {
		if(OrderConstant.orderTypeOfInstall.equals(getActionType()) || OrderConstant.orderTypeOfModifyAProduct.equals(getActionType())){
			Element elem;
			this.AProductInfoList = new ArrayList();
			for (Iterator it = root.elementIterator("AProductInfo"); it.hasNext();) {
				elem = (Element) it.next();
				AProductInfo aproduct = new AProductInfo(elem, getProdSpecCode(), getProductNo(), getReginID());
				this.AProductInfoList.add(aproduct);
			}
		}
	}

	private void parseVProductInfoList(Element root) throws SQLException {
		if(OrderConstant.orderTypeOfInstall.equals(getActionType()) || OrderConstant.orderTypeOfModifyVProduct.equals(getActionType())){
			Element elem;
			this.VProductInfoList = new ArrayList();
			for (Iterator it = root.elementIterator("VProductInfo"); it.hasNext();) {
				elem = (Element) it.next();
				VProductInfo vproduct = new VProductInfo(elem, getProdSpecCode(), getProductNo(), getProductOfferId(), getUserPayType(),getPorductInstID(), getReginID(), getOfferNbr());
				this.VProductInfoList.add(vproduct);
			}
		}
	}
	public void loadFromXml(String inXML, String string) throws ProductExistsException, SQLException {
		setStreamingNo(XMLUtils.getSingleTagValue(inXML,"StreamingNo"));
		setTimeStamp(XMLUtils.getSingleTagValue(inXML,"TimeStamp"));
		setSystemId(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		setOrderId(XMLUtils.getSingleTagValue(inXML,"OrderId"));
		setActionType(XMLUtils.getSingleTagValue(inXML,"ActionType"));
		setPorductInstID(XMLUtils.getSingleTagValue(inXML,"PorductInstID"));
		setReginID(XMLUtils.getSingleTagValue(inXML,"ReginID"));
		setProdSpecCode(XMLUtils.getSingleTagValue(inXML,"ProdSpecCode"));
		setProductNo(XMLUtils.getSingleTagValue(inXML,"ProductNo"));
		setOldProductNo(XMLUtils.getSingleTagValue(inXML,"OldProductNo"));
		String offNbr = XMLUtils.getSingleTagValue(inXML, "OffNbr");
		if("".equals(offNbr)){
			setOfferNbr(XMLUtils.getSingleTagValue(inXML, "ProductOfferId"));//ProductOfferId
			replaceProductOfferId(getOfferNbr());
		}else{
			setProductOfferId(XMLUtils.getSingleTagValue(inXML, "ProductOfferId"));
			setOfferNbr(offNbr);
		}
		String seq = XMLUtils.getSingleTagValue(inXML,"InOrderId");
		if(!"".equals(seq)){
			setSequence(Long.parseLong(seq));
		}
		setUserState(XMLUtils.getSingleTagValue(inXML,"UserState"));
		setUserPayType(XMLUtils.getSingleTagValue(inXML,"UserPayType"));
		//如果是新增产品则新建一个产品实例
		if(OrderConstant.orderTypeOfInstall.equals(getActionType())){
			if("".equals(getPorductInstID())){
				String prodInstId = createProductInstId();
				setPorductInstID(prodInstId);
			}
		}
		parseVProductInfoList(inXML);
		parseAProductInfoList(inXML);
	}
	private void parseAProductInfoList(String inXML) {
		if(OrderConstant.orderTypeOfInstall.equals(getActionType()) || OrderConstant.orderTypeOfModifyAProduct.equals(getActionType())){
			this.AProductInfoList = new ArrayList();
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "AProductInfo";
			bf.append("<").append(tagName ).append(">(.*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			while(matcher.find()){
				result = matcher.group(1);
				AProductInfo aproduct = new AProductInfo(result, getProdSpecCode(), getProductNo(), getReginID());
				this.AProductInfoList.add(aproduct);
			}
//			for (Iterator it = root.elementIterator("AProductInfo"); it.hasNext();) {
//				elem = (Element) it.next();
//				AProductInfo aproduct = new AProductInfo(elem, getProdSpecCode(), getProductNo(), getReginID());
//				this.AProductInfoList.add(aproduct);
//			}
		}
	}

	private void parseVProductInfoList(String inXML) throws SQLException {
		if(OrderConstant.orderTypeOfInstall.equals(getActionType()) || OrderConstant.orderTypeOfModifyVProduct.equals(getActionType())){
			this.VProductInfoList = new ArrayList();
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "VProductInfo";
			bf.append("<").append(tagName ).append(">(.*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			while(matcher.find()){
				result = matcher.group(1);
				VProductInfo vproduct = new VProductInfo(result, getProdSpecCode(), getProductNo(), getProductOfferId(), getUserPayType(),getPorductInstID(), getReginID(), getOfferNbr());
				this.VProductInfoList.add(vproduct);
			}
		}
	}

	public String toXml(){
		StringBuffer bf = new StringBuffer();
		bf
		.append("<WorkListFKToVSOPReq>")
			.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
			.append("<TimeStamp>").append(getTimeStamp()).append("</TimeStamp>")
			.append("<SystemId>").append(getSystemId()).append("</SystemId>")
			.append("<OrderId>").append(getOrderId()).append("</OrderId>")
			.append("<InOrderId>").append(getSequence()).append("</InOrderId>")
			.append("<ActionType>").append(getActionType()).append("</ActionType>")
			.append("<PorductInstID>").append(getPorductInstID()).append("</PorductInstID>")
			.append("<ReginID>").append(getReginID()).append("</ReginID>")
			.append("<ProdSpecCode>").append(getProdSpecCode()).append("</ProdSpecCode>")
			.append("<ProductNo>").append(getProductNo()).append("</ProductNo>")
			.append("<OldProductNo>").append(getOldProductNo()).append("</OldProductNo>")
			.append("<ProductOfferId>").append(getProductOfferId()).append("</ProductOfferId>")
			.append("<OffNbr>").append(getOfferNbr()).append("</OffNbr>")
			.append("<UserState>").append(getUserState()).append("</UserState>")
			.append("<UserPayType>").append(getUserPayType()).append("</UserPayType>");
		List vprodList = getVProductInfoList();
		if(vprodList != null){
			for (Iterator vprodItr = vprodList.iterator(); vprodItr.hasNext();) {
				VProductInfo vprod = (VProductInfo) vprodItr.next();
				bf.append(vprod.toXml());
			}
		}
		List aprodList = getAProductInfoList();
		if(aprodList != null){
			for (Iterator aprodItr = aprodList.iterator(); aprodItr.hasNext();) {
				AProductInfo aprod = (AProductInfo) aprodItr.next();
				bf.append(aprod.toXml());
			}
		}
		bf.append("</WorkListFKToVSOPReq>");
		return bf.toString();
	}

	public String getStreamingNo() {
		return StreamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		StreamingNo = streamingNo;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getSystemId() {
		return SystemId;
	}
	public void setSystemId(String systemId) {
		SystemId = systemId;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getActionType() {
		return ActionType;
	}
	public void setActionType(String actionType) {
		ActionType = actionType;
	}
	public String getPorductInstID() {
		return PorductInstID;
	}
	public void setPorductInstID(String porductInstID) {
		PorductInstID = porductInstID;
	}
	public String getReginID() {
		return ReginID;
	}
	public void setReginID(String reginID) {
		ReginID = reginID;
	}
	public String getProdSpecCode() {
		return ProdSpecCode;
	}
	public void setProdSpecCode(String prodSpecCode) {
		ProdSpecCode = prodSpecCode;
	}
	public String getProductNo() {
		return ProductNo;
	}
	public void setProductNo(String productNo) {
		
		ProductNo = productNo;
	}
	public String getOldProductNo() {
		return OldProductNo;
	}
	public void setOldProductNo(String oldProductNo) {
		OldProductNo = oldProductNo;
	}
	public String getProductOfferId() {
		return ProductOfferId;
	}
	public void setProductOfferId(String productOfferId) {
		ProductOfferId = productOfferId;
	}
	public String getUserState() {
		return UserState;
	}
	public void setUserState(String userState) {
		UserState = userState;
	}
	public String getUserPayType() {
		return UserPayType;
	}
	public void setUserPayType(String userPayType) {
		UserPayType = userPayType;
	}
	public List getVProductInfoList() {
		return VProductInfoList;
	}
	public void setVProductInfoList(List productInfoList) {
		VProductInfoList = productInfoList;
	}
	public List getAProductInfoList() {
		return AProductInfoList;
	}
	public void setAProductInfoList(List productInfo) {
		AProductInfoList = productInfo;
	} 
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getSequence() {
		return sequence;
	}
	public void setSequence(long sequence) {
		this.sequence = sequence;
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
