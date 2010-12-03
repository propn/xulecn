package fortest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.tracer.Debug;

public class LogControl {
	private static String insertSQL = "INSERT INTO iLog(Log_Id,Log_Msg,Cost_Time) values(SEQ_iLog.Nextval,?,?)" ;
	/*
	 
	 create sequence SEQ_iLog
		minvalue 1
		maxvalue 99999999999
		start with 1
		increment by 1
		cache 20;
	 create table iLog(
	 	log_id number not null ,
	 	log_msg varchar2(2000) not null,
	 	cost_time number not null ,
	 	primary key(log_id)
	 ) ;
	 
	 */
	public static final String XA_TYPE="XA" ;
	public static final String NXA_TYPE="NXA" ;
	
	public static void log(String msg ,long costTime){
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionContext.getContext().getConnection() ;
			stmt = conn.prepareStatement(insertSQL);
			
			
			stmt.setString(1,msg);
			stmt.setLong(2, costTime);
			
			int rows = stmt.executeUpdate();
			ConnectionContext.getContext().commit(null);
		} catch (SQLException se) {
			try {
				ConnectionContext.getContext().rollback(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Debug.print(insertSQL);
			throw new DAOSystemException("SQLException while insert sql:\n"
					+ insertSQL, se);
		} finally {
			DAOUtils.closeStatement(stmt);
			try {
				ConnectionContext.getContext().closeConnection(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//DAOUtils.closeConnection(conn, this);
		}
	}

}
