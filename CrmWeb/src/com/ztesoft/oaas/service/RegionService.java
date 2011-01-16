package com.ztesoft.oaas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.RrLanVO;
import com.ztesoft.oaas.vo.RrProvinceVO;

public class RegionService extends DictService {

	/**
	 * �ж��Ƿ񹲽���ĸ��
	 * 
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public boolean isPubCreatedBureau(String regionId) throws Exception {

		Map param = new HashMap();
		param.put("regionId", regionId);

		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "isPubCreatedBureau", param));

	}

	/**
	 * ͨ��region_org_rela���ȡһ��region_id����Ӧ��organization��¼
	 * 
	 * @param regionId
	 * @return OrganizationVO
	 * @throws Exception
	 */
	public VO getOrganizationByRegionId(String regionId) throws Exception {

		Map param = new HashMap();
		param.put("regionId", regionId);

		return DataTranslate._VO(ServiceManager.callJavaBeanService("RegionBO",
				"getOrganizationByRegionId", param));

	}

	public VO getRegionByCode(String regionCode) throws Exception {

		Map param = new HashMap();
		param.put("regionCode", regionCode);
		return DataTranslate._VO(ServiceManager.callJavaBeanService("RegionBO",
				"getRegionByCode", param));

	}

	public VO getRegionById(String regionId) throws Exception {
		Map param = new HashMap();
		param.put("regionId", regionId);
		return DataTranslate._VO(ServiceManager.callJavaBeanService("RegionBO",
				"getRegionById", param));
	}

	public boolean checkSubRegionNGNFlag(String regionId) throws Exception {
		Map param = new HashMap();
		param.put("regionId", regionId);
		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "checkSubRegionNGNFlag", param));
	}

	public ArrayList getProductFamily() throws Exception {
		return (ArrayList) ServiceManager.callJavaBeanService("RegionBO",
				"getProductFamily", null);
	}

	// **************************************ENTITY[Region]**************************************

	/**
	 * @param partyRoleId
	 *            �����˱�ʶ,��ʱ����,Ԥ��
	 * @param privType
	 *            Ȩ����������:0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
	 * @param privCode
	 *            Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	 * @param regionType
	 *            �������� 0��ʾ�Ʒ���,1��ʾ��Դ��,2��ʾӪ����,3��ʾ��֯��
	 * @param regionLevel
	 *            ���򼶱�
	 */
	public ArrayList getStaffRegionInfo(String partyRoleId, String privType,
			String privCode, String regionType, String regionLevel)
			throws Exception {

		Map param = new HashMap();
		param.put("partyRoleId", partyRoleId);
		param.put("privType", privType);
		param.put("privCode", privCode);
		param.put("regionType", regionType);
		param.put("regionLevel", regionLevel);
		return (ArrayList) ServiceManager.callJavaBeanService("RegionBO",
				"getStaffRegionInfo", param);

	}

	public ArrayList getStaffRegionInfo(String regionType, String regionLevel,
			ArrayList regionIds) throws Exception {
		Map param = new HashMap();
		param.put("regionType", regionType);
		param.put("regionLevel", regionLevel);
		param.put("regionIds", regionIds);
		return (ArrayList) ServiceManager.callJavaBeanService("RegionBO",
				"getStaffRegionInfo", param);
	}

	/**
	 * ��������ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½������ʶ
	 */

	public String insertRegion(RegionVO vo) throws Exception {

		Map param = new HashMap();
		param.put("RegionVO", vo);
		return DataTranslate._String((ServiceManager.callJavaBeanService(
				"RegionBO", "insertRegion", param)));

	}

	/**
	 * ��ȡһ������������¼����򼶱�ΪregionLevel ������ add by fxf 2008.5.22
	 * 
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getSubLevelRegion(String regionId, String regionLevel)
			throws Exception {
		Map param = new HashMap();
		param.put("regionId", regionId);
		param.put("regionLevel", regionLevel);
		return (ArrayList) ServiceManager.callJavaBeanService("RegionBO",
				"getSubLevelRegion", param);
	}

	/**
	 * ��ȡһ�������pathCode add by fxf 2008.4.28
	 * 
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public String getRegionPathCode(String regionId) throws Exception {
		Map param = new HashMap();
		param.put("regionId", regionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getRegionPathCode", param));

	}

	/**
	 * ��ȡһ������������ϼ������ID
	 * 
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public String[] getAllParentRegionIds(String regionId) throws Exception {
		VO vo = getRegion(regionId);
		if (vo == null)
			return null;
		String pathCode = ((RegionVO) vo).getPathCode();
		return pathCode.split("\\.");
	}

	public VO getRegion(String region_id) throws Exception {
		Map param = new HashMap();
		param.put("region_id", region_id);
		return DataTranslate._VO(ServiceManager.callJavaBeanService("RegionBO",
				"getRegion", param));
	}

	/**
	 * �ж���������Ƿ��Ѿ���ϵͳ�д���,����һ�������ʱ����Ҫ���ж���������Ψһ��.
	 * 
	 * @param regionCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistRegionCode(String regionCode) throws Exception {
		Map param = new HashMap();
		param.put("regionCode", regionCode);
		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "checkExistRegionCode", param));
	}

	/**
	 * ���ݱ�ʶ��������ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateRegion(RegionVO vo) throws Exception {
		return pupdateRegion(vo.getRegionId(), vo);
	}

	public boolean pupdateRegion(String region_id, RegionVO vo)
			throws Exception {

		Map param = new HashMap();
		param.put("region_id", region_id);
		param.put("RegionVO", vo);
		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "pupdateRegion", param));

	}

	/**
	 * ɾ������ʵ�壬ͬʱ�������ͼ���ɾ����������
	 * 
	 * @param pregion_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public long deleteRegion(String pregion_id) throws Exception {
		return pdeleteRegion(pregion_id);
	}

	public long pdeleteRegion(String region_id) throws Exception {
		Map param = new HashMap();
		param.put("region_id", region_id);

		return DataTranslate._long(ServiceManager.callJavaBeanService(
				"RegionBO", "pdeleteRegion", param));
	}

	/**
	 * ����privilegeCode��Ȩ�޵�����Χ,���������б� RegionSelect.js
	 * 
	 * @param staffCode
	 *            Ա����,����Ա���Ų�ѯ��Ա����privilegeCodeȨ���е�����Χ,��ǰ������ֱ�Ӵ�
	 *            ��½Ա����Session�л�ȡ��Ա����Ȩ�޷�Χ,������ʱ�ò�������.
	 * @param privilegeCode
	 *            Ȩ�ޱ���
	 * @param regionLevel
	 *            �������Χ
	 * @return
	 */
	public String getRegionByCond(String regionLevel, String regionType)
			throws Exception {
		Map param = new HashMap();
		param.put("regionLevel", regionLevel);
		param.put("regionType", regionType);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getRegionByCond", param));
	}

	/**
	 * ��ȡ����TreeList�����XML��ʽ�Ʒ����������Ʒ��ߣ�
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList��������мƷ����򹹳ɵ�xml�ַ���
	 */
	public String getBillRegionList(String parentRegionId) throws Exception {

		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getBillRegionList", param));
	}

	/**
	 * ��ȡ�˵���������Ӧ��Ȩ��Ȩ�����ڵĵ���
	 * 
	 * @param menuCode
	 * @param regionType
	 * @return
	 * @throws Exception
	 */
	public String getPrivilegeRegion(String menuCode, String regionType)
			throws Exception {

		Map param = new HashMap();
		param.put("menuCode", menuCode);
		param.put("regionType", regionType);

		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getPrivilegeRegion", param));
	}

	/**
	 * ��ȡ����TreeList�����XML��ʽӪ����������Ӫ���ߣ�
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList���������Ӫ�����򹹳ɵ�xml�ַ���
	 */
	public String getSaleRegionList(String parentRegionId) throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getSaleRegionList", param));
	}

	public String getRootResourceRegionListByPrivControl(String menuCode)
			throws Exception {

		Map param = new HashMap();
		param.put("menuCode", menuCode);

		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getRootResourceRegionListByPrivControl", param));
	}

	/**
	 * ��ȡ����TreeList�����XML��ʽ��Դ����������Դ�ߣ�
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����������Դ���򹹳ɵ�xml�ַ���
	 */
	public String getResourceRegionList(String parentRegionId) throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getResourceRegionList", param));

	}

	// ��ѯ��Դ����ǰ�ڵ�(��ǰ�ڵ������û���Ȩ�޷�Χ�ڵ�)�µ����нڵ�,��Ϊ��Щ�¼��ڵ����Ȩ�ޱ�־.
	public String getResourceRegionListWithPrivFlag(String parentRegionId)
			throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getResourceRegionListWithPrivFlag", param));

	}

	// ��ѯ��֯����ǰ�ڵ�(��ǰ�ڵ������û���Ȩ�޷�Χ�ڵ�)�µ����нڵ�,��Ϊ��Щ�¼��ڵ����Ȩ�ޱ�־.
	public String getOrganizationRegionListWithPrivFlag(String parentRegionId)
			throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getOrganizationRegionListWithPrivFlag", param));

	}

	// ��ѯ��֯������(��ǰ�ڵ㲻���û���Ȩ�޷�Χ),�����˵�û��Ȩ�޵�����
	public String getOrganizationRegionListByFilter(String parentRegionId,
			String privType, String privCode, String orgTypeId)
			throws Exception {
		Object obj = RequestContext.getContext().getHttpSession()
				.getAttribute("LoginRespond");// ��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		param.put("privType", privType);
		param.put("privCode", privCode);
		param.put("orgTypeId", orgTypeId);
		param.put("loginRespond", loginRespond);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getOrganizationRegionListByFilter", param));
	}

	// ��ѯ��Դ����(��ǰ�ڵ㲻���û���Ȩ�޷�Χ),�����˵�û��Ȩ�޵�����
	public String getResourceRegionListByFilter(String parentRegionId,
			String regionLevel, String privType, String privCode)
			throws Exception {
		Object obj = RequestContext.getContext().getHttpSession()
				.getAttribute("LoginRespond");// ��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;

		Map param = new HashMap();
		param.put("isSuperStaff", Boolean.valueOf(loginRespond.isSuperStaff()));
		param.put("parentRegionId", parentRegionId);
		param.put("regionLevel", regionLevel);
		param.put("loginRespond", loginRespond);
		param.put("privType", privType);
		param.put("privCode", privCode);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getResourceRegionListByFilter", param));

	}

	/**
	 * ����Ȩ����Ϣ�������ϼ�ID��ѯ�����
	 * 
	 * @param parentRegionId
	 *            �ϼ�����ID
	 * @param privType
	 *            Ȩ����������:0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
	 * @param privCode
	 *            Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	 * @return
	 * @throws Exception
	 */
	public String getFilteredResourceRegionList(String parentRegionId,
			String privType, String privCode) throws Exception {

		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		param.put("privType", privType);
		param.put("privCode", privCode);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getFilteredResourceRegionList", param));

	}

	public String getXmlItemsOfFilteredResourceRegions(String parentRegionId,
			ArrayList regionIds) throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		param.put("regionIds", regionIds);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getXmlItemsOfFilteredResourceRegions", param));

	}

	// **************************************ENTITY[RrProvince]**************************************
	/**
	 * ��ѯʡ��ʵ��
	 * 
	 * @param prrprovince_id
	 *            ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪRrProvinceVO
	 */
	public VO getProvinceByRegionId(String prrprovince_id) throws Exception {
		Map param = new HashMap();
		param.put("prrprovince_id", prrprovince_id);
		return DataTranslate._VO(ServiceManager.callJavaBeanService("RegionBO",
				"getProvinceByRegionId", param));

	}

	/**
	 * ����ʡ�ݺ���Ӧ����ʵ��
	 * 
	 * @param voRegion
	 *            ����������ʵ��
	 * @param voProvince
	 *            ������ʡ��ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�ʡ�������ʶ
	 */

	public String insertProvince(RegionVO voRegion, RrProvinceVO voProv)
			throws Exception {
		Map param = new HashMap();
		param.put("RegionVO", voRegion);
		param.put("RrProvinceVO", voProv);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "insertProvince", param));
	}

	/**
	 * ���ݱ�ʶ����ʡ�ݺ���Ӧ����ʵ��
	 * 
	 * @param voRegion
	 *            ����������ʵ��
	 * @param voProvince
	 *            ������ʡ��ʵ��
	 * @return �������
	 */
	public boolean updateProvince(RegionVO voRegion, RrProvinceVO voProvince)
			throws Exception {
		return pupdateRrProvince(voProvince, voRegion);
	}

	public boolean pupdateRrProvince(RrProvinceVO voProvince, RegionVO voRegion)
			throws Exception {
		Map param = new HashMap();
		param.put("RrProvinceVO", voProvince);
		param.put("RegionVO", voRegion);
		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "pupdateRrProvince", param));

	}

	// **************************************ENTITY[RrLan]**************************************
	/**
	 * ��ѯ������ʵ��
	 * 
	 * @param prrlan_id
	 *            ��������ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪRrLanVO
	 */
	public RrLanVO getLanByRegionId(String prrlan_id) throws Exception {
		Map param = new HashMap();
		param.put("prrlan_id", prrlan_id);
		return (RrLanVO) ServiceManager.callJavaBeanService("RegionBO",
				"getLanByRegionId", param);

	}

	/**
	 * ���뱾����ʵ�壬ͬʱ������Ӧ����ʵ��
	 * 
	 * @param voRegion
	 *            �������������ʵ��
	 * @param voLan
	 *            �����뱾����ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���������ʶ
	 */
	public String insertLan(RegionVO voRegion, RrLanVO voLan) throws Exception {
		return pinsertRrLan(voLan, voRegion);
	}

	public String pinsertRrLan(RrLanVO voLan, RegionVO voRegion)
			throws Exception {
		Map param = new HashMap();
		param.put("RrLanVO", voLan);
		param.put("RegionVO", voRegion);

		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "pinsertRrLan", param));

	}

	/**
	 * ���ݱ�ʶ���±�����ʵ�����Ӧ����ʵ��
	 * 
	 * @param voRegion
	 *            �����¹�������ʵ��
	 * @param voLan
	 *            �����±�����ʵ��
	 * @return �������
	 */
	public boolean updateLan(RegionVO voRegion, RrLanVO voLan) throws Exception {
		return pupdateRrLan(voLan, voRegion);
	}

	public boolean pupdateRrLan(RrLanVO voLan, RegionVO voRegion)
			throws Exception {
		Map param = new HashMap();
		param.put("RrLanVO", voLan);
		param.put("RegionVO", voRegion);
		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "pupdateRrLan", param));
	}

	// **************************************ENTITY[RrBusiness]**************************************
	/**
	 * ��ѯӪҵ��ʵ��
	 * 
	 * @param prrbusiness_id
	 *            ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪRrBusinessVO
	 */
	public RrBusinessVO getBusinessByRegionId(String prrbusiness_id)
			throws Exception {
		Map param = new HashMap();
		param.put("prrbusiness_id", prrbusiness_id);
		return (RrBusinessVO) ServiceManager.callJavaBeanService("RegionBO",
				"getBusinessByRegionId", param);

	}

	/**
	 * ����Ӫҵ������Ӧ����ʵ��
	 * 
	 * @param voRegion
	 *            �������������ʵ��
	 * @param voBusi
	 *            ������Ӫҵ��ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ӫҵ����ʶ
	 */
	public String insertBusiness(RegionVO voRegion, RrBusinessVO voBusi)
			throws Exception {
		String businessId = pinsertRrBusiness(voBusi, voRegion);
		return businessId;
	}

	public String pinsertRrBusiness(RrBusinessVO voBusi, RegionVO voRegion)
			throws Exception {
		Map param = new HashMap();
		param.put("RrBusinessVO", voBusi);
		param.put("RegionVO", voRegion);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "pinsertRrBusiness", param));

	}

	/**
	 * ���ݱ�ʶ����Ӫҵ������Ӧ����ʵ��
	 * 
	 * @param voRegion
	 *            �����¹�������ʵ��
	 * @param voBusi
	 *            ������Ӫҵ��ʵ��
	 * @return �������
	 */

	public boolean updateBusiness(RegionVO voRegion, RrBusinessVO voBusi)
			throws Exception {
		return pupdateRrBusiness(voBusi, voRegion);
	}

	public boolean pupdateRrBusiness(RrBusinessVO voBusi, RegionVO voRegion)
			throws Exception {

		Map param = new HashMap();
		param.put("RrBusinessVO", voBusi);
		param.put("RegionVO", voRegion);
		return DataTranslate._boolean(ServiceManager.callJavaBeanService(
				"RegionBO", "pupdateRrBusiness", param));
	}

}
