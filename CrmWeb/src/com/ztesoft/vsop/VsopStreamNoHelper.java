package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;

/**
 * ����������ˮ��
 * ��ȡ������ˮ��
 * @author cooltan
 *
 */

public class VsopStreamNoHelper {
	private VsopStreamNoHelper(){
		
	}
	
	private static VsopStreamNoHelper instance=new VsopStreamNoHelper();
	
	public static VsopStreamNoHelper getInstance(){
		return instance;
	}
	/**
	 * ����������ˮ�ţ�VSOPϵͳ���룫YYYYMMDDHH24MMSS��9λ����ֵ,��26λ
	 * @return
	 */
	public String genReqStreamNo(){
		String ret="";
		if(true){//���ݿⷽʽ
			String srcSeq=this.getSqlSeq();
			String seq=this.lpadWithZero(srcSeq, 9);
			ret+=VsopConstants.VSOP_SYSTEMID+DateUtil.getVSOPDateTime14()+seq;
		}else{//javaͬ��������ˮ���ݲ�ʵ��
			
		}
		
		return ret;
	}
	/**
	 * �������Ļ�ȡ��ˮ����Ϊ������ˮ��
	 * @param requestXml
	 * @return
	 */
	public String getRespStreamNo(String requestXml){
		String streamNo=StringUtil.getInstance().getTagValue("StreamingNo",requestXml );
		return streamNo;
	}
	
	private String getSqlSeq(){
		PreparedStatement stmt = null;
		ResultSet result = null;
		Connection conn = null;
		String GET_SEQUENCE = "SELECT "
				+ DAOSQLUtils.getTableName("seq_req_streamno")+".nextval seq_value FROM dual";
		try {
			conn = LegacyDAOUtil.getConnection() ;
			stmt = conn.prepareStatement(GET_SEQUENCE);
			result = stmt.executeQuery();
			if (result.next()) {
				return result.getString("seq_value");
			} else {
				return "-1";
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while execute "
					+ GET_SEQUENCE + ":\n" + se.getMessage(), se);
		} finally {
			DAOUtils.closeResultSet(result, this);
			DAOUtils.closeStatement(stmt, this);
			LegacyDAOUtil.releaseConnection(conn);
		}
	}
	/**
	 * ��0�����
	 * @param src Դ�ַ���
	 * @param targetLen �������ַ�������
	 * @return
	 */
	public String lpadWithZero(String src,int targetLen){
		int len=src.length();
		int addLen=targetLen-len;
		String zeros="";
		for(int i=0;i<addLen;i++){
			zeros+="0";
		}
		String ret=zeros+src;
		return ret;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
