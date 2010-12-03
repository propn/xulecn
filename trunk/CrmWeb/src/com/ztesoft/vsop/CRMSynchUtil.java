package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.vsop.web.DcSystemParamManager;
import common.Logger;

/**
 * 填写库表INF_ORDER_RELATION,状态为U未处理,发送次数为1
 * 系统参数(CRM_SYN 1同步,2不同步)来控制是否执行该操作,同步的时候填写该表,不同步则不用写.
 * @author qin.guoquan
 *
 */
public class CRMSynchUtil {

	private static Logger log = Logger.getLogger(CRMSynchUtil.class);
	
	private static CRMSynchUtil instance = new CRMSynchUtil();
	
	public static CRMSynchUtil getInstance() {
		return instance;
	}
	
	public static final String CRM_SYN = DcSystemParamManager.getParameter(VsopConstants.CRM_SYN);
	
	private String SEQ_INF_ORDER_RELATION_ID = "SEQ_INF_ORDER_RELATION_ID";
	
	private static String SQL_INSERT = "insert into inf_order_relation(inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times,PARTITION_ID) values (?,?,?,?,?,?,?,sysdate,sysdate,?,?)";
	
	//支持informix
	private static String SQL_INSERT_informix = "insert into inf_order_relation(inf_order_relation_id,user_id,user_id_type,product_id,package_id,op_type,state,state_date,create_date,send_times,PARTITION_ID) values (?,?,?,?,?,?,?,current,current,?,?)";
	
		
	static{
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			SQL_INSERT=SQL_INSERT_informix;
			
		} else if (CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
		
		}
		}
	
	public void writeInfOrderRelation(InfOrderRelationVo infOrderRelationVo) {
			
		Connection con = null;
		PreparedStatement ps = null;
		int i = 1;
		try {
			con = LegacyDAOUtil.getConnection();
			ps = con.prepareStatement(SQL_INSERT);
			ps.setLong(i++, Long.parseLong(getSequence(SEQ_INF_ORDER_RELATION_ID)));
			ps.setString(i++, infOrderRelationVo.getUserId());
			ps.setString(i++, infOrderRelationVo.getUserIdType());
			ps.setString(i++, infOrderRelationVo.getProductId());
			ps.setString(i++, infOrderRelationVo.getPackageId());
			ps.setString(i++, infOrderRelationVo.getOpType());
			ps.setString(i++, infOrderRelationVo.getState());
			ps.setString(i++, infOrderRelationVo.getSendTimes());
			ps.setInt(i++, DAOUtils.getCurrentMonth());
			ps.executeUpdate();
		} catch (Exception e) {
			log.error("填写库表INF_ORDER_RELATION失败!", e);
			e.printStackTrace();
		} finally {
			DAOUtils.closeStatement(ps, this);
			DAOUtils.closeConnection(con, this);
		}
	}
	
	private String getSequence(String sequenceCode) {
		SequenceManageDAOImpl sequenceManageDAO = new SequenceManageDAOImpl();
		return sequenceManageDAO.getNextSequenceWithDB(sequenceCode,
				JNDINames.VSOP_DATASOURCE);
	}
	
}
