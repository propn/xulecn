package com.ztesoft.vsop.order;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ztesoft.common.dao.PageFilterInformixImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.LegacyDAOUtil;

/**
 * �ȶԶ�����ϵ
 * �����ݿ��н��бȶԣ��ж϶�����ϵ��һ�µ������߼�Ϊ��VSOP���ݱ���û��״̬���ò����������ڵ�ǰƽ̨
 * �������м��(�Է�ƽ̨�ṩ�ȶ�����)����״̬���õģ��ж��˶������һ�µ��߼�Ϊ��VSOP���ݱ�����״̬���ò��������ǵ�ǰƽ̨��
 * �������м����û����״̬���õ� �����߼��ڴ洢������ʵ��
 * @author liu.yuming
 *
 */
public class compareToBO {
	
	public void invoProcedure(String cycleType) throws SQLException{
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = LegacyDAOUtil.getConnection();
			
			String sql = "{ call PKG_VSOP_ORDER_COMPARE.P_DEAL_MAIN(?) }"; //oracle���÷�ʽ
			
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				 sql = "{ call P_DEAL_MAIN(?) }"; //informix���÷�ʽ
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
			String sql = "{ call PKG_VSOP_ORDER_COMPARE.P_SAVE_UPATE_DATA_TO_ODS }";//oracle���÷�ʽ
			
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				 sql = "{ call P_SAVE_UPATE_DATA_TO_ODS }"; //informix���÷�ʽ
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
			String sql = "{ call PKG_VSOP_ORDER_COMPARE.P_CREATE_SEND_FILE_DATA(?) }";//oracle���÷�ʽ
			//����
			if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
				 sql = "{ call P_CREATE_SEND_FILE_DATA(?) }"; //informix���÷�ʽ
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
