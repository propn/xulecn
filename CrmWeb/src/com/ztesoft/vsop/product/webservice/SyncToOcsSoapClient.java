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
	
	//OCS服务端地址
	private static String PROD_SYNC_TO_OCS_URL = DcSystemParamManager.getParameter(VsopConstants.PROD_SYNC_TO_OCS_URL);
	private static String PRODOFFER_SYNC_TO_OCS_URL = DcSystemParamManager.getParameter(VsopConstants.PRODOFFER_SYNC_TO_OCS_URL);
	
	private static SyncToOcsSoapClient instance = new SyncToOcsSoapClient();
	
	private SyncToOcsSoapClient() {
		
	}
	
	public static SyncToOcsSoapClient getInst() {
		return instance;
	}
	
	/**
	 * VSOP向OCS同步增值产品
	 * @param ismpReqXml ISMP同步增值产品给VSOP的报文
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
	 * VSOP向OCS同步销售品
	 * @param ismpReqXml ISMP同步销售品给VSOP的报文
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
	 * 获取OCS的响应信息
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
	 * 获取VSOP向OCS同步增值产品的请求xml
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
			sb.append("<ProductType>").append("0").append("</ProductType>");//产品类型 说明：0：接入产品 1：增值附属产品 2：能力附属产品(待确定,ISMP请求报文无此字节)
			sb.append("<ProductID>").append(getTagValue(ismpReqXml, "ProductID")).append("</ProductID>");
			sb.append("<PnameCN>").append(getTagValue(ismpReqXml, "PnameCN")).append("</PnameCN>");
			sb.append("<PnameEN>").append(getTagValue(ismpReqXml, "PnameEN")).append("</PnameEN>");
			sb.append("<PdescriptionCN>").append(getTagValue(ismpReqXml, "PdescriptionCN")).append("</PdescriptionCN>");
			sb.append("<PdescriptionEn>").append(getTagValue(ismpReqXml, "PdescriptionEn")).append("</PdescriptionEn>");
			
			handleRepeatTag(sb, ismpReqXml, "ServiceCapabilityID", "ServiceCapabilityID");//业务能力ID列表
				
			sb.append("<Status>").append(getTagValue(ismpReqXml, "Status")).append("</Status>");
			sb.append("<Scope>").append(getTagValue(ismpReqXml, "Scope")).append("</Scope>");
			sb.append("<ProductHost>").append(getTagValue(ismpReqXml, "SystemId")).append("</ProductHost>");//增值产品属主(待确定,ISMP请求的报文无此字节)
			
			sb.append("<PlatForm>").append("").append("</PlatForm>");//增值产品所属管理平台的列表,ISMP请求无此报文,待确定
			
			//当动作为（3：删除）的时候，不需要填写该字段
			if (null != opFlag && !"3".equals(opFlag.trim())) handleProdRelation(sb, ismpReqXml);//关联的增值产品列表 
			
		sb.append("</ProductSyncFromVSOPReq>");
		
		return sb.toString();
	}
	
	/**
	 * 获取VSOP向OCS同步销售品的请求xml
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
			sb.append("<ProductOfferType>").append("0").append("</ProductOfferType>");//增值销售品类型：0：单增值销售品1：增值捆绑套餐2：（传统+增值）捆绑套餐(待确定,ISMP请求报文无此字节)
			sb.append("<ProductOfferID>").append(getTagValue(ismpReqXml, "PackageID")).append("</ProductOfferID>");
			sb.append("<PNameCN>").append(getTagValue(ismpReqXml, "PNameCN")).append("</PNameCN>");
			sb.append("<PNameEN>").append(getTagValue(ismpReqXml, "PNameEN")).append("</PNameEN>");
			sb.append("<PDesCN>").append(getTagValue(ismpReqXml, "PDesCN")).append("</PDesCN>");
			sb.append("<PDesEn>").append(getTagValue(ismpReqXml, "PDesEn")).append("</PDesEn>");
			
			handleRepeatTag(sb, ismpReqXml, "ProductID", "ProductID");//包含的增值产品列表
				
			sb.append("<Status>").append(getTagValue(ismpReqXml, "Status")).append("</Status>");
			sb.append("<ChargingPolicyCN>").append(getTagValue(ismpReqXml, "ChargingPolicyCN")).append("</ChargingPolicyCN>");
			sb.append("<Scope>").append(getTagValue(ismpReqXml, "Scope")).append("</Scope>");
			
			//当动作为（3：删除）的时候，不需要填写该字段
			if (null != opFlag && !"3".equals(opFlag.trim())) handleProdOfferRelation(sb, ismpReqXml);//关联的增值产品列表 
		sb.append("</ProdOfferSyncFromVSOPReq>");
		
		return sb.toString();
	}
	
	/**
	 * 处理请求串中多个重复节点的情况
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
			//jj = null;//完全是消除对象用
		}
		//srcTag = null;//完全是消除对象用
	}
	
	/**
	 * 处理VSOP向OCS同步增值产品时请求报文中关联的增值产品列表
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
	 * 处理VSOP向OCS同步销售品时请求报文中关联的销售品列表
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
	 * 获取节点值
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
