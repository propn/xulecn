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
	 * �ж�Ա����ĳȨ����ĳ�������Ƿ���Ȩ
	 * @param privCode	Ȩ���������루VARCHAR(50)�������ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	 * @param regionId		
	 * @param regionType	������������	region_type
	 * 															'0':�Ʒ��ߵĵ���
	 *																'1':��Դ�ߵ���
	 *																'2':Ӫ���ĵ���
	 *																'3':Ӫ���ߵ���֯
	 * @param privType		Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
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
		//��Դ������
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
		}else if("3".equals(regionType)){//Ӫ����֯��
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
			if( "0".equals(privType)){//Ȩ��ID
				privVO = daoPriv.findByPrimaryKey(privCode);
				if( privVO != null ){
					privCode = privVO.getPrivCode();
					set.add(privCode);
					//returnList.add( privCode ) ;
				}
			}else if( "1".equals(privType)){//Ȩ�ޱ���
				//returnList.add(privCode);
				set.add(privCode);
			}else if( "2".equals(privType)){//�˵�ID
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
			}else if( "3".equals(privType)){//�˵�����
				MmMenuDAO mmMenuDAO = MmMenuDAOFactory.getMmMenuDAO();
				List menuList = mmMenuDAO.findByCond( "menu_code = '" + privCode + "'");
				if( menuList.size() > 0 ){
					
					//��ѯ�˵�ID
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
	 * ͨ�����ݿ��ѯǰҳ�浱ǰ���ݼ���ǰ�ͻ������µĿɶ��ֶ��б�
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
	 * ��ѯMM_DATA_PRIVILEGE��,��ȡȨ��ID��Ӧ�Ĳ˵�ID
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
	 * ��ȡԱ����Ȩ�޵Ķ��۲���Ŀ¼
	 * @param partyRoleId Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getPricingParamCatalogByPrivilege( DynamicDict dto  ) throws Exception {
		ArrayList privilegeIds =  (ArrayList)dto.getValueByName("privilegeIds");
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
    	return mmDataPrivDAO.getPricingParamCatalogByPrivilege( privilegeIds );//�����Ȩ��,����Ȩ��ID��ѯ���۲���Ŀ¼
	}
	/**
	 * ��ȡԱ����Ȩ�޵ĺ���ȼ�
	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getRnLevelByPrivilege( DynamicDict dto ) throws Exception{
		ArrayList privilegeIds =  (ArrayList)dto.getValueByName("privilegeIds");
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
    	return mmDataPrivDAO.getRnLevelByPrivilege( privilegeIds );//�����Ȩ��,����Ȩ��ID��ѯ����ȼ�
	}
	
	
	/**
	 * ��ȡԱ����Ȩ�޵���֯  ѡ��
	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
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
//	 * ��ȡԱ����Ȩ�޵���֯  ��ͷ����
//	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
//	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList getOrgForNoHeadByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
//		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Y" , request);//��ѯԱ��ӵ�е�����Ȩ��
//		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
//			return new ArrayList() ;
//		}
//		
//		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
//    	return mmDataPrivDAO.getOrgForNoHeadByPrivilege( privilegeIds,noFamilyId);
//	}
//	
//	
	
//	/**
//	 * ��ȡԱ����Ȩ�޵���֯  �������
//	 * @param partyRoleId	Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�Session��
//	 * ��ȡ��ǰ��½Ա���Ĳ����˽�ɫID
//	 * @return
//	 * @throws Exception
//	 */
//	public ArrayList getOrgForDeliverNoByPrivilege( String partyRoleId,String noFamilyId,HttpServletRequest request) throws Exception{
//		
//		ArrayList privilegeIds = this.getStaffPrivilegeIdFromSession(partyRoleId, "99Z" , request);//��ѯԱ��ӵ�е�����Ȩ��
//		if( privilegeIds.size() == 0 ){//���û��Ȩ��,���ؿ��б�
//			return new ArrayList() ;
//		}
//		
//		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
//    	return mmDataPrivDAO.getOrgForDeliverNoByPrivilege(privilegeIds,noFamilyId);
//	}
	
	/**
	 * ��ȡԱ����ĳ��Ʒ��Ȩ�ķ����ṩ
	 * @param partyRoleId Ա��ID,Ԥ������,���򲻻�ʹ��������ݽ����Ĳ����˽�ɫID,���Ǵ�
	 * Session�л�ȡ��ǰ��½Ա���Ĳ����˽�ɫID
	 * @param offerId ��ƷID
	 */
	public ArrayList getOfferServiceByPrivilege(DynamicDict dto ) throws Exception{

		Map m = (Map)dto.getValueByName("parameter") ;
		ArrayList privilegeIds = (ArrayList)m.get("privilegeIds");
		String offerId  = (String)m.get("offerId");
		
		MmDataPrivDAO mmDataPrivDAO = MmDataPrivDAOFactory.getMmDataPrivDAO();
		return mmDataPrivDAO.getOfferServiceByPrivilege( offerId, privilegeIds );//�����Ȩ��,����Ȩ��ID��ѯ�����ṩ
	}

	

	

	// **************************************ENTITY[MmMenu]**************************************
	/**
	 * ����˵�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½��˵���ʶ
	 */
	public String insertMenu(DynamicDict dto ) throws Exception {
		MmMenuVO vo=  (MmMenuVO)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        daoMmMenu.insert(vo);
        return vo.getMenuId() ;
	}

	/**
	 * ���ݱ�ʶ���²˵�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateMenu(DynamicDict dto ) throws Exception {
		MmMenuVO vo=  (MmMenuVO)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        return daoMmMenu.update(vo.getMenuId(), vo);
	}

	/**
	 * ɾ���˵�ʵ��
	 * 
	 * @param pmmmenu_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public long deleteMenu(DynamicDict dto ) throws Exception {
		String menu_id=  (String)dto.getValueByName("parameter");
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        List list = daoMmMenu.findByCond(" super_id = " + menu_id );
        if( list.size() > 0 ){
        	return -1;//����-1��ʾ�˽ڵ����ӽڵ㣬���Ѳ���Ա
        }
        return daoMmMenu.delete(menu_id);
	}
	
	/**
	 * ɾ���˵�ʵ�壬������������Ӳ˵�Ҳһ��ɾ��
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
	 * ��ȡ�ϼ��˵�Id����Ӧ�������¼��˵�.
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
	 * ��ȡ����TreeList�����XML��ʽ�˵���
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList��������в˵����ɵ�XML
	 * �ַ���
	 */
	public String getMenuList(DynamicDict dto) throws Exception {
		MmMenuDAO daoMmMenu = MmMenuDAOFactory.getMmMenuDAO();
        ArrayList alMmMenus = daoMmMenu.getAllMmMenus();
        return XMLSegBuilder.toXmlItems(alMmMenus);
	}

	/**
	 * �жϲ˵������Ƿ��Ѿ���ϵͳ�д���,�����Ӳ˵���ʱ��,��Ҫ�Բ˵��������Ψһ���ж�
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
	 * ����Ȩ��ʵ��
	 * 
	 * @param vo
	 *            Ȩ��ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ
	 */
	public String insertPrivilege(DynamicDict dto ) throws Exception {
		PrivVO vo=  (PrivVO)dto.getValueByName("parameter");
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
	    daoPriv.insert(vo);
	    return vo.getPrivId();
	}

	/**
	 * ����Ȩ�ޱ�ʶ����Ȩ��ʵ��
	 * 
	 * @param vo
	 *            Ȩ��ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ
	 */
	public boolean updatePrivilege(DynamicDict dto ) throws Exception {
		PrivVO vo=  (PrivVO)dto.getValueByName("parameter");
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
        return daoPriv.update(vo.getPrivId(), vo);
	}

	/**
	 * ɾ��Ȩ��ʵ��
	 * 
	 * @param pprivilege_id
	 *            Ȩ�ޱ�ʶ
	 * @return �������
	 */
	public int deletePrivilege(DynamicDict dto ) throws Exception {
		String pprivilege_id=  (String)dto.getValueByName("parameter");
		if( checkPrivilegeInUsed(dto) ){//Ȩ���Ѿ�������ʹ��,����ɾ��
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
	 * �ж�һ��Ȩ���Ƿ��Ѿ���ϵͳʹ��,����Ѿ���ϵͳʹ����,����ɾ��
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
	 * ��ȡ����TreeList�����XML��ʽȨ����
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList���������Ȩ�޹��ɵ�XML�ַ���
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
	 * ��������ɫȨ��ʵ��
	 * 
	 * @param roleId
	 *            ��ɫId
	 * @param privIds
	 *            Ȩ��Id����
	 * @return �������
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
	 * ɾ����ɫȨ��ʵ��
	 * 
	 * @param roleId
	 *            ��ɫId
	 * @param privId
	 *            Ȩ��Id
	 * @return �������
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
	 * ���ݽ�ɫ��ѯ����Ȩ��
	 * 
	 * @param role_id
	 *            ��ɫ��ʶ
	 * @return �������,
	 *         �ɹ�ʱServiceResult.resultObjectΪָ����ɫ��Ӧ������Ȩ�޹��ɵ�������TreeList�����xml�ַ���
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
	 * ���ݽ�ɫ��ѯ����Ȩ��
	 * 
	 * @param role_id
	 *            ��ɫ��ʶ
	 * @return �������,
	 *         �ɹ�ʱServiceResult.resultObjectΪָ����ɫ��Ӧ������Ȩ�޹��ɵ�������TreeList�����xml�ַ���
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
	 * ��ѯ���н�ɫ
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ���н�ɫ���ɵ�������TreeList�����xml�ַ���
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
	 * �����ɫ,�����²���Ľ�ɫ�������ǰ�����û�
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���ɫ��ʶ
	 */
	public String insertRole(DynamicDict dto ) throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
		RolesVO vo = (RolesVO)m.get("vo");
		String partyRoleId  = (String)m.get("partyRoleId");
		
		//�����ɫ��
        RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
        daoRoles.insert(vo);
        
        //����Ա����Ϣ
//        PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
//        PartyRoleVO partyRoleVO = partyRoleDao.findByPrimaryKey(partyRoleId);
        
        //׼������Ա����ɫ��
//        StaffRoleDAO staffRoleDao = StaffRoleDAOFactory.getStaffRoleDAO() ;
//        StaffRoleVO staffRoleVO = new StaffRoleVO() ;
//        staffRoleVO.setPartyRoleId(partyRoleId);//�����˽�ɫID
//        staffRoleVO.setRoleId(vo.getRoleId());//��ɫID
//        staffRoleVO.setEffDate(DateFormatUtils.getFormatedDateTime());//��Ч����(��ǰʱ��)
//        staffRoleVO.setExpDate("2029-01-01 00:00:00");//ʧЧ����
//        staffRoleVO.setState("00A");//״̬Ϊ��Ч
//        staffRoleVO.setRegionType("3");//��������Ϊ��֯��
//        staffRoleVO.setRegionId(partyRoleVO.getOrgPartyId());//����IDΪԱ��������֯
        
        //staffRoleDao.insert(staffRoleVO);//����Ա����ɫ��
        
        return vo.getRoleId();
	}

	/**
	 * ���ݱ�ʶ���½�ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateRole(DynamicDict dto) throws Exception {
		RolesVO vo =  (RolesVO)dto.getValueByName("parameter");
		RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
        return daoRoles.update(vo.getRoleId(), vo);
	}

	/**
	 * ɾ����ɫʵ��
	 * 
	 * @param prole_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public int deleteRole(DynamicDict dto) throws Exception {
		//�����ɫ�Ѿ�������ʹ��,�����޸�
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
	 * �ж�һ����ɫ�Ѿ�������ʹ��
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
	 * ��ѯ���и�λ���
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ���и�λ��񹹳ɵ�������TreeList�����xml�ַ���
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
	 * �����λ���ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���λ����ʶ
	 */
	public String insertPosition(DynamicDict dto) throws Exception {
		PositionVO vo=  (PositionVO)dto.getValueByName("parameter");
		PositionDAO daoPosition = PositionDAOFactory.getPositionDAO();
	    daoPosition.insert(vo);
		StaticAttrCache.getInstance().clearAttrDataCache("DC_POST_TYPE");
		return vo.getPositionId();
	}

	/**
	 * ���ݱ�ʶ���¸�λ���ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
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

	//��ѯ������ӵ�е�Ȩ��VO(StaffPrivVO����)
	public List getPrivilegeVosByPartyRoleId(DynamicDict dto  ) throws Exception {
		String partyRoleId=  (String)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
        return daoStaffPriv.getStaffPrivsByStaff(partyRoleId);
	}
	
	//��ѯ������ӵ�е�Ȩ��Id
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
	
	//��ȡ��ɫ��ӵ�е�Ȩ��ID
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
	 * ɾ����λ���ʵ��
	 * 
	 * @param pposition_id
	 *            ʵ���ʶ
	 * @return �������
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
