package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class DateUtil {

	/**
	 * 
	 * @return
	 */
	public static String getDateStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String str = df.format(now);

		return str;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getDateStr());
	}

}
