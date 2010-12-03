package com.ztesoft.vsop.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.vsop.LegacyDAOUtil;

public class OrderCheckDao {
	public static final String LINE_SPLITER = "#";
	/**
	 * 
	 * @param scope 业务适用范围 0：全国业务 1：省级业务
	 * @return
	 * @throws SQLException 
	 */
	public List exportVsopDataToFile(String host,String scope, String systemCode) throws SQLException {
		// TODO:
		String exportSQL = "select a.* from ORDER_RELATION_QUERY_MID a where a.SYSTEM_CODE in ("+systemCode+") and exists " +
						   "(select 1 from product b where a.product_id=b.product_id " +
						   " and b.order_host in ("+host+") and b.scope = '"+scope+"')";//VSOP系统
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			ps = conn.prepareStatement(exportSQL);
			rs = ps.executeQuery();
			while(rs.next()){
				StringBuffer result = new StringBuffer();
				result.append(rs.getString("SUBSCRIPTION_ID")).append(LINE_SPLITER);
				result.append(rs.getString("PROD_INST_ID")).append(LINE_SPLITER);
				result.append(rs.getString("OA_TYPE")).append(LINE_SPLITER);//ProdSpecCode
				result.append(rs.getString("OA")).append(LINE_SPLITER);
				String packageId = rs.getString("PACKAGE_ID");
				String pprodOfferId = rs.getString("PPRODUCT_OFFER_ID");
				String prodOfferId = rs.getString("PRODUCT_ID");
				String prodOfferType = "0";
				if(!"".equals(packageId)){
					prodOfferType="2";
					prodOfferId = packageId;
				}else if (!"".equals(pprodOfferId)){
					prodOfferType="1";
					prodOfferId = pprodOfferId;
				}
				result.append(rs.getString("PRODUCT_ID")==null?"":rs.getString("PRODUCT_ID")).append(LINE_SPLITER);//增值产品标识, （订购关系比对必须字段）
				result.append(rs.getString("SPID")==null?"":rs.getString("SPID")).append(LINE_SPLITER);
				result.append(prodOfferType==null?"":prodOfferType).append(LINE_SPLITER);//ProductOfferType
				result.append(prodOfferId==null?"":prodOfferId).append(LINE_SPLITER);//PROD_OFFER_ID
				String status = rs.getString("STATUS");
				if(null!=status && !"".equals(status)){
					if("00X".equals(status)){
						status = "6";
					}else status = "0";
				}else status = "";
				result.append(status).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("SUBSCRIBE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EFFECTIVE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EXPIRE_TIME"))).append(LINE_SPLITER);
				result.append(rs.getString("CHANNEL_PLAYER_ID")).append("\n");
				dataList.add(result.toString());
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(rs != null)rs.close();
				if(ps != null)ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		LegacyDAOUtil.commitAndCloseConnection(conn);
		return dataList;
	}

	/**
	 * 
	 * @param scope 业务适用范围 0：全国业务 1：省级业务
	 * @return
	 * @throws SQLException 
	 */
	public List exportVsopDataToIsmpFile(String host,String scope, String systemCode) throws SQLException {
		// TODO:
		String exportSQL = "select a.*,b.prod_class from ORDER_RELATION_QUERY_MID a,product b" +
				" where a.SYSTEM_CODE in ("+systemCode+") and " +
						   " a.product_id=b.product_id " +
						   " and b.order_host in ("+host+") and b.scope = '"+scope+"'";//VSOP系统
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			ps = conn.prepareStatement(exportSQL);
			rs = ps.executeQuery();
			while(rs.next()){
//				Map map = new HashMap();
				StringBuffer result = new StringBuffer();
				String acc_nbr =rs.getString("OA")==null?"":rs.getString("OA");
				String ProdSpecCode =rs.getString("PROD_CLASS")==null?"":rs.getString("PROD_CLASS");
				result.append(getSequence("SEQ_STREAMING_NO",conn)).append(LINE_SPLITER);
				result.append(rs.getString("SUBSCRIPTION_ID")==null?"":rs.getString("SUBSCRIPTION_ID")).append(LINE_SPLITER);
				//result.append(rs.getString("PROD_INST_ID")==null?"":rs.getString("PROD_INST_ID")).append(LINE_SPLITER);
				result.append(acc_nbr).append(LINE_SPLITER);
				result.append(ProdSpecCode).append(LINE_SPLITER);//ProdSpecCode
				result.append(acc_nbr).append(LINE_SPLITER);
				result.append(ProdSpecCode).append(LINE_SPLITER);//ProdSpecCode
				result.append(acc_nbr).append(LINE_SPLITER);
				result.append(ProdSpecCode).append(LINE_SPLITER);//ProdSpecCode
				result.append(rs.getString("WITHDRAWER")==null?"":rs.getString("WITHDRAWER")).append(LINE_SPLITER);
				result.append(rs.getString("WITHDRAW_REASON")==null?"":rs.getString("WITHDRAW_REASON")).append(LINE_SPLITER);
				result.append(rs.getString("SUSPEND_REASON")==null?"":rs.getString("SUSPEND_REASON")).append(LINE_SPLITER);
				result.append(rs.getString("SPID")==null?"":rs.getString("SPID")).append(LINE_SPLITER);
				String prodOfferId = rs.getString("PRODUCT_ID");
				String packageId = rs.getString("PACKAGE_ID");
				String pprodOfferId = rs.getString("PPRODUCT_OFFER_ID");
				String prodOfferType = "0";
				if(!"".equals(packageId)){
					prodOfferType="2";
					prodOfferId = packageId;
				}else if (!"".equals(pprodOfferId)){
					prodOfferType="1";
					prodOfferId = pprodOfferId;
				}
				result.append(prodOfferId==null?"":prodOfferId).append(LINE_SPLITER);//增值产品标识, （订购关系比对必须字段）
				result.append(packageId==null?"":packageId).append(LINE_SPLITER);//ProductOfferType
				result.append(pprodOfferId==null?"":pprodOfferId).append(LINE_SPLITER);//PROD_OFFER_ID
				String status = rs.getString("STATUS");
				if(null!=status && !"".equals(status)){
					if("00X".equals(status)){
						status = "6";
					}else status = "0";
				}else status = "";
				result.append(status).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("SUBSCRIBE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EFFECTIVE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EXPIRE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("SUSPEND_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("RESUME_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("LAST_USE_TIME"))).append(LINE_SPLITER);
				result.append(rs.getString("SILENCE")==null?"":rs.getString("SILENCE")).append(LINE_SPLITER);
				result.append(rs.getString("CHANNEL_PLAYER_ID")).append("\n");
				dataList.add(result.toString());
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if(rs != null)rs.close();
				if(ps != null)ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		LegacyDAOUtil.commitAndCloseConnection(conn);
		return dataList;
	}

	/**
	 * 属主是ISMP的全网业务
	 * @return
	 */
	public List getIsmpData(String host, String scope) {
		// TODO:
		String exportSQL = "select a.* from ORDER_RELATION_QUERY_MID a where exists " +
						   "(select 1 from product b where a.product_id=b.product_id " +
						   " and b.order_host in ("+host+") and b.scope = '"+scope+"')";
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			ps = conn.prepareStatement(exportSQL);
			rs = ps.executeQuery();
			while(rs.next()){
				Map map = new HashMap();
				StringBuffer result = new StringBuffer();
				result.append(rs.getString("SUBSCRIPTION_ID")==null?"":rs.getString("SUBSCRIPTION_ID")).append(LINE_SPLITER);
				result.append(rs.getString("PROD_INST_ID")).append(LINE_SPLITER);
				result.append(rs.getString("OA_TYPE")==null?"":rs.getString("OA_TYPE")).append(LINE_SPLITER);//ProdSpecCode
				result.append(rs.getString("OA")==null?"":rs.getString("OA")).append(LINE_SPLITER);
				String packageId = rs.getString("PACKAGE_ID")==null?"":rs.getString("PACKAGE_ID");
				String pprodOfferId = rs.getString("PPRODUCT_OFFER_ID")==null?"":rs.getString("PPRODUCT_OFFER_ID");
				String prodOfferId = rs.getString("PRODUCT_ID")==null?"":rs.getString("PRODUCT_ID");
				String prodOfferType = "0";
				if(!"".equals(packageId)){
					prodOfferType="2";
					prodOfferId = packageId;
				}else if (!"".equals(pprodOfferId)){
					prodOfferType="1";
					prodOfferId = pprodOfferId;
				}
				result.append(rs.getString("PRODUCT_ID")).append(LINE_SPLITER);//增值产品标识, （订购关系比对必须字段）
				result.append(rs.getString("SPID")==null?"":rs.getString("SPID")).append(LINE_SPLITER);
				result.append(prodOfferType==null?"":prodOfferType).append(LINE_SPLITER);//ProductOfferType
				result.append(prodOfferId==null?"":prodOfferId).append(LINE_SPLITER);//PROD_OFFER_ID
				result.append(rs.getString("STATUS")==null?"":rs.getString("STATUS")).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("SUBSCRIBE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EFFECTIVE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EXPIRE_TIME"))).append(LINE_SPLITER);
				result.append(rs.getString("CHANNEL_PLAYER_ID")).append("\n");
				dataList.add(result.toString());
			}
		}catch (SQLException e) {
//			try {
////				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
			e.printStackTrace();
		}finally{
				try {
					if(rs != null)rs.close();
					if(ps != null)ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
//		LegacyDAOUtil.commitAndCloseConnection(conn);
		return dataList;
	}
	/**
	 * 将VSOP的所有订购关系数据导出到ODS中
	 * @return
	 */
	public List exportDataToODS() {
		// TODO:
		String exportSQL = "select * from ORDER_RELATION_ODS_MID ";//VSOP系统
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List dataList = new ArrayList();
		try {
			ps = conn.prepareStatement(exportSQL);
			rs = ps.executeQuery();
			while(rs.next()){
				StringBuffer result = new StringBuffer();
//				result.append(rs.getString("STREAMING_NO")).append(LINE_SPLITER);
				result.append(rs.getString("SUBSCRIPTION_ID")).append(LINE_SPLITER);
				result.append(rs.getString("PROD_INST_ID")).append(LINE_SPLITER);
				result.append(rs.getString("OA_TYPE")).append(LINE_SPLITER);//ProdSpecCode
				result.append(rs.getString("OA")).append(LINE_SPLITER);
//				result.append(rs.getString("OA_TYPE")).append(LINE_SPLITER);
//				result.append(rs.getString("DA")).append(LINE_SPLITER);
//				result.append(rs.getString("DA_TYPE")).append(LINE_SPLITER);
//				result.append(rs.getString("FA")).append(LINE_SPLITER);
//				result.append(rs.getString("FA_TYPE")).append(LINE_SPLITER);
//				result.append(rs.getString("WITHDRAWER")).append(LINE_SPLITER);
//				result.append(rs.getString("WITHDRAW_REASON")).append(LINE_SPLITER);
//				result.append(rs.getString("SUSPEND_REASON")).append(LINE_SPLITER);
				String packageId = rs.getString("PACKAGE_ID");
				String pprodOfferId = rs.getString("PPRODUCT_OFFER_ID");
				String prodOfferId = "";
				String prodOfferType = "0";
				if(!"".equals(packageId)){
					prodOfferType="2";
					prodOfferId = packageId;
				}else if (!"".equals(pprodOfferId)){
					prodOfferType="1";
					prodOfferId = pprodOfferId;
				}
				result.append(rs.getString("PRODUCT_ID")).append(LINE_SPLITER);//增值产品标识, （订购关系比对必须字段）
				result.append(rs.getString("SPID")).append(LINE_SPLITER);
				result.append(prodOfferType).append(LINE_SPLITER);//ProductOfferType
				result.append(prodOfferId).append(LINE_SPLITER);//PPRODUCT_OFFER_ID
				result.append(rs.getString("STATUS")).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("SUBSCRIBE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EFFECTIVE_TIME"))).append(LINE_SPLITER);
				result.append(DAOUtils.getFormat14ByDate(rs.getString("EXPIRE_TIME"))).append(LINE_SPLITER);
//				result.append(packageId).append(LINE_SPLITER);
//				result.append(pprodOfferId).append(LINE_SPLITER);
//				result.append(rs.getString("SUSPEND_TIME")).append(LINE_SPLITER);
//				result.append(rs.getString("RESUME_TIME")).append(LINE_SPLITER);
//				result.append(rs.getString("LAST_USE_TIME")).append(LINE_SPLITER);
//				result.append(rs.getString("SILENCE")).append(LINE_SPLITER);
				result.append(rs.getString("CHANNEL_PLAYER_ID")).append("\n");
				dataList.add(result.toString());
			}
		}catch (SQLException e) {
			try {
				if(ps != null)ps.close();
				if(rs != null)rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(ps != null)ps.close();
				if(rs != null)rs.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return dataList;
	}

	public void importIsmpDataToMid(List ls, String fileName) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = LegacyDAOUtil.getConnection();
		//文本文件传过来的product_id是外系统编码，需要转换
		String sql = "insert into ORDER_RELATION_ISMP_MID(STREAMING_NO,SUBSCRIPTION_ID,OA,OA_TYPE,DA,DA_TYPE,FA,FA_TYPE" +
				",WITHDRAWER,WITHDRAW_REASON,SUSPEND_REASON,SPID,PRODUCT_ID,PACKAGE_ID,PPRODUCT_OFFER_ID,STATUS,SUBSCRIBE_TIME," +
				"EFFECTIVE_TIME,EXPIRE_TIME,SUSPEND_TIME,RESUME_TIME,LAST_USE_TIME,SILENCE,CHANNEL_PLAYER_ID,V_STATUS,FILE_NAME)" +
				"values(?,?,?,?,?,?,?,?,?,?,?,?," +
				"(select product_id from product where product_nbr=?)," +//用特殊符号#标记，用以下面做替换
				"?,"+
				"?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		int cyNum = 0;
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			String[] linearray = line.split(OrderCheckDao.LINE_SPLITER);
			String status = linearray[15];//STATUS,
			String prodNbr = linearray[12];//PRODUCT_ID
			String channelId = linearray[23];//CHANNEL_PLAYER_ID
			if(prodNbr.equals("") || prodNbr == null
			   || channelId.equals("") || channelId == null)continue;
			
			if("1".equals(status))continue;//待生效的订购关系数据由于不需要比对，所以不将其插入比对中间表
			//ISMP注销的对应VSOP注销的,待生效的不比对,其余的都当作是正常状态比对
			if("6".equals(status)){
				status = "00X";
			}else{
				status = "00A";
			}
			int index = 1;
			ps.setString(index++, linearray[0]);//STREAMING_NO,
			ps.setString(index++, linearray[1]);//SUBSCRIPTION_ID,
			ps.setString(index++, linearray[2]);//OA,
			ps.setString(index++, linearray[3]);//OA_TYPE,
			ps.setString(index++, linearray[4]);//DA,
			ps.setString(index++, linearray[5]);//DA_TYPE,
			ps.setString(index++, linearray[6]);//FA,
			ps.setString(index++, linearray[7]);//FA_TYPE
			
			ps.setString(index++, linearray[8]);//WITHDRAWER,
			ps.setString(index++, linearray[9]);//WITHDRAW_REASON,
			ps.setString(index++, linearray[10]);//SUSPEND_REASON,
			ps.setString(index++, linearray[11]);//SPID,
			ps.setString(index++, prodNbr);//PRODUCT_ID,
//			ps.setString(index++, channelId);//CHANNEL_PLAYER_ID,
			ps.setString(index++, linearray[13]);//PACKAGE_ID,
//			ps.setString(index++, prodNbr);//转换成VSOP编码
			ps.setString(index++, linearray[14]);//PPRODUCT_OFFER_ID,
			ps.setString(index++, linearray[15]);//STATUS,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[16]));//SUBSCRIBE_TIME
			
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[17]));//EFFECTIVE_TIME,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[18]));//EXPIRE_TIME,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[19]));//SUSPEND_TIME,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[20]));//RESUME_TIME,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[21]));//LAST_USE_TIME,
			ps.setString(index++, linearray[22]);//SILENCE,
			ps.setString(index++, linearray[23]);//CHANNEL_PLAYER_ID
			ps.setString(index++, status);//V_STATUS
			ps.setString(index, fileName);//读取的文件名称
			ps.addBatch();
			++cyNum;
		}
		if(cyNum > 0)ps.executeBatch();
		else throw new Exception("数据文件中没有记录!");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
//		LegacyDAOUtil.commitAndCloseConnection(conn);
		
	}
	public List getSystemCode() {
		// TODO:
		String SQL = "select distinct SYSTEM_CODE from PRODUCT_SYSTEM_INFO";
		
		Connection conn = LegacyDAOUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List sysCodeList = new ArrayList();
		try {
			ps = conn.prepareStatement(SQL);
			rs = ps.executeQuery();
			while(rs.next()){
				sysCodeList.add(rs.getString("SYSTEM_CODE"));
			}
		}catch (SQLException e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
			e.printStackTrace();
		}finally{
				try {
					if(rs != null)rs.close();
					if(ps != null)ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
//		LegacyDAOUtil.commitAndCloseConnection(conn);
		return sysCodeList;
	}
	public long getSequence(String sequenceName, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		long seq = 0;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT " + sequenceName
					+ ".NEXTVAL FROM DUAL");
			rs = ps.executeQuery();
			if (rs.next()) {
				seq = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
			DAOUtils.closeResultSet(rs);
		}
		return seq;
	}
	/**
	 * 广西X平台订购关系比对
	 * @param ls
	 * @param fileName
	 * @throws Exception
	 */
	public void importXDataToMidGX(List ls, String fileName) throws Exception {
		// TODO Auto-generated method stub
		Connection conn = LegacyDAOUtil.getConnection();
		//文本文件传过来的product_id是外系统编码，需要转换
		String sql = "insert into ORDER_RELATION_ISMP_MID(STREAMING_NO,SUBSCRIPTION_ID,OA,OA_TYPE,DA,DA_TYPE,FA,FA_TYPE" +
				",SPID,PRODUCT_ID,PACKAGE_ID,PPRODUCT_OFFER_ID,STATUS,SUBSCRIBE_TIME," +
				"EFFECTIVE_TIME,EXPIRE_TIME,CHANNEL_PLAYER_ID,V_STATUS,FILE_NAME)" +
				"values(to_char(sysdate,'yyyyMMddHH24Miss')||SEQ_RELA_ISMP_MID_NO.Nextval,?,?,?,?,?,?,?,?," +
				"(select product_id from product where product_nbr=? and system_code=?)," +//用特殊符号#标记，用以下面做替换
				"?,?,?,?,?,?,?,?,?)";
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			sql="insert into ORDER_RELATION_ISMP_MID(STREAMING_NO,SUBSCRIPTION_ID,OA,OA_TYPE,DA,DA_TYPE,FA,FA_TYPE" +
			",SPID,PRODUCT_ID,PACKAGE_ID,PPRODUCT_OFFER_ID,STATUS,SUBSCRIBE_TIME," +
			"EFFECTIVE_TIME,EXPIRE_TIME,CHANNEL_PLAYER_ID,V_STATUS,FILE_NAME)" +
			"values(to_char(today,'%Y%m%d%H%M%S')||SEQ_RELA_ISMP_MID_NO.Nextval,?,?,?,?,?,?,?,?," +
			"(select product_id from product where product_nbr=? and system_code=?)," +//用特殊符号#标记，用以下面做替换
			"?,?,?,?,?,?,?,?,?)";
		}
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		int cyNum = 0;
		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			String[] linearray = line.split(OrderCheckDao.LINE_SPLITER);
			String status = linearray[8];//STATUS,
			String prodNbr = linearray[3];//PRODUCT_ID
			String channelId = linearray[12];//CHANNEL_PLAYER_ID
			if(prodNbr.equals("") || prodNbr == null
			   || channelId.equals("") || channelId == null)continue;
			
			if("1".equals(status))continue;//待生效的订购关系数据由于不需要比对，所以不将其插入比对中间表
			//X平台注销的对应VSOP注销的,待生效的不比对,其余的都当作是正常状态比对
			if("6".equals(status)){
				status = "00X";
			}else{
				status = "00A";
			}
			int index = 1;
//			ps.setString(index++, linearray[0]);//STREAMING_NO,
			ps.setString(index++, linearray[0]);//SUBSCRIPTION_ID,
			String productNo = linearray[3];
			String productCode = linearray[2];
			ps.setString(index++, productNo);//OA,
			ps.setString(index++, productCode);//OA_TYPE,
			ps.setString(index++, productNo);//DA,
			ps.setString(index++, productCode);//DA_TYPE,
			ps.setString(index++, productNo);//FA,
			ps.setString(index++, productCode);//FA_TYPE
			
//			ps.setString(index++, linearray[8]);//WITHDRAWER,
//			ps.setString(index++, linearray[9]);//WITHDRAW_REASON,
//			ps.setString(index++, linearray[10]);//SUSPEND_REASON,
			ps.setString(index++, linearray[5]);//SPID,
			ps.setString(index++, prodNbr);//PRODUCT_ID,
			ps.setString(index++, channelId);//CHANNEL_PLAYER_ID,
			String ProductOfferType = linearray[6];
			if("0".equals(ProductOfferType)){//0：纯增值销售品
				ps.setString(index++, "");//PACKAGE_ID,
			}else if("1".equals(ProductOfferType)){//1：增值捆绑套餐
				ps.setString(index++, linearray[7]);//PACKAGE_ID,
			}else{//2：（传统+增值）捆绑套餐
				ps.setString(index++, "");//PACKAGE_ID,
			}
			ps.setString(index++, "");//PPRODUCT_OFFER_ID,
			ps.setString(index++, linearray[8]);//STATUS,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[9]));//SUBSCRIBE_TIME
			
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[10]));//EFFECTIVE_TIME,
			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[11]));//EXPIRE_TIME,
//			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[19]));//SUSPEND_TIME,
//			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[20]));//RESUME_TIME,
//			ps.setDate(index++, DAOUtils.parseDateTimeFormat_14(linearray[21]));//LAST_USE_TIME,
//			ps.setString(index++, linearray[22]);//SILENCE,
			ps.setString(index++, linearray[12]);//CHANNEL_PLAYER_ID
			ps.setString(index++, status);//V_STATUS
			ps.setString(index, fileName);//读取的文件名称
			ps.addBatch();
			++cyNum;
		}
		if(cyNum > 0)ps.executeBatch();
		else throw new Exception("数据文件中没有记录!");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw e;
		}finally{
			DAOUtils.closeStatement(ps);
		}
//		LegacyDAOUtil.commitAndCloseConnection(conn);
		
	}
}
