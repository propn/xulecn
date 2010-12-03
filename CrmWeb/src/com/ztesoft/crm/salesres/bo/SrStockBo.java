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
			// departId����������ǲ���ID������ѯ��ѡ�в����µ�����Ա������.
			if (departId != null && !"".equals(departId)) {
				sql += " and org_party_id in (" + departId + ") ";
			}

			StaffDAO sdao = (StaffDAO) SrDAOFactory.getInstance().getStaffDAO();
			// ʹ��ר��Ϊ���ʶ���׼����sql���
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
		// ���ϸò���Ա�ܿ����ĵ�λ��Ȩ���ж�
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
						// ����л��˶���������
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
						rcOrderVO.setTacheId("2"); // ����

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
						// ����л��˶���������
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
	 * ��backOrderId������Ӧ��rc_order_list��¼����newOrderId���²���rc_order_list��
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
								rcOrderVO.setTacheId("5"); // ����;
								rcOrderVO.setStateId("c"); // ����;

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
								rcOrderVO.setTacheId("2"); // ����;

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
								rcOrderVO.setTacheId("2"); // ����
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
								rcOrderVO.setTacheId("2"); // ����

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
	 * �����ĳ���������
	 * 
	 * @param 
	 *        vo��vo�е�orderId��tacheId��stateId��salesRescId��operId��departId��actAmount����Ϊ��
	 * @return -1����������д��󣬿�����ȱ�����ϲ���֮һ 1:�ɹ���ɲ������� 2:�ö����Ѿ���������
	 *         3:�ö����Ļ����Ѿ��ı䣬�������ٲ��� 4:����Աû��Ȩ�޲�����������������ֿ� 5:��ת����ʱʧ�� 6:��治�㣬���¿��ʧ��
	 *         7:���¿�����ʧ�� 8:�ö�����Ӫ����Դ����ģʽΪ�������û��ָ������ģʽ
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
		// ��֤����ⶩ��������Ƿ���Խ��г������������ҳ��ö�������Ϣ
		List list1 = orderdao.findByCond(" and a.order_id=" + vo.getOrderId());
		if (list1 != null && list1.size() >= 1 && list1.get(0) != null) {
			tempvo1 = (RcOrderVO) list1.get(0);
			RcOrderVO tempvo2 = tempvo1.copy();
			if (tempvo2 == null || tempvo2.getTacheId() == null
					|| !tempvo2.getTacheId().equals(vo.getTacheId())) {
				return "3";
			}
			// ��vo��������Ҫ���µĲ��������ղ�ѯ��tempvo���ٸ���vo��Ϊ�˸�����Щ�ֶ�
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
		// ��֤������Ա�Ƿ���Ȩ�޽��иòֿ����
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

		// �����һ�����ǽ��������Ƚ��г������ز�������������ɹ�����ת���������ʧ�ܣ�����ʧ����Ϣ
		String tTacheId = this.qryNextTache(vo.getOrderId(), vo.getAppType(),
				vo.getTacheId(), vo.getStateId());
		String manageMode = new SrSalesRescBo().getManageMode(vo
				.getSalesRescId());
		int rtnStock = 1; // ��������Ĭ�ϳɹ�
		if (manageMode == null
				|| SalesRescVO.ManageMode_NoMag.equals(manageMode)) {
			return "8";
		}
		if (SalesRescVO.ManageMode_Entity.equals(manageMode)) { // ʵ�����ģʽ
			rtnStock = entityStockInOut(vo, tTacheId);
			if (rtnStock == -1) {
				return "-1";
			}
		} else { // ����Ǵ�������
			if (tTacheId != null && "5".equals(tTacheId)) { // �����һ���ǽ���������ֿ�����
				vo.setResBCode(null);
				vo.setResECode(null);
				// ����Ӫ����Դ�ĳ����
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

		// ������תģ�飺������ת������ͬʱ�Ѷ��������Ϣ����
		vo = this.doFlowControl(vo);
		if (vo == null) {
			return "5";
		}
		// ���¹���ģ����־,��¼RC_order��ı䶯
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
	 * �ж϶����е�ʵ������ⰲʲô��ʽ���У�����2�־����RcOrderVO��˵�� ����ǰ��ϴ��ļ�����ʽ���еģ�����װ��Ҫ�����ʵ������ļ���
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
	 * ����Ӫ����Դidȷ����Ҫ�����ı�ͬʱ��familyId�ͱ������ý�RcOrder��
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
	 * ʵ����Դ�ĳ����
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return int��-1������ȱʧ�������Ƿ�ɹ��ı�־
	 */
	public int entityStockInOut(RcOrderVO vo, String tTacheId) {
		if (vo == null || vo.getAppType() == null || vo.getTacheId() == null
				|| vo.getTacheId().trim().length() < 1 || tTacheId == null
				|| tTacheId.trim().length() > 1) {
			return -1;
		}

		int count = 0;
		// �ж�ʵ���ĳ��������(�ļ��ϴ�����ʼ��ֹ��������)
		this.setEntityHandleType(vo);
		// ����Ӫ����Դidȷ����Ҫ�����ı�ͬʱ��familyId�ͱ������ý�RcOrder��
		this.setEntityTableName(vo);
		// ������ڵĲ����ǳ���,����������������;��������,��������������
		if (RcOrderOperStateVO.Tache_outStorage.equals(vo.getTacheId())) {
			if (vo.getOutStorageId() == null
					|| vo.getOutStorageId().trim().length() < 1) {
				return -1;
			}
			// �����һ�����̲��ǽ������Ҷ������������������Ҫ������ʵ��״̬��Ϊ����;��
			if (!"5".equals(tTacheId)) {
				if ("n".equals(vo.getStateId()) && !"C".equals(vo.getAppType()))
					this.changeEntityStatus(vo,
							ParamsConsConfig.RcEntityStateInValide);

			} else {
				// �����һ���ǽ�������RC_ENTITY������ʼ���롢��ֹ���뷶Χ�ڵ�ʵ������RC_ENTITY��Ǩ�Ƶ�RC_ENTITY_OUT_LOG����
				count = this.outEntityToWaste(vo);
			}
		} else { // ��������
			if (vo.getInStorageId() == null
					|| vo.getInStorageId().trim().length() < 1) {
				return -1;
			}
			// �����һ�����ǽ������Ҷ����ǻ������ʵ��״̬��Ϊ���ڿ⡱
			if (!"5".equals(tTacheId)) {
				if ("b".equals(vo.getStateId())) {
					this.changeEntityStatus(vo,
							ParamsConsConfig.RcEntityStateValide);
				}
			} else { // �����һ���ǽ����������������
				boolean isOut = this.isOutBeforeInStorage(vo.getAppType());
				if (isOut) { // ���ǰһ���ǳ�����ѱ���֮�ڵ�ʵ���ı�ֿ⣬����Ҫ��ʵ����״̬��Ϊ���ڿ⡱
					if (vo.getOutStorageId() == null
							|| vo.getOutStorageId().trim().length() < 1) {
						return -1;
					}
					// ��ʵ��״̬��Ϊ���ڿ⡱
					this.changeEntityStatus(vo,
							ParamsConsConfig.RcEntityStateValide);
					// ����ʵ���Ĳֿ���Ϣ
					count = this.updateEntityStorage(vo);
				} else {
					// ���ǰһ�����ǳ�����ѱ����ڵ�ʵ������ֿ⣬�����ʵ�����ԣ���Ҫ����ʵ������
					count = this.inEntityStorage(vo);
				}
			}
		}
		// ���سɹ���־
		return 1;
	}

	/**
	 * �ı�ʵ����״̬(�ڿ⡢��;)���ֳɰ��ļ��ϴ��Ͱ���ʼ��ֹ�ŷ�ʽ����
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
		if (RcOrderVO.HandleType_Entity_ImportFile.equals(vo.getHandleType())) { // �ļ��ϴ���ʽ��ʵ���Ĵ���
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
			count = comDao.excute(sql + cond); // ����ʵ��״̬
		} else if (RcOrderVO.HandleType_Entity_SeriesCode.equals(vo
				.getHandleType())) { // ��ʼ��ֹ���뷽ʽ��ʵ������
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
			count = comDao.excute(sql + cond); // ����ʵ��״̬
		} else {// ����ֶ�����,��ֹ����
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
						count += comDao.excute(sql + cond + tmp); // ����ʵ��״̬
						tmp = "";
					}
				}
				RcOrderDAO orderdao = (RcOrderDAO) SrDAOFactory.getInstance()
						.getRcOrderDAO();
				// ����ǳ������ дRC_SEG_ORDER
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
	 * ���¸ö�����Ҫ�����ʵ���Ĳֿ⡣����ʵ���Ĵ���ʽ��ͬ(�ļ��ϴ�����ʼ��ֹ���뷽ʽ)���в�ͬ�ĸ��¿�洦��
	 * ��rc_order_list���в�ѯ���������ʵ���������ʼ��ֹ����ȷ�� ��������������Դ��ͬ(rc_entity������SIM��)
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
		// ����ʵ���Ĵ���ʽ��ͬ(�ļ��ϴ�����ʼ��ֹ���뷽ʽ)���в�ͬ�ĸ��¿�洦��
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
					count += comDao.excute(sql + cond); // ����ʵ��״̬
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
	 * ������Ӧ������������ֿ�Ŀ��,�����п��Ļ��������ӻ������Ӧ��������
	 * 
	 * @param actAmount
	 * @param outStorageId
	 * @param inStorageId
	 * @return -1:���¿��ʧ�ܣ�1�����¿��ɹ���2����治�㣬���ܸ���
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
	 * ���ݶ������Ͳ��Ҹ����Ͷ����������ֿ�������ֿ��Ƿ�Ϊ��
	 * 
	 * @param appType
	 * @return Map: ����:appType:�������� inStorage��1Ϊ��Ҫָ����0Ϊ����Ҫָ��
	 *         outStorage��1Ϊ��Ҫָ����0Ϊ����Ҫָ��
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
	 * ���ݴ����Ķ��������ַ�����ѯ�����б�Ϊ�˴�ӡ����
	 * 
	 * @param orderIds
	 *            :��ʽΪid1,id2,id3
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
	 * ������תģ�飺������ת���������ݶ�����Ϣ���¶�����һ��Ҫ��ת����Ϣ������������һ��Ҫ��ת�Ĳ��裬����ʱ�䣬�����ˣ������˲���
	 * ע�ⶩ��RcOrderVO�а�������Ϣ���������£����ڵ��ñ�����ǰ��ѯ��֤����vo��Ϣ�����µ� ���Ҽ�¼�¶���������־
	 * 
	 * @param vo
	 *            ����������������͡��������ںʹ���״̬��Ҫ���µĴ����˺ʹ��������ڲ���
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

		// �ж����̵���һ�������˺�������ת�Ĵ���ͬ
		String tTacheId = null;
		// ������ת�²�ѯ��һ������
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

		// дRC_ORDER_EXC��
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
	 * ���ݶ����е������������ں��������������һ������Ӧ����ʲô
	 * 
	 * @param appType
	 *            :��������
	 * @param fTacheId
	 *            :���ڱ�ʶ
	 * @param stateId
	 *            :״̬
	 * @return ��һ�����̻��ڵ�id�����Ϊ�����ʾ���ݲ�������û����һ����ת
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
	 * �����ύ�Ķ���
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

			// �ж����������ֿ��Ƿ�Ӧ��Ϊ�յ�map��1����Ϊ�գ�0����Ϊ��
			Map inoutMap = null;

			String logId = "";
			for (int i = 0; i < rcOrderList.size(); i++) {

				RcOrderVO rcOrderVO = (RcOrderVO) rcOrderList.get(i);
				String nextTacheIdSql = "";
				int nextTacheId = 0;

				// ��ѯ���������ֿ��Ƿ�ӦΪ�յ�map
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
				// ���������ֿ������
				if (rcOrderVO.getOutStorageId() != null
						&& "".equals(rcOrderVO.getOutStorageId())) {
					rcOrderVO.setOutStorageId(null);
				}
				if (inoutMap != null && inoutMap.get("outStorage") != null
						&& ("0".equals((String) inoutMap.get("outStorage")))) {
					// ���ݶ��������жϣ���������ֿ���Ҫ����������Ϊ����ֿ⣬�����ҪΪ�գ�������Ϊ��
					rcOrderVO.setOutStorageId(null);
				}
				strSql += " out_storage_id=" + rcOrderVO.getOutStorageId()
						+ ",";
				// ��������ֿ������
				if (rcOrderVO.getInStorageId() != null
						&& "".equals(rcOrderVO.getInStorageId())) {
					rcOrderVO.setInStorageId(null);
				}
				if (inoutMap != null && inoutMap.get("inStorage") != null
						&& ("0".equals((String) inoutMap.get("inStorage")))) {
					// ���ݶ��������жϣ��������ֿ���Ҫ����������Ϊ����ֿ⣬�����ҪΪ�գ�������Ϊ��
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
				rcOrderExcVO.setTacheId("2"); // 2Ϊ����;
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
				// ����д����̵���Ϣ���������̵���Ϣ
				insertCheckOrderAgentVO(rcOrderVO);
				// ��¼ͨ������
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
	 * �Ӷ�����Ϣ����ȡ����������Ϣ���������ݱ�,���û�д�������Ϣ���ؿ�ֵ ����Ѿ����ڸü�¼����¸ü�¼
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
		// ��ѯ�Ƿ��Ѿ����ڸü�¼��
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
	// * ���ݲ���id��Ӫ����Դid���ַ�����ѯ�ò��������ֿ���Դ�Ŀ��
	// * @param deptID:������id1,id2,id3����ʽ
	// * @param saleIds��������'id1','id2','id3'����ʽ
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
	 * ���ݲ���id��Ӫ����Դid���ַ�����ѯ�ò��������ֿ���Դ�Ŀ��
	 * 
	 * @param deptID
	 *            :������id1,id2,id3����ʽ 'id1','id2','id3'����ʽ
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

			if (storageId != null && storageId.trim().length() > 0) { // ������洫�����ѡ��Ĳֿ⣬��ֱ���òֿ��ѯ
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

			dao.setFlag(1); // ����Ҫ��ѯ�ֿ�ģ���������
			dao.setFiltered(true);

			// ��ѯʵ�����Ŀ��
			dao.setSQL_SELECT(sql);
			dao.setSQL_SELECT_COUNT(sql_count);
			pm = PageHelper.popupPageModel(dao, "", pi, ps);
		} else { // ʵʱ��̬��ѯ

			// start ��װ��ѯ����
			if (saleIds != null && saleIds.trim().length() > 0) {
				cond += " and a.sales_resource_id in (" + saleIds + ")";
			}

			if (storageId != null && storageId.trim().length() > 0) { // ������洫�����ѡ��Ĳֿ⣬��ֱ���òֿ��ѯ
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

			// end ��װ��ѯ����

			// ��sql���
			StringBuffer sql_buff = new StringBuffer();
			StringBuffer sql_count_buff = new StringBuffer();
			if ("rc_stock".equalsIgnoreCase(tableName)) { // ���������
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

			} else { // ʵ������
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

			System.out.println("��SrStockBo,����queryStockNum��ִ�е�sql_entity��:"
					+ sql);
			System.out.println("��SrStockBo,22����queryStockNum��ִ�е�sql_stock��:"
					+ sql_count);

			dao.setFlag(1); // ����Ҫ��ѯ�ֿ�ģ���������
			dao.setFiltered(true);

			dao.setSQL_SELECT(sql);
			dao.setSQL_SELECT_COUNT(sql_count);

			pm = PageHelper.popupPageModel(dao, "", pi, ps);
		}
		return pm;
	}

	// ///////////////////////////////////////////��ǰ̨�������ۿ��Ľӿ�////////////////////////////////////////

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
	          errInfo = "����ʧ�ܣ�";
	        }
	        map.put("failInfo", errInfo);
	      }
	      else {
	        map.put("resultFlag", "1");
	        map.put("failInfo", "����ɹ�!");
	      }
	      if ( (continueFlag1 != null && "0".equals(continueFlag1)) ||
	          (continueFlag2 != null && "0".equals(continueFlag2))) {
	        map.put("continueFlag", "0");
	      }
	      else {
	        map.put("continueFlag", "1");
	        // ��Ӽ۸���Ϣmap
	      }
	      map.put("price", priceMap);
	    }
	    else if (map1 != null) {
	      map = map1;
	    }
	    else {
	      map = map2;

	    }
	    Debug.print("��SrStockBean��entityHandle����----> retMap=" + map);
	    return map;
	}
	/**
	 * ���ۺ��ͷ���Դ
	 * 
	 * @param saleList
	 *            ����Ҫ���۵ļ���
	 * @param releseList
	 *            ����Ҫ�ͷŲ�������Դ
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
				errInfo = "����ʧ�ܣ�";
			}
			map.put("failInfo", errInfo);
			// ����Ӫ����Դ�۸���Ϣ
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
		Debug.print("��SrStockBo��entityHandle�����еļ۸���Ϣ:---> priceMap="
				+ (Map) map.get("price"));
		return map;
	}

	/**
	 * ������Դʵ�����۵ķ��������ڴ������Դʵ����ռ����Դ�������Ӧ����� �������һ����Դ����ʧ��������׳��쳣��ȫ������(�����ɹ������)���ع�
	 * 
	 * @param list
	 *            ��EntityResourceVO��ɵ�List��ÿ��EntityResourceVO����һ����Ҫ�������Դʵ��
	 * @return Map:resultFlag�����־��1�ɹ���0ʧ�ܣ���continueFlag���������־��1 ���� 0 ����������
	 *         failInfo������Ϣ������continueFlagĿǰֻ����1
	 * @throws Exception
	 *             :��֤�������ΪDAOSystemException��ع����쳣�а����д�����Ϣ
	 */
	public Map entitySale(List list) throws SaleResLogicException {
		Map map = new HashMap();
		String resultFlag = "1";
		String continueFlag = "1";
		StringBuffer failInfo = new StringBuffer();
		int successNum = 0;
		int rtn = -1; // �����Ƿ����Ĭ��Ϊ����,�������Դ�߼���������׳��쳣����֤������ԴҪôȫ������Ҫôȫ��������
		if (list == null || list.size() < 1) {
			Debug.print("��SrStockBo������entitySale(List list)���������Դʵ����Ϊ0");
			resultFlag = "1";
			failInfo.append("�������Դʵ����Ϊ0");
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
									.print("��SrStockBo������entitySale(List list)�����к�Ϊ"
											+ vo.getRescInstance2()
											+ "����Դʵ������ʧ��!");
							resultFlag = "0";
							throw new SaleResLogicException("����ʧ�ܣ����к�Ϊ"
									+ vo.getRescInstance2() + "����Դʵ������ʧ��!");
							// failInfo.append("���к�Ϊ"+vo.getRescInstance2()+"����Դʵ������ʧ��!").append("\n");
						}
					} else {
						// Ŀǰ�ǶԵ���ʵ�����δ���ģ�������Ϊ1����Ӧ��ʵ�������ı亯��Ҳ�ǶԵ����ֿ⴦��������
						vo.setAmount("-1");
						rtn = this.entityAmountChange(vo, 2);
						if (rtn == 0) {
							successNum++;
						} else {
							Debug
									.print("��SrStockBo������entitySale(List list)�����Ӫ����Դ�ڴ���������ʱʧ��!vo:"
											+ vo);
							resultFlag = "0";
							throw new SaleResLogicException(
									"����ʧ�ܣ����Ӫ����Դ�ڴ���������ʱʧ��!");
							// failInfo.append("���Ӫ����Դ�ڴ���������ʱʧ��!").append("\n");
						}
					}
				} else {
					Debug
							.print("��SrStockBo������entitySale(List list):���󣬴�����Ҫ���������Ϊ��!");
					throw new SaleResLogicException("���󣬴�����Ҫ���������Ϊ��!");
				}
			}
			failInfo.append("�ɹ��������ۣ�");
		}
		// �õ�Ӫ����Դ�ļ۸���Ϣ
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
	 * ��Դʵ���ͷŷ��������ڴ������Դʵ�����ͷ���Դ��������Ӧ����� �������һ����Դ����ʧ��������׳��쳣��ȫ������(�����ɹ������)���ع�
	 * 
	 * @param list
	 *            ��EntityResourceVO��ɵ�List��ÿ��EntityResourceVO����һ����Ҫ�������Դʵ��
	 * @return Map:resultFlag�����־��1�ɹ���0ʧ�ܣ���continueFlag���������־��1 ���� 0 ����������
	 *         failInfo������Ϣ������continueFlagĿǰֻ����1
	 * @throws Exception
	 *             :��֤�������ΪDAOSystemException��ع����쳣�а����д�����Ϣ
	 */
	public Map entityRelease(List list) throws SaleResLogicException {
		Map map = new HashMap();
		String resultFlag = "1";
		String continueFlag = "1";
		StringBuffer failInfo = new StringBuffer();
		int successNum = 0;
		int rtn = -1; // �����Ƿ����Ĭ��Ϊ����,�������Դ�߼���������׳��쳣����֤������ԴҪôȫ������Ҫôȫ��������
		if (list == null || list.size() < 1) {
			resultFlag = "1";
			failInfo.append("�������Դʵ����Ϊ0");
			Debug.print("��SrStockBo������entityRelease(List list)���������Դʵ����Ϊ0");
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
									.print("��SrStockBo������entityRelease(List list):��Դʵ��11��"
											+ vo.getRescInstance2() + "����ʧ��!");
							throw new SaleResLogicException("��Դʵ��"
									+ vo.getRescInstance2() + "��������ʧ��!");
							// failInfo.append("��Դʵ��"+vo.getRescInstance2()+"����ʧ��!").append("\n");
						}
					} else {
						vo.setAmount("1");
						rtn = this.entityAmountChange(vo, 1);
						if (rtn == 0) {
							successNum++;
						} else {
							resultFlag = "0";
							Debug
									.print("��SrStockBo������entityRelease(List list):Ӫ����Դ22��"
											+ vo.getSalesRescId() + "����ʧ��!");
							throw new SaleResLogicException(
									"����ʧ�ܣ����Ӫ����Դ�ڴ���������ʱʧ��!");
							// failInfo.append("���Ӫ����Դ�ڴ���������ʱʧ��!").append("\n");
						}
					}
				} else {
					Debug
							.print("��SrStockBo������entityRelease(List list):���󣬴�����Ҫ���������Ϊ��!");
					throw new SaleResLogicException("���󣬴�����Ҫ���������Ϊ��!");
				}
			}
			failInfo.append("�ɹ�������!");
		}
		// �õ�Ӫ����Դ�ļ۸���Ϣ
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
	 * ������Դʵ��״̬
	 * 
	 * @param vo
	 *            :��Ҫ�������ֶΣ�rescInstance2��status��departId
	 * @return ����ɹ�ʱ������ֵΪ0������ʧ��ʱ������ֵΪ-1��
	 */
	private int entityStatueChange(EntityResourceVO vo) {
		if (vo == null || vo.getRescInstance2() == null
				|| vo.getRescInstance2().trim().length() < 1
				|| vo.getStatus() == null || vo.getStatus().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1) {
			if (vo == null) {
				Debug.print("��SrStockBo������entityStatueChange����voΪnull������!");
			} else {
				Debug
						.print("��SrStockBo������entityStatueChange(EntityResourceVO vo)����vo.getRescInstance2():"
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
		// �ڵ�λ��Ȩ�޲鿴�Ĳֿ��в�ѯ��Դʵ��
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
			Debug.print("��SrStockBo������entityStatueChange����ѯΪ��sql:" + sql
					+ "##depart_id:" + vo.getDepartId()
					+ " ##RESOURCE_INSTANCE_CODE:" + vo.getRescInstance2());
			return -1;
		}
		RcEntityVO voTemp = (RcEntityVO) rtnList.get(0);
		String beforeStatus = voTemp.getRescState();
		// ���ʵ����״̬�����ã���ʧ��
		if ("00X".equals(voTemp.getState())) {
			Debug.print("��SrStockBo������entityStatueChange,ʵ��:"
					+ voTemp.getRescInstanceCode() + "״̬Ϊ������:"
					+ voTemp.getState() + "��ʧ��!");
			return -1;
		}
		// ���Ҫռ����Դ��������Դ�Ѿ���ռ�У����������ͷ���Դ������Դ�Ѿ������ͷ�״̬����ʧ��
		if (ParamsConsConfig.RcEntityUseState.equals(vo.getStatus())) {
			if ((beforeStatus == null)
					|| (ParamsConsConfig.RcEntityUseState.equals(beforeStatus))) {
				Debug.print("��SrStockBo������entityStatueChange,ʵ��11:"
						+ voTemp.getRescInstanceCode() + "�ѱ�ռ�ã�����ʧ��!");
				return -1;
			}
		} else if (ParamsConsConfig.RcEntityFreeState.equals(vo.getStatus())) {
			if ((beforeStatus == null)
					|| (ParamsConsConfig.RcEntityFreeState.equals(beforeStatus))) {
				Debug.print("��SrStockBo������entityStatueChange,ʵ��22:"
						+ voTemp.getRescInstanceCode() + "���ǿ��ã��ͷ�ʧ��!");
				return -1;
			}
		}
		// ������Դռ��״̬
		voTemp.setRescState(vo.getStatus());
		boolean rtnBool = entityDao.update(voTemp.getRescInstanceId(), voTemp);
		if (!rtnBool) {
			Debug.print("��SrStockBo������entityStatueChange,������Դռ��״̬ʧ�ܣ�voTemp:"
					+ voTemp);
			return -1;
		}
		// ���������־��¼
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
		Debug.print("��SrStockBo������entityStatueChange,�����������־voInsert:"
				+ voInsert);
		return 0;
	}

	/**
	 * ��Ҫ����ʵ���Ĳֿ������
	 * 
	 * @param vo
	 *            ����Ҫ�������ֶΣ�salesRescId��departId��amount
	 * @param changeType
	 *            ���������ͣ�1��������2�Ǽ���
	 * @return ����ɹ�ʱ������ֵΪ0������ʧ�ܻ�����������0ʱ������ֵΪ-1��
	 */
	private int entityAmountChange(EntityResourceVO vo, int changeType) {
		Debug.print("��SrStockBo������entityAmountChange����changeType:" + changeType
				+ "||vo:" + vo);
		if (vo == null || vo.getSalesRescId() == null
				|| vo.getSalesRescId().trim().length() < 1
				|| vo.getDepartId() == null
				|| vo.getDepartId().trim().length() < 1
				|| vo.getAmount() == null || vo.getAmount().trim().length() < 1) {
			Debug.print("��SrStockBo������entityAmountChange����ȱ�٣�����!");
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
		Debug.print("��SrStockBo������entityAmountChange��ѯ����cond:" + cond
				+ "||��ѯ���:" + rtnList);
		if ((rtnList == null || rtnList.size() < 1 || rtnList.get(0) == null)
				&& changeType == 2) {
			Debug.print("��SrStockBo������entityAmountChange��ѯ���������rtnList:"
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
					// �����������Դ��������1
					int amount = Integer.parseInt(voTemp.getStockAmount())
							+ Integer.parseInt(vo.getAmount());
					stockDao.updateAmount(storageId, vo.getSalesRescId(),
							String.valueOf(amount));
					needExtra = false;
					break;
				} else if (changeType == 2) {
					// ����Ǽ������ʣ���쿴�����Ƿ����amount�����������ֱ�Ӽ������������С����ѭ����һ���ֿ�
					// ������Ϊһ�δ��������Ϊ1�����Բ����ж�ֿ������ݼ���
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
			// �����ǶԲֿ��������������û�вֿ���Ϣ�����ش�����Ϣ
			// ˵��û��һ���ֿ���Ӧ���ʵ�����������ۼ����������ڴ˷���ʧ�ܡ�
			// ���Ҫ���жԲֿ������������ۼ���������Ҫ����
			Debug.print("��SrStockBo������entityAmountChange��needExtra:"
					+ needExtra + "||changeType:" + changeType);
			return -1;
		}
		// ���������־��¼
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
		Debug.print("��SrStockBo������entityAmountChange,���������־voInsert:"
				+ voInsert);
		return 0;
	}

	/**
	 * ����������Դ�ͳ�����Դ�Ĵ�����ʾ��Ϣ looks strange,but be orderd to do it.
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
		// ��������������Ϣ
		if (saleList != null && saleList.size() > 0) {
			for (int i = 0; i < saleList.size(); i++) {
				EntityResourceVO voTemp = (EntityResourceVO) saleList.get(i);
				if (voTemp != null) {
					if (voTemp.getRescInstance2() != null
							&& voTemp.getRescInstance2().trim().length() > 0) {
						buffer1
								.append("��Դʵ��" + voTemp.getRescInstance2()
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
				mess1 = buffer1.toString() + "����ʧ�ܣ�" + "\n";
			}
		}
		// ����ȡ����������Ϣ
		if (releseList != null && releseList.size() > 0) {
			for (int i = 0; i < releseList.size(); i++) {
				EntityResourceVO voTemp2 = (EntityResourceVO) releseList.get(i);
				if (voTemp2 != null) {
					if (voTemp2.getRescInstance2() != null
							&& voTemp2.getRescInstance2().trim().length() > 0) {
						buffer2.append("��Դʵ��" + voTemp2.getRescInstance2()
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
				mess2 = buffer2.toString() + "����ʧ�ܣ�";
			}
		}
		return mess1 + mess2;
	}

	/**
	 * ���ݴ�����账�����Դ���õ���ЩӪ����Դ��Ӧ�ĵ�һ�۸��¼��Ϣ
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
					// ����rescInstance2��ѯ����Ӫ����Դ
					entityVO = entityDao.findByEntityCode(voTemp
							.getRescInstance2());
					if (entityVO != null && entityVO.getSalesRescId() != null
							&& entityVO.getSalesRescId().length() > 0) {
						// �����������ϣ�ֻ��Ӫ����Դid��ͬ
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

	// ////////////////////////////////////////////ͳ�ƿ��Ĵ���///////////////////////////////////////

	/**
	 * ��ѯ������־
	 * 
	 * @param map
	 *            ,�����Ĳ����У�departIds��salesRescIds��rescInstance2��beginDate��endDate
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
	 * ͳ���ն��������
	 * 
	 * @param map
	 *            ,�����Ĳ����У�departIds��salesRescIds��beginDate��endDate
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
		// ����Ҫ��ѯ��������ѯ���
		// System.out.println(sqlBasic_oracle);
		dao.setSQL_SELECT(sqlBasic_oracle);
		dao.setSQL_SELECT_COUNT(count_oracle);
		dao.setFlag(5);
		pm = PageHelper.popupPageModel(dao, "", pi, ps);

		return pm;
	}

	/**
	 * ͳ����Ʒ�������
	 * 
	 * @param map
	 *            ,�����Ĳ����У�departIds��salesRescIds��beginDate��endDate
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
		// ����Ҫ��ѯ��������ѯ���
		dao.setSQL_SELECT(sqlBasic);
		dao.setSQL_SELECT_COUNT(count_sql);
		dao.setFlag(1);
		pm = PageHelper.popupPageModel(dao, "", pi, ps);

		return pm;
	}

	// ����
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
	 * ����Ӫ����Դid��ѯ�������ļ����ʵ�����Լ�
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
	 * ���ݶ��������ж��������͵Ķ��������ǰ�Ļ����Ƿ��ǳ��⻷�ڡ� ����û����ⲽ���������Ϊǰһ���ǳ���
	 * 
	 * @param appType
	 *            String
	 * @return boolean true:�ǳ��⻷�ڣ�false:���ǳ��⻷��
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
			// ���û�в�ѯ���������ļ�¼��˵�������Ǳ��ϻ��˻��������Ϊtrue
			result = true;
		}
		return result;
	}

	/**
	 * �����߼����ݰ���: 1. ����ǳ���������������ֱ��ͨ�������еļ����������� 2.
	 * ������ǰ�Ĳ��費�ǳ���,����֤����ʼ�������ֹ����֮���Ƿ��Ѿ�����ʵ����������ڣ������ 3.
	 * ������ǰ�ǳ���˵��ʵ���Ѿ�����,��֤��ʼ����ֹ����֮����Ѵ��ڵ�ʵ�������Ƿ��Ҫ������һ�£����һ�»�Ҫ��֤�Ƿ��в�����״̬��ʵ��
	 * 
	 * @param vo
	 *            RcOrderVO
	 *            Map:errorCode:-1:ȱ�ٲ�����ʧ�ܣ�1:�����û�г������ⶩ��ʱ����Щʵ���ڲֿ����Ѿ����ڣ�
	 *            2:����ʵ��������ָ����Ҫ������������ͬ;3:ָ��������ʵ��������Ч��ʵ�� Map:info:������Ϣ
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
			map.put("info", "ȱ����֤��������!");
			return map;
		}
		String result = "1";
		String info = "";
		String actAmount = vo.getActAmount();
		// ����ǻ���������֤
		if ("b".equals(vo.getStateId())) {
			map.put("result", "1");
			map.put("errorCode", "");
			map.put("info", "����ʱ��������֤");
			return map;
		}
		// �ж��Ƿ���ͨ���ļ��ϴ���ʽ��ʵ������ģ������ִ���ʽ
		RcOrderListDAO listDao = SrDAOFactory.getInstance().getRcOrderListDAO();
		List list = listDao.findByCond(" ORDER_ID=" + vo.getOrderId());

		// ����ǳ�����������Ƿ���ָ��һ������ʵ���ķ�ʽ
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
				// info = "�����ϴ������嵥��������ʼ��ֹ�������ַ�ʽ��ѡ��һ������ʵ���ķ�ʽ!";
				// } else {
				// BigDecimal actAmountNum = new BigDecimal(actAmount);
				// BigDecimal resBCodeNum = new BigDecimal(resBCode);
				// BigDecimal resECodeNum = new BigDecimal(resECode);
				// BigDecimal subNum = (resECodeNum.subtract(resBCodeNum))
				// .add(new BigDecimal("1"));
				// if (actAmountNum.compareTo(subNum) != 0) {
				// result = "0";
				// info = "��ʼ�������ֹ����֮���ʵ�������������ʵ�����������!";
				// }
				// }
			} else {
				if (list.size() != Integer.parseInt(actAmount)) {
					result = "0";
					info = "�ļ��嵥��ʵ�������������ʵ�����������!";
				} else {
					result = "1";
					info = "��ͨ�������嵥�б�������ö�����ʵ��!";
				}
			}
			if ("0".equals(result)) { // �������׶���֤ʧ�ܣ��򷵻�
				map.put("result", result);
				map.put("errorCode", "");
				map.put("info", info);
				return map;
			}
			// �������״̬�������������
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
		} else if (RcOrderOperStateVO.Tache_inStorage.equals(vo.getTacheId())) { // ������ڻ��������
			String state = ParamsConsConfig.RcEntityStateValide;
			// ��ѯǰһ���Ƿ��ǳ���
			boolean isOut = this.isOutBeforeInStorage(vo.getAppType());
			if (isOut)
				state = ParamsConsConfig.RcEntityStateInValide; // ���ǰһ�������ǳ��⻷�ڣ�������Щʵ���Ƿ�����;״̬
			// �������״̬��������ⶼ��Ҫ��������
			if (list != null && list.size() > 0) {
				map = this.checkEntityInStorage_importFile(vo, state);
			} else {
				map = this.checkEntityInStorage_serialCode(vo, state);
			}
		}
		return map;
	}

	/**
	 * �������ļ��ϴ���ʵ��������ֿ��е�ʵ���Ƿ���������
	 * 
	 * @param vo
	 *            RcOrderVO
	 * @return Map:result:0ʧ�ܣ�1�ɹ�
	 *         Map:errorCode:-1:ȱ�ٲ�����ʧ�ܣ�1:�����û�г������ⶩ��ʱ����Щʵ���ڲֿ����Ѿ����ڣ�
	 *         2:����ʵ��������ָ����Ҫ������������ͬ;3:ָ��������ʵ��������Ч��ʵ�� Map:info:������Ϣ
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
		String stateTip = "�ڿ�";
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
			map.put("info", "��Ҫ��֤�Ĳ�������!");
			return map;
		}
		if (ParamsConsConfig.RcEntityStateInValide.equals(state)) {
			stateTip = "��;";
		}
		// ����Ӧ�ò����ı�(ʵ�������롢SIM)
		String tableName = this.setEntityTableName(ordervo);
		// �����������Ͳ�ѯ����ǰ�Ĳ����ǲ������
		boolean isOut = true;
		if (!"3".equals(tacheId)) {// ���
			isOut = this.isOutBeforeInStorage(appType);
		}
		if (isOut) {
			if (outStorageId == null || outStorageId.trim().length() < 1) {
				map.put("result", "0");
				map.put("errorCode", "-1");
				map.put("info", "ȱ�������ֿ��������!");
				return map;
			}
		}
		// ��ѯ��ʼ�������ֹ����֮���ʵ������
		long actAmountNum = Long.parseLong(actAmount);
		String sqlNum = "select count(*) as COL_COUNTS��from " + tableName
				+ " where sales_resource_id=" + salesRescId + " and state='"
				+ state + "'";
		if (isOut) {
			sqlNum += " and storage_id=" + outStorageId; // �����ǰһ���ǳ��⣬����Ҫ���������ֿ���ж�����
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
		if (!isOut) { // ������ǰ�Ĳ��費�ǳ���,����֤�ϴ��ļ��б��е�ʵ�������Ƿ��Ѿ�����
			if (entitynum > 0) {
				map.put("result", "0");
				map.put("errorCode", "1");
				map.put("info", "�ϴ��ļ��б��е���Դʵ������Ӧ�Ĳֿ����Ѿ�����,���ʧ��!");
			} else {
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		} else { // ������ǰ�ǳ���˵��ʵ���Ѿ�����,��֤�ϴ��ļ�������Ѵ��ڵ�ʵ�������Ƿ��Ҫ������һ�£����һ�»�Ҫ��֤�Ƿ��в�����״̬��ʵ��
			if (entitynum != actAmountNum) {
				map.put("result", "0");
				map.put("errorCode", "2");
				if (entitynum < actAmountNum) {
					map.put("info", "ָ���ֿ���" + stateTip + "״̬��ʵ���������㣬�����ʧ��!");
				} else {
					map.put("info", "�ֿ���ָ��Ҫ�����" + stateTip
							+ "ʵ����������ʵ��Ҫ����������������ʧ��!");
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
	 * �����߼����ݰ���: 1. ����ǳ��������������һ�����ǽ������������ֱ��ͨ�������еļ����������� 2.
	 * ������ǰ�Ĳ��費�ǳ���,����֤����ʼ�������ֹ����֮���Ƿ��Ѿ�����ʵ����������ڣ������ 3.
	 * ������ǰ�ǳ���˵��ʵ���Ѿ�����,��֤��ʼ����ֹ����֮����Ѵ��ڵ�ʵ�������Ƿ��Ҫ������һ�£����һ�»�Ҫ��֤�Ƿ��в�����״̬��ʵ��
	 * 
	 * @param ordervo
	 *            RcOrderVO
	 * @return Map:result:0ʧ�ܣ�1�ɹ�
	 *         Map:errorCode:-1:ȱ�ٲ�����ʧ�ܣ�1:�����û�г������ⶩ��ʱ����Щʵ���ڲֿ����Ѿ����ڣ�
	 *         2:����ʵ��������ָ����Ҫ������������ͬ;3:ָ��������ʵ��������Ч��ʵ�� Map:info:������Ϣ
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
		String stateTip = "�ڿ�";
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
			map.put("info", "��Ҫ��֤�Ĳ�������,��ȷ����д����ʼ����ֹ����!");
			return map;
		}
		if (ParamsConsConfig.RcEntityStateInValide.equals(state)) {
			stateTip = "��;";
		}
		// ����Ӧ�ò����ı�(ʵ�������롢SIM)
		String tableName = this.setEntityTableName(ordervo);
		// �����������Ͳ�ѯ����ǰ�Ĳ����ǲ������
		SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
		boolean isOut = true;
		if (!"3".equals(tacheId)) {
			isOut = this.isOutBeforeInStorage(appType);
		}
		if (isOut) {
			if (outStorageId == null || outStorageId.trim().length() < 1) {
				map.put("result", "0");
				map.put("errorCode", "-1");
				map.put("info", "ȱ�������ֿ��������!");
				return map;
			}
		}
		// ��ѯ��ʼ�������ֹ����֮���ʵ������
		long actAmountNum = Long.parseLong(actAmount);
		String sqlNum = "select count(*) as COL_COUNTS��from " + tableName
				+ " where sales_resource_id=" + salesRescId + " and state='"
				+ state + "'";
		if (isOut) {
			sqlNum += " and storage_id=" + outStorageId; // �����ǰһ���ǳ��⣬����Ҫ���������ֿ���ж�����
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
		if (!isOut) { // ������ǰ�Ĳ��費�ǳ���,����֤����ʼ�������ֹ����֮���Ƿ��Ѿ�����ʵ��
			if (entitynum > 0) {
				map.put("result", "0");
				map.put("errorCode", "1");
				map.put("info", "�����Ѿ�������ʼ�������ֹ����֮���ʵ��,���ʧ��!");
			} else {
				map.put("result", "1");
				map.put("errorCode", "");
				map.put("info", "");
			}
		} else { // ������ǰ�ǳ���˵��ʵ���Ѿ�����,��֤��ʼ����ֹ����֮����Ѵ��ڵ�ʵ�������Ƿ��Ҫ������һ�£����һ�»�Ҫ��֤�Ƿ��в�����״̬��ʵ��
			if (entitynum != actAmountNum) {
				map.put("result", "0");
				map.put("errorCode", "2");
				if (entitynum < actAmountNum) {
					map.put("info", "ָ���ֿ�����ʼ����ֹ����֮���" + stateTip
							+ "ʵ���������㣬�����ʧ��!");
				} else {
					map.put("info", "ָ���ֿ�����ʼ����ֹ����֮���" + stateTip
							+ "ʵ��������ָ����ʵ�������࣬�����ʧ��!");
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
	 * ��Դʵ������ֱ�ӱ��ϻ��˿�Ĳ���������Դʵ����¼���뵽�������У�ɾ����Դʵ��
	 * ��rc_order_list���в�ѯ���������ʵ���������ʼ��ֹ����ȷ�� ��������������Դ��ͬ(rc_entity������SIM��)
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
			// ɾ��ʵ������sql���(���Գ���ʵ����ĺ����SIM����Դ)
		}
		String sql_del = " delete from " + tableName + " where ";
		int rows = 0;
		// ���ݶ�ʵ����Դ�Ĵ���ʽ��ͬ(�ļ��ϴ���ʽ����ʼ��ֹ������ʽ)�����ֱ���
		// ����ɾ��ʵ��������(���Գ���ʵ����ĺ����SIM����Դ)
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
			// ����ɾ��ʵ������Ϣ����
			sql_logInsert.append(cond0);
			sql_logAttrInsert += cond1;
			System.out.println("ɾ��ʵ�����ԣ�" + sql_logInsert.toString());

			comDao.excute(sql_logInsert.toString());
			// ���ʵ����rc_entity����ɾ��ʵ��������Ϣ
			if ("rc_entity".equalsIgnoreCase(tableName.trim())
					|| "rc_entity2".equalsIgnoreCase(tableName.trim())) {
				comDao.excute(sql_logAttrInsert);
				String del_attr = "delete from rc_entity_attr a where  "
						+ cond1;
				comDao.excute(del_attr);
			}
			// ɾ��ʵ��(����ʵ�������롢SIM������Դ)
			sql_del += cond0;
			System.out.println("ɾ������ʵ����" + sql_del);

			rows = comDao.excute(sql_del);
		} else {

			// ���ϵ�Ӫ����Դ��ID�Ͳֿ������
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
						// ����ɾ��ʵ������Ϣ����//ͬʱ����ʵ�������Ե�attr_log��// ɾ��ʵ������
						logDao.insertFromEntity(cond + tmp);
						rows = (int) entityDao.deleteByCond(cond + tmp);
					} else { // ����rc_entity��Ĳ���
						// ����ɾ������ʵ������Ϣ����
						comDao.excute(sql_logInsert.toString() + cond + tmp);
						// ɾ������ʵ����Ϣ
						rows = comDao.excute(sql_del + cond + tmp);
					}
					tmp = "";
				}

			}
		}
		Debug.print("SrStockBo:outEntityToWaste:" + sql_logInsert);
		Debug.print("SrStockBo:outEntityToWaste:" + sql_del);
		Debug.print("SrStockBo��outEntityToWaste������ɾ����" + rows + "����¼");
		return rows;
	}

	/**
	 * ��Դʵ������������ǰһ���費�ǳ��⡣ֱ�Ӳ���ʵ���ʵ������
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
			// �����������ʵ�����ԣ���Чʱ���ʧЧʱ�����ڴ�����
			if (vo.getEntityVO() != null) {
				if (vo.getEntityVO().getEffDate() != null) {
					effDate = vo.getEntityVO().getEffDate();
				}
				if (vo.getEntityVO().getExpDate() != null) {
					expDate = vo.getEntityVO().getExpDate();
				}
			}
			// ��ѯ����Ӫ����Դ�ļ���ģ��ĵȼ�����
			List levelList = null;
			RcLevelDefDAO levelDao = SrDAOFactory.getInstance()
					.getRcLevelDefDAO();
			if (vo.getFamilyId() != null
					&& vo.getFamilyId().trim().length() > 0) {
				levelList = levelDao.findByCond(" family_id="
						+ vo.getFamilyId(), null);
			}
			List attrList = vo.getAttrList();
			// ��ʼ����ֹ����
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
				.getHandleType())) {// ������߼���Ҫ����������ַ����ж�ǰ׺��ͺ�׺�롣
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
			// ȷ��ʵ���ļ���
			this.setEntityLevel(entityVO, levelList);
			// ����ʵ��
			entityVO = entityBo.insertRcEntity(entityVO, tableName);
			count++;
			// �����Դ�������б�Ϊ�գ������ʵ������
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
	 * ���ݴ���ļ��𼯺�ȷ������ʵ���ļ���,�˴�Ҫ��֤�����levelList�еĵȼ��ǰ����ȼ���С����˳�����е�(important)
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

	// //////////////////////////���������/////////////////////////////////////////
	/**
	 * ���ݳ���ֿ��ѯ��������Ϣ
	 * 
	 * @param map
	 *            Map:�������outStorageId��inStorageId
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
		System.out.println("��SrStockBo��qryAgentByStorage��ѯ������sql:" + sql);
		return pm;
	}

	/**
	 * ����������ѯ����Ѻ����Ϣ
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
	 * ���ݶ�����Ϣ����Ѻ�����˲���
	 * 
	 * @param vo
	 *            ��vo�е�orderId��tacheId��stateId��operId��acceptId��departId����Ϊ��
	 * @return -1����������д��󣬿�����ȱ�����ϲ���֮һ 1:�ɹ���ɲ������� 2:�ö����Ѿ���������
	 *         3:�ö����Ļ����Ѿ��ı䣬�������ٲ��� 5:��ת����ʱʧ��
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
			// ��vo��������Ҫ���µĲ��������ղ�ѯ��tempvo���ٸ���vo��Ϊ�˸�����Щ�ֶ�
			tempvo2.setStateId(vo.getStateId());
			tempvo2.setComments(vo.getComments());
			tempvo2.setOperId(vo.getOperId());
			tempvo2.setDepartId(vo.getDepartId());
			tempvo2.setAcceptId(vo.getAcceptId());
			tempvo2.setRealPrice(vo.getRealPrice());
			tempvo2.setRescPrice(vo.getRescPrice());
			tempvo2.setDiscount(vo.getDiscount());
			vo = tempvo2;
			// ����Ѻ����
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
			// ������תģ�飺������ת������ͬʱ�Ѷ��������Ϣ����
			vo = this.doFlowControl(vo);
			if (vo == null) {
				return "5";
			}
			// ���¹���ģ����־,��¼RC_order��ı䶯
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

	// /////////////////////// ͳ�ƴ������Ϣ�ķ��� //////////////////////////////
	/**
	 * ��Դʵ��������־��ѯ������״̬Ϊ����
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
	 * ͳ�ƴ��������
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

		// ��Ӫ����Դ��ѯ
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
		// �Թ������ؽ������⻯������b.depart_id�滻��b.party_id;
		// if(map!=null && map.get("provId")!=null &&
		// map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)){
		// sql_temp = sql_temp.replaceAll("b.depart_id", "b.party_id");
		// }

		// ���ֿ��ѯ
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

		// ��Ӫ����Դ��ѯ
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

		// �Թ������ؽ������⻯������b.depart_id�滻��b.party_id;
		// if(map!=null && map.get("provId")!=null &&
		// map.get("provId").equals(ParamsConsConfig.PROV_ID_GX)){
		// sql_temp = sql_temp.replaceAll("b.depart_id", "b.party_id");
		// }

		// ���ֿ��ѯ
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
	 * ���ݲ��Ų���Ӫҵ���������Ҷ�Ӧ��Ӫ����Դ�ļ۸񣬳������õ���ֵ
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
	 * ����Ӫ����Դid���������Ӧ�ı���
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
		// start ��װ��ѯ����
		cond = " and a.storage_id in(" + downStorageIds + ")";
		if (saleIds != null && saleIds.trim().length() > 0) {
			cond += " and a.sales_resource_id in (" + saleIds + ")";
		}
		// end ��װ��ѯ����

		// ��sql���
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

		Debug.print("��SrStockBo,����queryDownStockNum��ִ�е�sql��:" + sql);
		Debug
				.print("��SrStockBo,����queryDownStockNum��ִ�еĲ�ѯsqlcount��:"
						+ sqlcount);
		PageModel pm = new PageModel();

		// ����Ҫ��ѯ��������ѯ���
		dao.setSQL_SELECT(sql);
		dao.setSQL_SELECT_COUNT(sqlcount);
		dao.setFlag(1); // ����Ҫ��ѯ�ֿ�ģ���������
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
	 * ��ȡ�ն˴�������Ϣ
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
				&& "104".equals(salesRescVO.getFamilyId().trim())) {// �ն�
			isEntity = "1";
		}
		return new String[] { agentId, isEntity };
	}

	/**
	 * ���ü��Žӿڣ��洢�������ն���Ϣ
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
	 * �ն�ͳ��ƽ���
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
    	System.out.println("��ѯ����sqlΪ===============");
    	System.out.println(sql);
 
    	
       	
 	   Date currentTime = new Date();
 	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 	   String dateString = dateFormat.format(currentTime);//����
 	   SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
 	   String monthString = monthFormat.format(currentTime);//����
 	   SimpleDateFormat yeatFormat = new SimpleDateFormat("yyyy");
 	   String yearString = yeatFormat.format(currentTime);//����
 	   
 	if( ("1".equals(sheetType)&& date.equals(dateString)) //�ձ�����ǰ��
 			||("2".equals(sheetType)&& date.substring(0,7).equals(monthString)) //�±�����ǰ��
 			||("3".equals(sheetType)&& date.substring(0,4).equals(yearString))){//�걨����ǰ��
 		//Ϊ�˲���
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
    	List list1 = qryNewCreate(dateString,lanId,countType);//��������
    	List list3 = qryNewCreateNoDif(dateString,lanId);//��������
    	List list2 = qryNewSale(dateString,lanId,countType);//��������
    	list = addList(list,list1,"addnum");//���µ��������ֶ�
    	list = addList2(list,list3,"stockcurr");//���µ��ڿ���ֶ�
    	list = delList(list,list2);//��������
 	}
    	return countDif(list);
    	
	}
	//��������������crmϵͳ�����ն�ϵͳ����
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

	//����������������crmϵͳ�����ն�ϵͳ����
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
	//���µ����������ֶΣ�list1��  list2��ʵʱ��������
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
					if(vo.getLanId()!=null && vo.getLanId().endsWith((String) hm2.get("lanid"))){//���
						if(type.equals("addnum"))
							vo.setAddNum(String.valueOf(strToInt(vo.getAddNum())+strToInt((String)hm2.get("addnum"))));
						flag=1;
						break;
					}
					}
				if(flag==0){
					exList.add(hm2);
				}
				//exList.add(hm2);//����������
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
	
	//���µ��ڿ���ֶΣ�list1��  list2��ʵʱ��������
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
					if(vo.getLanId()!=null && vo.getLanId().endsWith((String) hm2.get("lanid"))){//���
							vo.setStockNumCurr(String.valueOf(strToInt(vo.getStockNumCurr())+strToInt((String)hm2.get("addnum"))));
						break;
					}
					}
			}
		}

		return volist1;
	}
	//list1��  list2��ʵʱ�����۵�
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
	//�������
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
	//���ַ����������
	public int strToInt(String str){
		if(str==null ||str.length()<=0){
			return 0;
		}
		return Integer.parseInt(str);
	}
}
