package com.ztesoft.common.servicelocator;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {
  public XMLReader() {
  }

  /**
   * 通过加载一个XML 文件来实例化form属性。
   * @param xmlFile File 要加载的XML 文件的FILE 对象。
   * @throws Exception 抛出文件解析出错的异常。
   */
  private Document doc = null;
  private Node root = null;

  public void loadXML(String xmlFile) throws Exception {
    loadXML(new File(xmlFile));
  }

  public void loadXML(File xmlFile) throws Exception {
    if (! (xmlFile.exists() && xmlFile.isFile() && xmlFile.canRead())) {
      throw new Exception("发生IO错误，不能访问配置文件 " +xmlFile.getAbsolutePath()  + " 。");
    }
    //至此得到 document对象。
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(xmlFile);
      root = doc.getDocumentElement();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception("解析配置文件错误。");
    }
  }

  public  String getNodeText(Node textNode) {
    StringBuffer buf = new StringBuffer();
    Node n;
    NodeList nodes = textNode.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      n = nodes.item(i);
      if (n.getNodeType() == Node.TEXT_NODE) {
        buf.append(n.getNodeValue());
      }
      else {
      }
    }
    return buf.toString();
  }
  public Node getRoot() {
    return root;
  }
}
