package com.ztesoft.vsop;

public class VsopConstants {
	public static final String VSOP_SYSTEMID="200";
	public static final String VSOP_REQUESTMESSAGEVersion="1.0.0";
	public static final String VSOP_SUCCESS_RESPONSECODE="0";
	public static final String VSOP_SYSTEM_ERROR_RESPONSECODE="-99";
	
	
	//各系统服务地址系统参数编码定义
	//UserInfoSynSV	用户信息同步（省级－>集团）
	public static final String UserInfoSynSV_URL_PARAMCODE="UserInfoSynSV_URL";
	//ReconfirmSV	VSOP二次确认信息发送服务
	public static final String ReconfirmSV_URL_PARAMCODE="ReconfirmSV_URL";
	//ReturnSubRTSV	VSOP向门户回复订购结果
	public static final String ReturnSubRTSV_URL_PARAMCODE="ReturnSubRTSV_URL";
	//SendSubRTSV	VSOP向用户发送订购结果通知
	public static final String SendSubRTSV_URL_PARAMCODE="SendSubRTSV_URL";
	//FeeCheckSV	VSOP向OCS系统发送费用鉴权
	public static final String FeeCheckSV_URL_PARAMCODE="FeeCheckSV_URL";
	//需要二次确认的系统
	public static final String RECONFIRM_SYSTEM = "RECONFIRM_SYSTEM";
	//是否需要二次确认
	public static final String NEED_RECONFIRM = "NEED_RECONFIRM";	
	//是否需要鉴权
	public static final String NEED_AUTHENTICATE = "NEED_AUTHENTICATE"; 
	//激活模块进程数
	public static final String SPI_PROC_NUM = "SPI_PROC_NUM"; 
	public static final String OPEN_USER_INFOSYNC = "OPEN_USER_INFOSYNC";
	//服务开通定时任务每次获取INF_MSG表记录条数
	public static final String FK_ORDER_JOB_PAGE_NUM = "FK_ORDER_JOB_PAGE_NUM";
	//短信营业厅网关地址
	public static final String DC_MES_URL = "DC_MES_URL";
	//逐个增值产品发送二次确认短信
	public static final String SEND_RECONFIRM_MSG_TYPE = "SEND_RECONFIRM_MSG_TYPE";
	//服务开通定时任务线程数
	public static final String FK_THREAD_NUM = "FK_THREAD_NUM";
	public static final String FK_FEEDBACK_JOB_PAGE_NUM = "FK_FEEDBACK_JOB_PAGE_NUM";
	//WorkSheetReturnSV	服务开通工单回单服务
	public static final String WORK_SHEET_RETURN_SERVICE_URL = "WORK_SHEET_RETURN_SERVICE_URL";
	public static final String SYNHB_SERVICE_URL = "SYNHB_SERVICE_URL";
	public static final String UNUSE_CLOB = "UNUSE_CLOB";
	public static final String XML_PARSE_TYPE = "XML_PARSE_TYPE";
	
	//10000号客服订购关系回复URL
	public static final String ORDER_BACK_SERVICE_URL_212="ORDER_BACK_SERVICE_URL_212";
	
	//订购关系同步时控制是否执行写表INF_ORDER_RELATION
	public static final String CRM_SYN = "CRM_SYN";
	
	//订购关系同步接口的发送次数
	public static final String INF_SEND_TIMES = "INF_SEND_TIMES";
	
	//查询订购关系同步接口的条数
	public static final String INF_ROWNUM = "INF_ROWNUM";
	//二次确认定时任务数
	public static final String RECONFIRM_THREAD_NUM = "RECONFIRM_THREAD_NUM";
	public static final String RECONFIRM_PAGE_NUM = "RECONFIRM_PAGE_NUM";
	//二次确认尝试下发次数，达到该次数则停止下发
	public static final String RECONFIRM_TRY_SEND_COUNT = "RECONFIRM_TRY_SEND_COUNT";
	//二次确认时间限制，单位小时
	public static final String RECONFIRM_EXP_TIME = "RECONFIRM_EXP_TIME";
	//二次确认定时任务数
	public static final String USERINFO_SYNC_THREAD_NUM = "USERINFO_SYNC_THREAD_NUM";
	public static final String USERINFO_SYNC_TRY_SEND_COUNT = "USERINFO_SYNC_TRY_SEND_COUNT";
	public static final String DC_PROVINCE_CODE = "DC_PROVINCE_CODE";
	//crm  ftp信息  格式 user:password@ip:port
	public static final String CRM_FTP_INFO = "CRM_FTP_INFO";
	//ftp 里面的 ismp 增值产品目录
	public static final String CRM_FTP_ISMP_PRODUCT_DIR = "CRM_FTP_ISMP_PRODUCT_DIR";
	//ftp 里面的 ismp 销售品目录
	public static final String CRM_FTP_ISMP_OFFER_DIR = "CRM_FTP_ISMP_OFFER_DIR";
	//ftp 里面的 ismp 纯增值捆绑销售品目录
	public static final String CRM_FTP_ISMP_POFFER_DIR = "CRM_FTP_ISMP_POFFER_DIR";
	public static final String ISMP_SERVICE_URL = "ISMP_SERVICE_URL";
	public static final String CRM_FTP_ISMP_PRODUCT_BACKUP_DIR = "CRM_FTP_ISMP_PRODUCT_BACKUP_DIR";
	public static final String CRM_FTP_ISMP_POFFER_BACKUP_DIR = "CRM_FTP_ISMP_POFFER_BACKUP_DIR";
	public static final String CRM_FTP_ISMP_OFFER_BACKUP_DIR = "CRM_FTP_ISMP_OFFER_BACKUP_DIR";  
	//手机的product_id
	public static final String DC_MSISDN = "DC_MSISDN";
	//小灵通的product_id
	public static final String DC_PHS = "DC_PHS";
	//固话的product_id
	public static final String DC_PSTN = "DC_PSTN";
	
	//ismp的c网默认业务能力字符
	public static final String ISMP_MSISDN_DEF_SERVICE_CODE = "ISMP_MSISDN_DEF_SERVICE_CODE";
	//ismp的p网默认业务能力字符
	public static final String ISMP_PSTN_DEF_SERVICE_CODE = "ISMP_PSTN_DEF_SERVICE_CODE";
	public static final String EXCLUDE_PLATFORMS = "EXCLUDE_PLATFORMS";
	//189邮箱、爱音乐产品编码
	public static final String MAIL_IMUSIC_PRODUCTNBR = "MAIL_IMUSIC_PRODUCTNBR";
	//是否关掉接口表日志
	public static final String CLOSE_INTERFACE_LOG = "CLOSE_INTERFACE_LOG";
	
	//135开头的全国增值产品
	public static final String PRODUCTNBR_135 = "135";
	
	//全省的qq外编码
	public static final String PRODUCTNBR_115_QQ = "115000000000000000351";	
	
	//从INF_PROD_TO_OCS表取出多少条状态为U的记录
	public static final String PROD_SYNC_ROWNUM = "PROD_SYNC_ROWNUM";
	
	//OCS提供的VSOP向其同步增值产品时的服务端地址
	public static final String PROD_SYNC_TO_OCS_URL = "PROD_SYNC_TO_OCS_URL";
	//OCS提供的VSOP向其同步销售品时的服务端地址
	public static final String PRODOFFER_SYNC_TO_OCS_URL = "PRODOFFER_SYNC_TO_OCS_URL";
	
	
	
}
