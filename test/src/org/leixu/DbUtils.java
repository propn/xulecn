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
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
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
			System.out.println("��������h2...");
			server = Server.createTcpServer(new String[] { "-tcpPort", port }).start();
		} catch (SQLException e) {
			System.out.println("����h2����" + e.toString());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void stopServer() {
		if (server != null) {
			System.out.println("���ڹر�h2...");
			server.stop();
			System.out.println("�رճɹ�.");
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
			stat.execute("insert into simulate_result values('0','�ɹ���)");

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
