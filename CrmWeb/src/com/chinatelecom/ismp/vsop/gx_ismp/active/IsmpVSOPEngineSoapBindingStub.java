/**
 * IsmpVSOPEngineSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class IsmpVSOPEngineSoapBindingStub extends org.apache.axis.client.Stub implements com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngine_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("subActionToISMP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "SubActionToISMPReq"), com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "SubActionToISMPResp"));
        oper.setReturnClass(com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "subActionToISMPReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("unSubActionToISMP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "UnSubActionToISMPReq"), com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionToISMPReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "UnSubActionFromVSOPResp"));
        oper.setReturnClass(com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "unSubActionToISMPReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("subsInstSynFromISMP");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "SubsInstSynFromISMPReq"), com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "SubsInstSynFromISMPResp"));
        oper.setReturnClass(com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "subsInstSynFromISMPReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

    }

    public IsmpVSOPEngineSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public IsmpVSOPEngineSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public IsmpVSOPEngineSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProductInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.ProductInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "SubActionToISMPReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "SubsInstSynFromISMPReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "UnSubActionToISMPReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionToISMPReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "ResultInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.ResultInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "SubActionToISMPResp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "SubsInstSynFromISMPResp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "UnSubActionFromVSOPResp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "ArrayOf_tns1_ResultInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.ResultInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "ResultInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "ArrayOf_tns2_ProductInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.vsop.gx_ismp.active.ProductInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProductInfo");
            qName2 = new javax.xml.namespace.QName("", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp subActionToISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPReq req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "subActionToISMP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp unSubActionToISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionToISMPReq req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "unSubActionToISMP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp subsInstSynFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPReq req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://vsop.ismp.chinatelecom.com", "subsInstSynFromISMP"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
