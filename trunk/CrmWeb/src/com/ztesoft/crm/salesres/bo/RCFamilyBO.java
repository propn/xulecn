package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.dao.BonusDAOFactory;
import com.ztesoft.crm.salesres.dao.RcFamilyDAO;
import com.ztesoft.crm.salesres.dao.RcStateDAO;
import com.ztesoft.crm.salesres.dao.RcStateDefDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcFamilyVO;
import com.ztesoft.crm.salesres.vo.RcStateDefVO;

public class RCFamilyBO extends DictAction {

	/**
	 * ���ݲ�ѯ���� ȡ�� ��Դ����Ķ�Ӧ����;
	 * 
	 * @param sType
	 * @param sContent
	 * @param pi
	 * @param ps
	 * @return
	 */
	public PageModel getRCFamilyInfo(DynamicDict dto) throws FrameException {
		Map m = (Map) dto.getValueByName("parameter");
		int pi = ((Integer) m.get("pageIndex")).intValue();
		int ps = ((Integer) m.get("pageSize")).intValue();
		String sType = (String) m.get("searchType");
		String sContent = (String) m.get("searchKeyword");
		// ������ѡ���ն�ʱ�Ŀ���
		String isCanSql = "select 1 from dc_public where stype = 94913 ";
		SqlComDAO comdao = SrDAOFactory.getInstance().getSqlComDAO();
		boolean isCan = comdao.checkExist(isCanSql);

		int flag = sContent.indexOf("@");// ��ʾ���Ǹ�ҳ�洫�����Ĳ�ѯ��

		if (flag >= 0)
			sContent = sContent.substring(0, sContent.indexOf("@"));

		PageModel pm = new PageModel();
		String sql = "";
		try {
			if (sContent == null) {
				sContent = "";
			}
			if (sType == null) {
				sType = "";
			}
			if (!("".equals(sContent))) {
				if ("fcode".equals(sType)) {
					if (isCan && flag >= 0 && sContent == "104")
						return null;
					sql += "  and FAMILY_ID='" + sContent + "' ";
				}
				if ("fname".equals(sType)) {
					sql += " and FAMILY_NAME like '%" + sContent + "%' ";
					sql += " and FAMILY_ID !='104'";
				}
			}
			if (isCan && flag >= 0)
				sql += " and FAMILY_ID !='104'";
			sql += " order by FAMILY_ID asc";

			RcFamilyDAO pdao = BonusDAOFactory.getInstance().getRcFamilyDAO();
			pm = PageHelper.popupPageModel(pdao, sql, pi, ps);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pm;
	}

	/**
	 * ���������Դ��һ����¼;
	 * 
	 * @param rcFamilyVo
	 * @return
	 */
	public String saveRCFamilyInfo(DynamicDict dto) {
		String retResult = "";
		try {
			String rcFamilyId = SeqDAOFactory.getInstance()
					.getSequenceManageDAO().getNextSequence("RC_FAMILY",
							"FAMILY_ID");
			Map m = (Map) dto.getValueByName("parameter");
			RcFamilyVO rcvo = (RcFamilyVO) m.get("vo");
			rcvo.setFamilyId(rcFamilyId);
			rcvo.setCreateDate(DateFormatUtils.getFormatedDateTime());
			rcvo.setStateDate(DateFormatUtils.getFormatedDateTime());
			VO vo = (VO) rcvo;
			RcFamilyDAO pdao = BonusDAOFactory.getInstance().getRcFamilyDAO();
			pdao.insert(vo);
			retResult = rcFamilyId;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retResult;
	}

	/**
	 * ����Ҫ�޸ļ�����Դ����.
	 * 
	 * @param rcFamilyVo
	 * @return
	 */
	public boolean updateRCFamilyInfo(DynamicDict dto) {
		boolean retResult = false;
		try {
			Map m = (Map) dto.getValueByName("parameter");
			RcFamilyVO rcvo = (RcFamilyVO) m.get("vo");
			String whereCon = " and FAMILY_ID=" + rcvo.getFamilyId();
			RcFamilyDAO pdao = BonusDAOFactory.getInstance().getRcFamilyDAO();
			pdao.update(whereCon, rcvo);
			retResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retResult;
	}

	/**
	 * ɾ��һ��������Դ�����ݡ�
	 * 
	 * @param rcFamilyId
	 * @return
	 * @throws FrameException 
	 */
	public int deleteRCFamilyInfo(DynamicDict dto) throws FrameException {
		int retResult = 0;
			RcFamilyDAO pdao = BonusDAOFactory.getInstance().getRcFamilyDAO();
			Map m = (Map) dto.getValueByName("parameter");
			String rcFamilyId = (String) m.get("rcFamilyId");
			boolean isUsed = pdao.checkFamilyState(rcFamilyId);
			if (isUsed) {
				retResult = 1;
			} else {
				SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
				String[] params = new String[] { rcFamilyId };

				String sql = "delete from RC_ATTR_DEF where family_id = ?";
				dao.excute(sql, params);

				sql = "delete from RC_LEVEL_DEF where family_id = ?";
				dao.excute(sql, params);

				sql = "delete from RC_STATE_DEF where family_id = ?";
				dao.excute(sql, params);

				String whereCon = " and FAMILY_ID=" + rcFamilyId;
				pdao.deleteByCond(whereCon);
				retResult = 2;
			}
		return retResult;
	}

	/**
	 * ��ѯ������Դ�����ԡ�
	 * 
	 * @param familyId
	 * @return
	 * @throws FrameException 
	 */
	public List qryRCFamilyAttrInfo(DynamicDict dto) throws FrameException {
		List list = new ArrayList();
		Map m = (Map) dto.getValueByName("parameter");
		String familyId = (String) m.get("familyId");
		if (familyId == null) {
			familyId = "";
		} else {
			RcFamilyDAO pdao = BonusDAOFactory.getInstance()
					.getRcFamilyDAO();
			list = pdao.searchAttrInfo(familyId);
		}
		return list;

	}

	public String insertRCFamilyAttrInfo(DynamicDict dto) throws FrameException {
		String flag = "";
		boolean bFlag = false;
		Map m = (Map) dto.getValueByName("parameter");
		String familyId = (String) m.get("familyId");
		String attrId = (String) m.get("attrValue");
		String attrOwnerType = (String) m.get("attrOwnerType");
		if ("".equals(attrOwnerType) || attrOwnerType == null
				|| familyId == null || "".equals(familyId)) {
			flag = "0";
			System.out.println("����:insertRCFamilyAttrInfo ���յĲ�������Ϊ�յ�����");
			return flag;
		} else {
			RcFamilyDAO pdao = BonusDAOFactory.getInstance()
					.getRcFamilyDAO();
			long lCount = pdao.countAttrInfo(attrId, familyId);
			if (lCount != 0) {
				flag = "1";
				return flag;
			}
			bFlag = pdao.insertAttrInfo(attrId, attrOwnerType, familyId);
			if (bFlag) {
				flag = "2";
			}
		}
		return flag;

	}

	/**
	 * ɾ��һ��������Դ�����ԡ�
	 * 
	 * @param rcFamilyId,attrValue
	 * @return
	 */
	public int deleteAttrInfo(DynamicDict dto) throws FrameException  {
		int retResult = 0;
		Map m = (Map) dto.getValueByName("parameter");
		String rcFamilyId = (String) m.get("rcFamilyId");
		String attrId = (String) m.get("attrId");
		String attrOwnerType = (String) m.get("attrOwnerType");
			RcFamilyDAO pdao = BonusDAOFactory.getInstance().getRcFamilyDAO();

			if (attrId == null || "".equals(attrId) || rcFamilyId == null
					|| "".equals(rcFamilyId) || attrOwnerType == null
					|| "".equals(attrOwnerType)) {
				return retResult;
			} else {
				retResult = pdao.deleteAttrInfo(rcFamilyId, attrId,
						attrOwnerType);
			}
		return retResult;
	}

	/**
	 * ��ѯ���õ����ԡ�
	 * 
	 * @param familyId
	 * @return
	 */
	public PageModel findAttrInfo(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		int pageIndex =((Integer)hm.get("pageIndex")).intValue();
		int pageSize=((Integer)hm.get("pageSize")).intValue();
		
		String familyId = hm.get("familyId") == null ? "" : String.valueOf(hm
				.get("familyId"));
		String attrOwnerType = hm.get("attrOwnerType") == null ? "" : String
				.valueOf(hm.get("attrOwnerType"));
		String salesRescId = hm.get("salesRescId") == null ? "" : String
				.valueOf(hm.get("salesRescId"));
		PageModel pm = new PageModel();
		List list = new ArrayList();
		try {
			RcFamilyDAO pdao = BonusDAOFactory.getInstance().getRcFamilyDAO();
			if (familyId == null || "".equals(familyId)
					|| attrOwnerType == null || "".equals(attrOwnerType)) {
				return null;
			} else if ("".equals(salesRescId)) {
				list = pdao.findAttrInfo(familyId, attrOwnerType);
			} else {
				list = pdao.findAttrInfo(familyId, attrOwnerType, salesRescId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		pm.setPageCount(pm.getPageCount());
		pm.setTotalCount(list.size());
		pm.setPageIndex(pageIndex);
		pm.setPageSize(pageSize);
		pm.setList(list);
		return pm;

	}

	/**
	 * ��ѯ���Զ�Ӧ��ȡֵ�б�
	 * 
	 * @param familyId
	 * @return
	 */
	public PageModel findAttrValue(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		int pageIndex =((Integer)hm.get("pageIndex")).intValue();
		int pageSize=((Integer)hm.get("pageSize")).intValue();
		String attrId = (String) hm.get("attrId");
		List list = new ArrayList();
		PageModel pm = new PageModel();
		try {
			if (attrId == null || "".equals(attrId)) {
				return null;
			} else {
				RcFamilyDAO pdao = BonusDAOFactory.getInstance()
						.getRcFamilyDAO();
				list = pdao.findAttrValue(attrId);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		pm.setPageCount(pm.getPageCount());
		pm.setTotalCount(list.size());
		pm.setPageIndex(pageIndex);
		pm.setPageSize(pageSize);
		pm.setList(list);
		return pm;

	}

	/**
	 * ����Ӫ����Դid����ѯ��Ӫ����Դ��Ӧ����Դģ���ѡ��״̬
	 * 
	 * @param salesRescId
	 * @return
	 */
	public List qryEntityAvailState(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		String salesRescId = (String) hm.get("salesRescId");
		if (salesRescId == null || salesRescId.trim().length() < 1) {
			return new ArrayList();
		}
		RcStateDefDAO dao = SrDAOFactory.getInstance().getRcStateDefDAO();
		String whereCond = " and exists (select * from sales_resource c "
				+ "where a.family_id=c.family_id and c.sales_resource_id="
				+ salesRescId + ")";
		return dao.findByCond(whereCond);
	}

	/**
	 * ���ݼ���id��ѯ�ü����Ѿ�ѡ���״̬�ļ���
	 * 
	 * @param familyId
	 * @return
	 */
	public List qryFamilyState(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		String familyId = (String) hm.get("familyId");
		if (familyId == null || familyId.trim().length() < 1) {
			return new ArrayList();
		}
		RcStateDefDAO dao = SrDAOFactory.getInstance().getRcStateDefDAO();
		String whereCond = " and a.family_id=" + familyId;
		return dao.findByCond(whereCond);
	}

	/**
	 * ���ݼ���id��ѯ�ü����ѡ��״̬�ļ���
	 * 
	 * @param familyId
	 * @return
	 */
	public List qryFamilyAvailState(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		String familyId = (String) hm.get("familyId");
		if (familyId == null || familyId.trim().length() < 1) {
			return new ArrayList();
		}
		RcStateDAO dao = SrDAOFactory.getInstance().getRcStateDAO();
		String whereCond = " not exists(select * from rc_state_def where rc_state_def.state=rc_state.state "
				+ "and rc_state_def.family_id=" + familyId + ")";
		return dao.findByCond(whereCond);
	}

	/**
	 * ����Դ�������״̬��-1����Ҫ��������ݲ�������1�������ɹ���-2����ü�¼�Ѿ����ڲ����ظ����룻
	 * 
	 * @param familyId
	 * @param state
	 * @return
	 */
	public int insertFamilyState(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		String familyId = (String) hm.get("familyId");
		String stateStr = (String) hm.get("stateStr");
		if (familyId == null || familyId.trim().length() < 1
				|| stateStr == null || stateStr.trim().length() < 1) {
			return -1;
		}
		int rtn = 0;
		RcStateDefDAO dao = SrDAOFactory.getInstance().getRcStateDefDAO();
		if (stateStr.endsWith(",")) {
			stateStr = stateStr.substring(0, stateStr.length() - 1);
			// �жϸ�״̬�Ƿ��Ѿ�������
		}
		String whereCond = " and a.family_id=" + familyId + " and a.state in("
				+ stateStr + ")";
		List list = dao.findByCond(whereCond);
		if (list != null && list.size() > 0) {
			return -2;
		}
		String[] arr = stateStr.split(",");
		if (arr == null || arr.length < 1) {
			return -1;
		}
		String state = null;
		for (int i = 0; i < arr.length; i++) {
			state = arr[i];
			if (state != null && state.trim().length() > 0) {
				if (state.startsWith("'")) {
					state = state.substring(1);
				}
				if (state.endsWith("'")) {
					state = state.substring(0, state.length() - 1);
				}
				RcStateDefVO vo = new RcStateDefVO(familyId, state);
				dao.insert(vo);
				rtn++;
			}
		}
		return rtn;
	}

	/**
	 * ɾ������״̬������ü����״̬�Ѿ�����������Դʵ��ʹ��������ɾ���� -1������ȱʧ -2:��״̬�Ѿ�����������Դʵ��ʹ�ò���ɾ��
	 * 
	 * @param familyId
	 * @param state
	 * @return ɾ��������
	 */
	public int delFamilyState(DynamicDict dto) throws FrameException  {
		Map hm = (Map) dto.getValueByName("parameter");
		String familyId = (String) hm.get("familyId");
		String state = (String) hm.get("state");
		if (familyId == null || state == null || familyId.trim().length() < 1
				|| state.trim().length() < 1) {
			return -1;
		}
		String qrySql_count = "select count(rc_entity.sales_resource_id) from rc_entity,sales_resource "
				+ " where rc_entity.sales_resource_id=sales_resource.sales_resource_id "
				+ " and sales_resource.family_id="
				+ familyId
				+ " and rc_entity.curr_state='" + state + "'";
		String del_cond = " family_id=" + familyId + " and state='" + state
				+ "'";
		RcStateDefDAO dao = SrDAOFactory.getInstance().getRcStateDefDAO();
		long num = dao.countBySql(qrySql_count);
		if (num > 0) {
			return -2;
		}
		long del_num = dao.deleteByCond(del_cond);
		return (int) del_num;
	}

	/**
	 * �����Դ�����Ƿ��Ѿ�����Դ����,����Ϊtrue��ʾ�У�false��ʾû��,""��ʾ������
	 * 
	 * @param familyId
	 *            String
	 * @throws Exception
	 * @return String
	 */
	public String checkRCFamilyInfo(DynamicDict dto) throws Exception {
		Map m = (Map) dto.getValueByName("parameter");
		String familyId = (String) m.get("familyId");
		if (familyId == null || familyId.equals("")) {
			return "";
		}
		HashMap map = new HashMap();
		map.put("sales_resource_fad", familyId);
		List list = SrDAOFactory.getInstance().getSalesRescDAO().findBySql(
				"select * from sales_resource where family_id=?",
				new String[] { familyId });
		if (list != null && list.size() > 0) {
			return "true";
		}
		return "false";
	}
}
