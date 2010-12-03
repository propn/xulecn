package com.ztesoft.vsop.engine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.engine.vo.ProdInstVO;

public class ProdInstHelpDao {
	protected static Logger logger = Logger.getLogger(ProdInstHelpDao.class);
	/**qryProdInstByAccNbrAndProductId
	 * 通过号码、产品类型查询产品实例
	 * target当true时除00X（拆机）状态的都为有效状态。
	 * target当false时只有00A（正常）00B，00C状态为有效状态。
	 * @param accNbr
	 * @param productId
	 * @return
	 */
	public ProdInstVO qryProdInstByAccNbrAndProductId(String accNbr,String productId, boolean target) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdInstVO prodInst = new ProdInstVO();
		String sql = "select * from PROD_INST where PRODUCT_ID=? and ACC_NBR=? and STATE_CD in(?,?,?)";
		String sqlB = "select * from PROD_INST where PRODUCT_ID=? and ACC_NBR=? and STATE_CD <> '00X'";
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			if(!target){
				ps = conn.prepareStatement(sql );
				ps.setString(1, productId);
				ps.setString(2, accNbr);
				ps.setString(3, ProdInstVO.StateOfEffective);
				ps.setString(4, ProdInstVO.StateOfDowntime);
				ps.setString(5, ProdInstVO.StateOfArrearsDown);
			}else{
				ps = conn.prepareStatement(sqlB);
				ps.setString(1, productId);
				ps.setString(2, accNbr);
			}
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
				prodInst.setUserName(rs.getString("USER_NAME"));
				prodInst.setUimNO(rs.getString("UIM_NO"));
				prodInst.setOldUimNO(rs.getString("OLD_UIM_NO"));
			}
		} catch (SQLException e) {
			logger.error("#qryProdInstByAccNbrAndProductId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}
	/**
	 * 通过号码与区号查询产品实例对象,如果区号不传则直接号码查询,查非00X状态
	 * @param lanCode
	 * @param accNbr
	 * @return
	 * @throws SQLException
	 */
	public ProdInstVO qryProdInstByAccNbrAndLanCode_GX(String accNbr,String lanCode) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ProdInstVO prodInst = null;
		try{
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sql="select * from PROD_INST where  ACC_NBR=? and STATE_CD <> '00X' "+ ( (null != lanCode) ? " and LAN_ID = ?" : "" );
			ps = conn.prepareStatement(sql);
			ps.setString(1, accNbr);
			if(null != lanCode) ps.setString(2, lanCode);
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst = new ProdInstVO();
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
				
				prodInst.setUserName(rs.getString("USER_NAME"));
				prodInst.setUimNO(rs.getString("UIM_NO"));
				prodInst.setOldUimNO(rs.getString("OLD_UIM_NO"));
			}
			
		}catch(SQLException e){
			logger.error("#saveOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}
	
	/**
	 * 通过号码与区号查询产品实例对象,如果区号不传则直接号码查询
	 * @param lanCode
	 * @param accNbr
	 * @return
	 * @throws SQLException
	 */
	public ProdInstVO qryProdInstByAccNbrAndLanCode(String accNbr,String lanCode) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ProdInstVO prodInst = null;
		try{
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sql="select * from PROD_INST where  ACC_NBR=? and STATE_CD in(?,?,?)"+ ( (null != lanCode) ? " and LAN_ID = ?" : "" );
			ps = conn.prepareStatement(sql);
			ps.setString(1, accNbr);
			ps.setString(2, ProdInstVO.StateOfEffective);
			ps.setString(3, ProdInstVO.StateOfDowntime);
			ps.setString(4, ProdInstVO.StateOfArrearsDown);
			if(null != lanCode) ps.setString(5, lanCode);
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst = new ProdInstVO();
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
				
				prodInst.setUserName(rs.getString("USER_NAME"));
				prodInst.setUimNO(rs.getString("UIM_NO"));
				prodInst.setOldUimNO(rs.getString("OLD_UIM_NO"));
			}
			
		}catch(SQLException e){
			logger.error("#saveOrderRelation ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}
	/**
	 * 新增产品实例
	 * @param prodInst
	 * @throws Exception
	 */
	public void insertProdInst(ProdInstVO prodInst) throws Exception{
		PreparedStatement ps1 = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO PROD_INST(PROD_INST_ID,PRODUCT_ID,COMMON_REGION_ID,PROD_TYPE_CD,PAYMENT_MODE_CD,CREATE_DATE,BEGIN_RENT_TIME,STOP_RENT_TIME,FINISH_TIME,PRODUCT_PASSWORD,STATE_CD,LAN_ID,ACC_NBR,STATE_DATE,USER_NAME,UIM_NO,OLD_UIM_NO)" +
				" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql_exit_check="select pi.prod_inst_id from prod_inst pi where pi.prod_inst_id=? ";
		boolean isUpdate=false;
		try {
			//判断是否已经存在此用户实例了
			if(null!=prodInst.getProdInstId() && !"".equals(prodInst.getProdInstId())){
				Connection conn1=null;
				ResultSet ret=null;
				try {
					conn1 = ConnectionContext.getContext().getConnection(
							JNDINames.CRM_DATASOURCE);
					ps1=conn1.prepareStatement(sql_exit_check);
					ps1.setLong(1, Long.valueOf(prodInst.getProdInstId()).longValue());
					ret=ps1.executeQuery();
					if(ret.next()){
						isUpdate = true;
					}
				} catch (RuntimeException e) {
					throw e;
				}finally{
					DAOUtils.closeResultSet(ret);
					DAOUtils.closeStatement(ps1);
				}
			}
			//撤单的时候，如果产品实例存在，替换掉产品实例相关信息
			if(isUpdate){
				updateProdInst(prodInst);
				return;
			}
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			ps = conn.prepareStatement(sql);
			int index = 1;
			String prodInstId = prodInst.getProdInstId();
			//String pid = "0";
			if (prodInstId == null || "".equals(prodInstId)) {
				SequenceManageDAOImpl sequenceManage = new SequenceManageDAOImpl();
				prodInstId =sequenceManage.getNextSequence("SEQ_PROD_INST_ID");
				prodInst.setProdInstId(prodInstId);
			}
			ps.setLong(index++, Long.valueOf(prodInstId).longValue());
			ps.setString(index++, prodInst.getProdId());//PRODUCT_ID,
			ps.setString(index++, prodInst.getCommonRegionId());//COMMON_REGION_ID,
			ps.setString(index++, prodInst.getProdTypeCd());//PROD_TYPE_CD, 0L 
			ps.setString(index++, prodInst.getPaymentModeCd());//PAYMENT_MODE_CD,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//CREATE_DATE,
			ps.setString(index++, prodInst.getBeginRentTime());//BEGIN_RENT_TIME,
			ps.setString(index++, prodInst.getStopRentTime());//STOP_RENT_TIME,
			ps.setString(index++, prodInst.getFinishTime());//FINISH_TIME,
			ps.setString(index++, prodInst.getProdPassword());//PRODUCT_PASSWORD,
			ps.setString(index++, prodInst.getStateCd());//STATE_CD,
			ps.setString(index++, prodInst.getLanId());//LAN_ID,
			ps.setString(index++, prodInst.getAccNbr());//ACC_NBR,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATE_DATE
			
			ps.setString(index++, prodInst.getUserName());//ACC_NBR,
			ps.setString(index++, prodInst.getUimNO());//ACC_NBR,
			ps.setString(index++, prodInst.getOldUimNO());//ACC_NBR,
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("#insertProdInst ex.", e);
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 根据产品实例修改产品实例信息
	 * @param prodInstId
	 * @param newAccNbr
	 * @param newProductId
	 * @throws Exception
	 */
	public void updateProdInst(ProdInstVO prodInst) throws Exception{
		Connection conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		String sql = "UPDATE PROD_INST SET ACC_NBR = ?,product_id=?,PAYMENT_MODE_CD = ?,STATE_CD=?,LAN_ID=?,STATE_DATE=? ,USER_NAME=? ,UIM_NO=? ,OLD_UIM_NO=?  WHERE prod_inst_id = ? ";
		int index = 1;
		PreparedStatement ps = conn.prepareStatement(sql);
		String prodInstId = prodInst.getProdInstId();
		try {
			ps.setString(index++, prodInst.getAccNbr());//ACC_NBR,
			ps.setString(index++, prodInst.getProdId());//PRODUCT_ID,
			ps.setString(index++, prodInst.getPaymentModeCd());//PAYMENT_MODE_CD,
			ps.setString(index++, prodInst.getStateCd());//STATE_CD,
			ps.setString(index++, prodInst.getLanId());//LAN_ID,
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());//STATE_DATE
			
			ps.setString(index++, prodInst.getUserName());//USER_NAME,
			ps.setString(index++, prodInst.getUimNO());//UIM_NO,
			ps.setString(index++, prodInst.getOldUimNO());//OLD_UIM_NO,
					
			ps.setLong(index++, Long.valueOf(prodInstId).longValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#updateProdInst ex.", e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
		}		
	}
	/**
	 * 根据产品实例修改产品实例号码、产品类型
	 * @param prodInstId
	 * @param newAccNbr
	 * @param newProductId
	 * @throws Exception
	 */
	public void updateProdInstAccNbr(String prodInstId,String newAccNbr,String newProductId) throws Exception{
		Connection conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		String sql = "UPDATE PROD_INST SET ACC_NBR = ?,product_id=? WHERE prod_inst_id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, newAccNbr);
			ps.setString(2, newProductId);
			ps.setString(3, prodInstId);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("#updateProdInstAccNbr ex.", e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
		}		
	}
	
	/**
	 * 根据ACC_NBR修改UIM卡号和用户旧UIM卡号
	 * @param newState
	 * @param prodInstId
	 * @throws SQLException
	 */
	public void updateProdInstUIMNO(String uimNO,String oldUimNO ,String acc_nbr) throws SQLException{
		Connection conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		String sql = "UPDATE PROD_INST SET UIM_NO= ?, OLD_UIM_NO =? WHERE ACC_NBR = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, uimNO);
			ps.setString(2, oldUimNO);
			ps.setString(3, acc_nbr);
			int i=ps.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			DAOUtils.closeStatement(ps);
			logger.error("#updateProdInstUIMNO ex.",e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
		}
	}
	
	
	/**
	 * 根据产品实例修改付费类型
	 * @param newState
	 * @param prodInstId
	 * @throws SQLException
	 */
	public void updateProdInstPayMode(String newState ,String prodInstId) throws SQLException{
		Connection conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		String sql = "UPDATE PROD_INST SET PAYMENT_MODE_CD = ? WHERE prod_inst_id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, newState);
			ps.setString(2, prodInstId);
			ps.executeUpdate();
		} catch (SQLException e) {
			DAOUtils.closeStatement(ps);
			logger.error("#updateProdInstPayMode ex.",e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 根据产品实例修改用户状态
	 * @param newState
	 * @param prodInstId
	 * @throws Exception
	 */
	public void updateProdInstUserState(String newState,String prodInstId) throws Exception{
		Connection conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		PreparedStatement ps = null;
		String sql = "UPDATE PROD_INST SET STATE_CD = ?,STATE_DATE=? WHERE prod_inst_id = ? ";
		try {
			ps = conn.prepareStatement(sql);
			if(null!=newState && "000".equals(newState)){
				ps.setString(1, ProdInstVO.StateOfEffective);
			}else{
				ps.setString(1, newState);
			}
			ps.setTimestamp(2, DAOUtils.getCurrentTimestamp());
			ps.setString(3, prodInstId);
			ps.executeUpdate();
		} catch (SQLException e) {
			DAOUtils.closeStatement(ps);
			logger.error("#updateProdInstUserState ex.",e);
			throw e;
		}
		finally{
			DAOUtils.closeStatement(ps);
		}
	}
	
	/**
	 * 通过号码、多个产品类型查询产品实例
	 * @param accNbr
	 * @param productId
	 * @return
	 */
	public ProdInstVO qryProdInstByAccNbrAndProductIds(String accNbr,String productIds) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdInstVO prodInst = new ProdInstVO();
		String sql = "select * from PROD_INST where PRODUCT_ID in ("+"torepalce"+") and ACC_NBR=? and STATE_CD in(?,?,?)";
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			String [] types=productIds.split(",");
			String where="";
			for(int j=0;j<types.length;j++){
				if(j==0){
					where+="?";
				}else{
					where+=",?";
				}
			}
			sql=sql.replaceAll("torepalce", where);
			ps = conn.prepareStatement(sql);
			int i=1;
			for(int j=0;j<types.length;j++){
				ps.setString(i++, types[j]);
			}
			ps.setString(i++, accNbr);
			ps.setString(i++, ProdInstVO.StateOfEffective);
			ps.setString(i++, ProdInstVO.StateOfDowntime);
			ps.setString(i++, ProdInstVO.StateOfArrearsDown);
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
			}
		} catch (SQLException e) {
			logger.error("#qryProdInstByAccNbrAndProductId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}
	/**
	 * 通过号码、多个产品类型查询产品实例,查非00X的用户
	 * @param accNbr
	 * @param productId
	 * @return
	 */
	public ProdInstVO qryProdInstByAccNbrAndProductIds_GX(String accNbr,String productIds) throws Exception{
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdInstVO prodInst = new ProdInstVO();
		String sql = "select * from PROD_INST where PRODUCT_ID in ("+"torepalce"+") and ACC_NBR=? and STATE_CD <> '00X'";
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			String [] types=productIds.split(",");
			String where="";
			for(int j=0;j<types.length;j++){
				if(j==0){
					where+="?";
				}else{
					where+=",?";
				}
			}
			sql=sql.replaceAll("torepalce", where);
			ps = conn.prepareStatement(sql);
			int i=1;
			for(int j=0;j<types.length;j++){
				ps.setString(i++, types[j]);
			}
			ps.setString(i++, accNbr);
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
			}
		} catch (SQLException e) {
			logger.error("#qryProdInstByAccNbrAndProductId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}
	public ProdInstVO qryProdInstByAccNbrAndProductNbr(String accNbr,
			String prodId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdInstVO prodInst = null;
		String sql = "select * from PROD_INST where PRODUCT_ID=(select p.product_id from product p where p.product_nbr=?) and ACC_NBR=? and STATE_CD in(?,?,?)";
		try {
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			ps = conn.prepareStatement(sql );
			ps.setString(1, prodId);
			ps.setString(2, accNbr);
			ps.setString(3, ProdInstVO.StateOfEffective);
			ps.setString(4, ProdInstVO.StateOfDowntime);
			ps.setString(5, ProdInstVO.StateOfArrearsDown);
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst = new ProdInstVO();
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
				
				prodInst.setUserName(rs.getString("USER_NAME"));
				prodInst.setUimNO(rs.getString("UIM_NO"));
				prodInst.setOldUimNO(rs.getString("OLD_UIM_NO"));
				
			}
		} catch (SQLException e) {
			logger.error("#qryProdInstByAccNbrAndProductNbr ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}
	
	/**
	 * 根据用户号码查询用户实例ID
	 * @param accNbr
	 * @return
	 */
	public String getProdInstIdByAccBnr(String accNbr)throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String productInstId="";
		String sql = "select PROD_INST_ID from PROD_INST where ACC_NBR=? ";
		try{
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, accNbr);
			rs=ps.executeQuery();
			if(rs.next()){
				productInstId= rs.getString("PROD_INST_ID");
			}
		}catch (SQLException e) {
			logger.error("#qryProdInstByAccNbrAndProductNbr ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return productInstId;
	}
	/**
	 * 新增产品实例
	 * @param prodInst
	 * @throws Exception
	 */
	public boolean isExitProdInst(String prodInstId) throws Exception{
		PreparedStatement ps1 = null;
		String sql_exit_check="select pi.prod_inst_id from prod_inst pi where pi.prod_inst_id=? ";
		boolean isExit=false;
			//判断是否已经存在此用户实例了
		if(null!=prodInstId && !"".equals(prodInstId)){
			Connection conn1=null;
			ResultSet ret=null;
			try {
				conn1 = ConnectionContext.getContext().getConnection(
						JNDINames.CRM_DATASOURCE);
				ps1=conn1.prepareStatement(sql_exit_check);
				ps1.setLong(1, Long.valueOf(prodInstId).longValue());
				ret=ps1.executeQuery();
				if(ret.next()){
					isExit = true;
				}
			} catch (RuntimeException e) {
				throw e;
			}finally{
				DAOUtils.closeResultSet(ret);
				DAOUtils.closeStatement(ps1);
			}
		}
		return isExit;
	}
	public ProdInstVO qryProdInstByProdInstId(String prodInstId) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ProdInstVO prodInst = null;
		try{
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			String sql="select * from PROD_INST where  PROD_INST_ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, prodInstId);
			rs = ps.executeQuery();
			if(rs.next()){
				prodInst = new ProdInstVO();
				prodInst.setAccNbr(rs.getString("ACC_NBR"));
				prodInst.setBeginRentTime(rs.getString("BEGIN_RENT_TIME"));
				prodInst.setCommonRegionId(rs.getString("COMMON_REGION_ID"));
				prodInst.setCreateDate(rs.getString("CREATE_DATE"));
				prodInst.setDbActionType("");
				prodInst.setFinishTime(rs.getString("FINISH_TIME"));
				prodInst.setLanId(rs.getString("LAN_ID"));
				prodInst.setPaymentModeCd(rs.getString("PAYMENT_MODE_CD"));
				prodInst.setProdId(rs.getString("PRODUCT_ID"));
				prodInst.setProdInstId(rs.getString("PROD_INST_ID"));
				prodInst.setProdPassword(rs.getString("PRODUCT_PASSWORD"));
				prodInst.setProdTypeCd(rs.getString("ACC_NBR"));
				prodInst.setStateCd(rs.getString("STATE_CD"));
				prodInst.setStateDate(rs.getString("STATE_DATE"));
				prodInst.setStopRentTime(rs.getString("STOP_RENT_TIME"));
			}
			
		}catch(SQLException e){
			logger.error("#qryProdInstByProdInstId ex.", e);
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodInst;
	}

}
