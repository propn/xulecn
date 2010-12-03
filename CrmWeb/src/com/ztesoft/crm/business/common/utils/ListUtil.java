package com.ztesoft.crm.business.common.utils;

import java.util.List;

public final class ListUtil {

	
	public static boolean isEmptiy(List list){
		
		
		if(list==null)
			return true;
		
		
		return list.size()==0;
		
	}
	
}
