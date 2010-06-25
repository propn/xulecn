package org.leixu.iap.core.fastm.interceptors;

import java.text.DateFormat;
import java.util.Date;

public class DateFormatInterceptor 
		extends DelegatedInterceptor {

	private DateFormat formatter;

	protected Object getValue(Object bean, String propertyName, Object value){
		if(formatter != null && value instanceof Date) {
			Date date = (Date)value;
			return formatter.format(date);
		}
		return value;
	}

	public void setFormatter(DateFormat formatter) {
		this.formatter = formatter;
	}
}
