package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;

/**
 * 生成请求流水号
 * 获取返回流水号
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
	 * 生成请求流水号：VSOP系统编码＋YYYYMMDDHH24MMSS＋9位序列值,共26位
	 * @return
	 */
	public String genReqStreamNo(){
		String ret="";
		if(true){//数据库方式
			String srcSeq=this.getSqlSeq();
			String seq=this.lpadWithZero(srcSeq, 9);
			ret+=VsopConstants.VSOP_SYSTEMID+DateUtil.getVSOPDateTime14()+seq;
		}else{//java同步管理流水，暂不实现
			
		}
		
		return ret;
	}
	/**
	 * 从请求报文获取流水号作为返回流水号
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
	 * 补0左对齐
	 * @param src 源字符串
	 * @param targetLen 期望的字符串长度
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
