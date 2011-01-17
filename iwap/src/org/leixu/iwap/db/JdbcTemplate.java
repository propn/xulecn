package org.leixu.iwap.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class JdbcTemplate {

	private Connection conn = null;

	private JdbcTemplate(Class clz) {
		try {
			conn = DbCtx.getConn(clz.getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}