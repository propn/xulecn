package org.leixu.iap.core.util.loader;


public class PipeLoader extends CompositeBase implements ObjectLoader{
	
	public PipeLoader(){}
	public PipeLoader(ObjectLoader[] loaders){
		setLoaders(loaders);
	}

	public Object load(Object path) {
		if(loaders == null) 
			return path;

		Object ret = path;
		for(int i = 0; i < loaders.length; i++){
			ret = loaders[i].load(ret);
			if(ret == null) 
				break;
		}
		return ret;
	}
}
