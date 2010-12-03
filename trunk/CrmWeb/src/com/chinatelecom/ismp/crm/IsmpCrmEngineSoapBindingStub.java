/**
 * IsmpCrmEngineSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm;

public class IsmpCrmEngineSoapBindingStub extends org.apache.axis.client.Stub implements com.chinatelecom.ismp.crm.IsmpCrmEngine {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[16];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("workOrderNotify");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "workOrderNotifyReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyReq"), com.chinatelecom.ismp.crm.req.WorkOrderNotifyReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "workOrderNotifyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("workOrderNotifyCnfm");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "workOrderNotifyCnfmReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyCnfmReq"), com.chinatelecom.ismp.crm.req.WorkOrderNotifyCnfmReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "workOrderNotifyCnfmReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createISMPUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "createISMPUserReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "createISMPUserReq"), com.chinatelecom.ismp.crm.req.CreateISMPUserReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "createISMPUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("notifyUserState");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "notifyUserStateReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "NotifyUserStateReq"), com.chinatelecom.ismp.crm.req.NotifyUserStateReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "notifyUserStateReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("notifyUserFeeType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "notifyUserFeeTypeReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "NotifyUserFeeTypeReq"), com.chinatelecom.ismp.crm.req.NotifyUserFeeTypeReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "notifyUserFeeTypeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("serviceCapabilitySubscriptionSync");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "serviceCapabilitySubscriptionSyncReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ServiceCapabilitySubscriptionSyncReq"), com.chinatelecom.ismp.crm.req.ServiceCapabilitySubscriptionSyncReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "serviceCapabilitySubscriptionSyncReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("orderRelationUpdateNotify");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "orderRelationUpdateNotifyReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "OrderRelationUpdateNotifyReq"), com.chinatelecom.ismp.crm.req.OrderRelationUpdateNotifyReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "orderRelationUpdateNotifyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createSubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "createSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "CreateSubscriptionReq"), com.chinatelecom.ismp.crm.req.CreateSubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "CreateSubscriptionRsp"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "createSubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("modifySubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "modifySubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ModifySubscriptionReq"), com.chinatelecom.ismp.crm.req.ModifySubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "ModifySubscriptionRsp"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "modifySubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("withdrawSubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "withdrawSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WithdrawSubscriptionReq"), com.chinatelecom.ismp.crm.req.WithdrawSubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "WithdrawSubscriptionRsp"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "withdrawSubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getSubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "getSubscriptionRsp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "GetSubscriptionReq"), com.chinatelecom.ismp.crm.req.GetSubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "GetSubscriptionRsp"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "getSubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("withdrawAllSubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "withdrawAllSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WithdrawAllSubscriptionReq"), com.chinatelecom.ismp.crm.req.WithdrawAllSubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "withdrawAllSubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("querySubscriptionHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "querySubscriptionHistoryReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryReq"), com.chinatelecom.ismp.crm.req.QuerySubscriptionHistoryReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryRsp"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "querySubscriptionHistoryReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("suspendSubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "suspendSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SuspendSubscriptionReq"), com.chinatelecom.ismp.crm.req.SuspendSubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "suspendSubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("resumeSubscription");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resumeSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ResumeSubscriptionReq"), com.chinatelecom.ismp.crm.req.ResumeSubscriptionReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "resumeSubscriptionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("subscriptionSync");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "subscriptionSyncReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SubscriptionSyncReq"), com.chinatelecom.ismp.crm.req.SubscriptionSyncReq.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        oper.setReturnClass(com.chinatelecom.ismp.crm.rsp.Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "subscriptionSyncReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

    }

    public IsmpCrmEngineSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public IsmpCrmEngineSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public IsmpCrmEngineSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "ArrayOf_tns1_SubHistoryInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.SubHistoryInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "SubHistoryInfo");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "ArrayOf_tns1_SubInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.SubInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "SubInfo");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "createISMPUserReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.CreateISMPUserReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "CreateSubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.CreateSubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "GetSubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.GetSubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ModifySubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.ModifySubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "NotifyUserFeeTypeReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.NotifyUserFeeTypeReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "NotifyUserStateReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.NotifyUserStateReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "OrderRelationUpdateNotifyReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.OrderRelationUpdateNotifyReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.QuerySubscriptionHistoryReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ResumeSubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.ResumeSubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ServiceCapabilitySubscriptionSyncReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.ServiceCapabilitySubscriptionSyncReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SubscriptionSyncReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.SubscriptionSyncReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SuspendSubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.SuspendSubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WithdrawAllSubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.WithdrawAllSubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WithdrawSubscriptionReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.WithdrawSubscriptionReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyCnfmReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.WorkOrderNotifyCnfmReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyReq");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.req.WorkOrderNotifyReq.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "CreateSubscriptionRsp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "GetSubscriptionRsp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "ModifySubscriptionRsp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryRsp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.Response.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "SubHistoryInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.SubHistoryInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "SubInfo");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.SubInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "WithdrawSubscriptionRsp");
            cachedSerQNames.add(qName);
            cls = com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

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

    public com.chinatelecom.ismp.crm.rsp.Response workOrderNotify(com.chinatelecom.ismp.crm.req.WorkOrderNotifyReq workOrderNotifyReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "workOrderNotify"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {workOrderNotifyReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response workOrderNotifyCnfm(com.chinatelecom.ismp.crm.req.WorkOrderNotifyCnfmReq workOrderNotifyCnfmReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "workOrderNotifyCnfm"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {workOrderNotifyCnfmReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response createISMPUser(com.chinatelecom.ismp.crm.req.CreateISMPUserReq createISMPUserReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "createISMPUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {createISMPUserReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response notifyUserState(com.chinatelecom.ismp.crm.req.NotifyUserStateReq notifyUserStateReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "notifyUserState"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {notifyUserStateReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response notifyUserFeeType(com.chinatelecom.ismp.crm.req.NotifyUserFeeTypeReq notifyUserFeeTypeReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "notifyUserFeeType"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {notifyUserFeeTypeReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response serviceCapabilitySubscriptionSync(com.chinatelecom.ismp.crm.req.ServiceCapabilitySubscriptionSyncReq serviceCapabilitySubscriptionSyncReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "serviceCapabilitySubscriptionSync"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {serviceCapabilitySubscriptionSyncReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response orderRelationUpdateNotify(com.chinatelecom.ismp.crm.req.OrderRelationUpdateNotifyReq orderRelationUpdateNotifyReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "orderRelationUpdateNotify"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {orderRelationUpdateNotifyReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp createSubscription(com.chinatelecom.ismp.crm.req.CreateSubscriptionReq createSubscriptionReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "createSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {createSubscriptionReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.CreateSubscriptionRsp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp modifySubscription(com.chinatelecom.ismp.crm.req.ModifySubscriptionReq modifySubscriptionReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "modifySubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {modifySubscriptionReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.ModifySubscriptionRsp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp withdrawSubscription(com.chinatelecom.ismp.crm.req.WithdrawSubscriptionReq withdrawSubscriptionReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "withdrawSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {withdrawSubscriptionReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.WithdrawSubscriptionRsp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp getSubscription(com.chinatelecom.ismp.crm.req.GetSubscriptionReq getSubscriptionRsp) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "getSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getSubscriptionRsp});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.GetSubscriptionRsp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response withdrawAllSubscription(com.chinatelecom.ismp.crm.req.WithdrawAllSubscriptionReq withdrawAllSubscriptionReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "withdrawAllSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {withdrawAllSubscriptionReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp querySubscriptionHistory(com.chinatelecom.ismp.crm.req.QuerySubscriptionHistoryReq querySubscriptionHistoryReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "querySubscriptionHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {querySubscriptionHistoryReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.QuerySubscriptionHistoryRsp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response suspendSubscription(com.chinatelecom.ismp.crm.req.SuspendSubscriptionReq suspendSubscriptionReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "suspendSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {suspendSubscriptionReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response resumeSubscription(com.chinatelecom.ismp.crm.req.ResumeSubscriptionReq resumeSubscriptionReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "resumeSubscription"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {resumeSubscriptionReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.chinatelecom.ismp.crm.rsp.Response subscriptionSync(com.chinatelecom.ismp.crm.req.SubscriptionSyncReq subscriptionSyncReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "subscriptionSync"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {subscriptionSyncReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.chinatelecom.ismp.crm.rsp.Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.chinatelecom.ismp.crm.rsp.Response) org.apache.axis.utils.JavaUtils.convert(_resp, com.chinatelecom.ismp.crm.rsp.Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
