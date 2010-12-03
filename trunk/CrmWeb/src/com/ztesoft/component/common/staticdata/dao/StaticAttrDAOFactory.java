package com.ztesoft.component.common.staticdata.dao;

import com.ztesoft.component.common.staticdata.dao.impl.AcctItemGrpDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.BankBranchDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.BankDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.BillFormatDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.BillRequementDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.BillingCycleDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.BillingCycleTypeDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.StaticAttrDAOImpl;
import com.ztesoft.component.common.staticdata.dao.impl.StaticDataDAOImpl;

public class StaticAttrDAOFactory {

	protected StaticAttrDAOFactory() {
	}

	private static StaticAttrDAOFactory instance = new StaticAttrDAOFactory();

	/**
	 * 获取工厂实例
	 * 
	 * @return
	 */
	public static StaticAttrDAOFactory getInstance() {
		return instance;
	}

	public StaticAttrDAO getStaticAttrDAO() {
		return new StaticAttrDAOImpl();
	}

	public StaticDataDAO getStaticDataDAO() {
		return new StaticDataDAOImpl();
	}

	public AcctItemGrpDAO getAcctItemGrpDAO() {
		return new AcctItemGrpDAOImpl();
	}

	public BankBranchDAO getBankBranchDAO() {
		return new BankBranchDAOImpl();
	}

	public BankDAO getBankDAO() {
		return new BankDAOImpl();
	}

	public BillingCycleDAO getBillingCycleDAO() {
		return new BillingCycleDAOImpl();
	}

	public BillingCycleTypeDAO getBillingCycleTypeDAO() {
		return new BillingCycleTypeDAOImpl();
	}

	public BillRequementDAO getBillRequementDAO() {
		return new BillRequementDAOImpl();
	}
	
	public BillFormatDAO getBillFormatDAO() {
		return new BillFormatDAOImpl();
	}

}
