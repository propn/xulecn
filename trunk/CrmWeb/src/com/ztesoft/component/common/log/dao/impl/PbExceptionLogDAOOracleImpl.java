/**
 * 
 */
package com.ztesoft.component.common.log.dao.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.OracleBlobForWeblogic;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.log.vo.PbExceptionLogVO;

/**
 * @author Administrator
 *
 */
public class PbExceptionLogDAOOracleImpl extends PbExceptionLogDAOImpl{
	protected void populateDto(PbExceptionLogVO vo, ResultSet rs) throws SQLException {
		vo.setLogId( DAOUtils.trimStr( rs.getString( "log_id" ) ) );
		vo.setExceptionCode( DAOUtils.trimStr( rs.getString( "exception_code" ) ) );
		vo.setExceptionReason( DAOUtils.trimStr( rs.getString( "exception_reason" ) ) );
		vo.setExceptionProject( DAOUtils.trimStr( rs.getString( "exception_project" ) ) );

		Blob blob = rs.getBlob("stack");
		byte[] b = blob.getBytes(1, (int) blob.length());
		String str = new String( b ) ;
		vo.setStack( DAOUtils.trimStr(str));
			
		vo.setClassName( DAOUtils.trimStr( rs.getString( "class_name" ) ) );
		vo.setMethName( DAOUtils.trimStr( rs.getString( "meth_name" ) ) );
	}
	
	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		String insertSQL = "INSERT INTO PB_EXCEPTION_LOG ( LOG_ID,EXCEPTION_CODE,EXCEPTION_REASON,EXCEPTION_PROJECT,STACK,CLASS_NAME,METH_NAME ) " +
										"VALUES ( ?,?,?,?,EMPTY_BLOB(),?,? )";
		try {
			
			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(insertSQL) );
			
			//String seqId = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence(vo.getTableName(), "LOG_ID");
			//((PbExceptionLogVO)vo).setLogId(seqId);
			int index = 1;
			if ("".equals(((PbExceptionLogVO)vo).getLogId())) {
				((PbExceptionLogVO)vo).setLogId(null);
			}
			
			stmt.setString( index++, ((PbExceptionLogVO)vo).getLogId() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionCode() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionReason() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getExceptionProject() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getClassName() );
			stmt.setString( index++, ((PbExceptionLogVO)vo).getMethName() );
			
			stmt.executeUpdate();
			
			String sqlStr = "SELECT STACK FROM PB_EXCEPTION_LOG WHERE LOG_ID = ? FOR UPDATE";
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sqlStr));
			
			//stmt.setString(1, seqId); 
			stmt.setString(1, ((PbExceptionLogVO)vo).getLogId());
			ResultSet rs = stmt.executeQuery();

			byte data[] = ((PbExceptionLogVO)vo).getStack().getBytes();
			if (rs.next()) {
				OracleBlobForWeblogic.setOracleBlob(rs, 1, data);
			}
		}
		catch (SQLException se) {
			Debug.print(insertSQL,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+insertSQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}
}
