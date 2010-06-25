package org.leixu.iap.core.util.loader;

import java.util.Map;

import org.leixu.iap.core.util.Nulls;

public class CachedLoader implements ObjectLoader{
	protected Map cache;
	protected ObjectLoader inner;

	protected boolean rememberNull = true;

	public boolean isRememberNull() {
		return rememberNull;
	}

	public void setRememberNull(boolean rememberNull) {
		this.rememberNull = rememberNull;
	}

	public CachedLoader(ObjectLoader inner, Map cache) {
		this.inner = inner;
		this.cache = cache;
	}

	public Object load(Object path) {
		Object obj = cache.get(path);
		if(obj == null)
			obj = inner.load(path);
		
		if(obj != null){
			if(rememberNull){
				if(Nulls.NULL_OBJECT == obj) return null;
			}

			cache.put(path, obj);
		}else if(rememberNull){
			cache.put(path, Nulls.NULL_OBJECT);
		}

		return obj;
	}
}
