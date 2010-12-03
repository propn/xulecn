package com.powerise.ibss.framework;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Const {
	public static final String ACTION_METHOD = "execMethod" ;
	
	public static final String ACTION_RESULT = "RESULT" ;

	public static final String ACTION_PARAMETER = "PARAMETER" ;
	
	public static final String ACTION_ERROR = "ERROR" ;
	
	
	public static final String PAGE_PAGESIZE = "pageSize" ;
	public static final String PAGE_PAGEINDEX = "pageIndex" ;
	public static final int UN_JUDGE_ERROR = -999999999 ;  //�����쳣������������Ҫ����
	
	public static Map getParam(DynamicDict dto){
		try {
			return (Map)dto.getValueByName(ACTION_PARAMETER)  ;
		} catch (FrameException e) {
			return getEmptyMap() ;
//			e.printStackTrace();
		}
	}
	
	private static Map getEmptyMap(){
		return new HashMap() ;
	}
	
	public static int getPageSize(Map m ){
		return ((Integer)m.get("pageSize")).intValue() ;
	}
	
	public static int getPageIndex(Map m ){
		return ((Integer)m.get("pageIndex")).intValue() ;
	}
	
	/**
	 * ����tStr�ֶ� ������map
	 * @param sm  ԭmap
	 * @param tStr ��ԭmap������tStr�ֶ���Ϊkey��������Ҫ��map
	 * @return
	 */
	public static Map getMapForTargetStr(Map sm , String tStr){
		if(sm == null || sm.isEmpty() ||
				tStr == null || "".equals(tStr.trim()))
			return getEmptyMap() ; 
		Map tm = new HashMap() ;
		Iterator it = sm.keySet().iterator() ;
		String[] tStrArray = tStr.split(",") ;
		for(int i = 0 , j= tStrArray.length ; i< j ; i++){
			String n = tStrArray[i] ;
			if(sm.get(n) != null ){
				tm.put(n, (String)sm.get(n));
			}
		}
		return tm ;
	}
	
	public static boolean containValue(Map m , String name ){
		return m.get(name) != null ;
	}
	
	public static  boolean containStrValue(Map m , String name) {
		if( !containValue( m , name ) )
			return false ;
		String t = (String)m.get(name) ;
		return t != null && !"".equals(t.trim()) ;
	}
	
	public static  String getStrValue(Map m , String name) {
		Object t = m.get(name) ;
		if(t == null )
			return "" ;
		return ((String)m.get(name)).trim() ;
	}
}
