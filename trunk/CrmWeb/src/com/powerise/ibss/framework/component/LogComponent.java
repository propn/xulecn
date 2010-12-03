package com.powerise.ibss.framework.component;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.component.LogComponent.SingletonHolder;

public class LogComponent implements IComponet {
//	µ¥ÀýÄ£Ê½
	private LogComponent(){
		
	}
	
	public static IComponet getInstance(){
		return SingletonHolder.instance ;
	}
	
	static class SingletonHolder{
		static IComponet instance = new LogComponent() ;
	}
	
	public DynamicDict execute(IComponet component, DynamicDict dto)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
