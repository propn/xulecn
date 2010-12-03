package com.ztesoft.component.common.log.dao.factory;

import com.ztesoft.component.common.log.dao.PbLogTypeDAO;
import com.ztesoft.component.common.log.dao.impl.PbLogTypeDAOImpl;

public class PbLogTypeDAOFactory {
	public static PbLogTypeDAO getPbLogTypeDAO() {
		return new PbLogTypeDAOImpl();
	}
}
