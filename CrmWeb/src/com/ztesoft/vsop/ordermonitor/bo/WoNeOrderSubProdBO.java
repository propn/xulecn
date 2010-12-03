package com.ztesoft.vsop.ordermonitor.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderSubProdDAO;
import com.ztesoft.vsop.ordermonitor.webservice.client.ActiveSVClient;

public class WoNeOrderSubProdBO extends DictAction {
	public boolean insertWoNeOrderSubProd(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map WoNeOrderSubProd = (Map) param.get("WoNeOrderSubProd");

		WoNeOrderSubProdDAO dao = new WoNeOrderSubProdDAO();
		boolean result = dao.insert(WoNeOrderSubProd);
		return result;
	}

	public boolean updateWoNeOrderSubProd(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map WoNeOrderSubProd = (Map) param.get("WoNeOrderSubProd");
		String keyStr = "ne_sub_id";
		Map keyCondMap = Const.getMapForTargetStr(WoNeOrderSubProd, keyStr);
		WoNeOrderSubProdDAO dao = new WoNeOrderSubProdDAO();
		boolean result = dao.updateByKey(WoNeOrderSubProd, keyCondMap);

		return result;
	}

	public PageModel searchWoNeOrderSubProdData(DynamicDict dto)
			throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "ne_order_id")) {
			whereCond.append(" and ne_order_id = ? ");
			para.add(Const.getStrValue(param, "ne_order_id"));
		}
		whereCond.append(" order by ne_sub_id ");
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// ����DAO����
		WoNeOrderSubProdDAO dao = new WoNeOrderSubProdDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getWoNeOrderSubProdById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		WoNeOrderSubProdDAO dao = new WoNeOrderSubProdDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findWoNeOrderSubProdByCond(DynamicDict dto) throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// ����DAO����
		WoNeOrderSubProdDAO dao = new WoNeOrderSubProdDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteWoNeOrderSubProdById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		WoNeOrderSubProdDAO dao = new WoNeOrderSubProdDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	/**
	 * ��ֵ��Ʒ�˹��ص�
	 * 
	 * @param ne_order_id
	 * @return String
	 * @throws Exception
	 */
	public String woNeOrderSubProdFeedBack(DynamicDict dto) throws Exception {
		String activeType = "4";
		String helpMsg = "";
		String strTemp = "";
		String searchStr = "<ResultCode>0</ResultCode>";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;
		String result = "";
		Map param = Const.getParam(dto);
		String ne_order_id = Const.getStrValue(param, "ne_order_id");
		String product_code = Const.getStrValue(param, "product_code");

		ActiveSVClient asvClient = ActiveSVClient.getInstance();
		helpMsg = "<ne_order_id>" + ne_order_id + "</ne_order_id>";
		helpMsg =helpMsg+"<sub_product_code>" + product_code + "</sub_product_code>";
		// ���ú�̨����ֵ��Ʒ�˹��ص�
		strTemp = asvClient.active(activeType, "", helpMsg);
		if(null!=strTemp){
			successFlag = strTemp.indexOf(searchStr);
			beginIndex = strTemp.indexOf("<ResultMsg>")+ "<ResultMsg>".length();
			endIndex = strTemp.indexOf("</ResultMsg>");
			if (!(successFlag == -1)) {
				result = strTemp.substring(beginIndex, endIndex);
			} else {
				result = "��ֵ��Ʒ�˹��ص�������ʧ��:" + strTemp.substring(beginIndex, endIndex);
			}
		}
		return result;
	}
	
	/**
	 * ��ֵ��Ʒ����ҵ��ƽ̨
	 * 
	 * @param ne_order_id
	 * @return String
	 * @throws Exception
	 */
	public String woNeOrderSubProdReSentPlatForms(DynamicDict dto) throws Exception {
		String activeType = "3";
		String helpMsg = "";
		String strTemp = "";
		String searchStr = "<ResultCode>0</ResultCode>";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;
		String result = "";
		Map param = Const.getParam(dto);
		String ne_order_id = Const.getStrValue(param, "ne_order_id");
		String product_code = Const.getStrValue(param, "product_code");
		String mainMsg = Const.getStrValue(param, "subproduct_xml");

		ActiveSVClient asvClient = ActiveSVClient.getInstance();
		helpMsg = "<ne_order_id>" + ne_order_id + "</ne_order_id>";
		helpMsg =helpMsg+"<sub_product_code>" + product_code + "</sub_product_code>";
	
		// ���ú�̨��������ֵ��Ʒ
		strTemp = asvClient.active(activeType, mainMsg, helpMsg);
		if(null!=strTemp){
			successFlag = strTemp.indexOf(searchStr);
			beginIndex = strTemp.indexOf("<ResultMsg>")+ "<ResultMsg>".length();
			endIndex = strTemp.indexOf("</ResultMsg>");
			if (!(successFlag == -1)) {
				result = strTemp.substring(beginIndex, endIndex);
			} else {
				result = "������ֵ��Ʒ������ʧ��:" + strTemp.substring(beginIndex, endIndex);
			}
		}
		return result;
	}
}
