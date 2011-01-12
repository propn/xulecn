package com.powerise.ibss.framework;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.ConfigFileHelper;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;

public class LogHelper {
	private static Logger m_Logger = Logger.getLogger(LogHelper.class);

//	/**
//	 * 记录程序错误日志
//	 *
//	 * @param sname
//	 *            服务名
//	 * @param ecode
//	 *            错误号
//	 * @param emsg
//	 *            错误信息
//	 * @param eexp
//	 *            异常信息
//	 */
//	public static long writeLog(String sname, int ecode, String emsg,
//			String eexp) {
//		Connection conn = null; // 写日志文件数据库连接
//		PreparedStatement st = null;
//		Statement stSeq = null;
////		HashMap hmDB = null;
////		String strDBProd = null;
////		Object obj = null;
//		String error_log = null;
//		long lSeq = 0;
//		try {
//			if (GlobalNames.ERR_TO_DB) {
////				HashMap insert = new HashMap();
//				if (emsg == null)
//					emsg = "";
//				if (eexp == null)
//					eexp = "";
//				error_log = "error message:" + emsg + "\nException:\n" + eexp;
//				if (error_log.length() > 1500) {
//					error_log = error_log.substring(error_log.length()-1500);
//				}
//				m_Logger.debug("start write log(" + ecode + "):" + error_log);
//				conn = ConnectionContext.getContext().getConnection(JNDINames.LOG_DATASOURCE);
//				try {
//					stSeq = conn.createStatement();
//					String dbType = CrmParamsConfig.getInstance()
//							.getParamValue("DBType");
//
//					ResultSet rs;
//					if ("SqlServer".equals(dbType)) {
//						rs = stSeq
//								.executeQuery("select max(error_seq)+1 from tfm_error_log");
//					} else {
//						rs = stSeq
//								.executeQuery("select tfm_err_log_seq.nextval from dual");
//					}
//					if (rs.next())
//						lSeq = rs.getLong(1);
//					rs.close();
//					st = conn
//							.prepareStatement("insert into tfm_error_log(error_seq,occur_time,service_name,error_code,error_log) values(?,?,?,?,?)");
//					st.setLong(1, lSeq);
//					st.setTimestamp(2,
//							new Timestamp(System.currentTimeMillis()));
//					st.setString(3, sname);
//					st.setString(4, String.valueOf(ecode));
//					st.setString(5, error_log);
//					st.execute();
//					ConnectionContext.getContext().commit(JNDINames.LOG_DATASOURCE);
//					m_Logger.debug("write log(" + ecode + ")complete");
//				} catch (Exception e1) {
//					e1.printStackTrace() ;
//					m_Logger.warn("wirte log fail:" + error_log);
//					m_Logger.warn(getStackMsg(e1));
//					ConnectionContext.getContext().rollback(JNDINames.LOG_DATASOURCE);
//				}
//			}
//		} catch (Exception e) {
//			m_Logger.warn("wirte log fail:" + eexp);
//			m_Logger.warn(getStackMsg(e));
//		} finally {
//			try {
//				DAOUtils.closeStatement(stSeq) ;
//				DAOUtils.closeStatement(st) ;
//					ConnectionContext.getContext().closeConnection(JNDINames.LOG_DATASOURCE);
//			} catch (SQLException sqle) {
//				System.out.println("Exception occus when close connection of log writer:");
//				sqle.printStackTrace();
//			}
//		}
//		return lSeq;
//	}

	/***************************************************************************
	 * 
	 * PART-A-6 其他方法
	 * 
	 * 
	 **************************************************************************/
	public static void log(String s) {
		// if(1==1) return;
		log(null, s);
	}

	public static void log(String file, String s) {
		FileWriter fw = null;
		try {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.format(new java.util.Date());

			if (file == null) {
				System.out.print("\n" + time + " " + s);
			} else {
				String sep = System.getProperty("file.separator");
				String filename = SysSet.getHomeDirectory() + sep + "log" + sep
						+ file + ".log";
				fw = new FileWriter(filename, true);
				fw.write("\n" + time + " " + s);
			}
		} catch (Throwable e) {
		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (Throwable e) {
				}
		}
	}

	/**
	 * 获取异常的堆栈信息，便于调试
	 *
	 * @param e
	 * @return String
	 */
	public static String getStackMsg(Throwable e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		String strMsg = "";
		sw = new StringWriter();
		pw = new PrintWriter(sw);
		try {
			e.printStackTrace(pw);
			sw.flush();
			strMsg = sw.toString();
			sw.close();
			pw.close();
		} catch (java.io.IOException le) {
			strMsg = "待定处理";
		}
		return strMsg;
	}

	 /**
	   * 获取异常的堆栈信息,便于调试
	   *
	   * @param e
	   * @return
	   */
	  public static String getStackMsg(Exception e) {
	    return getStackMsg( (Throwable) e);
	  }
	  
	  public static void initLog4J() throws IOException{
		  try {
			PropertyConfigurator.configure(ConfigFileHelper.getLog4JConfigFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  }

