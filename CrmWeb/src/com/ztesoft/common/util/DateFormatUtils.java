package com.ztesoft.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * <p>
 * Description: ʱ���Լ�ʱ���ʽ��صĴ�����
 * </p>
 * <p>
 * Copyright 2006 ZTEsoft Corp.
 * </p>
 * 
 * @Create Date : 2006-4-19
 * @Version : 1.0
 */
public class DateFormatUtils {
	private static Logger logger = Logger.getLogger(DateFormatUtils.class);

	/**
	 * �õ�Ӧ�÷�������ǰ���ڣ���Ĭ�ϸ�ʽ��ʾ��
	 * 
	 * @return
	 */
	public static String getFormatedDate() {
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);
		return dateFormator.format(date);

	}

	/**
	 * �õ�Ӧ�÷�������ǰ����ʱ�䣬��Ĭ�ϸ�ʽ��ʾ��
	 * 
	 * @return
	 */
	public static String getFormatedDateTime() {

		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(date);

	}

	/**
	 * �õ�Ӧ�÷������ĵ�ǰʱ��
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * �õ�Ӧ�÷������ĵ�ǰʱ�䣬���롣
	 * 
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * �õ�Ӧ�÷�������ǰ���� ����ָ���ĸ�ʽ���ء�
	 * 
	 * @param pattern
	 *            ��ʽ���ͣ�ͨ��ϵͳ�����ж��壬�磺CrmConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String formatDate(String pattern) {

		Date date = new Date();
		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = dateFormator.format(date);

		return str;
	}

	/**
	 * ת���������� ����ָ���ĸ�ʽ���ء�
	 * 
	 * @param date
	 * @param pattern
	 *            ��ʽ���ͣ�ͨ��ϵͳ�����ж��壬�磺CrmConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {

		if (date == null)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = dateFormator.format(date);

		return str;
	}

	/**
	 * ת���������� ����ָ���ĸ�ʽ���ء�
	 * 
	 * @param date
	 * @param pattern
	 *            ��ʽ���ͣ�ͨ��ϵͳ�����ж��壬�磺CrmConstants.DATE_FORMAT_8
	 * @param loc
	 *            locale
	 * @return
	 */
	public static String formatDate(Date date, String pattern, Locale loc) {
		if (pattern == null || date == null) {
			return "";
		}
		String newDate = "";
		if (loc == null)
			loc = Locale.getDefault();
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
			newDate = sdf.format(date);
		}
		return newDate;
	}

	/**
	 * ���ַ�ʱ���һ����ʽת������һ����ʽ��ʱ��ĸ�ʽ�����ͨ��ϵͳ�������塣 �磺
	 * String dateStr = "2006-10-9 12:09:08";
	 * DateFormatUtils.convertDateFormat(dateStr,
	 * CrmConstants.DATE_TIME_FORMAT,
	 * CrmConstants.DATE_FORMAT_8);
	 * 
	 * @param sdate
	 * @param patternFrom
	 *            ��ʽ���ͣ�ͨ��ϵͳ�����ж��壬�磺CrmConstants.DATE_FORMAT_8
	 * @param patternTo
	 *            ��ʽ���ͣ�ͨ��ϵͳ�����ж��壬�磺CrmConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String convertDateFormat(String dateStr, String patternFrom, String patternTo) {

		if (dateStr == null || patternFrom == null || patternTo == null) {
			return "";
		}

		String newDate = "";

		try {

			Date dateFrom = parseStrToDate(dateStr, patternFrom);
			newDate = formatDate(dateFrom, patternTo);

		} catch (Exception e) {
		}

		return newDate;
	}

	/**
	 * ��ʱ�䴮����Ĭ�ϸ�ʽCrmConstants.DATE_FORMAT����ʽ����Date��
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDate(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	public static String parseDateStrToDateTimeStr(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
		return formatDate(tDate, CrmConstants.DATE_TIME_FORMAT);

	}

	/**
	 * ��ʱ�䴮����Ĭ�ϸ�ʽCrmConstants.DATE_FORMAT����ʽ����Date��
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Calendar parseStrToCalendar(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);

		return cal;
	}

	/**
	 * ��ʱ�䴮����Ĭ�ϸ�ʽCrmConstants.DATE_TIME_FORMAT����ʽ����Date��
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDateTime(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * ��ʱ�䴮����ָ����ʽ����ʽ����Date��
	 * 
	 * @param date
	 * @param pattern
	 *            ��ʽ���ͣ�ͨ��ϵͳ�����ж��壬�磺CrmConstants.DATE_FORMAT_8
	 * @return
	 */

	public static Date parseStrToDate(String dateStr, String pattern) {
		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * ��ʱ���ʽ��ӣ�
	 * ����Ҫ��ӵ�ʱ����㣨�ַ����ͻ�ʱ�����ͣ�����ӵ�ʱ�������ͣ�����ʽ��"year"�ꡢ"month"�¡�"day"�ա���hour��ʱ����minute���֡���second���롢"week"�ܣ�
	 * �����Ӻ��ʱ�䣨�ַ����ͻ�ʱ�����ͣ�
	 * 
	 * @param dateStr
	 * @param pattern
	 * @param step
	 * @param type
	 *            "year"�ꡢ"month"�¡�"day"�ա���hour��ʱ����minute���֡���second���롢"week"��
	 *            ͨ������DateFormatUtils.YEAR��������.
	 * @return
	 */
	public static String addDate(String dateStr, String pattern, int step, String type) {
		if (dateStr == null) {
			return dateStr;
		} else {
			Date date1 = DateFormatUtils.parseStrToDate(dateStr, pattern);

			Locale loc = Locale.getDefault();
			Calendar cal = new GregorianCalendar(loc);
			cal.setTime(date1);

			if (DateFormatUtils.WEEK.equals(type)) {

				cal.add(Calendar.WEEK_OF_MONTH, step);

			} else if (DateFormatUtils.YEAR.equals(type)) {

				cal.add(Calendar.YEAR, step);

			} else if (DateFormatUtils.MONTH.equals(type)) {

				cal.add(Calendar.MONTH, step);

			} else if (DateFormatUtils.DAY.equals(type)) {

				cal.add(Calendar.DAY_OF_MONTH, step);

			} else if (DateFormatUtils.HOUR.equals(type)) {

				cal.add(Calendar.HOUR, step);

			} else if (DateFormatUtils.MINUTE.equals(type)) {

				cal.add(Calendar.MINUTE, step);

			} else if (DateFormatUtils.SECOND.equals(type)) {

				cal.add(Calendar.SECOND, step);

			}

			return DateFormatUtils.formatDate(cal.getTime(), pattern);
		}
	}

	/**
	 * ��ʱ���ʽ�����
	 * ����Ҫ��ӵ�ʱ����㣨�ַ����ͻ�ʱ�����ͣ�����ӵ�ʱ�������ͣ�����ʽ��"year"�ꡢ"month"�¡�"day"�ա���hour��ʱ����minute���֡���second���롢"week"�ܣ�
	 * �����Ӻ��ʱ�䣨�ַ����ͻ�ʱ�����ͣ�
	 * 
	 * @param dateStr
	 * @param pattern
	 * @param step
	 * @param type
	 *            "year"�ꡢ"month"�¡�"day"�ա���hour��ʱ����minute���֡���second���롢"week"��
	 * @return
	 */
	public static String minusDate(String dateStr, String pattern, int step, String type) {

		return addDate(dateStr, pattern, (0 - step), type);

	}

	/**
	 * ������������
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		if (date == null) {
			return date;
		} else {
			Locale loc = Locale.getDefault();
			Calendar cal = new GregorianCalendar(loc);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, days);
			return cal.getTime();
		}
	}

	public static int getDays(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		else
			return (int) ((date2.getTime() - date1.getTime()) / 0x5265c00L);
	}

	/**
	 * ���ڱȽϴ�С
	 * 
	 * @param dateStr1
	 * @param dateStr2
	 * @param pattern
	 * @return
	 */
	public static int compareDate(String dateStr1, String dateStr2, String pattern) {

		Date date1 = DateFormatUtils.parseStrToDate(dateStr1, pattern);
		Date date2 = DateFormatUtils.parseStrToDate(dateStr2, pattern);

		return date1.compareTo(date2);

	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayInMonth(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		cal.add(Calendar.DAY_OF_MONTH, (1 - day));

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @author mzp
	 * @time 20080717
	 * @return
	 */
	public static String getFirstDayInMonth(String dateStr, String pattern,String isZeroSecond) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int year=cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour=cal.get(Calendar.HOUR);
		int minute=cal.get(Calendar.MINUTE);
		int second=cal.get(Calendar.SECOND);
		day=1;hour=0;minute=0;second=0;
		cal.clear();
		cal.set(year, month, day, hour, minute, second);
		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}
	
	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getLastDayInMonth(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		int maxDayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int step = maxDayInMonth - day;

		cal.add(Calendar.DAY_OF_MONTH, step);

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayInWeek(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, (1 - day));

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getLastDayInWeek(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, (6 - day));

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	public static long calendarDayPlus(String dateStr1, String dateStr2) {
		
		if (dateStr1 == null || dateStr2 == null||dateStr1.equals("")||dateStr2.equals(""))
			return 0;
		Date date1 = DateFormatUtils.parseStrToDate(dateStr1);
		Date date2 = DateFormatUtils.parseStrToDate(dateStr2);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		long t1 = c1.getTimeInMillis();
		long t2 = c2.getTimeInMillis();
		long day = (t2 - t1) / (1000 * 60 * 60 * 24);
		return day;
	}
	
	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static int calendarPlus(String dateStr1, String dateStr2) {
		
		if (dateStr1 == null || dateStr2 == null||dateStr1.equals("")||dateStr2.equals(""))
			return 0;
		
		Calendar cal1 = DateFormatUtils.parseStrToCalendar(dateStr1);
		
		Calendar cal2 = DateFormatUtils.parseStrToCalendar(dateStr2);
		
		int dataStr1Year = cal1.get(Calendar.YEAR);
		int dataStr2Year = cal2.get(Calendar.YEAR);
		
		int dataStr1Month = cal1.get(Calendar.MONTH);
		int dataStr2Month = cal2.get(Calendar.MONTH);


		return ((dataStr2Year*12+dataStr2Month+1)-(dataStr1Year*12+dataStr1Month));
		
	}


	public static void main(String[] argv) {

		String dateStr = "2004-2-18 12:13:34";
		String dateStr1 = "2006-7-18 12:13:34";
		String dateStr2 = "2006-7-18 12:13:34";
		// add
		Date date = DateFormatUtils.addDay(DateFormatUtils.parseStrToDate(dateStr, CrmConstants.DATE_TIME_FORMAT), 20);

		logger.debug("after add " + DateFormatUtils.formatDate(date, CrmConstants.DATE_TIME_FORMAT));
		// add
 
		// sub
 

		// get day
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.DAY_OF_MONTH));
		// get month Ҫ��1���ǵ�ǰ����
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.MONTH) + 1);
		// // get year
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.YEAR));
		// // get hour
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.HOUR_OF_DAY));
		// // get minute
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.MINUTE));
		// // get second
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.SECOND));
		//
		// // compareDate
		// logger.debug(DateFormatUtils.compareDate(dateStr1,
		// dateStr2,
		// CrmConstants.DATE_TIME_FORMAT));

		// first or last
		// logger.debug(DateFormatUtils.getFirstDayInWeek(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		// logger.debug(DateFormatUtils.getLastDayInWeek(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		// logger.debug(DateFormatUtils.getFirstDayInMonth(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		// logger.debug(DateFormatUtils.getLastDayInMonth(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		//
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr)
		// .getActualMaximum(Calendar.DAY_OF_MONTH));

	}

	public final static String YEAR = "year";

	public final static String MONTH = "month";

	public final static String DAY = "day";

	public final static String HOUR = "hour";

	public final static String MINUTE = "minute";

	public final static String SECOND = "second";

	public final static String WEEK = "week";

}
