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
			if( "3".equals(orgVO.getOrgTypeId())){//分公司的ORG_TYPE_ID 是3
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
		//获取partyRoleId对应的工号对象
		StaffDAO daoStaff = StaffDAOFactory.getStaffDAO();
		StaffVO staffVO = daoStaff.findByPrimaryKey(partyRoleId ) ;
		
		//获取partyRoleId对应的参与人角色对象
		PartyRoleDAO daoPartyRole = PartyRoleDAOFactory.getPartyRoleDAO();
		PartyRoleVO partyRoleVo = daoPartyRole.findByPrimaryKey( partyRoleId ) ;
		
		//获取参与人角色对象所在的组织(可能是班组也可能是部门)
		OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();
		OrganizationVO orgVo = orgDao.findByPrimaryKey(partyRoleVo.getOrgPartyId());
		
		//部门终端
		MpDepartTermDAO daoDepartTerm = MpDepartTermDAOFactory.getMpDepartTermDAO();
		MpDepartTermVO departTermVo = null ;
		List list = daoDepartTerm.findByCond("PARTY_ID = " + orgVo.getPartyId());
		if( list.size() > 0 ){
			departTermVo = (MpDepartTermVO)list.get(0);
			departTermVo.getTermCode();
		}
		
		//部门
		MpDepartDAO daoDepart = MpDepartDAOFactory.getMpDepartDAO();
		MpDepartVO mpDepartVo = daoDepart.findByPrimaryKey( orgVo.getPartyId() ) ;
		if( mpDepartVo == null && "6".equals(orgVo.getOrgTypeId())){
			//如果员工所在的组织为班组,则取其上级部门
			mpDepartVo = daoDepart.findByPrimaryKey(orgVo.getParentPartyId());
		}
		
		//营业区
		RrBusinessDAO daoBusiness = RrBusinessDAOFactory.getRrBusinessDAO();
		RrBusinessVO rrBusinessVo = daoBusiness.findByPrimaryKey( mpDepartVo.getRegionId() );
		
		StaffInfo staffInfo = new StaffInfo() ;
		//获取工号所在的分公司
		String companyId = getCompanyIdByOrgId( partyRoleVo.getOrgPartyId() ) ;
		staffInfo.setCompanyId(companyId);
		//获取工号所在的班组
		if( "6".equals(orgVo.getOrgTypeId())){
			staffInfo.setGroupId(orgVo.getPartyId());
		}else{
			staffInfo.setGroupId("-1");
		}
		
		staffInfo.setStaffCode(staffVO.getStaffCode());//工号
		staffInfo.setStaffName(partyRoleVo.getPartyRoleName());//员工姓名
		staffInfo.setChannelSegmentId(staffVO.getChannelSegmentId());//渠道ID
		staffInfo.setBusinessId( mpDepartVo.getRegionId() ) ;//营业区ID
		staffInfo.setLanId( rrBusinessVo.getLanId() ) ;//本地网ID
		staffInfo.setBusinessCode( rrBusinessVo.getBusinessCode());//营业区编码
		staffInfo.setDepartType( mpDepartVo.getDepartType() );//部门类型
		
		if( departTermVo != null ){
			staffInfo.setTermCode(departTermVo.getTermCode());//
		}
		
		if( "6".equals(orgVo.getOrgTypeId())){
			orgVo = orgDao.findByPrimaryKey(orgVo.getParentPartyId());
		}
		staffInfo.setPartyName(orgVo.getOrgName());//所属部门名称
		staffInfo.setPartyCode(orgVo.getOrgCode());//所属组织编码(所属部门编码)		
		staffInfo.setPartyId( orgVo.getPartyId() ) ;//所属部门ID
		
		return staffInfo;
	}
}
