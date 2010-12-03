package com.ztesoft.vsop.ordermonitor.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.WoOrderInfoDAO;
import com.ztesoft.vsop.ordermonitor.webservice.client.ActiveSVClient;

public class WoOrderInfoBO extends DictAction {
	public boolean insertWoOrderInfo(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map WoOrderInfo = (Map) param.get("WoOrderInfo");

		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		boolean result = dao.insert(WoOrderInfo);
		return result;
	}

	public boolean updateWoOrderInfo(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map WoOrderInfo = (Map) param.get("WoOrderInfo");
		String keyStr = "order_id";
		Map keyCondMap = Const.getMapForTargetStr(WoOrderInfo, keyStr);
		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		boolean result = dao.updateByKey(WoOrderInfo, keyCondMap);

		return result;
	}

	public PageModel searchWoOrderInfoData(DynamicDict dto) throws Exception {
		// 条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "user_type")) {
			whereCond.append(" and t.prod_id = ? ");
			para.add(Const.getStrValue(param, "user_type"));
		}
		if (Const.containStrValue(param, "prod_no")) {
			whereCond.append(" and t.nbr = ? ");
			para.add(Const.getStrValue(param, "prod_no"));
		}
		if (Const.containStrValue(param, "lan_code")) {
			whereCond.append(" and t.area_id = ? ");
			para.add(Const.getStrValue(param, "lan_code"));
		}
		if (Const.containStrValue(param, "in_start_time")) {
			String in_start_time = Const.getStrValue(param, "in_start_time");
			in_start_time += " 00:00:00";
			whereCond.append(" and t.receive_date > to_date('" + in_start_time
					+ "',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if (Const.containStrValue(param, "in_end_time")) {
			String in_end_time = Const.getStrValue(param, "in_end_time");
			in_end_time += " 23:59:59";
			whereCond.append(" and t.receive_date < to_date('" + in_end_time
					+ "',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if (Const.containStrValue(param, "order")) {
			whereCond.append(" and t.order_id = ? ");
			para.add(Const.getStrValue(param, "order"));
		}
		if (Const.containStrValue(param, "sys")) {
			String sys = Const.getStrValue(param, "sys");
			whereCond.append(" and t.int_sys_id in ( " + sys + ")");
		}
		if (Const.containStrValue(param, "deal_start_time")) {
			String deal_start_time = Const
					.getStrValue(param, "deal_start_time");
			deal_start_time += " 00:00:00";
			whereCond.append(" and t.finish_date > to_date('" + deal_start_time
					+ "',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if (Const.containStrValue(param, "deal_end_time")) {
			String deal_end_time = Const.getStrValue(param, "deal_end_time");
			deal_end_time += " 23:59:59";
			whereCond.append(" and t.finish_date < to_date('" + deal_end_time
					+ "',"+DatabaseFunction.getDataFormat(2)+") ");

		}
		if (Const.containStrValue(param, "state")) {
			String state = Const.getStrValue(param, "state");
			state = state.replaceAll(",", "','");
			state = "'" + state + "'";
			whereCond.append(" and t.state_code in ( " + state + ")");
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// 调用DAO代码
		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 查询工单批次状态
	 * 
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public String getBatchList(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		Map order = dao.findByPrimaryKey(keyCondMap);
		if (order.isEmpty())
			return "";
		String control_str = Const.getStrValue(order, "control_str");
		String order_id = Const.getStrValue(order, "order_id");
		String[] batch = control_str.split(";");
		StringBuffer sbf = new StringBuffer("<items>");
		for (int i = 0; i < batch.length; i++) {
			String batch_str = batch[i];
			batch_str = "'" + batch_str.replaceAll(",", "','") + "'";
			sbf.append("<item batch='激活批次" + (i + 1) + "' state=''>");
			List list = dao.getBatchList(batch_str, order_id);
			for (int j = 0; j < list.size(); j++) {
				Map data = (Map) list.get(j);
				String state = "激活失败";
				String name = Const.getStrValue(data, "name");
				String state_code = Const.getStrValue(data, "state");
				if (null == state_code || "".equals(state_code))
					state = "未激活";
				else if ("10I".equals(state_code))
					state = "激活中";
				else if ("10F".equals(state_code) || "10M".equals(state_code))
					state = "已激活";

				sbf.append("<item batch='" + name + "' state='" + state
						+ "'></item>");
				;
			}
			sbf.append("</item>");
		}
		sbf.append("</items>");
		return sbf.toString();
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoOrderInfoById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findWoOrderInfoByCond(DynamicDict dto) throws Exception {
		// 条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// 组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// 调用DAO代码
		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteWoOrderInfoById(DynamicDict dto) throws Exception {
		// 调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		WoOrderInfoDAO dao = new WoOrderInfoDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	/**
	 * 人工回单
	 * 
	 * @param order_ids
	 * @return list
	 * @throws Exception
	 */
	public List woOrderFeedBackById(DynamicDict dto) throws Exception {
		String activeType = "0";
		String helpMsg = "";
		String strTemp = "";
		String searchStr = "<ResultCode>0</ResultCode>";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;
		Map map = null;
		Map param = Const.getParam(dto);
		String order_ids = Const.getStrValue(param, "order_id");
		String ruleStr[] = order_ids.split(",");
		ActiveSVClient asvClient = ActiveSVClient.getInstance();

		List resultList = new ArrayList();

		for (int i = 0; i < ruleStr.length; i++) {
			helpMsg = "<order_id>" + ruleStr[i] + "</order_id>";
			// 根据order_id 循环调用人工回单的方法
			try{
			map = new HashMap();
			map.put("orderId", ruleStr[i]);
			strTemp = asvClient.active(activeType, "", helpMsg);
			successFlag = strTemp.indexOf(searchStr);
			// 在返回结果中查找"<ResultCode>0</ResultCode>"，判断操作是否成功
			if (!(successFlag == -1)) {
				map.put("resultCode", "操作成功");
				map.put("resultMsg", "");
			} else {
				map.put("resultCode", "操作失败");
				beginIndex = strTemp.indexOf("<ResultMsg>")
						+ "<ResultMsg>".length();
				endIndex = strTemp.indexOf("</ResultMsg>");
				map.put("resultMsg", strTemp.subSequence(beginIndex, endIndex));
			}
			}catch (Exception e){
				map.put("resultCode", "操作失败");
				map.put("resultMsg", e.getMessage());
			}finally{
				resultList.add(map);
			}
		}
		return resultList;
	}
}
