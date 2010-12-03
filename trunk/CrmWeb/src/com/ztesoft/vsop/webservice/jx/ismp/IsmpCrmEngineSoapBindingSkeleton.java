/**
 * IsmpCrmEngineSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.webservice.jx.ismp;

public class IsmpCrmEngineSoapBindingSkeleton implements com.ztesoft.vsop.webservice.jx.ismp.IsmpCrmEngine, org.apache.axis.wsdl.Skeleton {
    private com.ztesoft.vsop.webservice.jx.ismp.IsmpCrmEngine impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "workOrderNotifyReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyReq"), com.ztesoft.vsop.webservice.jx.ismp.req.WorkOrderNotifyReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("workOrderNotify", _params, new javax.xml.namespace.QName("", "workOrderNotifyReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "workOrderNotify"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("workOrderNotify") == null) {
            _myOperations.put("workOrderNotify", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("workOrderNotify")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "workOrderNotifyCnfmReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WorkOrderNotifyCnfmReq"), com.ztesoft.vsop.webservice.jx.ismp.req.WorkOrderNotifyCnfmReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("workOrderNotifyCnfm", _params, new javax.xml.namespace.QName("", "workOrderNotifyCnfmReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "workOrderNotifyCnfm"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("workOrderNotifyCnfm") == null) {
            _myOperations.put("workOrderNotifyCnfm", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("workOrderNotifyCnfm")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "createISMPUserReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "createISMPUserReq"), com.ztesoft.vsop.webservice.jx.ismp.req.CreateISMPUserReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("createISMPUser", _params, new javax.xml.namespace.QName("", "createISMPUserReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "createISMPUser"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("createISMPUser") == null) {
            _myOperations.put("createISMPUser", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("createISMPUser")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "notifyUserStateReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "NotifyUserStateReq"), com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserStateReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("notifyUserState", _params, new javax.xml.namespace.QName("", "notifyUserStateReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "notifyUserState"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("notifyUserState") == null) {
            _myOperations.put("notifyUserState", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("notifyUserState")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "notifyUserFeeTypeReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "NotifyUserFeeTypeReq"), com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserFeeTypeReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("notifyUserFeeType", _params, new javax.xml.namespace.QName("", "notifyUserFeeTypeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "notifyUserFeeType"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("notifyUserFeeType") == null) {
            _myOperations.put("notifyUserFeeType", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("notifyUserFeeType")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "serviceCapabilitySubscriptionSyncReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ServiceCapabilitySubscriptionSyncReq"), com.ztesoft.vsop.webservice.jx.ismp.req.ServiceCapabilitySubscriptionSyncReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("serviceCapabilitySubscriptionSync", _params, new javax.xml.namespace.QName("", "serviceCapabilitySubscriptionSyncReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "serviceCapabilitySubscriptionSync"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("serviceCapabilitySubscriptionSync") == null) {
            _myOperations.put("serviceCapabilitySubscriptionSync", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("serviceCapabilitySubscriptionSync")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "orderRelationUpdateNotifyReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "OrderRelationUpdateNotifyReq"), com.ztesoft.vsop.webservice.jx.ismp.req.OrderRelationUpdateNotifyReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("orderRelationUpdateNotify", _params, new javax.xml.namespace.QName("", "orderRelationUpdateNotifyReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "orderRelationUpdateNotify"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("orderRelationUpdateNotify") == null) {
            _myOperations.put("orderRelationUpdateNotify", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("orderRelationUpdateNotify")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "createSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "CreateSubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.CreateSubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("createSubscription", _params, new javax.xml.namespace.QName("", "createSubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "CreateSubscriptionRsp"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "createSubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("createSubscription") == null) {
            _myOperations.put("createSubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("createSubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "modifySubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ModifySubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.ModifySubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("modifySubscription", _params, new javax.xml.namespace.QName("", "modifySubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "ModifySubscriptionRsp"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "modifySubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("modifySubscription") == null) {
            _myOperations.put("modifySubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("modifySubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "withdrawSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WithdrawSubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.WithdrawSubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("withdrawSubscription", _params, new javax.xml.namespace.QName("", "withdrawSubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "WithdrawSubscriptionRsp"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "withdrawSubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("withdrawSubscription") == null) {
            _myOperations.put("withdrawSubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("withdrawSubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "getSubscriptionRsp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "GetSubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.GetSubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getSubscription", _params, new javax.xml.namespace.QName("", "getSubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "GetSubscriptionRsp"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "getSubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getSubscription") == null) {
            _myOperations.put("getSubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getSubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "withdrawAllSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "WithdrawAllSubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.WithdrawAllSubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("withdrawAllSubscription", _params, new javax.xml.namespace.QName("", "withdrawAllSubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "withdrawAllSubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("withdrawAllSubscription") == null) {
            _myOperations.put("withdrawAllSubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("withdrawAllSubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "querySubscriptionHistoryReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryReq"), com.ztesoft.vsop.webservice.jx.ismp.req.QuerySubscriptionHistoryReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("querySubscriptionHistory", _params, new javax.xml.namespace.QName("", "querySubscriptionHistoryReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryRsp"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "querySubscriptionHistory"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("querySubscriptionHistory") == null) {
            _myOperations.put("querySubscriptionHistory", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("querySubscriptionHistory")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "suspendSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SuspendSubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.SuspendSubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("suspendSubscription", _params, new javax.xml.namespace.QName("", "suspendSubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "suspendSubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("suspendSubscription") == null) {
            _myOperations.put("suspendSubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("suspendSubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resumeSubscriptionReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ResumeSubscriptionReq"), com.ztesoft.vsop.webservice.jx.ismp.req.ResumeSubscriptionReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("resumeSubscription", _params, new javax.xml.namespace.QName("", "resumeSubscriptionReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "resumeSubscription"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("resumeSubscription") == null) {
            _myOperations.put("resumeSubscription", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("resumeSubscription")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "subscriptionSyncReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "SubscriptionSyncReq"), com.ztesoft.vsop.webservice.jx.ismp.req.SubscriptionSyncReq.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("subscriptionSync", _params, new javax.xml.namespace.QName("", "subscriptionSyncReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "Response"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://crm.ismp.chinatelecom.com", "subscriptionSync"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("subscriptionSync") == null) {
            _myOperations.put("subscriptionSync", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("subscriptionSync")).add(_oper);
    }

    public IsmpCrmEngineSoapBindingSkeleton() {
        this.impl = new com.ztesoft.vsop.webservice.jx.ismp.IsmpCrmEngineSoapBindingImpl();
    }

    public IsmpCrmEngineSoapBindingSkeleton(com.ztesoft.vsop.webservice.jx.ismp.IsmpCrmEngine impl) {
        this.impl = impl;
    }
    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response workOrderNotify(com.ztesoft.vsop.webservice.jx.ismp.req.WorkOrderNotifyReq workOrderNotifyReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.workOrderNotify(workOrderNotifyReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response workOrderNotifyCnfm(com.ztesoft.vsop.webservice.jx.ismp.req.WorkOrderNotifyCnfmReq workOrderNotifyCnfmReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.workOrderNotifyCnfm(workOrderNotifyCnfmReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response createISMPUser(com.ztesoft.vsop.webservice.jx.ismp.req.CreateISMPUserReq createISMPUserReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.createISMPUser(createISMPUserReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response notifyUserState(com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserStateReq notifyUserStateReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.notifyUserState(notifyUserStateReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response notifyUserFeeType(com.ztesoft.vsop.webservice.jx.ismp.req.NotifyUserFeeTypeReq notifyUserFeeTypeReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.notifyUserFeeType(notifyUserFeeTypeReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response serviceCapabilitySubscriptionSync(com.ztesoft.vsop.webservice.jx.ismp.req.ServiceCapabilitySubscriptionSyncReq serviceCapabilitySubscriptionSyncReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.serviceCapabilitySubscriptionSync(serviceCapabilitySubscriptionSyncReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response orderRelationUpdateNotify(com.ztesoft.vsop.webservice.jx.ismp.req.OrderRelationUpdateNotifyReq orderRelationUpdateNotifyReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.orderRelationUpdateNotify(orderRelationUpdateNotifyReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.CreateSubscriptionRsp createSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.CreateSubscriptionReq createSubscriptionReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.CreateSubscriptionRsp ret = impl.createSubscription(createSubscriptionReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.ModifySubscriptionRsp modifySubscription(com.ztesoft.vsop.webservice.jx.ismp.req.ModifySubscriptionReq modifySubscriptionReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.ModifySubscriptionRsp ret = impl.modifySubscription(modifySubscriptionReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.WithdrawSubscriptionRsp withdrawSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.WithdrawSubscriptionReq withdrawSubscriptionReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.WithdrawSubscriptionRsp ret = impl.withdrawSubscription(withdrawSubscriptionReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.GetSubscriptionRsp getSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.GetSubscriptionReq getSubscriptionRsp) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.GetSubscriptionRsp ret = impl.getSubscription(getSubscriptionRsp);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response withdrawAllSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.WithdrawAllSubscriptionReq withdrawAllSubscriptionReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.withdrawAllSubscription(withdrawAllSubscriptionReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.QuerySubscriptionHistoryRsp querySubscriptionHistory(com.ztesoft.vsop.webservice.jx.ismp.req.QuerySubscriptionHistoryReq querySubscriptionHistoryReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.QuerySubscriptionHistoryRsp ret = impl.querySubscriptionHistory(querySubscriptionHistoryReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response suspendSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.SuspendSubscriptionReq suspendSubscriptionReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.suspendSubscription(suspendSubscriptionReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response resumeSubscription(com.ztesoft.vsop.webservice.jx.ismp.req.ResumeSubscriptionReq resumeSubscriptionReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.resumeSubscription(resumeSubscriptionReq);
        return ret;
    }

    public com.ztesoft.vsop.webservice.jx.ismp.rsp.Response subscriptionSync(com.ztesoft.vsop.webservice.jx.ismp.req.SubscriptionSyncReq subscriptionSyncReq) throws java.rmi.RemoteException
    {
        com.ztesoft.vsop.webservice.jx.ismp.rsp.Response ret = impl.subscriptionSync(subscriptionSyncReq);
        return ret;
    }

}
