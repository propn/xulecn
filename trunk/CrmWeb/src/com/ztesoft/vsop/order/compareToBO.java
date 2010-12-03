package com.ztesoft.vsop.order;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ztesoft.common.dao.PageFilterInformixImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.LegacyDAOUtil;

/**
 * 比对订购关系
 * 在数据库中进行比对，判断订购关系不一致的数据逻辑为在VSOP备份表中没有状态可用并且属主属于当前平台
 * 但是在中间表(对方平台提供比对数据)中有状态可用的；判断退订结果不一致的逻辑为在VSOP备份表中有状态可用并且属主是当前平台，
 * 但是在中间表中没有有状态可用的 处理逻辑在存储过程中实现
 * @author liu.yuming
 *
 */
public class compareToBO {
	
	public void invoProcedure(String cycleType) throws SQLException{
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			
			String sql = "{ call PKG_VSOP_ORDER_COMPARE.P_DEAL_MAIN(?) }"; //oracle调用方式
			
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				 sql = "{ call P_DEAL_MAIN(?) }"; //informix调用方式
			}
			
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, cycleType);
			cstmt.execute();
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(cstmt != null) cstmt.close();
				//if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void invoOdsProcedure(String cycleType) throws SQLException{
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			String sql = "{ call PKG_VSOP_ORDER_COMPARE.P_SAVE_UPATE_DATA_TO_ODS }";//oracle调用方式
			
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				 sql = "{ call P_SAVE_UPATE_DATA_TO_ODS }"; //informix调用方式
			}
			
			
			cstmt = conn.prepareCall(sql);
//			cstmt.setString(1, cycleType);
			cstmt.execute();
		} catch (SQLException e) {
			try {
				if(cstmt != null) cstmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(cstmt != null) cstmt.close();
				//if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void invoSendFileDataProcedure(String cycleType) throws SQLException{
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			String sql = "{ call PKG_VSOP_ORDER_COMPARE.P_CREATE_SEND_FILE_DATA(?) }";//oracle调用方式
			//徐雷
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				 sql = "{ call P_CREATE_SEND_FILE_DATA(?) }"; //informix调用方式
			}
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, cycleType);
			cstmt.execute();
		} catch (SQLException e) {
			try {
				if(cstmt != null) cstmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(cstmt != null) cstmt.close();
				//if(conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
