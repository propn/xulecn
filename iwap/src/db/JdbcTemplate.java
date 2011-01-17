package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class JdbcTemplate {

	private Connection conn = null;

	private JdbcTemplate(Class clz) {
		try {
			conn = DbCtx.getConnection(clz.getSimpleName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
