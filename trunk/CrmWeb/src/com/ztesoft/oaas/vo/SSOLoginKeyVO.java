/**
 * 
 */
package com.ztesoft.oaas.vo;

import java.io.Serializable;
/**
 * @author Administrator
 *
 */
public class SSOLoginKeyVO implements Serializable{
	  private String loginKey ;
	  private String staffCode ;
	  private String loginDate ;
	/**
	 * @return Returns the loginDate.
	 */
	public String getLoginDate() {
		return loginDate;
	}
	/**
	 * @param loginDate The loginDate to set.
	 */
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	/**
	 * @return Returns the loginKey.
	 */
	public String getLoginKey() {
		return loginKey;
	}
	/**
	 * @param loginKey The loginKey to set.
	 */
	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}
	/**
	 * @return Returns the staffCode.
	 */
	public String getStaffCode() {
		return staffCode;
	}
	/**
	 * @param staffCode The staffCode to set.
	 */
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
}
