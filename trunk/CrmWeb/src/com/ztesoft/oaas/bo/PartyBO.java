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
	 * ��ȡ��ض����path_code�ֶ�ֵ
	 * @param id		����
	 * @param type	����:1��ʾREGION��,2��ʾORGANIZATION��
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
	 * ��ȡһ����֯�������ϼ���֯��ID
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
	 * �ж�һ��Ա�����Ƿ��Ѿ���ʹ����
	 */
	public boolean checkStaffCode( DynamicDict dto) throws Exception {
		String staffCode = (String)dto.getValueByName("parameter");
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
	    StaffVO vo = daoStaff.getStaffByStaffCode( staffCode );
	    return !(vo == null || "".equals(vo.getPartyRoleId()));
	}
	
	/*
	 * ����Ա��״̬
	 */
	public int passwordActivation( DynamicDict dto ) throws Exception {
			String staffCode = (String)dto.getValueByName("parameter");
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		
		
		//�Ҳ������ţ�����ҳ��1
    	StaffVO staffVO = daoStaff.getStaffByStaffCode( staffCode ) ;
    	if( staffVO == null ){
    		return 1 ;
    	}
    	
    	//�˹����Ҳ�����Ӧ��ɫ������ҳ��1
    	PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
    	PartyRoleVO partyRoleVO = daoPartyRole.findByPrimaryKey( staffVO.getPartyRoleId());
    	if( partyRoleVO == null ){
    		return 1 ;
    	}
    	//DB�����partyRole��loginStatus״̬Ϊ0(��ʾ����Ч�����ü���)����ҳ��2
    	if( "0".equals(partyRoleVO.getLoginStatus() )){
    		return 2 ;
    	}
    	//list�����Ԫ�ز�Ϊ�ձ�ʾ�˹��ŵļ�����û��90������
    	List list = daoPartyRole.findByCond("party_role_id = " + partyRoleVO.getPartyRoleId() + " AND  update_time  < ( " + DatabaseFunction.currentDay() + " - 89)");
    	if(list.size() > 0 ){
    		partyRoleVO.setLoginStatus("2");//2��ʾ������
    		partyRoleVO.setUpdateTime(DateFormatUtils.getFormatedDateTime());//���ü���ʱ��
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
	 * �����ַ��Ϣʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���ַʵ���ID
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
	 * ���ݱ�ʶ���µ�ַ��Ϣ
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateAddr(DynamicDict dto) throws Exception{
		
		AddrVO vo=(AddrVO)dto.getValueByName("parameter");
		AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
		return daoAddr.update(vo.getAddrId(), vo);
	}

	/**
	 * ɾ����ַʵ�壬ͬʱɾ������߼���ַ
	 * 
	 * @param paddr_id
	 *            ��ַ��ʶ
	 * @return �������
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
	 * ���ݵ�ַ��ʶ��ѯ�߼���ַʵ��
	 * 
	 * @param paddr_id ��ַ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪָ����ַ��ʶ��Ӧ���߼���ַ�б�(LogicalAddr���ɵ�ArrayList)
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
	 * �����߼���ַʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�߼���ַID
	 */
	public String insertLogicalAddr(DynamicDict dto )  throws Exception {
		
		LogicalAddrVO vo =  (LogicalAddrVO)dto.getValueByName("parameter");
		LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		daoLogicalAddr.insert(vo);
        return vo.getLogicalAddrId();
	}

	/**
	 * ���ݱ�ʶ����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateLogicalAddr(DynamicDict dto ) throws Exception{
		LogicalAddrVO vo =  (LogicalAddrVO)dto.getValueByName("parameter");
		LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		
		return daoLogicalAddr.update(vo.getLogicalAddrId(), vo);
	}

	/**
	 * ɾ���߼���ַʵ��
	 * 
	 * @param plogicaladdr_id
	 *            �߼���ַ��ʶ
	 * @return �������
	 */
	public boolean deleteLogicalAddr(DynamicDict dto ) throws Exception{
		String plogicaladdr_id = (String)dto.getValueByName("parameter");
		LogicalAddrDAO daoLogicalAddr = LogicalAddrDAOFactory.getLogicalAddrDAO();
		
		return daoLogicalAddr.delete(plogicaladdr_id)>0;	
	}


	// **************************************ENTITY[OrganizationType]**************************************
	/**
	 * ������֯�����ѯ�÷����������֯����
	 * 
	 * @param org_class
	 *            ��֯����
	 * @return ��֯�����б�OrganizationTypeVO���ɵ�ArrayList��
	 */
	public ArrayList getOrganizationTypesByClass(DynamicDict dto ) throws Exception{
		String org_class =  (String)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		 
		return daoOrganizationType.getOrgTypesByOrgClass(org_class);
	}

	/**
	 * ������֯����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�²�����֯����ʵ���ID
	 */
	public String insertOrganizationType(DynamicDict dto ) throws Exception{
		OrganizationTypeVO vo =  (OrganizationTypeVO)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		
		daoOrganizationType.insert(vo);
        return vo.getOrgTypeId();
	}

	/**
	 * ���ݱ�ʶ������֯����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateOrganizationType(DynamicDict dto ) throws Exception{
		OrganizationTypeVO vo =  (OrganizationTypeVO)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		
		
        return daoOrganizationType.update(vo.getOrgTypeId(),vo);
	}

	/**
	 * ɾ����֯����ʵ��
	 * 
	 * @param porganizationtype_id
	 *            ��֯���ͱ�ʶ
	 * @return �������
	 */
	public boolean deleteOrganizationType(DynamicDict dto ) throws Exception{
		String porganizationtype_id=(String)dto.getValueByName("parameter");
		OrganizationTypeDAO daoOrganizationType = OrganizationTypeDAOFactory.getOrganizationTypeDAO();
		
		return daoOrganizationType.delete(porganizationtype_id)>0;
	}

	// **************************************ENTITY[Organization]**************************************
	/**
	 * ������֯ID��ѯ��֯ʵ��
	 * 
	 * @param porganization_id
	 *            ��֯��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪOrganizationVO
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
	 * ������֯ʵ��
	 * ͬʱ���ɵ�ַ����ϵ��Ϣ�������ˡ���֯�������˽�ɫ
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱ���ز����˱�ʶ
	 */
	 public void insertTelecomOrganization(DynamicDict dto ) throws Exception
	    {
		 Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
	    	//�����ַ��ADDRESS
	        AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
	        daoAddr.insert(voAddr);
	        
	        //��������˱�(PARTY)
	   
	        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
	        PartyVO voParty = new PartyVO(null, voOrg.getOrgName(), voOrg.getEffDate(), voOrg.getState(), voOrg.getStateDate(), voAddr.getAddrId(), voOrg.getAddDescription());
	        daoParty.insert(voParty);
	        
	        //������ϵ��Ϣ��(CONTACT_MEDIUM)
	        ContactMediumDAO daoContactMedium = ContactMediumDAOFactory.getContactMediumDAO();
	        daoContactMedium.insert(new ContactMediumVO(null, voAddr.getAddrId(), voParty.getPartyId()));
	        voOrg.setPartyId(voParty.getPartyId());
	        voOrg.setAddrId(voAddr.getAddrId());
	        
	        //������֯��(ORGANIZATION)
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
	                "",//�칫�ص��ʶ
	                "",//��¼����
	                "",//������Ч������
	                "",//�����ϴ��޸�ʱ��
	                "",//��¼״̬
	                "",//��¼����
	                "",//��¼��������
	                ""//�����������ʱ��
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
	     * ����Ե���Ӫ����֯��ͬʱ���ɵ�ַ����ϵ��Ϣ�������ˡ������˽�ɫ���Ե���Ӫ��
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
	                "",//�칫�ص��ʶ
	                "",//��¼����
	                "",//������Ч������
	                "",//�����ϴ��޸�ʱ��
	                "",//��¼״̬
	                "",//��¼����
	                "",//��¼��������
	                ""//�����������ʱ��
	        		);
	        daoPartyRole.insert(voPartyRole);
	        CompetitorDAO daoCompetitor = CompetitorDAOFactory.getCompetitorDAO();
	        daoCompetitor.insert(new CompetitorVO(
	                voPartyRole.getPartyRoleId(),
	                voOrg.getOrgCode(),
	                voOrg.getOrgContent()));
	    }

	/**
	 * ������֯ʵ��
	 * ͬʱ���ɵ�ַ����ϵ��Ϣ�������ˡ���֯�������˽�ɫ��������Ϣ
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱ���ز����˱�ʶ
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
	 * ���ݱ�ʶ����ʵ�壬���θ��µ�ַ�������ˡ�������顢�����˽�ɫ
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateOrganization(DynamicDict dto ) throws Exception{
		Map m = (Map)dto.getValueByName("parameter") ;
		 OrganizationVO voOrg = (OrganizationVO)m.get("voOrg");
		 AddrVO voAddr  = (AddrVO)m.get("voAddr");
		//���µ�ַ
		AddrDAO daoAddr = AddrDAOFactory.getAddrDAO();
		if(voAddr.getAddrId()==null || "".equals(voAddr.getAddrId()))
		{
		    voAddr.setAddrId(voOrg.getAddrId());
		}
		daoAddr.update(voAddr.getAddrId(), voAddr);

		//���²�����
		PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
		PartyVO voParty = new PartyVO(voOrg.getPartyId(), voOrg.getOrgName(), voOrg.getEffDate(), voOrg.getState(), voOrg.getStateDate(), voAddr.getAddrId(), voOrg.getAddDescription());
		daoParty.update(voParty.getPartyId(), voParty);

		//������֯
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
        	num = 0;//û�и���
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
	 * ɾ��ʵ�壬����ɾ����
     * ��֯��λ��ɫ���Ʒ�Χ����֯��λ��ɫ����֯��λ��
     * ���š����Ű�Ρ������ն˷�Χ��
     * ��ϵ��Ϣ���߼���ַ����ַ��������ʶ����Ϣ��
     * �������ࡢ������顢�Ե���Ӫ�̡�
     * ������
	 * 
	 * @param porganization_id
	 *            ��֯��ʶ
	 * @return �������
	 */

	public long deleteOrganization(DynamicDict dto ) throws Exception
    {
		String party_id = (String)dto.getValueByName("parameter");
        long recsDeleted = 0;
        
        OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
        //�����֯���滹���¼���֯,���׳��쳣,��������ִ��
        List list = daoOrganization.findByCond( "parent_party_id = " + party_id );
        if( list.size() > 0 ){
        	//throw new OAASException( OAASError.EXISTS_SUB_ORGANIZATION_ERROR );
		return -3;	
        }
        //�������֯����Ա��,����ɾ��
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        list = daoPartyRole.findByCond( " org_party_id = " + party_id );
        if( list.size() > 0 ){
        	//throw new OAASException( OAASError.ORGANIZATION_WITH_STAFF_ERROR);
		return -4;
        }        
        
        //��֯��λ
        OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
        //��ȡҪɾ������֯�µ���֯��λ
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

        //��ȡҪɾ������֯VO����
        recsDeleted = recsDeleted + daoPartyRole.deleteByParty(party_id);
        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
        return recsDeleted + daoParty.delete(party_id); 
    }

	/**
	 * ��ȡ����TreeList�����XML��ʽ��֯��
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
	 */
	public String getOrganiztionList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}

    /**
     * ��ȡ����TreeList�����XML��ʽ������֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
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
	 * ��ȡ�˵���������Ӧ��Ȩ��Ȩ�����ڵ���֯
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public String getPrivilegeOrganization( DynamicDict dto ) throws Exception { 
		Map m = (Map)dto.getValueByName("parameter") ;
		LoginRespond loginRespond = (LoginRespond)m.get("loginRespond");
		String menuCode  = (String)m.get("menuCode");
		 
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//����ǳ�������Ա���ŵ�½,�򷵻����е���֯�ṹ
		if( loginRespond.isSuperStaff() ){
			dto.setValueByName("parentId", "-1") ;
			return this.getTelecomOrganizationListByParentId(dto);
		}
		RoleState[] roleStates = loginRespond.getRoleState();

		ArrayList orgIdSet = new ArrayList();
		m.put("privType", "-3");
		m.put("privCode", menuCode);
		ArrayList privCodes = getPrivCodeByType(dto);//ͨ���˵������ȡ��Ӧ��Ȩ�ޱ���

		for( int i = 0 ; i < roleStates.length ; i ++ ){
			for( int j = 0 ; j < privCodes.size() ; j ++ ){
				String privCode = (String)privCodes.get(j);
				if( privCode.equals(roleStates[i].getPrivilegeCode()) && "3".equals(roleStates[i].getPermitionFlag())){
					orgIdSet.add(roleStates[i].getRegionId());//��ѯ�Ͳ˵�����Ȩ�޶�Ӧ������ID
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
     * ��ȡ����TreeList�����XML��ʽ���������֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
     */
	public String getPartnerOrganiztionList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllPartnerOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}

    /**
     * ��ȡ����TreeList�����XML��ʽ�Ե���Ӫ����֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
     */
	public String getCompetitorOrganiztionList(DynamicDict dto ) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		ArrayList alOrganizations = daoOrganization.getAllCompetitorOrganizations();
        return XMLSegBuilder.toXmlItems(alOrganizations);
	}


	// **************************************ENTITY[OrganizationPost]**************************************
	/**
	 * ������֯��λʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���֯��λʵ���ʶ
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
	 * ������֯��λ��ʶ����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateOrganizationPost(DynamicDict dto ) throws Exception{
		OrganizationPostVO vo = (OrganizationPostVO)dto.getValueByName("parameter");
		 
		OrganizationPostDAO daoOrgPost = OrganizationPostDAOFactory.getOrganizationPostDAO();
		return daoOrgPost.update(vo.getOrgPostId(), vo);
	}

	/**
     * ɾ����֯��λ��ͬʱɾ����֯��λ��ɫ��Χ����֯��λ��ɫ
	 * 
	 * @param porganizationpost_id
	 *            ��֯��λ��ʶ
	 * @return �������
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
	 * ������֯��ʶ��ѯ��֯�����и�λ
	 * 
	 * @param party_id
	 *            ��֯�������ˣ���ʶ
	 * @return ָ����֯��������֯��λʵ���б�(OrganizationPostVO��ɵ�ArrayList)
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
	 * ��ѯ����ʵ��
	 * 
	 * @param party_id
	 *            ��֯��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪMpDepartVO
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
	 * ���벿��ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ���Ŷ�Ӧ����֯Id
	 */
	public String insertMpDepart(DynamicDict dto ) throws Exception{
		
		MpDepartVO vo= (MpDepartVO)dto.getValueByName("parameter");
		MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
        daoMpDepart.insert(vo);
        return vo.getPartyId();
	}

	/**easonwu
	 * ���ݱ�ʶ���²���ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateMpDepart(DynamicDict dto ) throws Exception{
		MpDepartVO vo =  (MpDepartVO)dto.getValueByName("parameter");
		MpDepartDAO daoMpDepart = MpDepartDAOFactory.getMpDepartDAO();
	        return daoMpDepart.update(vo.getPartyId(), vo);
	}

	/**
	 * ɾ������ʵ�壬ͬʱɾ�����Ű�Ρ������ն˷�Χ
	 * 
	 * @param pmpdepart_id
	 *            ʵ���ʶ
	 * @return �������
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
    	if( "99".equals(type)){//���еĲ���
    		return (ArrayList)daoMpDepart.findByCond( cond );
    	}else{
    		cond = cond + "and dep.depart_type = '" + type + "'";
    		return (ArrayList)daoMpDepart.findByCond( cond );
    	}
    }
	
	
	// **************************************ENTITY[PartyIdentification]**************************************
	/**
	 * ���������ʶ����Ϣʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�������ʶ����Ϣ��ʶ
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
		String logStr = "���Ӳ�����ʶ����Ϣ";
		logStr += "[ ����:" + partyRoleVO.getPartyRoleName() ;
		if( "91A".equals(vo.getSocialIdType())){
			logStr += ",���֤����:" + vo.getSocialId() + "]";
		}else if( "91B".equals(vo.getSocialIdType())){
			logStr += ",����:" + vo.getSocialId() + "]";
		}else if( "91C".equals(vo.getSocialIdType())){
			logStr += ",˰��Ǽ�֤:" + vo.getSocialId() + "]";
		}
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
		
		return identId ;
	}

	/**
	 * ���ݱ�ʶ���²�����ʶ����Ϣʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updatePartyIdentification(DynamicDict dto ) throws Exception{
		
		PartyIdentificationVO vo=  (PartyIdentificationVO)dto.getValueByName("parameter");
		PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
		boolean ret  = daoPartyIdentification.update(vo
				.getIdentId(), vo);
		dto.setValueByName("parameter", vo.getPartyId());
		PartyVO partyVo = getParty(dto);
		
//		PbLogVO pbLogVo = createDefaultLogVO();
		String logStr = "���²�����ʶ����Ϣ";
		logStr += "[ ����:" + partyVo.getPartyName() ;
		if( "91A".equals(vo.getSocialIdType())){
			logStr += ",���֤����:" + vo.getSocialId() + "]";
		}else if( "91B".equals(vo.getSocialIdType())){
			logStr += ",����:" + vo.getSocialId() + "]";
		}else if( "91C".equals(vo.getSocialIdType())){
			logStr += ",˰��Ǽ�֤:" + vo.getSocialId() + "]";
		}
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
		
		return ret;
	}

	/**
	 * ɾ��������ʶ����Ϣʵ��
	 * 
	 * @param ppartyidentification_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public boolean deletePartyIdentification(DynamicDict dto ) throws Exception{
		
		String ppartyidentification_id=  (String)dto.getValueByName("parameter");
		PartyIdentificationDAO daoPartyIdentification = PartyIdentificationDAOFactory.getPartyIdentificationDAO();
		PartyIdentificationVO idVo =  daoPartyIdentification.findByPrimaryKey(ppartyidentification_id);
		
		dto.setValueByName("parameter", idVo.getPartyId()) ;
		PartyVO partyVo = getParty(dto);
		
			if (daoPartyIdentification.delete(ppartyidentification_id) > 0) {
				
//				PbLogVO pbLogVo = createDefaultLogVO();
				String logStr = "ɾ��������ʶ����Ϣ";
				logStr = logStr + "[ ����:" + partyVo.getPartyName() ;
				if( "91A".equals(idVo.getSocialIdType())){
					logStr = logStr + ",���֤����:" + idVo.getSocialId() + "]";
				}else if( "91B".equals(idVo.getSocialIdType())){
					logStr = logStr + ",����:" + idVo.getSocialId() + "]";
				}else if( "91C".equals(idVo.getSocialIdType())){
					logStr = logStr + ",˰��Ǽ�֤:" + idVo.getSocialId() + "]";
				}
//				pbLogVo.setLogDeta(logStr);
//				LogUtils.logActionLog(pbLogVo);
				return true;
			} else {
				return false;
			}
	}

	/**
	 * ���ݲ����˽�ɫ��ʶ��ȡ������ʶ����Ϣ
	 * 
	 * @param pparty_id
	 *            ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�����˽�ɫ��ʶ��Ӧ�Ĳ�����ʶ����Ϣ�б�PartyIdentificationVO���ɵ�ArrayList��
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
	 * ��������˽�ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�����˽�ɫ��ʶ
	 */
	public String insertPartyRole(DynamicDict dto ) throws Exception{
		PartyRoleVO vo=  (PartyRoleVO)dto.getValueByName("parameter");
			PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        daoPartyRole.insert(vo);
	        return vo.getPartyRoleId();		
	}

	/**
	 * ���ݱ�ʶ���²����˽�ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updatePartyRole(DynamicDict dto ) throws Exception{
		PartyRoleVO vo =  (PartyRoleVO)dto.getValueByName("parameter");
		 PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
	        return daoPartyRole.update(vo.getPartyRoleId(), vo);
	    
	}

	/**
	 * ɾ��ʵ��
	 * 
	 * @param ppartyrole_id
	 *            ʵ���ʶ
	 * @return �������
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
	 * ��ѯָ�������˵����в����˽�ɫ
	 * 
	 * @param pparty_id
	 *            �����˱�ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪָ�������˵����в����˽�ɫ�б�PartyRoleVO���ɵ�ArrayList��
	 */
	public ArrayList getPartyRolesByPartyId(DynamicDict dto)  throws Exception{
		String party_id =  (String)dto.getValueByName("parameter");
		PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        return daoPartyRole.getPartyRolesByParty(party_id);
	}


	// **************************************ENTITY[Staff]**************************************
	/**
	 * ����Ա��������ѯԱ��
	 * 
	 * @param voStaff
	 *            ��ѯ�������䲻Ϊnull��""���ֶ���Ϊ������ID���ֶ�Ϊ��ȷƥ��(=)�����ơ��������ֶ�Ϊģ����ѯ(LIKE %...%)
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ����������Ա���б�
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
	 * �Է�ҳ��ʽ��ѯԱ����Ϣ
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
	 * ����Ա��ʵ�壬ͬʱ����
     * �����ˡ����ˡ������˽�ɫ
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public void insertStaff(DynamicDict dto ) throws Exception{
		StaffVO vo =  (StaffVO)dto.getValueByName("parameter");//Ӫҵ��businessId,�����־countyType
		OrganizationDAO organizationDAO = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO orgVO = organizationDAO.findByPrimaryKey(vo.getOrgPartyId());
    	//�жϹ����Ƿ��ظ� 
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
//		pbLogVo.setLogDeta("����Ա��[����:" + voStaff.getPartyName() +",����:" + voStaff.getStaffCode() + "]");
//		LogUtils.logActionLog(pbLogVo);
	}
	
	private void insertStaffWithoutCheck(DynamicDict dto ) throws Exception {
		StaffVO vo  =  (StaffVO)dto.getValueByName("parameter");
    	String projectCode = CrmParamsConfig.getInstance().getParamValue("PROJECT_CODE");
    	if( CrmConstants.CQ_PROJECT_CODE.equals(projectCode)){
	    	if( !"12".equals(vo.getChannelSegmentId()) && !"-1".equals(vo.getChannelSegmentId())){
		    	//�����ż�����֯����ǰ׺
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
        //��������˱�(PARTY)
        daoParty.insert(voParty);
        
        vo.setPartyId(voParty.getPartyId());
        
        //����Ա����ɫSTAFF_ROLE
//        StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
//        daoStaffRole.insert(new StaffRoleVO(vo.getPartyRoleId(),bo.getRoleId(),
//        		vo.getState(),vo.getEffDate(),vo.getExpDate(),vo.getReginId(),
//        		vo.getregin_type(),vo.getprivlUseType()));
        
        //������˱�(INDIVIDUAL)
        IndividualDAO daoIndividual = IndividualDAOFactory.getIndividualDAO();
        daoIndividual.insert(new IndividualVO(
                voParty.getPartyId(), vo.getGender(), 
                vo.getBirthPlace(), 
                vo.getBirthDate(), vo.getMaritalStatus(), 
                vo.getSkill()
                ));
        
        //��������˽�ɫ��PARTY_ROLE
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        if( "".equals(vo.getOfficeId())){
        	vo.setOfficeId(null);
        }
        
        PartyRoleVO voPartyRole = new PartyRoleVO(
                null,//�����˽�ɫID
                vo.getOrgPartyId(),//������֯
                vo.getOrgPartyId(),
                vo.getPartyName(),//����������
                vo.getPartyRoleType(),//����������
                vo.getOrgManager(),//�Ƿ���֯������.
                DateFormatUtils.getFormatedDateTime(),//��������(��ǰʱ��)
                vo.getStaffDesc(),//��ע
                vo.getState(),//״̬
                vo.getEffDate(),//��Ч����
                vo.getExpDate(),//ʧЧ����
                vo.getOfficeId(),//�칫�ص�
                vo.getPassword(),//����
                
                vo.getPwdvalidtype(),//������Ч������
                DateFormatUtils.getFormatedDateTime(),//����������ʱ��
                "2",//��½״̬,0:����; 1������;  2:���û�����ǿ���޸Ŀ���(���ڼ�Ȩ������δ����,��ʱ��ʹ��0,��������ʹ��2)
                "0",//��½����
                vo.getLimitCount(),//��½��������
                null//�����������ʱ��
        		);
        voPartyRole.setLanId(vo.getLanId());
        voPartyRole.setSPartyId(voParty.getPartyId());
        voPartyRole.setDevOrgId(vo.getDevOrgId());
        daoPartyRole.insert(voPartyRole);
        voPartyRole = daoPartyRole.findByPrimaryKey(voPartyRole.getPartyRoleId());
        /*
         * ���PARTY_ROLE��������ֶ�������,��Ҫ���ü�Ȩ�������ļ��ܽӿڶ�������м���
         * ���ڼ�Ȩ�������ļ����㷨�ǽ�PARTY_ROLE_ID�ֶ���Ϊ���ܵ�KEY,���Ա�����ִ��
         * �������,Ȼ�󽫲��������ɵ�PARTY_ROLE_ID�ֶ�ȡ����Ϊ��Կ,�����ĵ�����һ�����
         * ���ܽӿ�,��ȡ���ܺ������,�ٸ���PARTY_ROLE��.
         */
		if(voPartyRole.getPassword() == null || "".equals(voPartyRole.getPassword())){//�������Ϊ��,��ʹ�������ļ��е�Ĭ������
			String defaultPwd = CrmParamsConfig.getInstance().getParamValue("STAFF_PWD");
			voPartyRole.setPassword(defaultPwd);
			//����������ļ������Ĭ�������Ǽ��ܵ����ģ���Ҫ�����ܲ���
			// ======> ע�͵�����Ĵ��� �Ͽ���
//			EncryptRequest req = new EncryptRequest() ;
//			req.setFlag("F");
//			req.setSecretBuff(voPartyRole.getPassword()) ;
//			EncryptRespond res = OAASProxy.encrypt(req);
//			voPartyRole.setPassword(res.getStrResultBuff());
			// ======> ע�͵�����Ĵ��� �Ͽ���
		}
		
		//�����ĵ��������party_role_id��Ϊǰ׺�����м��� easonwu 2009-12-21ע��֮
//		EncryptRequest encryptRequest = new EncryptRequest() ;
		//encryptRequest.setFlag( "T" ) ;
		//encryptRequest.setProclaimedBuff(voPartyRole.getPartyRoleId()+voPartyRole.getPassword());
		
		/*
		 * ���ü�Ȩ������ִ�й��ż��ܲ���,���ݲ����˽�ɫID����������,��Ȩ�������������˽�ɫID��������Ӻ����
		 */
//		encryptRequest.setFlag( "S" ) ;//��������������������
//		encryptRequest.setSecretBuff(voPartyRole.getPartyRoleId());//�����˽�ɫID
//		encryptRequest.setProclaimedBuff(voPartyRole.getPassword());//��������
//		
//		EncryptRespond encryptRespond = OAASProxy.encrypt(encryptRequest);//���ͼ�������
//		String newPassword = encryptRespond.getStrResultBuff();//��ȡ���صļ����ַ���
		
		// ���뾭��DES���ܺ�浽PARTY_ROLE���ݱ��С������Ͽ�����2010/4/15
        DesEncrypt descript= new DesEncrypt();	//ʹ��DES����
        String newPassword =descript.encryptTriDes(voPartyRole.getPartyRoleId()+voPartyRole.getPassword());
		
		voPartyRole.setPassword(newPassword) ;
		daoPartyRole.update(voPartyRole.getPartyRoleId(),voPartyRole) ;

		daoPartyRole.insertStaffPassLog(voPartyRole.getPartyRoleId(),voPartyRole.getPassword());

		//����Ա����STAFF
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
//		pbLogVo.setLogType("2");//�˵�����
//		pbLogVo.setStaffId(helper.getVariable(GlobalVariableHelper.OPER_ID));
//		pbLogVo.setStaffName( helper.getVariable(GlobalVariableHelper.OPER_NAME));
//		pbLogVo.setState("10A");
//		pbLogVo.setStateDate(DateFormatUtils.getFormatedDateTime());
//		pbLogVo.setCreateDate(DateFormatUtils.getFormatedDateTime());
//		pbLogVo.setLogDate( DateFormatUtils.getFormatedDateTime());
//		pbLogVo.setModuleId("16000");//ϵͳģ���ʶ
//		pbLogVo.setActId(null);
//		return pbLogVo;
//	}
	/**
	 * ��������Ա��
	 * @param orgPartyId	������֯
	 * @param startStaffCode	��ʼ����
	 * @param endStaffCode		��������
	 * @param staffName			Ա������
	 * @param orgPostId			��λ
	 * @param state					״̬
	 * @param effDate				��Ч����
	 * @param expDate				��������
	 * @param channelSegmentId		����
	 * @param password			����
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
    	
    	//Ա��������֯
    	OrganizationDAO orgDAO = OrganizationDAOFactory.getOrganizationDAO();
    	OrganizationVO orgVO = orgDAO.findByPrimaryKey(orgPartyId);
    	
    	long start = new Long(startStaffCode).longValue();
    	long end = new Long(endStaffCode).longValue();
    	boolean first = true;
    	int successCount = 0 ;
    	int failureCount = 0 ;
    	for( long i = start; i <= end; i ++ ){
    		//�жϹ����Ƿ��ظ�
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
        		//�����Ѿ�����,������Ӻ����������
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
    		//������� liuyuming 2010-0920
    		DesEncrypt descript= new DesEncrypt();
    		password = descript.encryptTriDes(password);
    		staffVO.setPassword(password);
    		staffVO.setPwdvalidtype("1");//Ĭ�ϰ�������
    		staffVO.setSkill("");
    		staffVO.setStaffDesc("�������ӵ�Ա��");
    		staffVO.setState("00A");
    		//staffVO.setLimitCount("3");
			//�Ͽ������������� 2010/4/19
			staffVO.setLimitCount("999999");	//��¼��������Ĭ��Ϊ���ֵ��999999��
			staffVO.setOrgManager(orgManager);
			staffVO.setGender(gender);
			//�Ͽ������������� 2010/4/19
    		staffVO.setUpdateTime(DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT));
    		staffVO.setStateDate(DateFormatUtils.formatDate(CrmConstants.DATE_FORMAT));
    		staffVO.setChannelSegmentId( channelSegmentId );
    		staffVO.setLanId(lanId);
    		staffVO.setBusinessId(businessId);
    		staffVO.setCountyType(countyType);
    		dto.setValueByName("parameter", staffVO);
    		insertStaffWithoutCheck(dto);

//          ��������Ա������ʱ���ε���λ��Ϣ
//        	StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
//        	StaffPostVO staffPostVO = new StaffPostVO();
//        	staffPostVO.setEffDate(effDate);
//        	staffPostVO.setExpDate(expDate);
//        	staffPostVO.setOrgPostId(orgPostId);
//        	staffPostVO.setPartyRoleId(staffVO.getPartyRoleId());
//        	staffPostVO.setState(state);
//          daoStaffPost.insert(staffPostVO);
    	}

    	String returnStr = "�ɹ����ӹ���"+successCount+"�����ظ�����" + failureCount+"����";
    	if( failureCount > 0 ){
    		returnStr = returnStr + "\n�ظ����Ž�������,";
	    	if( failureCount > 10 ){
	    		returnStr = returnStr + "���ǲ����ظ��Ĺ���:\n" + returnValue + "...";
	    	}else{
	    		returnStr = returnStr + "�����ظ��Ĺ���:\n" + returnValue ;
	    	}
    	}
    	return returnStr;
	}
	
	
	 /**
	     * ����һ��������ID����Ա��
	     * ����Ա����ͬʱ����
	     * �����ˡ����ˡ������˽�ɫ
	     */
	    public void pcopyStaff( String sourcePartyRoleId, String staffCode, String staffName, String password ) throws Exception{
	    	
	    }
	/**
	 * ����Ա��
	 * @param sourcePartyRoleId	�����Ƶ�Ա���Ĳ����˽�ɫID
	 * @param staffCode	����
	 * @param staffName	����
	 * @param password	����
	 * @throws Exception
	 */
	public void copyStaff(DynamicDict dto  ) throws Exception {

		Map m = (Map)dto.getValueByName("parameter") ;
		String sourcePartyRoleId = (String)m.get("sourcePartyRoleId");
		String staffCode  = (String)m.get("staffCode");

		String staffName = (String)m.get("staffName");
		String password  = (String)m.get("password");		 
		
		//��ѯ��Դ Ա������Ϣ
    	StaffDAO staffDao = StaffDAOFactory.getStaffDAO();
    	StaffVO staffVo = staffDao.findByPrimaryKey( sourcePartyRoleId );
    	System.out.println("PartyRoleId: " + staffVo.getPartyRoleId());
    	System.out.println("business_id: " + staffVo.getBusinessId());
    	System.out.println("county_type: " + staffVo.getCountyType());
    	
    	
    	//��ѯ��ԴԱ���Ĳ����˽�ɫ��Ϣ
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
    	
    	//��ѯ��ԴԱ���Ĳ�������Ϣ
    	PartyDAO partyDao = PartyDAOFactory.getPartyDAO();
    	PartyVO partyVo = partyDao.findByPrimaryKey( partyRoleVo.getSPartyId() );
    	
    	//��ѯ��ԴԱ���ĸ�����Ϣ
    	IndividualDAO individualDao = IndividualDAOFactory.getIndividualDAO();
    	IndividualVO individualVo = individualDao.findByPrimaryKey( partyVo.getPartyId() );
    	
    	//��������˱�
    	partyVo.setPartyId( null ) ;
    	partyVo.setPartyName( staffName ) ;
    	partyVo.setAddDescription( null ) ;
    	partyVo.setAddrId( null ) ;
    	partyDao.insert( partyVo ) ;
    	
    	
    	//������˱�
    	individualVo.setPartyId( partyVo.getPartyId() );
    	individualDao.insert( individualVo );
    	
    	if( password == null || "".equals(password)){//�������Ϊ��,��������ļ��л�ȡĬ������
    		password = CrmParamsConfig.getInstance().getParamValue("STAFF_PWD");//��ȡĬ������
    		//Ĭ�������������ļ��������ķ�ʽ���棬��Ҫ����Ĭ������
    		EncryptRequest req = new EncryptRequest() ;
    		req.setFlag("F");
    		req.setSecretBuff(password) ;
    		EncryptRespond res = OAASProxy.encrypt(req);
    		password = res.getStrResultBuff();
    	}
    	//��������˽�ɫ��
    	partyRoleVo.setPassword( password ) ;
    	partyRoleVo.setPartyRoleName( staffName ) ;
    	partyRoleVo.setSPartyId( partyVo.getPartyId() );
    	partyRoleVo.setLoginStatus("2");
    	partyRoleDao.insert( partyRoleVo );
    	
    	//�����������������ĵ�,����ͨ����Ȩ��������������,Ȼ����updateһ��
//    	EncryptRequest encryptRequest = new EncryptRequest() ;
//		encryptRequest.setFlag( "T" ) ;
//		encryptRequest.setProclaimedBuff(partyRoleVo.getPartyRoleId()+partyRoleVo.getPassword());
//		
//		EncryptRespond encryptRespond = OAASProxy.encrypt(encryptRequest);
//		String newPassword = encryptRespond.getStrResultBuff();
		
		
		DesEncrypt descript= new DesEncrypt();
		//����ʹ��party_role_id+password��ϼ��� liuyuming 2010-09-22
        String newPassword =descript.encryptTriDes(partyRoleVo.getPassword());
		
		partyRoleVo.setPassword(newPassword) ;
		partyRoleDao.update(partyRoleVo.getPartyRoleId(),partyRoleVo) ;
		
		partyRoleDao.insertStaffPassLog(partyRoleVo.getPartyRoleId(),partyRoleVo.getPassword());
    	
    	staffVo.setStaffCode(staffCode);
    	staffVo.setPartyRoleId( partyRoleVo.getPartyRoleId());
    	staffVo.setStaffDesc(partyRoleVo.getPartyRoleName());
    	//�Ͽ��� 2010/4/19 TODO
    	//���������staffVo����business_id��county_id��û��ֵ
    	staffDao.insert( staffVo );//����Ա����
    	
    	//����Ա����λ��
    	StaffPostDAO staffPostDao = StaffPostDAOFactory.getStaffPostDAO();
    	List staffPostList = staffPostDao.findByCond( "party_role_id = " + sourcePartyRoleId);
    	for( int i = 0 ; i < staffPostList.size() ; i ++ ){
    		StaffPostVO staffPostVo = (StaffPostVO)staffPostList.get(i);
    		staffPostVo.setPartyRoleId( partyRoleVo.getPartyRoleId() );
    		staffPostDao.insert( staffPostVo ) ;
    	}
    	
    	//����Ա��Ȩ�ޱ�
    	StaffPrivDAO staffPrivilegeDao = StaffPrivDAOFactory.getStaffPrivDAO();
    	List staffPrivList = staffPrivilegeDao.findByCond( " party_role_id = " + sourcePartyRoleId);
    	for( int i = 0 ; i < staffPrivList.size() ; i ++ ){
    		StaffPrivVO staffPrivVo = (StaffPrivVO)staffPrivList.get(i);
    		staffPrivVo.setPartyRoleId( partyRoleVo.getPartyRoleId() );
    		staffPrivilegeDao.insert( staffPrivVo ) ;
    	}
    	
    	//����Ա����ɫ��
    	StaffRoleDAO staffRoleDao = StaffRoleDAOFactory.getStaffRoleDAO();
    	List staffRoleList = staffRoleDao.findByCond( " party_role_id = " + sourcePartyRoleId);
    	for( int i = 0 ; i < staffRoleList.size() ; i ++ ){
    		StaffRoleVO staffRoleVo = (StaffRoleVO)staffRoleList.get(i ) ;
    		staffRoleVo.setPartyRoleId( partyRoleVo.getPartyRoleId() ) ;
    		staffRoleDao.insert( staffRoleVo ) ;
    	}
		
    	//����Ա���� �Ͽ��� 2010/4/16 TODO
//    	staffVo.setPartyId(partyId);
//    	staffVo.setPartyRoleId(pPartyRoleId);
//    	staffVo.setStaffCode(staffCode);
//    	staffVo.setPassword(password);
//    	staffDao.insert(staffVo);
    	
//		PbLogVO pbLogVo = createDefaultLogVO();
//		pbLogVo.setLogDeta("����Ա��[����:" + staffName +",����:" + staffCode + "]");
//		LogUtils.logActionLog(pbLogVo);
	}
	/**
     * ����Ա����ͬʱ����
     * �����ˡ����ˡ������˽�ɫ
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateStaff(DynamicDict dto ) throws Exception{
		//�����˽�ɫ
		StaffVO vo =  (StaffVO)dto.getValueByName("parameter");//������ϵ㿴��vo��Ϣ�Ƿ������������ǳ����ʶcountyType��
		//�ἮbirthPlace�����maritalStatus���Ƿ������ORG_MANAGER,����skill   ==����Щ�ֶζ��ж�Ӧ��ֵ
        PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
        //����partyRoleVO����������ʲô
        //�����partyRoleVO���ݺ�����Ĳ�һ�� ���Բο�orgManage��ֵ
        //����vo��ֵ��T��������partyRoleVO�ͱ����F
        PartyRoleVO partyRoleVO = daoPartyRole.findByPrimaryKey(vo.getPartyRoleId());
        partyRoleVO.setState(vo.getState());
        // ======> ��ҳ���ϵ���ֵ���ǵ������ݿ��в������partyRoleVO����ֶ� �Ͽ���[BEGIN] 2010/4/15
        partyRoleVO.setPartyId(vo.getPartyRoleId());
        partyRoleVO.setOrgPartyId(vo.getOrgPartyId());
        partyRoleVO.setPartyRoleType(vo.getPartyRoleType());
        partyRoleVO.setOrgManager(vo.getOrgManager());
        partyRoleVO.setCreateDate(vo.getCreateDate());
        partyRoleVO.setLimitCount(vo.getLimitCount());
        //�޸�������Ҫ�������
        DesEncrypt descript= new DesEncrypt();
        String validatePwd =descript.encryptTriDes(vo.getPassword());
        partyRoleVO.setPassword(validatePwd);
        partyRoleVO.setPwdValidType(vo.getPwdvalidtype());
        partyRoleVO.setSPartyId(vo.getPartyId());
        // ======> ��ҳ���ϵ���ֵ���ǵ������ݿ��в������partyRoleVO����ֶ� �Ͽ���[END]
        if( !daoPartyRole.update(vo.getPartyRoleId(),partyRoleVO)){
        	return false ;
        }
        
        // ���˱�(INDIVIDUAL)  �Ͽ��� 2010/4/15
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
        
        //�����˱�(PARTY)
        PartyDAO daoParty = PartyDAOFactory.getPartyDAO();
        PartyVO partyVO = daoParty.findByPrimaryKey(partyRoleVO.getSPartyId());
        partyVO.setState( vo.getState() );
        // ======> ��ҳ���ϵ���ֵ���ǵ������ݿ��в������partyRoleVO����ֶ� �Ͽ���[BEGIN] 2010/4/15
        partyVO.setPartyName(vo.getPartyName());
        partyVO.setEffDate(vo.getEffDate());
        partyVO.setStateDate(vo.getStateDate());
        // ======> ��ҳ���ϵ���ֵ���ǵ������ݿ��в������partyRoleVO����ֶ� �Ͽ���[END]
        if( !daoParty.update( partyRoleVO.getSPartyId(), partyVO ) ){
        	return false ;
        }
        
        //����Ա����STAFF �Ͽ��� 2010/4/16
        StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
        StaffVO staffVO = daoStaff.findByPrimaryKey(vo.getPartyRoleId());
        staffVO.setBusinessId(vo.getBusinessId());
        staffVO.setCountyType(vo.getCountyType());
        if ( !daoStaff.update(staffVO.getPartyRoleId(), staffVO)){
        	return false;
        }
        
        
        return true ;
//		PbLogVO pbLogVo = createDefaultLogVO();
//		pbLogVo.setLogDeta("�޸�Ա��[����:" + vo.getPartyName() +",����:" + vo.getStaffCode() + "]");
//		LogUtils.logActionLog(pbLogVo);
//		return ret ;
	}

	 public boolean updateStaffState(DynamicDict dto ) throws Exception
	 {
	    	//�����˽�ɫ
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
	 *            ʵ���ʶ
	 * @return �������
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
//		pbLogVo.setLogDeta("ע��Ա��[����:" + staffVo.getStaffCode() + "]");
//		LogUtils.logActionLog(pbLogVo);
		return ret ;
	}

//	// **************************************ENTITY[Individual]**************************************
	// **************************************ENTITY[WorkingOffice]**************************************
	/**
	 * ����칫�ص�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�칫�ص��ʶ
	 */
	public String insertWorkingOffice(DynamicDict dto) throws Exception{
		WorkingOfficeVO vo =  (WorkingOfficeVO)dto.getValueByName("parameter");
		WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		daoWorkingOffice.insert(vo);
        return vo.getOfficeId();
	}

	/**
	 * ���ݱ�ʶ���°칫�ص�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateWorkingOffice(DynamicDict dto ) throws Exception{
		WorkingOfficeVO vo =  (WorkingOfficeVO)dto.getValueByName("parameter");
		WorkingOfficeDAO daoWorkingOffice = WorkingOfficeDAOFactory.getWorkingOfficeDAO();
		return daoWorkingOffice.update(vo.getOfficeId(), vo);
	}

	/**
	 * ɾ���칫�ص�ʵ��
	 * 
	 * @param pworkingoffice_id
	 *            ʵ���ʶ
	 * @return �������
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
	 * ����Ա����ʶ��ѯԱ����λ
	 * 
	 * @param ppartyrole_id
	 *            Ա�������˽�ɫ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪָ��Ա��������Ա����λ�б�StaffPostVO���ɵ�ArrayList��
	 */
	public ArrayList getStaffPostByPartyRoleId(DynamicDict dto) throws Exception{
		String ppartyrole_id  =  (String)dto.getValueByName("parameter");	
		StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
		return daoStaffPost.getStaffPostsByStaff(ppartyrole_id);
	}

	
	/**
	 * ����Ա����λʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public void insertStaffPost(DynamicDict dto) throws Exception{
		StaffPostVO[] vos =  (StaffPostVO[])dto.getValueByName("parameter");
		 StaffPostDAO daoStaffPost = StaffPostDAOFactory.getStaffPostDAO();
        StaffPostVO vo = vos[0];
        daoStaffPost.deleteByCond("PARTY_ROLE_ID=" + vo.getPartyRoleId());
        for( int i = 0 ; i < vos.length ; i ++ ){
        	daoStaffPost.insert(vos[i]);
        }
        
        
		//��־������ʼ
//		PartyRoleVO partyRoleVO = getPartyRole(vo.getPartyRoleId());//��ȡԱ������
//		
//		StaffVO staffVO = getStaff(partyRoleVO.getPartyRoleId());//��ȡ����

//		PbLogVO pbLogVo = createDefaultLogVO();
//		String logStr = "����Ա����λ[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//		//logStr = logStr + ",��λ:" + positionVO.getPositionName() + "]";
//		pbLogVo.setLogDeta(logStr);
//		LogUtils.logActionLog(pbLogVo);
//		//��־��������
	}

	/**
	 * ���ݱ�ʶ����Ա����λʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
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
//		String logStr = "����Ա����λ[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//		logStr = logStr + ",��λ:" + positionVO.getPositionName() + "]";
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
	 * ɾ��Ա����λʵ��
	 * 
	 * @param pstaffpost_id
	 *            ʵ���ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ
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
//				String logStr = "ɾ��Ա����λ[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//				logStr = logStr + ",��λ:" + positionVO.getPositionName() + "]";
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
	 * ��ѯԱ��Ȩ��
	 * 
	 * @param ppartyrole_id
	 *            Ա�������˽�ɫ��ʶ
	 * @return ������������ز�����Ȩ���б�StaffPrivVO���ɵ�ArrayList��
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
	 * ����Ա��Ȩ��ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա��Ȩ�ޱ�ʶ
	 */
	public String insertStaffPriv(DynamicDict dto ) throws Exception{
		StaffPrivVO vo =  (StaffPrivVO)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
		daoStaffPriv.insert(vo);
        return vo.getStaffPrivId();
	}

	/**
	 * ��������Ա��Ȩ�޺�Ȩ�޷�Χʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա��Ȩ�ޱ�ʶ
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
//			String logStr = "����Ա��Ȩ��[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode()  + "]";
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
	 * ����Ա��Ȩ�޺�Ȩ�޷�Χʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա��Ȩ�ޱ�ʶ
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
//			String logStr = "����Ա��Ȩ��[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//			logStr = logStr + ",Ȩ��:" + privVO.getPrivName() + "]";
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
		  
//	    	����Ա��Ȩ�ޱ�(STAFF_PRIVILEGE)
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
	 * ���ݱ�ʶ����Ա��Ȩ��ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateStaffPriv(DynamicDict dto ) throws Exception{
		StaffPrivVO vo =  (StaffPrivVO)dto.getValueByName("parameter");
		StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
        return daoStaffPriv.update(vo.getStaffPrivId(), vo);
	}

	/**
	 * ɾ��Ա��Ȩ�ޣ�ͬʱɾ����ӦԱ��Ȩ�޷�Χʵ��
	 * 
	 * @param pstaffpriv_id
	 *            ʵ���ʶ
	 * @return �������
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
//			String logStr = "ɾ��Ա��Ȩ��[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//			logStr = logStr + ",Ȩ��:" + privVO.getPrivName() + "]";
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
     * ɾ��ָ��Ա��������Ա��Ȩ�ޣ�ͬʱɾ����ӦԱ��Ȩ�޷�Χʵ��
     * 
     * @param ppartyrole_id
     *            Ա����ʶ
     * @return �������
     */
    public boolean deleteStaffPrivsByStaff(DynamicDict dto ) throws Exception{
    	String ppartyrole_id=  (String)dto.getValueByName("parameter");
    	StaffPrivDAO daoStaffPriv = StaffPrivDAOFactory.getStaffPrivDAO();
         return daoStaffPriv.deleteByStaff(ppartyrole_id) > 0;
    }


	// **************************************ENTITY[StaffRole]**************************************
	/**
	 * ��ѯԱ����ɫ
	 * 
	 * @param ppartyrole_id
	 *            Ա�������˽�ɫ��ʶ
	 * @return �������, ����Ա����Ӧ������Ա����ɫ�б�(StaffRoleVO���ɵ�ArrayList)
	 */
	public ArrayList getStaffRolesByPartyRoleId(DynamicDict dto) throws Exception{
		String staff_id =  (String)dto.getValueByName("parameter");
		StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
		return daoStaffRole.getStaffRolesByStaff(staff_id);
	}

	
	/**
	 * ��ѯ��ǰ��½Ա����ӵ�еĽ�ɫ
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
		//if( superStaffCode.equals(staffCode)){//����ǳ�������Ա,�򷵻����еĽ�ɫ
		if( loginRespond.isSuperStaff() ){
			RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
	        return daoRoles.getAllRoles();
			
		}
		
		StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
        return daoStaffRole.getSimpleStaffRolesByPartyRoleId(loginRespond.getPartyRoleId());
	}

	/**
	 * ����Ա����ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա����ɫ��ʶ
	 */
	public void insertStaffRole(DynamicDict dto ) throws Exception{
		StaffRoleVO vo=  (StaffRoleVO)dto.getValueByName("parameter");
		 StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
		 daoStaffRole.insert(vo);
	}

	/**
	 * ��������Ա����ɫ��Ա����ɫ��Χʵ��
	 * 
	 * @param
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա����ɫ��ʶ
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
 		        voStaffRole.setExpDate(expDate) ;//��������Ч���ں�ʧЧ����
 		        voStaffRole.setRegionId( vo.getRegionIds()[j] ) ;
 		        voStaffRole.setRegionType( vo.getRegionType() ) ;
 		        //����Ա����ɫ��(STAFF_ROLE)
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
//			String logStr = "����Ա����ɫ[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() + "]";
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
	 * ����Ա����ɫ��Ա����ɫ��Χʵ��
	 * 
	 * @param
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա����ɫ��ʶ
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
				 
		pinsertStaffRoleAndScopes(dto);//��������������Ч���ں�ʧЧ����
		
		dto.setValueByName("parameter", ppartyrole_id) ;
		PartyRoleVO partyRoleVO = getPartyRole(dto);
		
		StaffVO staffVO = getStaff(dto);
		
		RolesDAO daoRoles = RolesDAOFactory.getRolesDAO();
		RolesVO roleVO =  daoRoles.findByPrimaryKey(prole_id);
		
		
//		PbLogVO pbLogVo = createDefaultLogVO();
//		String logStr = "����Ա����ɫ[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//		logStr = logStr + ",��ɫ:" + roleVO.getRoleName() + "]";
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
		        voStaffRole.setExpDate(expDate) ;//��������Ч���ں�ʧЧ����
		        voStaffRole.setRegionId( region_ids[i] ) ;
		        voStaffRole.setRegionType( region_type ) ;
		        //����Ա����ɫ��(STAFF_ROLE)
		        daoStaffRole.insert(voStaffRole);
	        }
	        return null ;//voStaffRole.getStaffRoleId();
	    }
	/**
	 * ɾ��Ա����ɫ��ͬʱɾ����Ӧ��Ա����ɫ��Χʵ��
	 * 
	 * @param pstaffrole_id
	 *            ʵ���ʶ
	 * @return �������
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
//				String logStr = "ɾ��Ա����ɫ[����:" + partyRoleVO.getPartyRoleName() + ",����:" + staffVO.getStaffCode() ;
//				logStr = logStr + ",��ɫ:" + roleVO.getRoleName() + "]";
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
     * ɾ��ָ��Ա��������Ա����ɫ��ͬʱɾ����Ӧ��Ա����ɫ��Χʵ��
     * 
     * @param ppartyrole_id
     *            Ա����ʶ
     * @return �������
     */
    public boolean deleteStaffRolesByStaff(DynamicDict dto ) throws Exception{
    	String ppartyrole_id= (String)dto.getValueByName("parameter");
    	StaffRoleDAO daoStaffRole = StaffRoleDAOFactory.getStaffRoleDAO();
        return daoStaffRole.deleteByStaff(ppartyrole_id)>0 ;
    }

	/**
	 * ���Ա���Ƿ��Ѿ������˽�ɫ,ϵͳ�������һ��û���κν�ɫ��Ա����������Ȩ��
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
	

    //add by xiaoyong  �ж�"��������"��"��չ�û�����"�Ĳ��������Ƿ�Ϊ�������---20090310
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
