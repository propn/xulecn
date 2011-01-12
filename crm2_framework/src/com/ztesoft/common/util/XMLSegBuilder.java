package com.ztesoft.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class XMLSegBuilder
{
    /**
     * ��vo�б�ת��Ϊ��items������xml�ַ�����ÿ��voΪһ��item�ڵ㣩
     * @param alVOs vo�б����vo�б��������νṹ������Ҫ���������е�·����������(�����ݿ���е��ֶ�һ��Ϊpath_code)
     * @return vo�б��ɵ�������TreeList�����xml�ַ���
     */
    public static String toXmlItems(ArrayList alVOs)
    {
        if(alVOs==null || alVOs.size()<1)
        {
            return "<items/>";
        }
        if(((XMLItem)alVOs.get(0)).pathInTree()==null)
        {
            return toListItems(alVOs);
        }
        else
        {
            return toTreeItems(alVOs);
        }
    }
    
    /**
     * ���ݼ�¼������XML���ṹƬ�ϣ����ڴ������ṹ��vo��ת��Ϊ���ṹ��itemǶ�׽ڵ�
     * @param alVOs VO�������밴path_code����
     * @return XML��ʽ�������ݣ���xmlͷ
     */
    public static String toTreeItems(ArrayList alVOs)
    {
        StringBuffer sbXml = new StringBuffer();
        String pathCur;
        String pathPre = "";
        LinkedList llTree = new LinkedList();
        llTree.add("");
        
        sbXml.append("<items>");
        XMLItem vo;
        Iterator iterVO = alVOs.iterator();
        while(iterVO.hasNext())
        {
            vo = (XMLItem)iterVO.next();
            pathCur = vo.pathInTree()+".";
            if(!pathCur.startsWith(pathPre)) //·������ǰһ��Ԫ�ص�·����ͷ���������ӽڵ㣬��Ҫ����ǰ��δ��սڵ���ӽ�����ǩ
            {
                pathPre = (String)llTree.removeLast();
                while(!pathCur.startsWith(pathPre)) //������ǰ�������δ��սڵ㣬���������ӽڵ��������ӽ�����ǩ
                {
                    sbXml.append("</item>");
                    pathPre = (String)llTree.removeLast();
                }
                llTree.addLast(pathPre);
            }
            llTree.addLast(pathCur);
            vo.toXmlItemUnclosed(sbXml);
            pathPre = pathCur;
        }
        for(int i=1; i<llTree.size(); i++) //���������δ��յĽڵ㣬��ӽ�����ǩ
        {
            sbXml.append("</item>");
        }
        sbXml.append("</items>");
        return sbXml.toString();
    }
    
    /**
     * ���ݼ�¼������XML���ṹƬ�ϣ�����û�����ṹ�Ķ��󼯣�ֱ�ӽ��б��ÿ��Ԫ��ת��Ϊһ����յ�item�ڵ�
     * @param alVOs VO�������밴path_code����
     * @return XML��ʽ�������ݣ���xmlͷ
     */
    public static String toListItems(ArrayList alVOs)
    {
        StringBuffer sbXml = new StringBuffer();
        sbXml.append("<items>");
        XMLItem vo;
        Iterator iterVO = alVOs.iterator();
        while(iterVO.hasNext())
        {
            vo = (XMLItem)iterVO.next();
            vo.toXmlItemUnclosed(sbXml);
            sbXml.append("</item>");
        }
        sbXml.append("</items>");
        return sbXml.toString();
    }
    
//    /**
//     * ���ݼ�¼������XML���ṹƬ��
//     * @param rsst ����������һ�б���Ϊpath_code����ʾ�ü�¼�����е�·��
//     * @param namesFldToAttr ��ӳ�䵽�ڵ�������� 
//     * @return XML��ʽ�������ݣ���xmlͷ
//     * @throws SQLException
//     * @deprecated
//     */
//    public static String buildTreeFromResultSet(ResultSet rsst, String[] namesFldToAttr) throws SQLException
//    {
//        StringBuffer sbXml = new StringBuffer();
//        String pathCur;
//        String pathPre = "";
//        LinkedList llTree = new LinkedList();
//        llTree.add("");
//        sbXml.append("<items>");
//        while(rsst.next())
//        {
//            pathCur = rsst.getString(1)+".";
//            if(!pathCur.startsWith(pathPre)) //new subnode
//            {
//                pathPre = (String)llTree.removeLast();
//                while(!pathCur.startsWith(pathPre))
//                {
//                    sbXml.append("</item>");
//                    pathPre = (String)llTree.removeLast();
//                }
//                llTree.addLast(pathPre);
//            }
//            llTree.addLast(pathCur);
//            sbXml.append("<item ");
//            for(int i=0; i<namesFldToAttr.length; i++)
//            {
//                sbXml.append(namesFldToAttr[i]);
//                sbXml.append("='");
//                sbXml.append(escapeXMLStringValue(rsst.getObject(i+1)));
//                sbXml.append("' ");
//            }
//            sbXml.append(">");
//            pathPre = pathCur;
//        }
//        if(llTree.size()>1)
//        {
//            for(int i=1; i<llTree.size(); i++)
//            {
//                sbXml.append("</item>");
//            }
//        }
//        else
//        {
//            for(int i=0; i<namesFldToAttr.length; i++)
//            {
//                sbXml.append(namesFldToAttr[i]);
//                sbXml.append("=' '");
//            }
//        }
//        sbXml.append("</items>");
//        return sbXml.toString();
//    }
    
    /**
     * ���ݽ��������XML�ṹƬ��
     * @param rsst �����������¼Ϊƽ��ʵ��
     * @param namesFldToAttr ��ӳ�䵽�ڵ�������� 
     * @return XML��ʽ�������ݣ���xmlͷ
     * @throws SQLException
     */
    public static String buildNodesFromResultSet(ResultSet rsst, String[] namesFldToAttr) throws SQLException
    {
        StringBuffer sbXml = new StringBuffer();
        sbXml.append("<items>");
        while(rsst.next())
        {
            sbXml.append("<item ");
            for(int i=0; i<namesFldToAttr.length; i++)
            {
                sbXml.append(namesFldToAttr[i]);
                sbXml.append("='");
                sbXml.append(escapeXMLStringValue(rsst.getObject(i+1)));
                sbXml.append("' ");
            }
            sbXml.append("/>");
        }
        sbXml.append("</items>");
        return sbXml.toString();
    }
    
    public static String escapeXMLStringValue(Object objToStr)
    {
    	String ret = (objToStr == null ? "":objToStr.toString() );
    	ret = ret.replaceAll( "&", "&amp;") ;//�滻�������ַ�&
    	ret = ret.replaceAll("'", "&apos;") ;//�滻�������ַ�������
    	ret = ret.replaceAll( "<", "&lt;") ;//�滻�������ַ�С�ں�
    	ret = ret.replaceAll( "<", "&gt;") ;//�滻�������ַ����ں�
    	ret = ret.replaceAll( "\"", "&quot;") ;//�滻�������ַ�˫����
    	return ret ;
        //return objToStr==null ? "":objToStr.toString();
    }
    
    /**
     * ��ȡXML�ļ��ַ������ض�����ֵ
     * @param xmlStr
     * @param itemName
     * @return
     */
    public static ArrayList getXMLItem(String xmlStr, String itemName){
    	ArrayList returnValue = new ArrayList();
    	String tempXml = "";
    	boolean flag = true;
    	if(xmlStr==null || "".equals(xmlStr))
    		return returnValue;
    	
    	if(xmlStr.indexOf(itemName)>0){
    		flag = true;
    		tempXml = xmlStr;
    	}
    	
    	while(flag){
    		String itemvalue = tempXml.substring(tempXml.indexOf("<"+itemName+">") + itemName.length() + 2, tempXml.indexOf("</"+itemName+">"));
    		returnValue.add(itemvalue);
    		tempXml = tempXml.substring(tempXml.indexOf("</"+itemName+">") + itemName.length() + 2);
    		if(tempXml.indexOf(itemName)<0){
        		flag = false;
    		}
    	}
    	
    	return returnValue;
    }
}
