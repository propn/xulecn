package com.ztesoft.oaas.dao.sms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.InformVO;
import com.ztesoft.oaas.vo.SmsVo;
import com.ztesoft.oaas.vo.StaffSmsVo;

public class SmsDaoImpl    implements SmsDao {
	private String getSmsSeq(){
		return getNextSequence("INF_NOTE_INFORM_SEQ","SERIAL_NO");
	}
	public String getNextSequence(String tableCode,String fieldCode ) {

		String GET_SEQUENCE_CODE = "SELECT sequence_code FROM sequence_management "
				+ " WHERE table_code=? AND field_code=?";

		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sequenceCode ="";
		try {
			 
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(GET_SEQUENCE_CODE));
			stmt.setString(1, tableCode);
			stmt.setString(2, fieldCode);
			result = stmt.executeQuery();
			if (result.next()) {
				sequenceCode = result.getString("sequence_code");
			} else {
				throw new DAOSystemException("取序列出错,不存在的序列:table_code:"+tableCode+" field_code:"+fieldCode+"!");
			}
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
            
			synchronized(sequenceCode){
				conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;//需要修改数据源

				String GET_SEQUENCE = "SELECT "
						+ DAOSQLUtils.getTableName(sequenceCode)
						+ ".nextval seq_value FROM dual";
				stmt = conn.prepareStatement(GET_SEQUENCE);
				result = stmt.executeQuery();
				if (result.next()) {
					return result.getString("seq_value");
				} else {
					return "-1";
				}
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while execute "
					+ GET_SEQUENCE_CODE + ":" + sequenceCode + ":\n"
					+ se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	public String getSequenceValueBySeqname(String seqName ) {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String sequenceCode =seqName;
		try {
        
			synchronized(sequenceCode){
				conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;//需要修改数据源

				String GET_SEQUENCE = "SELECT "
						+ sequenceCode
						+ ".nextval seq_value FROM dual";
				stmt = conn.prepareStatement(GET_SEQUENCE);
				result = stmt.executeQuery();
				if (result.next()) {
					return result.getString("seq_value");
				} else {
					return "-1";
				}
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while execute "
					+   ":" + sequenceCode + ":\n"
					+ se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	String INSERT_SMS_SQL = "INSERT INTO PP_SM_SUBMIT(ID,CHARGETERMID,DESTTERMID,MSGCONTENT,SEND_DATE,PROC_DATE,PROC_FLAG)  VALUES(?,?,?,?,SYSDATE,SYSDATE,'0')";
	public void sendMsg(SmsVo smsVo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		String result="0";
		
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;			 
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(INSERT_SMS_SQL) );
			int index = 1;
 
			stmt.setString( index++,getSmsSeq() );
			stmt.setString(index++, smsVo.getChargeTermid());
			stmt.setString(index++, smsVo.getDestTermid());
			stmt.setString(index++, smsVo.getMsgContent());	
			stmt.execute();
			
		}
		catch (SQLException se) {
			Debug.print(INSERT_SMS_SQL,this);
			throw new DAOSystemException("SQLException while update sql:\n"+INSERT_SMS_SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		 
	}
   
	public long countByCond(String whereCond) throws DAOSystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List findByCond(String whereCond) throws DAOSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByCond(String where, QueryFilter queryFilter)
			throws DAOSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public VO getEmptyVO() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(VO vo) throws DAOSystemException {
		// TODO Auto-generated method stub

	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean update(String cond, VO vo) throws DAOSystemException {
		// TODO Auto-generated method stub
		return false;
	}
	String INSERT_MOBILE_SQL = "INSERT INTO StaffMobile(party_role_id,mobile_num,state_date) VALUES(?,?,SYSDATE)";
	public int  insertStaffCodeMobile(String staffCode,String mobile){
		Connection conn = null;
		PreparedStatement stmt = null;
		int result=0;
		 
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;			 
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(INSERT_MOBILE_SQL) );
			int index = 1;		
			stmt.setString(index++, staffCode);
			stmt.setString(index++, mobile);
			 
			stmt.execute();
			result=1;
		}
		catch (SQLException se) {
			result=0;
			Debug.print(INSERT_MOBILE_SQL,this);
			throw new DAOSystemException("SQLException while update sql:\n"+INSERT_MOBILE_SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return result;
	}
	String UPDATE_MOBILE_SQL = "UPDATE StaffMobile SET mobile_num=? WHERE party_role_id=? ";
	public int  updateStaffCodeMobile(String staffCode,String mobile){
		Connection conn = null;
		PreparedStatement stmt = null;
 
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;			 
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(UPDATE_MOBILE_SQL) );
			int index = 1;
			stmt.setString(index++, mobile);
			stmt.setString(index++,staffCode );		 
		    return 	stmt.executeUpdate();
			 
		}
		catch (SQLException se) {
			 
			Debug.print(UPDATE_MOBILE_SQL,this);
			throw new DAOSystemException("SQLException while update sql:\n"+UPDATE_MOBILE_SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		 
	}
	private String SEL_SQL="select a.party_role_id staffCode,a.mobile_num mobile from StaffMobile a where a.party_role_id= ? ";
	public  StaffSmsVo getStaffSmsVoByStaffcode(String staffcode) throws DAOSystemException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs=null;
		int result=0;
		StaffSmsVo vo=null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;			 
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SEL_SQL) );
			int index = 1;
 
			stmt.setString( index++,staffcode );	 
			rs=stmt.executeQuery();
			if(rs.next()){
			 vo=new StaffSmsVo();
			 vo.setStaffCode(rs.getString("staffCode"));
			 vo.setMobile(rs.getString("mobile"));
			}
			
			return vo;
		}
		catch (SQLException se) {
			result=0;
			Debug.print(INSERT_MOBILE_SQL,this);
			throw new DAOSystemException("SQLException while update sql:\n"+INSERT_MOBILE_SQL, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	public int setStaffMobile(String staffCode,String mobile)throws DAOSystemException{
		StaffSmsVo svo=getStaffSmsVoByStaffcode(staffCode);
		if(svo==null){//插入
			insertStaffCodeMobile(staffCode,mobile);
			return 1;//新加
		}
		else{//修改
			updateStaffCodeMobile(staffCode,mobile);
			return 2;//修改成功
		}	
	}
	//写入短信息接口库表
       /*       table INF_NOTE_INFORM  (
			   SERIAL_NO            NUMBER(12)                      not null,
			   DEST_NUM             VARCHAR2(20)                    not null,
			   CHARGE_TYPE_ID       VARCHAR2(20)                    not null,
			   MSG_CONTENT          VARCHAR2(200)                   not null,
			   SYS_FLAG_TYPE        CHAR(9)                         not null,
			   ACCEPT_ID            VARCHAR2(32)                    not null,
			   SEND_TIME            NUMBER(1),
			   STATE_FLAG           CHAR(1),
			   STATE_MSG            VARCHAR2(1000),
	 */
	String INSERT_INFORM_SQL = "INSERT INTO INF_NOTE_INFORM(SERIAL_NO,DEST_NUM,CHARGE_TYPE_ID,MSG_CONTENT,SYS_FLAG_TYPE," +
			"ACCEPT_ID,SEND_TIME,STATE_FLAG,STATE_MSG )  VALUES(?,?,?,?,?,?,?,? ,?)";
	public void sendMsg(InformVO smsVo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;			 
			stmt = conn.prepareStatement( INSERT_INFORM_SQL );
			int index = 1;
 
			stmt.setString( index++,getSmsSeq() );
			stmt.setString(index++, smsVo.getDestNum());
			stmt.setString(index++, smsVo.getChargeTypeId());
			stmt.setString(index++, smsVo.getMsgContent());				
			stmt.setString( index++,smsVo.getSysFlagType());
			stmt.setString(index++, smsVo.getAcceptId());
			stmt.setString(index++, smsVo.getSendTime());
			stmt.setString(index++, smsVo.getStateFlag());	
			stmt.setString(index++, smsVo.getStateMsg());	
			stmt.execute();
			
		}
		catch (SQLException se) {
			Debug.print(INSERT_INFORM_SQL,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+INSERT_INFORM_SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		 
	}
	
}
