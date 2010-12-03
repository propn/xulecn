package com.ztesoft.crm.salesres.bo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;

/**
 * 处理文件上传,批量更新资源实体表
 * 批量插入订单列表
 * @author Administrator
 *
 */
public  class SrFileUploadBo {
	
	/**
	 * 批量文件上传，写订单表，并将相应资源入订单列表
	 * 返回批量操作成功的个数
	 * @param orderId
	 * @param items
	 * @param condition
	 * @param table
	 * @return 
	 * @throws SQLException 
	 */
	public int batchFileUpload(String orderId,String storageId,String storageName,List items,String condition,String table,String action) throws DAOSystemException{
    	String eTable=DAOSQLUtils.getTableName(table);
    	if ("insert".equalsIgnoreCase(action)) {
    		return batchFileUploadInsert( orderId,storageId ,storageName, items, condition, eTable);
		} else if ("update".equalsIgnoreCase(action)) {
			return batchFileUploadUpdate( orderId, storageId ,storageName, items, condition, eTable);
		}else{
			return 0;
		}
    }
	/**
	 * 批量插入订单列表
	 * @param orderId
	 * @param items
	 * @param condition
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	protected int batchFileUploadInsert(String orderId,String storageId,String storageName,List items,String condition,String table) throws DAOSystemException{
		StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,STORAGE_ID_FROM,STORAGE_NAME_FROM,STORAGE_ID_TO,STORAGE_NAME_TO )");
		sb.append(" SELECT ").append(orderId).append(", RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,STORAGE_ID,STORAGE_NAME, ");
		sb.append(storageId+",'"+storageName+"'");
		sb.append(" FROM ").append(table).append(" WHERE RESOURCE_INSTANCE_CODE = ? AND ").append(condition);
		return batchExcute(sb.toString(),items);
	}
	/**
	 * 批量更新资源表
	 * @param orderId
	 * @param storageId
	 * @param items
	 * @param condition
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	protected int batchFileUploadUpdate(String orderId,String storageId,String storageName, List items,String condition,String table) throws DAOSystemException{
		StringBuffer sb = new StringBuffer();
		sb.append(" UPDATE ").append(table).append(" SET STORAGE_ID = ").append(storageId);
		if(storageName != null && !storageName.trim().equals("")){
			sb.append(",STORAGE_NAME='").append(storageName).append("'");
		}
		sb.append(" WHERE RESOURCE_INSTANCE_CODE = ? AND ").append(condition);
		return batchExcute(sb.toString(),items);
	}
	/**
	 * 批量执行,如果驱动不支持就返回0
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	protected int batchExcute(String sql,List parameters) throws DAOSystemException{
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result=0;
		try {
			conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
			pstmt = conn.prepareStatement(sql);
			for (int i=0; i< parameters.size(); i++) {
				String element = (String) parameters.get(i);
				pstmt.setString(1, element);
				pstmt.addBatch();
			}
			
			int tmp[] = pstmt.executeBatch();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i]>=0) {
					result++;
				}
			}
			
		}catch (SQLException se) {
			new DAOSystemException(se);
		}finally {
			DAOUtils.closeStatement(pstmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return result;
	}
	/**
	 * 批量插入VO,要求VO里面的VO类型必须一致,并继承公共的VO接口
	 * 到目前支持所以类型的批量插入.
	 * 返回的LIST含失败的VO,参数LIST是成功执行的VO集合
	 * @param voList
	 * @throws SQLException
	 */
	public List batchInsertVO(List voList) throws DAOSystemException {
		if (voList == null || voList.size() == 0)
			return null;
		List errList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		VO vo = (VO) voList.get(0);
		HashMap map = null;
		//过滤掉没值得属性。。。
		map = (HashMap) vo.unloadToHashMap();
		System.out.println("==============get first vo 'valid values "+map);
		
		HashMap dataMap = new HashMap();
		Connection conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB,
				this);
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		//System.out.println("==============get db metadata==================");
		try {
			dbmd = conn.getMetaData();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			throw new DAOSystemException("无法获得数据库元数据信息");
		}
		Set keySet = (Set) map.keySet();
		Iterator iter = keySet.iterator();
		//System.out.println("==============begin assemble the sql statement==================");
		sql.append("insert into ").append(vo.getTableName().toUpperCase())
				.append("(");
		String sqlTail = "";
		while (iter.hasNext()) {
			String col = String.valueOf(iter.next()).toUpperCase();
			sql.append(col);
			sqlTail += "?";
			if (iter.hasNext()) {
				sql.append(",");
				sqlTail += ",";
			}
			//将是时间类型的字段名加入到datamapzhong.
			try {
				rs = dbmd.getColumns(null, null, vo.getTableName(), col);
				if(rs != null && rs.next() && rs.getInt("DATA_TYPE")==Types.DATE){
					dataMap.put(col, null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				if(e.getErrorCode()==Integer.parseInt(ParamsConsConfig.TABLENOEXISTS)){
					throw new DAOSystemException("表："+vo.getTableName()+"不存在！");
				}
				throw new DAOSystemException("获取表 "+vo.getTableName()+" 列类型失败");
			}
		}
		sql.append(")values( ").append(sqlTail).append(")");
		//System.out.println("the data type column's collection:"+dataMap);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			//System.out.println("===========begin iterator insert into table:"+vo.getTableName()+"===========");		
			Iterator it = voList.iterator();
			
			while (it.hasNext()) {
				int i = 0;
				vo = (VO) it.next();
				map = (HashMap) vo.unloadToHashMap();
				iter = (Iterator) map.keySet().iterator();
				while (iter.hasNext()) {
					String columnName = String.valueOf(iter.next()).toUpperCase();
					Object columnValue= map.get(columnName);//map.get(columnName).equals("")?null:map.get(columnName);
					if(dataMap.containsKey(columnName)){
						columnValue = DAOUtils.parseDateTime(String.valueOf(columnValue));
						//System.out.println("--->>>"+i+","+columnName+","+columnValue);
					}
					//System.out.println("===>>>"+i+","+columnName+","+columnValue);
					pstmt.setObject(++i,columnValue);
				}
				try {
					pstmt.executeUpdate();
				} catch (SQLException se) {
					se.printStackTrace();
					errList.add(vo);
					it.remove();

				}
				pstmt.clearParameters();
			}
		} catch (SQLException ds) {
			throw new DAOSystemException(ds);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(pstmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return errList;
	}
	/**
	 * 由于目前CODE不是主键,导致无法在插入去检查,现在有三类需要这样,号码,SIM卡,终端,查找的条件是:如果参数2传入负数表示条件为 资源实体CODE,否则条件:资源ID,资源实体CODE
	 * @param list
	 * @return 返回的LIST含失败的VO,参数LIST是成功通过的VO集合
	 * @throws DAOSystemException
	 */
	public List filterExistsVO(List voList, int checkType) throws DAOSystemException {
		
		if (voList == null || voList.size() == 0)
			return null;
		List errList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		String SALESCODE = "resource_instance_code".toUpperCase();
		String SALESID = "sales_resource_id".toUpperCase();
		VO vo = (VO) voList.get(0);
		HashMap map = vo.unloadToHashMap();
		if (!map.containsKey(SALESCODE) && checkType < 0
				|| (!map.containsKey(SALESID) || !map.containsKey(SALESCODE))
				&& checkType >= 0) {
			throw new DAOSystemException("表：" + vo.getTableName() + "无可供查询的字段");
		}
		Connection conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB,
				this);
		ResultSet rs = null;
		sql.append("select count(*) as count from ").append(
				vo.getTableName().toUpperCase()).append(" where ");
		if (checkType < 0) {
			sql.append(SALESCODE + "=?");
		} else {
			sql.append(SALESCODE + "=?").append(" and ").append(SALESID + "=?");
		}

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			
			Iterator it = voList.iterator();

			while (it.hasNext()) {
				int i = 0;
				vo = (VO) it.next();
				map = vo.unloadToHashMap();

				if (checkType < 0) {
					pstmt.setObject(++i, map.get(SALESCODE));
				} else {
					pstmt.setObject(++i, map.get(SALESCODE));
					pstmt.setObject(++i, map.get(SALESID));
				}

				try {
					rs = pstmt.executeQuery();
					if (rs != null && rs.next() && rs.getLong(1) > 0l) {
						errList.add(vo);
						it.remove();
					}
				} catch (SQLException se) {
					se.printStackTrace();
					throw new DAOSystemException(se);
//					if (se.getErrorCode() == Integer
//							.parseInt(ParamsConsConfig.TABLENOEXISTS)) {
//						throw new DAOSystemException("表：" + vo.getTableName()
//								+ "不存在！");
//					}
				}
				pstmt.clearParameters();
			}
		} catch (SQLException ds) {
			throw new DAOSystemException(ds);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(pstmt, this);
			DAOUtils.closeConnection(conn, this);
		}
		return errList;

	}
	/**
	 * 过滤掉值 为NULL或空字符串的键值对.
	 * @param map
	 * @return
	 */
	private static Map filterNulls(Map map) {

		if (map == null)
			return map;
		Set set = map.entrySet();
		Iterator iter = set.iterator();
		for (; iter.hasNext();) {
			Entry ent = (Entry) iter.next();
			if (ent.getValue() == null || ent.getValue().equals("")) {
				iter.remove();
			}

		}
		return map;
	}
}
