package com.ztesoft.vsop.engine.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.vsop.engine.vo.InterfaceLogVO;
/**
 * 接口日志类
 * @author cooltan
 *
 */
public class InterfaceLogDao {
	private String SEQUENCECODE = "SEQ_INTERFACE_LOG_ID";
	private int maxRows;

	private String SQL_INSERT = "INSERT INTO INTERFACE_LOG ( LOG_ID,SRC_SYS_ID,INTERFACE_ID,ACC_NBR,NBR_TYPE,LAN_CODE,SERVICE_ID,START_TIME,END_TIME,PROCESS_TIME,TRANSACTION_ID,RESULT,RESULT_DESC,PARTITION_ID ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
	
	/**
	 * 接口库表日志功能；
	 * @param vo
	 * @throws DAOSystemException
	 */
	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String seq = aSequenceManageDAOImpl.getNextSequence(SEQUENCECODE);
			((InterfaceLogVO)vo).setLogId(seq);
			if ("".equals(((InterfaceLogVO)vo).getLogId())) {
				((InterfaceLogVO)vo).setLogId(null);
			}
			stmt.setString( index++, ((InterfaceLogVO)vo).getLogId() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getSrcSysId() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getInterfaceId() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getAccNbr() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getNbrType() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getLanCode() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getServId() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getStartTime() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getEndTime() );
			if ("".equals(((InterfaceLogVO)vo).getProcessTime())) {
				((InterfaceLogVO)vo).setProcessTime(null);
			}
			stmt.setString( index++, ((InterfaceLogVO)vo).getProcessTime() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getTransactionId() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getResult() );
			stmt.setString( index++, ((InterfaceLogVO)vo).getResultDesc() );
			stmt.setString( index++, String.valueOf(DAOUtils.getCurrentMonth()) );
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}
}
