package com.ztesoft.oaas.service;

import java.io.Serializable;
public class RoleStateBsnStrucVO implements Serializable {

    private String privilegeID;//Ȩ�ޱ�ʶ
    private String PrivilegeCode;//Ȩ�ޱ���
    private String RegionID;//�����ʶ
    private String permitionFlag;//�����־
    private String partyRoleSchema;// �����˽�ɫȨ������" 98A" ���б������Ȩ��"98B" ���뱾����ͬ��������Ȩ��
    private String validTime;//Ȩ����Чʱ�䣨��λΪ���ӣ����һ����
    
	public String getPartyRoleSchema() {
		return partyRoleSchema;
	}
	public void setPartyRoleSchema(String partyRoleSchema) {
		this.partyRoleSchema = partyRoleSchema;
	}
	public String getPermitionFlag() {
		return permitionFlag;
	}
	public void setPermitionFlag(String permitionFlag) {
		this.permitionFlag = permitionFlag;
	}
	public String getPrivilegeCode() {
		return PrivilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.PrivilegeCode = privilegeCode;
	}
	public String getPrivilegeID() {
		return privilegeID;
	}
	public void setPrivilegeID(String privilegeID) {
		this.privilegeID = privilegeID;
	}
	public String getRegionID() {
		return RegionID;
	}
	public void setRegionID(String regionID) {
		this.RegionID = regionID;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

}
