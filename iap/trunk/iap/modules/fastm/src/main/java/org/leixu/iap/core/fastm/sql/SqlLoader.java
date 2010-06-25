package org.leixu.iap.core.fastm.sql;

public interface SqlLoader {
	SqlHolder load(Class clazz, String resourceName);
}
