package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;

public interface  MpOperatorLanDAO extends DAO{


	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	public void setSQLFlag(String flag);
}
