package com.ztesoft.crm.salesres.bo;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcAppTypeDAO;
import com.ztesoft.crm.salesres.dao.RcApplicationDAO;
import com.ztesoft.crm.salesres.dao.RcEntityDAO;
import com.ztesoft.crm.salesres.dao.RcEntityDAO2;
import com.ztesoft.crm.salesres.dao.RcEntityOutLogDAO;
import com.ztesoft.crm.salesres.dao.RcEntityReportDAO;
import com.ztesoft.crm.salesres.dao.RcFamilyDAO;
import com.ztesoft.crm.salesres.dao.RcFamilyEntityRelaDAO;
import com.ztesoft.crm.salesres.dao.RcLevelDefDAO;
import com.ztesoft.crm.salesres.dao.RcOrderAgentDAO;
import com.ztesoft.crm.salesres.dao.RcOrderDAO;
import com.ztesoft.crm.salesres.dao.RcOrderExcDAO;
import com.ztesoft.crm.salesres.dao.RcOrderListDAO;
import com.ztesoft.crm.salesres.dao.RcOrderOperStateDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.RcSaleLogDAO;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.SaleRescPricDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.StaffDAO;
import com.ztesoft.crm.salesres.dao.StorageDepartRelaDAO;
import com.ztesoft.crm.salesres.exception.SaleResLogicException;
import com.ztesoft.crm.salesres.vo.EntityResourceVO;
import com.ztesoft.crm.salesres.vo.RcAppTypeVO;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcAttrDefVO;
import com.ztesoft.crm.salesres.vo.RcEntityReportVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO2;
import com.ztesoft.crm.salesres.vo.RcFamilyEntityRelaVO;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;
import com.ztesoft.crm.salesres.vo.RcOrderAgentVO;
import com.ztesoft.crm.salesres.vo.RcOrderExcVO;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;
import com.ztesoft.crm.salesres.vo.RcOrderOperStateVO;
import com.ztesoft.crm.salesres.vo.RcOrderSegInfoVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;
import com.ztesoft.crm.salesres.vo.RcSaleLogVO;
import com.ztesoft.crm.salesres.vo.RcStockVO;
import com.ztesoft.crm.salesres.vo.SaleRescPricVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

public class SrStockBo extends DictAction {
	public SrStockBo() {
	}

	// xiangyuwen
//	public PageModel getStaffInfo_order(HashMap map, int pi, int ps) {
	public PageModel getStaffInfo_order(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        int pi  = ((Integer)map.get("pageIndex")).intValue();
        int ps  = ((Integer)map.get("pageSize")).intValue();
        
		String sType = String.valueOf(map.get("sType"));
		String sContent = String.valueOf(map.get("sContent"));
		String departId = String.valueOf(map.get("departId"));

		if (departId.endsWith(",")) {
			departId = departId.substring(0, departId.length() - 1);

		}
		PageModel pm = new PageModel();
		String sql = "  state='00A' ";
		try {
			if (sContent == null) {
				sContent = "";
			}
			if (sType == null) {
				sType = "";
			}
			if (!("".equals(sContent))) {
				if ("scode".equals(sType)) {
					sql += "  and staff_code like '%" + sContent + "%' ";
				}
				if ("sname".equals(sType)) {
					sql += "  and party_role_name like '%" + sContent + "%' ";
				}
			}
			// departId传入的数据是部门ID，即查询出选中部门下的所有员工数据.
			if (departId != null && !"".equals(departId)) {
				sql += " and org_party_id in (" + departId + ") ";
			}

			StaffDAO sdao = (StaffDAO) SrDAOFactory.getInstance().getStaffDAO();
			// 使用专门为物资订单准备的sql语句
			pm = PageHelper.popupPageModel(sdao, sql, pi, ps);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pm;
	}

	public PageModel getRcOrderInfo(HashMap hashMap, int pi, int ps) {
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		String departId = (hashMap.get("departId") == null) ? null : (String
				.valueOf(hashMap.get("departId")));
		String operId = (hashMap.get("operId") == null) ? null : (String
				.valueOf(hashMap.get("operId")));
		String beginDate = (hashMap.get("beginDate") == null) ? null : (String
				.valueOf(hashMap.get("beginDate")));
		String endDate = (hashMap.get("endDate") == null) ? null : (String
				.valueOf(hashMap.get("endDate")));
		String appType = (hashMap.get("appType") == null) ? null : (String
				.valueOf(hashMap.get("appType")));
		String appStatus = (hashMap.get("appStatus") == null) ? null : (String
				.valueOf(hashMap.get("appStatus")));
		String appTache = (hashMap.get("appTache") == null) ? null : (String
				.valueOf(hashMap.get("appTache")));
		String appCode = (hashMap.get("appCode") == null) ? null : (String
				.valueOf(hashMap.get("appCode")));
		String inStorageId = (hashMap.get("inStorageId") == null) ? null
				: (String.valueOf(hashMap.get("inStorageId")));
		String gjselect = (hashMap.get("gjselect") == null) ? null : (String
				.valueOf(hashMap.get("gjselect")));
		String orderId = (hashMap.get("orderId") == null) ? null : (String
				.valueOf(hashMap.get("orderId")));
		String familyId = (hashMap.get("familyId") == null) ? null : (String
				.valueOf(hashMap.get("familyId")));

		// if (departId.endsWith(",")) {
		// departId = departId.substring(0, departId.length() - 1);
		// }

		PageModel pm = new PageModel();
		String cond = " and operate_type<>1 ";
		String sql = rdao.getSQL_SELECT();
		String sql_count = "SELECT COUNT(*) AS COL_COUNTS FROM rc_order a ,rc_application b,SALES_RESOURCE f "
				+ " where a.app_id=b.app_id and a.sales_resource_id=f.sales_resource_id ";

		// if (departId != null && !"".equals(departId)) {
		// sql += " and b.depart_id in (" + departId + ")";
		// }
		if (appType != null && !"".equals(appType)) {
			cond += " and b.app_type='" + appType + "'";
		}
		if (appStatus != null && !"".equals(appStatus)) {
			cond += " and a.state_id='" + appStatus + "'";
		}
		if (appTache != null && !"".equals(appTache)) {
			cond += " and a.tache_id=" + appTache;
		}
		if (appCode != null && !"".equals(appCode)) {
			cond += "  and b.app_id=" + DAOUtils.filterQureyCond(appCode);
		}
		if (inStorageId != null && !"".equals(inStorageId)) {
			cond += "  and a.in_storage_id=" + inStorageId;
		}
		if (orderId != null && !"".equals(orderId)) {
			cond += "  and a.ORDER_ID =" + DAOUtils.filterQureyCond(orderId);
		}
		if (familyId != null && !"".equals(familyId)) {
			cond += "  and f.FAMILY_ID=" + familyId;
		}

		if ("INFORMIX".equals(databaseType)) {
			if (beginDate != null && !"".equals(beginDate)) {
				// sql += " and a.app_time>=to_date('"+beginDate+"','%Y-%m-%d')
				// ";
				cond += "  and a.app_time>=to_date('" + beginDate
						+ " 00:00:00 ','%Y-%m-%d %H:%M:%S')  ";
			}
			if (endDate != null && !"".equals(endDate)) {
				// sql += " and a.app_time<=to_date('"+endDate+"','%Y-%m-%d') ";
				cond += "  and a.app_time<=to_date('" + endDate
						+ " 23:59:59 ','%Y-%m-%d %H:%M:%S')  ";
			}
			if (gjselect != null && !"".equals(gjselect)) {
				if ("true".equals(gjselect)) {
					cond += "  and a.require_time<to_date('"
							+ DateFormatUtils.getFormatedDate()
							+ "','%Y-%m-%d')  ";
				}
			}
		} else {
			if (beginDate != null && !"".equals(beginDate)) {
				cond += "  and a.app_time>=to_date('" + beginDate
						+ "','yyyy-mm-dd')  ";
			}
			if (endDate != null && !"".equals(endDate)) {
				cond += "  and a.app_time<=to_date('" + endDate
						+ "','yyyy-mm-dd')  ";
			}
			if (gjselect != null && !"".equals(gjselect)) {
				if ("true".equals(gjselect)) {
					cond += "  and a.require_time<to_date('"
							+ DateFormatUtils.getFormatedDate()
							+ "','yyyy-mm-dd')  ";
				}
			}
		}
		// 加上该操作员能看到的单位的权限判断
		// if (operId != null && !"".equals(operId)) {
		// cond += " and ( b.oper_id =" + operId + " or a.depart_id="+departId
		// +")";
		// }
		String cond_right = " ((select STORAGE_ID from mp_storage where oper_id="
				+ operId
				+ ") "
				+ " union all (select STORAGE_ID from STORAGE_DEPART_RELA where depart_id="
				+ departId + "))";
		cond += " and (a.APP_STORAGE_ID in " + cond_right
				+ " or a.IN_STORAGE_ID in " + cond_right
				+ " or a.OUT_STORAGE_ID in " + cond_right + ")";

		sql += cond;
		sql_count += cond;
		sql += " order by a.app_id asc";
		rdao.setSQL_SELECT(sql);
		rdao.setSQL_SELECT_COUNT(sql_count);
		System.out.println("<<< " + sql + "\n" + sql_count);
		pm = PageHelper.popupPageModel(rdao, "", pi, ps);

		return pm;
	}

	public List getOrderInfoList(HashMap hashMap) {
		String orderId = String.valueOf(hashMap.get("orderId"));
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getOrderInfoByOrderId(orderId);
		return retList;
	}

	public List getStorageInfoByDepartIdList(HashMap hashMap) {
		String departId = String.valueOf(hashMap.get("departId"));
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getStorageInfoByDepartIdList(departId);
		return retList;
	}

	public List getOrderExcInfoList(HashMap hashMap) {
		String orderId = String.valueOf(hashMap.get("orderId"));
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getOrderExcInfoByOrderId(orderId);
		return retList;
	}

	public List getSalesResourceByFamilyType(HashMap hashMap) {
		String familyType = String.valueOf(hashMap.get("familyType"));
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getSalesResourceByFamilyType(familyType);
		return retList;
	}

	public List getRcApplicationDataByAppId(HashMap hashMap) {
		String appId = String.valueOf(hashMap.get("appId"));
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getRcApplicationDataByAppId(appId);
		return retList;
	}

	public List getRcOrderDataByAppId(HashMap hashMap) {
		String appId = String.valueOf(hashMap.get("appId"));
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getRcOrderDataByAppId(appId);
		return retList;
	}

	public String saveRCAppOrder(RcApplicationVO rcApplicationVO,
			List rcOrderList, String operId) {
		String retv = "0";
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		RcApplicationDAO adao = (RcApplicationDAO) SrDAOFactory.getInstance()
				.getRcApplicationDAO();
		RcOrderExcDAO logdao = (RcOrderExcDAO) SrDAOFactory.getInstance()
				.getRcOrderExcDAO();
		SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
		SequenceManageDAO sequenceManageDAO = seqDAOFactory
				.getSequenceManageDAO();
		String appId = sequenceManageDAO.getNextSequence("rc_application",
				"app_id");
		String orderId = "";
		String logId = "";
		try {
			rcApplicationVO.setAppId(appId);
			adao.insert(rcApplicationVO);
			if ("save".equals(rcApplicationVO.getStatus())) {
				if (rcOrderList != null && rcOrderList.size() > 0) {
					for (int i = 0; i < rcOrderList.size(); i++) {
						RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList.get(i);
						orderId = sequenceManageDAO.getNextSequence("rc_order",
								"order_id");
						rcOrderVO.setAppId(appId);
						rcOrderVO.setOrderId(orderId);

						rdao.insert(rcOrderVO);
						// 如果有回退订单号则处理
						if (rcOrderVO.getBackOrderId() != null
								&& rcOrderVO.getBackOrderId().trim().length() > 0)
							this.insertBackOrderList(
									rcOrderVO.getBackOrderId(), orderId);
					}
				}
			} else if ("submit".equals(rcApplicationVO.getStatus())) {
				if (rcOrderList != null && rcOrderList.size() > 0) {
					for (int i = 0; i < rcOrderList.size(); i++) {
						RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList.get(i);
						orderId = sequenceManageDAO.getNextSequence("rc_order",
								"order_id");
						rcOrderVO.setAppId(appId);
						rcOrderVO.setOrderId(orderId);
						rcOrderVO.setDepartId(rcApplicationVO.getDepartId());
						rcOrderVO.setTacheTime(DateFormatUtils
								.getFormatedDateTime());
						rcOrderVO.setTacheId("2"); // 审批

						RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
						logId = sequenceManageDAO.getNextSequence(
								"rc_order_exc", "log_id");
						rcOrderExcVO.setLogId(logId);
						rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
						rcOrderExcVO.setTacheId("1");
						rcOrderExcVO.setExcTime(rcOrderVO.getTacheTime());
						rcOrderExcVO.setDepartId(rcApplicationVO.getDepartId());
						rcOrderExcVO.setOperId(operId);
						rcOrderExcVO.setStateId(rcOrderVO.getStateId());
						rcOrderExcVO.setExcComments(rcOrderVO.getComments());

						rdao.insert(rcOrderVO);
						logdao.insert(rcOrderExcVO);
						// 如果有回退订单号则处理
						if (rcOrderVO.getBackOrderId() != null
								&& rcOrderVO.getBackOrderId().trim().length() > 0)
							this.insertBackOrderList(
									rcOrderVO.getBackOrderId(), orderId);
					}
				}
			}

			retv = appId;
		} catch (Exception ex) {
			retv = "0";
			ex.printStackTrace();
		}
		return retv;
	}

	/**
	 * 把backOrderId订单对应的rc_order_list记录更换newOrderId重新插入rc_order_list表
	 * 
	 * @param backOrderId
	 *            String
	 * @param newOrderId
	 *            String
	 * @return boolean
	 */
	private boolean insertBackOrderList(String backOrderId, String newOrderId) {
		boolean rtn = true;
		if (backOrderId != null && backOrderId.trim().length() > 0
				&& newOrderId != null && newOrderId.trim().length() > 0) {
			backOrderId = DAOUtils.filterQureyCond(backOrderId);
			String sql = " insert into rc_order_list(order_id,resource_instance_id,sales_resource_id,resource_instance_code)"
					+ " select "
					+ newOrderId
					+ ",resource_instance_id,sales_resource_id,resource_instance_code from "
					+ " rc_order_list where order_id=" + backOrderId;
			SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
			dao.excute(sql);
		} else
			rtn = false;
		return rtn;
	}

	public int repealRCAppOrder(String orderIdStr, String operId,
			String departId, String ipStr) {
		int retv = 0;
		RcOrderVO oldRcOrderVO = null;
		RcOrderVO newRcOrderVO = null;
		RcPublicLogVO rcPublicLogVO = new RcPublicLogVO();
		if (orderIdStr != null && !"".equals(orderIdStr)) {
			String[] orderIdArr = orderIdStr.split(",");
			if (orderIdArr != null && orderIdArr.length > 0) {
				RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
						.getRcOrderDAO();
				RcOrderExcDAO logdao = (RcOrderExcDAO) SrDAOFactory
						.getInstance().getRcOrderExcDAO();
				RcPublicLogDAO publiclogdao = (RcPublicLogDAO) SrDAOFactory
						.getInstance().getRcPublicLogDAO();

				SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
				SequenceManageDAO sequenceManageDAO = seqDAOFactory
						.getSequenceManageDAO();
				List rcOrderList = new ArrayList();
				String logId = "";
				for (int i = 0; i < orderIdArr.length; i++) {
					String orderId = orderIdArr[i];
					try {
						if (orderId != null && !"".equals(orderId)) {
							rcOrderList = rdao.getOrderInfoByOrderId(orderId);
							if (rcOrderList != null && rcOrderList.size() > 0) {
								RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList
										.get(0);

								List oldList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (oldList != null && oldList.size() > 0) {
									oldRcOrderVO = (RcOrderVO) oldList.get(0);
								}

								rcOrderVO.setOrderId(orderId);
								rcOrderVO.setOperId(operId);
								rcOrderVO.setDepartId(departId);
								rcOrderVO.setTacheTime(DateFormatUtils
										.getFormatedDateTime());
								rcOrderVO.setTacheId("5"); // 结束;
								rcOrderVO.setStateId("c"); // 撤销;

								RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
								logId = sequenceManageDAO.getNextSequence(
										"rc_order_exc", "log_id");
								rcOrderExcVO.setLogId(logId);
								rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
								rcOrderExcVO.setTacheId("1");
								rcOrderExcVO.setExcTime(rcOrderVO
										.getTacheTime());
								rcOrderExcVO.setDepartId(rcOrderVO
										.getDepartId());
								rcOrderExcVO.setOperId(operId);
								rcOrderExcVO.setStateId(rcOrderVO.getStateId());
								rcOrderExcVO.setExcComments(rcOrderVO
										.getComments());

								String cond = " order_id="
										+ rcOrderVO.getOrderId();
								rdao.update(cond, rcOrderVO);
								logdao.insert(rcOrderExcVO);

								List newList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (newList != null && newList.size() > 0) {
									newRcOrderVO = (RcOrderVO) newList.get(0);
								}

								rcPublicLogVO.setReworkIp(ipStr);
								rcPublicLogVO.setReworkTable("rc_order");
								rcPublicLogVO.setReworkWen(operId);
								rcPublicLogVO.setReworkTime(DateFormatUtils
										.getFormatedDateTime());
								rcPublicLogVO.setAct("M");
								publiclogdao.logVO(rcPublicLogVO, oldRcOrderVO,
										newRcOrderVO);

								retv++;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return retv;
	}

	public int submitRCAppOrder(String orderIdStr, String operId,
			String departId, String ipStr) {
		int retv = 0;
		RcOrderVO oldRcOrderVO = null;
		RcOrderVO newRcOrderVO = null;
		RcPublicLogVO rcPublicLogVO = new RcPublicLogVO();
		if (orderIdStr != null && !"".equals(orderIdStr)) {
			String[] orderIdArr = orderIdStr.split(",");
			if (orderIdArr != null && orderIdArr.length > 0) {
				RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
						.getRcOrderDAO();
				RcOrderExcDAO logdao = (RcOrderExcDAO) SrDAOFactory
						.getInstance().getRcOrderExcDAO();
				RcPublicLogDAO publiclogdao = (RcPublicLogDAO) SrDAOFactory
						.getInstance().getRcPublicLogDAO();

				SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
				SequenceManageDAO sequenceManageDAO = seqDAOFactory
						.getSequenceManageDAO();
				List rcOrderList = new ArrayList();
				String logId = "";
				for (int i = 0; i < orderIdArr.length; i++) {
					String orderId = orderIdArr[i];
					try {
						if (orderId != null && !"".equals(orderId)) {
							rcOrderList = rdao.getOrderInfoByOrderId(orderId);
							if (rcOrderList != null && rcOrderList.size() > 0) {
								RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList
										.get(0);

								List oldList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (oldList != null && oldList.size() > 0) {
									oldRcOrderVO = (RcOrderVO) oldList.get(0);
								}

								rcOrderVO.setOrderId(orderId);
								rcOrderVO.setDepartId(departId);
								rcOrderVO.setTacheTime(DateFormatUtils
										.getFormatedDateTime());
								rcOrderVO.setTacheId("2"); // 审批;

								RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
								logId = sequenceManageDAO.getNextSequence(
										"rc_order_exc", "log_id");
								rcOrderExcVO.setLogId(logId);
								rcOrderExcVO.setOrderId(oldRcOrderVO
										.getOrderId());
								rcOrderExcVO.setTacheId("1");
								rcOrderExcVO.setExcTime(rcOrderVO
										.getTacheTime());
								rcOrderExcVO.setDepartId(oldRcOrderVO
										.getDepartId());
								rcOrderExcVO.setOperId(operId);
								rcOrderExcVO.setStateId(oldRcOrderVO
										.getStateId());
								rcOrderExcVO.setExcComments(oldRcOrderVO
										.getComments());

								String cond = " order_id="
										+ rcOrderVO.getOrderId();
								rdao.update(cond, rcOrderVO);
								logdao.insert(rcOrderExcVO);

								List newList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (newList != null && newList.size() > 0) {
									newRcOrderVO = (RcOrderVO) newList.get(0);
								}
								rcPublicLogVO.setReworkIp(ipStr);
								rcPublicLogVO.setReworkTable("rc_order");
								rcPublicLogVO.setReworkWen(operId);
								rcPublicLogVO.setReworkTime(DateFormatUtils
										.getFormatedDateTime());
								rcPublicLogVO.setAct("M");
								publiclogdao.logVO(rcPublicLogVO, oldRcOrderVO,
										newRcOrderVO);

								retv++;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return retv;
	}

	public String updateRCAppOrder(RcApplicationVO rcApplicationVO,
			List rcOrderList, String operId, String ipStr) {
		String retv = "0";
		RcOrderVO oldRcOrderVO = null;
		RcOrderVO newRcOrderVO = null;
		RcPublicLogVO rcPublicLogVO = new RcPublicLogVO();
		RcPublicLogDAO publiclogdao = (RcPublicLogDAO) SrDAOFactory
				.getInstance().getRcPublicLogDAO();

		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		RcApplicationDAO adao = (RcApplicationDAO) SrDAOFactory.getInstance()
				.getRcApplicationDAO();
		RcOrderExcDAO logdao = (RcOrderExcDAO) SrDAOFactory.getInstance()
				.getRcOrderExcDAO();
		SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
		SequenceManageDAO sequenceManageDAO = seqDAOFactory
				.getSequenceManageDAO();
		// String appId = sequenceManageDAO.getNextSequence("rc_application",
		// "app_id") ;
		String orderId = "";
		String logId = "";
		try {
			if ("updatesave".equals(rcApplicationVO.getStatus())) {
				String cond = " app_id = " + rcApplicationVO.getAppId();
				adao.update(cond, rcApplicationVO);
				rcApplicationVO.setStatus("updatesame");
			}
			if ("updatesubmit".equals(rcApplicationVO.getStatus())) {
				String cond = " app_id = " + rcApplicationVO.getAppId();
				adao.update(cond, rcApplicationVO);
				rcApplicationVO.setStatus("submitsame");
			}
			if ("updatesame".equals(rcApplicationVO.getStatus())) {
				if (rcOrderList != null && rcOrderList.size() > 0) {
					for (int i = 0; i < rcOrderList.size(); i++) {
						RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList.get(i);
						if ("1".equals(rcOrderVO.getTacheId())) {
							if (rcOrderVO.getOrderId() != null
									&& !"".equals(rcOrderVO.getOrderId())) {
								List oldList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (oldList != null && oldList.size() > 0) {
									oldRcOrderVO = (RcOrderVO) oldList.get(0);
								}
								String cond = " order_id="
										+ rcOrderVO.getOrderId();
								rcOrderVO.setAppType(rcApplicationVO
										.getAppType());
								rdao.update(cond, rcOrderVO);

								List newList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (newList != null && newList.size() > 0) {
									newRcOrderVO = (RcOrderVO) newList.get(0);
								}
								rcPublicLogVO.setReworkIp(ipStr);
								rcPublicLogVO.setReworkTable("rc_order");
								rcPublicLogVO.setReworkWen(operId);
								rcPublicLogVO.setReworkTime(DateFormatUtils
										.getFormatedDateTime());
								rcPublicLogVO.setAct("M");
								publiclogdao.logVO(rcPublicLogVO, oldRcOrderVO,
										newRcOrderVO);
							} else {
								orderId = sequenceManageDAO.getNextSequence(
										"rc_order", "order_id");
								rcOrderVO.setOrderId(orderId);
								rcOrderVO.setAppType(rcApplicationVO
										.getAppType());
								rcOrderVO.setAppId(rcApplicationVO.getAppId());

								rdao.insert(rcOrderVO);
							}
						}
					}
				}
			} else if ("submitsame".equals(rcApplicationVO.getStatus())) {
				if (rcOrderList != null && rcOrderList.size() > 0) {
					for (int i = 0; i < rcOrderList.size(); i++) {
						RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList.get(i);

						if ("1".equals(rcOrderVO.getTacheId())) {

							if (rcOrderVO.getOrderId() != null
									&& !"".equals(rcOrderVO.getOrderId())) {
								List oldList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (oldList != null && oldList.size() > 0) {
									oldRcOrderVO = (RcOrderVO) oldList.get(0);
								}
								// orderId =
								// sequenceManageDAO.getNextSequence("rc_order","order_id");
								rcOrderVO.setAppId(rcApplicationVO.getAppId());
								// rcOrderVO.setOrderId(orderId);
								rcOrderVO.setAppType(rcApplicationVO
										.getAppType());

								// rcOrderVO.setDepartId(rcApplicationVO.getDepartId());
								rcOrderVO.setTacheTime(DateFormatUtils
										.getFormatedDateTime());
								rcOrderVO.setTacheId("2"); // 审批
								String cond = " order_id="
										+ rcOrderVO.getOrderId();
								rdao.update(cond, rcOrderVO);

								List newList = rdao
										.getRcOrderDataByOrderId(rcOrderVO
												.getOrderId());
								if (newList != null && newList.size() > 0) {
									newRcOrderVO = (RcOrderVO) newList.get(0);
								}
								rcPublicLogVO.setReworkIp(ipStr);
								rcPublicLogVO.setReworkTable("rc_order");
								rcPublicLogVO.setReworkWen(operId);
								rcPublicLogVO.setReworkTime(DateFormatUtils
										.getFormatedDateTime());
								rcPublicLogVO.setAct("M");
								publiclogdao.logVO(rcPublicLogVO, oldRcOrderVO,
										newRcOrderVO);

								RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
								logId = sequenceManageDAO.getNextSequence(
										"rc_order_exc", "log_id");
								rcOrderExcVO.setLogId(logId);
								rcOrderExcVO.setOrderId(oldRcOrderVO
										.getOrderId());
								rcOrderExcVO.setTacheId("1");
								rcOrderExcVO.setExcTime(oldRcOrderVO
										.getTacheTime());
								rcOrderExcVO.setDepartId(rcApplicationVO
										.getDepartId());
								rcOrderExcVO.setOperId(operId);
								rcOrderExcVO.setStateId(oldRcOrderVO
										.getStateId());
								rcOrderExcVO.setExcComments(oldRcOrderVO
										.getComments());

								logdao.insert(rcOrderExcVO);
							} else {
								orderId = sequenceManageDAO.getNextSequence(
										"rc_order", "order_id");

								RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
								logId = sequenceManageDAO.getNextSequence(
										"rc_order_exc", "log_id");
								rcOrderExcVO.setLogId(logId);
								rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
								rcOrderExcVO.setTacheId("1");
								rcOrderExcVO.setExcTime(rcOrderVO
										.getTacheTime());
								rcOrderExcVO.setDepartId(rcApplicationVO
										.getDepartId());
								rcOrderExcVO.setOperId(operId);
								rcOrderExcVO.setStateId(rcOrderVO.getStateId());
								rcOrderExcVO.setExcComments(rcOrderVO
										.getComments());

								rcOrderVO.setAppId(rcApplicationVO.getAppId());
								// rcOrderVO.setOrderId(orderId);
								rcOrderVO.setAppType(rcApplicationVO
										.getAppType());
								// rcOrderVO.setDepartId(rcApplicationVO.getDepartId());
								rcOrderVO.setTacheTime(DateFormatUtils
										.getFormatedDateTime());
								rcOrderVO.setTacheId("2"); // 审批

								rdao.insert(rcOrderVO);
								logdao.insert(rcOrderExcVO);
							}
						}

					}
				}
			}

			retv = "1";
		} catch (Exception ex) {
			retv = "0";
			ex.printStackTrace();
		}
		return retv;
	}

	/**
	 * 订单的出入库操作。
	 * 
	 * @param 
	 *        vo，vo中的orderId、tacheId、stateId、salesRescId、operId、departId、actAmount不能为空
	 * @return -1：所需参数有错误，可能是缺少以上参数之一 1:成功完成操作返回 2:该订单已经不存在了
	 *         3:该订单的环节已经改变，请查验后再操作 4:操作员没有权限操作订单流入或流出仓库 5:流转订单时失败 6:库存不足，更新库存失败
	 *         7:更新库存操作失败 8:该订单的营销资源管理模式为不管理或没有指定管理模式
	 */
	public String inOutStock(RcOrderVO vo, String ip) {
		if (vo == null || vo.getOrderId() == null
				|| vo.getOrderId().trim().length() <= 0
				|| vo.getActAmount() == null || vo.getActAmount().length() <= 0
				|| vo.getStateId() == null
				|| vo.getStateId().trim().length() <= 0
				|| vo.getSalesRescId() == null
				|| vo.getSalesRescId().length() <= 0 || vo.getOperId() == null
				|| vo.getOperId().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1) {
			return "-1";
		}
		String result = "1";
		RcOrderDAO orderdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		RcOrderVO tempvo1 = null;
		// 验证出入库订单的情况是否可以进行出入库操作，查找出该订单的信息
		List list1 = orderdao.findByCond(" and a.order_id=" + vo.getOrderId());
		if (list1 != null && list1.size() >= 1 && list1.get(0) != null) {
			tempvo1 = (RcOrderVO) list1.get(0);
			RcOrderVO tempvo2 = tempvo1.copy();
			if (tempvo2 == null || tempvo2.getTacheId() == null
					|| !tempvo2.getTacheId().equals(vo.getTacheId())) {
				return "3";
			}
			// 把vo带进来需要更新的参数付给刚查询的tempvo，再付给vo，为了更新这些字段
			tempvo2.setStateId(vo.getStateId());
			tempvo2.setActAmount(vo.getActAmount());
			tempvo2.setComments(vo.getComments());
			tempvo2.setOperId(vo.getOperId());
			tempvo2.setDepartId(vo.getDepartId());
			tempvo2.setResBCode(vo.getResBCode());
			tempvo2.setResECode(vo.getResECode());
			tempvo2.setAttrList(vo.getAttrList());
			tempvo2.setEntityVO(vo.getEntityVO());
			tempvo2.setLanId(vo.getLanId());
			tempvo2.setRSeg(vo.getRSeg());
			tempvo2.setRecOptType(vo.getRecOptType());

			vo = tempvo2;
		} else {
			return "2";
		}
		// 验证操作人员是否有权限进行该仓库操作
		long sotrage_num = 0;
		SqlComDAO comdao = (SqlComDAO) SrDAOFactory.getInstance()
				.getSqlComDAO();

		String right_storage = " select count(*) as COL_COUNTS from rc_storage a where 1=1 "
				+ CommonUtilBo.getStorageRightFilteSql(vo.getOperId(), vo
						.getDepartId(), "a.STORAGE_ID") + " and a.storage_id=";
		if ("3".equals(vo.getTacheId()) && vo.getOutStorageId() != null
				&& vo.getOutStorageId().trim().length() > 0) {
			right_storage = right_storage + vo.getOutStorageId();
		} else if ("4".equals(vo.getTacheId()) && vo.getInStorageId() != null
				&& vo.getInStorageId().trim().length() > 0) {
			right_storage = right_storage + vo.getInStorageId();
		}
		sotrage_num = comdao.count(right_storage);
		if (sotrage_num < 1) {
			return "4";
		}

		// 如果下一步骤是结束，则先进行出入库相关操作，如果操作成功再流转订单，如果失败，返回失败信息
		String tTacheId = this.qryNextTache(vo.getOrderId(), vo.getAppType(),
				vo.getTacheId(), vo.getStateId());
		String manageMode = new SrSalesRescBo().getManageMode(vo
				.getSalesRescId());
		int rtnStock = 1; // 处理结果，默认成功
		if (manageMode == null
				|| SalesRescVO.ManageMode_NoMag.equals(manageMode)) {
			return "8";
		}
		if (SalesRescVO.ManageMode_Entity.equals(manageMode)) { // 实体管理模式
			rtnStock = entityStockInOut(vo, tTacheId);
			if (rtnStock == -1) {
				return "-1";
			}
		} else { // 如果是存量管理
			if (tTacheId != null && "5".equals(tTacheId)) { // 如果下一步是结束则处理库存仓库数量
				vo.setResBCode(null);
				vo.setResECode(null);
				// 处理营销资源的出入库
				rtnStock = this.updateStockInOut(vo.getSalesRescId(), vo
						.getActAmount(), vo.getOutStorageId(), vo
						.getInStorageId());
				if (rtnStock == 2) {
					return "6";
				} else if (rtnStock == -1) {
					return "7";
				}
			}
		}

		// 公用流转模块：公用流转操作。同时把订单相关信息更新
		vo = this.doFlowControl(vo);
		if (vo == null) {
			return "5";
		}
		// 更新公共模块日志,记录RC_order表的变动
		RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
		RcPublicLogVO logVO = new RcPublicLogVO();
		logVO.setAct("M");
		logVO.setReworkTime(DAOUtils.getFormatedDate());
		logVO.setReworkTable("rc_order");
		logVO.setReworkWen(vo.getOperId());
		logVO.setReworkIp(ip);
		logDao.logVO(logVO, tempvo1, vo);

		return result;
	}

	/**
	 * 判断订单中的实例出入库安什么方式进行，包括2种具体见RcOrderVO中说明 如果是按上传文件的形式进行的，则组装需要处理的实例编码的集合
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return String
	 */
	public String setEntityHandleType(RcOrderVO vo) {
		if (vo == null || vo.getOrderId() == null
				|| vo.getOrderId().trim().length() < 1) {
			return null;
		}
		String handleType = null;
		RcOrderListDAO dao = SrDAOFactory.getInstance().getRcOrderListDAO();
		String cond = " ORDER_ID=" + vo.getOrderId();
		List list = dao.findByCond(cond);
		if (list != null && list.size() > 0) {
			handleType = RcOrderVO.HandleType_Entity_ImportFile;
			vo.setEntityCodeList(list);
		} else if (vo.getResBCode() != null
				&& vo.getResBCode().trim().length() > 0
				&& vo.getResECode() != null
				&& vo.getResECode().trim().length() > 0) {
			handleType = RcOrderVO.HandleType_Entity_SeriesCode;
		} else {
			handleType = RcOrderVO.HandleType_Entity_SegCode;
		}
		vo.setHandleType(handleType);
		return handleType;
	}

	/**
	 * 根据营销资源id确定需要操作的表，同时把familyId和表名设置进RcOrder中
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return String
	 */
	public String setEntityTableName(RcOrderVO vo) {
		if (vo == null || vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1) {
			return null;
		}
		String tableName = "";
		SalesRescDAO salesRescDao = SrDAOFactory.getInstance()
				.getSalesRescDAO();
		RcFamilyEntityRelaDAO relaDao = SrDAOFactory.getInstance()
				.getRcFamilyEntityRelaDAO();
		SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(vo
				.getSalesRescId());
		if (salesRescVO != null && salesRescVO.getFamilyId() != null
				&& salesRescVO.getFamilyId().trim().length() > 0) {
			vo.setFamilyId(salesRescVO.getFamilyId());
			List list = relaDao.findByCond(" FAMILY_ID="
					+ salesRescVO.getFamilyId());
			if (list != null && list.size() > 0 && list.get(0) != null) {
				tableName = ((RcFamilyEntityRelaVO) list.get(0))
						.getEntityTabName();
			}
		}
		if ("".equals(tableName)) {
			tableName = "rc_entity2";
		}
		vo.setEntityTabName(tableName);
		return tableName;
	}

	/**
	 * 实体资源的出入库
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return int：-1，参数缺失。操作是否成功的标志
	 */
	public int entityStockInOut(RcOrderVO vo, String tTacheId) {
		if (vo == null || vo.getAppType() == null || vo.getTacheId() == null
				|| vo.getTacheId().trim().length() < 1 || tTacheId == null
				|| tTacheId.trim().length() > 1) {
			return -1;
		}

		int count = 0;
		// 判断实例的出入库的类别(文件上传、起始终止编码类型)
		this.setEntityHandleType(vo);
		// 根据营销资源id确定需要操作的表，同时把familyId和表名设置进RcOrder中
		this.setEntityTableName(vo);
		// 如果现在的步骤是出库,则处理出库结束的流程;如果是入库,则处理入库结束流程
		if (RcOrderOperStateVO.Tache_outStorage.equals(vo.getTacheId())) {
			if (vo.getOutStorageId() == null
					|| vo.getOutStorageId().trim().length() < 1) {
				return -1;
			}
			// 如果下一步流程不是结束，且动作是正常则把所有需要出入库的实例状态变为“在途”
			if (!"5".equals(tTacheId)) {
				if ("n".equals(vo.getStateId()) && !"C".equals(vo.getAppType()))
					this.changeEntityStatus(vo,
							ParamsConsConfig.RcEntityStateInValide);

			} else {
				// 如果下一步是结束，把RC_ENTITY表中起始编码、终止编码范围内的实例数据RC_ENTITY表，迁移到RC_ENTITY_OUT_LOG表中
				count = this.outEntityToWaste(vo);
			}
		} else { // 如果是入库
			if (vo.getInStorageId() == null
					|| vo.getInStorageId().trim().length() < 1) {
				return -1;
			}
			// 如果下一步不是结束，且动作是回退则把实例状态改为“在库”
			if (!"5".equals(tTacheId)) {
				if ("b".equals(vo.getStateId())) {
					this.changeEntityStatus(vo,
							ParamsConsConfig.RcEntityStateValide);
				}
			} else { // 如果下一步是结束，处理结束事务
				boolean isOut = this.isOutBeforeInStorage(vo.getAppType());
				if (isOut) { // 如果前一步是出库则把编码之内的实例改变仓库，并且要把实例的状态变为“在库”
					if (vo.getOutStorageId() == null
							|| vo.getOutStorageId().trim().length() < 1) {
						return -1;
					}
					// 把实例状态变为“在库”
					this.changeEntityStatus(vo,
							ParamsConsConfig.RcEntityStateValide);
					// 更新实例的仓库信息
					count = this.updateEntityStorage(vo);
				} else {
					// 如果前一步不是出库则把编码内的实例插入仓库，如果有实体属性，则要插入实体属性
					count = this.inEntityStorage(vo);
				}
			}
		}
		// 返回成功标志
		return 1;
	}

	/**
	 * 改变实例的状态(在库、在途)，分成按文件上传和按起始终止号方式更新
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @param state
	 *            String
	 * @return boolean
	 */
	private boolean changeEntityStatus(RcOrderVO vo, String state) {
		if (vo == null || state == null || state.trim().length() < 1)
			return false;
		int count = 0;
		SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
		String tableName = DAOUtils.filterQureyCond(vo.getEntityTabName());
		String sql = " update " + tableName + " set state = '" + state
				+ "' where ";
		String cond = " SALES_RESOURCE_ID=" + vo.getSalesRescId() + " ";
		if (RcOrderVO.HandleType_Entity_ImportFile.equals(vo.getHandleType())) { // 文件上传方式的实例的处理
			List entityList = vo.getEntityCodeList();
			if (entityList == null || entityList.size() < 1) {
				return false;
			}
			/*
			 * cond += " and exists(select rc_order_list.resource_instance_code
			 * from rc_order_list where " + tableName +
			 * ".resource_instance_code=rc_order_list.resource_instance_code and "
			 * + " rc_order_list.order_id=" + vo.getOrderId() + " and
			 * rc_order_list.SALES_RESOURCE_ID=" + vo.getSalesRescId() + ")";
			 */
			cond += " and resource_instance_id in (select rc_order_list.resource_instance_id from rc_order_list where  "
					+ " rc_order_list.order_id="
					+ vo.getOrderId()
					+ " and rc_order_list.SALES_RESOURCE_ID="
					+ vo.getSalesRescId() + ")";
			count = comDao.excute(sql + cond); // 更新实例状态
		} else if (RcOrderVO.HandleType_Entity_SeriesCode.equals(vo
				.getHandleType())) { // 起始终止号码方式的实例处理
			if ((vo.getResBCode() == null || vo.getResBCode().trim().length() < 1)
					&& (vo.getResECode() == null || vo.getResECode().trim()
							.length() < 1)) {
				return false;
			}
			if (vo.getResBCode() != null
					&& vo.getResBCode().trim().length() > 0)
				cond += " and resource_instance_code>='"
						+ DAOUtils.filterQureyCond(vo.getResBCode())
						+ "' and  length(resource_instance_code)>=length('"
						+ DAOUtils.filterQureyCond(vo.getResBCode()) + "') ";
			if (vo.getResECode() != null
					&& vo.getResECode().trim().length() > 0) {
				cond += " and resource_instance_code<='"
						+ DAOUtils.filterQureyCond(vo.getResECode())
						+ "' and length(resource_instance_code)<=length('"
						+ DAOUtils.filterQureyCond(vo.getResECode()) + "') ";
			}
			count = comDao.excute(sql + cond); // 更新实例状态
		} else {// 处理分段起至,终止编码
			RcOrderSegInfoVO[] segs = vo.getRSeg();
			String tmp = "";
			if (segs != null) {
				for (int i = 0; i < segs.length; i++) {
					if (segs[i] != null && !"".equals(segs[i].getResBCode())
							&& !"".equals(segs[i].getResECode())) {
						tmp += " and middle_code>=" + segs[i].getResBCode()
								+ " and  middle_code<=" + segs[i].getResECode();
						if (!"".equals(segs[i].getPreCode())) {
							tmp += " and pre_code= '"
									+ segs[i].getPreCode().trim() + "'";
						}
						if (!"".equals(segs[i].getPostCode())) {
							tmp += " and post_code= '"
									+ segs[i].getPostCode().trim() + "'";
						}
						count += comDao.excute(sql + cond + tmp); // 更新实例状态
						tmp = "";
					}
				}
				RcOrderDAO orderdao = (RcOrderDAO) SrDAOFactory.getInstance()
						.getRcOrderDAO();
				// 如果是出库情况 写RC_SEG_ORDER
				if (segs.length > 0 && "3".equals(vo.getTacheId())) {
					orderdao.insertSegOrder(segs);
				}
			}
		}

		if (count < 1)
			return false;
		else
			return true;
	}

	/**
	 * 跟新该订单需要处理的实例的仓库。根据实例的处理方式不同(文件上传或起始终止编码方式)进行不同的更新库存处理
	 * 从rc_order_list表中查询所需操作的实例或根据起始终止编码确定 根据所操作的资源不同(rc_entity或号码或SIM卡)
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return int
	 */
	public int updateEntityStorage(RcOrderVO vo) {
		if (vo == null || vo.getOutStorageId() == null
				|| vo.getOutStorageId().trim().length() < 1
				|| vo.getInStorageId() == null
				|| vo.getInStorageId().trim().length() < 1
				|| vo.getOrderId() == null
				|| vo.getOrderId().trim().length() < 1
				|| vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1
				|| vo.getEntityTabName() == null
				|| vo.getEntityTabName().trim().length() < 1) {
			return -1;
		}
		RcEntityDAO2 entityDao2 = SrDAOFactory
				.getInstance().getRcEntityDAO2();
		int count = 0;
		// 根据实例的处理方式不同(文件上传或起始终止编码方式)进行不同的更新库存处理
		String tableName = DAOUtils.filterQureyCond(vo.getEntityTabName());
		if (RcOrderVO.HandleType_Entity_ImportFile.equals(vo.getHandleType())) {
			List entityList = vo.getEntityCodeList();
			if (entityList == null || entityList.size() < 1) {
				return 0;
			}
			SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
			String sql = " update "
					+ tableName
					+ " set storage_id = "
					+ DAOUtils.filterQureyCond(vo.getInStorageId())
					+ " where storage_id = "
					+ DAOUtils.filterQureyCond(vo.getOutStorageId())
					+ " and exists(select rc_order_list.resource_instance_code from rc_order_list where  "
					+ tableName
					+ ".resource_instance_code=rc_order_list.resource_instance_code and "
					+ " rc_order_list.order_id=" + vo.getOrderId()
					+ " and rc_order_list.SALES_RESOURCE_ID="
					+ vo.getSalesRescId() + ")";
			count = comDao.excute(sql);
			Debug.print(sql);
		} else if (RcOrderVO.HandleType_Entity_SeriesCode.equals(vo
				.getHandleType())) {
			if ((vo.getResBCode() == null || vo.getResBCode().trim().length() < 1)
					&& (vo.getResECode() == null || vo.getResECode().trim()
							.length() < 1)) {
				return -1;
			}

			count = entityDao2.updateStorageByCode(vo.getEntityTabName(), vo
					.getSalesRescId(), vo.getResBCode(), vo.getResECode(), vo
					.getOutStorageId(), vo.getInStorageId());
		} else {
			SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
			String sql = " update " + tableName + " set storage_id = "
					+ DAOUtils.filterQureyCond(vo.getInStorageId())
					+ " where storage_id = "
					+ DAOUtils.filterQureyCond(vo.getOutStorageId());
			RcOrderSegInfoVO[] segs = vo.getRSeg();
			String cond = "";
			for (int i = 0; i < segs.length; i++) {
				if (segs[i] != null && !"".equals(segs[i].getResBCode())
						&& !"".equals(segs[i].getResECode())) {
					cond += " and middle_code>=" + segs[i].getResBCode()
							+ " and  middle_code<=" + segs[i].getResECode();
					if (!"".equals(segs[i].getPreCode())) {
						cond += " and pre_code= '"
								+ segs[i].getPreCode().trim() + "'";
					}
					if (!"".equals(segs[i].getPostCode())) {
						cond += " and post_code= '"
								+ segs[i].getPostCode().trim() + "'";
					}
					count += comDao.excute(sql + cond); // 更新实例状态
					cond = "";

				}
			}
			/*
			 * String bCode =""; String eCode = ""; for (int i = 0; i <
			 * segs.length; i++) { bCode = segs[i].getResBCode().trim(); eCode
			 * =segs[i].getResECode().trim(); if
			 * (segs[i]!=null&&!"0".equals(bCode)&&!"0".equals(eCode)) {
			 * count+=entityDao2.updateStorageByCode(vo.getEntityTabName(), vo
			 * .getSalesRescId(),
			 * segs[i].getPreCode().trim()+bCode+segs[i].getPostCode(),
			 * segs[i].getPreCode().trim()+eCode+segs[i].getPostCode(), vo
			 * .getOutStorageId(), vo.getInStorageId()); } }
			 */
		}

		return count;
	}

	/**
	 * 更新相应的流入和流出仓库的库存,在现有库存的基础上增加或减少相应数量物资
	 * 
	 * @param actAmount
	 * @param outStorageId
	 * @param inStorageId
	 * @return -1:更新库存失败；1：更新库存成功；2：库存不足，不能更新
	 */
	public int updateStockInOut(String salesRescId, String actAmount,
			String outStorageId, String inStorageId) {
		if (salesRescId == null || salesRescId.trim().length() < 1
				|| actAmount == null || actAmount.trim().length() < 1) {
			return -1;
		}
		if ((outStorageId == null || outStorageId.trim().length() < 1)
				&& (inStorageId == null || inStorageId.trim().length() < 1)) {
			return -1;
		}
		int result = 1;
		try {
			RcStockDAO dao = (RcStockDAO) SrDAOFactory.getInstance()
					.getRcStockDAO();
			if (outStorageId != null && outStorageId.trim().length() > 0) {
				RcStockVO vo = dao.findByPrimaryKey(salesRescId, outStorageId);
				if (vo != null && vo.getStockAmount() != null
						&& vo.getStockAmount().trim().length() > 0) {
					double actmountTemp1 = Double.parseDouble(vo
							.getStockAmount())
							- Double.parseDouble(actAmount);
					if (actmountTemp1 >= 0) {
						dao.updateAmount(outStorageId, salesRescId, String
								.valueOf(actmountTemp1));
					} else {
						return 2;
					}
				} else {
					return 2;
				}
			}
			if (inStorageId != null && inStorageId.trim().length() > 0) {
				RcStockVO vo = dao.findByPrimaryKey(salesRescId, inStorageId);
				if (vo == null) {
					vo = new RcStockVO();
					vo.setSalesRescId(salesRescId);
					vo.setStorageId(inStorageId);
					vo.setStockAmount(actAmount);
					dao.insert(vo);
				} else {
					if (vo.getStockAmount() == null
							|| vo.getStockAmount().trim().length() < 1) {
						vo.setStockAmount("0");
					}
					double actmountTemp2 = Double.parseDouble(vo
							.getStockAmount())
							+ Double.parseDouble(actAmount);
					dao.updateAmount(inStorageId, salesRescId, String
							.valueOf(actmountTemp2));
				}
			}
		} catch (DAOSystemException e) {
			result = -1;
			throw e;
		}
		return result;
	}

	/**
	 * 根据订单类型查找该类型订单的流出仓库或流出仓库是否为空
	 * 
	 * @param appType
	 * @return Map: 包括:appType:订单类型 inStorage：1为需要指定，0为不需要指定
	 *         outStorage：1为需要指定，0为不需要指定
	 */
	public Map qryInOutSetting(String appType) {
		Map map = new HashMap();
		RcAppTypeVO vo = null;
		RcAppTypeDAO dao = SrDAOFactory.getInstance().getRcAppTypeDAO();
		if (appType != null && appType.trim().length() > 0) {
			vo = dao.findByPk(appType);
		}
		if (vo != null) {
			map.put("appType", appType);
			if (vo.getInStyle() != null && ("n".equals(vo.getInStyle().trim()))) {
				map.put("inStorage", "0");
			} else if (vo.getInStyle() != null
					&& vo.getInStyle().trim().length() > 0) {
				map.put("inStorage", "1");
			}
			if (vo.getOutStyle() != null
					&& ("n".equals(vo.getOutStyle().trim()))) {
				map.put("outStorage", "0");
			} else if (vo.getOutStyle() != null
					&& vo.getOutStyle().trim().length() > 0) {
				map.put("outStorage", "1");
			}
		}
		return map;
	}

	/**
	 * 根据传来的订单主键字符串查询订单列表，为了打印订单
	 * 
	 * @param orderIds
	 *            :格式为id1,id2,id3
	 * @return
	 */
	public List qryPrintOrders(String orderIds) {
		if (orderIds == null || orderIds.trim().length() <= 0) {
			return new ArrayList();
		}
		PageModel page = this.getOrderInfoByOrderId_Excel(orderIds, -5, -5);
		if (page != null) {
			return page.getList();
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 公用流转模块：公用流转操作，根据订单信息更新订单下一步要流转的信息，包括更新下一步要流转的步骤，处理时间，处理人，处理人部门
	 * 注意订单RcOrderVO中包含的信息都将被更新，请在调用本方法前查询保证订单vo信息是最新的 并且记录下订单处理日志
	 * 
	 * @param vo
	 *            ，必须包含订单类型、订单环节和处理状态，要更新的处理人和处理人所在部门
	 * @param operId
	 * @param departId
	 * @return
	 */
	public RcOrderVO doFlowControl(RcOrderVO vo) {
		if (vo == null || vo.getAppId() == null
				|| vo.getAppId().trim().length() < 1 || vo.getOrderId() == null
				|| vo.getOrderId().trim().length() < 1
				|| vo.getTacheId() == null
				|| vo.getTacheId().trim().length() < 1
				|| vo.getStateId() == null
				|| vo.getStateId().trim().length() < 1
				|| vo.getOperId() == null || vo.getOperId().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1) {
			return null;
		}
		RcOrderDAO orderdao = SrDAOFactory.getInstance().getRcOrderDAO();
		RcOrderExcDAO excdao = SrDAOFactory.getInstance().getRcOrderExcDAO();
		String oldTacheId = vo.getTacheId();

		// 判断流程的下一步，回退和正常流转的处理不同
		String tTacheId = null;
		// 正常流转下查询下一步流程
		tTacheId = this.qryNextTache(vo.getOrderId(), vo.getAppType(),
				oldTacheId, vo.getStateId());
		if (tTacheId == null) {
			return null;
		}
		vo.setTacheId(tTacheId);
		vo.setTacheTime(DateFormatUtils.getFormatedDateTime());
		if ("5".equals(tTacheId))
			vo.setEndTime(vo.getTacheTime());
		orderdao.update(" order_id=" + vo.getOrderId(), vo);

		// 写RC_ORDER_EXC表
		SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
		SequenceManageDAO sequenceManageDAO = seqDAOFactory
				.getSequenceManageDAO();
		RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
		String logId = sequenceManageDAO.getNextSequence("rc_order_exc",
				"log_id");
		rcOrderExcVO.setLogId(logId);
		rcOrderExcVO.setOrderId(vo.getOrderId());
		rcOrderExcVO.setTacheId(oldTacheId);
		rcOrderExcVO.setExcTime(vo.getTacheTime());
		rcOrderExcVO.setDepartId(vo.getDepartId());
		rcOrderExcVO.setOperId(vo.getOperId());
		rcOrderExcVO.setStateId(vo.getStateId());
		rcOrderExcVO.setExcComments(vo.getComments());
		excdao.insert(rcOrderExcVO);

		return vo;
	}

	/**
	 * 根据订单中的现在所处环节和审批结果决定下一步流程应该是什么
	 * 
	 * @param appType
	 *            :订单类型
	 * @param fTacheId
	 *            :环节标识
	 * @param stateId
	 *            :状态
	 * @return 下一步流程环节的id，如果为空则表示数据不完整或没有下一步流转
	 */
	public String qryNextTache(String orderId, String appType, String fTacheId,
			String stateId) {
		if (stateId == null || stateId.trim().length() < 1 || appType == null
				|| appType.trim().length() < 1 || fTacheId == null
				|| fTacheId.trim().length() < 1)
			return null;
		String tTacheId = null;
		RcOrderOperStateDAO dao = SrDAOFactory.getInstance()
				.getRcOrderOperStateDAO();
		RcOrderExcDAO excdao = SrDAOFactory.getInstance().getRcOrderExcDAO();
		if ("b".equals(stateId)) {
			if (orderId != null && orderId.trim().length() > 0) {
				String cond = " order_id="
						+ orderId
						+ " and state_id='n' "
						+ "and exists(select tache_id from RC_ORDER_OPER_STATE "
						+ " where app_type='" + appType + "' and T_TACHE_ID="
						+ fTacheId
						+ " and tache_id=F_TACHE_ID) order by log_id desc";
				List list = excdao.findByCond(cond);
				if (list != null && list.size() > 0)
					tTacheId = ((RcOrderExcVO) list.get(0)).getTacheId();
			}
		} else {
			if (appType != null && appType.trim().length() > 0
					&& fTacheId != null && fTacheId.trim().length() > 0) {
				RcOrderOperStateVO vo = dao.findByPrimaryKey(appType, fTacheId,
						stateId);
				if (vo != null) {
					tTacheId = vo.getTTacheId();
				}
			}
		}
		return tTacheId;
	}

	public RcAppTypeVO getRcAppType(String appTypeId) throws Exception {
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		return rdao.getRcAppType(appTypeId);
	}

	/**
	 * 审批提交的订单
	 * 
	 * @param rcOrderList
	 * @param operId
	 * @param departId
	 * @param ipStr
	 * @return
	 */
	public int checkPassRCAppOrder(List rcOrderList, String operId,
			String departId, String ipStr) {
		int retv = 0;
		RcOrderVO oldRcOrderVO = null;
		RcOrderVO newRcOrderVO = null;
		RcPublicLogVO rcPublicLogVO = new RcPublicLogVO();

		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		if (rcOrderList != null && rcOrderList.size() > 0) {
			RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
					.getRcOrderDAO();
			RcOrderExcDAO logdao = (RcOrderExcDAO) SrDAOFactory.getInstance()
					.getRcOrderExcDAO();
			RcPublicLogDAO publiclogdao = (RcPublicLogDAO) SrDAOFactory
					.getInstance().getRcPublicLogDAO();

			SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
			SequenceManageDAO sequenceManageDAO = seqDAOFactory
					.getSequenceManageDAO();

			// 判断流入流出仓库是否应该为空的map，1代表不为空，0代表为空
			Map inoutMap = null;

			String logId = "";
			for (int i = 0; i < rcOrderList.size(); i++) {

				RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList.get(i);
				String nextTacheIdSql = "";
				int nextTacheId = 0;

				// 查询流入流出仓库是否应为空的map
				if (inoutMap == null) {
					inoutMap = this.qryInOutSetting(rcOrderVO.getAppType());
				}
				List oldList = rdao.getRcOrderDataByOrderId(rcOrderVO
						.getOrderId());
				if (oldList != null && oldList.size() > 0) {
					oldRcOrderVO = (RcOrderVO) oldList.get(0);
					nextTacheIdSql = "select * from RC_ORDER_OPER_STATE where app_type='"
							+ rcOrderVO.getAppType()
							+ "' and f_tache_id="
							+ oldRcOrderVO.getTacheId()
							+ " and state_id='"
							+ rcOrderVO.getStateId() + "'";
					nextTacheId = rdao.getTacheId(nextTacheIdSql);
				}
				String temp_requireTime = "";
				if ("INFORMIX".equals(databaseType)) {
					temp_requireTime = "to_date('" + rcOrderVO.getRequireTime()
							+ "','%Y-%m-%d %H:%M:%S')";
				} else {
					temp_requireTime = "to_date('" + rcOrderVO.getRequireTime()
							+ "','yyyy-mm-dd hh24:mi:ss')";
				}
				String tacheTime = "";
				if ("INFORMIX".equals(databaseType)) {
					tacheTime = "to_date('"
							+ DateFormatUtils.getFormatedDateTime()
							+ "','%Y-%m-%d %H:%M:%S')";
				} else {
					tacheTime = "to_date('"
							+ DateFormatUtils.getFormatedDateTime()
							+ "','yyyy-mm-dd hh24:mi:ss')";
				}

				String strSql = " update rc_order set ";
				// 处理流出仓库的设置
				if (rcOrderVO.getOutStorageId() != null
						&& "".equals(rcOrderVO.getOutStorageId())) {
					rcOrderVO.setOutStorageId(null);
				}
				if (inoutMap != null && inoutMap.get("outStorage") != null
						&& ("0".equals((String) inoutMap.get("outStorage")))) {
					// 根据订单类型判断，如果流出仓库需要设置则设置为传入仓库，如果需要为空，则设置为空
					rcOrderVO.setOutStorageId(null);
				}
				strSql += " out_storage_id=" + rcOrderVO.getOutStorageId()
						+ ",";
				// 处理流入仓库的设置
				if (rcOrderVO.getInStorageId() != null
						&& "".equals(rcOrderVO.getInStorageId())) {
					rcOrderVO.setInStorageId(null);
				}
				if (inoutMap != null && inoutMap.get("inStorage") != null
						&& ("0".equals((String) inoutMap.get("inStorage")))) {
					// 根据订单类型判断，如果流入仓库需要设置则设置为传入仓库，如果需要为空，则设置为空
					rcOrderVO.setInStorageId(null);
				}
				strSql += " in_storage_id=" + rcOrderVO.getInStorageId() + " ,";

				if (rcOrderVO.getStateId() != null
						&& !"".equals(rcOrderVO.getStateId())) {
					strSql += "  state_id='" + rcOrderVO.getStateId() + "', ";
				}
				if (rcOrderVO.getAppType() != null
						&& !"".equals(rcOrderVO.getAppType())) {
					strSql += "  app_type='" + rcOrderVO.getAppType() + "', ";
				}
				if (nextTacheId != 0) {
					strSql += "  tache_id=" + nextTacheId + ",tache_time = "
							+ tacheTime + ", ";
				}
				strSql += " oper_id=" + operId + ",depart_id=" + departId
						+ ",act_amount=" + rcOrderVO.getAppAmount()
						+ ",require_time=" + temp_requireTime
						+ ",res_comments='" + rcOrderVO.getResComments()
						+ "',comments='" + rcOrderVO.getComments()
						+ "' where order_id=" + rcOrderVO.getOrderId()
						+ " and app_id=" + rcOrderVO.getAppId();

				RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();
				logId = sequenceManageDAO.getNextSequence("rc_order_exc",
						"log_id");
				rcOrderExcVO.setLogId(logId);
				rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
				rcOrderExcVO.setTacheId("2"); // 2为审批;
				rcOrderExcVO.setExcTime(DateFormatUtils.getFormatedDateTime());
				rcOrderExcVO.setDepartId(oldRcOrderVO.getDepartId());
				rcOrderExcVO.setOperId(operId);
				rcOrderExcVO.setStateId(rcOrderVO.getStateId());
				rcOrderExcVO.setExcComments(rcOrderVO.getComments());

				rdao.updateRcOrderData(strSql);
				logdao.insert(rcOrderExcVO);
				List newList = rdao.getRcOrderDataByOrderId(rcOrderVO
						.getOrderId());
				if (newList != null && newList.size() > 0) {
					newRcOrderVO = (RcOrderVO) newList.get(0);
				}
				// 如果有代理商的信息则插入代理商的信息
				insertCheckOrderAgentVO(rcOrderVO);
				// 记录通用日至
				rcPublicLogVO.setReworkIp(ipStr);
				rcPublicLogVO.setReworkTable("rc_order");
				rcPublicLogVO.setReworkWen(operId);
				rcPublicLogVO.setReworkTime(DateFormatUtils
						.getFormatedDateTime());
				rcPublicLogVO.setAct("M");
				publiclogdao.logVO(rcPublicLogVO, oldRcOrderVO, newRcOrderVO);

				retv++;
			}
		}
		return retv;
	}

	/**
	 * 从订单信息中提取出代理商信息并插入数据表,如果没有代理商信息返回空值 如果已经存在该记录则更新该记录
	 * 
	 * @param rcordervo
	 *            RcOrderVO
	 * @return RcOrderAgentVO
	 */
	private RcOrderAgentVO insertCheckOrderAgentVO(RcOrderVO rcordervo) {
		if (rcordervo == null || rcordervo.getAgentId() == null
				|| rcordervo.getAgentId().length() < 1
				|| rcordervo.getOrderId() == null
				|| rcordervo.getAppId() == null) {
			return null;
		}
		RcOrderAgentVO vo = null;
		RcOrderAgentDAO dao = SrDAOFactory.getInstance().getRcOrderAgentDAO();
		// 查询是否已经存在该记录了
		vo = dao.findByPrimaryKey(rcordervo.getAppId(), rcordervo.getOrderId());
		if (vo != null && vo.getOrderId() != null
				&& vo.getOrderId().trim().length() > 0 && vo.getAppId() != null
				&& vo.getAppId().trim().length() > 0) {
			if (rcordervo.getDepartId() != null
					&& rcordervo.getDepartId().trim().length() > 0) {
				vo.setDepartId(rcordervo.getDepartId());
			}
			if (rcordervo.getAgentName() != null
					&& rcordervo.getAgentName().trim().length() > 0) {
				vo.setDepartName(rcordervo.getAgentName());
			}
			if (rcordervo.getAcceptType() != null
					&& rcordervo.getAcceptType().trim().length() > 0) {
				vo.setAcceptType(rcordervo.getAcceptType());
			}
			dao.update(vo.getAppId(), vo.getOrderId(), vo);
		} else {
			vo = new RcOrderAgentVO();
			vo.setOrderId(rcordervo.getOrderId());
			vo.setAppId(rcordervo.getAppId());
			vo.setDepartId(rcordervo.getAgentId());
			vo.setDepartName(rcordervo.getAgentName());
			vo.setAcceptType(rcordervo.getAcceptType());
			dao.insert(vo);
		}
		return vo;
	}

	public PageModel getOrderInfoByOrderId_Excel(String orderIdStr, int pi,
			int ps) {
		List retList = new ArrayList();
		orderIdStr = orderIdStr.substring(0, orderIdStr.length() - 1);
		String sql_select = "select a.*,c.party_role_name as oper_name,d.storage_name,e.sales_resource_name from rc_order a left join rc_application b on a.app_id=b.app_id left join party_role c on b.oper_id=c.party_role_id left join rc_storage d on a.app_storage_id = d.storage_id left join sales_resource e on a.sales_resource_id=e.sales_resource_id where 1=1 ";
		String sql_select_count = "select count(*) AS COL_COUNTS from rc_order a left join rc_application b on a.app_id=b.app_id left join party_role c on b.oper_id=c.party_role_id left join rc_storage d on a.app_storage_id = d.storage_id left join sales_resource e on a.sales_resource_id=e.sales_resource_id where 1=1 ";

		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		rdao.setSQL(sql_select, sql_select_count, "excel");
		String sql = " and a.order_id in(" + orderIdStr + ")";
		PageModel pm = new PageModel();
		pm = PageHelper.popupPageModel(rdao, sql, pi, ps);
		return pm;
	}

	public List getSalesResourceBySalesResourceId(String saleResourceId) {
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		if (saleResourceId != null && !"".equals(saleResourceId)) {
			retList = rdao.getSalesResourceBySalesResourceId(saleResourceId);
		}
		return retList;
	}

	// /////////////////////////////fan/////////////////////////////////////////////////
	// /**
	// * 根据部门id和营销资源id的字符串查询该部门所属仓库资源的库存
	// * @param deptID:必须是id1,id2,id3的形式
	// * @param saleIds：必须是'id1','id2','id3'的形式
	// * @return
	// */
	// public PageModel queryStockNum(String deptID, String saleIds, int
	// pageIndex,
	// int pageSize) {
	// String cond = "";
	// RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
	// if (deptID != null && deptID.trim().length() > 0) {
	// if (deptID.endsWith(",")) {
	// deptID = deptID.substring(0, deptID.length() - 1);
	// }
	// cond = " and b.depart_id in (" + deptID + ")";
	// }
	// if (saleIds != null && saleIds.trim().length() > 0) {
	// cond += " and a.sales_resource_id in (" + saleIds + ")";
	// }
	// return PageHelper.popupPageModel(dao, cond.toString(), pageIndex,
	// pageSize);
	// }
	private boolean getRealCfg(String lanId, String tableName) {
		RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
		return dao.getRealCfg(lanId, tableName);
	}

	/**
	 * 根据部门id和营销资源id的字符串查询该部门所属仓库资源的库存
	 * 
	 * @param deptID
	 *            :必须是id1,id2,id3的形式 'id1','id2','id3'的形式
	 * @return
	 */
	public PageModel queryStockNum(Map map, int pi, int ps) {
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		PageModel pm = new PageModel();
		String lanId = null;
		String regionId = null;
		String deptID = null;
		String saleIds = null;
		String operId = null;
		String upStorageId = null;
		String storageId = null;
		if (map != null) {
			if (map.get("lanId") != null
					&& ((String) map.get("lanId")).trim().length() > 0)
				lanId = (String) map.get("lanId");
			if (map.get("regionId") != null
					&& ((String) map.get("regionId")).trim().length() > 0)
				regionId = (String) map.get("regionId");
			if (map.get("deptID") != null
					&& ((String) map.get("deptID")).trim().length() > 0)
				deptID = (String) map.get("deptID");
			if (map.get("saleIds") != null
					&& ((String) map.get("saleIds")).trim().length() > 0)
				saleIds = (String) map.get("saleIds");
			if (map.get("operId") != null
					&& ((String) map.get("operId")).trim().length() > 0)
				operId = (String) map.get("operId");
			if (map.get("upStorageId") != null
					&& ((String) map.get("upStorageId")).trim().length() > 0)
				upStorageId = (String) map.get("upStorageId");
			if (map.get("storageId") != null
					&& ((String) map.get("storageId")).trim().length() > 0)
				storageId = (String) map.get("storageId");

		}

		if (regionId != null && regionId.endsWith(",")) {
			regionId = regionId.substring(0, regionId.length() - 1);
		}
		if (storageId != null && storageId.endsWith(",")) {
			storageId = storageId.substring(0, storageId.length() - 1);
		}
		if (deptID != null && deptID.endsWith(",")) {
			deptID = deptID.substring(0, deptID.length() - 1);
		}

		String cond = "";
		String storage_cond = "";
		RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
		String tableName = "rc_entity";
		String rescStateName = "curr_state";
		if (saleIds != null && !"".equals(saleIds)) {
			String id = saleIds.split(",")[0];

			tableName = CommonUtilBo.getTableNameByResouceId(id);
			tableName = ("".equals(tableName)) ? "rc_entity2" : tableName;
			rescStateName = CommonUtilBo.getRescStateNameByTableName(tableName);
		}
		if (getRealCfg(lanId, tableName)) {
			String sql = " select a.storage_id,a.storage_name,a.sales_resource_id,a.sales_resource_name,a.st_count as stock_amount,a.UP_LIMIT,a.DOWN_LIMIT, '' as DEPART_ID,"
					// + operId
					+ "'' as OPER_ID from rc_rp_st_count a ";
			String sql_count = " select count(a.sales_resource_id) as COL_COUNTS from rc_rp_st_count a ";
			String sql_cond = "";
			if (upStorageId != null && upStorageId.trim().length() > 0) {
				sql_cond += " inner join rc_storage b on ( b.storage_id = a.storage_id and b.rc_type =-1 and  b.UP_STORAGE_ID = "
						+ upStorageId
						+ " ) where a.resource_state='"
						+ ParamsConsConfig.RcEntityFreeState + "' ";
			} else {
				sql_cond += " where a.resource_state='"
						+ ParamsConsConfig.RcEntityFreeState + "' ";
			}
			// String cond2 = "";

			if (storageId != null && storageId.trim().length() > 0) { // 如果界面传入的有选择的仓库，则直接用仓库查询
				sql_cond += " and a.storage_id in(" + storageId + ") ";
			} else {
				if (deptID != null && !"".equals(deptID)) {
					sql_cond += " and exists( select e.storage_id from STORAGE_DEPART_RELA e "
							+ " where e.storage_id=a.storage_id and e.DEPART_ID in ("
							+ deptID
							+ " ) union all "
							+ " select y.storage_id from mp_storage y where a.storage_id=y.storage_id "
							+ " and y.oper_id=" + operId + " )";
				} else {
					if (regionId != null && !"".equals(regionId.trim())) {
						sql_cond += " and (exists(select e.storage_id from STORAGE_DEPART_RELA e,mp_operator_depart f "
								+ " where e.storage_id=a.storage_id and e.DEPART_ID=f.DEPART_ID and f.oper_id="
								+ operId
								+ " and f.lan_id="
								+ lanId
								+ " and f.REGION_ID  in ("
								+ regionId
								+ " )) or "
								+ " exists(select 1 from mp_storage g where g.storage_id=a.storage_id and g.STATE='00' and g.OPER_ID="
								+ operId + ")" + ")";
					} else {
						sql_cond += " and (exists(select e.storage_id from STORAGE_DEPART_RELA e,mp_operator_depart f "
								+ " where e.storage_id=a.storage_id and e.DEPART_ID=f.DEPART_ID and f.oper_id="
								+ operId
								+ " and f.lan_id="
								+ lanId
								+ " ) or exists(select 1 from mp_storage g where g.storage_id=a.storage_id and g.STATE='00' and g.OPER_ID="
								+ operId + ")" + ")";
					}
				}
			}
			// sql += cond2;
			if (saleIds != null && saleIds.trim().length() > 0) {
				sql_cond += " and a.sales_resource_id in (" + saleIds + ")";
			}

			sql += sql_cond;
			sql_count += sql_cond;

			dao.setFlag(1); // 设置要查询仓库模板的上下限
			dao.setFiltered(true);

			// 查询实体管理的库存
			dao.setSQL_SELECT(sql);
			dao.setSQL_SELECT_COUNT(sql_count);
			pm = PageHelper.popupPageModel(dao, "", pi, ps);
		} else { // 实时动态查询

			// start 组装查询条件
			if (saleIds != null && saleIds.trim().length() > 0) {
				cond += " and a.sales_resource_id in (" + saleIds + ")";
			}

			if (storageId != null && storageId.trim().length() > 0) { // 如果界面传入的有选择的仓库，则直接用仓库查询
				cond += " and a.storage_id in(" + storageId + ") ";
			} else {
				if (deptID != null && deptID.trim().length() > 0) {
					// cond = " and b.depart_id in (" + deptID + ")";
					cond += " and exists( select g.storage_id from STORAGE_DEPART_RELA g "
							+ " where g.storage_id=a.storage_id and g.DEPART_ID in ("
							+ deptID
							+ " ) union all "
							+ " select y.storage_id from mp_storage y where a.storage_id=y.storage_id "
							+ " and y.oper_id=" + operId + " )";
				} else if (operId != null) {
					String cond_temp1 = " ";
					if (lanId != null && lanId.trim().length() > 0) {
						cond_temp1 += " and h.LAN_ID = " + lanId + " ";
					}
					if (regionId != null && regionId.trim().length() > 0) {
						cond_temp1 += " and h.REGION_ID in (" + regionId + ") ";
					}

					cond += " and (exists(select g.storage_id from STORAGE_DEPART_RELA g,mp_operator_depart h "
							+ " where g.storage_id=a.storage_id and g.DEPART_ID=h.DEPART_ID and h.oper_id="
							+ operId
							+ cond_temp1
							+ ") or "
							+ " exists(select 1 from mp_storage j where j.storage_id=a.storage_id and j.STATE='00'"
							+ " and j.OPER_ID=" + operId + "))";
				}
			}
			if (upStorageId != null && upStorageId.trim().length() > 0) {
				storage_cond = " and d.UP_STORAGE_ID = " + upStorageId + " ";
			}

			// end 组装查询条件

			// 主sql语句
			StringBuffer sql_buff = new StringBuffer();
			StringBuffer sql_count_buff = new StringBuffer();
			if ("rc_stock".equalsIgnoreCase(tableName)) { // 存量管理的
				sql_buff
						.append("select c.storage_id,d.storage_name,c.sales_resource_id,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT, '");
				sql_buff.append(deptID).append("' as DEPART_ID,")
						.append(operId).append(" as OPER_ID from ");
				sql_buff
						.append(" ( select a.STORAGE_ID,a.SALES_RESOURCE_ID,sum(a.STOCK_AMOUNT) as stock_amount ");
				sql_buff.append(" from rc_stock a where 1=1 ");
				sql_buff.append(cond).append(
						" group by a.storage_id, a.sales_resource_id ) c ");
				sql_buff
						.append(
								"inner join RC_STORAGE d on (c.storage_id=d.storage_id ")
						.append(") ");
				sql_buff
						.append(" inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ");
				sql_buff
						.append(" left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");
				sql_buff.append(" where d.rc_type=-1 " + storage_cond);

				sql_count_buff
						.append("select count(c.sales_resource_id) as COL_COUNTS from ");
				sql_count_buff
						.append(" ( select a.STORAGE_ID,a.SALES_RESOURCE_ID,sum(a.STOCK_AMOUNT) as stock_amount ");
				sql_count_buff.append(" from rc_stock a where 1=1 ");
				sql_count_buff.append(cond).append(
						" group by a.storage_id, a.sales_resource_id ) c ");
				sql_count_buff
						.append(
								"inner join RC_STORAGE d on (c.storage_id=d.storage_id ")
						.append(") ");
				sql_count_buff
						.append(" inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ");
				sql_count_buff
						.append(" left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");
				sql_count_buff.append(" where d.rc_type=-1 " + storage_cond);

			} else { // 实体管理的
				sql_buff
						.append("select c.storage_id,d.storage_name,c.sales_resource_id,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT, '");
				sql_buff.append(deptID).append("' as DEPART_ID,")
						.append(operId).append(" as OPER_ID  from ");
				sql_buff
						.append("( select distinct a.storage_id,a.sales_resource_id,count(a.resource_instance_id) as stock_amount from ");
				sql_buff.append(tableName).append(
						" a where a." + rescStateName + "='");
				sql_buff.append(ParamsConsConfig.RcEntityFreeState).append(
						"' and a.state='");
				sql_buff.append(ParamsConsConfig.RcEntityStateValide).append(
						"' ");
				sql_buff.append(cond).append(
						" group by a.storage_id, a.sales_resource_id  ) c ");
				sql_buff
						.append(
								"inner join RC_STORAGE d on (c.storage_id=d.storage_id ")
						.append(") ");
				sql_buff
						.append(" inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ");
				sql_buff
						.append(" left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");
				sql_buff.append(" where d.rc_type=-1 " + storage_cond);

				sql_count_buff
						.append("select count(c.sales_resource_id) as COL_COUNTS from ");
				sql_count_buff
						.append("( select distinct a.storage_id,a.sales_resource_id,count(a.resource_instance_id) as stock_amount from ");
				sql_count_buff.append(tableName).append(
						" a where a." + rescStateName + "='");
				sql_count_buff.append(ParamsConsConfig.RcEntityFreeState)
						.append("' and a.state='");
				sql_count_buff.append(ParamsConsConfig.RcEntityStateValide)
						.append("' ");
				sql_count_buff.append(cond).append(
						" group by a.storage_id, a.sales_resource_id  ) c ");
				sql_count_buff
						.append(
								"inner join RC_STORAGE d on (c.storage_id=d.storage_id ")
						.append(") ");
				sql_count_buff.append(" where d.rc_type=-1 " + storage_cond);
			}

			// StringBuffer sql_informix_entity = new StringBuffer();
			// sql_informix_entity.append("select c.storage_id,d.storage_name,c.sales_resource_id,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT, '");
			// sql_informix_entity.append( deptID ).append(
			// "' as DEPART_ID,").append( operId).append(
			// " as OPER_ID  from table(MULTISET(");
			// sql_informix_entity.append(
			// " select distinct a.storage_id,a.sales_resource_id,count(a.resource_instance_id) as stock_amount from ");
			// sql_informix_entity.append(
			// tableName).append(" a,STORAGE_DEPART_RELA b ");
			// sql_informix_entity.append(
			// " where a."+rescStateName+"='").append(
			// ParamsConsConfig.RcEntityFreeState).append("' and a.state='");
			// sql_informix_entity.append(
			// ParamsConsConfig.RcEntityStateValide).append(
			// "' and a.storage_id=b.storage_id ").append( cond);
			// sql_informix_entity.append(
			// " group by a.storage_id, a.sales_resource_id )) c ");
			// sql_informix_entity.append(
			// "inner join RC_STORAGE d on (c.storage_id=d.storage_id and d.rc_type=-1").append(storage_cond
			// ).append( ") ");
			// sql_informix_entity.append(
			// " inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ");
			// sql_informix_entity.append(
			// " left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");

			// StringBuffer sql_informix_stock = new StringBuffer();
			// sql_informix_stock.append("select c.storage_id,d.storage_name,c.sales_resource_id,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT, '");
			// sql_informix_stock.append( deptID).append(
			// "' as DEPART_ID,").append(
			// operId).append(" as OPER_ID").append(" from table(MULTISET(");
			// sql_informix_stock.append(
			// "select a.STORAGE_ID,a.SALES_RESOURCE_ID,sum(a.STOCK_AMOUNT) as stock_amount ");
			// sql_informix_stock.append(
			// " from rc_stock a,STORAGE_DEPART_RELA b where a.storage_id=b.storage_id ");
			// sql_informix_stock.append(
			// cond).append(" group by a.storage_id, a.sales_resource_id )) c ");
			// sql_informix_stock.append(
			// "inner join RC_STORAGE d on (c.storage_id=d.storage_id and d.rc_type=-1").append(
			// storage_cond ).append( ") ");
			// sql_informix_stock.append(
			// " inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ");
			// sql_informix_stock.append(
			// " left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");

			String sql = sql_buff.toString();
			String sql_count = sql_count_buff.toString();

			System.out.println("类SrStockBo,方法queryStockNum中执行的sql_entity是:"
					+ sql);
			System.out.println("类SrStockBo,22方法queryStockNum中执行的sql_stock是:"
					+ sql_count);

			dao.setFlag(1); // 设置要查询仓库模板的上下限
			dao.setFiltered(true);

			dao.setSQL_SELECT(sql);
			dao.setSQL_SELECT_COUNT(sql_count);

			pm = PageHelper.popupPageModel(dao, "", pi, ps);
		}
		return pm;
	}

	// ///////////////////////////////////////////和前台界面销售库存的接口////////////////////////////////////////

	private Map entityHandle1(List saleList, List releseList){
	    Map map = new HashMap();
	    SrStockBo bo = new SrStockBo();
	    Map map1 = bo.entitySale(saleList);
	    Map map2 = bo.entityRelease(releseList);
	    if (map1 != null && map2 != null) {
	      String resultFlag1 = (String) map1.get("resultFlag");
	      String continueFlag1 = (String) map1.get("continueFlag");
	      String resultFlag2 = (String) map2.get("resultFlag");
	      String continueFlag2 = (String) map2.get("continueFlag");
	      Map priceMap = (map1.get("price") == null) ? new HashMap() :
	          ( (Map) map1.get("price"));
	      Map priceMap2 = (map2.get("price") == null) ? new HashMap() :
	          ( (Map) map2.get("price"));
	      priceMap.putAll(priceMap2);

	      if ( (resultFlag1 != null && "0".equals(resultFlag1)) ||
	          (resultFlag2 != null && "0".equals(resultFlag2))) {
	        map.put("resultFlag", "0");
	        String errInfo = bo.getSaleReleErrInfo(saleList, releseList);
	        if (errInfo == null || errInfo.trim().length() < 1) {
	          errInfo = "处理失败！";
	        }
	        map.put("failInfo", errInfo);
	      }
	      else {
	        map.put("resultFlag", "1");
	        map.put("failInfo", "处理成功!");
	      }
	      if ( (continueFlag1 != null && "0".equals(continueFlag1)) ||
	          (continueFlag2 != null && "0".equals(continueFlag2))) {
	        map.put("continueFlag", "0");
	      }
	      else {
	        map.put("continueFlag", "1");
	        // 添加价格信息map
	      }
	      map.put("price", priceMap);
	    }
	    else if (map1 != null) {
	      map = map1;
	    }
	    else {
	      map = map2;

	    }
	    Debug.print("类SrStockBean中entityHandle方法----> retMap=" + map);
	    return map;
	}
	/**
	 * 销售和释放资源
	 * 
	 * @param saleList
	 *            ：需要销售的集合
	 * @param releseList
	 *            ：需要释放操作的资源
	 * @return
	 */
	public Map entityHandle(List saleList, List releseList) {
		Map map = new HashMap();
		
		try {
			entityHandle1(saleList, releseList);
		} catch (Exception e) {
			if (e != null) {
				e.printStackTrace();
			}
			map = new HashMap();
			map.put("resultFlag", "0");
			map.put("continueFlag", "1");
			String errInfo = this.getSaleReleErrInfo(saleList, releseList);
			if (errInfo == null || errInfo.trim().length() < 1) {
				errInfo = "处理失败！";
			}
			map.put("failInfo", errInfo);
			// 处理营销资源价格信息
			Map priceMap = this.getSalesRescPriceInfo(saleList);
			if (priceMap == null) {
				priceMap = new HashMap();
			}
			Map priceMap2 = this.getSalesRescPriceInfo(releseList);
			if (priceMap2 == null) {
				priceMap2 = new HashMap();
			}
			priceMap.putAll(priceMap2);
			map.put("price", priceMap);
		}
		Debug.print("-------------SrStockBo:entityHandle: retMap=" + map);
		Debug.print("类SrStockBo中entityHandle方法中的价格信息:---> priceMap="
				+ (Map) map.get("price"));
		return map;
	}

	/**
	 * 处理资源实例销售的方法，对于传入的资源实例会占用资源或减少相应库存量 如果其中一个资源处理失败则程序抛出异常，全部处理(包括成功处理的)都回滚
	 * 
	 * @param list
	 *            ：EntityResourceVO组成的List，每个EntityResourceVO代表一个需要处理的资源实例
	 * @return Map:resultFlag结果标志（1成功，0失败），continueFlag继续受理标志（1 继续 0 不能受理），
	 *         failInfo错误信息。其中continueFlag目前只返回1
	 * @throws Exception
	 *             :保证事务，如果为DAOSystemException则回滚，异常中包含有错误信息
	 */
	public Map entitySale(List list) throws SaleResLogicException {
		Map map = new HashMap();
		String resultFlag = "1";
		String continueFlag = "1";
		StringBuffer failInfo = new StringBuffer();
		int successNum = 0;
		int rtn = -1; // 处理是否出错，默认为出错,如果对资源逻辑处理错误，抛出异常，保证所有资源要么全部处理，要么全部部处理
		if (list == null || list.size() < 1) {
			Debug.print("类SrStockBo，方法entitySale(List list)，传入的资源实例数为0");
			resultFlag = "1";
			failInfo.append("传入的资源实例数为0");
		} else {
			for (int i = 0; i < list.size(); i++) {
				EntityResourceVO vo = (EntityResourceVO) list.get(i);
				if (vo != null) {
					if (vo.getRescInstance2() != null
							&& vo.getRescInstance2().trim().length() > 0) {
						vo.setStatus(ParamsConsConfig.RcEntityUseState);
						rtn = this.entityStatueChange(vo);
						if (rtn == 0) {
							successNum++;
						} else {
							Debug
									.print("类SrStockBo，方法entitySale(List list)，序列号为"
											+ vo.getRescInstance2()
											+ "的资源实例处理失败!");
							resultFlag = "0";
							throw new SaleResLogicException("销售失败，序列号为"
									+ vo.getRescInstance2() + "的资源实例处理失败!");
							// failInfo.append("序列号为"+vo.getRescInstance2()+"的资源实例处理失败!").append("\n");
						}
					} else {
						// 目前是对单个实例依次处理的，所以设为1，相应的实体数量改变函数也是对单个仓库处理数量的
						vo.setAmount("-1");
						rtn = this.entityAmountChange(vo, 2);
						if (rtn == 0) {
							successNum++;
						} else {
							Debug
									.print("类SrStockBo，方法entitySale(List list)，相关营销资源在处理库存数量时失败!vo:"
											+ vo);
							resultFlag = "0";
							throw new SaleResLogicException(
									"销售失败，相关营销资源在处理库存数量时失败!");
							// failInfo.append("相关营销资源在处理库存数量时失败!").append("\n");
						}
					}
				} else {
					Debug
							.print("类SrStockBo，方法entitySale(List list):错误，传入需要处理的物资为空!");
					throw new SaleResLogicException("错误，传入需要处理的物资为空!");
				}
			}
			failInfo.append("成功处理销售！");
		}
		// 得到营销资源的价格信息
		Map priceMap = this.getSalesRescPriceInfo(list);
		if (priceMap == null) {
			priceMap = new HashMap();

		}
		map.put("resultFlag", resultFlag);
		map.put("continueFlag", continueFlag);
		map.put("failInfo", failInfo.toString());
		map.put("price", priceMap);
		return map;
	}

	/**
	 * 资源实例释放方法，对于传入的资源实例会释放资源或增加相应库存量 如果其中一个资源处理失败则程序抛出异常，全部处理(包括成功处理的)都回滚
	 * 
	 * @param list
	 *            ：EntityResourceVO组成的List，每个EntityResourceVO代表一个需要处理的资源实例
	 * @return Map:resultFlag结果标志（1成功，0失败），continueFlag继续受理标志（1 继续 0 不能受理），
	 *         failInfo错误信息。其中continueFlag目前只返回1
	 * @throws Exception
	 *             :保证事务，如果为DAOSystemException则回滚，异常中包含有错误信息
	 */
	public Map entityRelease(List list) throws SaleResLogicException {
		Map map = new HashMap();
		String resultFlag = "1";
		String continueFlag = "1";
		StringBuffer failInfo = new StringBuffer();
		int successNum = 0;
		int rtn = -1; // 处理是否出错，默认为出错,如果对资源逻辑处理错误，抛出异常，保证所有资源要么全部处理，要么全部部处理
		if (list == null || list.size() < 1) {
			resultFlag = "1";
			failInfo.append("传入的资源实例数为0");
			Debug.print("类SrStockBo，方法entityRelease(List list)，传入的资源实例数为0");
		} else {
			for (int i = 0; i < list.size(); i++) {
				EntityResourceVO vo = (EntityResourceVO) list.get(i);
				if (vo != null) {
					if (vo.getRescInstance2() != null
							&& vo.getRescInstance2().trim().length() > 0) {
						vo.setStatus(ParamsConsConfig.RcEntityFreeState);
						rtn = this.entityStatueChange(vo);
						if (rtn == 0) {
							successNum++;
						} else {
							resultFlag = "0";
							Debug
									.print("类SrStockBo，方法entityRelease(List list):资源实例11："
											+ vo.getRescInstance2() + "处理失败!");
							throw new SaleResLogicException("资源实例"
									+ vo.getRescInstance2() + "撤单处理失败!");
							// failInfo.append("资源实例"+vo.getRescInstance2()+"处理失败!").append("\n");
						}
					} else {
						vo.setAmount("1");
						rtn = this.entityAmountChange(vo, 1);
						if (rtn == 0) {
							successNum++;
						} else {
							resultFlag = "0";
							Debug
									.print("类SrStockBo，方法entityRelease(List list):营销资源22："
											+ vo.getSalesRescId() + "处理失败!");
							throw new SaleResLogicException(
									"撤单失败，相关营销资源在处理库存数量时失败!");
							// failInfo.append("相关营销资源在处理库存数量时失败!").append("\n");
						}
					}
				} else {
					Debug
							.print("类SrStockBo，方法entityRelease(List list):错误，传入需要处理的物资为空!");
					throw new SaleResLogicException("错误，传入需要处理的物资为空!");
				}
			}
			failInfo.append("成功处理撤单!");
		}
		// 得到营销资源的价格信息
		Map priceMap = this.getSalesRescPriceInfo(list);
		if (priceMap == null) {
			priceMap = new HashMap();

		}
		map.put("resultFlag", resultFlag);
		map.put("continueFlag", continueFlag);
		map.put("failInfo", failInfo.toString());
		// map.put("price", priceMap);
		return map;
	}

	/**
	 * 更新资源实例状态
	 * 
	 * @param vo
	 *            :需要包含的字段：rescInstance2、status、departId
	 * @return 处理成功时，返回值为0；处理失败时，返回值为-1；
	 */
	private int entityStatueChange(EntityResourceVO vo) {
		if (vo == null || vo.getRescInstance2() == null
				|| vo.getRescInstance2().trim().length() < 1
				|| vo.getStatus() == null || vo.getStatus().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1) {
			if (vo == null) {
				Debug.print("类SrStockBo，方法entityStatueChange参数vo为null，返回!");
			} else {
				Debug
						.print("类SrStockBo，方法entityStatueChange(EntityResourceVO vo)参数vo.getRescInstance2():"
								+ vo.getRescInstance2()
								+ "||vo.getStatus():"
								+ vo.getStatus()
								+ "||vo.getDepartId():"
								+ vo.getDepartId());
			}
			return -1;
		}
		RcEntityDAO entityDao = SrDAOFactory.getInstance().getRcEntityDAO();
		RcSaleLogDAO saleLogDao = SrDAOFactory.getInstance().getRcSaleLogDAO();
		SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
				.getSequenceManageDAO();
		// 在单位有权限查看的仓库中查询资源实例
		String sql = "SELECT  RESOURCE_INSTANCE_ID,A.SALES_RESOURCE_ID,"
				+ "A.RESOURCE_INSTANCE_CODE,A.RESOURCE_LEVEL,A.RESOURCE_KIND,"
				+ "A.LAN_ID,A.OWNER_TYPE,A.OWNER_ID,A.STORAGE_ID,A.CURR_STATE,"
				+ "A.STATE,A.CREATE_DATE,A.EFF_DATE,A.EXP_DATE,A.PK_CALBODY,"
				+ "A.CINVENTORYID,A.VBATCHCODE "
				+ " FROM RC_ENTITY A,STORAGE_DEPART_RELA  B "
				+ " where A.STORAGE_ID=B.STORAGE_ID and B.depart_id=?"
				+ " and A.RESOURCE_INSTANCE_CODE=?";
		String sqlParams[] = new String[] { vo.getDepartId(),
				vo.getRescInstance2() };
		List rtnList = entityDao.findBySql(sql, sqlParams);
		if (rtnList == null || rtnList.size() < 1 || rtnList.get(0) == null) {
			Debug.print("类SrStockBo，方法entityStatueChange，查询为空sql:" + sql
					+ "##depart_id:" + vo.getDepartId()
					+ " ##RESOURCE_INSTANCE_CODE:" + vo.getRescInstance2());
			return -1;
		}
		RcEntityVO voTemp = (RcEntityVO) rtnList.get(0);
		String beforeStatus = voTemp.getRescState();
		// 如果实例的状态不可用，则失败
		if ("00X".equals(voTemp.getState())) {
			Debug.print("类SrStockBo，方法entityStatueChange,实例:"
					+ voTemp.getRescInstanceCode() + "状态为不可用:"
					+ voTemp.getState() + "，失败!");
			return -1;
		}
		// 如果要占有资源，但该资源已经被占有，则错误；如果释放资源，该资源已经处在释放状态，则失败
		if (ParamsConsConfig.RcEntityUseState.equals(vo.getStatus())) {
			if ((beforeStatus == null)
					|| (ParamsConsConfig.RcEntityUseState.equals(beforeStatus))) {
				Debug.print("类SrStockBo，方法entityStatueChange,实例11:"
						+ voTemp.getRescInstanceCode() + "已被占用，销售失败!");
				return -1;
			}
		} else if (ParamsConsConfig.RcEntityFreeState.equals(vo.getStatus())) {
			if ((beforeStatus == null)
					|| (ParamsConsConfig.RcEntityFreeState.equals(beforeStatus))) {
				Debug.print("类SrStockBo，方法entityStatueChange,实例22:"
						+ voTemp.getRescInstanceCode() + "已是可用，释放失败!");
				return -1;
			}
		}
		// 更新资源占用状态
		voTemp.setRescState(vo.getStatus());
		boolean rtnBool = entityDao.update(voTemp.getRescInstanceId(), voTemp);
		if (!rtnBool) {
			Debug.print("类SrStockBo，方法entityStatueChange,更新资源占用状态失败，voTemp:"
					+ voTemp);
			return -1;
		}
		// 添加销售日志记录
		RcSaleLogVO voInsert = new RcSaleLogVO();
		String logId = sequenceManageDAO.getNextSequence("rc_sale_log",
				"log_id");
		voInsert.setLogId(logId);
		voInsert.setSaleTime(DAOUtils.getFormatedDate());
		voInsert.setSalesRescId(vo.getSalesRescId());
		voInsert.setRescInstance2(vo.getRescInstance2());
		voInsert.setDepartId(vo.getDepartId());
		voInsert.setOperId(vo.getOperId());
		voInsert.setCustId(vo.getCustId());
		voInsert.setStyle("A");
		if (ParamsConsConfig.RcEntityUseState.equals(vo.getStatus())) {
			voInsert.setIsOut("o");
			voInsert.setStockAmount("-1");
		} else if (ParamsConsConfig.RcEntityFreeState.equals(vo.getStatus())) {
			voInsert.setIsOut("i");
			voInsert.setStockAmount("1");
		}
		voInsert.setStorageId(voTemp.getStorageId());
		voInsert.setBeforeState(beforeStatus);
		voInsert.setAfterState(vo.getStatus());
		saleLogDao.insert(voInsert);
		Debug.print("类SrStockBo，方法entityStatueChange,添加了销售日志voInsert:"
				+ voInsert);
		return 0;
	}

	/**
	 * 需要更新实例的仓库的数量
	 * 
	 * @param vo
	 *            ：需要包含的字段：salesRescId、departId、amount
	 * @param changeType
	 *            ：更新类型，1是新增，2是减少
	 * @return 处理成功时，返回值为0；处理失败或库存数量少于0时，返回值为-1；
	 */
	private int entityAmountChange(EntityResourceVO vo, int changeType) {
		Debug.print("类SrStockBo，方法entityAmountChange参数changeType:" + changeType
				+ "||vo:" + vo);
		if (vo == null || vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1
				|| vo.getAmount() == null || vo.getAmount().trim().length() < 1) {
			Debug.print("类SrStockBo，方法entityAmountChange参数缺少，错误!");
			return -1;
		}
		RcStockDAO stockDao = SrDAOFactory.getInstance().getRcStockDAO();
		RcSaleLogDAO saleLogDao = SrDAOFactory.getInstance().getRcSaleLogDAO();
		StorageDepartRelaDAO relaDao = SrDAOFactory.getInstance()
				.getStorageDepartRelaDAO();
		SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
				.getSequenceManageDAO();
		String cond = " and b.depart_id =" + vo.getDepartId()
				+ " and a.sales_resource_id=" + vo.getSalesRescId();
		List rtnList = stockDao.findByCond(cond);
		Debug.print("类SrStockBo，方法entityAmountChange查询参数cond:" + cond
				+ "||查询结果:" + rtnList);
		if ((rtnList == null || rtnList.size() < 1 || rtnList.get(0) == null)
				&& changeType == 2) {
			Debug.print("类SrStockBo，方法entityAmountChange查询结果有问题rtnList:"
					+ rtnList);
			return -1;
		}
		RcStockVO voTemp = null;
		String storageId = null;
		boolean needExtra = true;
		for (int i = 0; i < rtnList.size(); i++) {
			voTemp = (RcStockVO) rtnList.get(i);
			storageId = ((RcStockVO) rtnList.get(i)).getStorageId();
			if (voTemp != null && storageId != null
					&& storageId.trim().length() > 0) {
				if (changeType == 1) {
					// 如果是新增资源则数量加1
					int amount = Integer.parseInt(voTemp.getStockAmount())
							+ Integer.parseInt(vo.getAmount());
					stockDao.updateAmount(storageId, vo.getSalesRescId(),
							String.valueOf(amount));
					needExtra = false;
					break;
				} else if (changeType == 2) {
					// 如果是减少物资，则察看数量是否大于amount，如果大于则直接减少数量，如果小于则循环下一个仓库
					// 这里因为一次处理的数量为1，所以不进行多仓库轮流递减。
					int amount = Integer.parseInt(vo.getAmount());
					if (amount > 0) {
						amount = -amount;
					}
					amount = Integer.parseInt(voTemp.getStockAmount()) + amount;
					if (amount >= 0) {
						stockDao.updateAmount(storageId, vo.getSalesRescId(),
								String.valueOf(amount));
						needExtra = false;
						break;
					} else {
						continue;
					}
				}
			}
		}
		if (needExtra) {
			// 无论是对仓库的新增或减少如果没有仓库信息都返回错误信息
			// 说明没有一个仓库相应物资的数量满足需扣减的数量，在此返回失败。
			// 如果要进行对仓库数量的轮流扣减，代码需要更改
			Debug.print("类SrStockBo，方法entityAmountChange中needExtra:"
					+ needExtra + "||changeType:" + changeType);
			return -1;
		}
		// 添加销售日志记录
		RcSaleLogVO voInsert = new RcSaleLogVO();
		String logId = sequenceManageDAO.getNextSequence("rc_sale_log",
				"log_id");
		voInsert.setLogId(logId);
		voInsert.setSaleTime(DAOUtils.getFormatedDate());
		voInsert.setSalesRescId(vo.getSalesRescId());
		voInsert.setRescInstance2(vo.getRescInstance2());
		if (changeType == 1) {
			voInsert.setIsOut("i");
		} else if (changeType == 2) {
			voInsert.setIsOut("o");
		}
		voInsert.setDepartId(vo.getDepartId());
		voInsert.setOperId(vo.getOperId());
		voInsert.setCustId(vo.getCustId());
		voInsert.setStyle("A");
		voInsert.setStorageId(storageId);
		if ((Integer.parseInt(vo.getAmount()) >= 0) && changeType == 2) {
			voInsert.setStockAmount("-" + vo.getAmount());
		} else {
			voInsert.setStockAmount(vo.getAmount());
		}
		saleLogDao.insert(voInsert);
		Debug.print("类SrStockBo，方法entityAmountChange,添加销售日志voInsert:"
				+ voInsert);
		return 0;
	}

	/**
	 * 构造销售资源和撤单资源的错误提示信息 looks strange,but be orderd to do it.
	 * 
	 * @param saleList
	 * @param releseList
	 * @return
	 */
	public String getSaleReleErrInfo(List saleList, List releseList) {
		StringBuffer buffer1 = new StringBuffer("");
		String mess1 = "";
		StringBuffer buffer2 = new StringBuffer("");
		String mess2 = "";
		SalesRescDAO dao = SrDAOFactory.getInstance().getSalesRescDAO();
		String cond = " sales_resource_id=";
		// 处理销售物资信息
		if (saleList != null && saleList.size() > 0) {
			for (int i = 0; i < saleList.size(); i++) {
				EntityResourceVO voTemp = (EntityResourceVO) saleList.get(i);
				if (voTemp != null) {
					if (voTemp.getRescInstance2() != null
							&& voTemp.getRescInstance2().trim().length() > 0) {
						buffer1
								.append("资源实例" + voTemp.getRescInstance2()
										+ ",");
					} else if (voTemp.getSalesRescId() != null
							&& voTemp.getSalesRescId().trim().length() > 0) {
						List listTemp = dao.findByCond(cond
								+ voTemp.getSalesRescId());
						if (listTemp != null && listTemp.size() > 0) {
							buffer1.append(((SalesRescVO) listTemp.get(0))
									.getSalesRescName()
									+ ",");
						}
					}
				}
			}
			if (buffer1.toString() != null && buffer1.toString().length() > 0) {
				mess1 = buffer1.toString() + "销售失败！" + "\n";
			}
		}
		// 处理取消单物资信息
		if (releseList != null && releseList.size() > 0) {
			for (int i = 0; i < releseList.size(); i++) {
				EntityResourceVO voTemp2 = (EntityResourceVO) releseList.get(i);
				if (voTemp2 != null) {
					if (voTemp2.getRescInstance2() != null
							&& voTemp2.getRescInstance2().trim().length() > 0) {
						buffer2.append("资源实例" + voTemp2.getRescInstance2()
								+ ",");
					} else if (voTemp2.getSalesRescId() != null
							&& voTemp2.getSalesRescId().trim().length() > 0) {
						List listTemp2 = dao.findByCond(cond
								+ voTemp2.getSalesRescId());
						if (listTemp2 != null && listTemp2.size() > 0) {
							buffer2.append(((SalesRescVO) listTemp2.get(0))
									.getSalesRescName()
									+ ",");
						}
					}
				}
			}
			if (buffer2.toString() != null && buffer2.toString().length() > 0) {
				mess2 = buffer2.toString() + "撤单失败！";
			}
		}
		return mess1 + mess2;
	}

	/**
	 * 根据传入的需处理的资源，得到这些营销资源对应的单一价格记录信息
	 * 
	 * @param list
	 * @return
	 */
	public Map getSalesRescPriceInfo(List list) {
		if (list == null || list.size() < 1) {
			return new HashMap();
		}
		Map map = new HashMap();
		String rescLevel = "-1";
		SaleRescPricVO priceVO = null;
		SaleRescPricDAO dao = SrDAOFactory.getInstance().getSaleRescPricDAO();
		RcEntityDAO entityDao = SrDAOFactory.getInstance().getRcEntityDAO();
		RcEntityVO entityVO = null;
		for (int i = 0; i < list.size(); i++) {
			EntityResourceVO voTemp = (EntityResourceVO) list.get(i);
			if (voTemp != null) {
				if (voTemp.getSalesRescId() != null
						&& voTemp.getSalesRescId().length() > 0) {
					if (voTemp.getBusinessId() == null
							|| voTemp.getBusinessId().length() < 1) {
						map.put(voTemp.getSalesRescId(), "0");
					} else {
						priceVO = dao.findByPrimaryKey(voTemp.getBusinessId(),
								rescLevel, voTemp.getSalesRescId());
						if (priceVO != null && priceVO.getPrice() != null
								&& priceVO.getPrice().length() > 0) {
							map
									.put(voTemp.getSalesRescId(), priceVO
											.getPrice());
						} else {
							map.put(voTemp.getSalesRescId(), "0");
						}
					}
				} else if (voTemp.getRescInstance2() != null
						&& voTemp.getRescInstance2().trim().length() > 0) {
					// 根据rescInstance2查询所属营销资源
					entityVO = entityDao.findByEntityCode(voTemp
							.getRescInstance2());
					if (entityVO != null && entityVO.getSalesRescId() != null
							&& entityVO.getSalesRescId().length() > 0) {
						// 处理流程如上，只是营销资源id不同
						if (voTemp.getBusinessId() == null
								|| voTemp.getBusinessId().length() < 1) {
							map.put(entityVO.getSalesRescId(), "0");
						} else {
							priceVO = dao.findByPrimaryKey(voTemp
									.getBusinessId(), rescLevel, entityVO
									.getSalesRescId());
							if (priceVO != null && priceVO.getPrice() != null
									&& priceVO.getPrice().length() > 0) {
								map.put(entityVO.getSalesRescId(), priceVO
										.getPrice());
							} else {
								map.put(entityVO.getSalesRescId(), "0");
							}
						}
					}
				}
			}
		}
		return map;
	}

	// ////////////////////////////////////////////统计库存的代码///////////////////////////////////////

	/**
	 * 查询销售日志
	 * 
	 * @param map
	 *            ,包含的参数有：departIds，salesRescIds，rescInstance2，beginDate，endDate
	 * @param pi
	 * @param ps
	 * @return
	 */
	public PageModel qryRcSaleLog(Map map, int pi, int ps) {
		String lanId = null;
		String operId = null;
		String familyId = null;
		String custName = null;
		String storageId = null;
		String salesRescIds = null;
		String rescInstance2 = null;
		String beginDate = null;
		String endDate = null;
		String prodNo = null;
		String isOut = null;
		String tableName = null;
		if (map != null) {
			if (map.get("lanId") != null)
				lanId = (String) map.get("lanId");
			if (map.get("operId") != null)
				operId = (String) map.get("operId");
			if (map.get("familyId") != null)
				familyId = (String) map.get("familyId");
			if (map.get("custName") != null)
				custName = (String) map.get("custName");
			if (map.get("storageId") != null)
				storageId = (String) map.get("storageId");
			if (map.get("salesRescIds") != null)
				salesRescIds = (String) map.get("salesRescIds");
			if (map.get("rescInstance2") != null)
				rescInstance2 = (String) map.get("rescInstance2");
			if (map.get("beginDate") != null)
				beginDate = (String) map.get("beginDate");
			if (map.get("endDate") != null)
				endDate = (String) map.get("endDate");
			if (map.get("prodNo") != null) {
				prodNo = (String) map.get("prodNo");
				prodNo = DAOUtils.filterQureyCond(prodNo);
			}
			if (map.get("isOut") != null)
				isOut = (String) map.get("isOut");
		}
		int flag = -1;
		PageModel pm = new PageModel();
		String entitySelectStr = "";
		String joinStr = "";
		String joinCondStr = "";
		String instanceIdStr = "a.resource_instance_id";
		if (salesRescIds != null && salesRescIds.trim().length() > 0) {
			tableName = this.getEntityTableName(salesRescIds);
			if (tableName != null && tableName.trim().length() > 0) {
				if (tableName.equalsIgnoreCase("rc_entity")) {
					entitySelectStr = ",f.create_date,f.curr_state as RESOURCE_STATE,f.state ";
					joinCondStr = " and a.SALES_RESOURCE_ID=f.SALES_RESOURCE_ID and a.RESOURCE_INSTANCE2=f.RESOURCE_INSTANCE_CODE ";
				} else if (tableName.equalsIgnoreCase("rc_entity2")) {
					entitySelectStr = ",f.create_date,f.curr_state as RESOURCE_STATE,f.state ";
					joinCondStr = " and a.SALES_RESOURCE_ID=f.SALES_RESOURCE_ID and a.RESOURCE_INSTANCE2=f.RESOURCE_INSTANCE_CODE ";
				} else if (tableName.equalsIgnoreCase("rc_sim")) {
					entitySelectStr = ",f.init_time as create_date,f.RESOURCE_STATE,f.state ";
					joinCondStr = " and a.SALES_RESOURCE_ID=f.SALES_RESOURCE_ID and a.RESOURCE_INSTANCE2=f.RESOURCE_INSTANCE_CODE ";
				} else if (tableName.equalsIgnoreCase("rc_no")) {
					entitySelectStr = ",f.init_time as create_date,f.RESOURCE_STATE,f.state ";
					joinCondStr = " and a.SALES_RESOURCE_ID=f.SALES_RESOURCE_ID and a.RESOURCE_INSTANCE2=f.RESOURCE_INSTANCE_CODE ";
				}
				flag = 2;
				instanceIdStr = "f.resource_instance_id";
				joinStr = "," + tableName + " f ";

			} else
				tableName = null;
		}
		if (custName != null && custName.trim().length() > 0) {
			instanceIdStr += ",g.CUST_NAME";
			joinStr += ",cust g ";
			joinCondStr += " and a.cust_id = g.cust_id and g.CUST_NAME like '%"
					+ DAOUtils.filterQureyCond(custName) + "%' ";
			if (lanId != null && lanId.trim().length() > 0)
				joinCondStr += " and a.lan_id=" + lanId;
		} else {
			instanceIdStr += ",a.cust_name";
		}
		String sql = "SELECT a.log_id,"
				+ instanceIdStr
				+ ",a.sales_resource_id,a.lan_id,a.sale_time,a.resource_instance2,a.depart_id,a.oper_id,a.price,a.style,a.is_out,a.before_state,a.after_state,a.storage_id,a.stock_amount,a.product_no,a.cust_id,a.imei,a.deal_type,a.deal_info,a.produce_no,"
				+ " a.biz_type,b.org_name,c.family_id,c.manage_mode,c.sales_resource_name,d.storage_name,e.party_role_name "
				+ entitySelectStr
				+ " FROM RC_SALE_LOG a,organization b,SALES_RESOURCE c,rc_storage d,PARTY_ROLE e "
				+ joinStr
				+ " where a.biz_type='SAL' and a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id and a.oper_id=e.party_role_id "
				+ joinCondStr;

		String sql_count = "SELECT COUNT(*) AS COL_COUNTS "
				+ " FROM RC_SALE_LOG a,organization b,SALES_RESOURCE c,rc_storage d,PARTY_ROLE e "
				+ joinStr
				+ " where a.biz_type='SAL' and a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id and a.oper_id=e.party_role_id "
				+ joinCondStr;

		StringBuffer cond = new StringBuffer();
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		if (lanId != null && lanId.trim().length() > 0)
			cond.append(" and a.LAN_ID = ").append(lanId).append(" ");
		if (operId != null && operId.trim().length() > 0)
			cond.append(" and a.oper_id = ").append(operId).append(" ");
		if (storageId != null && storageId.trim().length() > 0)
			cond.append(" and a.storage_id in (").append(storageId)
					.append(") ");
		if (salesRescIds != null && salesRescIds.trim().length() > 0)
			cond.append(" and a.sales_resource_id = ").append(salesRescIds)
					.append(" ");
		else if (familyId != null && familyId.trim().length() > 0)
			cond.append(" and c.FAMILY_ID = ").append(familyId).append(" ");
		if (rescInstance2 != null && rescInstance2.trim().length() > 0)
			cond.append(" and a.resource_instance2 like '%").append(
					DAOUtils.filterQureyCond(rescInstance2)).append("%' ");
		if (beginDate != null && beginDate.trim().length() > 0) {
			if ("INFORMIX".equals(databaseType)) {
				cond.append(" and a.sale_time>=to_date('" + beginDate
						+ "','%Y-%m-%d')");
			} else {
				cond.append(" and a.sale_time>=to_date('" + beginDate
						+ "','yyyy-mm-dd')");
			}
		}
		if (endDate != null && endDate.trim().length() > 0) {
			if ("INFORMIX".equals(databaseType)) {
				cond.append(" and a.sale_time<=to_date('" + endDate
						+ "','%Y-%m-%d')+interval(1) day to day");
			} else {
				cond.append(" and a.sale_time<=to_date('" + endDate
						+ "','yyyy-mm-dd')+1");
			}
		}
		if (prodNo != null && prodNo.trim().length() > 0)
			cond.append(" and a.product_no = ").append(prodNo).append(" ");
		if (isOut != null && (("i".equals(isOut)) || "o".equals(isOut)))
			cond.append(" and a.is_out = '").append(isOut).append("' ");

		sql += cond;
		sql_count += cond;

		RcSaleLogDAO dao = SrDAOFactory.getInstance().getRcSaleLogDAO();

		dao.setSQL_SELECT(sql);
		dao.setSQL_SELECT_COUNT(sql_count);
		if (flag > 0)
			dao.setFlag(flag);
		pm = PageHelper.popupPageModel(dao, "", pi, ps);
		return pm;
	}

	/**
	 * 统计终端销售情况
	 * 
	 * @param map
	 *            ,包含的参数有：departIds，salesRescIds，beginDate，endDate
	 * @param pi
	 * @param ps
	 * @return
	 */
	public PageModel satEntitySaleLog(Map map, int pi, int ps) {
		String lanIds = null;
		String salesRescIds = null;
		String beginDate = null;
		String endDate = null;
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		if (map != null) {
			if (map.get("lanIds") != null) {
				lanIds = (String) map.get("lanIds");
			}
			if (map.get("salesRescIds") != null) {
				salesRescIds = (String) map.get("salesRescIds");
			}
			if (map.get("beginDate") != null) {
				beginDate = (String) map.get("beginDate");
			}
			if (map.get("endDate") != null) {
				endDate = (String) map.get("endDate");
			}
		}

		StringBuffer cond = new StringBuffer(" and biz_type='SAL' ");
		if (lanIds != null && lanIds.trim().length() > 0) {
			if (lanIds.indexOf(",") > 0) {
				cond.append(" and a.lan_id in (").append(lanIds).append(") ");
			} else {
				cond.append(" and a.lan_id =").append(lanIds);
			}
		}
		if (salesRescIds != null && salesRescIds.trim().length() > 0) {
			if (salesRescIds.indexOf(",") > 0) {
				cond.append(" and a.sales_resource_id in (").append(
						salesRescIds).append(") ");
			} else {
				cond.append(" and a.sales_resource_id =").append(salesRescIds);
			}
		}
		if (beginDate != null && beginDate.trim().length() > 0) {
			if ("INFORMIX".equals(databaseType)) {
				cond.append(" and a.sale_time>=to_date('" + beginDate
						+ "','%Y-%m-%d')");
			} else {
				cond.append(" and a.sale_time>=to_date('" + beginDate
						+ "','yyyy-mm-dd')");
			}
		}
		if (endDate != null && endDate.trim().length() > 0) {
			if ("INFORMIX".equals(databaseType)) {
				cond.append(" and a.sale_time<=to_date('" + endDate
						+ "','%Y-%m-%d')+interval(1) day to day");
			} else {
				cond.append(" and a.sale_time<=to_date('" + endDate
						+ "','yyyy-mm-dd')+1");
			}
		}

		String sqlBasic_oracle = "select b.manufacturer,b.provider,b.shopkeeper,c.sales_resource_name,b.curr_state,d.lan_name,e.depart_name ,a.sale_time,a.product_no,a.resource_instance2 "
				+ " from rc_sale_log a, rc_entity b, SALES_RESOURCE c,rr_lan d, mp_department e "
				+ " where a.depart_id=e.depart_id and a.sales_resource_id=c.sales_resource_id and"
				+ " a.resource_instance_id=b.resource_instance_id and a.lan_id = d.lan_id "
				+ cond.toString()
				+ " order by b.provider,b.shopkeeper,a.sales_resource_id ";

		String count_oracle = "select COUNT(a.resource_instance2) AS COL_COUNTS "
				+ " from rc_sale_log a, rc_entity b, SALES_RESOURCE c,rr_lan d, mp_department e "
				+ " where a.depart_id=e.depart_id and a.sales_resource_id=c.sales_resource_id and"
				+ " a.resource_instance_id=b.resource_instance_id and a.lan_id = d.lan_id "
				+ cond.toString();

		PageModel pm = new PageModel();

		RcSaleLogDAO dao = SrDAOFactory.getInstance().getRcSaleLogDAO();
		// 设置要查询语句的主查询语句
		// System.out.println(sqlBasic_oracle);
		dao.setSQL_SELECT(sqlBasic_oracle);
		dao.setSQL_SELECT_COUNT(count_oracle);
		dao.setFlag(5);
		pm = PageHelper.popupPageModel(dao, "", pi, ps);

		return pm;
	}

	/**
	 * 统计商品销售情况
	 * 
	 * @param map
	 *            ,包含的参数有：departIds，salesRescIds，beginDate，endDate
	 * @param pi
	 * @param ps
	 * @return
	 */
	public PageModel satSaleLog(Map map, int pi, int ps) {
		String departIds = null;
		String salesRescIds = null;
		String beginDate = null;
		String endDate = null;
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		if (map != null) {
			if (map.get("departIds") != null) {
				departIds = (String) map.get("departIds");
			}
			if (map.get("salesRescIds") != null) {
				salesRescIds = (String) map.get("salesRescIds");
			}
			if (map.get("beginDate") != null) {
				beginDate = (String) map.get("beginDate");
			}
			if (map.get("endDate") != null) {
				endDate = (String) map.get("endDate");
			}
		}

		StringBuffer cond = new StringBuffer();
		if (departIds != null && departIds.trim().length() > 0) {
			cond.append(" and depart_id in (").append(departIds).append(") ");
		}
		if (salesRescIds != null && salesRescIds.trim().length() > 0) {
			cond.append(" and sales_resource_id in (").append(salesRescIds)
					.append(") ");
		}
		if (beginDate != null && beginDate.trim().length() > 0) {
			if ("INFORMIX".equals(databaseType)) {
				cond.append(" and sale_time>=to_date('" + beginDate
						+ "','%Y-%m-%d')");
			} else {
				cond.append(" and sale_time>=to_date('" + beginDate
						+ "','yyyy-mm-dd')");
			}
		}
		if (endDate != null && endDate.trim().length() > 0) {
			if ("INFORMIX".equals(databaseType)) {
				cond.append(" and sale_time<=to_date('" + endDate
						+ "','%Y-%m-%d')+interval(1) day to day");
			} else {
				cond.append(" and sale_time<=to_date('" + endDate
						+ "','yyyy-mm-dd')+1");
			}
		}
		String condStr = cond.toString();
		if (condStr != null && condStr.length() > 0) {
			condStr = " where biz_type='SAL' and "
					+ condStr.substring(condStr.indexOf("and") + 3);
		} else {
			condStr = " where biz_type='SAL' ";

		}
		String sqlBasic_informix = "select a.depart_id,a.storage_id,a.sales_resource_id,a.stock_amount,b.org_name,c.sales_resource_name,d.storage_name "
				+ " from table(MULTISET(select depart_id,storage_id,sales_resource_id,-sum(stock_amount) as stock_amount from "
				+ DAOSQLUtils.getTableName("rc_sale_log")
				+ condStr
				+ " group by depart_id,storage_id,sales_resource_id)) a, "
				+ DAOSQLUtils.getTableName("organization")
				+ " b,"
				+ DAOSQLUtils.getTableName("SALES_RESOURCE")
				+ " c,"
				+ DAOSQLUtils.getTableName("rc_storage")
				+ " d "
				+ " where a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id and d.rc_type=-1 ";

		String sqlBasic_oracle = "select a.depart_id,a.storage_id,a.sales_resource_id,a.stock_amount,b.org_name,c.sales_resource_name,d.storage_name "
				+ " from (select depart_id,storage_id,sales_resource_id,-sum(stock_amount) as stock_amount from "
				+ DAOSQLUtils.getTableName("rc_sale_log")
				+ condStr
				+ " group by depart_id,storage_id,sales_resource_id) a, "
				+ DAOSQLUtils.getTableName("organization")
				+ " b,"
				+ DAOSQLUtils.getTableName("SALES_RESOURCE")
				+ " c,"
				+ DAOSQLUtils.getTableName("rc_storage")
				+ " d "
				+ " where a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id and d.rc_type=-1 ";

		String count_informix = "select COUNT(*) AS COL_COUNTS "
				+ " from table(MULTISET(select depart_id,storage_id,sales_resource_id,sum(stock_amount) as stock_amount from "
				+ DAOSQLUtils.getTableName("rc_sale_log")
				+ condStr
				+ " group by depart_id,storage_id,sales_resource_id)) a, "
				+ DAOSQLUtils.getTableName("organization")
				+ " b,"
				+ DAOSQLUtils.getTableName("SALES_RESOURCE")
				+ " c,"
				+ DAOSQLUtils.getTableName("rc_storage")
				+ " d "
				+ " where a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id and d.rc_type=-1 ";

		String count_oracle = "select COUNT(*) AS COL_COUNTS "
				+ " from (select depart_id,storage_id,sales_resource_id,sum(stock_amount) as stock_amount from "
				+ DAOSQLUtils.getTableName("rc_sale_log")
				+ condStr
				+ " group by depart_id,storage_id,sales_resource_id) a, "
				+ DAOSQLUtils.getTableName("organization")
				+ " b,"
				+ DAOSQLUtils.getTableName("SALES_RESOURCE")
				+ " c,"
				+ DAOSQLUtils.getTableName("rc_storage")
				+ " d "
				+ " where a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id and d.rc_type=-1 ";

		String sqlBasic = sqlBasic_informix;
		String count_sql = count_informix;
		if (!"INFORMIX".equals(databaseType)) {
			sqlBasic = sqlBasic_oracle;
			count_sql = count_oracle;
		}

		PageModel pm = new PageModel();

		RcSaleLogDAO dao = SrDAOFactory.getInstance().getRcSaleLogDAO();
		// 设置要查询语句的主查询语句
		dao.setSQL_SELECT(sqlBasic);
		dao.setSQL_SELECT_COUNT(count_sql);
		dao.setFlag(1);
		pm = PageHelper.popupPageModel(dao, "", pi, ps);

		return pm;
	}

	// 测试
	public static void main(String[] args) throws Exception {
		SrStockBo bo = new SrStockBo();
		List list = new ArrayList();
		EntityResourceVO vo1 = new EntityResourceVO("305", "", "4001", "43",
				"0001", "", "1");
		EntityResourceVO vo2 = new EntityResourceVO("220", "", "4001", "43",
				"0001", "", "1");
		list.add(vo1);
		list.add(vo2);

		Map map = bo.entitySale(list);

		if (map != null) {
			System.out.println("resultFlag:" + (String) map.get("resultFlag"));
			System.out.println("continueFlag:"
					+ (String) map.get("continueFlag"));
			System.out.println("failInfo:" + (String) map.get("failInfo"));
		}

	}

	/**
	 * 根据营销资源id查询其所属的家族的实体属性集
	 * 
	 * @param salesRescId
	 *            String
	 * @return List
	 */
	public List qryEntityAttrInfoBySalesResc(String salesRescId) {
		List list = null;
		if (salesRescId == null || salesRescId.trim().length() < 1) {
			return list;
		}
		SalesRescDAO dao = SrDAOFactory.getInstance().getSalesRescDAO();
		SalesRescVO vo = dao.findByPrimaryKey(salesRescId);
		if (vo != null && vo.getFamilyId() != null) {
			RcFamilyDAO familyDao = SrDAOFactory.getInstance().getRcFamilyDAO();
			list = familyDao.findAttrInfo(vo.getSalesRescId(), "1");
		}
		return list;
	}

	/**
	 * 根据订单类型判断这种类型的订单在入库前的环节是否是出库环节。 对于没有入库步骤的类型认为前一步是出库
	 * 
	 * @param appType
	 *            String
	 * @return boolean true:是出库环节；false:不是出库环节
	 */
	public boolean isOutBeforeInStorage(String appType) {
		if (appType == null || appType.trim().length() < 1) {
			return false;
		}
		boolean result = false;
		RcOrderOperStateDAO dao = SrDAOFactory.getInstance()
				.getRcOrderOperStateDAO();
		String cond = " app_type='" + appType + "' and t_tache_id= "
				+ RcOrderOperStateVO.Tache_inStorage;
		List list = dao.findByCond(cond);
		if (list != null && list.size() > 0) {
			RcOrderOperStateVO voTemp = null;
			for (int i = 0; i < list.size(); i++) {
				voTemp = (RcOrderOperStateVO) list.get(i);
				if (voTemp != null
						&& voTemp.getFTacheId() != null
						&& RcOrderOperStateVO.Tache_outStorage.equals(voTemp
								.getFTacheId())) {
					result = true;
					break;
				}
			}
		} else {
			// 如果没有查询出关于入库的纪录，说明订单是报废或退货，因此设为true
			result = true;
		}
		return result;
	}

	/**
	 * 检查的逻辑内容包括: 1. 如果是出库操作，不做检查直接通过，所有的检查放在入库进行 2.
	 * 如果入库前的步骤不是出库,则验证在起始编码和终止编码之间是否已经存在实例，如果存在，则错误 3.
	 * 如果入库前是出库说明实例已经存在,验证起始和终止编码之间的已存在的实体数量是否和要求数量一致，如果一致还要验证是否有不可用状态的实例
	 * 
	 * @param vo
	 *            RcOrderVO
	 *            Map:errorCode:-1:缺少参数，失败；1:如果是没有出库的入库订单时，这些实例在仓库中已经存在；
	 *            2:库中实例数量和指定的要出入库的数量不同;3:指定出入库的实例中有无效的实例 Map:info:错误信息
	 */
	public Map checkEntityInStorage(RcOrderVO vo) {
		Map map = new HashMap();
		if (vo == null || vo.getOrderId() == null
				|| vo.getOrderId().trim().length() < 1
				|| vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1
				|| vo.getStateId() == null
				|| vo.getStateId().trim().length() < 1
				|| vo.getTacheId() == null
				|| vo.getTacheId().trim().length() < 1
				|| vo.getActAmount() == null
				|| vo.getActAmount().trim().length() < 1) {
			map.put("result", "0");
			map.put("errorCode", "-1");
			map.put("info", "缺少验证参数错误!");
			return map;
		}
		String result = "1";
		String info = "";
		String actAmount = vo.getActAmount();
		// 如果是回退则不作验证
		if ("b".equals(vo.getStateId())) {
			map.put("result", "1");
			map.put("errorCode", "");
			map.put("info", "回退时，不作验证");
			return map;
		}
		// 判断是否是通过文件上传形式传实例编码的，分两种处理方式
		RcOrderListDAO listDao = SrDAOFactory.getInstance().getRcOrderListDAO();
		List list = listDao.findByCond(" ORDER_ID=" + vo.getOrderId());

		// 如果是出库操作则检查是否有指定一种输入实例的方式
		if (RcOrderOperStateVO.Tache_outStorage.equals(vo.getTacheId())) {
			// String tTacheId = this.qryNextTache(vo.getOrderId(), vo
			// .getAppType(), vo.getTacheId(), vo.getStateId());
			if (list == null || list.size() < 1) {
				// String resBCode = vo.getResBCode();
				// String resECode = vo.getResECode();
				// if (resBCode == null || resBCode.trim().length() < 1
				// || resECode == null || resECode.trim().length() < 1
				// || actAmount == null || actAmount.trim().length() < 1) {
				// result = "0";
				// info = "请在上传出库清单和输入起始终止编码两种方式中选择一种输入实例的方式!";
				// } else {
				// BigDecimal actAmountNum = new BigDecimal(actAmount);
				// BigDecimal resBCodeNum = new BigDecimal(resBCode);
				// BigDecimal resECodeNum = new BigDecimal(resECode);
				// BigDecimal subNum = (resECodeNum.subtract(resBCodeNum))
				// .add(new BigDecimal("1"));
				// if (actAmountNum.compareTo(subNum) != 0) {
				// result = "0";
				// info = "起始编码和终止编码之间的实例数量和输入的实际数量不相等!";
				// }
				// }
			} else {
				if (list.size() != Integer.parseInt(actAmount)) {
					result = "0";
					info = "文件清单的实例数量和输入的实际数量不相等!";
				} else {
					result = "1";
					info = "将通过出库清单列表来处理该订单的实例!";
				}
			}
			if ("0".equals(result)) { // 如果出库阶段验证失败，则返回
				map.put("result", result);
				map.put("errorCode", "");
				map.put("info", info);
				return map;
			}
			// 检查数量状态，出库检验数量
			if (list != null && list.size() > 0) {
				map = this.checkEntityInStorage_importFile(vo,
						ParamsConsConfig.RcEntityStateValide);
			} else {
				// map = this.checkEntityInStorage_serialCode(vo,
				// ParamsConsConfig.RcEntityStateValide);
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		} else if (RcOrderOperStateVO.Tache_inStorage.equals(vo.getTacheId())) { // 如果现在环节是入库
			String state = ParamsConsConfig.RcEntityStateValide;
			// 查询前一步是否是出库
			boolean isOut = this.isOutBeforeInStorage(vo.getAppType());
			if (isOut)
				state = ParamsConsConfig.RcEntityStateInValide; // 如果前一个环节是出库环节，则检查这些实例是否是在途状态
			// 检查数量状态，出库入库都需要检验数量
			if (list != null && list.size() > 0) {
				map = this.checkEntityInStorage_importFile(vo, state);
			} else {
				map = this.checkEntityInStorage_serialCode(vo, state);
			}
		}
		return map;
	}

	/**
	 * 检查根据文件上传的实例编码检查仓库中的实例是否满足条件
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return Map:result:0失败；1成功
	 *         Map:errorCode:-1:缺少参数，失败；1:如果是没有出库的入库订单时，这些实例在仓库中已经存在；
	 *         2:库中实例数量和指定的要出入库的数量不同;3:指定出入库的实例中有无效的实例 Map:info:错误信息
	 */
	public Map checkEntityInStorage_importFile(RcOrderVO ordervo, String state) {
		Map map = new HashMap();
		String appType = null;
		String tacheId = null;
		String actAmount = null;
		String outStorageId = null;
		String salesRescId = null;
		String orderId = null;
		String stateId = null;
		String stateTip = "在库";
		if (ordervo != null) {
			appType = ordervo.getAppType();
			tacheId = ordervo.getTacheId();
			actAmount = ordervo.getActAmount();
			outStorageId = ordervo.getOutStorageId();
			orderId = ordervo.getOrderId();
			stateId = ordervo.getStateId();
			salesRescId = ordervo.getSalesRescId();
		}
		if (ordervo == null
				|| appType == null
				|| appType.trim().length() < 1
				|| tacheId == null
				|| tacheId.trim().length() < 1
				|| (!RcOrderOperStateVO.Tache_outStorage.equals(tacheId) && !RcOrderOperStateVO.Tache_inStorage
						.equals(tacheId)) || actAmount == null
				|| actAmount.trim().length() < 1 || orderId == null
				|| orderId.trim().length() < 1 || state == null
				|| state.trim().length() < 1 || stateId == null
				|| stateId.trim().length() < 1) {
			map.put("result", "0");
			map.put("errorCode", "-1");
			map.put("info", "需要验证的参数错误!");
			return map;
		}
		if (ParamsConsConfig.RcEntityStateInValide.equals(state)) {
			stateTip = "在途";
		}
		// 设置应该操作的表(实例、号码、SIM)
		String tableName = this.setEntityTableName(ordervo);
		// 根据申请类型查询出库前的步骤是不是入库
		boolean isOut = true;
		if (!"3".equals(tacheId)) {// 入库
			isOut = this.isOutBeforeInStorage(appType);
		}
		if (isOut) {
			if (outStorageId == null || outStorageId.trim().length() < 1) {
				map.put("result", "0");
				map.put("errorCode", "-1");
				map.put("info", "缺少流出仓库参数错误!");
				return map;
			}
		}
		// 查询起始编码和终止编码之间的实例个数
		long actAmountNum = Long.parseLong(actAmount);
		String sqlNum = "select count(*) as COL_COUNTS　from " + tableName
				+ " where sales_resource_id=" + salesRescId + " and state='"
				+ state + "'";
		if (isOut) {
			sqlNum += " and storage_id=" + outStorageId; // 如果是前一步是出库，则需要加上流出仓库的判断条件
		}
		String cond = " and exists(select * from rc_order_list where ORDER_ID="
				+ orderId
				+ " and "
				+ tableName
				+ ".sales_resource_id=rc_order_list.sales_resource_id and "
				+ tableName
				+ ".resource_instance_code=rc_order_list.RESOURCE_INSTANCE_CODE)";
		sqlNum += cond;
		SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();

		long entitynum = comDao.count(sqlNum);
		if (!isOut) { // 如果入库前的步骤不是出库,则验证上传文件列表中的实例编码是否已经存在
			if (entitynum > 0) {
				map.put("result", "0");
				map.put("errorCode", "1");
				map.put("info", "上传文件列表中的资源实例在相应的仓库中已经存在,入库失败!");
			} else {
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		} else { // 如果入库前是出库说明实例已经存在,验证上传文件编码的已存在的实体数量是否和要求数量一致，如果一致还要验证是否有不可用状态的实例
			if (entitynum != actAmountNum) {
				map.put("result", "0");
				map.put("errorCode", "2");
				if (entitynum < actAmountNum) {
					map.put("info", "指定仓库中" + stateTip + "状态的实例数量不足，出入库失败!");
				} else {
					map.put("info", "仓库中指定要处理的" + stateTip
							+ "实例数量大于实际要处理的数量，出入库失败!");
				}
			} else {
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		}
		return map;
	}

	/**
	 * 检查的逻辑内容包括: 1. 如果是出库操作，并且下一步不是结束，则不做检查直接通过；所有的检查放在入库进行 2.
	 * 如果入库前的步骤不是出库,则验证在起始编码和终止编码之间是否已经存在实例，如果存在，则错误 3.
	 * 如果入库前是出库说明实例已经存在,验证起始和终止编码之间的已存在的实体数量是否和要求数量一致，如果一致还要验证是否有不可用状态的实例
	 * 
	 * @param ordervo
	 *            RcOrderVO
	 * @return Map:result:0失败；1成功
	 *         Map:errorCode:-1:缺少参数，失败；1:如果是没有出库的入库订单时，这些实例在仓库中已经存在；
	 *         2:库中实例数量和指定的要出入库的数量不同;3:指定出入库的实例中有无效的实例 Map:info:错误信息
	 */
	public Map checkEntityInStorage_serialCode(RcOrderVO ordervo, String state) {
		Map map = new HashMap();
		String appType = null;
		String tacheId = null;
		String resBCode = null;
		String resECode = null;
		String actAmount = null;
		String outStorageId = null;
		String orderId = null;
		String stateId = null;
		String salesRescId = null;
		String stateTip = "在库";
		if (ordervo != null) {
			appType = ordervo.getAppType();
			tacheId = ordervo.getTacheId();
			resBCode = ordervo.getResBCode();
			resECode = ordervo.getResECode();
			actAmount = ordervo.getActAmount();
			outStorageId = ordervo.getOutStorageId();
			orderId = ordervo.getOrderId();
			stateId = ordervo.getStateId();
			salesRescId = ordervo.getSalesRescId();
		}
		if (ordervo == null
				|| appType == null
				|| appType.trim().length() < 1
				|| tacheId == null
				|| tacheId.trim().length() < 1
				|| (!RcOrderOperStateVO.Tache_outStorage.equals(tacheId) && !RcOrderOperStateVO.Tache_inStorage
						.equals(tacheId)) || actAmount == null
				|| actAmount.trim().length() < 1 || orderId == null
				|| orderId.trim().length() < 1 || stateId == null
				|| stateId.trim().length() < 1 || salesRescId == null
				|| salesRescId.trim().length() < 1 || state == null
				|| state.trim().length() < 1) {
			map.put("result", "0");
			map.put("errorCode", "-1");
			map.put("info", "需要验证的参数错误,请确认填写了起始和终止编码!");
			return map;
		}
		if (ParamsConsConfig.RcEntityStateInValide.equals(state)) {
			stateTip = "在途";
		}
		// 设置应该操作的表(实例、号码、SIM)
		String tableName = this.setEntityTableName(ordervo);
		// 根据申请类型查询出库前的步骤是不是入库
		SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
		boolean isOut = true;
		if (!"3".equals(tacheId)) {
			isOut = this.isOutBeforeInStorage(appType);
		}
		if (isOut) {
			if (outStorageId == null || outStorageId.trim().length() < 1) {
				map.put("result", "0");
				map.put("errorCode", "-1");
				map.put("info", "缺少流出仓库参数错误!");
				return map;
			}
		}
		// 查询起始编码和终止编码之间的实例个数
		long actAmountNum = Long.parseLong(actAmount);
		String sqlNum = "select count(*) as COL_COUNTS　from " + tableName
				+ " where sales_resource_id=" + salesRescId + " and state='"
				+ state + "'";
		if (isOut) {
			sqlNum += " and storage_id=" + outStorageId; // 如果是前一步是出库，则需要加上流出仓库的判断条件
		}
		if (resBCode != null && !"".equals(resBCode)) {
			sqlNum += " and resource_instance_code>="
					+ DAOUtils.filterQureyCond(resBCode);
			// "' and length(resource_instance_code)>=length('" +
			// DAOUtils.filterQureyCond(resBCode) + "')";
		}
		if (resECode != null && !"".equals(resECode)) {
			sqlNum += " and resource_instance_code<="
					+ DAOUtils.filterQureyCond(resECode);
			// "' and length(resource_instance_code)<=length('" +
			// DAOUtils.filterQureyCond(resECode) + "')";
		}
		long entitynum = comDao.count(sqlNum);
		if (!isOut) { // 如果入库前的步骤不是出库,则验证在起始编码和终止编码之间是否已经存在实例
			if (entitynum > 0) {
				map.put("result", "0");
				map.put("errorCode", "1");
				map.put("info", "库中已经存在起始编码和终止编码之间的实例,入库失败!");
			} else {
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		} else { // 如果入库前是出库说明实例已经存在,验证起始和终止编码之间的已存在的实体数量是否和要求数量一致，如果一致还要验证是否有不可用状态的实例
			if (entitynum != actAmountNum) {
				map.put("result", "0");
				map.put("errorCode", "2");
				if (entitynum < actAmountNum) {
					map.put("info", "指定仓库中起始和终止编码之间的" + stateTip
							+ "实例数量不足，出入库失败!");
				} else {
					map.put("info", "指定仓库中起始和终止编码之间的" + stateTip
							+ "实例数量比指定的实际数量多，出入库失败!");
				}
			} else {
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		}
		return map;
	}

	/**
	 * 资源实例出库直接报废或退库的操作；把资源实例纪录插入到日至表中，删除资源实例
	 * 从rc_order_list表中查询所需操作的实例或根据起始终止编码确定 根据所操作的资源不同(rc_entity或号码或SIM卡)
	 * 
	 * @param resBCode
	 *            String
	 * @param resECode
	 *            String
	 * @return int
	 */
	private int outEntityToWaste(RcOrderVO vo) {
		if (vo == null || vo.getHandleType() == null
				|| vo.getOutStorageId() == null
				|| vo.getOutStorageId().trim().length() < 1
				|| vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1
				|| vo.getEntityTabName().trim().length() < 1) {
			return -1;
		}
		// String resBCode = vo.getResBCode();
		// String resECode = vo.getResECode();
		String outStorageId = vo.getOutStorageId();

	   RcEntityDAO entityDao = SrDAOFactory
				.getInstance().getRcEntityDAO();
		// com.ztesoft.crm.sr.dao2.RcEntityDAO entityDao2 = SrDAOFactory
		// .getInstance().getRcEntityDAO2();
		RcEntityOutLogDAO logDao = SrDAOFactory.getInstance()
				.getRcEntityOutLogDAO();
		SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
		String tableName = DAOUtils.filterQureyCond(vo.getEntityTabName());
		String resource_state = "RESOURCE_STATE";
		if ("rc_entity".equalsIgnoreCase(tableName)
				|| "rc_entity2".equalsIgnoreCase(tableName)) {
			resource_state = "CURR_STATE";
			// 删除实例的主sql语句(仅对除了实例外的号码或SIM卡资源)
		}
		String sql_del = " delete from " + tableName + " where ";
		int rows = 0;
		// 根据对实例资源的处理方式不同(文件上传方式、起始终止编码形式)，而分别处理
		// 插入删除实例的日至(仅对除了实例外的号码或SIM卡资源)
		StringBuffer sql_logInsert = new StringBuffer(
				"INSERT INTO RC_ENTITY_OUT_LOG (resource_instance_id,sales_resource_id,resource_instance_code,resource_level,storage_id,CURR_STATE,state,eff_date,exp_date");
		if ("rc_no".equalsIgnoreCase(tableName)
				|| "rc_sim".equalsIgnoreCase(tableName)) {
			sql_logInsert.append(" )");
			sql_logInsert
					.append(" select resource_instance_id,sales_resource_id,resource_instance_code,resource_level,storage_id,");
			sql_logInsert.append(resource_state).append(
					",state,eff_date,exp_date from ").append(tableName).append(
					" where");
		} else {
			sql_logInsert.append(",OWNER_TYPE,OWNER_ID,CREATE_DATE,lan_id )");
			sql_logInsert
					.append(" select resource_instance_id,sales_resource_id,resource_instance_code,resource_level,storage_id,");
			sql_logInsert
					.append(resource_state)
					.append(
							",state,eff_date,exp_date,OWNER_TYPE,OWNER_ID,CREATE_DATE,lan_id from ")
					.append(tableName).append(" where");
		}
		String sql_logAttrInsert = "INSERT INTO RC_ENTITY_ATTR_LOG select * from  rc_entity_attr a where ";
		if (RcOrderVO.HandleType_Entity_ImportFile.equals(vo.getHandleType())) {
			List entityList = vo.getEntityCodeList();
			if (entityList == null || entityList.size() < 1) {
				return 0;
			}
			String cond0 = " exists(select rc_order_list.resource_instance_code from rc_order_list where  "
					+ tableName
					+ ".resource_instance_code=rc_order_list.resource_instance_code and "
					+ " rc_order_list.order_id="
					+ vo.getOrderId()
					+ " and rc_order_list.SALES_RESOURCE_ID="
					+ vo.getSalesRescId() + ")";
			String cond1 = " a.entity_id in (select b.resource_instance_id from RC_ENTITY b,rc_order_list c where"
					+ " c.order_id="
					+ vo.getOrderId()
					+ " and b.resource_instance_code=c.resource_instance_code "
					+ " and c.SALES_RESOURCE_ID=" + vo.getSalesRescId() + ")";
			// 插入删除实例的信息日至
			sql_logInsert.append(cond0);
			sql_logAttrInsert += cond1;
			System.out.println("删除实例属性：" + sql_logInsert.toString());

			comDao.excute(sql_logInsert.toString());
			// 如果实例是rc_entity表，则删除实例属性信息
			if ("rc_entity".equalsIgnoreCase(tableName.trim())
					|| "rc_entity2".equalsIgnoreCase(tableName.trim())) {
				comDao.excute(sql_logAttrInsert);
				String del_attr = "delete from rc_entity_attr a where  "
						+ cond1;
				comDao.excute(del_attr);
			}
			// 删除实例(包括实例、号码、SIM卡等资源)
			sql_del += cond0;
			System.out.println("删除报废实例：" + sql_del);

			rows = comDao.excute(sql_del);
		} else {

			// 作废的营销资源的ID和仓库的限制
			String cond = " SALES_RESOURCE_ID= "
					+ DAOUtils.filterQureyCond(vo.getSalesRescId())
					+ " and storage_id="
					+ DAOUtils.filterQureyCond(outStorageId.trim());
			RcOrderSegInfoVO[] segs = vo.getRSeg();
			String tmp = "";
			for (int i = 0; i < segs.length; i++) {
				if (segs[i] != null && !"".equals(segs[i].getResBCode())
						&& !"".equals(segs[i].getResECode())) {
					int resbcodeInt = Integer.parseInt(segs[i].getResBCode());
					int resecodeInt = Integer.parseInt(segs[i].getResECode());
					tmp += " and middle_code >= " + resbcodeInt
							+ " and middle_code <= " + resecodeInt;
					if (segs[i].getPreCode().length() > 0) {
						tmp += " and pre_code ='" + segs[i].getPreCode() + "'";
					}
					if (segs[i].getPostCode().length() > 0) {
						tmp += " and post_code ='" + segs[i].getPostCode()
								+ "'";
					}
					if ("rc_entity".equalsIgnoreCase(tableName.trim())) {
						// 插入删除实例的信息日至//同时插入实例的属性到attr_log中// 删除实例属性
						logDao.insertFromEntity(cond + tmp);
						rows = (int) entityDao.deleteByCond(cond + tmp);
					} else { // 不是rc_entity表的操作
						// 插入删除另类实例的信息日至
						comDao.excute(sql_logInsert.toString() + cond + tmp);
						// 删除另类实例信息
						rows = comDao.excute(sql_del + cond + tmp);
					}
					tmp = "";
				}

			}
		}
		Debug.print("SrStockBo:outEntityToWaste:" + sql_logInsert);
		Debug.print("SrStockBo:outEntityToWaste:" + sql_del);
		Debug.print("SrStockBo类outEntityToWaste方法中删除了" + rows + "条记录");
		return rows;
	}

	/**
	 * 资源实例的入库操作，前一步骤不是出库。直接插入实体和实体属性
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return int
	 */
	private int inEntityStorage(RcOrderVO vo) {
		if (vo == null || vo.getInStorageId() == null
				|| vo.getInStorageId().trim().length() < 1
				|| vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1
				|| vo.getLanId() == null || vo.getLanId().trim().length() < 1) {
			return -1;
		}
		String tableName = vo.getEntityTabName();
		int count = 0;
		if (RcOrderVO.HandleType_Entity_SegCode.equals(vo.getHandleType())) {
			String effDate = DAOUtils.getFormatedDate(new java.sql.Date(System
					.currentTimeMillis()));
			String expDate = "3000-01-01";
			// 如果界面输入实例属性，生效时间和失效时间则在此设置
			if (vo.getEntityVO() != null) {
				if (vo.getEntityVO().getEffDate() != null) {
					effDate = vo.getEntityVO().getEffDate();
				}
				if (vo.getEntityVO().getExpDate() != null) {
					expDate = vo.getEntityVO().getExpDate();
				}
			}
			// 查询该种营销资源的家族模板的等级集合
			List levelList = null;
			RcLevelDefDAO levelDao = SrDAOFactory.getInstance()
					.getRcLevelDefDAO();
			if (vo.getFamilyId() != null
					&& vo.getFamilyId().trim().length() > 0) {
				levelList = levelDao.findByCond(" family_id="
						+ vo.getFamilyId(), null);
			}
			List attrList = vo.getAttrList();
			// 起始和终止编码
			// long resbcodeInt = Long.parseLong(vo.getResBCode());
			// long resecodeInt = Long.parseLong(vo.getResECode());
			RcOrderSegInfoVO[] segs = vo.getRSeg();
			for (int i = 0; i < segs.length; i++) {
				if (segs[i] != null && !"".equals(segs[i].getResBCode())
						&& !"".equals(segs[i].getResECode())) {
					int resbcodeInt = Integer.parseInt(segs[i].getResBCode());
					int resecodeInt = Integer.parseInt(segs[i].getResECode());
					count += generateEntity(segs[i].getPreCode(), segs[i]
							.getPostCode(), resbcodeInt, resecodeInt, vo
							.getSalesRescId(), vo.getInStorageId(), effDate,
							expDate, vo.getLanId(), attrList, levelList,
							tableName);
				}

			}
		} else if (RcOrderVO.HandleType_Entity_ImportFile.equals(vo
				.getHandleType())) {// 这里的逻辑需要根据输入的字符串判断前缀码和后缀码。
			String sql = "";
			SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
			if ("rc_no".equalsIgnoreCase(tableName)) {
				sql = "insert into rc_no (RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,"
						+ "NO_SEG_ID,RESOURCE_STATE,STATE,init_time,EFF_DATE,EXP_DATE,STORAGE_ID)"
						+ " SELECT RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE "
						+ " '' as NO_SEG_ID,"
						+ " 'A' as RESOURCE_STATE,"
						+ " '00A' as STATE,sysdate as init_time,sysdate as EFF_DATE,to_date('3000-12-12', 'yyyy-mm-dd') as EXP_DATE,"
						+ vo.getAppStorageId()
						+ " as STORAGE_ID"
						+ " FROM RC_ORDER_LIST "
						+ " where order_id = "
						+ vo.getOrderId();

				count = comDao.excute(sql);
			} else if ("rc_no".equalsIgnoreCase(tableName)) {
				sql = "insert into rc_sim (RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,"
						+ " RESOURCE_STATE,STATE,in_s_time,EFF_DATE,EXP_DATE,STORAGE_ID)"
						+ " SELECT RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE "
						+ " ,'A' as RESOURCE_STATE,"
						+ " '00A' as STATE,sysdate as in_s_time,sysdate as EFF_DATE,to_date('3000-12-12', 'yyyy-mm-dd') as EXP_DATE,"
						+ vo.getAppStorageId()
						+ " as STORAGE_ID"
						+ " FROM RC_ORDER_LIST "
						+ " where order_id = "
						+ vo.getOrderId();

				count = comDao.excute(sql);
			} else if ("rc_entity2".equalsIgnoreCase(tableName)) {
				sql = "insert into rc_entity2 (RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,"
						+ "RESOURCE_LEVEL,RESOURCE_KIND,LAN_ID,OWNER_TYPE,OWNER_ID,CURR_STATE,STATE,CREATE_DATE,EFF_DATE,EXP_DATE,STORAGE_ID)"
						+ " SELECT RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE "
						+ " ,'' as RESOURCE_LEVEL,'1' as RESOURCE_KIND,"
						+ vo.getLanId()
						+ " as LAN_ID,'1' as OWNER_TYPE,'1' as OWNER_ID,'A' as CURR_STATE,"
						+ " '00A' as STATE,sysdate as CREATE_DATE,sysdate as EFF_DATE,to_date('3000-12-12', 'yyyy-mm-dd') as EXP_DATE,"
						+ vo.getAppStorageId()
						+ " as STORAGE_ID"
						+ " FROM RC_ORDER_LIST "
						+ " where order_id = "
						+ vo.getOrderId();

				count = comDao.excute(sql);
			} else {
				sql = "insert into rc_entity (RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,"
						+ "RESOURCE_LEVEL,RESOURCE_KIND,LAN_ID,OWNER_TYPE,OWNER_ID,CURR_STATE,STATE,CREATE_DATE,EFF_DATE,EXP_DATE,STORAGE_ID)"
						+ " SELECT RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE "
						+ " ,'' as RESOURCE_LEVEL,'1' as RESOURCE_KIND,"
						+ vo.getLanId()
						+ " as LAN_ID,'1' as OWNER_TYPE,'1' as OWNER_ID,'A' as CURR_STATE,"
						+ " '00A' as STATE,sysdate as CREATE_DATE,sysdate as EFF_DATE,to_date('3000-12-12', 'yyyy-mm-dd') as EXP_DATE,"
						+ vo.getAppStorageId()
						+ " as STORAGE_ID"
						+ " FROM RC_ORDER_LIST "
						+ " where order_id = "
						+ vo.getOrderId();

				count = comDao.excute(sql);
			}
		}
		return count;
	}

	private int generateEntity(String preCode, String postCode,
			int resbcodeInt, int resecodeInt, String salesRescId,
			String inStorageId, String effDate, String expDate, String lanId,
			List attrList, List levelList, String tableName) {
		int count = 0;
		RcEntityBo entityBo = new RcEntityBo();
		int t1 = String.valueOf(resecodeInt).length();
		int t2 = 0;
		for (int i = resbcodeInt; i <= resecodeInt; i++) {
			 RcEntityVO2 entityVO = new  RcEntityVO2();
			t2 = String.valueOf(i).length();
			String code = "";
			for (int j = 0; j < (t1 - t2); j++) {
				code += "0";
			}
			entityVO.setRescInstanceCode(preCode + code + i + postCode);
			entityVO.setPreCode(preCode);
			entityVO.setPostCode(postCode);
			entityVO.setMiddleCode(String.valueOf(i));
			entityVO.setSalesRescId(salesRescId);
			entityVO.setCurrState(ParamsConsConfig.RcEntityFreeState);
			entityVO.setState(ParamsConsConfig.RcEntityStateValide);
			entityVO.setStorageId(inStorageId);
			entityVO.setRescKind("0");
			entityVO.setEffDate(effDate);
			entityVO.setCreateDate(DAOUtils.getFormatedDate(new java.sql.Date(
					System.currentTimeMillis())));
			entityVO.setExpDate(expDate);
			entityVO.setLanId(lanId);
			// 确定实例的级别
			this.setEntityLevel(entityVO, levelList);
			// 插入实例
			entityVO = entityBo.insertRcEntity(entityVO, tableName);
			count++;
			// 如果资源的属性列表不为空，则插入实体属性
			if (entityVO != null && entityVO.getRescInstanceId() != null
					&& entityVO.getRescInstanceId().trim().length() > 0
					&& attrList != null && attrList.size() > 0) {
				RcAttrDefVO attrVO = null;
				for (int j = 0; j < attrList.size(); j++) {
					attrVO = (RcAttrDefVO) attrList.get(j);
					if (attrVO != null) {
						entityBo.insertRcEnAttrInfo(entityVO
								.getRescInstanceId(), attrVO.getAttrId(),
								attrVO.getAttrValue());
					}
				}
			}
		}
		return count;
	}

	/**
	 * 根据传入的级别集合确定传入实例的级别,此处要保证传入的levelList中的等级是按优先级由小到大顺序排列的(important)
	 * 
	 * @param vo
	 *            RcEntityVO
	 * @param levelList
	 *            List
	 * @return RcEntityVO
	 */
	private  RcEntityVO2 setEntityLevel(
			 RcEntityVO2 vo, List levelList) {
		if (vo == null || vo.getRescInstanceCode() == null || levelList == null
				|| levelList.size() < 1) {
			return vo;
		}
		String code = vo.getRescInstanceCode();
		RcLevelDefVO levelVO = null;
		// int prior = Integer.MAX_VALUE;
		String levelId = "";
		for (int i = 0; i < levelList.size(); i++) {
			levelVO = (RcLevelDefVO) levelList.get(i);
			if (levelVO != null && levelVO.getRuleString() != null
					&& code.matches(levelVO.getRuleString())) {
				levelId = levelVO.getRcLevelId();
				break;
			}
		}
		vo.setRescLevel(levelId);
		return vo;
	}

	// //////////////////////////代理商相关/////////////////////////////////////////
	/**
	 * 根据出入仓库查询代理商信息
	 * 
	 * @param map
	 *            Map:必须包括outStorageId或inStorageId
	 * @param pi
	 *            int
	 * @param ps
	 *            int
	 * @return PageModel
	 */
	public PageModel qryAgentByStorage(Map map, int pi, int ps) {
		PageModel pm = new PageModel();
		if (map == null
				|| (map.get("outStorageId") == null && map.get("inStorageId") == null)) {
			return pm;
		}
		String qryAgentCond = " ";
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		String outStorageId = null;
		String inStorageId = null;
		if (map.get("outStorageId") != null) {
			outStorageId = (String) map.get("outStorageId");
		}
		if (map.get("inStorageId") != null) {
			inStorageId = (String) map.get("inStorageId");
		}
		if (map.get("provId") != null
				&& ParamsConsConfig.PROV_ID_GX.equals((String) map
						.get("provId"))) {
			qryAgentCond = " and a.DEPART_ID in ( select DEPART_ID from AG_DEPART_RELA p where p.DEPART_TYPE='DT01')";
		}
		String sql = "";
		String sql_count = "";
		String cond = "";
		if (outStorageId != null && outStorageId.trim().length() > 0
				&& inStorageId != null && inStorageId.trim().length() > 0) {
			sql = "SELECT '' as ORDER_ID, '' as APP_ID,'' as ACCEPT_ID,a.DEPART_ID,a.DEPART_NAME, '"
					+ RcOrderAgentVO.AcceptType_Out
					+ "' as ACCEPT_TYPE,null as RESOURCE_PRICE,null as DISCOUNT,null as REAL_PRICE"
					+ " FROM MP_DEPARTMENT a,STORAGE_DEPART_RELA b where a.DEPART_ID=b.DEPART_ID  and a.unit_type='2' and b.STORAGE_ID="
					+ outStorageId.trim()
					+ qryAgentCond
					+ " union SELECT '' as ORDER_ID, '' as APP_ID,'' as ACCEPT_ID,a.DEPART_ID,a.DEPART_NAME, '"
					+ RcOrderAgentVO.AcceptType_In
					+ "' as ACCEPT_TYPE,null as RESOURCE_PRICE,null as DISCOUNT,null as REAL_PRICE"
					+ " FROM MP_DEPARTMENT a,STORAGE_DEPART_RELA b where a.DEPART_ID=b.DEPART_ID  and a.unit_type='2' and b.STORAGE_ID="
					+ inStorageId.trim() + qryAgentCond;
			if ("INFORMIX".equals(databaseType)) {
				sql_count = "select count(*) as COL_COUNTS from table(MULTISET("
						+ sql + "))";
			} else {
				sql_count = "select count(*) as COL_COUNTS from (" + sql + ")";
			}
		} else if (outStorageId != null && outStorageId.trim().length() > 0) {
			sql = "SELECT '' as ORDER_ID, '' as APP_ID,'' as ACCEPT_ID,a.DEPART_ID,a.DEPART_NAME, '"
					+ RcOrderAgentVO.AcceptType_Out
					+ "' as ACCEPT_TYPE,null as RESOURCE_PRICE,null as DISCOUNT,null as REAL_PRICE"
					+ " FROM MP_DEPARTMENT a,STORAGE_DEPART_RELA b where a.DEPART_ID=b.DEPART_ID and a.unit_type='2' and b.STORAGE_ID="
					+ outStorageId.trim() + qryAgentCond;
			sql_count = "SELECT count(*) as COL_COUNTS "
					+ " FROM MP_DEPARTMENT a,STORAGE_DEPART_RELA b where a.DEPART_ID=b.DEPART_ID and a.unit_type='2' and b.STORAGE_ID="
					+ outStorageId.trim() + qryAgentCond;
		} else if (inStorageId != null && inStorageId.trim().length() > 0) {
			sql = "SELECT '' as ORDER_ID, '' as APP_ID,'' as ACCEPT_ID,a.DEPART_ID,a.DEPART_NAME, '"
					+ RcOrderAgentVO.AcceptType_In
					+ "' as ACCEPT_TYPE,null as RESOURCE_PRICE,null as DISCOUNT,null as REAL_PRICE"
					+ " FROM MP_DEPARTMENT a,STORAGE_DEPART_RELA b where a.DEPART_ID=b.DEPART_ID and a.unit_type='2' and b.STORAGE_ID="
					+ inStorageId.trim() + qryAgentCond;
			sql_count = "SELECT count(*) as COL_COUNTS "
					+ " FROM MP_DEPARTMENT a,STORAGE_DEPART_RELA b where a.DEPART_ID=b.DEPART_ID and a.unit_type='2' and b.STORAGE_ID="
					+ inStorageId.trim() + qryAgentCond;
		}
		RcOrderAgentDAO dao = SrDAOFactory.getInstance().getRcOrderAgentDAO();
		dao.setSQL_SELECT(sql);
		dao.setSQL_SELECT_COUNT(sql_count);
		pm = PageHelper.popupPageModel(dao, cond, pi, ps);
		System.out.println("类SrStockBo中qryAgentByStorage查询代理商sql:" + sql);
		return pm;
	}

	/**
	 * 根据主键查询订单押金信息
	 * 
	 * @param appId
	 *            String
	 * @param orderId
	 *            String
	 * @return RcOrderAgentVO
	 */
	public RcOrderAgentVO qryOrderAgentByPk(String appId, String orderId) {
		if (orderId == null || orderId.trim().length() < 1 || appId == null
				|| appId.trim().length() < 1) {
			return null;
		}
		RcOrderAgentDAO dao = SrDAOFactory.getInstance().getRcOrderAgentDAO();
		return dao.findByPrimaryKey(appId, orderId);
	}

	/**
	 * 根据订单信息进行押金收退步骤
	 * 
	 * @param vo
	 *            ，vo中的orderId、tacheId、stateId、operId、acceptId、departId不能为空
	 * @return -1：所需参数有错误，可能是缺少以上参数之一 1:成功完成操作返回 2:该订单已经不存在了
	 *         3:该订单的环节已经改变，请查验后再操作 5:流转订单时失败
	 * @return String
	 */
	public String orderAgentSubmit(RcOrderVO vo) {
		if (vo == null || vo.getOrderId() == null
				|| vo.getOrderId().trim().length() <= 0
				|| vo.getStateId() == null
				|| vo.getStateId().trim().length() <= 0
				|| vo.getOperId() == null || vo.getOperId().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1) {
			return "-1";
		}
		String result = "1";
		RcOrderDAO orderdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		List list1 = orderdao.findByCond(" a.order_id=" + vo.getOrderId());
		if (list1 != null && list1.size() >= 1 && list1.get(0) != null) {
			RcOrderVO tempvo1 = (RcOrderVO) list1.get(0);
			RcOrderVO tempvo2 = tempvo1.copy();
			if (tempvo2 == null || tempvo2.getTacheId() == null
					|| !tempvo2.getTacheId().equals(vo.getTacheId())) {
				return "3";
			}
			// 把vo带进来需要更新的参数付给刚查询的tempvo，再付给vo，为了更新这些字段
			tempvo2.setStateId(vo.getStateId());
			tempvo2.setComments(vo.getComments());
			tempvo2.setOperId(vo.getOperId());
			tempvo2.setDepartId(vo.getDepartId());
			tempvo2.setAcceptId(vo.getAcceptId());
			tempvo2.setRealPrice(vo.getRealPrice());
			tempvo2.setRescPrice(vo.getRescPrice());
			tempvo2.setDiscount(vo.getDiscount());
			vo = tempvo2;
			// 更新押金编号
			if (vo.getAcceptId() == null) {
				vo.setAcceptId("");
			}
			SqlComDAO comDao = (SqlComDAO) SrDAOFactory.getInstance()
					.getSqlComDAO();
			String cond = "  where ORDER_ID=" + vo.getOrderId()
					+ " and APP_ID=" + vo.getAppId();
			String sql = "update rc_order_agent set ACCEPT_ID='"
					+ vo.getAcceptId() + "'";
			if (vo.getRescPrice() != null
					&& vo.getRescPrice().trim().length() > 0)
				sql += ",RESOURCE_PRICE="
						+ DAOUtils.filterQureyCond(vo.getRescPrice());
			if (vo.getDiscount() != null
					&& vo.getDiscount().trim().length() > 0)
				sql += ",DISCOUNT="
						+ DAOUtils.filterQureyCond(vo.getDiscount());
			if (vo.getRealPrice() != null
					&& vo.getRealPrice().trim().length() > 0)
				sql += ",REAL_PRICE="
						+ DAOUtils.filterQureyCond(vo.getRealPrice());
			sql += cond;
			comDao.excute(sql);
			// 公用流转模块：公用流转操作。同时把订单相关信息更新
			vo = this.doFlowControl(vo);
			if (vo == null) {
				return "5";
			}
			// 更新公共模块日志,记录RC_order表的变动
			RcPublicLogDAO logDao = SrDAOFactory.getInstance()
					.getRcPublicLogDAO();
			RcPublicLogVO logVO = new RcPublicLogVO();
			logVO.setAct("M");
			logVO.setReworkTime(DAOUtils.getFormatedDate());
			logVO.setReworkTable("rc_order");
			logVO.setReworkWen(vo.getOperId());
			logVO.setReworkIp(vo.getReworkIp());
			logDao.logVO(logVO, tempvo1, vo);
		} else {
			return "2";
		}
		return result;
	}

	// /////////////////////// 统计处入库信息的方法 //////////////////////////////
	/**
	 * 资源实例调动日志查询，订单状态为结束
	 * 
	 * @param rescInstanceCode
	 *            String
	 * @param salesRescId
	 *            String
	 * @return List
	 */
	public List qryRescInOutLog(String rescInstanceCode, String salesRescId) {
		if (rescInstanceCode == null || rescInstanceCode.trim().length() < 1
				|| salesRescId == null || salesRescId.trim().length() < 1) {
			return new ArrayList();
		}
		rescInstanceCode = DAOUtils
				.filterQureyLikeCond(rescInstanceCode.trim());
		salesRescId = DAOUtils.filterQureyLikeCond(salesRescId.trim());
		StringBuffer buffer = new StringBuffer();
		String orderIds = "";
		RcEntityDAO entityDao = SrDAOFactory
				.getInstance().getRcEntityDAO();
		RcOrderListDAO orderListDao = SrDAOFactory.getInstance()
				.getRcOrderListDAO();
		RcOrderDAO orderDao = SrDAOFactory.getInstance().getRcOrderDAO();
		String cond1 = " resource_instance_code='" + rescInstanceCode
				+ "' and sales_resource_id=" + salesRescId;
		String cond2 = " a.RES_B_CODE<= '" + rescInstanceCode // +
				// "' and length(a.RES_B_CODE)<=length('" + rescInstanceCode +
				// "') "
				+ "' and a.RES_E_CODE>= '" + rescInstanceCode + "'";
		// "' and length(a.RES_E_CODE)>=length('" + rescInstanceCode + "')";
		List list1 = orderListDao.findByCond(cond1);
		cond2 = " a.app_id=b.app_id and b.app_type=c.app_type and a.TACHE_ID = d.TACHE_ID and a.STATE_id = e.STATE_id  and a.sales_resource_id=f.sales_resource_id "
				+ " and " + cond2;
		List list2 = orderDao.findByCond(cond2);
		if (list1 != null && list1.size() > 0) {
			RcOrderListVO orderListVO = null;
			for (int i = 0; i < list1.size(); i++) {
				orderListVO = (RcOrderListVO) list1.get(i);
				if (orderListVO != null && orderListVO.getOrderId() != null) {
					buffer.append(orderListVO.getOrderId()).append(",");
				}
			}
		}
		if (list2 != null && list2.size() > 0) {
			RcOrderVO orderVO = null;
			for (int i = 0; i < list2.size(); i++) {
				orderVO = (RcOrderVO) list2.get(i);
				if (orderVO != null && orderVO.getOrderId() != null) {
					buffer.append(orderVO.getOrderId()).append(",");
				}
			}
		}
		if (buffer != null && buffer.length() > 0) {
			orderIds = buffer.substring(0, buffer.length() - 1);
		}
		List rtnList = orderDao.qryRescInOutLog(rescInstanceCode, orderIds);
		return rtnList;
	}

	/**
	 * 统计处入库数量
	 * 
	 * @param hashMap
	 *            HashMap
	 * @param pi
	 *            int
	 * @param ps
	 *            int
	 * @return PageModel
	 */
	public PageModel satRescInOutAmount(Map map, int pi, int ps) {
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");

		String storageId = null;
		String lanId = null;
		String regionId = null;
		if (map.get("lanId") != null
				&& ((String) map.get("lanId")).trim().length() > 0)
			lanId = (String) map.get("lanId");
		if (map.get("regionId") != null
				&& ((String) map.get("regionId")).trim().length() > 0)
			regionId = (String) map.get("regionId");
		if (map.get("storageId") != null)
			storageId = String.valueOf(map.get("storageId"));
		String departId = null;
		if (map.get("departId") != null)
			departId = String.valueOf(map.get("departId"));
		String familyId = null;
		if (map.get("familyId") != null)
			familyId = String.valueOf(map.get("familyId"));
		String salesRescId = null;
		if (map.get("salesRescId") != null)
			salesRescId = String.valueOf(map.get("salesRescId"));
		String beginDate = null;
		if (map.get("beginDate") != null)
			beginDate = String.valueOf(map.get("beginDate"));
		String endDate = null;
		if (map.get("endDate") != null)
			endDate = String.valueOf(map.get("endDate"));
		if (departId != null && departId.endsWith(",")) {
			departId = departId.substring(0, departId.length() - 1);
		}
		String operId = null;
		if (map.get("operId") != null)
			operId = String.valueOf(map.get("operId"));

		PageModel pm = new PageModel();
		String sql1 = ""
				+ DAOSQLUtils
						.getFilterSQL(" select order_id,depart_id,out_storage_id as storage_id,sales_resource_id,act_amount as out_amount,0 as in_amount from rc_order where out_storage_id is not null")
				+ "";
		String sql2 = ""
				+ DAOSQLUtils
						.getFilterSQL(" select order_id,depart_id,in_storage_id as storage_id,sales_resource_id,0 as out_amount,act_amount as in_amount from rc_order  where in_storage_id is not null ")
				+ "";
		String cond = " and tache_id=5 and state_id='n' ";
		String sql = "";
		String sql_count = "";
		String sql_temp = "";

		// 按营销资源查询
		if (salesRescId != null && !"".equals(salesRescId)) {
			cond += "  and SALES_RESOURCE_ID in (" + salesRescId + ")";
		} else if (familyId != null && familyId.trim().length() > 0) {
			cond += " and exists(select * from "
					+ DAOSQLUtils.getFilterSQL("SALES_RESOURCE")
					+ " where rc_order.sales_resource_id=SALES_RESOURCE.sales_resource_id and SALES_RESOURCE.FAMILY_ID in ("
					+ familyId + ")) ";
		}
		if (departId != null && !"".equals(departId)) {
			cond += "  and depart_id in (" + departId + ")";
		} else if (operId != null) {
			String cond_temp1 = "";
			if (regionId != null && regionId.trim().length() > 0) {
				if (regionId.endsWith(",")) {
					regionId = regionId.substring(0, regionId.length() - 1);
				}
				cond_temp1 += " and h.REGION_ID in (" + regionId + ") ";
			} else if (lanId != null && lanId.trim().length() > 0) {
				if (lanId.endsWith(",")) {
					lanId = lanId.substring(0, lanId.length() - 1);
				}
				cond_temp1 += " and h.LAN_ID in (" + lanId + ") ";
			}
			cond += " and depart_id in (select h.DEPART_ID from mp_operator_depart h where h.OPER_ID="
					+ operId + cond_temp1 + ")";
		}

		if ("INFORMIX".equals(databaseType)) {
			if (beginDate != null && !"".equals(beginDate)) {
				// sql += " and a.app_time>=to_date('"+beginDate+"','%Y-%m-%d')
				// ";
				cond += "  and a.tache_time>=to_date('" + beginDate
						+ " 00:00:00 ','%Y-%m-%d %H:%M:%S')  ";
			}
			if (endDate != null && !"".equals(endDate)) {
				// sql += " and a.app_time<=to_date('"+endDate+"','%Y-%m-%d') ";
				cond += "  and a.tache_time<=to_date('" + endDate
						+ " 23:59:59 ','%Y-%m-%d %H:%M:%S')  ";
			}
		} else {
			if (beginDate != null && !"".equals(beginDate)) {
				cond += "  and tache_time>=to_date('" + beginDate
						+ "','yyyy-mm-dd')  ";
			}
			if (endDate != null && !"".equals(endDate)) {
				cond += "  and tache_time<=to_date('" + endDate
						+ "','yyyy-mm-dd')  ";
			}
		}

		sql1 += cond;
		sql2 += cond;
		sql = "(select  depart_id,storage_id,sales_resource_id,sum(out_amount) as out_amount,sum(in_amount) as in_amount,sum(in_amount)-sum(out_amount) as net_amount "
				+ " from (("
				+ sql1
				+ ") union ("
				+ sql2
				+ ")) group by sales_resource_id,depart_id,storage_id)  ";
		sql_temp = DAOSQLUtils
				.getFilterSQL(" from mp_department b,rc_storage c,SALES_RESOURCE d, RC_FAMILY e ,")
				+ " "
				+ sql
				+ " a "
				+ " where a.depart_id=b.depart_id and a.storage_id=c.storage_id and a.sales_resource_id=d.sales_resource_id and d.FAMILY_ID=e.FAMILY_ID ";
		// 对广西本地进行特殊化处理，将b.depart_id替换成b.party_id;
		// if(map!=null && map.get("provId")!=null &&
		// map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)){
		// sql_temp = sql_temp.replaceAll("b.depart_id", "b.party_id");
		// }

		// 按仓库查询
		String cond1 = "";
		if (storageId != null && !"".equals(storageId)) {
			cond1 = "  and a.storage_id in (" + storageId + ")";
		}
		sql_temp += cond1;

		sql = "select a.depart_id,a.storage_id,a.sales_resource_id,a.out_amount,a.in_amount,a.net_amount,b.DEPART_NAME,c.storage_name,d.sales_resource_name,e.FAMILY_NAME, "
				+ " '"
				+ beginDate
				+ "' as beginDate,'"
				+ endDate
				+ "' as endDate " + sql_temp;
		sql_count = "select count(*) as COL_COUNTS " + sql_temp;
		System.out.println(">>> " + sql + "\n" + sql_count);
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		rdao.setSQL_SELECT(sql);
		rdao.setSQL_SELECT_COUNT(sql_count);
		rdao.setFlag(1);
		rdao.setFiltered(true);
		pm = PageHelper.popupPageModel(rdao, "", pi, ps);

		return pm;
	}

	public PageModel qryOrderBill(Map map, int pi, int ps) {
		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");

		String storageId = null;
		String lanId = null;
		String regionId = null;
		if (map.get("lanId") != null
				&& ((String) map.get("lanId")).trim().length() > 0)
			lanId = (String) map.get("lanId");
		if (map.get("regionId") != null
				&& ((String) map.get("regionId")).trim().length() > 0)
			regionId = (String) map.get("regionId");
		if (map.get("storageId") != null)
			storageId = String.valueOf(map.get("storageId"));
		String departId = null;
		if (map.get("departId") != null)
			departId = String.valueOf(map.get("departId"));
		String familyId = null;
		if (map.get("familyId") != null)
			familyId = String.valueOf(map.get("familyId"));
		String salesRescId = null;
		if (map.get("salesRescId") != null)
			salesRescId = String.valueOf(map.get("salesRescId"));
		System.out.println("akakak");
		System.out.println("asdf" + salesRescId);
		String beginDate = null;
		if (map.get("beginDate") != null)
			beginDate = String.valueOf(map.get("beginDate"));
		String endDate = null;
		if (map.get("endDate") != null)
			endDate = String.valueOf(map.get("endDate"));
		if (departId != null && departId.endsWith(",")) {
			departId = departId.substring(0, departId.length() - 1);
		}
		String operId = null;
		if (map.get("operId") != null)
			operId = String.valueOf(map.get("operId"));

		PageModel pm = new PageModel();

		String type = "";
		if (map.get("type") != null)
			type = String.valueOf(map.get("type"));
		// System.out.println(type);
		String sql = "";
		if (type.equals("in")) {
			sql = ""
					+ DAOSQLUtils
							.getFilterSQL(" select * from rc_order  where in_storage_id is not null ");
		} else {
			sql = ""
					+ DAOSQLUtils
							.getFilterSQL(" select * from rc_order  where out_storage_id is not null ");
		}
		String cond = " and tache_id=5 and state_id='n' ";
		String sql_count = "";
		String sql_temp = "";

		// 按营销资源查询
		if (salesRescId != null && !"".equals(salesRescId)) {
			cond += "  and SALES_RESOURCE_ID in (" + salesRescId + ")";
		} else if (familyId != null && familyId.trim().length() > 0) {
			cond += " and exists(select * from "
					+ DAOSQLUtils.getFilterSQL("SALES_RESOURCE")
					+ " where rc_order.sales_resource_id=SALES_RESOURCE.sales_resource_id and SALES_RESOURCE.FAMILY_ID in ("
					+ familyId + ")) ";
		}
		if (departId != null && !"".equals(departId)) {
			cond += "  and depart_id in (" + departId + ")";
		} else if (operId != null) {
			String cond_temp1 = "";
			if (regionId != null && regionId.trim().length() > 0) {
				if (regionId.endsWith(",")) {
					regionId = regionId.substring(0, regionId.length() - 1);
				}
				cond_temp1 += " and h.REGION_ID in (" + regionId + ") ";
			} else if (lanId != null && lanId.trim().length() > 0) {
				if (lanId.endsWith(",")) {
					lanId = lanId.substring(0, lanId.length() - 1);
				}
				cond_temp1 += " and h.LAN_ID in (" + lanId + ") ";
			}
			cond += " and depart_id in (select h.DEPART_ID from mp_operator_depart h where h.OPER_ID="
					+ operId + cond_temp1 + ")";
		}

		if ("INFORMIX".equals(databaseType)) {
			if (beginDate != null && !"".equals(beginDate)) {
				// sql += " and a.app_time>=to_date('"+beginDate+"','%Y-%m-%d')
				// ";
				cond += "  and a.tache_time>=to_date('" + beginDate
						+ " 00:00:00 ','%Y-%m-%d %H:%M:%S')  ";
			}
			if (endDate != null && !"".equals(endDate)) {
				// sql += " and a.app_time<=to_date('"+endDate+"','%Y-%m-%d') ";
				cond += "  and a.tache_time<=to_date('" + endDate
						+ " 23:59:59 ','%Y-%m-%d %H:%M:%S')  ";
			}
		} else {
			if (beginDate != null && !"".equals(beginDate)) {
				cond += "  and tache_time>=to_date('" + beginDate
						+ "','yyyy-mm-dd')  ";
			}
			if (endDate != null && !"".equals(endDate)) {
				cond += "  and tache_time<=to_date('" + endDate
						+ "','yyyy-mm-dd')  ";
			}
		}

		sql += cond;
		if (type.equals("in")) {
			sql_temp = DAOSQLUtils
					.getFilterSQL(" from mp_department b,rc_storage c,SALES_RESOURCE d, RC_FAMILY e , ")
					+ " ("
					+ sql
					+ ") a "
					+ "  left join rc_storage f on a.out_storage_id=f.storage_id left join PARTY_ROLE g on a.oper_id=g.party_role_id  where a.depart_id=b.depart_id and a.in_storage_id=c.storage_id   and a.sales_resource_id=d.sales_resource_id and d.FAMILY_ID=e.FAMILY_ID ";
		}
		if (type.equals("out")) {
			sql_temp = DAOSQLUtils
					.getFilterSQL(" from mp_department b,rc_storage f,SALES_RESOURCE d, RC_FAMILY e , ")
					+ " ("
					+ sql
					+ ") a "
					+ "  left join rc_storage c on a.in_storage_id=c.storage_id left join PARTY_ROLE g on a.oper_id=g.party_role_id  where a.depart_id=b.depart_id and a.out_storage_id=f.storage_id   and a.sales_resource_id=d.sales_resource_id and d.FAMILY_ID=e.FAMILY_ID ";
		}

		// 对广西本地进行特殊化处理，将b.depart_id替换成b.party_id;
		// if(map!=null && map.get("provId")!=null &&
		// map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)){
		// sql_temp = sql_temp.replaceAll("b.depart_id", "b.party_id");
		// }

		// 按仓库查询
		String cond1 = "";
		if (storageId != null && !"".equals(storageId)) {
			if (type.equals("in")) {
				cond1 = "  and a.in_storage_id in (" + storageId + ")";
			} else {
				cond1 = "  and a.out_storage_id in (" + storageId + ")";
			}
		}
		sql_temp += cond1;
		if (type.equals("in")) {
			sql = "select g.party_role_name as oper_name,c.storage_name as in_storage_name,f.storage_name as out_storage_name,a.order_id,a.tache_time,a.act_amount as in_amount,0 as out_amount "
					+ sql_temp;
		} else {
			sql = "select g.party_role_name as oper_name,c.storage_name as in_storage_name,f.storage_name as out_storage_name,a.order_id,a.tache_time,a.act_amount as out_amount,0 as in_amount "
					+ sql_temp;
		}
		sql_count = "select count(*) as COL_COUNTS " + sql_temp;
		System.out.println(">>> " + sql + "\n" + sql_count);
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		rdao.setSQL_SELECT(sql);
		rdao.setSQL_SELECT_COUNT(sql_count);
		rdao.setFlag(4);
		rdao.setFiltered(true);
		pm = PageHelper.popupPageModel(rdao, "", pi, ps);

		return pm;
	}

	/**
	 * 根据部门查找营业区进而查找对应的营销资源的价格，乘数量得到价值
	 * 
	 * @param departId
	 *            String
	 * @param salesRescId
	 *            String
	 * @param amount
	 *            double
	 * @return double
	 */
	public double computeSalesRescValue(String departId, String salesRescId,
			double amount) {
		if (departId == null || departId.trim().length() < 1
				|| salesRescId == null || salesRescId.trim().length() < 1)
			return 0d;
		double value = 0d;
		SaleRescPricDAO pricDao = SrDAOFactory.getInstance()
				.getSaleRescPricDAO();
		SrDepartBo departBo = new SrDepartBo();
		SaleRescPricVO priceVO = null;
		String rescLevel = "-1";
		String businessId = departBo.qryBusinessByDept(departId);
		if (businessId != null && businessId.trim().length() > 0) {
			priceVO = pricDao.findByPrimaryKey(businessId, rescLevel,
					salesRescId);
			if (priceVO != null && priceVO.getPrice() != null
					&& priceVO.getPrice().trim().length() > 0)
				value = Double.parseDouble(priceVO.getPrice()) * amount;
		}
		return value;
	}

	/**
	 * 根据营销资源id查找其相对应的表名
	 * 
	 * @param salesRescId
	 *            String
	 * @return String
	 */
	public String getEntityTableName(String salesRescId) {
		if (salesRescId == null || salesRescId.trim().length() < 1) {
			return null;
		}
		String tableName = "rc_entity";
		SalesRescDAO salesRescDao = SrDAOFactory.getInstance()
				.getSalesRescDAO();
		RcFamilyEntityRelaDAO relaDao = SrDAOFactory
				.getInstance().getRcFamilyEntityRelaDAO();
		SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(salesRescId);
		if (salesRescVO != null && salesRescVO.getFamilyId() != null
				&& salesRescVO.getFamilyId().trim().length() > 0) {
			if (SalesRescVO.ManageMode_Entity.equals(salesRescVO
					.getManageMode())) {
				List list = relaDao.findByCond(" FAMILY_ID="
						+ salesRescVO.getFamilyId());
				if (list != null && list.size() > 0 && list.get(0) != null) {
					tableName = ((RcFamilyEntityRelaVO) list.get(0))
							.getEntityTabName();
				}
			} else {
				tableName = null;
			}
		}
		return tableName;
	}

	public PageModel queryDownStockNum(HashMap map, int pageIndex, int pageSize) {

		String databaseType = CrmParamsConfig.getInstance().getParamValue(
				"DATABASE_TYPE");
		String cond = "";
		String lanId = null;
		String regionId = null;
		String downStorageIds = null;
		String saleIds = null;
		String operId = null;
		if (map != null) {
			if (map.get("lanId") != null
					&& ((String) map.get("lanId")).trim().length() > 0)
				lanId = (String) map.get("lanId");
			if (map.get("regionId") != null
					&& ((String) map.get("regionId")).trim().length() > 0)
				regionId = (String) map.get("regionId");
			if (map.get("downStorageIds") != null
					&& ((String) map.get("downStorageIds")).trim().length() > 0)
				downStorageIds = (String) map.get("downStorageIds");
			if (map.get("salesRescIds") != null
					&& ((String) map.get("salesRescIds")).trim().length() > 0)
				saleIds = (String) map.get("salesRescIds");
			if (map.get("operId") != null
					&& ((String) map.get("operId")).trim().length() > 0)
				operId = (String) map.get("operId");
		}
		RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
		// start 组装查询条件
		cond = " and a.storage_id in(" + downStorageIds + ")";
		if (saleIds != null && saleIds.trim().length() > 0) {
			cond += " and a.sales_resource_id in (" + saleIds + ")";
		}
		// end 组装查询条件

		// 主sql语句
		String sql_oracle = "select c.storage_id,d.storage_name,c.sales_resource_id,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT "
				+ " from (select storage_id,sales_resource_id,sum(stock_amount) as stock_amount from ( "
				+ DAOSQLUtils
						.getFilterSQL(" select distinct a.resource_instance_id,a.storage_id,a.sales_resource_id,1 as stock_amount from rc_entity a ")
				+ " where a.CURR_STATE='"
				+ ParamsConsConfig.RcEntityFreeState
				+ "' and a.state='"
				+ ParamsConsConfig.RcEntityStateValide
				+ "'"
				+ cond
				+ " union all "
				+ DAOSQLUtils
						.getFilterSQL("select distinct null as resource_instance_id,a.* from rc_stock a where 1=1 ")
				+ cond
				+ " ) group by storage_id,sales_resource_id) c "
				+ DAOSQLUtils
						.getFilterSQL("inner join RC_STORAGE d on c.storage_id=d.storage_id")
				+ DAOSQLUtils
						.getFilterSQL(" inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ")
				+ DAOSQLUtils
						.getFilterSQL(" left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");

		String sql_informix = "select c.storage_id,d.storage_name,c.sales_resource_id,e.sales_resource_name,c.stock_amount,f.UP_LIMIT,f.DOWN_LIMIT  "
				+ " from table(MULTISET(select storage_id,sales_resource_id,sum(stock_amount) as stock_amount from table(MULTISET( "
				+ " select distinct a.resource_instance_id,a.storage_id,a.sales_resource_id,1 as stock_amount from rc_entity a"
				+ " where a.CURR_STATE='"
				+ ParamsConsConfig.RcEntityFreeState
				+ "' and a.state='"
				+ ParamsConsConfig.RcEntityStateValide
				+ "'"
				+ cond
				+ " union all select distinct null as resource_instance_id,a.* from rc_stock a where 1=1 "
				+ cond
				+ " )) group by storage_id,sales_resource_id)) c inner join RC_STORAGE d on c.storage_id=d.storage_id"
				+ " inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id "
				+ " left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)";

		String sqlcount_oracle = "select count(c.sales_resource_id) AS COL_COUNTS "
				+ " from (select storage_id,sales_resource_id,sum(stock_amount) as stock_amount from ( "
				+ DAOSQLUtils
						.getFilterSQL(" select distinct a.resource_instance_id,a.storage_id,a.sales_resource_id,1 as stock_amount from rc_entity a")
				+ " where a.CURR_STATE='"
				+ ParamsConsConfig.RcEntityFreeState
				+ "' and a.state='"
				+ ParamsConsConfig.RcEntityStateValide
				+ "'"
				+ cond
				+ " union all "
				+ DAOSQLUtils
						.getFilterSQL("select distinct null as resource_instance_id,a.* from rc_stock a where 1=1 ")
				+ cond
				+ " ) group by storage_id,sales_resource_id) c "
				+ DAOSQLUtils
						.getFilterSQL("inner join RC_STORAGE d on c.storage_id=d.storage_id")
				+ DAOSQLUtils
						.getFilterSQL(" inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id ")
				+ DAOSQLUtils
						.getFilterSQL(" left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)");

		String sqlcount_informix = "select count(c.sales_resource_id) AS COL_COUNTS "
				+ " from table(MULTISET(select storage_id,sales_resource_id,sum(stock_amount) as stock_amount from table(MULTISET( "
				+ " select distinct a.resource_instance_id,a.storage_id,a.sales_resource_id,1 as stock_amount from rc_entity a"
				+ " where a.CURR_STATE='"
				+ ParamsConsConfig.RcEntityFreeState
				+ "' and a.state='"
				+ ParamsConsConfig.RcEntityStateValide
				+ "'"
				+ cond
				+ " union all select distinct null as resource_instance_id,a.* from rc_stock a where 1=1"
				+ cond
				+ " )) group by storage_id,sales_resource_id)) c inner join RC_STORAGE d on c.storage_id=d.storage_id"
				+ " inner join SALES_RESOURCE e on c.sales_resource_id=e.sales_resource_id "
				+ " left outer join rc_stock_limit f on (c.storage_id=f.storage_id and e.FAMILY_ID=f.FAMILY_ID)";

		String sql = sql_oracle;
		String sqlcount = sqlcount_oracle;
		if ("INFORMIX".equals(databaseType)) {
			sql = sql_informix;
			sqlcount = sqlcount_informix;
		}

		Debug.print("类SrStockBo,方法queryDownStockNum中执行的sql是:" + sql);
		Debug
				.print("类SrStockBo,方法queryDownStockNum中执行的查询sqlcount是:"
						+ sqlcount);
		PageModel pm = new PageModel();

		// 设置要查询语句的主查询语句
		dao.setSQL_SELECT(sql);
		dao.setSQL_SELECT_COUNT(sqlcount);
		dao.setFlag(1); // 设置要查询仓库模板的上下限
		dao.setFiltered(true);
		dao.setUpDown("down");
		pm = PageHelper.popupPageModel(dao, "", pageIndex, pageSize);
		return pm;
	}

	public List getOrderSegInfo(String orderId, String flag) {
		List retList = new ArrayList();
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		retList = rdao.getOrderSegInfoByOrderId(orderId, flag);
		return retList;
	}

	public String getSegInfo(String resBCode, String resECode, String preCode,
			String postCode, String storageId, String salesRescId) {
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		return rdao.getSegInfo(resBCode, resECode, preCode, postCode,
				storageId, salesRescId);
	}

	/**
	 * 获取终端代理商信息
	 * 
	 * @param orderId
	 * @param salesRescId
	 * @return
	 */
	public String[] getAgentInfo(String orderId, String salesRescId) {
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();
		String agentId = rdao.getAgentInfo(orderId);
		SalesRescDAO salesRescDao = SrDAOFactory.getInstance()
				.getSalesRescDAO();
		SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(salesRescId);
		String isEntity = "";
		if (salesRescVO != null && salesRescVO.getFamilyId() != null
				&& "104".equals(salesRescVO.getFamilyId().trim())) {// 终端
			isEntity = "1";
		}
		return new String[] { agentId, isEntity };
	}

	/**
	 * 调用集团接口，存储代理商终端信息
	 * 
	 * @param map
	 * @return
	 */
	public String insertAgentInfo(HashMap map) {
		RcOrderDAO rdao = (RcOrderDAO) SrDAOFactory.getInstance()
				.getRcOrderDAO();

		return rdao.insertAgentInfo(map);
	}

	/**
	 * 终端统计平衡表
	 * 
	 * @param deptID
	 * @param saleIds
	 * @return
	 */


	public List qryRcEntityCount(String lanId,String countType, String sheetType,String date) throws Exception {

    	List list = new ArrayList();
		RcEntityReportDAO redao = SrDAOFactory.getInstance().getRcEntityReportDAO();
		String sql = "";
		
		if(countType==null || countType.length()<=0){
			sql = "SELECT LAN_ID,TERM_TYPE,REPORT_TYPE,STOCK_NUM_END,ADD_NUM+ADD_NUM2 as add_num, SALE_NUM,STOCK_NUM_CURR,DIFFERENTNUM,SATDATE FROM RC_ENTITY_REPORT where 1=1 ";
		}else if("1".equals(countType)){
			sql = "SELECT LAN_ID,TERM_TYPE,REPORT_TYPE,STOCK_NUM_END,add_num, SALE_NUM,STOCK_NUM_CURR,DIFFERENTNUM,SATDATE FROM RC_ENTITY_REPORT where 1=1 ";
		}else{
			sql = "SELECT LAN_ID,TERM_TYPE,REPORT_TYPE,STOCK_NUM_END, add_num2 as add_num,SALE_NUM,STOCK_NUM_CURR,DIFFERENTNUM,SATDATE FROM RC_ENTITY_REPORT where 1=1 ";
		}

    	sql+=" and report_type = ? ";
    	sql+=" and satdate = ? ";
    	if("2".equals(sheetType)){
    		date=date.substring(0,7);
    	}else if("3".equals(sheetType)) {
    		date=date.substring(0,4);
    	}
    	
    	if(lanId!=null && lanId.length()>0){
    		sql+=" and lan_id = ? ";
        	String []params = new String[3];
        	params[0]=sheetType;
        	params[1]=date;
        	params[2]=lanId;
        	list=redao.findBySql(sql, params);
    	}else{
        	String []params = new String[2];
        	params[0]=sheetType;
        	params[1]=date;
        	list=redao.findBySql(sql, params);
    	}
    	System.out.println("查询报表sql为===============");
    	System.out.println(sql);
 
    	
       	
 	   Date currentTime = new Date();
 	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 	   String dateString = dateFormat.format(currentTime);//当天
 	   SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
 	   String monthString = monthFormat.format(currentTime);//当月
 	   SimpleDateFormat yeatFormat = new SimpleDateFormat("yyyy");
 	   String yearString = yeatFormat.format(currentTime);//当年
 	   
 	if( ("1".equals(sheetType)&& date.equals(dateString)) //日报，当前日
 			||("2".equals(sheetType)&& date.substring(0,7).equals(monthString)) //月报，当前月
 			||("3".equals(sheetType)&& date.substring(0,4).equals(yearString))){//年报，当前年
 		//为了测试
// 		List list1 = new ArrayList();
// 		HashMap map = new HashMap();
// 		map.put("lanid", "7");
// 		map.put("addnum", "10");
// 		list1.add(map);
// 		HashMap map4 = new HashMap();
// 		map4.put("lanid", "7");
// 		map4.put("addnum", "18");
// 		list1.add(map4);
//
// 		List list2 = new ArrayList();
// 		HashMap map2 = new HashMap();
// 		map2.put("lanid", "9");
// 		map2.put("salenum", "11");
// 		HashMap map3 = new HashMap();
// 		map3.put("lanid", "7");
// 		map3.put("salenum", "12");
// 		list2.add(map2);
// 		list2.add(map3);
    	List list1 = qryNewCreate(dateString,lanId,countType);//当日新增
    	List list3 = qryNewCreateNoDif(dateString,lanId);//当日新增
    	List list2 = qryNewSale(dateString,lanId,countType);//当日销售
    	list = addList(list,list1,"addnum");//更新当期新增字段
    	list = addList2(list,list3,"stockcurr");//更新当期库存字段
    	list = delList(list,list2);//更新销售
 	}
    	return countDif(list);
    	
	}
	//当日新增，区分crm系统还是终端系统导入
	public List qryNewCreate(String date,String lanId,String countType) throws Exception{

		SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
    	String sqlnewcreate = "select b.lan_id as lanid,count(a.resource_instance_id) as addnum from rc_entity a,rc_storage b " +
    		"where a.storage_id=b.storage_id and a.create_date = to_date(?,'yyyy-mm-dd')" +
    		" and exists (select 1 from dc_device,dc_termunit, dc_public,sales_resource_id_rela s"
    		+" where dc_public.stype = 41  and (dc_public.codee = '//' or  dc_public.codee like '%C7%')"
                 //and dc_termunit.business_id = '1149'
            +" and dc_device.vflag = '0'  and dc_device.scode = dc_termunit.dev_subtype(+)  " 
            +" and dc_device.DEV_TYPE = dc_public.pkey and dc_device.scode = s.DC_DEVICE_SCODE"
            +" and a.sales_resource_id = s.sales_resource_id)";
    	if(countType!=null && "1".equals(countType)){
    		sqlnewcreate+=" and exists(select 1 from RC_ENTITY_ATTR k where a.RESOURCE_INSTANCE_ID = k.entity_id and k.ATTR_ID = 1005)";
    	}else if (countType!=null && "2".equals(countType)){
        	sqlnewcreate+=" and not exists(select 1 from RC_ENTITY_ATTR k where a.RESOURCE_INSTANCE_ID = k.entity_id and k.ATTR_ID = 1005)";
    	}
    	if(lanId!=null && lanId.length()>0){
    		sqlnewcreate+=
                "  and b.lan_id = ?" +
                "  group by b.lan_id";
        	String []params = new String[2];
        	params[0]=date;
        	params[1]=lanId;
    		list1 = dao.executeQueryForMapList(sqlnewcreate, params);
    		String sqlnewcreate2 = sqlnewcreate.replaceAll("rc_entity a", "rc_entity2 a");
    		list2 = dao.executeQueryForMapList(sqlnewcreate2, params);
    	}else{
    		sqlnewcreate+=
                "  group by b.lan_id";
        	String []params = new String[1];
        	params[0]=date;
    		list1 = dao.executeQueryForMapList(sqlnewcreate, params);
    		String sqlnewcreate2 = sqlnewcreate.replaceAll("rc_entity", "rc_entity2");
    		list2 = dao.executeQueryForMapList(sqlnewcreate2, params);
    	}		
		for(int i=0;i <list2.size();i++) 
		{ 
			list1.add(list2.get(i)); 
		}
		return list1;
	}

	//当日新增，不区分crm系统还是终端系统导入
public List qryNewCreateNoDif(String date,String lanId) throws Exception{

	SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
	List list1 = new ArrayList();
	List list2 = new ArrayList();
	String sqlnewcreate = "select b.lan_id as lanid,count(a.resource_instance_id) as addnum from rc_entity a,rc_storage b " +
		"where a.storage_id=b.storage_id and a.create_date = to_date(?,'yyyy-mm-dd')" +
		" and exists (select 1 from  dc_device, dc_termunit, dc_public_view,sales_resource_id_rela s"
		+" where dc_public_view.stype = 41  and (dc_public_view.codee = '//' or  dc_public_view.codee like '%C7%')"
             //and dc_termunit.business_id = '1149'
        +" and dc_device.vflag = '0'  and dc_device.scode = dc_termunit.dev_subtype(+)  " 
        +" and dc_device.DEV_TYPE = dc_public_view.pkey and dc_device.scode = s.DC_DEVICE_SCODE"
        +" and a.sales_resource_id = s.sales_resource_id)";
       // +" group by b.lan_id";
	if(lanId!=null && lanId.length()>0){
		sqlnewcreate+=
            "  and b.lan_id = ?" +
            "  group by b.lan_id";
    	String []params = new String[2];
    	params[0]=date;
    	params[1]=lanId;
		list1 = dao.executeQueryForMapList(sqlnewcreate, params);
		String sqlnewcreate2 = sqlnewcreate.replaceAll("rc_entity a", "rc_entity2 a");
		list2 = dao.executeQueryForMapList(sqlnewcreate2, params);
	}else{
		sqlnewcreate+=
            "  group by b.lan_id";
    	String []params = new String[1];
    	params[0]=date;
		list1 = dao.executeQueryForMapList(sqlnewcreate, params);
		String sqlnewcreate2 = sqlnewcreate.replaceAll("rc_entity", "rc_entity2");
		list2 = dao.executeQueryForMapList(sqlnewcreate2, params);
	}		
	for(int i=0;i <list2.size();i++) 
	{ 
		list1.add(list2.get(i)); 
	}
	return list1;
}
	public List qryNewSale(String date,String lanId,String countType) throws Exception{

		SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
		List list1 = new ArrayList();
    	String sqlnewcreate = "select a.lan_id as lanid,count(a.resource_instance_id) as salenum from rc_sale_log a" +
    		" where to_char(a.sale_time,'yyyy-mm-dd') =?" +
    		" and exists (select 1 from  dc_device, dc_termunit, dc_public_view,sales_resource_id_rela s"
    		+" where dc_public_view.stype = 41  and (dc_public_view.codee = '//' or  dc_public_view.codee like '%C7%')"
                 //and dc_termunit.business_id = '1149'
            +" and dc_device.vflag = '0'  and dc_device.scode = dc_termunit.dev_subtype(+)  " 
            +" and dc_device.DEV_TYPE = dc_public_view.pkey and dc_device.scode = s.DC_DEVICE_SCODE"
            +" and a.sales_resource_id = s.sales_resource_id)" ;
    	if(lanId!=null && lanId.length()>0){
    		sqlnewcreate+=
                " and a.lan_id = ?" +
                "  group by a.lan_id";
        	String []params = new String[2];
        	params[0]=date;
        	params[1]=lanId;
    		list1 = dao.executeQueryForMapList(sqlnewcreate, params);
    	}else{
    		sqlnewcreate+=
                "  group by a.lan_id";
        	String []params = new String[1];
        	params[0]=date;
    		list1 = dao.executeQueryForMapList(sqlnewcreate, params);
    	}
		return list1;
	}
	//更新当日新增数字段，list1表  list2是实时查新增的
	public List addList (List volist1,List list2,String type){	
		List exList = new ArrayList();
		if(volist1==null ||volist1.size()<=0 ){
			volist1 =new ArrayList();
			if(list2==null||list2.size()<=0)
				return null;
			for(int j=0;j<list2.size();j++){
				HashMap hm2= (HashMap)list2.get(j);
				exList.add(hm2);
			}
		}else{
			int flag=0;
			for(int j=0;j<list2.size();j++){
				HashMap hm2= (HashMap)list2.get(j);
				if((String) hm2.get("lanid")==null || ((String) hm2.get("lanid")).length()<=0){
					exList.add(hm2);
					continue;
				}
				for(int i=0;i<volist1.size();i++){
					flag=0;
					RcEntityReportVO vo= (RcEntityReportVO)volist1.get(i);
					if(vo.getLanId()!=null && vo.getLanId().endsWith((String) hm2.get("lanid"))){//相等
						if(type.equals("addnum"))
							vo.setAddNum(String.valueOf(strToInt(vo.getAddNum())+strToInt((String)hm2.get("addnum"))));
						flag=1;
						break;
					}
					}
				if(flag==0){
					exList.add(hm2);
				}
				//exList.add(hm2);//如果都不相等
			}
		}

		if(exList==null || exList.size()<=0)
			return volist1;
		for(int i=0;i<exList.size();i++){
			HashMap hm2= (HashMap)exList.get(i);
			RcEntityReportVO vo= new RcEntityReportVO();
			vo.setLanId((String) hm2.get("lanid"));
			vo.setAddNum((String) hm2.get("addnum"));
			vo.setStockNumCurr("0");
			vo.setStockNumEnd("0");
			vo.setSaleNum("0");
			volist1.add(vo);
		}
		return volist1;
	}
	
	//更新当期库存字段，list1表  list2是实时查新增的
	public List addList2 (List volist1,List list2,String type){	
		List exList = new ArrayList();
		if(volist1==null ||volist1.size()<=0 ){
			volist1 =new ArrayList();
			if(list2==null||list2.size()<=0)
				return null;
			for(int j=0;j<list2.size();j++){
				HashMap hm2= (HashMap)list2.get(j);
				exList.add(hm2);
			}
		}else{
			for(int j=0;j<list2.size();j++){
				HashMap hm2= (HashMap)list2.get(j);
				if((String) hm2.get("lanid")==null || ((String) hm2.get("lanid")).length()<=0){
					exList.add(hm2);
					continue;
				}
				for(int i=0;i<volist1.size();i++){
					RcEntityReportVO vo= (RcEntityReportVO)volist1.get(i);
					if(vo.getLanId()!=null && vo.getLanId().endsWith((String) hm2.get("lanid"))){//相等
							vo.setStockNumCurr(String.valueOf(strToInt(vo.getStockNumCurr())+strToInt((String)hm2.get("addnum"))));
						break;
					}
					}
			}
		}

		return volist1;
	}
	//list1表  list2是实时查销售的
	public  List delList (List volist1,List list2){		
		List exList = new ArrayList();
		if(volist1==null ||volist1.size()<=0 ){
			volist1 =new ArrayList();
			if(list2==null||list2.size()<=0)
				return null;
			for(int j=0;j<list2.size();j++){
				HashMap hm2= (HashMap)list2.get(j);
				exList.add(hm2);
			}
		}else{
			int flag=0;
			for(int j=0;j<list2.size();j++){
				HashMap hm2= (HashMap)list2.get(j);
				if((String) hm2.get("lanid")==null || ((String) hm2.get("lanid")).length()<=0){
					exList.add(hm2);
					continue;
				}
				for(int i=0;i<volist1.size();i++){
					flag = 0;
					RcEntityReportVO vo= (RcEntityReportVO)volist1.get(i);
					if(vo.getLanId()!=null && vo.getLanId().endsWith((String) hm2.get("lanid"))){
						vo.setSaleNum(String.valueOf(strToInt(vo.getSaleNum())+strToInt((String)hm2.get("salenum"))));
						vo.setStockNumCurr(String.valueOf(strToInt(vo.getStockNumCurr())-strToInt((String)hm2.get("salenum"))));
						flag=1;
						break;
					}
				}
				if(flag==0){
					exList.add(hm2);
				}
			}
		}
		if(exList==null || exList.size()<=0)
			return volist1;
		for(int i=0;i<exList.size();i++){
			HashMap hm2= (HashMap)exList.get(i);
			RcEntityReportVO vo= new RcEntityReportVO();
			vo.setLanId((String) hm2.get("lanid"));
			vo.setAddNum("0");
			vo.setStockNumCurr("0");
			vo.setStockNumEnd("0");
			vo.setSaleNum((String)hm2.get("salenum"));
			volist1.add(vo);
		}
		
		return volist1;
	}
	//计算差异
	public List countDif(List voList){
		if(voList==null || voList.size()<=0){
			return voList;			
		}
		for (int i=0;i<voList.size();i++){
			RcEntityReportVO vo = (RcEntityReportVO)voList.get(i);
			vo.setDifferentnum(String.valueOf((strToInt(vo.getStockNumCurr())-(strToInt(vo.getStockNumEnd())+strToInt(vo.getAddNum())-strToInt(vo.getSaleNum())))));
		}
		return voList;
	}
	//将字符串变成整数
	public int strToInt(String str){
		if(str==null ||str.length()<=0){
			return 0;
		}
		return Integer.parseInt(str);
	}
}
