package com.ztesoft.buffalo.burlap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.PageModel;

/**
 * 
 * ��˵��:
 * 
 * ��������Ķ������ͣ�����dataset��ص�xml��
 * 
 * Copyright ? 2006 Guangzhou Nanfang telecom system software Co.Ltd. All rights
 * reserved.
 * 
 * @author fjy
 * @version 1.0
 * @since 2006-2-15
 * @modified by fjy 2006-2-15
 */
public class NFBurlapDatasetOutput {
	public static final String REPLY_BEGIN = "<?xml version=\"1.0\" encoding=\"utf-8\"?><buffalo-reply>";
	public static final String REPLY_END = "</buffalo-reply>";
	private static Logger logger = Logger.getLogger(NFBurlapDatasetOutput.class);
	

	// ������ֶ�,�����ʾ.
	private String[] fields = new String[0];

	// ������ֶ�,�ַ�����ʾ,ʹ�÷ָ���: ,
	private String fieldsStr = "";

	protected OutputStream os;

	public NFBurlapDatasetOutput(OutputStream outstream) {
		init(outstream);
	}

	public void init(OutputStream os) {
		this.os = os;
		/*
		 * if (serializerFactory == null) serializerFactory = new
		 * BurlapSerializerFactory();
		 */
	}

	/**
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeObject(Object object) throws IOException {
		if (object == null) {
			writeNull();
			return;
		} else {

			// �ݲ�����map���͡�
			if (object instanceof HashMap) {
				//map
				writeMap(object);
			} else if (object instanceof PageModel) {
				// PageModel
				PageModel pageModel = (PageModel) object;
				writeObject(pageModel.getList());
			} else if (Collection.class.isAssignableFrom(object.getClass())) {
				// Collection
				writeCollection(object);
			} else if (object.getClass().isArray()) {
				// Array
				writeArray(object);
			} else {
				// object
				writeJava(object);
			}

			return;
		}
	}

	/**
	 * 
	 * @param object
	 * @throws IOException
	 */
	protected void writeMap(Object object) throws IOException {
		HashMap map = (HashMap) object;
		Iterator iter = map.keySet().iterator();

		startRecord();
		StringBuffer recordBuf = new StringBuffer();
		recordBuf.append("<new>");
		Object key = null;
		Object valueObj = null;
		String value = "";

		if (fields == null || fields.length == 0) {
			// ���fieldsû��ָ�����������е����ԡ�
			while (iter.hasNext()) {
				key = iter.next();
				valueObj = map.get(key);
				value = (String) valueObj.toString();

				recordBuf.append(encodeStr(value) + ",");
			}
			
		} else {
			// ���fields��ָ��������fields��˳�򷵻����ݡ�
			for (int i = 0; i < fields.length; i++) {
				valueObj = map.get(fields[i]);				
				
				if (null == valueObj) {
					// �����ڵ����ԣ�����""�����ܺ���λ�á�
					recordBuf.append(",");
				}else{
					value = (String) valueObj;
					try{
					recordBuf.append(encodeStr(value) + ",");
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}		

		recordBuf.append("</new>");
		print(recordBuf.toString());
		completeRecord();
		
	}

	/**
	 * 
	 * @param object
	 * @throws IOException
	 */
	protected void writeCollection(Object object) throws IOException {
		Collection list = (Collection) object;
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Object value = iter.next();
			if (value instanceof HashMap) {
				//map
				writeMap(value);
			} else {
				writeJava(value);
			}

		}
	}

	/**
	 * 
	 * @param object
	 * @throws IOException
	 */
	protected void writeArray(Object object) throws IOException {
		Object[] array = (Object[]) object;
		for (int i = 0; i < array.length; i++)

			writeJava(array[i]);
	}

	/**
	 * ��java����ת����һ��dataset�е�record��
	 * 
	 * @param object
	 * @throws IOException
	 */
	protected void writeJava(Object object) throws IOException {

		// �������Ա��ַ���
		startRecord();

		StringBuffer recordBuf = new StringBuffer();
		recordBuf.append("<new>");

		BeanInfo bi;
		try {

			bi = Introspector.getBeanInfo(object.getClass());

			PropertyDescriptor[] voprops = bi.getPropertyDescriptors();

			if (fields == null || fields.length == 0) {
				// ���fieldsû��ָ�����������е����ԡ�
				PropertyDescriptor prop = null;
				String value = "";
				for (int i = 0; i < voprops.length; i++) {
					prop = voprops[i];
					// class ���ԴӲ���ʾ
					if ("class".equals(voprops[i].getName()))
						continue;
					// ���õ�ǰ����
					value = invokePropMethod(prop, object);

					// ��������
					recordBuf.append(value + ",");
				}
			} else {
				String field = "";
				Object valueObj = null;
				String value = "";
				// ���fields��ָ��������fields��˳�򷵻����ݡ�
				for (int i = 0; i < fields.length; i++) {
					field = fields[i];
					/*try{
						Method propMethod = object.getClass().getMethod("get"+capitalizePropertyName(field),null);					
						valueObj = propMethod.invoke(object, null);
					}catch(Exception ex){
						ex.printStackTrace();
						continue;
						//throw ex;
					}
					
					if (valueObj == null) {
						value = "";
					} else {
						value = (String) valueObj.toString();
					}					
					//��������
					recordBuf.append(encodeStr(value) + ",");*/

					boolean found = false;
					for (int j = 0; j < voprops.length; j++) {
						PropertyDescriptor prop = voprops[j];
						if (!field.equalsIgnoreCase(prop.getName())) {
							continue;
						} else {

							found = true;
							// ���õ�ǰ����
							value = invokePropMethod(prop, object);

							// ��������
							recordBuf.append(value + ",");
						}

					}
					if (!found) {
						// �����ڵ����ԣ�����""�����ܺ���λ�á�
						recordBuf.append(",");
					}
				}

			}

		} catch (Exception e1) {
			//e1.printStackTrace();
		}

		recordBuf.append("</new>");
		print(recordBuf.toString());
		completeRecord();
		//logger.debug("Return XML: " + recordBuf.toString());
	}
	
	private static String capitalizePropertyName(String s) {
		if (s.length() == 0) {
			return s;
		}
		char chars[] = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}
	/**
	 * 
	 * @param prop
	 * @return
	 */
	private String invokePropMethod(PropertyDescriptor prop, Object object) {

		Method getter = prop.getReadMethod();
		String value = "";

		try {

			// ��ͬ�������͵Ĵ���
			if ("java.util.Date".equals(prop.getPropertyType().getName())
					|| "java.sql.Date".equals(prop.getPropertyType().getName())
					|| "java.sql.Timestamp".equals(prop.getPropertyType().getName())) {
				Date tmpValue = null;
				tmpValue = (Date) getter.invoke(object, null);
				if (tmpValue != null) {
					/*
					 * //���ڸ�ʽ�趨ΪISO8609���� 20080508T095231Z value =
					 * formatISODate(tmpValue);
					 */
					// ���ڸ�ʽ�趨Ϊlong���͵�΢�롣
					value = "" + tmpValue.getTime();
				}
			} else if ("java.lang.Double".equals(prop.getPropertyType().getName())
					|| "double".equals(prop.getPropertyType().getName())) {
				Double tmpValue = null;
				tmpValue = (Double) getter.invoke(object, null);
				value = tmpValue.toString();

			} else if ("java.lang.Long".equals(prop.getPropertyType().getName())
					|| "long".equals(prop.getPropertyType().getName())) {
				Long tmpValue = null;
				tmpValue = (Long) getter.invoke(object, null);
				value = tmpValue.toString();

			} else if ("java.lang.Integer".equals(prop.getPropertyType().getName())
					|| "int".equals(prop.getPropertyType().getName())) {
				Integer tmpValue = null;
				tmpValue = (Integer) getter.invoke(object, null);
				value = tmpValue.toString();

			} else {
				// Ĭ��ֱ��ת����String
				Object obj = null;
				try {
					obj = getter.invoke(object, null);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (obj != null && obj instanceof String)
					value = (String) obj;
				else if (obj == null) {
					value = "";
				} else {
					value = (String) obj.toString();
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// encode
		value = encodeStr(value);

		return value;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void startRecord() throws IOException {
		print("<record  state=\"none\" >");
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void completeRecord() throws IOException {
		print("</record>");
	}

	/**
	 * ԭ������ȡ����ʹ���µĴ������ķ�����
	 * 
	 * @throws IOException
	 */
	public void startReply() throws IOException {
		// burlap
		//print(ProtocalTag.REPLY_BEGIN);
		print(REPLY_BEGIN);
		// records Ĭ��һЩ��ҳ�����ԡ�
		print("<xml><records>");
	}

	/**
	 * �·��������뷵�ؽ������Ϊ������
	 * 
	 * @throws IOException
	 */
	public void startReply(Object object) throws IOException {
		// burlap
		//print(ProtocalTag.REPLY_BEGIN);
		print(REPLY_BEGIN);
		// records
		if (object instanceof PageModel) {
			PageModel pageModel = (PageModel) object;

			StringBuffer xmlSbf = new StringBuffer();
			xmlSbf.append("<xml><records totalCount=\"");
			xmlSbf.append(pageModel.getTotalCount());
			xmlSbf.append("\" pageIndex=\"");
			xmlSbf.append(pageModel.getPageIndex());
			xmlSbf.append("\" pageCount=\"");
			xmlSbf.append(pageModel.getPageCount());
			xmlSbf.append("\" pageSize=\"");
			xmlSbf.append(pageModel.getPageSize());
			xmlSbf.append("\">");

			print(xmlSbf.toString());

		} else {
			// Ĭ�Ϸ�ҳ�����ԡ�
			print("<xml><records totalCount=\"-1\" pageIndex=\"1\" pageCount=\"1\">");
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void completeReply() throws IOException {
		// records
		print("</records></xml>");
		// burlap
		//print(ProtocalTag.REPLY_END);
		print(REPLY_END);
	}

	protected void print(char v) throws IOException {
		os.write(v);
	}

	protected void print(int v) throws IOException {
		print(String.valueOf(v));
	}

	protected void print(long v) throws IOException {
		print(String.valueOf(v));
	}

	protected void print(double v) throws IOException {
		print(String.valueOf(v));
	}

	protected void print(String s) throws IOException {
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			os.write(ch);
		}
	}

	public void writeNull() throws IOException {
		print("");
	}

	/**
	 * Prints a date.
	 * 
	 * @param date
	 *            the date to print.
	 */
	public String formatISODate(Date dvalue) {

		// Calendar calendar =
		// Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dvalue);

		StringBuffer dateBuf = new StringBuffer();

		int year = calendar.get(Calendar.YEAR);

		dateBuf.append((char) ('0' + (year / 1000 % 10)));
		dateBuf.append((char) ('0' + (year / 100 % 10)));
		dateBuf.append((char) ('0' + (year / 10 % 10)));
		dateBuf.append((char) ('0' + (year % 10)));

		int month = calendar.get(Calendar.MONTH) + 1;
		dateBuf.append((char) ('0' + (month / 10 % 10)));
		dateBuf.append((char) ('0' + (month % 10)));

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		dateBuf.append((char) ('0' + (day / 10 % 10)));
		dateBuf.append((char) ('0' + (day % 10)));

		dateBuf.append('T');

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		dateBuf.append((char) ('0' + (hour / 10 % 10)));
		dateBuf.append((char) ('0' + (hour % 10)));

		int minute = calendar.get(Calendar.MINUTE);
		dateBuf.append((char) ('0' + (minute / 10 % 10)));
		dateBuf.append((char) ('0' + (minute % 10)));

		int second = calendar.get(Calendar.SECOND);
		dateBuf.append((char) ('0' + (second / 10 % 10)));
		dateBuf.append((char) ('0' + (second % 10)));

		dateBuf.append('Z');

		return dateBuf.toString();
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public String getFieldsStr() {
		return fieldsStr;
	}

	public void setFieldsStr(String fieldsStr) {
		this.fieldsStr = fieldsStr;
	}

	/**
	 * 
	 * @param fieldName
	 * @return
	 */
	private boolean checkField(String fieldName) {
		boolean valid = false;

		for (int i = 0; i < fields.length; i++) {
			if (fieldName.equals(fields[i])) {
				valid = true;
				break;
			}
		}

		return valid;
	}

	/**
	 * 
	 * @param strIn
	 * @return
	 */
	protected String encodeStr(String strIn) {
		int intLen = strIn.length();
		String strOut = "";
		char strTemp;

		for (int i = 0; i < intLen; i++) {
			strTemp = strIn.charAt(i);
			if (strTemp > 255) {
				String tmp = Integer.toHexString(strTemp);
				for (int j = tmp.length(); j < 4; j++)
					tmp = "0" + tmp;
				strOut = strOut + "^" + tmp;
			} else {
				if (strTemp < 48 || (strTemp > 57 && strTemp < 65) || (strTemp > 90 && strTemp < 97) || strTemp > 122) {
					String tmp = Integer.toHexString(strTemp);
					for (int j = tmp.length(); j < 2; j++)
						tmp = "0" + tmp;
					strOut = strOut + "~" + tmp;
				} else {
					strOut = strOut + strIn.charAt(i);
				}
			}
		}
		return (strOut);
	}

	/**
	 * 
	 * @param strIn
	 * @return
	 */
	protected String decodeStr(String strIn) {
		int intLen = strIn.length();
		StringBuffer strOut = new StringBuffer();
		char strTemp;

		for (int i = 0; i < intLen; i++) {
			strTemp = strIn.charAt(i);
			switch (strTemp) {
			case 126: {

				String sTemp = strIn.substring(i + 1, i + 3);
				int iTemp = Integer.parseInt(sTemp, 16);
				sTemp = new Character((char) iTemp).toString();
				strOut.append(sTemp);

				// js
				/*
				 * strTemp = strIn.substring(i+1, i+3); strTemp =
				 * parseInt(strTemp, 16); strTemp =
				 * String.fromCharCode(strTemp); strOut = strOut+strTemp;
				 */

				i += 2;
				break;
			}
			case 94: {

				String sTemp = strIn.substring(i + 1, i + 5);
				int iTemp = Integer.parseInt(sTemp, 16);
				sTemp = new Character((char) iTemp).toString();
				strOut.append(sTemp);

				// js
				/*
				 * strTemp = strIn.substring(i + 1, i + 5); strTemp =
				 * parseInt(strTemp, 16); strTemp =
				 * String.fromCharCode(strTemp); strOut = strOut + strTemp;
				 */

				i += 4;
				break;
			}
			default: {

				strOut.append("" + strTemp);

				break;
			}
			}
		}
		return strOut.toString();
	}

	public static void main(String[] argv) {

		NFBurlapDatasetOutput dataSetSerializer = new NFBurlapDatasetOutput(null);
		char cc = "����=".charAt(0);
		logger.debug(Integer.toHexString(cc));

//		logger.debug(("~".charAt(0)));
		logger.debug(Integer.toHexString("~".charAt(0)));
		logger.debug(Integer.toHexString("^".charAt(0)));

		// encode demo
		String inStr = "a�а�asdf��.,��a��";
		String enStr = dataSetSerializer.encodeStr(inStr);

		logger.debug("encode=" + enStr);
		logger.debug("decode=" + dataSetSerializer.decodeStr(enStr));

		NFBurlapDatasetOutput dataSetOut = new NFBurlapDatasetOutput(System.out);
		ArrayList alist = new ArrayList();
		HashMap map = new HashMap();
		map.put("aa0", "aa0");
		map.put("aa1", "aa1");
		map.put("aa2", "aa2");
		alist.add(map);

		try {
			//dataSetOut.setFields(null);
			dataSetOut.startReply(alist);
			dataSetOut.writeObject(alist);
			//dataSetOut.completeReply();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
