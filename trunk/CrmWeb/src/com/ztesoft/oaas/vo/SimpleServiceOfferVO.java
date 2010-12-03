/**
 * 
 */
package com.ztesoft.oaas.vo;

import java.io.Serializable;

/**
 * @author –Ì»Ò∫¿
 *
 */
public class SimpleServiceOfferVO implements Serializable{
	private String serviceOfferId = "";
	private String serviceOfferName = "" ;
	/**
	 * @return Returns the serviceOfferId.
	 */
	public String getServiceOfferId() {
		return serviceOfferId;
	}
	/**
	 * @param serviceOfferId The serviceOfferId to set.
	 */
	public void setServiceOfferId(String serviceOfferId) {
		this.serviceOfferId = serviceOfferId;
	}
	/**
	 * @return Returns the serviceOfferName.
	 */
	public String getServiceOfferName() {
		return serviceOfferName;
	}
	/**
	 * @param serviceOfferName The serviceOfferName to set.
	 */
	public void setServiceOfferName(String serviceOfferName) {
		this.serviceOfferName = serviceOfferName;
	}
}
