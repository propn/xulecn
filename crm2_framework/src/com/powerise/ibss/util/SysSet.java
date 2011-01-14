package com.powerise.ibss.util;

/**
 * ��������: 229910xx
 * ��ܺ͸���ҵ��ģ��ʹ�õĹ�������,�ܹ�ʹ�õĶ��Ǿ�̬(static)����
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.LogHelper;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;

public class SysSet {


  static final byte m_encryptKey[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2,
      3, 4, 5, 6, 7, 8, 9, 8, 8, 8, 8};

  static final String m_PWDKey[] = {
      "PASSWORD", "PASSWD"};

  static HashMap action_cache; // �����Ѿ�ʵ��������ʵ��

  public static int ACTION_QUERY = 1, ACTION_SERVICE = 2;

//  static final String CONFIG_DIR = "config"; // �����ļ���Ŀ¼��$IBSS_HOME/config

//  static final String CONFIG_FILE = "ibss.xml"; // �����ļ�����$IBSS_HOME/config/ibss.xml

//  public static HashMap m_ActData = null;

//  private static HashMap m_ActionConns = null; // ���ÿ���߳�ʹ�õ�Connection������Connection���ܴ��ڶ������˶�Ӧ�Ĵ洢������MultiDBUtil��

  private static HashMap m_DBLink = null; // ���ݿ����������,��Ϊ���ڶ�������ݿ����ã������ṹ�Ƕ���hashmap

  private static String m_DefaultDBName = JNDINames.DEFAULT_DATASOURCE; // ȱʡ�����ݿ��������ƣ�����ͬʱ���Ӷ�����ݿ⣬���Դ�ֵ�ɱ�

  private static boolean m_FrameDataInMem = false;

  private static Logger m_Logger = Logger.getLogger(SysSet.class);

  private static boolean m_MultiDB = true ;

  // or HashMap)
//  private static HashMap m_ServData = null;

  private static HashMap m_System = null; // �������ã�������DBLink���������ã���ṹ��HashMap->HashMap->(String

  private static int m_XATranTimeOut = 60;
  
  public static boolean surportAjax=true;//�Ƿ�֧��bho��ajax
  
  /**
   * �˷�����������ʹ�á�����SQLPLUS,��ÿ�����񱻵�ִ��֮�󣬿���ͨ���˷�����δ�ύ֮ǰ�鿴���ݣ����ڿ��Իع�
   * ����ͬһ���������ݣ������ظ��������ԡ� ֧��SQL��䣬��ѯ�����룬�������ύ���ع�����֧��desc�ȷǱ�׼SQL���
   *
   * @param conn
   *            ���ݿ�����
   *            ���齫����ʹ�õ�Connection���룬�����ܹ�ȷ����ͬһ�����Ӻ�Session.���������ݾ���֮ǰ��������,��������ԵĻ�,�����ڲ��Խ���֮ǰ���лع�
   */
  public static void check(Connection conn) {
    Statement st = null;
    ResultSet rs = null;
    StringBuffer strBuffer = new StringBuffer();
    String strInput = null;
    byte[] byteData = null;
    int iRead = 0, iLen = 0;
    boolean bStart = true, bExec = false;
    System.out.println("�����Ҫ���ִ�н��������SQL�������˳�������exit����quit");
    while (true) {
      if (bStart) {
        System.out.print("SQL > ");
      }
      try {
        byteData = new byte[10];
        iRead = System.in.read(byteData);
        if (iRead > 1) {
          if (byteData[iRead - 2] == 13) {
            byteData[iRead - 2] = 0;
            byteData[iRead - 1] = 0;
            iRead = iRead - 2;
          }
          else if (byteData[iRead - 1] == 13) {
            byteData[iRead - 1] = 0;
            iRead--;
          }
        }
        else if (iRead == 1) {
          byteData[0] = 0;
          iRead--;
        }
        bStart = false;
        if (iRead > 0)
          strInput = new String(byteData, 0, iRead);
        else
          strInput = "";
        byteData = null;
        strInput.trim();
        strBuffer.append(strInput);
        if (System.in.available() == 0) { // ��ȡ���
          strInput = strBuffer.toString();
          strInput = strInput.trim();
          bStart = true;
          if (strInput.equals("") == false) {
            if (strInput.length() < 10)
              if (strInput.toUpperCase().equals("EXIT")
                  || strInput.toUpperCase().equals("QUIT")) {
                break;
              }
            iLen = strInput.length();
            if (strInput.substring(iLen - 1, iLen).equals(";")
                || strInput.substring(iLen - 1, iLen).equals(
                "/")) {
              // ִ��SQL���
              strInput = strInput.substring(0,
                                            strInput.length() - 1);
              strBuffer.setLength(0);
              bExec = false;
              try {
                st = conn.createStatement();
                bExec = st.execute(strInput);
                if (bExec) { // ���ؽ����
                  int iLoop, j;
                  Object obj;
                  rs = st.getResultSet();
                  rs.setFetchSize(30);
                  ResultSetMetaData rsdm = rs.getMetaData();
                  int iFields = rsdm.getColumnCount();
                  for (iLoop = 1; iLoop <= iFields; iLoop++) {
                    System.out.print(rsdm
                                     .getColumnName(iLoop));
                    // �ֶ���
                    System.out.print("          ");
                  }
                  System.out.print("\n");
                  j = 0;
                  while (rs.next()) {
                    for (iLoop = 1; iLoop <= iFields; iLoop++) {
                      obj = rs.getObject(iLoop);
                      if (rs.wasNull())
                        System.out.print("");
                      else
                        System.out.print(obj);
                      System.out.print("          ");
                    }
                    j++;
                    System.out.print("\n");
                  }
                  System.out.println("���������: " + j);
                }
                else {
                  System.out.println("Ӱ������: "
                                     + st.getUpdateCount());
                }
                if (st != null) {
                  st.close();
                  st = null;
                }
              }
              catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
              }
            }
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    byteData = null;
    strBuffer = null;
    strInput = null;
  }

 
 
  /**
   * ���������ļ���DB Name����ȡ���ݵ��������
   *
   * @param strDBName
   * @return ����-ֵ��Ե����ݿ����ò���,����Url,Driver�ȵ�
   * @throws FrameException
   */
  public static HashMap getDBInfoByName(String strDBName) throws FrameException {
    HashMap hmDB = null;
    if (m_DBLink != null) {
      if (strDBName == null)
        strDBName = m_DefaultDBName;
      Object obj = m_DBLink.get(strDBName);
      if (obj != null)
        hmDB = (HashMap) obj;
    }
    if (hmDB == null)
      throw new FrameException( -129922, "No database config for " + strDBName);
    return hmDB;
  }
  /**
   * ��ȡϵͳ��HOMEĿ¼�����ȴ�ϵͳ����IBSS_HOME�л�ȡ�����û�У���ֱ�ӻ�ȡ��ǰ�û��� HOME��������
   *
   * @return java.lang.String
   */
  public static String getHomeDirectory() {
    String s = System.getProperty("IBSS_HOME");
    if (s != null)
      return s;
    else
      return System.getProperty("user.home");
  }

  /**
   * �ж��Ƿ�ϵͳ�Ѿ���ʼ��
   *
   * @return
   */
  public static boolean getInitFlag() {
    return GlobalNames.CONFIG_LOADED;
  }


  /**
   * ��ȡ�����ļ���System���е�ָ�����Ե���������
   *
   * @param strSetName
   * @return
   * @throws FrameException
   */
  public static String getSystemSet(String strSetName) throws FrameException {
    return getSystemSet("System", strSetName, null);
  }

  /**
   * ��ȡ�����ļ���System���е�ָ�����Ե���������,����ָ�������Ҳ���ʱ��ȱʡֵ
   *
   * @param strSetName
   *            ��������
   *
   * @return
   * @throws FrameException
   */
  public static String getSystemSet(String strSetName, String strDefaultValue) throws
      FrameException {
    return getSystemSet("System", strSetName, strDefaultValue);
  }

  /**
   * ��ȡ�����ļ���ָ�����е�ָ�����Ե���������,����ָ�������Ҳ���ʱ��ȱʡֵ,��APiֻ����������System�ڵĽṹ
   *
   * @param strTypeName
   *            ������������
   * @param strSetName
   *            ��������
   * @return
   * @throws FrameException
   */
  public static String getSystemSet(String strTypeName, String strSetName,
                                    String strDefaultValue) throws
      FrameException {
    String strValue = CrmParamsConfig.getInstance().getParamValue(strSetName);
    return strValue != null ? strValue : strDefaultValue ;
//    Object obj = null;
//    HashMap hmSet = null;
//    if (m_System != null) {
//      obj = m_System.get(strTypeName);
//      if (obj == null) {
//        if (strDefaultValue != null)
//          strValue = strDefaultValue;
//        else
//          throw new FrameException( -22991002, strTypeName
//                                   + "have not been found in config file");
//      }
//      else {
//        hmSet = (HashMap) obj;
//        obj = hmSet.get(strSetName);
//        if (obj != null)
//          strValue = (String) obj;
//        else if (strDefaultValue != null)
//          strValue = strDefaultValue;
//      }
//    }
//    else {
//      throw new FrameException( -22991002,
//                               "variable m_System is not initialized");
//    }
//    return strValue;
  }

  /**
   * ��ȡ�����ļ���ָ�����е�ָ�����Ե��������ݼ�,û���ҵ�����null,��API������MQ�ڵ����ýṹ,������ڶ�ֵ(����������ͬ)�Ļ�,�򷵻ص�һ��
   *
   * @param strTypeName
   *            ���ͽڵ�����
   * @param strSetName
   *            ��������
   * @return ����-ֵ�Ե���������
   * @throws FrameException
   */
  public static HashMap getSystemSets(String strTypeName, String strSetName) throws
      FrameException {
    return getSystemSets(strTypeName, strSetName, null, null);
  }

  /**
   * ��ȡ�����ļ���ָ�����е�ָ�����Ե��������ݼ�,û���ҵ�����null,��API������MQ�ڵ����ýṹ,������Դ��ڶ������������ͬ������,��������Ҫ��λ�������ֵ,����Ҫָ����ѯ�����������ƺ�������ֵ,����MQ������Id������
   *
   * @param strTypeName
   * @param strSetName
   * @param strSearchName
   * @param strSearchValue
   * @return
   * @throws FrameException
   */
  public static HashMap getSystemSets(String strTypeName, String strSetName,
                                      String strSearchName,
                                      String strSearchValue) throws
      FrameException {
    HashMap hmValue = null, hmObj = null;
    HashMap hmSet = null;
    Object obj = null;
    int iSize;
    ArrayList arAttr = null;
    // ������û�ж�������ͽ����жϣ���Ϊ���ǻ��ڽ�����Щ�������������ǵ�
    if (m_System != null) {
      hmSet = (HashMap) m_System.get(strTypeName);
      if (hmSet != null) {
        arAttr = (ArrayList) hmSet.get(strSetName);
        iSize = arAttr.size();
        if (iSize > 0) {
          if (strSearchName != null && strSearchValue != null) { // �����Ϳ��Բ�����
            for (int i = 0; i < iSize; i++) {
              hmObj = (HashMap) arAttr.get(i);
              obj = hmObj.get(strSearchName);
              if (obj != null)
                if ( ( (String) obj).equals(strSearchValue)) {
                  hmValue = hmObj;
                  break;
                }
            }
          }
          else {
            hmValue = (HashMap) arAttr.get(0);
          }
        }
        else {
          throw new FrameException( -22991004, strTypeName + "."
                                   + strSetName +
                                   "have not been found in config file");
        }
      }
    }
    else {
      throw new FrameException( -22991003,
                               strTypeName + "have not been found in config file");
    }
    hmObj = null;
    return hmValue;
  }

  /**
   * ��XA������,��ȡJTA��UserTransaction
   *
   * @return
   * @throws FrameException
   */
  public static UserTransaction getUserTransaction() throws FrameException {
    UserTransaction usTran = null;
    Context usContext = null;
    try {
      usContext = new InitialContext();
      usTran = (UserTransaction) usContext
          .lookup("java:comp/UserTransaction");
      // Set the transaction timeout to 30 seconds
      usTran.setTransactionTimeout(m_XATranTimeOut);
    }
    catch (Exception e) {
      throw new FrameException( -22990013, "����UserTransaction�쳣",
                               LogHelper.getStackMsg(e));
    }
    return usTran;
  }

  /**
   * ��ʼ�������ݿ�ģʽ�µ����ӳ�,ͬʱ��ȡ�����е�XA����Timeoutֵ,�����ļ���System�ڿ���ͨ������XATransactionTime������ʱ��
   *
   * @throws FrameException
   */
  public static void initConnContainer() throws FrameException {
//    if (m_ActionConns == null) {
//        m_ActionConns = new HashMap();
        // ��ȡXA�����ʱ��
        m_XATranTimeOut = Integer.parseInt(SysSet.getSystemSet(
            "XATransactionTime", "60"));
//    }
  }

  /**
   * ��ʼ��ϵͳ����
   *
   * @throws FrameException
   */
  public static void initSystem() throws FrameException {
    initSystem(3); // ȱʡ����ͳһ��������
  }

  /**
   *
   * @param iFlag
   *            1- Web Server init 2 - App Server init 3 Web Server and App
   *            Server
   * @throws FrameException
   */
  public static synchronized void initSystem(int iFlag) throws FrameException {
    // ����log4j ������
    String strTemp = null;
   
    if (!GlobalNames.CONFIG_LOADED) {
    	try {
//    		 ���ò�������
//			CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
//			SysSet.initSystem(3);//web + app 
			LogHelper.initLog4J() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        if(CrmParamsConfig.getInstance().getParamValue("CurrentLang")!=null)
          GlobalNames.CURR_LANG=CrmParamsConfig.getInstance().getParamValue("CurrentLang");
        GlobalNames.SYSTEM_NAME = CrmParamsConfig.getInstance().getParamValue("SystemName") == null ? Utility
            .isoToGBK("�Ͼ����������Ƽ��ɷ����޹�˾", true)
            : CrmParamsConfig.getInstance().getParamValue("SystemName").toString();
        GlobalNames.SOFTWARE_NAME = CrmParamsConfig.getInstance().getParamValue("SystemName") == null ? "��������"
            : CrmParamsConfig.getInstance().getParamValue("DevCompany").toString();
        GlobalNames.PROJECT_MODE = CrmParamsConfig.getInstance().getParamValue("ProjectMode") == null ? 0
            : Integer.parseInt(CrmParamsConfig.getInstance().getParamValue("ProjectMode")
                               .toString());
        GlobalNames.ISO_TO_GBK = CrmParamsConfig.getInstance().getParamValue("IsoToGBK") == null ? false
            : Boolean.getBoolean(CrmParamsConfig.getInstance().getParamValue("IsoToGBK")
                                 .toString());
        GlobalNames.GBK_TO_ISO = CrmParamsConfig.getInstance().getParamValue("GbkToISO") == null ? false
            : Boolean.getBoolean(CrmParamsConfig.getInstance().getParamValue("GbkToISO")
                                 .toString());
        GlobalNames.PRINT_TERMINAL = CrmParamsConfig.getInstance().getParamValue("PrintTerminal") == null ? false
            : (new Boolean(CrmParamsConfig.getInstance().getParamValue("PrintTerminal").toString()
                           .trim())).booleanValue();
        GlobalNames.ERR_TO_DB = CrmParamsConfig.getInstance().getParamValue("ErrToDB") == null ? false
            : (new Boolean(CrmParamsConfig.getInstance().getParamValue("ErrToDB").toString()
                           .trim())).booleanValue();
        GlobalNames.EAI_ENCODING = CrmParamsConfig.getInstance().getParamValue("EaiEncoding") == null ?
            "gb2312"
            : CrmParamsConfig.getInstance().getParamValue("EaiEncoding").toString();
        GlobalNames.USE_LDAP = CrmParamsConfig.getInstance().getParamValue("UseLDAP") == null ? false
            : (new Boolean(CrmParamsConfig.getInstance().getParamValue("UseLDAP").toString()
                           .trim())).booleanValue();
        GlobalNames.USE_EAI = CrmParamsConfig.getInstance().getParamValue("UseEAI") == null ? false
            : (new Boolean(CrmParamsConfig.getInstance().getParamValue("UseEAI").toString().trim()))
            .booleanValue();
        GlobalNames.LOG_EAI_MSG = CrmParamsConfig.getInstance().getParamValue("LogEAIMsg") == null ? false
            : (new Boolean(CrmParamsConfig.getInstance().getParamValue("LogEAIMsg").toString()
                           .trim())).booleanValue();
//        HashMap queryDefault = SysSet.getTagProps("MQManager", "id",
//                                                  "DEFAULT", 1);
//        HashMap queryOM = SysSet
//            .getTagProps("MQManager", "id", "OM", 1);
//        ArrayList querys = new ArrayList();
//        if (queryDefault != null && queryDefault.size() > 0)
//          querys.add(queryDefault);
//        if (queryOM != null && queryOM.size() > 0)
//          querys.add(queryDefault);
//        if (querys.size() > 0)
//          GlobalNames.EAI_QUERYS = querys;
//      }
      // �������������
      CacheData.clear();
      
      strTemp = System.getProperty("DEFAULT_DB"); // �����Ҫ���ã�����java���������Ӳ���
      // -DDEFAULT_DB=xxxx
      if (strTemp != null)
        m_DefaultDBName = strTemp;
      else
        m_DefaultDBName = JNDINames.DEFAULT_DATASOURCE; // ȱʡ�����ݿ���������
        // ��ȡ����Ƿ�װ���ڴ������
      strTemp = getSystemSet("FrameDataInMem");
      if (strTemp != null)
        if (strTemp.equalsIgnoreCase("true"))
          m_FrameDataInMem = true;
        else
          m_FrameDataInMem = false;
      else
        m_FrameDataInMem = false;
      m_Logger.debug("default database: " + m_DefaultDBName);
      initConnContainer();
      m_Logger.info("end initialize application..");
      GlobalNames.CONFIG_LOADED = true;
      // ��Ϊ�����漰�����ݿ�Ĵ��������ڻ�ȡ����ʱ���ж��Ƿ��ʼ����ϵͳ�������config_loaded=true�����Ļ����ͻ������ѭ��
//      String directConn = CrmParamsConfig.getInstance().getParamValue("DIRECT_CONNECTION") ;
      if (m_FrameDataInMem ) {
        m_Logger.info("start load frame data...");
//        getFrameSetData();
        //��ʼ������
        FrameServiceController.getInstance() ;
        m_Logger.info("load complete");
      }
      System.out.println("!GlobalNames.CONFIG_LOADED="+!GlobalNames.CONFIG_LOADED +
		"\n m_FrameDataInMem="+m_FrameDataInMem) ;
      // �������
//      if (getSystemSet("Monitor", "true").equals("true"))
//        Monitor.start();
    }
  }

  /**
   * ��ȡ��������Ƿ����뵽�ڴ�
   *
   * @return
   */
  public static boolean isFrameDataInMem() {
    return m_FrameDataInMem;
  }

  /**
   * �ж��Ƿ�����ݿ�ģʽ
   *
   * @return
   */
  public static boolean isMultiDB() {
    return m_MultiDB;
  }

  /**
   * ͨ��GlobalNames.ISO_TO_GBK������ַ�������ISO8859-1 ��GBK��ת��
   *
   * @param p_in -
   *            Ҫת�����ַ���
   * @return String
   */
  public static String isoToGBK(String p_in) {
    String os = System.getProperty("os.name");
    if (os.lastIndexOf("Windows") < 0) { // for others platform
      if (! (p_in == null || p_in.equals(""))) {
        try {
          p_in = new String(p_in.getBytes("ISO-8859-1"), "GBK");
        }
        catch (Exception e) {
          System.err.println(e.getMessage());
        }
      }
    }
    return p_in;
  }

  // ��Ҫ�ж��Ƿ���õ�XA Driver��XA Driver �����������XA�Ĵ����ǲ�һ����
  public static boolean isXAType(String strDBName) throws FrameException {
	if(strDBName == null || "".equals(strDBName.trim()))
		return false ;
    String xaType = CrmParamsConfig.getInstance().getParamValue(strDBName+"_XAType")  ;
    return xaType != null && "true".equalsIgnoreCase(xaType) ;
    
  }


  

  public static void setDBInfo(String strDBName, HashMap hmDB) throws
      FrameException {
    if (m_DBLink != null && hmDB != null) {
      m_DBLink.put(strDBName, hmDB);
    }
  }

  public static void setInitFlag(boolean bFlag) {
    GlobalNames.CONFIG_LOADED = bFlag;
  }

  /**
   * xa�����µ���������
   *
   * @param usTran
   * @throws FrameException
   */
  public static void tpBegin(UserTransaction usTran) throws FrameException {
    // // Use JNDI to locate the UserTransaction object
    try {
      // ...
      // Begin a transaction
      usTran.begin();
    }
    catch (Exception e) {
      throw new FrameException( -22990010, "xa��ʼ������:"
                               + e.getClass().getName(), LogHelper.getStackMsg(e));
    }
  }

  /**
   * XA�����µ��ύ����
   *
   * @param usTran
   * @throws FrameException
   */
  public static void tpCommit(UserTransaction usTran) throws FrameException {
    if (usTran != null) {
      try {
        usTran.commit();
        usTran = null;
      }
      catch (Exception e) {
        throw new FrameException( -22990010, "xa�ύ���ݳ���", LogHelper.getStackMsg(e));
      }
    }
  }

  /**
   * xa�����µĻع�����
   *
   * @param usTran
   * @throws FrameException
   */
  public static void tpRollback(UserTransaction usTran) throws FrameException {
    if (usTran != null) {
      try {
        usTran.rollback();
        usTran = null;
      }
      catch (Exception e) {
        throw new FrameException( -22990010, "xa�ع����ݳ���", LogHelper.getStackMsg(e));
      }
    }
  }



  public SysSet() {
    super();
    if (m_Logger == null)
      m_Logger = Logger.getLogger(getClass().getName());
  }

 

  public static String encryptString(String strSrc) throws FrameException {
    String strDes = null;
    int iLen = 0;
    byte btSrc[] = null;
    byte btTemp[] = strSrc.getBytes();
    iLen = btTemp.length;
    int iPad = iLen % 8;
    if (iPad > 0)
      iLen = iLen + (8 - iPad);
    btSrc = new byte[iLen];

    for (int i = 0; i < btTemp.length; i++) {
      btSrc[i] = btTemp[i];
    }

    try {
      byte btResult[] = DesTool.RunDES(DesTool.ENCRYPT, DesTool.CBC,
                                       m_encryptKey, btSrc);
      strDes = DesTool.byteArr2HexStr(btResult);
    }
    catch (Exception e) {
      throw new FrameException( -22990041, "�ӽ����ַ���ʧ��", LogHelper.getStackMsg(e));
    }

    return strDes;
  }

  public static String decryptString(String strSrc) throws FrameException {
    String strDes = null;
    int iLen = 0;
    try {
      byte btSrc[] = DesTool.hexStr2ByteArr(strSrc);
      byte btResult[] = DesTool.RunDES(DesTool.DECRYPT, DesTool.CBC,
                                       m_encryptKey, btSrc);
      while (btResult[iLen] != 0)
        iLen++;
      strDes = new String(btResult, 0, iLen);
    }
    catch (Exception e) {
      throw new FrameException( -22990041, "�ӽ����ַ���ʧ��", LogHelper.getStackMsg(e));
    }

    return strDes;
  }

  public static int GeneDict(DynamicDict aDict, Element aElem) throws
      FrameException {
    String strNodeName, strNodeValue;
    ArrayList alList = new ArrayList();
    NodeList nlChild = null; // �ӽڵ�
    Element elBO = null;
    DynamicDict newBO = null;
    // ֻ������һ��parameters

    // Ŀǰֻ��parameters=DEFAULT�Ľڵ㣬����parameters�ڵ����
    NodeList nl1 = aElem.getChildNodes();
    // ���Ȼ�ȡ����Element�����û���ӽ����Ϊ��ǰBO��ֵ����BOֻ��һ��ʵ����������BO���Դ��ڶ��
    for (int n = 0; n < nl1.getLength(); n++) {
      Node ePara = nl1.item(n);
      // ������Element�Ž��д���
      if (ePara == null || ePara.getNodeType() != Node.ELEMENT_NODE)
        continue;
      // ����Ƿ����Child Element���еĻ����ǵ�����BO:�½�Dict

      nlChild = ePara.getChildNodes();
      //�˴����BOֻ��һ���ֽڵ�����ɲ���BO����
      if (nlChild.getLength() > 1) {
        alList.add(ePara);
        continue;
      }
      //�����޸�
      if (nlChild.getLength() == 1 && nlChild.item(0).hasChildNodes()) {
        alList.add(ePara);
        continue;
      }
      strNodeName = ePara.getNodeName();
      if (nlChild.getLength() == 0)
        strNodeValue = "";
      else
        strNodeValue = ePara.getFirstChild().getNodeValue();
      aDict.setValueByName(strNodeName, strNodeValue);
    }
    for (int n = 0; n < alList.size(); n++) {
      elBO = (Element) alList.get(n);
      strNodeName = elBO.getNodeName();
      newBO = new DynamicDict();
      newBO.m_ActionId = aDict.m_ActionId;
      GeneDict(newBO, elBO);
      aDict.setValueByName(strNodeName, newBO, 1);
    }

    return 0;
  }
}