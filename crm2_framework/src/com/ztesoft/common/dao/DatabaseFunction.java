/**
 * 
 */
package com.ztesoft.common.dao;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;

/**
 * @author Administrator
 *
 */
public class DatabaseFunction {

	private static String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
	/**
	 * ���ص�ǰʱ��
	 * @return
	 */
	public static String current() {
		String returnValue = "current" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "current" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "sysdate";
		}
		
		return returnValue ;
	}
	
	/**
	 * ����to_date(),ֻ�������ڣ�������ʱ��
	 * @return
	 */
	public static String to_date(String value) {
		if( value == null || "".equals(value)){
			return "";
		}
		String returnValue = "" ;
		//value : '2007-01-01 01:01:01'   OR value : '2007-01-01'
		String[] datetime = value.split(" ") ;
		
		if( datetime.length == 1 ){
			if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
				returnValue = "to_date('" + value + "', '%Y-%m-%d')";
			}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
				returnValue = "to_date('" + value + "','yyyy-mm-dd')";
			}
		}else if( datetime.length == 2 ){
			if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
				returnValue = "to_date('" + value + "', '%Y-%m-%d %H:%M:%S')";
			}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
				returnValue = "to_date('" + value + "','yyyy-mm-dd hh24:mi:ss')";
			}			
		}
		
		return returnValue ;
	}
	
	/**
	 * ������Ӧ�·�
	 * @param exp
	 * @return
	 */
	/*public static String month( String exp ) {
		String returnValue = "month(" + exp + ")" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "month(" + exp + ")" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "to_number(to_char(" + exp + ",'MM'))";
		}
		
		return returnValue ;
	}*/
	
	/**
	 * ���ص�ǰʱ��
	 * @return
	 */
	/*public static String time() {
		String returnValue = "time" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "time" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "to_char( sysdate, 'hh24:mi:ss')";
		}
		
		return returnValue ;
	}*/
	
	/**
	 * ���ص�ǰϵͳʱ��
	 * @return
	 */
	/*public static String today(){
		String returnValue = "today" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "today" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "sysdate";
		}
		
		return returnValue ;
	}*/
	
	/**
	 * ����ָ�����������ڼ���0��ʾ������
	 * @param exp
	 * @return
	 */
	/*public static String weekday( String exp ) {
		String returnValue = "weekday(" + exp + ")" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "weekday(" + exp + ")" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "to_number(to_char(" + exp + ",'D') -1 )";
		}
		
		return returnValue ;
	}*/
	
	/**
	 * ����ָ�����ڶ�Ӧ���
	 * @param exp
	 * @return
	 */
	/*public static String year( String exp ) {
		String returnValue = "year(" + exp + ")" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "year(" + exp + ")" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "to_number(to_char(" + exp + ",'YYYY'))";
		}
		
		return returnValue ;
	}*/
	
	/**
	 * ����ָ����ʽ����
	 * @param format
	 * @return
	 */
	/*public static String date( String format ) {
		String returnValue = "date(" + format + ")" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "date(" + format + ")" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "to_date(to_char('" + format + "','mm/dd/yyyy'))";
		}
		
		return returnValue ;
	}*/
	
	/**
	 * ����ָ��������ĳ�µĵڼ���
	 * @param dtimeExp
	 * @return
	 */
	/*public static String day(String dtimeExp ) {
		String returnValue = "day(" + dtimeExp + ")" ;
		if( databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)){
			returnValue = "day(" + dtimeExp + ")" ;
		}else if( databaseType.equals(CrmConstants.DB_TYPE_ORACLE)){
			returnValue = "to_number(to_char('" + dtimeExp + "','dd'))";
		}
		
		return returnValue ;
	}*/
}
