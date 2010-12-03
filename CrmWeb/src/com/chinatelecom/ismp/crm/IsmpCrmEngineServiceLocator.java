/**
 * IsmpCrmEngineServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm;

public class IsmpCrmEngineServiceLocator extends org.apache.axis.client.Service implements com.chinatelecom.ismp.crm.IsmpCrmEngineService {

    public IsmpCrmEngineServiceLocator() {
    }


    public IsmpCrmEngineServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IsmpCrmEngineServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IsmpCrmEngine
    private java.lang.String IsmpCrmEngine_address = "http://134.224.18.38:8002/intf/services/IsmpCrmEngine";

    public java.lang.String getIsmpCrmEngineAddress() {
        return IsmpCrmEngine_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IsmpCrmEngineWSDDServiceName = "IsmpCrmEngine";

    public java.lang.String getIsmpCrmEngineWSDDServiceName() {
        return IsmpCrmEngineWSDDServiceName;
    }

    public void setIsmpCrmEngineWSDDServiceName(java.lang.String name) {
        IsmpCrmEngineWSDDServiceName = name;
    }

    public com.chinatelecom.ismp.crm.IsmpCrmEngine getIsmpCrmEngine() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IsmpCrmEngine_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIsmpCrmEngine(endpoint);
    }

    public com.chinatelecom.ismp.crm.IsmpCrmEngine getIsmpCrmEngine(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.chinatelecom.ismp.crm.IsmpCrmEngineSoapBindingStub _stub = new com.chinatelecom.ismp.crm.IsmpCrmEngineSoapBindingStub(portAddress, this);
            _stub.setPortName(getIsmpCrmEngineWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIsmpCrmEngineEndpointAddress(java.lang.String address) {
        IsmpCrmEngine_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.chinatelecom.ismp.crm.IsmpCrmEngine.class.isAssignableFrom(serviceEndpointInterface)) {
                com.chinatelecom.ismp.crm.IsmpCrmEngineSoapBindingStub _stub = new com.chinatelecom.ismp.crm.IsmpCrmEngineSoapBindingStub(new java.net.URL(IsmpCrmEngine_address), this);
                _stub.setPortName(getIsmpCrmEngineWSDDServiceName());
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
        if ("IsmpCrmEngine".equals(inputPortName)) {
            return getIsmpCrmEngine();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "IsmpCrmEngineService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "IsmpCrmEngine"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IsmpCrmEngine".equals(portName)) {
            setIsmpCrmEngineEndpointAddress(address);
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
