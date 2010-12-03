package com.ztesoft.oaas.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.DesEncrypt;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.oaas.dao.addr.AddrDAO;
import com.ztesoft.oaas.dao.addr.AddrDAOFactory;
import com.ztesoft.oaas.dao.chnseg.ChnSegDAO;
import com.ztesoft.oaas.dao.chnseg.ChnSegDAOFactory;
import com.ztesoft.oaas.dao.competitor.CompetitorDAO;
import com.ztesoft.oaas.dao.competitor.CompetitorDAOFactory;
import com.ztesoft.oaas.dao.contactmedium.ContactMediumDAO;
import com.ztesoft.oaas.dao.contactmedium.ContactMediumDAOFactory;
import com.ztesoft.oaas.dao.individual.IndividualDAO;
import com.ztesoft.oaas.dao.individual.IndividualDAOFactory;
import com.ztesoft.oaas.dao.logicaladdr.LogicalAddrDAO;
import com.ztesoft.oaas.dao.logicaladdr.LogicalAddrDAOFactory;
import com.ztesoft.oaas.dao.mmdatapriv.MmDataPrivDAO;
import com.ztesoft.oaas.dao.mmdatapriv.MmDataPrivDAOFactory;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAO;
import com.ztesoft.oaas.dao.mmmenu.MmMenuDAOFactory;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAO;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAOFactory;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAO;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAOFactory;
import com.ztesoft.oaas.dao.officemachine.OfficeMachineDAO;
import com.ztesoft.oaas.dao.officemachine.OfficeMachineDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.organizationpost.OrganizationPostDAO;
import com.ztesoft.oaas.dao.organizationpost.OrganizationPostDAOFactory;
import com.ztesoft.oaas.dao.organizationtype.OrganizationTypeDAO;
import com.ztesoft.oaas.dao.organizationtype.OrganizationTypeDAOFactory;
import com.ztesoft.oaas.dao.orgpostsysrole.OrgPostSysroleDAO;
import com.ztesoft.oaas.dao.orgpostsysrole.OrgPostSysroleDAOFactory;
import com.ztesoft.oaas.dao.partner.PartnerDAO;
import com.ztesoft.oaas.dao.partner.PartnerDAOFactory;
import com.ztesoft.oaas.dao.party.PartyDAO;
import com.ztesoft.oaas.dao.party.PartyDAOFactory;
import com.ztesoft.oaas.dao.partyidentification.PartyIdentificationDAO;
import com.ztesoft.oaas.dao.partyidentification.PartyIdentificationDAOFactory;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAOFactory;
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.dao.region.RegionDAO;
import com.ztesoft.oaas.dao.region.RegionDAOFactory;
import com.ztesoft.oaas.dao.roles.RolesDAO;
import com.ztesoft.oaas.dao.roles.RolesDAOFactory;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAOFactory;
import com.ztesoft.oaas.dao.rrdepartterm.RrDepartTermDAO;
import com.ztesoft.oaas.dao.rrdepartterm.RrDepartTermDAOFactory;
import com.ztesoft.oaas.dao.staff.StaffDAO;
import com.ztesoft.oaas.dao.staff.StaffDAOFactory;
import com.ztesoft.oaas.dao.staffpost.StaffPostDAO;
import com.ztesoft.oaas.dao.staffpost.StaffPostDAOFactory;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAO;
import com.ztesoft.oaas.dao.staffpriv.StaffPrivDAOFactory;
import com.ztesoft.oaas.dao.staffrole.StaffRoleDAO;
import com.ztesoft.oaas.dao.staffrole.StaffRoleDAOFactory;
import com.ztesoft.oaas.dao.workingoffice.WorkingOfficeDAO;
import com.ztesoft.oaas.dao.workingoffice.WorkingOfficeDAOFactory;
import com.ztesoft.oaas.exception.OAASError;
import com.ztesoft.oaas.exception.OAASException;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.utils.OAASProxy;
import com.ztesoft.oaas.vo.AddrVO;
import com.ztesoft.oaas.vo.CompetitorVO;
import com.ztesoft.oaas.vo.ContactMediumVO;
import com.ztesoft.oaas.vo.IndividualVO;
import com.ztesoft.oaas.vo.LogicalAddrVO;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.MmMenuVO;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationPostVO;
import com.ztesoft.oaas.vo.OrganizationTypeVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartnerVO;
import com.ztesoft.oaas.vo.PartyIdentificationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.PartyVO;
import com.ztesoft.oaas.vo.PrivVO;
import com.ztesoft.oaas.vo.RegionVO;
import com.ztesoft.oaas.vo.RolesVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.SimpleStaffRoleVO;
import com.ztesoft.oaas.vo.StaffPostVO;
import com.ztesoft.oaas.vo.StaffPrivVO;
import com.ztesoft.oaas.vo.StaffRoleVO;
import com.ztesoft.oaas.vo.StaffVO;
import com.ztesoft.oaas.vo.WorkingOfficeVO;

public class PartyBO extends DictAction {

	public boolean checkStaffDefaultPriv(DynamicDict dto) throws Exception {
		String partyRoleId = (String)dto.getValueByName("parameter");
		PrivDAO privDao = PrivDAOFactory.getPrivDAO();
		
		List list =  privDao.getStaffPrivs( partyRoleId ) ;
		for( int i = 0 ; i < list.size() ; i ++ ){
			PrivVO vo = (PrivVO)list.get(i); 
			if( "DEFPRV".equals(vo.getPrivCode())){
				return true ;
			}
		}
		return false;
	}


	
	/**
	 * 获取相关对象的path_code字段值
	 * @param id		主键
	 * @param type	类型:1表示REGION表,2表示ORGANIZATION表
	 * @return
	 * @throws Exception
	 */
	public String getPathCode(DynamicDict dto ) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		String id = (String) m.get("id");
		String type = (String) m.get("type");;
		dto.setValueByName("id", id) ;
		if( "2".equals(type)){
			dto.setValueByName("parameter", id) ;
			OrganizationVO vo = getOrganization(dto) ;
			return vo.getPathCode();
		}else if( "1".equals(type)){
			RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
			RegionVO vo =  daoRegion.findByPrimaryKey(id);
			
			return vo.getPathCode();
		}/*else if("3".equals(type)){
			ChnRegionDAO dao = SmDAOFactory.getInstance().getChnRegionDAO();
			ChnRegionVO vo = dao.findByPrimaryKey(id);
			return vo.getPathCode();
		}*/else {
			return "";
		}
	}
	

	/**
	 * 获取一个组织的所有上级组织的ID
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
	public String[] getAllParentOrganizationIds(DynamicDict dto ) throws Exception{
		OrganizationVO vo = getOrganization(dto );
		String pathCode = vo.getPathCode();
		return  pathCode.replace('.',',').split(",");
	}

	/*
	 * 判断一个员工号是否已经被使用了
	 */
	public boolean checkStaffCode( DynamicDict dto) throws Exception {
		String staffCode = (String)dto.getValueByName("parameter");
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
	    StaffVO vo = daoStaff.getStaffByStaffCode( staffCode );
	    return !(vo == null || "".equals(vo.getPartyRoleId()));
	}
	
	/*
	 * 激活员工状态
	 */
	public int passwordActivation( DynamicDict dto ) throws Exception {
			String staffCode = (String)dto.getValueByName("parameter");
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		
		
		//找不到工号，返回页面1
    	StaffVO staffVO = daoStaff.getStaffByStaffCode( staffCode ) ;
    	if( staffVO == null ){
    		return 1 ;
    	}
    	
    	//此工号找不到对应角色，返回页面1
    	PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
    	PartyRoleVO partyRoleVO = daoPartyRole.findByPrimaryKey( staffVO.getPartyRoleId());
    	if( partyRoleVO == null ){
    		return 1 ;
    	}
    	//DB中如果partyRole表loginStatus状态为0(表示已有效，不用激活)返回页面2
    	if( "0".equals(partyRoleVO.getLoginStatus() )){
    		return 2 ;
    	}
    	//list里如果元素不为空表示此工号的激活期没过90天限制
    	List list = daoPartyRole.findByCond("party_role_id = " + partyRoleVO.getPartyRoleId() + " AND  update_time  < ( " + DatabaseFunction.currentDay() + " - 89)");
    	if(list.size() > 0 ){
    		partyRoleVO.setLoginStatus("2");//2表示被激活
    		partyRoleVO.setUpdateTime(DateFormatUtils.getFormatedDateTime());//设置激活时间
    		daoPartyRole.update(partyRoleVO.getPartyRoleId(), partyRoleVO);
    		return 0;
    	}else{
    		partyRoleVO.setLoginStatus("0");
    		daoPartyRole.update(partyRoleVO.getPartyRoleId(), partyRoleVO);
    		return 2 ;
    	}
	}


	
	
	public RegionVO getRegionById( DynamicDict dto ) throws Exception { 
		String id= (String)dto.getValueByName("parameter");
		RegionDAO daoRegion = RegionDAOFactory.getRegionDAO();
		return daoRegion.findByPrimaryKey(id);
	}
	
    public PartyRoleVO getPartyRole(DynamicDict dto) throws Exception
    {
    	String partyrole_id = (String)dto.getValueByName("parameter");
    	
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        return daoPartyRole.findByPrimaryKey(partyrole_id);
    }
    
    public PartyVO getParty(DynamicDict dto) throws Exception
    {
    	String party_id = (String)dto.getValueByName("parameter");
        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
        return daoParty.findByPrimaryKey(party_id);
    }
    
    public MpDepartVO getMpDepart(DynamicDict dto ) throws Exception
    {
    	String party_id = (String)dto.getValueByName("parameter");
        MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
        return daoMpDepart.findByPrimaryKey(party_id);
    }
	
	  public RrBusinessVO getRrBusiness(DynamicDict dto) throws Exception
	    {
		  String rrbusiness_id = (String)dto.getValueByName("parameter");
	        RrBusinessDAO daoRrBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
	        return daoRrBusiness.findByPrimaryKey(rrbusiness_id);
	    }

	// **************************************ENTITY[Addr]**************************************
	
	    public AddrVO getAddr(DynamicDict dto) throws Exception
	    {
	    	String addr_id = (String)dto.getValueByName("parameter");
	        AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
	        return daoAddr.findByPrimaryKey(addr_id);
	    }
	
	/**
	 * 插入地址信息实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建地址实体的ID
	 */
	public String insertAddr(DynamicDict dto) throws Exception{
		AddrVO vo  = (AddrVO)dto.getValueByName("parameter");
		AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
        daoAddr.insert(vo);
        return vo.getAddrId();
	}

	public void insertAddrWithLogicalAddr(DynamicDict dto) throws Exception{
		Map m = (Map)dto.getValueByName("parameter");
		String addrId = (String)m.get("addrId");
		LogicalAddrVO[] logicalAddrVOs = (LogicalAddrVO[])m.get("logicalAddrVOs") ;
    	LogicalAddrDAO dao = LogicalAddrDAOFactory.getLogicalAddrDAO();
    	
    	for( int i = 0 ; i < logicalAddrVOs.length; i ++ ){
    		logicalAddrVOs[i].setAddrId(addrId);
    		dao.insert( logicalAddrVOs[i] );
    	}
	}
	/**
	 * 根据标识更新地址信息
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateAddr(DynamicDict dto) throws Exception{
		
		AddrVO vo=(AddrVO)dto.getValueByName("parameter");
		AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
		return daoAddr.update(vo.getAddrId(), vo);
	}

	/**
	 * 删除地址实体，同时删除相关逻辑地址
	 * 
	 * @param paddr_id
	 *            地址标识
	 * @return 操作结果
	 */
	public boolean deleteAddr(DynamicDict dto) throws Exception{
		String paddr_id = (String)dto.getValueByName("parameter");
		long recsDeleted = 0;
	    ContactMediumDAO daoContactMedium = ContactMediumDAOFactory.getContactMediumDAO();
	    recsDeleted += daoContactMedium.deleteByAddr(paddr_id);
	    LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
	    recsDeleted += daoLogicalAddr.deleteByAddr(paddr_id);
	    AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
	    return  recsDeleted + daoAddr.delete(paddr_id) > 0;
		
	}


	// **************************************ENTITY[LogicalAddr]**************************************
	/**
	 * 根据地址标识查询逻辑地址实体
	 * 
	 * @param paddr_id 地址标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为指定地址标识对应的逻辑地址列表(LogicalAddr构成的ArrayList)
	 */
	public ArrayList getLogicalAddrsByAddrId(DynamicDict dto) throws Exception{
		String paddr_id= (String)dto.getValueByName("parameter");
	    LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
	    
	    return daoLogicalAddr.getLogicalAddrsByAddr(paddr_id);	
	}
	
	public ArrayList getLogicalAddrsByPartyId(DynamicDict dto  ) throws Exception {
		
		String party_id =  (String)dto.getValueByName("parameter");
		ContactMediumDAO contactMediumDAO = ContactMediumDAOFactory.getContactMediumDAO();
	
		List contactMediumList = contactMediumDAO.findByCond( "PARTY_ID = " + party_id );
		 ContactMediumVO contactMediumVO = null ;
		 if( contactMediumList.size() > 0 ){
			 contactMediumVO = (ContactMediumVO)contactMediumList.get(0);
		 }
		 ArrayList list = new ArrayList();
		 if( contactMediumVO == null ){
			 return list ;
		 }
		String addId = contactMediumVO.getAddrId();
		
		  LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		
		  return  daoLogicalAddr.getLogicalAddrsByAddr(addId);
	}

	/**
	 * 插入逻辑地址实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为逻辑地址ID
	 */
	public String insertLogicalAddr(DynamicDict dto )  throws Exception {
		
		LogicalAddrVO vo =  (LogicalAddrVO)dto.getValueByName("parameter");
		LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		daoLogicalAddr.insert(vo);
        return vo.getLogicalAddrId();
	}

	/**
	 * 根据标识更新实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateLogicalAddr(DynamicDict dto ) throws Exception{
		LogicalAddrVO vo =  (LogicalAddrVO)dto.getValueByName("parameter");
		LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		
		return daoLogicalAddr.update(vo.getLogicalAddrId(), vo);
	}

	/**
	 * 删除逻辑地址实体
	 * 
	 * @param plogicaladdr_id
	 *            逻辑地址标识
	 * @return 操作结果
	 */
	public boolean deleteLogicalAddr(DynamicDict dto ) throws Exception{
		String plogicaladdr_id = (String)dto.getValueByName("parameter");
		LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		
		return daoLogicalAddr.delete(plogicaladdr_id)>0;	
	}


	// **************************************ENTITY[OrganizationType]**************************************
	/**
	 * 根据组织分类查询该分类的所有组织类型
	 * 
	 * @param org_class
	 *            组织分类
	 * @return 组织类型列表（OrganizationTypeVO构成的ArrayList）
	 */
	public ArrayList getOrganizationTypesByClass(DynamicDict dto ) throws Exception{
		String org_class =  (String)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		 
		return daoOrganizationType.getOrgTypesByOrgClass(org_class);
	}

	/**
	 * 插入组织类型实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新插入组织类型实体的ID
	 */
	public String insertOrganizationType(DynamicDict dto ) throws Exception{
		OrganizationTypeVO vo =  (OrganizationTypeVO)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		
		daoOrganizationType.insert(vo);
        return vo.getOrgTypeId();
	}

	/**
	 * 根据标识更新组织类型实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateOrganizationType(DynamicDict dto ) throws Exception{
		OrganizationTypeVO vo =  (OrganizationTypeVO)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		
		
        return daoOrganizationType.update(vo.getOrgTypeId(),vo);
	}

	/**
	 * 删除组织类型实体
	 * 
	 * @param porganizationtype_id
	 *            组织类型标识
	 * @return 操作结果
	 */
	public boolean deleteOrganizationType(DynamicDict dto ) throws Exception{
		String porganizationtype_id=(String)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		
		return daoOrganizationType.delete(porganizationtype_id)>0;
	}

	// **************************************ENTITY[Organization]**************************************
	/**
	 * 根据组织ID查询组织实体
	 * 
	 * @param porganization_id
	 *            组织标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为OrganizationVO
	 */
	 public OrganizationVO getOrganization(DynamicDict dto) throws Exception
	    {
		 	
		 	String party_id = (String)dto.getValueByName("parameter") ;
	        OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
	       
	        OrganizationVO voOrg = daoOrganization.findByPrimaryKey(party_id);
	        
	        OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
	        
	        
	        OrganizationTypeVO voOrgType = daoOrganizationType.findByPrimaryKey(voOrg.getOrgTypeId());
	        voOrg.setOrgTypeName(voOrgType.getOrgTypeName());
	        
	        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
	        
	        PartyVO voParty = daoParty.findByPrimaryKey(party_id);
	        
	        voOrg.setEffDate(voParty.getEffDate());
	        voOrg.setAddDescription(voParty.getAddDescription());
	        if(!"00".equals(voOrg.getOrgClass()))
	        {
	            PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	            ArrayList alPartyRoles = daoPartyRole.getPartyRolesByParty(party_id);
	            if(alPartyRoles.size()>0)
	            {
	                PartyRoleVO voPartyRole = (PartyRoleVO)alPartyRoles.get(0);
	                voOrg.setOrgManager(voPartyRole.getOrgManager());
	                voOrg.setExpDate(voPartyRole.getExpDate());
	                if("01".equals(voOrg.getOrgClass()))
	                {
	                    PartnerDAO daoPartner = PartnerDAOFactory.getPartnerDAO();
	                    voOrg.setPartnerType(daoPartner.findByPrimaryKey(voPartyRole.getPartyRoleId()).getPartnerType());
	                }
	            }
	        }
	        return voOrg;
	    }
	
	

	/**
	 * 插入组织实体
	 * 同时生成地址、联系信息、参与人、组织、参与人角色
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时返回参与人标识
	 */
	 public void insertTelecomOrganization(DynamicDict dto ) throws Exception
	    {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
	    	//插入地址表ADDRESS
	        AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
	        daoAddr.insert(voAddr);
	        
	        //插入参与人表(PARTY)
	   
	        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
	        PartyVO voParty = new PartyVO(null, voOrg.getOrgName(), voOrg.getEffDate(), voOrg.getState(), voOrg.getStateDate(), voAddr.getAddrId(), voOrg.getAddDescription());
	        daoParty.insert(voParty);
	        
	        //插入联系信息表(CONTACT_MEDIUM)
	        ContactMediumDAO daoContactMedium = ContactMediumDAOFactory.getContactMediumDAO();
	        daoContactMedium.insert(new ContactMediumVO(null, voAddr.getAddrId(), voParty.getPartyId()));
	        voOrg.setPartyId(voParty.getPartyId());
	        voOrg.setAddrId(voAddr.getAddrId());
	        
	        //插入组织表(ORGANIZATION)
	        OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
	        daoOrganization.insert(voOrg);
	    }
	 
	 public void insertPartnerOrganization(
			 DynamicDict dto ) throws Exception
	    {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
	        insertTelecomOrganization(dto);
	        
	        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        PartyRoleVO voPartyRole = new PartyRoleVO(
	                null,
	                voOrg.getPartyId(),
	                voOrg.getParentPartyId(),
	                voOrg.getOrgName(),
	                "90C",
	                voOrg.getOrgManager(),
	                voOrg.getEffDate(),
	                voOrg.getOrgContent(),
	                voOrg.getState(),
	                voOrg.getEffDate(),
	                voOrg.getExpDate(),
	                "",//办公地点标识
	                "",//登录密码
	                "",//密码有效期类型
	                "",//密码上次修改时间
	                "",//登录状态
	                "",//登录次数
	                "",//登录次数限制
	                ""//输入错误密码时间
	                );
	        daoPartyRole.insert(voPartyRole);

	        PartnerDAO daoPartner = PartnerDAOFactory.getPartnerDAO();
	        daoPartner.insert(new PartnerVO(voPartyRole.getPartyRoleId(), voOrg
	                .getOrgCode(), voOrg.getPartnerType(), voOrg.getOrgContent()));
	    }
	 
	 public String insertOrganization(
			 DynamicDict dto ) throws Exception
	    {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
	        if("01".equals(voOrg.getOrgClass()))
	        {
	            insertPartnerOrganization(dto);
	        }
	        else if("02".equals(voOrg.getOrgClass()))
	        {
	            insertCompetitorOrganization(dto);
	        }
	        else
	        {
	            insertTelecomOrganization(dto);
	        }
	        return voOrg.getPartyId();
	    }
	 
	 

	    /**
	     * 插入对等运营商组织，同时生成地址、联系信息、参与人、参与人角色、对等运营商
	     */
	    
	 
	 public void insertCompetitorOrganization(DynamicDict dto ) throws Exception
	    {
	        insertTelecomOrganization(dto);
	        Map m = (Map)dto.getValueByName("parameter") ;
			 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
			 AddrVO voAddr  = (AddrVO)m.get("voAddr");
			 
	        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        PartyRoleVO voPartyRole = new PartyRoleVO(
	                null,
	                voOrg.getPartyId(),
	                voOrg.getParentPartyId(),
	                voOrg.getOrgName(),
	                "90B",
	                voOrg.getOrgManager(),
	                voOrg.getEffDate(),
	                voOrg.getOrgContent(),
	                voOrg.getState(),
	                voOrg.getEffDate(),
	                voOrg.getExpDate(),
	                "",//办公地点标识
	                "",//登录密码
	                "",//密码有效期类型
	                "",//密码上次修改时间
	                "",//登录状态
	                "",//登录次数
	                "",//登录次数限制
	                ""//输入错误密码时间
	        		);
	        daoPartyRole.insert(voPartyRole);
	        CompetitorDAO daoCompetitor = CompetitorDAOFactory.getCompetitorDAO();
	        daoCompetitor.insert(new CompetitorVO(
	                voPartyRole.getPartyRoleId(),
	                voOrg.getOrgCode(),
	                voOrg.getOrgContent()));
	    }

	/**
	 * 插入组织实体
	 * 同时生成地址、联系信息、参与人、组织、参与人角色、部门信息
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时返回参与人标识
	 */
	public String insertOrganizationWithDepartment(DynamicDict dto  ) throws Exception {
		
//		OrganizationVO voOrg, AddrVO voAddr, MpDepartVO voDepart 
		Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
		 MpDepartVO voDepart  = (MpDepartVO)m.get("voDepart");
		
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		long num=daoOrganization.countByCond( " org_name = '" + voOrg.getOrgName()+"' and org_level="+voOrg.getOrgLevel()  );//wxh
    	
		if(num>0){
			return "-1"; 
		}
		
		String partyId = insertOrganization( dto );
    	MpDepartDAO departmentDao = MpDepartDAOFactory.getMpDepartDAO();
    	voDepart.setPartyId( partyId );
    	departmentDao.insert( voDepart ) ;
    	return partyId ;
	}
	/**
	 * 根据标识更新实体，依次更新地址、参与人、合作伙伴、参与人角色
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateOrganization(DynamicDict dto ) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
		//更新地址
		AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
		if(voAddr.getAddrId()==null || "".equals(voAddr.getAddrId()))
		{
		    voAddr.setAddrId(voOrg.getAddrId());
		}
		daoAddr.update(voAddr.getAddrId(), voAddr);

		//更新参与人
		PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
		PartyVO voParty = new PartyVO(voOrg.getPartyId(), voOrg.getOrgName(), voOrg.getEffDate(), voOrg.getState(), voOrg.getStateDate(), voAddr.getAddrId(), voOrg.getAddDescription());
		daoParty.update(voParty.getPartyId(), voParty);

		//更新组织
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO voOrgOld = daoOrganization.findByPrimaryKey(voOrg.getPartyId());
		if(!"00".equals(voOrgOld.getOrgClass()))
		{
		    PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
		    ArrayList alPartyRoles = daoPartyRole.getPartyRolesByParty(voParty.getPartyId());
		    if(alPartyRoles.size()>0)
		    {
		        PartyRoleVO voPartyRole = (PartyRoleVO)alPartyRoles.get(0);
		        voPartyRole.setSPartyId(voOrg.getPartyId());
		        voPartyRole.setOrgPartyId(voOrg.getPartyId());
		        voPartyRole.setPartyRoleName(voOrg.getOrgName());
		        if("01".equals(voOrgOld.getOrgClass()))
		        {
		            PartnerDAO daoPartner = PartnerDAOFactory.getPartnerDAO();
		            daoPartner.update(
		                    voPartyRole.getPartyRoleId(),
		                    new PartnerVO(
		                            voPartyRole.getPartyRoleId(),
		                            voOrg.getOrgCode(),
		                            voOrg.getPartnerType(),
		                            voOrg.getOrgContent()));
		            voPartyRole.setPartyRoleType("90C");
		        }
		        else if("02".equals(voOrgOld.getOrgClass()))
		        {
		            voPartyRole.setPartyRoleType("90B");
		        }
		        voPartyRole.setOrgManager(voOrg.getOrgManager());
		        voPartyRole.setCreateDate(voOrg.getEffDate());
		        voPartyRole.setMemo(voOrg.getOrgContent());
		        voPartyRole.setState(voOrg.getState());
		        voPartyRole.setEffDate(voOrg.getEffDate());
		        voPartyRole.setExpDate(voOrg.getExpDate());
		        daoPartyRole.update(voPartyRole.getPartyRoleId(), voPartyRole);
		    }
		}

		MpDepartDAO departDAO = MpDepartDAOFactory.getMpDepartDAO();
		MpDepartVO departVO = departDAO.findByPrimaryKey(voOrg.getPartyId());
		if( departVO != null ){
			departDAO.delete(departVO.getPartyId());
		}

		return daoOrganization.update( voOrg );

	}
	
	// boolean updateOrganizationWithDepartment
	public String updateOrganizationWithDepartment( DynamicDict dto ) throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
		 MpDepartVO voDepart  = (MpDepartVO)m.get("voDepart");
		
		
		voDepart.setPartyId(voOrg.getPartyId());
		
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO vo=daoOrganization.findByPrimaryKey(voOrg.getPartyId());
        long num= 0 ;
        if(voOrg.getOrgName().equals(vo.getOrgName())){
        	num = 0;//没有更名
        }
    	num=daoOrganization.countByCond( " org_name = '" + voOrg.getOrgName()+"' and org_level="+voOrg.getOrgLevel() );//wxh		
		
		if(num>0){
			return "-1"; 
		}
		updateOrganization( dto );
    	MpDepartDAO departDAO = MpDepartDAOFactory.getMpDepartDAO();
    	MpDepartVO departVO = departDAO.findByPrimaryKey(voDepart.getPartyId());
    	if( departVO == null ){
    		departDAO.insert(voDepart);
    		return "1" ;
    	}else{
    		return departDAO.update( voDepart.getPartyId(), voDepart ) == true ? "1" : "0" ;
    	}
		
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

	public long deleteOrganization(DynamicDict dto ) throws Exception
    {
		String party_id = (String)dto.getValueByName("parameter");
        long recsDeleted = 0;
        
        OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
        //如果组织下面还有下级组织,则抛出异常,不再往下执行
        List list = daoOrganization.findByCond( "parent_party_id = " + party_id );
        if( list.size() > 0 ){
        	//throw new OAASException( OAASError.EXISTS_SUB_ORGANIZATION_ERROR );
		return -3;	
        }
        //如果该组织还有员工,则不能删除
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        list = daoPartyRole.findByCond( " org_party_id = " + party_id );
        if( list.size() > 0 ){
        	//throw new OAASException( OAASError.ORGANIZATION_WITH_STAFF_ERROR);
		return -4;
        }        
        
        //组织岗位
        OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
        //获取要删除的组织下的组织岗位
        ArrayList alOrgPost = daoOrgPost.getOrgPostsByOrganization(party_id);
        if(alOrgPost!=null && alOrgPost.size()>0)
        {
            OrgPostSysroleDAO daoOrgPostSysrole = OrgPostSysroleDAOFactory.getOrgPostSysroleDAO();
            OrganizationPostVO voPost;
            Iterator iterPost = alOrgPost.iterator();
            while(iterPost.hasNext())
            {
                voPost = (OrganizationPostVO)iterPost.next();
                recsDeleted = recsDeleted + daoOrgPostSysrole.deleteByOrgPost(voPost.getOrgPostId());
            }
            recsDeleted = recsDeleted + daoOrgPost.deleteByOrganization(party_id);
        }
        
        MpDepartTermDAO daoMpDepartTerm = MpDepartTermDAOFactory.getMpDepartTermDAO();
        recsDeleted = recsDeleted + daoMpDepartTerm.deleteByParty(party_id);
        
        DynamicDict dtoi = dto ;
        
        RrDepartTermDAO daoRrDepartTerm = RrDepartTermDAOFactory.getRrDepartTermDAO();
        recsDeleted = recsDeleted + daoRrDepartTerm.deleteByParty(party_id);
        
        MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
        recsDeleted = recsDeleted + daoMpDepart.delete(party_id);
        
        recsDeleted = recsDeleted + daoOrganization.delete(party_id);
        
        ContactMediumDAO daoContactMedium = ContactMediumDAOFactory.getContactMediumDAO();
        ArrayList alContactMedium = daoContactMedium.getContactMediumsByParty(party_id);
        recsDeleted = recsDeleted + daoContactMedium.deleteByParty(party_id);
        
        AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
        LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
        if(alContactMedium!=null && alContactMedium.size()>0)
        {
            Iterator iterCM = alContactMedium.iterator();
            ContactMediumVO voCM;
            while(iterCM.hasNext())
            {
                voCM = (ContactMediumVO)iterCM.next();
                recsDeleted = recsDeleted + daoLogicalAddr.deleteByAddr(voCM.getAddrId());
                recsDeleted = recsDeleted + daoAddr.delete(voCM.getAddrId());
            }
        }
        
        PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
        recsDeleted = recsDeleted + daoPartyIdentification.deleteByParty(party_id);

        //获取要删除的组织VO对象
        recsDeleted = recsDeleted + daoPartyRole.deleteByParty(party_id);
        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
        return recsDeleted + daoParty.delete(party_id); 
    }

	/**
	 * 获取用于TreeList组件的XML格式组织树
	 * 
	 * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
	 */
	public String getOrganiztionList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}

    /**
     * 获取用于TreeList组件的XML格式电信组织树
     * 
     * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
     */
	public String getTelecomOrganizationList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllTelecomOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}
	
	 public ArrayList getOrganizationByIds( DynamicDict dto ) throws Exception{



		 Map m = (Map)dto.getValueByName("parameter") ;
		 ArrayList ids = (ArrayList)m.get("ids");
		 String regionType  = (String)m.get("regionType");	
		 
		 String cond = "";
	    	for( int i = 0 ; i < ids.size() ; i ++ ){
	    		cond = cond + ids.get(i) + "," ;
	    	}
	    	if( !"".equals(cond) ){
	    		cond = cond.substring(0,cond.length() - 1);
	    	}
	    	List returnList ;
	    	if( "3".equals(regionType)){
	    		OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();
	    		returnList = orgDao.findByCond(" party_id in ( " + cond + ")");
	    	}else{
	    		RegionDAO regionDao = RegionDAOFactory.getRegionDAO();
	    		returnList = regionDao.findByCond(" region_id in (" + cond + ")");
	    	}
	    	return (ArrayList)returnList ;
	    }
	
	 public ArrayList getPrivCodeByType( DynamicDict dto  ) throws Exception{

		 Map m = (Map)dto.getValueByName("parameter") ;
		 String privCode = (String)m.get("privCode");
		 		String privType  = (String)m.get("privType");

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
	    
	 

	
	
	   public String getRootOrganizationListByPartyIds(DynamicDict dto  ) throws Exception{
		   ArrayList regionIds = (ArrayList)dto.getValueByName("parameter");
		   OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		   StringBuffer idCond = new StringBuffer();
	    	for( int i = 0 ; i < regionIds.size() ; i ++ ){
	    		idCond.append(",").append((String)regionIds.get(i));
	    	}
	    	idCond.deleteCharAt(0);
	    	ArrayList list = (ArrayList)daoOrganization.findByCond(" PARTY_ID IN(" + idCond.toString() + ") ORDER BY PATH_CODE");
	    	return XMLSegBuilder.toXmlItems(list);
	    }
    public String getTelecomOrganizationListByParentId(DynamicDict dto  ) throws Exception {
    	String parentId = (String)dto.getValueByName("parameter");
    	if(parentId == null || "".equals(parentId))
    		parentId = (String)dto.getValueByName("parentId");
    	OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
    	ArrayList alOrganizations = daoOrganization.getTelecomOrganizationListByParentId(parentId) ;
        return XMLSegBuilder.toXmlItems(alOrganizations);
    }
	/**
	 * 获取菜单编码所对应的权限权限所在的组织
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public String getPrivilegeOrganization( DynamicDict dto ) throws Exception { 
		Map m = (Map)dto.getValueByName("parameter") ;
		LoginRespond loginRespond = (LoginRespond)m.get("loginRespond");
		String menuCode  = (String)m.get("menuCode");
		 
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//如果是超级管理员工号登陆,则返回所有的组织结构
		if( loginRespond.isSuperStaff() ){
			dto.setValueByName("parentId", "-1") ;
			return this.getTelecomOrganizationListByParentId(dto);
		}
		RoleState[] roleStates = loginRespond.getRoleState();

		ArrayList orgIdSet = new ArrayList();
		m.put("privType", "-3");
		m.put("privCode", menuCode);
		ArrayList privCodes = getPrivCodeByType(dto);//通过菜单编码获取对应的权限编码

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
		dto.setValueByName("parameter", orgIdSet) ;
		return getRootOrganizationListByPartyIds( dto ) ;
	}

	/**
	 * 
	 * @param staffCode
	 * @param privilegeCode
	 * @param orgType
	 * @return
	 */
	public String getTelecomOrganizationListByCond(DynamicDict dto ) throws Exception{
		    		Map m = (Map)dto.getValueByName("parameter") ;
    		String staffCode = (String)m.get("staffCode");
    		String privilegeCode  = (String)m.get("privilegeCode");
    		String orgType  = (String)m.get("orgType");
    		String orgIds  = (String)m.get("orgIds");
    		
    		
			OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
			ArrayList alOrganizations = daoOrganization.getTelecomOrganizationByCond( orgIds,orgType);
	    	return XMLSegBuilder.toXmlItems( alOrganizations );
	}

    /**
     * 获取用于TreeList组件的XML格式合作伙伴组织树
     * 
     * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
     */
	public String getPartnerOrganiztionList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllPartnerOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}

    /**
     * 获取用于TreeList组件的XML格式对等运营商组织树
     * 
     * @return 操作结果, 成功时ServiceResult.resultObject为适用与TreeList组件的xml字符串
     */
	public String getCompetitorOrganiztionList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllCompetitorOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}


	// **************************************ENTITY[OrganizationPost]**************************************
	/**
	 * 插入组织岗位实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建组织岗位实体标识
	 */
	public String insertOrganizationPost(DynamicDict dto ) throws Exception {
		OrganizationPostVO vo = (OrganizationPostVO)dto.getValueByName("parameter");
		OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
		List list = daoOrgPost.findByCond( "POSITION_ID = " + vo.getPositionId() + " AND PARTY_ID = " + vo.getPartyId() );
        if( list.size() > 0 ){
        	return "-1" ;
        }
        daoOrgPost.insert(vo);
        return vo.getOrgPostId();
	}

	/**
	 * 根据组织岗位标识更新实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateOrganizationPost(DynamicDict dto ) throws Exception{
		OrganizationPostVO vo = (OrganizationPostVO)dto.getValueByName("parameter");
		 
		OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
		return daoOrgPost.update(vo.getOrgPostId(), vo);
	}

	/**
     * 删除组织岗位，同时删除组织岗位角色范围、组织岗位角色
	 * 
	 * @param porganizationpost_id
	 *            组织岗位标识
	 * @return 操作结果
	 */
	public int deleteOrganizationPost(DynamicDict dto )  throws Exception{
		String porganizationpost_id =  (String)dto.getValueByName("parameter");
		if( this.checkOrganizationPostInUsed(dto)){
			return 1 ;
		}
		
		 long recsDeleted = 0;
	        OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
	        OrgPostSysroleDAO daoOrgPostSysrole = OrgPostSysroleDAOFactory.getOrgPostSysroleDAO();
	        
	        ArrayList alOrgPostSysrole = daoOrgPostSysrole.getRolesByOrgPost(porganizationpost_id);
	        if(alOrgPostSysrole != null && alOrgPostSysrole.size()>0)
	        {
	            throw new OAASException( OAASError.ORG_POST_WITH_STAFF );
	        }
	        return recsDeleted + daoOrgPost.delete(porganizationpost_id) > 0 ? 0 : -1;
	    }

	public boolean checkOrganizationPostInUsed(DynamicDict dto ) throws Exception{
		 String organizationPostId  =  (String)dto.getValueByName("parameter");
		StaffPostDAO staffPostDAO = StaffPostDAOFactory.getStaffPostDAO();
		List list = staffPostDAO.findByCond( " ORG_POST_ID = " + organizationPostId ) ;
		if( list.size() > 0 ){
			return true ;
		}else{
			return false ;
		}
	}
	/**
	 * 根据组织标识查询组织的所有岗位
	 * 
	 * @param party_id
	 *            组织（参与人）标识
	 * @return 指定组织的所有组织岗位实体列表(OrganizationPostVO组成的ArrayList)
	 */
	public ArrayList getPositionListByPartyId(DynamicDict dto )  throws Exception{
		String party_id =  (String)dto.getValueByName("parameter");
		OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
		return daoOrgPost.getOrgPostsByOrganization(party_id);
	}

	public ArrayList queryOrgPosition( DynamicDict dto ) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		String orgId = (String)m.get("orgId");
		String postName  = (String)m.get("postName");
		String state  = (String)m.get("state");
		OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
		return daoOrgPost.queryOrgPosition( orgId, postName, state );
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
	public MpDepartVO getMpDepartByPartyId(DynamicDict dto ) throws Exception{
		String party_id = (String)dto.getValueByName("parameter");
		MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
		MpDepartVO departVo =  daoMpDepart.findByPrimaryKey(party_id);
		
		
		if( departVo == null ){
			return null ;
		}
		
		RrBusinessDAO daoRrBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
		RrBusinessVO businessVo =  daoRrBusiness.findByPrimaryKey(departVo.getRegionId() );
		
		
		departVo.setRegionName( businessVo.getBusinessName() );
		
		dto.setValueByName("parameter", departVo.getSuperPartyId() ) ;
		OrganizationVO organizationVo = getOrganization( dto);
		departVo.setSuperPartyName( organizationVo.getOrgName() );
		return departVo ;
	}

	/**
	 * 插入部门实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为部门对应的组织Id
	 */
	public String insertMpDepart(DynamicDict dto ) throws Exception{
		
		MpDepartVO vo= (MpDepartVO)dto.getValueByName("parameter");
		MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
        daoMpDepart.insert(vo);
        return vo.getPartyId();
	}

	/**easonwu
	 * 根据标识更新部门实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateMpDepart(DynamicDict dto ) throws Exception{
		MpDepartVO vo =  (MpDepartVO)dto.getValueByName("parameter");
		MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
	        return daoMpDepart.update(vo.getPartyId(), vo);
	}

	/**
	 * 删除部门实体，同时删除部门班次、部门终端范围
	 * 
	 * @param pmpdepart_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteMpDepart(DynamicDict dto ) throws Exception{
		String party_id  =  (String)dto.getValueByName("parameter");
		 long recsDeleted = 0;

	        MpDepartTermDAO daoMpDepartTerm = MpDepartTermDAOFactory.getMpDepartTermDAO();
	        recsDeleted = recsDeleted + daoMpDepartTerm.deleteByParty(party_id);
	        
	        RrDepartTermDAO daoRrDepartTerm = RrDepartTermDAOFactory.getRrDepartTermDAO();
	        recsDeleted = recsDeleted + daoRrDepartTerm.deleteByParty(party_id);
	        
	        MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
	        return recsDeleted + daoMpDepart.delete(party_id) > 0;
		
	}

	public ArrayList getDepartByType( DynamicDict dto  ) throws Exception {
	
		Map m = (Map)dto.getValueByName("parameter") ;
		String type = (String)m.get("type");
		String departName  = (String)m.get("departName");
		String businessId  = (String)m.get("businessId");
		 
		MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
    	String cond = "1=1" ;
    	if( !"".equals(departName)){
    		cond = cond + " and org.org_name like '%" + departName + "%'";
    	}
    	if( !"".equals(businessId)){
    		cond = cond + " and dep.REGION_ID = " + businessId ;
    	}
    	if( "99".equals(type)){//所有的部门
    		return (ArrayList)daoMpDepart.findByCond( cond );
    	}else{
    		cond = cond + "and dep.depart_type = '" + type + "'";
    		return (ArrayList)daoMpDepart.findByCond( cond );
    	}
    }
	
	
	// **************************************ENTITY[PartyIdentification]**************************************
	/**
	 * 插入参与人识别信息实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建参与人识别信息标识
	 */
public String insertPartyIdentification(DynamicDict dto ) throws Exception{
		
		Map m = (Map)dto.getValueByName("parameter") ;
		PartyIdentificationVO vo = (PartyIdentificationVO)m.get("v");
		String partyRoleId  = (String)m.get("partyRoleId");
		 
		dto.setValueByName("parameter", partyRoleId) ;
		PartyRoleVO partyRoleVO = getPartyRole( dto );
		vo.setPartyId( partyRoleVO.getSPartyId() );

		 vo.setCreatedDate(DAOUtils.getFormatedDate());
	        PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
	        daoPartyIdentification.insert(vo);
	        String identId =  vo.getIdentId();
		
		
		
//		PbLogVO pbLogVo = createDefaultLogVO();
		String logStr = "增加参与人识别信息";
		logStr += "[ 姓名:" + partyRoleVO.getPartyRoleName() ;
		if( "91A".equals(vo.getSocialIdType())){
			logStr += ",身份证号码:" + vo.getSocialId() + "]";
		}else if( "91B".equals(vo.getSocialIdType())){
			logStr += ",护照:" + vo.getSocialId() + "]";
		}else if( "91C".equals(vo.getSocialIdType())){
			logStr += ",税务登记证:" + vo.getSocialId() + "]";
		}
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
		
		return identId ;
	}

	/**
	 * 根据标识更新参与人识别信息实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updatePartyIdentification(DynamicDict dto ) throws Exception{
		
		PartyIdentificationVO vo=  (PartyIdentificationVO)dto.getValueByName("parameter");
		PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
		boolean ret  = daoPartyIdentification.update(vo
				.getIdentId(), vo);
		dto.setValueByName("parameter", vo.getPartyId());
		PartyVO partyVo = getParty(dto);
		
//		PbLogVO pbLogVo = createDefaultLogVO();
		String logStr = "更新参与人识别信息";
		logStr += "[ 姓名:" + partyVo.getPartyName() ;
		if( "91A".equals(vo.getSocialIdType())){
			logStr += ",身份证号码:" + vo.getSocialId() + "]";
		}else if( "91B".equals(vo.getSocialIdType())){
			logStr += ",护照:" + vo.getSocialId() + "]";
		}else if( "91C".equals(vo.getSocialIdType())){
			logStr += ",税务登记证:" + vo.getSocialId() + "]";
		}
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
		
		return ret;
	}

	/**
	 * 删除参与人识别信息实体
	 * 
	 * @param ppartyidentification_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deletePartyIdentification(DynamicDict dto ) throws Exception{
		
		String ppartyidentification_id=  (String)dto.getValueByName("parameter");
		PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
		PartyIdentificationVO idVo =  daoPartyIdentification.findByPrimaryKey(ppartyidentification_id);
		
		dto.setValueByName("parameter", idVo.getPartyId()) ;
		PartyVO partyVo = getParty(dto);
		
			if (daoPartyIdentification.delete(ppartyidentification_id) > 0) {
				
//				PbLogVO pbLogVo = createDefaultLogVO();
				String logStr = "删除参与人识别信息";
				logStr = logStr + "[ 姓名:" + partyVo.getPartyName() ;
				if( "91A".equals(idVo.getSocialIdType())){
					logStr = logStr + ",身份证号码:" + idVo.getSocialId() + "]";
				}else if( "91B".equals(idVo.getSocialIdType())){
					logStr = logStr + ",护照:" + idVo.getSocialId() + "]";
				}else if( "91C".equals(idVo.getSocialIdType())){
					logStr = logStr + ",税务登记证:" + idVo.getSocialId() + "]";
				}
//				pbLogVo.setLogDeta(logStr);
//				LogUtils.logActionLog(pbLogVo);
				return true;
			} else {
				return false;
			}
	}

	/**
	 * 根据参与人角色标识获取参与人识别信息
	 * 
	 * @param pparty_id
	 *            标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为参与人角色标识对应的参与人识别信息列表（PartyIdentificationVO构成的ArrayList）
	 */
	public ArrayList getPartyIdentificationByPartyId(DynamicDict dto ) throws Exception{
		String pparty_role_id =  (String)dto.getValueByName("parameter");
		PartyRoleVO partyRoleVO = getPartyRole( dto );
		 PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
		 ArrayList alPartyIdentifications =  daoPartyIdentification.getPartyIdentificationsByParty(partyRoleVO.getSPartyId());
		
		return alPartyIdentifications;
	}


	// **************************************ENTITY[PartyRole]**************************************
	/**
	 * 插入参与人角色实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为参与人角色标识
	 */
	public String insertPartyRole(DynamicDict dto ) throws Exception{
		PartyRoleVO vo=  (PartyRoleVO)dto.getValueByName("parameter");
			PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        daoPartyRole.insert(vo);
	        return vo.getPartyRoleId();		
	}

	/**
	 * 根据标识更新参与人角色实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updatePartyRole(DynamicDict dto ) throws Exception{
		PartyRoleVO vo =  (PartyRoleVO)dto.getValueByName("parameter");
		 PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        return daoPartyRole.update(vo.getPartyRoleId(), vo);
	    
	}

	/**
	 * 删除实体
	 * 
	 * @param ppartyrole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deletePartyRole(DynamicDict dto) throws Exception{
		String partyrole_id=  (String)dto.getValueByName("parameter");
		 long recsDeleted = 0;
	        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        PartyRoleVO voPartyRole = daoPartyRole.findByPrimaryKey(partyrole_id);
	        if("90C".equals(voPartyRole.getPartyRoleType()))
	        {
	            PartnerDAO daoPartner = PartnerDAOFactory.getPartnerDAO();
	            ChnSegDAO daoChnSeg = ChnSegDAOFactory.getChnSegDAO();
	            recsDeleted = recsDeleted + daoChnSeg.deleteByPartyRole(voPartyRole.getPartyRoleId());
	            recsDeleted = recsDeleted + daoPartner.delete(voPartyRole.getPartyRoleId());
	        }
	        else if("90B".equals(voPartyRole.getPartyRoleType()))
	        {
	            CompetitorDAO daoCompetitor = CompetitorDAOFactory.getCompetitorDAO();
	            recsDeleted = recsDeleted + daoCompetitor.delete(voPartyRole.getPartyRoleId());
	        }
	        else if("90A".equals(voPartyRole.getPartyRoleType()))
	        {
	            StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
	            recsDeleted = recsDeleted + daoStaffPost.deleteByStaff(partyrole_id);
	            
	            StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
	            recsDeleted = recsDeleted + daoStaffPriv.deleteByStaff(partyrole_id);
	            
	            StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
	            recsDeleted = recsDeleted + daoStaffRole.deleteByStaff(partyrole_id);
	            
	            StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
	            recsDeleted = recsDeleted + daoStaff.delete(partyrole_id);
	        }
	        return recsDeleted + daoPartyRole.delete(partyrole_id) > 0;
	}

	/**
	 * 查询指定参与人的所有参与人角色
	 * 
	 * @param pparty_id
	 *            参与人标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为指定参与人的所有参与人角色列表（PartyRoleVO构成的ArrayList）
	 */
	public ArrayList getPartyRolesByPartyId(DynamicDict dto)  throws Exception{
		String party_id =  (String)dto.getValueByName("parameter");
		PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        return daoPartyRole.getPartyRolesByParty(party_id);
	}


	// **************************************ENTITY[Staff]**************************************
	/**
	 * 根据员工条件查询员工
	 * 
	 * @param voStaff
	 *            查询条件，其不为null和""的字段作为条件，ID的字段为精确匹配(=)，名称、描述等字段为模糊查询(LIKE %...%)
	 * @return 操作结果, 成功时ServiceResult.resultObject为符合条件的员工列表
	 */
//	public ArrayList getStaffsByStaffCond(StaffVO voStaff) throws Exception{
//		throw new Exception();
//	}
	
	public String getOfficeDescByOfficeId( DynamicDict dto ) throws Exception{
		String officeId  =  (String)dto.getValueByName("parameter");
		WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		WorkingOfficeVO officeVo = daoWorkingOffice.findByPrimaryKey(officeId);
		return officeVo.getOfficeDesc();
	}
	public PageModel queryOfficeList(DynamicDict dto  ) throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
		String officeDesc = (String)m.get("officeDesc");
		 int pageIndex  = ((Integer)m.get("pageIndex")).intValue();
		 int pageSize  = ((Integer)m.get("pageSize")).intValue();
		 
		WorkingOfficeDAO workingOfficeDao = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		String whereCond = "";
    	if( "".equals(officeDesc ) ){
    		whereCond = " 1 = 1";
    	}else{
    		whereCond = " office_DESC like '%" + officeDesc + "%'";
    	}
    	return PageHelper.popupPageModel(workingOfficeDao, whereCond, pageIndex, pageSize);    	
        
	}
	
	/**
	 * 以分页方式查询员工信息
	 * @param voStaff
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel getStaffsByStaffCondPaginate(DynamicDict dto ) throws Exception {
		Map m = (Map)dto.getValueByName("parameter") ;
		StaffVO voStaff = (StaffVO)m.get("voStaff");
		 int pageIndex  = ((Integer)m.get("pageIndex")).intValue();
		 int pageSize  = ((Integer)m.get("pageSize")).intValue();
		 
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
    	return daoStaff.findByStaffVO( voStaff, pageIndex, pageSize );
	}	

	/**
	 * 插入员工实体，同时插入
     * 参与人、个人、参与人角色
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果
	 */
	public void insertStaff(DynamicDict dto ) throws Exception{
		StaffVO vo =  (StaffVO)dto.getValueByName("parameter");//营业区businessId,城乡标志countyType
		OrganizationDAO organizationDAO = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO orgVO = organizationDAO.findByPrimaryKey(vo.getOrgPartyId());
    	//判断工号是否重复 
    	StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
    	String staffCode = "" ;

    	String projectCode = CrmParamsConfig.getInstance().getParamValue("PROJECT_CODE");
    	if( CrmConstants.CQ_PROJECT_CODE.equals(projectCode)){
	    	if( !"12".equals(vo.getChannelSegmentId()) && !"-1".equals(vo.getChannelSegmentId())){
	    		staffCode = orgVO.getOrgCode() + vo.getStaffCode() ;
	    	}else{
	    		staffCode = vo.getStaffCode();
	    	}
    	}else {
    		staffCode = vo.getStaffCode() ;
    	}
    	ArrayList list = daoStaff.findBySql("SELECT * FROM STAFF WHERE STAFF_CODE = '" + staffCode.toUpperCase() + "'",null);
    	if( list.size() > 0 ){
    		throw new Exception ( new OAASException(OAASError.STAFF_SAME_STAFF_CODE));
    	}
    	insertStaffWithoutCheck( dto ) ;
		
//		PbLogVO pbLogVo = createDefaultLogVO();
//		pbLogVo.setLogDeta("增加员工[姓名:" + voStaff.getPartyName() +",工号:" + voStaff.getStaffCode() + "]");
//		LogUtils.logActionLog(pbLogVo);
	}
	
	private void insertStaffWithoutCheck(DynamicDict dto ) throws Exception {
		StaffVO vo  =  (StaffVO)dto.getValueByName("parameter");
    	String projectCode = CrmParamsConfig.getInstance().getParamValue("PROJECT_CODE");
    	if( CrmConstants.CQ_PROJECT_CODE.equals(projectCode)){
	    	if( !"12".equals(vo.getChannelSegmentId()) && !"-1".equals(vo.getChannelSegmentId())){
		    	//给工号加上组织编码前缀
		    	OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();
		    	OrganizationVO orgVO = orgDao.findByPrimaryKey(vo.getOrgPartyId());
		    	vo.setStaffCode(orgVO.getOrgCode() + vo.getStaffCode());
	    	}
    	}
    	
    	StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
    	PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
        PartyVO voParty = new PartyVO(
                null,
                vo.getPartyName(),
                vo.getEffDate(),
                vo.getState(),
                vo.getStateDate(),
                null,
                null);
        //插入参与人表(PARTY)
        daoParty.insert(voParty);
        
        vo.setPartyId(voParty.getPartyId());
        
        //插入员工角色STAFF_ROLE
//        StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
//        daoStaffRole.insert(new StaffRoleVO(vo.getPartyRoleId(),bo.getRoleId(),
//        		vo.getState(),vo.getEffDate(),vo.getExpDate(),vo.getReginId(),
//        		vo.getregin_type(),vo.getprivlUseType()));
        
        //插入个人表(INDIVIDUAL)
        IndividualDAO daoIndividual = IndividualDAOFactory.getIndividualDAO();
        daoIndividual.insert(new IndividualVO(
                voParty.getPartyId(), vo.getGender(), 
                vo.getBirthPlace(), 
                vo.getBirthDate(), vo.getMaritalStatus(), 
                vo.getSkill()
                ));
        
        //插入参与人角色表PARTY_ROLE
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        if( "".equals(vo.getOfficeId())){
        	vo.setOfficeId(null);
        }
        
        PartyRoleVO voPartyRole = new PartyRoleVO(
                null,//参与人角色ID
                vo.getOrgPartyId(),//所属组织
                vo.getOrgPartyId(),
                vo.getPartyName(),//参与人姓名
                vo.getPartyRoleType(),//参与人类型
                vo.getOrgManager(),//是否组织管理者.
                DateFormatUtils.getFormatedDateTime(),//创建日期(当前时间)
                vo.getStaffDesc(),//备注
                vo.getState(),//状态
                vo.getEffDate(),//生效日期
                vo.getExpDate(),//失效日期
                vo.getOfficeId(),//办公地点
                vo.getPassword(),//密码
                
                vo.getPwdvalidtype(),//密码有效期类型
                DateFormatUtils.getFormatedDateTime(),//最后更新密码时间
                "2",//登陆状态,0:正常; 1：锁定;  2:新用户，需强制修改口令(由于鉴权服务器未完善,暂时先使用0,正常必须使用2)
                "0",//登陆次数
                vo.getLimitCount(),//登陆次数限制
                null//输入密码错误时间
        		);
        voPartyRole.setLanId(vo.getLanId());
        voPartyRole.setSPartyId(voParty.getPartyId());
        voPartyRole.setDevOrgId(vo.getDevOrgId());
        daoPartyRole.insert(voPartyRole);
        voPartyRole = daoPartyRole.findByPrimaryKey(voPartyRole.getPartyRoleId());
        /*
         * 插进PARTY_ROLE表的密码字段是明文,需要调用鉴权服务器的加密接口对密码进行加密
         * 由于鉴权服务器的加密算法是将PARTY_ROLE_ID字段作为加密的KEY,所以必须先执行
         * 插入操作,然后将插入是生成的PARTY_ROLE_ID字段取出作为密钥,和明文的密码一起调用
         * 加密接口,获取加密后的密码,再更新PARTY_ROLE表.
         */
		if(voPartyRole.getPassword() == null || "".equals(voPartyRole.getPassword())){//如果密码为空,则使用配置文件中的默认密码
			String defaultPwd = CrmParamsConfig.getInstance().getParamValue("STAFF_PWD");
			voPartyRole.setPassword(defaultPwd);
			//存放在配置文件里面的默认密码是加密的密文，需要做解密操作
			// ======> 注释掉下面的代码 严俊波
//			EncryptRequest req = new EncryptRequest() ;
//			req.setFlag("F");
//			req.setSecretBuff(voPartyRole.getPassword()) ;
//			EncryptRespond res = OAASProxy.encrypt(req);
//			voPartyRole.setPassword(res.getStrResultBuff());
			// ======> 注释掉上面的代码 严俊波
		}
		
		//将明文的密码加上party_role_id作为前缀，进行加密 easonwu 2009-12-21注释之
//		EncryptRequest encryptRequest = new EncryptRequest() ;
		//encryptRequest.setFlag( "T" ) ;
		//encryptRequest.setProclaimedBuff(voPartyRole.getPartyRoleId()+voPartyRole.getPassword());
		
		/*
		 * 调用鉴权服务器执行工号加密操作,传递参与人角色ID和密码明文,鉴权服务器将参与人角色ID和密码相加后加密
		 */
//		encryptRequest.setFlag( "S" ) ;//工号密码加密请求包类型
//		encryptRequest.setSecretBuff(voPartyRole.getPartyRoleId());//参与人角色ID
//		encryptRequest.setProclaimedBuff(voPartyRole.getPassword());//密码明文
//		
//		EncryptRespond encryptRespond = OAASProxy.encrypt(encryptRequest);//发送加密请求
//		String newPassword = encryptRespond.getStrResultBuff();//获取返回的加密字符串
		
		// 密码经过DES加密后存到PARTY_ROLE数据表中　　　严俊波　2010/4/15
        DesEncrypt descript= new DesEncrypt();	//使用DES加密
        String newPassword =descript.encryptTriDes(voPartyRole.getPartyRoleId()+voPartyRole.getPassword());
		
		voPartyRole.setPassword(newPassword) ;
		daoPartyRole.update(voPartyRole.getPartyRoleId(),voPartyRole) ;

		daoPartyRole.insertStaffPassLog(voPartyRole.getPartyRoleId(),voPartyRole.getPassword());

		//插入员工表STAFF
        vo.setPartyRoleId(voPartyRole.getPartyRoleId());
        vo.setLoginCount("0");
        vo.setLoginStatus("0");
        vo.setUpdateTime(DAOUtils.getFormatedDate());
        
        daoStaff.insert(vo);
        
        
        
    }
	
//	private PbLogVO createDefaultLogVO() throws Exception{
//		PbLogVO pbLogVo = new PbLogVO();
//		GlobalVariableHelper helper = new GlobalVariableHelper( getRequest() );
//		
//		pbLogVo.setLogType("2");//菜单操作
//		pbLogVo.setStaffId(helper.getVariable(GlobalVariableHelper.OPER_ID));
//		pbLogVo.setStaffName( helper.getVariable(GlobalVariableHelper.OPER_NAME));
//		pbLogVo.setState("10A");
//		pbLogVo.setStateDate(DateFormatUtils.getFormatedDateTime());
//		pbLogVo.setCreateDate(DateFormatUtils.getFormatedDateTime());
//		pbLogVo.setLogDate( DateFormatUtils.getFormatedDateTime());
//		pbLogVo.setModuleId("16000");//系统模块标识
//		pbLogVo.setActId(null);
//		return pbLogVo;
//	}
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
	public String insertBatchStaff(DynamicDict dto  ) throws Exception{
		Map m =(Map) dto.getValueByName("parameter") ;
		String orgPartyId  = (String) m.get("orgPartyId"); 
		String startStaffCode = (String) m.get("startStaffCode"); 
		String endStaffCode = (String) m.get("endStaffCode");
		String staffName = (String) m.get("staffName"); 
		String orgPostId = (String) m.get("orgPostId"); 
		String state =(String) m.get("state") ;
		String effDate = (String) m.get("effDate"); 
		String expDate = (String) m.get("expDate"); 
		String channelSegmentId = (String) m.get("channelSegmentId"); 
		String password = (String) m.get("password"); 
		String lanId = (String) m.get("lanId");
		String businessId  = (String) m.get("businessId"); 
		String countyType  = (String) m.get("countyType");
		String orgManager = (String) m.get("orgManager");
		String gender = (String) m.get("gender");
		
		String returnValue = "";
    	StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
    	String[] staffNameArr = staffName.split(",");
    	
    	//员工所属组织
    	OrganizationDAO orgDAO = OrganizationDAOFactory.getOrganizationDAO();
    	OrganizationVO orgVO = orgDAO.findByPrimaryKey(orgPartyId);
    	
    	long start = new Long(startStaffCode).longValue();
    	long end = new Long(endStaffCode).longValue();
    	boolean first = true;
    	int successCount = 0 ;
    	int failureCount = 0 ;
    	for( long i = start; i <= end; i ++ ){
    		//判断工号是否重复
        	StaffVO testStaffVO = null ;
        	String projectCode = CrmParamsConfig.getInstance().getParamValue("PROJECT_CODE");
        	if( CrmConstants.CQ_PROJECT_CODE.equals(projectCode)){
        		if( !"12".equals(channelSegmentId) && !"-1".equals(channelSegmentId)){
            		testStaffVO = daoStaff.getStaffByStaffCode(orgVO.getOrgCode() + i );
            	}else{
            		testStaffVO = daoStaff.getStaffByStaffCode( i + "" );
            	}
        	}else{
        		testStaffVO = daoStaff.getStaffByStaffCode( i + "" );
        	}

        	if( testStaffVO != null ){
        		//工号已经存在,批量添加忽略这个工号
        		if( first ){
        			returnValue =  "" + i ;
        			first = false ;
        		}else if( failureCount <=10){
        			returnValue = returnValue + ",\n" + i ;
        		}
        		failureCount++;
        		continue ;
        	}
        	successCount++;

    		StaffVO staffVO = new StaffVO();
    		staffVO.setOrgPartyId(orgPartyId);
    		staffVO.setStaffCode( (new Long(i)).toString());
    		staffVO.setPartyName(staffNameArr[new Long( i - start ).intValue()]);
    		staffVO.setBirthDate("1970-01-01 00:00:00");
    		staffVO.setBirthPlace("");
    		staffVO.setCreateDate(DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT));
    		staffVO.setEffDate(effDate + " 00:00:00");
    		staffVO.setExpDate(expDate  + " 00:00:00");
    		staffVO.setGender("0");
    		staffVO.setMaritalStatus("F");
    		staffVO.setPartyRoleType("90A");
    		//密码加密 liuyuming 2010-0920
    		DesEncrypt descript= new DesEncrypt();
    		password = descript.encryptTriDes(password);
    		staffVO.setPassword(password);
    		staffVO.setPwdvalidtype("1");//默认半年后过期
    		staffVO.setSkill("");
    		staffVO.setStaffDesc("批量增加的员工");
    		staffVO.setState("00A");
    		//staffVO.setLimitCount("3");
			//严俊波添加下面代码 2010/4/19
			staffVO.setLimitCount("999999");	//登录次数限制默认为最大值“999999”
			staffVO.setOrgManager(orgManager);
			staffVO.setGender(gender);
			//严俊波添加上面代码 2010/4/19
    		staffVO.setUpdateTime(DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT));
    		staffVO.setStateDate(DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT));
    		staffVO.setChannelSegmentId( channelSegmentId );
    		staffVO.setLanId(lanId);
    		staffVO.setBusinessId(businessId);
    		staffVO.setCountyType(countyType);
    		dto.setValueByName("parameter", staffVO);
    		insertStaffWithoutCheck(dto);

//          批量插入员工，暂时屏蔽掉岗位信息
//        	StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
//        	StaffPostVO staffPostVO = new StaffPostVO();
//        	staffPostVO.setEffDate(effDate);
//        	staffPostVO.setExpDate(expDate);
//        	staffPostVO.setOrgPostId(orgPostId);
//        	staffPostVO.setPartyRoleId(staffVO.getPartyRoleId());
//        	staffPostVO.setState(state);
//          daoStaffPost.insert(staffPostVO);
    	}

    	String returnStr = "成功增加工号"+successCount+"个，重复工号" + failureCount+"个。";
    	if( failureCount > 0 ){
    		returnStr = returnStr + "\n重复工号将被忽略,";
	    	if( failureCount > 10 ){
	    		returnStr = returnStr + "这是部分重复的工号:\n" + returnValue + "...";
	    	}else{
	    		returnStr = returnStr + "这是重复的工号:\n" + returnValue ;
	    	}
    	}
    	return returnStr;
	}
	
	
	 /**
	     * 根据一个参与人ID复制员工
	     * 插入员工，同时插入
	     * 参与人、个人、参与人角色
	     */
	    public void pcopyStaff( String sourcePartyRoleId, String staffCode, String staffName, String password ) throws Exception{
	    	
	    }
	/**
	 * 复制员工
	 * @param sourcePartyRoleId	被复制的员工的参与人角色ID
	 * @param staffCode	工号
	 * @param staffName	姓名
	 * @param password	密码
	 * @throws Exception
	 */
	public void copyStaff(DynamicDict dto  ) throws Exception {

		Map m = (Map)dto.getValueByName("parameter") ;
		String sourcePartyRoleId = (String)m.get("sourcePartyRoleId");
		String staffCode  = (String)m.get("staffCode");

		String staffName = (String)m.get("staffName");
		String password  = (String)m.get("password");		 
		
		//查询出源 员工的信息
    	StaffDAO staffDao = StaffDAOFactory.getStaffDAO();
    	StaffVO staffVo = staffDao.findByPrimaryKey( sourcePartyRoleId );
    	System.out.println("PartyRoleId: " + staffVo.getPartyRoleId());
    	System.out.println("business_id: " + staffVo.getBusinessId());
    	System.out.println("county_type: " + staffVo.getCountyType());
    	
    	
    	//查询出源员工的参与人角色信息
    	PartyRoleDAO partyRoleDao = PartyRoleDAOFactory.getPartyRoleDAO();
    	PartyRoleVO partyRoleVo = partyRoleDao.findByPrimaryKey( sourcePartyRoleId ) ;
    	
    	String projectCode = CrmParamsConfig.getInstance().getParamValue("PROJECT_CODE");
    	if( CrmConstants.CQ_PROJECT_CODE.equals(projectCode)){
	    	if( !"12".equals(staffVo.getChannelSegmentId()) && !"-1".equals(staffVo.getChannelSegmentId())){
		    	OrganizationDAO organizationDao = OrganizationDAOFactory.getOrganizationDAO();
		    	OrganizationVO orgVO = organizationDao.findByPrimaryKey(partyRoleVo.getOrgPartyId());
		    	staffCode = orgVO.getOrgCode() + staffCode ;
	    	}
    	}
    	
    	ArrayList list = staffDao.findBySql("SELECT * FROM STAFF WHERE STAFF_CODE = '" + staffCode.toUpperCase() + "'",null);
    	if( list.size() > 0 ){
    		throw new OAASException(OAASError.STAFF_SAME_STAFF_CODE);
    	}
    	
    	//查询出源员工的参与人信息
    	PartyDAO partyDao = PartyDAOFactory.getPartyDAO();
    	PartyVO partyVo = partyDao.findByPrimaryKey( partyRoleVo.getSPartyId() );
    	
    	//查询出源员工的个人信息
    	IndividualDAO individualDao = IndividualDAOFactory.getIndividualDAO();
    	IndividualVO individualVo = individualDao.findByPrimaryKey( partyVo.getPartyId() );
    	
    	//插入参与人表
    	partyVo.setPartyId( null ) ;
    	partyVo.setPartyName( staffName ) ;
    	partyVo.setAddDescription( null ) ;
    	partyVo.setAddrId( null ) ;
    	partyDao.insert( partyVo ) ;
    	
    	
    	//插入个人表
    	individualVo.setPartyId( partyVo.getPartyId() );
    	individualDao.insert( individualVo );
    	
    	if( password == null || "".equals(password)){//如果密码为空,则从配制文件中获取默认密码
    		password = CrmParamsConfig.getInstance().getParamValue("STAFF_PWD");//获取默认密码
    		//默认密码在配制文件中以密文方式保存，需要解密默认密码
    		EncryptRequest req = new EncryptRequest() ;
    		req.setFlag("F");
    		req.setSecretBuff(password) ;
    		EncryptRespond res = OAASProxy.encrypt(req);
    		password = res.getStrResultBuff();
    	}
    	//插入参与人角色表
    	partyRoleVo.setPassword( password ) ;
    	partyRoleVo.setPartyRoleName( staffName ) ;
    	partyRoleVo.setSPartyId( partyVo.getPartyId() );
    	partyRoleVo.setLoginStatus("2");
    	partyRoleDao.insert( partyRoleVo );
    	
    	//上面插入的密码是明文的,这里通过鉴权服务器加密密码,然后再update一次
//    	EncryptRequest encryptRequest = new EncryptRequest() ;
//		encryptRequest.setFlag( "T" ) ;
//		encryptRequest.setProclaimedBuff(partyRoleVo.getPartyRoleId()+partyRoleVo.getPassword());
//		
//		EncryptRespond encryptRespond = OAASProxy.encrypt(encryptRequest);
//		String newPassword = encryptRespond.getStrResultBuff();
		
		
		DesEncrypt descript= new DesEncrypt();
		//不再使用party_role_id+password组合加密 liuyuming 2010-09-22
        String newPassword =descript.encryptTriDes(partyRoleVo.getPassword());
		
		partyRoleVo.setPassword(newPassword) ;
		partyRoleDao.update(partyRoleVo.getPartyRoleId(),partyRoleVo) ;
		
		partyRoleDao.insertStaffPassLog(partyRoleVo.getPartyRoleId(),partyRoleVo.getPassword());
    	
    	staffVo.setStaffCode(staffCode);
    	staffVo.setPartyRoleId( partyRoleVo.getPartyRoleId());
    	staffVo.setStaffDesc(partyRoleVo.getPartyRoleName());
    	//严俊波 2010/4/19 TODO
    	//看下这里的staffVo属性business_id和county_id有没有值
    	staffDao.insert( staffVo );//插入员工表
    	
    	//插入员工岗位表
    	StaffPostDAO staffPostDao = StaffPostDAOFactory.getStaffPostDAO();
    	List staffPostList = staffPostDao.findByCond( "party_role_id = " + sourcePartyRoleId);
    	for( int i = 0 ; i < staffPostList.size() ; i ++ ){
    		StaffPostVO staffPostVo = (StaffPostVO)staffPostList.get(i);
    		staffPostVo.setPartyRoleId( partyRoleVo.getPartyRoleId() );
    		staffPostDao.insert( staffPostVo ) ;
    	}
    	
    	//插入员工权限表
    	StaffPrivDAO staffPrivilegeDao = StaffPrivDAOFactory.getStaffPrivDAO();
    	List staffPrivList = staffPrivilegeDao.findByCond( " party_role_id = " + sourcePartyRoleId);
    	for( int i = 0 ; i < staffPrivList.size() ; i ++ ){
    		StaffPrivVO staffPrivVo = (StaffPrivVO)staffPrivList.get(i);
    		staffPrivVo.setPartyRoleId( partyRoleVo.getPartyRoleId() );
    		staffPrivilegeDao.insert( staffPrivVo ) ;
    	}
    	
    	//插入员工角色表
    	StaffRoleDAO staffRoleDao = StaffRoleDAOFactory.getStaffRoleDAO();
    	List staffRoleList = staffRoleDao.findByCond( " party_role_id = " + sourcePartyRoleId);
    	for( int i = 0 ; i < staffRoleList.size() ; i ++ ){
    		StaffRoleVO staffRoleVo = (StaffRoleVO)staffRoleList.get(i ) ;
    		staffRoleVo.setPartyRoleId( partyRoleVo.getPartyRoleId() ) ;
    		staffRoleDao.insert( staffRoleVo ) ;
    	}
		
    	//插入员工表 严俊波 2010/4/16 TODO
//    	staffVo.setPartyId(partyId);
//    	staffVo.setPartyRoleId(pPartyRoleId);
//    	staffVo.setStaffCode(staffCode);
//    	staffVo.setPassword(password);
//    	staffDao.insert(staffVo);
    	
//		PbLogVO pbLogVo = createDefaultLogVO();
//		pbLogVo.setLogDeta("复制员工[姓名:" + staffName +",工号:" + staffCode + "]");
//		LogUtils.logActionLog(pbLogVo);
	}
	/**
     * 更新员工，同时更新
     * 参与人、个人、参与人角色
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateStaff(DynamicDict dto ) throws Exception{
		//参与人角色
		StaffVO vo =  (StaffVO)dto.getValueByName("parameter");//在这里断点看下vo信息是否完整，尤其是城乡标识countyType，
		//贯籍birthPlace，婚否maritalStatus，是否管理者ORG_MANAGER,技巧skill   ==》这些字段都有对应的值
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        //看下partyRoleVO里面数据是什么
        //这里的partyRoleVO数据和上面的不一致 可以参考orgManage的值
        //上面vo的值是T，下来后partyRoleVO就变成了F
        PartyRoleVO partyRoleVO = daoPartyRole.findByPrimaryKey(vo.getPartyRoleId());
        partyRoleVO.setState(vo.getState());
        // ======> 把页面上的新值覆盖掉从数据库中查出来的partyRoleVO相关字段 严俊波[BEGIN] 2010/4/15
        partyRoleVO.setPartyId(vo.getPartyRoleId());
        partyRoleVO.setOrgPartyId(vo.getOrgPartyId());
        partyRoleVO.setPartyRoleType(vo.getPartyRoleType());
        partyRoleVO.setOrgManager(vo.getOrgManager());
        partyRoleVO.setCreateDate(vo.getCreateDate());
        partyRoleVO.setLimitCount(vo.getLimitCount());
        //修改密码需要加密入库
        DesEncrypt descript= new DesEncrypt();
        String validatePwd =descript.encryptTriDes(vo.getPassword());
        partyRoleVO.setPassword(validatePwd);
        partyRoleVO.setPwdValidType(vo.getPwdvalidtype());
        partyRoleVO.setSPartyId(vo.getPartyId());
        // ======> 把页面上的新值覆盖掉从数据库中查出来的partyRoleVO相关字段 严俊波[END]
        if( !daoPartyRole.update(vo.getPartyRoleId(),partyRoleVO)){
        	return false ;
        }
        
        // 个人表(INDIVIDUAL)  严俊波 2010/4/15
        IndividualDAO daoIndi = IndividualDAOFactory.getIndividualDAO();
        IndividualVO indiVO = daoIndi.findByPrimaryKey(partyRoleVO.getSPartyId());
        indiVO.setGender(vo.getGender());
        indiVO.setBirthPlace(vo.getBirthDate());
        indiVO.setBirthDate(vo.getBirthDate());
        indiVO.setMaritalStatus(vo.getMaritalStatus());
        indiVO.setSkill(vo.getSkill());
        if( !daoIndi.update(partyRoleVO.getSPartyId(),indiVO)){
        	return false;
        }
        
        //参与人表(PARTY)
        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
        PartyVO partyVO = daoParty.findByPrimaryKey(partyRoleVO.getSPartyId());
        partyVO.setState( vo.getState() );
        // ======> 把页面上的新值覆盖掉从数据库中查出来的partyRoleVO相关字段 严俊波[BEGIN] 2010/4/15
        partyVO.setPartyName(vo.getPartyName());
        partyVO.setEffDate(vo.getEffDate());
        partyVO.setStateDate(vo.getStateDate());
        // ======> 把页面上的新值覆盖掉从数据库中查出来的partyRoleVO相关字段 严俊波[END]
        if( !daoParty.update( partyRoleVO.getSPartyId(), partyVO ) ){
        	return false ;
        }
        
        //更新员工表STAFF 严俊波 2010/4/16
        StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
        StaffVO staffVO = daoStaff.findByPrimaryKey(vo.getPartyRoleId());
        staffVO.setBusinessId(vo.getBusinessId());
        staffVO.setCountyType(vo.getCountyType());
        if ( !daoStaff.update(staffVO.getPartyRoleId(), staffVO)){
        	return false;
        }
        
        
        return true ;
//		PbLogVO pbLogVo = createDefaultLogVO();
//		pbLogVo.setLogDeta("修改员工[姓名:" + vo.getPartyName() +",工号:" + vo.getStaffCode() + "]");
//		LogUtils.logActionLog(pbLogVo);
//		return ret ;
	}

	 public boolean updateStaffState(DynamicDict dto ) throws Exception
	 {
	    	//参与人角色
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String party_role_id = (String)m.get("party_role_id");
		 String state  = (String)m.get("state");
		 
	        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        PartyRoleVO partyRoleVO = daoPartyRole.findByPrimaryKey(party_role_id);
	        partyRoleVO.setState(state);
	        if( !daoPartyRole.update(party_role_id,partyRoleVO)){
	        	return false ;
	        }
	        
	        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
	        PartyVO partyVO = daoParty.findByPrimaryKey(partyRoleVO.getSPartyId());
	        partyVO.setState( state );
	        if( !daoParty.update( partyRoleVO.getSPartyId(), partyVO ) ){
	        	return false ;
	        }
	        
	        return true ;
	 }
	
	/**
	 * 
	 * @param ppartyrole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteStaff(DynamicDict dto ) throws Exception{
		String ppartyrole_id = (String)dto.getValueByName("parameter");
		
		Map m = new HashMap() ;
	 	m.put("party_role_id", ppartyrole_id) ;
		m.put("state", "00X" ) ;
		dto.setValueByName("parameter", m) ;
		
		boolean ret = updateStaffState(dto);
		
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		StaffVO staffVo =  daoStaff.findByPrimaryKey(ppartyrole_id);
//		PbLogVO pbLogVo = createDefaultLogVO();
//		pbLogVo.setLogDeta("注销员工[工号:" + staffVo.getStaffCode() + "]");
//		LogUtils.logActionLog(pbLogVo);
		return ret ;
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
	public String insertWorkingOffice(DynamicDict dto) throws Exception{
		WorkingOfficeVO vo =  (WorkingOfficeVO)dto.getValueByName("parameter");
		WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		daoWorkingOffice.insert(vo);
        return vo.getOfficeId();
	}

	/**
	 * 根据标识更新办公地点实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateWorkingOffice(DynamicDict dto ) throws Exception{
		WorkingOfficeVO vo =  (WorkingOfficeVO)dto.getValueByName("parameter");
		WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		return daoWorkingOffice.update(vo.getOfficeId(), vo);
	}

	/**
	 * 删除办公地点实体
	 * 
	 * @param pworkingoffice_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteWorkingOffice(DynamicDict dto ) throws Exception{
		String workingoffice_id  =  (String)dto.getValueByName("parameter");
			OfficeMachineDAO daoOfficeMachine = OfficeMachineDAOFactory.getOfficeMachineDAO();
			daoOfficeMachine.deleteByCond("OFFICE_ID=" + workingoffice_id);
	        WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
	        return daoWorkingOffice.delete(workingoffice_id)>0;	
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
	public ArrayList getStaffPostByPartyRoleId(DynamicDict dto) throws Exception{
		String ppartyrole_id  =  (String)dto.getValueByName("parameter");	
		StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
		return daoStaffPost.getStaffPostsByStaff(ppartyrole_id);
	}

	
	/**
	 * 插入员工岗位实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果
	 */
	public void insertStaffPost(DynamicDict dto) throws Exception{
		StaffPostVO[] vos =  (StaffPostVO[])dto.getValueByName("parameter");
		 StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
        StaffPostVO vo = vos[0];
        daoStaffPost.deleteByCond("PARTY_ROLE_ID=" + vo.getPartyRoleId());
        for( int i = 0 ; i < vos.length ; i ++ ){
        	daoStaffPost.insert(vos[i]);
        }
        
        
		//日志操作开始
//		PartyRoleVO partyRoleVO = getPartyRole(vo.getPartyRoleId());//获取员工姓名
//		
//		StaffVO staffVO = getStaff(partyRoleVO.getPartyRoleId());//获取工号

//		PbLogVO pbLogVo = createDefaultLogVO();
//		String logStr = "增加员工岗位[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//		//logStr = logStr + ",岗位:" + positionVO.getPositionName() + "]";
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
//		//日志操作结束
	}

	/**
	 * 根据标识更新员工岗位实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateStaffPost(DynamicDict dto ) throws Exception{
		
		StaffPostVO vo= (StaffPostVO)dto.getValueByName("parameter");
		StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
		boolean ret = daoStaffPost.update(vo.getOrgPostId(), vo
				.getPartyRoleId(), vo);
		
		dto.setValueByName("parameter", vo.getPartyRoleId());
		PartyRoleVO partyRoleVO = getPartyRole(dto);
		
		StaffVO staffVO = getStaff(dto);
		
		OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
		OrganizationPostVO orgPostVo =  daoOrgPost.findByPrimaryKey(vo.getOrgPostId());
		
//		Position ejbPosition = (Position)EJBHelper.creatEJB("ejb/Position",PositionHome.class);
//		PositionVO positionVO = ejbPosition.getPosition(orgPostVo.getPositionId());

//		PbLogVO pbLogVo = createDefaultLogVO();
//		String logStr = "更新员工岗位[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//		logStr = logStr + ",岗位:" + positionVO.getPositionName() + "]";
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
		
		return ret ;
	}

	   public StaffVO getStaff(DynamicDict dto ) throws Exception
	    {
		   String party_role_id = (String)dto.getValueByName("parameter");
	        StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
	        return daoStaff.findByPrimaryKey(party_role_id);
	    }
	/**
	 * 删除员工岗位实体
	 * 
	 * @param pstaffpost_id
	 *            实体标识
	 * @return 操作结果, 成功时ServiceResult.resultObject为
	 */
	public boolean deleteStaffPost(DynamicDict dto ) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		String porgpost_id = (String)m.get("porgpost_id");
		String ppartyrole_id  = (String)m.get("ppartyrole_id");
		 
		StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
        long spd = daoStaffPost.delete(porgpost_id, ppartyrole_id);
		
			if (spd > 0) {
				
//				PartyRoleVO partyRoleVO = ejbPartyRole.getPartyRole(ppartyrole_id);
//				
//				Staff ejbStaff = createStaffEJB();
//				StaffVO staffVO = ejbStaff.getStaff(partyRoleVO.getPartyRoleId());
//
//				OrganizationPost ejbOrganizationPost = (OrganizationPost)EJBHelper.creatEJB("ejb/OrganizationPost",OrganizationPostHome.class);
//				OrganizationPostVO orgPostVo = ejbOrganizationPost.getOrganizationPost(porgpost_id);
				
//				Position ejbPosition = (Position)EJBHelper.creatEJB("ejb/Position",PositionHome.class);
//				PositionVO positionVO = ejbPosition.getPosition(orgPostVo.getPositionId());
//
//				PbLogVO pbLogVo = createDefaultLogVO();
//				String logStr = "删除员工岗位[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//				logStr = logStr + ",岗位:" + positionVO.getPositionName() + "]";
//				pbLogVo.setLogDeta(logStr);
//				LogUtils.logActionLog(pbLogVo);
//				
				return true;
			} else {
				return false;
			}
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
	public ArrayList getStaffPrivsByPartyRoleId(DynamicDict dto ) throws Exception{
		
		String ppartyrole_id=  (String)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
        return daoStaffPriv.getStaffPrivsByStaff(ppartyrole_id);
	}

	public String getStaffPrivXMLItemByPartyRoleId(DynamicDict dto ) throws Exception {
		String partyRoleId =  (String)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
        return daoStaffPriv.getStaffPrivXMLItemByPartyRoleId(partyRoleId);
	}
	
	public List getStaffPrivilegeRegionInfo(DynamicDict dto  ) throws Exception {
		
		Map m = (Map)dto.getValueByName("parameter") ;
		String partyRoleId = (String)m.get("partyRoleId");
		String privilegeId  = (String)m.get("privilegeId");
		 
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
    	return daoStaffPriv.getStaffPrivilegeRegionInfo(privilegeId, partyRoleId );
	}
	/**
	 * 插入员工权限实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工权限标识
	 */
	public String insertStaffPriv(DynamicDict dto ) throws Exception{
		StaffPrivVO vo =  (StaffPrivVO)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
		daoStaffPriv.insert(vo);
        return vo.getStaffPrivId();
	}

	/**
	 * 批量插入员工权限和权限范围实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工权限标识
	 */
public boolean insertBatchStaffPrivAndScopes(DynamicDict dto )  throws Exception{
		
		StaffPrivVO[] staffPrivs =  (StaffPrivVO[] )dto.getValueByName("parameter");
		boolean ret = pinsertBatchStaffPrivAndScopes(dto);
		
		
		if( staffPrivs.length > 0 ){
			String partyRoleId = staffPrivs[0].getPartyRoleId();
			dto.setValueByName("parameter", partyRoleId) ;
			PartyRoleVO partyRoleVO = getPartyRole(dto);
			
			StaffVO staffVO = getStaff(dto);

//			PbLogVO pbLogVo = createDefaultLogVO();
//			String logStr = "增加员工权限[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode()  + "]";
//			pbLogVo.setLogDeta(logStr);
//			LogUtils.logActionLog(pbLogVo);
		}
		
		return ret ;
	}
	  public boolean pinsertBatchStaffPrivAndScopes(DynamicDict dto)  throws Exception{
		  StaffPrivVO[] staffPrivs =  (StaffPrivVO[] )dto.getValueByName("parameter");
			
	    	StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
	        for( int i = 0 ; i < staffPrivs.length ; i ++ ){
	        	StaffPrivVO vo = staffPrivs[i];
	        	List list = daoStaffPriv.findByCond("PARTY_ROLE_ID = " + vo.getPartyRoleId() + " AND PRIVILEGE_ID = " + vo.getPrivId() +
		        		" AND region_id = " + vo.getRegionId() + " AND region_type = '" + vo.getRegionType() + "'");
	        	if( list.size() > 0 ){
	        		continue ;
	        	}
	        	StaffPrivVO voStaffPriv = new StaffPrivVO() ;
	        	voStaffPriv.setPartyRoleId( vo.getPartyRoleId() ) ;
	        	voStaffPriv.setPrivId( vo.getPrivId());
	        	voStaffPriv.setState( "00A" );
	        	voStaffPriv.setEffDate( vo.getEffDate());
	        	voStaffPriv.setExpDate( vo.getExpDate() ) ;
	        	voStaffPriv.setRegionId( vo.getRegionId() );
	        	voStaffPriv.setRegionType( vo.getRegionType() ) ;
	        	daoStaffPriv.insert(voStaffPriv);
	        }
	        return true ;
	    }
	/**
	 * 插入员工权限和权限范围实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工权限标识
	 */
	public boolean insertStaffPrivAndScopes(DynamicDict dto )  throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		String ppartyrole_id = (String)m.get("ppartyrole_id");
		String ppriv_id  = (String)m.get("ppriv_id");
		String pregion_type = (String)m.get("pregion_type");
		String[] pregion_ids  = (String[])m.get("pregion_ids");
		  
		boolean ret = pinsertStaffPrivAndScopes(dto);
		
//		PartyRoleVO partyRoleVO =getPartyRole(ppartyrole_id);
//		
//		StaffVO staffVO = getStaff(ppartyrole_id);
//
//		PrivVO privVO = getPriv(ppriv_id);
//			
//			PbLogVO pbLogVo = createDefaultLogVO();
//			String logStr = "增加员工权限[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//			logStr = logStr + ",权限:" + privVO.getPrivName() + "]";
//			pbLogVo.setLogDeta(logStr);
//			LogUtils.logActionLog(pbLogVo);
		return ret ;
	}

	
	  public boolean pinsertStaffPrivAndScopes(	DynamicDict dto ) throws Exception
	    {
		  
		  Map m = (Map)dto.getValueByName("parameter") ;
			String party_role_id = (String)m.get("ppartyrole_id");
			String priv_id  = (String)m.get("ppriv_id");
			String region_type = (String)m.get("pregion_type");
			String[] region_ids  = (String[])m.get("pregion_ids");
		  
//	    	插入员工权限表(STAFF_PRIVILEGE)
	    	PrivDAO privDao = PrivDAOFactory.getPrivDAO();
	    	PrivVO privVO = privDao.findByPrimaryKey(priv_id);
	    	while( privVO != null ){
		        StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
		        for( int i = 0 ; i < region_ids.length ; i ++ ){
		        	List list = daoStaffPriv.findByCond("PARTY_ROLE_ID = " + party_role_id + " AND PRIVILEGE_ID = " + privVO.getPrivId() +
			        		" AND region_id = " + region_ids[i] + " AND region_type = '" + region_type + "'");
		        	if( list.size() > 0 ){
		        		continue ;
		        	}
		        	StaffPrivVO voStaffPriv = new StaffPrivVO() ;
		        	voStaffPriv.setPartyRoleId( party_role_id ) ;
		        	voStaffPriv.setPrivId(privVO.getPrivId());
		        	voStaffPriv.setState( "00A" );
		        	voStaffPriv.setEffDate( DAOUtils.getFormatedDate());
		        	voStaffPriv.setExpDate( null ) ;
		        	voStaffPriv.setRegionId( region_ids[i] );
		        	voStaffPriv.setRegionType( region_type ) ;
		        	daoStaffPriv.insert(voStaffPriv);
		        }
		        privVO = privDao.findByPrimaryKey(privVO.getParentPrgId());
	    	}
	        return true;
	    }
	/**
	 * 根据标识更新员工权限实体
	 * 
	 * @param vo
	 *            待更新实体
	 * @return 操作结果
	 */
	public boolean updateStaffPriv(DynamicDict dto ) throws Exception{
		StaffPrivVO vo =  (StaffPrivVO)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
        return daoStaffPriv.update(vo.getStaffPrivId(), vo);
	}

	/**
	 * 删除员工权限，同时删除相应员工权限范围实体
	 * 
	 * @param pstaffpriv_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteStaffPriv(DynamicDict dto ) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		String partyRoleId = (String)m.get("partyRoleId");
		String privId  = (String)m.get("privId");
		String regionId = (String)m.get("regionId");
		String regionType  = (String)m.get("regionType");
		 
		StaffPrivVO staffPrivVo = getStaffPriv(dto);
		
		 StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
	     long result =  daoStaffPriv.deleteByCond(" PARTY_ROLE_ID = " + partyRoleId + " AND PRIVILEGE_ID = " + privId + " AND region_id = " + regionId + " AND region_type = '" + regionType +"'");
	 
		if (result > 0) {
			
//			PartyRole ejbPartyRole = createPartyRoleEJB();
//			PartyRoleVO partyRoleVO = ejbPartyRole.getPartyRole(staffPrivVo.getPartyRoleId());
//			
//			Staff ejbStaff = createStaffEJB();
//			StaffVO staffVO = ejbStaff.getStaff(partyRoleVO.getPartyRoleId());
//
//			Priv ejbPriv = (Priv)EJBHelper.creatEJB("ejb/Priv",PrivHome.class);
//			PrivVO privVO = ejbPriv.getPriv(staffPrivVo.getPrivId());
//			
//			PbLogVO pbLogVo = createDefaultLogVO();
//			String logStr = "删除员工权限[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//			logStr = logStr + ",权限:" + privVO.getPrivName() + "]";
//			pbLogVo.setLogDeta(logStr);
//			LogUtils.logActionLog(pbLogVo);
			
			return true;
		}
		return false ;
	}

	  public StaffPrivVO getStaffPriv(DynamicDict dto ) throws Exception
	    {
		  Map m = (Map)dto.getValueByName("parameter") ;
			String partyRoleId = (String)m.get("partyRoleId");
			String privId  = (String)m.get("privId");
			String regionId = (String)m.get("regionId");
			String regionType  = (String)m.get("regionType");
	        StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
	        List list = daoStaffPriv.findByCond(" PARTY_ROLE_ID = " + partyRoleId + " AND PRIVILEGE_ID = " + privId + " AND region_id = " + regionId + " AND region_type = '" + regionType +"'");
	        if( list.size() > 0 ) {
	        	return (StaffPrivVO)list.get(0);
	        }else{
	        	return null ;
	        }
	    }
    /**
     * 删除指定员工的所有员工权限，同时删除相应员工权限范围实体
     * 
     * @param ppartyrole_id
     *            员工标识
     * @return 操作结果
     */
    public boolean deleteStaffPrivsByStaff(DynamicDict dto ) throws Exception{
    	String ppartyrole_id=  (String)dto.getValueByName("parameter");
    	StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
         return daoStaffPriv.deleteByStaff(ppartyrole_id) > 0;
    }


	// **************************************ENTITY[StaffRole]**************************************
	/**
	 * 查询员工角色
	 * 
	 * @param ppartyrole_id
	 *            员工参与人角色标识
	 * @return 操作结果, 返回员工对应的所有员工角色列表(StaffRoleVO构成的ArrayList)
	 */
	public ArrayList getStaffRolesByPartyRoleId(DynamicDict dto) throws Exception{
		String staff_id =  (String)dto.getValueByName("parameter");
		StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
		return daoStaffRole.getStaffRolesByStaff(staff_id);
	}

	
	/**
	 * 查询当前登陆员工所拥有的角色
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCurrentStaffRolesList(DynamicDict dto ) throws Exception{
		LoginRespond loginRespond =  (LoginRespond)dto.getValueByName("parameter");
		//GlobalVariableHelper helper = new GlobalVariableHelper( getRequest());
		ArrayList list ;
		
		//String operId = helper.getVariable(GlobalVariableHelper.OPER_ID);
		//String staffCode = helper.getVariable(GlobalVariableHelper.OPER_CODE);
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(staffCode)){//如果是超级管理员,则返回所有的角色
		if( loginRespond.isSuperStaff() ){
			RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
	        return daoRoles.getAllRoles();
			
		}
		
		StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
        return daoStaffRole.getSimpleStaffRolesByPartyRoleId(loginRespond.getPartyRoleId());
	}

	/**
	 * 插入员工角色实体
	 * 
	 * @param vo
	 *            待插入实体
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工角色标识
	 */
	public void insertStaffRole(DynamicDict dto ) throws Exception{
		StaffRoleVO vo=  (StaffRoleVO)dto.getValueByName("parameter");
		 StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
		 daoStaffRole.insert(vo);
	}

	/**
	 * 批量插入员工角色和员工角色范围实体
	 * 
	 * @param
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工角色标识
	 */
	public boolean insertBatchStaffRoleAndScopes(DynamicDict dto ) throws Exception{
//		ejbStaffRole.insertBatchStaffRoleAndScopes( simpleStaffRoleVO,effDate,expDate);
		Map m = (Map)dto.getValueByName("parameter") ;
		SimpleStaffRoleVO[]  simpleStaffRoleVO = (SimpleStaffRoleVO[] )m.get("simpleStaffRoleVO");
		String effDate  = (String)m.get("effDate");
		String expDate  = (String)m.get("expDate");

		
		for( int i = 0; i < simpleStaffRoleVO.length ; i ++ ){
    		SimpleStaffRoleVO vo = simpleStaffRoleVO[i];
    		
    		 StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
    		 for( int j = 0 ; j < vo.getRegionIds().length ; j ++ ){
 		        StaffRoleVO voStaffRole = new StaffRoleVO();//null, staff_id, role_id, "00A", DAOUtils.getFormatedDate(), null);
 		        voStaffRole.setPartyRoleId( vo.getPartyRoleId() );
 		        voStaffRole.setRoleId( vo.getRoleId() );
 		        voStaffRole.setState( "00A" ) ;
 		        voStaffRole.setEffDate(effDate);
 		        voStaffRole.setExpDate(expDate) ;//增加了生效日期和失效日期
 		        voStaffRole.setRegionId( vo.getRegionIds()[j] ) ;
 		        voStaffRole.setRegionType( vo.getRegionType() ) ;
 		        //插入员工角色表(STAFF_ROLE)
 		        daoStaffRole.insert(voStaffRole);
 	        }
    		
//    		pinsertStaffRoleAndScopes(vo.getPartyRoleId(),vo.getRoleId(),vo.getRegionType(),vo.getRegionIds(),effDate,expDate);
    	}
//    	return true ;
		
		if( simpleStaffRoleVO.length > 0 ){
			String partyRoleId = simpleStaffRoleVO[0].getPartyRoleId() ;
			dto.setValueByName("parameter", partyRoleId);
			PartyRoleVO partyRoleVO = getPartyRole(dto);
			
			StaffVO staffVO = getStaff(dto);
			
//			PbLogVO pbLogVo = createDefaultLogVO();
//			String logStr = "增加员工角色[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() + "]";
//			pbLogVo.setLogDeta(logStr);
//			LogUtils.logActionLog(pbLogVo);
		}
		return true;
	}
	
//    public boolean pinsertBatchStaffRoleAndScopes(SimpleStaffRoleVO[] simpleStaffRoleVO,String effDate, String expDate) throws Exception{
//    	for( int i = 0; i < simpleStaffRoleVO.length ; i ++ ){
//    		SimpleStaffRoleVO vo = simpleStaffRoleVO[i];
//    		pinsertStaffRoleAndScopes(vo.getPartyRoleId(),vo.getRoleId(),vo.getRegionType(),vo.getRegionIds(),effDate,expDate);
//    	}
//    	return true ;
//    }
	/**
	 * 插入员工角色和员工角色范围实体
	 * 
	 * @param
	 * @return 操作结果, 成功时ServiceResult.resultObject为新建员工角色标识
	 */
	public boolean insertStaffRoleAndScopes(DynamicDict dto ) throws Exception{
//		String ppartyrole_id,
//		String prole_id, String pregion_type, String[] pregion_ids
		

		Map m = (Map)dto.getValueByName("parameter") ;
		String ppartyrole_id = (String)m.get("ppartyrole_id");
				 String prole_id  = (String)m.get("prole_id");

				 String pregion_type = (String)m.get("pregion_type");
				 String[] pregion_ids  = (String[])m.get("pregion_ids");
				 m.put("effDate", "");
				 m.put("expDate", "");
				 
		pinsertStaffRoleAndScopes(dto);//后两个参数是生效日期和失效日期
		
		dto.setValueByName("parameter", ppartyrole_id) ;
		PartyRoleVO partyRoleVO = getPartyRole(dto);
		
		StaffVO staffVO = getStaff(dto);
		
		RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
		RolesVO roleVO =  daoRoles.findByPrimaryKey(prole_id);
		
		
//		PbLogVO pbLogVo = createDefaultLogVO();
//		String logStr = "增加员工角色[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//		logStr = logStr + ",角色:" + roleVO.getRoleName() + "]";
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
		
		return true;
	}

	  public String pinsertStaffRoleAndScopes(DynamicDict dto ) throws Exception
	    {
		  Map m = (Map)dto.getValueByName("parameter") ;
			String staff_id = (String)m.get("ppartyrole_id");
					 String role_id  = (String)m.get("prole_id");

					 String region_type = (String)m.get("pregion_type");
					 String[] region_ids  = (String[])m.get("pregion_ids");
					 
					 String effDate = (String)m.get("effDate");
					 String expDate  = (String)m.get("expDate");
		  
		  
	        StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
	        for( int i = 0 ; i < region_ids.length ; i ++ ){
		        StaffRoleVO voStaffRole = new StaffRoleVO();//null, staff_id, role_id, "00A", DAOUtils.getFormatedDate(), null);
		        voStaffRole.setPartyRoleId( staff_id );
		        voStaffRole.setRoleId( role_id );
		        voStaffRole.setState( "00A" ) ;
		        voStaffRole.setEffDate(effDate);
		        voStaffRole.setExpDate(expDate) ;//增加了生效日期和失效日期
		        voStaffRole.setRegionId( region_ids[i] ) ;
		        voStaffRole.setRegionType( region_type ) ;
		        //插入员工角色表(STAFF_ROLE)
		        daoStaffRole.insert(voStaffRole);
	        }
	        return null ;//voStaffRole.getStaffRoleId();
	    }
	/**
	 * 删除员工角色，同时删除相应的员工角色范围实体
	 * 
	 * @param pstaffrole_id
	 *            实体标识
	 * @return 操作结果
	 */
	public boolean deleteStaffRole(DynamicDict dto ) throws Exception {
//		String party_role_id,String role_id,String region_id,String region_type
		

		Map m = (Map)dto.getValueByName("parameter") ;
		String party_role_id = (String)m.get("party_role_id");
		String role_id  = (String)m.get("role_id");
		String region_id = (String)m.get("region_id");
		String region_type  = (String)m.get("region_type");
		
		StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
		long result =  daoStaffRole.delete(party_role_id,role_id,region_id,region_type);	
		
		if (result > 0) {
				dto.setValueByName("parameter", party_role_id) ;
				PartyRoleVO partyRoleVO = getPartyRole(dto);
				
				StaffVO staffVO = getStaff(dto);
				dto.setValueByName("parameter", role_id) ;
				RolesVO roleVO = getRoles(dto);
				
//				PbLogVO pbLogVo = createDefaultLogVO();
//				String logStr = "删除员工角色[姓名:" + partyRoleVO.getPartyRoleName() + ",工号:" + staffVO.getStaffCode() ;
//				logStr = logStr + ",角色:" + roleVO.getRoleName() + "]";
//				pbLogVo.setLogDeta(logStr);
//				LogUtils.logActionLog(pbLogVo);
				
				return true ;
			} else {
				return false;
			}
	}
public RolesVO getRoles(DynamicDict dto ) throws Exception {
	String prole_id = (String)dto.getValueByName("parameter");
	RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
	return  daoRoles.findByPrimaryKey(prole_id);
	
}
    /**
     * 删除指定员工的所有员工角色，同时删除相应的员工角色范围实体
     * 
     * @param ppartyrole_id
     *            员工标识
     * @return 操作结果
     */
    public boolean deleteStaffRolesByStaff(DynamicDict dto ) throws Exception{
    	String ppartyrole_id= (String)dto.getValueByName("parameter");
    	StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
        return daoStaffRole.deleteByStaff(ppartyrole_id)>0 ;
    }

	/**
	 * 检查员工是否已经分配了角色,系统不允许给一个没有任何角色的员工单独分配权限
	 * @param partyRoleId
	 * @return
	 * @throws Exception
	 */
	public boolean checkStaffRoles(DynamicDict dto  ) throws Exception {
		List list = this.getStaffRolesByPartyRoleId( dto );
		if( list.size() > 0 ){
			return true;
		}else{
			return false ;
		}
	}
	
	public boolean addWorkOffice(DynamicDict dto ) throws Exception{
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String officeAddr = (String)m.get("officeAddr");
		 String officeDesc  = (String)m.get("officeDesc");
		 WorkingOfficeVO vo =  	new WorkingOfficeVO("", officeDesc, officeAddr ) ;	 
		 dto.setValueByName("parameter" , vo) ;
		insertWorkingOffice(dto );
		return true ;
	}
	
	public boolean updateWorkOffice(DynamicDict dto ) throws Exception{
//		 String officeId , String officeDesc, String officeAddr
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String officeAddr = (String)m.get("officeAddr");
		 String officeDesc  = (String)m.get("officeDesc");

		 String officeId  = (String)m.get("officeId");
		WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		return daoWorkingOffice.update(officeId,  new WorkingOfficeVO(officeId,officeDesc,officeAddr));
	}
	
	public boolean deleteWorkOffice(DynamicDict dto) throws Exception { 
		
		 String officeId =  (String)dto.getValueByName("parameter");
		OfficeMachineDAO daoOfficeMachine = OfficeMachineDAOFactory.getOfficeMachineDAO();
		daoOfficeMachine.deleteByCond("OFFICE_ID=" + officeId);
        WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
        return daoWorkingOffice.delete(officeId)>0;
	}
	

    //add by xiaoyong  判断"所属部门"及"发展用户归属"的部门类型是否为社会渠道---20090310
	public String judgePartyIsBelongSecietyChannels(DynamicDict dto) throws Exception {

//		Map m = (Map)dto.getValueByName("parameter") ;
//		String org_party_id = (String)m.get("org_party_id");
//		String devUserBelong_id  = (String)m.get("devUserBelong_id");
//		
//		String return_Value="";
//		org_party_id=org_party_id+"";
//		devUserBelong_id=devUserBelong_id+"";
//	 	if(((org_party_id != null) && !"".equals(org_party_id)) ||
//	 			((devUserBelong_id != null) && !"".equals(devUserBelong_id)))
//		{
//	 		CommonDAO dao = new CommonDAO();
//			String sqlStr = "select 1 from organization a,mp_department b"
//				+ " where a.party_id = b.party_id and b.depart_type = 05"
//				+ " and a.party_id = '"+ org_party_id + "'"
//				+ " union " 
//				+ "select 1 from organization a,mp_department b"
//				+ " where a.party_id = b.party_id"
//				+ " and b.depart_type = 05 and a.party_id = '"+ devUserBelong_id+"'";
//			sqlStr=DAOSQLUtils.getFilterSQL(sqlStr);
//			return_Value=dao.querySingleValue(sqlStr);
//		}
//		return return_Value;
		return "" ;
	}
	//add end; ---20090310
}
