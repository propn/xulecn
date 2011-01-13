/**
 * 
 */
package com.ztesoft.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * 简单的对象缓存
 * @author leadyu
 * @company www.ztesoft.com
 * @date 2008-12-31
 */
public class SimpleCacheStore {

    //采用FIFO算法
    private static Map cache=new HashMap();
    //最大缓存时间，单位（分）
    private int cacheMaxTime=-1;
    //最大缓存数
    private int cacheMaxSize=500;
    
    public SimpleCacheStore(int cacheMaxTime,int cacheMaxSize){
        this.cacheMaxSize=cacheMaxSize;
        this.cacheMaxTime=cacheMaxTime;
    }
    
    private class CacheEntry{
        CacheEntry(Object value){
            _value=value;
            _cacheTime=System.currentTimeMillis();
        }
        
        private Object _value;
        
        private long _cacheTime=0;
    }
    /**
     * 获取缓存
     * @param key
     * @return
     */
    public void putCache(String key,Object obj){
        if(cacheMaxSize>cache.size()){
            cache.put(key,new CacheEntry(obj));
        }else{
            Iterator cahceKeys=cache.keySet().iterator();
            while(cahceKeys.hasNext() && cache.size()<cacheMaxSize){
                String cahceKey=(String)cahceKeys.next();
                cache.remove(cahceKey);
                
            }
            cache.put(key, new CacheEntry(obj));    
            
        }
        
    }
    
    /**
     * 获取缓存
     * @param key
     * @return
     */
    public Object getFromCache(String key){
        CacheEntry compEntry=(CacheEntry)cache.get(key);
        
        if(compEntry!=null && (System.currentTimeMillis()-compEntry._cacheTime)/60000<cacheMaxTime){
            return (Object)compEntry._value;
        }else{
            return null;
        }
        
    }
}
