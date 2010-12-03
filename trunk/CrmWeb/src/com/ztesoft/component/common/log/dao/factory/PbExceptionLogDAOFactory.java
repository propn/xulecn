package com.ztesoft.component.common.log.dao.factory;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.component.common.log.dao.PbExceptionLogDAO;
import com.ztesoft.component.common.log.dao.impl.PbExceptionLogDAOImpl;
import com.ztesoft.component.common.log.dao.impl.PbExceptionLogDAOOracleImpl;

public class PbExceptionLogDAOFactory {

	public static PbExceptionLogDAO getPbExceptionLogDAO(){
		PbExceptionLogDAO dao = null ;
		String databaseType = DAOUtils.getDatabaseType();
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			dao = new PbExceptionLogDAOImpl();
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			dao = new PbExceptionLogDAOOracleImpl();
		}
		return dao ;
	}
}
