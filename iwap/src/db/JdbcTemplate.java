package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class JdbcTemplate {
	
	private Connection conn=null;
	
	private JdbcTemplate(Class clz){
		try {
			conn=DbCtx.getConnection(clz.getSimpleName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List queryList(String sql, Object[] agrs, RowMapper rowMapper)
			throws Exception {

		Statement stmt = DbCtx.getConnection(null).createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM News");

		while (rs.next()) {
			String title = rs.getString("title");
			String content = rs.getString("content");
			System.out.println(title + ": " + content + " ");
		}

		return null;
	}
	

	
}
