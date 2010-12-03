package com.ztesoft.vsop.ismp;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.ztesoft.vsop.order.XMLUtils;

/**
 * ismpµÄservice
 * @author yi.chengfeng
 *
 */
public class IsmpServiceVo {
	
	
	public IsmpServiceVo() {
	}
	public IsmpServiceVo(Element root) {
		setStreamingNo(XMLUtils.getElementStringValue(root, "streamingNo"));
		setsPID(XMLUtils.getElementStringValue(root, "SPID"));
		setoPFlag(XMLUtils.getElementStringValue(root, "OPFlag"));
		setProductID(XMLUtils.getElementStringValue(root, "productID"));
		setNameCn(XMLUtils.getElementStringValue(root, "nameCN"));
		setNameEn(XMLUtils.getElementStringValue(root, "nameEN"));
		setDescriptionCN(XMLUtils.getElementStringValue(root, "descriptionCN"));
		setDescriptionEN(XMLUtils.getElementStringValue(root, "descriptionEn"));
		List ServiceCapabilityID = root.elements("serviceCapabilityID");
		if (ServiceCapabilityID != null) {
			for (int i = 0; i < ServiceCapabilityID.size(); i++) {
				Element subElement = (Element) ServiceCapabilityID.get(i);
				String service_code = subElement.getStringValue();
				getServiceCapabilityID().add(service_code);
			}
		}
		setAccess_no(XMLUtils.getElementStringValue(root, "accessNo"));
		setStatus(XMLUtils.getElementStringValue(root, "status"));
	}
	
	/*
	 * fileds
	 */
	private String id = "";
	private String streamingNo = "";
	private String sPID = "";
	private String oPFlag = "";
	private String productID = "";
	private String nameCn = "";
	private String nameEn = "";
	private String descriptionCN = "";
	private String descriptionEN = "";
	private List serviceCapabilityID = new ArrayList();
	private String access_no = "";
	private String status = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStreamingNo() {
		return streamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	public String getsPID() {
		return sPID;
	}
	public void setsPID(String sPID) {
		this.sPID = sPID;
	}
	public String getoPFlag() {
		return oPFlag;
	}
	public void setoPFlag(String oPFlag) {
		this.oPFlag = oPFlag;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getNameCn() {
		return nameCn;
	}
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getDescriptionCN() {
		return descriptionCN;
	}
	public void setDescriptionCN(String descriptionCN) {
		this.descriptionCN = descriptionCN;
	}
	public String getDescriptionEN() {
		return descriptionEN;
	}
	public void setDescriptionEN(String descriptionEN) {
		this.descriptionEN = descriptionEN;
	}

	public List getServiceCapabilityID() {
		return serviceCapabilityID;
	}
	public void setServiceCapabilityID(List serviceCapabilityID) {
		this.serviceCapabilityID = serviceCapabilityID;
	}
	public String getAccess_no() {
		return access_no;
	}
	public void setAccess_no(String accessNo) {
		access_no = accessNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
