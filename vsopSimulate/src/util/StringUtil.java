package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 
 * <pre>
 * Title:�ַ���������
 * Description: �ַ��������� 
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class StringUtil {
	/**
	 * ȡָ��С��λ�ĸ�����,����С��λ��ʱ����
	 * @param floStr
	 * @return
	 */
	public static String paseFloat(String floStr, int location) {
		if (floStr == null) {
			return "";
		}
		int index = floStr.indexOf(".");
		//���û��С����.���һ��С����.
		if (index == -1) {
			floStr = floStr + ".";
		}
		index = floStr.indexOf(".");
		int leave = floStr.length() - index;
		//���С��ָ��λ�����ں��油��
		for (; leave <= location; leave++) {
			floStr = floStr + "0";
		}
		return floStr.substring(0, index + location + 1);
	}

	/**
	 * ���ַ�������ת��������.
	 * 
	 * @param str
	 *            �ַ�������
	 * @return int ��������ֵ���������ת���򷵻�Ĭ��ֵdefaultValue.
	 */
	public static int getInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		if (isInt(str)) {
			return Integer.parseInt(str);
		} else {
			return defaultValue;
		}
	}

	/** ȡ������Ĭ��ֵ-1 */
	public static int getInt(String str) {
		return getInt(str, -1);
	}

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ����
	 */
	public static boolean isInt(String str) {
		return str.matches("\\d+");
	}

	/**
	 * �ж�һ���ַ����Ƿ��
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/** �ж�ָ�����ַ����Ƿ��ǿմ� */
	public static boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/** ����ַ���ΪNULL�Ĵ��� */
	public static String notNull(String str) {
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * �ж�2���ַ����Ƿ����
	 */
	public static boolean isequals(String str1, String str2) {
		return str1.equals(str2);
	}

	/**
	 * �ڳ�����ǰ����
	 * 
	 * @param num ����
	 * @param length ���λ��
	 */
	public static String addzero(long num, int length) {
		String str = "";
		if (num < Math.pow(10, length - 1)) {
			for (int i = 0; i < (length - (num + "").length()); i++) {
				str += "0";
			}
		}
		str = str + num;
		return str;
	}

	/**
	 * ������ǰ����
	 * 
	 * @param num ����
	 * @param length ���λ��
	 */
	public static String addzero(int num, int length) {
		String str = "";
		if (num < Math.pow(10, length - 1)) {
			for (int i = 0; i < (length - (num + "").length()); i++) {
				str += "0";
			}
		}
		str = str + num;
		return str;
	}

	/**
	 * ʹHTML�ı�ǩʧȥ����*
	 * 
	 * @param input
	 *            ���������ַ���
	 * @return String
	 */
	public static final String escapeHTMLTag(String input) {
		if (input == null) {
			return "";
		}
		input = input.trim().replaceAll("&", "&amp;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll("\n", "<br>");
		input = input.replaceAll("'", "&#39;");
		input = input.replaceAll("\"", "&quot;");
		input = input.replaceAll("\\\\", "&#92;");
		return input;
	}

	/**
	 * ��ԭhtml��ǩ
	 * @param input
	 * @return
	 */
	public static final String unEscapeHTMLTag(String input) {
		if (input == null) {
			return "";
		}
		input = input.trim().replaceAll("&amp;", "&");
		input = input.replaceAll("&lt;", "<");
		input = input.replaceAll("&gt;", ">");
		input = input.replaceAll("<br>", "\n");
		input = input.replaceAll("&#39;", "'");
		input = input.replaceAll("&quot;", "\"");
		input = input.replaceAll("&#92;", "\\\\");
		return input;
	}

	/**
	 * ������ϳ��ַ���
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static String toString(String[] str, String seperator) {
		if (str == null || str.length == 0) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0, n = str.length; i < n; i++) {
			if (i != 0) {
				buf.append(seperator);
			}
			buf.append(str[i]);
		}
		return buf.toString();
	}

	/**
	 * ���ַ����ָ�������
	 * 
	 * @param str
	 *            �ַ� �磺 1/2/3/4/5
	 * @param seperator
	 *            �ָ����� ��: /
	 * @return String[] �ַ������� ��: {1,2,3,4,5}
	 */
	public static String[] split(String str, String seperator) {
		StringTokenizer token = new StringTokenizer(str, seperator);
		int count = token.countTokens();
		String[] ret = new String[count];
		for (int i = 0; i < count; i++) {
			ret[i] = token.nextToken();
		}
		return ret;
	}

	/**
	 * ��ָ���ķָ����ָ����ݣ���N���ָ����򷵻�һ��N+1������
	 * @param str
	 * @param seperator
	 * @return
	 */
	public static String[] splitHaveEmpty(String str, String seperator) {
		//�ָ���ǰ������һ���հ��ַ�
		str = str.replaceAll(seperator, " " + seperator + " ");
		return str.split(seperator);
	}

	/**
	 * @param len
	 *            ��Ҫ��ʾ�ĳ���(<font color="red">ע�⣺��������byteΪ��λ�ģ�һ��������2��byte</font>)
	 * @param symbol
	 *            ���ڱ�ʾʡ�Ե���Ϣ���ַ����硰...��,��>>>���ȡ�
	 * @return ���ش������ַ���
	 */
	public static String getSub(String str, int len, String symbol) {
		if (str == null) {
			return "";
		}
		try {
			int counterOfDoubleByte = 0;
			byte[] b = str.getBytes("gbk");
			if (b.length <= len) {
				return str;
			}
			for (int i = 0; i < len; i++) {
				if (b[i] < 0) {
					counterOfDoubleByte++; // ͨ���ж��ַ������������н�ȡ
				}
			}
			if (counterOfDoubleByte % 2 == 0) {
				str = new String(b, 0, len, "gbk") + symbol;
			} else {
				str = new String(b, 0, len - 1, "gbk") + symbol;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * ���ֽڻ�ȡ�ַ����ĳ���,һ������ռ�����ֽ�
	 * @param str
	 * @return
	 */
	public static int getLen(String str) {
		try {
			byte[] b = str.getBytes("gbk");
			return b.length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getSub(String str, int len) {
		return getSub(str, len, "");
	}

	public static String getString(String str, int begin, int end) {
		return str.substring(begin, end);
	}

	/** ֻȡĳһ�ַ�����ǰ�����ַ���������...��ʾ */
	public static String getAbc(String str, int len) {
		return getAbc(str, len, "...");
	}

	/** ��ȡ���ٳ���ǰ��һ���ַ��� */
	public static String getAbc(String str, int len, String symbol) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len).concat(symbol);
		}
	}

	/**
	 * ��ȡĳ�ַ����������ַ���֮���һ���ַ��� eg:StringUtil.subBetween("yabczyabcz", "y", "z") =
	 * "abc"
	 */
	public static String subBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * ��ȡĳ�ַ�����������ָ���ַ���֮���һ���ַ��� StringUtil.subAfterLast("abcba", "b") = "a"
	 */
	public static String subAfterLast(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null || separator.length() == 0) {
			return "";
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * ��ȡĳ�ַ�����������ָ���ַ���֮ǰ��һ���ַ��� StringUtil.subBeforeLast("abcba", "b") = "abc"
	 */
	public static String subBeforeLast(String str, String separator) {
		if (str == null || separator == null || str.length() == 0 || separator.length() == 0) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * ��ȡĳ�ַ�����ָ���ַ���֮���һ���ַ��� StringUtil.subAfter("abcba", "b") = "cba"
	 */
	public static String subAfter(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * ��ȡĳ�ַ�����ָ���ַ���֮ǰ��һ���ַ��� StringUtil.subBefore("abcbd", "b") = "a"
	 */
	public static String subBefore(String str, String separator) {
		if (str == null || separator == null || str.length() == 0) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/** �ж������ַ������Ƿ�����ͬ��Ԫ�� */
	public static boolean containsNone(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsNone(str, invalidChars.toCharArray());
	}

	/** �ж��ַ������Ƿ����ַ������е�Ԫ�� */
	public static boolean containsNone(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return false;
				}
			}
		}
		return true;
	}

	/** �ж��ַ������Ƿ����ָ���ַ��� */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return (str.indexOf(searchStr) >= 0);
	}

	/**
	 * �ж��ǲ���һ���Ϸ��ĵ����ʼ���ַ
	 * 
	 * @param email
	 * @return boolean
	 */
	static java.util.regex.Pattern emailer;

	public static boolean isEmail(String email) {
		if (emailer == null) {
			String check = "^([a-z0-9A-Z]+[-|\\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			emailer = java.util.regex.Pattern.compile(check);
		}
		java.util.regex.Matcher matcher = emailer.matcher(email);
		return matcher.matches();
	}

	/**
	 * ת��html�����ַ�Ϊhtml��
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlSpecialChars(String str) {
		try {
			if (str.trim() == null) {
				return "";
			}
			StringBuffer sb = new StringBuffer();
			char ch = ' ';
			for (int i = 0; i < str.length(); i++) {
				ch = str.charAt(i);
				if (ch == '&') {
					sb.append("&amp;");
				} else if (ch == '<') {
					sb.append("&lt;");
				} else if (ch == '>') {
					sb.append("&gt;");
				} else if (ch == '"') {
					sb.append("&quot;");
				} else if (ch == '\'') {
					sb.append("&#039;");
				} else if (ch == '(') {
					sb.append("&#040;");
				} else if (ch == ')') {
					sb.append("&#041;");
				} else if (ch == '@') {
					sb.append("&#064;");
				} else {
					sb.append(ch);
				}
			}
			if (sb.toString().replaceAll("&nbsp;", "").replaceAll("��", "").trim().length() == 0) {
				return "";
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * @param word: ������ַ���
	 * @return �Ƿ���������ַ�
	 */
	public boolean CharIsLetter(String word) {
		boolean sign = true; //��ʼ����־ΪΪ'true'
		for (int i = 0; i < word.length(); i++) { //���������ַ�����ÿһ���ַ�
			if (!Character.isLetter(word.charAt(i))) { //�жϸ��ַ��Ƿ�ΪӢ���ַ�
				sign = false; //����һλ����Ӣ���ַ����򽫱�־λ�޸�Ϊ'false'
			}
		}
		return sign;//���ر�־λ���
	}

	/**
	 * ����С�����������ʽ�滻,mark�Ǵ������ص��ʶ
	 * С�����ê���ʶ��mark�ӳ��ֵ���ŵķ�ʽ���ɣ����һ�����ֵ�С����Ϊ��
	 * mark1,�ڸ����ֵ�Ϊmark2��������ơ�
	 * С����ʾ��:
	 *         <ol>
	            <li><span class="menuId">3.1</span><a href="#">��ʷ�����˶�Ա</a></li>
	            <li><span class="menuId">3.2</span><a href="#">2004����˻������˶�Ա</a></li>
	            <li><span class="menuId">3.3</span><a href="#">�����˶�Ա</a></li>
	            <li><span class="menuId">3.4</span><a href="#">������������</a></li>
	           </ol>
	   ����һ���ַ������飬���1Ϊ����������ݣ����2ΪС�������
	 * @param input ��Ҫ������ԭʼ����
	 * @param mark  ������ê���ʶ
	 * @param bigProIndex �������������
	 * @return
	 */
	public static String[] findSpecData(String input, String mark, String bigProIndex) {
		//������Ŵ����������ı�����
		StringBuffer sb = new StringBuffer();
		//��������С���ê��
		StringBuffer smallPro = new StringBuffer("<ol>").append("\n");
		//�������С��ı��
		int index = 1;

		String regex = "(<div class=s_title>)(.*?)(</div>)";
		Matcher testM = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(input);
		while (testM.find()) {
			testM.appendReplacement(sb, "<div class=\"s_title\"><a name=\"" + mark + index + "\"></a>$2$3");
			//С��������
			String smallName = testM.group(2);
			smallPro.append("<li><span class=\"menuId\" >").append(bigProIndex).append(".").append(index).append(
					"</span><a href=\"#").append(mark).append(index).append("\">").append(smallName)
					.append("</a></li>").append("\n");
			//�������Լ�
			index++;
		}
		//�������С���⣬
		if (index != 1) {
			//��װС����
			smallPro.append("</ol>");
			//���ɴ�С����ê�������
			testM.appendTail(sb);
			return new String[] { sb.toString(), smallPro.toString() };
		}
		return null;
	}

	/**
	 * �ַ�����ȡ
	 * @param str Ҫ������ַ���
	 * @param beginIndex ��ʼλ��
	 * @param endIndex ����λ��
	 * @return
	 */
	public String substr(String str, int beginIndex, int endIndex) {
		if (isBlank(str)) {
			return "";
		}
		if (endIndex == -1) {
			return str.substring(beginIndex);
		}

		if (endIndex > str.length()) {
			endIndex = str.length();
		}
		return str.substring(beginIndex, endIndex);
	}

	/**
	 * ������ɼ�����ͬ����
	 * @param lenth
	 * @param num
	 * @return
	 */
	public static int[] random5(int lenth, int num) {

		Random rd = new Random();

		HashSet set = new HashSet();
		while (true) {
			int i = rd.nextInt(lenth);
			set.add(new Integer(i));
			if (set.size() == num) {
				break;
			}
		}
		Iterator iter = set.iterator();
		int jj[] = new int[num];
		int i = 0;
		while (iter.hasNext()) {

			jj[i] = ((Integer) iter.next()).intValue();
			++i;
		}
		return jj;
	}

	/**
	 * �ַ�����ȡ
	 * @param str Ҫ������ַ���
	 * @param beginIndex ��ʼλ��
	 * @param endIndex ����λ��
	 * @param endMark �ڽ�������...��
	 * @return
	 */
	public String substr(String str, int beginIndex, int endIndex, String endMark) {
		if (isBlank(str)) {
			return "";
		}
		if (endIndex == -1) {
			return str.substring(beginIndex);
		}

		if (endIndex > str.length()) {
			endIndex = str.length();
		}
		String restr = str.substring(beginIndex, endIndex);
		if (endIndex < str.length()) {
			restr = restr + "...";
		}
		return restr;
	}

	/**
	 * ȥ�������Ӻ�<P></P>,����ժҪʹ��
	 * @param str
	 * @return
	 */
	public static String filtHref(String str) {
		if (str == null) {
			return "";
		}
		String regex = "<[a|A] href=\".*?>(.*?)</[a|A]>";
		java.util.regex.Pattern pattern = null;
		pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group(1);
			str = str.replaceAll("<[a|A] href=\".*?>" + ss + "</[a|A]>", ss);
		}

		regex = "<[p|P] [^>]*?>(.*?)</[p|P]>";
		pattern = java.util.regex.Pattern.compile(regex);
		matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group(1);
			str = str.replaceAll("<[p|P] [^>]*?>" + ss + "</[p|P]>", ss);
		}
		return str;
	}

	/**
	 * �������Ӽ���:target="_blank"
	 * @param str
	 * @return
	 */
	public static String addHrefBlank(String str) {
		if (str == null) {
			return "";
		}
		String regex = "<[a|A] href=\"([^>]*?)>.*?</[a|A]>";
		java.util.regex.Pattern pattern = null;
		pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String ss = matcher.group(1);
			if (ss.indexOf("_blank") == -1) {
				str = str.replaceAll(ss, ss + "  target=\"_blank\"");
			}
		}
		return str;
	}

	//��ȡ26����ĸ
	public static char[] getEnChar() {
		char[] cs = new char[26];
		char c = 'A' - 1;
		for (int i = 0; c++ < 'Z'; i++) {
			cs[i] = c;
		}
		return cs;
	}

	//�ж��Ƿ���26����ĸ����
	public static boolean isInChar(String c) {
		boolean in = false;
		char[] ch = getEnChar();
		for (int i = 0; i < ch.length; i++) {
			if (c.equals(ch[i] + "")) {
				in = true;
				break;
			}
		}
		return in;
	}

	//ת���ɴ�д
	public String toUpperCase(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.toUpperCase();
	}

	//ת���ɴ�д
	public String toLowerCase(String str) {
		if (isBlank(str)) {
			return "";
		}
		return str.toLowerCase();
	}

	/**
	 * ���ݴ�ͼ���Сͼ��ַ
	 * @param imgurl
	 * @return
	 */
	public static String getSmallImg(String imgurl) {
		int len = imgurl.lastIndexOf("/");
		if (len > 1) {
			return imgurl.substring(0, len + 1) + "t_" + imgurl.substring(len + 1, imgurl.length());
		} else {
			return imgurl;
		}
	}

	/**
	 * ȥ��html����
	 * @param html
	 * @return
	 */
	public static String trimHtml(String html) {
		//  	Parser parser = Parser.createParser(html,"GBK");
		//		if(parser!=null){
		//			StringBean sb = new StringBean();
		//			try {
		//				parser.visitAllNodesWith(sb);
		//				html = sb.getStrings();
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//			}			
		//		}
		return html;
	}

	/**
	 * ���ַ����г�ÿ���ַ�
	 * @param str
	 * @return
	 */
	public static char[] toArray(String str) {
		return str.toCharArray();
	}

	/**
	 * ��ʽ���ַ���Ϊ��׼XML��ʽ
	 * @param sourcStr	�޹���Դ�ַ���
	 * @param
	 * @return	��ʽ�����׼XML��ʽ�ַ���
	 */
	public static String formatXML(byte[] sourceByte, String encoding) throws Exception {
		if (sourceByte.length == 0) {
			return null;
		}
		SAXReader reader = new SAXReader();
		ByteArrayInputStream inputStream = null;
		OutputFormat formater = OutputFormat.createPrettyPrint();
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter();
		Document doc;
		try {
			inputStream = new ByteArrayInputStream(sourceByte);
			doc = reader.read(inputStream);
			if (encoding != null) {
				formater.setEncoding(encoding);
			}
			writer = new XMLWriter(out, formater);
			writer.write(doc);
			String temp = out.toString();
			return temp;
		} catch (Exception e) {
			return new String(sourceByte);
		} finally {
			try {
				IOUtils.closeQuietly(inputStream);
				IOUtils.closeQuietly(out);
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * ��ʽ���ַ���Ϊ��׼XML��ʽ
	 * @param sourcStr	�޹���Դ�ַ���
	 * @param
	 * @return	��ʽ�����׼XML��ʽ�ַ���
	 */
	public static String formatXML(String sourcStr, String encoding) throws Exception {
		if (sourcStr == null) {
			return sourcStr;
		}
		sourcStr = sourcStr.substring(sourcStr.indexOf('<'));
		SAXReader reader = new SAXReader();
		StringReader in = new StringReader(sourcStr);
		OutputFormat formater = OutputFormat.createPrettyPrint();
		StringWriter out = new StringWriter();
		XMLWriter writer = new XMLWriter();
		Document doc;
		try {
			doc = reader.read(in);
			if (encoding != null) {
				formater.setEncoding(encoding);
			}
			writer = new XMLWriter(out, formater);
			writer.write(doc);
			String temp = out.toString();
			return temp;
		} catch (Exception e) {
			return sourcStr;
		} finally {
			try {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
