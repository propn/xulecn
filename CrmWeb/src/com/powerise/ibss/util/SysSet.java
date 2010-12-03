package com.powerise.ibss.util;

/**
 * 错误号码段: 229910xx
 * 框架和各个业务模块使用的公共方法,能够使用的都是静态(static)方法
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
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.LogHelper;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;

public class SysSet {
  // private static boolean m_Inited = false;
  private static String NAMESPACE = "http://www.ztesoft.com/ns/PowerIBSS";

//  private static Context ctx = null;

//  private static String m_UseEncryptPwd = null;

//  static final String m_encrypPrefix = "{3DES}";

//  static final int m_encrypPrefixLen = m_encrypPrefix.length();

  static final byte m_encryptKey[] = {
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2,
      3, 4, 5, 6, 7, 8, 9, 8, 8, 8, 8};

  static final String m_PWDKey[] = {
      "PASSWORD", "PASSWD"};

  static HashMap action_cache; // 缓存已经实例化的类实例

  public static int ACTION_QUERY = 1, ACTION_SERVICE = 2;

//  static final String CONFIG_DIR = "config"; // 配置文件的目录，$IBSS_HOME/config

//  static final String CONFIG_FILE = "ibss.xml"; // 配置文件名，$IBSS_HOME/config/ibss.xml

//  public static HashMap m_ActData = null;

//  private static HashMap m_ActionConns = null; // 针对每个线程使用的Connection，其中Connection可能存在多个，因此对应的存储变量是MultiDBUtil类

  private static HashMap m_DBLink = null; // 数据库的连接配置,因为存在多个的数据库配置，因此其结构是二级hashmap

  private static String m_DefaultDBName = JNDINames.DEFAULT_DATASOURCE; // 缺省的数据库连接名称，考虑同时连接多个数据库，所以此值可变

  private static boolean m_FrameDataInMem = false;

  private static Logger m_Logger = Logger.getLogger(SysSet.class);

  private static boolean m_MultiDB = true ;

  // or HashMap)
//  private static HashMap m_ServData = null;

  private static HashMap m_System = null; // 其它配置，区别于DBLink的其它配置，其结构是HashMap->HashMap->(String

  private static int m_XATranTimeOut = 60;
  
  public static boolean surportAjax=true;//是否支持bho和ajax
  
  /**
   * 此方法用来调试使用。类似SQLPLUS,当每个服务被调执行之后，可以通过此方法在未提交之前查看数据，由于可以回滚
   * 对于同一批测试数据，可以重复用来测试。 支持SQL语句，查询，插入，建表，提交，回滚，不支持desc等非标准SQL语句
   *
   * @param conn
   *            数据库连接
   *            建议将服务使用的Connection传入，这样能够确保是同一个连接和Session.看到的数据就是之前操作过的,如果做测试的话,必须在测试结束之前进行回滚
   */
  public static void check(Connection conn) {
    Statement st = null;
    ResultSet rs = null;
    StringBuffer strBuffer = new StringBuffer();
    String strInput = null;
    byte[] byteData = null;
    int iRead = 0, iLen = 0;
    boolean bStart = true, bExec = false;
    System.out.println("如果需要检查执行结果，键入SQL，如想退出，键入exit或者quit");
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
        if (System.in.available() == 0) { // 读取完毕
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
              // 执行SQL语句
              strInput = strInput.substring(0,
                                            strInput.length() - 1);
              strBuffer.setLength(0);
              bExec = false;
              try {
                st = conn.createStatement();
                bExec = st.execute(strInput);
                if (bExec) { // 返回结果集
                  int iLoop, j;
                  Object obj;
                  rs = st.getResultSet();
                  rs.setFetchSize(30);
                  ResultSetMetaData rsdm = rs.getMetaData();
                  int iFields = rsdm.getColumnCount();
                  for (iLoop = 1; iLoop <= iFields; iLoop++) {
                    System.out.print(rsdm
                                     .getColumnName(iLoop));
                    // 字段名
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
                  System.out.println("结果集行数: " + j);
                }
                else {
                  System.out.println("影响行数: "
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
   * 根据配置文件的DB Name来获取数据的配置情况
   *
   * @param strDBName
   * @return 名称-值配对的数据库配置参数,例如Url,Driver等等
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
   * 获取系统的HOME目录，首先从系统变量IBSS_HOME中获取，如果没有，则直接获取当前用户的 HOME环境变量
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
   * 判断是否系统已经初始化
   *
   * @return
   */
  public static boolean getInitFlag() {
    return GlobalNames.CONFIG_LOADED;
  }


  /**
   * 获取配置文件中System节中的指定属性的配置内容
   *
   * @param strSetName
   * @return
   * @throws FrameException
   */
  public static String getSystemSet(String strSetName) throws FrameException {
    return getSystemSet("System", strSetName, null);
  }

  /**
   * 获取配置文件中System节中的指定属性的配置内容,可以指定当查找不到时的缺省值
   *
   * @param strSetName
   *            参数名称
   *
   * @return
   * @throws FrameException
   */
  public static String getSystemSet(String strSetName, String strDefaultValue) throws
      FrameException {
    return getSystemSet("System", strSetName, strDefaultValue);
  }

  /**
   * 获取配置文件中指定节中的指定属性的配置内容,可以指定当查找不到时的缺省值,此APi只适用于类似System节的结构
   *
   * @param strTypeName
   *            配置类型名称
   * @param strSetName
   *            参数名称
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
   * 获取配置文件中指定节中的指定属性的配置内容集,没有找到返回null,此API适用于MQ节的配置结构,如果存在多值(属性名称相同)的话,则返回第一个
   *
   * @param strTypeName
   *            类型节的名称
   * @param strSetName
   *            属性名称
   * @return 名称-值对的配置内容
   * @throws FrameException
   */
  public static HashMap getSystemSets(String strTypeName, String strSetName) throws
      FrameException {
    return getSystemSets(strTypeName, strSetName, null, null);
  }

  /**
   * 获取配置文件中指定节中的指定属性的配置内容集,没有找到返回null,此API适用于MQ节的配置结构,并且针对存在多个属性名称相同的配置,因此如果需要定位到具体的值,还需要指定查询的子属性名称和子属性值,例如MQ配置中Id子属性
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
    // 代码中没有对类的类型进行判断，因为这是基于建立这些参数数据来考虑的
    if (m_System != null) {
      hmSet = (HashMap) m_System.get(strTypeName);
      if (hmSet != null) {
        arAttr = (ArrayList) hmSet.get(strSetName);
        iSize = arAttr.size();
        if (iSize > 0) {
          if (strSearchName != null && strSearchValue != null) { // 这样就可以查找了
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
   * 在XA环境下,获取JTA的UserTransaction
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
      throw new FrameException( -22990013, "创建UserTransaction异常",
                               LogHelper.getStackMsg(e));
    }
    return usTran;
  }

  /**
   * 初始化多数据库模式下的连接池,同时获取配置中的XA事务Timeout值,配置文件中System节可以通过配置XATransactionTime来设置时长
   *
   * @throws FrameException
   */
  public static void initConnContainer() throws FrameException {
//    if (m_ActionConns == null) {
//        m_ActionConns = new HashMap();
        // 获取XA事务的时间
        m_XATranTimeOut = Integer.parseInt(SysSet.getSystemSet(
            "XATransactionTime", "60"));
//    }
  }

  /**
   * 初始化系统配置
   *
   * @throws FrameException
   */
  public static void initSystem() throws FrameException {
    initSystem(3); // 缺省按照统一部署配置
  }

  /**
   *
   * @param iFlag
   *            1- Web Server init 2 - App Server init 3 Web Server and App
   *            Server
   * @throws FrameException
   */
  public static synchronized void initSystem(int iFlag) throws FrameException {
    // 设置log4j 的配置
    String strTemp = null;
   
    if (!GlobalNames.CONFIG_LOADED) {
    	try {
//    		 配置参数载入
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
            .isoToGBK("南京中兴软件科技股份有限公司", true)
            : CrmParamsConfig.getInstance().getParamValue("SystemName").toString();
        GlobalNames.SOFTWARE_NAME = CrmParamsConfig.getInstance().getParamValue("SystemName") == null ? "中兴软创"
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
      // 清除掉缓存数据
      CacheData.clear();
      
      strTemp = System.getProperty("DEFAULT_DB"); // 如果需要设置，则在java命令行增加参数
      // -DDEFAULT_DB=xxxx
      if (strTemp != null)
        m_DefaultDBName = strTemp;
      else
        m_DefaultDBName = JNDINames.DEFAULT_DATASOURCE; // 缺省的数据库连接名称
        // 获取框架是否装入内存的配置
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
      // 因为以下涉及到数据库的处理，而在获取连接时在判断是否初始化了系统，如果在config_loaded=true来做的话，就会造成死循环
//      String directConn = CrmParamsConfig.getInstance().getParamValue("DIRECT_CONNECTION") ;
      if (m_FrameDataInMem ) {
        m_Logger.info("start load frame data...");
//        getFrameSetData();
        //初始化数据
        FrameServiceController.getInstance() ;
        m_Logger.info("load complete");
      }
      System.out.println("!GlobalNames.CONFIG_LOADED="+!GlobalNames.CONFIG_LOADED +
		"\n m_FrameDataInMem="+m_FrameDataInMem) ;
      // 启动监控
//      if (getSystemSet("Monitor", "true").equals("true"))
//        Monitor.start();
    }
  }

  /**
   * 获取框架数据是否载入到内存
   *
   * @return
   */
  public static boolean isFrameDataInMem() {
    return m_FrameDataInMem;
  }

  /**
   * 判断是否多数据库模式
   *
   * @return
   */
  public static boolean isMultiDB() {
    return m_MultiDB;
  }

  /**
   * 通过GlobalNames.ISO_TO_GBK定义对字符串进行ISO8859-1 对GBK的转换
   *
   * @param p_in -
   *            要转换的字符串
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

  // 需要判断是否采用的XA Driver，XA Driver 处理事务与非XA的代码是不一样的
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
   * xa环境下的启动事务
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
      throw new FrameException( -22990010, "xa初始化出错:"
                               + e.getClass().getName(), LogHelper.getStackMsg(e));
    }
  }

  /**
   * XA环境下的提交事务
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
        throw new FrameException( -22990010, "xa提交数据出错", LogHelper.getStackMsg(e));
      }
    }
  }

  /**
   * xa环境下的回滚事务
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
        throw new FrameException( -22990010, "xa回滚数据出错", LogHelper.getStackMsg(e));
      }
    }
  }



  public SysSet() {
    super();
    if (m_Logger == null)
      m_Logger = Logger.getLogger(getClass().getName());
  }

  /**
   * 将字典转换成调用的xml入参格式字符串
   *
   * @param dict
   * @return
   * @throws FrameException
   */
  public static String getInXMLFromDict(DynamicDict dict) throws FrameException {
    String strXML = null;
    HashMap hmValue = null;
    Iterator it;
    XmlSerializer m_serializer = null;
    ByteArrayOutputStream m_bsOut = null;
    String NAMESPACE = "http://www.powerise.com.cn/ns/PowerIBSS";
    String strName, strParam, strVal;
    ArrayList al = null;
    HashMap hmParam = null;
    Vector vOthers = new Vector(); // 存储不是Default的变量
    HashMap hmTempArray = new HashMap(); // 针对是HashMap的变量，需要临时创建ArrayList
    Object obj;
    if (dict == null)
      throw new FrameException( -22990020, "Input dict  is null");
    hmValue = dict.m_Values;
    String strActionId = dict.m_ActionId;

    int iSize;
    // 建立新的XML 处理对象及其初始化
    try {
      XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
          System.getProperty(XmlPullParserFactory.PROPERTY_NAME),
          null);
      m_serializer = factory.newSerializer();
      m_bsOut = new ByteArrayOutputStream();
      m_serializer.setOutput(new PrintWriter(m_bsOut, true));
      m_serializer.startDocument("UTF-8", null);
      m_serializer.ignorableWhitespace("\n\n");
      m_serializer.setPrefix("", NAMESPACE);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new FrameException( -22990021,
                               "Exception occurs when create input xml:" +
                               e.getMessage());
    }
    try {

      m_serializer.startTag(NAMESPACE, "Powerise_IBSS");
      m_serializer.startTag(NAMESPACE, "Definition");
      m_serializer.attribute(null, "Type", "1");
      m_serializer.endTag(NAMESPACE, "Definition");
      // 建立definition
      // 建立actionid
      m_serializer.startTag(NAMESPACE, "ActionId");
      m_serializer
          .text( ( (strActionId == null) || strActionId.equals("")) ? " "
                : strActionId);
      m_serializer.endTag(NAMESPACE, "ActionId");
      // 建立data-parameter
      m_serializer.startTag(NAMESPACE, "Data");
      // 遍历m_values参数表，分三种情况处理：
      // 1。普通参数，输出到parameters=DEFAULT节点中；
      // 2。arraylist，输出到parameters=变量名的节点中；其中，如果arraylist中元素是hashmap类型，则将hashmap内容依次输出。
      // 3。resultset，输出到parameters=变量名的节点中。
      // m_Logger.debug(strActionId+"参数:"+vArg);
      it = hmValue.keySet().iterator();
      m_serializer.startTag(NAMESPACE, "Parameters");
      m_serializer.attribute(null, "Name", "DEFAULT");
      while (it.hasNext()) {
        strName = (String) it.next();
        obj = hmValue.get(strName);
        if (obj == null)
          continue;
        if (obj.getClass().getName().indexOf("java.util.HashMap") >= 0) {
          al = new ArrayList();
          al.add(obj);
          hmTempArray.put(strName, al);
          continue;
        }

        if (obj.getClass().getName().indexOf("java.util.ArrayList") >= 0) {
          vOthers.add(strName);
          continue;
        }
        // 针对普通参数直接打包
        m_serializer.startTag(NAMESPACE, strName);
        m_serializer.text( (String) obj);
        m_serializer.endTag(NAMESPACE, strName);

      }
      m_serializer.endTag(NAMESPACE, "Parameters");
      // 处理为ArrayList的参数集
      for (int i = 0; i < vOthers.size(); i++) {
        strParam = (String) vOthers.get(i);
        m_serializer.startTag(NAMESPACE, "Parameters");
        m_serializer.attribute(NAMESPACE, "Name", strParam);
        al = (ArrayList) hmValue.get(strParam);
        for (int j = 0; j < al.size(); j++) {
          obj = al.get(j);
          if (obj.getClass().getName().indexOf("java.util.HashMap") >= 0) {
            hmParam = (HashMap) obj;
            it = hmParam.keySet().iterator();
            while (it.hasNext()) {
              strName = (String) it.next();
              strVal = (String) hmParam.get(strName);
              m_serializer.startTag(NAMESPACE, strName);
              m_serializer.text(strVal);
              m_serializer.endTag(NAMESPACE, strName);
            }
          }
          else {
            m_serializer.startTag(NAMESPACE, strParam);
            m_serializer.text( (String) obj);
            m_serializer.endTag(NAMESPACE, strParam);

          }
        }
        m_serializer.endTag(NAMESPACE, "Parameters"); // 结束Parameter
      }
      // 处理临时创建的ArrayList的处理
      Iterator itArr = hmTempArray.keySet().iterator();
      while (itArr.hasNext()) {
        strParam = (String) itArr.next();
        m_serializer.startTag(NAMESPACE, "Parameters");
        m_serializer.attribute(NAMESPACE, "Name", strParam);
        al = (ArrayList) hmTempArray.get(strParam);
        for (int j = 0; j < al.size(); j++) {
          obj = al.get(j);
          if (obj.getClass().getName().indexOf("java.util.HashMap") >= 0) {
            hmParam = (HashMap) obj;
            it = hmParam.keySet().iterator();
            while (it.hasNext()) {
              strName = (String) it.next();
              strVal = (String) hmParam.get(strName);
              m_serializer.startTag(NAMESPACE, strName);
              m_serializer.text(strVal);
              m_serializer.endTag(NAMESPACE, strName);
            }
          }
          else {
            m_serializer.startTag(NAMESPACE, strParam);
            m_serializer.text( (String) obj);
            m_serializer.endTag(NAMESPACE, strParam);

          }
        }
        m_serializer.endTag(NAMESPACE, "Parameters"); // 结束Parameter
      }
      // 文档结束处理
      m_serializer.endTag(NAMESPACE, "Data");
      m_serializer.endTag(NAMESPACE, "Powerise_IBSS");
      strXML = m_bsOut.toString();
    }
    catch (Exception e) {
      m_Logger.info(strActionId + LogHelper.getStackMsg(e));
      throw new FrameException( -22995102,
                               "Exception occurs when create input xml:"
                               + e.getClass().getName(), LogHelper.getStackMsg(e));
    }
    finally {
      if (m_bsOut != null) {
        try {
          m_bsOut.close();
        }
        catch (Exception e) {
        }
        m_bsOut = null;
      }
    }
    return strXML;

  }

//  public static DynamicDict callRemoteEJB(DynamicDict dict) throws
//      FrameException {
//    Object obj = null;
//    DynamicDict outDict = null;
//    String strEJBName = "DispatchEJB";
//    com.powerise.ibss.framework.ejb.DispatchEJB disp = null;
//    try {
//      if (ctx == null)
//        ctx = getInitialContext();
//      m_Logger.debug("查找JNDI对象");
//      obj = ctx.lookup(strEJBName);
//      com.powerise.ibss.framework.ejb.DispatchEJBHome dispatchEJBHome = (com.
//          powerise.ibss.framework.ejb.DispatchEJBHome) javax.rmi.
//          PortableRemoteObject
//          .narrow(
//          obj,
//          com.powerise.ibss.framework.ejb.DispatchEJBHome.class);
//      m_Logger.debug("创建EJB本地接口");
//      disp = dispatchEJBHome.create();
//      if (dict.flag == 1)
//        outDict = disp.performWEBDynamicAction(dict);
//      else
//        outDict = disp.perform(dict);
//    }
//    catch (java.rmi.RemoteException re) {
//      throw new FrameException( -100002003, "EJBUtil.DispatchEJB()出现异常！",
//                               re.getMessage());
//    }
//    catch (javax.ejb.CreateException re1) {
//      throw new FrameException( -100002002, "EJBUtil.DispatchEJB()出现异常！",
//                               re1.getMessage());
//    }
//    catch (Exception ce) {
//      ce.printStackTrace();
//      throw new FrameException( -100002001, "定位JNDI(" + strEJBName
//                               + ")出错！", ce.getMessage());
//    }
//    finally {
//      if (disp != null) {
//        try {
//          disp.remove();
//        }
//        catch (Exception e) {
//        }
//      }
//
//    }
//
//    return outDict;
//
//  }

  /**
   * 远程调用servlet AgentServlet的方法,通过将Dict序列化进行数据的交互
   *
   * @param dict
   * @return
   * @throws FrameException
   */
  public static DynamicDict callRemoteService(DynamicDict dict) throws
      FrameException {
    DynamicDict outDict = null;

    // 获取远程的配置
    String strServ, strPort, strServlet, strData, strEJB;
    String strStaffNo, strSiteNo;
    strServ = getSystemSet("RemoteService", "Server", null);
    strPort = getSystemSet("RemoteService", "Port", null);
    strServlet = getSystemSet("RemoteService", "AgentServlet", null);
    strSiteNo = getSystemSet("RemoteService", "SiteNo", null);
    strStaffNo = getSystemSet("RemoteService", "StaffNo", null);
    strEJB = getSystemSet("RemoteService", "UseEJB", "0"); // 缺省不使用EJB
    if (strServ == null || strPort == null || strServlet == null
        || strSiteNo == null || strStaffNo == null)
      throw new FrameException( -22995105, "需要在配置文件中配置RemoteService等参数");
    dict.setValueByName("SITE-NO", strSiteNo, 2);
    dict.setValueByName("STAFF-NO", strStaffNo, 2);
    dict.SetConnection(null);
//    if (strEJB.equals("1")) {
//      return callRemoteEJB(dict);
//
//    }
    try {
      strData = Base64.encodeObject(dict);
      strData = HttpTool.CallService(strServ, Integer.parseInt(strPort),
                                     strServlet, strData);
      outDict = (DynamicDict) Base64.decodeToObject(strData);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return outDict;
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
      throw new FrameException( -22990041, "加解密字符串失败", LogHelper.getStackMsg(e));
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
      throw new FrameException( -22990041, "加解密字符串失败", LogHelper.getStackMsg(e));
    }

    return strDes;
  }

  public static int GeneDict(DynamicDict aDict, Element aElem) throws
      FrameException {
    String strNodeName, strNodeValue;
    ArrayList alList = new ArrayList();
    NodeList nlChild = null; // 子节点
    Element elBO = null;
    DynamicDict newBO = null;
    // 只处理第一个parameters

    // 目前只读parameters=DEFAULT的节点，其余parameters节点忽略
    NodeList nl1 = aElem.getChildNodes();
    // 首先获取各个Element，如果没有子结点则为当前BO的值，根BO只有一个实例，其它子BO可以存在多个
    for (int n = 0; n < nl1.getLength(); n++) {
      Node ePara = nl1.item(n);
      // 必须是Element才进行处理
      if (ePara == null || ePara.getNodeType() != Node.ELEMENT_NODE)
        continue;
      // 检查是否存在Child Element，有的话则是单独的BO:新建Dict

      nlChild = ePara.getChildNodes();
      //此处如果BO只有一个字节点会生成不了BO对象
      if (nlChild.getLength() > 1) {
        alList.add(ePara);
        continue;
      }
      //刘武修改
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

  private static void GeneBOXML(XmlSerial aSerializer, HashMap hmValue,
                                Vector vArg, int iLevel, boolean isAjax) throws
      FrameException {
    ArrayList alObj = null;
    String strName, strValue = null;

    Object obj = null;
    Set hmset = null;
    DynamicDict newDict = null;
    boolean bGo = false;
    try {
      hmset = hmValue.keySet();
      if (hmset != null) { // hashmap有内容
        Iterator it = hmset.iterator();
        while (it.hasNext()) {
          strName = (String) it.next();
          // 对于首层的数据，需要检查是否需要送到前台
          if (iLevel == 0 && vArg != null) {
            bGo = false;
            for (int j = 0; j < vArg.size(); j++) {
              if (strName.equals( (String) vArg.get(j))) {
                bGo = true;
                break;
              }
            }
            if (bGo == false) //没有找到，说明不需要打到前台
              continue;
          }
          obj = hmValue.get(strName);

          if (obj == null) {
            //continue;
            //obj=" ";
            //strValue = " ";
          }
          if(obj==null) {
            aSerializer.startTag(NAMESPACE, strName.toUpperCase());
            //aSerializer.text("test");
            aSerializer.endTag(NAMESPACE, strName.toUpperCase());
          } else {
            String objClass = obj.getClass().getName();
            if (objClass.indexOf("ArrayList") != -1) { // 为BO类型
              alObj = (ArrayList) obj;
              //没有内容需要打一个空节点到前台 刘武修改
              if (isAjax && alObj.size() == 0) {
                aSerializer.startTag(NAMESPACE, strName
                                     .toUpperCase());
                //aSerializer.attribute(null, "type", "Array");
                aSerializer
                    .endTag(NAMESPACE, strName.toUpperCase());
              }
              for (int i = 0; (i < alObj.size()) && (i < 10000); i++) {
                //刘武修改2007-03-21 支持ArrayList中放HashMap
                Object itemObj = alObj.get(i);
                HashMap itemMap;
                String className = itemObj.getClass().getName();
                if (className.indexOf("HashMap") >= 0) {
                  itemMap = (HashMap) itemObj;
                }
                else if (className.indexOf("DynamicDict") >= 0) {
                  newDict = (DynamicDict) alObj.get(i);
                  itemMap = newDict.m_Values;
                }
                else {
                  throw new FrameException( -22990110,
                                           "unkown object type:" + strName +
                                           ":" +
                                           className +
                                           "object must be HashMap or DynamicDict");
                }

                aSerializer.startTag(NAMESPACE, strName
                                     .toUpperCase());
                //aSerializer.attribute(null, "type", "Array");
                GeneBOXML(aSerializer, itemMap, vArg,
                          iLevel + 1, isAjax);
                /*GeneBOXML(aSerializer, newDict.m_Values, vArg,
                         iLevel + 1);*/
                aSerializer
                    .endTag(NAMESPACE, strName.toUpperCase());
              }

            }
            else if (objClass.indexOf("HashMap") >= 0 ||
                     objClass.indexOf("DynamicDict") >= 0) { //刘武增加
              HashMap itemMap;
              if (objClass.indexOf("HashMap") >= 0)
                itemMap = (HashMap) obj;
              else
                itemMap = ( (DynamicDict) obj).m_Values;
              aSerializer.startTag(NAMESPACE, strName
                                   .toUpperCase());
              GeneBOXML(aSerializer, itemMap, vArg,
                        iLevel + 1, isAjax);
              aSerializer
                  .endTag(NAMESPACE, strName.toUpperCase());
            }
            else {
              aSerializer.startTag(NAMESPACE, strName.toUpperCase());
              //System.out.println("class name class nameclass nameclass nameclass name:"+obj.getClass());
              strValue = (String) obj;
              aSerializer.text(strValue.toString());
              aSerializer.endTag(NAMESPACE, strName.toUpperCase());
            }
          }
        }
      }

    }
    catch (Exception e) {
      throw new FrameException( -22990110,
                               "Exception occurs when create output xml:" +
                               e.getMessage(),
                               LogHelper.getStackMsg(e));
    }
  }

  
  
  public static void GeneBOXML(XmlSerial aSerializer, HashMap hmValue,
			Vector vArg, int iLevel) throws FrameException {
	  boolean	isAjax=false;
	  ArrayList alObj = null;
		String strName, strValue = null;
		Object obj = null;
		Set hmset = null;
		DynamicDict newDict = null;
		boolean bGo = false;
		try {
			hmset = hmValue.keySet();
			if (hmset != null) { // hashmap有内容
				Iterator it = hmset.iterator();
				while (it.hasNext()) {
					strName = (String) it.next();
					// 对于首层的数据，需要检查是否需要送到前台
					if (iLevel == 0 && vArg != null) {
						bGo = false;
						for (int j = 0; j < vArg.size(); j++) {
							if (strName.equals((String) vArg.get(j))) {
								bGo = true;
								break;
							}
						}
						if (bGo == false) //没有找到，说明不需要打到前台
							continue;
					}
					obj = hmValue.get(strName);

					if (obj == null) {
          continue;
          //strValue = "";
        }
        String objClass=obj.getClass().getName();
					if (objClass.indexOf("ArrayList") != -1) // 为BO类型
					{
						alObj = (ArrayList) obj;
          //没有内容需要打一个空节点到前台 刘武修改
          if(isAjax && alObj.size()==0) {
            aSerializer.startTag(NAMESPACE, strName
                                 .toUpperCase());
            //aSerializer.attribute(null, "type", "Array");
            aSerializer
                .endTag(NAMESPACE, strName.toUpperCase());
          }
						for (int i = 0; (i < alObj.size()) && (i < 10000); i++) {
            //刘武修改2007-03-21 支持ArrayList中放HashMap
            Object itemObj=alObj.get(i);
            HashMap itemMap;
            String className=itemObj.getClass().getName();
            if(className.indexOf("HashMap") >=0) {
              itemMap=(HashMap)itemObj;
            } else if(className.indexOf("DynamicDict") >=0) {
              newDict = (DynamicDict) alObj.get(i);
              itemMap=newDict.m_Values;
            } else {
              throw new FrameException(-22990110, strName+"对象不符合要求,数组中只能存放HashMap或者DynamicDict");
            }

            aSerializer.startTag(NAMESPACE, strName
                                 .toUpperCase());
            //aSerializer.attribute(null, "type", "Array");
            GeneBOXML(aSerializer, itemMap, vArg,
                      iLevel + 1);
            /*GeneBOXML(aSerializer, newDict.m_Values, vArg,
                     iLevel + 1);*/
            aSerializer
                .endTag(NAMESPACE, strName.toUpperCase());
          }

					} else if(objClass.indexOf("HashMap") >=0 || objClass.indexOf("DynamicDict") >=0) {  //刘武增加
          HashMap itemMap;
          if(objClass.indexOf("HashMap") >=0)
            itemMap=(HashMap)obj;
          else
            itemMap=((DynamicDict)obj).m_Values;
          aSerializer.startTag(NAMESPACE, strName
                                 .toUpperCase());
            GeneBOXML(aSerializer, itemMap, vArg,
                      iLevel + 1);
            aSerializer
                .endTag(NAMESPACE, strName.toUpperCase());
        } else {
						aSerializer.startTag(NAMESPACE, strName.toUpperCase());
        //System.out.println("class name class nameclass nameclass nameclass name:"+obj.getClass());
						strValue = (String) obj;

						aSerializer.text(strValue.toString());
						aSerializer.endTag(NAMESPACE, strName.toUpperCase());
					}
				}
			}

		} catch (Exception e) {
			throw new FrameException(-22990110, "处理出参数据时出现异常" + e.getMessage(),
					LogHelper.getStackMsg(e));
		}
	} 
  

  /**
   * 在多数据库模式下来获取数据库连接,框架使用
   *
   * @param strId
   *            可以是服务名称,也可以是查询的ActionId,通过type 来区分
   * @param iType
   *            iType=1代表通用查询,iType=2代表服务调用
   * @return
   * @throws FrameException
   */
  public static Connection getMultiConnection(String strId, int iType) throws
      FrameException {
    Connection conn = null;
    String strActionDBName = null;
    if (m_MultiDB) {
//      strActionDBName = getDBNameById(strId, iType);
      if (strActionDBName == null || strActionDBName.trim().equals(""))
        strActionDBName = m_DefaultDBName; // 给出缺省数据库名
      conn = ConnectionContext.getContext().getConnection(strActionDBName);
    }
    return conn;
  }

}