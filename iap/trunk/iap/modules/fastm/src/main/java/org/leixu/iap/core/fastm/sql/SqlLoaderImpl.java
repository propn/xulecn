package org.leixu.iap.core.fastm.sql;

import java.io.InputStream;

import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.HelperParser;

public class SqlLoaderImpl implements SqlLoader{
	/**
	 * 
	 */
	public SqlHolder load(Class clazz, String resourceName) {
		InputStream inputStream = clazz.getClassLoader().getResourceAsStream(resourceName);
		if(inputStream == null) throw new RuntimeException("resource " + resourceName + " not exists in the class path");

		DynamicPart template = HelperParser.parse(inputStream);
		SqlHolderImpl sqlGeneratorImpl = new SqlHolderImpl(template);
		return sqlGeneratorImpl;
	}
}
