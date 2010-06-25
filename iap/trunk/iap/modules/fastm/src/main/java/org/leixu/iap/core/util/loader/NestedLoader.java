package org.leixu.iap.core.util.loader;

import java.util.Map;

public class NestedLoader implements ObjectLoader{
	protected Object rootLoaderObject;

	public NestedLoader(Object rootLoaderObject) {
		this.rootLoaderObject = rootLoaderObject;
	}

	public Object load(Object path) {
		Object[] array = (Object[])path;
		return nestedLoad(array);
	}

	public Object nestedLoad(Object[] array){
		Object result = null;
		Object loaderObject = rootLoaderObject;
		for(int i = 0; i < array.length; i++){
			Object param = array[i];
			
			if(loaderObject instanceof Map){
				Map map = (Map)loaderObject;
				result = map.get(param);
			}else if(loaderObject instanceof ObjectLoader){
				ObjectLoader loader = (ObjectLoader)loaderObject;
				result = loader.load(param);
			}else{
				return result;
			}

			loaderObject = result;
		}
		
		return result;
	}
}
