package com.ztesoft.vsop.webservice.vo;

import com.ztesoft.vsop.order.XMLUtils;

public class OfferDetailInfo {
	
	private String offerDetailId = "";
	private String instanceRelationId = "";
	private String productId = "";
	private String prodType = "";
	private String accNbr = "";
	private String lanId = "";
	private String regionCode = "";//区号
	private String productNbr = "";//增值产品外码
	public OfferDetailInfo(){}
	public OfferDetailInfo(String xml){
		this.setInstanceRelationId(XMLUtils.getSingleTagValue(xml,"InstanceRelationId"));
		this.setOfferDetailId(XMLUtils.getSingleTagValue(xml,"OfferDetailId"));
		this.setProductId(XMLUtils.getSingleTagValue(xml,"ProductId"));
	}
	public String getOfferDetailId() {
		return offerDetailId;
	}
	public void setOfferDetailId(String offerDetailId) {
		this.offerDetailId = offerDetailId;
	}
	public String getInstanceRelationId() {
		return instanceRelationId;
	}
	public void setInstanceRelationId(String instanceRelationId) {
		this.instanceRelationId = instanceRelationId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getAccNbr() {
		return accNbr;
	}
	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}
	public String getLanId() {
		return lanId;
	}
	public void setLanId(String lanId) {
		this.lanId = lanId;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getProductNbr() {
		return productNbr;
	}
	public void setProductNbr(String productNbr) {
		this.productNbr = productNbr;
	}
}
