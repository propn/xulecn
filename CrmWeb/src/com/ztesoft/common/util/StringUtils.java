package com.ztesoft.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>
 * Description: 字符串处理的公共类
 * </p>
 * <p>
 * Copyright 2006 ZTEsoft Corp.
 * </p>
 * 
 * @Create Date : 2006-4-26
 * @Version : 1.0
 */
public class StringUtils {

	/**
	 * 将字符串的数据，转换成中文的大写货币值
	 * 
	 * @param pValue
	 * @return
	 */
	public static String convertToBigMoney(String pValue) {
		double S = 0;

		try {
			S = Double.parseDouble(pValue);
		} catch (Exception e) {
		}

		S = S + 0.005; // 把它加0.005,为了预防浮点数的四舍五入造成的误差
		String Result = "", odxs, odxc, temp1, temp2;
		int integer, Point, ormb;
		odxs = "零壹贰叁肆伍陆柒捌玖";
		odxc = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		try {
			integer = (int) S; // 取得他的整数部分
			Point = (int) (100 * (S - (float) integer)); // 取得他的小数部分
			if (integer == 0)
				Result = "零圆"; // 如果整数为0,则显示零圆
			for (int i = 1; integer > 0; i++) {
				ormb = (integer % 10); // 取得他的个位
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i + 1, i + 2); // 根据循环次数确定他的单位
				Result = temp1 + temp2 + Result;
				integer = integer / 10;
			}
			ormb = (Point / 10); // 取得角
			for (int i = 1; i > -1; i--) {
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i, i + 1); // 根据循环次数确定他的单位
				Result = Result + temp1 + temp2;
				ormb = Point % 10; // 取得分
			}
			 //System.out.print("Result frist: "+Result);
            Result= Result.replaceAll("零仟","零");
            Result= Result.replaceAll("零佰","零");
            Result= Result.replaceAll("零拾","零");
            while(Result.indexOf("零零")>-1)
            {
            	Result= Result.replaceAll("零零","零");
            }
            Result = Result.replaceAll("零圆","圆");
            Result = Result.replaceAll("零万","万");
            if("圆零角零分".equals(Result))
            {
            	Result="零圆零角零分";
            }
	          //  System.out.print("Result second: "+Result);
		} catch (Exception se) {
			se.printStackTrace();
		}
		return Result;
	}

	/**
	 * 将数字串的数据，转换成中文的大写货币值
	 * 
	 * @param pValue
	 * @return
	 */
	public static String convertToBigMoney(double pValue) {
		double S = pValue;
		S = S + 0.005; // 把它加0.005,为了预防浮点数的四舍五入造成的误差
		String Result = "", odxs, odxc, temp1, temp2;
		int integer, Point, ormb;
		odxs = "零壹贰叁肆伍陆柒捌玖";
		odxc = "分角圆拾佰仟万拾佰仟亿拾佰仟万拾佰仟亿拾佰仟";
		try {
			integer = (int) S; // 取得他的整数部分
			Point = (int) (100 * (S - (float) integer)); // 取得他的小数部分
			if (integer == 0)
				Result = "零圆"; // 如果整数为0,则显示零圆
			for (int i = 1; integer > 0; i++) {
				ormb = (integer % 10); // 取得他的个位
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i + 1, i + 2); // 根据循环次数确定他的单位
				Result = temp1 + temp2 + Result;
				integer = integer / 10;
			}
			ormb = (Point / 10); // 取得角
			for (int i = 1; i > -1; i--) {
				temp1 = odxs.substring(ormb, ormb + 1);// 根据相应的值取得他的大写
				temp2 = odxc.substring(i, i + 1); // 根据循环次数确定他的单位
				Result = Result + temp1 + temp2;
				ormb = Point % 10; // 取得分
			}
			//System.out.print("Result frist: "+Result);
            Result= Result.replaceAll("零仟","零");
            Result= Result.replaceAll("零佰","零");
            Result= Result.replaceAll("零拾","零");
            while(Result.indexOf("零零")>-1)
            {
            	Result= Result.replaceAll("零零","零");
            }
            Result = Result.replaceAll("零圆","圆");
            Result = Result.replaceAll("零万","万");
            if("圆零角零分".equals(Result))
            {
            	Result="零圆零角零分";
            }
            //System.out.print("Result second: "+Result);
			
		} catch (Exception se) {
			se.printStackTrace();
		}
		return Result;
	}

    public static final String[] split(String line, String separator) {
        int index = 0;
        ArrayList matchList = new ArrayList();

        int start = line.indexOf(separator, index);

        if (start == -1)
            return new String[] { line.toString() };

        while (start > -1) {
            String match = line.subSequence(index, start).toString();
            matchList.add(match);
            index = start + separator.length();
            start = line.indexOf(separator, index);
        }

        matchList.add(line.subSequence(index, line.length()).toString());

        
        int resultSize = matchList.size();

        while (resultSize > 0 && matchList.get(resultSize - 1).equals(""))
            resultSize--;
        
        String[] result = new String[resultSize];
        return (String[]) matchList.subList(0, resultSize).toArray(result);
    }
	
	
	public static final String[] splitWithoutSpace(String line, String separator) {
		LinkedList list = new LinkedList();
		if (line != null) {
			int start = 0;
			int end = 0;
			int separatorLen = separator.length();
			while ((end = line.indexOf(separator, start)) >= 0) {
				String str = line.substring(start, end).trim();
				if (str != null && !str.equals(""))
					list.add(str);
				start = end + separatorLen;
			}
			if (start < line.length()) {
				String str = line.substring(start, line.length()).trim();
				if (str != null && !str.equals(""))
					list.add(str);
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

    public static final String replaceAll(String line, String str1, String str2) {
        StringBuffer newStr = new StringBuffer();
        int lastAppendIndex=0;
        
        int start=line.indexOf(str1,lastAppendIndex);
        
        if(start==-1)return line;
        
        while(start>-1){
            newStr.append(line.subSequence(lastAppendIndex, start));
            newStr.append(str2);
            lastAppendIndex=start+str1.length();
            start=line.indexOf(str1,lastAppendIndex);
        }
        newStr.append(line.subSequence(lastAppendIndex, line.length()));
        
        return newStr.toString();

    }
	
	public static String join(Iterator iterator, String separator) throws Exception {
		if (iterator == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(256);
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				buf.append("'"+obj+"'");
			}
			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}
	
	/**
	 * 把字符转换为钱数0.00的格式
	 * @param temp
	 * @return
	 */
	public static String formatToMoney(String str) {
		int place = 0;
		int len = 0;
		if (str == null || "".equals(str)) {
			return "0.00";
		}
		place = str.indexOf(".");
		if (place == -1) {
			return str = str + ".00";
		}
		len = str.length();
		if ((len - place) == 2) {
			str = str + "0";
		} else if ((len - place) == 1) {
			str = str + "00";
		} else {
			str = str.substring(0, place + 3);
		}
		return str;
	}
	
	 /*
     * function :校验是否为数值性
     * time :2009-8-24
     * **/
	public static  boolean isNum(String num)
	{
	   if(num==null ||num.equals(""))return false;
	   Pattern pattern = Pattern.compile("^\\d+$");
	   Matcher isNum = pattern.matcher(num);
	   return isNum.matches();
	}
    /**
     *function :校验是否为货币性
     * time :2009-8-24 
     * */
	public static boolean isMoney(String money){
		
				if(money==null ||money.equals(""))return false;
		   Pattern pattern = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
		   Matcher isNum = pattern.matcher(money);
		   return isNum.matches();
	}

	public static void main(String[] args) {

		StringUtils.replaceAll("erer(erer(", "(", " ( ");

		StringUtils.replaceAll("erer(erer(erer", "(", " ( ");

	}

}
