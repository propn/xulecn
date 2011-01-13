/**
 * 
 */
package com.ztesoft.component.common.signon.simulate;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class DataVO implements Serializable{
	private String privId = "";
	private String privCode = "";
	private String privType = "";
	private String regionId = "";
	private String regionType = "";
	/**
	 * @return Returns the privCode.
	 */
	public String getPrivCode() {
		return privCode;
	}
	/**
	 * @param privCode The privCode to set.
	 */
	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}
	/**
	 * @return Returns the privId.
	 */
	public String getPrivId() {
		return privId;
	}
	/**
	 * @param privId The privId to set.
	 */
	public void setPrivId(String privId) {
		this.privId = privId;
	}
	/**
	 * @return Returns the privType.
	 */
	public String getPrivType() {
		return privType;
	}
	/**
	 * @param privType The privType to set.
	 */
	public void setPrivType(String privType) {
		this.privType = privType;
	}
	/**
	 * @return Returns the regionId.
	 */
	public String getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId The regionId to set.
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return Returns the regionType.
	 */
	public String getRegionType() {
		return regionType;
	}
	/**
	 * @param regionType The regionType to set.
	 */
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
}
