package org.leixu.iap.core.util.loader;

public class ForEachLoader implements ObjectLoader{
	private ObjectLoader inner;
	
	public ForEachLoader(ObjectLoader inner) {
		this.inner = inner;
	}

	public Object load(Object path) {
		Object[] array = (Object[])path;
		return loadForEach(array);
	}

	public Object[] loadForEach(Object[] array){
		int n = array.length;
		Object[] results = new Object[n];
		for(int i = 0; i < n; i++){
			results[i] = inner.load(array[i]);
		}
		return results;
	}
}
