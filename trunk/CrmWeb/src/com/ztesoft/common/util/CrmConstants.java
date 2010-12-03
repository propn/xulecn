package com.ztesoft.common.util;

/**
 * 
 * <p>Description: ϵͳ����</p>
 * <p>Copyright 2006 ZTEsoft Corp.</p>
 * @Create Date   : 2006-4-1
 * @Version       : 1.0
 */
public class CrmConstants {
	
	public static final String DB_TYPE_INFORMIX = "INFORMIX";
	public static final String DB_TYPE_ORACLE = "ORACLE";	

	//ϵͳĬ�����ڸ�ʽ
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	//ϵͳĬ������ʱ���ʽ
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	//8λ���ڸ�ʽ
	public static final String DATE_FORMAT_8 = "yyyyMMdd";
	//14λ����ʱ���ʽ
	public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

	
	//Ĭ��ʧЧʱ��
	public static final String DEFAULT_EXPIRED_DATE = "2030-1-1 00:00:00";
	
	//ϵͳ���ݿ�����
	public static  String DATABASE_TYPE = "ORACLE";
	
	static {
		CrmParamsConfig.getInstance().initParams("");
		DATABASE_TYPE =CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
	}
	
	
	//�Զ���ȡ��web-inf����·��
	public static String WEB_INF_PATH = "CRM_WEB_INF_PATH";
	
	//�Զ���ȡ��web-inf����·��
	public static String SHOW_SQL = "true";
	
	//�Զ���ȡ��web-inf����·��
	public static String SHOW_METHOD_TIME = "true";	
	
	//��ҳ��¼�����������
	public static int MAX_PAGE_SIZE = 30;
	
	//��ҳ��¼��Ĭ��ֵ
	public static int DEFAULT_PAGE_SIZE = 20;
	
	//��ѯ����¼������
	public static int MAX_QUERY_SIZE = 3000;
	
	// #ϵͳ��Ŀ������TIANJIN,CHONGQING��  
	public static String TJ_PROJECT_CODE = "TIANJIN";
	public static String CQ_PROJECT_CODE = "CONGQING";
	//ʡ�ݱ���
	public static String GX_PROV_CODE = "21"; //����
	public static String JX_PROV_CODE = "15"; //����
	public static String CQ_PROV_CODE = "04"; //����
	
	public static final String SYS_CT10000_PWD = "CT10000_PWD";	//2009-05-04����Ϊ10000��¼���õ�����
	
	public static void setMaxQuerySize(String size){
		try {
			if(null!=size && !"".equals(size))
				MAX_QUERY_SIZE = Integer.parseInt(size);
		} catch (Exception e) {
		}
		 
	}
    public static int getMaxQuerySize(){				
		return MAX_QUERY_SIZE;
	}
	
	//�ϴ��ļ���С(һ������10M����)	
	public static long MAX_UPLOAD_SIZE = 10000;
	
	public static void setMaxUploadSize(String size){
		try {
			if(null!=size && !"".equals(size))
				MAX_UPLOAD_SIZE = Long.parseLong(size);
		} catch (Exception e) {
		}
		 
	}
    public static long getMaxUploadSize(){				
		return MAX_UPLOAD_SIZE;
	}
	
    //����Ҫ��½��ֱ�ӿ��Է��ʵ�ҳ��
    public static String[] NOT_FILTER_PAFGES = new String[0];
    
	public static void setNotFilterPages(String pages){
		try {
			if(null != pages)
				NOT_FILTER_PAFGES = pages.split("(,)");
		} catch (Exception e) {
		}
	}
	//����Ҫ��½��ֱ�ӿ��Է��ʵ�ҳ��
	public static String[] getNotFilterPages(){		
		
		return NOT_FILTER_PAFGES;
		
	}
	                     
	
	/**
	 * ��ȡ�ӿڷ�������IP��ַ
	 * @return
	 */
	public static String getInterfaceIp(){
		return CrmParamsConfig.getInstance().getParamValue("IF_RMI_ADD");
		//return "136.5.8.119:8888";
	}
	/**
	 * ����״̬
	 * @return
	 */
	public static boolean getDebugState(){
	  return true;	
	}
	
	/**
	 * ��ȡ�ӿڷ�������IP��ַ
	 * @return
	 */
	public static String getIIOPInterfaceIp(){
		return CrmParamsConfig.getInstance().getParamValue("IIOP_RMI_ADD");
		//return "136.5.8.119:8888";
	}
	
	/**
	 * ��ȡIOM��������IP��ַ
	 * 
	 */
	public static String getIomIp(){
		return CrmParamsConfig.getInstance().getParamValue("IOM_TACHE_INFO_IP");
	}

}
