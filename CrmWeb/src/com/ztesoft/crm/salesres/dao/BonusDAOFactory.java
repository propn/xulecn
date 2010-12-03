package com.ztesoft.crm.salesres.dao;

import com.ztesoft.crm.salesres.dao.impl.RcFamilyDAOImpl;



public class BonusDAOFactory {
	private static String DATA_BASE = "informix";

	private static BonusDAOFactory instance = new BonusDAOFactory();

	protected BonusDAOFactory(){}

	public static BonusDAOFactory getInstance() {

		return instance;
	}
	///copy this code to your XXDAOFactory//


	public RcFamilyDAO getRcFamilyDAO(){
		return new RcFamilyDAOImpl();
	}

}

