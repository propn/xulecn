package org.leixu.iap.core.fastm.sql;

public class SqlParamImpl implements SqlParam{
	private Object sql;
	private Object param;
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}
	public Object getSql() {
		return sql;
	}
	public void setSql(Object sql) {
		this.sql = sql;
	}
}

