package org.leixu.iap.core.util.loader;

import java.util.Map;

import org.leixu.iap.core.util.map.ReadWriteMap;

public class LoaderFactory {
	public static final ObjectLoader objectCreater = 
		new PipeLoader(new ObjectLoader[]{
			ClassLoaderByName.instance, 
			ObjectLoaderByClass.instance});

	public static final ObjectLoader instanceCreater = instanceCreater();
	public static final ObjectLoader instanceCache = instanceCache();

	public static ObjectLoader instanceCreater(){
		Map cache = new ReadWriteMap();
		return new PipeLoader(new ObjectLoader[]{
			new CachedLoader(ClassLoaderByName.instance, cache), 
			ObjectLoaderByClass.instance});
	}

	public static ObjectLoader instanceCache(){
		Map cache = new ReadWriteMap();
		return new CachedLoader(objectCreater, cache);
	}
}
