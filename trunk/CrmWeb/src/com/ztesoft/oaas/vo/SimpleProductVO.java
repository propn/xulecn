/**
 * 
 */
package com.ztesoft.oaas.vo;

import java.io.Serializable;
/**
 * @author Administrator
 *
 */
public class SimpleProductVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productId = "";
	private String productPrividerId = "";
	private String productName = "";
	private String productCode = "" ;
	private String productComments = "";
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductComments() {
		return productComments;
	}
	public void setProductComments(String productComments) {
		this.productComments = productComments;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrividerId() {
		return productPrividerId;
	}
	public void setProductPrividerId(String productPrividerId) {
		this.productPrividerId = productPrividerId;
	}
}
