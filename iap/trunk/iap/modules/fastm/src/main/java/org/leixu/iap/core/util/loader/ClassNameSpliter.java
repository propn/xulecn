package org.leixu.iap.core.util.loader;

public class ClassNameSpliter implements ObjectLoader{
	public static final ClassNameSpliter instance = new ClassNameSpliter();

	public Object load(Object path) {
		if(path == null) return null;
		String className = null;

		Class pathClass = path.getClass();
		if(pathClass == String.class){
			className = (String)path;
		}else if(path == Class.class){
			className = ((Class)path).getName();
		}else{
			className = path.getClass().getName();
		}

		return splitClassName(className);
	}

	public String[] splitClassName(String fullName){
		String packagePart = null;
		String namePart = fullName;
		int dot = fullName.lastIndexOf('.');
		if(dot > 0){
			packagePart = fullName.substring(0, dot);
			namePart = fullName.substring(dot + 1);
		}

		return new String[]{packagePart, namePart};
	}
}
