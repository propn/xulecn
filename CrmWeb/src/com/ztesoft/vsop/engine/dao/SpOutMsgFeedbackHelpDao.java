package com.ztesoft.vsop.engine.dao;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.SpOutMsgFeedbackVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class SpOutMsgFeedbackHelpDao {
	protected static Logger logger = Logger.getLogger(SpOutMsgFeedbackHelpDao.class);
	/**
	 * 新增服开回单队列信息
	 * @param spOutMsgFeedback
	 * @throws SQLException
	 */
	public void insertFeedbackQueue(SpOutMsgFeedbackVO spOutMsgFeedback) throws SQLException {
		Connection conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		InputStreamReader ir = null;
		String sql = "insert into sp_out_msg_feedback (ID, IN_TIME, ORDER_ID, OIUT_ORDER_ID, OUT_STREAM_NO, ORDER_RESULT, MSG, FEEBACK_RESULT, FEEBACK_MSG, STATE, FEEBACK_DESC, FEEBACK_TIME, INT_SYS_ID,THREAD_ID,writer,prod_no,PARTITION_ID)" +
				" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			
			ps = conn.prepareStatement(sql );
			SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
			String id = sequenceManage.getNextSequence("sp_out_msg_feedback_seq");
			spOutMsgFeedback.setId(id);
			int index = 1;
			ps.setLong(index ++, Long.valueOf(id).longValue());//ID, 
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//IN_TIME, 
			ps.setString(index++, spOutMsgFeedback.getOrderId());//ORDER_ID, 
			ps.setString(index++, spOutMsgFeedback.getOiutOrderId());//OIUT_ORDER_ID, 
			ps.setString(index++, spOutMsgFeedback.getOutStreamNo());//OUT_STREAM_NO, 
			ps.setString(index++, spOutMsgFeedback.getOrderResult());//ORDER_RESULT, 
			ps.setString(index++, spOutMsgFeedback.getMsg());
			ps.setString(index++, spOutMsgFeedback.getFeebackResult());//FEEBACK_RESULT, 
			ps.setString(index++, spOutMsgFeedback.getFeebackMsg());//FEEBACK_MSG, 
			ps.setString(index++, "0");//"0");//STATE, 
			ps.setString(index++, "");//FEEBACK_DESC, 
			ps.setString(index++, "");//FEEBACK_TIME, 
			ps.setString(index++, spOutMsgFeedback.getIntSysId());//INT_SYS_ID
			ps.setInt(index++, createThreadId(spOutMsgFeedback.getProdNo()));
			ps.setInt(index++, 1);
			ps.setString(index++, spOutMsgFeedback.getProdNo());
			ps.setLong(index++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#saveSpOutMsgFeedback ex.", e);
			throw e;
		}finally{
			if(ir != null){
				try {
					ir.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			DAOUtils.closeStatement(ps);
		}
	}
	private int createThreadId(String productNo) {
		String threadIdStringValue = DcSystemParamManager.getParameter(VsopConstants.FK_THREAD_NUM);
		if(null == threadIdStringValue || "".equals(threadIdStringValue)) return 0;
		int threadNum = Integer.parseInt(threadIdStringValue);
		int prodNo = Integer.parseInt(productNo.substring(productNo.length()-1));
		int ret = prodNo % threadNum;
		return ret;
	}
	
	public SpOutMsgFeedbackVO createFeedbackByOrder(CustomerOrder order){
		SpOutMsgFeedbackVO spOutMsgFeedback = new SpOutMsgFeedbackVO();
		spOutMsgFeedback.setIntSysId(order.getOrderSystem());
		spOutMsgFeedback.setOiutOrderId(order.getOtherSysOrderId());
		spOutMsgFeedback.setOrderId(order.getCustOrderId());
		spOutMsgFeedback.setOutStreamNo(order.getCustSoNumber());
		spOutMsgFeedback.setProdNo(order.getAccNbr());
		return spOutMsgFeedback;
	}
}
