package com.ztesoft.oaas.utils;

import java.util.List;

import com.ztesoft.oaas.dao.mpdepart.MpDepartDAO;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAOFactory;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAO;
import com.ztesoft.oaas.dao.mpdepartterm.MpDepartTermDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAO;
import com.ztesoft.oaas.dao.partyrole.PartyRoleDAOFactory;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAO;
import com.ztesoft.oaas.dao.rrbusiness.RrBusinessDAOFactory;
import com.ztesoft.oaas.dao.staff.StaffDAO;
import com.ztesoft.oaas.dao.staff.StaffDAOFactory;
import com.ztesoft.oaas.vo.MpDepartTermVO;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;
import com.ztesoft.oaas.vo.RrBusinessVO;
import com.ztesoft.oaas.vo.StaffInfo;
import com.ztesoft.oaas.vo.StaffVO;

public class StaffHelper {
	public StaffVO getStaffByCode( String staffCode ) throws Exception{
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		List list = daoStaff.findByCond("STAFF_CODE = '" + staffCode +"'") ;
		if( list.size() > 0 ){
			return (StaffVO)list.get(0);
		}else{
			return null ;
		}
	}
	
	private String getCompanyIdByOrgId(String orgId) throws Exception{
		OrganizationDAO daoOrganization = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO orgVO = daoOrganization.findByPrimaryKey(orgId);
		if( orgVO == null ){
			return "";
		}else{
			if( "3".equals(orgVO.getOrgTypeId())){//�ֹ�˾��ORG_TYPE_ID ��3
				return orgVO.getPartyId();
			}else{
				if( "-1".equals(orgVO.getParentPartyId())){
					return "";
				}else{
					return getCompanyIdByOrgId( orgVO.getParentPartyId());
				}
			}
		}
	}
	
	public StaffInfo getStaffInfoByPartyRoleId( String partyRoleId ) throws Exception {
		//��ȡpartyRoleId��Ӧ�Ĺ��Ŷ���
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		StaffVO staffVO = daoStaff.findByPrimaryKey(partyRoleId ) ;
		
		//��ȡpartyRoleId��Ӧ�Ĳ����˽�ɫ����
		PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
		PartyRoleVO partyRoleVo = daoPartyRole.findByPrimaryKey( partyRoleId ) ;
		
		//��ȡ�����˽�ɫ�������ڵ���֯(�����ǰ���Ҳ�����ǲ���)
		OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO orgVo = orgDao.findByPrimaryKey(partyRoleVo.getOrgPartyId());
		
		//�����ն�
		MpDepartTermDAO daoDepartTerm = MpDepartTermDAOFactory.getMpDepartTermDAO();
		MpDepartTermVO departTermVo = null ;
		List list = daoDepartTerm.findByCond("PARTY_ID = " + orgVo.getPartyId());
		if( list.size() > 0 ){
			departTermVo = (MpDepartTermVO)list.get(0);
			departTermVo.getTermCode();
		}
		
		//����
		MpDepartDAO daoDepart = MpDepartDAOFactory.getMpDepartDAO();
		MpDepartVO mpDepartVo = daoDepart.findByPrimaryKey( orgVo.getPartyId() ) ;
		if( mpDepartVo == null && "6".equals(orgVo.getOrgTypeId())){
			//���Ա�����ڵ���֯Ϊ����,��ȡ���ϼ�����
			mpDepartVo = daoDepart.findByPrimaryKey(orgVo.getParentPartyId());
		}
		
		//Ӫҵ��
		RrBusinessDAO daoBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
		RrBusinessVO rrBusinessVo = daoBusiness.findByPrimaryKey( mpDepartVo.getRegionId() );
		
		StaffInfo staffInfo = new StaffInfo() ;
		//��ȡ�������ڵķֹ�˾
		String companyId = getCompanyIdByOrgId( partyRoleVo.getOrgPartyId() ) ;
		staffInfo.setCompanyId(companyId);
		//��ȡ�������ڵİ���
		if( "6".equals(orgVo.getOrgTypeId())){
			staffInfo.setGroupId(orgVo.getPartyId());
		}else{
			staffInfo.setGroupId("-1");
		}
		
		staffInfo.setStaffCode(staffVO.getStaffCode());//����
		staffInfo.setStaffName(partyRoleVo.getPartyRoleName());//Ա������
		staffInfo.setChannelSegmentId(staffVO.getChannelSegmentId());//����ID
		staffInfo.setBusinessId( mpDepartVo.getRegionId() ) ;//Ӫҵ��ID
		staffInfo.setLanId( rrBusinessVo.getLanId() ) ;//������ID
		staffInfo.setBusinessCode( rrBusinessVo.getBusinessCode());//Ӫҵ������
		staffInfo.setDepartType( mpDepartVo.getDepartType() );//��������
		
		if( departTermVo != null ){
			staffInfo.setTermCode(departTermVo.getTermCode());//
		}
		
		if( "6".equals(orgVo.getOrgTypeId())){
			orgVo = orgDao.findByPrimaryKey(orgVo.getParentPartyId());
		}
		staffInfo.setPartyName(orgVo.getOrgName());//������������
		staffInfo.setPartyCode(orgVo.getOrgCode());//������֯����(�������ű���)		
		staffInfo.setPartyId( orgVo.getPartyId() ) ;//��������ID
		
		return staffInfo;
	}
}
