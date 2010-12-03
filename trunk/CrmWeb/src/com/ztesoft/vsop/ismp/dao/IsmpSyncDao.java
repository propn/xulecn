package com.ztesoft.vsop.ismp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.ismp.IsmpServiceVo;

public class IsmpSyncDao {
	
	private static Logger logger = Logger.getLogger(IsmpSyncDao.class);
	
	public void addService(IsmpServiceVo serviceVo) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
		String sql = "INSERT INTO ismp_service(id,streamingNo,SPID,OPFlag,productID,namecn,nameen" +
				",descriptionCN,descriptioneN,access_no,status)" +
				" VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			String id = new SequenceManageDAOImpl().getNextSequence("seq_ismp_service_id");
			ps.setString(index++, id);//id
			ps.setString(index++, serviceVo.getStreamingNo());//streamingNo
			ps.setString(index++, serviceVo.getsPID());//SPID,
			ps.setString(index++, serviceVo.getoPFlag());//OPFlag,
			ps.setString(index++, serviceVo.getProductID());//productID,
			ps.setString(index++, serviceVo.getNameCn());//namecn,
			ps.setString(index++, serviceVo.getNameEn());//nameen
			ps.setString(index++, serviceVo.getDescriptionCN());//descriptionCN,
			ps.setString(index++, serviceVo.getDescriptionEN());//descriptioneN,
//			ps.setString(index++, serviceVo.getServiceCapabilityID());//ServiceCapabilityID,
			ps.setString(index++, serviceVo.getAccess_no());//access_no,
			ps.setString(index++, serviceVo.getStatus());//status
			
			List serviceCapabList = serviceVo.getServiceCapabilityID();
			for (Iterator iterator = serviceCapabList.iterator(); iterator.hasNext();) {
				String serviceCode = (String) iterator.next();
				addServiceAbilityRel(id,serviceVo.getProductID(),serviceCode);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	public boolean deleteService(IsmpServiceVo serviceVo) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
		String sql = "delete from  ismp_service where productID = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, serviceVo.getProductID());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return true;
	}
	public boolean deleteServiceAblRel(IsmpServiceVo serviceVo) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
		String sql = "delete from  ismp_SERVICE_ABILITY_REL where productID = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, serviceVo.getProductID());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return true;
	}

	private void addServiceAbilityRel(String id, String productID,
			String serviceCode) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
		String sql = "insert into ismp_SERVICE_ABILITY_REL(ismp_service_id,productID,SERVICE_CODE) values (?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			int index = 1;
			ps.setString(index ++, id);
			ps.setString(index ++, productID);
			ps.setString(index ++, serviceCode);
			ps.executeUpdate();
		}catch (SQLException e) {
			logger.error("#addProductOfferInstance ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	/**
	 * 
	 * @param productId  ismp的增值产品，相当于VSOP的业务，service_id，content_id
	 * @return
	 * @throws SQLException 
	 */
	public List getIsmpServiceCapabilityIdList(String productId) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
		String sql = "select SERVICE_CODE from  ismp_SERVICE_ABILITY_REL where productID=? ";
		ResultSet rs = null;
		List ret = new ArrayList();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, productId);
			rs = ps.executeQuery();
			
			while(rs.next()){
				String serviceCode = rs.getString("SERVICE_CODE");
				ret.add(serviceCode);
			}
		}catch (SQLException e) {
			logger.error("#addProductOfferInstance ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return ret;
	}
	public String getPNameCn(String serviceId) throws SQLException {
		PreparedStatement ps = null;
		Connection conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
		String sql = "select NAMECN from ismp_service where PRODUCTID=? ";
		ResultSet rs = null;
		String ret = "";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, serviceId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				ret = rs.getString("NAMECN");
			}
			
		}catch (SQLException e) {
			logger.error("#addProductOfferInstance ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return ret;
	}

}
