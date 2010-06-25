package org.leixu.iap.core.fastm.interceptors;

import java.text.DateFormat;
import java.util.Date;

public class DateInterceptor extends DelegatedInterceptor{
	private DateFormat dateFormat;

	public DateInterceptor(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	protected Object getValue(Object bean, String propertyName, Object value) {
		if(value instanceof Date){
			value = dateFormat.format((Date)value);
		}
		return value;
	}
}
