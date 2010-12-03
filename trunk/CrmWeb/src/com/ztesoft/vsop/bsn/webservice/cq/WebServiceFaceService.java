/**
 * WebServiceFaceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public interface WebServiceFaceService extends javax.xml.rpc.Service {
    public java.lang.String getWebServiceFaceAddress();

    public WebServiceFace_PortType getWebServiceFace() throws javax.xml.rpc.ServiceException;

    public WebServiceFace_PortType getWebServiceFace(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
