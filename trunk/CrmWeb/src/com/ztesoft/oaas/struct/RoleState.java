package com.ztesoft.oaas.struct;

import java.io.Serializable;
public class RoleState implements Serializable
{
    private String privilegeId = "";
    private String privilegeCode = ""; //Ȩ�ޱ���
    private String regionId = ""; //�����ʶ
    private String permitionFlag = ""; /*���������־
    																'0':�Ʒ��ߵĵ���
																	'1':��Դ�ߵ���
																	'2':Ӫ���ĵ���
																	'3':Ӫ���ߵ���֯*/
    private String partyRoleSchema = "";  /*99B	Ҷ�Ӳ˵���
    																	99F	��Ʒ�����ṩ
    																	99G	���۲���Ŀ¼
    																	99H	����ȼ�
    																	99I	�ͻ�����
    																	99J	�ۺϲ�ѯ*/
    private String validTime = ""; //Ȩ����Чʱ�䣨��λΪ���ӣ����һ����


    /**
     * ��ȡ����privilegeId
     * @return privilegeId
     */
    public String getPrivilegeId()
    {
        return privilegeId;
    }
    
    /**
     * ��������privilegeId
     * @param privilegeId ����ֵ
     */
    public void setPrivilegeId(String privilegeId)
    {
        this.privilegeId = privilegeId;
    }

    /**
     * ��ȡ����privilegeCode
     * @return privilegeCode
     */
    public String getPrivilegeCode()
    {
        return privilegeCode;
    }
    
    /**
     * ��������privilegeCode
     * @param privilegeCode ����ֵ
     */
    public void setPrivilegeCode(String privilegeCode)
    {
        this.privilegeCode = privilegeCode;
    }

    /**
     * ��ȡ����regionId
     * @return regionId
     */
    public String getRegionId()
    {
        return regionId;
    }
    
    /**
     * ��������regionId
     * @param regionId ����ֵ
     */
    public void setRegionId(String regionId)
    {
        this.regionId = regionId;
    }

    /**
     * ��ȡ����permitionFlag
     * @return permitionFlag
     */
    public String getPermitionFlag()
    {
        return permitionFlag;
    }
    
    /**
     * ��������permitionFlag
     * @param permitionFlag ����ֵ
     */
    public void setPermitionFlag(String permitionFlag)
    {
        this.permitionFlag = permitionFlag;
    }

    /**
     * ��ȡ����partyRoleSchema
     * @return partyRoleSchema
     */
    public String getPartyRoleSchema()
    {
        return partyRoleSchema;
    }
    
    /**
     * ��������partyRoleSchema
     * @param partyRoleSchema ����ֵ
     */
    public void setPartyRoleSchema(String partyRoleSchema)
    {
        this.partyRoleSchema = partyRoleSchema;
    }

    /**
     * ��ȡ����validTime
     * @return validTime
     */
    public String getValidTime()
    {
        return validTime;
    }
    
    /**
     * ��������validTime
     * @param validTime ����ֵ
     */
    public void setValidTime(String validTime)
    {
        this.validTime = validTime;
    }

}
