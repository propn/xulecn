package org.leixu.iap.core.fastm.interceptors;

import java.io.IOException;

import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.ITemplate;
import org.leixu.iap.core.fastm.StaticPart;
import org.leixu.iap.core.fastm.TemplateLocatoer;
import org.leixu.iap.core.util.bean.BeanUtils;

public class IncludeInterceptor extends DelegatedInterceptor{
	private TemplateLocatoer templateFinder;
	public IncludeInterceptor(TemplateLocatoer templateFinder) {
		this.templateFinder = templateFinder;
	}

	protected Object getValue(Object bean, String propertyName, Object value) {
		if( !(value == null || value == emptyStatic) 
			|| propertyName == null || !propertyName.startsWith("/"))
			return value;

		ITemplate template = null;

		try {
			template = templateFinder.findTemplate(propertyName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(template == null)
			return value;
		
		DelegatedInterceptor outtest = getOuttest();
		
		if(template instanceof DynamicPart){
			DynamicPart dyn = (DynamicPart)template;
			String name = dyn.getName();

			if(BeanUtils.containsProperty(bean, name)){
				bean = BeanUtils.getProperty(bean, name);
			}
		}

		String result = template.toString(bean, outtest);
		return new StaticPart(result);
	}
}
