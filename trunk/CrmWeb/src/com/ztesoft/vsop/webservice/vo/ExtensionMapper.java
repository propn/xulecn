/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package com.ztesoft.vsop.webservice.vo;


/**
 *  ExtensionMapper class
 */
public class ExtensionMapper {
    public static java.lang.Object getTypeObject(
        java.lang.String namespaceURI, java.lang.String typeName,
        javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        if ("http://www.mbossvsop.com.cn/vsop".equals(namespaceURI) &&
                "vsopServiceResponse".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.VsopServiceResponse.Factory.parse(reader);
        }

        if ("http://www.mbossvsop.com.cn/vsop".equals(namespaceURI) &&
                "vsopServiceRequest".equals(typeName)) {
            return com.ztesoft.vsop.webservice.vo.VsopServiceRequest.Factory.parse(reader);
        }

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " +
            namespaceURI + " " + typeName);
    }
}
