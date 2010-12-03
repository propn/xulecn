package com.ztesoft.oaas.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.exception.ErrorLevel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.oaas.dao.areacode.AreaCodeDAO;
import com.ztesoft.oaas.dao.areacode.AreaCodeDAOFactory;
import com.ztesoft.oaas.dao.chsalearea.ChSaleAreaDAO;
import com.ztesoft.oaas.dao.chsalearea.ChSaleAreaDAOFactory;
import com.ztesoft.oaas.dao.chsalecomm.ChSaleCommDAO;
import com.ztesoft.oaas.dao.chsalecomm.ChSaleCommDAOFactory;
import com.ztesoft.oaas.dao.exch.ExchDAO;
import com.ztesoft.oaas.dao.exch.ExchDAOFactory;
import com.ztesoft.oaas.dao.mmdatapriv.MmDataPrivDAO;
import com.ztesoft.oaas.dao.mmdatapriv.MmDataPrivDAOFactory;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAO;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.dao.prodFamily.ProdFamilyDAO;
import com.ztesoft.oaas.dao.prodFamily.ProdFamilyDAOFactory;
import com.ztesoft.oaas.dao.region.RegionDAO;
import com.ztesoft.oaas.dao.region.RegionDAOFactory;
import com.ztesoft.oaas.dao.regionorgrelat.RegionOrgRelaDAO;
import com.ztesoft.oaas.dao.regionorgrelat.RegionOrgRelatDAOFactory;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAOFactory;
import com.ztesoft.oaas.dao.rrlan.RrLanDAO;
import com.ztesoft.oaas.dao.rrlan.RrLanDAOFactory;
import com.ztesoft.oaas.dao.rrprovince.RrProvinceDAO;
import com.ztesoft.oaas.dao.rrprovince.RrProvinceDAOFactory;
import com.ztesoft.oaas.exception.OAASError;
import com.ztesoft.oaas.exception.OAASException;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.vo.AreaCodeVO;
import com.ztesoft.oaas.vo.ExchVO;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.MmMenuVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PrivVO;
import com.ztesoft.oaas.vo.RegionOrgRelaVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.RrLanVO;
import com.ztesoft.oaas.vo.RrProvinceVO;
import com.ztesoft.oaas.vo.SimpleRegionVO;

public class RegionBO extends DictAction {
	public String getResourceRegionListWithPrivFlag(DynamicDict dto)
			throws Exception {
		HashMap map=(HashMap) dto.getValueByName("parameter");
		String parentRegionId = (String)map.get("parentRegionId");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = (ArrayList) daoRegion
				.getResourceRegionListWithPrivFlag(parentRegionId);
		return XMLSegBuilder.toXmlItems(alRegions);
	}

	public String getResourceRegionListByFilter(DynamicDict dto)
			throws Exception {
		Map m = (Map) dto.getValueByName("parameter");
		boolean isSuperStaff = ((Boolean) m.get("isSuperStaff")).booleanValue();
		String parentRegionId = (String) m.get("parentRegionId");
		String regionLevel = (String) m.get("regionLevel");
		// String privType = (String)m.get("privType") ;
		String privCode = (String) m.get("privCode");

		LoginRespond loginRespond = (LoginRespond) m.get("loginRespond");

		ArrayList regionIds = new ArrayList();

		RoleState[] roleStates = loginRespond.getRoleState();
		ArrayList privCodeList = getPrivCodeByType(dto);
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			for (int j = 0; j < privCodeList.size(); j++) {
				privCode = (String) privCodeList.get(j);
				// ��Դ�ߵ�Ȩ�ޱ���
				if ((privCode.equals(roleState.getPrivilegeCode()) || ("SuperM"
						.equals(roleState.getPrivilegeCode())))
						&& "1".equals(roleState.getPermitionFlag())) {
					regionIds.add(roleState.getRegionId());
				}
			}
		}
		if (regionIds.size() == 0 && !loginRespond.isSuperStaff()) {
			return "<items/>";
		}

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		if (isSuperStaff) {
			ArrayList alRegions = (ArrayList) daoRegion
					.getResourceRegionListWithPrivFlag(parentRegionId);
			return XMLSegBuilder.toXmlItems(alRegions);
		} else {

			ArrayList alRegions = (ArrayList) daoRegion
					.getResourceRegionListByFilter(parentRegionId, regionLevel,
							regionIds);
			return XMLSegBuilder.toXmlItems(alRegions);
		}
	}

	public ArrayList getPrivCodeByType(DynamicDict dto) throws Exception {

		Map m = (Map) dto.getValueByName("parameter");

		String privType = (String) m.get("privType");
		String privCode = (String) m.get("privCode");

		ArrayList returnList = new ArrayList();
		Set set = new HashSet();

		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
		PrivVO privVO = null;
		if ("0".equals(privType)) {// Ȩ��ID
			privVO = daoPriv.findByPrimaryKey(privCode);
			if (privVO != null) {
				privCode = privVO.getPrivCode();
				set.add(privCode);
				// returnList.add( privCode ) ;
			}
		} else if ("1".equals(privType)) {// Ȩ�ޱ���
			// returnList.add(privCode);
			set.add(privCode);
		} else if ("2".equals(privType)) {// �˵�ID
			MmDataPrivDAO dao = MmDataPrivDAOFactory.getMmDataPrivDAO();
			List list = dao.findByCond("data_pkey_1 = '" + privCode + "'");
			for (int i = 0; i < list.size(); i++) {
				MmDataPrivVO vo = (MmDataPrivVO) list.get(0);
				String privId = vo.getPrivId();
				privVO = daoPriv.findByPrimaryKey(privId);
				if (privVO != null) {
					privCode = privVO.getPrivCode();
					// returnList.add(privCode);
					set.add(privCode);
				}
			}
		} else if ("3".equals(privType)) {// �˵�����
			MmMenuDAO mmMenuDAO = MmMenuDAOFactory.getMmMenuDAO();
			List menuList = mmMenuDAO.findByCond("menu_code = '" + privCode
					+ "'");
			if (menuList.size() > 0) {

				// ��ѯ�˵�ID
				MmMenuVO menuVO = (MmMenuVO) menuList.get(0);
				String menuId = menuVO.getMenuId();

				MmDataPrivDAO dao = MmDataPrivDAOFactory.getMmDataPrivDAO();
				List mmDataPrivList = dao.findByCond("data_pkey_1 = '" + menuId
						+ "'");

				for (int i = 0; i < mmDataPrivList.size(); i++) {
					MmDataPrivVO vo = (MmDataPrivVO) mmDataPrivList.get(i);
					String privId = vo.getPrivId();
					privVO = daoPriv.findByPrimaryKey(privId);
					if (privVO == null) {
						continue;
					}
					privCode = privVO.getPrivCode();
					// returnList.add(privCode);
					set.add(privCode);
				}
			}
		}
		returnList.addAll(set);
		return returnList;
	}

	// ��ѯ��֯������(��ǰ�ڵ㲻���û���Ȩ�޷�Χ),�����˵�û��Ȩ�޵�����
	public String getOrganizationRegionListByFilter(DynamicDict dto)
			throws Exception {

		Map m = (Map) dto.getValueByName("parameter");

		String parentRegionId = (String) m.get("parentRegionId");
		String privType = (String) m.get("privType");
		String privCode = (String) m.get("privCode");
		String orgTypeId = (String) m.get("orgTypeId");
		LoginRespond loginRespond = (LoginRespond) m.get("loginRespond");

		ArrayList regionIds = new ArrayList();

		RoleState[] roleStates = loginRespond.getRoleState();

		ArrayList privCodeList = getPrivCodeByType(dto);
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			// ��֯�ߵ�Ȩ�ޱ���
			for (int j = 0; j < privCodeList.size(); j++) {
				privCode = (String) privCodeList.get(j);
				if ((privCode.equals(roleState.getPrivilegeCode()) || ("SuperM"
						.equals(roleState.getPrivilegeCode())))
						&& "3".equals(roleState.getPermitionFlag())) {
					regionIds.add(roleState.getRegionId());
				}
			}
		}
		String t = "";
		if (regionIds.size() == 0 && !loginRespond.isSuperStaff()) {
			return "<items/>";
		}

		if (loginRespond.isSuperStaff()) {

			OrganizationDAO daoOrganization = OrganizationDAOFactory
					.getOrganizationDAO();
			ArrayList alOrganizations = daoOrganization
					.getTelecomOrganizationListByParentIdWithPrivFlag(
							parentRegionId, "T");
			return XMLSegBuilder.toXmlItems(alOrganizations);
		} else {
			RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
			ArrayList alRegions = (ArrayList) daoRegion
					.getOrganizationRegionListByFilter(parentRegionId,
							regionIds, orgTypeId);
			return XMLSegBuilder.toXmlItems(alRegions);
		}
	}

	public String getOrganizationRegionListWithPrivFlag(DynamicDict dto)
			throws Exception {
//		String parentRegionId = (String) dto
//				.getValueByName(Const.ACTION_PARAMETER);
		
		Map map = (Map) dto.getValueByName(Const.ACTION_PARAMETER);	//bob modify
		String parentRegionId = (String)map.get("parentRegionId");
		
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = (ArrayList) daoRegion
				.getOrganizationRegionListWithPrivFlag(parentRegionId);
		return XMLSegBuilder.toXmlItems(alRegions);
	}

	public boolean checkExistRegionCode(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionCode = (String) param.get("regionCode");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		List list = daoRegion.findByCond("REGION_CODE = '" + regionCode + "'");
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String insertRegion(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RegionVO vo = (RegionVO) param.get("RegionVO");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		daoRegion.insert(vo);

		// ������ӵ�Region��Organization֮����ڹ���,�����region_org_rela��
		if (!(vo.getPartyId() == null) && !"".equals(vo.getPartyId())) {
			RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
					.createRegionOrgRelaDAO();
			RegionOrgRelaVO regionOrgRelaVO = new RegionOrgRelaVO();
			regionOrgRelaVO.setRegionId(vo.getRegionId());
			regionOrgRelaVO.setPartyId(vo.getPartyId());
			regionOrgRelaDao.insert(regionOrgRelaVO);
		}

		if ("98F".equalsIgnoreCase(vo.getRegionLevel())) {
			daoRegion.insertChnBranch(vo.getRegionId());
		}
		return vo.getRegionId();
	}

	public String insertProvince(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RegionVO voRegion = (RegionVO) param.get("RegionVO");
		RrProvinceVO voProv = (RrProvinceVO) param.get("RrProvinceVO");

		RrProvinceDAO daoRrProvince = RrProvinceDAOFactory.getRrProvinceDAO();
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		daoRegion.insert(voRegion);

		voProv.setProvId(voRegion.getRegionId());
		daoRrProvince.insert(voProv);

		// ����region_org_rela��,����region��organization֮��Ĺ�����ϵ
		if (!(voRegion.getPartyId() == null)
				&& !"".equals(voRegion.getPartyId())) {
			RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
					.createRegionOrgRelaDAO();
			RegionOrgRelaVO regionOrgRelaVO = new RegionOrgRelaVO();
			regionOrgRelaVO.setRegionId(voRegion.getRegionId());
			regionOrgRelaVO.setPartyId(voRegion.getPartyId());
			regionOrgRelaDao.insert(regionOrgRelaVO);
		}

		return voProv.getProvId();

	}

	public String pinsertRrLan(DynamicDict dto) throws Exception {

		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RrLanVO voLan = (RrLanVO) param.get("RrLanVO");
		RegionVO voRegion = (RegionVO) param.get("RegionVO");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		daoRegion.insert(voRegion);

		voLan.setLanId(voRegion.getRegionId());
		RrLanDAO daoRrLan = RrLanDAOFactory.getRrLanDAO();
		daoRrLan.insert(voLan);

		AreaCodeVO voAreaCode = new AreaCodeVO(voLan.getAreaCode(), voLan
				.getLanName(), voRegion.getRegionId(), voRegion
				.getRegionLevel());
		AreaCodeDAO daoAreaCode = AreaCodeDAOFactory.getAreaCodeDAO();
		daoAreaCode.insert(voAreaCode);

		// ��������Region��¼��Organization֮���й�����ϵ,�����region_org_rela��
		if (!(voRegion.getPartyId() == null)
				&& !"".equals(voRegion.getPartyId())) {
			RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
					.createRegionOrgRelaDAO();
			RegionOrgRelaVO regionOrgRelaVO = new RegionOrgRelaVO();
			regionOrgRelaVO.setRegionId(voRegion.getRegionId());
			regionOrgRelaVO.setPartyId(voRegion.getPartyId());
			regionOrgRelaDao.insert(regionOrgRelaVO);
		}
		return voLan.getLanId();
	}

	public String pinsertRrBusiness(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RrBusinessVO voBusi = (RrBusinessVO) param.get("RrBusinessVO");
		RegionVO voRegion = (RegionVO) param.get("RegionVO");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		daoRegion.insert(voRegion);

		voBusi.setBusinessId(voRegion.getRegionId());
		RrBusinessDAO daoRrBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
		daoRrBusiness.insert(voBusi);

		// ��������Region��¼��Organization��֮���й�����ϵ,�����region_org_rela��
		if (!(voRegion.getPartyId() == null)
				&& !"".equals(voRegion.getPartyId())) {
			RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
					.createRegionOrgRelaDAO();
			RegionOrgRelaVO regionOrgRelaVO = new RegionOrgRelaVO();
			regionOrgRelaVO.setRegionId(voRegion.getRegionId());
			regionOrgRelaVO.setPartyId(voRegion.getPartyId());
			regionOrgRelaDao.insert(regionOrgRelaVO);
		}
		return voBusi.getBusinessId();
	}

	public boolean pupdateRegion(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String region_id = (String) param.get("region_id");
		RegionVO vo = (RegionVO) param.get("RegionVO");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		daoRegion.update(vo);

		// �޸�region_org_rela��
		RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
				.createRegionOrgRelaDAO();
		RegionOrgRelaVO regionOrgRelaVO = regionOrgRelaDao.findByPrimaryKey(vo
				.getRegionId());
		if (regionOrgRelaVO != null) {// ���ԭ���Ѿ�����Region��Organization֮��Ĺ�����ϵ
			if (!"".equals(vo.getPartyId())) {// ������޸���֯ID
				regionOrgRelaVO.setPartyId(vo.getPartyId());
				regionOrgRelaDao.update(vo.getRegionId(), regionOrgRelaVO);
			} else {// �����ȥ������
				regionOrgRelaDao.delete(vo.getRegionId());
			}
		} else {// �������û�н���������ϵ
			if (!"".equals(vo.getPartyId())) {// �����½����˹�����ϵ,�����
				regionOrgRelaVO = new RegionOrgRelaVO();
				regionOrgRelaVO.setRegionId(vo.getRegionId());
				regionOrgRelaVO.setPartyId(vo.getPartyId());
				regionOrgRelaDao.insert(regionOrgRelaVO);
			}
		}

		if ("97D".equals(vo.getRegionLevel())) {// �޸ĵ��Ǿ���
			ExchDAO exchDao = ExchDAOFactory.getExchDAO();
			List list = exchDao.findByCond(" REGION_ID = " + region_id);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ExchVO exchVO = (ExchVO) list.get(i);
					exchVO.setExchCode(vo.getRegionCode());
					exchVO.setExchName(vo.getRegionName());
					exchDao.update(exchVO.getExchId(), exchVO);
				}
			}
		}
		return true;
	}

	public boolean pupdateRrProvince(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RrProvinceVO voProvince = (RrProvinceVO) param.get("RrProvinceVO");
		RegionVO voRegion = (RegionVO) param.get("RegionVO");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		voRegion.setRegionId(voProvince.getProvId());
		daoRegion.update(voRegion);

		// �޸�region_org_rela��
		RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
				.createRegionOrgRelaDAO();
		RegionOrgRelaVO regionOrgRelaVO = regionOrgRelaDao
				.findByPrimaryKey(voRegion.getRegionId());
		if (regionOrgRelaVO != null) {// ���ԭ���Ѿ�����Region��Organization֮��Ĺ�����ϵ
			if (!"".equals(voRegion.getPartyId())) {// ������޸���֯ID,�޸�region_org_rela��
				regionOrgRelaVO.setPartyId(voRegion.getPartyId());
				regionOrgRelaDao
						.update(voRegion.getRegionId(), regionOrgRelaVO);
			} else {// �����ȥ������,ɾ��region_org_rela��
				regionOrgRelaDao.delete(voRegion.getRegionId());
			}
		} else {// �������û�н���������ϵ
			if (!"".equals(voRegion.getPartyId())) {// �����½����˹�����ϵ,�����region_org_rela��
				regionOrgRelaVO = new RegionOrgRelaVO();
				regionOrgRelaVO.setRegionId(voRegion.getRegionId());
				regionOrgRelaVO.setPartyId(voRegion.getPartyId());
				regionOrgRelaDao.insert(regionOrgRelaVO);
			}
		}

		RrProvinceDAO daoRrProvince = RrProvinceDAOFactory.getRrProvinceDAO();
		return daoRrProvince.update(voProvince.getProvId(), voProvince);
	}

	public boolean pupdateRrLan(DynamicDict dto) throws Exception {

		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RrLanVO voLan = (RrLanVO) param.get("RrLanVO");
		RegionVO voRegion = (RegionVO) param.get("RegionVO");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		voRegion.setRegionId(voLan.getLanId());
		daoRegion.update(voRegion);

		// �޸�region_org_rela��
		RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
				.createRegionOrgRelaDAO();
		RegionOrgRelaVO regionOrgRelaVO = regionOrgRelaDao
				.findByPrimaryKey(voRegion.getRegionId());
		if (regionOrgRelaVO != null) {// ���ԭ���Ѿ�����Region��Organization֮��Ĺ�����ϵ
			if (!"".equals(voRegion.getPartyId())) {// ������޸���֯ID
				regionOrgRelaVO.setPartyId(voRegion.getPartyId());
				regionOrgRelaDao
						.update(voRegion.getRegionId(), regionOrgRelaVO);
			} else {// �����ȥ������
				regionOrgRelaDao.delete(voRegion.getRegionId());
			}
		} else {// �������û�н���������ϵ
			if (!"".equals(voRegion.getPartyId())) {// �����½����˹�����ϵ,�����
				regionOrgRelaVO = new RegionOrgRelaVO();
				regionOrgRelaVO.setRegionId(voRegion.getRegionId());
				regionOrgRelaVO.setPartyId(voRegion.getPartyId());
				regionOrgRelaDao.insert(regionOrgRelaVO);
			}
		}

		RrLanDAO daoRrLan = RrLanDAOFactory.getRrLanDAO();

		AreaCodeDAO daoAreaCode = AreaCodeDAOFactory.getAreaCodeDAO();
		AreaCodeVO voAreaCodeOld = daoAreaCode.findByPrimaryKey(voLan
				.getLanId());

		if (voAreaCodeOld == null || voAreaCodeOld.getRegionId() == null
				|| "".equals(voAreaCodeOld.getRegionId())) {
			daoAreaCode
					.insert(new AreaCodeVO(voLan.getAreaCode(), voLan
							.getLanName(), voLan.getLanId(), voRegion
							.getRegionLevel()));
		} else if ((voAreaCodeOld.getAreaCode() == null
				&& voLan.getAreaCode() != null && !"".equals(voLan
				.getAreaCode()))
				|| voAreaCodeOld.getAreaCode() != null
				&& !voAreaCodeOld.getAreaCode().equals(voLan.getAreaCode())) {
			daoAreaCode.update(voLan.getLanId(), new AreaCodeVO(voLan
					.getAreaCode(), voLan.getLanName(), voLan.getLanId(),
					voRegion.getRegionLevel()));
		}

		return daoRrLan.update(voLan.getLanId(), voLan);

	}

	public boolean pupdateRrBusiness(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		RrBusinessVO voBusi = (RrBusinessVO) param.get("RrBusinessVO");
		RegionVO voRegion = (RegionVO) param.get("RegionVO");
		
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		voRegion.setRegionId(voBusi.getBusinessId());
		
//		 �޸�region_org_rela��
		RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
				.createRegionOrgRelaDAO();
		RegionOrgRelaVO regionOrgRelaVO = regionOrgRelaDao
				.findByPrimaryKey(voRegion.getRegionId());
		if (regionOrgRelaVO != null) {// ���ԭ���Ѿ�����Region��Organization֮��Ĺ�����ϵ
			if (!"".equals(voRegion.getPartyId())) {// ������޸���֯ID
				regionOrgRelaVO.setPartyId(voRegion.getPartyId());
				regionOrgRelaDao
						.update(voRegion.getRegionId(), regionOrgRelaVO);
			} else {// �����ȥ������
				regionOrgRelaDao.delete(voRegion.getRegionId());
			}
		} else {// �������û�н���������ϵ
			if (!"".equals(voRegion.getPartyId())) {// �����½����˹�����ϵ,�����
				regionOrgRelaVO = new RegionOrgRelaVO();
				regionOrgRelaVO.setRegionId(voRegion.getRegionId());
				regionOrgRelaVO.setPartyId(voRegion.getPartyId());
				regionOrgRelaDao.insert(regionOrgRelaVO);
			}
		}
		RrBusinessDAO daoRrBuiness = RrBusinessDAOFactory.getRrBusinessDAO();
		return daoRrBuiness.update(voBusi.getBusinessId(), voBusi);
	}

	public String getRootResourceRegionListByPrivControl(DynamicDict dto)
			throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String menuCode = (String) param.get("menuCode");

		ArrayList privCodeList = getPrivCodeByType("3", menuCode);

		Object obj = RequestContext.getContext().getHttpSession().getAttribute(
				"LoginRespond");// ��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;

		if (loginRespond.isSuperStaff()) {
			RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
			ArrayList list = daoRegion.getAllRescRegions("-1");

			return XMLSegBuilder.toXmlItems(list);
		}

		Set regionIds = new HashSet();

		RoleState[] roleStates = loginRespond.getRoleState();
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			for (int j = 0; j < privCodeList.size(); j++) {
				String privCode = (String) privCodeList.get(j);
				if ((privCode.equals(roleState.getPrivilegeCode()))
						&& "1".equals(roleState.getPermitionFlag())) {
					regionIds.add(roleState.getRegionId());
				}
			}
		}

		if (regionIds.size() == 0) {
			return "<items/>";
		}

		RegionDAO regionDao = RegionDAOFactory.getRegionDAO();
		ArrayList list = (ArrayList) regionDao
				.getBillRegionByRegionIds(regionIds);
		return XMLSegBuilder.toXmlItems(list);

	}

	public ArrayList getPrivCodeByType(String privType, String privCode)
			throws Exception {
		ArrayList returnList = new ArrayList();
		Set set = new HashSet();

		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
		PrivVO privVO = null;
		if ("0".equals(privType)) {// Ȩ��ID
			privVO = daoPriv.findByPrimaryKey(privCode);
			if (privVO != null) {
				privCode = privVO.getPrivCode();
				set.add(privCode);
				// returnList.add( privCode ) ;
			}
		} else if ("1".equals(privType)) {// Ȩ�ޱ���
			// returnList.add(privCode);
			set.add(privCode);
		} else if ("2".equals(privType)) {// �˵�ID
			MmDataPrivDAO dao = MmDataPrivDAOFactory.getMmDataPrivDAO();
			List list = dao.findByCond("data_pkey_1 = '" + privCode + "'");
			for (int i = 0; i < list.size(); i++) {
				MmDataPrivVO vo = (MmDataPrivVO) list.get(0);
				String privId = vo.getPrivId();
				privVO = daoPriv.findByPrimaryKey(privId);
				if (privVO != null) {
					privCode = privVO.getPrivCode();
					// returnList.add(privCode);
					set.add(privCode);
				}
			}
		} else if ("3".equals(privType)) {// �˵�����
			MmMenuDAO mmMenuDAO = MmMenuDAOFactory.getMmMenuDAO();
			List menuList = mmMenuDAO.findByCond("menu_code = '" + privCode
					+ "'");
			if (menuList.size() > 0) {

				// ��ѯ�˵�ID
				MmMenuVO menuVO = (MmMenuVO) menuList.get(0);
				String menuId = menuVO.getMenuId();

				MmDataPrivDAO dao = MmDataPrivDAOFactory.getMmDataPrivDAO();
				List mmDataPrivList = dao.findByCond("data_pkey_1 = '" + menuId
						+ "'");

				for (int i = 0; i < mmDataPrivList.size(); i++) {
					MmDataPrivVO vo = (MmDataPrivVO) mmDataPrivList.get(i);
					String privId = vo.getPrivId();
					privVO = daoPriv.findByPrimaryKey(privId);
					if (privVO == null) {
						continue;
					}
					privCode = privVO.getPrivCode();
					// returnList.add(privCode);
					set.add(privCode);
				}
			}
		}
		returnList.addAll(set);
		return returnList;
	}

	public long pdeleteRegion(DynamicDict dto) throws Exception {

		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String region_id = (String) param.get("region_id");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();

		List list = daoRegion.findByCond(" parent_region_id = " + region_id);
       
		if (list.size() > 0) {
           return -9999;			
		}

		// ɾ��region_org_rela���к�region_id�����ļ�¼
		RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
				.createRegionOrgRelaDAO();
		regionOrgRelaDao.delete(region_id);

		RegionVO voRegion = daoRegion.findByPrimaryKey(region_id);

		String regionLevel = voRegion.getRegionLevel();
		/**
		 * 97A ���� 97B ʡ 97C ������ 97D Ӫҵ�� 97E �ƷѾ��� 98D ����� 98E ĸ�� 98F ��վ
		 */
		if ("97B".equals(regionLevel)) {
			// ɾ��ʡ�ݱ�
			RrProvinceDAO daoRrProvince = RrProvinceDAOFactory
					.getRrProvinceDAO();
			return daoRrProvince.delete(region_id)
					+ daoRegion.delete(region_id);
		} else if ("97C".equals(regionLevel)) {
			// ɾ����������
			RrLanDAO daoRrLan = RrLanDAOFactory.getRrLanDAO();
			AreaCodeDAO daoAreaCode = AreaCodeDAOFactory.getAreaCodeDAO();
			return daoRrLan.delete(region_id) + daoAreaCode.delete(region_id)
					+ daoRegion.delete(region_id);
		} else if ("97D".equals(regionLevel)) {
			// ɾ��Ӫҵ����
			RrBusinessDAO daoRrBusiness = RrBusinessDAOFactory
					.getRrBusinessDAO();
			return daoRrBusiness.delete(region_id)
					+ daoRegion.delete(region_id);
		} else if ("97E".equals(regionLevel)) {
			ExchDAO daoExch = ExchDAOFactory.getExchDAO();
			return daoExch.deleteByCond("REGION_ID=" + region_id)
					+ daoRegion.delete(region_id);
		} else if ("98D".equals(regionLevel)) {             
            //RrPpdomDAO daoRrPpdom = RrPpdomDAOFactory.getRrPpdomDAO();
            //return daoRrPpdom.delete(region_id) +
            //daoRegion.delete(region_id);
			
			
			return daoRegion.delete(region_id);
		} else if ("98E".equals(regionLevel)) {
			// RrExchDAO daoRrExch = RrExchDAOFactory.getRrExchDAO();
			// return daoRrExch.delete(region_id) + daoRegion.delete(region_id);
			return daoRegion.delete(region_id);
		} else if ("98F".equals(regionLevel)) {
			// RcMeasureDAO daoRcMeasure =
			// RcMeasureDAOFactory.getRcMeasureDAO();
			// return daoRcMeasure.delete(region_id) +
			// daoRegion.delete(region_id);
			return daoRegion.delete(region_id);
		} else if ("99D".equals(regionLevel)) {
			ChSaleAreaDAO daoChSaleArea = ChSaleAreaDAOFactory
					.getChSaleAreaDAO();
			return daoChSaleArea.delete(region_id)
					+ daoRegion.delete(region_id);
		} else if ("99E".equals(regionLevel)) {
			ChSaleCommDAO daoChSaleComm = ChSaleCommDAOFactory
					.getChSaleCommDAO();
			return daoChSaleComm.delete(region_id)
					+ daoRegion.delete(region_id);
		} else if ("99F".equals(regionLevel)) {
			throw new DAOSystemException("Unsupported region-level: "
					+ regionLevel);
		} else {
			return daoRegion.delete(region_id);
		}

	}

	public String getResourceRegionList(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String parentRegionId = (String) param.get("parentRegionId");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = daoRegion.getAllRescRegions(parentRegionId);
		return XMLSegBuilder.toXmlItems(alRegions);
	}

	public OrganizationVO getOrganizationByRegionId(DynamicDict dto)
			throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionId = (String) param.get("regionId");

		RegionOrgRelaDAO regionOrgRelaDao = RegionOrgRelatDAOFactory
				.createRegionOrgRelaDAO();
		OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();

		RegionOrgRelaVO regionOrgRelaVO = regionOrgRelaDao
				.findByPrimaryKey(regionId);
		if (regionOrgRelaVO == null) {
			return null;
		}
		OrganizationVO orgVO = orgDao.findByPrimaryKey(regionOrgRelaVO
				.getPartyId());
		return orgVO;

	}

	public ArrayList getStaffRegionInfo(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);

		if (param.size() == 3) {
			String regionType = (String) param.get("regionType");
			String regionLevel = (String) param.get("regionLevel");
			ArrayList regionIds = (ArrayList) param.get("regionIds");

			String regionIdCond = "";
			for (int i = 0; i < regionIds.size(); i++) {
				regionIdCond = regionIdCond + (String) regionIds.get(i) + ",";
			}
			if (!"".equals(regionIdCond)) {
				regionIdCond = regionIdCond.substring(0,
						regionIdCond.length() - 1);
			}
			ArrayList returnList = new ArrayList();
			ArrayList tempList = null;
			if ("3".equals(regionType)) {// ����Ϊ��֯�ߵ�
				OrganizationDAO organizationDAO = OrganizationDAOFactory
						.getOrganizationDAO();
				tempList = organizationDAO.findBySql(
						"select * from organization where ORG_TYPE_ID="
								+ regionLevel + " AND PARTY_ID IN ("
								+ regionIdCond + ")", null);
				if (tempList == null) {
					return null;
				} else {
					for (int i = 0; i < tempList.size(); i++) {
						OrganizationVO vo = (OrganizationVO) tempList.get(i);
						SimpleRegionVO regionVO = new SimpleRegionVO();
						regionVO.setRegionId(vo.getPartyId());
						regionVO.setRegionName(vo.getOrgName());
						returnList.add(regionVO);

						// ��ѯ��֯���¼���֯����ӵ�����List��

						// List subList =
						// getSubLevelOrganization(vo.getPartyId(),regionLevel);
						String sql = "select * from organization where ORG_TYPE_ID = "
								+ regionLevel
								+ " and  '.' || path_code || '.' like = '%."
								+ vo.getPartyId() + ".%";
						OrganizationDAO orgDao = OrganizationDAOFactory
								.getOrganizationDAO();
						List subList = orgDao.findBySql(sql, null);

						for (int j = 0; j < subList.size(); j++) {
							OrganizationVO subVO = (OrganizationVO) tempList
									.get(j);
							SimpleRegionVO subRegionVO = new SimpleRegionVO();
							subRegionVO.setRegionId(subVO.getPartyId());
							subRegionVO.setRegionName(subVO.getOrgName());
							returnList.add(subRegionVO);
						}
					}
				}
			} else {
				RegionDAO regionDAO = RegionDAOFactory.getRegionDAO();
				ArrayList filterList = new ArrayList();
				tempList = regionDAO
						.findBySql(
								"select * from region where ( virtual_deal_flag is null or virtual_deal_flag != 'T' ) AND REGION_ID IN ("
										+ regionIdCond + ")", null);

				returnList.addAll(tempList);
			}
			return returnList;

		} else if (param.size() == 5) {
			String partyRoleId = (String) param.get("partyRoleId");
			String privType = (String) param.get("privType");
			String privCode = (String) param.get("privCode");
			String regionType = (String) param.get("regionType");
			String regionLevel = (String) param.get("regionLevel");
			ArrayList privCodeList = getPrivCodeByType(privType, privCode);

			Object obj = RequestContext.getContext().getHttpSession()
					.getAttribute("LoginRespond");// ��Session�л�ȡ��Ȩ���������صĵ�½���
			LoginRespond loginRespond = (LoginRespond) obj;

			ArrayList regionIds = new ArrayList();

			RoleState[] roleStates = loginRespond.getRoleState();

			for (int i = 0; i < roleStates.length; i++) {
				RoleState roleState = roleStates[i];
				for (int j = 0; j < privCodeList.size(); j++) {
					privCode = (String) privCodeList.get(j);
					if (((privCode.equals(roleState.getPrivilegeCode())) || "SuperM"
							.equals(privCode))
							&& regionType.equals(roleState.getPermitionFlag())) {
						regionIds.add(roleState.getRegionId());
					}
				}
			}
			if (regionIds.size() == 0) {
				return new ArrayList();
			}

			param.put("regionType", regionType);
			param.put("regionLevel", regionLevel);
			param.put("regionIds", regionIds);

			dto.setValueByName(Const.ACTION_PARAMETER, param);

			return getStaffRegionInfo(dto);
		} else {
			return new ArrayList();
		}
	}

	public String getPrivilegeRegion(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String menuCode = (String) param.get("menuCode");
		String regionType = (String) param.get("regionType");

		LoginRespond loginRespond = (LoginRespond) RequestContext.getContext()
				.getHttpSession().getAttribute("LoginRespond");

		// String superStaffCode =
		// CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		// if( superStaffCode.equals(loginRespond.getStaffCode())){//����ǳ�������Ա
		if (loginRespond.isSuperStaff()) {
			if ("0".equals(regionType)) {// �Ʒ���
				param.put("parentRegionId", "-1");
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				return this.getBillRegionList(dto);
			} else if ("1".equals(regionType)) {// ��Դ��
				param.put("parentRegionId", "-1");
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				return this.getResourceRegionList(dto);
			}
		}

		RoleState[] roleStates = loginRespond.getRoleState();

		Set regionIdSet = new HashSet();
		ArrayList privCodes = getPrivCodeByType("3", menuCode);// ͨ���˵������ȡ��Ӧ��Ȩ�ޱ���

		for (int i = 0; i < roleStates.length; i++) {
			for (int j = 0; j < privCodes.size(); j++) {
				String privCode = (String) privCodes.get(j);
				if (privCode.equals(roleStates[i].getPrivilegeCode())
						&& regionType.equals(roleStates[i].getPermitionFlag())) {
					regionIdSet.add(roleStates[i].getRegionId());// ��ѯ�Ͳ˵�����Ȩ�޶�Ӧ������ID
				}
			}
		}
		if (regionIdSet.size() == 0) {
			return "<items/>";
		}

		RegionDAO regionDao = RegionDAOFactory.getRegionDAO();
		ArrayList list = (ArrayList) regionDao
				.getBillRegionByRegionIds(regionIdSet);
		return XMLSegBuilder.toXmlItems(list);
	}

	public String getBillRegionList(DynamicDict dto) throws Exception {

		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String parentRegionId = (String) param.get("parentRegionId");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = daoRegion.getAllBillRegions(parentRegionId);
		return XMLSegBuilder.toXmlItems(alRegions);
	}

	public String getFilteredResourceRegionList(DynamicDict dto)
			throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String parentRegionId = (String) param.get("parentRegionId");
		String privType = (String) param.get("privType");
		String privCode = (String) param.get("privCode");

		ArrayList privCodeList = getPrivCodeByType(privType, privCode);

		Object obj = RequestContext.getContext().getHttpSession().getAttribute(
				"LoginRespond");// ��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;

		ArrayList regionIds = new ArrayList();

		RoleState[] roleStates = loginRespond.getRoleState();

		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			for (int j = 0; j < privCodeList.size(); j++) {
				privCode = (String) privCodeList.get(j);
				if (privCode.equals(roleState.getPrivilegeCode())) {
					regionIds.add(roleState.getRegionId());
				}
			}
		}
		// String superStaffCode =
		// CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		// GlobalVariableHelper helper = new GlobalVariableHelper(
		// getRequest());
		// String staffCode = helper.getVariable( GlobalVariableHelper.OPER_CODE
		// ) ;
		// if(regionIds.size() == 0 && !staffCode.equals(superStaffCode)){
		if (regionIds.size() == 0 && !loginRespond.isSuperStaff()) {
			return "<items/>";
		}

		param.put("parentRegionId", parentRegionId);
		param.put("regionIds", regionIds);

		dto.setValueByName(Const.ACTION_PARAMETER, param);

		return getXmlItemsOfFilteredResourceRegions(dto);

	}

	public String getXmlItemsOfFilteredResourceRegions(DynamicDict dto)
			throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String parentRegionId = (String) param.get("parentRegionId");
		ArrayList regionIds = (ArrayList) param.get("regionIds");

		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = daoRegion.getAllRescRegions(parentRegionId);
		ArrayList returnList = new ArrayList();
		for (int i = 0; i < alRegions.size(); i++) {
			RegionVO vo = (RegionVO) alRegions.get(i);
			for (int j = 0; j < regionIds.size(); j++) {
				String regionId = (String) regionIds.get(j);
				if (regionId.equals(vo.getRegionId())) {
					returnList.add(vo);
				}
			}
		}
		return XMLSegBuilder.toXmlItems(returnList);
	}

	public boolean isPubCreatedBureau(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionId = (String) param.get("regionId");

		param.put("regionId", regionId);
		dto.setValueByName(Const.ACTION_PARAMETER, param);
		OrganizationVO vo = this.getOrganizationByRegionId(dto);
		if (vo == null) {
			return false;
		}
		if ("03".equals(vo.getOrgClass())) {
			return true;
		}
		return false;
	}

	public RegionVO getRegionByCode(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionCode = (String) param.get("regionCode");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		List list = daoRegion.findByCond("REGION_CODE = '" + regionCode + "'");

		if (list.size() > 0) {
			return (RegionVO) list.get(0);
		} else {
			return null;
		}

	}

	public RegionVO getRegionById(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionId = (String) param.get("regionId");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		return daoRegion.findByPrimaryKey(regionId);
	}

	public boolean checkSubRegionNGNFlag(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionId = (String) param.get("regionId");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		return daoRegion.checkSubRegionNGNFlag(regionId);
	}

	public ArrayList getProductFamily(DynamicDict dto) throws Exception {
		ProdFamilyDAO daoProdFamily = ProdFamilyDAOFactory.getProdFamilyDAO();
		return daoProdFamily.getAllProdFamily();
	}

	public ArrayList getSubLevelRegion(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionId = (String) param.get("regionId");
		String regionLevel = (String) param.get("regionLevel");
		String sql = "select * from region where ( virtual_deal_flag is null or virtual_deal_flag != 'T' ) AND   '.' || path_code || '.'  like '%."
				+ regionId + ".%' and region_level = '" + regionLevel + "'";
		RegionDAO regionDao = RegionDAOFactory.getRegionDAO();
		ArrayList list = regionDao.findBySql(sql, null);
		return list;
	}

	public String getRegionPathCode(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionId = (String) param.get("regionId");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		RegionVO vo = daoRegion.findByPrimaryKey(regionId);
		String pathCode = vo.getPathCode();
		return pathCode;
	}

	public RegionVO getRegion(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String region_id = (String) param.get("region_id");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		return daoRegion.findByPrimaryKey(region_id);

	}

	public String getRegionByCond(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String regionLevel = (String) param.get("regionLevel");
		String regionType = (String) param.get("regionType");
		Object obj = RequestContext.getContext().getHttpSession().getAttribute(
				"LoginRespond");// ��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;

		String regionIds = "";

		RoleState[] roleStates = loginRespond.getRoleState();
		/**
		 * for (int i = 0; i < roleStates.length; i++) { RoleState roleState =
		 * roleStates[i]; if
		 * (privilegeCode.equals(roleState.getPrivilegeCode())) { regionIds +=
		 * roleState.getRegionId() + ","; } }
		 */
		// regionIds = regionIds.substring(0, regionIds.length() - 1);
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = daoRegion.getRegionsByCond(regionIds,
				regionLevel, regionType);
		if (alRegions.size() < 1) {
			alRegions.add(new RegionVO());
		}
		return XMLSegBuilder.toXmlItems(alRegions);

	}

	public String getSaleRegionList(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String parentRegionId = (String) param.get("parentRegionId");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		ArrayList alRegions = daoRegion.getAllSaleRegions(parentRegionId);
		return XMLSegBuilder.toXmlItems(alRegions);
	}

	public RrProvinceVO getProvinceByRegionId(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String prrprovince_id = (String) param.get("prrprovince_id");
		RrProvinceDAO daoRrProvince = RrProvinceDAOFactory.getRrProvinceDAO();
		return daoRrProvince.findByPrimaryKey(prrprovince_id);
	}

	public RrLanVO getLanByRegionId(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String prrlan_id = (String) param.get("prrlan_id");
		RrLanDAO daoRrLan = RrLanDAOFactory.getRrLanDAO();
		RrLanVO vo = daoRrLan.findByPrimaryKey(prrlan_id);
		AreaCodeDAO daoAreaCode = AreaCodeDAOFactory.getAreaCodeDAO();
		AreaCodeVO voAreaCode = daoAreaCode.findByPrimaryKey(prrlan_id);
		if (voAreaCode != null) {
			vo.setAreaCode(voAreaCode.getAreaCode());
		}

		AreaCodeVO areaCodeVo = daoAreaCode.findByPrimaryKey(vo.getLanId());

		vo.setAreaCode(areaCodeVo.getAreaCode());
		return vo;

	}

	public RrBusinessVO getBusinessByRegionId(DynamicDict dto) throws Exception {
		Map param = (HashMap) dto.getValueByName(Const.ACTION_PARAMETER);
		String prrbusiness_id = (String) param.get("prrbusiness_id");
		RrBusinessDAO daoRrBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
		return daoRrBusiness.findByPrimaryKey(prrbusiness_id);

	}
}
