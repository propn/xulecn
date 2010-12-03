package com.ztesoft.vsop.order.vo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 增值产品
 * @author yi.chengfeng Mar 2, 2010 4:11:48 PM
 *
 */
public class ProductOfferInfo {
	private static Logger logger = Logger.getLogger(ProductOfferInfo.class);
	private String ProductOfferType = "";
	private String ProductOfferID = "";
	private String EffDate = "";
	private String ExpDate = "";
	private List VSubProdInfoList;
	private String actionType = "";
	private String ProdSpecCode = "";
	private String productNo = "";
	private String offNbr = "";  //接口协议过来的是外系统销售品编码，需要转换成内部的PROD_OFFER_ID
	private String systemId = "";
	
	public ProductOfferInfo(Element el, String actionType, String prodSpecCode, String productNo, String systemId) throws Exception {
		long s = System.currentTimeMillis();
		setProductOfferType(XMLUtils.getElementStringValue(el, "ProductOfferType"));
		setEffDate(XMLUtils.getElementStringValue(el, "EffDate"));
		setExpDate(XMLUtils.getElementStringValue(el, "ExpDate"));
		String offNbr = XMLUtils.getElementStringValue(el, "OffNbr");
		if("".equals(offNbr)){
			setOffNbr(XMLUtils.getElementStringValue(el, "ProductOfferID"));
			replaceProductOfferId(getOffNbr());
		}else{
			setProductOfferID(XMLUtils.getElementStringValue(el, "ProductOfferID"));
			setOffNbr(offNbr);
		}
		logger.info("replaceProductOfferId cost:" +(System.currentTimeMillis()-s));
		setProdSpecCode(prodSpecCode);
		setActionType(actionType);
		setSystemId(systemId);
		validate();
		s = System.currentTimeMillis();
		parseVSubProdInfo(el);
		logger.info("parseVSubProdInfo cost:" +(System.currentTimeMillis()-s));
		//如果业务动作是订购，并且VSubProdInfo节点是空的，也就是没有增值产品，则表示订购该销售品下的所有增值产品
		if(OrderConstant.orderTypeOfAdd.equals(actionType) && getVSubProdInfoList().size() == 0){
			List vproductList = null;
			vproductList = new OrderDao().getVProductsByProdOfferId(getProductOfferID(), actionType, getEffDate(), getExpDate());
			setVSubProdInfoList(vproductList);
		}
		//如果业务动作是退订,并且VSubProdInfo节点是空的，也就是没有增值产品，则表示退订销售品下的所有增值产品
		else if(OrderConstant.orderTypeOfDel.equals(actionType) && getVSubProdInfoList().size() == 0){
			List vproductList = null;
			vproductList = new OrderDao().getOrderedVProductsByProdOfferId(getProdSpecCode(), productNo, getProductOfferID(), actionType, getEffDate(), getExpDate());
			if(vproductList == null || (vproductList != null && vproductList.size() == 0)){
				throw new Exception("you not order productoffer yet!");
			}
			setVSubProdInfoList(vproductList);
		}
	}
	
	private void validate() throws Exception {
		//如果是订购或者退订，销售品id不能为空，有些系统吧
//		if(OrderConstant.orderTypeOfAdd.equals(getActionType()) || OrderConstant.orderTypeOfDel.equals(getActionType())){
//			if("".equals(getOffNbr()) || null == getOffNbr()) throw new Exception("销售品Id不能为空!");
//		}
	}

	/**
	 * 接口xml定义productOfferID节点是外系统销售品编码，需要转成内部的id
	 * @param offerNbr
	 * @throws SQLException 
	 */
	private void replaceProductOfferId(String offerNbr) throws SQLException {
		String productOfferId = null;
		//productOfferId = new OrderDao().getProductOfferId(offerNbr);
		productOfferId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(offerNbr);
		setProductOfferID(productOfferId);
	}

	private void parseVSubProdInfo(Element root) throws SQLException {
		Element element;
		VSubProdInfoList = new ArrayList();
		for (Iterator it = root.elementIterator("VSubProdInfo"); it.hasNext();) {
			element = (Element) it.next();
			VSubProdInfo vSubProdInfo = new VSubProdInfo(element,getEffDate(),getExpDate(),getProductOfferType(), getProductOfferID(), getActionType(), getOffNbr(), getSystemId());
			VSubProdInfoList.add(vSubProdInfo);
		}
	}

	public String orderProdOfferToXml(){
		StringBuffer bf = new StringBuffer();
		bf.append("<ProductOfferInfo>")
		.append("<ProductOfferType>").append(getProductOfferType()).append("</ProductOfferType>")
		.append("<ProductOfferID>").append(getProductOfferID()).append("</ProductOfferID>")
		.append("<OffNbr>").append(getOffNbr()).append("</OffNbr>")
		.append("<EffDate>").append(getEffDate()).append("</EffDate>")
		.append("<ExpDate>").append(getExpDate()).append("</ExpDate>");
		List subProducts = getVSubProdInfoList();
		for (Iterator subiterator = subProducts.iterator(); subiterator.hasNext();) {
			VSubProdInfo subprod = (VSubProdInfo) subiterator.next();
			bf.append(subprod.orderProductToXml());
		}
		bf.append("</ProductOfferInfo>");
		return bf.toString();
	}
	
	public ProductOfferInfo(String inXML, String actionType2,
			String prodSpecCode, String productNo, String systemId) throws Exception {
		long s = System.currentTimeMillis();
		setProductOfferType(XMLUtils.getSingleTagValue(inXML, "ProductOfferType"));
		setEffDate(XMLUtils.getSingleTagValue(inXML, "EffDate"));
		setExpDate(XMLUtils.getSingleTagValue(inXML, "ExpDate"));
		String offNbr = XMLUtils.getSingleTagValue(inXML, "OffNbr");
		if("".equals(offNbr)){
			setOffNbr(XMLUtils.getSingleTagValue(inXML, "ProductOfferID"));
			replaceProductOfferId(getOffNbr());
		}else{
			setProductOfferID(XMLUtils.getSingleTagValue(inXML, "ProductOfferID"));
			setOffNbr(offNbr);
		}
		logger.info("replaceProductOfferId cost:" +(System.currentTimeMillis()-s));
		setProdSpecCode(prodSpecCode);
		setActionType(actionType2);
		setSystemId(systemId);
		validate();
		s = System.currentTimeMillis();
		parseVSubProdInfo(inXML);
		logger.info("parseVSubProdInfo cost:" +(System.currentTimeMillis()-s));
		//如果业务动作是订购，并且VSubProdInfo节点是空的，也就是没有增值产品，则表示订购该销售品下的所有增值产品
		if(OrderConstant.orderTypeOfAdd.equals(actionType) && getVSubProdInfoList().size() == 0){
			List vproductList = null;
			vproductList = new OrderDao().getVProductsByProdOfferId(getProductOfferID(), actionType2, getEffDate(), getExpDate());
			setVSubProdInfoList(vproductList);
		}
		//如果业务动作是退订,并且VSubProdInfo节点是空的，也就是没有增值产品，则表示退订销售品下的所有增值产品
		else if(OrderConstant.orderTypeOfDel.equals(actionType) && getVSubProdInfoList().size() == 0){
			List vproductList = null;
			vproductList = new OrderDao().getOrderedVProductsByProdOfferId(getProdSpecCode(), productNo, getProductOfferID(), actionType, getEffDate(), getExpDate());
			if(vproductList == null || (vproductList != null && vproductList.size() == 0)){
				throw new Exception("you not order productoffer yet!");
			}
			setVSubProdInfoList(vproductList);
		}
	}
	
	private void parseVSubProdInfo(String inXML) throws SQLException {
		String tagName = "VSubProdInfo";
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(tagName).append(">([\\s\\S]*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		VSubProdInfoList = new ArrayList();
		while(matcher.find()){
			String result = matcher.group(1);
			VSubProdInfo vSubProdInfo = new VSubProdInfo(result,getEffDate(),getExpDate(),getProductOfferType(), getProductOfferID(), getActionType(), getOffNbr(), getSystemId());
			VSubProdInfoList.add(vSubProdInfo);
		}
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
	public ProductOfferInfo() {
	}
	
	public String getProductOfferType() {
		return ProductOfferType;
	}
	public void setProductOfferType(String productOfferType) {
		ProductOfferType = productOfferType;
	}
	public String getProductOfferID() {
		return ProductOfferID;
	}
	public void setProductOfferID(String productOfferID) {
		ProductOfferID = productOfferID;
	}
	public List getVSubProdInfoList() {
		return VSubProdInfoList;
	}
	public void setVSubProdInfoList(List subProdInfoList) {
		VSubProdInfoList = subProdInfoList;
	}
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getProdSpecCode() {
		return ProdSpecCode;
	}

	public void setProdSpecCode(String prodSpecCode) {
		ProdSpecCode = prodSpecCode;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getOffNbr() {
		return offNbr;
	}

	public void setOffNbr(String offNbr) {
		this.offNbr = offNbr;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	

}
