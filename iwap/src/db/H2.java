/**
 * 
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

public class H2 {
	private static Server server;
	private static final String port = "9094";
	private static final String dbDir = "./db/db";
	private static final String user = "sa";
	private static final String password = "sa";

	public static void startServer() {
		try {
			System.out.println("正在启动h2...");
			server = Server.createTcpServer(new String[] { "-tcpPort", port })
					.start();
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
		H2.startServer();
		
		try {
			Class.forName("org.h2.Driver");
			Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir,
					user, password);
			Statement stat = conn.createStatement();
			stat.execute("  drop   table   News");
			stat
					.execute("  create   table   News( title varchar(10),content  varchar(255))");
			stat
					.execute("  insert into News(title,content) values('adfafs','asdffaasdfa' )");
			ResultSet result = stat.executeQuery("select * from News ");
			int i = 1;
			while (result.next()) {
				System.out.println(i++ + ":" + result.getString("title"));
			}
			result.close();
			stat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		H2.stopServer();
		
		System.out.println("==END==");
	}

}
