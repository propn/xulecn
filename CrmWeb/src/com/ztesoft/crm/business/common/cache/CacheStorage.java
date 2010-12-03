/**
 * 
 */
package com.ztesoft.crm.business.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * �򵥵Ķ��󻺴�
 */
public class CacheStorage {
    //�㷨
    private static Map cache=new HashMap();
    //��󻺴���
    private int cacheMaxSize=1000;
    
    
    public CacheStorage(){
    	
    }
    
    public CacheStorage(int cacheMaxSize){
        this.cacheMaxSize=cacheMaxSize;
    }
    private class CacheEntry{
        
    	CacheEntry(Object value){
            _value=value;
            _lastTime=System.currentTimeMillis();
        }
        private long _lastTime=0;
        private Object _value;
        
    }
    /**
     * ��ȡ����
     * @param key
     * @return
     */
    public void putCache(String key,Object obj){
        if(cacheMaxSize>cache.size()){
            cache.put(key,new CacheEntry(obj));
        }else{
        	
        	CacheEntry cacheEntry=new CacheEntry(obj);
        	cache.put(key, cacheEntry);  
        	
        	//ɾ������ʱ�䣬������ʹ�õ�
        	String removeKey=key;
        	long lastTime=cacheEntry._lastTime;
            Iterator entrys=cache.entrySet().iterator();
            while(entrys.hasNext()){
            	Entry entry=(Entry)entrys.next();
            	String entryKey=(String) entry.getKey();
            	CacheEntry entryValue=(CacheEntry) entry.getValue();
            	if(entryValue._lastTime<lastTime){
            		lastTime=entryValue._lastTime;
            		removeKey=entryKey;
            	}
            }
            cache.remove(removeKey);  
        }  
    }
    
    
    
    
    /**
     * ��ȡ����
     * @param key
     * @return
     */
    public Object getFromCache(String key){
        
    	CacheEntry compEntry=(CacheEntry)cache.get(key);
        
    	if(compEntry!=null){
    	compEntry._lastTime=System.currentTimeMillis();
    	
    	 	return (Object)compEntry._value;
    	}else{
    		return null;
    	}
        
    }
}
