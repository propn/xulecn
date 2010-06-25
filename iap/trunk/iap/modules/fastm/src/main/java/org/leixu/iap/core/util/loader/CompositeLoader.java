package org.leixu.iap.core.util.loader;

public class CompositeLoader extends CompositeBase implements ObjectLoader{

	public CompositeLoader(){}
	public CompositeLoader(ObjectLoader[] loaders){
		setLoaders(loaders);
	}

	public Object load(Object path) {
		if(loaders == null) return null;

		Object ret = null;
		for(int i = 0; i < loaders.length; i++){
			ret = loaders[i].load(ret);
			if(ret != null)
				return ret;
		}
		return ret;
	}
}
