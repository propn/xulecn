package com.ztesoft.crm.salesres.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;

public class ObjUtil {

	private static Logger logger = Logger.getLogger(ObjUtil.class);

	/**
	 * ��orig��ֵ������dest��
	 *
	 * @param dest
	 * @param orig
	 */
	public static void copyProperties(Object dest, Object orig) {
		BeanUtilsBean beanUtil = BeanUtilsBean.getInstance();
		try {
			beanUtil.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			logger.error("ObjUtil��copyProperties(Object dest,Object orig)ʱ������!", e);
		} catch (InvocationTargetException e) {
			logger.error("ObjUtil��copyProperties(Object dest,Object orig)ʱ������!", e);
		}
	}

	/**
	 * ���÷����һ��������ȡ�ò�Ϊnull�����Եļ����ַ���
	 * @param obj
	 * @param deli
	 * @return
	 */
	public static String printFields(Object obj,String deli) {
		if (obj == null)
			return "";
		if(obj instanceof String||obj instanceof Long||obj instanceof Integer||obj instanceof Float
				||obj instanceof Double||obj instanceof Byte||obj instanceof Character||obj instanceof Short)
			return obj.toString();
		StringBuffer buffer = new StringBuffer();
		Field[] fieldArr = obj.getClass().getDeclaredFields();
		String fieldStr = "";
		try {
			if (fieldArr != null && fieldArr.length > 0)
				for (int i = 0; i < fieldArr.length; i++) {
					if (fieldArr[i] != null) {
						fieldArr[i].setAccessible(true);
						if(fieldArr[i].get(obj)!=null)
							fieldStr = fieldArr[i].get(obj).toString();
						else
							fieldStr = "";
						buffer.append(fieldArr[i].getName()).append("=")
								.append(fieldStr).append(deli);
					}
				}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
    		e.printStackTrace();
		}
		if(buffer.length()>0)
			return buffer.substring(0,buffer.length()-1);
		else
			return buffer.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

          //vo.setContent("hello,test");
          //String rtn = ObjUtil.printFields(vo,',');
          //System.out.println(rtn);
	}

}
