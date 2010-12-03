/**
 * 
 */
package com.ztesoft.oaas.dao.regionorgrelat;

/**
 * @author Administrator
 *
 */
public class RegionOrgRelatDAOFactory {

	public static RegionOrgRelaDAO createRegionOrgRelaDAO(){
		return new RegionOrgRelaDAOImpl();
	}
}
