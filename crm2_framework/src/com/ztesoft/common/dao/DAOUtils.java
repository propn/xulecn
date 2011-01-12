package com.ztesoft.common.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;

public class DAOUtils {
	private static int WAIT_SECONDS = 200;

	private static String mainSql = "";

	private static LinkedHashMap whereCond = new LinkedHashMap();

	private static HashMap manualVoType = new HashMap();

	private static HashMap manualVoName = new HashMap();

	private static SqlMapExe sqlMapExe = SqlMapExe.getInstance();// SQL��ѯ����

	private DAOUtils() {
	}

	/**
	 * ��ǰϵͳʹ�õ����ݿ�����
	 * 
	 * @return
	 */
	public static String getDatabaseType() {
		return CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
	}

	public static void systempInit() {
		// �ж��Ƿ��Ѿ���ʼ��ϵͳ
		if (!GlobalNames.CONFIG_LOADED) {
			try {
				// ���ò�������
				CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
				SysSet.initSystem(3);// web + app
			} catch (FrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param :source
	 *            ����Դ����
	 */
	public static Connection getDBConnection(String source) throws DAOSystemException {
		throw new DAOSystemException("����ֱ��ͨ���˷�ʽ��ȡ���ݿ�����!");
	}

	/**
	 * @param :source
	 *            ����Դ����, callerObj �����߾��
	 */
	public static Connection getDBConnection(String source, Object callerObj) throws DAOSystemException {
		throw new DAOSystemException("����ֱ��ͨ���˷�ʽ��ȡ���ݿ�����!");

	}

	/**
	 * Specify informixLockModeWait if you prefer to wait for locks for the specified amount of time before an exception
	 * is thrown.
	 * 
	 * @param seconds
	 *            The number of seconds to wait. If seconds is == 0 the waiting is disabled. If seconds is less than 0
	 *            then wait indefinitly.
	 * @exception java.sql.SQLException
	 *                If a database access error occurs.
	 */
	public synchronized static void setLockModeToWait(int seconds, Connection conn) throws SQLException {
		String sql = "";
		if (seconds > 0) {
			sql = "SET LOCK MODE TO WAIT " + seconds;
		} else if (seconds == 0) {
			sql = "SET LOCK MODE TO NOT WAIT";
		} else {
			sql = "SET LOCK MODE TO WAIT";
		}

		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		stmt.close();
	}

	/**
	 * ֱ��ʹ��JDBC�ķ�ʽ��ȡ��ǰ���ݿ������.
	 * 
	 * @return
	 */
	public static Connection getDirectConnection(String dbName) throws DAOSystemException {
		throw new DAOSystemException("����ֱ��ͨ���˷�ʽ��ȡ���ݿ�����!");
	}

	/**
	 * @param :dbConnection
	 *            ��Ҫ�رյ����ݿ�����
	 */
	public static void closeConnection(Connection dbConnection) throws DAOSystemException {
		// throw new DAOSystemException("����ֱ��ͨ���˷�ʽ�ر����ݿ�����!") ;
	}

	/**
	 * @param :dbConnection
	 *            ��Ҫ�رյ����ݿ�����, callerObj �����߾��
	 */
	public static void closeConnection(Connection dbConnection, Object callerObj) throws DAOSystemException {
		/*
		 * try { if(dbConnection != null && !dbConnection.isClosed()){
		 * if(dbConnection.getTransactionIsolation()==dbConnection.TRANSACTION_READ_UNCOMMITTED)
		 * dbConnection.setTransactionIsolation(dbConnection.TRANSACTION_READ_COMMITTED); dbConnection.close(); } }
		 * catch (SQLException se) { throw new DAOSystemException("SQL Exception while closing " + "DB connection : \n" +
		 * se); }
		 */
	}

	/**
	 * @param :result
	 *            ��Ҫ�رյĽ����
	 */
	public static void closeResultSet(ResultSet result) throws DAOSystemException {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing " + "Result Set : \n" + se);
		}
	}



	/**
	 * @param :result
	 *            ��Ҫ�رյĽ����, callerObj �����߾��
	 */
	public static void closeResultSet(ResultSet result, Object callerObj) throws DAOSystemException {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing " + "Result Set : \n" + se);
		}
	}

	/**
	 * @param :stmt
	 *            ��Ҫ�رյĳ���
	 */
	public static void closeStatement(PreparedStatement stmt) throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing " + "Statement : \n" + se);
		}
	}

	/**
	 * @param :stmt
	 *            ��Ҫ�رյĳ���, callerObj �����߾��
	 */
	public static void closeStatement(PreparedStatement stmt, Object callerObj) throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing " + "Statement : \n" + se);
		}
	}

	/**
	 * 
	 * @param stmt
	 * @throws DAOSystemException
	 */
	public static void closeStatement(Statement stmt) throws DAOSystemException {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while closing " + "Statement : \n" + se);
		}
	}

	/**
	 * ��������ã�����һ��������Զ�ȡ��һ��������δ�ύ���޸ġ�
	 * 
	 * @param conn
	 * @throws DAOSystemException
	 */
	public static void openDirtyRead(Connection conn) throws DAOSystemException {
		try {
			if (conn != null) {
				if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_INFORMIX)) {
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				} else if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_ORACLE)) {
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
					/*
					 * String sql = "SET TRANSACTION READ ONLY"; Statement stmt = null; try { stmt =
					 * conn.createStatement(); stmt.execute(sql); } finally { if (stmt != null) stmt.close(); }
					 */
				}
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQL Exception while openDirtyRead : " + se);
		}
	}

	/**
	 * �ر�������ã������ύ����״̬�� һ�������ȡ��һ������ȫ���ύ���޸ġ�
	 * 
	 * @param conn
	 * @throws DAOSystemException
	 */
	public static void closeDirtyRead(Connection conn) throws DAOSystemException {
		throw new DAOSystemException("����ֱ��ͨ���˷�ʽ�ر����ݿ�����!");
		// try {
		// if (conn != null) {
		// if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_INFORMIX)) {
		// conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		// } else if (DAOUtils.getDatabaseType().equals(CrmConstants.DB_TYPE_ORACLE)) {
		// //conn.commit();
		// conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		// }
		// }
		// } catch (SQLException se) {
		// throw new DAOSystemException("SQL Exception while openDirtyRead : "
		// + se);
		// }
	}

	/**
	 * ��ȡ��ǰ��ʱ�䡣��ǰʵ�ִ�Ӧ�÷�����ȡʱ�䡣 ���Ҫͳһȡ���ݿ�ʱ�䣬Ҳ��������ͳһ��ȡ��
	 * 
	 * @return
	 */
	public static java.sql.Date getCurrentDate() {

		return new java.sql.Date(System.currentTimeMillis());

	}

	/**
	 * ��ȡ��ǰ��ʱ�䡣��ǰʵ�ִ�Ӧ�÷�����ȡʱ�䡣 ���Ҫͳһȡ���ݿ�ʱ�䣬Ҳ��������ͳһ��ȡ��
	 * 
	 * @return
	 */
	public static java.sql.Timestamp getCurrentTimestamp() {

		return new java.sql.Timestamp(System.currentTimeMillis());

	}

	/**
	 * ��ȡ��ǰ��ʱ�䡣��ǰʵ�ִ�Ӧ�÷�����ȡʱ�䡣 ����ͨ��ͳһ�ĸ�ʽ����
	 * 
	 * @return
	 */
	public static String getFormatedDate() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(new java.sql.Date(System.currentTimeMillis()));

	}

	/**
	 * ��ȡ��ǰ�����ݿ�ʱ�䡣 ����ͨ��ͳһ�ĸ�ʽ����
	 * 
	 * @author RyoUehara ���ʱ��:090504
	 * @return
	 */
	public static String getFormatedDbDate() throws Exception {
		// modify by RyoUehara 090504 itime��Ϊȡ���ݿ��sysdate
		// ComDAO commandDAO=(ComDAO)ComServiceDAO.getInstance().getComDAO("");
		mainSql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual";
		return sqlMapExe.querySingleValue(mainSql);

	}

	/**
	 * ��ȡ��ǰ��ʱ�䡣��ǰʵ�ִ�Ӧ�÷�����ȡʱ�䡣 ����ͨ��ͳһ�ĸ�ʽ����
	 * 
	 * @return
	 */
	public static String getShortFormatedDate() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);
		return dateFormator.format(new java.sql.Date(System.currentTimeMillis()));

	}

	/**
	 * ��ȡĬ�ϵ�ʧЧ��ʱ��
	 * 
	 * @return
	 */
	public static java.sql.Date getDefaultExpiredDate() {

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		java.util.Date tDate = dateFormator.parse(CrmConstants.DEFAULT_EXPIRED_DATE, new ParsePosition(0));
		return new java.sql.Date(tDate.getTime());

	}

	/**
	 * ��ȡĬ�ϵ�ʧЧ��ʱ�� ����ͨ��ͳһ�ĸ�ʽ����
	 * 
	 * @return
	 */
	public static String getFormatedExpiredDate() {

		return CrmConstants.DEFAULT_EXPIRED_DATE;

	}

	/**
	 * ͨ��ͳһ�ĸ�ʽ���ı�ת����Date������Ϊ���ں�ʱ�䡣
	 * 
	 * @return
	 */
	public static Date parseDateTime(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;

		// ֻ����������
		if (sdate.length() <= 10) {
			return parseDate(sdate);
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);

		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null)
			return null;

		return new java.sql.Date(tDate.getTime());

	}

	/**
	 * ͨ��ͳһ�ĸ�ʽ���ı�ת����Date������Ϊ���ں�ʱ�䡣
	 * 
	 * @return
	 */
	public static Date parseDateTime(Object sdate) {
		if (null == sdate)
			return null;
		return parseDateTime((String) sdate);
	}

	public static Timestamp parseTimestamp(Object sdate) {
		if (null == sdate)
			return null;
		return parseTimestamp((String) sdate);
	}

	/**
	 * ͨ��ͳһ�ĸ�ʽ���ı�ת����Timestamp������Ϊ���ں�ʱ�䡣
	 * 
	 * @return
	 */
	public static Timestamp parseTimestamp(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;

		java.util.Date tDate = null;

		// ֻ����������
		if (sdate.length() <= 10) {
			SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		} else {

			SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);

			tDate = dateFormator.parse(sdate, new ParsePosition(0));
		}

		if (tDate == null)
			return null;

		return new java.sql.Timestamp(tDate.getTime());

	}

	/**
	 * ͨ��ͳһ�ĸ�ʽ���ı�ת����Date������Ϊ���ڡ�
	 * 
	 * @return
	 */
	public static Date parseDate(String sdate) {
		if (null == sdate || "".equals(sdate))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null)
			return null;

		return new java.sql.Date(tDate.getTime());
	}

	/**
	 * ����CLOBת��STRING����
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String ClobToString(Clob clob) {
		if (clob == null)
			return null;
		String reString = "";
		Reader is;
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			is = clob.getCharacterStream();
			// �õ���
			br = new BufferedReader(is);
			String s = br.readLine();

			while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING
				sb.append(s);
				s = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * ��Dateת����ͳһ�����ڸ�ʽ�ı���
	 * 
	 * @return
	 */
	public static String getFormatedDate(Date date) {
		if (null == date)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);
		return dateFormator.format(new java.sql.Date(date.getTime()));
	}

	/**
	 * ��Dateת����ͳһ������ʱ���ʽ�ı���
	 * 
	 * @return
	 */
	public static String getFormatedDateTime(Date date) {
		if (null == date)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(new java.sql.Date(date.getTime()));
	}

	/**
	 * ��Dateת����ͳһ������ʱ���ʽ�ı���
	 * 
	 * @return
	 */
	public static String getFormatedDateTime(Timestamp stamp) {
		if (null == stamp)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(new java.sql.Date(stamp.getTime()));
	}

	/**
	 * ��Dateת����ͳһ�����ڸ�ʽ�ı��� ��ʽ��yyyy-mm-dd
	 * 
	 * @return
	 * @author suns
	 */
	public static String getFormatedDate(Timestamp stamp) {
		if (null == stamp)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);
		return dateFormator.format(new java.sql.Date(stamp.getTime()));
	}

	/**
	 * �ṩɾ���ַ���ǰ��Ŀո�Ĺ���
	 * 
	 * @return
	 */
	public static String trimStr(String str) {

		if (null == str)
			return "";
		else
			return str.trim();

	}

	/**
	 * �û�����Ĳ�ѯ�����У�ת�塢����������ַ��� informix�µ�ת���Ϊ"\" ��Ҫת��ķ��ţ� ' - % \
	 * 
	 * @param condStr
	 * @return
	 */
	public static String filterQureyLikeCond(String condStr) {
		if (condStr == null || "".equals(condStr))
			return "";
		condStr = Pattern.compile("(\\')").matcher(condStr).replaceAll("''");
		// condStr = Pattern.compile("(\\\\)").matcher(condStr).replaceAll(
		// "\\\\\\\\");
		// condStr = Pattern.compile("(\\-)").matcher(condStr).replaceAll("\\\\-");
		// condStr = Pattern.compile("(\\%)").matcher(condStr).replaceAll("\\\\%");
		return condStr;
	}

	/**
	 * �û�����Ĳ�ѯ�����У�ת�塢����������ַ��� ��Ҫת��ķ��ţ� '
	 * 
	 * @param condStr
	 * @return
	 */
	public static String filterQureyCond(String condStr) {
		if (condStr == null || "".equals(condStr))
			return "";
		condStr = Pattern.compile("(\\')").matcher(condStr).replaceAll("''");

		return condStr;
	}

	/**
	 * ˵������ȡϵͳ��������������������õ�ֵ
	 * 
	 * @param paramCode
	 * @return
	 */
	public static String getSysParamValue(String paramCode) {

		if ("".equals(paramCode.trim())) {
			throw new DAOSystemException("ȡϵͳ��������:�����paramCodeΪ��!");
		}
		String GET_PARAM_VALUE = "SELECT param_val FROM dc_system_param " + " WHERE param_code='" + paramCode + "'";

		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String paramVal = "";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(GET_PARAM_VALUE));
			result = stmt.executeQuery();
			if (result.next()) {
				paramVal = result.getString("param_val");
			} else {
				throw new DAOSystemException("ȡϵͳ��������:�����ڸò���:param_code:" + paramCode);
			}
			return paramVal;
		} catch (SQLException se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execute " + GET_PARAM_VALUE + ":\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result);
			DAOUtils.closeStatement(stmt);
		}
	}

	/**
	 * ��ȡ���ݿ�ʱ��
	 * 
	 * @return
	 */
	public static String getDBCurrentTime() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String selStr = " select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') dbTime from dual ";
			stmt = conn.prepareStatement(selStr);
			result = stmt.executeQuery();
			String dbTime = "";
			if (result.next()) {
				dbTime = result.getString("dbTime");
			}

			return dbTime;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOSystemException(" ��ѯ���ݿ�ĵ�ǰʱ�䱨�� :\n" + e.getMessage(), e);
		} finally {
			DAOUtils.closeResultSet(result);
			DAOUtils.closeStatement(stmt);
			// conn �ɿ���Լ��رա�
		}
	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

}
