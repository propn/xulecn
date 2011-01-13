package com.ztesoft.oaas.exception;

import java.util.ResourceBundle;

import com.ztesoft.common.exception.CrmError;

public class OAASError extends CrmError{
	public static final String EXISTS_SUB_MENU_ERROR = "menu_error_0001"; //菜单节点下面还有下级节点,不能删除
	public static final String EXISTS_SUB_REGION_ERROR="region_error_0001";//区域节点下面还有下级节点,不能删除
	public static final String EXISTS_SUB_ORGANIZATION_ERROR="organization_error_0001";//组织节点下面还有下级节点,不能删除
	public static final String ORGANIZATION_WITH_STAFF_ERROR="organization_error_0002";//组织下面还有员工,该组织不能删除.
	public static final String EXISTS_SUB_PRIVILEGE_ERROR="privilege_error_0001";//权限下面还有下级权限,不能删除
	public static final String ROLE_WITH_POSITION="roles_error_0001";//角色已经被分配给某个岗位,不能删除
	public static final String ORG_POST_WITH_STAFF="org_post_error_0001";//组织岗位已经被分配给员工,不能删除
	public static final String STAFF_SAME_STAFF_CODE="staff_error_0001";//相同的工号异常
    /** 信息资源 */
    private transient static final ResourceBundle messageRes =
        ResourceBundle.getBundle("com.ztesoft.oaas.exception.Resources");

    public OAASError(String errorCode) {
        super(errorCode);
    }
    public void initialize(String errorCode) {
        setErrorMessage(messageRes.getString(errorCode));
    }
}
