package com.ztesoft.component.common.log.dao.factory;

import com.ztesoft.component.common.log.dao.StaffOnlineDAO;
import com.ztesoft.component.common.log.dao.impl.StaffOnlineDAOImpl;

public class StaffOnlineDAOFactory {
	public static StaffOnlineDAO getStaffOnlineDAO() {
		return new StaffOnlineDAOImpl();
	}
}
