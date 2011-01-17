package org.leixu.iwap.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.leixu.iwap.config.ParamsUtils;

import com.jolbox.bonecp.BoneCPDataSource;

public class BoneCpUtils {

	/**
	 * 
	 * @param dataSourceName
	 * @throws Exception
	 */
	public static DataSource getDataSource(String dataSourceName)
			throws Exception {

		BoneCPDataSource dataSource = new BoneCPDataSource();

		Class.forName(ParamsUtils.getParamValue("driver"));
		dataSource.setDriverClass(ParamsUtils.getParamValue("driver"));

		dataSource.setJdbcUrl(ParamsUtils.getParamValue("url"));
		dataSource.setUsername(ParamsUtils.getParamValue("username"));
		dataSource.setPassword(ParamsUtils.getParamValue("password"));

		dataSource.setMaxConnectionsPerPartition(Integer.parseInt(ParamsUtils
				.getParamValue("maxConnections")));
		dataSource.setMinConnectionsPerPartition(Integer.parseInt(ParamsUtils
				.getParamValue("minConnections")));

		return dataSource;
	}

	public static void main(String[] args) {
		try {
			Connection connection;
			connection = ConnUtils.getConnection();

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM News");
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				System.out.println(title + ": " + content + " ");
			}
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
