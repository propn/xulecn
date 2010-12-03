package com.ztesoft.vsop.engine.dao;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.StringUtil;

/**
 * 服务开通工单处理队列辅助DAO
 * @author cooltan
 *
 */

public class InfMsgHelpDao {
	private String undealState = "U"; //未处理
	private String dealingState = "D"; // 处理中
	private String failState = "F"; // 处理失败
	private String successState = "S"; // 处理成功

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 新增服开通处理队列
	 * @param systemId
	 * @param msg
	 * @param productNo
	 * @return
	 */
	public boolean saveInfMsg(String systemId,String msg,String productNo,String otherOrderId)throws SQLException{
		boolean result=false;
		String unuseClob = DcSystemParamManager.getParameter(VsopConstants.UNUSE_CLOB);
		if("true".equalsIgnoreCase(unuseClob)){
			result = this.saveWorkListToInfMsgWithoutClob(systemId, msg,productNo,otherOrderId);
		}else{
			result = this.saveWorkListToInfMsg(systemId, msg,productNo,otherOrderId);
		}
		return result;
	}
	private boolean saveWorkListToInfMsgWithoutClob(String infSystem, String infMsg,
			String productNo,String otherOrderId) throws SQLException {
		long s=System.currentTimeMillis();
		Connection conn = null;
		String sql = "insert into inf_msg(INF_MSG_ID,INF_MSG,INF_SYSTEM,CREATE_DATE,STATE,STATE_DATE,THREAD_ID,PARTITION_ID,OTHER_SYS_ORDER_ID) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			int index = 1;
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String seq = aSequenceManageDAOImpl.getNextSequence("SEQ_INF_MSG_ID");
			ps.setString(index++, seq);
			ps.setString(index++, infMsg);
			ps.setString(index++, infSystem);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, undealState);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setInt(index++, createThreadId(productNo));
			ps.setString( index++, String.valueOf(DAOUtils.getCurrentMonth()) );
			ps.setString(index++, otherOrderId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return true;
	}
	
	private boolean saveWorkListToInfMsg(String infSystem, String infMsg, String productNo,String otherOrderId) throws SQLException {
		long s=System.currentTimeMillis();
		Connection conn = null;
		String sql = "insert into inf_msg(INF_MSG_ID,INF_MSG,INF_SYSTEM,CREATE_DATE,STATE,STATE_DATE,THREAD_ID,PARTITION_ID,OTHER_SYS_ORDER_ID,ACTION_TYPE,ACC_NBR) values(?,?,?,?,?,?,?,?,?,?,?)";
		String actionType = StringUtil.getInstance().getTagValue("ActionType", infMsg);
		if(actionType == null)actionType="";
		PreparedStatement ps = null;
		InputStreamReader ir = null;
		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			int index = 1;
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String seq = aSequenceManageDAOImpl.getNextSequence("SEQ_INF_MSG_ID");
			ps.setString(index++, seq);
			if (infMsg != null) {
				ir = new InputStreamReader(new ByteArrayInputStream(infMsg.getBytes()));
				ps.setCharacterStream(index++, ir, infMsg.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setString(index++, infSystem);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, undealState);
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setInt(index++, createThreadId(productNo));
			ps.setString( index++, String.valueOf(DAOUtils.getCurrentMonth()) );
			ps.setString(index++, otherOrderId);
			ps.setString(index++, actionType);
			ps.setString(index++, productNo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			if(ir != null){
				try {
						ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	private int createThreadId(String productNo) {
		String threadIdStringValue = DcSystemParamManager.getParameter(VsopConstants.FK_THREAD_NUM);
		if(null == threadIdStringValue || "".equals(threadIdStringValue)) return 0;
		int threadNum = Integer.parseInt(threadIdStringValue);
		int prodNo = Integer.parseInt(productNo.substring(productNo.length()-1));
		int ret = prodNo % threadNum;
		return ret;
	}

}
