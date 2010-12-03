package com.ztesoft.crm.business.common.utils;



public final class Strings {

	
	public static boolean isEmptiy(String str){
		
		
		if(str==null||"".equals(str))
			return true;
		
		
		return false;
		
	}
	
	public static boolean big(String str1,String str2){
		
		if(isEmptiy(str1)||isEmptiy(str2))
			return false;
		
		if(Integer.parseInt(str1)>Integer.parseInt(str2)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean little(String str1,String str2){
		
		if(isEmptiy(str1)||isEmptiy(str2))
			return false;
		
		if(Integer.parseInt(str1)<Integer.parseInt(str2)){
			return true;
		}else{
			return false;
		}
	}
	
	
}
