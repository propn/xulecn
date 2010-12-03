package com.ztesoft.common.dao;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.PageModel;

public class QueryFilterFactory {

	public static QueryFilter getPageQueryFilter(PageModel pageModel) {

		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			return new PageFilterInformixImpl(pageModel);
		} else if (CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			return new PageFilterOracleImpl(pageModel);
		}
		
		//default use JdbcImpl
		return new PageFilterJdbcImpl(pageModel);
	}
}
