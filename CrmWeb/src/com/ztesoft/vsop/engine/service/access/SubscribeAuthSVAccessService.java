package com.ztesoft.vsop.engine.service.access;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.OrderEngine;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.AproductInfo;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdOffVO;

/**
 * ������Ȩ
 * 
 * @author panshaohua
 * 
 */
public class SubscribeAuthSVAccessService extends CommonAccessService implements
		IAccess {

	public Map concreteInOpertion(Map in) throws Exception {
		String requestXML = (String) in.get("accessInObject");
		List orderList = process(requestXML);
		in.put("busiObject", orderList);
		in.put("serviceCode", String.valueOf(OrderEngine.SERVICE_ORDERAUTH31));
		return in;
	}

	public Map concreteOutOpertion(Map in) throws Exception {
		List orderList = (List) in.get("busiObject");
		Map resultInfo = (Map) in.get("retBusiObject");
		 String responseXML = getResult(orderList,resultInfo,in);
		 in.put("accessOutObject", responseXML);
		return in;
	}
	/**
	 * ����CRM��VSOP���𶩹���Ȩ����
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public List process(String xmlStr) throws Exception {
		List orderList = new ArrayList();
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		System.out.println(xmlStr);
		Document doc = saxReader.read(reader);
		Element requestEle = doc.getRootElement();
		Element serssionBodyEle = requestEle.element("SessionBody");
		Element root = serssionBodyEle.element("SubAuthFromCRMReq");
		String streamingNo = "";
		String TimeStamp = "";
		String SystemId = "";
		String productOfferId = "";
		List ProductInfoLst = root.elements("ProductInfo");
		StringBuffer bf = new StringBuffer("");
		ArrayList productList = new ArrayList();// ��ֵ��Ʒ�б�
		ArrayList aProductList = new ArrayList();// ������Ʒ�б�
		ArrayList prodOfferList = new ArrayList();// ����Ʒ�б�
		streamingNo = XMLUtils.getElementStringValue(root, "StreamingNo");
		TimeStamp = XMLUtils.getElementStringValue(root, "TimeStamp");
		SystemId = XMLUtils.getElementStringValue(root, "SystemId");
		productOfferId = XMLUtils.getElementStringValue(root, "ProductOfferId");
		productOfferId = DcSystemParamManager.getInstance()
				.getProdOfferIdByNbr(productOfferId);
		
		//cooltan add
		String offerType="";
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			ProdOffVO prodoffvo=DcSystemParamManager.getInstance().getProdOffVOById(productOfferId);
			if(prodoffvo!=null){
				offerType+=prodoffvo.getProdOffSubType();
			}
		}
		if (ProductInfoLst != null) {
			for (int i = 0; i < ProductInfoLst.size(); i++) {
				CustomerOrder order = new CustomerOrder();
				order.setCustSoNumber(streamingNo);
				order.setOrderSystem(SystemId);

				Element ProductInfoElement = (Element) ProductInfoLst.get(i);
				String ProdSpecCode = XMLUtils.getElementStringValue(
						ProductInfoElement, "ProdSpecCode");
				//�������ػ�����
				//yi.chengfeng 2010-8-9 ����product.product_nbr��product.product_id��һ������Ʒ������Ҫת��  start
				if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
					ProdSpecCode = DcSystemParamManager.getInstance().getProductIdByNbr(ProdSpecCode);
				}
				//yi.chengfeng 2010-8-9 ��Ʒ������Ҫת�� end
				String ProductNo = XMLUtils.getElementStringValue(
						ProductInfoElement, "ProductNo");
				List VProductInfoLst = ProductInfoElement
						.elements("VProductInfo");// ��ֵ��Ʒ
				List AProductInfoLst = ProductInfoElement
						.elements("AProductInfo");// ������Ʒ

				ProductOfferInfo prodOffer = new ProductOfferInfo();
				prodOffer.setOfferId(productOfferId);
				prodOffer.setActioType("2");
				if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
					//*****
					//������������д����Ʒ���� ����ֵ����Ʒ
					prodOffer.setOfferType(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID);
				}
				ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
				ProdInstVO prodInst=null;
				// �������ػ��������00X���û�ʵ��
				if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
					// ��trueʱ��00X״̬�������
					prodInst = prodInstDao.qryProdInstByAccNbrAndProductId(
							ProductNo, ProdSpecCode, true);// ��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
				} else {
					prodInst = prodInstDao.qryProdInstByAccNbrAndProductId(
							ProductNo, ProdSpecCode, false);// ��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
				}
				order.setProdInst(prodInst);
				order.setAccNbr(ProductNo);
				order.setProdId(ProdSpecCode);
				String productInstID = prodInst.getProdInstId();
				order.setProdInstId(productInstID);
				//cooltan add
				if(prodInst!=null){
					order.setLanId(prodInst.getLanId());
				}
				if (VProductInfoLst != null) {
					for (int m = 0; m < VProductInfoLst.size(); m++) {
						VproductInfo product = new VproductInfo();
						Element VProductInfoLstElement = (Element) VProductInfoLst
								.get(m);
						String actionType = XMLUtils.getElementStringValue(
								VProductInfoLstElement, "ActionType");
						String VProductID = XMLUtils.getElementStringValue(
								VProductInfoLstElement, "VProductID");
						String productId=""; 
						//�������ػ�����,��crm�͹�����CRM_CODEת����ISMP��Ӧ�ı���
						if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode) && null!=VProductID
								&& !"".equals(VProductID)){
							String crmIsmpNbr=DcSystemParamManager.getInstance().getCrmCodeIsmpNbrByCode(VProductID);
							productId = DcSystemParamManager.getInstance().getProductIdByNbr(crmIsmpNbr);
							//����crm������Ȩû��������ƷID��vsop�Լ�ά��
							if(prodOffer.getOfferId() == null || "".equals(prodOffer.getOfferId())){
								String offerId = DcSystemParamManager.getInstance().getofferIdByProductId(productId);
								productOfferId = offerId;
								prodOffer.setOfferId(offerId);
							}
						}else{
							productId= DcSystemParamManager.getInstance().getProductIdByNbr(VProductID);
						}
						product.setVProductId(productId);
						product.setVProductInstID(productInstID);
						product.setOfferId(productOfferId);
						product.setActionType(actionType);
						product.setVProductNbr(VProductID);//��ֵ��Ʒ����룬add 201001009 rule12 ʹ�� 
						
						if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
							//*****
							//������������д����Ʒ���� ����ֵ����Ʒ
							product.setOfferType(OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID);
						}
						//cooltan modify
						if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
							if(!"".equals(offerType)&&"2".equals(offerType)){
								product.setOfferType(offerType);
							}
						}
						productList.add(product);
					}
				}

				if (AProductInfoLst != null) {
					for (int m = 0; m < AProductInfoLst.size(); m++) {
						Element AProductInfoLstElement = (Element) AProductInfoLst
								.get(m);
						String ActionType = XMLUtils.getElementStringValue(
								AProductInfoLstElement, "ActionType");
						String AProductID = XMLUtils.getElementStringValue(
								AProductInfoLstElement, "AProductID");
						AproductInfo aproduct = new AproductInfo();
						aproduct.setAProductID(AProductID);
						aproduct.setActionType(ActionType);
						aProductList.add(aproduct);
					}
				}
				prodOffer.setVproductInfoList(productList);
				prodOfferList.add(prodOffer);
				order.setProductOfferInfoList(prodOfferList);
				order.setAproductInfoList(aProductList);
				orderList.add(order);
			}
		}
		return orderList;
	}
	/**
	 * �õ����ظ��ӿڵ�xml
	 * @param orderList
	 * @param resultLst
	 * @return
	 */
	private String getResult(List orderList,Map resultLst,Map in){
		//resultLst key=AccNbr,value=List<Map<ProductOfferID,OPResult,OPDesc,VProductName,VProductID,ProductNo>>
		CustomerOrder orderTemp = (CustomerOrder) orderList.get(0);
		StringBuffer headBf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		headBf.append("<Response>")
		.append("<SubAuthFromCRMResp>")
		.append("<StreamingNo>").append(orderTemp.getCustSoNumber()).append("</StreamingNo>");
		boolean flag = true;
		StringBuffer bf = new StringBuffer();
		StringBuffer bf2=new StringBuffer("");
		String resultDesc="";
		if(orderList!=null){
			for(int m=0;m<orderList.size();m++){
				CustomerOrder order =(CustomerOrder) orderList.get(m);
				String productNo = order.getAccNbr();
				String prodSpecCode = order.getProdId();
				Map oneResultInfo = (Map)resultLst.get(productNo);
				bf.append("<ProductInfo>")
				.append("<ProdSpecCode>").append(prodSpecCode).append("</ProdSpecCode>")
				.append("<ProductNo>").append(productNo).append("</ProductNo>");
				String resultCode  = (String) oneResultInfo.get("resultCode");
				resultDesc=(String) oneResultInfo.get("resultMsg");
				List retLst = (List) oneResultInfo.get("retBusiObject");
				if(!"0".equals(resultCode))
					flag = false;
				if(null == retLst) 
					continue;
				for(int i=0;i<retLst.size();i++){
					HashMap map = (HashMap)retLst.get(i);
					String OPResult = (String)map.get("OPResult");
					String ProductNo = (String)map.get("ProductNo");
					String VProductID = (String)map.get("VProductID");
					String OPDesc = (String)map.get("OPDesc");
					String VProductName = (String)map.get("VProductName");
					bf.append("<VProductInfo>")
					.append("<VProductID>").append(VProductID).append("</VProductID>")
					.append("<VProductName>").append(VProductName).append("</VProductName>")
					.append("<OPResult>").append(OPResult).append("</OPResult>")
					.append("<OPDesc>").append(OPDesc).append("</OPDesc>")
					.append("</VProductInfo>");
					if(!"0".equals(OPResult)){
						bf2.append(OPDesc+"|");
					}
				}
				bf.append("</ProductInfo>");
			}
			bf.append("</SubAuthFromCRMResp>")
			.append("</Response>");
		}
		StringBuffer codeMsg = new StringBuffer();
		String resCode = "0";
		String resDesc = "�ɹ�";
		if(!flag){
			resCode  ="-1";
			if(resultDesc!=null&&!"".equals(resultDesc)){
				resDesc=resultDesc;
			}else{
				resDesc = bf2.toString();
			}
		}
		codeMsg.append("<ResultCode>").append(resCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resDesc).append("</ResultDesc>");
		in.put("resultCode",resCode);
		in.put("resultMsg",resDesc);
		return headBf.toString()+codeMsg.toString() +bf.toString();
	}
}
