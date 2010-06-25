package org.leixu.iap.core.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ContainedMap implements Map{
	protected Map rootMap;
	protected Object innerKey;

	public ContainedMap(Map rootCache, Object innerKey){
		this.innerKey = innerKey;
		this.rootMap = rootCache;
	}

	public Object get(Object key) {
		if(rootMap == null || innerKey == null) 
			return null;
		Map targetCache = (Map)rootMap.get(innerKey);
		if(targetCache == null) 
			return null;
		return targetCache.get(key);
	}

	protected Map getInnerCache(){
		if(rootMap == null || innerKey == null) return null;
		return (Map)rootMap.get(innerKey);
	}

	protected Map makeSureInnerCache(){
		if(rootMap == null || innerKey == null) return null;

		Map targetCache = getInnerCache();
		if(targetCache == null) {
			try{
				targetCache = (Map)rootMap.getClass().newInstance();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			rootMap.put(innerKey, targetCache);
		}
		
		return targetCache;
	}
	
	public Object put(Object key, Object value) {
		if(rootMap == null || innerKey == null) 
			return null;
		Map targetCache = makeSureInnerCache();
		return targetCache.put(key, value);
	}
	
	public Object remove(Object key) {
		if(rootMap == null || innerKey == null)
			return null;

		Map targetCache = getInnerCache();
		if(targetCache == null) 
			return null;
		return targetCache.remove(key);
	}

	public void clear() {
		if(rootMap == null || innerKey == null) return;
		rootMap.remove(innerKey);
	}
	
	public void putAll(Map map){
		if(rootMap == null || innerKey == null) return;
		Map targetCache = makeSureInnerCache();
		
		targetCache.putAll(map);
	}

	public int size() {
		Map targetCache = getInnerCache();
		if(targetCache == null) return 0;
		return targetCache.size();
	}

	public boolean isEmpty() {
		Map targetCache = getInnerCache();
		if(targetCache == null) return true;

		return targetCache.isEmpty();
	}

	public boolean containsKey(Object key) {
		Map targetCache = getInnerCache();
		if(targetCache == null) return false;
		return targetCache.containsKey(key);
	}

	public boolean containsValue(Object value) {
		Map targetCache = getInnerCache();
		if(targetCache == null) return false;
		return targetCache.containsKey(value);
	}

	public Collection values() {
		Map targetCache = getInnerCache();
		if(targetCache == null) return null;
		return targetCache.values();
	}

	public Set entrySet() {
		Map targetCache = getInnerCache();
		if(targetCache == null) return null;
		return targetCache.entrySet();
	}

	public Set keySet() {
		Map targetCache = getInnerCache();
		if(targetCache == null) return null;
		return targetCache.keySet();
	}
}
