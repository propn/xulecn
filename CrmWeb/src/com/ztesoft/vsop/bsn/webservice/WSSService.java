/**
 * WSSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice;

public interface WSSService extends javax.xml.rpc.Service {
    public java.lang.String getWSSAddress();

    public com.ztesoft.vsop.bsn.webservice.WSS getWSS() throws javax.xml.rpc.ServiceException;

    public com.ztesoft.vsop.bsn.webservice.WSS getWSS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
