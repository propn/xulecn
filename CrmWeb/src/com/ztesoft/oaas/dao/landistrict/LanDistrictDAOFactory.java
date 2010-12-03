package com.ztesoft.oaas.dao.landistrict;

public class LanDistrictDAOFactory {

	public static LanDistrictDAO  getLanDistrictDAO(){
		return new LanDistrictDAOImpl();
	}
}
