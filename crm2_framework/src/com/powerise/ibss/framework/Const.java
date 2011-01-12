package com.powerise.ibss.framework;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.ztesoft.common.util.DateFormatUtils;

public class Const {
	public static final String ACTION_METHOD = "execMethod" ;
	
	public static final String ACTION_RESULT = "RESULT" ;

	public static final String ACTION_PARAMETER = "PARAMETER" ;
	
	public static final String ACTION_ERROR = "ERROR" ;
	
	
	public static final String PAGE_PAGESIZE = "pageSize" ;
	public static final String PAGE_PAGEINDEX = "pageIndex" ;
	public static final int UN_JUDGE_ERROR = -999999999 ;  //这种异常就跳过，不需要处理。
	public static final int NODATA_NOERROR = 0 ;  //返回空数据，也不报错。

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
	 * 根据tStr字段 构建新map
	 * @param sm  原map
	 * @param tStr 从原map，根据tStr字段作为key，构建需要的map
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
	/**
	 * 获取客户端传来的字符型日期参数，返回java.sql.Date对象
	 * @param m
	 * @param name
	 * @return java.sql.Date
	 * @throws ParseException 
	 */
	public static java.sql.Date getDateValue(Map m,String name) throws ParseException{
		Object obj = m.get(name);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date date = null;
		if(obj!=null&&!"".equals(obj)){
			String strDate = (String)obj;
			date = new java.sql.Date(formatter.parse(strDate).getTime());
		}
		return date;
	}
	/**
	 * 获取客户端传来的字符型日期参数，返回java.sql.Timestamp对象
	 * @param m
	 * @param name
	 * @return java.sql.Timestamp
	 * @throws ParseException 
	 */
	public static java.sql.Timestamp getDateTimeValue(Map m,String name) throws ParseException{
		Object obj = m.get(name);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.sql.Timestamp dateTime = null;
		if(obj!=null&&!"".equals(obj)){
			String strDate = (String)obj;
			dateTime = new java.sql.Timestamp(formatter.parse(strDate).getTime());
		}
		return dateTime;
	}
	public static String getSystime() {
		return DateFormatUtils.getFormatedDateTime();
		/*String ret = "";
		try {
			ret= Base.query_string(JNDINames.PPM_DATASOURCE, "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') curentDate from dual", -210020502);
		} catch (FrameException e) {
			e.printStackTrace();
		}
		if(ret==null)ret = "";
		return ret;*/
	}

}
