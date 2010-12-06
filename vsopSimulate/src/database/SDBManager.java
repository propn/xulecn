/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.Config;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author caozj  cao.zhijun3@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SDBManager {

	private static Connection conn = null;
	private String url = "jdbc:informix-sqli://192.168.1.128:8888/test11:informixServer=crmdev;NEWLOACLE=en_us,zh_cn,zh_tw;NEWCODESET=GB2312-80,8859-1,819,Big5;IFX_LOCK_MODE_WAIT=20";
	private String driver = "com.informix.jdbc.IfxDriver";
	private String user = "informix";
	private String password = "informix";

	/**
	 * 初始化连接
	 *
	 */
	public void init() {
		if (Config.getConfig("DB_RUL") != null && !"".equals(Config.getConfig("DB_RUL"))) {
			url = Config.getConfig("DB_RUL");
		}
		if (Config.getConfig("DB_DRIVER") != null && !"".equals(Config.getConfig("DB_DRIVER"))) {
			driver = Config.getConfig("DB_DRIVER");
		}
		if (Config.getConfig("DB_USER") != null && !"".equals(Config.getConfig("DB_USER"))) {
			user = Config.getConfig("DB_USER");
		}
		if (Config.getConfig("DB_PASSWORD") != null && !"".equals(Config.getConfig("DB_PASSWORD"))) {
			password = Config.getConfig("DB_PASSWORD");
		}
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行SQL语句
	 * @param SQL
	 * @throws Exception
	 */
	public void excuSQL(String SQL) throws Exception {
		if (conn == null) {
			this.init();
		}
		Statement st = null;
		try {
			st = conn.createStatement();
			st.execute(SQL);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			st.close();
		}
	}

	public static void main(String[] args) {
		String sql = "insert into  test  values('12','nisdjfkd')";
		SDBManager sm = new SDBManager();
		try {
			sm.excuSQL(sql);
			sm.excuSQL("update test set desc='测试' where id='12'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
