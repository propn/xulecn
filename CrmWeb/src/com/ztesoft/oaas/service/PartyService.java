package com.ztesoft.oaas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.utils.OAASProxy;
import com.ztesoft.oaas.vo.AddrVO;
import com.ztesoft.oaas.vo.LogicalAddrVO;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationPostVO;
import com.ztesoft.oaas.vo.OrganizationTypeVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyIdentificationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.PartyVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RolesVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.SimpleStaffRoleVO;
import com.ztesoft.oaas.vo.StaffPostVO;
import com.ztesoft.oaas.vo.StaffPrivVO;
import com.ztesoft.oaas.vo.StaffRoleVO;
import com.ztesoft.oaas.vo.StaffVO;
import com.ztesoft.oaas.vo.WorkingOfficeVO;

public class PartyService  extends DictService{

	public String getCompanyId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() );
		return helper.getVariable(GlobalVariableHelper.COMPANY_ID);
	}
	
	public boolean checkStaffDefaultPriv(String partyRoleId) throws Exception {
		HashMap param = new HashMap() ;
		param.put("partyRoleId", partyRoleId) ;
		Object result = ServiceManager.callJavaBeanService("PartyBO","checkStaffDefaultPriv" , param) ;
		return ((Boolean)result).booleanValue() ;
	}
	/**
	 * 获取员工所在的处理局ID
	 * @return
	 * @throws Exception
	 */
	public String getStaffDefaultDealExchId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		return helper.getVariable(GlobalVariableHelper.DEFAULT_DEAL_EXCH_ID);
	}
	/**
	 * 获取员工所在的处理局名称
	 * @return
	 * @throws Exception
	 */
	public String getStaffDefaultDealExchName() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		return helper.getVariable(GlobalVariableHelper.DEFAULT_DEAL_EXCH_NAME);
	}

	/**
	 * 获取工号所在的分公司ID
	 * @return
	 * @throws Exception
	 */
	public String getStaffCompanyId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String companyId = helper.getVariable(GlobalVariableHelper.COMPANY_ID);
		return companyId ;
	}
	/**
	 * 获取工号所在的分公司NAME
	 * @return
	 * @throws Exception
	 */
	public String getStaffCompanyName() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String companyId = helper.getVariable(GlobalVariableHelper.COMPANY_ID);
		OrganizationVO orgVO = this.getOrganization(companyId);
		return orgVO.getOrgName() ;
	}
	/**
	 * 获取工号所属组织ID(部门或者班组)
	 * @return
	 * @throws Exception
	 */
	public String getStaffOrganizationId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String staffOrganizationId = helper.getVariable(GlobalVariableHelper.OPER_ORG_ID);
		return staffOrganizationId ;
	}
	/**
	 * 获取工号所属组织名称(部门或者班组)
	 * @return
	 * @throws Exception
	 */
	public String getStaffOrganizationName() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String staffOrganizationName = helper.getVariable(GlobalVariableHelper.OPER_ORG_NAME);
		return staffOrganizationName ;
	}
	/**
	 * 获取相关对象的path_code字段值
	 * @param id		主键
	 * @param type	类型:1表示REGION表,2表示ORGANIZATION表
	 * @return
	 * @throws Exception
	 */
	public String getPathCode( String id, String type ) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPathCode") ;
		Map m = new HashMap();
		m.put("type", type) ;
		m.put("id", id) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
		
	}
	
	/**
	 * 获取系统配置文件中所配置的超级用户工号
	 * @return
	 */
	public String getSuperStaffCode(){
		String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		return superStaffCode ;
	}
	
	public boolean ifSuperManager() {
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");//从Session中获取鉴权服务器返回的登陆结果
		LoginRespond loginRespond = (LoginRespond) obj;
		return loginRespond.isSuperStaff() ;
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//如果是超级管理员工号,则返回所有的电信组织根节点
		//	return true ;
		//}else{
		//	return false ;
		//}
	}
	/**
	 * 获取一个组织的所有上级组织的ID
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
	public String[] getAllParentOrganizationIds( String partyId ) throws Exception{
		OrganizationVO vo = getOrganization(partyId );
		String pathCode = vo.getPathCode();
		return  pathCode.replace('.',',').split(",");
	}

	/*
	 * 判断一个员工号是否已经被使用了
	 */
	public boolean checkStaffCode( String staffCode ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"checkStaffCode") ;
		dto.setValueByName("parameter", staffCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
		
	}
	
	/*
	 * 激活员工状态
	 */
	public int passwordActivation( String staffCode ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"passwordActivation") ;
		dto.setValueByName("parameter", staffCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	}
	
	 
	//获取当前的用户ID
	public String getStaffIdOfCurrentUser() throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");

		if( loginRespond.isSuperStaff() ){
			return "999999";
		}
		return loginRespond.getPartyRoleId() ;
	}
	
	/**
	 * 根据局站ID获取其所在的处理局
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RegionVO getDealExchByExchStation(String id) throws Exception{
		RegionVO vo = this.getRegionById( id ) ;//获取到局站VO
		vo = this.getRegionById( vo.getParentRegionId() ) ;//获取到母局VO
		return this.getRegionById( vo.getParentRegionId() ) ;//获取到处理局 VO
	}
	public RegionVO getRegionById( String id ) throws Exception { 
		DynamicDict dto = getServiceDTO("PartyBO" ,"getRegionById") ;
		dto.setValueByName("parameter", id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((RegionVO)dto.getValueByName("result")) ;
	}
	
    public PartyRoleVO getPartyRole(String partyrole_id) throws Exception
    {
    	DynamicDict dto = getServiceDTO("PartyBO" ,"getPartyRole") ;
		dto.setValueByName("parameter", partyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((PartyRoleVO)dto.getValueByName("result")) ;
    }
    
    public PartyVO getParty(String party_id) throws Exception
    {
    	DynamicDict dto = getServiceDTO("PartyBO" ,"getParty") ;
		dto.setValueByName("parameter", party_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((PartyVO)dto.getValueByName("result")) ;
		}
    
    public MpDepartVO getMpDepart(String party_id ) throws Exception
    {
    	DynamicDict dto = getServiceDTO("PartyBO" ,"getMpDepart") ;
		dto.setValueByName("parameter", party_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((MpDepartVO)dto.getValueByName("result")) ;
    }

	  public RrBusinessVO getRrBusiness(String rrbusiness_id) throws Exception
	    {
		  DynamicDict dto = getServiceDTO("PartyBO" ,"getRrBusiness") ;
			dto.setValueByName("parameter", rrbusiness_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((RrBusinessVO)dto.getValueByName("result")) ;
			
	    }

	// **************************************ENTITY[Addr]**************************************
	
	    public AddrVO getAddr(String addr_id) throws Exception
	    {
	        DynamicDict dto = getServiceDTO("PartyBO" ,"getAddr") ;
			dto.setValueByName("parameter", addr_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((AddrVO)dto.getValueByName("result"));
	        
	    }
	  /**
	 * 根据参与人标识查询其地址信息
	 * 
	 * @param pparty_id
	 *            参与人标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为AddrVO对象
	 */
	public AddrVO getAddressInfoByPartyId(String pparty_id) throws Exception{
		
			PartyVO voParty = getParty(pparty_id);
			if (voParty == null || voParty.getAddrId() == null
					|| "".equals(voParty.getAddrId())) {
				return null;
			}
			AddrVO vo = getAddr(voParty.getAddrId());
			return vo ;
	}

	/**
	 * 插入地址信息实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建地址实体的ID
	 */
	public String insertAddr(AddrVO vo) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"insertAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

	public void insertAddrWithLogicalAddr( AddrVO vo, LogicalAddrVO[] logicalAddrVOs) throws Exception{

		String addrId = insertAddr( vo );
    	
    	DynamicDict dto = getServiceDTO("PartyBO" ,"insertAddrWithLogicalAddr") ;
    	Map m = new HashMap() ;
    	m.put("addrId", addrId) ;
    	m.put("logicalAddrVOs", logicalAddrVOs) ;
		dto.setValueByName("parameter",  m) ;
		dto = ActionDispatch.dispatch(dto);
    	
	}
	/**
	 * 根据标识更新地址信息
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateAddr(AddrVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除地址实体，同时删除相关逻辑地址
	 * 
	 * @param paddr_id
	 *            地址标识
	 * @return 操作结果
	 */
	public boolean deleteAddr(String paddr_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteAddr") ;
		dto.setValueByName("parameter", paddr_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}


	// **************************************ENTITY[LogicalAddr]**************************************
	/**
	 * 根据地址标识查询逻辑地址实体
	 * 
	 * @param paddr_id 地址标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为指定地址标识对应的逻辑地址列表(LogicalAddr构成的ArrayList)
	 */
	public ArrayList getLogicalAddrsByAddrId(String paddr_id) throws Exception{
	        DynamicDict dto = getServiceDTO("PartyBO" ,"getLogicalAddrsByAddrId") ;
			dto.setValueByName("parameter", paddr_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((ArrayList)dto.getValueByName("result"));
	}
	
	public ArrayList getLogicalAddrsByPartyId( String party_id ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"getLogicalAddrsByAddrId") ;
		dto.setValueByName("parameter", party_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result"));
	}

	/**
	 * 插入逻辑地址实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为逻辑地址ID
	 */
	public String insertLogicalAddr(LogicalAddrVO vo)  throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertLogicalAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result"));
	}

	/**
	 * 根据标识更新实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateLogicalAddr(LogicalAddrVO vo) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"updateLogicalAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除逻辑地址实体
	 * 
	 * @param plogicaladdr_id
	 *            逻辑地址标识
	 * @return 操作结果
	 */
	public boolean deleteLogicalAddr(String plogicaladdr_id) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"deleteLogicalAddr") ;
		dto.setValueByName("parameter", plogicaladdr_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}


	// **************************************ENTITY[OrganizationType]**************************************
	/**
	 * 根据组织分类查询该分类的所有组织类型
	 * 
	 * @param org_class
	 *            组织分类
	 * @return 组织类型列表（OrganizationTypeVO构成的ArrayList）
	 */
	public ArrayList getOrganizationTypesByClass(String org_class) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganizationTypesByClass") ;
		dto.setValueByName("parameter", org_class) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}

	/**
	 * 插入组织类型实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新插入组织类型实体的ID
	 */
	public String insertOrganizationType(OrganizationTypeVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertOrganizationType") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新组织类型实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateOrganizationType(OrganizationTypeVO vo) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"updateOrganizationType") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除组织类型实体
	 * 
	 * @param porganizationtype_id
	 *            组织类型标识
	 * @return 操作结果
	 */
	public boolean deleteOrganizationType(String porganizationtype_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteOrganizationType") ;
		dto.setValueByName("parameter", porganizationtype_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	// **************************************ENTITY[Organization]**************************************
	/**
	 * 根据组织ID查询组织实体
	 * 
	 * @param porganization_id
	 *            组织标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为OrganizationVO
	 */
	 public OrganizationVO getOrganization(String party_id) throws Exception
	    {
		 DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganization") ;
			dto.setValueByName("parameter", party_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((OrganizationVO)dto.getValueByName("result")) ;
	    }
	
	

	/**
	 * 插入组织实体
	 * 同时生成地址、联系信息、参与人、组织、参与人角色
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时返回参与人标识
	 */
	 public void insertTelecomOrganization(OrganizationVO voOrg, AddrVO voAddr) throws Exception
	    {
		 	DynamicDict dto = getServiceDTO("PartyBO" ,"insertTelecomOrganization") ;
		 	Map m = new HashMap() ;
		 	m.put("voOrg", voOrg) ;
		 	m.put("voAddr", voAddr) ;
			dto.setValueByName("parameter", m) ;
			dto = ActionDispatch.dispatch(dto);
	    }
	 
	 public void insertPartnerOrganization(OrganizationVO voOrg, AddrVO voAddr) throws Exception
	    {
		 DynamicDict dto = getServiceDTO("PartyBO" ,"insertPartnerOrganization") ;
		 	Map m = new HashMap() ;
		 	m.put("voOrg", voOrg) ;
		 	m.put("voAddr", voAddr) ;
			dto.setValueByName("parameter", m) ;
			dto = ActionDispatch.dispatch(dto);
	    }
	 
	 public String insertOrganization(OrganizationVO voOrg, AddrVO voAddr) throws Exception
	    {


		 DynamicDict dto = getServiceDTO("PartyBO" ,"insertOrganization") ;
		 		 	Map m = new HashMap() ;
		 		 	m.put("voOrg", voOrg) ;
		 		 	m.put("voAddr", voAddr) ;
		 			dto.setValueByName("parameter", m) ;
		 			dto = ActionDispatch.dispatch(dto);
		 			return ((String)dto.getValueByName("result")) ;
	    }
	 
	 

	    /**
	     * 插入对等运营商组织，同时生成地址、联系信息、参与人、参与人角色、对等运营商
	     */
	    
	 
	 public void insertCompetitorOrganization(OrganizationVO voOrg, AddrVO voAddr) throws Exception
	    {

		 DynamicDict dto = getServiceDTO("PartyBO" ,"insertCompetitorOrganization") ;
		 		 	Map m = new HashMap() ;
		 		 	m.put("voOrg", voOrg) ;
		 		 	m.put("voAddr", voAddr) ;
		 			dto.setValueByName("parameter", m) ;
		 			dto = ActionDispatch.dispatch(dto);
	    }
	public String getAddressIdByPartyId( String partyId) throws Exception{
		OrganizationVO vo = getOrganization(partyId);
		return vo.getAddrId();
	}
	/**
	 * 插入组织实体
	 * 同时生成地址、联系信息、参与人、组织、参与人角色、部门信息
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时返回参与人标识
	 */
	public String insertOrganizationWithDepartment( OrganizationVO voOrg, AddrVO voAddr, MpDepartVO voDepart ) throws Exception {

		DynamicDict dto = getServiceDTO("PartyBO" ,"insertOrganizationWithDepartment") ;
				 	Map m = new HashMap() ;
				 	m.put("voOrg", voOrg) ;
				 	m.put("voAddr", voAddr) ;
				 	m.put("voDepart", voDepart) ;
					dto.setValueByName("parameter", m) ;
					dto = ActionDispatch.dispatch(dto);
					return ((String)dto.getValueByName("result")) ;
	}
	/**
	 * 根据标识更新实体，依次更新地址、参与人、合作伙伴、参与人角色
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateOrganization(OrganizationVO voOrg, AddrVO voAddr) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateOrganization") ;
		Map m = new HashMap() ;
	 	m.put("voOrg", voOrg) ;
	 	m.put("voAddr", voAddr) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
		
	}
	
	public String updateOrganizationWithDepartment( OrganizationVO voOrg, AddrVO voAddr, MpDepartVO voDepart ) throws Exception {
		

		DynamicDict dto = getServiceDTO("PartyBO" ,"updateOrganizationWithDepartment") ;
				 	Map m = new HashMap() ;
				 	m.put("voOrg", voOrg) ;
				 	m.put("voAddr", voAddr) ;
				 	m.put("voDepart", voDepart) ;
					dto.setValueByName("parameter", m) ;
					dto = ActionDispatch.dispatch(dto);

		return ((String)dto.getValueByName("result")) ;
	}
	/**
	 * 删除实体，依次删除：
     * 组织岗位角色控制范围、组织岗位角色、组织岗位、
     * 部门、部门班次、部门终端范围、
     * 联系信息、逻辑地址、地址、参与人识别信息、
     * 渠道分类、合作伙伴、对等运营商、
     * 参与人
	 * 
	 * @param porganization_id
	 *            组织标识
	 * @return 操作结果
	 */
	public long deleteOrganization(String pparty_id) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteOrganization") ;
		dto.setValueByName("parameter", pparty_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Long)dto.getValueByName("result")).longValue() ;
	}
	
	/**
	 * 获取用于TreeList组件的XML格式组织树
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
	 */
	public String getOrganiztionList() throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganiztionList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

    /**
     * 获取用于TreeList组件的XML格式电信组织树
     * 
     * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
     */
	public String getTelecomOrganizationList() throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"getTelecomOrganizationList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}
	
	/**
	 * 根据菜单编码获取菜单对应的权限所在的地域范围
	 * @param menuCode	菜单编码
	 * @param regionType 区域类型:'0':计费线的地域;'1':资源线地域;'2':营销的地域;'3':营销线的组织
	 * @return
	 * @throws Exception
	 */
	public ArrayList getPrivRegionByMenuCode( String menuCode , String regionType ) throws Exception{
//		DynamicDict dto = getDTO("PartyBO" ,"getPrivRegionByMenuCode") ;
//				 	Map m = new HashMap() ;
//				 	m.put("menuCode", menuCode) ;
//				 	m.put("regionType", regionType) ;
//					dto.setValueByName("parameter", m) ;
//					dto = ActionDispatch.dispatch(dto);
//
//		return ((ArrayList)dto.getValueByName("result")) ;
		Set regionSet = new HashSet();
		ArrayList privCodeList = getPrivCodeByType( "3", menuCode ) ;
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState[] roleStates = loginRespond.getRoleState();
		for( int i = 0 ; i < roleStates.length ; i ++ ){
			for( int j = 0 ; j < privCodeList.size() ; j ++ ){
				String privCode = (String)privCodeList.get(j);
				if( privCode.equals(roleStates[i].getPrivilegeCode()) && regionType.equals(roleStates[i].getPermitionFlag())){
					regionSet.add(roleStates[i].getRegionId());
				}
			}
		}
		ArrayList idList = new ArrayList();
		idList.addAll(regionSet) ;
		if( idList.size() == 0 ){
			return idList ;
		}
		ArrayList returnList = getOrganizationByIds( idList, regionType ) ;
		return returnList ;
	}
	
	
	 public ArrayList getOrganizationByIds( ArrayList ids , String regionType ) throws Exception{
		 DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganizationByIds") ;
		 		 	Map m = new HashMap() ;
		 		 	m.put("ids", ids) ;
		 		 	m.put("regionType", regionType) ;
		 			dto.setValueByName("parameter", m) ;
		 			dto = ActionDispatch.dispatch(dto);
		 return ((ArrayList)dto.getValueByName("result"));
	    }
	
	 public ArrayList getPrivCodeByType( String privType, String privCode ) throws Exception {
		 DynamicDict dto = getServiceDTO("PartyBO" ,"getPrivCodeByType") ;
		 		 	Map m = new HashMap() ;
		 		 	m.put("privType", privType) ;
		 		 	m.put("privCode", privCode) ;
		 			dto.setValueByName("parameter", m) ;
		 			dto = ActionDispatch.dispatch(dto);
		 return ((ArrayList)dto.getValueByName("result")) ;
	    }
	    
	 
	public String getRootOrganizationListByPrivControl( String menuCode ) throws Exception {
		ArrayList privCodeList = getPrivCodeByType( "3", menuCode ) ;
		
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");//从Session中获取鉴权服务器返回的登陆结果
		LoginRespond loginRespond = (LoginRespond) obj;
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//如果是超级管理员工号,则返回所有的电信组织根节点
		if( loginRespond.isSuperStaff() ){
			return getTelecomOrganizationListByParentId("-1");
		}
		
		ArrayList partyIds = new ArrayList();
		
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			for( int j = 0 ; j < privCodeList.size() ; j ++ ){
				String privCode = (String)privCodeList.get(j);
				if ((privCode.equals(roleState.getPrivilegeCode())) && "3".equals(roleState.getPermitionFlag())) {
					partyIds.add(roleState.getRegionId());
				}
			}
		}
		
		if(partyIds.size() == 0){
			return "<items/>"; 
		}
		
		return getRootOrganizationListByPartyIds( partyIds ) ;
	}
	
	
	   public String getRootOrganizationListByPartyIds( ArrayList regionIds ) throws Exception{
		   DynamicDict dto = getServiceDTO("PartyBO" ,"getRootOrganizationListByPartyIds") ;
			dto.setValueByName("parameter", regionIds) ;
			dto = ActionDispatch.dispatch(dto);
			return ((String)dto.getValueByName("result")) ;
	    }
    public String getTelecomOrganizationListByParentId( String parentId) throws Exception {
    	DynamicDict dto = getServiceDTO("PartyBO" ,"getTelecomOrganizationListByParentId") ;
		dto.setValueByName("parameter", parentId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
    }
	/**
	 * 获取菜单编码所对应的权限权限所在的组织
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public String getPrivilegeOrganization( String menuCode ) throws Exception { 
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//如果是超级管理员工号登陆,则返回所有的组织结构
		if( loginRespond.isSuperStaff() ){
			return this.getTelecomOrganizationListByParentId("-1");
		}
		RoleState[] roleStates = loginRespond.getRoleState();

		ArrayList orgIdSet = new ArrayList();
		ArrayList privCodes = getPrivCodeByType("3",menuCode);//通过菜单编码获取对应的权限编码

		for( int i = 0 ; i < roleStates.length ; i ++ ){
			for( int j = 0 ; j < privCodes.size() ; j ++ ){
				String privCode = (String)privCodes.get(j);
				if( privCode.equals(roleStates[i].getPrivilegeCode()) && "3".equals(roleStates[i].getPermitionFlag())){
					orgIdSet.add(roleStates[i].getRegionId());//查询和菜单编码权限对应的区域ID
				}
			}
		}
		if( orgIdSet.size() == 0 ){
			return "<items/>";
		}
		return getRootOrganizationListByPartyIds( orgIdSet ) ;
	}

	/**
	 * 
	 * @param staffCode
	 * @param privilegeCode
	 * @param orgType
	 * @return
	 */
	public String getTelecomOrganizationListByCond(String staffCode,String privilegeCode,String orgType) throws Exception{
			Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
    		LoginRespond loginRespond = (LoginRespond)obj;
    		String orgIds = "";
    		RoleState[] roleStates = loginRespond.getRoleState();
    		

    		DynamicDict dto = getServiceDTO("PartyBO" ,"getTelecomOrganizationListByCond") ;
    				 	Map m = new HashMap() ;
    				 	m.put("staffCode", staffCode) ;
    				 	m.put("privilegeCode", privilegeCode) ;
    				 	m.put("orgType", orgType) ;
    				 	m.put("roleStates", roleStates) ;
    				 	
    					dto.setValueByName("parameter", m) ;
    					dto = ActionDispatch.dispatch(dto);

    		return ((String)dto.getValueByName("result")) ;
    	}

    /**
     * 获取用于TreeList组件的XML格式合作伙伴组织树
     * 
     * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
     */
	public String getPartnerOrganiztionList() throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPartnerOrganiztionList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

    /**
     * 获取用于TreeList组件的XML格式对等运营商组织树
     * 
     * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
     */
	public String getCompetitorOrganiztionList() throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getCompetitorOrganiztionList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}


	// **************************************ENTITY[OrganizationPost]**************************************
	/**
	 * 插入组织岗位实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建组织岗位实体标识
	 */
	public String insertOrganizationPost(OrganizationPostVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertOrganizationPost") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据组织岗位标识更新实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateOrganizationPost(OrganizationPostVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateOrganizationPost") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
     * 删除组织岗位，同时删除组织岗位角色范围、组织岗位角色
	 * 
	 * @param porganizationpost_id
	 *            组织岗位标识
	 * @return 操作结果
	 */
	public int deleteOrganizationPost(String porganizationpost_id)  throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteOrganizationPost") ;
		dto.setValueByName("parameter", porganizationpost_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	    }

	public boolean checkOrganizationPostInUsed( String organizationPostId ) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"checkOrganizationPostInUsed") ;
		dto.setValueByName("parameter", organizationPostId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	/**
	 * 根据组织标识查询组织的所有岗位
	 * 
	 * @param party_id
	 *            组织（参与人）标识
	 * @return 指定组织的所有组织岗位实体列表(OrganizationPostVO组成的ArrayList)
	 */
	public ArrayList getPositionListByPartyId(String party_id)  throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPositionListByPartyId") ;
		dto.setValueByName("parameter", party_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result"));
	}

	public ArrayList queryOrgPosition( String orgId, String postName, String state ) throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"queryOrgPosition") ;
	 	Map m = new HashMap() ;
	 	m.put("orgId", orgId) ;
	 	m.put("postName", postName) ;
	 	m.put("state", state) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	

	// **************************************ENTITY[OrgPostSysrole]**************************************

	// **************************************ENTITY[MpDepart]**************************************
	/**
	 * 查询部门实体
	 * 
	 * @param party_id
	 *            组织标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为MpDepartVO
	 */
	public MpDepartVO getMpDepartByPartyId(String party_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getMpDepartByPartyId") ;
				dto.setValueByName("parameter", party_id) ;
				dto = ActionDispatch.dispatch(dto);
				return ((MpDepartVO)dto.getValueByName("result")) ;
	}

	/**
	 * 插入部门实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为部门对应的组织Id
	 */
	public String insertMpDepart(MpDepartVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertMpDepart") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新部门实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateMpDepart(MpDepartVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateMpDepart") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除部门实体，同时删除部门班次、部门终端范围
	 * 
	 * @param pmpdepart_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteMpDepart(String party_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteMpDepart") ;
		dto.setValueByName("parameter", party_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	public ArrayList getDepartByType( String type,String departName, String businessId ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"getDepartByType") ;
	 	Map m = new HashMap() ;
	 	m.put("type", type) ;
	 	m.put("departName", departName) ;
	 	m.put("businessId", businessId) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

return ((ArrayList)dto.getValueByName("result"));


    }
	
	

//	// **************************************ENTITY[MpDepartTerm]**************************************

	// **************************************ENTITY[PartyIdentification]**************************************
	/**
	 * 插入参与人识别信息实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建参与人识别信息标识
	 */
	public String insertPartyIdentification(PartyIdentificationVO vo,String partyRoleId) throws Exception{
DynamicDict dto = getServiceDTO("PartyBO" ,"insertPartyIdentification") ;
				Map m = new HashMap() ;
			 	m.put("v", vo) ;
			 	m.put("partyRoleId", partyRoleId) ;
				dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新参与人识别信息实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updatePartyIdentification(PartyIdentificationVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updatePartyIdentification") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除参与人识别信息实体
	 * 
	 * @param ppartyidentification_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deletePartyIdentification(String ppartyidentification_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deletePartyIdentification") ;
		dto.setValueByName("parameter", ppartyidentification_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 根据参与人角色标识获取参与人识别信息
	 * 
	 * @param pparty_id
	 *            标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为参与人角色标识对应的参与人识别信息列表（PartyIdentificationVO构成的ArrayList）
	 */
	public ArrayList getPartyIdentificationByPartyId(String pparty_role_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPartyIdentificationByPartyId") ;
		dto.setValueByName("parameter", pparty_role_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result"));
	}


	// **************************************ENTITY[PartyRole]**************************************
	/**
	 * 插入参与人角色实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为参与人角色标识
	 */
	public String insertPartyRole(PartyRoleVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertPartyRole") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新参与人角色实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updatePartyRole(PartyRoleVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updatePartyRole") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * 删除实体
	 * 
	 * @param ppartyrole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deletePartyRole(String partyrole_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deletePartyRole") ;
		dto.setValueByName("parameter", partyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * 查询指定参与人的所有参与人角色
	 * 
	 * @param pparty_id
	 *            参与人标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为指定参与人的所有参与人角色列表（PartyRoleVO构成的ArrayList）
	 */
	public ArrayList getPartyRolesByPartyId(String party_id)  throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPartyRolesByPartyId") ;
		dto.setValueByName("parameter", party_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;

	}


//	// **************************************ENTITY[ChnSeg]**************************************

	// **************************************ENTITY[Staff]**************************************
	/**
	 * 根据员工条件查询员工
	 * 
	 * @param voStaff
	 *            查询条件，其不为null和""的字段作为条件，ID的字段为精确匹配(=)，名称、描述等字段为模糊查询(LIKE %...%)
	 * @return 操作结果, 成功时ServiceResult.resultObject为符合条件的员工列表
	 */
	public ArrayList getStaffsByStaffCond(StaffVO voStaff) throws Exception{
		throw new Exception();
	}
	
	public String getOfficeDescByOfficeId( String officeId ) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getOfficeDescByOfficeId") ;
				dto.setValueByName("parameter", officeId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}
	public PageModel queryOfficeList(String officeDesc, int pageIndex, int pageSize ) throws Exception {

		DynamicDict dto = getServiceDTO("PartyBO" ,"queryOfficeList") ;
				 	Map m = new HashMap() ;
				 	m.put("voOrg", officeDesc) ;
				 	m.put("pageIndex", new Integer(pageIndex)) ;
				 	m.put("pageSize", new Integer(pageSize)) ;
					dto.setValueByName("parameter", m) ;
					dto = ActionDispatch.dispatch(dto);

		return ((PageModel)dto.getValueByName("result")) ;
		}
	
	/**
	 * 以分页方式查询员工信息
	 * @param voStaff
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel getStaffsByStaffCondPaginate(StaffVO voStaff,int pageIndex, int pageSize) throws Exception {

		DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffsByStaffCondPaginate") ;
						 	Map m = new HashMap() ;
						 	m.put("voStaff", voStaff) ;
						 	m.put("pageIndex", new Integer(pageIndex)) ;
						 	m.put("pageSize", new Integer(pageSize)) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

				return ((PageModel)dto.getValueByName("result")) ;
	}	


	/**
	 * 插入员工实体，同时插入
     * 参与人、个人、参与人角色
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果
	 */
	public void insertStaff(StaffVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaff") ;
		
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
	}
	
	private void insertStaffWithoutCheck( StaffVO vo ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffWithoutCheck") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
    }
	/**
	 * 批量插入员工
	 * @param orgPartyId	所属组织
	 * @param startStaffCode	起始工号
	 * @param endStaffCode		结束工号
	 * @param staffName			员工名称
	 * @param orgPostId			岗位
	 * @param state					状态
	 * @param effDate				生效日期
	 * @param expDate				结束日期
	 * @param channelSegmentId		渠道
	 * @param password			密码
	 * @throws Exception			
	 */
	public String insertBatchStaff(String orgPartyId , String startStaffCode, String endStaffCode,
			String staffName, String orgPostId, String state,
			String effDate, String expDate, String channelSegmentId, String password, 
    		String lanId,String businessId  ,String countyType,
    		String orgManager,String gender  ) throws Exception{
		
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertBatchStaff") ;
	 	Map m = new HashMap() ;
	 	m.put("orgPartyId", orgPartyId) ;
	 	m.put("startStaffCode", startStaffCode) ;
	 	m.put("endStaffCode", endStaffCode) ;
	 	m.put("staffName", staffName) ;
	 	m.put("orgPostId", orgPostId) ;
	 	m.put("state", state) ;
	 	m.put("effDate", effDate) ;
	 	m.put("expDate", expDate) ;
	 	m.put("channelSegmentId", channelSegmentId) ;
	 	m.put("password", password) ;
	 	m.put("lanId", lanId) ;
	 	m.put("businessId", businessId) ;
	 	m.put("countyType", countyType) ;
	 	m.put("orgManager", orgManager) ;
	 	m.put("gender", gender) ;
	 	
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
		
	}
	
	
	/**
	 * 复制员工
	 * @param sourcePartyRoleId	被复制的员工的参与人角色ID
	 * @param staffCode	工号
	 * @param staffName	姓名
	 * @param password	密码
	 * @throws Exception
	 */
	public void copyStaff( String sourcePartyRoleId, String staffCode, String staffName, String password ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"copyStaff") ;
		Map m = new HashMap() ;
	 	m.put("sourcePartyRoleId", sourcePartyRoleId) ;
	 	m.put("staffCode", staffCode) ;
	 	m.put("staffName", staffName) ;
	 	m.put("password", password) ;
	 	dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
	}
	/**
     * 更新员工，同时更新
     * 参与人、个人、参与人角色
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateStaff(StaffVO vo) throws Exception{
		// 传递过来的StaffVO已经包含了修改后的页面信息的了
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateStaff") ;
		StaffVO b = vo ;
		// 把StaffVO传递给BO，在BO里面通过getValueByName("parameter")获得
		dto.setValueByName("parameter", b) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	 public boolean updateStaffState(String party_role_id,String state) throws Exception
	    {
		 DynamicDict dto = getServiceDTO("PartyBO" ,"checkStaffDefaultPriv") ;
		 Map m = new HashMap() ;
		 	m.put("party_role_id", party_role_id) ;
		 	m.put("state", state) ;
		 	dto.setValueByName("parameter", m) ;
			dto = ActionDispatch.dispatch(dto);
			return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	    }
	
	/**
	 * 
	 * @param ppartyrole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteStaff(String ppartyrole_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaff") ;
		dto.setValueByName("parameter", ppartyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

//	// **************************************ENTITY[Individual]**************************************

	// **************************************ENTITY[WorkingOffice]**************************************
	/**
	 * 插入办公地点实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为办公地点标识
	 */
	public String insertWorkingOffice(WorkingOfficeVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertWorkingOffice") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新办公地点实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateWorkingOffice(WorkingOfficeVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateWorkingOffice") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * 删除办公地点实体
	 * 
	 * @param pworkingoffice_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteWorkingOffice(String workingoffice_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteWorkingOffice") ;
		dto.setValueByName("parameter", workingoffice_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}
//
//	// **************************************ENTITY[OfficeMachine]**************************************

	// **************************************ENTITY[StaffPost]**************************************
	/**
	 * 根据员工标识查询员工岗位
	 * 
	 * @param ppartyrole_id
	 *            员工参与人角色标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为指定员工的所有员工岗位列表（StaffPostVO构成的ArrayList）
	 */
	public ArrayList getStaffPostByPartyRoleId(String ppartyrole_id) throws Exception{
	DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffPostByPartyRoleId") ;
	dto.setValueByName("parameter", ppartyrole_id) ;
	dto = ActionDispatch.dispatch(dto);
	return ((ArrayList)dto.getValueByName("result"));

	}

	/**
	 * 查询当前登陆员工所拥有的岗位
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCurrentStaffPost(String orgPartyId) throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		
		//GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest());
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(helper.getVariable(GlobalVariableHelper.OPER_CODE))){//如果是超级管理员用户,则返回指定组织额所有岗位
		if( loginRespond.isSuperStaff() ){
			return this.getPositionListByPartyId(orgPartyId);
		}
		//return this.getStaffPostByPartyRoleId(helper.getVariable(GlobalVariableHelper.OPER_ID));
		return this.getStaffPostByPartyRoleId( loginRespond.getPartyRoleId() );
	}
	/**
	 * 插入员工岗位实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果
	 */
	public void insertStaffPost(StaffPostVO[] vos) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffPost") ;
		dto.setValueByName("parameter", vos) ;
		dto = ActionDispatch.dispatch(dto);

	}

	/**
	 * 根据标识更新员工岗位实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateStaffPost(StaffPostVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateStaffPost") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	   public StaffVO getStaff(String party_role_id) throws Exception
	    {
		   DynamicDict dto = getServiceDTO("PartyBO" ,"getStaff") ;
			dto.setValueByName("parameter", party_role_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((StaffVO)dto.getValueByName("result"));

	    }
	/**
	 * 删除员工岗位实体
	 * 
	 * @param pstaffpost_id
	 *            实体标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为
	 */
	public boolean deleteStaffPost(String porgpost_id,
			String ppartyrole_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"checkStaffDefaultPriv") ;
		Map m = new HashMap() ;
	 	m.put("porgpost_id", porgpost_id) ;
	 	m.put("ppartyrole_id", ppartyrole_id) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

//	// **************************************ENTITY[OrgRole]**************************************

	// **************************************ENTITY[StaffPriv]**************************************
	/**
	 * 查询员工权限
	 * 
	 * @param ppartyrole_id
	 *            员工参与人角色标识
	 * @return 操作结果，返回参与人权限列表（StaffPrivVO构成的ArrayList）
	 */
	public ArrayList getStaffPrivsByPartyRoleId(String ppartyrole_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffPrivsByPartyRoleId") ;
				dto.setValueByName("parameter", ppartyrole_id) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;

	}

	public String getStaffPrivXMLItemByPartyRoleId(String partyRoleId ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffPrivXMLItemByPartyRoleId") ;
				dto.setValueByName("parameter", partyRoleId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;

	}
	
	public List getStaffPrivilegeRegionInfo(String privilegeId,String partyRoleId ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffPrivilegeRegionInfo") ;
		Map m = new HashMap() ;
	 	m.put("privilegeId", privilegeId) ;
	 	m.put("partyRoleId", partyRoleId) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((List)dto.getValueByName("result")) ;

	}
	/**
	 * 插入员工权限实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工权限标识
	 */
	public String insertStaffPriv(StaffPrivVO vo) throws Exception{

		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffPriv") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 批量插入员工权限和权限范围实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工权限标识
	 */
	public boolean insertBatchStaffPrivAndScopes(ArrayList staffPrivs)  throws Exception{
		if(staffPrivs == null || staffPrivs.isEmpty())
			return false ;
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertBatchStaffPrivAndScopes") ;
		StaffPrivVO[] sarray = new StaffPrivVO[staffPrivs.size()] ;
		for(int i = 0 , j=staffPrivs.size(); i< j ; i++){
			sarray[i] = (StaffPrivVO)staffPrivs.get(i);
		}
		dto.setValueByName("parameter", sarray) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
	  public boolean pinsertBatchStaffPrivAndScopes(StaffPrivVO[] staffPrivs)  throws Exception{
		  DynamicDict dto = getServiceDTO("PartyBO" ,"pinsertBatchStaffPrivAndScopes") ;
			dto.setValueByName("parameter", staffPrivs) ;
			dto = ActionDispatch.dispatch(dto);
			return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	    }
	/**
	 * 插入员工权限和权限范围实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工权限标识
	 */
	public boolean insertStaffPrivAndScopes(String ppartyrole_id,
			String ppriv_id, String pregion_type, String[] pregion_ids)  throws Exception{

		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffPrivAndScopes") ;
						 	Map m = new HashMap() ;
						 	m.put("ppartyrole_id", ppartyrole_id) ;
						 	m.put("ppriv_id", ppriv_id) ;
						 	m.put("pregion_type", pregion_type) ;
						 	m.put("pregion_ids", pregion_ids) ;
						 	
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

							return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 根据标识更新员工权限实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateStaffPriv(StaffPrivVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateStaffPriv") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * 删除员工权限，同时删除相应员工权限范围实体
	 * 
	 * @param pstaffpriv_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteStaffPriv(String partyRoleId, String privId, String regionId, String regionType) throws Exception{

		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaffPriv") ;
						 	Map m = new HashMap() ;
						 	m.put("partyRoleId", partyRoleId) ;
						 	m.put("privId", privId) ;
						 	m.put("regionId", regionId) ;
						 	m.put("regionType", regionType) ;
						 	
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

							return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	  public StaffPrivVO getStaffPriv(String partyRoleId, String privId, String regionId, String regionType) throws Exception
	    {


		  DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffPriv") ;
		  				 	Map m = new HashMap() ;
		  				 	m.put("partyRoleId", partyRoleId) ;
		  				 	m.put("privId", privId) ;
		  				 	m.put("regionId", regionId) ;
		  				 	m.put("regionType", regionType) ;
		  				 	dto.setValueByName("parameter", m) ;
		  					dto = ActionDispatch.dispatch(dto);

		  		return ((StaffPrivVO)dto.getValueByName("result")) ;
	    }
    /**
     * 删除指定员工的所有员工权限，同时删除相应员工权限范围实体
     * 
     * @param ppartyrole_id
     *            员工标识
     * @return 操作结果
     */
    public boolean deleteStaffPrivsByStaff(String ppartyrole_id) throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaffPrivsByStaff") ;
		dto.setValueByName("parameter", ppartyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
    }


	// **************************************ENTITY[StaffRole]**************************************
	/**
	 * 查询员工角色
	 * 
	 * @param ppartyrole_id
	 *            员工参与人角色标识
	 * @return 操作结果, 返回员工对应的所有员工角色列表(StaffRoleVO构成的ArrayList)
	 */
	public ArrayList getStaffRolesByPartyRoleId(String staff_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffRolesByPartyRoleId") ;
				dto.setValueByName("parameter", staff_id) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}

	public String getCurrentStaffRoleListByName(String roleName) throws Exception{
		ArrayList list = getCurrentStaffRolesList();
		if( list == null || list.size() == 0 ){
			return "<items/>";
		}
		ArrayList returnList = new ArrayList();
		for( int i = 0 ; i < list.size() ; i ++ ){
			RolesVO vo = (RolesVO)list.get(i);
			if( vo.getRoleName().indexOf(roleName) != -1){
				returnList.add(vo);
			}
		}
		return XMLSegBuilder.toXmlItems(returnList);
	}
	/**
	 * 查询当前登陆员工所拥有的角色
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCurrentStaffRolesList() throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");

		DynamicDict dto = getServiceDTO("PartyBO" ,"getCurrentStaffRolesList") ;
				dto.setValueByName("parameter", loginRespond) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;


		
	}
	/**
	 * 查询当前登陆员工所拥有的角色
	 * @return
	 * @throws Exception
	 */
	public String getCurrentStaffRoles() throws Exception{
		ArrayList list = getCurrentStaffRolesList();
		return XMLSegBuilder.toXmlItems(list);
	}
	/**
	 * 插入员工角色实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工角色标识
	 */
	public void insertStaffRole(StaffRoleVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffRole") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
	}

	/**
	 * 批量插入员工角色和员工角色范围实体
	 * 
	 * @param
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工角色标识
	 */
	public boolean insertBatchStaffRoleAndScopes(ArrayList simpleStaffRoleVO,String effDate, String expDate) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertBatchStaffRoleAndScopes") ;
		List temp = simpleStaffRoleVO ;
		int s = temp.size() ;
	 	Map m = new HashMap() ;
	 	if(simpleStaffRoleVO == null || simpleStaffRoleVO.isEmpty())
	 		return false ;
	 	
	 	SimpleStaffRoleVO[] ssr = new SimpleStaffRoleVO[simpleStaffRoleVO.size()] ;
	 	
	 	for( int i=0 ,j=simpleStaffRoleVO.size() ; i< j ; i++){
	 		ssr[i] = (SimpleStaffRoleVO)simpleStaffRoleVO.get(i) ;
	 	}
	 	m.put("simpleStaffRoleVO",ssr) ;
	 	m.put("effDate", effDate) ;
	 	m.put("expDate", expDate) ;
	 	dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
	/**
	 * 插入员工角色和员工角色范围实体
	 * 
	 * @param
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工角色标识
	 */
	public boolean insertStaffRoleAndScopes(String ppartyrole_id,
			String prole_id, String pregion_type, String[] pregion_ids) throws Exception{

		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffRoleAndScopes") ;
						 	Map m = new HashMap() ;
						 	m.put("ppartyrole_id", ppartyrole_id) ;
						 	m.put("prole_id", prole_id) ;
						 	m.put("pregion_type", pregion_type) ;
						 	m.put("pregion_ids", pregion_ids) ;
						 	
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

							return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除员工角色，同时删除相应的员工角色范围实体
	 * 
	 * @param pstaffrole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteStaffRole(String party_role_id,String role_id,String region_id,String region_type) throws Exception {


		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaffRole") ;
						 	Map m = new HashMap() ;
						 	m.put("party_role_id", party_role_id) ;
						 	m.put("role_id", role_id) ;
						 	m.put("region_id", region_id) ;
						 	m.put("region_type", region_type) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);
							return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
public RolesVO getRoles(String prole_id) throws Exception {
	DynamicDict dto = getServiceDTO("PartyBO" ,"getRoles") ;
			dto.setValueByName("parameter", prole_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((RolesVO)dto.getValueByName("result")) ;
	
}
    /**
     * 删除指定员工的所有员工角色，同时删除相应的员工角色范围实体
     * 
     * @param ppartyrole_id
     *            员工标识
     * @return 操作结果
     */
    public boolean deleteStaffRolesByStaff(String ppartyrole_id) throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaffRolesByStaff") ;
		dto.setValueByName("parameter", ppartyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

    }

	/**
	 * 检查员工是否已经分配了角色,系统不允许给一个没有任何角色的员工单独分配权限
	 * @param partyRoleId
	 * @return
	 * @throws Exception
	 */
	public boolean checkStaffRoles( String partyRoleId ) throws Exception {
		List list = this.getStaffRolesByPartyRoleId( partyRoleId );
		if( list.size() > 0 ){
			return true;
		}else{
			return false ;
		}
	}
	
	public boolean addWorkOffice( String officeDesc, String officeAddr) throws Exception{
		insertWorkingOffice( new WorkingOfficeVO("", officeDesc, officeAddr ));
		return true ;
	}
	
	public boolean updateWorkOffice( String officeId , String officeDesc, String officeAddr) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateWorkOffice") ;
		Map m = new HashMap() ;
	 	m.put("officeId", officeId) ;
		m.put("officeDesc", officeDesc) ;
		m.put("officeAddr", officeAddr) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}
	
	public boolean deleteWorkOffice( String officeId ) throws Exception { 
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteWorkOffice") ;
		dto.setValueByName("parameter", officeId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
	public String[] getUserOf10000( String type ) throws Exception{
//		从配置文件中读取由10000号建立统一的给CRM的查询知识库的工号和密码
		String staffCode = "";
		String password = "" ;

		if( "query".equalsIgnoreCase(type)){
			staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_CODE");
			password = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_PWD");
		}else if( "admin".equalsIgnoreCase(type) || "manager".equalsIgnoreCase(type)){
			staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_CODE");
			password = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_PWD");
		}
		//对从配置文件中读取出来的密码进行解密
		EncryptRequest req = new EncryptRequest() ;
		req.setFlag("F");
		req.setSecretBuff(password) ;
		EncryptRespond res = OAASProxy.encrypt(req);
		password = res.getStrResultBuff();
		String[] returnValue = new String[2];
		returnValue[0] = staffCode ;
		returnValue[1] = password ;
		return returnValue ;
	}
	
	public String getProjectCode() {
		String projectCode = CrmParamsConfig.getInstance().getParamValue("PROJECT_CODE");
		return projectCode ;
	}

	
    //add by xiaoyong  判断"所属部门"及"发展用户归属"的部门类型是否为社会渠道---20090310
	public String judgePartyIsBelongSecietyChannels(String org_party_id,
			String devUserBelong_id) throws Exception {

//		DynamicDict dto = getServiceDTO("PartyBO" ,"judgePartyIsBelongSecietyChannels") ;
//						 	Map m = new HashMap() ;
//						 	m.put("org_party_id", org_party_id) ;
//							m.put("devUserBelong_id", devUserBelong_id) ;
//							dto.setValueByName("parameter", m) ;
//							dto = ActionDispatch.dispatch(dto);
//				System.out.println("XX=="+((String)dto.getValueByName("result"))) ;			
//				return ((String)dto.getValueByName("result")) ;	
	return "" ;	
	}
	//add end; ---20090310
}
