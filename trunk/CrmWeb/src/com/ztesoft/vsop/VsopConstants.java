package com.ztesoft.vsop;

public class VsopConstants {
	public static final String VSOP_SYSTEMID="200";
	public static final String VSOP_REQUESTMESSAGEVersion="1.0.0";
	public static final String VSOP_SUCCESS_RESPONSECODE="0";
	public static final String VSOP_SYSTEM_ERROR_RESPONSECODE="-99";
	
	
	//��ϵͳ�����ַϵͳ�������붨��
	//UserInfoSynSV	�û���Ϣͬ����ʡ����>���ţ�
	public static final String UserInfoSynSV_URL_PARAMCODE="UserInfoSynSV_URL";
	//ReconfirmSV	VSOP����ȷ����Ϣ���ͷ���
	public static final String ReconfirmSV_URL_PARAMCODE="ReconfirmSV_URL";
	//ReturnSubRTSV	VSOP���Ż��ظ��������
	public static final String ReturnSubRTSV_URL_PARAMCODE="ReturnSubRTSV_URL";
	//SendSubRTSV	VSOP���û����Ͷ������֪ͨ
	public static final String SendSubRTSV_URL_PARAMCODE="SendSubRTSV_URL";
	//FeeCheckSV	VSOP��OCSϵͳ���ͷ��ü�Ȩ
	public static final String FeeCheckSV_URL_PARAMCODE="FeeCheckSV_URL";
	//��Ҫ����ȷ�ϵ�ϵͳ
	public static final String RECONFIRM_SYSTEM = "RECONFIRM_SYSTEM";
	//�Ƿ���Ҫ����ȷ��
	public static final String NEED_RECONFIRM = "NEED_RECONFIRM";	
	//�Ƿ���Ҫ��Ȩ
	public static final String NEED_AUTHENTICATE = "NEED_AUTHENTICATE"; 
	//����ģ�������
	public static final String SPI_PROC_NUM = "SPI_PROC_NUM"; 
	public static final String OPEN_USER_INFOSYNC = "OPEN_USER_INFOSYNC";
	//����ͨ��ʱ����ÿ�λ�ȡINF_MSG���¼����
	public static final String FK_ORDER_JOB_PAGE_NUM = "FK_ORDER_JOB_PAGE_NUM";
	//����Ӫҵ�����ص�ַ
	public static final String DC_MES_URL = "DC_MES_URL";
	//�����ֵ��Ʒ���Ͷ���ȷ�϶���
	public static final String SEND_RECONFIRM_MSG_TYPE = "SEND_RECONFIRM_MSG_TYPE";
	//����ͨ��ʱ�����߳���
	public static final String FK_THREAD_NUM = "FK_THREAD_NUM";
	public static final String FK_FEEDBACK_JOB_PAGE_NUM = "FK_FEEDBACK_JOB_PAGE_NUM";
	//WorkSheetReturnSV	����ͨ�����ص�����
	public static final String WORK_SHEET_RETURN_SERVICE_URL = "WORK_SHEET_RETURN_SERVICE_URL";
	public static final String SYNHB_SERVICE_URL = "SYNHB_SERVICE_URL";
	public static final String UNUSE_CLOB = "UNUSE_CLOB";
	public static final String XML_PARSE_TYPE = "XML_PARSE_TYPE";
	
	//10000�ſͷ�������ϵ�ظ�URL
	public static final String ORDER_BACK_SERVICE_URL_212="ORDER_BACK_SERVICE_URL_212";
	
	//������ϵͬ��ʱ�����Ƿ�ִ��д��INF_ORDER_RELATION
	public static final String CRM_SYN = "CRM_SYN";
	
	//������ϵͬ���ӿڵķ��ʹ���
	public static final String INF_SEND_TIMES = "INF_SEND_TIMES";
	
	//��ѯ������ϵͬ���ӿڵ�����
	public static final String INF_ROWNUM = "INF_ROWNUM";
	//����ȷ�϶�ʱ������
	public static final String RECONFIRM_THREAD_NUM = "RECONFIRM_THREAD_NUM";
	public static final String RECONFIRM_PAGE_NUM = "RECONFIRM_PAGE_NUM";
	//����ȷ�ϳ����·��������ﵽ�ô�����ֹͣ�·�
	public static final String RECONFIRM_TRY_SEND_COUNT = "RECONFIRM_TRY_SEND_COUNT";
	//����ȷ��ʱ�����ƣ���λСʱ
	public static final String RECONFIRM_EXP_TIME = "RECONFIRM_EXP_TIME";
	//����ȷ�϶�ʱ������
	public static final String USERINFO_SYNC_THREAD_NUM = "USERINFO_SYNC_THREAD_NUM";
	public static final String USERINFO_SYNC_TRY_SEND_COUNT = "USERINFO_SYNC_TRY_SEND_COUNT";
	public static final String DC_PROVINCE_CODE = "DC_PROVINCE_CODE";
	//crm  ftp��Ϣ  ��ʽ user:password@ip:port
	public static final String CRM_FTP_INFO = "CRM_FTP_INFO";
	//ftp ����� ismp ��ֵ��ƷĿ¼
	public static final String CRM_FTP_ISMP_PRODUCT_DIR = "CRM_FTP_ISMP_PRODUCT_DIR";
	//ftp ����� ismp ����ƷĿ¼
	public static final String CRM_FTP_ISMP_OFFER_DIR = "CRM_FTP_ISMP_OFFER_DIR";
	//ftp ����� ismp ����ֵ��������ƷĿ¼
	public static final String CRM_FTP_ISMP_POFFER_DIR = "CRM_FTP_ISMP_POFFER_DIR";
	public static final String ISMP_SERVICE_URL = "ISMP_SERVICE_URL";
	public static final String CRM_FTP_ISMP_PRODUCT_BACKUP_DIR = "CRM_FTP_ISMP_PRODUCT_BACKUP_DIR";
	public static final String CRM_FTP_ISMP_POFFER_BACKUP_DIR = "CRM_FTP_ISMP_POFFER_BACKUP_DIR";
	public static final String CRM_FTP_ISMP_OFFER_BACKUP_DIR = "CRM_FTP_ISMP_OFFER_BACKUP_DIR";  
	//�ֻ���product_id
	public static final String DC_MSISDN = "DC_MSISDN";
	//С��ͨ��product_id
	public static final String DC_PHS = "DC_PHS";
	//�̻���product_id
	public static final String DC_PSTN = "DC_PSTN";
	
	//ismp��c��Ĭ��ҵ�������ַ�
	public static final String ISMP_MSISDN_DEF_SERVICE_CODE = "ISMP_MSISDN_DEF_SERVICE_CODE";
	//ismp��p��Ĭ��ҵ�������ַ�
	public static final String ISMP_PSTN_DEF_SERVICE_CODE = "ISMP_PSTN_DEF_SERVICE_CODE";
	public static final String EXCLUDE_PLATFORMS = "EXCLUDE_PLATFORMS";
	//189���䡢�����ֲ�Ʒ����
	public static final String MAIL_IMUSIC_PRODUCTNBR = "MAIL_IMUSIC_PRODUCTNBR";
	//�Ƿ�ص��ӿڱ���־
	public static final String CLOSE_INTERFACE_LOG = "CLOSE_INTERFACE_LOG";
	
	//135��ͷ��ȫ����ֵ��Ʒ
	public static final String PRODUCTNBR_135 = "135";
	
	//ȫʡ��qq�����
	public static final String PRODUCTNBR_115_QQ = "115000000000000000351";	
	
	//��INF_PROD_TO_OCS��ȡ��������״̬ΪU�ļ�¼
	public static final String PROD_SYNC_ROWNUM = "PROD_SYNC_ROWNUM";
	
	//OCS�ṩ��VSOP����ͬ����ֵ��Ʒʱ�ķ���˵�ַ
	public static final String PROD_SYNC_TO_OCS_URL = "PROD_SYNC_TO_OCS_URL";
	//OCS�ṩ��VSOP����ͬ������Ʒʱ�ķ���˵�ַ
	public static final String PRODOFFER_SYNC_TO_OCS_URL = "PRODOFFER_SYNC_TO_OCS_URL";
	
	
	
}
