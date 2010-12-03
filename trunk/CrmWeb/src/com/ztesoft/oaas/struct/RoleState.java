package com.ztesoft.oaas.struct;

import java.io.Serializable;
public class RoleState implements Serializable
{
    private String privilegeId = "";
    private String privilegeCode = ""; //权限编码
    private String regionId = ""; //区域标识
    private String permitionFlag = ""; /*区域允许标志
    																'0':计费线的地域
																	'1':资源线地域
																	'2':营销的地域
																	'3':营销线的组织*/
    private String partyRoleSchema = "";  /*99B	叶子菜单项
    																	99F	商品服务提供
    																	99G	定价参数目录
    																	99H	号码等级
    																	99I	客户类型
    																	99J	综合查询*/
    private String validTime = ""; //权限有效时间（单位为分钟），最长一星期


    /**
     * 获取属性privilegeId
     * @return privilegeId
     */
    public String getPrivilegeId()
    {
        return privilegeId;
    }
    
    /**
     * 设置属性privilegeId
     * @param privilegeId 属性值
     */
    public void setPrivilegeId(String privilegeId)
    {
        this.privilegeId = privilegeId;
    }

    /**
     * 获取属性privilegeCode
     * @return privilegeCode
     */
    public String getPrivilegeCode()
    {
        return privilegeCode;
    }
    
    /**
     * 设置属性privilegeCode
     * @param privilegeCode 属性值
     */
    public void setPrivilegeCode(String privilegeCode)
    {
        this.privilegeCode = privilegeCode;
    }

    /**
     * 获取属性regionId
     * @return regionId
     */
    public String getRegionId()
    {
        return regionId;
    }
    
    /**
     * 设置属性regionId
     * @param regionId 属性值
     */
    public void setRegionId(String regionId)
    {
        this.regionId = regionId;
    }

    /**
     * 获取属性permitionFlag
     * @return permitionFlag
     */
    public String getPermitionFlag()
    {
        return permitionFlag;
    }
    
    /**
     * 设置属性permitionFlag
     * @param permitionFlag 属性值
     */
    public void setPermitionFlag(String permitionFlag)
    {
        this.permitionFlag = permitionFlag;
    }

    /**
     * 获取属性partyRoleSchema
     * @return partyRoleSchema
     */
    public String getPartyRoleSchema()
    {
        return partyRoleSchema;
    }
    
    /**
     * 设置属性partyRoleSchema
     * @param partyRoleSchema 属性值
     */
    public void setPartyRoleSchema(String partyRoleSchema)
    {
        this.partyRoleSchema = partyRoleSchema;
    }

    /**
     * 获取属性validTime
     * @return validTime
     */
    public String getValidTime()
    {
        return validTime;
    }
    
    /**
     * 设置属性validTime
     * @param validTime 属性值
     */
    public void setValidTime(String validTime)
    {
        this.validTime = validTime;
    }

}
