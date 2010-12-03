package com.ztesoft.component.common.staticdata.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.component.common.staticdata.dao.StaticDataDAO;
import com.ztesoft.component.common.staticdata.vo.DcSqlVO;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
/**
 * 
 * <p>Description: ȡ��̬����DAOImpl</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-13
 * @Version       : 1.0
 */
public class StaticDataDAOImpl implements StaticDataDAO {

	//dc_sql �����ֶ����ظ�������ʹ�ù�������
	private final static String SELECT_DC_SQL_STR = " SELECT dc_sql from "+DAOSQLUtils.getTableName("dc_sql")+" where dc_name = ? ";

	private final static String ALL_SELECT_DC_SQL_STR = " SELECT dc_name, dc_sql from "+DAOSQLUtils.getTableName("dc_sql");
	
	private final static String DC_PRODUCT_PROP = "select  a.attr_value, a.attr_value_desc, '',   b.product_id  from  "+DAOSQLUtils.getTableName("attribute_value")+"  a  , "+DAOSQLUtils.getTableName("product_attr")+"  b  where a.attr_id = 10265  and a.attr_id = b.attr_id and ','||b.attr_value||',' like '%,'||a.attr_value||',%'";
	
	
	public StaticDataDAOImpl() {
	}

	/**
	 * ��ȡ���ݵ�sql
	 */
	public String getSql(String name) throws DAOSystemException{
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;

		try {

//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(SELECT_DC_SQL_STR);
			stmt.setString(1, name);
			result = stmt.executeQuery();
			if (result.next()) {
				return result.getString("dc_sql");
			} else {
				return null;
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting " + "dc_sql:\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}
	

	/**
	 * ��ȡ���ݵ�sql
	 */
	public ArrayList getAllSql() throws DAOSystemException{
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		ArrayList retList = new ArrayList();

		try {

//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(ALL_SELECT_DC_SQL_STR);
			result = stmt.executeQuery();
			while (result.next()) {
				DcSqlVO dcSqlVO = new DcSqlVO();
				dcSqlVO.setDcName(result.getString("dc_name"));
				dcSqlVO.setDcSql(result.getString("dc_sql"));
				retList.add(dcSqlVO);
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting " + ALL_SELECT_DC_SQL_STR + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
		
		return retList;
	}

	/**
	 * ��ȡ��������������
	 */
	public ArrayList getStaticData(String dcSql) throws DAOSystemException{
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;

		ArrayList retList = new ArrayList();

		try {

//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			
			if("DC_PRODUCT_PROP".equals(dcSql)){
				dcSql = DC_PRODUCT_PROP;
				stmt = conn.prepareStatement(dcSql);
			}else{
				stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(dcSql));
			}
			
			result = stmt.executeQuery();
			// �ж��ж��ٸ��ֶ��ڽ������
			ResultSetMetaData md = stmt.getMetaData();
			int columCount = md.getColumnCount();
			md = null;
		
			while (result.next() ) {
				
				//ԭ������ΪStaticDataVO������ͳһΪStaticAttrVO��ֻ����ʾ��ValueDesc�����ڵ���ʾֵ�������Ķ��Ǵ��롣
				StaticAttrVO vo = new StaticAttrVO();
				
				vo.setAttrValue(DAOUtils.trimStr(result.getString(1)));
				vo.setAttrId(result.getString(1));
				vo.setAttrCode(result.getString(1));
				vo.setAttrValueDesc(DAOUtils.trimStr(result.getString(2)));
				vo.setAttrValueId(result.getString(1));
				
				//����д���2���ֶ�ȡparentValueId�����������ֶ����ϼ��ڵ�Ķ�Ӧ��ֵ��
				if(columCount > 2)
					vo.setParentValueId(result.getString(3));
				else
					vo.setParentValueId("");
				if(columCount > 3)
					vo.setParam1(result.getString(4));
				else
					vo.setParam1("");				
								
				retList.add(vo);
			}
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting " + "dc_sql:"+dcSql+"\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}

		return retList;
	}
	
	
	public  String[] getSidFlag() throws DAOSystemException{
		String s = "select param_val from dc_system_param where param_code = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String[] ret = {"","",""};
		try {


//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			//ȡsidԶ�̿���
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(s) );
			stmt.setString( 1, "RUN_SID_FLAG");

			rs = stmt.executeQuery();
			if(rs.next()){
				ret[0] = rs.getString("param_val");
			}
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			
			//ȡsid���ؿ���
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(s) );
			stmt.setString( 1, "RUN_LOCAL_SID_FLAG");

			rs = stmt.executeQuery();
			if(rs.next()){
				ret[1] = rs.getString("param_val");
			}
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			
			//ȡ ����ά���ر𹤺�
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(s) );
			stmt.setString( 1, "MAINTANCE_OPER_ID");

			rs = stmt.executeQuery();
			if(rs.next()){
				ret[2] = rs.getString("param_val");
			}
			
			return ret;
			
		}
		catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"+s, se);
		}
		finally {
			
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
			
		}
	}
	
	public void setSidFlag(String remoteSidFlag,String localSidFlag,String maintinceOperId) throws DAOSystemException{
		String s = "update dc_system_param set param_val = ? where param_code  = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {


//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(s) );
			for(int i=0;i<3;i++){
				if(i==0){
					stmt.setString(1, remoteSidFlag);
					stmt.setString(2, "RUN_SID_FLAG");
				}
				if(i==1){
					stmt.setString(1, localSidFlag);
					stmt.setString(2, "RUN_LOCAL_SID_FLAG");
				}
				if(i==2){
					stmt.setString(1, maintinceOperId);
					stmt.setString(2, "MAINTANCE_OPER_ID");
				}
				stmt.addBatch();
			}
			stmt.executeBatch();
		}
		catch (SQLException se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while insert sql:\n"+s, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}
	private DynamicDict dto = null ;
	public DynamicDict getDto() {
		// TODO Auto-generated method stub
		return this.dto;
	}

	public void setDto(DynamicDict dto) {
		// TODO Auto-generated method stub
		this.dto = dto ;
	}

	
	
}
