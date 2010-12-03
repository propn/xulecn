package com.ztesoft.vsop.engine.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.SecondConfirmMsgVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class SecondConfirmMsgHelpDao {
	protected static Logger logger = Logger.getLogger(SecondConfirmMsgHelpDao.class);
	/**
	 * 新增二次确认队列信息
	 * @param secondConfMsg
	 * @throws Exception
	 */
	public void insertSecondConfirmMsg(SecondConfirmMsgVO secondConfMsg) throws Exception{
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		InputStreamReader ir = null;
		try {
			ps = conn.prepareStatement("insert into secondconfirm_msg(rqcode,cust_order_id,request_xml,create_date,state,THREAD_ID,SMS_MSG,DEAL_TIME,RESULT_DESC,ACC_NBR,RQResult,reply_count,PARTITION_ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			int index = 1;
			ps.setString(index ++, secondConfMsg.getRqcode());
			ps.setString(index++, secondConfMsg.getCustOrderId());
			String requestXML = secondConfMsg.getRequestXml();
			if (null !=requestXML) {
				ir = new InputStreamReader(new ByteArrayInputStream(requestXML.getBytes()));
				ps.setCharacterStream(index++, ir, requestXML.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, secondConfMsg.getState());//ConstantsState.SecondConfirmMsg_UnDeal);
			ps.setString(index++, generateReconfirmThreadId(secondConfMsg.getAccNbr()));
			ps.setString(index++, secondConfMsg.getSmsMsg());
			ps.setString(index++, secondConfMsg.getDealTime());  //DEAL_TIME
			ps.setString(index++, secondConfMsg.getResultDesc());  //RESULT_DESC
			ps.setString(index++, secondConfMsg.getAccNbr()); //ACC_NBR
			ps.setString(index++, secondConfMsg.getRqresult());//ConstantsState.SecondConfirmMsg_Unreply); //RQResult
			ps.setInt(index++, 0);  //reply_count
			ps.setInt(index++, DAOUtils.getCurrentMonth());  
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#insertSecondConfirmMsg",e);
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
	private String generateReconfirmThreadId(String accNbr) {
		String threadCount = DcSystemParamManager.getParameter(VsopConstants.RECONFIRM_THREAD_NUM);
		return generateThreadId(accNbr,threadCount);
	}
	/**
	 * 根据业务号码和线程数生成线程id
	 * @param accNbr  产品号码
	 * @param threadNum  线程总数
	 * @return
	 */
	private String generateThreadId(String accNbr,String threadNum){
		int r = 2;
		if(threadNum != null && !"".equals(threadNum)) r = Integer.parseInt(threadNum);
		int prodNo = Integer.parseInt(accNbr.substring(accNbr.length()-1));
		int ramdom = prodNo % r;
		return String.valueOf(ramdom);
	}
}
