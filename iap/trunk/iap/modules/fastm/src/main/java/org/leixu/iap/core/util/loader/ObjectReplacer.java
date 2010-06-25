package org.leixu.iap.core.util.loader;

public class ObjectReplacer implements ObjectLoader{
	private Object newObject;

	public ObjectReplacer(Object newObject) {
		this.newObject = newObject;
	}

	public Object load(Object path) {
		return newObject;
	}
}
