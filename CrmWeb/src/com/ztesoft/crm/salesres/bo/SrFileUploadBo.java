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
 * �����ļ��ϴ�,����������Դʵ���
 * �������붩���б�
 * @author Administrator
 *
 */
public  class SrFileUploadBo {
	
	/**
	 * �����ļ��ϴ���д������������Ӧ��Դ�붩���б�
	 * �������������ɹ��ĸ���
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
	 * �������붩���б�
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
	 * ����������Դ��
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
	 * ����ִ��,���������֧�־ͷ���0
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
	 * ��������VO,Ҫ��VO�����VO���ͱ���һ��,���̳й�����VO�ӿ�
	 * ��Ŀǰ֧���������͵���������.
	 * ���ص�LIST��ʧ�ܵ�VO,����LIST�ǳɹ�ִ�е�VO����
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
		//���˵�ûֵ�����ԡ�����
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
			throw new DAOSystemException("�޷�������ݿ�Ԫ������Ϣ");
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
			//����ʱ�����͵��ֶ������뵽datamapzhong.
			try {
				rs = dbmd.getColumns(null, null, vo.getTableName(), col);
				if(rs != null && rs.next() && rs.getInt("DATA_TYPE")==Types.DATE){
					dataMap.put(col, null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				if(e.getErrorCode()==Integer.parseInt(ParamsConsConfig.TABLENOEXISTS)){
					throw new DAOSystemException("��"+vo.getTableName()+"�����ڣ�");
				}
				throw new DAOSystemException("��ȡ�� "+vo.getTableName()+" ������ʧ��");
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
	 * ����ĿǰCODE��������,�����޷��ڲ���ȥ���,������������Ҫ����,����,SIM��,�ն�,���ҵ�������:�������2���븺����ʾ����Ϊ ��Դʵ��CODE,��������:��ԴID,��Դʵ��CODE
	 * @param list
	 * @return ���ص�LIST��ʧ�ܵ�VO,����LIST�ǳɹ�ͨ����VO����
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
			throw new DAOSystemException("��" + vo.getTableName() + "�޿ɹ���ѯ���ֶ�");
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
//						throw new DAOSystemException("��" + vo.getTableName()
//								+ "�����ڣ�");
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
	 * ���˵�ֵ ΪNULL����ַ����ļ�ֵ��.
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
