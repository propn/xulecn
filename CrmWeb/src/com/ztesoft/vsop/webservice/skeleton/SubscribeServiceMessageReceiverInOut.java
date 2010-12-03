/**
 * SubscribeServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  SubscribeServiceMessageReceiverInOut message receiver
 */
public class SubscribeServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            SubscribeServiceSkeleton skel = (SubscribeServiceSkeleton) obj;

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
                if ("subscribeToVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse subscribeToVSOPReqResponse1 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq wrappedParam = (com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                                                                                  .getBody()
                                                                                                                                                  .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subscribeToVSOPReqResponse1 = skel.subscribeToVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subscribeToVSOPReqResponse1, false);
                } else
                 if ("subInstQryFromVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse subInstQryFromVSOPReqResponse3 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                            .getBody()
                                                                                            .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subInstQryFromVSOPReqResponse3 = skel.subInstQryFromVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subInstQryFromVSOPReqResponse3, false);
                } else
                 if ("subscribeAuth".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse subscribeAuthReqResponse5 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubscribeAuthReq wrappedParam = (com.ztesoft.vsop.webservice.vo.SubscribeAuthReq) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubscribeAuthReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subscribeAuthReqResponse5 = skel.subscribeAuth(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subscribeAuthReqResponse5, false);
                } else
                 if ("workListFKToVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse workListFKToVSOPReqResponse7 =
                        null;
                    com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq wrappedParam = (com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                                                                                    .getBody()
                                                                                                                                                    .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    workListFKToVSOPReqResponse7 = skel.workListFKToVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            workListFKToVSOPReqResponse7, false);
                } else
                 if ("subsInstSynToVSOP".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse subsInstSynToVSOPReqResponse9 =
                        null;
                    com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq) fromOM(msgContext.getEnvelope()
                                                                                           .getBody()
                                                                                           .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    subsInstSynToVSOPReqResponse9 = skel.subsInstSynToVSOP(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            subsInstSynToVSOPReqResponse9, false);
                } else
                 if ("recvRQMessage".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse recvRQMessageReqResponse11 =
                        null;
                    com.ztesoft.vsop.webservice.vo.RecvRQMessageReq wrappedParam = (com.ztesoft.vsop.webservice.vo.RecvRQMessageReq) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.RecvRQMessageReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    recvRQMessageReqResponse11 = skel.recvRQMessage(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            recvRQMessageReqResponse11, false);
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
        com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.RecvRQMessageReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.RecvRQMessageReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubscribeAuthReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubscribeAuthReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse.MY_QNAME,
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
            if (com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.RecvRQMessageReq.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.RecvRQMessageReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubscribeAuthReq.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.SubscribeAuthReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
