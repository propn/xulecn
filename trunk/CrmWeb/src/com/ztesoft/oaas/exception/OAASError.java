package com.ztesoft.oaas.exception;

import java.util.ResourceBundle;

import com.ztesoft.common.exception.CrmError;

public class OAASError extends CrmError{
	public static final String EXISTS_SUB_MENU_ERROR = "menu_error_0001"; //�˵��ڵ����滹���¼��ڵ�,����ɾ��
	public static final String EXISTS_SUB_REGION_ERROR="region_error_0001";//����ڵ����滹���¼��ڵ�,����ɾ��
	public static final String EXISTS_SUB_ORGANIZATION_ERROR="organization_error_0001";//��֯�ڵ����滹���¼��ڵ�,����ɾ��
	public static final String ORGANIZATION_WITH_STAFF_ERROR="organization_error_0002";//��֯���滹��Ա��,����֯����ɾ��.
	public static final String EXISTS_SUB_PRIVILEGE_ERROR="privilege_error_0001";//Ȩ�����滹���¼�Ȩ��,����ɾ��
	public static final String ROLE_WITH_POSITION="roles_error_0001";//��ɫ�Ѿ��������ĳ����λ,����ɾ��
	public static final String ORG_POST_WITH_STAFF="org_post_error_0001";//��֯��λ�Ѿ��������Ա��,����ɾ��
	public static final String STAFF_SAME_STAFF_CODE="staff_error_0001";//��ͬ�Ĺ����쳣
    /** ��Ϣ��Դ */
    private transient static final ResourceBundle messageRes =
        ResourceBundle.getBundle("com.ztesoft.oaas.exception.Resources");

    public OAASError(String errorCode) {
        super(errorCode);
    }
    public void initialize(String errorCode) {
        setErrorMessage(messageRes.getString(errorCode));
    }
}
