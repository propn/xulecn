package org.leixu.iap.core.fastm.interceptors;

import org.leixu.iap.core.fastm.DefaultInterceptor;
import org.leixu.iap.core.fastm.IValueInterceptor;
import org.leixu.iap.core.fastm.StaticPart;

public abstract class DelegatedInterceptor 
		implements IValueInterceptor{

	protected static final StaticPart emptyStatic = new StaticPart("");

	protected IValueInterceptor outer = this;
	protected void setOuter(IValueInterceptor outer) {
		this.outer = outer;
	}
	protected IValueInterceptor getOuter() {
		return outer;
	}
	
	protected DelegatedInterceptor getOuttest(){
		if(outer == null || outer == this)
			return (DelegatedInterceptor)outer;

		DelegatedInterceptor outtest = null;
		IValueInterceptor current = outer;
		while(current instanceof DelegatedInterceptor){
			outtest = (DelegatedInterceptor)current;
			current = outtest.getOuter();

			if(outtest == current || current == null)
				break;
		}

		return outtest;
	}

	protected IValueInterceptor inner = DefaultInterceptor.instance;
	public void setInner(IValueInterceptor inner) {
		this.inner = inner;
		
		if(inner instanceof DelegatedInterceptor){
			DelegatedInterceptor innerDelegator = (DelegatedInterceptor)inner;
			innerDelegator.setOuter(inner);
		}
	}

	public Object getProperty(Object bean, String propertyName) {
		if(inner == null) 
			return null;
		Object value = inner.getProperty(bean, propertyName);
		return getValue(bean, propertyName, value);
	}

	protected abstract Object getValue(Object bean, String propertyName, Object value);
}
