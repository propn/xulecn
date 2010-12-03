package com.ztesoft.vsop.order.vo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.dom4j.Element;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.order.SystemCode;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * ��ֵ��Ʒ�����������ӿڵ���
 * @author yi.chengfeng Apr 14, 2010 2:47:04 PM
 *
 */
public class VSubProdInfo {
	private long sequence;
	private String VProductID;
	private String EffDate = "";
	private String ExpDate = "";
	private String actionType = "";
	
	private String packageId = "";
	private String productOfferId = "";
	private String pprodOfferId = "";
	private String productOfferType = "";
	private String vproductInstId = "";
	private String vproductNbr = "";  //��ֵ��Ʒ�ⲿϵͳ���룬�ӿ�Э�鶨���VProductID��Ȼ���ø���Ϣ��ѯproduct.product_id
	private String productOfferNbr = "";
	private String systemId = "";
	
	public VSubProdInfo(Element element, String effDate2, String expDate2,
			String productOfferType2, String productOfferId, String actionType, String productOfferNbr, String systemId) throws SQLException {
		String vproductNbr = XMLUtils.getElementStringValue(element, "VProductNbr");
		if("".equals(vproductNbr)){  //��û��VProductNbr�ڵ���ȡVProductID��Ȼ��ת��
			setVproductNbr(XMLUtils.getElementStringValue(element, "VProductID"));
			replaceVproductId();
		}else{  //�����Ƕ���ȷ�ϵ�ʱ����������õ�
			setVproductNbr(vproductNbr);
			setVProductID(XMLUtils.getElementStringValue(element, "VProductID"));
		}
		
		setEffDate(effDate2); //Ŀǰ������Ʒ��ʱ��Ϊ׼ 
		setExpDate(expDate2);
		setActionType(actionType);
		setProductOfferNbr(productOfferNbr);
		setVproductInstId(XMLUtils.getElementStringValue(element, "VProductInstID"));
/*		if(getActionType().equals(OrderConstant.orderTypeOfAdd) && "".equals(getVproductInstId())){
			String rId = getOrderRelationId();
			setVproductInstId(rId);
		}*/
		setProductOfferType(productOfferType2);
		setSystemId(systemId);
		//��ֵ����Ʒ���͸�ֵ
		if(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID.equals(productOfferType2)){
			setProductOfferId(productOfferId);
		}else if(OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID.equals(productOfferType2)){
			setPackageId(productOfferId);
		}else if(OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID.equals(productOfferType2)){
			setPprodOfferId(productOfferId);
		}
		//�������ػ��޸�:������ֵ��Ʒ�����ѯ����Ʒ���� start yi.chengfeng 2010-07-14
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			queryAndSetProductOfferIdByVProductId(getVProductID());
		}
		//�������ػ��޸�:������ֵ��Ʒ�����ѯ����Ʒ���� end
		validate();
	}
	
	private void queryAndSetProductOfferIdByVProductId(String vProductID) throws SQLException {
		//it��ļ���ƽ̨���⴦��
		if(SystemCode.CT10000.equals(getSystemId()) || SystemCode.SEAT_10000.equals(getSystemId()) ||
				SystemCode.SMS.equals(getSystemId()) || SystemCode.VOICE_10000.equals(getSystemId())){
			//����ֵ����Ʒ����û�д�����Ʒ�����������ֵ��Ʒ�����ѯ����Ʒ����
			if(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID.equals(getProductOfferType()) && (null == getProductOfferId() || "".equals(getProductOfferId())) ){
				Map offerIdAndOfferNbrMap = new OrderDao().queryAndSetProductOfferIdByVProductId(vProductID);
				setProductOfferId((String) offerIdAndOfferNbrMap.get("prod_offer_id"));
				setProductOfferNbr((String) offerIdAndOfferNbrMap.get("offer_nbr"));
			}
		}
	}

	public VSubProdInfo() {
	}

	public VSubProdInfo(String inXML, String effDate2, String expDate2,
			String productOfferType2, String productOfferID2,
			String actionType, String offNbr, String systemId) throws SQLException {
		String vproductNbr = XMLUtils.getSingleTagValue(inXML, "VProductNbr");
		if("".equals(vproductNbr)){  //��û��VProductNbr�ڵ���ȡVProductID��Ȼ��ת��
			setVproductNbr(XMLUtils.getSingleTagValue(inXML, "VProductID"));
			replaceVproductId();
		}else{  //�����Ƕ���ȷ�ϵ�ʱ����������õ�
			setVproductNbr(vproductNbr);
			setVProductID(XMLUtils.getSingleTagValue(inXML, "VProductID"));
		}
		
		setEffDate(effDate2); //Ŀǰ������Ʒ��ʱ��Ϊ׼ 
		setExpDate(expDate2);
		setActionType(actionType);
		setProductOfferNbr(offNbr);
		setVproductInstId(XMLUtils.getSingleTagValue(inXML, "VProductInstID"));
		setProductOfferType(productOfferType2);
		setSystemId(systemId);
		//��ֵ����Ʒ���͸�ֵ
		if(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID.equals(productOfferType2)){
			setProductOfferId(productOfferID2);
		}else if(OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID.equals(productOfferType2)){
			setPackageId(productOfferID2);
		}else if(OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID.equals(productOfferType2)){
			setPprodOfferId(productOfferID2);
		}
		//�������ػ��޸�:������ֵ��Ʒ�����ѯ����Ʒ���� start yi.chengfeng 2010-07-14
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			queryAndSetProductOfferIdByVProductId(getVProductID());
		}
		//�������ػ��޸�:������ֵ��Ʒ�����ѯ����Ʒ���� end
		validate();
	}

	private void validate() {
		
	}

	public String toXmlForSpi() {
		StringBuffer bf = new StringBuffer();
		bf 
		.append("<sub_product>")
			.append("<sub_product_code>").append(getVproductNbr()).append("</sub_product_code>")
			.append("<oldsub_product_code>").append("").append("</oldsub_product_code>")
			.append("<act_type>").append(getActType()).append("</act_type>")
			.append("<idtype>").append(getProductOfferType()).append("</idtype>")
			.append("<id>").append(getProductOfferNbr()).append("</id>")
			.append("<prodCharacters>").append("").append("</prodCharacters>")
		.append("</sub_product>");
		return bf.toString();
	}
	
	private String getActType() {
		String actType = getActionType();
		if(OrderConstant.orderTypeOfDel.equals(getActionType())) actType = "3";
		if(OrderConstant.orderTypeOfAll.equals(getActionType())) actType = "3";
		return actType;
	}

	public String orderProductToXml(){
		StringBuffer bf = new StringBuffer();
		bf.append("<VSubProdInfo>")
		.append("<VProductID>").append(getVProductID()).append("</VProductID>")
		.append("<VProductNbr>").append(getVproductNbr()).append("</VProductNbr>")
		.append("<EffDate>").append(getEffDate()).append("</EffDate>")
		.append("<ExpDate>").append(getExpDate()).append("</ExpDate>")
		.append("<VProductInstID>").append(getVproductInstId()).append("</VProductInstID>")
		.append("</VSubProdInfo>");
		return bf.toString();
	}
	
	public void replaceVproductId() throws SQLException {
		String productId = null;
		//productId = new OrderDao().getProductId(getVproductNbr());
		productId=DcSystemParamManager.getInstance().getProductIdByNbr(getVproductNbr());
//		try {
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection();
//		}
		setVProductID(productId);
	}
	/**
	 * ��ȡ����
	 * @return
	 * @throws SQLException 
	 */
	private String getOrderRelationId() throws SQLException {
		Connection conn = LegacyDAOUtil.getConnection();
		long seq = new OrderDao().getSequence("SEQ_ORDER_RELATION_ID", conn);
//		try {
//			return String.valueOf(seq);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			LegacyDAOUtil.releaseConnection(conn);
//		}
		return String.valueOf(seq);
	}
	public String getActionType() {
		return actionType;
	}
	
	public void setActionType(String actionType) {
		this.actionType = actionType;
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
	public long getSequence() {
		return sequence;
	}
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProductOfferId() {
		return productOfferId;
	}

	public void setProductOfferId(String productOfferId) {
		this.productOfferId = productOfferId;
	}

	public String getPprodOfferId() {
		return pprodOfferId;
	}

	public void setPprodOfferId(String pprodOfferId) {
		this.pprodOfferId = pprodOfferId;
	}

	public String getProductOfferType() {
		return productOfferType;
	}

	public void setProductOfferType(String productOfferType) {
		this.productOfferType = productOfferType;
	}
	public String getVproductInstId() {
		return vproductInstId;
	}
	public void setVproductInstId(String vproductinstId) {
		this.vproductInstId = vproductinstId;
	}
	public String getVproductNbr() {
		return vproductNbr;
	}
	public void setVproductNbr(String vproductNbr) {
		this.vproductNbr = vproductNbr;
	}

	public String getProductOfferNbr() {
		return productOfferNbr;
	}

	public void setProductOfferNbr(String productOfferNbr) {
		this.productOfferNbr = productOfferNbr;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	
}
