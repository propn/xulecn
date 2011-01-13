package com.powerise.ibss.util;

import java.util.HashMap;
import java.util.Map;

import com.powerise.ibss.framework.IAction;

/**
 * 
 * ����IActionʵ���࣬����ÿ�η������
 * @author easonwu 2010-01-15
 *
 */
public class IActionCache {
	
	private static Map cache = new HashMap() ;
	
	//����ģʽ
	static class SingletonHolder{
		static IActionCache instance = new IActionCache() ;
	}
	
	private IActionCache(){
		
	}
	
	public static IActionCache getInstance(){
		return SingletonHolder.instance ;
	}
	
	public void put(String name , IAction action ){
		cache.put(name, action) ;
	}
	
	public IAction get(String name ){
		return cache.get(name) != null ? (IAction)cache.get(name) : null;
	}
	
	public void clearCache(){
		cache.clear() ;
	}
	
	public void remove(String name ){
		cache.remove(name);
	}
	
	public boolean contains(String name){
		return cache.containsKey(name) ;
	}

}
