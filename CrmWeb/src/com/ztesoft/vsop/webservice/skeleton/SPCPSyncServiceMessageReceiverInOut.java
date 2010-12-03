/**
 * SPCPSyncServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


/**
 *  SPCPSyncServiceMessageReceiverInOut message receiver
 */
public class SPCPSyncServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            SPCPSyncServiceSkeleton skel = (SPCPSyncServiceSkeleton) obj;

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
                if ("CPSPInfoSync".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse cPSPInfoSyncReqResponse1 =
                        null;
                    com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq wrappedParam = (com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq) fromOM(msgContext.getEnvelope()
                                                                                                                                            .getBody()
                                                                                                                                            .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    cPSPInfoSyncReqResponse1 = skel.CPSPInfoSync(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            cPSPInfoSyncReqResponse1, false);
                } else
                 if ("CPSPCapabilitySync".equals(methodName)) {
                    com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse cPSPCapabilitySyncReqResponse3 =
                        null;
                    com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq wrappedParam =
                        (com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq) fromOM(msgContext.getEnvelope()
                                                                                            .getBody()
                                                                                            .getFirstElement(),
                            com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    cPSPCapabilitySyncReqResponse3 = skel.CPSPCapabilitySync(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            cPSPCapabilitySyncReqResponse3, false);
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
        com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse.MY_QNAME,
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
            if (com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq.class.equals(type)) {
                return com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse.class.equals(
                        type)) {
                return com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
