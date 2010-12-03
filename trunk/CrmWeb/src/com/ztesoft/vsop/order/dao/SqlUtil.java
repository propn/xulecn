package com.ztesoft.vsop.order.dao;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.JNDINames;

public class SqlUtil {
	private static Logger logger = Logger.getLogger(SqlUtil.class);
	private static String dbName = JNDINames.DEFAULT_DATASOURCE;
	private static SqlUtil sqlUtil = null;
	private SqlUtil(){}
	public static SqlUtil getInstance(){
		if(sqlUtil == null)sqlUtil = new SqlUtil();
		return new SqlUtil();
	}
	public String getSequence(String sequenceName) throws FrameException {
		logger.info("SqlUtil.getSequence start");
		String querySQL = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
		logger.info("SQL:"+querySQL);
		long result = Base.query_long(dbName, querySQL, new String[]{}, 1);
		logger.info("SqlUtil.getSequence end");
		return String.valueOf(result);
	}
}
