package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
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
