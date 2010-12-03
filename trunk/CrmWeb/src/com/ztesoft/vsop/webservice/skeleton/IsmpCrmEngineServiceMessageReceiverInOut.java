/**
 * IsmpCrmEngineServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  IsmpCrmEngineServiceMessageReceiverInOut message receiver
 */
public class IsmpCrmEngineServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            IsmpCrmEngineServiceSkeleton skel = (IsmpCrmEngineServiceSkeleton) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(
                            op.getName().getLocalPart())) != null)) {
                if ("workOrderNotify".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response11 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8) fromOM(msgContext.getEnvelope()
                                                                                             .getBody()
                                                                                             .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response11 = skel.workOrderNotify(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response11, false);
                } else
                 if ("workOrderNotifyCnfm".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response13 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response13 = skel.workOrderNotifyCnfm(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response13, false);
                } else
                 if ("createISMPUser".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response15 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9) fromOM(msgContext.getEnvelope()
                                                                                            .getBody()
                                                                                            .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response15 = skel.createISMPUser(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response15, false);
                } else
                 if ("notifyUserState".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response17 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12) fromOM(msgContext.getEnvelope()
                                                                                              .getBody()
                                                                                              .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response17 = skel.notifyUserState(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response17, false);
                } else
                 if ("notifyUserFeeType".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response19 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response19 = skel.notifyUserFeeType(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response19, false);
                } else
                 if ("serviceCapabilitySubscriptionSync".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response21 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11) fromOM(msgContext.getEnvelope()
                                                                                                                .getBody()
                                                                                                                .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response21 = skel.serviceCapabilitySubscriptionSync(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response21, false);
                } else
                 if ("orderRelationUpdateNotify".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response23 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2) fromOM(msgContext.getEnvelope()
                                                                                                       .getBody()
                                                                                                       .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response23 = skel.orderRelationUpdateNotify(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response23, false);
                } else
                 if ("createSubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17 createSubscriptionRsp25 =
                        null;
                    com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15) fromOM(msgContext.getEnvelope()
                                                                                                 .getBody()
                                                                                                 .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    createSubscriptionRsp25 = skel.createSubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            createSubscriptionRsp25, false);
                } else
                 if ("modifySubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19 modifySubscriptionRsp27 =
                        null;
                    com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10) fromOM(msgContext.getEnvelope()
                                                                                                 .getBody()
                                                                                                 .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    modifySubscriptionRsp27 = skel.modifySubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            modifySubscriptionRsp27, false);
                } else
                 if ("withdrawSubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16 withdrawSubscriptionRsp29 =
                        null;
                    com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    withdrawSubscriptionRsp29 = skel.withdrawSubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            withdrawSubscriptionRsp29, false);
                } else
                 if ("getSubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21 getSubscriptionRsp31 =
                        null;
                    com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3) fromOM(msgContext.getEnvelope()
                                                                                             .getBody()
                                                                                             .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getSubscriptionRsp31 = skel.getSubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getSubscriptionRsp31, false);
                } else
                 if ("withdrawAllSubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response33 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7) fromOM(msgContext.getEnvelope()
                                                                                                     .getBody()
                                                                                                     .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response33 = skel.withdrawAllSubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response33, false);
                } else
                 if ("querySubscriptionHistory".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18 querySubscriptionHistoryRsp35 =
                        null;
                    com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13) fromOM(msgContext.getEnvelope()
                                                                                                       .getBody()
                                                                                                       .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    querySubscriptionHistoryRsp35 = skel.querySubscriptionHistory(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            querySubscriptionHistoryRsp35, false);
                } else
                 if ("suspendSubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response37 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4) fromOM(msgContext.getEnvelope()
                                                                                                 .getBody()
                                                                                                 .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response37 = skel.suspendSubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response37, false);
                } else
                 if ("resumeSubscription".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response39 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1) fromOM(msgContext.getEnvelope()
                                                                                                .getBody()
                                                                                                .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response39 = skel.resumeSubscription(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response39, false);
                } else
                 if ("subscriptionSync".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.jx.Response20 response41 = null;
                    com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0 wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0) fromOM(msgContext.getEnvelope()
                                                                                              .getBody()
                                                                                              .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    response41 = skel.subscriptionSync(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            response41, false);
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.Response20 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.Response20.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.jx.Response20 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.jx.Response20.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19 param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type, java.util.Map extraNamespaces)
        throws org.apache.axis2.AxisFault {
        try {
            if (com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.GetSubscriptionReq3.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.GetSubscriptionRsp21.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserFeeTypeReq6.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.ServiceCapabilitySubscriptionSyncReq11.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.QuerySubscriptionHistoryReq13.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.QuerySubscriptionHistoryRsp18.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.CreateSubscriptionReq15.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.CreateSubscriptionRsp17.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.OrderRelationUpdateNotifyReq2.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.SubscriptionSyncReq0.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.CreateISMPUserReq9.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyReq8.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.WithdrawAllSubscriptionReq7.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.WorkOrderNotifyCnfmReq14.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.WithdrawSubscriptionReq5.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.WithdrawSubscriptionRsp16.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.ModifySubscriptionReq10.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.ModifySubscriptionRsp19.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.ResumeSubscriptionReq1.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.NotifyUserStateReq12.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.jx.req.SuspendSubscriptionReq4.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.jx.Response20.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.jx.Response20.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    /**
     *  A utility method that copies the namepaces from the SOAPEnvelope
     */
    private java.util.Map getEnvelopeNamespaces(
        org.apache.axiom.soap.SOAPEnvelope env) {
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();

        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
        }

        return returnMap;
    }

    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
