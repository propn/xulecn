package com.powerise.ibss.util;

import java.util.ArrayList;
/**
Ӧ�÷�������ȫ�ֱ�������
*/
public class GlobalNames {


    /**�������ŵ�Attribute����*/
    public static String ERROR_ATTR ="error";

    /**HTML�ļ��пؼ����ֵĿ�ʼ��*/

       public static final String BEGING_TAG ="para_";


   /**���ݿ����ӷ�ʽ 1��DataSource 2��JDBC THIN*/
   public static int DB_CONN_MODE =1;//(APP)

   /**Ӧ�÷�������ʼ��Factory*/
   public static String INITIAL_FACTORY = "weblogic.jndi.WLInitialContextFactory";//(APP/WEB)
   public static String URL_PROVIDER = "t3://172.18.2.55:7001";//(APP/WEB)
   //ȱʡ�����ݿ����Ӳ�����ʹ�����ӳ�
   public static String DBPOOL_URL="";
   public static String DBPOOL_DRIVER="";

   /**����Դ����*/
   public static String DATA_SOURCE = "ibss";//(APP)
   /**Ӧ�÷�����������ҵ���������Դ*/
   public static String OTHER_DATA_SOURCE ="other_ibss";

   public static String URL ="";//(APP)
   public static String DRIVER ="";//(APP)
   public static String DB_USER_NAME ="";//(APP)
   public static String DB_PASSWORD ="";//(APP)

   /**ϵͳ����·��*/
   public static String WEB_PATH = "";//(WEB)

   /**ϵͳ����*/
   public static String SYSTEM_NAME = "���������ۺ�ҵ��֧��ƽ̨";//(APP/WEB)
   /**����������*/
   public static String SOFTWARE_NAME = "���ǵ�����ҵ��";//(APP/WEB)

   /**��Ŀ����ģʽ 0:����ģʽ 1����Ʒģʽ*/
   public static int PROJECT_MODE = 1;//(APP/WEB)

   /**�Ƿ�����Ľ���ISO ��GBK��ת��*/
   public static boolean ISO_TO_GBK = false;//(APP/WEB)

   /**�Ƿ�����Ľ���GBK ��ISO��ת��*/
   public static boolean GBK_TO_ISO = false;//(APP/WEB)

   /**������ն˻���������ļ�, 0:�ն� 1���ļ�(�ļ�λ��Ϊibss.xml���õ��ļ�λ��)*/
   public static int LOG_TYPE = 0;//(WEB)

   /**�Ƿ��ӡ���ն�*/
   public static boolean PRINT_TERMINAL =false;//(APP/WEB)

   /**�Ƿ�Ѵ�����Ϣ��������ݿ��tfm_err_log*/
   public static boolean ERR_TO_DB =true;//(APP)

   /**EAI XML���뷽ʽ*/
   public static String EAI_ENCODING ="gb2312";//(APP)

   /**�Ƿ�������ļ���������Load*/
   public static boolean CONFIG_LOADED =false;//(APP/WEB)

   /**ϵͳ�Ƿ�ʹ��LDAP*/
   public static boolean USE_LDAP =false;//APP

   /**ϵͳ�Ƿ�ͨ��EAIͨѶ�� true:�ǣ�false: �񣬲�ͨ��EAIͨѶ��ͨ��BO XML�ļ�����ģ��*/
   public static boolean USE_EAI =false; //APP

   /**ϵͳ�Ƿ��ͨ��EAIͨѶ��BO XML��¼��$IBSSHOME/boĿ¼*/
   public static boolean LOG_EAI_MSG =false; //APP
   /**EAI �����б�*/
   public static ArrayList EAI_QUERYS =null;
   //��ǰʹ������  1 ���� 2 Ӣ��
   public static String CURR_LANG ="1";

}
