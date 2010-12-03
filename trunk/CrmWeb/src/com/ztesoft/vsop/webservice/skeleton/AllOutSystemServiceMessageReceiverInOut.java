/**
 * AllOutSystemServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  AllOutSystemServiceMessageReceiverInOut message receiver
 */
public class AllOutSystemServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            AllOutSystemServiceSkeleton skel = (AllOutSystemServiceSkeleton) obj;

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
                if ("subResultFromVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse subResultFromVSOPReqResponse1 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subResultFromVSOPReqResponse1 = skel.subResultFromVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subResultFromVSOPReqResponse1, false);
                } else
                 if ("sendRQMessage".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse sendRQMessageReqResponse3 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SendRQMessageReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SendRQMessageReq) fromOM(msgContext.getEnvelope()
                                                                                           .getBody()
                                                                                           .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SendRQMessageReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    sendRQMessageReqResponse3 = skel.sendRQMessage(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            sendRQMessageReqResponse3, false);
                } else
                 if ("subResulToUser".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse subResulToUserReqResponse5 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubResulToUserReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SubResulToUserReq) fromOM(msgContext.getEnvelope()
                                                                                            .getBody()
                                                                                            .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubResulToUserReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subResulToUserReqResponse5 = skel.subResulToUser(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subResulToUserReqResponse5, false);
                } else
                 if ("workListVSOPToFK".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse workListVSOPToFKReqResponse7 =
                        null;
                    com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq) fromOM(msgContext.getEnvelope()
                                                                                              .getBody()
                                                                                              .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    workListVSOPToFKReqResponse7 = skel.workListVSOPToFK(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            workListVSOPToFKReqResponse7, false);
                } else
                 if ("feeCheck".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.FeeCheckResp feeCheckReqResponse9 =
                        null;
                    com.ztesoft.vsop.webservice.vo.FeeCheckReq wrappedParam = (com.ztesoft.vsop.webservice.vo.FeeCheckReq) fromOM(msgContext.getEnvelope()
                                                                                                                                            .getBody()
                                                                                                                                            .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.FeeCheckReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    feeCheckReqResponse9 = skel.feeCheck(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            feeCheckReqResponse9, false);
                } else
                 if ("subsInstSynToHB".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse subsInstSynToHBReqResponse11 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq) fromOM(msgContext.getEnvelope()
                                                                                             .getBody()
                                                                                             .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subsInstSynToHBReqResponse11 = skel.subsInstSynToHB(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subsInstSynToHBReqResponse11, false);
                } else
                 if ("subActionFromVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse subActionFromVSOPReqResponse13 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subActionFromVSOPReqResponse13 = skel.subActionFromVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subActionFromVSOPReqResponse13, false);
                } else
                 if ("chgActionFromVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse chgActionFromVSOPReqResponse15 =
                        null;
                    com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    chgActionFromVSOPReqResponse15 = skel.chgActionFromVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            chgActionFromVSOPReqResponse15, false);
                } else
                 if ("unSubActionFromVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse unSubActionFromVSOPReqResponse17 =
                        null;
                    com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                                 .getBody()
                                                                                                 .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    unSubActionFromVSOPReqResponse17 = skel.unSubActionFromVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            unSubActionFromVSOPReqResponse17, false);
                } else
                 if ("staffAuthFromVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse staffAuthFromVSOPReqResponse19 =
                        null;
                    com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    staffAuthFromVSOPReqResponse19 = skel.staffAuthFromVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            staffAuthFromVSOPReqResponse19, false);
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
        com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.FeeCheckReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.FeeCheckReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.FeeCheckResp param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.FeeCheckResp.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubResulToUserReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubResulToUserReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SendRQMessageReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SendRQMessageReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.FeeCheckResp param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.FeeCheckResp.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse.MY_QNAME,
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
            if (com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.FeeCheckReq.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.FeeCheckReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.FeeCheckResp.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.FeeCheckResp.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubResulToUserReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubResulToUserReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SendRQMessageReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SendRQMessageReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
