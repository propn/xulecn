package com.ztesoft.vsop.order.vo.request;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.exception.NoVproductException;
import com.ztesoft.vsop.order.vo.ProductOfferInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 订单信息
 * @author yi.chengfeng Mar 2, 2010 4:10:08 PM
 *
 */
public class SubscribeToVSOPRequest {
	private static Logger logger = Logger.getLogger(SubscribeToVSOPRequest.class);
	private long sequence; //主键，内部订单号
	private String StreamingNo = "";
	private String TimeStamp = "";
	private String SystemId = "";
	private String ActionType = "";
	private String OrderId = "";// 外部订单id
	private String ProdSpecCode = "";
	private String ProductNo = "";
	private List ProductOfferInfo;
	
	private boolean needSendConfirm = false;
//	private List aProductList;	//没有附属产品
	private String ProductOfferId = "";
	private String state = "";
	private String OldProductNo = "";
	private String ProductId = "";  //同ProdSpecCode，有些代码用ProductId
	private String lanCode = "";
	
	private List pprodOfferList;  //退订所有 actionType=2 时使用
	private List vproductList;  //退订所有 actionType=2 时使用
	
	private String prodInstId = "";
	
	String userState = "";
	String payMode = "";
	public void loadFromXML(String inXML, String patternParse) throws Exception{
		long s1 = System.currentTimeMillis();
		setStreamingNo(XMLUtils.getSingleTagValue(inXML, "StreamingNo"));
		setTimeStamp(XMLUtils.getSingleTagValue(inXML, "TimeStamp"));
		setSystemId(XMLUtils.getSingleTagValue(inXML, "SystemId"));
		setActionType(XMLUtils.getSingleTagValue(inXML, "ActionType"));
		setOrderId(XMLUtils.getSingleTagValue(inXML, "OrderId"));
		setProdSpecCode(XMLUtils.getSingleTagValue(inXML, "ProdSpecCode"));
		setProductNo(XMLUtils.getSingleTagValue(inXML, "ProductNo"));
		setProdInstId(XMLUtils.getSingleTagValue(inXML, "ProdInstId"));
		//yi.chengfeng 2010-7-10 支持没有产品类型入参的修改 start
		String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			//江西省ProductNo=区号+产品号码，根据该信息查询ProdSpecCode等并赋值
			resetProdSpecCode();
		}
		//yi.chengfeng 2010-7-10 支持没有产品类型入参的修改 end
		long s = System.currentTimeMillis();
		parseProductOfferInfo(inXML);
		logger.info("pattern parseProductOfferInfo cost:"+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		//如果业务动作时退订所有
		if(OrderConstant.orderTypeOfAll.equals(getActionType())){
			Map ret = new OrderDao().getOrderedVproducts(getProdSpecCode(),getProductNo(),OrderConstant.orderTypeOfDel);//actionType 用退订的，鉴权复用退订的逻辑
			setPprodOfferList((List)ret.get("pprodofferList"));
			setVproductList((List)ret.get("vproductList"));
		}
		logger.info("pattern orderTypeOfAll cost:"+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		validate();
		logger.info("pattern loadFromXML cost:"+(System.currentTimeMillis()-s1));
	}
	public void loadFromXML(String inXML) throws Exception{
		long s1 = System.currentTimeMillis();
		Document doc = XMLUtils.parse(inXML);
		logger.info("XMLUtils.parse(inXML) cose:" + (System.currentTimeMillis()-s1));
		Element root = doc.getRootElement();
		setStreamingNo(XMLUtils.getElementStringValue(root,"StreamingNo"));
		setTimeStamp(XMLUtils.getElementStringValue(root,"TimeStamp"));
		setSystemId(XMLUtils.getElementStringValue(root,"SystemId"));
		setActionType(XMLUtils.getElementStringValue(root,"ActionType"));
		setOrderId(XMLUtils.getElementStringValue(root,"OrderId"));
		setProdSpecCode(XMLUtils.getElementStringValue(root,"ProdSpecCode"));
		setProductNo(XMLUtils.getElementStringValue(root,"ProductNo"));
		setProdInstId(XMLUtils.getElementStringValue(root,"ProdInstId"));
		//yi.chengfeng 2010-7-10 支持没有产品类型入参的修改 start
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			//江西省ProductNo=区号+产品号码，根据该信息查询ProdSpecCode等并赋值
			resetProdSpecCode();
		}
		//yi.chengfeng 2010-7-10 支持没有产品类型入参的修改 end
		long s = System.currentTimeMillis();
		parseProductOfferInfo(root);
		logger.info("parseProductOfferInfo cost:"+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		//如果业务动作时退订所有
		if(OrderConstant.orderTypeOfAll.equals(getActionType())){
			Map ret = new OrderDao().getOrderedVproducts(getProdSpecCode(),getProductNo(),OrderConstant.orderTypeOfDel);//actionType 用退订的，鉴权复用退订的逻辑
			setPprodOfferList((List)ret.get("pprodofferList"));
			setVproductList((List)ret.get("vproductList"));
		}
		logger.info("orderTypeOfAll cost:"+(System.currentTimeMillis()-s));
		s = System.currentTimeMillis();
		validate();
		logger.info("loadFromXML cost:"+(System.currentTimeMillis()-s1));
		
	}
	
	/**
	 * ProductNo=区号+产品号码，根据该信息查询ProdSpecCode等并赋值
	 * 江西：网厅、10000坐席，短厅、10000语言系统入参是产品大类，需要转换;后续是否不需要判断系统编码，所有编码统一这样处理？
	 * yi.chengfeng add 2010-7-10
	 * @throws SQLException 
	 */
	private void resetProdSpecCode() throws SQLException {
		String origProdSpecCode = getProdSpecCode(); //原来的产品类型，产品大类
		String systemCode = getSystemId();  //外系统编码
		String lanCodeTmp = null;
		String productNoTmp = null;
		if(SystemCode.CT10000.equals(systemCode) || SystemCode.SEAT_10000.equals(systemCode) ||
				SystemCode.SMS.equals(systemCode) || SystemCode.VOICE_10000.equals(systemCode) || SystemCode.WAP.equals(systemCode)){
			
			if(OrderConstant.PROD_SPEC_CODE_TELEPHONE.equals(origProdSpecCode) || 
					OrderConstant.PROD_SPEC_CODE_PHS.equals(origProdSpecCode) ||
					OrderConstant.PROD_SPEC_CODE_WAN.equals(origProdSpecCode) ||
					OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
				
				if(OrderConstant.PROD_SPEC_CODE_MOBILE.equals(origProdSpecCode)){
					productNoTmp = getProductNo();
				}else{
					lanCodeTmp = getProductNo().substring(0,4);
					productNoTmp = getProductNo().substring(4);
				}
				String[] prodInfos = new OrderDao().getProdInstInfos(lanCodeTmp,productNoTmp);
				setProdInstId(prodInfos[0]);
				setLanCode(prodInfos[1]);
				setUserState(prodInfos[2]);
				setPayMode(prodInfos[3]);
				setProdSpecCode(prodInfos[4]);
				setProductNo(productNoTmp);
			}
		}
	}
	
	private void validate() throws DocumentException, NoVproductException {
		if(OrderConstant.orderTypeOfAdd.equals(getActionType()) || OrderConstant.orderTypeOfDel.equals(getActionType())){
			if(ProductOfferInfo.size()<1){
				throw new DocumentException("ProductOfferInfo node required!");
			}
			//判断是否有增值产品可以订购或者退订
//			int count = 0;
//			for (Iterator iterator = ProductOfferInfo.iterator(); iterator.hasNext();) {
//				ProductOfferInfo productOffer = (ProductOfferInfo) iterator.next();
//				List vprodList = productOffer.getVSubProdInfoList();
//				for (Iterator iterator2 = vprodList.iterator(); iterator2
//						.hasNext();) {
//					VSubProdInfo vprod = (VSubProdInfo) iterator2.next();
//					count++;
//				}
//			}
//			if(count==0) throw new DocumentException("no vproduct");
		}
		//全部退订情况下检查是否用户已经订购了增值产品
		if(OrderConstant.orderTypeOfAll.equalsIgnoreCase(getActionType())){
			if(getVproductList().size()==0) throw new NoVproductException("no any vproduct to cancel!");
		}
	}

	private void parseProductOfferInfo(Element root) throws Exception {
		//如果是退订所有不需要解析该节点
		if(OrderConstant.orderTypeOfAll.equals(getActionType())){
			return;
		}
		Element extElement;
		this.ProductOfferInfo = new ArrayList();
		for (Iterator it = root.elementIterator("ProductOfferInfo"); it.hasNext();) {
			extElement = (Element) it.next();
			ProductOfferInfo productOfferInfo = new ProductOfferInfo(extElement, getActionType(), getProdSpecCode(), getProductNo(), getSystemId());
			this.ProductOfferInfo.add(productOfferInfo);
		}
	}
	
	private void parseProductOfferInfo(String inXML) throws Exception {
		//如果是退订所有不需要解析该节点
		if(OrderConstant.orderTypeOfAll.equals(getActionType())){
			return;
		}
		String tagName = "ProductOfferInfo";
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(tagName).append(">([\\s\\S]*?)</").append(tagName).append(">");
		Pattern pattern = Pattern.compile(bf.toString());
		Matcher matcher = pattern.matcher(inXML);
		this.ProductOfferInfo = new ArrayList();
		while(matcher.find()){
			String result = matcher.group(1);
			ProductOfferInfo productOfferInfo = new ProductOfferInfo(result, getActionType(), getProdSpecCode(), getProductNo(), getSystemId());
			this.ProductOfferInfo.add(productOfferInfo);
		}
	}
	/**
	 * 订购增值产品信息
	 * @return
	 */
	public String orderProductstoXml(){
		StringBuffer bf = new StringBuffer("");
		bf.append("<SubscribeToVSOPReq>")
				.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
				.append("<TimeStamp>").append(getTimeStamp()).append("</TimeStamp>")
				.append("<OrderId>").append(getOrderId()).append("</OrderId>")
				.append("<SystemId>").append(getSystemId()).append("</SystemId>")
				.append("<ActionType>").append(getActionType()).append("</ActionType>")
				.append("<ProdSpecCode>").append(getProdSpecCode()).append("</ProdSpecCode>")
				.append("<ProdInstId>").append(getProdInstId()).append("</ProdInstId>")
				.append("<ProductNo>").append(getProductNo()).append("</ProductNo>");
				List ProductOfferInfos = getProductOfferInfo();
				if(ProductOfferInfos != null && ProductOfferInfos.size() > 0){
					for (Iterator iterator = ProductOfferInfos.iterator(); iterator
							.hasNext();) {
						ProductOfferInfo	 productOfferInfo = (ProductOfferInfo) iterator.next();
						bf.append(productOfferInfo.orderProdOfferToXml());
					}
				}
		bf.append("</SubscribeToVSOPReq>");
		return bf.toString();
	}
	/**
	 * 订购增值产品信息
	 * @return
	 * @throws DocumentException 
	 */
	public Map getOrderProductResult(String respXml) throws Exception{
		Map map = new HashMap();
		Document doc = XMLUtils.parse(respXml);
		Element root = doc.getRootElement();
		//Element root = resp.element();
		map.put("ResultCode", StringUtil.getInstance().getTagValue("ResultCode", respXml));
		map.put("ResultDesc", StringUtil.getInstance().getTagValue("ResultDesc", respXml));

		return map;
	}
	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getOldProductNo() {
		return OldProductNo;
	}

	public void setOldProductNo(String oldProductNo) {
		OldProductNo = oldProductNo;
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

	public List getProductOfferInfo() {
		return ProductOfferInfo;
	}

	public void setProductOfferInfo(List productOfferInfo) {
		ProductOfferInfo = productOfferInfo;
	}

	public boolean isNeedSendConfirm() {
		return needSendConfirm;
	}

	public void setNeedSendConfirm(boolean needSendConfirm) {
		this.needSendConfirm = needSendConfirm;
	}
	

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public void setProdInstId(String prodInstId) {
		this.prodInstId = prodInstId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getActionType() {
		return ActionType;
	}

	public void setActionType(String actionType) {
		ActionType = actionType;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getSystemId() {
		return SystemId;
	}

	public void setSystemId(String systemId) {
		SystemId = systemId;
	}

	public String getProductNo() {
		return ProductNo;
	}

	public void setProductNo(String productNo) {
		ProductNo = productNo;
	}

	public String getProdSpecCode() {
		return ProdSpecCode;
	}

	public void setProdSpecCode(String prodSpecCode) {
		ProdSpecCode = prodSpecCode;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getProductOfferId() {
		return ProductOfferId;
	}

	public void setProductOfferId(String productOfferId) {
		ProductOfferId = productOfferId;
	}

	public List getPprodOfferList() {
		return pprodOfferList;
	}

	public void setPprodOfferList(List pprodOfferList) {
		this.pprodOfferList = pprodOfferList;
	}

	public List getVproductList() {
		return vproductList;
	}

	public void setVproductList(List vproductList) {
		this.vproductList = vproductList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SubscribeToVSOPRequest.logger = logger;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

}
