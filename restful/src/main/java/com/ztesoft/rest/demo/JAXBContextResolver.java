package com.ztesoft.rest.demo;

import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
public class JAXBContextResolver {
	private JAXBContext context;

	private Class[] types = { Student.class, Student.class };

	public JAXBContextResolver() throws Exception {

		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(),types);
	}

	public JAXBContext getContext(Class<?> objectType) {
		return (types[0].equals(objectType)) ? context : null;
	}
}
