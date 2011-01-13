package com.ztesoft.common.exception;

import java.util.ResourceBundle;

/**
 * 
 * <p>Description: ϵͳ�����Ĵ�����Ϣ��</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class CommonError extends CrmError {

	public static final String UNDEFINED_ERROR = "common-error-0000"; //û�ж���Ĵ���
	public static final String COMMON_ERROR = "common-error-0001"; //ϵͳ����ʱ����
	public static final String DATABASE_ERROR = "common-error-0002"; //���ݿ⴦�����
	public static final String DATA_NOFOUND_ERROR = "common-error-0003"; //���ݲ�����
	public static final String PAGE_TOO_LARGE_ERROR = "common-error-0004"; //��ҳ����̫��
	public static final String INVALID_REQ_ERROR = "common-error-0005"; //�Ƿ����ʷ��������ȵ�½��
	public static final String LOGIN_TIMEOUT_ERROR = "common-error-0006"; //�����ȵ�½����ʹ��ϵͳ��
	
	/** ��Ϣ��Դ */
	private transient static final ResourceBundle messageRes = ResourceBundle
			.getBundle("com.ztesoft.common.exception.CommonResources");

	public CommonError(String errorCode) {
		super(errorCode);
	}

	public void initialize(String errorCode) {
		setErrorMessage(messageRes.getString(errorCode));
		//setErrorResolve(messageRes.getString("resolve-" + errorCode));
	}
}
