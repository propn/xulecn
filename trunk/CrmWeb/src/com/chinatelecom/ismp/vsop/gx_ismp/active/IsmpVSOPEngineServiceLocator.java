/**
 * IsmpVSOPEngineServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class IsmpVSOPEngineServiceLocator extends org.apache.axis.client.Service implements com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngineService {

    public IsmpVSOPEngineServiceLocator() {
    }


    public IsmpVSOPEngineServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IsmpVSOPEngineServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for IsmpVSOPEngine
    private java.lang.String IsmpVSOPEngine_address = "http://localhost:8080/services/IsmpVSOPEngine";

    public java.lang.String getIsmpVSOPEngineAddress() {
        return IsmpVSOPEngine_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IsmpVSOPEngineWSDDServiceName = "IsmpVSOPEngine";

    public java.lang.String getIsmpVSOPEngineWSDDServiceName() {
        return IsmpVSOPEngineWSDDServiceName;
    }

    public void setIsmpVSOPEngineWSDDServiceName(java.lang.String name) {
        IsmpVSOPEngineWSDDServiceName = name;
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngine_PortType getIsmpVSOPEngine() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IsmpVSOPEngine_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIsmpVSOPEngine(endpoint);
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngine_PortType getIsmpVSOPEngine(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngineSoapBindingStub _stub = new com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngineSoapBindingStub(portAddress, this);
            _stub.setPortName(getIsmpVSOPEngineWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIsmpVSOPEngineEndpointAddress(java.lang.String address) {
        IsmpVSOPEngine_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngine_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngineSoapBindingStub _stub = new com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngineSoapBindingStub(new java.net.URL(IsmpVSOPEngine_address), this);
                _stub.setPortName(getIsmpVSOPEngineWSDDServiceName());
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
        if ("IsmpVSOPEngine".equals(inputPortName)) {
            return getIsmpVSOPEngine();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "IsmpVSOPEngineService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "IsmpVSOPEngine"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IsmpVSOPEngine".equals(portName)) {
            setIsmpVSOPEngineEndpointAddress(address);
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
