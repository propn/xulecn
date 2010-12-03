package com.ztesoft.oaas.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.oaas.dao.mmdatapriv.MmDataPrivDAO;
import com.ztesoft.oaas.dao.mmdatapriv.MmDataPrivDAOFactory;
import com.ztesoft.oaas.dao.mmdataprivrule.MmDataPrivRuleDAO;
import com.ztesoft.oaas.dao.mmdataprivrule.MmDataPrivRuleDAOFactory;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAO;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAOFactory;
import com.ztesoft.oaas.dao.mmrightmodule.MmRightModuleDAO;
import com.ztesoft.oaas.dao.mmrightmodule.MmRightModuleDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.organizationpost.OrganizationPostDAO;
import com.ztesoft.oaas.dao.organizationpost.OrganizationPostDAOFactory;
import com.ztesoft.oaas.dao.orgpostsysrole.OrgPostSysroleDAO;
import com.ztesoft.oaas.dao.orgpostsysrole.OrgPostSysroleDAOFactory;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAOFactory;
import com.ztesoft.oaas.dao.position.PositionDAO;
import com.ztesoft.oaas.dao.position.PositionDAOFactory;
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.dao.region.RegionDAO;
import com.ztesoft.oaas.dao.region.RegionDAOFactory;
import com.ztesoft.oaas.dao.rolepriv.RolePrivDAO;
import com.ztesoft.oaas.dao.rolepriv.RolePrivDAOFactory;
import com.ztesoft.oaas.dao.roles.RolesDAO;
import com.ztesoft.oaas.dao.roles.RolesDAOFactory;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAO;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAOFactory;
import com.ztesoft.oaas.dao.staffrole.StaffRoleDAO;
import com.ztesoft.oaas.dao.staffrole.StaffRoleDAOFactory;
import com.ztesoft.oaas.exception.OAASError;
import com.ztesoft.oaas.exception.OAASException;
import com.ztesoft.oaas.vo.MmDataPrivRuleVO;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.MmMenuVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.PositionVO;
import com.ztesoft.oaas.vo.PrivVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RolePrivVO;
import com.ztesoft.oaas.vo.RolesVO;
import com.ztesoft.oaas.vo.StaffPrivVO;
import com.ztesoft.oaas.vo.StaffRoleVO;

public class PrivilegeBO extends DictAction{

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
	public boolean isPrivilegeInRegion(DynamicDict dto  ) throws Exception{
		
//		String privCode , String regionId, String regionType, String privType
		Map m = (Map)dto.getValueByName("parameter") ;
		String privCode = (String)m.get("privCode");
		String regionId  = (String)m.get("regionId");
		String regionType = (String)m.get("regionType");
		String privType  = (String)m.get("privType");
		String cond  = (String)m.get("cond");

		boolean returnValue = false ;
		//资源地域线
		if("1".equals(regionType)){
			RegionDAO regionDao = RegionDAOFactory.getRegionDAO();
			RegionVO regionVO = regionDao.findByPrimaryKey(regionId);
			String sourcePathCode = regionVO.getPathCode();
			List list = regionDao.findByCond("region_id in (" + cond.toString() + ")");
			for( int i = 0 ; i < list.size() ; i ++ ){
				String pathCode = ((RegionVO)list.get(i)).getPathCode();
				if( sourcePathCode.startsWith(pathCode)){
					returnValue = true ;
					break ;
				}
			}
		}else if("3".equals(regionType)){//营销组织线
			OrganizationDAO organizationDao = OrganizationDAOFactory.getOrganizationDAO();
			OrganizationVO orgVO = organizationDao.findByPrimaryKey(regionId);
			String sourcePathCode = orgVO.getPathCode();
			List list = organizationDao.findByCond("party_id in (" + cond.toString() + ")");
			for( int i = 0 ; i < list.size() ; i ++ ){
				String pathCode = ((OrganizationVO)list.get(i)).getPathCode();
				if( sourcePathCode.startsWith(pathCode)){
					returnValue = true ;
					break ;
				}
			}
		}
		return returnValue ;
	}
	
	 public ArrayList getPrivCodeByType(DynamicDict dto  ) throws Exception{
	    

		 Map m = (Map)dto.getValueByName("parameter") ;
		 		 String privType = (String)m.get("privType");
		 		 String privCode  = (String)m.get("privCode");

		 ArrayList returnList = new ArrayList() ;
	    	Set set = new HashSet();
	    	
	    	PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
		    PrivVO privVO = null ;
			if( "0".equals(privType)){//权限ID
				privVO = daoPriv.findByPrimaryKey(privCode);
				if( privVO != null ){
					privCode = privVO.getPrivCode();
					set.add(privCode);
					//returnList.add( privCode ) ;
				}
			}else if( "1".equals(privType)){//权限编码
				//returnList.add(privCode);
				set.add(privCode);
			}else if( "2".equals(privType)){//菜单ID
				MmDataPrivDAO dao = MmDataPrivDAOFactory.getMmDataPrivDAO();
				List list = dao.findByCond("data_pkey_1 = '" + privCode + "'");
				for( int i = 0 ; i < list.size() ; i ++ ){
					MmDataPrivVO vo = (MmDataPrivVO)list.get(0);
					String privId = vo.getPrivId() ;
					privVO = daoPriv.findByPrimaryKey(privId);
					if( privVO != null ){
						privCode = privVO.getPrivCode();
						//returnList.add(privCode);
						set.add(privCode);
					}
				}
			}else if( "3".equals(privType)){//菜单编码
				MmMenuDAO mmMenuDAO = MmMenuDAOFactory.getMmMenuDAO();
				List menuList = mmMenuDAO.findByCond( "menu_code = '" + privCode + "'");
				if( menuList.size() > 0 ){
					
					//查询菜单ID
					MmMenuVO menuVO = (MmMenuVO)menuList.get(0);
					String menuId = menuVO.getMenuId();
					
					MmDataPrivDAO dao = MmDataPrivDAOFactory.getMmDataPrivDAO();
					List mmDataPrivList = dao.findByCond("data_pkey_1 = '" + menuId + "'");
					
					for( int i = 0 ; i < mmDataPrivList.size() ; i ++ ){
						MmDataPrivVO vo = (MmDataPrivVO)mmDataPrivList.get(i);
						String privId = vo.getPrivId() ;
						privVO = daoPriv.findByPrimaryKey(privId);
						if( privVO == null ){
							continue ;
						}
						privCode = privVO.getPrivCode();
						//returnList.add(privCode);
						set.add(privCode);
					}
				}
			}
			returnList.addAll(set);
			return returnList ;
	    }
	/**
	 * 通过数据库查询前页面当前数据集当前客户类型下的可读字段列表．
	 * @param pageCode
	 * @param datasetCode
	 * @param custTypeId
	 * @return
	 * @throws Exception
	 */
	private ArrayList getCustCtrlDataInfoFromDatabase(DynamicDict dto ) throws Exception{
		
		
		Map m = (Map)dto.getValueByName("parameter") ;
		 String pageCode = (String)m.get("pageCode");
		 String datasetCode  = (String)m.get("datasetCode");
		 String custTypeId  = (String)m.get("custTypeId");
		 ArrayList privilegeIds  = (ArrayList)m.get("privilegeIds");
		 
		MmDataPrivDAO mmDataPrivDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
		return mmDataPrivDao.getCustCtrlDataInfoFromDatabase( pageCode, datasetCode, custTypeId, privilegeIds ) ;

	}
	
	/**
	 * 查询MM_DATA_PRIVILEGE表,获取权限ID对应的菜单ID
	 * @param privId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getMenuIdByPrivilege(DynamicDict dto ) throws Exception {
		
		 String privId =  (String)dto.getValueByName("parameter");
		MmDataPrivDAO mmDataPriv = MmDataPrivDAOFactory.getMmDataPrivDAO();
		return mmDataPriv.getMenuIdByPrivilege(privId) ;
	}
	/**
	 * 获取员工有权限的定价参数目录
	 * @param partyRoleId 预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getPricingParamCatalogByPrivilege( DynamicDict dto  ) throws Exception {
		ArrayList privilegeIds =  (ArrayList)dto.getValueByName("privilegeIds");
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
    	return mmDataPrivDAO.getPricingParamCatalogByPrivilege( privilegeIds );//如果有权限,根据权限ID查询定价参数目录
	}
	/**
	 * 获取员工有权限的号码等级
	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getRnLevelByPrivilege( DynamicDict dto ) throws Exception{
		ArrayList privilegeIds =  (ArrayList)dto.getValueByName("privilegeIds");
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
    	return mmDataPrivDAO.getRnLevelByPrivilege( privilegeIds );//如果有权限,根据权限ID查询号码等级
	}
	
	
	/**
	 * 获取员工有权限的组织  选号
	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
	 * 获取当前登陆员工的参与人角色ID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getOrganizationByPrivilege( DynamicDict dto ) throws Exception{
//		ArrayList privilegeIds =  (ArrayList)dto.getValueByName("privilegeIds");
//		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
//		mmDataPrivDAO.setDto(dto);
//		return mmDataPrivDAO.getOrganizationByPrivilege( privilegeIds,cProductId );
		return null ;
	}
	
//	/**
//	 * 获取员工有权限的组织  号头管理
//	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
//	 * 获取当前登陆员工的参与人角色ID
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList getOrgForNoHeadByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
//		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Y" , request);//查询员工拥有的类型权限
//		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
//			return new ArrayList() ;
//		}
//		
//		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
//    	return mmDataPrivDAO.getOrgForNoHeadByPrivilege( privilegeIds,noFamilyId);
//	}
//	
//	
	
//	/**
//	 * 获取员工有权限的组织  号码分配
//	 * @param partyRoleId	预留参数,程序不会使用这个传递进来的参与人角色ID,而是从Session中
//	 * 获取当前登陆员工的参与人角色ID
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList getOrgForDeliverNoByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
//		
//		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Z" , request);//查询员工拥有的类型权限
//		if( privilegeIds.size() == 0 ){//如果没有权限,返回空列表
//			return new ArrayList() ;
//		}
//		
//		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
//    	return mmDataPrivDAO.getOrgForDeliverNoByPrivilege(privilegeIds,noFamilyId);
//	}
	
	/**
	 * 获取员工对某商品有权的服务提供
	 * @param partyRoleId 员工ID,预留参数,程序不会使用这个传递进来的参与人角色ID,而是从
	 * Session中获取当前登陆员工的参与人角色ID
	 * @param offerId 商品ID
	 */
	public ArrayList getOfferServiceByPrivilege(DynamicDict dto ) throws Exception{

		Map m = (Map)dto.getValueByName("parameter") ;
		ArrayList privilegeIds = (ArrayList)m.get("privilegeIds");
		String offerId  = (String)m.get("offerId");
		
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
		return mmDataPrivDAO.getOfferServiceByPrivilege( offerId, privilegeIds );//如果有权限,根据权限ID查询服务提供
	}

	

	

	// **************************************ENTITY[MmMenu]**************************************
	/**
	 * 插入菜单实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建菜单标识
	 */
	public String insertMenu(DynamicDict dto ) throws Exception {
		MmMenuVO vo=  (MmMenuVO)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        daoMmMenu.insert(vo);
        return vo.getMenuId() ;
	}

	/**
	 * 根据标识更新菜单实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateMenu(DynamicDict dto ) throws Exception {
		MmMenuVO vo=  (MmMenuVO)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        return daoMmMenu.update(vo.getMenuId(), vo);
	}

	/**
	 * 删除菜单实体
	 * 
	 * @param pmmmenu_id
	 *            实体标识
	 * @return 操作结果
	 */
	public long deleteMenu(DynamicDict dto ) throws Exception {
		String menu_id=  (String)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        List list = daoMmMenu.findByCond(" super_id = " + menu_id );
        if( list.size() > 0 ){
        	return -1;//返回-1表示此节点有子节点，提醒操作员
        }
        return daoMmMenu.delete(menu_id);
	}
	
	/**
	 * 删除菜单实体，如果其下面有子菜单也一并删除
	 * @param dto
	 * @return
	 */
	public long deleteMenuWithSubMenu(DynamicDict dto ) throws Exception{
		String menu_id=  (String)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
		String whereCond=" MENU_ID= " + menu_id +" OR SUPER_ID= " + menu_id ;
        return daoMmMenu.deleteByCond(whereCond);
		
	}

	/**
	 * 获取上级菜单Id所对应的所有下级菜单.
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public String getMenuByParentId(DynamicDict dto  ) throws Exception {
		String parentId=  (String)dto.getValueByName("parameter");
		MmMenuDAO daoMenu = MmMenuDAOFactory.getMmMenuDAO();
    	ArrayList list = (ArrayList)daoMenu.findByCond(" super_id = " + parentId ) ;
    	return XMLSegBuilder.toXmlItems(list);
	}
	/**
	 * 获取用于TreeList组件的XML格式菜单树
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有菜单构成的XML
	 * 字符串
	 */
	public String getMenuList(DynamicDict dto) throws Exception {
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        ArrayList alMmMenus = daoMmMenu.getAllMmMenus();
        return XMLSegBuilder.toXmlItems(alMmMenus);
	}

	/**
	 * 判断菜单编码是否已经在系统中存在,在增加菜单的时候,需要对菜单编码进行唯一性判断
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public int checkMenuCodeInUsed(DynamicDict dto  ) throws Exception {
		String menuCode =  (String)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
    	List list = daoMmMenu.findByCond( "MENU_CODE = '" + menuCode + "'");
    	return list.size();
	}


	// **************************************ENTITY[Privilege]**************************************
	public String getMenuDataPrivilege(DynamicDict dto ) throws Exception {
		String privilegeId=  (String)dto.getValueByName("parameter");
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
		ArrayList list =  mmDataPrivDAO.getMenuDataPrivilege(privilegeId);
    	return XMLSegBuilder.toTreeItems(list);
	}

	public ArrayList getDataPrivilegeByPrivilegeType(DynamicDict dto ) throws Exception {

		Map m = (Map)dto.getValueByName("parameter") ;
				 String privType = (String)m.get("privType");
				 String privId  = (String)m.get("privId");
		MmDataPrivRuleDAO mmDataPrivRuleDAO = MmDataPrivRuleDAOFactory.getMmDataPrivRuleDAO();
    	MmDataPrivRuleVO vo = mmDataPrivRuleDAO.findByPrimaryKey(privType);
    	if( !"".equals(vo.getGetValSql())){
    		MmDataPrivDAO mmDataPriviDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
    		return mmDataPriviDao.findPrivilegeData(vo.getGetValSql(),privId );
    	}else{
    		return null ;
    	}
	}
	
	public ArrayList getDataPrivilegeByPrivilegeId(DynamicDict dto  ) throws Exception {
		
		Map m = (Map)dto.getValueByName("parameter") ;
				 String privilegeId = (String)m.get("privilegeId");
				 String type  = (String)m.get("type");
				 
		MmDataPrivRuleDAO mmDataPrivRuleDAO = MmDataPrivRuleDAOFactory.getMmDataPrivRuleDAO();
		MmDataPrivRuleVO vo = mmDataPrivRuleDAO.findByPrimaryKey(type);
    	if( !"".equals(vo.getTransSql())){
    		MmDataPrivDAO mmDataPriviDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
    		return mmDataPriviDao.findDataByPrivilegeId(vo.getTransSql(), privilegeId );
    	}else{
    		return null ;
    	}
	}
	
	public ArrayList getSimpleDataPrivilegeRule(DynamicDict dto ) throws Exception{
		MmDataPrivRuleDAO mmDataPrivRuleDAO = MmDataPrivRuleDAOFactory.getMmDataPrivRuleDAO();
		
		return mmDataPrivRuleDAO.getSimpleDataPrivilegeRule() ;
	}
	
	public void addDataPrivilege(DynamicDict dto ) throws Exception{
		MmDataPrivVO vo =  (MmDataPrivVO)dto.getValueByName("parameter");
		MmDataPrivDAO mmDataPriviDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
    	mmDataPriviDao.insert(vo ) ;
	}
	
	
	public void addDataPrivileges(DynamicDict dto) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
				 String privType = (String)m.get("privType");
				 MmDataPrivVO[] dataPrivileges  = (MmDataPrivVO[])m.get("dataPrivileges");
				 
		MmDataPrivDAO mmDataPriviDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
		MmDataPrivVO vo = dataPrivileges[0];
		String deleteCond = "PRIVILEGE_ID=" + vo.getPrivId() ;
		mmDataPriviDao.deleteByCond(deleteCond);
    	for( int i = 0; i < dataPrivileges.length ; i ++ ){
    		vo = dataPrivileges[i];
    		mmDataPriviDao.insert( vo ) ;  
    	}
	}
	public void addMenuDataPrivileges(DynamicDict dto ) throws Exception {
		MmDataPrivVO[] dataPrivileges =  (MmDataPrivVO[])dto.getValueByName("parameter");
		MmDataPrivDAO mmDataPriviDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
		for( int i = 0 ; i < dataPrivileges.length ; i ++ ){
			MmDataPrivVO vo = dataPrivileges[i];
			mmDataPriviDao.insert( vo ) ;
		}  
	}
	public void deleteDataPrivilege( DynamicDict dto  ) throws Exception{
		MmDataPrivVO[] voLs =  (MmDataPrivVO[])dto.getValueByName("parameter");
		MmDataPrivDAO mmDataPriviDao = MmDataPrivDAOFactory.getMmDataPrivDAO();
		mmDataPriviDao.delete( voLs );
	}
	/**
	 * 插入权限实体
	 * 
	 * @param vo
	 *            权限实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为
	 */
	public String insertPrivilege(DynamicDict dto ) throws Exception {
		PrivVO vo=  (PrivVO)dto.getValueByName("parameter");
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
	    daoPriv.insert(vo);
	    return vo.getPrivId();
	}

	/**
	 * 根据权限标识更新权限实体
	 * 
	 * @param vo
	 *            权限实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为
	 */
	public boolean updatePrivilege(DynamicDict dto ) throws Exception {
		PrivVO vo=  (PrivVO)dto.getValueByName("parameter");
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        return daoPriv.update(vo.getPrivId(), vo);
	}

	/**
	 * 删除权限实体
	 * 
	 * @param pprivilege_id
	 *            权限标识
	 * @return 操作结果
	 */
	public int deletePrivilege(DynamicDict dto ) throws Exception {
		String pprivilege_id=  (String)dto.getValueByName("parameter");
		if( checkPrivilegeInUsed(dto) ){//权限已经被分配使用,不能删除
			return 1 ;
		}
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        List list = daoPriv.findByCond( " parent_prg_id = " + pprivilege_id) ;
        if( list.size() > 0 ){
        	return -1;
        }
        return daoPriv.delete(pprivilege_id) > 0 ? 0 : -1;
	}

	/**
	 * 判断一个权限是否已经被系统使用,如果已经被系统使用了,则不能删除
	 * @param privilegeId
	 * @return
	 * @throws Exception
	 */
	public boolean checkPrivilegeInUsed(DynamicDict dto  ) throws Exception{
		String privilegeId=  (String)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
		List list =  daoStaffPriv.findByCond("PRIVILEGE_ID=" + privilegeId );
		
		if( list.size() >0 ){
			return true ;
		}
	
		MmRightModuleDAO daoMmRightModule = MmRightModuleDAOFactory.getMmRightModuleDAO();
		
		List list2 =  daoMmRightModule.findByCond("PRIVILEGE_ID=" + privilegeId);
        
		if( list2.size() > 0 ){
			return true ;
		}
		return false ;
	}
	/**
	 * 获取用于TreeList组件的XML格式权限树
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用于TreeList组件的所有权限构成的XML字符串
	 */
	public String getPrivilegeList(DynamicDict dto ) throws Exception {
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        ArrayList alPrivs = daoPriv.getAllPrivs();
        return XMLSegBuilder.toXmlItems(alPrivs);
	}
	
	public String getPrivilegeListWithRegionRelatByName( DynamicDict dto  ) throws Exception {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 		 String privilegeName = (String)m.get("privilegeName");
		 		Set privilegeSet  = (Set)m.get("privilegeSet");
		 		 
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        ArrayList alPrivs = daoPriv.getPrivilegeListWithRegionRelatByName(privilegeName,privilegeSet);
        return XMLSegBuilder.toXmlItems(alPrivs);
	}

	public String getPrivilegeListWithRegionRelatByParentId(DynamicDict dto ) throws Exception {

		Map m = (Map)dto.getValueByName("parameter") ;
				 String parentId = (String)m.get("parentId");
				 Set privilegeSet  = (Set)m.get("privilegeSet");
		
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        ArrayList alPrivs = daoPriv.getPrivilegeListWithRegionRelatByParentId(parentId,privilegeSet);
        return XMLSegBuilder.toXmlItems(alPrivs);
	}
	
	public String getPrivilegeListByParentId(DynamicDict dto) throws Exception {
		String parentId =  (String)dto.getValueByName("parameter");
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        ArrayList alPrivs = daoPriv.getDirectSubNodes(parentId);
        return XMLSegBuilder.toXmlItems(alPrivs);    
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
	public void insertRolePriv(DynamicDict dto )throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
		 String roleId = (String)m.get("roleId");
		 String type  = (String)m.get("type");
		 String[] privIds  = (String[])m.get("privIds");
		
		RolePrivDAO daoRolePriv = RolePrivDAOFactory.getRolePrivDAO();
        for(int i=0; i<privIds.length; i++)
        {
            daoRolePriv.insert(new RolePrivVO(roleId, privIds[i], type));
        }
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
	public boolean deleteRolePriv(DynamicDict dto ) throws Exception {

		Map m = (Map)dto.getValueByName("parameter") ;
				 String role_id = (String)m.get("role_id");
				 String priv_id  = (String)m.get("priv_id");
				 String priv_type  = (String)m.get("priv_type");
		RolePrivDAO daoRolePriv = RolePrivDAOFactory.getRolePrivDAO();
        return daoRolePriv.delete(priv_id, role_id, priv_type)>0;
	}

	/**
	 * 根据角色查询所有权限
	 * 
	 * @param role_id
	 *            角色标识
	 * @return 操作结果,
	 *         成功时ServiceResult.resultObject为指定角色对应的所有权限构成的适用于TreeList组件的xml字符串
	 */
	public String getPrivXMLItemByRoleId(DynamicDict dto )
			throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
				 String privilegeType = (String)m.get("privilegeType");
				 String role_id  = (String)m.get("role_id");
		
		RolePrivDAO daoRolePriv = RolePrivDAOFactory.getRolePrivDAO();
        ArrayList alPrivs = daoRolePriv.getPrivsByRole(role_id,privilegeType);
        if(alPrivs.size()<1)
        {
            alPrivs.add(new PrivVO());
        }
        return XMLSegBuilder.toXmlItems(alPrivs);
	}

	/**
	 * 根据角色查询所有权限
	 * 
	 * @param role_id
	 *            角色标识
	 * @return 操作结果,
	 *         成功时ServiceResult.resultObject为指定角色对应的所有权限构成的适用于TreeList组件的xml字符串
	 */
	public ArrayList getPrivByRoleId(DynamicDict dto) throws Exception{
		
		Map m = (Map)dto.getValueByName("parameter") ;
				 String roleId = (String)m.get("roleId");
				 String privilegeType  = (String)m.get("privilegeType");
		RolePrivDAO daoRolePriv = RolePrivDAOFactory.getRolePrivDAO();
        return daoRolePriv.getPrivsByRole(roleId,privilegeType);
	}

	public ArrayList getRelatingRoleByRoleId(DynamicDict dto ) throws Exception {
		String roleId =  (String)dto.getValueByName("parameter");
		 RolePrivDAO daoRolePriv = RolePrivDAOFactory.getRolePrivDAO();
	     return daoRolePriv.getRelatingRoleByRole(roleId);
	}


	// **************************************ENTITY[Role]**************************************
	
	/**
	 * 查询所有角色
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为所有角色构成的适用于TreeList组件的xml字符串
	 */
	public String getXMLRoleList(DynamicDict dto ) throws Exception {
		 RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
	     ArrayList alRoles = daoRoles.getAllRoles();
	     return XMLSegBuilder.toXmlItems(alRoles);
	}

	public String getXMLRoleListByName(DynamicDict dto ) throws Exception {
		String roleName =  (String)dto.getValueByName("parameter");
		RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
    	ArrayList alRoles = (ArrayList)daoRoles.findByCond( " role_name like '%" + roleName + "%'") ;
    	return XMLSegBuilder.toXmlItems( alRoles ) ;
	}

	public ArrayList getRoleList(DynamicDict dto ) {
		RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
        return daoRoles.getAllRoles();
	}

	/**
	 * 插入角色,并将新插入的角色分配给当前操作用户
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建角色标识
	 */
	public String insertRole(DynamicDict dto ) throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
		RolesVO vo = (RolesVO)m.get("vo");
		String partyRoleId  = (String)m.get("partyRoleId");
		
		//插入角色表
        RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
        daoRoles.insert(vo);
        
        //查找员工信息
//        PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
//        PartyRoleVO partyRoleVO = partyRoleDao.findByPrimaryKey(partyRoleId);
        
        //准备插入员工角色表
//        StaffRoleDAO staffRoleDao = StaffRoleDAOFactory.getStaffRoleDAO() ;
//        StaffRoleVO staffRoleVO = new StaffRoleVO() ;
//        staffRoleVO.setPartyRoleId(partyRoleId);//参与人角色ID
//        staffRoleVO.setRoleId(vo.getRoleId());//角色ID
//        staffRoleVO.setEffDate(DateFormatUtils.getFormatedDateTime());//生效日期(当前时间)
//        staffRoleVO.setExpDate("2029-01-01 00:00:00");//失效日期
//        staffRoleVO.setState("00A");//状态为有效
//        staffRoleVO.setRegionType("3");//区域类型为组织线
//        staffRoleVO.setRegionId(partyRoleVO.getOrgPartyId());//区域ID为员工所在组织
        
        //staffRoleDao.insert(staffRoleVO);//插入员工角色表
        
        return vo.getRoleId();
	}

	/**
	 * 根据标识更新角色实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateRole(DynamicDict dto) throws Exception {
		RolesVO vo =  (RolesVO)dto.getValueByName("parameter");
		RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
        return daoRoles.update(vo.getRoleId(), vo);
	}

	/**
	 * 删除角色实体
	 * 
	 * @param prole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public int deleteRole(DynamicDict dto) throws Exception {
		//如果角色已经被分配使用,则不能修改
		if( this.checkRoleInUsed(dto)){
			return 1 ;
		}
		if (deleteRoles(dto) > 0) {
			return 0;
		} else {
			return -1;
		}
	}
	   public long deleteRoles(DynamicDict dto) throws Exception
	    {
		   String roles_id = (String)dto.getValueByName("parameter");
	        RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
//	        OrgPostSysroleDAO orgPostSysroleDAO = OrgPostSysroleDAOFactory.getOrgPostSysroleDAO();
//	        
//	        long count = orgPostSysroleDAO.countByCond( " role_id = " + roles_id );
//	        if( count > 0 ){
//	        	throw new OAASException( OAASError.ROLE_WITH_POSITION );
//	        }
	        RolePrivDAO rolePrivDao = RolePrivDAOFactory.getRolePrivDAO();
	        
	        return daoRoles.delete(roles_id) + rolePrivDao.deleteByCond("ROLE_ID=" + roles_id);
	    }

	/**
	 * 判断一个角色已经被分配使用
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoleInUsed( DynamicDict dto ) throws Exception {
		String roleId =(String)dto.getValueByName("parameter");
		StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
    	List list = daoStaffRole.findByCond("ROLE_ID=" + roleId);
    	if( list.size() > 0 ){
    		return true ;
    	}else {
    		return false ;
    	}
	}

	// **************************************ENTITY[Position]**************************************
	/**
	 * 查询所有岗位规格
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为所有岗位规格构成的适用于TreeList组件的xml字符串
	 */
	public String getPositionList(DynamicDict dto) throws Exception {
		PositionDAO daoPosition = PositionDAOFactory.getPositionDAO();
        ArrayList alPositions = daoPosition.getAllPositions();
        return XMLSegBuilder.toXmlItems(alPositions);
	}

	public ArrayList getPositionArrayList(DynamicDict dto) {
		PositionDAO daoPosition = PositionDAOFactory.getPositionDAO();
        return daoPosition.getAllPositions();
	}

	/**
	 * 插入岗位规格实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建岗位规格标识
	 */
	public String insertPosition(DynamicDict dto) throws Exception {
		PositionVO vo=  (PositionVO)dto.getValueByName("parameter");
		PositionDAO daoPosition = PositionDAOFactory.getPositionDAO();
	    daoPosition.insert(vo);
		StaticAttrCache.getInstance().clearAttrDataCache("DC_POST_TYPE");
		return vo.getPositionId();
	}

	/**
	 * 根据标识更新岗位规格实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updatePosition(DynamicDict dto) throws Exception {
		PositionVO vo=  (PositionVO)dto.getValueByName("parameter");
		PositionDAO daoPosition = PositionDAOFactory.getPositionDAO();
		
        boolean result = daoPosition.update(vo.getPositionId(), vo);
		if (result) {
			StaticAttrCache.getInstance().clearAttrDataCache("DC_POST_TYPE");
			return true;
		} else {
			return false;
		}
	}

	//查询工号所拥有的权限VO(StaffPrivVO对象)
	public List getPrivilegeVosByPartyRoleId(DynamicDict dto  ) throws Exception {
		String partyRoleId=  (String)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
        return daoStaffPriv.getStaffPrivsByStaff(partyRoleId);
	}
	
	//查询工号所拥有的权限Id
	public ArrayList getPrivilegeIdsByPartyRoleId( DynamicDict dto ) throws Exception {
		String partyRoleId =  (String)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
	    List list = daoStaffPriv.getStaffPrivsByStaff(partyRoleId);
		ArrayList returnList = new ArrayList();
		for( int i = 0 ; i < list.size() ; i ++ ){
			StaffPrivVO vo = (StaffPrivVO)list.get(i);
			returnList.add(vo.getPrivId());
		}
		return returnList ;
	}
	
	//获取角色所拥有的权限ID
	public ArrayList getPrivilegeIdsByRoleId(DynamicDict dto  ) throws Exception{
		String roleId =  (String)dto.getValueByName("parameter");
		RolePrivDAO daoRolePriv = RolePrivDAOFactory.getRolePrivDAO();
        ArrayList list = daoRolePriv.getPrivsByRole(roleId,"0");
		
		ArrayList returnList = new ArrayList() ;
		for( int i = 0 ; i < list.size() ; i ++ ){
			PrivVO vo = (PrivVO)list.get(i) ;
			returnList.add(vo.getPrivId());
		}
		return returnList ;
	}
	/**
	 * 删除岗位规格实体
	 * 
	 * @param pposition_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deletePosition(DynamicDict dto) throws Exception {
		String pposition_id =  (String)dto.getValueByName("parameter");
		OrganizationPostDAO orgPostDao = OrganizationPostDAOFactory.getOrganizationPostDAO();
	
		List list = orgPostDao.findByCond("POSITION_ID=" + pposition_id);
		if( list == null || list.isEmpty()){
			return false ;
		}
		
		 PositionDAO daoPosition = PositionDAOFactory.getPositionDAO();
	     long result =  daoPosition.delete(pposition_id);
		if (result > 0) {
			StaticAttrCache.getInstance().clearAttrDataCache("DC_POST_TYPE");
		}
		return true;
	}
	
	public boolean hasPrivlegeByCode(DynamicDict dto) throws Exception{
		String privCode =  (String)dto.getValueByName("parameter");
		PrivDAO dao=PrivDAOFactory.getPrivDAO();
    	List list = dao.findByCond( " privilege_code = '" + privCode + "'");
    	if(list.size()>0)return true;
    	else return false;
	}
	
}
