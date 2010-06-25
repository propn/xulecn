package org.leixu.iap.core.util.loader;


public class NameComposer extends CompositeBase implements ObjectLoader{
	public NameComposer(){}
	public NameComposer(ObjectLoader[] loaders){
		setLoaders(loaders);
	}

	public Object load(Object path) {
		Object[] array = (Object[])path;
		return null;
	}

	public String compose(Object[] array){
		int nItems = array.length;
		int nLoaders = loaders.length;
		int min = Math.min(nItems, nLoaders);

		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < min; i++){
			ObjectLoader loader = loaders[i];
			Object item = array[i];
			
			Object result = null;
			if(loader == null){
				result = item;
			}else{
				result = loader.load(item);
			}

			if(result != null)
				buf.append(result);
		}

		return buf.toString();
	}
}
