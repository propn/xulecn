package com.ztesoft.common.servicelocator.web;

import java.net.*;
import java.util.*;
import javax.ejb.*;
import javax.jms.*;
import javax.jms.Queue;
import javax.naming.*;
import javax.rmi.*;
import javax.sql.*;
import javax.transaction.*;

import com.ztesoft.common.servicelocator.*;
import com.ztesoft.common.util.tracer.*;
//import weblogic.jndi.*;

/**
 * This class is an implementation of the Service Locator pattern. It is
 * used to looukup resources such as EJBHomes, JMS Destinations, etc.
 * This implementation uses the "singleton" strategy and also the "caching"
 * strategy.
 * This implementation is intended to be used on the web tier and
 * not on the ejb tier.
 */
public class ServiceLocator {

  private InitialContext ic;
  // private Context ic;

  //used to hold references to EJBHomes/JMS Resources for re-use
  private Map cache = Collections.synchronizedMap(new HashMap());

  private static ServiceLocator instance = new ServiceLocator();

  public static ServiceLocator getInstance() {
    return instance;
  }

  private ServiceLocator() throws ServiceLocatorException {
    try {
      ic = new InitialContext();
      // ic = getInitialContext();
    }
    catch (Exception e) {
      throw new ServiceLocatorException(e);
    }
  }

  /**
   * will get the ejb Local home factory. If this ejb home factory has already been
   * clients need to cast to the type of EJBHome they desire
   *
   * @return the EJB Home corresponding to the homeName
   */
  public EJBLocalHome getLocalHome(String jndiHomeName) throws
      ServiceLocatorException {
    EJBLocalHome home = (EJBLocalHome) cache.get(jndiHomeName);
    if (home == null) {
      try {
        home = (EJBLocalHome) ic.lookup(jndiHomeName);
        cache.put(jndiHomeName, home);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return home;
  }

  /**
   * will get the ejb Remote home factory. If this ejb home factory has already been
   * clients need to cast to the type of EJBHome they desire
   *
   * @return the EJB Home corresponding to the homeName
   */
  public EJBHome getRemoteHome(String jndiHomeName, Class className) throws
      ServiceLocatorException {
    EJBHome home = (EJBHome) cache.get(jndiHomeName);
    if (home == null) {
      try {
        Object objref = ic.lookup(jndiHomeName);
        Object obj = PortableRemoteObject.narrow(objref, className);
        home = (EJBHome) obj;
        cache.put(jndiHomeName, home);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return home;
  }

  public EJBHome getRemoteHome(String jndiHomeName, Class className,
                               Object callerObj) throws
      ServiceLocatorException {
    EJBHome home = (EJBHome) cache.get(jndiHomeName);
    if (home == null) {
      try {
        Object objref = ic.lookup(jndiHomeName);
        Object obj = PortableRemoteObject.narrow(objref, className);
        home = (EJBHome) obj;
        cache.put(jndiHomeName, home);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return home;
  }

  /**
   * @return the factory for the factory to get queue connections from
   */
  public QueueConnectionFactory getQueueConnectionFactory(String
      qConnFactoryName) throws ServiceLocatorException {
    QueueConnectionFactory factory = (QueueConnectionFactory) cache.get(
        qConnFactoryName);
    if (factory == null) {
      try {
        factory = (QueueConnectionFactory) ic.lookup(qConnFactoryName);
        cache.put(qConnFactoryName, factory);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return factory;
  }

  /**
   * @return the Queue Destination to send messages to
   */
  public Queue getQueue(String queueName) throws ServiceLocatorException {
    Queue queue = (Queue) cache.get(queueName);
    if (queue == null) {
      try {
        queue = (Queue) ic.lookup(queueName);
        cache.put(queueName, queue);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return queue;
  }

  /**
   * This method helps in obtaining the topic factory
   * @return the factory for the factory to get topic connections from
   */
  public TopicConnectionFactory getTopicConnectionFactory(String
      topicConnFactoryName) throws ServiceLocatorException {
    TopicConnectionFactory factory = (TopicConnectionFactory) cache.get(
        topicConnFactoryName);
    if (factory == null) {
      try {
        factory = (TopicConnectionFactory) ic.lookup(topicConnFactoryName);
        cache.put(topicConnFactoryName, factory);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return factory;
  }

  /**
   * This method obtains the topc itself for a caller
   * @return the Topic Destination to send messages to
   */
  public Topic getTopic(String topicName) throws ServiceLocatorException {
    Topic topic = (Topic) cache.get(topicName);
    if (topic == null) {
      try {
        topic = (Topic) ic.lookup(topicName);
        cache.put(topicName, topic);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return topic;
  }

  /**
   * This method obtains the datasource itself for a caller
   *
   * @return the DataSource corresponding to the name parameter
   */
  public DataSource getDataSource(String dataSourceName) throws
      ServiceLocatorException {
    DataSource dataSource = (DataSource) cache.get(dataSourceName);
    if (dataSource == null) {
      try {
        dataSource = (DataSource) ic.lookup(dataSourceName);
        cache.put(dataSourceName, dataSource);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return dataSource;
  }

  public DataSource getDataSource(String dataSourceName, Object callerObj) throws
      ServiceLocatorException {
    DataSource dataSource = (DataSource) cache.get(dataSourceName);
    if (dataSource == null) {
      try {
        dataSource = (DataSource) ic.lookup(dataSourceName);
        cache.put(dataSourceName, dataSource);
      }
      catch (Exception e) {
        throw new ServiceLocatorException(e);
      }
    }
    return dataSource;
  }

  /**
   * This method obtains the UserTransaction itself for a caller
   *
   * @return the UserTransaction corresponding to the name parameter
   */
  public UserTransaction getUserTransaction(String utName) throws
      ServiceLocatorException {
    try {
      return (UserTransaction) ic.lookup(utName);
    }
    catch (Exception e) {
      throw new ServiceLocatorException(e);
    }
  }

  public UserTransaction getUserTransaction(String utName, Object callerObj) throws
      ServiceLocatorException {
    try {
      return (UserTransaction) ic.lookup(utName);
    }
    catch (Exception e) {
      throw new ServiceLocatorException(e);
    }
  }

  /**
   * @return the URL value corresponding
   * to the env entry name.
   */
  public URL getUrl(String envName) throws ServiceLocatorException {
    try {
      return (URL) ic.lookup(envName);
    }
    catch (Exception e) {
      throw new ServiceLocatorException(e);
    }
  }

  /**
   * @return the boolean value corresponding
   * to the env entry such as SEND_CONFIRMATION_MAIL property.
   */
  public boolean getBoolean(String envName) throws ServiceLocatorException {
    try {
      return ( (Boolean) ic.lookup(envName)).booleanValue();
    }
    catch (Exception e) {
      throw new ServiceLocatorException(e);
    }
  }

  /**
   * @return the String value corresponding
   * to the env entry name.
   */
  public String getString(String envName) throws ServiceLocatorException {
    try {
      return (String) ic.lookup(envName);
    }
    catch (Exception e) {
      throw new ServiceLocatorException(e);
    }
  }


  /**
   * getCatchList
   *
   * @param jndiName String
   * @return Map
   */
  public Map getCatchList(String jndiName) {
    Map catchListMap = null;
    try {
      catchListMap = (Map) ic.lookup(jndiName);
    }
    catch (NamingException ex) {
      // Debug.print("未能获取缓存列表" + jndiName + "：" + ex.toString(), this);
    }
    return catchListMap;
  }

  /**
   * setCatchList
   *
   * @param jndiName String
   * @param catchListMap Map
   * @return boolean
   */
  public boolean setCatchList(String jndiName, Map catchListMap) {
    try {
      ic.rebind(jndiName, catchListMap);
    }
    catch (NamingException ex) {
      Debug.print("绑定缓存列表时出错：" + ex.toString(), this);
      return false;
    }
    return true;
  }

}
