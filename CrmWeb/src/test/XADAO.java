package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.tracer.Debug;

public class XADAO {
	private String iapp_sql = "insert into i_test(a) values(123)" ;
	private String easonwu_sql = "insert into e_test(a) values('hi')" ;
	
	public boolean iappInsert() throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = iapp_sql;
		try {
			conn = ConnectionContext.getContext().getConnection("Default") ;
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate() ;
			return row > 0 ;
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			 try {
				ConnectionContext.getContext().closeConnection("Default") ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 程序控制事务列子
	 * @return
	 * @throws DAOSystemException
	 */
	public boolean controlTransactionTest() throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = iapp_sql;
		try {
			conn = ConnectionContext.getContext().getConnection("crm1") ;
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate() ;
			return row > 0 ;
		} catch (SQLException se) {
			//异常时，调用ConnectionContext.getContext().rollback(DBName) ;回滚
			try {
				ConnectionContext.getContext().rollback("crm1") ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//最后关闭数据库连接，调用ConnectionContext.getContext().closeConnection(DBName) ;
			try {
				ConnectionContext.getContext().closeConnection("crm1") ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean easonwuInsert() throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = easonwu_sql;
		try {
			conn = ConnectionContext.getContext().getConnection("crm2") ;
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate() ;
			return row > 0 ;
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
		}
	}
}
