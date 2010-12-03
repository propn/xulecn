package com.ztesoft.vsop.engine.dao;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.SpOutMsgVO;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class SpOutMsgHelpDao {
	protected static Logger logger = Logger.getLogger(SpOutMsgHelpDao.class);
	public void insertSpiQueue(SpOutMsgVO spOutMsg) throws SQLException {
		String insertSql = "INSERT INTO SP_OUT_MSG(ID,IN_TIME,MSG_ID,SYS,ORDER_ID,STATE,MSG,PROD_NO,LAN_CODE,PARTITION_ID,LAN_ID) " +
				" VALUES(SP_OUT_MSG_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";
		long s=System.currentTimeMillis();
		PreparedStatement ps = null;
		Connection conn = null;
		InputStreamReader ir = null;
		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			ps = conn.prepareStatement(insertSql);
			int index = 1;
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());
			ps.setString(index++, "0");//msg_id
			ps.setString(index++, spOutMsg.getSys());//SYS,
			ps.setString(index++, spOutMsg.getOrderId());//order_id,
			ps.setString(index++, "0");//STATE
			String spiXML = spOutMsg.getMsg();
			if (spiXML != null) {
				ir = new InputStreamReader(new ByteArrayInputStream(spiXML.getBytes()));
				ps.setCharacterStream(index++, ir, spiXML.length());
			} else {
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
			ps.setString(index++, spOutMsg.getProdNo());
			ps.setString(index++, genLanCode(spOutMsg.getProdNo()));//�����lanCode������̱�ʶ
			ps.setLong(index++, DAOUtils.getCurrentMonth());
			ps.setString(index++, spOutMsg.getLanId());//�����lanID���ڹ����ı�������ʶ
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertSpiQueue ex.",e);
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
		logger.info("save spi XML cost:"+(System.currentTimeMillis()-s));
	}
	/**
	 * ����ģ���Ż�������
	 * �ָ�����ģ��Ĳ�ͬ����
	 * @param lanCode
	 * @return
	 */
	private String genLanCode(String productNo) {
		String spiProcNum = DcSystemParamManager.getParameter(VsopConstants.SPI_PROC_NUM);
		return generateThreadId(productNo, spiProcNum);
	}
	/**
	 * ����ҵ�������߳��������߳�id
	 * @param accNbr  ��Ʒ����
	 * @param threadNum  �߳�����
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
