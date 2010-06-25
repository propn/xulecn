package org.leixu.iap.core.util.loader;

import java.util.logging.Logger;

public class ObjectLoaderByClass implements ObjectLoader{
	public static ObjectLoaderByClass instance = new ObjectLoaderByClass();

	protected Logger log = Logger.getLogger(this.getClass().getName());

	public Object load(Object path) {
		Class clazz = (Class)path;
		return loadObjectByClass(clazz);
	}

	private Object loadObjectByClass(Class clazz) {
		Object obj = null;
		try{
			obj = clazz.newInstance();
		}catch(Exception e){
			log.severe(e.getMessage());
		}
		return obj;
	}
}
