package com.ztesoft.component.common.staticdata.exception;

import java.util.ResourceBundle;

import com.ztesoft.common.exception.CrmError;

/**
 * 
 * <p>Description: ��̬���ݵĴ�����Ϣ��</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-26
 * @Version       : 1.0
 */
public class StaticDataError extends CrmError {

	public static final String NOFOUND_ERROR = "staticdata-error-0000"; //�Ҳ�������
	public static final String DATA_NOFOUND_ERROR = "staticdata-error-0001"; //�Ҳ�����̬����
	public static final String DATA_DEFINED_NOFOUND_ERROR = "staticdata-error-0002"; //�Ҳ�����̬���ݶ���
	public static final String DATA_BASE_ERROR = "staticdata-error-0003"; //���ݿ����
	
	/** ��Ϣ��Դ */
	private transient static final ResourceBundle messageRes = ResourceBundle
			.getBundle("com.ztesoft.component.common.staticdata.exception.StaticDataResources");

	public StaticDataError(String errorCode) {
		super(errorCode);
	}

	public void initialize(String errorCode) {
		setErrorMessage(messageRes.getString(errorCode));
		//setErrorResolve(messageRes.getString("resolve-" + errorCode));
	}
}
