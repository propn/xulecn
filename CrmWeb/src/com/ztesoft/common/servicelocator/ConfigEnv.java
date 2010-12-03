package com.ztesoft.common.servicelocator;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigEnv {
  private static String providerUrl =null;
  private static String securityPrincipals =null;
  private static String securityCredentials =null;

  private static String configXML = "environment.xml";
  private static String configDIR = System.getProperty("user.dir");
  private static String separator = System.getProperty("file.separator");
  
  private static Logger logger = Logger.getLogger(ConfigEnv.class);

  public ConfigEnv() {
  }


  private static void read() throws Exception{
    String xml = configDIR + separator + configXML;
    XMLReader xmlRd = new XMLReader();
    xmlRd.loadXML(xml);
    NodeList nl = xmlRd.getRoot().getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node tmpNode = nl.item(i);
      if (Node.ELEMENT_NODE == tmpNode.getNodeType()) {
        String nn = tmpNode.getNodeName();
        String value = xmlRd.getNodeText(tmpNode);
        logger.debug(nn +"|" + value);
        if("providerurl".equals(nn)){
          providerUrl =value;
        }else
        if("securityprincipal".equals(nn)){
          securityPrincipals = value;
        }else
        if("securitycredentials".equals(nn)){
          securityCredentials = value;
        }
      }
    }
  }
  public static String getSecurityPrincipals() throws Exception{
    if(null==securityPrincipals){
      try{
        read();
      }catch(Exception ex){
        throw new Exception("xxx" + ex.toString());
      }
    }
    return securityPrincipals;
  }
  public static  String getSecurityCredentials()  throws Exception{
    if(null==securityCredentials){
      try{
        read();
      }catch(Exception ex){
        throw new Exception("xxx" + ex.toString());
      }
    }
    return securityCredentials;
  }
  public static  String getProviderUrl()  throws Exception{
    if(null==providerUrl){
      try{
        read();
      }catch(Exception ex){
        throw new Exception("xxx" + ex.toString());
      }
    }
    return providerUrl;
  }

}
