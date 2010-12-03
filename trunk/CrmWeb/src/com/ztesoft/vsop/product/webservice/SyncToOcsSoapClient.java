package com.ztesoft.vsop.product.webservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.product.vo.OCSRespVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * @desc
 * @author qin.guoquan
 * @date Oct 13, 2010 11:09:29 AM
 */
public class SyncToOcsSoapClient {

	//private static Logger log = Logger.getLogger(SyncToOcsSoapClient.class);
	
	//OCS����˵�ַ
	private static String PROD_SYNC_TO_OCS_URL = DcSystemParamManager.getParameter(VsopConstants.PROD_SYNC_TO_OCS_URL);
	private static String PRODOFFER_SYNC_TO_OCS_URL = DcSystemParamManager.getParameter(VsopConstants.PRODOFFER_SYNC_TO_OCS_URL);
	
	private static SyncToOcsSoapClient instance = new SyncToOcsSoapClient();
	
	private SyncToOcsSoapClient() {
		
	}
	
	public static SyncToOcsSoapClient getInst() {
		return instance;
	}
	
	/**
	 * VSOP��OCSͬ����ֵ��Ʒ
	 * @param ismpReqXml ISMPͬ����ֵ��Ʒ��VSOP�ı���
	 * @return
	 * @throws Exception
	 */
	public OCSRespVO syncProductToOCS(String ismpReqXml) throws Exception {
		
		ProductSyncFromVSOPStub stub = new ProductSyncFromVSOPStub(PROD_SYNC_TO_OCS_URL);
		
		ProductSyncFromVSOPStub.VsopServiceRequest serviceReq = new ProductSyncFromVSOPStub.VsopServiceRequest();
		serviceReq.setRequest(getSyncProductReqXml(ismpReqXml));
		
		ProductSyncFromVSOPStub.ProductSyncFromVSOPReq req = new ProductSyncFromVSOPStub.ProductSyncFromVSOPReq();
		req.setProductSyncFromVSOPReq(serviceReq);
		
		String resXml = stub.ProductSyncFromVSOP(req).getProductSyncFromVSOPResp().getResponse();
		
		return getOCSResp(resXml);
		
	}
	
	/**
	 * VSOP��OCSͬ������Ʒ
	 * @param ismpReqXml ISMPͬ������Ʒ��VSOP�ı���
	 * @return
	 * @throws Exception
	 */
	public OCSRespVO syncProdOfferToOCS(String ismpReqXml) throws Exception {
		
		ProdOfferSyncFromVSOPSVStub stub = new ProdOfferSyncFromVSOPSVStub(PRODOFFER_SYNC_TO_OCS_URL);
		
		ProdOfferSyncFromVSOPSVStub.VsopServiceRequest serviceReq = new ProdOfferSyncFromVSOPSVStub.VsopServiceRequest();
		serviceReq.setRequest(getSyncProdOfferReqXml(ismpReqXml));
		
		ProdOfferSyncFromVSOPSVStub.ProdOfferSyncFromVSOPReq req = new ProdOfferSyncFromVSOPSVStub.ProdOfferSyncFromVSOPReq();
		req.setProdOfferSyncFromVSOPReq(serviceReq);
		
		String resXml = stub.ProdOfferSyncFromVSOPSV(req).getProdOfferSyncFromVSOPResp().getResponse();
		
		return getOCSResp(resXml);
		
	}
	
	/**
	 * ��ȡOCS����Ӧ��Ϣ
	 * @param resXml
	 * @return
	 * @throws Exception
	 */
	public OCSRespVO getOCSResp(String resXml) throws Exception {
		
		OCSRespVO vo = new OCSRespVO();
		vo.setStreamingNo(getTagValue(resXml, "StreamingNo"));
		vo.setResultCode(getTagValue(resXml, "ResultCode"));
		vo.setResultDesc(getTagValue(resXml, "ResultDesc"));
		
		return vo;
	}
	
	/**
	 * ��ȡVSOP��OCSͬ����ֵ��Ʒ������xml
	 * @param ismpReqXml
	 * @return
	 * @throws Exception
	 */
	public String getSyncProductReqXml(String ismpReqXml) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<ProductSyncFromVSOPReq>");
			sb.append("<StreamingNo>").append(getTagValue(ismpReqXml, "StreamingNo")).append("</StreamingNo>");
			sb.append("<TimeStamp>").append(getTagValue(ismpReqXml, "TimeStamp")).append("</TimeStamp>");
			sb.append("<SystemId>").append(getTagValue(ismpReqXml, "SystemId")).append("</SystemId>");
			sb.append("<SPID>").append(getTagValue(ismpReqXml, "SPID")).append("</SPID>");
			String opFlag = getTagValue(ismpReqXml, "OPFlag");
			sb.append("<OPFlag>").append(opFlag).append("</OPFlag>");
			sb.append("<ProductType>").append("0").append("</ProductType>");//��Ʒ���� ˵����0�������Ʒ 1����ֵ������Ʒ 2������������Ʒ(��ȷ��,ISMP�������޴��ֽ�)
			sb.append("<ProductID>").append(getTagValue(ismpReqXml, "ProductID")).append("</ProductID>");
			sb.append("<PnameCN>").append(getTagValue(ismpReqXml, "PnameCN")).append("</PnameCN>");
			sb.append("<PnameEN>").append(getTagValue(ismpReqXml, "PnameEN")).append("</PnameEN>");
			sb.append("<PdescriptionCN>").append(getTagValue(ismpReqXml, "PdescriptionCN")).append("</PdescriptionCN>");
			sb.append("<PdescriptionEn>").append(getTagValue(ismpReqXml, "PdescriptionEn")).append("</PdescriptionEn>");
			
			handleRepeatTag(sb, ismpReqXml, "ServiceCapabilityID", "ServiceCapabilityID");//ҵ������ID�б�
				
			sb.append("<Status>").append(getTagValue(ismpReqXml, "Status")).append("</Status>");
			sb.append("<Scope>").append(getTagValue(ismpReqXml, "Scope")).append("</Scope>");
			sb.append("<ProductHost>").append(getTagValue(ismpReqXml, "SystemId")).append("</ProductHost>");//��ֵ��Ʒ����(��ȷ��,ISMP����ı����޴��ֽ�)
			
			sb.append("<PlatForm>").append("").append("</PlatForm>");//��ֵ��Ʒ��������ƽ̨���б�,ISMP�����޴˱���,��ȷ��
			
			//������Ϊ��3��ɾ������ʱ�򣬲���Ҫ��д���ֶ�
			if (null != opFlag && !"3".equals(opFlag.trim())) handleProdRelation(sb, ismpReqXml);//��������ֵ��Ʒ�б� 
			
		sb.append("</ProductSyncFromVSOPReq>");
		
		return sb.toString();
	}
	
	/**
	 * ��ȡVSOP��OCSͬ������Ʒ������xml
	 * @param ismpReqXml
	 * @return
	 * @throws Exception
	 */
	public String getSyncProdOfferReqXml(String ismpReqXml) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<ProdOfferSyncFromVSOPReq>");
			sb.append("<StreamingNo>").append(getTagValue(ismpReqXml, "StreamingNo")).append("</StreamingNo>");
			sb.append("<TimeStamp>").append(getTagValue(ismpReqXml, "TimeStamp")).append("</TimeStamp>");
			sb.append("<SystemId>").append(getTagValue(ismpReqXml, "SystemId")).append("</SystemId>");
			String opFlag = getTagValue(ismpReqXml, "OPFlag");
			sb.append("<OPFlag>").append(opFlag).append("</OPFlag>");
			sb.append("<ProductOfferType>").append("0").append("</ProductOfferType>");//��ֵ����Ʒ���ͣ�0������ֵ����Ʒ1����ֵ�����ײ�2������ͳ+��ֵ�������ײ�(��ȷ��,ISMP�������޴��ֽ�)
			sb.append("<ProductOfferID>").append(getTagValue(ismpReqXml, "PackageID")).append("</ProductOfferID>");
			sb.append("<PNameCN>").append(getTagValue(ismpReqXml, "PNameCN")).append("</PNameCN>");
			sb.append("<PNameEN>").append(getTagValue(ismpReqXml, "PNameEN")).append("</PNameEN>");
			sb.append("<PDesCN>").append(getTagValue(ismpReqXml, "PDesCN")).append("</PDesCN>");
			sb.append("<PDesEn>").append(getTagValue(ismpReqXml, "PDesEn")).append("</PDesEn>");
			
			handleRepeatTag(sb, ismpReqXml, "ProductID", "ProductID");//��������ֵ��Ʒ�б�
				
			sb.append("<Status>").append(getTagValue(ismpReqXml, "Status")).append("</Status>");
			sb.append("<ChargingPolicyCN>").append(getTagValue(ismpReqXml, "ChargingPolicyCN")).append("</ChargingPolicyCN>");
			sb.append("<Scope>").append(getTagValue(ismpReqXml, "Scope")).append("</Scope>");
			
			//������Ϊ��3��ɾ������ʱ�򣬲���Ҫ��д���ֶ�
			if (null != opFlag && !"3".equals(opFlag.trim())) handleProdOfferRelation(sb, ismpReqXml);//��������ֵ��Ʒ�б� 
		sb.append("</ProdOfferSyncFromVSOPReq>");
		
		return sb.toString();
	}
	
	/**
	 * ���������ж���ظ��ڵ�����
	 * @param sb
	 * @param reqXml
	 * @param srcTagName
	 * @param desTagName
	 * @throws Exception
	 */
	private void handleRepeatTag(StringBuffer sb, String reqXml, String srcTagName, String desTagName) throws Exception {
		
		StringBuffer srcTag = new StringBuffer("<").append(srcTagName).append(">");
		if (null != reqXml && !"".equals(reqXml.trim()) && -1 != reqXml.indexOf(srcTag.toString())) {
			StringBuffer jj = new StringBuffer();
			jj.append("<").append(srcTagName).append(">([\\s\\S]*?)</").append(srcTagName).append(">");
			Pattern pattern = Pattern.compile(jj.toString());
			Matcher matcher = pattern.matcher(reqXml);
			while (matcher.find()) {
				String value = matcher.group(1);
				System.out.println("value=>" + value);
				System.out.println(matcher.group());
				sb.append("<").append(desTagName).append(">").append(matcher.group(1)).append("</").append(desTagName).append(">");
			}
			//jj = null;//��ȫ������������
		}
		//srcTag = null;//��ȫ������������
	}
	
	/**
	 * ����VSOP��OCSͬ����ֵ��Ʒʱ�������й�������ֵ��Ʒ�б�
	 * @param sb
	 * @param reqXml
	 * @throws Exception
	 */
	private void handleProdRelation(StringBuffer sb, String reqXml) throws Exception {
		
		if (null != reqXml && !"".equals(reqXml.trim()) && -1 != reqXml.indexOf("<ProdRelation>")) {
			String prodRelation = "<ProdRelation>([\\s\\S]*?)</ProdRelation>";
			Pattern pattern = Pattern.compile(prodRelation);
			Matcher matcher = pattern.matcher(reqXml);
			while (matcher.find()) {
				String prodRelationValue = matcher.group(1);
				sb.append("<ProdRelation>");
					sb.append("<OPType>").append(getTagValue(prodRelationValue, "OPType")).append("</OPType>");
					sb.append("<RelationType>").append(getTagValue(prodRelationValue, "RelationType")).append("</RelationType>");
					sb.append("<RproductID>").append(getTagValue(prodRelationValue, "RProductID")).append("</RproductID>");
				sb.append("</ProdRelation>");
			}
		}
	}
	
	/**
	 * ����VSOP��OCSͬ������Ʒʱ�������й���������Ʒ�б�
	 * @param sb
	 * @param reqXml
	 * @throws Exception
	 */
	private void handleProdOfferRelation(StringBuffer sb, String reqXml) throws Exception {
		
		if (null != reqXml && !"".equals(reqXml.trim()) && -1 != reqXml.indexOf("<ProdOfferRelation>")) {
			String prodRelation = "<ProdOfferRelation>([\\s\\S]*?)</ProdOfferRelation>";
			Pattern pattern = Pattern.compile(prodRelation);
			Matcher matcher = pattern.matcher(reqXml);
			while (matcher.find()) {
				String prodRelationValue = matcher.group(1);
				sb.append("<ProdOfferRelation>");
					sb.append("<OPType>").append(getTagValue(prodRelationValue, "OPType")).append("</OPType>");
					sb.append("<RelationType>").append(getTagValue(prodRelationValue, "RelationType")).append("</RelationType>");
					sb.append("<RProdOfferID>").append(getTagValue(prodRelationValue, "RProdOfferID")).append("</RProdOfferID>");
				sb.append("</ProdOfferRelation>");
			}
		}
	}
	
	/**
	 * ��ȡ�ڵ�ֵ
	 * @param tagName
	 * @param xml
	 * @return
	 */
	public String getTagValue(String xml, String tagName) {
		String tagValue = "";
		String tag = "<" + tagName + ">";
		if (xml.indexOf(tag) == -1 && xml.indexOf("<" + tagName + "/>") != -1
				|| xml.indexOf(tag) == -1)
			return "";
		else if (xml.indexOf(tag) != -1) {
			int star = xml.indexOf(tag);
			int end = xml.indexOf("</" + tagName + ">");
			tagValue = xml.substring(star + tag.length(), end).trim();
		}
		return tagValue;
	}
	
	public static void main(String[] args) throws Exception {
		String reqXml = "<PdescriptionEn>7</PdescriptionEn><ServiceCapabilityID>1</ServiceCapabilityID><ServiceCapabilityID>2</ServiceCapabilityID><ServiceCapabilityID>3</ServiceCapabilityID><ServiceCapabilityID>4</ServiceCapabilityID><Status>6</Status>";
		StringBuffer sb = new StringBuffer();
		SyncToOcsSoapClient.getInst().handleRepeatTag(sb, reqXml, "ServiceCapabilityID", "jj");
	}
}
