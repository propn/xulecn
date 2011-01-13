package com.ztesoft.common.dao;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.PageModel;

public class QueryFilterFactory {

	public static QueryFilter getPageQueryFilter(PageModel pageModel) {

		if ("informix".equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE)) {
			return new PageFilterJdbcImpl(pageModel);
		} else if ("oracle".equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE)) {
			return new PageFilterOracleImpl(pageModel);
		}
		
		//default use JdbcImpl
		return new PageFilterJdbcImpl(pageModel);
	}
}
