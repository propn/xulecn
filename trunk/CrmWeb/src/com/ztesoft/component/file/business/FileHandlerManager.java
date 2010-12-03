package com.ztesoft.component.file.business;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.component.file.tool.Utils;

public class FileHandlerManager {
	
	private FileHandlerManager(){
		
	}
	
	private static Map manager = new HashMap() ;

	static{
//		manager.put("A", new )
	}
	
	public TxtFileHandler getHandler(String handlerName ){
		
		if(Utils.isEmpty(handlerName) )
			return null ;
		
		Object t =  manager.get(handlerName) ;
		if(t == null )
			return null ;
		
		return (TxtFileHandler)t ;
	}
	
}
