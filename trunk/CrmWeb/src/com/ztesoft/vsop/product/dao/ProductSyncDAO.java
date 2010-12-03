package com.ztesoft.vsop.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.product.vo.InfProdToOCSVO;
import com.ztesoft.vsop.web.DcSystemParamManager;
import common.Logger;
/**
 * @desc
 * @author qin.guoquan
 * @date Oct 11, 2010 1:55:01 PM
 */
public class ProductSyncDAO {

	private Logger log = Logger.getLogger(ProductSyncDAO.class);
	////从INF_PROD_TO_OCS表取出多少条状态为U的记录
	private final String PROD_SYNC_ROWNUM = DcSystemParamManager.getParameter(VsopConstants.PROD_SYNC_ROWNUM);
	
	/**
	 * 按主键的顺序取出前N条数据状态同时为U的数据，进行处理，同时把其状态置为D
	 * @param prodSubType
	 * @return
	 * @throws Exception
	 */
	public List getInfProdToOCSInfo(String prodSubType) throws Exception {
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement psmt = null;
		PreparedStatement updatePsmt = null;
		ResultSet rs = null;
		List ocsList = null;
		InfProdToOCSVO ocs = null;
		
		//按主键的顺序取出前N条数据状态同时为U的数据
		String selectSql = "select inf_prod_to_ocs_id, prod_msg, prod_sub_type, prod_code, op_flag, prod_system, create_date, state, state_date, send_times, result_msg " +
				"from inf_prod_to_ocs where state='U' and prod_sub_type = ? and rownum <= ? order by inf_prod_to_ocs_id";
		//状态置为D
		String updateSql = "update inf_prod_to_ocs set state = 'D', state_date=" + DatabaseFunction.current() + " where inf_prod_to_ocs_id = ?";
		
		try {
			psmt = conn.prepareStatement(selectSql);
			psmt.setString(1, prodSubType);
			psmt.setString(2, PROD_SYNC_ROWNUM);
			rs = psmt.executeQuery();
			
			ocsList = new ArrayList();
			updatePsmt = conn.prepareStatement(updateSql);
			
			while (rs.next()) {
				ocs = new InfProdToOCSVO();
				ocs.setInfProdToOcsId(rs.getString("inf_prod_to_ocs_id"));
				ocs.setProdMsg(rs.getString("prod_msg"));
				ocs.setProdSubType(rs.getString("prod_sub_type"));
				ocs.setProdCode(rs.getString("prod_code"));
				ocs.setOpFlag(rs.getString("op_flag"));
				ocs.setProdSystem(rs.getString("prod_system"));
				ocs.setCreateDate(rs.getString("create_date"));
				ocs.setState(rs.getString("state"));
				ocs.setStateDate(rs.getString("state_date"));
				ocs.setSendTimes(rs.getString("send_times"));
				ocs.setResultMsg(rs.getString("result_msg"));
				
				updatePsmt.setString(1, "D");
				updatePsmt.setString(2, rs.getString("inf_prod_to_ocs_id"));
				updatePsmt.addBatch();
			}
			
			if (ocsList.size() > 0) {
				updatePsmt.executeBatch();
			}
		} catch (Exception e) {
			log.error("##ProductSyncDAO.getInfProdToOCSInfo ex.", e);
		} finally {
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(psmt);
			DAOUtils.closeStatement(updatePsmt);			
		}
		return ocsList;
	}
	
	/**
	 * 写历史表操作
	 * @param vo
	 * @throws Exception
	 */
	public void witeInfProdToOCSHis(InfProdToOCSVO vo) throws Exception {
		
		String sql = "insert into inf_prod_to_ocs_his(inf_prod_to_ocs_id, prod_msg, prod_sub_type, prod_code, op_flag, prod_system, create_date, state, state_date, send_times, result_msg) values (?,?,?,?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			int i = 1;
			ps.setLong(i++, Long.parseLong(vo.getInfProdToOcsId()));
			ps.setString(i++, vo.getProdMsg());
			ps.setString(i++, vo.getProdSubType());
			ps.setString(i++, vo.getProdCode());
			ps.setString(i++, vo.getOpFlag());
			ps.setLong(i++, Long.parseLong(vo.getProdSystem()));
			//ps.setTimestamp(i++, DateUtil.getTimestamp(vo.getCreateDate()));
			ps.setTimestamp(i++, DateUtil.getTimestamp(DatabaseFunction.current()));
			ps.setString(i++, vo.getState());
			ps.setTimestamp(i++, DateUtil.getTimestamp(vo.getStateDate()));
			ps.setLong(i++, Long.parseLong(vo.getSendTimes()));
			ps.setString(i++, vo.getResultMsg());
			ps.executeUpdate();
		} catch (Exception e) {
			log.error("####ProductSyncDAO.witeInfProdToOCSHis.ex", e);
		} finally {
			DAOUtils.closeStatement(ps, this);
			DAOUtils.closeConnection(con, this);
		}
	}
	
	/**
	 * 每发送一次，重送次数加1,重送次数等于大于4则不再发送，将记录状态置F
	 * @param vo
	 * @throws Exception
	 */
	public void updateInfProdToOCS(InfProdToOCSVO vo) throws Exception {
		

		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update inf_prod_to_ocs set state = ?,send_times = ?,state_date=" + DatabaseFunction.current() + " where inf_prod_to_ocs_id = ?";
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getState());
			ps.setString(2, vo.getSendTimes());
			ps.setString(3, vo.getInfProdToOcsId());
			ps.executeUpdate();
		} catch (Exception e) {
			log.error("#ProductSyncDAO.updateInfProdToOCS.ex", e);
		} finally {
			DAOUtils.closeStatement(ps, this);
			DAOUtils.closeConnection(con, this);
		}
	
		
	}
	
}
