package org.leixu.iwap.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.leixu.iwap.config.ParamsUtils;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class BoneCpUtils {
	private static BoneCP connectionPool = null;

	public static void main(String[] args) {
		try {
			initBoneCP();

			Connection connection;
			connection = connectionPool.getConnection();

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News");
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				System.out.println(title + ": " + content + " ");
			}
			connection.close();

			distroy();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void initBoneCP() throws Exception {

		BoneCPConfig config = new BoneCPConfig();

		Class.forName(ParamsUtils.getParamValue("driver"));
		config.setJdbcUrl(ParamsUtils.getParamValue("url"));
		config.setUsername(ParamsUtils.getParamValue("username"));
		config.setPassword(ParamsUtils.getParamValue("password"));

		config.setMaxConnectionsPerPartition(Integer.parseInt(ParamsUtils
				.getParamValue("maxConnections")));
		config.setMinConnectionsPerPartition(Integer.parseInt(ParamsUtils
				.getParamValue("minConnections")));

		connectionPool = new BoneCP(config);

	}

	public static Connection getConn(String dataSourceName) throws Exception {
		return connectionPool.getConnection();
	}

	public static void distroy() {
		connectionPool.shutdown();
	}
}
