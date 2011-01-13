/**
 * 
 */
package com.ztesoft.oaas.dao.ssologinkey;

/**
 * @author Administrator
 *
 */
public class SSOLoginKeyDAOFactory {

	public static SSOLoginKeyDAO getSSOLoginKeyDAO(){
		return new SSOLoginKeyDAOImpl();
	}
}
