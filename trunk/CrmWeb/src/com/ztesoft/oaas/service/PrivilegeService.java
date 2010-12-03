package com.ztesoft.oaas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.vo.CustCtrlInfoVo;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.MmMenuVO;
import com.ztesoft.oaas.vo.PositionVO;
import com.ztesoft.oaas.vo.PrivVO;
import com.ztesoft.oaas.vo.RolesVO;

public class PrivilegeService extends DictService {
	
	public PrivilegeService() {
	}
	
	/**
	 * 判断员工对某权限在某地域上是否有权
	 * @param privCode	权限条件编码（VARCHAR(50)）：根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
	 * @param regionId		
	 * @param regionType	管理区域类型	region_type
	 * 															'0':计费线的地域
	 *																'1':资源线地域
	 *																'2':营销的地域
	 *																'3':营销线的组织
	 * @param privType		权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
	 * @return
	 * @throws Exception
	 */
	public boolean isPrivilegeInRegion(String privCode , String regionId, String regionType, String privType ) throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState roleState[] = loginRespond.getRoleState();
		boolean returnValue = false ;
		ArrayList privCodeList = getPrivCodeByType( privType, privCode ) ;
		Set regionIdSet = new HashSet();
		for( int i = 0 ; i < privCodeList.size() ; i ++ ){
			for( int j = 0 ; j < roleState.length ; j ++ ){
				String pCode = (String)privCodeList.get(i);
				//if( pCode.equals(privCode) && regionId.equals(rId) && regionType.equals(roleState[j].getPermitionFlag())){
				if( pCode.equals(roleState[j].getPrivilegeCode()) && regionType.equals(roleState[j].getPermitionFlag())){
					regionIdSet.add(roleState[j].getRegionId());
				}
			}
		}
		if( regionIdSet.size() == 0 ){
			return false ;
		}
		StringBuffer cond = new StringBuffer();
		Iterator it = regionIdSet.iterator();
		while( it.hasNext()){
			cond.append("," + it.next());
		}
		cond.deleteCharAt(0);
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"isPrivilegeInRegion") ;
	 	Map m = new HashMap() ;
	 	m.put("privCode", privCode) ;
		m.put("regionId", regionId) ;
	 	m.put("regionType", regionType) ;
		m.put("privType", privType) ;
		m.put("cond", cond) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
		
		
	}
	
	 public ArrayList getPrivCodeByType( String privType, String privCode ) throws Exception{
		 DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivCodeByType") ;
		 	Map m = new HashMap() ;
		 	m.put("privType", privType) ;
			m.put("privCode", privCode) ;
			dto.setValueByName("parameter", m) ;
			dto = ActionDispatch.dispatch(dto);

			return ((ArrayList)dto.getValueByName("result")) ;
	 }
	/**
	 * 通过数据库查询前页面当前数据集当前客户类型下的可读字段列表．
	 * @param pageCode
	 * @param datasetCode
	 * @param custTypeId
	 * @return
	 * @throws Exception
	 */
	private ArrayList getCustCtrlDataInfoFromDatabase(String pageCode, String datasetCode, String custTypeId) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeId("" , "99I");
		if( privilegeIds.size() == 0 ){
			return new ArrayList() ;
		}

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getTelecomOrganizationListByParentId") ;
		Map m = new HashMap() ;
	 	m.put("pageCode", pageCode) ;
		m.put("datasetCode", datasetCode) ;
	 	m.put("custTypeId", custTypeId) ;
		m.put("privilegeIds", privilegeIds) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	/**
	 *取当当前页面当前数据集当前客户类型下的可读字段列表．每次查询后缓存在session中． 
	 * @param pageId
	 * @param datasetId
	 * @param custTypeId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCustCtrlDataInfo(String pageCode, String datasetCode, String custTypeId) throws Exception {
		ArrayList custCtrlDataList = (ArrayList)RequestContext.getContext().getHttpSession().getAttribute("CustCtrlDataList");
		ArrayList returnList = new ArrayList() ;
		if( custCtrlDataList != null ){//如果Session中已经有缓存
			for( int i = 0 ; i < custCtrlDataList.size() ; i ++ ){
				CustCtrlInfoVo custCtrlInfoVo = (CustCtrlInfoVo)custCtrlDataList.get(i);
				if( pageCode.equals(custCtrlInfoVo.getPageCode()) &&
						datasetCode.equals(custCtrlInfoVo.getDatasetCode()) &&
						custTypeId.equals(custCtrlInfoVo.getCustTypeId())){
					if( "999999".equals(custCtrlInfoVo.getFieldCode())){//如果缓存里的FieldCode为999999,则返回空列表给服务调用者
						return new ArrayList();
					}else{
						returnList.add(custCtrlInfoVo.getFieldCode());//将查询到的FieldCode加入到返回列表中
					}
				}
			}
		}
		
		//如果Session中没有缓存或者查询Session以后,没有发现参数所要查询的FieldCode,则需继续查询数据库.
		if( custCtrlDataList == null || returnList.size() == 0 ){
			if( custCtrlDataList == null ){
				custCtrlDataList = new ArrayList();
			}
			ArrayList queryDataList = getCustCtrlDataInfoFromDatabase( pageCode, datasetCode, custTypeId ) ;
			if( queryDataList.size() == 0 ){//如果查询数据库也查询不到数据
				//往Session中保存的信息增加一个FieldCode为999999的记录,并返回空列表给服务调用者
				CustCtrlInfoVo vo = new CustCtrlInfoVo() ;
				vo.setCustTypeId(custTypeId) ;
				vo.setDatasetCode(datasetCode) ;
				vo.setPageCode(pageCode) ;
				vo.setFieldCode("999999") ;
				custCtrlDataList.add(vo) ;
				RequestContext.getContext().getHttpSession().setAttribute("CustCtrlDataList",custCtrlDataList) ;
				return new ArrayList() ;
			}else{//如果在数据库中能够查询到符合参数要求的记录
				for( int i = 0 ; i < queryDataList.size() ; i ++ ){
					CustCtrlInfoVo vo = (CustCtrlInfoVo)queryDataList.get(i);
					if( pageCode.equals(vo.getPageCode()) &&
					datasetCode.equals(vo.getDatasetCode()) &&
					custTypeId.equals(vo.getCustTypeId())){
						returnList.add(vo.getFieldCode());//将查询到的FieldCode加入到返回列表中
					}
				}
				if(custCtrlDataList == null){
					//如果Session中没有缓存,则将数据库查询出来的结果缓存到Session
					RequestContext.getContext().getHttpSession().setAttribute("CustCtrlDataList",queryDataList);
				}else{
					custCtrlDataList.addAll(queryDataList);
					//如果Session中有缓存,但是没有当次查询的数据,则将当次查询的数据增加到Session中的
					//缓存中,并更新Session的缓冲.
					RequestContext.getContext().getHttpSession().setAttribute("CustCtrlDataList",custCtrlDataList);
				}
			}
		}
		return returnList ;
	}
	
	/**
	 * 查询MM_DATA_PRIVILEGE表,获取权限ID对应的菜单ID
	 * @param privId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getMenuIdByPrivilege( String privId ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuIdByPrivilege") ;
				dto.setValueByName("parameter", privId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	/**
	 * 获取员工有权限的定价参数目录
	 * @param partyRoleId 预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getPricingParamCatalogByPrivilege( String partyRoleId ) throws Exception {
		
		ArrayList privilegeIds = this.getStaffPrivilegeId(partyRoleId , "99G");//查询员工拥有的类型为定价参数目录的权限
		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
			return new ArrayList() ;
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPricingParamCatalogByPrivilege") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
			}
	/**
	 * 获取员工有权限的号码等级
	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getRnLevelByPrivilege( String partyRoleId ) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeId(partyRoleId , "99H");//查询员工拥有的类型为号码等级的权限
		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
			return new ArrayList() ;
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRnLevelByPrivilege") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	
	/**
	 * 获取员工有权限的组织  选号
	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrganizationByPrivilege( String partyRoleId,String cProductId,HttpServletRequest request) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99X" , request);//查询员工拥有的类型权限
		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
			return new ArrayList() ;
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getOrganizationByPrivilege") ;
		dto.setValueByName("parameter", partyRoleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	/**
	 * 获取员工有权限的组织  号头管理
	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrgForNoHeadByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Y" , request);//查询员工拥有的类型权限
		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
			return new ArrayList() ;
		}
		

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRnLevelByPrivilege") ;
		Map m = new HashMap() ;
	 	m.put("partyRoleId", partyRoleId) ;
		m.put("noFamilyId", noFamilyId) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	
	
	/**
	 * 获取员工有权限的组织  号码分配
	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrgForDeliverNoByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
		
		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Z" , request);//查询员工拥有的类型权限
		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
			return new ArrayList() ;
		}
		

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getOrgForDeliverNoByPrivilege") ;
		Map m = new HashMap() ;
	 	m.put("partyRoleId", partyRoleId) ;
		m.put("noFamilyId", noFamilyId) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	/**
	 * 获取员工对某商品有权的服务提供
	 * @param partyRoleId 员工ID,预留参数,程序不会使用这个传递进来的参与人角色ID,而是从
	 * Session中获取当前登陆员工的参与人角色ID
	 * @param offerId 商品ID
	 */
	public ArrayList getOfferServiceByPrivilege(String partyRoleId, String offerId ) throws Exception{
		ArrayList privilegeIds = this.getStaffPrivilegeId(partyRoleId , "99F");//查询员工拥有的类型为服务提供的权限
		if( privilegeIds.size() == 0 ){
			return new ArrayList() ;
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getOrgForDeliverNoByPrivilege") ;
		Map m = new HashMap() ;
	 	m.put("voOrg", partyRoleId) ;
		m.put("offerId", offerId) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	/**
	 * 从丛书Request中获取Session,从Session中获取员工的某类权限
	 * @param partyRoleId	员工标识(预留)
	 * @param privilegeType	权限使用类型:
	 * 99B	叶子菜单项
	 * 99F	商品服务提供
	 * 99G	定价参数目录
	 * 99H	号码等级
	 * 99I	客户类型
	 * 99J	综合查询
	 * @return
	 */
	public ArrayList getStaffPrivilegeIdFromSession(String partyRoleId , String privilegeType, HttpServletRequest request){
		//从Session中查找从鉴权服务器返回的用户登陆信息	
		return getStaffPrivilegeIdFromSession( partyRoleId , privilegeType, request.getSession()) ;		
	}
	
	/**
	 * 从丛书Request中获取Session,从Session中获取员工的某类权限
	 * @param partyRoleId	员工标识(预留)
	 * @param privilegeType	权限使用类型:
	 * 99B	叶子菜单项
	 * 99F	商品服务提供
	 * 99G	定价参数目录
	 * 99H	号码等级
	 * 99I	客户类型
	 * 99J	综合查询
	 * @return
	 */
	public ArrayList getStaffPrivilegeIdFromSession(String partyRoleId , String privilegeType, HttpSession session){
		//从Session中查找从鉴权服务器返回的用户登陆信息
		LoginRespond respond = (LoginRespond)session.getAttribute("LoginRespond");
		RoleState[] roleStates = respond.getRoleState();
		ArrayList returnList = new ArrayList() ;
		Set privilegeSet = new HashSet();
		for( int i = 0 ; i < roleStates.length ; i ++ ){
			RoleState roleState = roleStates[i];
			if( privilegeType.equals(roleState.getPartyRoleSchema())){//如果权限类型为参数所指定,则存储到返回列表中
				//returnList.add(roleState.getPrivilegeId());
				privilegeSet.add(roleState.getPrivilegeId());//去掉重复的权限ID
			}
		}
		returnList.addAll(privilegeSet);
		return returnList ;		
	}

	/**
	 * 获取员工的某类权限
	 * @param partyRoleId	员工标识(预留)
	 * @param privilegeType	权限使用类型:
	 * 99B	叶子菜单项
	 * 99F	商品服务提供
	 * 99G	定价参数目录
	 * 99H	号码等级
	 * 99I	客户类型
	 * 99J	综合查询
	 * @return
	 */
	public ArrayList getStaffPrivilegeId(String partyRoleId, String privilegeType) {
		return this.getStaffPrivilegeIdFromSession(partyRoleId, privilegeType , RequestContext.getContext().getHttpRequest());
	}
	
	/**
	 * 判断当前用户是否拥有参数制定的权限
	 * @param privilegeId
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeId(String privilegeId) throws Exception {
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			if (privilegeId.equals(roleState.getPrivilegeId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断当前用户是否拥有参数制定的权限
	 * @param privilegeCode
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeCode(String privilegeCode) throws Exception {
		return checkPrivilegeCodeFromSession(RequestContext.getContext().getHttpRequest(), privilegeCode) ;
	}
	public boolean checkPrivilegeCodeFromSession(HttpServletRequest req, String privilegeCode ) throws Exception {
		Object obj = req.getSession().getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int i = 0; i < roleStates.length; i++) {
			RoleState roleState = roleStates[i];
			if (privilegeCode.equals(roleState.getPrivilegeCode())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 这个服务函数用于校验客户端传上来的权限是否为当前用户所拥有的权限, 将当前用户所拥有
	 * 的权限过滤掉,返回当前用户所没有的权限.
	 * @param privilegesToCheck
	 * @return
	 */
	public ArrayList privilegesCheck(String[] privilegesToCheck) {
		ArrayList list = new ArrayList();
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;
		RoleState[] roleStates = loginRespond.getRoleState();
		for (int j = 0; j < privilegesToCheck.length; j++) {
			boolean found = false;
			for (int i = 0; i < roleStates.length; i++) {
				RoleState roleState = roleStates[i];
				if (privilegesToCheck[j].equals(roleState.getPrivilegeCode())) {
					found = true;
					break;
				}
			}
			if (!found) {
				list.add(privilegesToCheck[j]);
			}
		}
		return list;
	}

	/**
	 * 这个服务函数用于校验客户端传上来的权限是否为当前用户所拥有的权限, 将当前用户所拥有的
	 * 权限过滤掉,返回当前用户所没有的权限.
	 * @param privilegesToCheck
	 * @param session
	 * @return
	 */
	public ArrayList privilegesCheckWithSession(String[] privilegesToCheck,
			HttpSession session) {
		ArrayList list = new ArrayList();
		Object obj = session.getAttribute("LoginRespond");
		LoginRespond loginRespond = (LoginRespond) obj;

		RoleState[] roleStates = loginRespond.getRoleState();

		for (int j = 0; j < privilegesToCheck.length; j++) {
			boolean found = false;
			for (int i = 0; i < roleStates.length; i++) {
				RoleState roleState = roleStates[i];
				if (privilegesToCheck[j].equals(roleState.getPrivilegeCode())) {
					found = true;
					break;
				}
			}
			if (!found) {
				list.add(privilegesToCheck[j]);
				found = false;
			}
		}
		return list;
	}

	// **************************************ENTITY[MmMenu]**************************************
	/**
	 * 插入菜单实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建菜单标识
	 */
	public String insertMenu(MmMenuVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertMenu") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新菜单实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateMenu(MmMenuVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updateMenu") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除菜单实体
	 * 
	 * @param pmmmenu_id
	 *            实体标识
	 * @return 操作结果
	 */
	public long deleteMenu(String menu_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteMenu") ;
		dto.setValueByName("parameter", menu_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Long)dto.getValueByName("result")).longValue() ;
	}
	/**
	 * 删除菜单实体，连其下面的子菜单也一并删除
	 * @param menu_id
	 * @return
	 * @throws Exception
	 */
	
	public long deleteMenuWithSubNotes(String menu_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteMenuWithSubMenu") ;
		dto.setValueByName("parameter", menu_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Long)dto.getValueByName("result")).longValue() ;
	}
	
	

	/**
	 * 获取上级菜单Id所对应的所有下级菜单.
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public String getMenuByParentId( String parentId ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuByParentId") ;
		dto.setValueByName("parameter", parentId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;


	}
	/**
	 * 获取用于TreeList组件的XML格式菜单树
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有菜单构成的XML
	 * 字符串
	 */
	public String getMenuList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 判断菜单编码是否已经在系统中存在,在增加菜单的时候,需要对菜单编码进行唯一性判断
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public int checkMenuCodeInUsed(String menuCode ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"checkMenuCodeInUsed") ;
		dto.setValueByName("parameter", menuCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	}


	// **************************************ENTITY[Privilege]**************************************

	public String getMenuDataPrivilege(String privilegeId) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getMenuDataPrivilege") ;
		dto.setValueByName("parameter", privilegeId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;


	}
	public ArrayList getDataPrivilegeByPrivilegeType( String privType,String privId ) throws Exception {DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getDataPrivilegeByPrivilegeType") ;
						 	Map m = new HashMap() ;
						 	m.put("privType", privType) ;
							m.put("privId", privId) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

				return ((ArrayList)dto.getValueByName("result")) ;

	}
	
	public ArrayList getDataPrivilegeByPrivilegeId( String privilegeId,String type ) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getDataPrivilegeByPrivilegeId") ;
		Map m = new HashMap() ;
	 	m.put("privilegeId", privilegeId) ;
		m.put("type", type) ;
		dto.setValueByName("parameter", m) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	public ArrayList getSimpleDataPrivilegeRule() throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getSimpleDataPrivilegeRule") ;
		dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;
	}
	
	public void addDataPrivilege(MmDataPrivVO vo ) throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"addDataPrivilege") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
	}
	
	public void addDataPrivileges(MmDataPrivVO[] dataPrivileges,String privType ) throws Exception{

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"addDataPrivileges") ;
						 	Map m = new HashMap() ;
						 	m.put("dataPrivileges", dataPrivileges) ;
							m.put("privType", privType) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);
	}
	public void addMenuDataPrivileges(ArrayList dataPrivileges) throws Exception {
		MmDataPrivVO[] params = new MmDataPrivVO[dataPrivileges.size()];
		for(int i = 0;i<dataPrivileges.size();i++)
		{
			params[i] = (MmDataPrivVO)dataPrivileges.get(i);
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"addMenuDataPrivileges") ;
				dto.setValueByName("parameter", params) ;
				dto = ActionDispatch.dispatch(dto);
	}
	public void deleteDataPrivilege(ArrayList voLs ) throws Exception{
		MmDataPrivVO[] params = new MmDataPrivVO[voLs.size()];
		for(int i = 0;i<voLs.size();i++)
		{
			params[i] = (MmDataPrivVO)voLs.get(i);
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteDataPrivilege") ;
		dto.setValueByName("parameter", params) ;
		dto = ActionDispatch.dispatch(dto);
	}
	/**
	 * 插入权限实体
	 * 
	 * @param vo
	 *            权限实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为
	 */
	public String insertPrivilege(PrivVO vo) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertPrivilege") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据权限标识更新权限实体
	 * 
	 * @param vo
	 *            权限实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为
	 */
	public boolean updatePrivilege(PrivVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updatePrivilege") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * 删除权限实体
	 * 
	 * @param pprivilege_id
	 *            权限标识
	 * @return 操作结果
	 */
	public int deletePrivilege(String pprivilege_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deletePrivilege") ;
		dto.setValueByName("parameter", pprivilege_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;

	}

	/**
	 * 判断一个权限是否已经被系统使用,如果已经被系统使用了,则不能删除
	 * @param privilegeId
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeInUsed(String privilegeId ) throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"checkPrivilegeInUsed") ;
		dto.setValueByName("parameter", privilegeId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}
	/**
	 * 获取用于TreeList组件的XML格式权限树
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有权限构成的XML字符串
	 */
	public String getPrivilegeList() throws Exception {


		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;	}
	
	public String getPrivilegeListWithRegionRelatByName( String privilegeName ) throws Exception {
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState roleStates[] = loginRespond.getRoleState();
		Set privilegeSet = new HashSet();
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//如果不是超级管理员工号,则记录该工号所拥有的权限,并使用这些权限来查询,如果是超级管理
		//员工号,则返回所有的权限
		if( !loginRespond.isSuperStaff() ){
			for( int i = 0 ; i < roleStates.length ; i ++ ){
				privilegeSet.add(roleStates[i].getPrivilegeId());
			}
		}
		if( privilegeSet.size() == 0 && !loginRespond.isSuperStaff() ){
			return "<items/>";
		}
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeListWithRegionRelatByName") ;
	 	Map m = new HashMap() ;
	 	m.put("privilegeName", privilegeName) ;
		m.put("privilegeSet", privilegeSet) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

		return ((String)dto.getValueByName("result")) ;

	}

	public String getPrivilegeListWithRegionRelatByParentId( String parentId ) throws Exception {
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		RoleState roleStates[] = loginRespond.getRoleState();
		Set privilegeSet = new HashSet();
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//如果不是超级管理员工号,则记录该工号所拥有的权限,并使用这些权限来查询,如果是超级管理
		//员工号,则返回所有的权限
		//if( !superStaffCode.equals(loginRespond.getStaffCode())){
		if( !loginRespond.isSuperStaff() ){
			for( int i = 0 ; i < roleStates.length ; i ++ ){
				privilegeSet.add(roleStates[i].getPrivilegeId());
			}
		}
		//if( privilegeSet.size() == 0 && !superStaffCode.equals(loginRespond.getStaffCode())){
		if( privilegeSet.size() == 0 && !loginRespond.isSuperStaff() ){
			return "<items/>";
		}
		
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeListWithRegionRelatByParentId") ;
	 	Map m = new HashMap() ;
	 	m.put("parentId", parentId) ;
		m.put("privilegeSet", privilegeSet) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);

		return ((String)dto.getValueByName("result")) ;
		
	}
	
	public String getPrivilegeListByParentId(String parentId) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeListByParentId") ;
				dto.setValueByName("parameter", parentId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
 
	}


	// **************************************ENTITY[RolePriv]**************************************

	/**
	 * 插入多个角色权限实体
	 * 
	 * @param roleId
	 *            角色Id
	 * @param privIds
	 *            权限Id数组
	 * @return 操作结果
	 */
	public void insertRolePriv(String roleId, ArrayList privIds, String type)
			throws Exception {
		if(privIds == null || privIds.isEmpty() )
			return ;
		
		String[] sarray = new String[privIds.size()] ;
		for(int i=0 , j=privIds.size() ; i < j ; i++){
			sarray[i] = (String)privIds.get(i) ;
		}
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertRolePriv") ;
						 	Map m = new HashMap() ;
						 	m.put("roleId", roleId) ;
							m.put("privIds", sarray) ;
							m.put("type", type) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);
	}

	/**
	 * 删除角色权限实体
	 * 
	 * @param roleId
	 *            角色Id
	 * @param privId
	 *            权限Id
	 * @return 操作结果
	 */
	public boolean deleteRolePriv(String role_id, String priv_id,
			String priv_type) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteRolePriv") ;

	 	Map m = new HashMap() ;
	 	m.put("role_id", role_id) ;
		m.put("priv_id", priv_id) ;
		m.put("priv_type", priv_type) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 根据角色查询所有权限
	 * 
	 * @param role_id
	 *            角色标识
	 * @return 操作结果,
	 *         成功时ServiceResult.resultObject为指定角色对应的所有权限构成的适用于TreeList组件的xml字符串
	 */
	public String getPrivXMLItemByRoleId(String role_id, String privilegeType)
			throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivXMLItemByRoleId") ;

	 	Map m = new HashMap() ;
	 	m.put("role_id", role_id) ;
		m.put("privilegeType", privilegeType) ;
		dto.setValueByName("parameter", m) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result"));
	}

	/**
	 * 根据角色查询所有权限
	 * 
	 * @param role_id
	 *            角色标识
	 * @return 操作结果,
	 *         成功时ServiceResult.resultObject为指定角色对应的所有权限构成的适用于TreeList组件的xml字符串
	 */
	public ArrayList getPrivByRoleId(String roleId, String privilegeType) throws Exception  {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivByRoleId") ;
						 	Map m = new HashMap() ;
						 	m.put("privilegeType", privilegeType) ;
							m.put("roleId", roleId) ;
							dto.setValueByName("parameter", m) ;
							dto = ActionDispatch.dispatch(dto);

				return ((ArrayList)dto.getValueByName("result")) ;

	}

	public ArrayList getRelatingRoleByRoleId(String roleId)  throws Exception  {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRelatingRoleByRoleId") ;
		dto.setValueByName("parameter",roleId) ;
		dto = ActionDispatch.dispatch(dto);

return ((ArrayList)dto.getValueByName("result")) ;
	}


	// **************************************ENTITY[Role]**************************************
	
	/**
	 * 查询所有角色
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为所有角色构成的适用于TreeList组件的xml字符串
	 */
	public String getXMLRoleList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getXMLRoleList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;

	}

	public String getXMLRoleListByName(String roleName) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getXMLRoleListByName") ;
		dto.setValueByName("parameter", roleName) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;	}

	public ArrayList getRoleList()  throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getRoleList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}

	/**
	 * 插入角色,并将新插入的角色分配给当前操作用户
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建角色标识
	 */
	public String insertRole(RolesVO vo) throws Exception {
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		String partyRoleId = loginRespond.getPartyRoleId() ;
		
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertRole") ;
				 	Map m = new HashMap() ;
				 	m.put("partyRoleId", partyRoleId) ;
				 	m.put("vo", vo) ;
					dto.setValueByName("parameter", m) ;
					dto = ActionDispatch.dispatch(dto);
					return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * 根据标识更新角色实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateRole(RolesVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updateRole") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * 删除角色实体
	 * 
	 * @param prole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public int deleteRole(String prole_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteRole") ;
		dto.setValueByName("parameter", prole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	}
	   public long deleteRoles(String roles_id) throws Exception
	    {
		   DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deleteRoles") ;
			dto.setValueByName("parameter", roles_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((Long)dto.getValueByName("result")).longValue() ;
	    }

	/**
	 * 判断一个角色已经被分配使用
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoleInUsed( String roleId ) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"checkRoleInUsed") ;
		dto.setValueByName("parameter", roleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	// **************************************ENTITY[Position]**************************************
	/**
	 * 查询所有岗位规格
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为所有岗位规格构成的适用于TreeList组件的xml字符串
	 */
	public String getPositionList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPositionList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	public ArrayList getPositionArrayList() throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPositionArrayList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}

	/**
	 * 插入岗位规格实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建岗位规格标识
	 */
	public String insertPosition(PositionVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"insertPosition") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;

	}

	/**
	 * 根据标识更新岗位规格实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updatePosition(PositionVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"updatePosition") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	//查询工号所拥有的权限VO(StaffPrivVO对象)
	public List getPrivilegeVosByPartyRoleId( String partyRoleId ) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeVosByPartyRoleId") ;
				dto.setValueByName("parameter", partyRoleId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((List)dto.getValueByName("result")) ;


	}
	
	//查询工号所拥有的权限Id
	public ArrayList getPrivilegeIdsByPartyRoleId( String partyRoleId ) throws Exception {

		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeIdsByPartyRoleId") ;
				dto.setValueByName("parameter", partyRoleId) ;
				dto = ActionDispatch.dispatch(dto);
				return ((ArrayList)dto.getValueByName("result")) ;


	}
	
	//获取角色所拥有的权限ID
	public ArrayList getPrivilegeIdsByRoleId( String roleId ) throws Exception{
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"getPrivilegeIdsByRoleId") ;
		dto.setValueByName("parameter", roleId) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}
	/**
	 * 删除岗位规格实体
	 * 
	 * @param pposition_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deletePosition(String pposition_id) throws Exception {
		DynamicDict dto = getServiceDTO("PrivilegeBO" ,"deletePosition") ;
		dto.setValueByName("parameter", pposition_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
	
	//检测是否权限编码是否存在
	public boolean hasPrivlegeByCode(String privCode) throws Exception{
		DynamicDict dto=getServiceDTO("PrivilegeBO", "hasPrivlegeByCode");
		dto.setValueByName("parameter", privCode);
		dto=ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}
	
}
