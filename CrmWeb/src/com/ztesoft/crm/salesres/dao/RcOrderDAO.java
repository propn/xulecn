package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcAppTypeVO;
import com.ztesoft.crm.salesres.vo.RcOrderSegInfoVO;

public interface RcOrderDAO extends DAO {

	ArrayList findBySql(String sql, String[] sqlParams)
			throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	List getOrderInfoByOrderId(String orderId) throws DAOSystemException;

	List getOrderExcInfoByOrderId(String orderId) throws DAOSystemException;

	List getSalesResourceByFamilyType(String familyType)
			throws DAOSystemException;

	List getStorageInfoByDepartIdList(String departId)
			throws DAOSystemException;

	List getRcOrderDataByAppId(String appId) throws DAOSystemException;

	List getRcApplicationDataByAppId(String appId) throws DAOSystemException;

	List getRcOrderDataByOrderId(String orderId) throws DAOSystemException;

	RcAppTypeVO getRcAppType(String appTypeId) throws DAOSystemException;

	boolean updateRcOrderData(String strSql) throws DAOSystemException;

	List getOrderInfoByOrderId_Excel(String orderIdStr)
			throws DAOSystemException;

	List getSalesResourceBySalesResourceId(String saleResourceId)
			throws DAOSystemException;

	void setSQL(String sql_select, String sql_select_count, String operState);

	int getTacheId(String strSql) throws DAOSystemException;

	public void setFlag(int flag);

	public String getSQL_SELECT();

	public void setSQL_SELECT(String SQL_SELECT);

	public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT);

	public String getRcStorageName(String rcStorageId)
			throws DAOSystemException;

	/**
	 * 查询处入库订单日至记录
	 * 
	 * @param orderIds
	 *            String
	 * @throws DAOSystemException
	 * @return List
	 */
	public List qryRescInOutLog(String rescInstanceCode, String orderIds)
			throws DAOSystemException;

	/**
	 * 查询生成号码或sim卡的订单信息
	 * 
	 * @param orderIds
	 *            String
	 * @throws DAOSystemException
	 * @return List
	 */
	public List qryOrderNoSim(Map map) throws DAOSystemException;

	/**
	 * @param b
	 */
	public void setFiltered(boolean b);

	List getOrderSegInfoByOrderId(String orderId, String flag);

	String getSegInfo(String resBCode, String resECode, String preCode, String postCode,String storageId,String salesRescId);

	void insertSegOrder(RcOrderSegInfoVO[] segs);

	String getAgentInfo(String orderId);

	String insertAgentInfo(HashMap map);

}
