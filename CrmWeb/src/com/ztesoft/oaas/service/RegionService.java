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
	 * 判断是否共建局母局
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
	 * 通过region_org_rela表获取一个region_id所对应的organization记录
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
	 *            参与人标识,暂时不用,预留
	 * @param privType
	 *            权限条件类型:0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
	 * @param privCode
	 *            权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
	 * @param regionType
	 *            地域类型 0表示计费线,1表示资源线,2表示营销线,3表示组织线
	 * @param regionLevel
	 *            地域级别
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
	 * 插入区域实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建区域标识
	 */

	public String insertRegion(RegionVO vo) throws Exception {

		Map param = new HashMap();
		param.put("RegionVO", vo);
		return DataTranslate._String((ServiceManager.callJavaBeanService(
				"RegionBO", "insertRegion", param)));

	}

	/**
	 * 获取一个区域的所以下级区域级别为regionLevel 的区域 add by fxf 2008.5.22
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
	 * 获取一个区域的pathCode add by fxf 2008.4.28
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
	 * 获取一个区域的所有上级区域的ID
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
	 * 判断区域编码是否已经在系统中存在,增加一个区域的时候需要先判断区域编码的唯一性.
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
	 * 根据标识更新区域实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
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
	 * 删除区域实体，同时根据类别和级别删除相关冗余表
	 * 
	 * @param pregion_id
	 *            实体标识
	 * @return 操作结果
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
	 * 根据privilegeCode的权限的区域范围,查找区域列表 RegionSelect.js
	 * 
	 * @param staffCode
	 *            员工号,根据员工号查询改员工在privilegeCode权限中的区域范围,当前功能是直接从
	 *            登陆员工的Session中获取该员工的权限范围,所以暂时该参数无用.
	 * @param privilegeCode
	 *            权限编码
	 * @param regionLevel
	 *            最低区域范围
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
	 * 获取用于TreeList组件的XML格式计费区域树（计费线）
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有计费区域构成的xml字符串
	 */
	public String getBillRegionList(String parentRegionId) throws Exception {

		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getBillRegionList", param));
	}

	/**
	 * 获取菜单编码所对应的权限权限所在的地域
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
	 * 获取用于TreeList组件的XML格式营销区域树（营销线）
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有营销区域构成的xml字符串
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
	 * 获取用于TreeList组件的XML格式资源区域树（资源线）
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有资源区域构成的xml字符串
	 */
	public String getResourceRegionList(String parentRegionId) throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getResourceRegionList", param));

	}

	// 查询资源区域当前节点(当前节点是再用户的权限范围内的)下的所有节点,并为这些下级节点加上权限标志.
	public String getResourceRegionListWithPrivFlag(String parentRegionId)
			throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getResourceRegionListWithPrivFlag", param));

	}

	// 查询组织区域当前节点(当前节点是再用户的权限范围内的)下的所有节点,并为这些下级节点加上权限标志.
	public String getOrganizationRegionListWithPrivFlag(String parentRegionId)
			throws Exception {
		Map param = new HashMap();
		param.put("parentRegionId", parentRegionId);
		return DataTranslate._String(ServiceManager.callJavaBeanService(
				"RegionBO", "getOrganizationRegionListWithPrivFlag", param));

	}

	// 查询组织线区域(当前节点不是用户的权限范围),并过滤掉没有权限的区域
	public String getOrganizationRegionListByFilter(String parentRegionId,
			String privType, String privCode, String orgTypeId)
			throws Exception {
		Object obj = RequestContext.getContext().getHttpSession()
				.getAttribute("LoginRespond");// 从Session中获取鉴权服务器返回的登陆结果
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

	// 查询资源区域(当前节点不是用户的权限范围),并过滤掉没有权限的区域
	public String getResourceRegionListByFilter(String parentRegionId,
			String regionLevel, String privType, String privCode)
			throws Exception {
		Object obj = RequestContext.getContext().getHttpSession()
				.getAttribute("LoginRespond");// 从Session中获取鉴权服务器返回的登陆结果
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
	 * 根据权限信息和区域上级ID查询区域表
	 * 
	 * @param parentRegionId
	 *            上级区域ID
	 * @param privType
	 *            权限条件类型:0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
	 * @param privCode
	 *            权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
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
	 * 查询省份实体
	 * 
	 * @param prrprovince_id
	 *            标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为RrProvinceVO
	 */
	public VO getProvinceByRegionId(String prrprovince_id) throws Exception {
		Map param = new HashMap();
		param.put("prrprovince_id", prrprovince_id);
		return DataTranslate._VO(ServiceManager.callJavaBeanService("RegionBO",
				"getProvinceByRegionId", param));

	}

	/**
	 * 插入省份和相应区域实体
	 * 
	 * @param voRegion
	 *            待插入区域实体
	 * @param voProvince
	 *            待插入省份实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建省份区域标识
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
	 * 根据标识更新省份和相应区域实体
	 * 
	 * @param voRegion
	 *            待更新区域实体
	 * @param voProvince
	 *            待更新省份实体
	 * @return 操作结果
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
	 * 查询本地网实体
	 * 
	 * @param prrlan_id
	 *            本地网标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为RrLanVO
	 */
	public RrLanVO getLanByRegionId(String prrlan_id) throws Exception {
		Map param = new HashMap();
		param.put("prrlan_id", prrlan_id);
		return (RrLanVO) ServiceManager.callJavaBeanService("RegionBO",
				"getLanByRegionId", param);

	}

	/**
	 * 插入本地网实体，同时插入相应区域实体
	 * 
	 * @param voRegion
	 *            待插入管理区域实体
	 * @param voLan
	 *            待插入本地网实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建本地网标识
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
	 * 根据标识更新本地网实体和相应区域实体
	 * 
	 * @param voRegion
	 *            待更新管理区域实体
	 * @param voLan
	 *            待更新本地网实体
	 * @return 操作结果
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
	 * 查询营业区实体
	 * 
	 * @param prrbusiness_id
	 *            标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为RrBusinessVO
	 */
	public RrBusinessVO getBusinessByRegionId(String prrbusiness_id)
			throws Exception {
		Map param = new HashMap();
		param.put("prrbusiness_id", prrbusiness_id);
		return (RrBusinessVO) ServiceManager.callJavaBeanService("RegionBO",
				"getBusinessByRegionId", param);

	}

	/**
	 * 插入营业区和相应区域实体
	 * 
	 * @param voRegion
	 *            待插入管理区域实体
	 * @param voBusi
	 *            待插入营业区实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建营业区标识
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
	 * 根据标识更新营业区和相应区域实体
	 * 
	 * @param voRegion
	 *            待更新管理区域实体
	 * @param voBusi
	 *            待更新营业区实体
	 * @return 操作结果
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
