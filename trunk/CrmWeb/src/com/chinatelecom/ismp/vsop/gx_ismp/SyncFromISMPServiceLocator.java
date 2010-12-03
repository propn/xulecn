/**
 * SyncFromISMPServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class SyncFromISMPServiceLocator extends org.apache.axis.client.Service implements com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMPService {

    public SyncFromISMPServiceLocator() {
    }


    public SyncFromISMPServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SyncFromISMPServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SyncFromISMP
    private java.lang.String SyncFromISMP_address = "http://localhost:8080/mdmc/services/SyncFromISMP";

    public java.lang.String getSyncFromISMPAddress() {
        return SyncFromISMP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SyncFromISMPWSDDServiceName = "SyncFromISMP";

    public java.lang.String getSyncFromISMPWSDDServiceName() {
        return SyncFromISMPWSDDServiceName;
    }

    public void setSyncFromISMPWSDDServiceName(java.lang.String name) {
        SyncFromISMPWSDDServiceName = name;
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMP_PortType getSyncFromISMP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SyncFromISMP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSyncFromISMP(endpoint);
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMP_PortType getSyncFromISMP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMPSoapBindingStub _stub = new com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMPSoapBindingStub(portAddress, this);
            _stub.setPortName(getSyncFromISMPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSyncFromISMPEndpointAddress(java.lang.String address) {
        SyncFromISMP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMP_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMPSoapBindingStub _stub = new com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMPSoapBindingStub(new java.net.URL(SyncFromISMP_address), this);
                _stub.setPortName(getSyncFromISMPWSDDServiceName());
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
        if ("SyncFromISMP".equals(inputPortName)) {
            return getSyncFromISMP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "SyncFromISMPService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "SyncFromISMP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SyncFromISMP".equals(portName)) {
            setSyncFromISMPEndpointAddress(address);
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
