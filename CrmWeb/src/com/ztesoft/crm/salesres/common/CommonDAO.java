package com.ztesoft.crm.salesres.common;

import java.sql.*;
import java.util.*;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.dao.*;
import com.ztesoft.common.util.tracer.Debug;


/**
 * CommonDAO.java
 * @function: 通用查询类
 * 
 * @author nik
 * @since 2010-1-14
 * @modified  
 */
public class CommonDAO {
	/**
	 * 
	 * 根据SQL查询数据，可以多表查询;
	 * 
	 * @param aSql
	 *            String
	 * @return List，由list组成记录，记录里的字段值由list组成。
	 */
	public List QueryDataBySQL(String aSql) {

		List recordList = new ArrayList();
		if (aSql == null) {
			return recordList;
		}
		if (aSql.trim().length() == 0) {
			return recordList;
		}
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		// int maxRow = 22;

		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			String sqlStr = DAOSQLUtils.getFilterSQL(aSql);
			Debug.print(sqlStr);
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			int i = 1;
			// 判断有多少个字段在结果集中
			ResultSetMetaData md = stmt.getMetaData();
			int columCount = md.getColumnCount();
			md = null;
			int count = 0;
			while (result.next()) {
				count++;
				if(count>CrmConstants.getMaxQuerySize()){
					throw new DAOSystemException("出现超量数据查询，请与项目组联系 !!!!!!!!!!!!!!  SQLException while execSQL:" + aSql + "\n");
				}
				List fieldList = new ArrayList();
				for (i = 1; i <= columCount; i++) {
					fieldList.add(result.getString(i));
				}
				recordList.add(fieldList);
			}
			return recordList;
		} catch (DAOSystemException se) {
			throw se;
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + aSql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
	}

	/**
	 * QueryMulFieldBySQL 根据SQL查询,返回多个字段值;
	 * 
	 * @param aSql
	 *            String
	 * @return ArrayList
	 */
	public void resultDebug(List recordList) {

		Iterator recordIt = recordList.iterator();
		int i = 1;
		while (recordIt.hasNext()) {
			Debug.print("record" + i + "-------------------------------------------", this);
			List fieldList = (List) recordIt.next();
			Iterator fieldIt = fieldList.iterator();
			int j = 1;
			while (fieldIt.hasNext()) {
				String field = (String) fieldIt.next();
				Debug.print("field" + j + "=" + field, this);
			}
		}
	}

	public List query(String sql) {
		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			while (result.next()) {
				list.add(result.getString(1));
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return list;
	}

	public String querySingleValue(String sql) {
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		String returnValue = "";
		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			if (result.next()) {
				returnValue = result.getString(1);
			}
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return returnValue;
	}

	public int update(String sql) {
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		int returnValue = 0;
		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			returnValue=stmt.executeUpdate();

		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {

			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return returnValue;
	}
	public List exeSql(String sql) {

		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = DAOUtils.getDBConnection(JNDINames.BSN_DATASOURCE, this);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql,2);
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			list = (List) handle(result);
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return list;
	}
	public List executeQueryForMapList(String sql) {

		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			Debug.print("getAttr:"+sqlStr);
			stmt = dbConnection.prepareStatement(sqlStr);
			result = stmt.executeQuery();
			list = (List) handle(result);
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return list;
	}
	private Object handle(ResultSet rs) throws SQLException {

		List results = new ArrayList();

		while (rs.next()) {
			results.add(this.rowToMap(rs));
		}

		return results;
	}
	private Map rowToMap(ResultSet rs) throws SQLException {
		Map result = new HashMap();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			result.put(rsmd.getColumnName(i), rs.getString(i));
		}
		return result;
	}
	public void execBatchUpdate(java.util.List sqls) {
		Connection dbConnection = null;
		Statement stmt = null;
		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			//dbConnection.setAutoCommit(false);
			stmt = dbConnection.createStatement();
			for (int i = 0; i < sqls.size(); i++) {

				stmt.addBatch(DAOSQLUtils.getFilterSQL(sqls.get(i).toString()));
			}
			stmt.executeBatch();
			//dbConnection.commit();
		} catch (Exception se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execSQL:" + sqls + "\n", se);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			DAOUtils.closeConnection(dbConnection, this);
		}
	}
	
	/**
	 *  新增用来查询时，参数值穿进来发挥PreparedStatement的作用
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public List executeQueryForMapList(String sql,String[] sqlParams) {

		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setString( i+1, sqlParams[i] );
			}
			result = stmt.executeQuery();
			list = (List) handle(result);
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return list;
	}
	
	/**
	 *  新增用来查询时，参数值穿进来发挥PreparedStatement的作用------------电子发票的数据源
	 * @param sql
	 * @param sqlParams
	 * @return
	 */
	public List executeQueryForMapList2(String sql,String[] sqlParams) {

		List list = new ArrayList();
		Connection dbConnection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			dbConnection = DAOUtils.getDBConnection(JNDINames.CRM_INVOICE, this);
			String sqlStr = DAOSQLUtils.getFilterSQL(sql);
			stmt = dbConnection.prepareStatement(sqlStr);
			
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setString( i+1, sqlParams[i] );
			}
			result = stmt.executeQuery();
			list = (List) handle(result);
		} catch (Exception se) {
			throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(dbConnection, this);
		}
		return list;
	}
	
	public  String callProc(String procName,HashMap inParamMap,int retParaPos) throws Exception{
		Connection dbConnection = null;
		CallableStatement proc = null;
		String returnVal="";
		try{
			String paramStr="?";
			for(int i=0;i<inParamMap.size();i++)
				paramStr=paramStr+",?";
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			proc = dbConnection.prepareCall("{ call "+procName.toUpperCase()+"("+paramStr+")}");
			proc.registerOutParameter(retParaPos,Types.VARCHAR);
			Iterator iterator=inParamMap.keySet().iterator();
			while(iterator.hasNext()){
				  String key=(String)iterator.next();
				  proc.setString(Integer.parseInt(key),(String)inParamMap.get(key));
			}
			proc.execute();
			returnVal=proc.getString(retParaPos);
		}
		catch (Exception se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execProc:" +procName+"\n",se);
		} finally {
			proc.close();
			DAOUtils.closeConnection(dbConnection, this);
		}
		return returnVal;
  }
	
  public  String[] callProc(String i_lan,String i_ppdom,String i_addr,
		                    String procName,int i_page,int i_numperpage) throws Exception{
		Connection dbConnection = null;
		CallableStatement proc = null;
		String[] retStrArr=new String[3];
		try{
			String paramStr="?";
			for(int i=0;i<7;i++)
				paramStr=paramStr+",?";
			dbConnection =  ConnectionContext.getContext().getConnection(JNDINames.CRM_RCDB);
			proc = dbConnection.prepareCall("{ call "+procName.toUpperCase()+"("+paramStr+")}");
			//设置出参
			proc.registerOutParameter(6,Types.VARCHAR);
			proc.registerOutParameter(7,Types.VARCHAR);
			proc.registerOutParameter(8,Types.LONGVARCHAR);
			//设置入参
		    proc.setInt(1,Integer.parseInt(i_lan));
		    proc.setInt(2,Integer.parseInt(i_ppdom));
		    proc.setString(3,i_addr);
		    proc.setInt(4,i_page);
		    proc.setInt(5,i_numperpage);
		    //执行存储过程
			proc.execute();
			//获取返回结果
			retStrArr[0]=proc.getString(6);
			retStrArr[1]=proc.getString(7);
			retStrArr[2]=proc.getString(8);
		}
		catch (Exception se){
			se.printStackTrace();
			throw new DAOSystemException("SQLException while execProc:" +procName+"\n",se);
		} finally {
			proc.close();
			DAOUtils.closeConnection(dbConnection, this);
		}
		return retStrArr;
  }
	

}
