package org.leixu.iap.core.util.loader;

import java.util.logging.Logger;

public class ClassLoaderByName implements ObjectLoader{
	public static ClassLoaderByName instance = new ClassLoaderByName();

	private static Logger log = Logger.getLogger(ClassLoaderByName.class.getName());

	public Object load(Object path) {
		String className = (String)path;
		return loadClassByName(className);
	}

	public static Class loadClassByName(String className){
		Class clazz = null;
		try{
			clazz = Class.forName(className);
		}catch(Exception e){
			log.severe(e.getMessage());
		}
		return clazz;
	}

}
