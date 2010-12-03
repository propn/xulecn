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
   * ͨ������һ��XML �ļ���ʵ����form���ԡ�
   * @param xmlFile File Ҫ���ص�XML �ļ���FILE ����
   * @throws Exception �׳��ļ�����������쳣��
   */
  private Document doc = null;
  private Node root = null;

  public void loadXML(String xmlFile) throws Exception {
    loadXML(new File(xmlFile));
  }

  public void loadXML(File xmlFile) throws Exception {
    if (! (xmlFile.exists() && xmlFile.isFile() && xmlFile.canRead())) {
      throw new Exception("����IO���󣬲��ܷ��������ļ� " +xmlFile.getAbsolutePath()  + " ��");
    }
    //���˵õ� document����
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      doc = builder.parse(xmlFile);
      root = doc.getDocumentElement();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new Exception("���������ļ�����");
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
