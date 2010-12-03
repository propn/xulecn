/**
 * WSSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice;

public class WSSServiceLocator extends org.apache.axis.client.Service implements com.ztesoft.vsop.bsn.webservice.WSSService {

    public WSSServiceLocator() {
    }


    public WSSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSS
    private java.lang.String WSS_address = "http://134.201.27.5:9080/web/services/WSS";

    public java.lang.String getWSSAddress() {
        return WSS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSSWSDDServiceName = "WSS";

    public java.lang.String getWSSWSDDServiceName() {
        return WSSWSDDServiceName;
    }

    public void setWSSWSDDServiceName(java.lang.String name) {
        WSSWSDDServiceName = name;
    }

    public com.ztesoft.vsop.bsn.webservice.WSS getWSS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSS(endpoint);
    }

    public com.ztesoft.vsop.bsn.webservice.WSS getWSS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ztesoft.vsop.bsn.webservice.WSSSoapBindingStub _stub = new com.ztesoft.vsop.bsn.webservice.WSSSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSSEndpointAddress(java.lang.String address) {
        WSS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ztesoft.vsop.bsn.webservice.WSS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ztesoft.vsop.bsn.webservice.WSSSoapBindingStub _stub = new com.ztesoft.vsop.bsn.webservice.WSSSoapBindingStub(new java.net.URL(WSS_address), this);
                _stub.setPortName(getWSSWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSS".equals(inputPortName)) {
            return getWSS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://bll.webservice.bsn.ztesoft.com", "WSSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://bll.webservice.bsn.ztesoft.com", "WSS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSS".equals(portName)) {
            setWSSEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
