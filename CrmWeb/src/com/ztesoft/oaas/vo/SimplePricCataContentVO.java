package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class SimplePricCataContentVO implements Serializable {
	private String offerName = "" ;//offer_name
	private String pricingPlanName = "";//pricing_plan_name
	private String catalogType = "";//CATALOG_TYPE
	private String pricingParamCatalogName = "";//pricing_param_catalog_name
	private String pricingParamCatalogId = "" ;//pricing_param_catalog_id,和mm_data_privilege的data_pkey1字段关联
	private String productName = "";
	public String getCatalogType() {
		return catalogType;
	}
	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getPricingParamCatalogId() {
		return pricingParamCatalogId;
	}
	public void setPricingParamCatalogId(String pricingParamCatalogId) {
		this.pricingParamCatalogId = pricingParamCatalogId;
	}
	public String getPricingParamCatalogName() {
		return pricingParamCatalogName;
	}
	public void setPricingParamCatalogName(String pricingParamCatalogName) {
		this.pricingParamCatalogName = pricingParamCatalogName;
	}
	public String getPricingPlanName() {
		return pricingPlanName;
	}
	public void setPricingPlanName(String pricingPlanName) {
		this.pricingPlanName = pricingPlanName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
