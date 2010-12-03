package com.ztesoft.vsop.engine.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 订购关系实例功能组件类
 * 
 * @author cooltan
 * 
 */

public class OrderRelationHelpDao {
	private String SEQUENCECODE = "SEQ_ORDER_RELATION_ID";
	private String SEQUENCECODE_MIDDLE = "SEQ_ORDER_RELATION_MIDDLE_ID";
	private int maxRows;
	
	
	private static String SQL_SELECT = "SELECT ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID," +
								"ORDER_CHANNEL,STATE,TO_CHAR(EFF_DATE,'YYYYMMDDHH24MISS') as EFF_DATE,TO_CHAR(EXP_DATE,'YYYYMMDDHH24MISS') as EXP_DATE,PACKAGE_ID,LAN_ID,TO_CHAR(STATE_DATE,'YYYYMMDDHH24MISS') as STATE_DATE,PPROD_OFFER_ID," +
								"ACTIVE_STATE,ACC_NBR,TO_CHAR(CREATE_DATE,'YYYYMMDDHH24MISS') as CREATE_DATE,PROD_TYPE_CD" +
								" FROM ORDER_RELATION a";
	
	//informix
	private static String SQL_SELECT_informix = "SELECT ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID," +
	"ORDER_CHANNEL,STATE,TO_CHAR(EFF_DATE,'%Y%m%d%H%M%S') as EFF_DATE,TO_CHAR(EXP_DATE,'%Y%m%d%H%M%S') as EXP_DATE,PACKAGE_ID,LAN_ID,TO_CHAR(STATE_DATE,'%Y%m%d%H%M%S') as STATE_DATE,PPROD_OFFER_ID," +
	"ACTIVE_STATE,ACC_NBR,TO_CHAR(CREATE_DATE,'%Y%m%d%H%M%S') as CREATE_DATE,PROD_TYPE_CD" +
	" FROM ORDER_RELATION a";

	
	
	private String SQL_INSERT = "INSERT INTO ORDER_RELATION ( ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,PACKAGE_ID,LAN_ID,STATE_DATE,PPROD_OFFER_ID,ACTIVE_STATE,ACC_NBR,CREATE_DATE,PROD_TYPE_CD ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE ORDER_RELATION SET  PROD_INST_ID = ?, PRODUCT_ID = ?, PROD_OFFER_INST_ID = ?, PROD_OFFER_ID = ?, ORDER_CHANNEL = ?, STATE = ?, EFF_DATE = ?, EXP_DATE = ?, PACKAGE_ID = ?, LAN_ID = ?, STATE_DATE = ?, PPROD_OFFER_ID = ?, ACTIVE_STATE = ?, ACC_NBR = ?, CREATE_DATE = ?, PROD_TYPE_CD = ? WHERE ORDER_RELATION_ID = ? ";

	private String SQL_DELETE = "DELETE FROM ORDER_RELATION WHERE ORDER_RELATION_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM ORDER_RELATION ";

	static{
	if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
		SQL_SELECT=SQL_SELECT_informix;
	}
	}
	/**
	 * 通过产品实例、销售品标识、销售品类型查询有效的订购关系实例；
	 * 
	 * @param prodInstId
	 * @param offerId
	 * @return
	 */
	public List qryOfferORSByInstAndOfferId(String prodInstId, String offerId,String offerType) {
		String partCond=null;
		if(OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID.equals(offerType)){
			partCond=" and package_id=? ";
		}else if(OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID.equals(offerType)){
			partCond=" and pprod_offer_id=? ";
		}else{
			partCond=" and prod_offer_id=? ";
		}
		return this.findByCond(" prod_inst_id=?  and state =? "+partCond,
				new String[] { prodInstId, "00A" ,offerId});
	}

	/**
	 * 通过产品实例查询所有的有效订购关系实例；
	 * 
	 * @param prodInstId
	 * @return
	 */
	public List qryORSByProdInstId(String prodInstId) {
		return this.findByCond(" prod_inst_id=? and state =?", new String[] { prodInstId,"00A" });
	}
	/**
	 * 通过产品号码、产品类型查询用户有效的订购关系实例；
	 * 取消订购关系查询只能查在用数据，对端平台退订时可根据状态字段判断注销增值产品不能退订。liuyuming2010-07-22
	 * @param prodInstId
	 * @return
	 */
	public List qryORSByAccNbrAndType(String accNbr) {
//		return this.findByCond(" acc_nbr=? and prod_type_cd=? and state =? ", new String[] {
//				accNbr, prodType ,"00A"});
		return this.findByCond(" acc_nbr=? ", new String[] {
				accNbr});
	}

	/**
	 * 通过产品号码、产品类型查询用户有效的订购关系实例；
	 * 
	 * @param prodInstId
	 * @return
	 */
	public List qryORSByAccNbrAndType(String accNbr, String prodType) {
		if("".equals(prodType)||prodType==null){
			return this.findByCond(" acc_nbr=? and state=?  ", new String[] {
				accNbr,"00A" });
		}else{
			return this.findByCond(" acc_nbr=? and prod_type_cd=? and state=? ", new String[] {
					accNbr,prodType,"00A" });
		}
	}
	/**
	 * 通过产品号码查询用户有效的订购关系实例；
	 * 
	 * @param accNbr
	 * @return
	 */
	public List qryORSByAccNbr(String accNbr) {
		return this.findByCond(" acc_nbr=? and state =? ", new String[] {
				accNbr,"00A"});
	}
	/**
	 * 根据订单对象批量新增订购关系实例；
	 * 
	 * @param aCustomerOrder
	 */
	public void insertOrderRelaionsByCustomerOrder(CustomerOrder aCustomerOrder)
			throws SQLException {
		this.saveOrderRelation(aCustomerOrder);
	}

	/**
	 * 根据订单对象批量修改订购关系实例；
	 * 
	 * @param prodInstId
	 * @param producdtOfferInfos
	 */
	public void modifyORSByCustomerOrder(CustomerOrder aCustomerOrder)throws SQLException {
		this.modifyOrderRelaionsByCustomerOrder(aCustomerOrder);
	}
	/**
	 * 根据订单对象批量修改订购关系实例,ORDER_RELATION_MIDDLE 中间表，GX用
	 * 
	 * @param prodInstId
	 * @param producdtOfferInfos
	 */
	public void modifyORSMIDDByCustomerOrder(CustomerOrder aCustomerOrder)throws SQLException {
		this.modifyOrderRelaionsMiddleByCustomerOrder(aCustomerOrder);
	}

	/**
	 * 根据产品实例、销售品列表批量修改订购关系实例状态
	 * 
	 * @param prodInstId
	 * @param vproducts
	 */
	public boolean batchUpdateORStateByProductOfferInfos(String prodInstId,
			List producdtOfferInfos)throws SQLException {
		Connection conn=null;
		PreparedStatement ps = null;
		StringBuffer prodIdStr = new StringBuffer("");
		int idx = 0;
		for (Iterator iterator = producdtOfferInfos.iterator(); iterator.hasNext();) {
			ProductOfferInfo poffer = (ProductOfferInfo)iterator.next();
			List vproductList = poffer.getVproductInfoList();
			for (Iterator vprodItr = vproductList.iterator(); vprodItr.hasNext();) {
				VproductInfo vproduct = (VproductInfo) vprodItr.next();
				if(idx == 0 ) {
					prodIdStr.append(vproduct.getVProductId());
				}else{
					prodIdStr.append("," + vproduct.getVProductId());
				}
				idx++;
			}
		}
		String updateSql = "";
		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			updateSql = "update ORDER_RELATION  set STATE = ?, ACTIVE_STATE=?,state_date=current "
					+ " where  PRODUCT_ID in (#toreplace#) "
					+ " and PROD_INST_ID =? " + " and STATE=?";
		}else if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_ORACLE)){
			updateSql = "update ORDER_RELATION r set STATE = ?, ACTIVE_STATE=?,state_date=sysdate "
					+ " where  PRODUCT_ID in (#toreplace#) "
					+ " and PROD_INST_ID =? " + " and r.STATE=?";
		}
		
		String [] sProdIds=(prodIdStr.toString()).split(",");
		String whereCond="";
		if(sProdIds!=null&&sProdIds.length>0){
			for(int j=0;j<sProdIds.length;j++){
				if(j==0){
					whereCond+="?";
				}else{
					whereCond+=",?";
				}
			}
			updateSql=updateSql.replaceAll("#toreplace#", whereCond);
		}else{
			return false;//如果没有退订的增值产品返回失败
		}
		int result;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			
			ps = conn.prepareStatement(updateSql);
			int i=1;
			ps.setString(i++, OrderConstant.orderStateOfDelete);
			ps.setString(i++, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			for(int j=0;j<sProdIds.length;j++){
				String eq = sProdIds[j];
				ps.setString(i++,sProdIds[j]);
				String e = sProdIds[j];
			}
			ps.setString(i++,prodInstId);
			ps.setString(i++, OrderConstant.orderStateOfCreated);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return result > 0;
	}

	/**
	 * 根据产品实例修改订购关系实例号码、产品类型；
	 * 
	 * @param prodInstId
	 * @param accNbr
	 * @param productId
	 */
	public void updateORAccNbrProductByProdInstId(String prodInstId,
			String accNbr, String productId)throws SQLException {
		Connection conn =  null;
		PreparedStatement updateRelationPs = null;
		String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码
		String updateRelation = "update ORDER_RELATION set ACC_NBR=?,prod_type_cd=? WHERE PROD_INST_ID = ? ";
		//用户改号时广西需要更新状态时间为当前时间，目的为了做订购关系文件比对 liuyuming 2010-09-22
		if(CrmConstants.GX_PROV_CODE.equals(provinceCode)){
			updateRelation = "update ORDER_RELATION set ACC_NBR=?,prod_type_cd=?,STATE_DATE=sysdate WHERE PROD_INST_ID = ? ";
		}
		try {
			conn =  ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			updateRelationPs = conn.prepareStatement(updateRelation);
			updateRelationPs.setString(1, accNbr);
			updateRelationPs.setString(2, productId);
			updateRelationPs.setString(3, prodInstId);		
			updateRelationPs.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally{
			DAOUtils.closeStatement(updateRelationPs);
		}		
	}

	/**
	 * 根据产品实例修改订购关系实例状态为失效
	 * 
	 * @param prodInstId
	 */
	public void updateORSExpireByProdInstId(String prodInstId)throws SQLException {
		Connection conn =  null;
		PreparedStatement updateRelationPs = null;
		//支持ORACLE和INFORMIX
		String updateRelation = "update ORDER_RELATION set state=?,state_date=sysdate WHERE PROD_INST_ID = ? ";
		
		String updateRelation_informix = "update ORDER_RELATION set state=?,state_date=current WHERE PROD_INST_ID = ? ";
		
		
		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			updateRelation=updateRelation_informix;
		}
		
		try {
			conn =  ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			updateRelationPs = conn.prepareStatement(updateRelation);
			updateRelationPs.setString(1, OrderConstant.orderStateOfDelete);
			updateRelationPs.setString(2, prodInstId);		
			updateRelationPs.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally{
			DAOUtils.closeStatement(updateRelationPs);
		}	
	}

	/**
	 * 根据订购关系实例对象更新对应的订购关系实例信息
	 * 
	 * @param vo
	 */
	public void updateORByORObject(OrderRelationVO vo) {
	}
	/**
	 * 通过产品实例标识、增值产品标识查询订购关系实例 如果不存在返回null
	 * @param prodInstId
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	public OrderRelationVO qryORByProdInstIdAndProductId(String prodInstId,
			String productId)throws SQLException{
		List ls= this.findByCond(" prod_inst_id=? and state =? and product_id=?", 
				new String[] { prodInstId,"00A",productId });
		if(ls!=null&&ls.size()>0){
			return (OrderRelationVO)ls.get(0);
		}else{
			return null;
		}
	}

	public List findByCond(String whereCond, String[] para)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			if (para != null) {
				for (int i = 0; i < para.length; i++) {
					stmt.setString(i + 1, para[i]);
				}
			}
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
		}
	}

	private void populateDto(OrderRelationVO vo, ResultSet rs)
			throws SQLException {
		vo.setOrderRelationId(DAOUtils.trimStr(rs
				.getString("ORDER_RELATION_ID")));
		vo.setProdInstId(DAOUtils.trimStr(rs.getString("PROD_INST_ID")));
		vo.setProdId(DAOUtils.trimStr(rs.getString("PRODUCT_ID")));
		vo.setProdOffInstId(DAOUtils
				.trimStr(rs.getString("PROD_OFFER_INST_ID")));
		vo.setProdOffId(DAOUtils.trimStr(rs.getString("PROD_OFFER_ID")));
		vo.setOrderChn(DAOUtils.trimStr(rs.getString("ORDER_CHANNEL")));
		vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
		vo.setEffDate(DAOUtils.trimStr(rs.getString("EFF_DATE")));
		vo.setExpDate(DAOUtils.trimStr(rs.getString("EXP_DATE")));
		vo.setPackageId(DAOUtils.trimStr(rs.getString("PACKAGE_ID")));
		vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
		vo.setStateDate(DAOUtils.trimStr(rs.getString("STATE_DATE")));
		vo.setPprodOffId(DAOUtils.trimStr(rs.getString("PPROD_OFFER_ID")));
		vo.setActiveState(DAOUtils.trimStr(rs.getString("ACTIVE_STATE")));
		vo.setAccNbr(DAOUtils.trimStr(rs.getString("ACC_NBR")));
		vo.setCreateDate(DAOUtils
				.trimStr(rs.getString("CREATE_DATE")));
		vo.setProdTypeCd(DAOUtils.trimStr(rs.getString("PROD_TYPE_CD")));
	}

	private ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			OrderRelationVO vo = new OrderRelationVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	private void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			int index = 1;
			if ("".equals(((OrderRelationVO) vo).getOrderRelationId())) {
				((OrderRelationVO) vo).setOrderRelationId(null);
			}
			stmt
					.setString(index++, ((OrderRelationVO) vo)
							.getOrderRelationId());
			if ("".equals(((OrderRelationVO) vo).getProdInstId())) {
				((OrderRelationVO) vo).setProdInstId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdInstId());
			if ("".equals(((OrderRelationVO) vo).getProdId())) {
				((OrderRelationVO) vo).setProdId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdId());
			if ("".equals(((OrderRelationVO) vo).getProdOffInstId())) {
				((OrderRelationVO) vo).setProdOffInstId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdOffInstId());
			if ("".equals(((OrderRelationVO) vo).getProdOffId())) {
				((OrderRelationVO) vo).setProdOffId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdOffId());
			stmt.setString(index++, ((OrderRelationVO) vo).getOrderChn());
			stmt.setString(index++, ((OrderRelationVO) vo).getState());
			stmt.setDate(index++, DAOUtils.parseDateTime(((OrderRelationVO) vo)
					.getEffDate()));
			stmt.setDate(index++, DAOUtils.parseDateTime(((OrderRelationVO) vo)
					.getExpDate()));
			if ("".equals(((OrderRelationVO) vo).getPackageId())) {
				((OrderRelationVO) vo).setPackageId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getPackageId());
			stmt.setString(index++, ((OrderRelationVO) vo).getLanId());
			stmt.setDate(index++, DAOUtils.parseDateTime(((OrderRelationVO) vo)
					.getStateDate()));
			if ("".equals(((OrderRelationVO) vo).getPprodOffId())) {
				((OrderRelationVO) vo).setPprodOffId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getPprodOffId());
			if ("".equals(((OrderRelationVO) vo).getActiveState())) {
				((OrderRelationVO) vo).setActiveState(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getActiveState());
			stmt.setString(index++, ((OrderRelationVO) vo).getAccNbr());
			stmt.setDate(index++, DAOUtils.parseDateTime(((OrderRelationVO) vo)
					.getCreateDate()));
			stmt.setString(index++, ((OrderRelationVO) vo).getProdTypeCd());
			int rows = stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(SQL_INSERT, this);
			throw new DAOSystemException("SQLException while insert sql:\n"
					+ SQL_INSERT, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
	}

	private boolean update(String pORDER_RELATION_ID, OrderRelationVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			sql
					.append("UPDATE ORDER_RELATION SET ORDER_RELATION_ID = ?,PROD_INST_ID = ?,PRODUCT_ID = ?,PROD_OFFER_INST_ID = ?,PROD_OFFER_ID = ?,ORDER_CHANNEL = ?,STATE = ?,EFF_DATE = ?,EXP_DATE = ?,PACKAGE_ID = ?,LAN_ID = ?,STATE_DATE = ?,PPROD_OFFER_ID = ?,ACTIVE_STATE = ?,ACC_NBR = ?,CREATE_DATE = ?,PROD_TYPE_CD = ?");
			sql.append(" WHERE  ORDER_RELATION_ID = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((OrderRelationVO) vo).getOrderRelationId())) {
				((OrderRelationVO) vo).setOrderRelationId(null);
			}
			stmt.setString(index++, vo.getOrderRelationId());
			if ("".equals(((OrderRelationVO) vo).getProdInstId())) {
				((OrderRelationVO) vo).setProdInstId(null);
			}
			stmt.setString(index++, vo.getProdInstId());
			if ("".equals(((OrderRelationVO) vo).getProdId())) {
				((OrderRelationVO) vo).setProdId(null);
			}
			stmt.setString(index++, vo.getProdId());
			if ("".equals(((OrderRelationVO) vo).getProdOffInstId())) {
				((OrderRelationVO) vo).setProdOffInstId(null);
			}
			stmt.setString(index++, vo.getProdOffInstId());
			if ("".equals(((OrderRelationVO) vo).getProdOffId())) {
				((OrderRelationVO) vo).setProdOffId(null);
			}
			stmt.setString(index++, vo.getProdOffId());
			stmt.setString(index++, vo.getOrderChn());
			stmt.setString(index++, vo.getState());
			stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
			stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));
			if ("".equals(((OrderRelationVO) vo).getPackageId())) {
				((OrderRelationVO) vo).setPackageId(null);
			}
			stmt.setString(index++, vo.getPackageId());
			stmt.setString(index++, vo.getLanId());
			stmt.setDate(index++, DAOUtils.parseDateTime(vo.getStateDate()));
			if ("".equals(((OrderRelationVO) vo).getPprodOffId())) {
				((OrderRelationVO) vo).setPprodOffId(null);
			}
			stmt.setString(index++, vo.getPprodOffId());
			if ("".equals(((OrderRelationVO) vo).getActiveState())) {
				((OrderRelationVO) vo).setActiveState(null);
			}
			stmt.setString(index++, vo.getActiveState());
			stmt.setString(index++, vo.getAccNbr());
			stmt.setDate(index++, DAOUtils.parseDateTime(vo.getCreateDate()));
			stmt.setString(index++, vo.getProdTypeCd());
			stmt.setString(index++, pORDER_RELATION_ID);
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
		return bResult;
	}

	private boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			sql
					.append("UPDATE ORDER_RELATION SET ORDER_RELATION_ID = ?,PROD_INST_ID = ?,PRODUCT_ID = ?,PROD_OFFER_INST_ID = ?,PROD_OFFER_ID = ?,ORDER_CHANNEL = ?,STATE = ?,EFF_DATE = ?,EXP_DATE = ?,PACKAGE_ID = ?,LAN_ID = ?,STATE_DATE = ?,PPROD_OFFER_ID = ?,ACTIVE_STATE = ?,ACC_NBR = ?,CREATE_DATE = ?,PROD_TYPE_CD = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((OrderRelationVO) vo).getOrderRelationId())) {
				((OrderRelationVO) vo).setOrderRelationId(null);
			}
			stmt
					.setString(index++, ((OrderRelationVO) vo)
							.getOrderRelationId());
			if ("".equals(((OrderRelationVO) vo).getProdInstId())) {
				((OrderRelationVO) vo).setProdInstId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdInstId());
			if ("".equals(((OrderRelationVO) vo).getProdId())) {
				((OrderRelationVO) vo).setProdId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdId());
			if ("".equals(((OrderRelationVO) vo).getProdOffInstId())) {
				((OrderRelationVO) vo).setProdOffInstId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdOffInstId());
			if ("".equals(((OrderRelationVO) vo).getProdOffId())) {
				((OrderRelationVO) vo).setProdOffId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getProdOffId());
			stmt.setString(index++, ((OrderRelationVO) vo).getOrderChn());
			stmt.setString(index++, ((OrderRelationVO) vo).getState());
			stmt.setDate(index++, DAOUtils.parseDateTime(((OrderRelationVO) vo)
					.getEffDate()));
			stmt.setDate(index++, DateUtil.parseDateTime(((OrderRelationVO) vo)
					.getExpDate()));
			if ("".equals(((OrderRelationVO) vo).getPackageId())) {
				((OrderRelationVO) vo).setPackageId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getPackageId());
			stmt.setString(index++, ((OrderRelationVO) vo).getLanId());
			stmt.setDate(index++, DateUtil.parseDateTime(((OrderRelationVO) vo)
					.getStateDate()));
			if ("".equals(((OrderRelationVO) vo).getPprodOffId())) {
				((OrderRelationVO) vo).setPprodOffId(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getPprodOffId());
			if ("".equals(((OrderRelationVO) vo).getActiveState())) {
				((OrderRelationVO) vo).setActiveState(null);
			}
			stmt.setString(index++, ((OrderRelationVO) vo).getActiveState());
			stmt.setString(index++, ((OrderRelationVO) vo).getAccNbr());
			stmt.setDate(index++, DateUtil.parseDateTime(((OrderRelationVO) vo)
					.getCreateDate()));
			stmt.setString(index++, ((OrderRelationVO) vo).getProdTypeCd());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
		}
		return bResult;
	}

	private void saveOrderRelation(CustomerOrder order) throws SQLException {
		String prodInstId = order.getProdInstId();
		for (Iterator itr = order.getProductOfferInfoList().iterator(); itr
				.hasNext();) {
			ProductOfferInfo productOffer = (ProductOfferInfo) itr.next();
			saveOrderRelation(prodInstId, productOffer, order.getOrderChn(),
					order.getStatus(), order.getProdId(), order.getLanId(),
					order.getAccNbr());
		}
	}
	/**
	 * 
	 * @param prodInstId产品实例标识
	 * @param productOffer 销售品信息
	 * @param orderChannel 订购渠道
	 * @param state 状态
	 * @param productId 产品类型
	 * @param lanId 本地网编码
	 * @param accNbr 产品号码
	 * @return
	 * @throws SQLException
	 */
	private boolean saveOrderRelation(String prodInstId,
			ProductOfferInfo productOffer, String orderChannel, String state,
			String productId, String lanId, String accNbr) throws SQLException {
		String prodOfferId = "";
		String pprodOfferId = "";
		String packageId = "";
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement deletePs = null;
		int result = 0;
		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.DEFAULT_DATASOURCE);
			String inserSql = "INSERT INTO ORDER_RELATION(ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PPROD_OFFER_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,PACKAGE_ID,LAN_ID,STATE_DATE,ACTIVE_STATE,ACC_NBR,PROD_TYPE_CD,CREATE_DATE)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(inserSql);
			deletePs = conn
					.prepareStatement("delete from ORDER_RELATION where "
							+ " PROD_INST_ID = ? and PRODUCT_ID=?");
			List vprod = productOffer.getVproductInfoList();
			for (Iterator iterator = vprod.iterator(); iterator.hasNext();) {
				VproductInfo vproduct = (VproductInfo) iterator.next();
				//江西对替换规则特殊处理
				if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
					if(OrderConstant.VProductActionTypeOfReplace.equals(vproduct.getDbActionType())){
						replaceOrderRelation(prodInstId,vproduct,true);
					}
				}
				// 先删除再新增
				deletePs.setString(1, prodInstId);
				deletePs.setString(2, vproduct.getVProductId());
				deletePs.addBatch();

				if (OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID
						.equals(productOffer.getOfferType())) {// 传统＋增值销售品
					pprodOfferId = productOffer.getOfferId();
					prodOfferId = DcSystemParamManager.getInstance()
							.getofferIdByProductId(vproduct.getVProductId());
				} else if (OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID
						.equals(productOffer.getOfferType())) {// 纯增值捆绑销售品
					packageId = productOffer.getOfferId();
					prodOfferId = DcSystemParamManager.getInstance()
							.getofferIdByProductId(vproduct.getVProductId());
				} else if (OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID
						.equals(productOffer.getOfferType())) {// 纯增值销售品
					prodOfferId = productOffer.getOfferId();
				}
				int index = 1;
				String seq = "";
				if(vproduct.getVProductInstID()!=null&&!"".equals(vproduct.getVProductInstID())){
					seq = vproduct.getVProductInstID().trim();
				}
				if (seq==null||"".equals(seq)) {
					SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
					seq = aSequenceManageDAOImpl.getNextSequence(SEQUENCECODE);
					vproduct.setVProductInstID(seq);
				}
				ps.setString(index++, seq); // ORDER_RELATION_ID
				ps.setString(index++, prodInstId); // PROD_INST_ID
				ps.setString(index++, vproduct.getVProductId());// PRODUCT_ID
				ps.setString(index++, pprodOfferId);// PPROD_OFFER_ID
				ps.setString(index++, "");// PROD_OFFER_INST_ID
				ps.setString(index++, prodOfferId);// PROD_OFFER_ID,直接写产品标识,这个标识与虚拟销售品标识一致
				ps.setString(index++, orderChannel);// ORDER_CHANNEL
				ps.setString(index++, state);// STATE
				String dateType1 = CrmConstants.DATE_TIME_FORMAT;
				String effdate = productOffer.getEffDate(); // 得到销售品的生失效时间
				if (null != effdate && !"".equals(effdate)) {
					if (effdate.length() == 14)
						dateType1 = CrmConstants.DATE_TIME_FORMAT_14;
				}
				ps.setTimestamp(index++, DAOUtils.parseTimestamp(effdate,
						dateType1));// EFF_TIME,
				String expdate = productOffer.getExpDate();
				String dateType2 = CrmConstants.DATE_TIME_FORMAT;
				if (null != expdate && !"".equals(expdate)) {
					if (expdate.length() == 14)
						dateType2 = CrmConstants.DATE_TIME_FORMAT_14;
				}
				ps.setTimestamp(index++, DAOUtils.parseTimestamp(expdate,
						dateType2));// EXP_TIME,
				ps.setString(index++, packageId);// PACKAGE_ID
				ps.setString(index++, lanId);// LAN_ID
				ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// STATE_DATE
				ps
						.setString(index++,
								OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);// 待激活
				ps.setString(index++, accNbr);// ACC_NBR
				ps.setString(index++, productId);// PROD_TYPE_CD
				ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// CREATE_DATE
				ps.addBatch();
			}
			if (vprod.size() > 0) {
				deletePs.executeBatch();
				ps.executeBatch();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(deletePs);
		}
		return result > 0;
	}
	/**
	 * 根据订单修改增值产品 默认订单不用二次确认。。
	 * @param order
	 * @throws SQLException
	 */
	private void modifyOrderRelaionsByCustomerOrder(CustomerOrder order) throws SQLException {
		String prodInstId = order.getProdInstId();
		List productOfferInfoList = order.getProductOfferInfoList();
		String orderChnannel=order.getOrderChn();
		String accNbr=order.getAccNbr();
		String prodType=order.getProdId();
		String orderState=order.getStatus();
		String lanCode=order.getLanId();
		boolean sendActiveFlag=order.getSendActiveFlag();
		
		if(productOfferInfoList != null && productOfferInfoList.size()>0){
			for (Iterator iterator2 = productOfferInfoList.iterator(); iterator2.hasNext();) {
				ProductOfferInfo aProductOfferInfo=(ProductOfferInfo)iterator2.next();
				List vproductInfoList=aProductOfferInfo.getVproductInfoList();
				for (Iterator iterator = vproductInfoList.iterator(); iterator.hasNext();) {
					VproductInfo vproduct = (VproductInfo) iterator.next();
					if(OrderConstant.orderTypeOfAdd.
							equals(vproduct.getActionType())){//订购
						vproduct.setState(OrderConstant.orderStateOfCreated);
						String offerId=aProductOfferInfo.getOfferId();
						String offerType=aProductOfferInfo.getOfferType();
						this.createOrderRelation(orderChnannel,vproduct, orderState, accNbr, 
								prodType, prodInstId,offerId,offerType,lanCode,sendActiveFlag);
					}else if(OrderConstant.VProductActionTypeOfCancelFromPackage.
							equals(vproduct.getActionType())){//产品退出套餐
						this.cancelOrderRelationFromPackage(prodInstId,vproduct,aProductOfferInfo.getOfferId(),sendActiveFlag);
					}else if(OrderConstant.orderTypeOfDel.
							equals(vproduct.getActionType())){//退订
						vproduct.setState(OrderConstant.orderStateOfDelete);
						this.deleleOrderRelation(prodInstId,vproduct,sendActiveFlag);
					}else if(OrderConstant.VProductActionTypeOfPendding.
							equals(vproduct.getActionType())){//加入套餐
						this.penndingPackage(prodInstId,vproduct,aProductOfferInfo.getOfferId(),sendActiveFlag);
					}else if(OrderConstant.VProductActionTypeOfReplace.
							equals(vproduct.getActionType())){//替换
						this.replaceOrderRelation(prodInstId,vproduct,sendActiveFlag);
					}
				}
			}
		}
	}
	
	/**
	 * 根据订单修改增值产品 默认订单不用二次确认。。在订购关系同步时，VSOP暂无用户实例信息，更新到中间表,后续定时任务处理,GX用
	 * @param order
	 * @throws SQLException
	 */
	private void modifyOrderRelaionsMiddleByCustomerOrder(CustomerOrder order) throws SQLException {
		String prodInstId = order.getProdInstId();
		List productOfferInfoList = order.getProductOfferInfoList();
		String orderChnannel=order.getOrderChn();
		String accNbr=order.getAccNbr();
		String prodType=order.getProdId();
		String orderState=order.getStatus();
		String lanCode=order.getLanId();
		
		if(productOfferInfoList != null && productOfferInfoList.size()>0){
			for (Iterator iterator2 = productOfferInfoList.iterator(); iterator2.hasNext();) {
				ProductOfferInfo aProductOfferInfo=(ProductOfferInfo)iterator2.next();
				List vproductInfoList=aProductOfferInfo.getVproductInfoList();
				for (Iterator iterator = vproductInfoList.iterator(); iterator.hasNext();) {
					VproductInfo vproduct = (VproductInfo) iterator.next();
					if(OrderConstant.orderTypeOfAdd.
							equals(vproduct.getActionType())){//订购
						vproduct.setState(OrderConstant.orderStateOfCreated);
						String offerId=aProductOfferInfo.getOfferId();
						String offerType=aProductOfferInfo.getOfferType();
						this.createOrderRelationMiddle(orderChnannel,vproduct, orderState, accNbr, 
								prodType, prodInstId,offerId,offerType,lanCode);
					}else if(OrderConstant.VProductActionTypeOfCancelFromPackage.
							equals(vproduct.getActionType())){//产品退出套餐
						this.cancelOrderRelationMiddleFromPackage(prodInstId,vproduct,aProductOfferInfo.getOfferId());
					}else if(OrderConstant.orderTypeOfDel.
							equals(vproduct.getActionType())){//退订
						vproduct.setState(OrderConstant.orderStateOfDelete);
						this.deleleOrderRelationMiddle(prodInstId,vproduct);
					}else if(OrderConstant.VProductActionTypeOfPendding.
							equals(vproduct.getActionType())){//加入套餐
						this.penndingPackageMiddle(prodInstId,vproduct,aProductOfferInfo.getOfferId());
					}else if(OrderConstant.VProductActionTypeOfReplace.
							equals(vproduct.getActionType())){//替换
						this.replaceOrderRelationMiddle(prodInstId,vproduct);
					}
				}
			}
		}
	}
	/**
	 * 替换单个增值产品
	 * @param prodInstId
	 * @param vproduct
	 * @throws SQLException
	 */
	public void replaceOrderRelation(String prodInstId, VproductInfo vproduct,boolean sendActiveFlag) throws SQLException {
		Connection conn = null;
		String sql = "update ORDER_RELATION set PRODUCT_ID = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		
		String sql_informix = "update ORDER_RELATION set PRODUCT_ID = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";

		
		
		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			sql=sql_informix;
		}
		
		
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, vproduct.getVProductId());
			if(sendActiveFlag){//true 需要送平台激活
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			}else{
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			}
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getOldVProductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 替换单个增值产品
	 * @param prodInstId
	 * @param vproduct
	 * @throws SQLException
	 */
	public void replaceOrderRelationMiddle(String prodInstId, VproductInfo vproduct) throws SQLException {
		Connection conn = null;
		String sql = "update ORDER_RELATION_MIDDLE set PRODUCT_ID = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		String sql_informix = "update ORDER_RELATION_MIDDLE set PRODUCT_ID = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";

		
		
		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			sql=sql_informix;
		}
		
		
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, vproduct.getVProductId());
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getOldVProductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}

	/**
	 * 单个增值产品加入套餐
	 * @param prodInstId
	 * @param vproduct
	 * @param pproductOfferId
	 * @throws SQLException
	 */
	public void penndingPackage(String prodInstId, VproductInfo vproduct,String pproductOfferId,boolean sendActiveFlag) throws SQLException {
		Connection conn = null;
		
		//支持ORACLE和 INFORMIX
		String sql = "update ORDER_RELATION set PPROD_OFFER_ID = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		String sql_informix = "update ORDER_RELATION set PPROD_OFFER_ID = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";

		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			sql=sql_informix;
		}
		
				
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, pproductOfferId);
			if(sendActiveFlag){//true 需要送平台激活
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			}else{
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			}
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 单个增值产品加入套餐
	 * @param prodInstId
	 * @param vproduct
	 * @param pproductOfferId
	 * @throws SQLException
	 */
	public void penndingPackageMiddle(String prodInstId, VproductInfo vproduct,String pproductOfferId) throws SQLException {
		Connection conn = null;
		String sql = "update ORDER_RELATION_MIDDLE set PPROD_OFFER_ID = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		String sql_informix = "update ORDER_RELATION_MIDDLE set PPROD_OFFER_ID = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";

		
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, pproductOfferId);
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 删除单个增值产品
	 * @param prodInstId
	 * @param vproduct
	 * @throws SQLException
	 */
	public void deleleOrderRelation(String prodInstId, VproductInfo vproduct,boolean sendActiveFlag) throws SQLException {
		Connection conn = null;
		String sql = "update ORDER_RELATION set STATE = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		String sql_informix= "update ORDER_RELATION set STATE = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";

		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
		
		PreparedStatement ps = null;
		
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, vproduct.getState());
			if(sendActiveFlag){//true 需要送平台激活
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			}else{
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			}
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 删除单个增值产品
	 * @param prodInstId
	 * @param vproduct
	 * @throws SQLException
	 */
	public void deleleOrderRelationMiddle(String prodInstId, VproductInfo vproduct) throws SQLException {
		Connection conn = null;
		String sql = "update ORDER_RELATION_MIDDLE set STATE = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		String sql_informix = "update ORDER_RELATION_MIDDLE set STATE = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";


		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
		PreparedStatement ps = null;
		
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, vproduct.getState());
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 新增单个增值产品
	 * @param systemId 订购渠道
	 * @param vprod 订单增值产品
	 * @param state 状态
	 * @param productNo 号码
	 * @param productId 号码类型
	 * @param productInstId 产品实例标识
	 * @offerId 销售品标识
	 * @lanId 本地网编码
	 * @return
	 * @throws SQLException
	 */
	public boolean createOrderRelation(String systemId, VproductInfo vproduct, 
			String state, String productNo, String productId, 
			String productInstId,String offerId,String offerType,String lanId,boolean sendActiveFlag) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement deletePs = null;
		int index = 1;
		int result = 0;
		try {
			String inserSql = "INSERT INTO ORDER_RELATION(ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PPROD_OFFER_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,PACKAGE_ID,LAN_ID,STATE_DATE,ACTIVE_STATE,ACC_NBR,PROD_TYPE_CD,CREATE_DATE)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(inserSql);
			deletePs = conn.prepareStatement("delete from ORDER_RELATION where PROD_INST_ID = ? and " +
					" PRODUCT_ID=?");
			// 先删除再新增
			deletePs.setString(1, productInstId);
			deletePs.setString(2, vproduct.getVProductId());

			if (vproduct.getVProductInstID()==null||"".equals(vproduct.getVProductInstID())) {
				SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
				String seq = aSequenceManageDAOImpl
						.getNextSequence(SEQUENCECODE);
				vproduct.setVProductInstID(seq);
			}
			Map product_prodOfferMap = DcSystemParamManager.getInstance().getProductId2prodOfferId();
			String prodOfferId = Const.getStrValue(product_prodOfferMap,vproduct.getVProductId());// findOfferByProductDb(vproduct.getVProductID(),conn);
			String pprodOfferId = "";
			String packageId = "";
			if("2".equals(offerType)){
				pprodOfferId = offerId;
			}
			else if("1".equals(offerType)){
				packageId = offerId;
			}
			ps.setString(index++, vproduct.getVProductInstID()); // ORDER_RELATION_ID
			ps.setString(index++, productInstId); // PROD_INST_ID
			ps.setString(index++, vproduct.getVProductId());// PRODUCT_ID
			ps.setString(index++, pprodOfferId);// PPROD_OFFER_ID
			ps.setString(index++, "");// PROD_OFFER_INST_ID
			ps.setString(index++, prodOfferId);// PROD_OFFER_ID
			ps.setString(index++, systemId);// ORDER_CHANNEL
			ps.setString(index++,(state == null || "".equals(state)) ? vproduct.getState(): state);// STATE
			ps.setTimestamp(index++, DAOUtils.parseTimestamp(vproduct.getEffDate()));// EFF_TIME,
			ps.setTimestamp(index++, DAOUtils.parseTimestamp(vproduct.getExpDate()));// EXP_TIME,
			ps.setString(index++, packageId);// PACKAGE_ID
			ps.setString(index++, lanId);// LAN_ID
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// STATE_DATE
			if(sendActiveFlag){//true 需要送平台激活
				ps.setString(index++, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			}else{
				ps.setString(index++, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			}
			
			ps.setString(index++, productNo); // acc_nbr
			ps.setString(index++, productId); // PROD_TYPE_CD 主产品id
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// CREATE_DATE
			deletePs.executeUpdate();
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
			DAOUtils.closeStatement(deletePs);
		}
		return result > 0;
	}
	
	/**
	 * 新增单个增值产品
	 * @param systemId 订购渠道
	 * @param vprod 订单增值产品
	 * @param state 状态
	 * @param productNo 号码
	 * @param productId 号码类型
	 * @param productInstId 产品实例标识
	 * @offerId 销售品标识
	 * @lanId 本地网编码
	 * @return
	 * @throws SQLException
	 */
	public boolean createOrderRelationMiddle(String systemId, VproductInfo vproduct, 
			String state, String productNo, String productId, 
			String productInstId,String offerId,String offerType,String lanId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
//		PreparedStatement deletePs = null;
		int index = 1;
		int result = 0;
		try {
			String inserSql = "INSERT INTO ORDER_RELATION_MIDDLE(ORDER_RELATION_ID,PROD_INST_ID,PRODUCT_ID,PPROD_OFFER_ID,PROD_OFFER_INST_ID,PROD_OFFER_ID,ORDER_CHANNEL,STATE,EFF_DATE,EXP_DATE,PACKAGE_ID,LAN_ID,STATE_DATE,ACTIVE_STATE,ACC_NBR,PROD_TYPE_CD,CREATE_DATE)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(inserSql);
//			deletePs = conn.prepareStatement("delete from ORDER_RELATION_MIDDLE where PROD_INST_ID = ? and " +
//					" PRODUCT_ID=?");
// 先删除再新增(操作order_relation_middle表，去掉先删除再新增)
//			deletePs.setString(1, productInstId);
//			deletePs.setString(2, vproduct.getVProductId());

			if (vproduct.getVProductInstID()==null||"".equals(vproduct.getVProductInstID())) {
				SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
				String seq = aSequenceManageDAOImpl
						.getNextSequence(SEQUENCECODE_MIDDLE);
				vproduct.setVProductInstID(seq);
			}
			Map product_prodOfferMap = DcSystemParamManager.getInstance().getProductId2prodOfferId();
			String prodOfferId = Const.getStrValue(product_prodOfferMap,vproduct.getVProductId());// findOfferByProductDb(vproduct.getVProductID(),conn);
			String pprodOfferId = "";
			String packageId = "";
			if("2".equals(offerType)){
				pprodOfferId = offerId;
			}
			else if("1".equals(offerType)){
				packageId = offerId;
			}
			ps.setString(index++, vproduct.getVProductInstID()); // ORDER_RELATION_ID
			ps.setString(index++, productInstId); // PROD_INST_ID
			ps.setString(index++, vproduct.getVProductId());// PRODUCT_ID
			ps.setString(index++, pprodOfferId);// PPROD_OFFER_ID
			ps.setString(index++, "");// PROD_OFFER_INST_ID
			ps.setString(index++, prodOfferId);// PROD_OFFER_ID
			ps.setString(index++, systemId);// ORDER_CHANNEL
			ps.setString(index++,(state == null || "".equals(state)) ? vproduct.getState(): state);// STATE
			ps.setTimestamp(index++, DAOUtils.parseTimestamp(vproduct.getEffDate()));// EFF_TIME,
			ps.setTimestamp(index++, DAOUtils.parseTimestamp(vproduct.getExpDate()));// EXP_TIME,
			ps.setString(index++, packageId);// PACKAGE_ID
			ps.setString(index++, lanId);// LAN_ID
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// STATE_DATE
			ps.setString(index++, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			ps.setString(index++, productNo); // acc_nbr
			ps.setString(index++, productId); // PROD_TYPE_CD 主产品id
			ps.setTimestamp(index++, DAOUtils.getCurrentTimestamp());// CREATE_DATE
//			deletePs.executeUpdate();
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			DAOUtils.closeStatement(ps);
//			DAOUtils.closeStatement(deletePs);
		}
		return result > 0;
	}
	/**
	 * 单个增值产品退出套餐
	 * 
	 * @param prodInstId
	 * @param vproduct
	 * @param productOfferId
	 * @throws SQLException
	 */
	public void cancelOrderRelationFromPackage(String prodInstId,
			VproductInfo vproduct, String productOfferId,boolean sendActiveFlag) throws SQLException {
		Connection conn = null;
		String sql = "update ORDER_RELATION set PACKAGE_ID = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=? and PACKAGE_ID=?";
		
		
		String sql_informix = "update ORDER_RELATION set PACKAGE_ID = ?,ACTIVE_STATE=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=? and PACKAGE_ID=?";

		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
		
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			
			if(sendActiveFlag){//true 需要送平台激活
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_UNACTIVE);
			}else{
				ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			}	
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductId());
			ps.setString(5, productOfferId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	/**
	 * 单个增值产品退出套餐
	 * 
	 * @param prodInstId
	 * @param vproduct
	 * @param productOfferId
	 * @throws SQLException
	 */
	public void cancelOrderRelationMiddleFromPackage(String prodInstId,
			VproductInfo vproduct, String productOfferId) throws SQLException {
		Connection conn = null;
		
		//支持ORACLE和INFORMIX
		String sql = "update ORDER_RELATION_MIDDLE set PACKAGE_ID = ?,ACTIVE_STATE=?,state_date=sysdate " +
				" where PROD_INST_ID=? and PRODUCT_ID=? and PACKAGE_ID=?";
		
		
		String sql_informix = "update ORDER_RELATION set PPROD_OFFER_ID=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
		
		PreparedStatement ps = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, "");
			ps.setString(2, OrderConstant.ORDER_ACTIVE_STATE_SUCCESS);
			ps.setString(3, prodInstId);
			ps.setString(4, vproduct.getVProductId());
			ps.setString(5, productOfferId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
	}
	public List getReletionsByIds(String relationIds) throws SQLException {
		String sql = "select r.ORDER_RELATION_ID,r.PROD_INST_ID,r.PRODUCT_ID,pi.ACC_NBR,r.EFF_DATE,r.EXP_DATE" +
				",PPROD_OFFER_ID,PROD_OFFER_ID,PACKAGE_ID" +
				",(select product_nbr from product where product_id=r.product_id) product_nbr" +
				",(select offer_nbr from prod_offer where PROD_OFFER_ID=r.PROD_OFFER_ID) offer_nbr " +
				",(select offer_nbr from prod_offer where PROD_OFFER_ID=r.PACKAGE_ID) package_nbr " +
				",(select offer_nbr from prod_offer where PROD_OFFER_ID=r.PPROD_OFFER_ID) pprod_offer_nbr " +
				"from ORDER_RELATION r left join PROD_INST pi on r.PROD_INST_ID=pi.PROD_INST_ID " +
				"where r.ORDER_RELATION_ID in (" + relationIds + ")";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List result = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			result = new ArrayList();
			while (rs.next()) {
				Map m = new HashMap();
				m.put("ORDER_RELATION_ID", rs.getString("ORDER_RELATION_ID"));
				m.put("PROD_INST_ID", rs.getString("PROD_INST_ID"));
				m.put("PRODUCT_ID", rs.getString("PRODUCT_ID"));
				m.put("product_nbr", rs.getString("product_nbr"));
				//m.put("SUB_PRODUCT_ID", rs.getString("sub_product_id"));
				m.put("ACC_NBR", rs.getString("ACC_NBR"));
				m.put("EFF_DATE", rs.getString("EFF_DATE"));
				m.put("EXP_DATE", rs.getString("EXP_DATE"));
				String prodOfferNbr = rs.getString("offer_nbr");
				String packageNbr = rs.getString("package_nbr");
				String pprodOfferNbr = rs.getString("pprod_offer_nbr");
				m.put("PROD_OFFER_NBR", prodOfferNbr);
				m.put("PROD_OFFER_TYPE", OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID);
				if(packageNbr!=null && !"".equals(packageNbr)){
					m.put("PROD_OFFER_NBR", packageNbr);
					m.put("PROD_OFFER_TYPE", OrderConstant.PROD_OFFER_TYPE_PACKAGE_ID);
				}
				if(pprodOfferNbr!=null && !"".equals(pprodOfferNbr)){
					m.put("PROD_OFFER_NBR", pprodOfferNbr);
					m.put("PROD_OFFER_TYPE", OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID);
				}
				m.put("PROD_OFFER_ID", rs.getString("PROD_OFFER_ID"));
				m.put("PACKAGE_ID", rs.getString("PACKAGE_ID"));
				m.put("PPROD_OFFER_ID", rs.getString("PPROD_OFFER_ID"));
				result.add(m);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return result;
	}
	
	/**
	 * 根据 用户号码/产品ID/状态 判断是否存在订购关系
	 * 
	 * @param accNbr
	 * @param productId
	 * @param state
	 * @return
	 */
	public String isExistsOrderRelation(String accNbr, String productId, String state) throws SQLException {
		
		//支持ORACLE和INFORMIX
		String sql = "select PPROD_OFFER_ID from ORDER_RELATION r where r.acc_nbr=? and r.product_id=? and r.state=? and rownum=1";
		
		
		String sql_informix = "select first 1 PPROD_OFFER_ID from ORDER_RELATION r where r.acc_nbr=? and r.product_id=? and r.state=? ";
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, accNbr);
			ps.setString(2, productId);
			ps.setString(3, state);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getString(1);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return id;
	}
	
	/**
	 * 根据 用户号码/产品ID/状态 判断是否存在订购关系,判断字段order_relationID
	 * 
	 * @param accNbr
	 * @param productId
	 * @param state
	 * @return
	 */
	public String isExistsOrderRelationOnOrderRelatID(String accNbr, String productId, String state) throws SQLException {

		//oracle
		
		String sql = "select order_relation_id||','||PPROD_OFFER_ID as vas from ORDER_RELATION r where r.acc_nbr=? and r.product_id=? and r.state=? and rownum=1";
		
		//informix
		
		String sql_informix = "select first 1 order_relation_id||','||PPROD_OFFER_ID as vas from ORDER_RELATION r where r.acc_nbr=? and r.product_id=? and r.state=? ";
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, accNbr);
			ps.setString(2, productId);
			ps.setString(3, state);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getString(1);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return id;
	}
	
	/**
	 * 根据 用户号码/产品ID/状态 判断是否存在订购关系,判断字段order_relationID
	 * 
	 * @param accNbr
	 * @param productId
	 * @param state
	 * @return
	 */
	public String isExistsOrderRelationMiddleOnOrderRelatID(String accNbr, String productId, String state) throws SQLException {
		
		//oracle
		//ORDER_RELATION_MIDDLE
		
		String sql = "select order_relation_id||','||PPROD_OFFER_ID as vas from ORDER_RELATION_MIDDLE r where r.acc_nbr=? and r.product_id=? and r.state=? and rownum=1";
		
		//informix
		String sql_informix = "select first 1 order_relation_id||','||PPROD_OFFER_ID as vas from ORDER_RELATION_MIDDLE r where r.acc_nbr=? and r.product_id=? and r.state=? ";
		

		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			sql=sql_informix;
		}
		
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			ps = conn.prepareStatement(sql);
			ps.setString(1, accNbr);
			ps.setString(2, productId);
			ps.setString(3, state);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getString(1);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return id;
	}
	/**
	 * 根据用户号码查询用户实例ID
	 * @param accNbr
	 * @return
	 */
	public String updateProdInstOfferByOrder(ProductOfferInfo productOffer)throws SQLException{
		PreparedStatement ps = null;
		
		//支持ORACLE和INFORMIX
		
		String sql = "update ORDER_RELATION set PPROD_OFFER_ID=?,state_date=sysdate " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		String sql_informix = "update ORDER_RELATION set PPROD_OFFER_ID=?,state_date=current " +
		" where PROD_INST_ID=? and PRODUCT_ID=?";
		
		
		if( CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE").equals(CrmConstants.DB_TYPE_INFORMIX)){
			sql=sql_informix;
		}
		
		try{
			Connection conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			ps = conn.prepareStatement(sql);
			String pprod_offer_id="";
				List productList = productOffer.getVproductInfoList();
				for (int i = 0; i < productList.size();i++){
					VproductInfo product = (VproductInfo) productList.get(i);
					if("0".equals(product.getActionType()))
						pprod_offer_id=productOffer.getOfferId();
					else 
						pprod_offer_id="";
					ps.setString(1,pprod_offer_id);
					ps.setString(2,product.getVProductInstID());
					ps.setString(3, product.getVProductId());
					ps.addBatch();
				}
				ps.executeBatch();
		}catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
		return null;
	}

	/**
	 * 目前的189邮箱一共3种，免费189，5元189和10元189。规则是：档次上升变更，业务允许受理，档次下降变更，业务不允许受理
	 * 例如：用户有免费189，再办理5元189，可以允许办理；用户有10元189，再办理5元189，则不允许办理
	 * 
	 * 检查欲订购的增值产品是否可以替换在用的增值产品
	 * 由于业务规则变更不可预测，用存储过程实现，方便在不改java代码的情况修改业务规则，同时也容易增加某产品实现，比如实现通讯助理的替换
	 * @author yi.chengfeng 2010-10-10 实现189邮箱多个等级的自动替换 增加该方法
	 * @param newVproductId  欲订购的增值产品id
	 * @param oldVproductId  在用的增值产品id
	 * @return
	 * @throws SQLException 
	 */
	public boolean checkRepalceRelation(String newVproductId, String oldVproductId) throws SQLException {
		Connection conn = null;
		CallableStatement proc = null;
		conn = ConnectionContext.getContext().getConnection(
				JNDINames.CRM_DATASOURCE);
		try {
			proc = conn.prepareCall("{ call p_check_replaceRelation(?,?,?) }");
			proc.setString(1, newVproductId);
			proc.setString(2, oldVproductId);
			proc.registerOutParameter(3, Types.VARCHAR);
			proc.execute();
			String resultCode = proc.getString(3);
			if("0".equals(resultCode)) return true;
		} catch (SQLException e) {
			throw e;
		}finally{
			try {
				if(proc != null) proc.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
