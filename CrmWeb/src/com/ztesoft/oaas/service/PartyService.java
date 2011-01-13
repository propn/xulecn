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
	 * ��ȡԱ�����ڵĴ����ID
	 * @return
	 * @throws Exception
	 */
	public String getStaffDefaultDealExchId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		return helper.getVariable(GlobalVariableHelper.DEFAULT_DEAL_EXCH_ID);
	}
	/**
	 * ��ȡԱ�����ڵĴ��������
	 * @return
	 * @throws Exception
	 */
	public String getStaffDefaultDealExchName() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		return helper.getVariable(GlobalVariableHelper.DEFAULT_DEAL_EXCH_NAME);
	}

	/**
	 * ��ȡ�������ڵķֹ�˾ID
	 * @return
	 * @throws Exception
	 */
	public String getStaffCompanyId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String companyId = helper.getVariable(GlobalVariableHelper.COMPANY_ID);
		return companyId ;
	}
	/**
	 * ��ȡ�������ڵķֹ�˾NAME
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
	 * ��ȡ����������֯ID(���Ż��߰���)
	 * @return
	 * @throws Exception
	 */
	public String getStaffOrganizationId() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String staffOrganizationId = helper.getVariable(GlobalVariableHelper.OPER_ORG_ID);
		return staffOrganizationId ;
	}
	/**
	 * ��ȡ����������֯����(���Ż��߰���)
	 * @return
	 * @throws Exception
	 */
	public String getStaffOrganizationName() throws Exception{
		GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest() ) ;
		String staffOrganizationName = helper.getVariable(GlobalVariableHelper.OPER_ORG_NAME);
		return staffOrganizationName ;
	}
	/**
	 * ��ȡ��ض����path_code�ֶ�ֵ
	 * @param id		����
	 * @param type	����:1��ʾREGION��,2��ʾORGANIZATION��
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
	 * ��ȡϵͳ�����ļ��������õĳ����û�����
	 * @return
	 */
	public String getSuperStaffCode(){
		String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		return superStaffCode ;
	}
	
	public boolean ifSuperManager() {
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");//��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;
		return loginRespond.isSuperStaff() ;
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//����ǳ�������Ա����,�򷵻����еĵ�����֯���ڵ�
		//	return true ;
		//}else{
		//	return false ;
		//}
	}
	/**
	 * ��ȡһ����֯�������ϼ���֯��ID
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
	 * �ж�һ��Ա�����Ƿ��Ѿ���ʹ����
	 */
	public boolean checkStaffCode( String staffCode ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"checkStaffCode") ;
		dto.setValueByName("parameter", staffCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
		
	}
	
	/*
	 * ����Ա��״̬
	 */
	public int passwordActivation( String staffCode ) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"passwordActivation") ;
		dto.setValueByName("parameter", staffCode) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Integer)dto.getValueByName("result")).intValue() ;
	}
	
	 
	//��ȡ��ǰ���û�ID
	public String getStaffIdOfCurrentUser() throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");

		if( loginRespond.isSuperStaff() ){
			return "999999";
		}
		return loginRespond.getPartyRoleId() ;
	}
	
	/**
	 * ���ݾ�վID��ȡ�����ڵĴ����
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RegionVO getDealExchByExchStation(String id) throws Exception{
		RegionVO vo = this.getRegionById( id ) ;//��ȡ����վVO
		vo = this.getRegionById( vo.getParentRegionId() ) ;//��ȡ��ĸ��VO
		return this.getRegionById( vo.getParentRegionId() ) ;//��ȡ������� VO
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
	 * ���ݲ����˱�ʶ��ѯ���ַ��Ϣ
	 * 
	 * @param pparty_id
	 *            �����˱�ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪAddrVO����
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
	 * �����ַ��Ϣʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���ַʵ���ID
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
	 * ���ݱ�ʶ���µ�ַ��Ϣ
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateAddr(AddrVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ����ַʵ�壬ͬʱɾ������߼���ַ
	 * 
	 * @param paddr_id
	 *            ��ַ��ʶ
	 * @return �������
	 */
	public boolean deleteAddr(String paddr_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteAddr") ;
		dto.setValueByName("parameter", paddr_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}


	// **************************************ENTITY[LogicalAddr]**************************************
	/**
	 * ���ݵ�ַ��ʶ��ѯ�߼���ַʵ��
	 * 
	 * @param paddr_id ��ַ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪָ����ַ��ʶ��Ӧ���߼���ַ�б�(LogicalAddr���ɵ�ArrayList)
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
	 * �����߼���ַʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�߼���ַID
	 */
	public String insertLogicalAddr(LogicalAddrVO vo)  throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertLogicalAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result"));
	}

	/**
	 * ���ݱ�ʶ����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateLogicalAddr(LogicalAddrVO vo) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"updateLogicalAddr") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ���߼���ַʵ��
	 * 
	 * @param plogicaladdr_id
	 *            �߼���ַ��ʶ
	 * @return �������
	 */
	public boolean deleteLogicalAddr(String plogicaladdr_id) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"deleteLogicalAddr") ;
		dto.setValueByName("parameter", plogicaladdr_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}


	// **************************************ENTITY[OrganizationType]**************************************
	/**
	 * ������֯�����ѯ�÷����������֯����
	 * 
	 * @param org_class
	 *            ��֯����
	 * @return ��֯�����б�OrganizationTypeVO���ɵ�ArrayList��
	 */
	public ArrayList getOrganizationTypesByClass(String org_class) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganizationTypesByClass") ;
		dto.setValueByName("parameter", org_class) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result")) ;
	}

	/**
	 * ������֯����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�²�����֯����ʵ���ID
	 */
	public String insertOrganizationType(OrganizationTypeVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertOrganizationType") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ���ݱ�ʶ������֯����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateOrganizationType(OrganizationTypeVO vo) throws Exception{
        DynamicDict dto = getServiceDTO("PartyBO" ,"updateOrganizationType") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ����֯����ʵ��
	 * 
	 * @param porganizationtype_id
	 *            ��֯���ͱ�ʶ
	 * @return �������
	 */
	public boolean deleteOrganizationType(String porganizationtype_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteOrganizationType") ;
		dto.setValueByName("parameter", porganizationtype_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	// **************************************ENTITY[Organization]**************************************
	/**
	 * ������֯ID��ѯ��֯ʵ��
	 * 
	 * @param porganization_id
	 *            ��֯��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪOrganizationVO
	 */
	 public OrganizationVO getOrganization(String party_id) throws Exception
	    {
		 DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganization") ;
			dto.setValueByName("parameter", party_id) ;
			dto = ActionDispatch.dispatch(dto);
			return ((OrganizationVO)dto.getValueByName("result")) ;
	    }
	
	

	/**
	 * ������֯ʵ��
	 * ͬʱ���ɵ�ַ����ϵ��Ϣ�������ˡ���֯�������˽�ɫ
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱ���ز����˱�ʶ
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
	     * ����Ե���Ӫ����֯��ͬʱ���ɵ�ַ����ϵ��Ϣ�������ˡ������˽�ɫ���Ե���Ӫ��
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
	 * ������֯ʵ��
	 * ͬʱ���ɵ�ַ����ϵ��Ϣ�������ˡ���֯�������˽�ɫ��������Ϣ
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱ���ز����˱�ʶ
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
	 * ���ݱ�ʶ����ʵ�壬���θ��µ�ַ�������ˡ�������顢�����˽�ɫ
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
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
	public long deleteOrganization(String pparty_id) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"deleteOrganization") ;
		dto.setValueByName("parameter", pparty_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Long)dto.getValueByName("result")).longValue() ;
	}
	
	/**
	 * ��ȡ����TreeList�����XML��ʽ��֯��
	 * 
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
	 */
	public String getOrganiztionList() throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getOrganiztionList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}

    /**
     * ��ȡ����TreeList�����XML��ʽ������֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
     */
	public String getTelecomOrganizationList() throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"getTelecomOrganizationList") ;
		dto.setValueByName("parameter", null) ;
		dto = ActionDispatch.dispatch(dto);
		return ((String)dto.getValueByName("result")) ;
	}
	
	/**
	 * ���ݲ˵������ȡ�˵���Ӧ��Ȩ�����ڵĵ���Χ
	 * @param menuCode	�˵�����
	 * @param regionType ��������:'0':�Ʒ��ߵĵ���;'1':��Դ�ߵ���;'2':Ӫ���ĵ���;'3':Ӫ���ߵ���֯
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
		
		Object obj = RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");//��Session�л�ȡ��Ȩ���������صĵ�½���
		LoginRespond loginRespond = (LoginRespond) obj;
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//����ǳ�������Ա����,�򷵻����еĵ�����֯���ڵ�
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
	 * ��ȡ�˵���������Ӧ��Ȩ��Ȩ�����ڵ���֯
	 * @param menuCode
	 * @return
	 * @throws Exception
	 */
	public String getPrivilegeOrganization( String menuCode ) throws Exception { 
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(loginRespond.getStaffCode())){//����ǳ�������Ա���ŵ�½,�򷵻����е���֯�ṹ
		if( loginRespond.isSuperStaff() ){
			return this.getTelecomOrganizationListByParentId("-1");
		}
		RoleState[] roleStates = loginRespond.getRoleState();

		ArrayList orgIdSet = new ArrayList();
		ArrayList privCodes = getPrivCodeByType("3",menuCode);//ͨ���˵������ȡ��Ӧ��Ȩ�ޱ���

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
     * ��ȡ����TreeList�����XML��ʽ���������֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
     */
	public String getPartnerOrganiztionList() throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPartnerOrganiztionList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

    /**
     * ��ȡ����TreeList�����XML��ʽ�Ե���Ӫ����֯��
     * 
     * @return �������, �ɹ�ʱServiceResult.resultObjectΪ������TreeList�����xml�ַ���
     */
	public String getCompetitorOrganiztionList() throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getCompetitorOrganiztionList") ;
				dto.setValueByName("parameter", null) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}


	// **************************************ENTITY[OrganizationPost]**************************************
	/**
	 * ������֯��λʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½���֯��λʵ���ʶ
	 */
	public String insertOrganizationPost(OrganizationPostVO vo) throws Exception {
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertOrganizationPost") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ������֯��λ��ʶ����ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateOrganizationPost(OrganizationPostVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateOrganizationPost") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
     * ɾ����֯��λ��ͬʱɾ����֯��λ��ɫ��Χ����֯��λ��ɫ
	 * 
	 * @param porganizationpost_id
	 *            ��֯��λ��ʶ
	 * @return �������
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
	 * ������֯��ʶ��ѯ��֯�����и�λ
	 * 
	 * @param party_id
	 *            ��֯�������ˣ���ʶ
	 * @return ָ����֯��������֯��λʵ���б�(OrganizationPostVO��ɵ�ArrayList)
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
	 * ��ѯ����ʵ��
	 * 
	 * @param party_id
	 *            ��֯��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪMpDepartVO
	 */
	public MpDepartVO getMpDepartByPartyId(String party_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getMpDepartByPartyId") ;
				dto.setValueByName("parameter", party_id) ;
				dto = ActionDispatch.dispatch(dto);
				return ((MpDepartVO)dto.getValueByName("result")) ;
	}

	/**
	 * ���벿��ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ���Ŷ�Ӧ����֯Id
	 */
	public String insertMpDepart(MpDepartVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertMpDepart") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ���ݱ�ʶ���²���ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateMpDepart(MpDepartVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateMpDepart") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ������ʵ�壬ͬʱɾ�����Ű�Ρ������ն˷�Χ
	 * 
	 * @param pmpdepart_id
	 *            ʵ���ʶ
	 * @return �������
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
	 * ���������ʶ����Ϣʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�������ʶ����Ϣ��ʶ
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
	 * ���ݱ�ʶ���²�����ʶ����Ϣʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updatePartyIdentification(PartyIdentificationVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updatePartyIdentification") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ɾ��������ʶ����Ϣʵ��
	 * 
	 * @param ppartyidentification_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public boolean deletePartyIdentification(String ppartyidentification_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deletePartyIdentification") ;
		dto.setValueByName("parameter", ppartyidentification_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
	}

	/**
	 * ���ݲ����˽�ɫ��ʶ��ȡ������ʶ����Ϣ
	 * 
	 * @param pparty_id
	 *            ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�����˽�ɫ��ʶ��Ӧ�Ĳ�����ʶ����Ϣ�б�PartyIdentificationVO���ɵ�ArrayList��
	 */
	public ArrayList getPartyIdentificationByPartyId(String pparty_role_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"getPartyIdentificationByPartyId") ;
		dto.setValueByName("parameter", pparty_role_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((ArrayList)dto.getValueByName("result"));
	}


	// **************************************ENTITY[PartyRole]**************************************
	/**
	 * ��������˽�ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�����˽�ɫ��ʶ
	 */
	public String insertPartyRole(PartyRoleVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertPartyRole") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ���ݱ�ʶ���²����˽�ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updatePartyRole(PartyRoleVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updatePartyRole") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * ɾ��ʵ��
	 * 
	 * @param ppartyrole_id
	 *            ʵ���ʶ
	 * @return �������
	 */
	public boolean deletePartyRole(String partyrole_id) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"deletePartyRole") ;
		dto.setValueByName("parameter", partyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * ��ѯָ�������˵����в����˽�ɫ
	 * 
	 * @param pparty_id
	 *            �����˱�ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪָ�������˵����в����˽�ɫ�б�PartyRoleVO���ɵ�ArrayList��
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
	 * ����Ա��������ѯԱ��
	 * 
	 * @param voStaff
	 *            ��ѯ�������䲻Ϊnull��""���ֶ���Ϊ������ID���ֶ�Ϊ��ȷƥ��(=)�����ơ��������ֶ�Ϊģ����ѯ(LIKE %...%)
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ����������Ա���б�
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
	 * �Է�ҳ��ʽ��ѯԱ����Ϣ
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
	 * ����Ա��ʵ�壬ͬʱ����
     * �����ˡ����ˡ������˽�ɫ
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
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
	 * ����Ա��
	 * @param sourcePartyRoleId	�����Ƶ�Ա���Ĳ����˽�ɫID
	 * @param staffCode	����
	 * @param staffName	����
	 * @param password	����
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
     * ����Ա����ͬʱ����
     * �����ˡ����ˡ������˽�ɫ
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateStaff(StaffVO vo) throws Exception{
		// ���ݹ�����StaffVO�Ѿ��������޸ĺ��ҳ����Ϣ����
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateStaff") ;
		StaffVO b = vo ;
		// ��StaffVO���ݸ�BO����BO����ͨ��getValueByName("parameter")���
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
	 *            ʵ���ʶ
	 * @return �������
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
	 * ����칫�ص�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�칫�ص��ʶ
	 */
	public String insertWorkingOffice(WorkingOfficeVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertWorkingOffice") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ���ݱ�ʶ���°칫�ص�ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateWorkingOffice(WorkingOfficeVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateWorkingOffice") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * ɾ���칫�ص�ʵ��
	 * 
	 * @param pworkingoffice_id
	 *            ʵ���ʶ
	 * @return �������
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
	 * ����Ա����ʶ��ѯԱ����λ
	 * 
	 * @param ppartyrole_id
	 *            Ա�������˽�ɫ��ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪָ��Ա��������Ա����λ�б�StaffPostVO���ɵ�ArrayList��
	 */
	public ArrayList getStaffPostByPartyRoleId(String ppartyrole_id) throws Exception{
	DynamicDict dto = getServiceDTO("PartyBO" ,"getStaffPostByPartyRoleId") ;
	dto.setValueByName("parameter", ppartyrole_id) ;
	dto = ActionDispatch.dispatch(dto);
	return ((ArrayList)dto.getValueByName("result"));

	}

	/**
	 * ��ѯ��ǰ��½Ա����ӵ�еĸ�λ
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCurrentStaffPost(String orgPartyId) throws Exception{
		LoginRespond loginRespond = (LoginRespond)RequestContext.getContext().getHttpSession().getAttribute("LoginRespond");
		
		//GlobalVariableHelper helper = new GlobalVariableHelper( RequestContext.getContext().getHttpRequest());
		
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( superStaffCode.equals(helper.getVariable(GlobalVariableHelper.OPER_CODE))){//����ǳ�������Ա�û�,�򷵻�ָ����֯�����и�λ
		if( loginRespond.isSuperStaff() ){
			return this.getPositionListByPartyId(orgPartyId);
		}
		//return this.getStaffPostByPartyRoleId(helper.getVariable(GlobalVariableHelper.OPER_ID));
		return this.getStaffPostByPartyRoleId( loginRespond.getPartyRoleId() );
	}
	/**
	 * ����Ա����λʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public void insertStaffPost(StaffPostVO[] vos) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffPost") ;
		dto.setValueByName("parameter", vos) ;
		dto = ActionDispatch.dispatch(dto);

	}

	/**
	 * ���ݱ�ʶ����Ա����λʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
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
	 * ɾ��Ա����λʵ��
	 * 
	 * @param pstaffpost_id
	 *            ʵ���ʶ
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ
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
	 * ��ѯԱ��Ȩ��
	 * 
	 * @param ppartyrole_id
	 *            Ա�������˽�ɫ��ʶ
	 * @return ������������ز�����Ȩ���б�StaffPrivVO���ɵ�ArrayList��
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
	 * ����Ա��Ȩ��ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա��Ȩ�ޱ�ʶ
	 */
	public String insertStaffPriv(StaffPrivVO vo) throws Exception{

		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffPriv") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
				return ((String)dto.getValueByName("result")) ;
	}

	/**
	 * ��������Ա��Ȩ�޺�Ȩ�޷�Χʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա��Ȩ�ޱ�ʶ
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
	 * ����Ա��Ȩ�޺�Ȩ�޷�Χʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա��Ȩ�ޱ�ʶ
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
	 * ���ݱ�ʶ����Ա��Ȩ��ʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������
	 */
	public boolean updateStaffPriv(StaffPrivVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"updateStaffPriv") ;
		dto.setValueByName("parameter", vo) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

	}

	/**
	 * ɾ��Ա��Ȩ�ޣ�ͬʱɾ����ӦԱ��Ȩ�޷�Χʵ��
	 * 
	 * @param pstaffpriv_id
	 *            ʵ���ʶ
	 * @return �������
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
     * ɾ��ָ��Ա��������Ա��Ȩ�ޣ�ͬʱɾ����ӦԱ��Ȩ�޷�Χʵ��
     * 
     * @param ppartyrole_id
     *            Ա����ʶ
     * @return �������
     */
    public boolean deleteStaffPrivsByStaff(String ppartyrole_id) throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaffPrivsByStaff") ;
		dto.setValueByName("parameter", ppartyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;
    }


	// **************************************ENTITY[StaffRole]**************************************
	/**
	 * ��ѯԱ����ɫ
	 * 
	 * @param ppartyrole_id
	 *            Ա�������˽�ɫ��ʶ
	 * @return �������, ����Ա����Ӧ������Ա����ɫ�б�(StaffRoleVO���ɵ�ArrayList)
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
	 * ��ѯ��ǰ��½Ա����ӵ�еĽ�ɫ
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
	 * ��ѯ��ǰ��½Ա����ӵ�еĽ�ɫ
	 * @return
	 * @throws Exception
	 */
	public String getCurrentStaffRoles() throws Exception{
		ArrayList list = getCurrentStaffRolesList();
		return XMLSegBuilder.toXmlItems(list);
	}
	/**
	 * ����Ա����ɫʵ��
	 * 
	 * @param vo
	 *            ������ʵ��
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա����ɫ��ʶ
	 */
	public void insertStaffRole(StaffRoleVO vo) throws Exception{
		DynamicDict dto = getServiceDTO("PartyBO" ,"insertStaffRole") ;
				dto.setValueByName("parameter", vo) ;
				dto = ActionDispatch.dispatch(dto);
	}

	/**
	 * ��������Ա����ɫ��Ա����ɫ��Χʵ��
	 * 
	 * @param
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա����ɫ��ʶ
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
	 * ����Ա����ɫ��Ա����ɫ��Χʵ��
	 * 
	 * @param
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪ�½�Ա����ɫ��ʶ
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
	 * ɾ��Ա����ɫ��ͬʱɾ����Ӧ��Ա����ɫ��Χʵ��
	 * 
	 * @param pstaffrole_id
	 *            ʵ���ʶ
	 * @return �������
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
     * ɾ��ָ��Ա��������Ա����ɫ��ͬʱɾ����Ӧ��Ա����ɫ��Χʵ��
     * 
     * @param ppartyrole_id
     *            Ա����ʶ
     * @return �������
     */
    public boolean deleteStaffRolesByStaff(String ppartyrole_id) throws Exception{
    	DynamicDict dto = getServiceDTO("PartyBO" ,"deleteStaffRolesByStaff") ;
		dto.setValueByName("parameter", ppartyrole_id) ;
		dto = ActionDispatch.dispatch(dto);
		return ((Boolean)dto.getValueByName("result")).booleanValue() ;

    }

	/**
	 * ���Ա���Ƿ��Ѿ������˽�ɫ,ϵͳ�������һ��û���κν�ɫ��Ա����������Ȩ��
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
//		�������ļ��ж�ȡ��10000�Ž���ͳһ�ĸ�CRM�Ĳ�ѯ֪ʶ��Ĺ��ź�����
		String staffCode = "";
		String password = "" ;

		if( "query".equalsIgnoreCase(type)){
			staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_CODE");
			password = CrmParamsConfig.getInstance().getParamValue("ACK_QUERY_STAFF_PWD");
		}else if( "admin".equalsIgnoreCase(type) || "manager".equalsIgnoreCase(type)){
			staffCode = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_CODE");
			password = CrmParamsConfig.getInstance().getParamValue("ACK_MANAGER_STAFF_PWD");
		}
		//�Դ������ļ��ж�ȡ������������н���
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

	
    //add by xiaoyong  �ж�"��������"��"��չ�û�����"�Ĳ��������Ƿ�Ϊ�������---20090310
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
