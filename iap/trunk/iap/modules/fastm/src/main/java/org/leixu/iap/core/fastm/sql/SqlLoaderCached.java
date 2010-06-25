package org.leixu.iap.core.fastm.sql;

import java.util.Map;

import org.leixu.iap.core.util.map.ReadWriteMap;

public class SqlLoaderCached implements SqlLoader {
	private Map cache = new ReadWriteMap();
	private SqlLoader inner = new SqlLoaderImpl();

	public void setCache(Map cache) {
		this.cache = cache;
	}
	public void setInner(SqlLoader loader){
		inner = loader;
	}

	public SqlHolder load(Class clazz, String resourceName) {
		SqlHolder sqlHolder = (SqlHolder)cache.get(resourceName); 
		if(sqlHolder == null){
			sqlHolder = inner.load(clazz, resourceName);
			cache.put(resourceName, sqlHolder);
		}
		return sqlHolder;
	}
}
