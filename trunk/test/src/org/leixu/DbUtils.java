/**
 * 
 */
package org.leixu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DbUtils {
	private static Server server;
	private static final String port = "9094";
	private static final String dbDir = "./db/db";
	private static final String user = "vsop";
	private static final String password = "vsop";

/*	public static void startServer() {
		try {
			System.out.println("正在启动h2...");
			server = Server.createTcpServer(new String[] { "-tcpPort", port }).start();
		} catch (SQLException e) {
			System.out.println("启动h2出错：" + e.toString());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void stopServer() {
		if (server != null) {
			System.out.println("正在关闭h2...");
			server.stop();
			System.out.println("关闭成功.");
		}
	}

	public static Connection getConn() throws Exception {
		return DriverManager.getConnection("jdbc:h2:" + dbDir, user, password);
	}

	public static void main(String[] args) {
		//		DbUtils h2 = new DbUtils();
		DbUtils.startServer();
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir, user, password);
			Statement stat = conn.createStatement();
			stat.execute("  create   table   simulate_result(resultCode varchar(10),resultDesc  varchar(255))");
			stat.execute("insert into simulate_result values('0','成功‘)");

			ResultSet result = stat.executeQuery("select name from test ");
			int i = 1;
			while (result.next()) {
				System.out.println(i++ + ":" + result.getString("name"));
			}
			result.close();
			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DbUtils.stopServer();
		System.out.println("==END==");
	}*/

}
