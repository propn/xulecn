/**
 * 
 */
package com.ztesoft.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * �򵥵Ķ��󻺴�
 * @author leadyu
 * @company www.ztesoft.com
 * @date 2008-12-31
 */
public class SimpleCacheStore {

    //����FIFO�㷨
    private static Map cache=new HashMap();
    //��󻺴�ʱ�䣬��λ���֣�
    private int cacheMaxTime=-1;
    //��󻺴���
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
     * ��ȡ����
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
     * ��ȡ����
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
