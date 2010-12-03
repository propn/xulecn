package com.ztesoft.common.util;


public final class GB2Alpha {

	private static char[] alphatable = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
										 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
										 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static int[] table = { 45217, 45253, 45761, 46318, 46826, 47010, 47297, 47614, 47614, 48119, 49062, 49324,
			49896, 50371, 50614, 50622, 50906, 51387, 51446, 52218, 52218, 52218, 52698, 52980, 53689, 54481, 55289 };

	public GB2Alpha() {

	}

	// ������,�����ַ�,�õ�������ĸ,
	// Ӣ����ĸ���ض�Ӧ�Ĵ�д��ĸ
	// �����Ǽ��庺�ַ��� '0'

	public static char Char2Alpha(char ch) {

		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;

		int gb = gbValue(ch);
		if (gb < table[0])
			return '0';

		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}

		if (i >= 26)
			return '0';
		else
			return alphatable[i];
	}

	// ����һ���������ֵ��ַ�������һ������ƴ������ĸ���ַ���
	public static String String2Alpha(String SourceStr) {

		String Result = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				Result += Char2Alpha(SourceStr.charAt(i));
			}
		} catch (Exception e) {
			Result = "";
		}
		return Result;
	}

	private static boolean match(int i, int gb) {
		if (gb < table[i])
			return false;

		int j = i + 1;

		// ��ĸZʹ����������ǩ
		while (j < 26 && (table[j] == table[i]))
			++j;

		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];

	}

	// ȡ�����ֵı���
	private static int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}

	}
}
