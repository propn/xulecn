package org.leixu.iap.core.util.loader;

import java.util.Map;


public class MapAsLoader implements ObjectLoader{
	private Map map;
	
	public MapAsLoader(){}
	public MapAsLoader(Map map){
		setMap(map);
	}
	public void setMap(Map map) {
		this.map = map;
	}

	public Object load(Object path) {
		if(map == null) return null;
		return map.get(path);
	}
}
